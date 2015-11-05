package org.ambientmonitoring.webapp.client.widgets.temperature;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.client.widgets.chart.Updatable;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.html.Strong;

import java.util.Date;

public class TemperatureWidget extends SimplePanel implements Updatable {

    interface MyUiBinder extends UiBinder<Widget, TemperatureWidget> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Label fieldTemp;
    @UiField
    Label fieldHum;
    @UiField
    Heading fieldTitle;
    @UiField
    Strong fieldUpdated;

    private final Integer sensorId;
    private final String title;
    private final boolean inside;

    private long lastTimestamp;

    public TemperatureWidget(Integer sensorId, String title, boolean inside) {
        this.sensorId = sensorId;
        this.title = title;
        this.inside = inside;

        initUi();

        loadValues();
    }

    private void initUi() {
        setWidget(uiBinder.createAndBindUi(this));

        // todo
        getElement().getStyle().setPadding(10, Style.Unit.PX);
        fieldTitle.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
    }

    private void loadValues() {
        fieldTitle.setText(title);

        AmbientRPC.getLastReading(sensorId, 1l, new AsyncCallback<ReadingRPC>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(ReadingRPC result) {
                loadReading(result);
            }
        });
    }

    private void loadReading(ReadingRPC reading) {
        if (reading.timestamp <= lastTimestamp) {
            return;
        }

        fieldTemp.setText(reading.temperature + " °C");
        fieldHum.setText(reading.humidity + "%");
        // todo show date if more than a few hours
        fieldUpdated.setText("Updated: " + DateTimeFormat.getLongTimeFormat().format(new Date(reading.timestamp)));

        lastTimestamp = reading.timestamp;
    }

    @Override
    public void setReading(ReadingRPC reading) {
        loadReading(reading);
    }

}
