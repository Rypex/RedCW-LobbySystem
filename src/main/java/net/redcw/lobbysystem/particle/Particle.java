package net.redcw.lobbysystem.particle;

import net.redcw.lobbysystem.particle.protocol.ProtocolManager;
import net.redcw.lobbysystem.particle.protocol.packets.ProtocolPacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Particle {

    private final ParticleData data;

    public Particle(ParticleData component) {
        this.data = component;
    }

    public void display(Player player, boolean longdistance, float offsetx, float offsety, float offsetz, float speed, int amout) {
        if (player.getLocation().distance(data.getLocation()) < 32) {
            ProtocolPacketPlayOutWorldParticles partcles = new ProtocolPacketPlayOutWorldParticles(data.getLocation(), data.getType());
            partcles.setOffSetX(offsetx);
            partcles.setOffSetY(offsety);
            partcles.setOffSetZ(offsetz);
            partcles.setSpeed(speed);
            partcles.setAmout(amout);
            ProtocolManager.sendPacket(partcles, player);
        }
    }

    public void display(Player player, Location c, boolean longdistance, float offsetx, float offsety, float offsetz, float speed, int amout) {
        if (player.getLocation().distance(c) < 32) {
            ProtocolPacketPlayOutWorldParticles partcles = new ProtocolPacketPlayOutWorldParticles(c, data.getType());
            partcles.setOffSetX(offsetx);
            partcles.setOffSetY(offsety);
            partcles.setOffSetZ(offsetz);
            partcles.setSpeed(speed);
            partcles.setAmout(amout);
            ProtocolManager.sendPacket(partcles, player);
        }
    }

    public void display(boolean longdistance, float offsetx, float offsety, float offsetz, float speed, int amout) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            display(all, longdistance, offsetx, offsety, offsetz, speed, amout);
        }
    }

    public void display(Location c, boolean longdistance, float offsetx, float offsety, float offsetz, float speed, int amout) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            display(all, c, longdistance, offsetx, offsety, offsetz, speed, amout);
        }
    }

    public ParticleData getData() {
        return data;
    }

}
