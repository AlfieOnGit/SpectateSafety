package spectatesafety;

import org.bukkit.entity.Player;

public final class Text {

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
            player.sendMessage(formatCommand("/specpoint <set/clear>", "Set or clear the global spec point"));
            player.sendMessage(formatCommand("/specpoint <set/clear> <world>", "Set or clear a world's spec point"));
        }

        /* Unspecpoint command */
        if (player.hasPermission("spectatesafety.unspecpoint")) {
            player.sendMessage(formatCommand("/unspecpoint <set/clear>", "Set or clear the global unspec point"));
            player.sendMessage(formatCommand("/unspecpoint <set/clear> <world>", "Set or clear a world's unspec point"));
        }
    }

    public static void info (Player player, SpectateSafety plugin) {
        player.sendMessage(Messages.INFO_MESSAGE.toString());

        String version = plugin.getDescription().getVersion();
        player.sendMessage(Messages.VERSION.toString().replace("{VERSION}", version));

        /* Vault integration check */
        String found = (SpectateSafety.permission != null) ? "FOUND" : "NOT FOUND";
        player.sendMessage(Messages.DEPENDENCY.toString().replace("{DEPENDENCY}", "Vault")
                .replace("{FOUND}", found));

        /* World Guard integration check */
        found = (SpectateSafety.worldGuardHandler != null) ? "FOUND" : "NOT FOUND";
        player.sendMessage(Messages.DEPENDENCY.toString().replace("{DEPENDENCY}", "WorldGuard")
                .replace("{FOUND}", found));
    }

    private static String formatCommand(String command, String description) {
        return Messages.HELP_FORMAT.toString().replace("{COMMAND}", command)
                .replace("{DESCRIPTION}", description);
    }
}
