package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 05.01.2020 at 00:05
*/

import com.google.common.collect.Maps;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitPlayerUpdateEvent;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.manager.ItemManager;
import net.redcw.lobbysystem.player.LobbyPlayer;
import net.redcw.lobbysystem.utils.GameStats;
import net.redcw.redapi.RedAPI;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onLogin(final PlayerLoginEvent event)
    {
        LobbyPlayer lobbyPlayer = LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(event.getPlayer().getUniqueId());
        if (lobbyPlayer == null) {
            lobbyPlayer = new LobbyPlayer(event.getPlayer().getUniqueId(), LobbyService.getInstance().getSpawnLocations().getPosition("Spawn"), new Timestamp(0L));
            LobbyService.getInstance().getLobbyPlayerProvider().registerPlayer(lobbyPlayer);
        }

        final Document clanDocument = RedAPI.getInstance().getMongoDatabase().getCollection("bungee_clan_list").find(new Document("name", RedAPI.getInstance().getMongoDatabase().getCollection("bungee_clan_user").find(new Document("uniqueId", event.getPlayer().getUniqueId().toString())).first().getString("clan"))).first();

        if(clanDocument != null)
            LobbyService.getInstance().getClanDocuments().put(event.getPlayer().getUniqueId(), clanDocument);

        LobbyService.getInstance().getPlayerCookies().put(event.getPlayer().getUniqueId(), lobbyPlayer.getCookies());

        LobbyService.getInstance().getLobbyPlayerProvider().getPlayers().put(event.getPlayer().getUniqueId(), lobbyPlayer);
        LobbyService.getInstance().getPlayerGroups().put(event.getPlayer().getUniqueId(), CloudAPI.getInstance().getOnlinePlayer(lobbyPlayer.getUniqueId()).getPermissionEntity().getHighestPermissionGroup(CloudAPI.getInstance().getPermissionPool()));

        RedAPI.getInstance().getProcessQueue().runTask(() -> {
            final Document mlgRush = RedAPI.getInstance().getMongoDatabase().getCollection("mlgrush").find(new Document("uniqueId", event.getPlayer().getUniqueId().toString())).first();

            final Map<String, GameStats> gameStats = Maps.newConcurrentMap();

            gameStats.put("MLGRush", new GameStats(mlgRush.getInteger("kills"), mlgRush.getInteger("deaths"), mlgRush.getInteger("games")));

            LobbyService.getInstance().getPlayerGameStats().put(event.getPlayer().getUniqueId(), gameStats);
        });

    }

}
