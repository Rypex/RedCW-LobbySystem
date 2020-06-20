package net.redcw.lobbysystem.particle.protocol.packets;

import net.redcw.lobbysystem.particle.ParticleType;
import net.redcw.lobbysystem.particle.protocol.ProtocolManager;
import net.redcw.lobbysystem.particle.protocol.ProtocolPacket;
import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;

public class ProtocolPacketPlayOutWorldParticles implements ProtocolPacket {

    private Object instance;
    private Class<?> classbyPacket;
    private String name;
    private Location location;
    private Enum<?> particleEnum;
    private final ParticleType type;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ProtocolPacketPlayOutWorldParticles(Location loc, ParticleType type) {

        this.location = loc;
        this.type = type;
        this.name = "PacketPlayOutWorldParticles";
        this.classbyPacket = ProtocolManager.getInstance().getMinecraftServerInstance("PacketPlayOutWorldParticles");
        this.particleEnum = Enum.valueOf((Class<Enum>) ProtocolManager.getInstance().getMinecraftServerInstance("EnumParticle"), type.toString());
        try
        {
            this.instance = classbyPacket.getConstructor(new Class<?>[0]).newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e)
        {
            e.printStackTrace();
        }

        ProtocolManager.getInstance().setField(instance, instance.getClass(), "a", particleEnum);
        ProtocolManager.getInstance().setField(instance, instance.getClass(), "b", (float)location.getX());
        ProtocolManager.getInstance().setField(instance, instance.getClass(), "c", (float)location.getY());
        ProtocolManager.getInstance().setField(instance, instance.getClass(), "d", (float)location.getZ());

    }

    public void setOffSetX(float offsetx)
    {
        ProtocolManager.getInstance().setField(instance, instance.getClass(), "e", offsetx);
    }

    public void setOffSetY(float offsety)
    {
        ProtocolManager.getInstance().setField(instance, instance.getClass(), "f", offsety);
    }

    public void setOffSetZ(float offsetz)
    {
        ProtocolManager.getInstance().setField(instance, instance.getClass(), "g", offsetz);
    }

    public void setSpeed(float speed)
    {
        ProtocolManager.getInstance().setField(instance, instance.getClass(), "h", speed);
    }

    public void setAmout(int amout)
    {
        ProtocolManager.getInstance().setField(instance, instance.getClass(), "i", amout);
    }

    public Object getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClassbyPacket() {
        return classbyPacket;
    }

    public ParticleType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Enum<?> getParticleEnum() {
        return particleEnum;
    }
}

