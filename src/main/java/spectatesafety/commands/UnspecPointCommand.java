package spectatesafety.commands;

import spectatesafety.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UnspecPointCommand implements CommandExecutor, TabCompleter {

    private final List<String> subCommands = Arrays.asList("set", "clear");
    private final SpectateSafety plugin;

    public UnspecPointCommand(SpectateSafety plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("unspecpoint")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("unspecpoint")) {
            if (!sender.hasPermission("spectatesafety.unspecpoint")) { /* If no perms */
                sender.sendMessage(Messages.NO_PERMISSION.toString().replace("{PERMISSION}", "spectatesafety.unspecpoint"));
            } else if (args.length == 0 || !subCommands.contains(args[0])) { /* If no subcommands or invalid subcommands */
                sender.sendMessage(Messages.VALID_SUBCOMMANDS.toString().replace("{SUBCOMMANDS}", "set, clear"));
            } else {
                if (args[0].equalsIgnoreCase("set")) { /* SET command execution */
                    plugin.getHandler().setGlobalUnspecPoint(((Player) sender).getLocation());
                    sender.sendMessage(Messages.UNPOINT_SET.toString());
                } else if (args[0].equalsIgnoreCase("clear")) { /* CLEAR command execution */
                    if (plugin.getHandler().clearUnspecPoint()) sender.sendMessage(Messages.UNPOINT_CLEARED.toString());
                    else sender.sendMessage(Messages.NO_UNPOINT.toString());
                } else { /* If invalid subcommand */
                    sender.sendMessage(Messages.VALID_SUBCOMMANDS.toString().replace("%SUBCOMMANDS%", "set, clear"));
                }
            } return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> output = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission("spectatesafety.unspecpoint")) { /* If player has typed "/unspecpoint " */
            for (String x : this.subCommands) {
                if (x.startsWith(args[0])) {
                    output.add(x);
                }
            }
        } return output;
    }
}