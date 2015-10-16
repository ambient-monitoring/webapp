package org.ambientmonitoring.webapp.client;

import com.google.gwt.user.client.ui.SimplePanel;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;

public class ChartExample extends SimplePanel{

    public ChartExample() {
        Chart chart = new Chart()
                .setType(Series.Type.SPLINE)
                .setChartTitleText("Lawn Tunnels")
                .setMarginRight(10);

        Series series = chart.createSeries()
                .setName("Moles per Yard")
                .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
        chart.addSeries(series);

        setWidget(chart);
    }
}
