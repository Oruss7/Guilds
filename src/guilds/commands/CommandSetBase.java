package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetBase {

    private GuildsBasic plugin;

    public CommandSetBase(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {

        if (player.hasPermission("guilds.admin.setbase")) {
            Guild guild = null;
            if (args.length > 1) {
                guild = plugin.getGuild(args[1]);
            } else {
                User user = plugin.getUser(player.getUniqueId());
                if (user != null && user.haveGuild()) {
                    guild = plugin.getGuild(user.getGuild());
                } else {
                    player.sendMessage(plugin.getMessage("COMMAND_SETBASE"));
                    return;
                }
            }
            if (guild != null) {
                guild.setLocation(player.getLocation());
                player.sendMessage(plugin.getMessage("BASE_SET").replaceAll("%guild%", guild.getName()));
                plugin.getConfiguration().saveGuilds();
            } else {
                player.sendMessage(plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[1]));
            }
        } else {
            player.sendMessage(plugin.getMessage("NO_PERMISSION"));
        }

    }

    private void Console(String[] args) {
        plugin.sendConsole(plugin.getMessage("CONSOLE_ERROR"));
    }

}
