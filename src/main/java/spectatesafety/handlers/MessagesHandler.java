package spectatesafety.handlers;

import org.bukkit.Bukkit;
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

    public MessagesHandler(Plugin plugin) {
        file = new File(plugin.getDataFolder(), "messages.yml");
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
                loadFile(plugin);
            } catch (IOException ignored) { }
        }
    }

    private void loadFile(Plugin plugin) {
        Bukkit.getLogger().info("LOAD FILE CALLED");
        InputStream inputStream = plugin.getResource("messages.yml");

        try {
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
