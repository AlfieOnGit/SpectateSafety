package manaspectate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Handler {

    private final ArrayList<Spectator> spectators;

    public Handler() {
        this.spectators = new ArrayList<>();
    }

    public Feedback setSpectator (Player player) {
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) return Feedback.ALREADY_SPEC;
        } spectators.add(new Spectator(player));
        return Feedback.SUCCESS;
    }

    public Feedback unsetSpectator (Player player) {
        ArrayList<Spectator> hold = new ArrayList<>();
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) {
                s.unspectate();
                hold.add(s);
            }
        } if (hold.isEmpty()) return Feedback.ALREADY_UNSPEC;
        spectators.removeIf(hold::contains);
        return Feedback.SUCCESS;
    }

    public void setAllSpectator () {
        for (Player p : Bukkit.getOnlinePlayers()) {
            setSpectator(p);
        }
    }

    public void unsetAllSpectator () {
        ArrayList<Spectator> hold = new ArrayList<>();
        for (Spectator s : spectators) {
            s.unspectate();
            hold.add(s);
        } spectators.removeIf(hold::contains);
    }
}
