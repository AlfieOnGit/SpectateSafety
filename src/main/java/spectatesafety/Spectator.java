package spectatesafety;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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

        this.player.setGameMode(GameMode.SPECTATOR);

        Location specPoint = plugin.getHandler().getGlobalSpecPoint();
        if (specPoint != null) this.player.teleport(specPoint);
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
}
