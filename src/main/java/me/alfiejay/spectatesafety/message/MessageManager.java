package me.alfiejay.spectatesafety.message;

import me.alfiejay.spectatesafety.FileHandler;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public final class MessageManager extends FileHandler {

    private @NotNull static final HashMap<String, String> customPlaceholders = new HashMap<>();
    private @NotNull static final HashMap<String, String> messages = new HashMap<>();

    public MessageManager(Plugin plugin) {
        super(plugin, "messages.yml");
        loadResource();
        load();
    }

    /**
     * Load messages.yml into the manager
     */
    public void load() {
        loadCustomPlaceholders();
        loadMessages();
    }

    /**
     * Load all custom placeholders from messages.yml into manager
     */
    private void loadCustomPlaceholders() {
        ConfigurationSection sect = config.getConfigurationSection("custom-placeholders");
        if (sect == null) return;
        for (String k : sect.getKeys(false)) {
            String placeholder = config.getString("custom-placeholders." + k);
            if (placeholder != null) customPlaceholders.put("{" + k.toUpperCase() + "}", placeholder);
        }
    }

    /**
     * Load all messages from messages.yml into manager
     * WARNING: Always run loadCustomPlaceholders() before this to avoid outdated custom placeholders
     */
    private void loadMessages() {
        for (String s : config.getKeys(false)) {
            if (s.equals("custom-placeholders")) continue;
            String hold = config.getString(s);
            if (hold == null) continue;
            for (String p : customPlaceholders.keySet()) hold = hold.replace(p, customPlaceholders.get(p));
            messages.put(s, hold);
        }
    }

    /**
     * Get text assigned to message enum
     * @param message Message enum
     * @return String message
     */
    public @Nullable static String getMessage(Message message) {
        return messages.get(message.name().replace("_", "-").toLowerCase());
    }
}
