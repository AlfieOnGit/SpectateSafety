package spectatesafety;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Spectator {

    private final Player player;
    private final GameMode ogGamemode;
    private final Location originalLocation;

    private boolean locked;
    /* If a spectator is "locked", only players with spectatesafety.bypasslocks can unspec them */

    public Spectator (Player player, boolean locked) {
        this.player = player;
        this.ogGamemode = player.getGameMode();
        this.originalLocation = player.getLocation();
        this.locked = locked;

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

    /**
     * Sets whether a spectator is locked in spectate mode or not
     *
     * @param bool <code>true</code> to lock; <code>false</code> to unlock
     * @return <code>true</code> if successfully changed; <code>false</code> if spectator already locked
     */
    public boolean setLocked(boolean bool) {
        if (this.locked == bool) return false;
        else {
            this.locked = bool;
            return true;
        }
    }

    public Player getPlayer () {
        return this.player;
    }

    public boolean isLocked () { return this.locked; }
}
