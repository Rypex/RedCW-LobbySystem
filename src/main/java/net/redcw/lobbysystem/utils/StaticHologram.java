package net.redcw.lobbysystem.utils;

/*
Class created by SpigotSource on 24.05.2020 at 22:16
*/

import net.redcw.lobbysystem.LobbyService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitTask;

public class StaticHologram {

    String text = "";
    double height = 0;
    Location location = null;
    ArmorStand hologram = null;

    public StaticHologram(Location location,String Text, double height) {
        this.text = Text;
        this.location = location;
        this.height = height;
    }

    public StaticHologram() {}

    public void spawn() {
        this.location.setY((this.location.getY() + this.height)-1.25);
        hologram = (ArmorStand)this.location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        hologram.setCustomName(this.text);
        hologram.setCustomNameVisible(true);
        hologram.setGravity(false);
        hologram.setVisible(false);
    }

    public void spawnTemporary(int time){
        this.location.setY((this.location.getY() + this.height) -1.25);
        hologram = (ArmorStand) this.location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        hologram.setCustomName(this.text);
        hologram.setCustomNameVisible(true);
        hologram.setGravity(false);
        hologram.setVisible(false);

        Bukkit.getScheduler().runTaskLater(LobbyService.getInstance().getPlugin(), () -> remove(), time);
    }

    public void remove() {
        hologram.remove();
    }

    public void teleport(Location location) {
        hologram.teleport(location);
    }

    public void changeText(String text) {
        hologram.setCustomName(text);
    }
}

