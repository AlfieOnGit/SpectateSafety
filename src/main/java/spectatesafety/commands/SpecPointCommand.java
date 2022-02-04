package spectatesafety.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
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

public class SpecPointCommand implements CommandExecutor, TabCompleter {

    private final List<String> subCommands = Arrays.asList("set", "clear");

    public SpecPointCommand(Main main) {
        Objects.requireNonNull(main.getCommand("specpoint")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("specpoint")) {
            if (!sender.hasPermission("spectatesafety.specpoint")) { /* If no perms */
                sender.sendMessage(Messages.NO_PERMISSION.toString().replace("{PERMISSION}", "spectatesafety.specpoint"));
            } else if (args.length == 0 || !subCommands.contains(args[0])) { /* If no subcommands or invalid subcommands */
                sender.sendMessage(Messages.VALID_SUBCOMMANDS.toString().replace("{SUBCOMMANDS}", "set, clear"));
            } else {
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length > 1) {
                        if (args[1].startsWith("w:")) {

                            /* Sender setting a world's spec point */
                            String worldName = args[1].substring(2);
                            World world = Bukkit.getWorld(worldName);
                            if (world == null) { /* If world not found */
                                sender.sendMessage(Messages.NOT_WORLD.toString().replace("{WORLD}", worldName));
                            } else { /* Command execution */
                                Main.handler.setLocalSpecPoint(((Player) sender).getLocation(), world);
                                sender.sendMessage(Messages.WORLD_POINT_SET.toString().replace("{WORLD}", worldName));
                            }

                        } else { /* Invalid argument */
                            sender.sendMessage(Messages.VALID_ARGUMENTS.toString().replace("{ARGUMENTS}", "w:<world>"));
                        }

                    } else {

                        /* Sender setting the global spec point */
                        Main.handler.setGlobalSpecPoint(((Player) sender).getLocation());
                        sender.sendMessage(Messages.POINT_SET.toString());

                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (args.length > 1) {
                        if (args[1].startsWith("w:")) {

                            /* Sender clearing a world's spec point */
                            String worldName = args[1].substring(2);
                            World world = Bukkit.getWorld(worldName);
                            if (world == null) { /* If world not found */
                                sender.sendMessage(Messages.NOT_WORLD.toString().replace("{WORLD}", worldName));
                            } else { /* Command execution */
                                if (Main.handler.clearLocalSpecPoint(world)) sender.sendMessage(Messages.WORLD_POINT_CLEARED.toString()
                                        .replace("{WORLD}", worldName));
                                else sender.sendMessage(Messages.NO_WORLD_POINT.toString().replace("{WORLD}", worldName));
                            }

                        } else { /* Invalid argument */
                            sender.sendMessage(Messages.VALID_ARGUMENTS.toString().replace("{ARGUMENTS}", "w:<world>"));
                        }
                    } else {

                        /* Sender clearing the global spec point */
                        if (Main.handler.clearGlobalSpecPoint()) sender.sendMessage(Messages.POINT_CLEARED.toString());
                        else sender.sendMessage(Messages.NO_POINT.toString());

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
        if (args.length == 1 && sender.hasPermission("spectatesafety.specpoint")) { /* If player has typed "/specpoint " */
            for (String x : this.subCommands) {
                if (x.startsWith(args[0])) {
                    output.add(x);
                }
            }
        } return output;
    }
}
