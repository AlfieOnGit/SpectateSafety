package me.alfiejay.spectatesafety.command;

import me.alfiejay.spectatesafety.SpectateSafety;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class UnspecCommand extends Command {

    public UnspecCommand(@NotNull SpectateSafety plugin) {
        super(plugin, "unspec");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage("BYE BABES");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
