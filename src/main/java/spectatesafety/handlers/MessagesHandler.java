package spectatesafety.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public class MessagesHandler {

    private final File file;
    private FileConfiguration config;
    private final String tag;

    public MessagesHandler(Plugin plugin) {
        file = new File(plugin.getDataFolder(), "messages.yml");
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            try {
                file.createNewFile();
                loadFile(plugin);
                config = YamlConfiguration.loadConfiguration(file);
            } catch (IOException ignored) { }
        } tag = config.getString("tag");
    }

    private void loadFile(Plugin plugin) {
        InputStream inputStream = plugin.getResource("messages.yml");

        try {
            assert inputStream != null;
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches a message in the message.yml file from the input path
     * @param path message path
     * @return formatted message
     */
    public String get(String path) {
        return ChatColor.translateAlternateColorCodes('&', tag + " " + config.getString(path));
    }
}
