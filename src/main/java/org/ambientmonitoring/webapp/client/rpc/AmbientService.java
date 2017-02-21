package org.ambientmonitoring.webapp.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

import java.util.List;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("rpc")
public interface AmbientService extends RemoteService {

    ReadingRPC getLastReading(Integer id, long lastTimestamp);

    List<ReadingRPC> getLastReadings(Integer id, int count);

    List<ReadingRPC> getReadingsSince(long lastTimestamp, boolean withSignal);
}
