package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.messages.Console;
import guilds.messages.Message;
import guilds.messages.MessageType;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandChat {

    private GuildsBasic GuildsBasic;

    public CommandChat(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.GuildsBasic = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player p) {

        if (p.hasPermission("guilds.user.chat")) {
            Guild g = GuildsBasic.getPlayerGuild(p);
            if (g != null) {
                StringBuilder message = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    message = message.append(args[i]);
                }
                
                for (Map.Entry<String, Guild> es : GuildsBasic.PlayerGuild.entrySet()) {
                    if (es.getValue().getName().equalsIgnoreCase(g.getName())) {
                        Player player = Bukkit.getPlayer(es.getKey());
                        GuildsBasic.sendMessage(player, p.getDisplayName() + ": "+ message.toString());
                    }
                }

            } else {
                new Message(MessageType.NOT_IN_GUILD, p, GuildsBasic);
            }
        } else {
            new Message(MessageType.NO_PERMISSION, p, GuildsBasic);
        }
    }

    private void Console(String[] args) {

        new Console(MessageType.CONSOLE_ERROR, GuildsBasic);

    }
}
