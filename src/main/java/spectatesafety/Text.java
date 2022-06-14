package spectatesafety;

import org.bukkit.entity.Player;

public class Text {

    public static void help (Player player) {

        player.sendMessage(Messages.HELP_MESSAGE.toString());

        /* Help command */
        player.sendMessage(formatCommand("/spec help", "Show plugin help page"));

        /* Info command */
        if (player.hasPermission("spectatesafety.info")) {
            player.sendMessage(formatCommand("/spec info", "Show plugin info page"));
        }

        /* Reload command */
        if (player.hasPermission("spectatesafety.reload")) {
            player.sendMessage(formatCommand("/spec reload", "Reloads plugin configs"));
        }

        /* Spec command */
        if (player.hasPermission("spectatesafety.spectate")) {
            player.sendMessage(formatCommand("/spec", "Enter spectate mode"));
        } if (player.hasPermission("spectatesafety.spectate.others")) {
            player.sendMessage(formatCommand("/spec <player>", "Put target player in spectate mode"));
        }

        /* Unspec command */
        if (player.hasPermission("spectatesafety.unspectate")) {
            player.sendMessage(formatCommand("/unspec", "Exit spectate mode"));
        } if (player.hasPermission("spectatesafety.unspectate.others")) {
            player.sendMessage(formatCommand("/unspec <player>", "Take target player out of spectate mode"));
        }

        /* Specpoint command */
        if (player.hasPermission("spectatesafety.specpoint")) {
            player.sendMessage(formatCommand("/specpoint <set/clear>", "Set or clear the spec point"));
        }

        /* Unspecpoint command */
        if (player.hasPermission("spectatesafety.unspecpoint")) {
            player.sendMessage(formatCommand("/unspecpoint <set/clear>", "Set or clear the unspec point"));
        }
    }

    public static void info (Player player, SpectateSafety plugin) {
        player.sendMessage(Messages.INFO_MESSAGE.toString());

        String version = plugin.getDescription().getVersion();
        player.sendMessage(Messages.VERSION.toString().replace("{VERSION}", version));

        String found = "NOT FOUND";
        if (SpectateSafety.permission != null) { /* If vault integration found */
            found = "FOUND";
        }
        player.sendMessage(Messages.DEPENDENCY.toString().replace("{DEPENDENCY}", "Vault")
                .replace("{FOUND}", found));
    }

    private static String formatCommand(String command, String description) {
        return Messages.HELP_FORMAT.toString().replace("{COMMAND}", command)
                .replace("{DESCRIPTION}", description);
    }

}
