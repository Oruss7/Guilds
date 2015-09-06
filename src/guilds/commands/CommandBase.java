package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import guilds.utilities.*;
import org.bukkit.plugin.*;
import guilds.*;
import org.bukkit.*;

public class CommandBase {

    private GuildsBasic plugin;

    public CommandBase(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player) sender);
        } else {
            this.plugin.sendConsole("CONSOLE_ERROR");
        }
    }

    private void Player(final String[] args, final Player player) {
        if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
            if (player.hasPermission("guilds.user.base")) {
                if (Utils.isCooldown(player.getUniqueId())) {
                    player.sendMessage(this.plugin.getMessage("TELEPORTATION_COOLDOWN"));
                    return;
                }
                final User user = this.plugin.getUser(player.getUniqueId());
                if (user != null) {
                    final Guild guild = this.plugin.getGuild(user.getGuild());
                    if (guild != null) {
                        final Location location = guild.getLocation();
                        location.getChunk().load();
                        final Chunk c = location.getChunk();
                        location.getWorld().loadChunk(c);
                        location.getWorld().refreshChunk(c.getX(), c.getZ());
                        location.setY((double) location.getWorld().getHighestBlockYAt(location));
                        if (!Utils.checkLocation(player, location)) {
                            player.sendMessage(this.plugin.getMessage("CAN_NOT_TELEPORT_HERE"));
                            return;
                        }
                        player.teleport(location);
                        player.sendMessage(this.plugin.getMessage("TELEPORTATION"));
                        Utils.addCooldown((Plugin) this.plugin, player.getUniqueId(), this.plugin.getConfig().getInt("config.BASE.COOLDOWN"));
                    } else {
                        player.sendMessage(this.plugin.getMessage("NOT_IN_GUILD"));
                    }
                } else {
                    player.sendMessage(this.plugin.getMessage("NOT_IN_GUILD"));
                }
            } else {
                player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
            }
        }
    }
}
