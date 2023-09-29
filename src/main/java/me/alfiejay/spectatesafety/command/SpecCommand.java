package me.alfiejay.spectatesafety.command;

import me.alfiejay.spectatesafety.SpectateSafety;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SpecCommand extends Command {

    public SpecCommand(@NotNull final SpectateSafety plugin) {
        super(plugin, "spec");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage("HELLO BABES");
        return true;
    }
}
