package org.ambientmonitoring.webapp.client.layout;

import com.google.gwt.user.client.ui.FlowPanel;
import org.ambientmonitoring.webapp.client.chart.LiveChart;

public class Layout extends FlowPanel {

    public Layout() {
        add(new LiveChart(2, "Living Room - Humidity"));
//        add(new LiveChart(1, "Bedroom 1 - Humidity"));
//        add(new LiveChart(3, "Kitchen - Humidity"));
    }
}
