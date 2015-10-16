package org.ambientmonitoring.webapp.client.chart;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SimplePanel;
import org.moxieapps.gwt.highcharts.client.*;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.BarPlotOptions;

import java.util.Date;

public class SplineUpdatingExample extends SimplePanel {

    public SplineUpdatingExample() {
        setWidget(createChart());
    }

    public Chart createChart() {

        final Chart chart = new Chart()
                .setType(Series.Type.SPLINE)
                .setMarginRight(10)
                .setChartTitleText("Live random data")
                .setBarPlotOptions(new BarPlotOptions()
                                .setDataLabels(new DataLabels()
                                                .setEnabled(true)
                                )
                )
                .setLegend(new Legend()
                                .setEnabled(false)
                )
                .setCredits(new Credits()
                                .setEnabled(false)
                )
                .setToolTip(new ToolTip()
                                .setFormatter(new ToolTipFormatter() {
                                    public String format(ToolTipData toolTipData) {
                                        return "<b>" + toolTipData.getSeriesName() + "</b><br/>" +
                                                DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss")
                                                        .format(new Date(toolTipData.getXAsLong())) + "<br/>" +
                                                NumberFormat.getFormat("0.00").format(toolTipData.getYAsDouble());
                                    }
                                })
                );

        chart.getXAxis()
                .setType(Axis.Type.DATE_TIME)
                .setTickPixelInterval(150);

        chart.getYAxis()
                .setAxisTitleText("Value")
                .setPlotLines(
                        chart.getYAxis().createPlotLine()
                                .setValue(0)
                                .setWidth(1)
                                .setColor("#808080")
                );

        final Series series = chart.createSeries();
        chart.addSeries(series
                        .setName("Random data")
        );

        // Generate an array of random data
        long time = new Date().getTime();
        for (int i = -19; i <= 0; i++) {
            series.addPoint(time + i * 1000, com.google.gwt.user.client.Random.nextDouble());
        }

        Timer tempTimer = new Timer() {
            @Override
            public void run() {
                series.addPoint(
                        new Date().getTime(),
                        com.google.gwt.user.client.Random.nextDouble(),
                        true, true, true
                );
            }
        };
        tempTimer.scheduleRepeating(1000);

        return chart;
    }

}
