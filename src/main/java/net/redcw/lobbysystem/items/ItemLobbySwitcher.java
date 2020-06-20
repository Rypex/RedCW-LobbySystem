package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 11.01.2020 at 08:02
*/

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.api.player.PlayerExecutorBridge;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitServerAddEvent;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitServerInfoUpdateEvent;
import de.dytanic.cloudnet.bridge.event.bukkit.BukkitServerRemoveEvent;
import de.dytanic.cloudnet.bridge.internal.util.ItemStackBuilder;
import de.dytanic.cloudnet.lib.player.PlayerExecutor;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.internal.enums.ItemType;
import org.bukkit.Bukkit;
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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;

public class ItemLobbySwitcher implements Listener {

    @Getter
    private static ItemLobbySwitcher instance;

    private Inventory inventory = Bukkit.createInventory(null, 6*9, "§8» §bLobbySwitcher");

    public ItemLobbySwitcher()
    {
        instance = this;
        reloadInventory();
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event)
    {
        if(event.getItem().equals(LobbyService.getInstance().getGermanItems().get(ItemType.LOBBYSWITCHER))
                || event.getItem().equals(LobbyService.getInstance().getEnglishItems().get(ItemType.LOBBYSWITCHER)))
        {
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))
                event.getPlayer().openInventory(inventory);
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event)
    {
        if(event.getInventory().equals(inventory))
        {
            final Player player = (Player) event.getWhoClicked();

            event.setCancelled(true);

            if(event.getCurrentItem().getType().equals(Material.STORAGE_MINECART))
            {
                final String newServer = event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» §b", "");

                if(newServer == null) return;

                if(newServer.equals(player.getServer().getName()))
                {
                    player.sendMessage(LobbyService.PREFIX + "§cDu bist bereits auf diesem Server!");
                    player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 3, 3);
                    return;
                }

                sendPlayer(player, newServer);
            }
        }
    }

    @EventHandler
    public void onServerAdd(final BukkitServerAddEvent event)
    {
        if(event.getServerInfo().getServiceId().getGroup() == null) return;
        if(event.getServerInfo().getServiceId().getGroup().equals("Lobby"))
        {
            reloadInventory();
        }
    }

    @EventHandler
    public void onServerRemove(final BukkitServerRemoveEvent event)
    {
        if(event.getServerInfo().getServiceId().getGroup() == null) return;
        if(event.getServerInfo().getServiceId().getGroup().equals("Lobby"))
        {
            reloadInventory();
        }
    }

    @EventHandler
    public void onServerInfoUpdate(final BukkitServerInfoUpdateEvent event)
    {
        if(event.getServerInfo().getServiceId().getGroup() == null) return;
        if(event.getServerInfo().getServiceId().getGroup().equals("Lobby"))
        {
            reloadInventory();
        }
    }

    private void reloadInventory()
    {
        inventory.clear();

        final ItemStack GRAY_GLASS = ItemStackBuilder.builder(Material.STAINED_GLASS_PANE, 1, 7).displayName(" ").build();
        final ItemStack BLACK_GLASS = ItemStackBuilder.builder(Material.STAINED_GLASS_PANE, 1, 15).displayName(" ").build();

        final ItemStack TITLE_ITEM = ItemStackBuilder.builder(Material.BEACON).displayName("§8» §bLobbySwitcher").build();
        final ItemStack SILENT_LOBBY = ItemStackBuilder.builder(Material.TNT).displayName("§8» §4Silent-Lobby").build();
        final ItemStack RANDOM_LOBBY = ItemStackBuilder.builder(Material.COMPASS).displayName("§8» §eRandom-Lobby").build();

        inventory.setItem(0, GRAY_GLASS);
        inventory.setItem(1, GRAY_GLASS);
        inventory.setItem(2, BLACK_GLASS);
        inventory.setItem(3, BLACK_GLASS);
        inventory.setItem(4, TITLE_ITEM);
        inventory.setItem(5, BLACK_GLASS);
        inventory.setItem(6, BLACK_GLASS);
        inventory.setItem(7, GRAY_GLASS);
        inventory.setItem(8, GRAY_GLASS);

        inventory.setItem(9, GRAY_GLASS);
        inventory.setItem(10, BLACK_GLASS);
        inventory.setItem(16, BLACK_GLASS);
        inventory.setItem(17, GRAY_GLASS);

        inventory.setItem(18, GRAY_GLASS);
        inventory.setItem(19, BLACK_GLASS);
        inventory.setItem(25, BLACK_GLASS);
        inventory.setItem(26, GRAY_GLASS);

        inventory.setItem(27, GRAY_GLASS);
        inventory.setItem(28, BLACK_GLASS);
        inventory.setItem(34, BLACK_GLASS);
        inventory.setItem(35, GRAY_GLASS);

        for(int i = 36; i < (6*9) - 9; i++)
            inventory.setItem(i, GRAY_GLASS);

        inventory.setItem(45, GRAY_GLASS);
        inventory.setItem(46, BLACK_GLASS);
        inventory.setItem(47, BLACK_GLASS);
        inventory.setItem(48, SILENT_LOBBY);
        inventory.setItem(49, BLACK_GLASS);
        inventory.setItem(50, RANDOM_LOBBY);
        inventory.setItem(51, BLACK_GLASS);
        inventory.setItem(52, BLACK_GLASS);
        inventory.setItem(53, GRAY_GLASS);

        final Collection<ServerInfo> lobbyServer = CloudAPI.getInstance().getServers("Lobby");
        for(ServerInfo server : lobbyServer)
            inventory.addItem(ItemStackBuilder.builder(Material.STORAGE_MINECART)
                    .displayName("§8» §b" + server.getServiceId().getServerId())
                    .lore(
                            "§8§m------------------------",
                            " ",
                            "§8» §7Spieler §8➜ §b" + server.getOnlineCount(),
                            " ",
                            "§8§m------------------------"
                    ).build());
    }

    private void sendPlayer(Player player, String Server) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(Server);
        } catch (IOException e) {
            player.sendMessage(LobbyService.PREFIX + "§cDieser Server ist derzeit nicht erreichbar!");
        }
        player.sendPluginMessage(LobbyService.getInstance().getPlugin(), "BungeeCord", byteArrayOutputStream.toByteArray());


    }

}
