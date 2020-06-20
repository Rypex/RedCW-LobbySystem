package net.redcw.lobbysystem.scheduled;

/*
Class created by SpigotSource on 15.01.2020 at 23:16
*/

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.bridge.internal.serverselectors.SignSelector;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.language.LanguageDefinition;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public final class ScheduledActionbar implements Runnable {

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private volatile int val = 0;

    @Override
    public void run()
    {
        for (Player all : Bukkit.getOnlinePlayers())
        {
            Block block = all.getTargetBlock((Set<Material>) null, 15);
            if (block.getType().equals(Material.SIGN_POST) || block.getType().equals(Material.WALL_SIGN))
                if (SignSelector.getInstance() != null)
                    for (de.dytanic.cloudnet.lib.serverselectors.sign.Sign sign : SignSelector.getInstance().getSigns().values())
                        if (sign.getPosition().equals(SignSelector.getInstance().toPosition(block.getLocation())))
                        {
                            if(RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(all.getUniqueId()).getLanguage().equals(LanguageDefinition.GERMAN)) {
                                if(sign.getServerInfo() != null)
                                {
                                    sendTitle(all, "§8●» §4" + sign.getServerInfo().getServiceId().getServerId() + " §8«●",

                                            "§8➜ §c" + sign.getServerInfo().getOnlineCount() + "§8/§c" + sign.getServerInfo().getMaxPlayers() + "§8│ §4" + sign.getServerInfo().getMotd());
                                }
                                else
                                {
                                    if(CloudAPI.getInstance().getServerGroupData(sign.getTargetGroup()).isMaintenance())
                                    {
                                        sendTitle(all, "§8× §4§lWARTUNG §8×", "");
                                        return;
                                    }

                                    sendTitle(all, "§8× §4Suchen §8×", "§8» §7Ein Server wird gesucht§8" + (val > 0 ? val == 1 ? "." : val == 2 ? ".." : "..." : ""));
                                }
                            } else {
                                if(sign.getServerInfo() != null)
                                {
                                    sendTitle(all, "§8●» §4" + sign.getServerInfo().getServiceId().getServerId() + " §8«●",
                                            "§8➜ §c" + sign.getServerInfo().getOnlineCount() + "§8/§c" + sign.getServerInfo().getMaxPlayers() + "§8│ §4" + sign.getServerInfo().getMotd());
                                }
                                else
                                {
                                    if(CloudAPI.getInstance().getServerGroupData(sign.getTargetGroup()).isMaintenance())
                                    {
                                        sendTitle(all, "§8× §4§lMAINTENANCE §8×", "");
                                        return;
                                    }

                                    sendTitle(all, "§8× §4Search §8×", "§8» §7search server§8" + (val > 0 ? val == 1 ? "." : val == 2 ? ".." : "..." : ""));
                                }
                            }
                            break;
                        }
            sendActionbar(all);
        }
        val++;

        if(val >= 4) val = 0;
    }

    private void sendActionbar(Player player)
    {
        if(RedAPI.getInstance().getRedPlayerProvider().getRedPlayer(player.getUniqueId()).getLanguage().equals(LanguageDefinition.GERMAN)) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(
                    "§8» §7Uhrzeit §8▰§7▰ §4" + dateFormat.format((System.currentTimeMillis() + TimeUnit.HOURS.toMillis(6))) + " §8● §7Spieler §8▰§7▰ §4" + CloudAPI.getInstance().getOnlineCount() + " §8«"
            ), (byte) 2));
        }else {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(
                    "§8» §7time §8▰§7▰ §4" + dateFormat.format((System.currentTimeMillis() + TimeUnit.HOURS.toMillis(6))) + " §8● §7online-count §8▰§7▰ §4" + CloudAPI.getInstance().getOnlineCount() + " §8«"
            ), (byte) 2));
        }
    }

    private void sendTitle(Player p, String message, String subMessage)
    {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(0, 15, 2));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(message)));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(subMessage)));
    }

}
