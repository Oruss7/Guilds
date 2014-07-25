package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.messages.Console;
import guilds.messages.Message;
import guilds.messages.MessageType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandCreate {

    private GuildsBasic GuildsBasic;

    public CommandCreate(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {

        this.GuildsBasic = GuildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }

    }

    private void Player(String[] args, Player p) {

        if (args.length > 1) {
            StringBuilder guildName = new StringBuilder(args[1]);
            for (int arg = 2; arg < args.length; arg++) {
                guildName.append(" ").append(args[arg]);
            }
            if (p.hasPermission("guilds.admin.create")) {
                Guild g = GuildsBasic.getGuild(guildName.toString());
                if (g != null) {
                    new Message(MessageType.GUILD_EXISTS, p, g, GuildsBasic);
                } else {
                    Guild gld = new Guild();
                    gld.setName(guildName.toString());
                    gld.New(GuildsBasic);
                    GuildsBasic.GuildsList.add(gld);
                    gld.setBase(p.getLocation());
                    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Guilds");
                    plugin.getLogger().info(gld.getName());
                    GuildsBasic.saveGuilds();
                    //GuildsBasic.loadGuilds();
                    new Message(MessageType.GUILD_CREATED, p, gld, GuildsBasic);
                }
            } else {
                new Message(MessageType.NO_PERMISSION, p, GuildsBasic);
            }
        } else {
            new Message(MessageType.COMMAND_CREATE, p, GuildsBasic);
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
                new Console(MessageType.GUILD_EXISTS, guild, GuildsBasic);
            } else {
                World w = null;
                String world = "world";
                w = Bukkit.getWorld(world);
                if (w == null) {
                    for (World wld : Bukkit.getWorlds()) {
                        w = wld;
                        break;
                    }
                }
                Guild g = new Guild();
                g.setName(guildName.toString());
                g.New(GuildsBasic);
                g.setBase(new Location(w, 0, 0, 0, 0, 0));
                GuildsBasic.GuildsList.add(g);
                GuildsBasic.saveGuilds();
                GuildsBasic.loadGuilds();
                new Console(MessageType.GUILD_CREATED, g, GuildsBasic);
            }
        } else {
            new Console(MessageType.COMMAND_CREATE, GuildsBasic);
        }

    }
}
