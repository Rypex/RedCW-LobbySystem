package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 11.01.2020 at 09:13
*/

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.internal.util.ItemStackBuilder;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import de.dytanic.cloudnet.lib.player.permission.PermissionGroup;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.ItemBanner;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.internal.enums.ItemType;
import net.redcw.lobbysystem.manager.ItemManager;
import net.redcw.lobbysystem.player.LobbyPlayerProvider;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.util.Trio;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ItemProfile implements Listener {

    @Getter
    private static ItemProfile instance;

    @Getter
    private Map<UUID, Trio<Inventory, Inventory, Inventory>> inventorys = new ConcurrentHashMap<>();

    public ItemProfile()
    {
        instance = this;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event)
    {
        inventorys.put(event.getPlayer().getUniqueId(), new Trio<>(Bukkit.createInventory(null, 6*9, "§8▰§7▰ §dFreunde"), Bukkit.createInventory(null, 6*9, "§8▰§7▰ §3Stats"), Bukkit.createInventory(null, 6*9, "§8▰§7▰ §eSecrets")));

        LobbyService.getInstance().getItemHistory().fillAround(inventorys.get(event.getPlayer().getUniqueId()).getThird(), LobbyService.getInstance().getItemHistory().getGLASS_GRAY());
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event)
    {
        if(event.getItem().equals(LobbyService.getInstance().getEnglishItems().get(ItemType.PROFILE))
                || event.getItem().equals(LobbyService.getInstance().getGermanItems().get(ItemType.PROFILE)))
        {
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))
                openInventory(event.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event)
    {
        if(event.getInventory().getTitle().equals("§8» §dProfil"))
        {
            final Player player = (Player) event.getWhoClicked();
            if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§7» §dFreunde"))
            {
                final Inventory inventory = inventorys.get(player.getUniqueId()).getFirst();

                inventory.clear();

                RedAPI.getInstance().getProcessQueue().runTask(() -> {
                    final ItemStack GRAY_GLASS = ItemStackBuilder.builder(Material.STAINED_GLASS_PANE, 1, 7).displayName(" ").build();
                    final ItemStack REQUESTS_ITEM = ItemStackBuilder.builder(Material.PAPER).displayName("§8» §eAnfragen").build();
                    final ItemStack CLEAR_ITEM = ItemStackBuilder.builder(Material.BARRIER).displayName("§8» §cAlle Freunde entfernen").build();

                    for (int i = 36; i < (36 + 9); i++)
                        inventory.setItem(i, GRAY_GLASS);

                    inventory.setItem(50, REQUESTS_ITEM);
                    inventory.setItem(52, CLEAR_ITEM);

                    final Document document = LobbyService.getInstance().getMongoManager().getFriendSystem().find(new Document("uuid", player.getUniqueId().toString())).first();

                    final String[] friends = document.getString("friendlist").split(";");

                    for(int i = 0; i < friends.length; i++)
                    {
                        final PermissionGroup permissionGroup = LobbyService.getInstance().getPlayerGroup(UUID.fromString(friends[i]));
                        final String playerName = CloudAPI.getInstance().getPlayerName(UUID.fromString(friends[i]));
                        final Document friend = LobbyService.getInstance().getMongoManager().getFriendSystem().find(new Document("uuid", friends[i])).first();
                        final CloudPlayer cloudPlayer = CloudAPI.getInstance().getOnlinePlayer(UUID.fromString(friends[i]));

                        inventory.setItem(i, new ItemManager(Material.SKULL_ITEM).setData((short) 3)
                                .setSkullOwner(playerName)
                                .setDisplayName(permissionGroup.getPrefix().replace("&", "§") + playerName)
                                .addLoreLine("§8§m--------------------------")
                                .addLoreLine(" ")
                                .addLoreLine((cloudPlayer == null ? "  §8» §7Zuletzt Online vor §8▰§7▰ §e" + LobbyService.getInstance().getFriendManager().getLastTimeOnline(friend.getLong("lastconnect")) : "  §8» §7Online §8▰§7▰ §e" + cloudPlayer.getServer()))
                                .addLoreLine("  §8» §7Status §8▰§7▰ §e" + friend.getString("status"))
                                .addLoreLine(" ")
                                .addLoreLine("§8§m--------------------------")
                                .build());
                    }
                });

                player.openInventory(inventory);
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§7» §eSecrets")) {
                final Inventory inventory = inventorys.get(player.getUniqueId()).getThird();

                inventory.setItem(4, ItemStackBuilder.builder(Material.PAPER).displayName("§8» §eSecrets").build());

                RedAPI.getInstance().getProcessQueue().runTask(() -> LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId()).getFoundedSecrets().forEach(secrets -> {
                    inventory.addItem(ItemStackBuilder.builder(Material.PAPER).displayName("§8» §e" + secrets).build());
                }));

                player.openInventory(inventory);
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§7» §aStats")) {
                final Inventory inventory = inventorys.get(player.getUniqueId()).getSecond();

                RedAPI.getInstance().getProcessQueue().runTask(() -> LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId()).getFoundedSecrets().forEach(secrets -> {
                    inventory.addItem(ItemStackBuilder.builder(Material.PAPER).displayName("§8» §e" + secrets).build());
                }));

                player.openInventory(inventory);
            }

            event.setCancelled(true);
        }
    }

    public void openInventory(Player player)
    {
        final Inventory inventory = Bukkit.createInventory(null, InventoryType.BREWING, "§8» §dProfil");

        inventory.setItem(0, new ItemManager(Material.ENCHANTED_BOOK).setDisplayName("§7» §dFreunde").build());
        inventory.setItem(1, new ItemManager(Material.PAPER).setDisplayName("§7» §3Stats").build());
        inventory.setItem(2, new ItemManager(Material.GOLD_NUGGET).setDisplayName("§7» §eSecrets").build());
        inventory.setItem(3, new ItemManager(LobbyService.getInstance().getRawSkull(player.getUniqueId())).setDisplayName("§8» " + LobbyService.getInstance().getPlayerGroups().get(player.getUniqueId()).getSuffix().replace("&", "§") + player.getName()).build());

        player.openInventory(inventory);
    }

}
