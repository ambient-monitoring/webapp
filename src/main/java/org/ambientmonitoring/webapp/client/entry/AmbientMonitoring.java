package org.ambientmonitoring.webapp.client.entry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import org.ambientmonitoring.webapp.client.layout.Layout;

public class AmbientMonitoring implements EntryPoint {

    @Override
    public void onModuleLoad() {
        RootPanel.get("content").add(new Layout());
    }

}
