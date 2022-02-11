package spectatesafety;

import org.bukkit.ChatColor;

import java.util.HashMap;

public enum Messages {

    NO_PERMISSION,
    VALID_SUBCOMMANDS,
    VALID_ARGUMENTS,
    FORCE_ENABLED,
    FORCE_DISABLED,

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
    WORLD_POINT_SET,
    WORLD_UNPOINT_SET,
    POINT_CLEARED,
    UNPOINT_CLEARED,
    WORLD_POINT_CLEARED,
    WORLD_UNPOINT_CLEARED,
    NO_POINT,
    NO_WORLD_POINT,
    NO_UNPOINT,
    NO_WORLD_UNPOINT,
    NOT_WORLD,

    HELP_MESSAGE,
    INFO_MESSAGE,
    RELOAD_MESSAGE,
    HELP_FORMAT,
    VERSION,
    DEPENDENCY;

    private String message;
    private HashMap<String, String> customPlaceholders;

    Messages() {
        String path = this.name().replace("_","-").toLowerCase();
        this.message = ChatColor.translateAlternateColorCodes('&', SpectateSafety.messagesHandler.get(path));

        this.customPlaceholders = SpectateSafety.messagesHandler.getCustomPlaceholders();
    }

    @Override
    public String toString() {
        String output = message;
        for (String cph : this.customPlaceholders.keySet()) {
            output = output.replace(cph, customPlaceholders.get(cph));
        }
        return output;
    }

    /**
     * Updates messages
     */
    public static void reload() {
        for (Messages m : values()) {
            String path = m.name().replace("_","-").toLowerCase();
            m.message = ChatColor.translateAlternateColorCodes('&', SpectateSafety.messagesHandler.get(path));

            m.customPlaceholders = SpectateSafety.messagesHandler.getCustomPlaceholders();
        }
    }
}
