package org.ambientmonitoring.webapp.client.widgets.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.widgets.chart.TemperatureChart;
import org.gwtbootstrap3.client.ui.Row;

import java.util.List;

public class Graph extends SimplePanel {

    interface MyUiBinder extends UiBinder<Widget, Graph> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Row fieldRow;

    public Graph(List<String> params) {
        initUi();
    }

    private void initUi() {
        setWidget(uiBinder.createAndBindUi(this));

        fieldRow.add(new TemperatureChart());
    }

}
