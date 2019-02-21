package org.kaccag.io;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public abstract class SaveableMongo<T> extends Saveable<T> {
    protected MongoCollection<Document> collection;

    protected SaveableMongo() {
    }
}
