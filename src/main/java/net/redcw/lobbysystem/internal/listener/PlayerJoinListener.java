package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 05.01.2020 at 00:19
*/

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.manager.ItemManager;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.BukkitPositionAdapter;
import net.redcw.redapi.util.LabyModService;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(final PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();

        event.setJoinMessage(null);

        player.teleport(BukkitPositionAdapter.toLocation(LobbyService.getInstance().getSpawnLocations().getPosition("Spawn")).getFirst());

        LobbyService.getInstance().setupPlayer(player);

        CloudServer.getInstance().updateNameTags(player);

        LobbyService.getInstance().getExecutorService().execute(() -> {
            for(Player players : Bukkit.getOnlinePlayers()) {
                players.hidePlayer(player);
                players.showPlayer(player);
                LabyModService.setSubtitle(player.getUniqueId(), "§7Coins §8» §c" + RedAPI.getInstance().getTextService().formatInteger(RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(players.getUniqueId()).getCoins()) + " §8┃ §7Cookies §8» §c" + RedAPI.getInstance().getTextService().formatInteger(LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(players.getUniqueId()).getCookies()) + " §8┃ §7RedPass §8» §c✖");
            }
        });

        LobbyService.getInstance().getNpcManager().init(player);

        if(LobbyService.getInstance().getClanDocuments().containsKey(player.getUniqueId())) {
            final PermissionGroup permissionGroup = LobbyService.getInstance().getPlayerGroup(player.getUniqueId());
            final Document document = LobbyService.getInstance().getClanDocuments().get(player.getUniqueId());

            player.setPlayerListName(permissionGroup.getPrefix().replace("&", "§") + player.getName() + " §8[§e" + document.getString("tag") + "§8]");
        }

        LobbyService.getInstance().sendTitle(player, "§8» §4Red§cCW §8«", "§7Lobby", 7, 40, 7);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 1);

        LabyModService.sendCurrentPlayingGamemode(player, CloudAPI.getInstance().getServerId());

    }

}
