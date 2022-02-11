package spectatesafety;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import spectatesafety.commands.*;
import spectatesafety.handlers.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SpectateSafety extends JavaPlugin {

    private Handler handler;
    private SpecPointsHandler specPointsHandler;
    public static MessagesHandler messagesHandler;
    public static Permission permission;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("spec")).setExecutor(new SpecCommand(this));
        Objects.requireNonNull(getCommand("unspec")).setExecutor(new UnspecCommand(this));
        Objects.requireNonNull(getCommand("specpoint")).setExecutor(new SpecPointCommand(this));
        Objects.requireNonNull(getCommand("unspecpoint")).setExecutor(new UnspecPointCommand(this));

        handler = new Handler(this);
        specPointsHandler = new SpecPointsHandler(this);
        messagesHandler = new MessagesHandler(this);

        new ListenerHandler(this);
        handler.loadSpecPoints();

        permission = getPermissions();
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

        Messages.reload();
    }

    public Handler getHandler() { return handler; }

    public SpecPointsHandler getSpecPointsHandler() { return specPointsHandler; }
}
