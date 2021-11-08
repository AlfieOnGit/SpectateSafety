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
                if (sender.hasPermission("manaspectate.spec")) {
                    Feedback feedback = Main.handler.setSpectator((Player) sender);
                    if (feedback == Feedback.SUCCESS) { sender.sendMessage("Spec ran"); }
                    else { sender.sendMessage("You're already in spec"); }
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
            } return true;
        } else return false;
    }
}
