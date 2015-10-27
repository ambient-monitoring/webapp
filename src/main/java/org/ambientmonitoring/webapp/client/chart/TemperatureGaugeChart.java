package org.ambientmonitoring.webapp.client.chart;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.moxieapps.gwt.highcharts.client.*;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.labels.YAxisLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.Dial;
import org.moxieapps.gwt.highcharts.client.plotOptions.GaugePlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.Pivot;

public class TemperatureGaugeChart extends FlowPanel {

    private final Integer sensorId;
    private final String title;
    private final boolean indoor;

    private long lastTimestamp;

    public TemperatureGaugeChart(Integer sensorId, String title, boolean indoor) {
        this.sensorId = sensorId;
        this.title = title;
        this.indoor = indoor;

        AmbientRPC.getLastReading(sensorId, 1l, new AsyncCallback<ReadingRPC>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(ReadingRPC result) {
                add(createChart(result.temperature));

                lastTimestamp = result.timestamp;
            }
        });
    }

    public Chart createChart(double temperature) {
        setWidth("400px");

        final Chart chart = new Chart()
                .setType(Series.Type.GAUGE)
                .setAlignTicks(false)
                .setPlotBackgroundImage(null)
                .setBorderWidth(0)
                .setPlotShadow(false)
                .setBackgroundColor("#ffffff")
                .setCredits(new Credits().setEnabled(false))
                .setChartTitle(new ChartTitle().setText(title))
                .setPane(new Pane().setStartAngle(-150).setEndAngle(150)
                );

        chart.setGaugePlotOptions(new GaugePlotOptions()
                        .setDataLabels(new DataLabels()
                                .setEnabled(true)
                                .setY(30)
                                .setFormat("{point.y} °C"))
                        .setPivotOptions(new Pivot().setRadius("3%"))
                        .setDialOptions(new Dial()
                                        .setRearLength("20%")
                                        .setBaseWidth(4)
                                        .setTopWidth(1)
                                        .setBackgroundColor("#FF5722")
                        )
        );

        // Primary axis
        chart.getYAxis(0)
                .setMin(-20)
                .setMax(50)
                .setTickPosition(Axis.TickPosition.INSIDE)
                .setMinorTickPosition(Axis.TickPosition.INSIDE)
                .setLineColor("#888")
                .setTickColor("#888")
                .setMinorTickColor("#888")
                .setOffset(-25)
                .setLineWidth(2)
                .setTickLength(5)
                .setMinorTickLength(5)
                .setEndOnTick(false)
                .setLabels(
                        new YAxisLabels()
                                // There is no documented "distance" option for gauge chart axes
                                .setOption("distance", -20)
                );

        // #55BF3B
        // #DDDF0D
        // #DF5353

        if (indoor) {
//        YAxis yAxis = chart.getYAxis();
//        yAxis.setPlotBands(yAxis.createPlotBand()
//                        .setColor("#DF5353")
//                        .setFrom(0)
//                        .setTo(20),
//                yAxis.createPlotBand()
//                        .setColor("#DDDF0D")
//                        .setFrom(20)
//                        .setTo(40),
//                yAxis.createPlotBand()
//                        .setColor("#55BF3B")
//                        .setFrom(40)
//                        .setTo(60),
//                yAxis.createPlotBand()
//                        .setColor("#DDDF0D")
//                        .setFrom(60)
//                        .setTo(70),
//                yAxis.createPlotBand()
//                        .setColor("#DF5353")
//                        .setFrom(70)
//                        .setTo(100)
//        );
        }

        final Series series = chart.createSeries();
        chart.addSeries(series
                        .setName("Temperature °C")
                        .addPoint(temperature)
        );

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
        AmbientRPC.getLastReading(sensorId, 1l, new AsyncCallback<ReadingRPC>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(ReadingRPC result) {
                if (result.timestamp > lastTimestamp) {
                    series.getPoints()[0].update(result.temperature);

                    lastTimestamp = result.timestamp;
                }
            }
        });
    }
}