package net.redcw.lobbysystem.utils;

/*
Class created by SpigotSource on 06.01.2020 at 18:35
*/

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

public class Skull {

    private static final Base64 base64 = new Base64();
    private String id;

    private Skull(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static ItemStack getCustomSkull(String url, String displayname, int amount, List<String> lore) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        ItemStack head = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName(displayname);
        headMeta.setLore(lore);
        Class<?> headMetaClass = headMeta.getClass();
        Reflections.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);
        head.setItemMeta(headMeta);
        return head;
    }

}
