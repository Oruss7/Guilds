package guilds.listeners;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.User;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private GuildsBasic plugin;

    public PlayerListener(GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (plugin.getConfig().getBoolean("config.ENABLE_BASE_ON_DEATH")) {
            // le respawn à la base est activé
            Player player = event.getPlayer();
            if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
                User user = plugin.getUser(player.getUniqueId());
                if (user != null && user.getGuild() != null) {
                    // le joueur est enregistré et appartient à une guilde 
                    Guild guild = plugin.getGuild(user.getGuild());
                    if (guild != null) {
                        player.teleport(guild.getLocation());
                    }
                }
            }
        }
    }
}
