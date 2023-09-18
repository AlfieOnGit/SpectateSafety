package spectatesafety;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import spectatesafety.handlers.WorldGuardHandler;

public class Spectator {

    private final Player player;
    private final GameMode ogGamemode;
    private final Location originalLocation;
    private final SpectateSafety plugin;

    public Spectator (Player player, SpectateSafety plugin) {
        this.player = player;
        this.ogGamemode = player.getGameMode();
        this.originalLocation = player.getLocation();
        this.plugin = plugin;

        Location specPoint = this.getSpecPoint();
        if (specPoint != null) this.player.teleport(specPoint);

        this.player.setGameMode(GameMode.SPECTATOR);
    }

    /**
     * Takes target out of spectate mode. DOES NOT DELETE SPECTATOR INSTANCE
     */
    public void unspectate () {
        this.player.setGameMode(this.ogGamemode);
        Location unspecPoint = plugin.getHandler().getGlobalUnspecPoint();
        if (unspecPoint != null) this.player.teleport(unspecPoint);
        else this.player.teleport(this.originalLocation);
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
            Location regionUnspecPoint = SpectateSafety.worldGuardHandler.getSpecPoint(player);
            if (regionUnspecPoint != null) return regionUnspecPoint;
        }
        Location specPoint = plugin.getHandler().getSpecPoints().get(this.player.getWorld());
        if (specPoint != null) { return specPoint; }
        specPoint = plugin.getHandler().getSpecPoints().get(null);
        return specPoint;
    }
}
