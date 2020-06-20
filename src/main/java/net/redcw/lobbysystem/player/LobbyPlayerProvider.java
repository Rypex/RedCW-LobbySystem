package net.redcw.lobbysystem.player;

/*
Class created by SpigotSource on 04.01.2020 at 23:42
*/

import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import net.redcw.redapi.mongodb.gson.GsonToBsonAdapter;
import org.bson.Document;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class LobbyPlayerProvider {

    private MongoCollection<Document> mongoCollection;

    private Map<UUID, LobbyPlayer> players = new ConcurrentHashMap<>();

    public LobbyPlayerProvider(MongoCollection<Document> lobbyPlayers)
    {
        this.mongoCollection = lobbyPlayers;
    }

    public void registerPlayer(LobbyPlayer lobbyPlayer)
    {
        mongoCollection.insertOne(GsonToBsonAdapter.toDocument(lobbyPlayer));
    }

    public LobbyPlayer getPlayer(UUID uniqueId)
    {

        if(players.containsKey(uniqueId))
        {
            return players.get(uniqueId);
        }

        Document document = mongoCollection.find(Filters.eq("uniqueId", uniqueId.toString())).first();
        if(document == null) return null;

        return GsonToBsonAdapter.toObjective(document, new TypeToken<LobbyPlayer>(){}.getType());
    }


}
