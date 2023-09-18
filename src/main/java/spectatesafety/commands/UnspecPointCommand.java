package spectatesafety.commands;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length > 1) {
                        if (args[1].startsWith("r:") && SpectateSafety.worldGuardHandler != null) {

                            /* Sender setting a region's unspec point */
                            String regionName = args[1].substring(2);
                            Player player = (Player) sender;
                            ProtectedRegion region = SpectateSafety.worldGuardHandler
                                    .getRegion(player.getWorld(), regionName);
                            if (region == null) { /* If region not found */
                                sender.sendMessage(Messages.WG_NOT_REGION.toString().replace("{REGION}", regionName));
                            } else { /* Command execution */
                                SpectateSafety.worldGuardHandler.saveUnspecPoint(player.getWorld(), region, player.getLocation());
                                sender.sendMessage(Messages.WG_UNPOINT_SET.toString().replace("{REGION}", region.getId()));
                            }

                        } else {

                            /* Sender setting a world's unspec point */
                            String worldName = args[1];
                            World world = Bukkit.getWorld(worldName);
                            if (world == null) { /* If world not found */
                                sender.sendMessage(Messages.NOT_WORLD.toString().replace("{WORLD}", worldName));
                            } else { /* Command execution */
                                plugin.getHandler().setLocalUnspecPoint(((Player) sender).getLocation(), world);
                                sender.sendMessage(Messages.WORLD_UNPOINT_SET.toString().replace("{WORLD}", worldName));
                            }

                        }
                    } else {

                        /* Sender setting the global unspec point */
                        plugin.getHandler().setGlobalUnspecPoint(((Player) sender).getLocation());
                        sender.sendMessage(Messages.POINT_SET.toString());

                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (args.length > 1) {

                        /* Sender clearing a world's unspec point */
                        String worldName = args[1];
                        World world = Bukkit.getWorld(worldName);
                        if (world == null) { /* If world not found */
                            sender.sendMessage(Messages.NOT_WORLD.toString().replace("{WORLD}", worldName));
                        } else { /* Command execution */
                            if (plugin.getHandler().clearLocalUnspecPoint(world)) sender.sendMessage(Messages.WORLD_UNPOINT_CLEARED.toString()
                                    .replace("{WORLD}", worldName));
                            else sender.sendMessage(Messages.NO_WORLD_UNPOINT.toString().replace("{WORLD}", worldName));
                        }

                    } else {

                        /* Sender clearing the global unspec point */
                        if (plugin.getHandler().clearGlobalUnspecPoint()) sender.sendMessage(Messages.UNPOINT_CLEARED.toString());
                        else sender.sendMessage(Messages.NO_UNPOINT.toString());

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