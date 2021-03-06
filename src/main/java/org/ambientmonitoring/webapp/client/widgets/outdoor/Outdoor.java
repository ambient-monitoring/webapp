package org.ambientmonitoring.webapp.client.widgets.outdoor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.widgets.chart.HumidityChart;
import org.ambientmonitoring.webapp.client.widgets.chart.TemperatureChart;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;

import java.util.List;

public class Outdoor extends SimplePanel {

    interface MyUiBinder extends UiBinder<Widget, Outdoor> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Row fieldRow;

    public Outdoor(List<String> params) {
        initUi();
    }

    private void initUi() {
        setWidget(uiBinder.createAndBindUi(this));

        Column column = new Column(ColumnSize.XS_12, ColumnSize.SM_12, ColumnSize.MD_6, ColumnSize.LG_6);
        column.add(new TemperatureChart(false));
        fieldRow.add(column);

        column = new Column(ColumnSize.XS_12, ColumnSize.SM_12, ColumnSize.MD_6, ColumnSize.LG_6);
        column.add(new HumidityChart(false));
        fieldRow.add(column);

    }

}
