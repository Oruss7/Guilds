package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import guilds.*;
import java.util.*;

public class CommandLeave
{
    private GuildsBasic plugin;
    
    public CommandLeave(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.plugin.sendConsole(this.plugin.getMessage("CONSOLE_ERROR"));
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (player.hasPermission("guilds.user.leave")) {
            if (this.plugin.getConfig().getBoolean("config.ENABLE_CHANGE_GUILD")) {
                final User user = this.plugin.getUser(player.getUniqueId());
                if (user == null || !user.haveGuild()) {
                    player.sendMessage(this.plugin.getMessage("NOT_IN_GUILD"));
                    return;
                }
                final Guild guild = this.plugin.getGuild(user.getGuild());
                guild.removeMember(user);
                user.setGuild(null);
                user.setJoined(null);
                if (user.getRank().equalsIgnoreCase(Rank.LEAD.toString())) {
                    guild.setLead(null);
                }
                user.setRank(null);
                if (user.getInvitation() == null) {
                    this.plugin.removePlayer(user);
                }
                this.plugin.getConfiguration().savePlayers();
                for (final User member : guild.getListMember()) {
                    if (member.getOfflinePlayer().isOnline()) {
                        member.getPlayer().sendMessage(this.plugin.getMessage("GUILD_AS_LEAVE").replaceAll("%player%", player.getDisplayName()));
                    }
                }
                player.sendMessage(this.plugin.getMessage("GUILD_LEAVE").replaceAll("%player", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
            }
            else {
                player.sendMessage(this.plugin.getMessage("GUILD_CHOSEN"));
            }
        }
        else {
            player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
        }
    }
}
