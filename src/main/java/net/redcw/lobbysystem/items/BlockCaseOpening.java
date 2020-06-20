package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 14.06.2020 at 02:44
*/

import de.dytanic.cloudnet.bridge.internal.util.ItemStackBuilder;
import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.caseopening.Animator;
import net.redcw.lobbysystem.caseopening.ChestLoot;
import net.redcw.lobbysystem.caseopening.IAnimator;
import net.redcw.lobbysystem.player.LobbyPlayer;
import net.redcw.lobbysystem.utils.Skull;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.player.RedPlayer;
import net.redcw.redapi.player.RedPlayerProperty;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Arrays;
import java.util.List;

@Getter
public class BlockCaseOpening implements Listener {

    private static BlockCaseOpening instance;

    private Location caseOpeningLocation;

    public BlockCaseOpening() {
        instance = this;

        caseOpeningLocation = new Location(Bukkit.getWorld("Lobby"), 162.5, 35, 814.5);
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) return;
        if(!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;

        if(event.getClickedBlock().getLocation().getBlockX() == caseOpeningLocation.getBlockX() && event.getClickedBlock().getLocation().getBlockZ() == caseOpeningLocation.getBlockZ()) {
            final Inventory inventory = Bukkit.createInventory(null, 5*9, "§8▰§7▱ §5CaseOpening");

            final LobbyPlayer lobbyPlayer = LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(event.getPlayer().getUniqueId());

            LobbyService.getInstance().getItemHistory().fillAround(inventory, LobbyService.getInstance().getItemHistory().getGLASS_GRAY());

            inventory.setItem(4, ItemStackBuilder.builder(Material.ENDER_CHEST).displayName("§8» §5CaseOpening").build());

            inventory.setItem(21, Skull.getCustomSkull("http://textures.minecraft.net/texture/d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622", "§8» §eNormal §8▰§7▱ Case", (lobbyPlayer.getNormalCaseKeys() <= 64 ? lobbyPlayer.getNormalCaseKeys() : 64),
                    Arrays.asList(
                            "§8§m------------------------------",
                            " ",
                            " §8» §7Du besitzt momentan §e" + lobbyPlayer.getNormalCaseKeys() + " Normal-Case §7Keys§8.",
                            " ",
                            "§8§m------------------------------"
                    )));

            inventory.setItem(23, Skull.getCustomSkull("http://textures.minecraft.net/texture/a6cc486c2be1cb9dfcb2e53dd9a3e9a883bfadb27cb956f1896d602b4067", "§8» §5Epic §8▰§7▱ Case", (lobbyPlayer.getEpicCaseKeys() <= 64 ? lobbyPlayer.getEpicCaseKeys() : 64),
                    Arrays.asList(
                            "§8§m------------------------------",
                            " ",
                            " §8» §7Du besitzt momentan §5" + lobbyPlayer.getEpicCaseKeys() + " Epic-Case §7Keys§8.",
                            " ",
                            "§8§m------------------------------"
                    )));


            event.getPlayer().openInventory(inventory);
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if(event.getInventory().getTitle().equals("§8▰§7▱ §5CaseOpening")) {
            final LobbyPlayer lobbyPlayer = LobbyService.getInstance().getLobbyPlayerProvider().getPlayer(player.getUniqueId());
            switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                case "§8» §eNormal §8▰§7▱ Case":
                    if(lobbyPlayer.getNormalCaseKeys() == 0) {
                        player.sendMessage(LobbyService.PREFIX + "§cDu besitzt keine Normal-Chest keys.");
                        player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 3, 3);
                        player.closeInventory();
                        return;
                    }

                    lobbyPlayer.setNormalCaseKeys((lobbyPlayer.getNormalCaseKeys() - 1));

                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 2);
                    LobbyService.getInstance().setChestAnimator(new Animator(player, new IAnimator() {
                        @Override
                        public Inventory setupInventory(Player player)
                        {
                            Inventory inv = Bukkit.createInventory(player, 27, "§8▰§7▱ §5CaseOpening");
                            for (int i = 0; i < inv.getSize(); i++)
                            {
                                inv.setItem(i, LobbyService.getInstance().getItemHistory().getGLASS_GRAY());
                            }
                            return inv;
                        }

                        @Override
                        public void onTick(Player player, Inventory inventory, List<ItemStack> items, boolean lastTick) {
                            player.playSound(player.getLocation(), Sound.CLICK, 2, 1);
                            if (lastTick) {
                                if (player != null) {
                                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 1);

                                    final RedPlayer redPlayer = RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId());

                                    if (inventory.getItem(13).getItemMeta().getDisplayName().equalsIgnoreCase("§8» §6100 §8▰§7▱ §6Coins"))
                                    {
                                        redPlayer.updateProperty(RedPlayerProperty.COINS, (redPlayer.$coins() + 100));
                                        player.sendMessage(LobbyService.PREFIX + "§7Du hast §6100 Coins §7gewonnen§8.");
                                    }
                                    else if (inventory.getItem(13).getItemMeta().getDisplayName().equalsIgnoreCase("§8» §6300 §8▰§7▱ §6Coins"))
                                    {
                                        redPlayer.updateProperty(RedPlayerProperty.COINS, (redPlayer.$coins() + 300));
                                        player.sendMessage(LobbyService.PREFIX + "§7Du hast §6300 Coins §7gewonnen§8.");
                                    }
                                    else if (inventory.getItem(13).getItemMeta().getDisplayName().equalsIgnoreCase("§8» §6500 §8▰§7▱ §6Coins"))
                                    {
                                        redPlayer.updateProperty(RedPlayerProperty.COINS, (redPlayer.$coins() + 500));
                                        player.sendMessage(LobbyService.PREFIX + "§7Du hast §6500 Coins §7gewonnen§8.");
                                    }
                                    else if (inventory.getItem(13).getItemMeta().getDisplayName().equalsIgnoreCase("§8» §61000 §8▰§7▱ §6Coins"))
                                    {
                                        redPlayer.updateProperty(RedPlayerProperty.COINS, (redPlayer.$coins() + 1000));
                                        player.sendMessage(LobbyService.PREFIX + "§7Du hast §61000 Coins §7gewonnen§8.");
                                    }
                                    else if (inventory.getItem(13).getItemMeta().getDisplayName().equalsIgnoreCase("§8» §e5000 §8▰§7▱ §6Cookies"))
                                    {
                                        LobbyService.getInstance().getPlayerCookies().replace(player.getUniqueId(), (LobbyService.getInstance().getPlayerCookies().get(player.getUniqueId()) + 5000));
                                        player.sendMessage(LobbyService.PREFIX + "§7Du hast §e5000 Cookies §7gewonnen§8.");
                                    }
                                    else if (inventory.getItem(13).getItemMeta().getDisplayName().equalsIgnoreCase("§8» §e1000 §8▰§7▱ §6Cookies"))
                                    {
                                        LobbyService.getInstance().getPlayerCookies().replace(player.getUniqueId(), (LobbyService.getInstance().getPlayerCookies().get(player.getUniqueId()) + 1000));
                                        player.sendMessage(LobbyService.PREFIX + "§7Du hast §e1000 Cookies §7gewonnen§8.");
                                    }
                                    else if (inventory.getItem(13).getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cNiete §8▰§7▱ §c§oNichts"))
                                    {
                                        player.sendMessage(LobbyService.PREFIX + "§7Du hast leider §c§oNichts §7gewonnen§8.");
                                    }

                                    player.closeInventory();

                                    LobbyService.getInstance().getPlayerScoreboard().get(player.getUniqueId()).updateBoard(player, 8, "    §8➥ §7", "§c" + RedAPI.getInstance().getTextService().formatInteger(RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).$coins()));

                                    final String win = inventory.getItem(13).getItemMeta().getDisplayName().replace("§cNiete ", "").replace("§8» ", "").replace("§8▰§7▱ ", "");

                                    Bukkit.broadcastMessage(" ");
                                    Bukkit.broadcastMessage("  §8[§5!§8] §5CaseOpening §8» " + LobbyService.getInstance().getPlayerGroup(player.getUniqueId()).getPrefix().replace("&", "§") + player.getName() + " §7hat " + win + " §7gewonnen§8!");
                                    Bukkit.broadcastMessage(" ");

                                    Bukkit.getScheduler().runTaskLater(LobbyService.getInstance().getPlugin(), () -> {
                                        Firework fire = player.getWorld().spawn(player.getLocation(), Firework.class);

                                        FireworkMeta meta = fire.getFireworkMeta();
                                        meta.addEffect(FireworkEffect.builder().withFade(Color.FUCHSIA).flicker(true)
                                                .withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE).flicker(true).build());
                                        meta.setPower(2);

                                        fire.setFireworkMeta(meta);
                                    }, 10);

                                    return;
                                }
                            }

                            inventory.setItem(4, ItemStackBuilder.builder(Material.HOPPER).displayName("§8» §7Gewinn").build());
                            inventory.setItem(10, items.get(0));
                            inventory.setItem(11, items.get(1));
                            inventory.setItem(12, items.get(2));
                            inventory.setItem(13, items.get(3));
                            inventory.setItem(14, items.get(4));
                            inventory.setItem(15, items.get(5));
                            inventory.setItem(16, items.get(6));
                            items.add(items.get(0).clone());
                            items.remove(items.get(0));

                        }
                    }));
                    new ChestLoot().getNormalLoot();
                    break;
            }
        }
    }

}
