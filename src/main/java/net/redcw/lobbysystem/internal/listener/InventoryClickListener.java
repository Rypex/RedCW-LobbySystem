package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 17.06.2020 at 20:39
*/

import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.player.LobbyPlayer;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.player.RedPlayer;
import net.redcw.redapi.player.RedPlayerProperty;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);

        switch (event.getInventory().getTitle()) {
            case "§8▰§7▱ §6Daily§eReward":
                final RedPlayer redPlayer = RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId());
                switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                    case "§8» §eNormal §8▰§7▱ Belohnung§1":
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 2);

                        redPlayer.updatePropertyAsync(RedPlayerProperty.COINS, (redPlayer.$coins() + 500));

                        LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId()).setDailyRewardNormal(new Timestamp((System.currentTimeMillis() + TimeUnit.HOURS.toMillis(6)) + TimeUnit.DAYS.toMillis(1)));

                        LobbyService.getInstance().getPlayerCookies().replace(player.getUniqueId(), (LobbyService.getInstance().getPlayerCookies().get(player.getUniqueId()) + 1000));

                        LobbyService.getInstance().getPlayerScoreboard().get(player.getUniqueId()).updateBoard(player, 8, "    §8➥ §7", "§c" + RedAPI.getInstance().getTextService().formatInteger(RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).$coins()));

                        LobbyService.getInstance().sendTitle(player, "§8» §6Daily§eReward §8«", "§7Normal §8● §aAbgeholt", 7, 40, 7);
                        break;
                    case "§8» §eNormal §8▰§7▱ Belohnung§2":
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 3, 3);
                        player.sendMessage(LobbyService.PREFIX + "§7Du hast dein §6Daily§eReward §7bereits abgeholt§8, §7 du kannst ihn am §c" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId()).getDailyRewardNormal().getTime()) + " §7erneut abholen§8.");
                        break;
                    case "§8» §6Prime §8▰§7▱ Belohnung§1":
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 2);

                        redPlayer.updatePropertyAsync(RedPlayerProperty.COINS, (redPlayer.$coins() + 1000));

                        final LobbyPlayer lobbyPlayer = LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId());
                        lobbyPlayer.setPrivateServerKeys((lobbyPlayer.getPrivateServerKeys() + 1));

                        LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId()).setDailyRewardPrime(new Timestamp((System.currentTimeMillis() + TimeUnit.HOURS.toMillis(6)) + TimeUnit.DAYS.toMillis(1)));

                        LobbyService.getInstance().getPlayerCookies().replace(player.getUniqueId(), (LobbyService.getInstance().getPlayerCookies().get(player.getUniqueId()) + 2000));

                        LobbyService.getInstance().getPlayerScoreboard().get(player.getUniqueId()).updateBoard(player, 8, "    §8➥ §7", "§c" + RedAPI.getInstance().getTextService().formatInteger(RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).$coins()));

                        LobbyService.getInstance().sendTitle(player, "§8» §6Daily§eReward §8«", "§6Prime §8● §aAbgeholt", 7, 40, 7);
                        break;
                    case "§8» §6Prime §8▰§7▱ Belohnung§2":
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 3, 3);
                        player.sendMessage(LobbyService.PREFIX + "§7Du hast dein §6Daily§eReward §7bereits abgeholt§8, §7 du kannst ihn am §c" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId()).getDailyRewardPrime().getTime()) + " §7erneut abholen§8.");
                        break;
                    case "§8» §6Prime §8▰§7▱ Belohnung§3":
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 3, 3);
                        player.sendMessage(LobbyService.PREFIX + "§7Du benötigst §7§nmindestens §7den §6§lPrime §7Rang§8.");
                        break;
                }
                break;
        }

    }

}
