package net.redcw.lobbysystem.internal.database;

/*
Class created by SpigotSource on 02.02.2020 at 14:05
*/

import com.mongodb.client.MongoCollection;
import net.redcw.redapi.RedAPI;
import org.bson.Document;

public class MongoManager {

    private MongoCollection<Document> friendSystem;

    public void connect()
    {
        this.friendSystem = RedAPI.getInstance().getMongoDatabase().getCollection("bungee_friendsystem");
    }

    public MongoCollection<Document> getFriendSystem() { return friendSystem; }

}
