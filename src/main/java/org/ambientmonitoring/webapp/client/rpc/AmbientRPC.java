package org.ambientmonitoring.webapp.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

import java.util.List;

public class AmbientRPC {

    private static final AmbientServiceAsync service = GWT.create(AmbientService.class);

    public static void getLastReading(Integer id, long lastTimestamp, AsyncCallback<ReadingRPC> callback) {
        service.getLastReading(id, lastTimestamp, callback);
    }

    public static void getLastReadings(Integer id, int count, AsyncCallback<List<ReadingRPC>> callback) {
        service.getLastReadings(id, count, callback);
    }

    public static void getReadingsSince(long lastTimestamp, boolean withSignal, AsyncCallback<List<ReadingRPC>> callback) {
        service.getReadingsSince(lastTimestamp, withSignal, callback);
    }
}
