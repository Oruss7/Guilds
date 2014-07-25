package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.messages.Message;
import guilds.messages.MessageType;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandList {

    private GuildsBasic GuildsBasic;

    public CommandList(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.GuildsBasic = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }

    }

    private void Player(String[] args, Player player) {

        if (player.hasPermission("guilds.user.list")) {
            String msg = "";
            for (Guild g : GuildsBasic.GuildsList) {
                if (msg == "") {
                    msg = g.getName();
                } else {
                    msg = msg + ", " + g.getName();
                }
            }
            if (GuildsBasic.GuildsList.isEmpty()) {
                new Message(MessageType.NOT_GUILD, player, GuildsBasic);
            } else {
                GuildsBasic.sendMessage(player, msg + ".");
            }
        } else {
            new Message(MessageType.NO_PERMISSION, player, GuildsBasic);
        }

    }

    private void Console(String[] args) {

        String msg = "";
        for (Guild g : GuildsBasic.GuildsList) {
            if (msg == "") {
                msg = g.getName();
            } else {
                msg = msg + ", " + g.getName();
            }
        }
        GuildsBasic.sendConsole(msg + ".");

    }
}
