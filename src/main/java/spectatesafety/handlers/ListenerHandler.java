package spectatesafety.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import spectatesafety.SpectateSafety;

public class ListenerHandler implements Listener {

    private final Handler handler;

    public ListenerHandler (SpectateSafety plugin) {
        handler = plugin.getHandler();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent e) {
        handler.unsetSpectator(e.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport (PlayerTeleportEvent e) {
        if (!e.isCancelled() && e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            if (handler.checkStatus(e.getPlayer()) && !e.getPlayer().hasPermission("spectatesafety.teleport")) {
                e.setCancelled(true);
            }
        }
    }

}
