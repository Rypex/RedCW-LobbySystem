package net.redcw.lobbysystem.scheduled;

/*
Class created by SpigotSource on 05.01.2020 at 16:26
*/

import de.dytanic.cloudnet.lib.NetworkUtils;
import net.redcw.lobbysystem.LobbyService;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.BukkitPositionAdapter;
import net.redcw.redapi.positions.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ScheduledSpawnBlocks implements Runnable {

    @Override
    public void run() {
        RedAPI.getInstance().getProcessQueue().runTask(() -> {
            for(Player players : Bukkit.getOnlinePlayers()) {
                for (Position position : LobbyService.getInstance().getSpawnLocations().getPositions().values()) {
                    changeBlock(players, BukkitPositionAdapter.toLocation(position).getFirst().clone().add(0, -1, 0));
                }
            }
        });
    }

    private void changeBlock(final Player player, Location location) {
        player.sendBlockChange(location, Material.STAINED_CLAY, (byte) NetworkUtils.RANDOM.nextInt(16));
    }

}
