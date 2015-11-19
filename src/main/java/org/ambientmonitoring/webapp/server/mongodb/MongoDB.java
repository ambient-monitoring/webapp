package org.ambientmonitoring.webapp.server.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

public class MongoDB {

    private static MongoClient client;

    static {
//        // Enable MongoDB logging in general
//        System.setProperty("DEBUG.MONGO", "true");
//
//        // Enable DB operation tracing
//        System.setProperty("DB.TRACE", "true");

        MongoClientOptions opts = MongoClientOptions.builder()
                .readPreference(ReadPreference.primaryPreferred())
                .serverSelectionTimeout(60 * 1000) // 1 minute
                .connectionsPerHost(200)
                .socketTimeout(1 * 60 * 1000) // 2 minutes
                .socketKeepAlive(true)
                .localThreshold(1000) // 1 second
//                .requiredReplicaSetName(REPLICA_SET_NAME)
                .cursorFinalizerEnabled(true)
                .build();

        List<ServerAddress> servers = Arrays.asList(
                new ServerAddress("ambient", 27017));

        // or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
        client = new MongoClient(servers, opts);
    }

    protected MongoDB() {
    }

    public static MongoClient get() {
        return client;
    }

    public static class Databases {

        private static String DB_AMBIENT = "ambient";

        private static MongoDatabase getDatabase(String db) {
            return get().getDatabase(db);
        }

        public static MongoDatabase getAmbient() {
            return getDatabase(DB_AMBIENT);
        }
    }

    public static class Collections {

        private static String COLLECTION_READINGS = "readings";

        public static MongoCollection<Document> getReadingsCollection() {
            return Databases.getAmbient().getCollection(COLLECTION_READINGS);
        }
    }

}
