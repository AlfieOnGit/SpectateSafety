package spectatesafety.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SpecPointsHandler {

    private final File file;
    private FileConfiguration config;

    public SpecPointsHandler(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        file = new File(plugin.getDataFolder(), "specpoints.yml");
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
            } catch (IOException ignored) { }
        }
    }

    /**
     * Saves the specpoints.yml file
     */
    private void save() {
        try {
            config.save(file);
        } catch (IOException ignored) { }
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
    public void saveUnspecPoint(Location location) {
        if (file.exists()) {
            config.set("unspec-point", location);
            save();
        }
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
