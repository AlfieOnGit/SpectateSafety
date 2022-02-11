package spectatesafety.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import spectatesafety.SpectateSafety;

public class ListenerHandler implements Listener {

    private final SpectateSafety plugin;

    public ListenerHandler (SpectateSafety plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent e) {
        plugin.getHandler().unsetSpectator(e.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport (PlayerTeleportEvent e) {
        if (!e.isCancelled() && e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            if (plugin.getHandler().checkStatus(e.getPlayer()) && !e.getPlayer().hasPermission("spectatesafety.teleport")) {
                e.setCancelled(true);
            }
        }
    }

}
