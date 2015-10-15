package org.ambientmonitoring.webapp.client.layout;

import com.google.gwt.user.client.ui.SimplePanel;
import org.ambientmonitoring.webapp.client.AreaChartExample;
import org.ambientmonitoring.webapp.client.LineChartExample;

public class Layout extends SimplePanel {

    public Layout() {
        setWidget(new LineChartExample());
//        setWidget(new AreaChartExample());
    }
}
