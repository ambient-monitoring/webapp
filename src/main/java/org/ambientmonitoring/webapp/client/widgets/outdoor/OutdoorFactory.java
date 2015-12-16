package org.ambientmonitoring.webapp.client.widgets.outdoor;

import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.components.ComponentFactory;
import org.ambientmonitoring.webapp.shared.constants.SharedConstants;

import java.util.List;

public class OutdoorFactory extends ComponentFactory {

    private static OutdoorFactory instance;

    @Override
    public String getComponentId() {
        return SharedConstants.Components.OUTDOOR;
    }

    @Override
    public Widget getWidget(List<String> params) {
        return new Outdoor(params);
    }

    public static OutdoorFactory getInstance() {
        if (instance == null) {
            instance = new OutdoorFactory();
        }

        return instance;
    }
}
