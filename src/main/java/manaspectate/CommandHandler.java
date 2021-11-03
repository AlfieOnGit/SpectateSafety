package manaspectate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("spec")) {
            if (sender.hasPermission("manaspectate.spec")) {
                sender.sendMessage("Good job!");
                return true;
            }
        } else if (command.getName().equalsIgnoreCase("unspec")) {
            if (sender.hasPermission("manaspectate.unspec")) {
                sender.sendMessage("Better job!");
                return true;
            }
        } return false;
    }
}
