package net.redcw.lobbysystem.utils;

/*
Class created by SpigotSource on 23.05.2020 at 19:37
*/

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class Hologram {

    private java.util.List<EntityArmorStand> List = new ArrayList<EntityArmorStand>();
    private String[] text;
    private Location loc;
    private double distance = 0.25D;
    int count;

    public Hologram(String[] text, Location loc){
        this.text = text;
        this.loc = loc;
        create();
    }

    public void create(){
        for(String text : this.text){
            EntityArmorStand e = new EntityArmorStand(((CraftWorld)this.loc.getWorld()).getHandle(), this.loc.getX(), this.loc.getY(), this.loc.getZ());

            e.setCustomName(text);
            e.setCustomNameVisible(true);
            e.setInvisible(true);
            e.setGravity(true);
            List.add(e);
            count++;
            this.loc.subtract(0, this.distance,0);

        }
        for(int i = 0; i < count; i++){
            this.loc.add(0,this.distance, 0);
        }
        this.count++;
    }


    public void hidePlayer(Player p){
        for(EntityArmorStand a : List){
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(a.getId());
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);

        }


    }

    public void showPlayer(Player p){
        for(EntityArmorStand a : List){
            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(a);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }
    }
}

