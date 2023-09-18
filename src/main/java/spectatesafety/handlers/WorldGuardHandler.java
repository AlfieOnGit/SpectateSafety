package spectatesafety.handlers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class WorldGuardHandler extends FileHandler {

    private final WorldGuard wg;
    private RegionContainer container;

    public WorldGuardHandler(Plugin plugin) {
        super(plugin, "regionpoints.yml");
        wg = WorldGuard.getInstance();
        if (wg == null) return;
        container = wg.getPlatform().getRegionContainer();
    }

    public @Nullable ProtectedRegion getRegion(World world, String name) {
        RegionManager manager = container.get(BukkitAdapter.adapt(world));
        if (manager == null) return null;
        else return manager.getRegion(name);
    }

    /**
     * Get all regions a player is in
     * @param player Input player
     * @return Region List
     */
    public @NotNull List<ProtectedRegion> getRegions(Player player) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        RegionManager manager = container.get(BukkitAdapter.adapt(world));
        if (manager == null) throw new NullPointerException();
        List<ProtectedRegion> out = new ArrayList<>();
        for (ProtectedRegion r : manager.getRegions().values())
            if (r.contains(BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()))) {
                out.add(r);
            }
        return out;
    }

    public void saveSpecPoint(World world, ProtectedRegion region, Location location) {
        config.set(world.getName() + "." + region.getId() + ".spec", location);
        save();
    }

    public void saveUnspecPoint(World world, ProtectedRegion region, Location location) {
        config.set(world.getName() + "." + region.getId() + ".unspec", location);
        save();
    }

    public @Nullable Location getSpecPoint(World world, ProtectedRegion region) {
        return config.getLocation(world.getName() + "." + region.getId() + ".spec");
    }

    public @Nullable Location getSpecPoint(Player player) {
        World world = player.getWorld();
        for (ProtectedRegion r : getRegions(player)) {
            Location loc = getSpecPoint(world, r);
            if (loc != null) return loc;
        }
        return null;
    }

    public @Nullable Location getUnspecPoint(World world, ProtectedRegion region) {
        return config.getLocation(world.getName() + "." + region.getId() + ".unspec");
    }
}
