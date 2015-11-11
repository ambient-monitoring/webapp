package org.ambientmonitoring.webapp.client.widgets.temperature;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.client.widgets.chart.Updatable;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.constants.LabelType;
import org.gwtbootstrap3.client.ui.constants.PanelType;
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
    Label fieldVcc;
    @UiField
    Heading fieldTitle;
    @UiField
    Strong fieldUpdated;
    @UiField
    Panel panel;

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
        fieldVcc.setText(reading.voltage + " mV");
        // todo show date if more than a few hours
        fieldUpdated.setText("Updated: " + DateTimeFormat.getLongTimeFormat().format(new Date(reading.timestamp)));

        lastTimestamp = reading.timestamp;

        updateColors(reading);
    }

    private void updateColors(ReadingRPC reading) {
        if (inside) {
            // humidity
            if (reading.humidity < 40 || reading.humidity > 60) {
                fieldHum.setType(LabelType.WARNING);
            }

            if (reading.humidity < 20 || reading.humidity > 80) {
                fieldHum.setType(LabelType.DANGER);
            }
        }

        // voltage
        if (reading.voltage < 3700) {
            fieldVcc.setType(LabelType.WARNING);
        }

        if (reading.voltage < 3000) {
            fieldVcc.setType(LabelType.DANGER);
        }

        // last update
        long now = System.currentTimeMillis();

        if (now - reading.timestamp > 900 * 1000) { // over 15 minutes
            panel.setType(PanelType.WARNING);
        }

        if (now - reading.timestamp > 3600 * 1000) { // over 1 hour
            panel.setType(PanelType.DANGER);
        }
    }

    @Override
    public void setReading(ReadingRPC reading) {
        loadReading(reading);
    }

}
