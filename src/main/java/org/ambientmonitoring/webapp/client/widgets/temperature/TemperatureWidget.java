package org.ambientmonitoring.webapp.client.widgets.temperature;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.client.widgets.sensor.Sensor;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.constants.LabelType;

import java.util.Date;

public class TemperatureWidget extends SimplePanel implements Sensor {

    private static final int HUMIDITY_SAFE_MIN = 40;
    private static final int HUMIDITY_SAFE_MAX = 60;

    private static final int HUMIDITY_WARNING_MIN = 40;
    private static final int HUMIDITY_WARNING_MAX = 60;

    private static final int HUMIDITY_DANGER_MIN = 20;
    private static final int HUMIDITY_DANGER_MAX = 80;

    private static final int VOLTAGE_SAFE = 3700;
    private static final int VOLTAGE_WARNING = 3700;
    private static final int VOLTAGE_DANGER = 3000;

    private static final long TIMESTAMP_SAFE = 900 * 1000; // 15 minutes
    private static final long TIMESTAMP_DANGER = 3600 * 1000; // 1 hour


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
    Label fieldUpdated;
    @UiField
    Label fieldSignal;

    private final Integer sensorId;
    private final String title;
    private final boolean inside;

    private ReadingRPC reading;
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

        fieldTitle.setText(title);
    }

    private void loadValues() {
        AmbientRPC.getLastReading(sensorId, new AsyncCallback<ReadingRPC>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(ReadingRPC result) {
                loadReading(result);

                updateColors();
            }
        });
    }

    private void loadReading(ReadingRPC reading) {
        this.reading = reading;

        if (reading.timestamp <= lastTimestamp) {
            return;
        }

        fieldTemp.setText(reading.temperature + " °C");
        fieldHum.setText(reading.humidity + " %");
        fieldVcc.setText(reading.voltage + " mV");
        fieldSignal.setText(reading.signal + " %");
        // todo show date if more than a few hours
        fieldUpdated.setText(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.TIME_FULL).format(new Date(reading.timestamp)));

        lastTimestamp = reading.timestamp;
    }

    @Override
    public void updateColors() {
        if (inside) {
            // humidity
            if (reading.humidity >= HUMIDITY_SAFE_MIN && reading.humidity <= HUMIDITY_SAFE_MAX) {
                fieldHum.setType(LabelType.PRIMARY);
            }

            if (reading.humidity < HUMIDITY_WARNING_MIN || reading.humidity > HUMIDITY_WARNING_MAX) {
                fieldHum.setType(LabelType.WARNING);
            }

            if (reading.humidity < HUMIDITY_DANGER_MIN || reading.humidity > HUMIDITY_DANGER_MAX) {
                fieldHum.setType(LabelType.DANGER);
            }
        }

        // voltage
        if (reading.voltage >= VOLTAGE_SAFE) {
            fieldVcc.setType(LabelType.PRIMARY);
        }

        if (reading.voltage < VOLTAGE_WARNING) {
            fieldVcc.setType(LabelType.WARNING);
        }

        if (reading.voltage < VOLTAGE_DANGER) {
            fieldVcc.setType(LabelType.DANGER);
        }

        // last update
        long now = System.currentTimeMillis();

        if (now - reading.timestamp <= TIMESTAMP_SAFE) { // under 15 minutes
            fieldUpdated.setType(LabelType.DEFAULT);
        }

        if (now - reading.timestamp > TIMESTAMP_SAFE) { // over 15 minutes
            fieldUpdated.setType(LabelType.WARNING);
        }

        if (now - reading.timestamp > TIMESTAMP_DANGER) { // over 1 hour
            fieldUpdated.setType(LabelType.DANGER);
        }
    }

    @Override
    public void setReading(ReadingRPC reading) {
        loadReading(reading);
    }
}
