package org.ambientmonitoring.webapp.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

import java.util.List;

public interface AmbientServiceAsync {

    void getLastReading(Integer id, AsyncCallback<ReadingRPC> callback);

    void getLastReadings(Integer id, int count, AsyncCallback<List<ReadingRPC>> callback);

    void getReadingsSince(long lastTimestamp, boolean withSignal, AsyncCallback<List<ReadingRPC>> callback);
}
