package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import guilds.*;

public class CommandSetBase {

    private GuildsBasic plugin;

    public CommandSetBase(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player) sender);
        } else {
            this.Console(args);
        }
    }

    private void Player(final String[] args, final Player player) {
        if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
            if (player.hasPermission("guilds.admin.setbase")) {
                Guild guild = null;
                if (args.length > 1) {
                    guild = this.plugin.getGuild(args[1]);
                } else {
                    final User user = this.plugin.getUser(player.getUniqueId());
                    if (user == null || !user.haveGuild()) {
                        player.sendMessage(this.plugin.getMessage("COMMAND_SETBASE"));
                        return;
                    }
                    guild = this.plugin.getGuild(user.getGuild());
                }
                if (guild != null) {
                    guild.setLocation(player.getLocation());
                    player.sendMessage(this.plugin.getMessage("BASE_SET").replaceAll("%guild%", guild.getName()));
                    this.plugin.getConfiguration().saveGuilds();
                } else {
                    player.sendMessage(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[1]));
                }
            } else {
                player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
            }
        }
    }

    private void Console(final String[] args) {
        this.plugin.sendConsole(this.plugin.getMessage("CONSOLE_ERROR"));
    }
}
