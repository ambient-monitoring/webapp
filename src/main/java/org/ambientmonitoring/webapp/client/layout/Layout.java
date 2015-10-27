package org.ambientmonitoring.webapp.client.layout;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import org.ambientmonitoring.webapp.client.chart.HumidityGaugeChart;
import org.ambientmonitoring.webapp.client.chart.TemperatureGaugeChart;

public class Layout extends FlowPanel {

    public Layout() {
        HorizontalPanel hp2 = new HorizontalPanel();
        add(hp2);

        hp2.add(new TemperatureGaugeChart(2, "Living Room - Temperature", true));
        hp2.add(new TemperatureGaugeChart(1, "Bedroom 1 - Temperature", true));
        hp2.add(new TemperatureGaugeChart(3, "Kitchen - Temperature", true));
        hp2.add(new TemperatureGaugeChart(4, "Outside (NW) - Temperature", false));
        
        //////////

        HorizontalPanel hp = new HorizontalPanel();
        add(hp);

        hp.add(new HumidityGaugeChart(2, "Living Room - Humidity", true));
        hp.add(new HumidityGaugeChart(1, "Bedroom 1 - Humidity", true));
        hp.add(new HumidityGaugeChart(3, "Kitchen - Humidity", true));
        hp.add(new HumidityGaugeChart(4, "Outside (NW) - Humidity", false));


//        add(new LiveChart(2, "Living Room - Humidity"));
//        add(new LiveChart(1, "Bedroom 1 - Humidity"));
//        add(new LiveChart(3, "Kitchen - Humidity"));
//        add(new LiveChart(4, "Outside (NW) - Humidity"));


    }
}
