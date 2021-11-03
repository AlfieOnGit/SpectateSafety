package manaspectate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils {

    public static Player getPlayerFromName (String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        } return null;
    }
}
