package spectatesafety.commands;

import spectatesafety.Main;
import spectatesafety.Messages;
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
                if (sender.hasPermission("spectatesafety.unspectate")) {
                    Player p = (Player) sender;
                    if (!Main.handler.checkStatus(p)) { /* Sender isn't in spec */
                        sender.sendMessage(Messages.ALREADY_DISABLED.toString());
                    } else { /* If sender can run command */
                        Main.handler.unsetSpectator((Player) sender);
                        sender.sendMessage(Messages.DISABLED.toString());
                    } return true;
                }
            } else {
                if (args[0].equalsIgnoreCase("*")) {

                    /* Sender unspec-ing all */
                    Integer count = Main.handler.unsetAllSpectator();
                    sender.sendMessage(Messages.DISABLED_ALL.toString().replace("%COUNT%", count.toString()));

                } else {

                    /* Sender unspec-ing target spectator */
                    Player p = Util.getPlayerFromName(args[0]);
                    if (p == null) { /* If target isn't a player */
                        sender.sendMessage(Messages.NOT_PLAYER.toString().replace("%TARGET%", args[0]));
                        return true;
                    } else { /* If sender can run command */
                        Boolean feedback = Main.handler.unsetSpectator(p);
                        String playerName = Objects.requireNonNull(p).getName();
                        if (feedback) { /* If success */
                            sender.sendMessage(Messages.DISABLED_FOR.toString().replace("%TARGET%", playerName));
                        } else { /* If target was already in spec */
                            sender.sendMessage(Messages.ALREADY_DISABLED_FOR.toString().replace("%TARGET%", playerName));
                        }
                    }
                } return true;
            } sender.sendMessage(Messages.NO_PERMISSION.toString());
            return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> possibleOutputs = new ArrayList<>();
        if (sender.hasPermission("spectatesafety.unspectate.others")) {
            if (args.length == 1) {
                possibleOutputs.add("*");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    possibleOutputs.add(p.getName());
                }
            }
        } ArrayList<String> output = new ArrayList<>();
        for (String s : possibleOutputs) {
            if (s.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                output.add(s);
            }
        } return output;
    }
}
