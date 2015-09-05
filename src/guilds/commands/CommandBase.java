package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.User;
import guilds.utilities.Utils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBase {

    private GuildsBasic plugin;

    public CommandBase(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            plugin.sendConsole("CONSOLE_ERROR");
        }
    }

    private void Player(String[] args, Player player) {

        if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
            if (player.hasPermission("guilds.user.base")) {
                if (Utils.isCooldown(player.getUniqueId())) {
                    player.sendMessage(plugin.getMessage("TELEPORTATION_COOLDOWN"));
                    return;
                }
                User user = plugin.getUser(player.getUniqueId());
                if (user != null) {
                    Guild guild = plugin.getGuild(user.getGuild());
                    if (guild != null) {
    
                        Location location = guild.getLocation();
                        location.getChunk().load();
                        Chunk c = location.getChunk();
                        location.getWorld().loadChunk(c);
                        location.getWorld().refreshChunk(c.getX(), c.getZ());
                        location.setY(location.getWorld().getHighestBlockYAt(location));
                        if (!Utils.checkLocation(player, location)) {
                            player.sendMessage(plugin.getMessage("CAN_NOT_TELEPORT_HERE"));
                            return;
                        } else {
                            player.teleport(location);
                            player.sendMessage(plugin.getMessage("TELEPORTATION"));
                            Utils.addCooldown(plugin, player.getUniqueId(), plugin.getConfig().getInt("config.BASE.COOLDOWN"));
                        }
    
                    } else {
                        player.sendMessage(plugin.getMessage("NOT_IN_GUILD"));
                    }
                } else {
                    player.sendMessage(plugin.getMessage("NOT_IN_GUILD"));
                }
            } else {
                player.sendMessage(plugin.getMessage("NO_PERMISSION"));
            }
        }

    }
}
