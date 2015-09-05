package guilds.commands;

import guilds.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class CommandSave
{
    private GuildsBasic plugin;
    
    public CommandSave(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (player.hasPermission("guilds.admin.save")) {
            this.plugin.getConfiguration().saveGuilds();
            this.plugin.getConfiguration().savePlayers();
            player.sendMessage(this.plugin.getMessage("SAVE"));
        }
        else {
            player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
        }
    }
    
    private void Console(final String[] args) {
        this.plugin.getConfiguration().saveGuilds();
        this.plugin.getConfiguration().savePlayers();
        this.plugin.sendConsole(this.plugin.getMessage("SAVE"));
    }
}
