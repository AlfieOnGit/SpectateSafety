package spectatesafety;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Spectator {

    private final Player player;
    private final GameMode ogGamemode;
    private final Location originalLocation;

    public Spectator (Player player) {
        this.player = player;
        this.ogGamemode = player.getGameMode();
        this.originalLocation = player.getLocation();

        this.player.setGameMode(GameMode.SPECTATOR);

        Location specPoint = Main.handler.getSpecPoint();
        if (specPoint != null) this.player.teleport(specPoint);
    }

    /**
     * Takes target out of spectate mode. Deletes Spectator instance
     */
    public void unspectate () {
        this.player.setGameMode(this.ogGamemode);
        Location unspecPoint = Main.handler.getUnspecPoint();
        if (unspecPoint != null) this.player.teleport(unspecPoint);
        else this.player.teleport(this.originalLocation);
    }

    public Player getPlayer () {
        return this.player;
    }
}
