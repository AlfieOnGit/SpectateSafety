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

    public UnspecPointCommand(Main main) {
        Objects.requireNonNull(main.getCommand("unspecpoint")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("unspecpoint")) {
            if (!sender.hasPermission("spectatesafety.unspecpoint")) { /* If no perms */
                sender.sendMessage(Messages.NO_PERMISSION.toString().replace("{PERMISSION}", "spectatesafety.unspecpoint"));
            } else if (args.length == 0) { /* If no subcommands */
                sender.sendMessage(Messages.VALID_SUBCOMMANDS.toString().replace("{SUBCOMMANDS}", "set, clear"));
            } else if (!subCommands.contains(args[0])) { /* If invalid subcommands */
                sender.sendMessage(Messages.VALID_SUBCOMMANDS.toString().replace("{SUBCOMMANDS}", "set, clear"));
            } else {
                if (args[0].equalsIgnoreCase("set")) { /* SET command execution */
                    Main.handler.setUnspecPoint(((Player) sender).getLocation());
                    sender.sendMessage(Messages.UNPOINT_SET.toString());
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (Main.handler.getUnspecPoint() == null) { /* If no unspec point to clear */
                        sender.sendMessage(Messages.NO_UNPOINT.toString());
                    } else { /* CLEAR command execution */
                        Main.handler.clearUnspecPoint();
                        sender.sendMessage(Messages.UNPOINT_CLEARED.toString());
                    }
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