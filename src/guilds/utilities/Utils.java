package guilds.utilities;

import java.util.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class Utils
{
    private static ArrayList<UUID> cooldown;
    
    public static void addCooldown(final Plugin pl, final UUID player, final int timeInSec) {
        Utils.cooldown.add(player);
        new BukkitRunnable() {
            public void run() {
                Utils.cooldown.remove(player);
            }
        }.runTaskLater(pl, (long)(timeInSec * 20));
    }
    
    public static boolean isCooldown(final UUID player) {
        return Utils.cooldown.contains(player);
    }
    
    public static boolean checkLocation(final Player player, final Location location) {
        return location.getBlock().getType() == Material.AIR || location.getBlock() == null;
    }
    
    static {
        Utils.cooldown = new ArrayList<UUID>();
    }
}
