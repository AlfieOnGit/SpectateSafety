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

public class SpecPointCommand implements CommandExecutor, TabCompleter {

    private final List<String> subCommands = Arrays.asList("set", "clear");
    private final SpectateSafety plugin;

    public SpecPointCommand(SpectateSafety plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("specpoint")).setTabCompleter(this);
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
                        if (args[1].startsWith("r:") && SpectateSafety.worldGuardHandler != null) {

                            /* Sender setting a region's spec point */
                            String regionName = args[1].substring(2);
                            Player player = (Player) sender;
                            ProtectedRegion region = SpectateSafety.worldGuardHandler
                                    .getRegion(player.getWorld(), regionName);
                            if (region == null) { /* If region not found */
                                sender.sendMessage(Messages.WG_NOT_REGION.toString().replace("{REGION}", regionName));
                            } else { /* Command execution */
                                SpectateSafety.worldGuardHandler.saveSpecPoint(player.getWorld(), region, player.getLocation());
                                sender.sendMessage(Messages.WG_POINT_SET.toString().replace("{REGION}", region.getId()));
                            }

                        } else {

                            /* Sender setting a world's spec point */
                            String worldName = args[1];
                            World world = Bukkit.getWorld(worldName);
                            if (world == null) { /* If world not found */
                                sender.sendMessage(Messages.NOT_WORLD.toString().replace("{WORLD}", worldName));
                            } else { /* Command execution */
                                plugin.getHandler().setLocalSpecPoint(((Player) sender).getLocation(), world);
                                sender.sendMessage(Messages.WORLD_POINT_SET.toString().replace("{WORLD}", worldName));
                            }

                        }
                    } else {

                        /* Sender setting the global spec point */
                        plugin.getHandler().setGlobalSpecPoint(((Player) sender).getLocation());
                        sender.sendMessage(Messages.POINT_SET.toString());

                    }
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (args.length > 1) {
                        if (args[1].startsWith("r:") && SpectateSafety.worldGuardHandler != null) {

                            /* Sender clearing a region's spec point */
                            String regionName = args[1].substring(2);
                            Player player = (Player) sender;
                            ProtectedRegion region = SpectateSafety.worldGuardHandler
                                    .getRegion(player.getWorld(), regionName);
                            if (region == null) { /* If region not found */
                                sender.sendMessage(Messages.WG_NOT_REGION.toString().replace("{REGION}", regionName));
                            } else { /* Command execution */
                                boolean result = SpectateSafety.worldGuardHandler.clearSpecPoint(player.getWorld(), region);
                                if (result) sender.sendMessage(Messages.WG_POINT_CLEARED.toString().replace("{REGION}", region.getId()));
                                else sender.sendMessage(Messages.WG_NO_POINT.toString().replace("{REGION}", region.getId()));
                            }

                        } else {

                            /* Sender clearing a world's spec point */
                            String worldName = args[1];
                            World world = Bukkit.getWorld(worldName);
                            if (world == null) { /* If world not found */
                                sender.sendMessage(Messages.NOT_WORLD.toString().replace("{WORLD}", worldName));
                            } else { /* Command execution */
                                if (plugin.getHandler().clearLocalSpecPoint(world)) sender.sendMessage(Messages.WORLD_POINT_CLEARED.toString()
                                        .replace("{WORLD}", worldName));
                                else sender.sendMessage(Messages.NO_WORLD_POINT.toString().replace("{WORLD}", worldName));
                            }

                        }
                    } else {

                        /* Sender clearing the global spec point */
                        if (plugin.getHandler().clearGlobalSpecPoint()) sender.sendMessage(Messages.POINT_CLEARED.toString());
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
            for (String x : this.subCommands) { // TODO: Add world suggestions and region suggestions (And on unspecpoint command too)
                if (x.startsWith(args[0])) {
                    output.add(x);
                }
            }
        } return output;
    }
}
