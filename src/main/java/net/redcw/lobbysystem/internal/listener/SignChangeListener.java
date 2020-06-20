package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 25.05.2020 at 18:32
*/

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void handle(SignChangeEvent event)
    {
        if (event.getPlayer().hasPermission("net.redcw.lobbysystem.secrets"))
        {
            if (event.getLine(0).equalsIgnoreCase("[secret]"))
            {
                event.setLine(0, "§4•§c● Secret §c●§4•");
                event.setLine(1, ChatColor.translateAlternateColorCodes('&', event.getLine(1)));
                event.setLine(2, ChatColor.translateAlternateColorCodes('&', event.getLine(2)));
                event.setLine(3, ChatColor.translateAlternateColorCodes('&', event.getLine(3)));
            }
        }
    }
}
