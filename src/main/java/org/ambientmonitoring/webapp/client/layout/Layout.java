package org.ambientmonitoring.webapp.client.layout;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import org.ambientmonitoring.webapp.client.chart.HumidityGaugeChart;

public class Layout extends FlowPanel {

    public Layout() {
        HorizontalPanel hp = new HorizontalPanel();
        add(hp);

        hp.add(new HumidityGaugeChart(2, "Living Room - Humidity"));
        hp.add(new HumidityGaugeChart(1, "Bedroom 1 - Humidity"));
        hp.add(new HumidityGaugeChart(3, "Kitchen - Humidity"));

//        add(new LiveChart(2, "Living Room - Humidity"));
//        add(new LiveChart(1, "Bedroom 1 - Humidity"));
//        add(new LiveChart(3, "Kitchen - Humidity"));


    }
}
