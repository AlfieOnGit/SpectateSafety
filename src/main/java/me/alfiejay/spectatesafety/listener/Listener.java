package me.alfiejay.spectatesafety.listener;

import me.alfiejay.spectatesafety.Manager;
import me.alfiejay.spectatesafety.SpectateSafety;
import me.alfiejay.spectatesafety.message.Message;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

public final class Listener implements org.bukkit.event.Listener {

    private final Manager manager;

    public Listener(@NotNull final SpectateSafety plugin) {
        manager = plugin.getManager();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        manager.takeOutOfSpectate(e.getPlayer());
    }

    @EventHandler
    public void onPlayerTeleport (PlayerTeleportEvent e) {
        if (!e.isCancelled()
                && e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)
                && manager.isInSpec(e.getPlayer())
                && !e.getPlayer().hasPermission("spectatesafety.teleport")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void monitorPlayerTeleport(PlayerTeleportEvent e) {
        if (e.isCancelled()
                && e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)
                && manager.isInSpec(e.getPlayer())
                && !e.getPlayer().hasPermission("spectatesafety.teleport")) {
            e.getPlayer().sendMessage(Message.CANNOT_TELEPORT.get());
        }
    }
}
