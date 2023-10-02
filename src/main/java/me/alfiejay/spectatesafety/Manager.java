package me.alfiejay.spectatesafety;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public final class Manager {

    private @NotNull final Set<Spectator> spectators = new HashSet<>();

    /**
     * Put a player into spectate mode
     * @param player Player instance
     * @return TRUE if successful, FALSE if Player already in spectate mode
     */
    public boolean putInSpectate(@NotNull final Player player) {
        if (getSpectator(player) != null) return false;
        spectators.add(new Spectator(player));
        return true;
    }

    /**
     * Take a player out of spectate mode
     * @param player Player instance
     * @return TRUE if successful, FALSE if Player not in spectate mode
     */
    public boolean takeOutOfSpectate(@NotNull final Player player) {
        Spectator s = getSpectator(player);
        if (s == null) return false;
        s.unspectate();
        spectators.remove(s);
        return true;
    }

    /**
     * Get spectator instance from player
     * @param player Player instance
     * @return Spectator instance or NULL if not found
     */
    private @Nullable Spectator getSpectator(@NotNull final Player player) {
        for (Spectator s : spectators) if (s.getPlayer() == player) return s;
        return null;
    }

    /**
     * Check if player is in spectate mode
     * @param player Player instance
     * @return TRUE if player in spectate mode, FALSE if not
     */
    public boolean isInSpec(@NotNull final Player player) {
        for (Spectator s : spectators) if (s.getPlayer() == player) return true;
        return false;
    }
}
