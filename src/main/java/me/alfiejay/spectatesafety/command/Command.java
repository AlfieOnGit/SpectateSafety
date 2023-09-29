package me.alfiejay.spectatesafety.command;

import me.alfiejay.spectatesafety.SpectateSafety;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Command extends BukkitCommand {

    protected Command(@NotNull final SpectateSafety plugin, @NotNull final String commandName) {
        super(plugin.getConfigManager().getAliases(commandName).get(0));
        List<String> hold = plugin.getConfigManager().getAliases(commandName);
        hold.remove(0);
        this.setAliases(hold);
    }
}
