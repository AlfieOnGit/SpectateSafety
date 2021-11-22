package spectatesafety.handlers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class FileHandler {

    File file;
    FileConfiguration config;

    public FileHandler(Plugin plugin) {
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

    private void save() {
        try {
            config.save(file);
        } catch (IOException ignored) { }
    }

    public Location getSpecPoint() {
        if (config != null) {
            if (!config.contains("spec-point")) return null;
            return config.getLocation("spec-point");
        } else return null;
    }

    public Location getUnspecPoint() {
        if (config != null) {
            if (!config.contains("unspec-point")) return null;
            return config.getLocation("unspec-point");
        } else return null;
    }

    public void saveSpecPoint(Location location) {
        if (file.exists()) {
            config.set("spec-point", location);
            save();
        }
    }

    public void saveUnspecPoint(Location location) {
        if (file.exists()) {
            config.set("unspec-point", location);
            save();
        }
    }

    public void clearSpecPoint() {
        if (file.exists()) {
            if (config.contains("spec-point")) {
                config.set("spec-point", null);
                save();
            }
        }
    }

    public void clearUnspecPoint() {
        if (file.exists()) {
            if (config.contains("unspec-point")) {
                config.set("unspec-point", null);
                save();
            }
        }
    }
}
