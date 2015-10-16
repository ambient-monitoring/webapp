package org.ambientmonitoring.webapp.client.entry;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import org.ambientmonitoring.webapp.client.rpc.GreetingService;
import org.ambientmonitoring.webapp.client.rpc.GreetingServiceAsync;
import org.ambientmonitoring.webapp.client.layout.Layout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AmbientMonitoring implements EntryPoint {
    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        RootPanel.get("content").add(new Layout());
//        RootPanel.get("content").add(new Label("asdsa"));

    }

}
