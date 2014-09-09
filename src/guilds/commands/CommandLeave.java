package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.Rank;
import guilds.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLeave {

    private GuildsBasic plugin;

    public CommandLeave(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            plugin.sendConsole(plugin.getMessage("CONSOLE_ERROR"));
        }
    }

    private void Player(String[] args, Player player) {
        if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
            if (player.hasPermission("guilds.user.leave")) {
                if (plugin.getConfig().getBoolean("config.ENABLE_CHANGE_GUILD")) {
    
                    User user = plugin.getUser(player.getUniqueId());
                    if (user == null || !user.haveGuild()) {
                        player.sendMessage(plugin.getMessage("NOT_IN_GUILD"));
                        return;
                    }
    
                    Guild guild = plugin.getGuild(user.getGuild());
    
                    guild.removeMember(user);
                    user.setGuild(null);
                    user.setJoined(null);
                    if(user.getRank().equalsIgnoreCase(Rank.LEAD.toString())){
                        guild.setLead(null);
                    }
                    user.setRank(null);
                    if (user.getInvitation() == null) {
                        plugin.removePlayer(user);
                    }
    
                    plugin.getConfiguration().savePlayers();
    
                    for (User member : guild.getListMember()) {
                        if (member.getOfflinePlayer().isOnline() && (plugin.getConfig().getList("config.enableWorlds").contains(member.getPlayer.getWorld().getName())) {
                            member.getPlayer().sendMessage(plugin.getMessage("GUILD_AS_LEAVE").replaceAll("%player%", player.getDisplayName()));
                        }
                    }
    
                    player.sendMessage(plugin.getMessage("GUILD_LEAVE").replaceAll("%player", player.getDisplayName()).replaceAll("%guild%", guild.getName()));;
    
                } else {
                    player.sendMessage(plugin.getMessage("GUILD_CHOSEN"));
                }
            } else {
                player.sendMessage(plugin.getMessage("NO_PERMISSION"));
            }
        }
    }
}
