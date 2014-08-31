package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.Rank;
import guilds.User;
import java.util.logging.Level;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCreate {

    private GuildsBasic plugin;

    public CommandCreate(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

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
            if (player.hasPermission("guilds.admin.create")) {
                Guild guild = plugin.getGuild(guildName.toString());
                User user = plugin.getUser(player.getUniqueId());
                if (guild != null) {
                    player.sendMessage(plugin.getMessage("GUILD_EXISTS").replaceAll("%guild%", guild.getName()));
                } else {
                    guild = new Guild();
                    guild.setName(guildName.toString());
                    guild.setLocation(player.getLocation());

                    if (user == null) {
                        user = new User(player.getUniqueId(), guild.getId(), Rank.LEAD.toString(), System.currentTimeMillis(), null);
                        plugin.addPlayers(user);
                        guild.setLead(player.getUniqueId());
                        guild.addMember(user);
                        player.sendMessage(plugin.getMessage("GUILD_LEADER").replaceAll("%guild%", guild.getName()));
                    } else {
                        if (user.haveGuild()) {
                            player.sendMessage(plugin.getMessage("GUILD_WITHOUT_LEADER").replaceAll("%guild%", guild.getName()));
                        }
                    }
                    plugin.addGuild(guild);
                    plugin.getConfiguration().saveGuilds();
                    player.sendMessage(plugin.getMessage("GUILD_CREATED").replaceAll("%guild%", guild.getName()));
                }
            } else {
                player.sendMessage(plugin.getMessage("NO_PERMISSION"));
            }
        } else {
            player.sendMessage(plugin.getMessage("COMMAND_CREATE"));
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
                plugin.getLogger().log(Level.INFO, plugin.getMessage("GUILD_EXISTS").replaceAll("%guild%", guild.getName()).replaceAll("&([0-9a-fk-or])", ""));
            } else {
                guild = new Guild();
                guild.setName(guildName.toString());
                plugin.getLogger().log(Level.INFO, plugin.getMessage("GUILD_WITHOUT_LEADER").replaceAll("%guild%", guild.getName()).replaceAll("&([0-9a-fk-or])", ""));
                plugin.addGuild(guild);
                plugin.getConfiguration().saveGuilds();
                plugin.getLogger().log(Level.INFO, plugin.getMessage("GUILD_CREATED").replaceAll("%guild%", guild.getName()).replaceAll("&([0-9a-fk-or])", ""));
            }
        } else {
            plugin.getLogger().log(Level.INFO, plugin.getMessage("COMMAND_CREATE").replaceAll("&([0-9a-fk-or])", ""));
        }

    }
}
