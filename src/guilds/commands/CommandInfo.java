package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.User;
import guilds.messages.Message;
import guilds.messages.MessageType;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInfo {

    private GuildsBasic GuildsBasic;

    public CommandInfo(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.GuildsBasic = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }

    }

    private void Player(String[] args, Player player) {

        if (player.hasPermission("guilds.user.info")) {

            if (!(args.length > 1)) {
                new Message(MessageType.COMMAND_INFO, player, GuildsBasic);
                return;
            }

            Player target = User.getPlayer(args[1]);

            if (target != null) {
                if (GuildsBasic.PlayerGuild.containsKey(target.getName())) {
                    Timestamp stamp = new Timestamp(GuildsBasic.getPlayerJoined(target));
                    Date date = new Date(stamp.getTime());
                    SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    GuildsBasic.sendMessage(player,"&e==================================");
                    GuildsBasic.sendMessage(player,"&9" + target.getDisplayName());
                    GuildsBasic.sendMessage(player,"Guilde : " + GuildsBasic.getPlayerGuild(target).getName());
                    GuildsBasic.sendMessage(player,"Date d'entrée : " + dt.format(date));
                    GuildsBasic.sendMessage(player,"&e==================================");
                }
            }else {
                new Message(MessageType.PLAYER_NOT_RECOGNISED, player, args[1], GuildsBasic);
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
