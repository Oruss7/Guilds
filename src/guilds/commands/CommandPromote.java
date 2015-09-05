package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import guilds.*;
import java.util.*;

public class CommandPromote
{
    private final GuildsBasic plugin;
    
    public CommandPromote(final CommandSender sender, final String[] args, final GuildsBasic GuildsBasic) {
        this.plugin = GuildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player sender) {
        if (args.length > 1) {
            final User user = this.plugin.getUser(sender.getUniqueId());
            if (user == null) {
                sender.sendMessage(this.plugin.getMessage("NOT_IN_GUILD").replaceAll("%player%", sender.getDisplayName()));
                return;
            }
            if ((sender.hasPermission("guilds.admin.promote") && args[0].equalsIgnoreCase("promote")) || (sender.hasPermission("guilds.admin.demote") && args[0].equalsIgnoreCase("demote"))) {
                final OfflinePlayer playerTarget = Bukkit.getOfflinePlayer(args[1]);
                if (playerTarget != null) {
                    final User userTarget = this.plugin.getUser(playerTarget.getUniqueId());
                    if (userTarget == null) {
                        sender.sendMessage(this.plugin.getMessage("NOT_IN_GUILD").replaceAll("%player%", args[1]));
                        return;
                    }
                    final Guild guild = this.plugin.getGuild(userTarget.getGuild());
                    if (guild != null) {
                        if (userTarget.getGuild() != user.getGuild()) {
                            sender.sendMessage(this.plugin.getMessage("PLAYER_NOT_IN_YOUR_GUILD").replaceAll("%player%", args[1]).replaceAll("%guild%", this.plugin.getGuild(user.getGuild()).getName()));
                            return;
                        }
                        if (args[0].equalsIgnoreCase("promote")) {
                            switch (userTarget.promote()) {
                                case -1: {
                                    sender.sendMessage(this.plugin.getMessage("RANK_MAX").replaceAll("%player%", args[1]).replace("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                    return;
                                }
                                case -2: {
                                    sender.sendMessage(this.plugin.getMessage("RANK_UNKNOW").replaceAll("%player%", args[1]).replace("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                    return;
                                }
                                case 1: {
                                    sender.sendMessage(this.plugin.getMessage("RANK_ALREADY_GIVE").replaceAll("%player%", Bukkit.getOfflinePlayer(guild.getLead()).getName()).replace("%rank%", userTarget.getRank()));
                                    return;
                                }
                            }
                        }
                        else if (args[0].equalsIgnoreCase("demote")) {
                            switch (userTarget.demote()) {
                                case -1: {
                                    sender.sendMessage(this.plugin.getMessage("RANK_MIN").replaceAll("%player%", args[1]).replace("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                    return;
                                }
                                case -2: {
                                    sender.sendMessage(this.plugin.getMessage("RANK_UNKNOW").replaceAll("%player%", args[1]).replace("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                    return;
                                }
                            }
                        }
                        this.plugin.getConfiguration().savePlayers();
                        for (final User member : guild.getListMember()) {
                            if (member.getOfflinePlayer().isOnline()) {
                                if (args[0].equalsIgnoreCase("promote")) {
                                    member.getPlayer().sendMessage(this.plugin.getMessage("PLAYER_PROMOTE").replaceAll("%player%", args[1]).replaceAll("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                }
                                else {
                                    member.getPlayer().sendMessage(this.plugin.getMessage("PLAYER_DEMOTE").replaceAll("%player%", args[1]).replaceAll("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                }
                            }
                        }
                    }
                    else {
                        sender.sendMessage(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                    }
                }
                else {
                    sender.sendMessage(this.plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                }
            }
            else {
                sender.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
            }
        }
        else {
            sender.sendMessage(this.plugin.getMessage("COMMAND_PROMOTE"));
        }
    }
    
    private void Console(final String[] args) {
        if (args.length > 2) {
            final OfflinePlayer opl = Bukkit.getOfflinePlayer(args[1]);
            if (!opl.isOnline()) {
                this.plugin.sendConsole(this.plugin.getMessage("PLAYER_OFFLINE").replaceAll("%player%", args[1]));
            }
            else {
                final Player player = opl.getPlayer();
                if (player != null) {
                    User user = this.plugin.getUser(player.getUniqueId());
                    final Guild guild = this.plugin.getGuild(args[2]);
                    if (guild != null) {
                        if (user != null) {
                            if (user.haveGuild()) {
                                this.plugin.sendConsole(this.plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", this.plugin.getGuild(user.getGuild()).getName()));
                                return;
                            }
                            user.setGuild(guild.getId());
                            user.setRank(Rank.NEWBIE.toString());
                            user.setJoined(System.currentTimeMillis());
                            if (user.getInvitation().equals(guild.getId())) {
                                user.setInvitation(null);
                            }
                        }
                        else {
                            user = new User(player.getUniqueId(), guild.getId(), Rank.NEWBIE.toString(), System.currentTimeMillis(), null);
                            this.plugin.addPlayers(user);
                        }
                        guild.addMember(user);
                        this.plugin.getConfiguration().savePlayers();
                        this.plugin.sendConsole(this.plugin.getMessage("PLAYER_GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                        for (final User member : guild.getListMember()) {
                            if (member.getOfflinePlayer().isOnline()) {
                                member.getPlayer().sendMessage(this.plugin.getMessage("PLAYER_GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                            }
                        }
                    }
                    else {
                        this.plugin.sendConsole(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                    }
                }
                else {
                    this.plugin.sendConsole(this.plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                }
            }
        }
        else {
            this.plugin.sendConsole(this.plugin.getMessage("COMMAND_ADD"));
        }
    }
}
