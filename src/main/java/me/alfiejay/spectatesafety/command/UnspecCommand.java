package me.alfiejay.spectatesafety.command;

import me.alfiejay.spectatesafety.Manager;
import me.alfiejay.spectatesafety.SpectateSafety;
import me.alfiejay.spectatesafety.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class UnspecCommand extends Command {

    private final Manager manager;

    public UnspecCommand(@NotNull SpectateSafety plugin) {
        super(plugin, "unspec");
        manager = plugin.getManager();
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            selfUnspec((Player) commandSender);
            return true;
        }

        commandSender.sendMessage("CONSOLE CAN'T USE THIS COMMAND!"); // TODO
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    private void selfUnspec(@NotNull final Player player) {
        if (!player.hasPermission("spectatesafety.unspectate")) {
            player.sendMessage(Message.NO_PERMISSION.get("spectatesafety.unspectate"));
            return;
        }

        boolean result = manager.takeOutOfSpectate(player);
        if (result) player.sendMessage(Message.DISABLED.get(player.getName()));
        else player.sendMessage(Message.ALREADY_DISABLED.get());
    }
}
