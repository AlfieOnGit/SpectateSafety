package me.alfiejay.spectatesafety;

import me.alfiejay.spectatesafety.message.Message;
import me.alfiejay.spectatesafety.message.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpectateSafety extends JavaPlugin {

    private MessageManager messageManager;

    @Override
    public void onEnable() {
        this.messageManager = new MessageManager(this);
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
