package org.ambientmonitoring.webapp.server.manager;

import com.mongodb.client.MongoCursor;
import org.ambientmonitoring.webapp.server.adapter.ReadingAdapter;
import org.ambientmonitoring.webapp.server.entity.ReadingEntity;
import org.ambientmonitoring.webapp.server.mongodb.MongoDB;
import org.ambientmonitoring.webapp.server.util.ServerUtil;
import org.ambientmonitoring.webapp.shared.rpc.ReadingRPC;
import org.bson.Document;

import java.util.*;

public class ReadingManager {

    private Integer getSignal(Integer id) {
        Document docFind = new Document("id", id);
        Document docSort = new Document("timestamp", -1);

        MongoCursor<Document> cursor = MongoDB.Collections.getReadingsCollection()
                .find(docFind)
                .sort(docSort)
                .limit(100)
                .iterator();

        List<ReadingEntity> entities = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document obj = cursor.next();

                entities.add(ReadingAdapter.getEntity(obj));
            }
        } finally {
            cursor.close();
        }

        int signal = 100;

        for (int i = 1; i < entities.size(); i++) {
            int curr = entities.get(i - 1).counter;
            int prev = entities.get(i).counter;

            int diff = curr - prev;

            if (diff < 0) {
                continue; // counter wrap-around
            }

            signal -= diff - 1;
        }

        return signal;
    }

    public ReadingRPC getLastReading(Integer id) {
        Document docFind = new Document("id", id);
        Document docSort = new Document("timestamp", -1);

        MongoCursor<Document> cursor = MongoDB.Collections.getReadingsCollection()
                .find(docFind)
                .sort(docSort)
                .limit(1)
                .iterator();

        ReadingEntity entity = null;

        try {
            while (cursor.hasNext()) {
                Document obj = cursor.next();

                entity = ReadingAdapter.getEntity(obj);
            }
        } finally {
            cursor.close();
        }

        if (entity == null) {
            return null;
        }

        ReadingRPC rpc = ServerUtil.getReadingRpc(entity);

        rpc.signal = getSignal(rpc.id);

        return rpc;
    }

    public List<ReadingRPC> getLastReadings(Integer id, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -hours);
        Date since = cal.getTime();

        Document find = new Document("id", id)
                .append("timestamp", new Document("$gt", since.getTime()));

        Document sort = new Document("timestamp", -1);

        MongoCursor<Document> cursor = MongoDB.Collections.getReadingsCollection().find(find).sort(sort).iterator();

        List<ReadingRPC> rpcs = new ArrayList<>();

        ReadingEntity entity = null;

        try {
            while (cursor.hasNext()) {
                Document obj = cursor.next();

                entity = ReadingAdapter.getEntity(obj);

                if (entity == null) {
                    continue;
                }

                rpcs.add(ServerUtil.getReadingRpc(entity));
            }
        } finally {
            cursor.close();
        }

        Collections.reverse(rpcs);

        return rpcs;
    }

    public List<ReadingRPC> getReadingsSince(long lastTimestamp, boolean withSignal) {
        Document find = new Document("timestamp", new Document("$gt", lastTimestamp));
        Document sort = new Document("timestamp", 1);

        MongoCursor<Document> cursor = MongoDB.Collections.getReadingsCollection().find(find).sort(sort).iterator();

        ReadingEntity entity = null;

        List<ReadingRPC> rpcs = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document obj = cursor.next();

                entity = ReadingAdapter.getEntity(obj);

                rpcs.add(ServerUtil.getReadingRpc(entity));
            }
        } finally {
            cursor.close();
        }

        if (withSignal) {
            for (ReadingRPC rpc : rpcs) {
                rpc.signal = getSignal(rpc.id);
            }
        }

        return rpcs;
    }
}
