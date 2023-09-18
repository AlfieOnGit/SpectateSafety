package spectatesafety.handlers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public final class MessagesHandler extends FileHandler {

    public MessagesHandler(Plugin plugin) {
        super(plugin, "messages.yml");
        loadResource();
    }

    /**
     * Fetches a message in the message.yml file from the input path
     * @param path message path
     * @return formatted message
     */
    public String get(String path) {
        return config.getString(path);
    }

    /**
     * Fetches all custom placeholders in the message.yml file
     * @return hashmap of placeholders to their replacement text
     */
    public HashMap<String, String> getCustomPlaceholders() {
        HashMap<String, String> output = new HashMap<>();
        Set<String> keys = Objects.requireNonNull(config.getConfigurationSection("custom-placeholders")).getKeys(false);
        for (String key : keys) {
            String placeholder = Objects.requireNonNull(config.getString("custom-placeholders." + key));
            output.put("{" + key.toUpperCase() + "}", ChatColor.translateAlternateColorCodes('&', placeholder));
        } return output;
    }
}
