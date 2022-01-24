package spectatesafety.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

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
     * Fetches the global spec point from the specpoints.yml file
     * @return spec point location
     */
    public Location getSpecPoint() {
        if (config == null) return null;
        if (!config.contains("spec-point")) return null;
        return config.getLocation("spec-point");
    }

    /**
     * Fetches a specified world's spec point from the specpoints.yml file
     * @param world specified world
     * @return spec point location
     */
    public Location getSpecPoint(World world) {
        if (config == null) return null;
        String path = "spec-point-" + world.getName();
        if (!config.contains(path)) return null;
        return config.getLocation(path);
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
     * @param location specified location
     * @param world specified world
     */
    public void saveSpecPoint(@Nullable Location location, @Nullable World world) {
        Bukkit.getLogger().info("TEST 1");
        if (!file.exists()) return;
        if (world == null) config.set("spec-point", location);
        else {
            config.set("spec-point-" + world.getName(), location);
            Bukkit.getLogger().info("TEST 2");
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
