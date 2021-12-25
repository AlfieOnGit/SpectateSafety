package spectatesafety.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import spectatesafety.Main;
import spectatesafety.Spectator;

import java.util.ArrayList;

public class Handler {

    private final ArrayList<Spectator> spectators;

    private Location specPoint, unspecPoint;

    public Handler() {
        this.spectators = new ArrayList<>();
    }

    /**
     * Puts the specified player in spectate mode
     * @param player Target player
     */
    public Boolean setSpectator (Player player) {
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) return false;
        } spectators.add(new Spectator(player));
        return true;
    }

    /**
     * Takes the specified player out of spectate mode
     * @param player Target player
     */
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

    /**
     * Puts all players not currently spectating in spectate mode
     */
    public Integer setAllSpectator () {
        Integer output = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            Boolean feedback = setSpectator(p);
            if (feedback) output++;
        } return output;
    }

    /**
     * Takes all currently spectating players out of spectate mode
     */
    public Integer unsetAllSpectator () {
        ArrayList<Spectator> hold = new ArrayList<>();
        for (Spectator s : spectators) {
            s.unspectate();
            hold.add(s);
        } spectators.removeIf(hold::contains);
        return hold.toArray().length;
    }

    /**
     * Sets the spec point to the specified location
     * @param location Specified location
     */
    public void setSpecPoint (Location location) {
        this.specPoint = location;
        Main.fileHandler.saveSpecPoint(location);
    }

    /**
     * Unsets the spec point and deletes it from the save file
     * @return <code>true</code> if successful; <code>false</code> if there was no spec point
     */
    public Boolean clearSpecPoint () {
        if (this.specPoint != null) {
            this.specPoint = null;
            Main.fileHandler.clearSpecPoint();
            return true;
        } else return false;
    }

    /**
     * Sets the unspec point to the specified location
     * @param location Specified location
     */
    public void setUnspecPoint (Location location) {
        this.unspecPoint = location;
        Main.fileHandler.saveUnspecPoint(location);
    }

    /**
     * Unsets the unspec point and deletes it from the save file
     * @return <code>true</code> if successful; <code>false</code> if there was no unspec point
     */
    public Boolean clearUnspecPoint () {
        if (this.unspecPoint != null) {
            this.unspecPoint = null;
            Main.fileHandler.clearUnspecPoint();
            return true;
        } else return false;
    }

    /**
     * Loads the spec points from the save file
     */
    public void loadSpecPoints() {
        specPoint = Main.fileHandler.getSpecPoint();
        unspecPoint = Main.fileHandler.getUnspecPoint();
    }

    /**
     * Checks whether a player is in spectate mode or not
     * @param player target of check
     * @return <code>true</code> if player is in spectate mode; <code>false</code> if they're not
     */
    public Boolean checkStatus (Player player) {
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) {
                return true;
            }
        } return false;
    }

    public Location getSpecPoint () {
        return specPoint;
    }

    public Location getUnspecPoint () {
        return unspecPoint;
    }
}
