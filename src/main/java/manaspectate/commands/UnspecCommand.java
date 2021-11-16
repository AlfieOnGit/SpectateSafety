package manaspectate.commands;

import manaspectate.Main;
import manaspectate.Util;
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
            if (args.length == 0 || !sender.hasPermission("manaspectate.unspectate.others")) {
                if (sender.hasPermission("manaspectate.unspectate")) {
                    Boolean feedback = Main.handler.unsetSpectator((Player) sender);
                    if (feedback) sender.sendMessage(Util.formatOutput("&7Spectator mode &c&lDISABLED&7!"));
                    else sender.sendMessage(Util.formatOutput("&cYou're not currently in spectator mode!"));
                    return true;
                }
            } else {
                if (args[0].equalsIgnoreCase("*")) {
                    Integer count = Main.handler.unsetAllSpectator();
                    sender.sendMessage(Util.formatOutput("&7Disabled spectator mode for &c&l" + count.toString() + "&7 other players!"));
                } else {
                    if (Util.getPlayerFromName(args[0]) == null) {
                        sender.sendMessage(Util.formatOutput("&c'" + args[0] + "' is not an online player!"));
                        return true;
                    }
                    Boolean feedback = Main.handler.unsetSpectator(Util.getPlayerFromName(args[0]));
                    String playerName = Objects.requireNonNull(Util.getPlayerFromName(args[0])).getName();
                    if (feedback) sender.sendMessage(Util.formatOutput("&7Spectator mode &c&lDISABLED&7 for &f" + playerName + "&7!"));
                    else sender.sendMessage(Util.formatOutput("&c" + playerName + " isn't currently in spectator mode!"));
                } return true;
            } sender.sendMessage(Util.formatOutput("&cSorry, you don't have permission to use that command!"));
            return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> output = new ArrayList<>();
        if (sender.hasPermission("manaspectate.unspectate.others")) {
            if (args.length == 1) {
                output.add("*");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    output.add(p.getName());
                }
            }
        } return output;
    }
}
