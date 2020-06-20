package net.redcw.lobbysystem.items;

/*
Class created by SpigotSource on 06.01.2020 at 01:20
*/

import lombok.Getter;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.internal.enums.ItemType;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.language.LanguageDefinition;
import net.redcw.redapi.player.RedPlayerProperty;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemNick implements Listener {

    @Getter
    private static ItemNick instance;

    public ItemNick()
    {
        instance = this;
    }

    @EventHandler
    public void onInteract(final PlayerInteractEvent event)
    {
        if(!event.getItem().hasItemMeta()) return;
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))
            if(event.getItem().equals(LobbyService.getInstance().getGermanItems().get(ItemType.NICK_ON))
                    || event.getItem().equals(LobbyService.getInstance().getEnglishItems().get(ItemType.NICK_ON)))
            {
                final Player player = event.getPlayer();

                if(LobbyService.getInstance().getLanguage(player.getUniqueId()).equals(LanguageDefinition.GERMAN))
                {
                    player.getInventory().setItem(4, LobbyService.getInstance().getGermanItems().get(ItemType.NICK_OFF));
                } else {
                    player.getInventory().setItem(4, LobbyService.getInstance().getEnglishItems().get(ItemType.NICK_OFF));
                }

                player.playSound(player.getLocation(), Sound.BLAZE_DEATH, 3, 3);

                RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).updateProperty(RedPlayerProperty.NICK_SERVICE, false);
            } else if(event.getItem().equals(LobbyService.getInstance().getGermanItems().get(ItemType.NICK_OFF))
                    || event.getItem().equals(LobbyService.getInstance().getEnglishItems().get(ItemType.NICK_OFF)))
            {
                final Player player = event.getPlayer();

                if(LobbyService.getInstance().getLanguage(player.getUniqueId()).equals(LanguageDefinition.GERMAN))
                {
                    player.getInventory().setItem(4, LobbyService.getInstance().getGermanItems().get(ItemType.NICK_ON));
                } else {
                    player.getInventory().setItem(4, LobbyService.getInstance().getEnglishItems().get(ItemType.NICK_ON));
                }

                player.playSound(player.getLocation(), Sound.LEVEL_UP, 3, 3);

                RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).updateProperty(RedPlayerProperty.NICK_SERVICE, true);
            }

    }

}
