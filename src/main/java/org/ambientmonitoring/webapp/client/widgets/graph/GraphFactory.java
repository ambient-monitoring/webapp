package org.ambientmonitoring.webapp.client.widgets.graph;

import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.components.ComponentFactory;
import org.ambientmonitoring.webapp.shared.constants.SharedConstants;

import java.util.List;

public class GraphFactory extends ComponentFactory {

    private static GraphFactory instance;

    @Override
    public String getComponentId() {
        return SharedConstants.Components.GRAPH;
    }

    @Override
    public Widget getWidget(List<String> params) {
        return new Graph(params);
    }

    public static GraphFactory getInstance() {
        if (instance == null) {
            instance = new GraphFactory();
        }

        return instance;
    }
}
