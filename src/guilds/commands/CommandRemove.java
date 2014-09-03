package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemove {

    private GuildsBasic plugin;

    public CommandRemove(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {

        if (args.length > 1) {
            StringBuilder guildName = new StringBuilder(args[1]);
            for (int arg = 2; arg < args.length; arg++) {
                guildName.append(" ").append(args[arg]);
            }
            if (player.hasPermission("guilds.admin.remove")) {
                Guild guild = plugin.getGuild(guildName.toString());
                if (guild != null) {
                    for (User member : guild.getListMember()) {
                        if (member.getOfflinePlayer().isOnline()) {
                            member.getPlayer().sendMessage(plugin.getMessage("GUILD_DELETED").replaceAll("%guild%", guildName.toString()));
                        }
                            member.setGuild(null);
                            member.setJoined(null);
                            member.setRank(null);
                        if (member.getInvitation() == null) {
                            plugin.removePlayer(member);
                        }
                    }

                    plugin.removeGuild(guild);

                    plugin.getConfiguration().saveGuilds();
                    plugin.getConfiguration().savePlayers();

                } else {
                    player.sendMessage(plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", guildName.toString()));
                }
            } else {
                player.sendMessage(plugin.getMessage("NO_PERMISSION"));
            }
        } else {
            player.sendMessage(plugin.getMessage("COMMAND_REMOVE"));
        }

    }

    private void Console(String[] args) {

        if (args.length > 1) {
            StringBuilder guildName = new StringBuilder(args[1]);
            for (int arg = 2; arg < args.length; arg++) {
                guildName.append(" ").append(args[arg]);
            }

            Guild guild = plugin.getGuild(guildName.toString());
            if (guild != null) {
                for (User member : guild.getListMember()) {
                    if (member.getOfflinePlayer().isOnline()) {
                        member.getPlayer().sendMessage(plugin.getMessage("GUILD_DELETED").replaceAll("%guild%", guildName.toString()));
                    }
                    guild.removeMember(member);
                    if (member.getInvitation() == null) {
                        plugin.removePlayer(member);
                    }
                }

                plugin.removeGuild(guild);

                plugin.getConfiguration().saveGuilds();
                plugin.getConfiguration().savePlayers();

            } else {
                plugin.sendConsole(plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild", guildName.toString()));
            }
        } else {
            plugin.sendConsole(plugin.getMessage("COMMAND_REMOVE"));
        }

    }
}
