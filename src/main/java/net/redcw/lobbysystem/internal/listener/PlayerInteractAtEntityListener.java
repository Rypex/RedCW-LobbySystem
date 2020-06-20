package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 12.01.2020 at 17:35
*/

import de.dytanic.cloudnet.api.CloudAPI;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.player.LobbyPlayer;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.RedAPIBukkit;
import net.redcw.redapi.bukkit.game.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;

import java.text.SimpleDateFormat;

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent event) {
        if(event.getRightClicked() == null) return;

        final Player player = event.getPlayer();

        if(event.getRightClicked().equals(LobbyService.getInstance().getDailyRewardEntity())) {
            RedAPI.getInstance().getProcessQueue().runTask(() -> {
                final Inventory inventory = Bukkit.createInventory(null, 5*9, "§8▰§7▱ §6Daily§eReward");

                final LobbyPlayer lobbyPlayer = LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId());

                LobbyService.getInstance().getItemHistory().fillAround(inventory, LobbyService.getInstance().getItemHistory().getGLASS_GRAY());

                inventory.setItem(4, new ItemManager(Material.GOLD_INGOT).setDisplayName("§8» §6Daily§eReward").build());

                if(LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId()).getDailyRewardNormal().getTime() < System.currentTimeMillis()) {
                    inventory.setItem(21, new ItemManager(Material.GLOWSTONE_DUST).setDisplayName("§8» §eNormal §8▰§7▱ Belohnung§1")
                            .addLoreLine("§8§m----------------------------")
                            .addLoreLine(" ")
                            .addLoreLine(" §8» §7Inhalt")
                            .addLoreLine("    §8➥ §a500 Coins")
                            .addLoreLine("    §8➥ §a1000 Cookies")
                            .addLoreLine(" ")
                            .addLoreLine(" §8» §7Verfügbar §8» §a§oJetzt")
                            .addLoreLine(" ")
                            .addLoreLine("§8§m----------------------------")
                            .build());
                } else {
                    inventory.setItem(21, new ItemManager(Material.getMaterial(289)).setDisplayName("§8» §eNormal §8▰§7▱ Belohnung§2")
                            .addLoreLine("§8§m----------------------------")
                            .addLoreLine(" ")
                            .addLoreLine(" §8» §7Inhalt")
                            .addLoreLine("    §8➥ §a500 Coins")
                            .addLoreLine("    §8➥ §a1000 Cookies")
                            .addLoreLine(" ")
                            .addLoreLine(" §8» §7Verfügbar §8» §c" + new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss").format(lobbyPlayer.getDailyRewardNormal().getTime()))
                            .addLoreLine(" ")
                            .addLoreLine("§8§m----------------------------")
                            .build());
                }

                if(player.hasPermission("net.redcw.feature.dailyreward.prime")) {
                    if (LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId()).getDailyRewardPrime().getTime() < System.currentTimeMillis()) {
                        inventory.setItem(23, new ItemManager(Material.STORAGE_MINECART).setDisplayName("§8» §6Prime §8▰§7▱ Belohnung§1")
                                .addLoreLine("§8§m----------------------------")
                                .addLoreLine(" ")
                                .addLoreLine(" §8» §7Inhalt")
                                .addLoreLine("    §8➥ §a1000 Coins")
                                .addLoreLine("    §8➥ §a2000 Cookies")
                                .addLoreLine("    §8➥ §a2 Coin-Bomben")
                                .addLoreLine("    §8➥ §a1 Private-Server Key")
                                .addLoreLine(" ")
                                .addLoreLine(" §8» §7Verfügbar §8» §a§oJetzt")
                                .addLoreLine(" ")
                                .addLoreLine("§8§m----------------------------")
                                .build());
                    } else {
                        inventory.setItem(23, new ItemManager(Material.getMaterial(289)).setDisplayName("§8» §6Prime §8▰§7▱ Belohnung§2")
                                .addLoreLine("§8§m----------------------------")
                                .addLoreLine(" ")
                                .addLoreLine(" §8» §7Inhalt")
                                .addLoreLine("    §8➥ §a1000 Coins")
                                .addLoreLine("    §8➥ §a2000 Cookies")
                                .addLoreLine("    §8➥ §a2 Coin-Bomben")
                                .addLoreLine("    §8➥ §a1 Private-Server Key")
                                .addLoreLine(" ")
                                .addLoreLine(" §8» §7Verfügbar §8» §c" + new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss").format(lobbyPlayer.getDailyRewardPrime().getTime()))
                                .addLoreLine(" ")
                                .addLoreLine("§8§m----------------------------")
                                .build());
                    }
                } else {
                    inventory.setItem(23, new ItemManager(Material.getMaterial(289)).setDisplayName("§8» §6Prime §8▰§7▱ Belohnung§3")
                            .addLoreLine("§8§m----------------------------")
                            .addLoreLine(" ")
                            .addLoreLine(" §8» §7Inhalt")
                            .addLoreLine("    §8➥ §a1000 Coins")
                            .addLoreLine("    §8➥ §a2000 Cookies")
                            .addLoreLine("    §8➥ §a2 Coin-Bomben")
                            .addLoreLine("    §8➥ §a1 Private-Server Key")
                            .addLoreLine(" ")
                            .addLoreLine(" §8» §7Verfügbar §8» §c§oDu benötigst mindestens den Prime-Rang")
                            .addLoreLine(" ")
                            .addLoreLine("§8§m----------------------------")
                            .build());
                }

                player.openInventory(inventory);
            });
            return;
        }

        if(!event.getRightClicked().getType().equals(EntityType.PLAYER)) return;

        openPlayerInventory(player, (Player) event.getRightClicked());
    }

    private void openPlayerInventory(final Player player, Player target) {
        final Inventory inventory = Bukkit.createInventory(null, 9*3, "§8▰§7▱ " + LobbyService.getInstance().getPlayerGroup(target.getUniqueId()).getPrefix().replace("&", "§") + target.getName());

        for (int i = 0; i < 9*3; i++)
            inventory.setItem(i, LobbyService.getInstance().getItemHistory().getGLASS_GRAY());

        inventory.setItem(3, new ItemManager(Material.COOKIE).setDisplayName("§8» §6Cookies §8▰§7▱ §6" + LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(target.getUniqueId()).getCookies()).build());
        inventory.setItem(5, new ItemManager(Material.BEACON).setDisplayName("§8» §aStatus §8▰§7▱ §aRedCW <3").build());
        inventory.setItem(11, new ItemManager(Material.TORCH).setDisplayName("§8» §2Onlinezeit §8▰§7▱ §2" + new SimpleDateFormat("HH:mm:ss").format(RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(target.getUniqueId()).getOnlineTime())).build());
        inventory.setItem(13, new ItemManager(RedAPIBukkit.getPlugin().getReflectionSkull(target.getUniqueId())).setDisplayName("§8» " + LobbyService.getInstance().getColor(target.getUniqueId()) + target.getName()).build());
        inventory.setItem(15, new ItemManager(Material.GOLD_INGOT).setDisplayName("§8» §6Coins §8▰§7▱ §6" + RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(target.getUniqueId()).$coins()).build());
        inventory.setItem(21, new ItemManager(Material.DIAMOND_CHESTPLATE).setDisplayName("§8» " + LobbyService.getInstance().getColor(target.getUniqueId()) + "Rang §8▰§7▱ " + LobbyService.getInstance().getColor(target.getUniqueId()) + LobbyService.getInstance().getPlayerGroup(target.getUniqueId()).getName()).build());
        inventory.setItem(23, new ItemManager(Material.PAPER).setDisplayName("§8» §eErster-Login §8▰§7▱ §e" + new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss").format(CloudAPI.getInstance().getOnlinePlayer(target.getUniqueId()).getFirstLogin())).build());

        player.openInventory(inventory);
    }

}
