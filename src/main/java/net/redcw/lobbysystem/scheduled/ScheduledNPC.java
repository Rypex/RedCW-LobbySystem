package net.redcw.lobbysystem.scheduled;

/*
Class created by SpigotSource on 25.05.2020 at 18:22
*/

import net.redcw.lobbysystem.LobbyService;
import net.redcw.redapi.RedAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScheduledNPC implements Runnable {

    private List<Location> npcLocations;

    public ScheduledNPC() {
        npcLocations = new CopyOnWriteArrayList<>(LobbyService.getInstance().getNpcMap().keySet());
    }

    @Override
    public void run() {
        RedAPI.getInstance().getProcessQueue().runTask(() -> Bukkit.getOnlinePlayers().forEach(players -> {
            npcLocations.forEach(location -> {
                if(players.getLocation().distance(location) <= 1.6 && !players.getGameMode().equals(GameMode.CREATIVE)) {
                    players.setVelocity(players.getLocation().getDirection().multiply(-1.5));
                    Bukkit.getOnlinePlayers().forEach(playerList -> {
                        LobbyService.getInstance().getNpcMap().get(location).forceEmote(playerList, 37);
                    });

                }

            });

        }));

    }

}
