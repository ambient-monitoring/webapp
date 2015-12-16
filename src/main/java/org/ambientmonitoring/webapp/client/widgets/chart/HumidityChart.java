package org.ambientmonitoring.webapp.client.widgets.chart;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.moxieapps.gwt.highcharts.client.*;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.BarPlotOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HumidityChart extends AbstractChart {

    private Map<Integer, Series> seriesMap = new HashMap<>();
    private Chart chart;
    private long lastTimestamp;
    private Timer timer;

    public HumidityChart() {
        initUi();

        // todo proper server side call
        long now = new Date().getTime();
        long fourHoursAgo = now - (4 * 3600 * 1000);
//        long fourHoursAgo = now - (900 * 1000);

        loadValues(fourHoursAgo, false);

        loadNewValues();
    }

    private void initUi() {
        chart = createChart();

        setWidget(chart);
    }

    private Chart createChart() {
        Chart chart = new Chart()
                .setType(Series.Type.SPLINE)
                .setMarginRight(10)
                .setChartTitleText("Humidity")
                .setBarPlotOptions(new BarPlotOptions().setDataLabels(new DataLabels().setEnabled(false)))
                .setLegend(new Legend().setEnabled(true))
                .setCredits(new Credits().setEnabled(false))
                .setToolTip(new ToolTip().setEnabled(false));

        // X axis
        chart.getXAxis().setType(Axis.Type.DATE_TIME);

        // Y axis
        chart.getYAxis()
                .setAxisTitleText("Humidity %")
                .setPlotLines(chart.getYAxis().createPlotLine());

        // series
        initSeries(chart);

        return chart;
    }

    protected void initSeries(Chart chart) {
        // todo load from DB

        // id 1 - bedroom 1
        Series series = chart.createSeries().setName("Bedroom 1");
        chart.addSeries(setSeriesOpts(series));
        seriesMap.put(1, series);

        // id 2 - living room
        series = chart.createSeries().setName("Living Room");
        chart.addSeries(setSeriesOpts(series));
        seriesMap.put(2, series);

        // id 3 - kitchen
        series = chart.createSeries().setName("Kitchen");
        chart.addSeries(setSeriesOpts(series));
        seriesMap.put(3, series);

        // id 4 - outside
        series = chart.createSeries().setName("Outside (NW)");
        chart.addSeries(setSeriesOpts(series));
        seriesMap.put(4, series);
    }

    private void loadValues(long ts, final boolean animate) {
        AmbientRPC.getReadingsSince(ts, new AsyncCallback<List<ReadingRPC>>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(List<ReadingRPC> readings) {
                loadFromReadings(readings, animate);
            }
        });
    }

    private void loadFromReadings(List<ReadingRPC> readings, boolean animate) {
        for (ReadingRPC reading : readings) {
            Series series = seriesMap.get(reading.id);

            if (animate) {
                series.addPoint(reading.timestamp, reading.humidity);
            } else {
                series.addPoint(reading.timestamp, reading.humidity, false, false, false);
            }

            lastTimestamp = reading.timestamp;
        }

        if (!animate) {
            chart.redraw();
        }
    }

    private void loadNewValues() {
        timer = new Timer() {
            @Override
            public void run() {
                loadValues(lastTimestamp, true);
            }
        };

        // todo some main thread that dispatches to all widgets
        // todo start/stop on bind/unbind
        timer.scheduleRepeating(30 * 1000);
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        if (!timer.isRunning()) {
            timer.run();
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();

        if (timer.isRunning()) {
            timer.cancel();
        }
    }


}
