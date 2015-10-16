package org.ambientmonitoring.webapp.server.util;

import org.ambientmonitoring.webapp.server.entity.ReadingEntity;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;

public class ServerUtil {

    public static ReadingRPC getReadingRpc(ReadingEntity entity) {
        ReadingRPC rpc = new ReadingRPC();

        rpc.id = entity.id;
        rpc.temperature = entity.temperature;
        rpc.humidity = entity.humidity;
        rpc.timestamp = entity.timestamp;

        return rpc;
    }
}
