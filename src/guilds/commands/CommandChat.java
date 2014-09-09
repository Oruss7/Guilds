package guilds.commands;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandChat {

    private GuildsBasic plugin;

    public CommandChat(CommandSender sender, String[] args, GuildsBasic guildsBasic) {

        this.plugin = guildsBasic;

        if (sender instanceof Player) {
            Player(args, (Player) sender);
        } else {
            Console(args);
        }
    }

    private void Player(String[] args, Player player) {
        
        if (plugin.getConfig().getList("config.enableWorlds").contains(player.getWorld().getName())) {
            if (player.hasPermission("guilds.user.chat")) {
                User user = plugin.getUser(player.getUniqueId());
                if (user != null) {
                    Guild guild = plugin.getGuild(user.getGuild());
                    if (guild != null) {
                        StringBuilder message = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            message = message.append(" ").append(args[i]);
                        }
    
                        for (User member : guild.getListMember()) {
                            if (member != null && member.getPlayer() != null && member.getPlayer().isOnline()) {
                                member.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.GREEN+"["+plugin.getMessage("GUILD")+ChatColor.GREEN+"] " + ChatColor.WHITE + player.getDisplayName() + ":"+ChatColor.WHITE+ message.toString()));
                            }
                        }
                    } else {
                        player.sendMessage(plugin.getMessage("NOT_IN_GUILD"));
                    }
                } else {
                    player.sendMessage(plugin.getMessage("NOT_IN_GUILD"));
                }
            } else {
                player.sendMessage(plugin.getMessage("NO_PERMISSION"));
            }
        }
    }

    private void Console(String[] args) {

        plugin.sendConsole(plugin.getMessage("CONSOLE_ERROR"));

    }
}
