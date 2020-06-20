package net.redcw.lobbysystem.particle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DisplayRing {
    protected DisplayRing() {}

    public void asyncDisplayFlat(Plugin plugin, Particle particle, Location spawn, float speed, double high, double distance)
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            displayFlat(particle, spawn, speed, high, distance);
        });
    }

    public void asyncDisplayCosmic(Plugin plugin, Particle particle, Location spawn, float speed, double high, double distance)
    {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            displayCosmic(particle, spawn, speed, high, distance);
        });
    }

    private Location calc(Location pos, double high, double distance, int corner) //corner = winkel
    {
        return new Location(pos.getWorld(), pos.getX() * distance + (Math.sin(corner)), pos.getY() + high, pos.getZ() * distance + (Math.cos(corner)));
    }

    public void displayCosmic(Particle particle, Location spawn, float speed, double high, double distance)
    {
        spawn.setY(spawn.getY()+ high);
        Location dis = spawn.clone();

        particle.getData().setLocation(dis.clone().add(1F * distance, 1F, 0 * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(1F * distance, 1.05F, 0.1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(1F * distance, 1.1F, 0.2F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(1F * distance, 1.15F, 0.3F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.9F * distance, 1.2F, 0.3F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.9F * distance, 1.25F, 0.4F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.9F * distance, 1.3F, 0.5F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.8F * distance, 1.35F, 0.5F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.8F * distance, 1.35F, 0.6F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.7F * distance, 1.35F, 0.6F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.7F * distance, 1.35F, 0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.6F * distance, 1.35F, 0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.6F * distance, 1.35F, 0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.5F * distance, 1.35F, 0.8F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.5F * distance, 1.3F, 0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.4F * distance, 1.25F, 0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.3F * distance, 1.2F, 0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.3F * distance, 1.15F, 1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.2F * distance, 1.1F, 1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.1F * distance, 1.05F, 1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);

        particle.getData().setLocation(dis.clone().add(-1F * distance, 0, 0));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-1F * distance, 0.05F, 0.1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-1F * distance, 0.1F, 0.2F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-1F * distance, 0.15F, 0.3F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0.2F, 0.3F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0.25F, 0.4F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0.3F, 0.5F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.8F * distance, 0.35F, 0.5F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.8F * distance, 0.4F, 0.6F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.7F * distance, 0.45F, 0.6F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.7F * distance, 0.5F, 0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.6F * distance, 0.55F, 0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.6F * distance, 0.60F, 0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.5F * distance, 0.65F, 0.8F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.5F * distance, 0.70F, 0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.4F * distance, 0.75F, 0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.3F * distance, 0.80F, 0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.3F * distance, 0.85F, 1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.2F * distance, 0.90F, 1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.1F * distance, 0.95F, 1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.0F * distance, 1F, 1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);

        particle.getData().setLocation(dis.clone().add(1F * distance, 1F, 0));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(1F * distance, 0.95F, -0.1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(1F * distance, 0.9F, -0.2F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(1F * distance, 0.85F, -0.3F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.9F * distance, 0.8F, -0.3F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.9F * distance, 0.75F, -0.4F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.9F * distance, 0.70F, -0.5F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.8F * distance, 0.65F, -0.5F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.8F * distance, 0.6F, -0.6F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.7F * distance, 0.55F, -0.6F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.7F * distance, 0.5F, -0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.6F * distance, 0.45F, -0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.6F * distance, 0.4F, -0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.5F * distance, 0.35F, -0.8F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.5F * distance, 0.3F, -0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.4F * distance, 0.25F, -0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.3F * distance, 0.2F, -0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.3F * distance, 0.15F, -1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.2F * distance, 0.1F, -1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.1F * distance, 0.05F, -1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(0.0F * distance, 0, -1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);

        particle.getData().setLocation(dis.clone().add(-1F * distance, -0.05F, 0));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-1F * distance, -0.1F, -0.1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-1F * distance, -0.15F, -0.2F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-1F * distance, -0.2F, -0.3F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.9F * distance, -0.25F, -0.3F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.9F * distance, -0.3F, -0.4F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.9F * distance, -0.35F, -0.5F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.8F * distance, -0.4F, -0.5F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.8F * distance, -0.4F, -0.6F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.7F * distance, -0.4F, -0.6F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.7F * distance, -0.4F, -0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.6F * distance, -0.4F, -0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.6F * distance, -0.4F, -0.7F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.5F * distance, -0.4F, -0.8F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.5F * distance, -0.35F, -0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.4F * distance, -0.3F, -0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.3F * distance, -0.25F, -0.9F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.3F * distance, -0.2F, -1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.2F * distance, -0.15F, -1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.1F * distance, -0.1F, -1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
        particle.getData().setLocation(dis.clone().add(-0.0F * distance, -0.05F, -1F * distance));
        particle.display(true, 0, 0, 0, speed, 1);
    }

    public void displayFlat(Particle particle, Location spawn, float speed, double high, double distance)
    {
        for(Player all : Bukkit.getOnlinePlayers())displayFlat(all, particle, spawn, speed, high, distance);
    }

    public void displayFlat(Player player, Particle particle, Location spawn, float speed, double high, double distance)
    {
        for(int i = 0; i < 360; i += 4)
        {
            particle.getData().setLocation(calc(spawn, high, distance, i));
            particle.display(player, true, 0, 0, 0, speed, 1);
        }

        if(player.getLocation().distance(spawn) < 30)
        {
            spawn.setY(spawn.getY()+ high);
            Location dis = spawn.clone();
            particle.getData().setLocation(dis.clone().add(1F * distance, 0, 0 * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(1F * distance, 0, 0.1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(1F * distance, 0, 0.2F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(1F * distance, 0, 0.3F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.9F * distance, 0, 0.3F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.9F * distance, 0, 0.4F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.9F * distance, 0, 0.5F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.8F * distance, 0, 0.5F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.8F * distance, 0, 0.6F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.7F * distance, 0, 0.6F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.7F * distance, 0, 0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.6F * distance, 0, 0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.6F * distance, 0, 0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.5F * distance, 0, 0.8F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.5F * distance, 0, 0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.4F * distance, 0, 0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.3F * distance, 0, 0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.3F * distance, 0, 1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.2F * distance, 0, 1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.1F * distance, 0, 1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);

            particle.getData().setLocation(dis.clone().add(-1F * distance, 0, 0));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-1F * distance, 0, 0.1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-1F * distance, 0, 0.2F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-1F * distance, 0, 0.3F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0, 0.3F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0, 0.4F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0, 0.5F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.8F * distance, 0, 0.5F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.8F * distance, 0, 0.6F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.7F * distance, 0, 0.6F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.7F * distance, 0, 0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.6F * distance, 0, 0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.6F * distance, 0, 0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.5F * distance, 0, 0.8F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.5F * distance, 0, 0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.4F * distance, 0, 0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.3F * distance, 0, 0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.3F * distance, 0, 1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.2F * distance, 0, 1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.1F * distance, 0, 1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.0F * distance, 0, 1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);

            particle.getData().setLocation(dis.clone().add(1F * distance, 0, 0));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(1F * distance, 0, -0.1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(1F * distance, 0, -0.2F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(1F * distance, 0, -0.3F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.9F * distance, 0, -0.3F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.9F * distance, 0, -0.4F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.9F * distance, 0, -0.5F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.8F * distance, 0, -0.5F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.8F * distance, 0, -0.6F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.7F * distance, 0, -0.6F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.7F * distance, 0, -0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.6F * distance, 0, -0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.6F * distance, 0, -0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.5F * distance, 0, -0.8F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.5F * distance, 0, -0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.4F * distance, 0, -0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.3F * distance, 0, -0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.3F * distance, 0, -1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.2F * distance, 0, -1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.1F * distance, 0, -1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(0.0F * distance, 0, -1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);

            particle.getData().setLocation(dis.clone().add(-1F * distance, 0, 0));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-1F * distance, 0, -0.1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-1F * distance, 0, -0.2F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-1F * distance, 0, -0.3F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0, -0.3F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0, -0.4F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.9F * distance, 0, -0.5F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.8F * distance, 0, -0.5F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.8F * distance, 0, -0.6F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.7F * distance, 0, -0.6F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.7F * distance, 0, -0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.6F * distance, 0, -0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.6F * distance, 0, -0.7F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.5F * distance, 0, -0.8F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.5F * distance, 0, -0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.4F * distance, 0, -0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.3F * distance, 0, -0.9F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.3F * distance, 0, -1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.2F * distance, 0, -1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.1F * distance, 0, -1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
            particle.getData().setLocation(dis.clone().add(-0.0F * distance, 0, -1F * distance));
            particle.display(player, true, 0, 0, 0, speed, 1);
        }
    }

}

