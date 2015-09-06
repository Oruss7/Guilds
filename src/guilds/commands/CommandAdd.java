package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import guilds.*;

public class CommandAdd {

    private final GuildsBasic plugin;

    public CommandAdd(final CommandSender sender, final String[] args, final GuildsBasic GuildsBasic) {
        this.plugin = GuildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player) sender);
        } else {
            this.Console(args);
        }
    }

    private void Player(final String[] args, final Player sender) {
        if (plugin.getConfig().getList("config.enableWorlds").contains(sender.getWorld().getName())) {
            if (args.length > 2) {
                if (sender.hasPermission("guilds.admin.add")) {
                    final OfflinePlayer opl = Bukkit.getOfflinePlayer(args[1]);
                    if (!opl.isOnline()) {
                        sender.sendMessage(this.plugin.getMessage("PLAYER_OFFLINE"));
                    } else {
                        final Player player = opl.getPlayer();
                        if (player != null) {
                            User user = this.plugin.getUser(player.getUniqueId());
                            final Guild guild = this.plugin.getGuild(args[2]);
                            if (guild != null) {
                                if (user != null) {
                                    if (user.haveGuild()) {
                                        sender.sendMessage(this.plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", this.plugin.getGuild(user.getGuild()).getName()));
                                        return;
                                    }
                                    user.setGuild(guild.getId());
                                    user.setRank(Rank.NEWBIE.toString());
                                    user.setJoined(System.currentTimeMillis());
                                    if (user.getInvitation().equals(guild.getId())) {
                                        user.setInvitation(null);
                                    }
                                } else {
                                    user = new User(player.getUniqueId(), guild.getId(), Rank.NEWBIE.toString(), System.currentTimeMillis(), null);
                                    this.plugin.addPlayers(user);
                                }
                                guild.addMember(user);
                                this.plugin.getConfiguration().savePlayers();
                                for (final User member : guild.getListMember()) {
                                    if (member.getOfflinePlayer().isOnline()) {
                                        member.getPlayer().sendMessage(this.plugin.getMessage("PLAYER_GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                                    }
                                }
                            } else {
                                sender.sendMessage(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                            }
                        } else {
                            sender.sendMessage(this.plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                        }
                    }
                } else {
                    sender.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
                }
            } else {
                sender.sendMessage(this.plugin.getMessage("COMMAND_ADD"));
            }
        }
    }

    private void Console(final String[] args) {
        if (args.length > 2) {
            final OfflinePlayer opl = Bukkit.getOfflinePlayer(args[1]);
            if (!opl.isOnline()) {
                this.plugin.sendConsole(this.plugin.getMessage("PLAYER_OFFLINE").replaceAll("%player%", args[1]));
            } else {
                final Player player = opl.getPlayer();
                if (player != null) {
                    User user = this.plugin.getUser(player.getUniqueId());
                    final Guild guild = this.plugin.getGuild(args[2]);
                    if (guild != null) {
                        if (user != null) {
                            if (user.haveGuild()) {
                                this.plugin.sendConsole(this.plugin.getMessage("ALREADY_IN_GUILD").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", this.plugin.getGuild(user.getGuild()).getName()));
                                return;
                            }
                            user.setGuild(guild.getId());
                            user.setRank(Rank.NEWBIE.toString());
                            user.setJoined(System.currentTimeMillis());
                            if (user.getInvitation().equals(guild.getId())) {
                                user.setInvitation(null);
                            }
                        } else {
                            user = new User(player.getUniqueId(), guild.getId(), Rank.NEWBIE.toString(), System.currentTimeMillis(), null);
                            this.plugin.addPlayers(user);
                        }
                        guild.addMember(user);
                        this.plugin.getConfiguration().savePlayers();
                        this.plugin.sendConsole(this.plugin.getMessage("PLAYER_GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                        for (final User member : guild.getListMember()) {
                            if (member.getOfflinePlayer().isOnline()) {
                                member.getPlayer().sendMessage(this.plugin.getMessage("PLAYER_GUILD_JOIN").replaceAll("%player%", player.getDisplayName()).replaceAll("%guild%", guild.getName()));
                            }
                        }
                    } else {
                        this.plugin.sendConsole(this.plugin.getMessage("GUILD_NOT_RECOGNISED").replaceAll("%guild%", args[2]));
                    }
                } else {
                    this.plugin.sendConsole(this.plugin.getMessage("PLAYER_NOT_RECOGNISED").replaceAll("%player%", args[1]));
                }
            }
        } else {
            this.plugin.sendConsole(this.plugin.getMessage("COMMAND_ADD"));
        }
    }
}
