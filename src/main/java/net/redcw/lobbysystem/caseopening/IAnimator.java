package net.redcw.lobbysystem.caseopening;

/*
Interface created by SpigotSource on 14.06.2020 at 02:22
*/

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IAnimator {

    Inventory setupInventory(Player player);

    void onTick(Player player, Inventory inventory, List<ItemStack> items, boolean lastTick);

}
