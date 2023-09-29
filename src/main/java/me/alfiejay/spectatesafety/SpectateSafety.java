package me.alfiejay.spectatesafety;

import me.alfiejay.spectatesafety.message.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpectateSafety extends JavaPlugin {

    private MessageManager messageManager;

    @Override
    public void onEnable() {
        this.messageManager = new MessageManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Reload plugin config files
     */
    public void reload() {
        messageManager.load();
    }
}
