package spectatesafety;

public enum Messages {

    NO_PERMISSION,
    VALID_SUBCOMMANDS,

    ENABLED,
    DISABLED,
    ALREADY_ENABLED,
    ALREADY_DISABLED,
    ENABLED_FOR,
    DISABLED_FOR,
    ENABLED_ALL,
    DISABLED_ALL,
    NOT_PLAYER,
    ALREADY_ENABLED_FOR,
    ALREADY_DISABLED_FOR,

    POINT_SET,
    UNPOINT_SET,
    POINT_CLEARED,
    UNPOINT_CLEARED,
    NO_POINT,
    NO_UNPOINT;

    private final String message;

    Messages() {
        String path = this.name().replace("_","-").toLowerCase();
        this.message = Main.messagesHandler.get(path);
    }

    @Override
    public String toString() {
        return message;
    }
}
