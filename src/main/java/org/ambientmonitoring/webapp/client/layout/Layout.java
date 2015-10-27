package org.ambientmonitoring.webapp.client.layout;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.ambientmonitoring.webapp.client.chart.HumidityGaugeChart;
import org.ambientmonitoring.webapp.client.chart.Setable;
import org.ambientmonitoring.webapp.client.chart.TemperatureGaugeChart;
import org.ambientmonitoring.webapp.client.rpc.AmbientRPC;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

import java.util.*;

public class Layout extends FlowPanel {

    public Map<Integer, Set<Setable>> setableMap = new HashMap<>();

    private long lastTimestamp = new Date().getTime();

    public Layout() {
        initUi();

        loadValues();
    }

    private void initUi() {
        // temp
        HorizontalPanel hp2 = new HorizontalPanel();
        add(hp2);

        hp2.add(getTempGauge(2, "Living Room - Temperature", true));
        hp2.add(getTempGauge(1, "Bedroom 1 - Temperature", true));
        hp2.add(getTempGauge(3, "Kitchen - Temperature", true));
        hp2.add(getTempGauge(4, "Outside (NW) - Temperature", false));

        // hum
        HorizontalPanel hp = new HorizontalPanel();
        add(hp);

        hp.add(getHumGauge(2, "Living Room - Humidity", true));
        hp.add(getHumGauge(1, "Bedroom 1 - Humidity", true));
        hp.add(getHumGauge(3, "Kitchen - Humidity", true));
        hp.add(getHumGauge(4, "Outside (NW) - Humidity", false));
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
                Set<Setable> setables = setableMap.get(reading.id);

                for (Setable setable : setables) {
                    setable.setReading(reading);
                }
            }
            GWT.log("reading = " + reading.toString());
        }
    }

    public Widget getTempGauge(Integer sensorId, String title, boolean indoor) {
        TemperatureGaugeChart widget = new TemperatureGaugeChart(sensorId, title, indoor);

        addToSetables(sensorId, widget);

        return widget;
    }

    public Widget getHumGauge(Integer sensorId, String title, boolean indoor) {
        HumidityGaugeChart widget = new HumidityGaugeChart(sensorId, title, indoor);

        addToSetables(sensorId, widget);

        return widget;
    }

    private void addToSetables(Integer sensorId, Setable setable) {
        Set<Setable> setables = new HashSet<>();
        setables.add(setable);

        if (setableMap.containsKey(sensorId)) {
            setableMap.get(sensorId).addAll(setables);
        } else {
            setableMap.put(sensorId, setables);
        }
    }
}
