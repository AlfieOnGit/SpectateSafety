package me.alfiejay.spectatesafety.command;

import me.alfiejay.spectatesafety.SpectateSafety;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public final class CommandManager {

    private @NotNull final Set<Command> commands = new HashSet<>();
    private @NotNull final SpectateSafety plugin;

    public CommandManager(@NotNull final SpectateSafety plugin) {
        this.plugin = plugin;

        registerCommand(new SpecCommand(plugin));
        registerCommand(new UnspecCommand(plugin));
    }

    private void registerCommand(@NotNull final Command command) {
        commands.add(command);
        plugin.getServer().getCommandMap().register("spectatesafety", command);
    }

    public void reload() {
        for (Command c : commands) {
            c.reload(plugin);
        }
    }
}
