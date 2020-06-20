package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 05.01.2020 at 00:51
*/

import de.dytanic.cloudnet.bridge.internal.util.ItemStackBuilder;
import de.dytanic.cloudnet.lib.NetworkUtils;
import de.dytanic.cloudnet.lib.utility.CollectionWrapper;
import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.internal.enums.ItemType;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.BukkitPositionAdapter;
import net.redcw.redapi.positions.Position;
import net.redcw.redapi.positions.PositionMeta;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class ItemNavigator implements Listener {

    @Getter
    private static ItemNavigator instance;

    private Inventory inventory = Bukkit.createInventory(null, 54, "§8» §4Navigator");

    public ItemNavigator()
    {
        instance = this;
        reloadInventory();
    }

    public void reloadInventory() {

        final ItemStack GLASS_GRAY = ItemStackBuilder.builder(Material.STAINED_GLASS_PANE, 1, (short) 8).displayName(" ").build();
        final ItemStack GLASS_BLACK = ItemStackBuilder.builder(Material.STAINED_GLASS_PANE, 1, (short) 15).displayName(" ").build();
        final ItemStack GLASS_RED = ItemStackBuilder.builder(Material.STAINED_GLASS_PANE, 1, (short) 14).displayName(" ").build();
        final ItemStack BARRIER = ItemStackBuilder.builder(Material.BARRIER).displayName(" ").build();

        for(int i = 0; i < 6*9; i++)
        {
            switch (i)
            {
                case 2: case 3: case 4: case 5: case 6: case 18: case 26: case 27: case 35: case 47: case 48: case 50: case 51:
                    inventory.setItem(i, GLASS_BLACK);
                    break;
                case 1: case 7: case 9: case 10: case 12: case 14: case 16: case 17: case 28: case 31: case 34: case 36: case 37: case 38: case 42: case 43: case 44: case 46: case 52:
                    inventory.setItem(i, GLASS_RED);
                    break;
                case 11: case 15: case 22: case 29: case 33: case 40:
                    inventory.setItem(i, BARRIER);
                    break;
                default:
                    inventory.setItem(i, GLASS_GRAY);
                    break;
            }
        }

        for(Map.Entry<String, Position> positions : LobbyService.getInstance().getSpawnLocations().getPositions().entrySet())
        {
            Position position = positions.getValue();
            PositionMeta positionMeta = CollectionWrapper.filter(position.getPositionMetas(), positionMeta1 -> positionMeta1.getKey().equalsIgnoreCase("itemId"));

            ItemStack itemStack = ItemStackBuilder
                    .builder(Material.getMaterial(Integer.parseInt(positionMeta.getValue()))).displayName("§8» §c" + positions.getKey() + " §8▰§7▰ §7Linksklick").build();

            PositionMeta _positionMeta = CollectionWrapper.filter(position.getPositionMetas(), positionMeta12 -> positionMeta12.getKey().equalsIgnoreCase("slot"));
            inventory.setItem(Integer.parseInt(_positionMeta.getValue()), itemStack);
        }

    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event)
    {
        if(event.getWhoClicked() instanceof Player
                &&
                (event.getInventory().equals(inventory) || event.getInventory().getName().equalsIgnoreCase(inventory.getName())) &&
                event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null)
        {
            event.setCancelled(true);

            final String spawn = event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» §c", "").replace(" §8▰§7▰ §7Linksklick", "");
            if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§8» §c"))
            {
                final Player player = (Player) event.getWhoClicked();
                player.closeInventory();

                player.playSound(event.getWhoClicked().getLocation(), Sound.LEVEL_UP, 16, 16);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        RedAPI.getInstance().getProcessQueue().runTask(() -> {
                            Position position = LobbyService.getInstance().getSpawnLocations().getPosition(spawn);

                            if (position != null)
                                event.getWhoClicked().teleport(BukkitPositionAdapter.toLocation(position).getFirst().add(0, 1.0, 0));

                            if(spawn.equals("Fast-Join")) {
                                LobbyService.getInstance().getNpcMap().values().forEach(npc -> npc.forceEmote(player, 4));
                            }
                        });
                        cancel();
                    }
                }.runTaskTimer(LobbyService.getInstance().getPlugin(), 0, 10L);
            }
        }
    }

    @EventHandler
    public void handleNavigation(final PlayerInteractEvent event)
    {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            if(event.getPlayer().getGameMode().equals(GameMode.ADVENTURE))
                if(event.getItem() != null)
                    if(event.getItem().getItemMeta() != null)
                        if(event.getItem().getItemMeta().getDisplayName() != null)
                            if (event.getItem() != null)
                            {
                                if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(LobbyService
                                        .getInstance().getGermanItems().get(ItemType.COMPASS).getItemMeta().getDisplayName()) || event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(LobbyService
                                            .getInstance().getEnglishItems().get(ItemType.COMPASS).getItemMeta().getDisplayName()))
                                {
                                    event.setCancelled(true);
                                    event.getPlayer().openInventory(inventory);
                                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 3, 2);
                                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 3, 2);
                                }
                            }
        }

    }

}