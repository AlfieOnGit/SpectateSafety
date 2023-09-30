package me.alfiejay.spectatesafety.command;

import com.google.common.base.Preconditions;
import me.alfiejay.spectatesafety.SpectateSafety;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Command extends BukkitCommand implements TabCompleter {

    protected Command(@NotNull final SpectateSafety plugin, @NotNull final String commandName) {
        super(plugin.getConfigManager().getAliases(commandName).get(0));
        List<String> hold = plugin.getConfigManager().getAliases(commandName);
        hold.remove(0);
        this.setAliases(hold);
    }

    @Override
    @SuppressWarnings("all") // I nabbed this code from PluginCommand in the Bukkit API so I trust it
    public final @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        Preconditions.checkArgument(sender != null, "Sender cannot be null");
        Preconditions.checkArgument(args != null, "Arguments cannot be null");
        Preconditions.checkArgument(alias != null, "Alias cannot be null");
        List<String> completions = null;

        try {
            completions = this.onTabComplete(sender, this, alias, args);
        } catch (Throwable var11) {
            StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            String[] var7 = args;
            int var8 = args.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String arg = var7[var9];
                message.append(arg).append(' ');
            }

            message.deleteCharAt(message.length() - 1).append("' in plugin SpectateSafety");
            throw new CommandException(message.toString(), var11);
        }

        return completions == null ? new ArrayList<>() : completions;
    }
}
