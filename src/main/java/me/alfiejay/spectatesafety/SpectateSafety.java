package me.alfiejay.spectatesafety;

import me.alfiejay.spectatesafety.command.SpecCommand;
import me.alfiejay.spectatesafety.command.UnspecCommand;
import me.alfiejay.spectatesafety.config.ConfigManager;
import me.alfiejay.spectatesafety.message.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

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
        this.getServer().getCommandMap().register("spectatesafety", new SpecCommand(this));
        this.getServer().getCommandMap().register("spectatesafety", new UnspecCommand(this));
    }

    /**
     * Reload plugin config files
     */
    public void reload() {
        messageManager.load();
    }

    public ConfigManager getConfigManager() { return configManager; }
}
