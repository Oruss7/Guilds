package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import guilds.*;
import java.util.logging.*;

public class CommandCreate {

    private GuildsBasic plugin;

    public CommandCreate(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player) sender);
        } else {
            this.Console(args);
        }
    }

    private void Player(final String[] args, final Player player) {
        if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
            if (args.length > 1) {
                final StringBuilder guildName = new StringBuilder(args[1]);
                for (int arg = 2; arg < args.length; ++arg) {
                    guildName.append(" ").append(args[arg]);
                }
                if (player.hasPermission("guilds.admin.create")) {
                    Guild guild = this.plugin.getGuild(guildName.toString());
                    User user = this.plugin.getUser(player.getUniqueId());
                    if (guild != null) {
                        player.sendMessage(this.plugin.getMessage("GUILD_EXISTS").replaceAll("%guild%", guild.getName()));
                    } else {
                        guild = new Guild();
                        guild.setName(guildName.toString());
                        guild.setLocation(player.getLocation());
                        if (user == null) {
                            user = new User(player.getUniqueId(), guild.getId(), Rank.LEAD.toString(), System.currentTimeMillis(), null);
                            this.plugin.addPlayers(user);
                            guild.setLead(player.getUniqueId());
                            guild.addMember(user);
                            player.sendMessage(this.plugin.getMessage("GUILD_LEADER").replaceAll("%guild%", guild.getName()));
                        } else if (user.haveGuild()) {
                            player.sendMessage(this.plugin.getMessage("GUILD_WITHOUT_LEADER").replaceAll("%guild%", guild.getName()));
                        }
                        this.plugin.addGuild(guild);
                        this.plugin.getConfiguration().saveGuilds();
                        player.sendMessage(this.plugin.getMessage("GUILD_CREATED").replaceAll("%guild%", guild.getName()));
                    }
                } else {
                    player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
                }
            } else {
                player.sendMessage(this.plugin.getMessage("COMMAND_CREATE"));
            }
        }
    }

    private void Console(final String[] args) {
        if (args.length > 1) {
            final StringBuilder guildName = new StringBuilder(args[1]);
            for (int arg = 2; arg < args.length; ++arg) {
                guildName.append(" ").append(args[arg]);
            }
            Guild guild = this.plugin.getGuild(guildName.toString());
            if (guild != null) {
                this.plugin.getLogger().log(Level.INFO, this.plugin.getMessage("GUILD_EXISTS").replaceAll("%guild%", guild.getName()).replaceAll("&([0-9a-fk-or])", ""));
            } else {
                guild = new Guild();
                guild.setName(guildName.toString());
                this.plugin.getLogger().log(Level.INFO, this.plugin.getMessage("GUILD_WITHOUT_LEADER").replaceAll("%guild%", guild.getName()).replaceAll("&([0-9a-fk-or])", ""));
                this.plugin.addGuild(guild);
                this.plugin.getConfiguration().saveGuilds();
                this.plugin.getLogger().log(Level.INFO, this.plugin.getMessage("GUILD_CREATED").replaceAll("%guild%", guild.getName()).replaceAll("&([0-9a-fk-or])", ""));
            }
        } else {
            this.plugin.getLogger().log(Level.INFO, this.plugin.getMessage("COMMAND_CREATE").replaceAll("&([0-9a-fk-or])", ""));
        }
    }
}
