package me.alfiejay.spectatesafety;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Spectator {

    private @NotNull final Player player;
    private @NotNull final GameMode ogGamemode;
    private @NotNull final Location unspecLoc;

    public Spectator(@NotNull final Player player) {
        this.player = player;
        ogGamemode = player.getGameMode();
        unspecLoc = player.getLocation();

        player.setGameMode(GameMode.SPECTATOR);
    }

    /**
     * Take player out of spectate mode
     */
    public void unspectate() {
        player.setGameMode(ogGamemode);
        player.teleport(unspecLoc);
    }

    public @NotNull Player getPlayer() { return player; }
}
