package org.ambientmonitoring.webapp.client.widgets.indoor;

import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.components.ComponentFactory;
import org.ambientmonitoring.webapp.shared.constants.SharedConstants;

import java.util.List;

public class IndoorFactory extends ComponentFactory {

    private static IndoorFactory instance;

    @Override
    public String getComponentId() {
        return SharedConstants.Components.INDOOR;
    }

    @Override
    public Widget getWidget(List<String> params) {
        return new Indoor(params);
    }

    public static IndoorFactory getInstance() {
        if (instance == null) {
            instance = new IndoorFactory();
        }

        return instance;
    }
}
