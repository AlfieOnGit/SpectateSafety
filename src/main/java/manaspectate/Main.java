package manaspectate;

import manaspectate.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static Handler handler;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("spec")).setExecutor(new SpecCommand());
        Objects.requireNonNull(getCommand("unspec")).setExecutor(new UnspecCommand());
        Objects.requireNonNull(getCommand("specpoint")).setExecutor(new SpecPointCommand(this));
        Objects.requireNonNull(getCommand("unspecpoint")).setExecutor(new UnspecPointCommand());
        handler = new Handler();
        new ListenerHandler(this);
    }

    @Override
    public void onDisable() {
        handler.unsetAllSpectator();
    }
}
