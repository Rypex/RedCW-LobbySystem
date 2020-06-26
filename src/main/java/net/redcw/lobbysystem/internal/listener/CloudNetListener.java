package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 26.06.2020 at 19:42
*/

import de.dytanic.cloudnet.bridge.event.bukkit.BukkitSubChannelMessageEvent;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import net.redcw.lobbysystem.LobbyService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class CloudNetListener implements Listener {

    @EventHandler
    public void onBukkitSubServerMessage(final BukkitSubChannelMessageEvent event) {
        if(event.getChannel() == null) return;

        if(event.getChannel().equals("bungeesystem")) {
            switch (event.getMessage()) {
                case "player_clan_create":
                    final Player player = Bukkit.getPlayer(UUID.fromString(event.getDocument().getString("uniqueId")));

                    if(player != null) {
                        final PermissionGroup permissionGroup = LobbyService.getInstance().getPlayerGroups().get(player.getUniqueId());

                        player.setPlayerListName(permissionGroup.getPrefix().replace("&", "§") + player.getName() + " §8[§e" + event.getDocument().getString("tag") + "§8]");
                    }
                    break;
            }
        }

        //CloudAPI.getInstance().sendCustomSubServerMessage("bungeesystem", "player_clan_create", new Document("name", args[1]).append("tag", args[2]));
    }

}
