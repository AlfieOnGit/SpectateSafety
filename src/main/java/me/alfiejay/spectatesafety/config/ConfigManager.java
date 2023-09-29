package me.alfiejay.spectatesafety.config;

import me.alfiejay.spectatesafety.FileManager;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ConfigManager extends FileManager {

    public ConfigManager(@NotNull final Plugin plugin) {
        super(plugin, "config.yml");
    }

    public @NotNull List<String> getAliases(@NotNull final String commandName) {
        return config.getStringList("aliases." + commandName);
    }
}
