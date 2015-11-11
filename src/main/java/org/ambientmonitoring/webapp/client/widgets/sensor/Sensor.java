package org.ambientmonitoring.webapp.client.widgets.sensor;

import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

public interface Sensor {

    void setReading(ReadingRPC reading);

    void updateColors();
}
