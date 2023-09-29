package me.alfiejay.spectatesafety;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public abstract class FileHandler {

    protected File file;

    private final Plugin plugin;
    private final String fileName;

    protected FileConfiguration config;

    public FileHandler(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        file = new File(plugin.getDataFolder(), fileName);
        if (file.exists()) {
            config = YamlConfiguration.loadConfiguration(file);
        } else {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
            } catch (IOException ignored) { }
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException ignored) { }
    }

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