package manaspectate.commands;

import manaspectate.Main;
import manaspectate.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnspecPointCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("unspecpoint")) {
            if (sender.hasPermission("manaspectate.unspecpoint")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("set")) {
                        Player p = (Player) sender;
                        Main.handler.setUnspecPoint(p.getLocation());
                        sender.sendMessage(Util.formatOutput("&aThe unspectate point has been set to your current location!"));
                    } else if (args[0].equalsIgnoreCase("clear")) {
                        Boolean feedback = Main.handler.clearUnspecPoint();
                        if (feedback) sender.sendMessage(Util.formatOutput("&aThe unspectate point has been cleared!"));
                        else sender.sendMessage(Util.formatOutput("&cThere's no existing unspectate point to clear!"));
                    } else sender.sendMessage(Util.formatOutput("&cValid subcommands: set, clear"));
                } else sender.sendMessage(Util.formatOutput("&cValid subcommands: set, clear"));
            } else sender.sendMessage(Util.formatOutput("&cSorry, you don't have permission to use that command!"));
            return true;
        } else return false;
    }
}