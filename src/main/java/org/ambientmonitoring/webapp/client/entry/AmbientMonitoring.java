package org.ambientmonitoring.webapp.client.entry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import org.ambientmonitoring.webapp.client.layout.Layout;
import org.moxieapps.gwt.highcharts.client.Global;
import org.moxieapps.gwt.highcharts.client.Highcharts;

public class AmbientMonitoring implements EntryPoint {

    @Override
    public void onModuleLoad() {
        settings();

        RootPanel.get("content").add(new Layout());
    }

    private void settings() {
        Highcharts.setOptions(new Highcharts.Options().setGlobal(new Global().setUseUTC(false)));
    }

}
