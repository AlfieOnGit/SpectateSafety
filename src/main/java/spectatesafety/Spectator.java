package spectatesafety;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Spectator {

    private final Player player;
    private final SpectateSafety plugin;
    private final GameMode ogGamemode;
    private final Location unspecLocation;

    public Spectator (Player player, SpectateSafety plugin) {
        this.player = player;
        this.plugin = plugin;
        this.ogGamemode = player.getGameMode();
        this.unspecLocation = getUnspecLocation();

        Location specPoint = this.getSpecPoint();
        if (specPoint != null) this.player.teleport(specPoint);

        this.player.setGameMode(GameMode.SPECTATOR);
    }

    /**
     * Takes target out of spectate mode. DOES NOT DELETE SPECTATOR INSTANCE
     */
    public void unspectate () {
        this.player.setGameMode(this.ogGamemode);
        this.player.teleport(unspecLocation);
    }

    public Player getPlayer () {
        return this.player;
    }

    /**
     * Get the spec point most relevant to player
     * @return specPoint or <code>null</code> if none
     */
    private Location getSpecPoint() {
        if (SpectateSafety.worldGuardHandler != null) {
            Location regionSpecPoint = SpectateSafety.worldGuardHandler.getSpecPoint(player);
            if (regionSpecPoint != null) return regionSpecPoint;
        }
        Location specPoint = plugin.getHandler().getSpecPoints().get(this.player.getWorld());
        if (specPoint != null) { return specPoint; }
        specPoint = plugin.getHandler().getSpecPoints().get(null);
        return specPoint;
    }

    private @NotNull Location getUnspecLocation() {
        Location out;
        if (SpectateSafety.worldGuardHandler != null) {
            out = SpectateSafety.worldGuardHandler.getUnspecPoint(player);
            if (out != null) return out;
        }
        out = plugin.getHandler().getUnspecPoints().get(this.player.getWorld());
        if (out != null) return out;
        out = plugin.getHandler().getUnspecPoints().get(null);
        if (out != null) return out;
        return player.getLocation();
    }
}
