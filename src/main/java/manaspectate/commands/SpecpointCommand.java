package manaspectate.commands;

import manaspectate.Main;
import manaspectate.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpecpointCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("specpoint")) {
            if (sender.hasPermission("manaspectate.specpoint")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("set")) {
                        Player p = (Player) sender;
                        Main.handler.setSpecPoint(p.getLocation());
                        sender.sendMessage(Util.formatOutput("&7The spectate point has been set to your current location!"));
                        return true;
                    } else if (args[0].equalsIgnoreCase("clear")) {
                        Boolean feedback = Main.handler.clearSpecPoint();
                        if (feedback) sender.sendMessage(Util.formatOutput("&7The spectate point has been cleared!"));
                        else sender.sendMessage(Util.formatOutput("&cThere's no existing spectate point to clear!"));
                        return true;
                    } else sender.sendMessage(Util.formatOutput("&cValid subcommands: set, clear"));
                } else sender.sendMessage(Util.formatOutput("&cValid subcommands: set, clear"));
            } else sender.sendMessage(Util.formatOutput("&cSorry, you don't have permission to use that command!"));
            return true;
        } else return false;
    }
}
