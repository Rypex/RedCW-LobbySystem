package net.redcw.lobbysystem;

/*
Class created by SpigotSource on 04.01.2020 at 22:42
*/

import de.dytanic.cloudnet.bridge.CloudServer;
import net.redcw.lobbysystem.internal.commands.SetupCommand;
import net.redcw.lobbysystem.scheduled.ScheduledActionbar;
import net.redcw.lobbysystem.scheduled.ScheduledParticles;
import net.redcw.lobbysystem.scheduled.ScheduledSpawnBlocks;
import net.redcw.redapi.RedAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbySystem extends JavaPlugin {

    @Override
    public void onEnable() {

        new LobbyService(this);

        getCommand("setup").setExecutor(new SetupCommand());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "CloudNet");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(players -> {
            players.kickPlayer(RedAPI.PREFIX + "§cDer Server wird neu gestartet!");
        });

        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getScheduler().cancelTasks(this);
        getServer().getScheduler().cancelAllTasks();
    }

}
