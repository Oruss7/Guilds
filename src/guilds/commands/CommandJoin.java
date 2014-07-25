package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.messages.Console;
import guilds.messages.Message;
import guilds.messages.MessageType;
import guilds.utilities.Settings;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandJoin {

    private GuildsBasic GuildsBasic;

    public CommandJoin(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.GuildsBasic = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }

    }

    private void Player(String[] args, Player player) {

        if (!(args.length > 1)) {
            new Message(MessageType.COMMAND_JOIN, player, GuildsBasic);
            return;
        }

        Long Time = GuildsBasic.getLongSetting(Settings.SET_CHANGE_GUILD_TIME);

        if (Time > 0L) {
            if ((Math.abs(GuildsBasic.getPlayerJoined(player) - System.currentTimeMillis())) < Time) {
                Long Join = (Time / 1000) - (Math.abs(GuildsBasic.getPlayerJoined(player) - System.currentTimeMillis()) / 1000);
                new Message(MessageType.CHANGE_TIME, player, Join.toString(), GuildsBasic);
                return;
            }
        }

        StringBuilder guildName = new StringBuilder(args[1]);
        for (int arg = 2; arg < args.length; arg++) {
            guildName.append(" ").append(args[arg]);
        }
        Guild guild = GuildsBasic.getGuild(guildName.toString());

        if (guild == null) {
            new Message(MessageType.GUILD_NOT_RECOGNISED, player, args[1], GuildsBasic);
            return;
        }

        if (GuildsBasic.getEnabled(Settings.ENABLE_GUILD_JOIN_PERMISSIONS)) {
            if (!player.hasPermission("guilds.guild." + args[1])) {
                new Message(MessageType.NO_PERMISSION_JOIN, player, args[1], GuildsBasic);
                return;
            }
        } else {
            if (!player.hasPermission("guilds.user.join")) {
                new Message(MessageType.NO_PERMISSION, player, GuildsBasic);
                return;
            }
        }

        if (!GuildsBasic.getEnabled(Settings.ENABLE_CHANGE_GUILD)) {
            new Message(MessageType.GUILD_CHOSEN, player, player, GuildsBasic);
            return;
        }

        if (GuildsBasic.getPlayerGuild(player) != null) {
             new Message(MessageType.ALREADY_IN_GUILD, player, player, GuildsBasic);
             return;
        }

        if (GuildsBasic.PlayerGuild.containsKey(player.getName())) {
            GuildsBasic.PlayerGuild.remove(player.getName());
        }

        if (GuildsBasic.PlayerJoined.containsKey(player.getName())) {
            GuildsBasic.PlayerJoined.remove(player.getName());
        }

        GuildsBasic.PlayerGuild.put(player.getName(), guild);
        GuildsBasic.PlayerJoined.put(player.getName(), System.currentTimeMillis());

        guild.addOnline();

        GuildsBasic.savePlayers();
        GuildsBasic.loadPlayers();

        new Message(MessageType.GUILD_JOIN, player, player, guild, GuildsBasic);

    }

    private void Console(String[] args) {

        new Console(MessageType.CONSOLE_ERROR, GuildsBasic);

    }
}
