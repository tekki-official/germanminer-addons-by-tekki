package net.germanminer.assistant.blitzer;

/**
 * Repräsentiert einen gespeicherten Blitzer mit Koordinaten und Name.
 */
public class BlitzerEntry {

    private final String name;
    private final double x;
    private final double y;
    private final double z;
    private final String world;

    public BlitzerEntry(String name, double x, double y, double z, String world) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public String getName() { return name; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public String getWorld() { return world; }

    /**
     * Berechnet die horizontale Distanz vom Spieler zum Blitzer.
     */
    public double distanceTo(double px, double pz) {
        double dx = this.x - px;
        double dz = this.z - pz;
        return Math.sqrt(dx * dx + dz * dz);
    }

    @Override
    public String toString() {
        return name + " (" + (int)x + "/" + (int)y + "/" + (int)z + ")";
    }
}
