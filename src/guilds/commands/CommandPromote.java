package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.Rank;
import guilds.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPromote {

    private final GuildsBasic plugin;

    public CommandPromote(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.plugin = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player sender) {

        if (args.length > 1) {
            // sender est dans une guilde
            User user = plugin.getUser(sender.getUniqueId());
            if (user == null) {
                sender.sendMessage(plugin.getMessage("NOT_IN_GUILD").replaceAll("%player%", sender.getDisplayName()));
                return;
            }

            if ((sender.hasPermission("guilds.admin.promote") && args[0].equalsIgnoreCase("promote")) || (sender.hasPermission("guilds.admin.demote") && args[0].equalsIgnoreCase("demote"))) {
                OfflinePlayer playerTarget = Bukkit.getOfflinePlayer(args[1]);
                if (playerTarget != null) {
                    User userTarget = plugin.getUser(playerTarget.getUniqueId());
                    if (userTarget == null) {
                        sender.sendMessage(plugin.getMessage("NOT_IN_GUILD").replaceAll("%player%", args[1]));
                        return;
                    }
                    Guild guild = plugin.getGuild(userTarget.getGuild());
                    if (guild != null) {

                        // joueur n'est pas dans le même guilde
                        if (userTarget.getGuild() != user.getGuild()) {
                            sender.sendMessage(plugin.getMessage("PLAYER_NOT_IN_YOUR_GUILD").replaceAll("%player%", args[1]).replaceAll("%guild%", plugin.getGuild(user.getGuild()).getName()));
                            return;
                        }
                        // joueur dans la même guilde

                        if (args[0].equalsIgnoreCase("promote")) {

                            //-1 = rang max, 0 ok, -2 rang inconnu , 1 chef existe deja
                            switch (userTarget.promote()) {
                                case -1:
                                    sender.sendMessage(plugin.getMessage("RANK_MAX").replaceAll("%player%", args[1]).replace("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                    return;
                                case -2:
                                    sender.sendMessage(plugin.getMessage("RANK_UNKNOW").replaceAll("%player%", args[1]).replace("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                    return;
                                case 1:
                                    sender.sendMessage(plugin.getMessage("RANK_ALREADY_GIVE").replaceAll("%player%", Bukkit.getOfflinePlayer(guild.getLead()).getName()).replace("%rank%", userTarget.getRank()));
                                    return;
                            }
                        } else if (args[0].equalsIgnoreCase("demote")) {
                            //-1 = rang min, -2 rang inconnu et 0 ok
                            switch (userTarget.demote()) {
                                case -1:
                                    sender.sendMessage(plugin.getMessage("RANK_MIN").replaceAll("%player%", args[1]).replace("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                    return;
                                case -2:
                                    sender.sendMessage(plugin.getMessage("RANK_UNKNOW").replaceAll("%player%", args[1]).replace("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                    return;
                            }
                        }

                        plugin.getConfiguration().savePlayers();

                        for (User member : guild.getListMember()) {
                            if (member.getOfflinePlayer().isOnline()) {
                                if (args[0].equalsIgnoreCase("promote")) {
                                    member.getPlayer().sendMessage(plugin.getMessage("PLAYER_PROMOTE").replaceAll("%player%", args[1]).replaceAll("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                } else {
                                    member.getPlayer().sendMessage(plugin.getMessage("PLAYER_DEMOTE").replaceAll("%player%", args[1]).replaceAll("%rank%", Rank.valueOf(userTarget.getRank()).getRank()));
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                    }
                } else {
                    sender.sendMessage(plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                }

            } else {
                sender.sendMessage(plugin.getMessage("NO_PERMISSION"));
            }
        } else {
            sender.sendMessage(plugin.getMessage("COMMAND_PROMOTE"));
        }
    }

    private void Console(String[] args) {

        if (args.length > 2) {
            OfflinePlayer opl = Bukkit.getOfflinePlayer(args[1]);
            if (!opl.isOnline()) {
                plugin.sendConsole(plugin.getMessage("PLAYER_OFFLINE").replaceAll("%player%", args[1]));
            } else {
                Player player = opl.getPlayer();
                if (player != null) {
                    User user = plugin.getUser(player.getUniqueId());
                    Guild guild = plugin.getGuild(args[2]);
                    if (guild != null) {
                        if (user != null) {
                            // joueur est déjà présent
                            if (user.haveGuild()) {
                                // joueur déjà guildé  
                                plugin.sendConsole(plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", plugin.getGuild(user.getGuild()).getName()));
                                return;
                            } else {
                                // joueur non guildé
                                user.setGuild(guild.getId());
                                user.setRank(Rank.NEWBIE.toString());
                                user.setJoined(System.currentTimeMillis());
                                if (user.getInvitation().equals(guild.getId())) {
                                    // remove de l'invitation si elle vient de cette guilde
                                    user.setInvitation(null);
                                }
                            }
                        } else {
                            // joueur non créé
                            user = new User(player.getUniqueId(), guild.getId(), Rank.NEWBIE.toString(), System.currentTimeMillis(), null);
                            plugin.addPlayers(user);
                        }
                        guild.addMember(user);

                        plugin.getConfiguration().savePlayers();

                        plugin.sendConsole(plugin.getMessage("PLAYER_GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                        for (User member : guild.getListMember()) {
                            if (member.getOfflinePlayer().isOnline()) {
                                member.getPlayer().sendMessage(plugin.getMessage("PLAYER_GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                            }
                        }

                    } else {
                        plugin.sendConsole(plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                    }
                } else {
                    plugin.sendConsole(plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                }
            }
        } else {
            plugin.sendConsole(plugin.getMessage("COMMAND_ADD"));
        }
    }
}
