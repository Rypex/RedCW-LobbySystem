package net.redcw.lobbysystem.internal.listener;

/*
Class created by SpigotSource on 25.05.2020 at 18:42
*/

import de.dytanic.cloudnet.lib.NetworkUtils;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.player.LobbyPlayer;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.language.LanguageDefinition;
import net.redcw.redapi.player.RedPlayer;
import net.redcw.redapi.player.RedPlayerProperty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerInteractListener implements Listener {

    private final Map<UUID, Integer> cookieMap;
    private final Location cookieLocation;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public PlayerInteractListener() {
        cookieMap = LobbyService.getInstance().getPlayerCookies();
        cookieLocation = LobbyService.getInstance().getCookieLocation();
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if(player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(false);
            return;
        }

        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.SKULL)) {
            final Location blockLocation = event.getClickedBlock().getLocation();
            if(blockLocation.getBlockX() == cookieLocation.getBlockX() && blockLocation.getBlockZ() == cookieLocation.getBlockZ()) {
                executorService.execute(() -> {
                    cookieMap.replace(player.getUniqueId(), (cookieMap.get(player.getUniqueId()) + 1));
                    LobbyService.getInstance().sendTitle(player, "§8» §6CookieClicker §8«", "§a+1 §8× §6" + RedAPI.getInstance().getTextService().formatInteger(cookieMap.get(player.getUniqueId())) + " Cookies", 0, 40, 9);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 3, 1);
                });
                return;
            }
        }

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.WALL_SIGN))
        {
            Sign sign = (Sign) event.getClickedBlock().getState();
            if (sign.getLine(0).equalsIgnoreCase("§4•§c● Secret §c●§4•"))
            {
                String lobbySecret = sign.getLine(1);
                if (!LobbyService.getInstance().getLobbyPlayerProvider().getPlayers().get(event.getPlayer().getUniqueId()).getFoundedSecrets().contains(lobbySecret))
                {
                    LobbyPlayer lobbyPlayer = LobbyService.getInstance().getLobbyPlayerProvider().getPlayers().get(event.getPlayer().getUniqueId());

                    lobbyPlayer.getFoundedSecrets().add(lobbySecret);
                    lobbyPlayer.updateAsync();

                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 3, 1);

                    RedPlayer redPlayer = RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(event.getPlayer().getUniqueId());

                    if (redPlayer.getLanguage().equals(LanguageDefinition.GERMAN)) {
                        event.getPlayer().sendMessage(LobbyService.PREFIX + "Du hast das Secret §8\"§c" + lobbySecret + "§8\" §7gefunden§8.");
                    } else {
                        event.getPlayer().sendMessage(LobbyService.PREFIX + "You found the secret §8\"§c" + lobbySecret + "§8\".");
                    }

                    LobbyService.getInstance().sendTitle(event.getPlayer(), "§8× §cSecret §8×", "§8» §c" + lobbySecret + " §8┃ §8+§620 Coins§8«", 7, 40, 7);

                    redPlayer.updatePropertyAsync(RedPlayerProperty.COINS, redPlayer.getNoCachedProperty$(RedPlayerProperty.COINS) + 20);

                    LobbyService.getInstance().getPlayerScoreboard().get(player.getUniqueId()).updateBoard(player, 8, "    §8➥ §7", "§c" + RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).$coins());
                } else {
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.VILLAGER_NO, 3, 2);
                    if (LobbyService.getInstance().getLanguage(event.getPlayer().getUniqueId()).equals(LanguageDefinition.GERMAN)) {
                        event.getPlayer().sendMessage(LobbyService.PREFIX + "Du hast dieses Secret bereits gefunden!");
                    } else {
                        event.getPlayer().sendMessage(LobbyService.PREFIX + "You already found the secret!");
                    }
                }
            }
            return;
        }

        event.setCancelled(true);

    }

}
