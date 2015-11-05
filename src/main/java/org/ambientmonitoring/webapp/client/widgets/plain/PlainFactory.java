package org.ambientmonitoring.webapp.client.widgets.plain;

import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.components.ComponentFactory;
import org.ambientmonitoring.webapp.shared.constants.SharedConstants;

import java.util.List;

public class PlainFactory extends ComponentFactory {

    private static PlainFactory instance;

    @Override
    public String getComponentId() {
        return SharedConstants.Components.PLAIN;
    }

    @Override
    public Widget getWidget(List<String> params) {
        return new PlainWidget(params);
    }

    public static PlainFactory getInstance() {
        if (instance == null) {
            instance = new PlainFactory();
        }

        return instance;
    }
}
