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

    public ReadingRPC getLastReading(Integer id, long lastTimestamp) {
        Document docFind = new Document("id", id);
        Document docSort = new Document("timestamp", -1);

        MongoCursor<Document> cursor = MongoDB.Collections.getReadingsCollection().find(docFind).sort(docSort).limit(1).iterator();

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

        return ServerUtil.getReadingRpc(entity);
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
}
