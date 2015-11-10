package org.ambientmonitoring.webapp.server.adapter;

import org.ambientmonitoring.webapp.server.entity.ReadingEntity;
import org.bson.Document;

public class ReadingAdapter {

    public static ReadingEntity getEntity(Document doc) {
        ReadingEntity entity = new ReadingEntity();

        entity.id = doc.getInteger("id");
        entity.temperature = doc.getDouble("temperature");
        entity.humidity = doc.getDouble("humidity");
        entity.voltage = doc.getInteger("voltage");
        entity.timestamp = doc.getLong("timestamp");

        return entity;
    }
}
