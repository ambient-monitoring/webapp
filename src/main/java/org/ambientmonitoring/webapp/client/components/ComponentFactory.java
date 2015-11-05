package org.ambientmonitoring.webapp.client.components;

import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.shared.history.HistoryHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class ComponentFactory {

    public abstract String getComponentId();

    public abstract Widget getWidget(List<String> params);

    public String createComponentToken() {
        List<String> params = new ArrayList<>();

        params.add(getComponentId());

        return HistoryHelper.encode(params);
    }
}
