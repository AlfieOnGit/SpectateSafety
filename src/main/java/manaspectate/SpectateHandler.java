package manaspectate;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpectateHandler {

    private final ArrayList<Spectator> spectators;

    public SpectateHandler () {
        this.spectators = new ArrayList<>();
    }

    public void setSpectator (Player player) {
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) return;
        } spectators.add(new Spectator(player));
    }

    public void unsetSpectator (Player player) {
        ArrayList<Spectator> hold = new ArrayList<>();
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) {
                s.unspectate();
                hold.add(s);
            }
        } spectators.removeIf(hold::contains);
    }
}
