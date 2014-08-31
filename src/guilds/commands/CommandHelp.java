package guilds.commands;

import guilds.GuildsBasic;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelp {

    private GuildsBasic plugin;

    public CommandHelp(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {

        player.sendMessage(ChatColor.YELLOW + "=============== Guilds v" + plugin.v + " ===============");
        player.sendMessage(ChatColor.AQUA + "/guilds info [player] " + ChatColor.YELLOW + ": display informations about player.");
        player.sendMessage(ChatColor.AQUA + "/guilds invite " + ChatColor.YELLOW + ": invite player to join your guild.");
        player.sendMessage(ChatColor.AQUA + "/guilds accept/deny " + ChatColor.YELLOW + ": if you have a invitation, accept or deny to join.");
        player.sendMessage(ChatColor.AQUA + "/guilds leave " + ChatColor.YELLOW + ": leave current guild.");
        player.sendMessage(ChatColor.AQUA + "/base " + ChatColor.YELLOW + ": tp to your guild base.");
        player.sendMessage(ChatColor.AQUA + "/g " + ChatColor.YELLOW + ": talk on guild chanel.");
        player.sendMessage(ChatColor.AQUA + "/guilds promote <player> [rank] " + ChatColor.YELLOW + ": promote a member.");
        player.sendMessage(ChatColor.AQUA + "/guilds demote <player> [rank] " + ChatColor.YELLOW + ": demote a member.");
        player.sendMessage(ChatColor.AQUA + "/guilds kick <player> "+ ChatColor.YELLOW +": kick player from guild.");
        if (player.hasPermission("guilds.admin.*")) {
            player.sendMessage(ChatColor.AQUA + "/guilds create <guild> " + ChatColor.YELLOW + ": create guild.");
            player.sendMessage(ChatColor.AQUA + "/guilds remove <guild> " + ChatColor.YELLOW + ": remove guild.");
            player.sendMessage(ChatColor.AQUA + "/guilds setbase [guild] " + ChatColor.YELLOW + ": set guilds base.");
            player.sendMessage(ChatColor.AQUA + "/guilds add <player> [guild]" + ChatColor.YELLOW + ": add a member (without invitation).");
            player.sendMessage(ChatColor.AQUA + "/guilds save" + ChatColor.YELLOW + ": save to file.");
            player.sendMessage(ChatColor.AQUA + "/guilds load" + ChatColor.YELLOW + ": load to file.");
        }
        player.sendMessage(ChatColor.YELLOW + "==========================================");

    }

    private void Console(String[] args) {

        plugin.sendConsole( "=============== Guilds v" + plugin.v + " ===============");
        plugin.sendConsole( "/guilds save : save to file.");
        plugin.sendConsole( "/guilds load : load from file.");
        plugin.sendConsole( "/guilds leave : leave current guild.");
        plugin.sendConsole( "/guilds kick <player> <guild> : kick player from guild.");
        plugin.sendConsole( "/guilds add <player> <guild> : add player to guild.");
        plugin.sendConsole( "/guilds promote <player> [rank] : promote a member.");
        plugin.sendConsole( "/guilds demote <player> [rank] : demote a member.");
        plugin.sendConsole( "/guilds create <guild> : create guild.");
        plugin.sendConsole( "/guilds remove <guild> : remove guild.");
        plugin.sendConsole( "/guilds setbase <guild> : set guilds base.");
        plugin.sendConsole( "/base : tp to your guild base.");
        plugin.sendConsole( "========================================================");

    }

}
