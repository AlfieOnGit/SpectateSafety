package me.alfiejay.spectatesafety;

import me.alfiejay.spectatesafety.command.CommandManager;
import me.alfiejay.spectatesafety.config.ConfigManager;
import me.alfiejay.spectatesafety.listener.Listener;
import me.alfiejay.spectatesafety.message.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpectateSafety extends JavaPlugin {

    private Manager manager;
    private MessageManager messageManager;
    private ConfigManager configManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        manager = new Manager();
        messageManager = new MessageManager(this);
        configManager = new ConfigManager(this);
        commandManager = new CommandManager(this);
        getServer().getPluginManager().registerEvents(new Listener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Reload plugin config files
     */
    public void reload() {
        messageManager.reload();
        configManager.reload();
        commandManager.reload();
    }

    public Manager getManager() { return manager; }

    public ConfigManager getConfigManager() { return configManager; }
}
