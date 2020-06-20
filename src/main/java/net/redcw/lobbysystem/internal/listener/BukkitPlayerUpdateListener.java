package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 07.01.2020 at 21:26
*/

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.CloudServer;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitPlayerUpdateEvent;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import net.redcw.lobbysystem.LobbyService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BukkitPlayerUpdateListener implements Listener {

    @EventHandler
    public void onBukkitPlayerUpdate(final BukkitPlayerUpdateEvent event)
    {
        final Player player = Bukkit.getPlayer(event.getCloudPlayer().getUniqueId());

        if(player == null) return;

        final PermissionGroup permissionGroup = event.getCloudPlayer().getPermissionEntity().getHighestPermissionGroup(CloudAPI.getInstance().getPermissionPool());

        LobbyService.getInstance().getPlayerGroups().replace(player.getUniqueId(), permissionGroup);

        CloudServer.getInstance().updateNameTags(player);
        LobbyService.getInstance().getPlayerScoreboard().get(player.getUniqueId()).updateBoard(player, 11, "    §8➥ §7", permissionGroup.getColor().replace("&", "§") + permissionGroup.getName());

    }

}
