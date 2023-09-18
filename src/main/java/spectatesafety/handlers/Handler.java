package spectatesafety.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import spectatesafety.SpectateSafety;
import spectatesafety.Spectator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class Handler {

    private final ArrayList<Spectator> spectators = new ArrayList<>();
    private final SpectateSafety plugin;

    private HashMap<World, Location> specPoints = new HashMap<>(); // Global spec point stored at key "null"

    private HashMap<World, Location> unspecPoints = new HashMap<>(); // Global unspec point stored at key "null"

    public Handler(SpectateSafety plugin) {
        this.plugin = plugin;
    }

    /**
     * Puts the specified player in spectate mode
     * @param player Target player
     */
    public Boolean setSpectator (Player player) {
        for (Spectator s : spectators) {
            if (s.getPlayer() == player) return false;
        } spectators.add(new Spectator(player, plugin));
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
            for (String g : SpectateSafety.permission.getPlayerGroups(p)) {
                if (group.equals(g)) {
                    setSpectator(p);
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
        ArrayList<Spectator> spectators = new ArrayList<>(getSpectators());
        for (Spectator s : spectators) {
            Player p = s.getPlayer();
            for (String g : SpectateSafety.permission.getPlayerGroups(p)) {
                if (group.equals(g)) {
                    unsetSpectator(p);
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
        plugin.getSpecPointsHandler().saveSpecPoint(null, location);
    }

    /**
     * Sets the unspec point to the specified location
     * @param location Specified location
     */
    public void setGlobalUnspecPoint(Location location) {
        this.unspecPoints.put(null, location);
        plugin.getSpecPointsHandler().saveUnspecPoint(null, location);
    }

    public void setLocalSpecPoint(Location location, World world) {
        this.specPoints.put(world, location);
        plugin.getSpecPointsHandler().saveSpecPoint(world, location);
    }

    public void setLocalUnspecPoint(Location location, World world) {
        this.unspecPoints.put(world, location);
        plugin.getSpecPointsHandler().saveUnspecPoint(world, location);
    }

    /**
     * Unsets the global spec point and deletes it from the save file
     * @return <code>true</code> if existing spec point deleted; <code>false</code> if no existing specpoint
     */
    public Boolean clearGlobalSpecPoint() {
        if (this.specPoints.get(null) == null) return false;
        else {
            this.specPoints.put(null, null);
            plugin.getSpecPointsHandler().saveSpecPoint(null, null);
            return true;
        }
    }

    /**
     * Unsets the global unspec point and deletes it from the save file
     * @return <code>true</code> if existing unspec point deleted; <code>false</code> if no existing unspec point
     */
    public Boolean clearGlobalUnspecPoint() {
        if (this.unspecPoints.get(null) == null) return false;
        else {
            this.unspecPoints.put(null, null);
            plugin.getSpecPointsHandler().saveUnspecPoint(null, null);
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
            plugin.getSpecPointsHandler().saveSpecPoint(world, null);
            return true;
        }
    }

    /**
     * Unsets a local unspec point and deletes it from the save file
     * @param world World of target local unspec point
     * @return <code>true</code> if existing unspec point deleted; <code>false</code> if no existing unspec point
     */
    public Boolean clearLocalUnspecPoint(World world) {
        if (this.unspecPoints.get(world) == null) return false;
        else {
            this.unspecPoints.remove(world);
            plugin.getSpecPointsHandler().saveUnspecPoint(world, null);
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
     * Loads the spec points and unspec points from the save file
     */
    public void loadSpecPoints() {
        this.specPoints = plugin.getSpecPointsHandler().getSpecPoints();
        this.unspecPoints = plugin.getSpecPointsHandler().getUnspecPoints();
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

    public HashMap<World, Location> getSpecPoints() { return this.specPoints; }

    public HashMap<World, Location> getUnspecPoints() { return this.unspecPoints; }
}
