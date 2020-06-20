package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 17.06.2020 at 19:49
*/

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if(event.getEntity().getType().equals(EntityType.PLAYER)) return;

        event.setCancelled(true);
    }

}
