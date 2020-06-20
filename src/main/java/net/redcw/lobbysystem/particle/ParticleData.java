package net.redcw.lobbysystem.particle;

import org.bukkit.Location;

public final class ParticleData {

    private final ParticleType type;
    private Location location;
    private boolean async;

    protected ParticleData(ParticleType type, Location loc) {
        this.type = type;
        this.location = loc;
        this.async = false;
    }

    protected ParticleData(ParticleType type) {
        this.type = type;
        this.location = null;
        this.async = false;
    }

    protected ParticleData(ParticleType type, boolean async) {
        this.type = type;
        this.location = null;
        this.async = async;
    }

    protected ParticleData(ParticleType type, Location loc, boolean async) {
        this.type = type;
        this.location = loc;
        this.async = async;
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

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}

