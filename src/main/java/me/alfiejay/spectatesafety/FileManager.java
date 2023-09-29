package me.alfiejay.spectatesafety;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public abstract class FileManager {

    protected File file;

    private final Plugin plugin;
    private final String fileName;

    protected FileConfiguration config;

    public FileManager(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;

        if (!plugin.getDataFolder().exists()) {
            if (!plugin.getDataFolder().mkdir()) throw new RuntimeException("Could not create plugin data folder");
        }
        file = new File(plugin.getDataFolder(), fileName);
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            try {
                if (!file.createNewFile()) throw new RuntimeException("Could not create " + fileName);
                plugin.getLogger().info(Component.text(fileName + " created!").color(NamedTextColor.GREEN).content());
                config = YamlConfiguration.loadConfiguration(file);
                loadResource();
            } catch (IOException ignored) { }
        }
    }

    /**
     * Save configuration to yml file
     */
    public void save() {
        try {
            config.save(file);
        } catch (IOException ignored) { }
    }

    /**
     * Load resource file content into file
     */
    public void loadResource() {
        InputStream inputStream = plugin.getResource(fileName);

        try {
            assert inputStream != null;
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        config = YamlConfiguration.loadConfiguration(file);
    }
}