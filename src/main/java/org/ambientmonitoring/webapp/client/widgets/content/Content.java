package org.ambientmonitoring.webapp.client.widgets.content;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.SimplePanel;
import org.ambientmonitoring.webapp.client.widgets.plain.PlainFactory;

public class Content extends SimplePanel {

    private static final String STYLE_NAME = "contentPanel";

    private static Content content;

    protected Content() {
        initUi();
    }

    private void initUi() {
        History.newItem(PlainFactory.getInstance().getComponentId()); // todo
    }

    public static Content getInstance() {
        if (content == null) {
            content = new Content();
        }

        return content;
    }
}
