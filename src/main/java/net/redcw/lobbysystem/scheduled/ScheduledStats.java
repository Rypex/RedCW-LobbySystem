package net.redcw.lobbysystem.scheduled;

/*
Class created by SpigotSource on 31.05.2020 at 14:10
*/

import net.redcw.lobbysystem.LobbyService;
import net.redcw.redapi.bukkit.BukkitPositionAdapter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ScheduledStats implements Runnable {

    @Override
    public void run() {
        final Location mlgRush = BukkitPositionAdapter.toLocation(LobbyService.getInstance().getSpawnLocations().getPosition("MLGRush")).getFirst();

        Bukkit.getOnlinePlayers().forEach(players -> {
            if(players.getLocation().distance(mlgRush) <= 6) {
                LobbyService.getInstance().getActionbar().sendActionBar(players, "§8» §3MLG§bRush §8● ");
            }
        });

    }

}
