package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import guilds.*;
import org.bukkit.*;
import java.util.*;

public class CommandKick
{
    private GuildsBasic plugin;
    
    public CommandKick(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (args.length >= 2) {
            Guild guild;
            if (args.length == 2) {
                final User user = this.plugin.getUser(player.getUniqueId());
                if (user == null || !user.haveGuild()) {
                    player.sendMessage(this.plugin.getMessage("NOT_IN_GUILD"));
                    return;
                }
                guild = this.plugin.getGuild(user.getGuild());
            }
            else {
                guild = this.plugin.getGuild(args[2]);
                if (guild == null) {
                    player.sendMessage(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                }
            }
            if (player.hasPermission("guilds.admin.kick")) {
                final OfflinePlayer oplayer = Bukkit.getOfflinePlayer(args[1]);
                if (oplayer == null) {
                    player.sendMessage(this.plugin.getMessage("PLAYER_NOT_RECOGNISED"));
                    return;
                }
                final Player playerTarget = oplayer.getPlayer();
                final User userTarget = this.plugin.getUser(playerTarget.getUniqueId());
                if (userTarget == null || !userTarget.getGuild().equals(guild.getId())) {
                    player.sendMessage(this.plugin.getMessage("CAN_NOT_REMOVE"));
                    return;
                }
                guild.removeMember(userTarget);
                userTarget.setGuild(null);
                userTarget.setJoined(null);
                if (userTarget.getRank().equalsIgnoreCase(Rank.LEAD.toString())) {
                    guild.setLead(null);
                }
                userTarget.setRank(null);
                if (userTarget.getInvitation() == null) {
                    this.plugin.removePlayer(userTarget);
                }
                for (final User member : guild.getListMember()) {
                    if (member.getOfflinePlayer().isOnline()) {
                        member.getPlayer().sendMessage(this.plugin.getMessage("PLAYER_REMOVED_FROM_GUILD").replaceAll("%player%", player.getDisplayName()).replaceAll("%playerTarget%", playerTarget.getDisplayName()));
                    }
                }
                if (playerTarget.isOnline()) {
                    playerTarget.sendMessage(this.plugin.getMessage("YOU_REMOVED_FROM_GUILD").replaceAll("%player", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                }
                this.plugin.getConfiguration().savePlayers();
            }
            else {
                player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
            }
        }
        else {
            player.sendMessage(this.plugin.getMessage("COMMAND_KICK"));
        }
    }
    
    private void Console(final String[] args) {
        if (args.length > 1) {
            final OfflinePlayer oplayer = Bukkit.getOfflinePlayer(args[1]);
            if (oplayer == null) {
                this.plugin.sendConsole(this.plugin.getMessage("PLAYER_NOT_RECOGNISED"));
                return;
            }
            final Guild guild = this.plugin.getGuild(args[2]);
            if (guild == null) {
                this.plugin.sendConsole(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
            }
            final Player playerTarget = oplayer.getPlayer();
            final User userTarget = this.plugin.getUser(playerTarget.getUniqueId());
            if (userTarget == null || !userTarget.getGuild().equals(guild.getId())) {
                this.plugin.sendConsole(this.plugin.getMessage("CAN_NOT_REMOVE"));
                return;
            }
            guild.removeMember(userTarget);
            userTarget.setGuild(null);
            userTarget.setJoined(null);
            if (userTarget.getRank().equalsIgnoreCase(Rank.LEAD.toString())) {
                guild.setLead(null);
            }
            userTarget.setRank(null);
            if (userTarget.getInvitation() == null) {
                this.plugin.removePlayer(userTarget);
            }
            for (final User member : guild.getListMember()) {
                if (member.getOfflinePlayer().isOnline()) {
                    member.getPlayer().sendMessage(this.plugin.getMessage("PLAYER_REMOVED_FROM_GUILD").replaceAll("%player%", "Console").replaceAll("%playerTarget%", playerTarget.getDisplayName()));
                }
            }
            if (playerTarget.isOnline()) {
                playerTarget.sendMessage(this.plugin.getMessage("YOU_REMOVED_FROM_GUILD").replaceAll("%player", "Console").replaceAll("%guild%", guild.getName()));
            }
            this.plugin.getConfiguration().savePlayers();
            this.plugin.getConfiguration().saveGuilds();
        }
        else {
            this.plugin.sendConsole(this.plugin.getMessage("COMMAND_KICK"));
        }
    }
}
