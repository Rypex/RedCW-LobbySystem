package net.redcw.lobbysystem.scheduled;

/*
Class created by SpigotSource on 16.01.2020 at 20:55
*/

import net.redcw.lobbysystem.LobbyService;
import net.redcw.lobbysystem.particle.Particle;
import net.redcw.redapi.RedAPI;
import net.redcw.redapi.bukkit.BukkitPositionAdapter;
import net.redcw.redapi.positions.Position;

public class ScheduledParticles implements Runnable {

    @Override
    public void run() {
        RedAPI.getInstance().getProcessQueue().runTask(() -> {
            final Particle particle = LobbyService.getInstance().getParticleAPI().getParticle("dailychestspawn");
            for(Position position : LobbyService.getInstance().getSpawnLocations().getPositions().values())
                LobbyService.getInstance().getParticleAPI().getDisplayRing().displayFlat(particle, BukkitPositionAdapter.toLocation(position).getFirst().clone(), 0, 0, 1);
        });

    }

}
