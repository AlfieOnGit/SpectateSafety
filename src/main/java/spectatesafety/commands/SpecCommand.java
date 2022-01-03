package spectatesafety.commands;

import spectatesafety.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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
                    sender.sendMessage(Messages.NO_PERMISSION.toString().replace("{PERMISSION}", "spectatesafety.spectate"));
                } else if (Main.handler.getSpectatorFromPlayer((Player) sender) != null) { /* If sender already in spectate mode */
                    sender.sendMessage(Messages.ALREADY_ENABLED.toString());
                } else { /* Command execution */
                    Main.handler.setSpectator((Player) sender);
                    sender.sendMessage(Messages.ENABLED.toString().replace("{SENDER}", sender.getName()));
                }

            } else {
                if (args[0].equalsIgnoreCase("*")) {

                    /* Sender spec-ing all */
                    Set<Player> affected = Main.handler.setAllSpectator();
                    for (Player p : affected) p.sendMessage(Messages.FORCE_ENABLED.toString().replace("{SENDER}", sender.getName()));
                    String count = Integer.toString(affected.size());
                    sender.sendMessage(Messages.ENABLED_ALL.toString().replace("{COUNT}",count));

                } else if (args[0].toLowerCase().startsWith("g:") && Main.permission != null) {

                    /* Sender spec-ing a group */
                    String group = args[0].substring(2);
                    if (!Arrays.asList(Main.permission.getGroups()).contains(group)) { /* If group doesn't exist */
                        sender.sendMessage(Messages.NOT_GROUP.toString().replace("{GROUP}", group));
                    } else { /* Command execution */
                        Set<Player> affected = Main.handler.setGroupSpectator(group);
                        for (Player p : affected) p.sendMessage(Messages.FORCE_ENABLED.toString().replace("{SENDER}", sender.getName()));
                        String count = Integer.toString(affected.size());
                        sender.sendMessage(Messages.ENABLED_GROUP.toString().replace("{GROUP}", group).replace("{COUNT}", count));
                    }

                } else {

                    /* Sender spec-ing a target */
                    Player p = Util.getPlayerFromName(args[0]);
                    if (p == null) { /* If target doesn't exist */
                        sender.sendMessage(Messages.NOT_PLAYER.toString().replace("{TARGET}",args[0]));
                    } else if (Main.handler.checkStatus(p)) { /* If target already in spectate mode */
                        sender.sendMessage(Messages.ALREADY_ENABLED_FOR.toString().replace("{TARGET}", p.getName()));
                    } else { /* Command execution */
                        Main.handler.setSpectator(p);
                        p.sendMessage(Messages.FORCE_ENABLED.toString().replace("{SENDER}", sender.getName()));
                        sender.sendMessage(Messages.ENABLED_FOR.toString().replace("{TARGET}", p.getName()));
                    }

                }
            } return true;
        } else return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> possibleOutputs = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("spectatesafety.spectate.others")) {
                if (args[0].startsWith("g:") && Main.permission != null) { /* "/spec g:" */
                    for (String g : Main.permission.getGroups()) possibleOutputs.add("g:" + g);
                } else { /* "/spec " */
                    possibleOutputs.add("*");
                    if (Main.permission != null) possibleOutputs.add("g:");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        possibleOutputs.add(p.getName());
                    }
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
