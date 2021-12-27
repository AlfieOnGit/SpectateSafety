package spectatesafety.commands;

import spectatesafety.Main;
import spectatesafety.Messages;
import spectatesafety.Spectator;
import spectatesafety.Util;
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

public class SpecCommand implements CommandExecutor, TabCompleter {

    public SpecCommand(Main main) {
        Objects.requireNonNull(main.getCommand("spec")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("spec")) {
            if (args.length == 0 || !sender.hasPermission("spectatesafety.spectate.others")) {

                /* Sender spec-ing themself */
                if (!sender.hasPermission("spectatesafety.spectate")) { /* If no perms */
                    sender.sendMessage(Messages.NO_PERMISSION.toString());
                } else if (Main.handler.getSpectatorFromPlayer((Player) sender) != null) { /* If sender already in spectate mode */
                    sender.sendMessage(Messages.ALREADY_ENABLED.toString());
                } else { /* Command execution */
                    Main.handler.setSpectator((Player) sender);
                    sender.sendMessage(Messages.ENABLED.toString());
                }

            } else {
                if (args[0].equalsIgnoreCase("*") && sender.hasPermission("spectatesafety.spectate.others")) {

                    /* Sender spec-ing all */
                    Integer count = Main.handler.setAllSpectator();
                    sender.sendMessage(Messages.ENABLED_ALL.toString().replace("%COUNT%",count.toString()));

                } else {

                    /* Sender spec-ing a target */
                    Player p = Util.getPlayerFromName(args[0]);
                    Spectator s = Main.handler.getSpectatorFromPlayer(p);
                    if (p == null) { /* If target doesn't exist */
                        sender.sendMessage(Messages.NOT_PLAYER.toString().replace("%TARGET%",args[0]));
                    } else if (s != null) { /* If target isn't in spectate mode */
                        sender.sendMessage(Messages.ALREADY_ENABLED_FOR.toString().replace("%TARGET%", p.getName()));
                    } else { /* Command execution */
                        Main.handler.setSpectator(p);
                        sender.sendMessage(Messages.ENABLED_FOR.toString().replace("%TARGET%", p.getName()));
                    }

                }
            } return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> possibleOutputs = new ArrayList<>();
        if (args.length == 1) { /* "/spec " */
            if (sender.hasPermission("spectatesafety.spectate.others")) {
                possibleOutputs.add("*");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    possibleOutputs.add(p.getName());
                }
            }
        }

        ArrayList<String> output = new ArrayList<>();
        for (String s : possibleOutputs) {
            if (s.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                output.add(s);
            }
        } return output;
    }
}
