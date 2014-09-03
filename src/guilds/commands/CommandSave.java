package guilds.commands;

import guilds.GuildsBasic;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSave {

    private GuildsBasic plugin;

    public CommandSave(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {

        if (player.hasPermission("guilds.admin.save")) {
            plugin.getConfiguration().saveGuilds();
            plugin.getConfiguration().savePlayers();
            player.sendMessage(plugin.getMessage("SAVE"));
        } else {
            player.sendMessage(plugin.getMessage("NO_PERMISSION"));
        }
    }

    private void Console(String[] args) {

        plugin.getConfiguration().saveGuilds();
        plugin.getConfiguration().savePlayers();

        plugin.sendConsole(plugin.getMessage("SAVE"));

    }

}
