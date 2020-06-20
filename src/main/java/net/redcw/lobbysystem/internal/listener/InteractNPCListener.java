package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 07.06.2020 at 00:43
*/

import com.google.common.collect.Lists;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.api.player.PlayerExecutorBridge;
import de.dytanic.cloudnet.lib.CloudNetwork;
import de.dytanic.cloudnet.lib.server.ServerState;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.manager.ItemManager;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.NPCManager;
import net.redcw.redapi.bukkit.RedNPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InteractNPCListener implements Listener {

    @EventHandler
    public void onInteract(final NPCManager.PlayerInteractNPCEvent event) {
        final Player player = event.getPlayer();
        final RedNPC npc = event.getRedNPC();

        final String npcName = ChatColor.stripColor(npc.getEntityPlayer().getName());
        switch (npcName) {
            case "KnockPvP": case "MLGRush":
                interactGameNpc(player, npcName, event.isLeftClick());
                break;
        }

    }

    private void interactGameNpc(Player player, String npcName, boolean leftClick) {
        final List<ServerInfo> serverInfoList = new CopyOnWriteArrayList<>(CloudAPI.getInstance().getServers(npcName));

        if(leftClick) {
            if(CloudAPI.getInstance().getServerGroupData(npcName).isMaintenance()) {
                LobbyService.getInstance().sendTitle(player, "§8× §4§lWARTUNG §8×", "§8» §7Dieser Modus ist derzeit in Wartung §8«", 7, 20, 7);
                return;
            }

            if(serverInfoList.size() == 0) {
                LobbyService.getInstance().sendTitle(player, "§8× §4§lFehler §8×", "§8» §7Es wurde kein Server gefunden §8«", 7, 20, 7);
                return;
            }

            sendServerRound(player, npcName);
            return;
        }

        RedAPI.getInstance().getProcessQueue().runTask(() -> player.openInventory(createNpcInventory(npcName)));
    }

    private void sendServerRound(final Player player, String group)
    {
        final Collection<ServerInfo> serverCollection = CloudAPI.getInstance().getServers(group);

        final List<ServerInfo> serverInfoList = Lists.newCopyOnWriteArrayList();
        for(ServerInfo serverInfo : serverCollection) {
            if (serverInfo.getServerState().equals(ServerState.LOBBY) && !serverInfo.getServiceId().getServerId().equalsIgnoreCase(player.getServer().getName())) {
                serverInfoList.add(serverInfo);
            }
        }

        ServerInfo next = null;
        for (ServerInfo snapshot : serverInfoList) {
            if (next == null) {
                next = snapshot;
                continue;
            }

            int online = next.getOnlineCount();
            if (online > next.getOnlineCount()) {
                next = snapshot;
            }
        }

        PlayerExecutorBridge.INSTANCE.sendPlayer(CloudAPI.getInstance().getOnlinePlayer(player.getUniqueId()), next.getServiceId().getServerId());
    }

    private Inventory createNpcInventory(final String group) {
        final Inventory inventory = Bukkit.createInventory(null, 5*9, "§8» §4§5§e" + group);

        final List<ServerInfo> serverInfoList = new CopyOnWriteArrayList<>(CloudAPI.getInstance().getServers(group));

        LobbyService.getInstance().getItemHistory().fillAround(inventory, LobbyService.getInstance().getItemHistory().getGLASS_GRAY());

        inventory.setItem(4, new ItemManager(Material.STORAGE_MINECART).setDisplayName("§8» §e" + group).build());

        serverInfoList.forEach(server -> {
            inventory.addItem(new ItemManager(Material.STORAGE_MINECART).setDisplayName("§8» §e" + server.getServiceId().getServerId())
                    .addLoreLine("§8§m---------------------------")
                    .addLoreLine(" ")
                    .addLoreLine(" §8» §7Spieler §8➜ §e" + server.getOnlineCount() + "§8/§e" + server.getMaxPlayers())
                    .addLoreLine(" §8» §7Karte §8➜ §e" + server.getMotd())
                    .addLoreLine(" ")
                    .addLoreLine("§8§m---------------------------")
                    .build());
        });

        return inventory;
    }

}
