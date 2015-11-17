package org.ambientmonitoring.webapp.client.widgets.overview;

import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.components.ComponentFactory;
import org.ambientmonitoring.webapp.shared.constants.SharedConstants;

import java.util.List;

public class OverviewFactory extends ComponentFactory {

    private static OverviewFactory instance;

    @Override
    public String getComponentId() {
        return SharedConstants.Components.OVERVIEW;
    }

    @Override
    public Widget getWidget(List<String> params) {
        return new Overview(params);
    }

    public static OverviewFactory getInstance() {
        if (instance == null) {
            instance = new OverviewFactory();
        }

        return instance;
    }
}
