package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import guilds.*;
import org.bukkit.*;
import java.util.*;

public class CommandInvite
{
    private GuildsBasic plugin;
    
    public CommandInvite(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (args[0].equalsIgnoreCase("invite")) {
            if (args.length < 2) {
                player.sendMessage(this.plugin.getMessage("COMMAND_INVITE"));
                return;
            }
            if (!player.hasPermission("guilds.user.invite")) {
                player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
                return;
            }
            final User user = this.plugin.getUser(player.getUniqueId());
            if (user == null) {
                player.sendMessage(this.plugin.getMessage("NOT_IN_GUILD"));
                return;
            }
            if (user.getGuild() == null || !user.haveGuild()) {
                player.sendMessage(this.plugin.getMessage("NOT_IN_GUILD"));
                return;
            }
            final Guild guild = this.plugin.getGuild(user.getGuild());
            final OfflinePlayer playerTarget = Bukkit.getOfflinePlayer(args[1]);
            if (playerTarget == null) {
                player.sendMessage(this.plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                return;
            }
            User userTarget = this.plugin.getUser(playerTarget.getUniqueId());
            if (userTarget == null) {
                userTarget = new User(playerTarget);
                this.plugin.addPlayers(userTarget);
            }
            else if (userTarget.haveGuild()) {
                player.sendMessage(this.plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%player%", playerTarget.getName()).replaceAll("%guild%", this.plugin.getGuild(userTarget.getGuild()).getName()));
                return;
            }
            if (userTarget.getInvitation() != null) {
                player.sendMessage(this.plugin.getMessage("ALREADY_PENDING").replaceAll("%guild%", this.plugin.getGuild(userTarget.getInvitation()).getName()));
                return;
            }
            userTarget.setInvitation(guild.getId());
            if (playerTarget.isOnline()) {
                playerTarget.getPlayer().sendMessage(this.plugin.getMessage("INVITATION").replaceAll("%player%", player.getName().replaceAll("%guild%", guild.getName())));
            }
            for (final User member : guild.getListMember()) {
                if (member.getOfflinePlayer().isOnline()) {
                    member.getPlayer().sendMessage(this.plugin.getMessage("PLAYER_INVITED").replaceAll("%player%", playerTarget.getName().replaceAll("%guild%", guild.getName())));
                }
            }
            this.plugin.getConfiguration().savePlayers();
        }
        if (args[0].equalsIgnoreCase("accept")) {
            final User user = this.plugin.getUser(player.getUniqueId());
            if (user == null || user.getInvitation() == null) {
                player.sendMessage(this.plugin.getMessage("NO_PENDING"));
                return;
            }
            if (user.haveGuild()) {
                player.sendMessage(this.plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%guild%", this.plugin.getGuild(user.getGuild()).getName()));
                return;
            }
            if (!player.hasPermission("guilds.user.join")) {
                player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
                return;
            }
            final Guild guild = this.plugin.getGuild(user.getInvitation());
            if (guild == null) {
                player.sendMessage(this.plugin.getMessage("GUILD_NOT_RECOGNISED"));
                return;
            }
            user.setGuild(guild.getId());
            user.setJoined(System.currentTimeMillis());
            user.setInvitation(null);
            user.setRank(Rank.NEWBIE.toString());
            guild.addMember(user);
            for (final User member2 : guild.getListMember()) {
                if (member2.getOfflinePlayer().isOnline()) {
                    member2.getPlayer().sendMessage(this.plugin.getMessage("GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                }
            }
            this.plugin.getConfiguration().savePlayers();
        }
        if (args[0].equalsIgnoreCase("deny")) {
            final User user = this.plugin.getUser(player.getUniqueId());
            if (user == null || user.getInvitation() == null) {
                player.sendMessage(this.plugin.getMessage("NO_PENDING"));
                return;
            }
            final Guild guild = this.plugin.getGuild(user.getInvitation());
            if (guild == null) {
                player.sendMessage(this.plugin.getMessage("GUILD_NOT_RECOGNISED"));
                return;
            }
            player.sendMessage(this.plugin.getMessage("YOU_DENY_INVITATION").replace("%guild%", guild.getName()));
            for (final User member2 : guild.getListMember()) {
                if (member2.getOfflinePlayer().isOnline()) {
                    member2.getPlayer().sendMessage(this.plugin.getMessage("INVITATION_DENY").replace("%player%", player.getDisplayName()));
                }
            }
            this.plugin.removePlayer(user);
            this.plugin.getConfiguration().savePlayers();
        }
    }
    
    private void Console(final String[] args) {
        this.plugin.sendConsole(this.plugin.getMessage("CONSOLE_ERROR"));
    }
}
