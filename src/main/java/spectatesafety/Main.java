package spectatesafety;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import spectatesafety.commands.*;
import spectatesafety.handlers.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    public static Handler handler;
    public static SpecPointsHandler specPointsHandler;
    public static MessagesHandler messagesHandler;
    public static Permission permission;
    private static Main plugin;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("spec")).setExecutor(new SpecCommand(this));
        Objects.requireNonNull(getCommand("unspec")).setExecutor(new UnspecCommand(this));
        Objects.requireNonNull(getCommand("specpoint")).setExecutor(new SpecPointCommand(this));
        Objects.requireNonNull(getCommand("unspecpoint")).setExecutor(new UnspecPointCommand(this));

        handler = new Handler();
        specPointsHandler = new SpecPointsHandler(this);
        messagesHandler = new MessagesHandler(this);

        new ListenerHandler(this);
        handler.loadSpecPoints();

        permission = getPermissions();

        plugin = this;
    }

    @Override
    public void onDisable() {
        handler.unsetAllSpectator();
    }

    private Permission getPermissions() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return null;
        } else {
            RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
            if (rsp == null) return null;
            else return rsp.getProvider();
        }
    }

    /**
     * Reloads the plugin's configs
     */
    public void reload () {
        messagesHandler = new MessagesHandler(this);

        specPointsHandler = new SpecPointsHandler(this);
        handler.loadSpecPoints();

        Messages.reload();
    }

    public static Main getPlugin() {
        return plugin;
    }
}
