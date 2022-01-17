package spectatesafety;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Text {

    public static void help (Player player) {
        ArrayList<String> lines = new ArrayList<>();

        /* Help command */
        if (player.hasPermission("spectatesafety.help")) {
            lines.add(formatLine("/spec help", "Show plugin help page"));
        }

        /* Info command */
        if (player.hasPermission("spectatesafety.info")) {
            lines.add(formatLine("/spec info", "Show plugin info page"));
        }

        /* Spec command */
        if (player.hasPermission("spectatesafety.spectate")) {
            lines.add(formatLine("/spec", "Enter spectate mode"));
        } if (player.hasPermission("spectatesafety.spectate.others")) {
            lines.add(formatLine("/spec <player>", "Put target player in spectate mode"));
        }

        /* Unspec command */
        if (player.hasPermission("spectatesafety.unspectate")) {
            lines.add(formatLine("/unspec", "Exit spectate mode"));
        } if (player.hasPermission("spectatesafety.unspectate.others")) {
            lines.add(formatLine("/unspec <player>", "Take target player out of spectate mode"));
        }

        /* Specpoint command */
        if (player.hasPermission("spectatesafety.specpoint")) {
            lines.add(formatLine("/specpoint <set/clear>", "Set or clear the spec point"));
        }

        /* Unspecpoint command */
        if (player.hasPermission("spectatesafety.unspecpoint")) {
            lines.add(formatLine("/unspecpoint <set/clear>", "Set or clear the unspec point"));
        }

        for (String s : lines) player.sendMessage(s);
    }

    public static void info (Player player) {
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Plugin version: " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "v1.1");

        String val = "NOT FOUND";
        if (Main.permission != null) { /* If vault integration found */
            val = "FOUND";
        }
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Vault integration: " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + val);

    }

    private static String formatLine (String command, String description) {
        return Messages.COMMAND_HELP.toString().replace("{COMMAND}", command)
                .replace("{DESCRIPTION}", description);
    }

}
