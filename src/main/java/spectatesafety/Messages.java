package spectatesafety;

import org.bukkit.Bukkit;

public enum Messages {

//    NO_PERMISSION {
//        @Override
//        public String toString() {
//            return "this is one";
//        }
//    }

    NO_PERMISSION ("no-permission");

    private final String message;

    Messages(String path) {
        this.message = Main.messagesHandler.get(path);
    }

    @Override
    public String toString() {
        return message;
    }
}
