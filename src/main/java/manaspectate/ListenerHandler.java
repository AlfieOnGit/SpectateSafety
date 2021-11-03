package manaspectate;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerHandler implements Listener {

    public ListenerHandler (Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent e) {
        Main.handler.unsetSpectator(e.getPlayer());
    }

}
