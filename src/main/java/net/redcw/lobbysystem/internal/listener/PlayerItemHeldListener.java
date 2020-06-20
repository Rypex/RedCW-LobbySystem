package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 31.05.2020 at 17:10
*/

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class PlayerItemHeldListener implements Listener {

    @EventHandler
    public void onItemHeld(final PlayerItemHeldEvent event) {
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.CLICK, 3, 1);
    }

}
