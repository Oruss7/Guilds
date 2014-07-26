package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.User;
import guilds.messages.Console;
import guilds.messages.Message;
import guilds.messages.MessageType;
import guilds.utilities.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInvite {

    private GuildsBasic GuildsBasic;

    public CommandInvite(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.GuildsBasic = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }

    }

    private void Player(String[] args, Player player) {

        if (args[0].equalsIgnoreCase("invite")) {
            Player playerTarget = User.getPlayer(args[1]);
            Guild guild = GuildsBasic.getPlayerGuild(player);

            if (guild == null) {
                new Message(MessageType.NOT_IN_GUILD, player, GuildsBasic);
                return;
            }
            if (args.length < 2) {
                new Message(MessageType.COMMAND_INVITE, player, GuildsBasic);
                return;
            }

            if (!player.hasPermission("guilds.user.invite")) {
                new Message(MessageType.NO_PERMISSION, player, GuildsBasic);
                return;
            }

            if (playerTarget == null) {
                new Message(MessageType.PLAYER_NOT_RECOGNISED, player, args[1], GuildsBasic);
                return;
            }

            if (GuildsBasic.getPlayerGuild(playerTarget) != null) {
                new Message(MessageType.ALREADY_IN_GUILD, player, player, GuildsBasic);
                return;
            }

            String currentGuildPending = GuildsBasic.getPlayerPending(playerTarget);
            if (currentGuildPending != null) {
                new Message(MessageType.ALREADY_PENDING, player, currentGuildPending, GuildsBasic);
            }

            GuildsBasic.setPlayerPending(playerTarget, guild);

            GuildsBasic.savePlayers();
            GuildsBasic.loadPlayers();
            new Message(MessageType.INVITATION, playerTarget, guild, GuildsBasic);
            new Message(MessageType.PLAYER_INVITED, playerTarget, GuildsBasic);
        }

        if (args[0].equalsIgnoreCase("accept")) {

            String guildName = GuildsBasic.getPlayerPending(player);
            if (guildName == null) {
                new Message(MessageType.NO_PENDING, player, GuildsBasic);
            }

            if (!player.hasPermission("guilds.user.join")) {
                new Message(MessageType.NO_PERMISSION, player, GuildsBasic);
                return;
            }

            Guild guild = GuildsBasic.getGuild(guildName);
            if (guild == null) {
                new Message(MessageType.GUILD_NOT_RECOGNISED, player, guildName, GuildsBasic);
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
            GuildsBasic.PlayerPending.remove(player.getName());
            GuildsBasic.PlayerRank.put(player.getName(), "Recrue");

            GuildsBasic.savePlayers();
            GuildsBasic.loadPlayers();

            new Message(MessageType.GUILD_JOIN, player, player, guild, GuildsBasic);

        }

        if (args[0].equalsIgnoreCase("deny")) {
            String guildName = GuildsBasic.getPlayerPending(player);
            if (guildName == null) {
                new Message(MessageType.NO_PENDING, player, GuildsBasic);
            }

            GuildsBasic.PlayerPending.remove(player.getName());
            Guild guild = GuildsBasic.getGuild(guildName);
            String leadName = guild.getLead();
            if (leadName != null) {
                Player lead = User.getPlayer(leadName);
                if (lead != null && lead.isOnline()) {
                    new Message(MessageType.INVITATION_DENY, lead, GuildsBasic);
                }
            }

        }
    }

    private void Console(String[] args) {

        new Console(MessageType.CONSOLE_ERROR, GuildsBasic);

    }

}
