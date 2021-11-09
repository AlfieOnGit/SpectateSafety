package manaspectate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnspecCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("unspec")) {
            if (args.length == 0) {
                if (sender.hasPermission("manaspectate.unspectate")) {
                    Boolean feedback = Main.handler.unsetSpectator((Player) sender);
                    if (feedback) { sender.sendMessage("Unspec ran"); }
                    else { sender.sendMessage("You're not in spec"); }
                    return true;
                } sender.sendMessage("No perms to unspec");
            } else {
                if (sender.hasPermission("manaspectate.unspectate.others")) {
                    if (args[0].equalsIgnoreCase("*")) {
                        Integer count = Main.handler.unsetAllSpectator();
                        sender.sendMessage("Unspec ran on " + count.toString() + " players");
                    } else {
                        Boolean feedback = Main.handler.unsetSpectator(Utils.getPlayerFromName(args[0]));
                        if (feedback) sender.sendMessage("Unspec ran on " + args[0]);
                        else sender.sendMessage("That player isn't in spec");
                    } return true;
                } sender.sendMessage("No perms to unspec others");
            } return true;
        } else return false;
    }
}
