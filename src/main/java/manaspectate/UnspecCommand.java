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
                if (sender.hasPermission("manaspectate.unspec")) {
                    Feedback feedback = Main.handler.unsetSpectator((Player) sender);
                    if (feedback == Feedback.SUCCESS) { sender.sendMessage("Unspec ran"); }
                    else { sender.sendMessage("You're not in spec"); }
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
            } return true;
        } else return false;
    }
}
