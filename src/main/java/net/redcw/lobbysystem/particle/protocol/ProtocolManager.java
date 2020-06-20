package net.redcw.lobbysystem.particle.protocol;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ProtocolManager {

    private ProtocolManager() {}

    private static ProtocolAPI instance = new ProtocolAPI();

    public ProtocolPacket createPacket(String name, Class<?>[] clazzes, Object...instance)
    {
        ProtocolPacket  packet = new ProtocolPacket()
        {

            private final Class<?> claZ = ProtocolManager.getInstance().getMinecraftServerInstance(name);

            @Override
            public String getName()
            {
                return name;
            }

            @Override
            public Object getInstance()
            {
                try{
                    Constructor<?> cons = claZ.getConstructor(clazzes);
                    return cons.newInstance(instance);
                }catch(Exception ex)
                {
                    ex.getStackTrace();
                    return null;
                }
            }

            @Override
            public Class<?> getClassbyPacket()
            {
                return claZ;
            }
        };
        return packet;
    }

    public static void sendAllPacket(ProtocolPacket packet)
    {
        for(Player all : Bukkit.getOnlinePlayers())
        {
            sendPacket(packet, all);
        }
    }

    public static void sendAllPacket(Object packet)
    {
        for(Player all : Bukkit.getOnlinePlayers())
        {
            sendPacket(packet, all);
        }
    }

    public static void sendPacket(Object packet, Player player)
    {
        ProtocolAPI protocolAPI = getInstance();
        try{
            Class<?> typeNMSPlayer = protocolAPI.getMinecraftServerInstance("EntityPlayer");
            Class<?> typeCraftPlayer = protocolAPI.getCraftInstance("entity.CraftPlayer");
            Class<?> typePlayerConnection = protocolAPI.getMinecraftServerInstance("PlayerConnection");
            Method getHandle = typeCraftPlayer.getMethod("getHandle", new Class[0]);
            Field playerConnection = typeNMSPlayer.getField("playerConnection");
            Method sendPacket = typePlayerConnection.getMethod("sendPacket", new Class[] { protocolAPI.getMinecraftServerInstance("Packet") });
            Object entityPlayer = getHandle.invoke(player, new Object[0]);
            Object connection = playerConnection.get(entityPlayer);
            sendPacket.invoke(connection, packet);
        }catch(Exception ex)
        {
            ex.getStackTrace();
        }
    }

    public static void sendPacket(ProtocolPacket packet, Player player)
    {
        ProtocolAPI protocolAPI = getInstance();
        try{
            Class<?> typeNMSPlayer = protocolAPI.getMinecraftServerInstance("EntityPlayer");
            Class<?> typeCraftPlayer = protocolAPI.getCraftInstance("entity.CraftPlayer");
            Class<?> typePlayerConnection = protocolAPI.getMinecraftServerInstance("PlayerConnection");
            Method getHandle = typeCraftPlayer.getMethod("getHandle", new Class[0]);
            Field playerConnection = typeNMSPlayer.getField("playerConnection");
            Method sendPacket = typePlayerConnection.getMethod("sendPacket", new Class[] { protocolAPI.getMinecraftServerInstance("Packet") });
            Object entityPlayer = getHandle.invoke(player, new Object[0]);
            Object connection = playerConnection.get(entityPlayer);
            sendPacket.invoke(connection, packet.getInstance());
        }catch(Exception ex)
        {
            ex.getStackTrace();
        }
    }

    public static ProtocolAPI getInstance() {
        return instance;
    }
}

