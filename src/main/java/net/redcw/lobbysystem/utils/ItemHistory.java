package net.redcw.lobbysystem.utils;

/*
Class created by SpigotSource on 12.01.2020 at 17:42
*/

import lombok.Getter;
import net.redcw.lobbysystem.manager.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class ItemHistory {

    private ItemStack GLASS_BLACK;
    private ItemStack GLASS_GRAY;
    private ItemStack GLASS_RED;

    private ItemStack GROUP_ITEM_LOBBY;
    private ItemStack GROUP_ITEM_INGAME;

    public ItemHistory()
    {
        GLASS_BLACK = new ItemManager(Material.STAINED_GLASS_PANE).setData((short) 15).build();
        GLASS_GRAY = new ItemManager(Material.STAINED_GLASS_PANE).setData((short) 7).build();
        GLASS_RED = new ItemManager(Material.STAINED_GLASS_PANE).setData((short) 14).build();

        GROUP_ITEM_LOBBY = new ItemManager(Material.STAINED_GLASS_PANE).setData((short) 5).build();
        GROUP_ITEM_INGAME = new ItemManager(Material.STAINED_GLASS_PANE).setData((short) 1).build();
    }

    public void fillAround(Inventory inventory, ItemStack filler) {
        for (int i = 0; i < (inventory.getSize() / 9); i++) {
            if (i == 0) {
                for (int j = 0; j < 9; j++) {
                    inventory.setItem(j, filler);
                    inventory.setItem(inventory.getSize() - 9 + j, filler);
                }
            }

            inventory.setItem(i * 9 + 8, filler);
            inventory.setItem(i * 9, filler);
        }
    }

}
