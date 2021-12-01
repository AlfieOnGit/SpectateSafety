package spectatesafety.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import spectatesafety.Main;

public class ListenerHandler implements Listener {

    public ListenerHandler (Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent e) {
        Main.handler.unsetSpectator(e.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport (PlayerTeleportEvent e) {
        if (!e.isCancelled() && e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            if (Main.handler.checkStatus(e.getPlayer()) && !e.getPlayer().hasPermission("spectatesafety.teleport")) {
                e.setCancelled(true);
            }
        }
    }

}
