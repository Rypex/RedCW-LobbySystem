package net.redcw.lobbysystem.caseopening;

/*
Class created by SpigotSource on 14.06.2020 at 02:22
*/

import net.redcw.lobbysystem.LobbyService;
import net.redcw.redapi.bukkit.game.ItemManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChestLoot {

    public static List<ItemStack> normalLoot = new ArrayList<>();
    public static List<ItemStack> epicLoot = new ArrayList<>();

    public void getNormalLoot() {
        for (int i = 0; i < 8; i++)
            normalLoot.add(new ItemManager(Material.GOLD_INGOT).setAmount(1).setDisplayName("§8» §6100 §8▰§7▱ §6Coins").build());

        for (int i = 0; i < 5; i++)
            normalLoot.add(new ItemManager(Material.GOLD_INGOT).setAmount(3).setDisplayName("§8» §6300 §8▰§7▱ §6Coins").build());

        for (int i = 0; i < 4; i++)
            normalLoot.add(new ItemManager(Material.GOLD_INGOT).setAmount(5).setDisplayName("§8» §6500 §8▰§7▱ §6Coins").build());

        for (int i = 0; i < 2; i++)
            normalLoot.add(new ItemManager(Material.GOLD_INGOT).setAmount(10).setDisplayName("§8» §61000 §8▰§7▱ §6Coins").setGlow().build());

        for (int i = 0; i < 3; i++)
            normalLoot.add(new ItemManager(Material.COOKIE).setAmount(50).setDisplayName("§8» §e5000 §8▰§7▱ §6Cookies").setGlow().build());

        for (int i = 0; i < 6; i++)
            normalLoot.add(new ItemManager(Material.COOKIE).setAmount(10).setDisplayName("§8» §e1000 §8▰§7▱ §6Cookies").build());

        for (int i = 0; i < 2; i++)
            normalLoot.add(new ItemManager(Material.BARRIER).setAmount(0).setDisplayName("§8» §cNiete §8▰§7▱ §c§oNichts").build());

        Collections.shuffle(normalLoot);

        LobbyService.getInstance().getChestAnimator().startAnimation(60, ChestLoot.normalLoot, 20);
    }


    public void getEpicLoot() {
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§c•§4● 3x Traitor §8∎ §7Pass").build());

        epicLoot.add(new ItemManager(Material.PAPER).setAmount(4).setDisplayName("§c•§4● 4x Traitor §8∎ §7Pass").setGlow().build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(4).setDisplayName("§c•§4● 4x Traitor §8∎ §7Pass").setGlow().build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(4).setDisplayName("§c•§4● 4x Traitor §8∎ §7Pass").setGlow().build());

        epicLoot.add(new ItemManager(Material.PAPER).setAmount(0).setDisplayName("§4•§c● 1x Niete §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(0).setDisplayName("§4•§c● 1x Niete §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(0).setDisplayName("§4•§c● 1x Niete §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(0).setDisplayName("§4•§c● 1x Niete §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(0).setDisplayName("§4•§c● 1x Niete §8∎ §7Pass").build());

        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(3).setDisplayName("§1•§9● 3x Detektive §8∎ §7Pass").build());

        epicLoot.add(new ItemManager(Material.PAPER).setAmount(4).setDisplayName("§1•§9● 4x Detektive §8∎ §7Pass").setGlow().build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(4).setDisplayName("§1•§9● 4x Detektive §8∎ §7Pass").setGlow().build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(4).setDisplayName("§1•§9● 4x Detektive §8∎ §7Pass").setGlow().build());

        epicLoot.add(new ItemManager(Material.PAPER).setAmount(2).setDisplayName("§1•§9● 2x Detektive §7& §4Traitor §8∎ §7Pass").setGlow().build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(2).setDisplayName("§1•§9● 2x Detektive §7& §4Traitor §8∎ §7Pass").setGlow().build());
        epicLoot.add(new ItemManager(Material.PAPER).setAmount(2).setDisplayName("§1•§9● 2x Detektive §7& §4Traitor §8∎ §7Pass").setGlow().build());

        Collections.shuffle(epicLoot);

        LobbyService.getInstance().getChestAnimator().startAnimation(60, ChestLoot.epicLoot, 20);
    }
}
