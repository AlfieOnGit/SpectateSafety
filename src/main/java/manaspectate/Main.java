package manaspectate;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static Handler handler;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("spec")).setExecutor(new CommandHandler());
        Objects.requireNonNull(getCommand("unspec")).setExecutor(new CommandHandler());
        handler = new Handler();
        new ListenerHandler(this);
    }

    @Override
    public void onDisable() {
        handler.unsetAllSpectator();
    }
}
