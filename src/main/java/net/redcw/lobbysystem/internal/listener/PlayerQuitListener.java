package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 05.01.2020 at 15:52
*/

import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.player.LobbyPlayer;
import net.redcw.redapi.bukkit.BukkitPositionAdapter;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(final PlayerQuitEvent event)
    {
        event.setQuitMessage(null);

        LobbyPlayer lobbyPlayer = LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(event.getPlayer().getUniqueId());
        lobbyPlayer.setPosition(BukkitPositionAdapter.toPosition(event.getPlayer().getLocation()));
        lobbyPlayer.setCookies(LobbyService.getInstance().getPlayerCookies().get(event.getPlayer().getUniqueId()));
        LobbyService.getInstance().getLobbyPlayerProvider().getPlayers().remove(event.getPlayer().getUniqueId());

        LobbyService.getInstance().getNpcManager().onPlayerQuit(event);

        if (event.getPlayer().getPassenger() != null && event.getPlayer().getPassenger() instanceof ArmorStand)
            event.getPlayer().getPassenger().remove();

    }

}
