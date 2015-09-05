package guilds.commands;

import guilds.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class CommandLoad
{
    private GuildsBasic plugin;
    
    public CommandLoad(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (player.hasPermission("guilds.admin.load")) {
            this.plugin.getConfiguration().start();
            this.plugin.clearScheduler();
            player.sendMessage(this.plugin.getMessage("LOAD"));
        }
        else {
            player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
        }
    }
    
    private void Console(final String[] args) {
        this.plugin.getConfiguration().start();
        this.plugin.clearScheduler();
        this.plugin.sendConsole(this.plugin.getMessage("LOAD"));
    }
}
