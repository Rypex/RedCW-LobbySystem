package net.redcw.lobbysystem.player;

/*
Class created by SpigotSource on 04.01.2020 at 23:41
*/

import com.mongodb.client.model.Filters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.internal.listener.ProxiedRedListener;
import net.redcw.redapi.mongodb.gson.GsonToBsonAdapter;
import net.redcw.redapi.positions.Position;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Tareko on 29.10.2017.
 */
@Getter
@AllArgsConstructor
public class LobbyPlayer {

    private UUID uniqueId;

    private Position position;

    private Timestamp dailyRewardNormal;

    private Timestamp dailyRewardPrime;

    private boolean hubFly;

    private boolean scoreboard;

    private boolean chat;

    private Integer privateServerKeys, cookies, normalCaseKeys, epicCaseKeys;

    private Collection<String> foundedSecrets, settings;

    public LobbyPlayer(UUID uniqueId, Position position, Timestamp timestamp)
    {
        this.uniqueId = uniqueId;
        this.position = position;
        this.dailyRewardNormal = timestamp;
        this.dailyRewardPrime = timestamp;
        this.hubFly = false;
        this.scoreboard = true;
        this.chat = true;
        this.privateServerKeys = 0;
        this.cookies = 0;
        this.normalCaseKeys = 1;
        this.epicCaseKeys = 0;
        this.foundedSecrets = new ArrayList<>();
        this.settings = new ArrayList<>();
    }

    public void setPosition(Position position)
    {
        this.position = position;
        update();
    }

    public void setDailyRewardNormal(Timestamp dailyReward)
    {
        this.dailyRewardNormal = dailyReward;
        update();
    }

    public void setDailyRewardPrime(Timestamp dailyRewardPrime)
    {
        this.dailyRewardPrime = dailyRewardPrime;
        update();
    }

    public void setChat(boolean chat)
    {
        this.chat = chat;
        updateAsync();
    }

    public void setScoreboard(boolean scoreboard)
    {
        this.scoreboard = scoreboard;
        updateAsync();
    }

    public void setHubFly(boolean hubFly)
    {
        this.hubFly = hubFly;
        updateAsync();
    }

    public void setPrivateServerKeys(Integer privateServerKeys)
    {
        this.privateServerKeys = privateServerKeys;
        updateAsync();
    }

    public void setCookies(Integer cookies)
    {
        this.cookies = cookies;
        updateAsync();
    }

    public void setNormalCaseKeys(Integer normalCaseKeys) {
        this.normalCaseKeys = normalCaseKeys;
        updateAsync();
    }

    public void setEpicCaseKeys(Integer epicCaseKeys) {
        this.epicCaseKeys = epicCaseKeys;
        updateAsync();
    }

    public Position getPosition()
    {
        return GsonToBsonAdapter.toObjective((Document) LobbyService.getInstance().getLobbyPlayerProvider().getMongoCollection().find(Filters.eq("uniqueId", uniqueId.toString())).first().get("position"), Position.class);
    }

    public void update()
    {
        LobbyService.getInstance().getLobbyPlayerProvider().getMongoCollection().updateOne(Filters.eq("uniqueId", uniqueId.toString()),
                new Document("$set", GsonToBsonAdapter.toDocument(this)));
    }

    public void updateAsync()
    {
        RedAPI.getInstance().getProcessQueue().runTask(() -> update());
    }
}
