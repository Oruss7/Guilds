package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.Rank;
import guilds.User;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInfo {

    private GuildsBasic plugin;

    public CommandInfo(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {

        if (player.hasPermission("guilds.user.info")) {

            OfflinePlayer target;

            if (!(args.length > 1)) {
                target = player;
            } else {
                target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
            }

            if (target != null) {
                User user = plugin.getUser(target.getUniqueId());
                if (user != null) {
                    Guild guild = plugin.getGuild(user.getGuild());
                    if (guild != null) {
                        Timestamp stamp = new Timestamp(user.getJoined());
                        Date date = new Date(stamp.getTime());
                        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        player.sendMessage(ChatColor.YELLOW+ "==================================");
                        player.sendMessage(ChatColor.AQUA + target.getName());
                        player.sendMessage(ChatColor.YELLOW + plugin.getMessage("GUILD") + " : " + ChatColor.AQUA + guild.getName());
                        player.sendMessage(ChatColor.YELLOW + plugin.getMessage("RANK") + " : " + ChatColor.AQUA + Rank.valueOf(user.getRank()).getRank());
                        player.sendMessage(ChatColor.YELLOW + plugin.getMessage("JOINED") + " : " + ChatColor.AQUA + dt.format(date));
                        player.sendMessage(ChatColor.YELLOW+ "==================================");
                    } else {
                        player.sendMessage(plugin.getMessage("PLAYER_NOT_IN_GUILD").replaceAll("%player%", target.getName()));
                    }
                } else {
                    player.sendMessage(plugin.getMessage("PLAYER_NOT_IN_GUILD").replaceAll("%player%", target.getName()));
                }
            } else {
                player.sendMessage(plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
            }
        } else {
            player.sendMessage(plugin.getMessage("NO_PERMISSION"));
        }
    }

    private void Console(String[] args) {

        if (!(args.length > 1)) {
             plugin.sendConsole( plugin.getMessage("COMMAND_INFO").replaceAll("&([0-9a-fk-or])", ""));
             return;
        }
        
        Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
       
        if (target != null) {
                User user = plugin.getUser(target.getUniqueId());
                if (user != null) {
                    Guild guild = plugin.getGuild(user.getGuild());
                    if (guild != null) {
                        Timestamp stamp = new Timestamp(user.getJoined());
                        Date date = new Date(stamp.getTime());
                        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        plugin.sendConsole(  "==================================");
                        plugin.sendConsole( target.getDisplayName());
                        plugin.sendConsole( plugin.getMessage("GUILD") + " : " + guild.getName());
                        plugin.sendConsole( plugin.getMessage("RANK") + " : " + user.getRank());
                        plugin.sendConsole( plugin.getMessage("JOINED") + " : " + dt.format(date));
                        plugin.sendConsole(  "==================================");
                    } else {
                        plugin.sendConsole( plugin.getMessage("PLAYER_NOT_IN_GUILD").replaceAll("%player%", args[1]));
                    }
                } else {
                    plugin.sendConsole( plugin.getMessage("PLAYER_NOT_IN_GUILD").replaceAll("%player%", args[1]));
                }
            } else {
                plugin.sendConsole( plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
            }
    }
}
