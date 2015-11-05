package org.ambientmonitoring.webapp.client.layout;

import com.google.gwt.user.client.ui.FlowPanel;
import org.ambientmonitoring.webapp.client.widgets.content.Content;
import org.ambientmonitoring.webapp.client.widgets.header.Header;

public class Layout extends FlowPanel {

    private static Layout layout;

    protected Layout() {
        initUi();
    }

    private void initUi() {
        add(Header.getInstance());
        add(Content.getInstance());
    }

    public static Layout getInstance() {
        if (layout == null) {
            layout = new Layout();
        }

        return layout;
    }
}