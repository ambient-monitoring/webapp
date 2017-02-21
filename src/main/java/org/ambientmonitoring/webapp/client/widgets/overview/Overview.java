package org.ambientmonitoring.webapp.client.widgets.overview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.client.widgets.sensor.Sensor;
import org.ambientmonitoring.webapp.client.widgets.temperature.TemperatureWidget;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.gwtbootstrap3.client.ui.Column;
import org.gwtbootstrap3.client.ui.Row;
import org.gwtbootstrap3.client.ui.constants.ColumnSize;

import java.util.*;

public class Overview extends SimplePanel {

    interface MyUiBinder extends UiBinder<Widget, Overview> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    Row fieldRow;

    public Map<Integer, Set<Sensor>> sensorMap = new HashMap<>();
    private long lastTimestamp = new Date().getTime();

    private Timer timer;

    public Overview(List<String> params) {
        initUi();

        loadValues();
    }

    private void initUi() {
        setWidget(uiBinder.createAndBindUi(this));

        addSensors();
    }

    private void addSensors() {
        // todo load from db dynamically

        Column column = new Column(ColumnSize.XS_12, ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_4);
        column.add(getTempWidget(2, "Living Room", true));
        fieldRow.add(column);

        column = new Column(ColumnSize.XS_12, ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_4);
        column.add(getTempWidget(1, "Bedroom", true));
        fieldRow.add(column);

        column = new Column(ColumnSize.XS_12, ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_4);
        column.add(getTempWidget(3, "Kitchen", true));
        fieldRow.add(column);

        column = new Column(ColumnSize.XS_12, ColumnSize.SM_6, ColumnSize.MD_6, ColumnSize.LG_4);
        column.add(getTempWidget(4, "Outside", false));
        fieldRow.add(column);
    }

    private void loadValues() {
        timer = new Timer() {

            @Override
            public void run() {
                AmbientRPC.getReadingsSince(lastTimestamp, true, new AsyncCallback<List<ReadingRPC>>() {
                    @Override
                    public void onFailure(Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(List<ReadingRPC> readings) {
                        if (!readings.isEmpty()) {
                            lastTimestamp = readings.get(readings.size() - 1).timestamp;
                        }

                        loadReadings(readings);
                    }
                });
            }
        };

        timer.scheduleRepeating(4 * 1000);

        // todo repeating timer that updates the warning colors for readings not received in a while
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        if (!timer.isRunning()) {
            timer.run();
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();

        if (timer.isRunning()) {
            timer.cancel();
        }
    }

    private void loadReadings(List<ReadingRPC> readings) {
        // update data
        for (ReadingRPC reading : readings) {
            if (sensorMap.containsKey(reading.id)) {
                Set<Sensor> sensors = sensorMap.get(reading.id);

                for (Sensor sensor : sensors) {
                    sensor.setReading(reading);
                }
            }
        }

        // update colors
        // we update every sensor because we need to signal lack of data, not only out of range data
        // e.g.: no reading received for a while
        for (Set<Sensor> sensors : sensorMap.values()) {
            for (Sensor sensor : sensors) {
                sensor.updateColors();
            }
        }
    }

    public Widget getTempWidget(Integer sensorId, String title, boolean indoor) {
        TemperatureWidget widget = new TemperatureWidget(sensorId, title, indoor);

        addToSetables(sensorId, widget);

        return widget;
    }

    private void addToSetables(Integer sensorId, Sensor sensor) {
        Set<Sensor> sensors = new HashSet<>();
        sensors.add(sensor);

        if (sensorMap.containsKey(sensorId)) {
            sensorMap.get(sensorId).addAll(sensors);
        } else {
            sensorMap.put(sensorId, sensors);
        }
    }

}
