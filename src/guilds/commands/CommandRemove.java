package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import guilds.*;
import java.util.*;

public class CommandRemove
{
    private GuildsBasic plugin;
    
    public CommandRemove(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (args.length > 1) {
            final StringBuilder guildName = new StringBuilder(args[1]);
            for (int arg = 2; arg < args.length; ++arg) {
                guildName.append(" ").append(args[arg]);
            }
            if (player.hasPermission("guilds.admin.remove")) {
                final Guild guild = this.plugin.getGuild(guildName.toString());
                if (guild != null) {
                    for (final User member : guild.getListMember()) {
                        if (member.getOfflinePlayer().isOnline()) {
                            member.getPlayer().sendMessage(this.plugin.getMessage("GUILD_DELETED").replaceAll("%guild%", guildName.toString()));
                        }
                        member.setGuild(null);
                        member.setJoined(null);
                        member.setRank(null);
                        if (member.getInvitation() == null) {
                            this.plugin.removePlayer(member);
                        }
                    }
                    this.plugin.removeGuild(guild);
                    this.plugin.getConfiguration().saveGuilds();
                    this.plugin.getConfiguration().savePlayers();
                }
                else {
                    player.sendMessage(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", guildName.toString()));
                }
            }
            else {
                player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
            }
        }
        else {
            player.sendMessage(this.plugin.getMessage("COMMAND_REMOVE"));
        }
    }
    
    private void Console(final String[] args) {
        if (args.length > 1) {
            final StringBuilder guildName = new StringBuilder(args[1]);
            for (int arg = 2; arg < args.length; ++arg) {
                guildName.append(" ").append(args[arg]);
            }
            final Guild guild = this.plugin.getGuild(guildName.toString());
            if (guild != null) {
                for (final User member : guild.getListMember()) {
                    if (member.getOfflinePlayer().isOnline()) {
                        member.getPlayer().sendMessage(this.plugin.getMessage("GUILD_DELETED").replaceAll("%guild%", guildName.toString()));
                    }
                    guild.removeMember(member);
                    if (member.getInvitation() == null) {
                        this.plugin.removePlayer(member);
                    }
                }
                this.plugin.removeGuild(guild);
                this.plugin.getConfiguration().saveGuilds();
                this.plugin.getConfiguration().savePlayers();
            }
            else {
                this.plugin.sendConsole(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild", guildName.toString()));
            }
        }
        else {
            this.plugin.sendConsole(this.plugin.getMessage("COMMAND_REMOVE"));
        }
    }
}
