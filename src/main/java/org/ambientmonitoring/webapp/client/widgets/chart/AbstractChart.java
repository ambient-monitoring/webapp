package org.ambientmonitoring.webapp.client.widgets.chart;

import com.google.gwt.user.client.ui.SimplePanel;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.plotOptions.SplinePlotOptions;

public abstract class AbstractChart extends SimplePanel {

    protected abstract void initSeries(Chart chart);

    protected Series setSeriesOpts(Series series) {
        return series.setPlotOptions(new SplinePlotOptions().setEnableMouseTracking(false));
    }
}
