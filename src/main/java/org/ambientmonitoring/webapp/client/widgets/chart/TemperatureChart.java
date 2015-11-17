package org.ambientmonitoring.webapp.client.widgets.chart;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.moxieapps.gwt.highcharts.client.*;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.BarPlotOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemperatureChart extends SimplePanel {

    private Map<Integer, Series> seriesMap = new HashMap<>();
    private int sensorId;

    public TemperatureChart(int sensorId) {
        this.sensorId = sensorId;
        initUi();

        loadValues();
    }

    private void initUi() {
        setWidget(createChart());
    }

    private Chart createChart() {
        Chart chart = new Chart()
                .setType(Series.Type.SPLINE)
                .setMarginRight(10)
                .setChartTitleText("Temperature " + sensorId)
                .setBarPlotOptions(new BarPlotOptions().setDataLabels(new DataLabels().setEnabled(true)))
                .setLegend(new Legend().setEnabled(false))
                .setCredits(new Credits().setEnabled(false))
//                .setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
//
//                            @Override
//                            public String format(ToolTipData toolTipData) {
//                                return NumberFormat.getFormat("0.00").format(toolTipData.getYAsDouble()) + " °C";
//                            }
//                        })
//                );
                ;

        chart.getXAxis()
                .setType(Axis.Type.DATE_TIME)
                .setTickPixelInterval(150);

        chart.getYAxis()
                .setAxisTitleText("Temperature °C")
                .setPlotLines(chart.getYAxis().createPlotLine()
                                .setValue(0)
                                .setWidth(1)
                                .setColor("#808080")
                );

        initSeries(chart);

        return chart;
    }

    private void initSeries(Chart chart) {
        for (int i = 1; i < 5; i++) {
            Series series = chart.createSeries().setName("Sensor " + i);
            chart.addSeries(series);

            seriesMap.put(i, series);
        }
    }

    private void loadValues() {
        loadHistory();
    }

    private void loadHistory() {

        AmbientRPC.getLastReadings(sensorId, 1, new AsyncCallback<List<ReadingRPC>>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(List<ReadingRPC> result) {
                Series series = seriesMap.get(sensorId);

                for (ReadingRPC readingRPC : result) {
                    series.addPoint(readingRPC.timestamp, readingRPC.temperature);
                }
            }
        });
    }
}
