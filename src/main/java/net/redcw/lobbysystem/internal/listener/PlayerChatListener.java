package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 14.01.2020 at 19:53
*/

import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import net.redcw.lobbysystem.LobbyService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event)
    {
        final Player player = event.getPlayer();
        String message = event.getMessage();

        message = message.replaceAll("%", "%%");

        final PermissionGroup permissionGroup = LobbyService.getInstance().getPlayerGroups().get(player.getUniqueId());

        event.setFormat(permissionGroup.getColor().replace("&", "§") + permissionGroup.getName() + " §8▰§7▰ " + permissionGroup.getColor().replace("&", "§") + player.getName() + " §8➜ §7" + message);
    }

}
