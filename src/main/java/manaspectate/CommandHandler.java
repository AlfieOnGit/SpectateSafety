package manaspectate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("spec")) {
            if (args.length == 0) {
                if (sender.hasPermission("manaspectate.spec")) {
                    Main.handler.setSpectator((Player) sender);
                    sender.sendMessage("Spec ran");
                    return true;
                }
            } else {
                if (sender.hasPermission("manaspectate.spec.others")) {
                    if (args[0].equalsIgnoreCase("*")) {
                        Main.handler.setAllSpectator();
                    } else {
                        Main.handler.setSpectator(Utils.getPlayerFromName(args[0]));
                        sender.sendMessage("Spec ran on " + args[0]);
                    } return true;
                }
            }
        } else if (command.getName().equalsIgnoreCase("unspec")) {
            if (args.length == 0) {
                if (sender.hasPermission("manaspectate.unspec")) {
                    Main.handler.unsetSpectator((Player) sender);
                    sender.sendMessage("Unspec ran");
                    return true;
                }
            } else {
                if (sender.hasPermission("manaspectate.unspec.others")) {
                    if (args[0].equalsIgnoreCase("*")) {
                        Main.handler.unsetAllSpectator();
                    } else {
                        Main.handler.unsetSpectator(Utils.getPlayerFromName(args[0]));
                        sender.sendMessage("Unspec ran on " + args[0]);
                    } return true;
                }
            }
        } return false;
    }
}
