package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 06.01.2020 at 18:13
*/

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import de.dytanic.cloudnet.bridge.internal.util.ItemStackBuilder;
import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.internal.enums.ItemType;
import net.redcw.lobbysystem.utils.Skull;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.InventoryHandler;
import net.redcw.redapi.bukkit.RedAPIBukkit;
import net.redcw.redapi.bukkit.game.ItemManager;
import net.redcw.redapi.language.LanguageDefinition;
import net.redcw.redapi.player.RedPlayerProperty;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class ItemSettings implements Listener {

    @Getter
    private static ItemSettings instance;

    private MongoCollection<Document> friendCollection, clanCollection, partyCollection;

    private Map<UUID, Map<SettingInventory, Inventory>> playerInventory = Maps.newConcurrentMap();

    public ItemSettings()
    {
        instance = this;

        Bukkit.getScheduler().scheduleSyncDelayedTask(LobbyService.getInstance().getPlugin(), () -> friendCollection = RedAPI.getInstance().getMongoDatabase().getCollection("bungee_friendsystem"), 3*20);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        loadInventory(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if(LobbyService.getInstance().getGermanItems().get(ItemType.SETTINGS).equals(event.getItem()) ||
                LobbyService.getInstance().getEnglishItems().get(ItemType.SETTINGS).equals(event.getItem())) {
            player.openInventory(playerInventory.get(player.getUniqueId()).get(SettingInventory.MAIN_INVENTORY));
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if(playerInventory.get(player.getUniqueId()).containsValue(event.getInventory())) {
            event.setCancelled(true);

            switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                case "§8» §cSprache §8▰§7▱ Linksklick":
                    player.openInventory(playerInventory.get(player.getUniqueId()).get(SettingInventory.LANGUAGE));
                    break;
                case "§8» §aNetzwerk-Farbe §8▰§7▱ Linksklick":
                    player.openInventory(playerInventory.get(player.getUniqueId()).get(SettingInventory.NETWORK_COLOR));
                    break;
                case "§8» §eLobby §8▰§7▱ Linksklick":
                    player.openInventory(playerInventory.get(player.getUniqueId()).get(SettingInventory.LOBBY_SETTINGS));
                    break;
                case "§8» §cFreunde §8▰§7▱ Linksklick":
                    player.openInventory(playerInventory.get(player.getUniqueId()).get(SettingInventory.FRIENDS));
                    break;
                case "§8» §eClan §8▰§7▱ Linksklick":
                    player.openInventory(playerInventory.get(player.getUniqueId()).get(SettingInventory.CLAN));
                    break;
                case "§8» §dParty §8▰§7▱ Linksklick":
                    player.openInventory(playerInventory.get(player.getUniqueId()).get(SettingInventory.PARTY));
                    break;
            }
        }
    }

    private void loadInventory(final UUID uniqueId) {
        playerInventory.put(uniqueId, Maps.newConcurrentMap());

        RedAPI.getInstance().getProcessQueue().runTask(() -> {
            //      MAIN-INVENTORY      //

            final Inventory mainInventory = Bukkit.createInventory(null, 6*9, "§8▰§7▱ §4Einstellungen");

            InventoryHandler.fillAround(mainInventory, LobbyService.getInstance().getItemHistory().getGLASS_GRAY());

            mainInventory.setItem(4, new ItemManager(Material.COMMAND_MINECART).setDisplayName("§8» §4Einstellungen").addAllFlags().build());

            final LanguageDefinition language = LobbyService.getInstance().getLanguage(uniqueId);
            final String languageUrl = (
                    language.equals(LanguageDefinition.GERMAN) ? "http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f" :
                    language.equals(LanguageDefinition.ENGLISH) ? "http://textures.minecraft.net/texture/a9edcdd7b06173d7d221c7274c86cba35730170788bb6a1db09cc6810435b92c" :
                    language.equals(LanguageDefinition.FRENCH) ? "http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc" :
                    language.equals(LanguageDefinition.RUSSIA) ? "http://textures.minecraft.net/texture/16eafef980d6117dabe8982ac4b4509887e2c4621f6a8fe5c9b735a83d775ad" :
                    "null");

            mainInventory.setItem(21, new ItemManager(Skull.getCustomSkull(languageUrl, "§8» §cSprache §8▰§7▱ Linksklick", 1, null)).addAllFlags()
                    .addLoreAll(Arrays.asList(
                            "§8§m----------------------------------",
                            " ",
                            " §8» §7Ändere deine §cSprache §7auf dem Server§8.",
                            " ",
                            "§8§m----------------------------------"))
                    .build());

            mainInventory.setItem(22, new ItemManager(Material.INK_SACK).setDisplayName("§8» §aNetzwerk-Farbe §8▰§7▱ Linksklick").setData((short) 10).addAllFlags()
                    .addLoreLine("§8§m----------------------------------")
                    .addLoreLine(" ")
                    .addLoreLine(" §8» §7Ändere die §aFarbe §7in der dir das")
                    .addLoreLine("     §7Netzwerk dargestellt wird§8.")
                    .addLoreLine(" ")
                    .addLoreLine("§8§m----------------------------------")
                    .build());

            mainInventory.setItem(23, new ItemManager(Material.STORAGE_MINECART).setDisplayName("§8» §eLobby §8▰§7▱ Linksklick").addAllFlags()
                    .addLoreLine("§8§m----------------------------------")
                    .addLoreLine(" ")
                    .addLoreLine(" §8» §7Stelle dir die §eLobby §7so ein wie")
                    .addLoreLine("     §ees §edir §7gefällt§8.")
                    .addLoreLine(" ")
                    .addLoreLine("§8§m----------------------------------")
                    .build());

            mainInventory.setItem(29, new ItemManager(Material.ENCHANTED_BOOK).setDisplayName("§8» §cFreunde §8▰§7▱ Linksklick").addAllFlags()
                    .addLoreLine("§8§m----------------------------------")
                    .addLoreLine(" ")
                    .addLoreLine(" §8» §7Ändere was dir an deinen §cFreundschafts-")
                    .addLoreLine("     §cEinstellungen §7nicht gefällt§8.")
                    .addLoreLine(" ")
                    .addLoreLine("§8§m----------------------------------")
                    .build());

            mainInventory.setItem(30, new ItemManager(Material.GOLD_CHESTPLATE).setGlow().setDisplayName("§8» §eClan §8▰§7▱ Linksklick").addAllFlags()
                    .addLoreLine("§8§m----------------------------------")
                    .addLoreLine(" ")
                    .addLoreLine((" §8» §7Ändere §edeine §7und die"))
                    .addLoreLine("      §eClan-Einstellungen §7welche dir nicht gefallen§8.")
                    .addLoreLine(" ")
                    .addLoreLine("§8§m----------------------------------")
                    .build());

            mainInventory.setItem(31, new ItemManager(Material.CAKE).setDisplayName("§8» §dParty §8▰§7▱ Linksklick").addAllFlags()
                    .addLoreLine("§8§m----------------------------------")
                    .addLoreLine(" ")
                    .addLoreLine(" §8» §7Ändere deine §dParty-Einstellungen§8.")
                    .addLoreLine(" ")
                    .addLoreLine("§8§m----------------------------------")
                    .build());

            mainInventory.setItem(32, new ItemManager(Material.NAME_TAG).setDisplayName("§8» §eStats-Reset §8▰§7▱ Linksklick").addAllFlags()
                    .addLoreLine("§8§m----------------------------------")
                    .addLoreLine(" ")
                    .addLoreLine(" §8» §7Setze deine §eStats §7in einem")
                    .addLoreLine("    §ebestimmten Modus §7zurück§8.")
                    .addLoreLine(" ")
                    .addLoreLine("§8§m----------------------------------")
                    .build());

            mainInventory.setItem(33, new ItemManager(Material.BARRIER).setDisplayName(" ").build());

            playerInventory.get(uniqueId).put(SettingInventory.MAIN_INVENTORY, mainInventory);

            //      LANGUAGE-INVENTORY      //

            final Inventory languageInventory = Bukkit.createInventory(null, 6*9, "§8▰§7▱ §4Einstellungen §8× §7Sprache");

            fillInventory(uniqueId, languageInventory);

            languageInventory.setItem(22, Skull.getCustomSkull(
                    "http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f",
                    "§8» §cDeutsch §8▰§7▱ Linksklick",
                    1, Arrays.asList("  §8➥ §7§oKlicke zum auswählen§8.")));

            languageInventory.setItem(23, Skull.getCustomSkull(
                    "http://textures.minecraft.net/texture/a9edcdd7b06173d7d221c7274c86cba35730170788bb6a1db09cc6810435b92c",
                    "§8» §cEnglish §8▰§7▱ Linksklick",
                    1, Arrays.asList("  §8➥ §7§oClick to select§8.")));

            languageInventory.setItem(24, Skull.getCustomSkull(
                    "http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc",
                    "§8» §cfrançais §8▰§7▱ Linksklick",
                    1, Arrays.asList("  §8➥ §7§oCliquez pour sélectionner§8.")));

            languageInventory.setItem(31, Skull.getCustomSkull(
                    "http://textures.minecraft.net/texture/16eafef980d6117dabe8982ac4b4509887e2c4621f6a8fe5c9b735a83d775ad",
                    "§8» §cрусский §8▰§7▱ Linksklick",
                    1, Arrays.asList("  §8➥ §7§oНажимай к выбору§8.")));

            playerInventory.get(uniqueId).put(SettingInventory.LANGUAGE, languageInventory);

            //      COLOR-INVENTORY     //

            final Inventory colorInventory = Bukkit.createInventory(null, 6*9, "§8▰§7▱ §4Einstellungen §8× §7Farbe");

            fillInventory(uniqueId, colorInventory);

            colorInventory.setItem(22, new ItemManager(Material.INK_SACK).setDisplayName("§8» §cRot §8▰§7▱ Linksklick").setData((short) 1).build());
            colorInventory.setItem(23, new ItemManager(Material.INK_SACK).setDisplayName("§8» §aGrün §8▰§7▱ Linksklick").setData((short) 10).build());
            colorInventory.setItem(24, new ItemManager(Material.INK_SACK).setDisplayName("§8» §9Blau §8▰§7▱ Linksklick").setData((short) 4).build());
            colorInventory.setItem(31, new ItemManager(Material.INK_SACK).setDisplayName("§8» §5Lila §8▰§7▱ Linksklick").setData((short) 5).build());
            colorInventory.setItem(32, new ItemManager(Material.INK_SACK).setDisplayName("§8» §bTürkis §8▰§7▱ Linksklick").setData((short) 6).build());
            colorInventory.setItem(33, new ItemManager(Material.INK_SACK).setDisplayName("§8» §eGelb §8▰§7▱ Linksklick").setData((short) 11).build());

            playerInventory.get(uniqueId).put(SettingInventory.NETWORK_COLOR, colorInventory);

            //      LOBBY-INVENTORY     //

            final Inventory lobbyInventory = Bukkit.createInventory(null, 6*9, "§8▰§7▱ §4Einstellungen §8× §7Farbe");

            fillInventory(uniqueId, lobbyInventory);

            lobbyInventory.setItem(22, new ItemManager(Material.TORCH).setDisplayName("§8» §eSpieler-Sichtbarkeit §8▰§7▱ Linksklick").build());
            lobbyInventory.setItem(23, new ItemManager(Material.RECORD_8).setDisplayName("§8» §eTeleport-Animation §8▰§7▱ Linksklick").build());
            lobbyInventory.setItem(24, new ItemManager(Material.BOOK).setDisplayName("§8» §eChat §8▰§7▱ Linksklick").build());
            lobbyInventory.setItem(31, new ItemManager(Material.PAPER).setDisplayName("§8» §eScoreboard §8▰§7▱ Linksklick").build());
            lobbyInventory.setItem(32, new ItemManager(Material.WATCH).setDisplayName("§8» §eLobby-Zeit §8▰§7▱ Linksklick").build());
            lobbyInventory.setItem(33, new ItemManager(Material.COOKIE).setDisplayName("§8» §eCookieClicker-Sound §8▰§7▱ Linksklick").build());

            playerInventory.get(uniqueId).put(SettingInventory.LOBBY_SETTINGS, lobbyInventory);

            //      FRIEND-INVENTORY        //

            final Inventory friendInventory = Bukkit.createInventory(null, 6*9, "§8▰§7▱ §4Einstellungen §8× §7Freunde");

            fillInventory(uniqueId, friendInventory);

            final Document friendDocument = friendCollection.find(new Document("uniqueId", uniqueId.toString())).first();

            friendInventory.setItem(22, new ItemManager(Material.BOOKSHELF).setDisplayName("§8» §cAnfragen §8● " + (friendDocument.getBoolean("requestsEnabled") ? "§a✔" : "§c✖") + " §8▰§7▱ Linksklick").build());
            friendInventory.setItem(23, new ItemManager(Material.PAPER).setDisplayName("§8» §cOnline/Offline-Nachrichten §8● " + (friendDocument.getBoolean("messagesEnabled") ? "§a✔" : "§c✖") + " §8▰§7▱ Linksklick").build());
            friendInventory.setItem(24, new ItemManager(RedAPIBukkit.getPlugin().getReflectionSkull(uniqueId)).setDisplayName("§8» §cPrivate-Nachrichten §8● " + (friendDocument.getBoolean("privateMessagesEnabled") ? "§a✔" : "§c✖") + " §8▰§7▱ Linksklick").build());
            friendInventory.setItem(31, new ItemManager(Material.GOLD_BOOTS).setGlow().setDisplayName("§8» §cNachspringen §8● " + (friendDocument.getBoolean("jumpEnabled") ? "§a✔" : "§c✖") + " §8▰§7▱ Linksklick").build());
            friendInventory.setItem(32, new ItemManager(Material.SIGN).setDisplayName("§8» §cStatus §8● §aRedCW <3 §8▰§7▱ Linksklick").build());
            friendInventory.setItem(33, new ItemManager(Material.SKULL).setDisplayName("§8» §cOnline-Status §8● §aOnline §8▰§7▱ Linksklick").build());

            playerInventory.get(uniqueId).put(SettingInventory.FRIENDS, friendInventory);

            //      CLAN-INVENTORY      //

            final Inventory clanInventory = Bukkit.createInventory(null, 6*9, "§8▰§7▱ §4Einstellungen §8× §7Clan");

            fillInventory(uniqueId, clanInventory);

            clanInventory.setItem(22, new ItemManager(Material.BOOKSHELF).setDisplayName("§8» §eAnfragen §8▰§7▱ Linksklick").build());
            clanInventory.setItem(23, new ItemManager(Material.BOOK).setDisplayName("§8» §eClan-Chat Nachrichten §8▰§7▱ Linksklick").build());
            clanInventory.setItem(24, new ItemManager(Material.PAPER).setDisplayName("§8» §eOnline/Offline Nachrichten §8▰§7▱ Linksklick").build());
            clanInventory.setItem(31, new ItemManager(Material.BEACON).setDisplayName("§8» §eClan-Join/Leave Nachrichten §8▰§7▱ Linksklick").build());
            clanInventory.setItem(32, new ItemManager(Material.INK_SACK).setDisplayName("§8» §eClan-Tag Farbe §8▰§7▱ Linksklick").setData((short) 11).build());
            clanInventory.setItem(33, new ItemManager(Material.SKULL_ITEM).setDisplayName("§8» §eOnline-Status §8▰§7▱ Linksklick").build());

            playerInventory.get(uniqueId).put(SettingInventory.CLAN, clanInventory);

            //      PARTY-INVENTORY        //

            final Inventory partyInventory = Bukkit.createInventory(null, 6*9, "§8▰§7▱ §4Einstellungen §8× §7Party");

            partyInventory.setItem(22, new ItemManager(Material.BOOKSHELF).setDisplayName("§8» §5Anfragen §8▰§7▱ Linksklick").build());
            partyInventory.setItem(23, new ItemManager(Material.PAPER).setDisplayName("§8» §5Party-Chat Nachrichten §8▰§7▱ Linksklick").build());

            playerInventory.get(uniqueId).put(SettingInventory.PARTY, partyInventory);
        });
    }

    private void fillInventory(final UUID uniqueId, final Inventory inventory) {
        final ItemStack GREY_GLASS = LobbyService.getInstance().getItemHistory().getGLASS_GRAY();

        InventoryHandler.fillAround(inventory, GREY_GLASS);

        inventory.setItem(11, GREY_GLASS);
        inventory.setItem(20, GREY_GLASS);
        inventory.setItem(29, GREY_GLASS);
        inventory.setItem(38, GREY_GLASS);

        final LanguageDefinition language = LobbyService.getInstance().getLanguage(uniqueId);
        final String languageUrl = (
                language.equals(LanguageDefinition.GERMAN) ? "http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f" :
                language.equals(LanguageDefinition.ENGLISH) ? "http://textures.minecraft.net/texture/a9edcdd7b06173d7d221c7274c86cba35730170788bb6a1db09cc6810435b92c" :
                language.equals(LanguageDefinition.FRENCH) ? "http://textures.minecraft.net/texture/51269a067ee37e63635ca1e723b676f139dc2dbddff96bbfef99d8b35c996bc" :
                language.equals(LanguageDefinition.RUSSIA) ? "http://textures.minecraft.net/texture/16eafef980d6117dabe8982ac4b4509887e2c4621f6a8fe5c9b735a83d775ad" :
                "null");

        inventory.setItem(9, new ItemManager(Skull.getCustomSkull(languageUrl, "§8» §cSprache §8▰§7▱ Linksklick", 1, null)).addAllFlags()
                .addLoreAll(Arrays.asList(
                        "§8§m----------------------------------",
                        " ",
                        " §8» §7Ändere deine §cSprache §7auf dem Server§8.",
                        " ",
                        "§8§m----------------------------------"))
                .build());

        inventory.setItem(18, new ItemManager(Material.INK_SACK).setDisplayName("§8» §aNetzwerk-Farbe §8▰§7▱ Linksklick").setData((short) 10).addAllFlags()
                .addLoreLine("§8§m----------------------------------")
                .addLoreLine(" ")
                .addLoreLine(" §8» §7Ändere die §aFarbe §7in der dir das")
                .addLoreLine("     §7Netzwerk dargestellt wird§8.")
                .addLoreLine(" ")
                .addLoreLine("§8§m----------------------------------")
                .build());

        inventory.setItem(27, new ItemManager(Material.STORAGE_MINECART).setDisplayName("§8» §eLobby §8▰§7▱ Linksklick").addAllFlags()
                .addLoreLine("§8§m----------------------------------")
                .addLoreLine(" ")
                .addLoreLine(" §8» §7Stelle dir die §eLobby §7so ein wie")
                .addLoreLine("     §ees §edir §7gefällt§8.")
                .addLoreLine(" ")
                .addLoreLine("§8§m----------------------------------")
                .build());

        inventory.setItem(36, new ItemManager(Material.NAME_TAG).setDisplayName("§8» §eStats-Reset §8▰§7▱ Linksklick").addAllFlags()
                .addLoreLine("§8§m----------------------------------")
                .addLoreLine(" ")
                .addLoreLine(" §8» §7Setze deine §eStats §7in einem")
                .addLoreLine("    §ebestimmten Modus §7zurück§8.")
                .addLoreLine(" ")
                .addLoreLine("§8§m----------------------------------")
                .build());

        inventory.setItem(10, new ItemManager(Material.ENCHANTED_BOOK).setDisplayName("§8» §cFreunde §8▰§7▱ Linksklick").addAllFlags()
                .addLoreLine("§8§m----------------------------------")
                .addLoreLine(" ")
                .addLoreLine(" §8» §7Ändere was dir an deinen §cFreundschafts-")
                .addLoreLine("     §cEinstellungen §7nicht gefällt§8.")
                .addLoreLine(" ")
                .addLoreLine("§8§m----------------------------------")
                .build());

        inventory.setItem(19, new ItemManager(Material.GOLD_CHESTPLATE).setGlow().setDisplayName("§8» §eClan §8▰§7▱ Linksklick").addAllFlags()
                .addLoreLine("§8§m----------------------------------")
                .addLoreLine(" ")
                .addLoreLine((" §8» §7Ändere §edeine §7und die"))
                .addLoreLine("      §eClan-Einstellungen §7welche dir nicht gefallen§8.")
                .addLoreLine(" ")
                .addLoreLine("§8§m----------------------------------")
                .build());

        inventory.setItem(28, new ItemManager(Material.CAKE).setDisplayName("§8» §dParty §8▰§7▱ Linksklick").addAllFlags()
                .addLoreLine("§8§m----------------------------------")
                .addLoreLine(" ")
                .addLoreLine(" §8» §7Ändere deine §dParty-Einstellungen§8.")
                .addLoreLine(" ")
                .addLoreLine("§8§m----------------------------------")
                .build());


        inventory.setItem(37, new ItemManager(Material.BARRIER).setDisplayName(" ").build());
    }

    private enum SettingInventory {
        MAIN_INVENTORY, LANGUAGE, FRIENDS, CLAN, PARTY, NETWORK_COLOR, LOBBY_SETTINGS, STATS_RESET;
    }

}
