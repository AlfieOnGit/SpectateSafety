package manaspectate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpecCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("spec")) {
            if (args.length == 0) {
                if (sender.hasPermission("manaspectate.spectate")) {
                    Boolean feedback = Main.handler.setSpectator((Player) sender);
                    if (feedback) sender.sendMessage("Spec ran");
                    else sender.sendMessage("You're already in spec");
                    return true;
                } sender.sendMessage("No perms to spec");
            } else {
                if (sender.hasPermission("manaspectate.spectate.others")) {
                    if (args[0].equalsIgnoreCase("*")) {
                        Integer count = Main.handler.setAllSpectator();
                        sender.sendMessage("Spec ran on " + count.toString() + " players");
                    } else {
                        Boolean feedback = Main.handler.setSpectator(Utils.getPlayerFromName(args[0]));
                        if (feedback) sender.sendMessage("Spec ran on " + args[0]);
                        else sender.sendMessage("That player is already in spec");
                    } return true;
                } sender.sendMessage("No perms to spec others");
            } return true;
        } else return false;
    }
}
