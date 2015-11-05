package org.ambientmonitoring.webapp.client.entry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import org.ambientmonitoring.webapp.client.components.Components;
import org.ambientmonitoring.webapp.client.history.HistoryValueChangeHandler;
import org.ambientmonitoring.webapp.client.layout.Layout;
import org.ambientmonitoring.webapp.client.widgets.plain.PlainFactory;
import org.moxieapps.gwt.highcharts.client.Global;
import org.moxieapps.gwt.highcharts.client.Highcharts;

public class AmbientMonitoring implements EntryPoint {

    private static final String DIV_ID_CONTENT = "content";

    @Override
    public void onModuleLoad() {
        // setup history handlers
        History.addValueChangeHandler(new HistoryValueChangeHandler());

        // highchart settings
        doHighchartSettings();

        // load layout
        RootPanel.get(DIV_ID_CONTENT).add(Layout.getInstance());

        // register components
        registerComponents();

        // for direct URLs, fire our current history state
        History.fireCurrentHistoryState();
    }

    private void registerComponents() {
        Components.getInstance().register(PlainFactory.getInstance());
    }

    private void doHighchartSettings() {
        Highcharts.setOptions(new Highcharts.Options().setGlobal(new Global().setUseUTC(false)));
    }

}
