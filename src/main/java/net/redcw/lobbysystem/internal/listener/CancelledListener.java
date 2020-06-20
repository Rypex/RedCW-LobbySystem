package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 05.01.2020 at 01:41
*/

import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.internal.enums.ItemType;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class CancelledListener implements Listener {

    @EventHandler
    public void handle(BlockBreakEvent event)
    {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(InventoryClickEvent event)
    {
        if(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(BlockPlaceEvent event)
    {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(WeatherChangeEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerArmorStandManipulateEvent event)
    {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(EntityDamageEvent event)
    {
        if(event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION))
        {
            ((Player) event.getEntity()).setHealth(((Player) event.getEntity()).getMaxHealth());
        }

        if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handle(EntityDamageByEntityEvent event)
    {
        if(event.getEntity().getType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER))
        {
            final Player player = (Player) event.getEntity();
            final Player damager = (Player) event.getDamager();

            if(player.getItemInHand().equals(LobbyService.getInstance().getGermanItems().get(ItemType.KNOCKBACK_AXE))
                    && damager.getItemInHand().equals(LobbyService.getInstance().getGermanItems().get(ItemType.KNOCKBACK_AXE)))
            {
                event.setCancelled(false);
                event.setDamage(0.0);
            }
            else
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent e)
    {
        /*
        if(e.getNewGameMode().equals(GameMode.CREATIVE) || e.getNewGameMode().equals(GameMode.SPECTATOR))
        {
            if(e.getPlayer().getPassenger() != null)
            e.getPlayer().getPassenger().remove();
        }
        else
        {
            LobbyService.getInstance().armorStand(e.getPlayer());
        }
        */
    }

    @EventHandler
    public void handle(FoodLevelChangeEvent e)
    {
        e.setCancelled(true);
    }

    @EventHandler
    public void handle(PaintingBreakEvent e)
    {
        e.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerDropItemEvent event)
    {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void handle(PlayerPickupItemEvent event)
    {
        event.setCancelled(true);
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event)
    {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            if(event.getItem().getType().equals(Material.ITEM_FRAME))
            {
                event.setCancelled(true);
            }
        }
    }
}
