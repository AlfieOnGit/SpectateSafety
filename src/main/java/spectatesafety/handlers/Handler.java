package spectatesafety.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import spectatesafety.Main;
import spectatesafety.Spectator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Handler {

    private final ArrayList<Spectator> spectators = new ArrayList<>();

    private Location globalUnspecPoint;
    private HashMap<World, Location> specPoints = new HashMap<>(); // Global spec point stored at key "null"

    public Handler() { }

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
    public void unsetSpectator (Player player) {
        ArrayList<Spectator> hold = new ArrayList<>();
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) {
                s.unspectate();
                hold.add(s);
            }
        }
        spectators.removeIf(hold::contains);
    }

    /**
     * Puts all players in a group, not currently, spectating in spectate mode
     * @param group Target group
     * @return set of players affected
     */
    public Set<Player> setGroupSpectator (String group) {
        Set<Player> output = new HashSet<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (String g : Main.permission.getPlayerGroups(p)) {
                if (group.equals(g)) {
                    Main.handler.setSpectator(p);
                    output.add(p);
                    break;
                }
            }
        } return output;
    }

    /**
     * Puts all players not currently spectating in spectate mode
     * @return set of players affected
     */
    public Set<Player> setAllSpectator () {
        Set<Player> output = new HashSet<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Boolean feedback = setSpectator(p);
            if (feedback) output.add(p);
        } return output;
    }

    /**
     * Takes all players in a group, currently spectating, out of spectate mode
     * @param group Target group
     * @return set of players affected
     */
    public Set<Player> unsetGroupSpectator (String group) {
        Set<Player> output = new HashSet<>();
        ArrayList<Spectator> spectators = new ArrayList<>(Main.handler.getSpectators());
        for (Spectator s : spectators) {
            Player p = s.getPlayer();
            for (String g : Main.permission.getPlayerGroups(p)) {
                if (group.equals(g)) {
                    Main.handler.unsetSpectator(p);
                    output.add(p);
                    break;
                }
            }
        } return output;
    }

    /**
     * Takes all currently spectating players out of spectate mode
     * @return set of players affected
     */
    public Set<Player> unsetAllSpectator () {
        ArrayList<Spectator> hold = new ArrayList<>();
        for (Spectator s : spectators) {
            s.unspectate();
            hold.add(s);
        } spectators.removeIf(hold::contains);
        Set<Player> output = new HashSet<>();
        for (Spectator s : hold) output.add(s.getPlayer());
        return output;
    }

    /**
     * Sets the global spec point to the specified location
     * @param location Specified location
     */
    public void setGlobalSpecPoint(Location location) {
        this.specPoints.put(null, location);
        Main.specPointsHandler.saveSpecPoint(null, location);
    }

    public void setLocalSpecPoint(Location location, World world) {
        this.specPoints.put(world, location);
        Main.specPointsHandler.saveSpecPoint(world, location);
    }

    /**
     * Unsets the global spec point and deletes it from the save file
     * @return <code>true</code> if existing spec point deleted; <code>false</code> if no existing specpoint
     */
    public Boolean clearGlobalSpecPoint() {
        if (this.specPoints.get(null) == null) return false;
        else {
            this.specPoints.put(null, null);
            Main.specPointsHandler.saveSpecPoint(null, null);
            return true;
        }
    }

    /**
     * Unsets a local spec point and deletes it from the save file
     * @param world World of target local spec point
     * @return <code>true</code> if existing spec point deleted; <code>false</code> if no existing specpoint
     */
    public Boolean clearLocalSpecPoint(World world) {
        if (this.specPoints.get(world) == null) return false;
        else {
            this.specPoints.remove(world);
            Main.specPointsHandler.saveSpecPoint(world, null);
            return true;
        }
    }

    /**
     * Sets the unspec point to the specified location
     * @param location Specified location
     */
    public void setGlobalUnspecPoint(Location location) {
        this.globalUnspecPoint = location;
        Main.specPointsHandler.saveUnspecPoint(location);
    }

    /**
     * Unsets the unspec point and deletes it from the save file
     * @return <code>true</code> if existing unspec point deleted; <code>false</code> if no existing unspecpoint
     */
    public Boolean clearUnspecPoint () {
        if (this.globalUnspecPoint == null) return false;
        else {
            this.globalUnspecPoint = null;
            Main.specPointsHandler.clearUnspecPoint();
            return true;
        }
    }

    /**
     * Gets a Spectator object from their player
     * @param player player
     * @return spectator instance
     */
    public Spectator getSpectatorFromPlayer(Player player) {
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) return s;
        } return null;
    }

    /**
     * Loads the spec points from the save file
     */
    public void loadSpecPoints() {
        this.specPoints = Main.specPointsHandler.getSpecPoints();
        globalUnspecPoint = Main.specPointsHandler.getUnspecPoint();
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

    public ArrayList<Spectator> getSpectators () { return spectators; }

    public Location getGlobalSpecPoint() { return this.specPoints.get(null); }

    public Location getGlobalUnspecPoint() { return globalUnspecPoint; }
}
