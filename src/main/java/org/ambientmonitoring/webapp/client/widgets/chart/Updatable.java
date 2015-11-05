package org.ambientmonitoring.webapp.client.widgets.chart;

import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

public interface Updatable {

    void setReading(ReadingRPC reading);
}
