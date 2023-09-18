package spectatesafety.commands;

import spectatesafety.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SpecCommand implements CommandExecutor, TabCompleter {

    private final SpectateSafety plugin;

    public SpecCommand(SpectateSafety plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("spec")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("spec")) {
            if (args.length == 0 || (!sender.hasPermission("spectatesafety.spectate.others")
                    && !sender.hasPermission("spectatesafety.help")
                    && !sender.hasPermission("spectatesafety.info")
                    && !sender.hasPermission("spectatesafety.reload"))) {

                /* Sender spec-ing themself */
                if (!sender.hasPermission("spectatesafety.spectate")) { /* If no perms */
                    sender.sendMessage(Messages.NO_PERMISSION.toString()
                            .replace("{PERMISSION}", "spectatesafety.spectate"));
                } else if (plugin.getHandler().getSpectatorFromPlayer((Player) sender) != null) { /* If sender already in spectate mode */
                    sender.sendMessage(Messages.ALREADY_ENABLED.toString());
                } else { /* Command execution */
                    plugin.getHandler().setSpectator((Player) sender);
                    sender.sendMessage(Messages.ENABLED.toString().replace("{SENDER}", sender.getName()));
                }

            } else {
                if (args[0].equalsIgnoreCase("help")) {

                    /* Help command */
                    if (!sender.hasPermission("spectatesafety.help")) { /* If no perms */
                        sender.sendMessage(Messages.NO_PERMISSION.toString().
                                replace("{PERMISSION}", "spectatesafety.help"));
                    } else { /* Command execution */
                        Text.help((Player) sender);
                    }

                } else if (args[0].equalsIgnoreCase("info")) {

                    /* Info command */
                    if (!sender.hasPermission("spectatesafety.info")) { /* If no perms */
                        sender.sendMessage(Messages.NO_PERMISSION.toString()
                                .replace("{PERMISSION}", "spectatesafety.info"));
                    } else { /* Command execution */
                        Text.info((Player) sender, plugin);
                    }

                } else if (args[0].equalsIgnoreCase("reload")) {

                    /* Reload command */
                    if (!sender.hasPermission("spectatesafety.reload")) { /* If no perms */
                        sender.sendMessage(Messages.NO_PERMISSION.toString()
                                .replace("{PERMISSION}", "spectatesafety.reload"));
                    } else { /* Command execution */
                        plugin.reload();
                        sender.sendMessage(Messages.RELOAD_MESSAGE.toString());
                    }

                } else if (args[0].equalsIgnoreCase("*")) {

                    /* Sender spec-ing all */
                    Set<Player> affected = plugin.getHandler().setAllSpectator();
                    for (Player p : affected) p.sendMessage(Messages.FORCE_ENABLED.toString()
                            .replace("{SENDER}", sender.getName()));
                    String count = Integer.toString(affected.size());
                    sender.sendMessage(Messages.ENABLED_ALL.toString().replace("{COUNT}",count));

                } else if (args[0].toLowerCase().startsWith("g:") && SpectateSafety.permission != null) {

                    /* Sender spec-ing a group */
                    String group = args[0].substring(2);
                    if (!Arrays.asList(SpectateSafety.permission.getGroups()).contains(group)) { /* If group doesn't exist */
                        sender.sendMessage(Messages.NOT_GROUP.toString().replace("{GROUP}", group));
                    } else { /* Command execution */
                        Set<Player> affected = plugin.getHandler().setGroupSpectator(group);
                        for (Player p : affected) p.sendMessage(Messages.FORCE_ENABLED.toString().
                                replace("{SENDER}", sender.getName()));
                        String count = Integer.toString(affected.size());
                        sender.sendMessage(Messages.ENABLED_GROUP.toString().replace("{GROUP}", group)
                                .replace("{COUNT}", count));
                    }

                } else {
                    if (!sender.hasPermission("spectatesafety.spectate.others")) {

                        /* If invalid subcommand but has perms for subcommands */
                        String subcommands = "";
                        if (sender.hasPermission("spectatesafety.help")) {
                            subcommands = "help, ";
                        } if (sender.hasPermission("spectatesafety.info")) {
                            subcommands = subcommands + "info, ";
                        } subcommands = subcommands.substring(0, subcommands.length() - 2);
                        sender.sendMessage(Messages.VALID_SUBCOMMANDS.toString().
                                replace("{SUBCOMMANDS}", subcommands));

                    } else {

                        /* Sender spec-ing a target */
                        //Player p = Util.getPlayerFromName(args[0]);
                        Player p = Bukkit.getPlayer(args[0]);
                        if (p == null) { /* If target doesn't exist */
                            sender.sendMessage(Messages.NOT_PLAYER.toString().replace("{TARGET}",args[0]));
                        } else if (plugin.getHandler().checkStatus(p)) { /* If target already in spectate mode */
                            sender.sendMessage(Messages.ALREADY_ENABLED_FOR.toString().
                                    replace("{TARGET}", p.getName()));
                        } else { /* Command execution */
                            plugin.getHandler().setSpectator(p);
                            p.sendMessage(Messages.FORCE_ENABLED.toString().replace("{SENDER}", sender.getName()));
                            sender.sendMessage(Messages.ENABLED_FOR.toString().replace("{TARGET}", p.getName()));
                        }
                    }

                }
            } return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> possibleOutputs = new ArrayList<>();
        if (args.length == 1) { /* If player has typed "/spec " */

            /* /spec help suggestion */
            if (sender.hasPermission("spectatesafety.help")) {
                possibleOutputs.add("help");
            }

            /* /spec info suggestion */
            if (sender.hasPermission("spectatesafety.info")) {
                possibleOutputs.add("info");
            }

            /* /spec reload suggestion */
            if (sender.hasPermission("spectatesafety.reload")) {
                possibleOutputs.add("reload");
            }

            /* /spec <player> suggestion */
            if (sender.hasPermission("spectatesafety.spectate.others")) {
                if (args[0].startsWith("g:") && SpectateSafety.permission != null) { /* If player has typed "/spec g:" */
                    for (String g : SpectateSafety.permission.getGroups()) possibleOutputs.add("g:" + g);
                } else { /* If player has typed "/spec " */
                    possibleOutputs.add("*");
                    if (SpectateSafety.permission != null) possibleOutputs.add("g:");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        possibleOutputs.add(p.getName());
                    }
                }
            }
        }

        ArrayList<String> output = new ArrayList<>();
        for (String s : possibleOutputs) {
            if (s.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                output.add(s);
            }
        } return output;
    }
}
