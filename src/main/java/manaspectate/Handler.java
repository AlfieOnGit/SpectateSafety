package manaspectate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Handler {

    private final ArrayList<Spectator> spectators;

    private Location specPoint, unspecPoint;

    public Handler() {
        this.spectators = new ArrayList<>();
    }

    public Boolean setSpectator (Player player) {
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) return false;
        } spectators.add(new Spectator(player));
        return true;
    }

    public Boolean unsetSpectator (Player player) {
        ArrayList<Spectator> hold = new ArrayList<>();
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) {
                s.unspectate();
                hold.add(s);
            }
        } if (hold.isEmpty()) return false;
        spectators.removeIf(hold::contains);
        return true;
    }

    public Integer setAllSpectator () {
        Integer output = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            Boolean feedback = setSpectator(p);
            if (feedback) output++;
        } return output;
    }

    public Integer unsetAllSpectator () {
        ArrayList<Spectator> hold = new ArrayList<>();
        for (Spectator s : spectators) {
            s.unspectate();
            hold.add(s);
        } spectators.removeIf(hold::contains);
        return hold.toArray().length;
    }

    public void setSpecPoint (Location specPoint) {
        this.specPoint = specPoint;
    }

    public Boolean clearSpecPoint () {
        if (this.specPoint != null) {
            this.specPoint = null;
            return true;
        } else return false;
    }

    public void setUnspecPoint (Location unspecPoint) {
        this.unspecPoint = unspecPoint;
    }

    public Boolean clearUnspecPoint () {
        if (this.unspecPoint != null) {
            this.unspecPoint = null;
            return true;
        } else return false;
    }

    public Location getSpecPoint () {
        return specPoint;
    }

    public Location getUnspecPoint () {
        return unspecPoint;
    }
}
