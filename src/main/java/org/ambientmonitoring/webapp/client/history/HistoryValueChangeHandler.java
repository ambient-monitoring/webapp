package org.ambientmonitoring.webapp.client.history;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import org.ambientmonitoring.webapp.client.components.ComponentFactory;
import org.ambientmonitoring.webapp.client.components.Components;
import org.ambientmonitoring.webapp.client.widgets.content.Content;
import org.ambientmonitoring.webapp.shared.history.HistoryHelper;

import java.util.List;

public class HistoryValueChangeHandler implements ValueChangeHandler<String> {

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        List<String> params = HistoryHelper.decode(event.getValue());

        if (params.isEmpty()) {
            return;
        }

        String componentId = params.get(0);

        ComponentFactory component = Components.getInstance().getComponent(componentId);

        if (component == null) {
            return;
        }

        Content.getInstance().setWidget(component.getWidget(params));
    }
}
