package spectatesafety.commands;

import spectatesafety.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SpecPointCommand implements CommandExecutor, TabCompleter {

    private final List<String> subCommands = Arrays.asList("set", "clear");

    public SpecPointCommand(Main main) {
        Objects.requireNonNull(main.getCommand("specpoint")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("specpoint")) {
            if (sender.hasPermission("spectatesafety.specpoint")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("set")) {
                        Player p = (Player) sender;
                        Main.handler.setSpecPoint(p.getLocation());
                        sender.sendMessage(Util.formatOutput("&aThe spectate point has been set to your current location!"));
                    } else if (args[0].equalsIgnoreCase("clear")) {
                        Boolean feedback = Main.handler.clearSpecPoint();
                        if (feedback) sender.sendMessage(Util.formatOutput("&aThe spectate point has been cleared!"));
                        else sender.sendMessage(Util.formatOutput("&cThere's no existing spectate point to clear!"));
                    } else sender.sendMessage(Util.formatOutput("&cValid subcommands: set, clear"));
                } else sender.sendMessage(Util.formatOutput("&cValid subcommands: set, clear"));
            } else sender.sendMessage(Util.formatOutput("&cSorry, you don't have permission to use that command!"));
            return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> output = new ArrayList<>();
        if (args.length == 1) {
            for (String x : this.subCommands) {
                if (x.startsWith(args[0])) {
                    output.add(x);
                }
            }
        } return output;
    }
}
