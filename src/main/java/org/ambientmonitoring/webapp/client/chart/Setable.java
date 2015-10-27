package org.ambientmonitoring.webapp.client.chart;

import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

public interface Setable {

    void setReading(ReadingRPC reading);
}
