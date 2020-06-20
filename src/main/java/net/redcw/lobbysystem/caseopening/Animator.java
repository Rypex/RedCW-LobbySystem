package net.redcw.lobbysystem.caseopening;

/*
Class created by SpigotSource on 14.06.2020 at 02:21
*/

import net.redcw.lobbysystem.LobbyService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class Animator implements Listener{

    private Player player;
    private IAnimator ianimator;
    private Animator animator;

    private Inventory inventory;
    private boolean running;
    private BukkitTask task;

    private int rolls;
    private int timeout;

    public Animator(Player player, IAnimator ianimator) {
        this.player = player;
        this.ianimator = ianimator;
        this.animator = this;
    }

    public void startAnimation(int rolls, List<ItemStack> items, long waitAtEnd) {
        this.inventory = this.ianimator.setupInventory(this.player);
        this.rolls = rolls;
        this.player.openInventory(this.inventory);

        this.running = true;

        Bukkit.getPluginManager().registerEvents(this.animator, LobbyService.getInstance().getPlugin());

        this.task = Bukkit.getScheduler().runTaskTimer(LobbyService.getInstance().getPlugin(), () -> {
            if(Animator.this.timeout != 0) {
                Animator.this.timeout--;
            } else {
                Animator.this.ianimator.onTick(Animator.this.player, Animator.this.inventory, items, false);

                Animator.this.rolls--;
                if(Animator.this.rolls >= 20) {
                    Animator.this.timeout = 0;
                }else if(Animator.this.rolls <= 19 && Animator.this.rolls >= 10) {
                    Animator.this.timeout = 1;
                }else if(Animator.this.rolls <= 9 && Animator.this.rolls >= 5) {
                    Animator.this.timeout = 2;
                }else if(Animator.this.rolls <= 4 && Animator.this.rolls >= 0) {
                    Animator.this.timeout = 3;
                }

                if(Animator.this.rolls == 0) {
                    Animator.this.task.cancel();
                    Bukkit.getScheduler().runTaskLater(LobbyService.getInstance().getPlugin(), () -> {
                        Animator.this.ianimator.onTick(Animator.this.player, Animator.this.inventory, items, true);

                        Bukkit.getScheduler().runTaskLater(LobbyService.getInstance().getPlugin(), () -> {
                            HandlerList.unregisterAll(Animator.this.animator);

                            Animator.this.running = false;
                            Animator.this.player.closeInventory();
                        }, waitAtEnd);
                    }, 20);
                }
            }
        }, 0L, 2L);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event .getInventory().equals(this.inventory) && this.running) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if(event.getInventory().equals(this.inventory) && this.running) {
            Bukkit.getScheduler().runTaskLater(LobbyService.getInstance().getPlugin(), () -> player.openInventory(Animator.this.inventory), 3);
        }
    }
}
