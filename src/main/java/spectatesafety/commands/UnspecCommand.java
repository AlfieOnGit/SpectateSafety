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
            boolean canUnlock = (sender.hasPermission("spectatesafety.bypasslocks"));
            if (args.length == 0 || !sender.hasPermission("spectatesafety.unspectate.others")) {

                /* Sender unspec-ing themself */
                if (sender.hasPermission("spectatesafety.unspectate")) {
                    Player p = (Player) sender;
                    if (canUnlock || !Main.handler.getSpectatorFromPlayer(p).isLocked()) {
                        Boolean feedback = Main.handler.unsetSpectator((Player) sender);
                        if (feedback) sender.sendMessage(Messages.DISABLED.toString());
                        else sender.sendMessage(Messages.ALREADY_DISABLED.toString());
                    } else {
                        sender.sendMessage(Messages.SENDER_LOCKED.toString());
                    } return true;
                }
            } else {
                if (args[0].equalsIgnoreCase("*")) {

                    /* Sender unspec-ing all */
                    Integer count = Main.handler.unsetAllSpectator(canUnlock);
                    sender.sendMessage(Messages.DISABLED_ALL.toString().replace("%COUNT%", count.toString()));

                } else {

                    /* Sender unspec-ing target spectator */
                    Player p = Util.getPlayerFromName(args[0]);
                    if (p == null) {
                        sender.sendMessage(Messages.NOT_PLAYER.toString().replace("%TARGET%", args[0]));
                        return true;
                    }
                    if (canUnlock || !Main.handler.getSpectatorFromPlayer(p).isLocked()) {
                        Boolean feedback = Main.handler.unsetSpectator(p);
                        String playerName = Objects.requireNonNull(p).getName();
                        if (feedback) sender.sendMessage(Messages.DISABLED_FOR.toString().replace("%TARGET%", playerName));
                        else sender.sendMessage(Messages.ALREADY_DISABLED_FOR.toString().replace("%TARGET%", playerName));
                    } else {
                        sender.sendMessage(Messages.TARGET_LOCKED.toString().replace("%TARGET%", p.getName()));
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
