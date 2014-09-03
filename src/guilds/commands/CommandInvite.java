package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.Rank;
import guilds.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInvite {

    private GuildsBasic plugin;

    public CommandInvite(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {

        if (args[0].equalsIgnoreCase("invite")) {

            if (args.length < 2) {
                player.sendMessage(plugin.getMessage("COMMAND_INVITE"));
                return;
            }

            if (!player.hasPermission("guilds.user.invite")) {
                player.sendMessage(plugin.getMessage("NO_PERMISSION"));
                return;
            }

            User user = plugin.getUser(player.getUniqueId());
            if (user == null) {
                player.sendMessage(plugin.getMessage("NOT_IN_GUILD"));
                return;
            }

            if (user.getGuild() == null || !user.haveGuild()) {
                player.sendMessage(plugin.getMessage("NOT_IN_GUILD"));
                return;
            }

            Guild guild = plugin.getGuild(user.getGuild());

            OfflinePlayer playerTarget = Bukkit.getOfflinePlayer(args[1]);

            if (playerTarget == null) {
                player.sendMessage(plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                return;
            }

            User userTarget = plugin.getUser(playerTarget.getUniqueId());

            if (userTarget == null) {
                // joueur non enregistré
                userTarget = new User(playerTarget);
                plugin.addPlayers(userTarget);
            } else if (userTarget.haveGuild()) {
                // joueur enregistré
                player.sendMessage(plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%player%", playerTarget.getName()).replaceAll("%guild%", plugin.getGuild(userTarget.getGuild()).getName()));
                return;
            }

            if (userTarget.getInvitation() != null) {
                player.sendMessage(plugin.getMessage("ALREADY_PENDING").replaceAll("%guild%", plugin.getGuild(userTarget.getInvitation()).getName()));
                return;
            }
            // envois de l'invitation
            userTarget.setInvitation(guild.getId());

            if (playerTarget.isOnline()) {
                playerTarget.getPlayer().sendMessage(plugin.getMessage("INVITATION").replaceAll("%player%", player.getName().replaceAll("%guild%", guild.getName())));
            }

            for (User member : guild.getListMember()) {
                if (member.getOfflinePlayer().isOnline()) {
                    member.getPlayer().sendMessage(plugin.getMessage("PLAYER_INVITED").replaceAll("%player%", playerTarget.getName().replaceAll("%guild%", guild.getName())));
                }
            }
            plugin.getConfiguration().savePlayers();
        }

        if (args[0].equalsIgnoreCase("accept")) {

            User user = plugin.getUser(player.getUniqueId());
            if (user == null || user.getInvitation() == null) {
                player.sendMessage(plugin.getMessage("NO_PENDING"));
                return;
            }

            if (user.haveGuild()) {
                player.sendMessage(plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%guild%", plugin.getGuild(user.getGuild()).getName()));
                return;
            }

            if (!player.hasPermission("guilds.user.join")) {
                player.sendMessage(plugin.getMessage("NO_PERMISSION"));
                return;
            }

            Guild guild = plugin.getGuild(user.getInvitation());
            if (guild == null) {
                player.sendMessage(plugin.getMessage("GUILD_NOT_RECOGNISED"));
                return;
            }

            user.setGuild(guild.getId());
            user.setJoined(System.currentTimeMillis());
            user.setInvitation(null);
            user.setRank(Rank.NEWBIE.toString());
            guild.addMember(user);

            for (User member : guild.getListMember()) {
                if (member.getOfflinePlayer().isOnline()) {
                    member.getPlayer().sendMessage(plugin.getMessage("GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                }
            }
            plugin.getConfiguration().savePlayers();
        }

        if (args[0].equalsIgnoreCase("deny")) {
            User user = plugin.getUser(player.getUniqueId());
            if (user == null || user.getInvitation() == null) {
                player.sendMessage(plugin.getMessage("NO_PENDING"));
                return;
            }

            Guild guild = plugin.getGuild(user.getInvitation());
            if (guild == null) {
                player.sendMessage(plugin.getMessage("GUILD_NOT_RECOGNISED"));
                return;
            }

            player.sendMessage(plugin.getMessage("YOU_DENY_INVITATION").replace("%guild%", guild.getName()));

            for (User member : guild.getListMember()) {
                if (member.getOfflinePlayer().isOnline()) {
                    member.getPlayer().sendMessage(plugin.getMessage("INVITATION_DENY").replace("%player%",player.getDisplayName()));
                }
            }
            
            plugin.removePlayer(user);
            plugin.getConfiguration().savePlayers();
        }
    }

    private void Console(String[] args) {
        plugin.sendConsole(plugin.getMessage("CONSOLE_ERROR"));
    }
}
