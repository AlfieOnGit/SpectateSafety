package manaspectate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class Util {

    private final static String tag = "&f[&3&lSPEC&f]&f";

    public static Player getPlayerFromName (String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        } return null;
    }

    public static String formatOutput(String message) {
        String output = tag + " " + message;
        output = ChatColor.translateAlternateColorCodes('&', output);
        return output;
    }
}
