package org.ambientmonitoring.webapp.client.widgets.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class Header extends SimplePanel {

    interface MyUiBinder extends UiBinder<Widget, Header> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    private static Header header;

    protected Header() {
        initUi();

        initHandlers();
    }

    private void initUi() {
        setWidget(uiBinder.createAndBindUi(this));
    }

    private void initHandlers() {
    }


    public static Header getInstance() {
        if (header == null) {
            header = new Header();
        }

        return header;
    }
}
