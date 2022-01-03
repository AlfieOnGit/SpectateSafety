package spectatesafety;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;

public enum Messages {

    NO_PERMISSION,
    VALID_SUBCOMMANDS,

    ENABLED,
    DISABLED,
    ENABLED_FOR,
    DISABLED_FOR,
    ENABLED_GROUP,
    DISABLED_GROUP,
    ENABLED_ALL,
    DISABLED_ALL,

    ALREADY_ENABLED,
    ALREADY_DISABLED,
    NOT_PLAYER,
    NOT_GROUP,
    ALREADY_ENABLED_FOR,
    ALREADY_DISABLED_FOR,

    POINT_SET,
    UNPOINT_SET,
    POINT_CLEARED,
    UNPOINT_CLEARED,
    NO_POINT,
    NO_UNPOINT;

    private final String message;
    private final HashMap<String, String> customPlaceholders;

    Messages() {
        String path = this.name().replace("_","-").toLowerCase();
        this.message = ChatColor.translateAlternateColorCodes('&', Main.messagesHandler.get(path));

        this.customPlaceholders = Main.messagesHandler.getCustomPlaceholders();
    }

    @Override
    public String toString() {
        String output = message;
        for (String cph : this.customPlaceholders.keySet()) {
            output = output.replace(cph, customPlaceholders.get(cph));
        }
        return output;
    }
}
