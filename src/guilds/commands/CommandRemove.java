package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.messages.Console;
import guilds.messages.Message;
import guilds.messages.MessageType;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRemove {

    private GuildsBasic GuildsBasic;

    public CommandRemove(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.GuildsBasic = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player p) {

        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length > 1) {
                StringBuilder guildName = new StringBuilder(args[1]);
                for (int arg = 2; arg < args.length; arg++) {
                    guildName.append(" ").append(args[arg]);
                }
                if (p.hasPermission("guilds.admin.remove")) {
                    Guild guild = GuildsBasic.getGuild(guildName.toString());
                    if (guild != null) {
                        boolean removed = GuildsBasic.GuildsList.remove(guild);
                        if (!removed) {
                            new Message(MessageType.GUILD_NOT_RECOGNISED, p, guildName.toString(), GuildsBasic);
                        } else {
                            new Message(MessageType.GUILD_DELETED, p, guild, GuildsBasic);
                            GuildsBasic.saveGuilds();
                            GuildsBasic.loadGuilds();
                            GuildsBasic.savePlayers();
                            GuildsBasic.loadPlayers();
                        }
                    } else {
                        new Message(MessageType.GUILD_NOT_RECOGNISED, p, guildName.toString(), GuildsBasic);
                    }
                } else {
                    new Message(MessageType.NO_PERMISSION, p, GuildsBasic);
                }
            } else {
                new Message(MessageType.COMMAND_REMOVE, p, GuildsBasic);
            }
        }
    }

    private void Console(String[] args) {

        if (args.length > 1) {
            StringBuilder guildName = new StringBuilder(args[1]);
            for (int arg = 2; arg < args.length; arg++) {
                guildName.append(" ").append(args[arg]);
            }
            Guild guild = GuildsBasic.getGuild(guildName.toString());
            if (guild != null) {
                GuildsBasic.GuildsList.remove(guild);
                new Console(MessageType.GUILD_DELETED, guild, GuildsBasic);
                GuildsBasic.saveGuilds();
                GuildsBasic.loadGuilds();
                GuildsBasic.savePlayers();
                GuildsBasic.loadPlayers();
            } else {
                new Console(MessageType.GUILD_NOT_RECOGNISED, guildName.toString(), GuildsBasic);
            }
        } else {
            new Console(MessageType.COMMAND_REMOVE, GuildsBasic);
        }

    }
}
