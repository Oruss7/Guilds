package guilds.commands;

import guilds.GuildsBasic;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelp {

    private final GuildsBasic plugin;

    public CommandHelp(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {

        if (player.hasPermission("guilds.admin.*")) {
            player.sendMessage(ChatColor.YELLOW + "=============== Guilds v" + plugin.v + " ===============");
        } else {
            player.sendMessage(ChatColor.YELLOW + "=============== Guilds ===============");
        }
        player.sendMessage(ChatColor.AQUA + "/guilds info [joueur] " + ChatColor.YELLOW + ": affiche les informations sur le joueur.");
        player.sendMessage(ChatColor.AQUA + "/guilds invite " + ChatColor.YELLOW + ": invite un joueur a rejoindre votre guilde.");
        player.sendMessage(ChatColor.AQUA + "/guilds accept/deny " + ChatColor.YELLOW + ": si vous avez une invitation, accepte ou refuse.");
        player.sendMessage(ChatColor.AQUA + "/guilds leave " + ChatColor.YELLOW + ": quitte la guilde actuelle.");
        player.sendMessage(ChatColor.AQUA + "/base " + ChatColor.YELLOW + ": téléporte à la taverne de guilde.");
        player.sendMessage(ChatColor.AQUA + "/g " + ChatColor.YELLOW + ": parle sur le chant de guilde.");
        player.sendMessage(ChatColor.AQUA + "/guilds promote <player> [rank] " + ChatColor.YELLOW + ": grade un joueur de votre guilde.");
        player.sendMessage(ChatColor.AQUA + "/guilds demote <player> [rank] " + ChatColor.YELLOW + ": dégrade un joueur de votre guilde.");
        player.sendMessage(ChatColor.AQUA + "/guilds kick <player> " + ChatColor.YELLOW + ": exclu un joueur de votre guilde.");
        if (player.hasPermission("guilds.admin.*")) {
            player.sendMessage(ChatColor.AQUA + "/guilds create <guild> " + ChatColor.YELLOW + ": créé une guilde.");
            player.sendMessage(ChatColor.AQUA + "/guilds remove <guild> " + ChatColor.YELLOW + ": supprime une guilde.");
            player.sendMessage(ChatColor.AQUA + "/guilds setbase [guild] " + ChatColor.YELLOW + ": définit la taverne de guilde (pour les téléportations).");
            player.sendMessage(ChatColor.AQUA + "/guilds add <player> [guild]" + ChatColor.YELLOW + ": ajoute un joueur à une guilde (sans invitation).");
            player.sendMessage(ChatColor.AQUA + "/guilds save" + ChatColor.YELLOW + ": sauvegarde la config et les guildes.");
            player.sendMessage(ChatColor.AQUA + "/guilds load" + ChatColor.YELLOW + ": recharge la config et les guildes.");
        }
        player.sendMessage(ChatColor.YELLOW + "==========================================");

    }

    private void Console(String[] args) {

        plugin.sendConsole("=============== Guilds v" + plugin.v + " ===============");
        plugin.sendConsole("/guilds save : save to file.");
        plugin.sendConsole("/guilds load : load from file.");
        plugin.sendConsole("/guilds leave : leave current guild.");
        plugin.sendConsole("/guilds kick <player> <guild> : kick player from guild.");
        plugin.sendConsole("/guilds add <player> <guild> : add player to guild.");
        plugin.sendConsole("/guilds promote <player> [rank] : promote a member.");
        plugin.sendConsole("/guilds demote <player> [rank] : demote a member.");
        plugin.sendConsole("/guilds create <guild> : create guild.");
        plugin.sendConsole("/guilds remove <guild> : remove guild.");
        plugin.sendConsole("========================================================");

    }

}
