package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import guilds.*;
import java.util.*;

class CommandChat
{
    private GuildsBasic plugin;
    
    public CommandChat(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (player.hasPermission("guilds.user.chat")) {
            final User user = this.plugin.getUser(player.getUniqueId());
            if (user != null) {
                final Guild guild = this.plugin.getGuild(user.getGuild());
                if (guild != null) {
                    StringBuilder message = new StringBuilder();
                    for (int i = 0; i < args.length; ++i) {
                        message = message.append(" ").append(args[i]);
                    }
                    for (final User member : guild.getListMember()) {
                        if (member != null && member.getPlayer() != null && member.getPlayer().isOnline()) {
                            member.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GREEN + "[" + this.plugin.getMessage("GUILD") + ChatColor.GREEN + "] " + ChatColor.WHITE + player.getDisplayName() + ":" + ChatColor.WHITE + message.toString()));
                        }
                    }
                }
                else {
                    player.sendMessage(this.plugin.getMessage("NOT_IN_GUILD"));
                }
            }
            else {
                player.sendMessage(this.plugin.getMessage("NOT_IN_GUILD"));
            }
        }
        else {
            player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
        }
    }
    
    private void Console(final String[] args) {
        this.plugin.sendConsole(this.plugin.getMessage("CONSOLE_ERROR"));
    }
}
