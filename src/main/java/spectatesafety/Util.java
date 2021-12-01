package spectatesafety;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class Util {

    public static Player getPlayerFromName (String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        } return null;
    }
}
