package org.ambientmonitoring.webapp.client.widgets.plain;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.client.widgets.chart.Updatable;
import org.ambientmonitoring.webapp.client.widgets.temperature.TemperatureWidget;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.gwtbootstrap3.client.ui.Heading;

import java.util.*;

public class PlainWidget extends SimplePanel {

    interface MyUiBinder extends UiBinder<Widget, PlainWidget> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField
    SimplePanel sensorPanel;
    @UiField
    Heading fieldHeading;

    public Map<Integer, Set<Updatable>> setableMap = new HashMap<>();
    private long lastTimestamp = new Date().getTime();

    public PlainWidget(List<String> params) {
        initUi();

        loadValues();
    }

    private void initUi() {
        setWidget(uiBinder.createAndBindUi(this));

        addSensors();
    }

    private void addSensors() {
        FlowPanel hp = new FlowPanel();

        hp.add(getTempWidget(2, "Living Room", true));
        hp.add(getTempWidget(1, "Bedroom 1", true));
        hp.add(getTempWidget(3, "Kitchen", true));
        hp.add(getTempWidget(4, "Outside (NW)", false));

        sensorPanel.setWidget(hp);
    }

    private void loadValues() {
        Timer timer = new Timer() {

            @Override
            public void run() {
                AmbientRPC.getReadingsSince(lastTimestamp, new AsyncCallback<List<ReadingRPC>>() {
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
    }

    private void loadReadings(List<ReadingRPC> readings) {
        for (ReadingRPC reading : readings) {
            // todo
            if (setableMap.containsKey(reading.id)) {
                Set<Updatable> updatables = setableMap.get(reading.id);

                for (Updatable updatable : updatables) {
                    updatable.setReading(reading);
                }
            }
            GWT.log("reading = " + reading.toString());
        }
    }

    public Widget getTempWidget(Integer sensorId, String title, boolean indoor) {
        TemperatureWidget widget = new TemperatureWidget(sensorId, title, indoor);

        addToSetables(sensorId, widget);

        return widget;
    }

    private void addToSetables(Integer sensorId, Updatable updatable) {
        Set<Updatable> updatables = new HashSet<>();
        updatables.add(updatable);

        if (setableMap.containsKey(sensorId)) {
            setableMap.get(sensorId).addAll(updatables);
        } else {
            setableMap.put(sensorId, updatables);
        }
    }

}
