package me.alfiejay.spectatesafety.message;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

public enum Message {

    NO_PERMISSION("{PERMISSION}"),
    CANNOT_TELEPORT,
    VALID_SUBCOMMANDS("{SUBCOMMANDS}"),
    FORCE_ENABLED("{SENDER]", "{TARGET}"),
    FORCE_DISABLED("{SENDER}", "{TARGET}"),

    ENABLED("{SENDER}"),
    DISABLED("{SENDER}"),
    ENABLED_FOR("{TARGET}"),
    DISABLED_FOR("{TARGET}"),
    ENABLED_GROUP("{GROUP}", "{COUNT}"),
    DISABLED_GROUP("{GROUP}", "{COUNT}"),
    ENABLED_ALL("{COUNT}"),
    DISABLED_ALL("{COUNT}"),

    ALREADY_ENABLED,
    ALREADY_DISABLED,
    NOT_PLAYER("{TARGET}"),
    NOT_GROUP("{GROUP}"),
    ALREADY_ENABLED_FOR("{TARGET}"),
    ALREADY_DISABLED_FOR("{TARGET}"),

    POINT_SET,
    UNPOINT_SET,
    WORLD_POINT_SET("{WORLD}"),
    WORLD_UNPOINT_SET("{WORLD}"),
    POINT_CLEARED,
    UNPOINT_CLEARED,
    WORLD_POINT_CLEARED("{WORLD}"),
    WORLD_UNPOINT_CLEARED("{WORLD}"),
    NO_POINT,
    NO_WORLD_POINT("{WORLD}"),
    NO_UNPOINT,
    NO_WORLD_UNPOINT("{WORLD}"),
    NOT_WORLD("{WORLD}"),

    WG_POINT_SET("{REGION}"),
    WG_UNPOINT_SET("{REGION}"),
    WG_POINT_CLEARED("{REGION}"),
    WG_UNPOINT_CLEARED("{REGION}"),
    WG_NOT_REGION("{REGION}"),
    WG_NO_POINT("{REGION}"),
    WG_NO_UNPOINT("{REGION}"),

    HELP_MESSAGE,
    INFO_MESSAGE,
    RELOAD_MESSAGE,
    HELP_FORMAT("{COMMAND}", "{DESCRIPTION}"),
    VERSION("{VERSION}"),
    DEPENDENCY("{DEPENDENCY}", "{FOUND}");

    private @NotNull final String text;
    private @NotNull final String[] placeholders;

    Message(String... placeholders) {
        this.placeholders = placeholders;
        String message = MessageManager.getMessage(this);
        if (message != null) text = message;
        else text = "&c&lMESSAGE MISSING";
    }

    /**
     * Get message text
     * @param args Strings to replace placeholders
     * @return TextComponent text
     */
    public TextComponent get(String... args) {
        String out = this.text;
        for (int i = 0; i < this.placeholders.length; i++) {
            if (i >= args.length) break;
            out = out.replace(placeholders[i], args[i]);
        } return LegacyComponentSerializer.legacy('&').deserialize(out);
    }
}