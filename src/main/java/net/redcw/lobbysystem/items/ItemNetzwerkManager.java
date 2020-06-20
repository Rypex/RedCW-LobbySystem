package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 01.06.2020 at 22:02
*/

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.internal.util.ItemStackBuilder;
import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@Getter
public class ItemNetzwerkManager implements Listener {

    private ItemNetzwerkManager instance;

    public ItemNetzwerkManager() {
        this.instance = this;
    }

    @EventHandler
    public void InventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §4Netzwerk-Manager")) {
            final Inventory inventory = Bukkit.createInventory(null, 9*3, "§cNetzwerk-Manager");

            LobbyService.getInstance().getItemHistory().fillAround(inventory, LobbyService.getInstance().getItemHistory().getGLASS_GRAY());
            inventory.setItem(4, ItemStackBuilder.builder(Material.COMMAND).displayName("§8» §cNetzwerk-Manager").build());

            inventory.setItem(10, ItemStackBuilder.builder(Material.BOOK).displayName("§8» §2Cloud-Gruppen").build());
            inventory.setItem(10, ItemStackBuilder.builder(Material.PAPER).displayName("§8» §bOnline-Server").build());
            inventory.setItem(10, ItemStackBuilder.builder(Material.BARRIER).displayName("§8» §c§l§kkkkk§4§lNOT-AUS§c§l§kkkkk §8").build());

            player.openInventory(inventory);
        }

        if(event.getInventory().getTitle().equals("§cNetzwerk-Manager")) {
            if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§8» §2Cloud-Gruppen")) {
                final Inventory inventory = Bukkit.createInventory(null, 9*4, "§2Cloud-Gruppen");

                LobbyService.getInstance().getItemHistory().fillAround(inventory, LobbyService.getInstance().getItemHistory().getGLASS_GRAY());
                inventory.setItem(4, ItemStackBuilder.builder(Material.BOOK).displayName("§8» §cCloud-Gruppen").build());

                CloudAPI.getInstance().getCloudNetwork().getServerGroups().values().forEach(groups -> {
                    inventory.addItem(ItemStackBuilder.builder(Material.BOOK).displayName("§8» §2" + groups.getName()).build());
                });

                player.openInventory(inventory);
            }
        }
    }

}
