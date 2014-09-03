package guilds.utilities;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Utils {

    private static ArrayList<UUID> cooldown = new ArrayList<>();

    public static void addCooldown(Plugin pl, final UUID player, int timeInSec) {
        cooldown.add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                cooldown.remove(player);
            }
        }.runTaskLater(pl, timeInSec * 20);
    }
    
    public static boolean isCooldown(UUID player){
        return cooldown.contains(player);
    }

     public static boolean checkLocation(Player player, Location location) {
        return location.getBlock().getType() == Material.AIR || location.getBlock() == null;
    }
}
