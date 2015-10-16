package org.ambientmonitoring.webapp.client.entry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import org.ambientmonitoring.webapp.client.layout.Layout;
import org.ambientmonitoring.webapp.client.rpc.AmbientService;
import org.ambientmonitoring.webapp.client.rpc.AmbientServiceAsync;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AmbientMonitoring implements EntryPoint {
    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final AmbientServiceAsync greetingService = GWT.create(AmbientService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("content").add(new Layout());
//        RootLayoutPanel.get().add(new Layout());
//        RootPanel.get("content").add(new Label("asdsa"));

    }

}
