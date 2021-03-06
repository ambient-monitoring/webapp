package org.ambientmonitoring.webapp.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.ambientmonitoring.webapp.client.rpc.AmbientService;
import org.ambientmonitoring.webapp.server.manager.ReadingManager;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

import java.util.List;

/**
 * The server-side implementation of the RPC service.
 */
@Singleton
public class AmbientServiceImpl extends RemoteServiceServlet implements AmbientService {

    public static final String ENDPOINT = "/ambientmonitoring/rpc";

    @Inject
    Injector injector;

    @Override
    public ReadingRPC getLastReading(Integer id) {
        ReadingManager manager = injector.getInstance(ReadingManager.class);
        return manager.getLastReading(id);
    }

    @Override
    public List<ReadingRPC> getLastReadings(Integer id, int count) {
        ReadingManager manager = injector.getInstance(ReadingManager.class);
        return manager.getLastReadings(id, count);
    }


    @Override
    public List<ReadingRPC> getReadingsSince(long lastTimestamp, boolean withSignal) {
        ReadingManager manager = injector.getInstance(ReadingManager.class);
        return manager.getReadingsSince(lastTimestamp, withSignal);
    }
}
