package spectatesafety.handlers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SpecPointsConfig {

    private final File file;
    private FileConfiguration config;

    public SpecPointsConfig(Plugin plugin) {
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
     * Fetches the spec point from the specpoints.yml file
     * @return spec point location
     */
    public Location getSpecPoint() {
        if (config != null) {
            if (!config.contains("spec-point")) return null;
            return config.getLocation("spec-point");
        } else return null;
    }

    /**
     * Fetches the unspec point from the specpoints.yml file
     * @return unspec point location
     */
    public Location getUnspecPoint() {
        if (config != null) {
            if (!config.contains("unspec-point")) return null;
            return config.getLocation("unspec-point");
        } else return null;
    }

    /**
     * Saves specified location as the spec point in the specpoints.yml file
     * @param location specified location
     */
    public void saveSpecPoint(Location location) {
        if (file.exists()) {
            config.set("spec-point", location);
            save();
        }
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
     * Removes saved spec point location from the specpoints.yml file
     */
    public void clearSpecPoint() {
        if (file.exists()) {
            if (config.contains("spec-point")) {
                config.set("spec-point", null);
                save();
            }
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
