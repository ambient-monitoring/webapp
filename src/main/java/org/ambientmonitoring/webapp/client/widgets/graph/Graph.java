package org.ambientmonitoring.webapp.client.widgets.graph;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.widgets.chart.TemperatureChart;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;

import java.util.List;

public class Graph extends SimplePanel {

    interface MyUiBinder extends UiBinder<Widget, Graph> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Row fieldRows;

    public Graph(List<String> params) {
        initUi();
    }

    private void initUi() {
        setWidget(uiBinder.createAndBindUi(this));

        Column column = new Column(ColumnSize.XS_12, ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_4);
        column.add(new TemperatureChart(1));
        fieldRows.add(column);

        column = new Column(ColumnSize.XS_12, ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_4);
        column.add(new TemperatureChart(2));
        fieldRows.add(column);

        column = new Column(ColumnSize.XS_12, ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_4);
        column.add(new TemperatureChart(3));
        fieldRows.add(column);

        column = new Column(ColumnSize.XS_12, ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_4);
        column.add(new TemperatureChart(4));
        fieldRows.add(column);
    }

}
