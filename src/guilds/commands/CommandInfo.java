package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.util.*;
import java.text.*;
import org.bukkit.*;
import guilds.*;
import java.sql.Timestamp;

public class CommandInfo
{
    private GuildsBasic plugin;
    
    public CommandInfo(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (player.hasPermission("guilds.user.info")) {
            OfflinePlayer target;
            if (args.length <= 1) {
                target = (OfflinePlayer)player;
            }
            else {
                target = (OfflinePlayer)Bukkit.getOfflinePlayer(args[1]).getPlayer();
            }
            if (target != null) {
                final User user = this.plugin.getUser(target.getUniqueId());
                if (user != null) {
                    final Guild guild = this.plugin.getGuild(user.getGuild());
                    if (guild != null) {
                        final Timestamp stamp = new Timestamp(user.getJoined());
                        final Date date = new Date(stamp.getTime());
                        final SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        player.sendMessage(ChatColor.YELLOW + "==================================");
                        player.sendMessage(ChatColor.AQUA + target.getName());
                        player.sendMessage(ChatColor.YELLOW + this.plugin.getMessage("GUILD") + " : " + ChatColor.AQUA + guild.getName());
                        player.sendMessage(ChatColor.YELLOW + this.plugin.getMessage("RANK") + " : " + ChatColor.AQUA + Rank.valueOf(user.getRank()).getRank());
                        player.sendMessage(ChatColor.YELLOW + this.plugin.getMessage("JOINED") + " : " + ChatColor.AQUA + dt.format(date));
                        player.sendMessage(ChatColor.YELLOW + "==================================");
                    }
                    else {
                        player.sendMessage(this.plugin.getMessage("PLAYER_NOT_IN_GUILD").replaceAll("%player%", target.getName()));
                    }
                }
                else {
                    player.sendMessage(this.plugin.getMessage("PLAYER_NOT_IN_GUILD").replaceAll("%player%", target.getName()));
                }
            }
            else {
                player.sendMessage(this.plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
            }
        }
        else {
            player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
        }
    }
    
    private void Console(final String[] args) {
        if (args.length <= 1) {
            this.plugin.sendConsole(this.plugin.getMessage("COMMAND_INFO").replaceAll("&([0-9a-fk-or])", ""));
            return;
        }
        final Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
        if (target != null) {
            final User user = this.plugin.getUser(target.getUniqueId());
            if (user != null) {
                final Guild guild = this.plugin.getGuild(user.getGuild());
                if (guild != null) {
                    final Timestamp stamp = new Timestamp(user.getJoined());
                    final Date date = new Date(stamp.getTime());
                    final SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    this.plugin.sendConsole("==================================");
                    this.plugin.sendConsole(target.getDisplayName());
                    this.plugin.sendConsole(this.plugin.getMessage("GUILD") + " : " + guild.getName());
                    this.plugin.sendConsole(this.plugin.getMessage("RANK") + " : " + user.getRank());
                    this.plugin.sendConsole(this.plugin.getMessage("JOINED") + " : " + dt.format(date));
                    this.plugin.sendConsole("==================================");
                }
                else {
                    this.plugin.sendConsole(this.plugin.getMessage("PLAYER_NOT_IN_GUILD").replaceAll("%player%", args[1]));
                }
            }
            else {
                this.plugin.sendConsole(this.plugin.getMessage("PLAYER_NOT_IN_GUILD").replaceAll("%player%", args[1]));
            }
        }
        else {
            this.plugin.sendConsole(this.plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
        }
    }
}
