package org.kaccag.io;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public abstract class SaveableMongo<T> extends Saveable<T> {
    protected final MongoCollection<Document> collection;

    protected SaveableMongo(String defaultDatabase, String defaultCollection) {
        String uri = generateConnectionUri();

        MongoClient client = new MongoClient(new MongoClientURI(uri));
        MongoDatabase db = client.getDatabase(defaultDatabase);
        collection = db.getCollection(defaultCollection);
    }

    protected abstract String generateConnectionUri();
}
