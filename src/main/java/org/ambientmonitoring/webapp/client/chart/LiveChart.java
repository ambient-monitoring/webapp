package org.ambientmonitoring.webapp.client.chart;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.moxieapps.gwt.highcharts.client.*;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.BarPlotOptions;

import java.util.List;

public class LiveChart extends SimplePanel {

    private final String title;
    private Long lastTimestamp;

    public LiveChart(Integer sensorId, String title) {
        this.title = title;

        // todo get last 24 hour readings, not last N readings
        AmbientRPC.getLastReadings(sensorId, 100, new AsyncCallback<List<ReadingRPC>>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(List<ReadingRPC> result) {
                setWidget(createChart(result));
            }
        });
    }

    public Chart createChart(List<ReadingRPC> readings) {
        Chart chart = new Chart()
                .setType(Series.Type.SPLINE)
                .setMarginRight(10)
                .setChartTitleText(title)
                .setBarPlotOptions(new BarPlotOptions().setDataLabels(new DataLabels().setEnabled(true)))
                .setLegend(new Legend().setEnabled(false))
                .setCredits(new Credits().setEnabled(false))
                .setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {

                            @Override
                            public String format(ToolTipData toolTipData) {
                                return NumberFormat.getFormat("0.00").format(toolTipData.getYAsDouble()) + "%";

//                                return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +
//                                        DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss")
//                                                .format(new Date(toolTipData.getXAsLong())) + "<br/>" +
//                                        NumberFormat.getFormat("0.00").format(toolTipData.getYAsDouble());
                            }
                        })
                );

        chart.setHeight("200px");

        chart.getXAxis()
                .setType(Axis.Type.DATE_TIME)
                .setTickPixelInterval(150);

        chart.getYAxis()
                .setAxisTitleText("Humidity %")
                .setPlotLines(chart.getYAxis().createPlotLine()
                                .setValue(0)
                                .setWidth(1)
                                .setColor("#808080")
                );

        final Series series = chart.createSeries();
        chart.addSeries(series.setName("Humidity %")
        );

        // Generate an array of random data
        for (ReadingRPC reading : readings) {
            series.addPoint(reading.timestamp, reading.humidity);

            lastTimestamp = reading.timestamp;
        }

        Timer tempTimer = new Timer() {
            @Override
            public void run() {
                loadReading(series);
            }
        };
        tempTimer.scheduleRepeating(4000);

        return chart;
    }

    private void loadReading(final Series series) {
        AmbientRPC.getLastReading(2, 1l, new AsyncCallback<ReadingRPC>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(ReadingRPC result) {
                if (result.timestamp > lastTimestamp) {
                    series.addPoint(result.timestamp, result.humidity);
                }
            }
        });
    }
}
