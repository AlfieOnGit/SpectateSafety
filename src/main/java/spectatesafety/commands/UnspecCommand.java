package spectatesafety.commands;

import spectatesafety.Main;
import spectatesafety.Messages;
import spectatesafety.Spectator;
import spectatesafety.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UnspecCommand implements CommandExecutor, TabCompleter {

    public UnspecCommand(Main main) {
        Objects.requireNonNull(main.getCommand("unspec")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("unspec")) {
            if (args.length == 0 || !sender.hasPermission("spectatesafety.unspectate.others")) {

                /* Sender unspec-ing themself */
                if (!sender.hasPermission("spectatesafety.unspectate")) { /* If no perms */
                    sender.sendMessage(Messages.NO_PERMISSION.toString());
                } else if (Main.handler.getSpectatorFromPlayer((Player) sender) == null) { /* If sender not in spec */
                    sender.sendMessage(Messages.ALREADY_DISABLED.toString());
                } else { /* Command execution */
                    Main.handler.unsetSpectator((Player) sender);
                    sender.sendMessage(Messages.DISABLED.toString());
                }

            } else {
                if (args[0].equalsIgnoreCase("*")) {

                    /* Sender unspec-ing all */
                    Integer count = Main.handler.unsetAllSpectator();
                    sender.sendMessage(Messages.DISABLED_ALL.toString().replace("%COUNT%", count.toString()));

                } else if (args[0].toLowerCase().startsWith("g:") && Main.permission != null) {

                    /* Sender unspec-ing a group */
                    int count = 0;
                    ArrayList<Spectator> spectators = new ArrayList<>(Main.handler.getSpectators());
                    for (Spectator s : spectators) {
                        Player p = s.getPlayer();
                        for (String g : Main.permission.getPlayerGroups(p)) {
                            if (args[0].substring(2).equals(g)) {
                                Main.handler.unsetSpectator(p);
                                count++;
                                break;
                            }
                        }
                    } sender.sendMessage(Messages.DISABLED_ALL.toString().replace("%COUNT%", Integer.toString(count)));

                } else {

                    /* Sender unspec-ing target spectator */
                    Player p = Util.getPlayerFromName(args[0]);
                    if (p == null) { /* If target isn't a player */
                        sender.sendMessage(Messages.NOT_PLAYER.toString().replace("%TARGET%", args[0]));
                    } else if (Main.handler.getSpectatorFromPlayer(p) == null) { /* If target not in spec */
                        sender.sendMessage(Messages.ALREADY_DISABLED_FOR.toString().replace("%TARGET%", p.getName()));
                    } else { /* Command execution */
                        Main.handler.unsetSpectator(p);
                        sender.sendMessage(Messages.DISABLED_FOR.toString().replace("%TARGET%", p.getName()));
                    }

                }
            } return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> possibleOutputs = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("spectatesafety.unspectate.others")) {
                if (args[0].toLowerCase().startsWith("g:") && Main.permission != null) { /* "/unspec g:" */
                    for (String g : Main.permission.getGroups()) possibleOutputs.add("g:" + g);
                } else { /* "/unspec " */
                    possibleOutputs.add("*");
                    if (Main.permission != null) possibleOutputs.add("g:");
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
