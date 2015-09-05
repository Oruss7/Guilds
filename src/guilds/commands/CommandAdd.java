package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.Rank;
import guilds.User;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAdd {

    private final GuildsBasic plugin;

    /**
     * Ajoute <player name> à la guilde <guild name>
     * sans invitations ni confirmations
     *
     * @param sender
     * @param args
     * @param GuildsBasic
     */
    public CommandAdd(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.plugin = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player sender) {
        if (plugin.getConfig().getList("config.enableWorlds").contains(sender.getWorld().getName())) {
            if (args.length > 2) {
                if (sender.hasPermission("guilds.admin.add")) {
                    OfflinePlayer opl = Bukkit.getOfflinePlayer(args[1]);
                    if (!opl.isOnline()) {
                        sender.sendMessage(plugin.getMessage("PLAYER_OFFLINE"));
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
                                        sender.sendMessage(plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", plugin.getGuild(user.getGuild()).getName()));
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
    
                                for (User member : guild.getListMember()) {
                                    if (member.getOfflinePlayer().isOnline()) {
                                        member.getPlayer().sendMessage(plugin.getMessage("PLAYER_GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                                    }
                                }
                            } else {
                                sender.sendMessage(plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                            }
                        } else {
                            sender.sendMessage(plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                        }
                    }
                } else {
                    sender.sendMessage(plugin.getMessage("NO_PERMISSION"));
                }
            } else {
                sender.sendMessage(plugin.getMessage("COMMAND_ADD"));
            }
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
