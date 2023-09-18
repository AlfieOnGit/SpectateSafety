package spectatesafety.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.HashMap;

public final class SpecPointsHandler extends FileHandler {

    public SpecPointsHandler(Plugin plugin) {
        super(plugin, "specpoints.yml");
    }

    /**
     * Fetches all spec points from the specpoints.yml file
     * @return A hashmap of all specpoints
     */
    public HashMap<World, Location> getSpecPoints() {
        if (config == null) return null;
        HashMap<World, Location> output = new HashMap<>();
        if (config.contains("spec-point")) {
            output.put(null, config.getLocation("spec-point"));
        }
        for (String path : config.getKeys(false)) {
            if (path.startsWith("spec-point-")) {
                World world = Bukkit.getWorld(path.substring(11));
                if (world != null) {
                    output.put(world, config.getLocation(path));
                } else {
                    Bukkit.getLogger().info(ChatColor.RED + "[ERROR] INVALID WORLD " + path.substring(11) + " SAVED IN SPECPOINTS.YML");
                }
            }
        } return output;
    }

    /**
     * Fetches all unspec points from the specpoints.yml file
     * @return A hashmap of all unspecpoints
     */
    public HashMap<World, Location> getUnspecPoints() {
        if (config == null) return null;
        HashMap<World, Location> output = new HashMap<>();
        if (config.contains("unspec-point")) {
            output.put(null, config.getLocation("unspec-path"));
        }
        for (String path : config.getKeys(false)) {
            if (path.startsWith("unspec-point-")) {
                World world = Bukkit.getWorld(path.substring(13));
                if (world != null) {
                    output.put(world, config.getLocation(path));
                } else {
                    Bukkit.getLogger().info(ChatColor.RED + "[ERROR] INVALID WORLD " + path.substring(13) + " SAVED IN SPECPOINTS.YML");
                }
            }
        } return output;
    }

    /**
     * Fetches the global unspec point from the specpoints.yml file
     * @return unspec point location
     */
    public Location getUnspecPoint() {
        if (config != null) {
            if (!config.contains("unspec-point")) return null;
            return config.getLocation("unspec-point");
        } else return null;
    }

    /**
     * Saves specified location as the specified world's spec point in the specpoints.yml file. If
     * location is Null then it'll clear the location. If world is null, it'll apply it as the global
     * spec point
     * @param world specified world
     * @param location specified location
     */
    public void saveSpecPoint(@Nullable World world, @Nullable Location location) {
        if (!file.exists()) return;
        if (world == null) config.set("spec-point", location);
        else {
            config.set("spec-point-" + world.getName(), location);
        }
        save();
    }

    /**
     * Saves the specified location as the unspec point in the specpoints.yml file
     * @param location specified location
     */
    public void saveUnspecPoint(World world, Location location) {
        if (!file.exists()) return;
        if (world == null) config.set("unspec-point", location);
        else {
            config.set("unspec-point-" + world.getName(), location);
        }
        save();
    }

    /**
     * Removes saved unspec point location from the specpoints.yml file
     */
    public void clearUnspecPoint() {
        if (file.exists()) {
            if (config.contains("unspec-point")) {
                config.set("unspec-point", null);
                save();
            }
        }
    }
}
