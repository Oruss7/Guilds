package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.Rank;
import guilds.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKick {

    private GuildsBasic plugin;

    public CommandKick(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {
        if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
            if (args.length >= 2) {
    
                Guild guild;
                if (args.length == 2) {
                    // pas de guilde = guilde du joueur actuel
                    User user = plugin.getUser(player.getUniqueId());
                    if (user == null || !user.haveGuild()) {
                        player.sendMessage(plugin.getMessage("NOT_IN_GUILD"));
                        return;
                    }
                    guild = plugin.getGuild(user.getGuild());
                } else {
                    guild = plugin.getGuild(args[2]);
                    if (guild == null) {
                        player.sendMessage(plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                    }
                }
    
                if (player.hasPermission("guilds.admin.kick")) {
                    OfflinePlayer oplayer = Bukkit.getOfflinePlayer(args[1]);
                    if (oplayer == null) {
                        player.sendMessage(plugin.getMessage("PLAYER_NOT_RECOGNISED"));
                        return;
                    }
    
                    Player playerTarget = oplayer.getPlayer();
    
                    User userTarget = plugin.getUser(playerTarget.getUniqueId());
                    if (userTarget == null || !userTarget.getGuild().equals(guild.getId())) {
                        player.sendMessage(plugin.getMessage("CAN_NOT_REMOVE"));
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
                        plugin.removePlayer(userTarget);
                    }
    
                    for (User member : guild.getListMember()) {
                        if (member.getOfflinePlayer().isOnline() && (plugin.getConfig().getList("config.enableWorlds").contains(member.getPlayer.getWorld().getName())) {
                            member.getPlayer().sendMessage(plugin.getMessage("PLAYER_REMOVED_FROM_GUILD").replaceAll("%player%", player.getDisplayName()).replaceAll("%playerTarget%", playerTarget.getDisplayName()));
                        }
                    }
    
                    if (playerTarget.isOnline() && (plugin.getConfig().getList("config.enableWorlds").contains(playerTarget.getPlayer.getWorld().getName())) {
                        playerTarget.sendMessage(plugin.getMessage("YOU_REMOVED_FROM_GUILD").replaceAll("%player", player.getDisplayName()).replaceAll("%guild%", guild.getName()));;
                    }
    
                    plugin.getConfiguration().savePlayers();
    
                } else {
                    player.sendMessage(plugin.getMessage("NO_PERMISSION"));
                }
            } else {
                player.sendMessage(plugin.getMessage("COMMAND_KICK"));
            }
        }

    }

    private void Console(String[] args) {

        if (args.length > 1) {
            OfflinePlayer oplayer = Bukkit.getOfflinePlayer(args[1]);
            if (oplayer == null) {
                plugin.sendConsole(plugin.getMessage("PLAYER_NOT_RECOGNISED"));
                return;
            }

            Guild guild = plugin.getGuild(args[2]);
            if (guild == null) {
                plugin.sendConsole(plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
            }

            Player playerTarget = oplayer.getPlayer();

            User userTarget = plugin.getUser(playerTarget.getUniqueId());
            if (userTarget == null || !userTarget.getGuild().equals(guild.getId())) {
                plugin.sendConsole(plugin.getMessage("CAN_NOT_REMOVE"));
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
                plugin.removePlayer(userTarget);
            }

            for (User member : guild.getListMember()) {
                if (member.getOfflinePlayer().isOnline()) {
                    member.getPlayer().sendMessage(plugin.getMessage("PLAYER_REMOVED_FROM_GUILD").replaceAll("%player%", "Console").replaceAll("%playerTarget%", playerTarget.getDisplayName()));
                }
            }

            if (playerTarget.isOnline()) {
                playerTarget.sendMessage(plugin.getMessage("YOU_REMOVED_FROM_GUILD").replaceAll("%player", "Console").replaceAll("%guild%", guild.getName()));;
            }

            plugin.getConfiguration().savePlayers();
            plugin.getConfiguration().saveGuilds();

        } else {
            plugin.sendConsole(plugin.getMessage("COMMAND_KICK"));
        }

    }

}
