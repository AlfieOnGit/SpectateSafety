package manaspectate;

import manaspectate.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static Handler handler;
    public static FileHandler fileHandler;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("spec")).setExecutor(new SpecCommand(this));
        Objects.requireNonNull(getCommand("unspec")).setExecutor(new UnspecCommand(this));
        Objects.requireNonNull(getCommand("specpoint")).setExecutor(new SpecPointCommand(this));
        Objects.requireNonNull(getCommand("unspecpoint")).setExecutor(new UnspecPointCommand(this));
        handler = new Handler();
        fileHandler = new FileHandler(this);
        new ListenerHandler(this);
        handler.loadSpecPoints();
    }

    @Override
    public void onDisable() {
        handler.unsetAllSpectator();
    }
}
