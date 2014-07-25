package guilds.listeners;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.User;
import guilds.utilities.Settings;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private GuildsBasic GuildsBasic;

    public PlayerListener(GuildsBasic GuildsBasic) {
        this.GuildsBasic = GuildsBasic;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        GuildsBasic.PlayerUser.put(p, GuildsBasic.newPlayerUser(p));
        Guild g = GuildsBasic.getPlayerGuild(p);
        World w = p.getWorld();
        Biome b = p.getLocation().getBlock().getBiome();
        if (g != null) {
            g.addOnline();
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (GuildsBasic.getIntSetting(Settings.SET_BASE_TP_DELAY) > 0) {
            if (GuildsBasic.BaseDelay.containsKey(p)) {
                Bukkit.getScheduler().cancelTask(GuildsBasic.BaseDelay.get(p));
                GuildsBasic.BaseDelay.remove(p);
            }
        }

        Guild g = GuildsBasic.getPlayerGuild(p);

        if (g != null) {
            g.subtractOnline();
        }

        if (GuildsBasic.PlayerUser.containsKey(p)) {
            GuildsBasic.PlayerUser.remove(p);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerKick(PlayerKickEvent event) {
        Player p = event.getPlayer();
        if (GuildsBasic.getIntSetting(Settings.SET_BASE_TP_DELAY) > 0) {
            if (GuildsBasic.BaseDelay.containsKey(p)) {
                Bukkit.getScheduler().cancelTask(GuildsBasic.BaseDelay.get(p));
                GuildsBasic.BaseDelay.remove(p);
            }
        }
        if (GuildsBasic.PlayerUser.containsKey(p)) {
            GuildsBasic.PlayerUser.remove(p);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Player p = event.getPlayer();
        User u = GuildsBasic.getPlayerUser(p);
        Guild g = GuildsBasic.getPlayerGuild(p);

        if (GuildsBasic.getIntSetting(Settings.SET_BASE_TP_DELAY) > 0) {
            if (GuildsBasic.BaseDelay.containsKey(p)) {
                Bukkit.getScheduler().cancelTask(GuildsBasic.BaseDelay.get(p));
                GuildsBasic.BaseDelay.remove(p);
            }
        }

        if (GuildsBasic.getEnabled(Settings.ENABLE_BASE_ON_DEATH)) {
            if (g != null) {
                if (g.getWorlds().contains(p.getLocation().getWorld())) {
                    event.setRespawnLocation(g.getBase());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerDeath(PlayerDeathEvent event) {

        if (event.getEntity() instanceof Player) {
            Player p = event.getEntity();
            User u = GuildsBasic.getPlayerUser(p);
            if (GuildsBasic.getIntSetting(Settings.SET_BASE_TP_DELAY) > 0) {
                if (GuildsBasic.BaseDelay.containsKey(p)) {
                    Bukkit.getScheduler().cancelTask(GuildsBasic.BaseDelay.get(p));
                    GuildsBasic.BaseDelay.remove(p);
                }
            }
        }
    }
}
