package guilds.listeners;

import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import guilds.*;
import org.bukkit.event.*;

public class PlayerListener implements Listener
{
    private GuildsBasic plugin;
    
    public PlayerListener(final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        if (this.plugin.getConfig().getBoolean("config.ENABLE_BASE_ON_DEATH")) {
            final Player player = event.getPlayer();
            if (this.plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
                final User user = this.plugin.getUser(player.getUniqueId());
                if (user != null && user.getGuild() != null) {
                    final Guild guild = this.plugin.getGuild(user.getGuild());
                    if (guild != null) {
                        player.teleport(guild.getLocation());
                    }
                }
            }
        }
    }
}
