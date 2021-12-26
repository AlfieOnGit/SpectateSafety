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

public class SpecCommand implements CommandExecutor, TabCompleter {

    public SpecCommand(Main main) {
        Objects.requireNonNull(main.getCommand("spec")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("spec")) {
            if (args.length == 0 || !sender.hasPermission("spectatesafety.spectate.others")) {

                /* Sender spec-ing themself */
                if (sender.hasPermission("spectatesafety.spectate")) {
                    Boolean feedback = Main.handler.setSpectator((Player) sender, false);
                    if (feedback) sender.sendMessage(Messages.ENABLED.toString());
                    else sender.sendMessage(Messages.ALREADY_ENABLED.toString());
                    return true;

                }
            } else {
                if (args[0].equalsIgnoreCase("*")) {

                    /* Sender spec-ing all */
                    boolean b = false;
                    if (args.length > 1) { b = (args[1].equals("#l") || args[1].equals("#lock")); }
                    Integer count = Main.handler.setAllSpectator(b);
                    sender.sendMessage(Messages.ENABLED_ALL.toString().replace("%COUNT%",count.toString()));

                } else {

                    /* Sender spec-ing a target */
                    Player p = Util.getPlayerFromName(args[0]);
                    if (p == null) {
                        sender.sendMessage(Messages.NOT_PLAYER.toString().replace("%TARGET%",args[0]));
                        return true;
                    }
                    boolean b = false;
                    if (args.length > 1) { b = (args[1].equals("#l") || args[1].equals("#lock")); }
                    Boolean feedback = Main.handler.setSpectator(p, b);
                    String playerName = Objects.requireNonNull(p).getName();
                    if (feedback) sender.sendMessage(Messages.ENABLED_FOR.toString().replace("%TARGET%", playerName));
                    else sender.sendMessage(Messages.ALREADY_ENABLED_FOR.toString().replace("%TARGET%", playerName));

                } return true;
            } sender.sendMessage(Messages.NO_PERMISSION.toString());
            return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> possibleOutputs = new ArrayList<>();
        if (sender.hasPermission("spectatesafety.spectate.others")) {
            if (args.length == 1) {
                possibleOutputs.add("*");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    possibleOutputs.add(p.getName());
                }
            } else if (args.length == 2) {
                possibleOutputs.add("#l");
            }
        } ArrayList<String> output = new ArrayList<>();
        for (String s : possibleOutputs) {
            if (s.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                output.add(s);
            }
        } return output;
    }
}
