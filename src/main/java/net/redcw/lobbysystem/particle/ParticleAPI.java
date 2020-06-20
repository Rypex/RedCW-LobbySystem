package net.redcw.lobbysystem.particle;

import org.bukkit.Location;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParticleAPI {

    private Map<String, Particle> cachedParticles;
    private final DisplayRing displayRing;

    public ParticleAPI() {
        this.cachedParticles = new ConcurrentHashMap<>();
        this.displayRing = new DisplayRing();
    }

    public ParticleData getData(ParticleType type, Location loc)
    {
        return new ParticleData(type, loc);
    }

    public ParticleData getData(ParticleType type)
    {
        return new ParticleData(type);
    }

    public void addParticle(String key, Particle value)
    {
        this.cachedParticles.put(key,value);
    }

    public Particle getParticle(String key)
    {
        return this.cachedParticles.get(key);
    }

    public boolean isParticleIdExists(String key)
    {
        return cachedParticles.containsKey(key);
    }

    public DisplayRing getDisplayRing() { return displayRing; }
}
