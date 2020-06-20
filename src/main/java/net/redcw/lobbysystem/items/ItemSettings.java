package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 06.01.2020 at 18:13
*/

import com.google.common.collect.Maps;
import de.dytanic.cloudnet.bridge.internal.util.ItemStackBuilder;
import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.internal.enums.ItemType;
import net.redcw.lobbysystem.utils.Skull;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.language.LanguageDefinition;
import net.redcw.redapi.player.RedPlayerProperty;
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

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class ItemSettings implements Listener {

    @Getter
    private static ItemSettings instance;

    private Inventory inventory = Bukkit.createInventory(null, 54, "§8» §4Settings");

    private Map<UUID, Map<SettingInventory, Inventory>> playerInventorys = Maps.newConcurrentMap();

    public ItemSettings()
    {
        instance = this;
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event)
    {
        if(!event.getItem().hasItemMeta()) return;
        if(event.getItem().equals(LobbyService.getInstance().getGermanItems().get(ItemType.SETTINGS))
                || event.getItem().equals(LobbyService.getInstance().getEnglishItems().get(ItemType.SETTINGS)))
        {
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                event.getPlayer().openInventory(inventory);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.CHEST_OPEN, 3, -12);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event)
    {
        if(event.getInventory().equals(inventory))
        {
            final Player player = (Player) event.getWhoClicked();
            switch (event.getCurrentItem().getItemMeta().getDisplayName())
            {
                case "§8» §bSprache §8▰§7▱ §7Linksklick":
                    inventory.setItem(21, Skull.getCustomSkull(
                            "http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f",
                            "§8» §bDeutsch §8▰§7▱ §7Linksklick",
                            1,
                            null));

                    inventory.setItem(22, Skull.getCustomSkull(
                            "http://textures.minecraft.net/texture/a9edcdd7b06173d7d221c7274c86cba35730170788bb6a1db09cc6810435b92c",
                            "§8» §bEnglisch §8▰§7▱ §7Linksklick",
                            1,
                            null));
                    break;
                case "§8» §bDeutsch §8▰§7▱ §7Linksklick":
                    player.closeInventory();

                    RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).updateProperty(RedPlayerProperty.COUNTRY, LanguageDefinition.GERMAN);

                    player.sendMessage(LobbyService.PREFIX + "§7Du hast deine Sprache in §bDeutsch §7gewechselt§8!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 2);

                    LobbyService.getInstance().setupPlayer(player);
                    break;
                case "§8» §bEnglisch §8▰§7▱ §7Linksklick":
                    player.closeInventory();

                    player.sendMessage(LobbyService.PREFIX + "§cDiese Sprache ist noch nicht verfügbar!");
                    player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 3, 3);
                    break;
           }

        }

    }

    private void loadInventorys()
    {
        inventory.clear();

        final ItemStack GREY_GLASS = ItemStackBuilder.builder(Material.STAINED_GLASS_PANE, 1, 7).displayName(" ").build();

        for(int i = 0; i < 9; i++)
            inventory.setItem(i, GREY_GLASS);

        inventory.setItem(11, GREY_GLASS);
        inventory.setItem(17, GREY_GLASS);

        inventory.setItem(20, GREY_GLASS);
        inventory.setItem(26, GREY_GLASS);

        inventory.setItem(29, GREY_GLASS);
        inventory.setItem(35, GREY_GLASS);

        inventory.setItem(38, GREY_GLASS);
        inventory.setItem(44, GREY_GLASS);

        for(int i = (54 - 9); i < 54; i++)
            inventory.setItem(i, GREY_GLASS);

        inventory.setItem(9,
                Skull.getCustomSkull(
                        "http://textures.minecraft.net/texture/b1dd4fe4a429abd665dfdb3e21321d6efa6a6b5e7b956db9c5d59c9efab25",
                        "§8» §bSprache §8▰§7▱ §7Linksklick",
                        1,
                        Arrays.asList(
                                "§8§m----------------------",
                                " ",
                                " §8▰§7▱ §7Verfügbar Sprachen",
                                " ",
                                "     §8➥ §bDeutsch",
                                "     §8➥ §bEnglisch",
                                " ",
                                "§8§m----------------------"
                        )
                ));

        inventory.setItem(18, ItemStackBuilder.builder(Material.ENCHANTED_BOOK).displayName("§8» §cFreunde-Einstellungen §8▰§7▱ §7Linksklick")
                .lore(
                        Arrays.asList(
                                "§8§m----------------------",
                                " ",
                                " §8▰§7▱ §7Verfügbar Einstellungen",
                                " ",
                                "     §8➥ §cAnfragen",
                                "     §8➥ §cPrivate-Nachrichten",
                                "     §8➥ §cNachspringen",
                                " ",
                                "§8§m----------------------"
                        )
                ).build());

        inventory.setItem(27, ItemStackBuilder.builder(Material.CAKE).displayName("§8» §dParty-Einstellungen §8▰§7▱ §7Linksklick")
                .lore(
                        Arrays.asList(
                                "§8§m----------------------",
                                " ",
                                " §8▰§7▱ §7Verfügbar Einstellungen",
                                " ",
                                "     §8➥ §dAnfragen",
                                "     §8➥ §dPrivate-Nachrichten",
                                "     §8➥ §dNachspringen",
                                " ",
                                "§8§m----------------------"
                        )
                ).build());

        inventory.setItem(36, ItemStackBuilder.builder(Material.ANVIL).displayName("§8» §eClan-Einstellungen §8▰§7▱ §7Linksklick")
                .lore(Arrays.asList(
                        "§8§m----------------------",
                        " ",
                        " §8▰§7▱ §7Verfügbar Einstellungen",
                        " ",
                        "     §8➥ §eAnfragen",
                        "     §8➥ §ePrivate-Nachrichten",
                        "     §8➥ §eNachspringen",
                        " ",
                        "§8§m----------------------"
                        )
                ).build());

        inventory.setItem(10, ItemStackBuilder.builder(Material.STORAGE_MINECART).displayName("§8» §eLobby-Einstellungen").lore(
                Arrays.asList(
                        ""
                )
        ).build());
    }

    private enum SettingInventory {
        GENERAL, LOBBY, CLAN, FRIENDS, PARTY;
    }

}
