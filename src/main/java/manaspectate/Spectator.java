package manaspectate;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Spectator {

    private final Player player;
    private final GameMode ogGamemode;
    private final Location ogLocation;

    public Spectator (Player player) {
        this.player = player;
        this.ogGamemode = player.getGameMode();
        this.ogLocation = player.getLocation();

        this.player.setGameMode(GameMode.SPECTATOR);

        Location specPoint = Main.handler.getSpecPoint();
        if (specPoint != null) this.player.teleport(specPoint);
    }

    public void unspectate () {
        this.player.setGameMode(this.ogGamemode);
        this.player.teleport(this.ogLocation);
    }

    public Player getPlayer () {
        return this.player;
    }
}
