package me.alfiejay.spectatesafety;

import me.alfiejay.spectatesafety.command.Command;
import me.alfiejay.spectatesafety.command.SpecCommand;
import me.alfiejay.spectatesafety.command.UnspecCommand;
import me.alfiejay.spectatesafety.config.ConfigManager;
import me.alfiejay.spectatesafety.message.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class SpectateSafety extends JavaPlugin {

    private MessageManager messageManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.messageManager = new MessageManager(this);
        this.configManager = new ConfigManager(this);
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        registerCommand(new SpecCommand(this));
        registerCommand(new UnspecCommand(this));
    }

    private void registerCommand(@NotNull final Command command) {
        this.getServer().getCommandMap().register("spectatesafety", command);
    }

    /**
     * Reload plugin config files
     */
    public void reload() {
        messageManager.load();
    }

    public ConfigManager getConfigManager() { return configManager; }
}
