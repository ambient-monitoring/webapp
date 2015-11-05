package org.ambientmonitoring.webapp.client.widgets.content;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import org.ambientmonitoring.webapp.client.widgets.plain.PlainFactory;
import org.gwtbootstrap3.client.ui.Jumbotron;

public class Content extends SimplePanel {

    private static final String STYLE_NAME = "contentPanel";

    private static Content content;

    protected Content() {
        initUi();
    }

    private void initUi() {
        setStyleName(STYLE_NAME);
        Jumbotron jumbotron = new Jumbotron();
        Label label = new Label("Click me");

        jumbotron.add(label);

        label.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                History.newItem(PlainFactory.getInstance().getComponentId()); // todo
            }
        });


        setWidget(jumbotron);
    }

    public static Content getInstance() {
        if (content == null) {
            content = new Content();
        }

        return content;
    }
}
