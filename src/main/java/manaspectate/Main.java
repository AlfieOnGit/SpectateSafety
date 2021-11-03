package manaspectate;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static SpectateHandler handler;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("spec")).setExecutor(new CommandHandler());
        Objects.requireNonNull(getCommand("unspec")).setExecutor(new CommandHandler());
        handler = new SpectateHandler();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
