package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandList {

    private GuildsBasic plugin;

    public CommandList(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {
        if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
            if (player.hasPermission("guilds.user.list")) {
                StringBuilder msg = new StringBuilder();
                for (Guild g : plugin.getGuilds()) {
                    if (msg.length() == 0) {
                        msg = msg.append(g.getName());
                    } else {
                        msg = msg.append(", ").append(g.getName());
                    }
                }
                if (plugin.getGuilds().isEmpty()) {
                    player.sendMessage(plugin.getMessage("NOT_GUILD"));
                } else {
                    player.sendMessage(msg.toString());
                }
            } else {
                player.sendMessage(plugin.getMessage("NO_PERMISSION"));
            }
        }
    }

    private void Console(String[] args) {

        StringBuilder msg = new StringBuilder();
        for (Guild g : plugin.getGuilds()) {
            if (msg.length() == 0) {
                msg = msg.append(g.getName());
            } else {
                msg = msg.append(", ").append(g.getName());
            }
        }
        if (plugin.getGuilds().isEmpty()) {
            plugin.sendConsole(plugin.getMessage("NOT_GUILD"));
        } else {
            plugin.sendConsole(msg.toString());
        }
        plugin.sendConsole(msg.toString());

    }
}
