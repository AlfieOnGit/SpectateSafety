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
                if (sender.hasPermission("spectatesafety.spectate")) {
                    Boolean feedback = Main.handler.setSpectator((Player) sender);
                    if (feedback) sender.sendMessage(Messages.ENABLED.toString());
                    else sender.sendMessage(Messages.ALREADY_ENABLED.toString());
                    return true;
                }
            } else {
                if (args[0].equalsIgnoreCase("*")) {
                    Integer count = Main.handler.setAllSpectator();
                    sender.sendMessage(Messages.ENABLED_ALL.toString().replace("%COUNT%",count.toString()));
                } else {
                    if (Util.getPlayerFromName(args[0]) == null) {
                        sender.sendMessage(Messages.NOT_PLAYER.toString().replace("%TARGET%",args[0]));
                        return true;
                    }
                    Boolean feedback = Main.handler.setSpectator(Util.getPlayerFromName(args[0]));
                    String playerName = Objects.requireNonNull(Util.getPlayerFromName(args[0])).getName();
                    if (feedback) sender.sendMessage(Messages.ENABLED_FOR.toString().replace("%TARGET%", playerName));
                    else sender.sendMessage(Messages.ALREADY_ENABLED_FOR.toString().replace("%TARGET%", playerName));
                } return true;
            } sender.sendMessage(Messages.NO_PERMISSION.toString());
            return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> output = new ArrayList<>();
        if (sender.hasPermission("spectatesafety.spectate.others")) {
            if (args.length == 1) {
                output.add("*");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    output.add(p.getName());
                }
            }
        } return output;
    }
}
