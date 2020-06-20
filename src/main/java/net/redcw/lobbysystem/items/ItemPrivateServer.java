package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 02.02.2020 at 16:06
*/

import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.internal.enums.ItemType;
import net.redcw.lobbysystem.manager.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ItemPrivateServer implements Listener {

    @Getter
    private ItemPrivateServer instance;

    private Map<UUID, Inventory> inventorys = new ConcurrentHashMap<>();

    public ItemPrivateServer()
    {
        instance = this;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event)
    {
        final Inventory inventory = Bukkit.createInventory(null, 6*9, "§8▰§7▰ §6Private-Servrer");

        for(int i = 0; i < 6*9; i++)
        {
            switch (i)
            {
                case 2: case 3: case 4: case 5: case 6: case 10: case 11: case 15: case 16: case 18: case 26: case 27: case 28: case 34: case 35: case 37: case 38: case 42: case 43: case 47: case 48: case 49: case 50: case 51:
                    inventory.setItem(i, LobbyService.getInstance().getItemHistory().getGLASS_BLACK());
                    break;
                case 0: case 1: case 7: case 8: case 9: case 17: case 36: case 44: case 45: case 46: case 52: case 53:
                    inventory.setItem(i, LobbyService.getInstance().getItemHistory().getGLASS_RED());
                    break;
                case 31:
                    inventory.setItem(i, new ItemManager(Material.INK_SACK).setData((short) 10).setDisplayName("§8» §aServer-Starten §8▰§7▰ Linksklick")
                            .addLoreLine("§8§m----------------------------")
                            .addLoreLine(" ")
                            .addLoreLine(" §8» §7Du hast §6" + LobbyService.getInstance().getLobbyPlayerProvider().getPlayers().get(event.getPlayer().getUniqueId()).getPrivateServerKeys() + " §7Server-Keys§8.")
                            .addLoreLine(" ")
                            .addLoreLine("§8§m----------------------------")
                            .build());
                    break;
                case 13:
                    inventory.setItem(i, new ItemManager(Material.ENDER_PORTAL_FRAME).setDisplayName("§8» §6Private-Server").build());
                    break;
            }
        }

        inventorys.put(event.getPlayer().getUniqueId(), inventory);
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event)
    {
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))
            if(event.getItem().equals(LobbyService.getInstance().getGermanItems().get(ItemType.PRIVATE_SERVER)))
            {
                final Player player = event.getPlayer();

                player.openInventory(inventorys.get(player.getUniqueId()));
            }
    }

}
