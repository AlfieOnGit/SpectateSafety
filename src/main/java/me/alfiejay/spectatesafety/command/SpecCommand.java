package me.alfiejay.spectatesafety.command;

import me.alfiejay.spectatesafety.Manager;
import me.alfiejay.spectatesafety.SpectateSafety;
import me.alfiejay.spectatesafety.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SpecCommand extends Command {

    private final Manager manager;

    public SpecCommand(@NotNull final SpectateSafety plugin) {
        super(plugin, "spec");
        manager = plugin.getManager();
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            selfSpec((Player) commandSender);
            return true;
        }

        commandSender.sendMessage("CONSOLE CAN'T USE THIS COMMAND!"); // TODO
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }

    private void selfSpec(@NotNull final Player player) {
        if (!player.hasPermission("spectatesafety.spectate")) {
            player.sendMessage(Message.NO_PERMISSION.get("spectatesafety.spectate"));
            return;
        }

        boolean result = manager.putInSpectate(player);
        if (result) player.sendMessage(Message.ENABLED.get(player.getName()));
        else player.sendMessage(Message.ALREADY_ENABLED.get());
    }
}
