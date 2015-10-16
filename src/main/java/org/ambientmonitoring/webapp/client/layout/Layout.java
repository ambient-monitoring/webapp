package org.ambientmonitoring.webapp.client.layout;

import com.google.gwt.user.client.ui.SimplePanel;
import org.ambientmonitoring.webapp.client.chart.SplineUpdatingExample;

public class Layout extends SimplePanel {

    public Layout() {
        setWidget(new SplineUpdatingExample());
    }
}
