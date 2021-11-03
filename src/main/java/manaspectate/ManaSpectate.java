package manaspectate;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ManaSpectate extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("spec")).setExecutor(new SpecCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
