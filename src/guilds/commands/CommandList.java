package guilds.commands;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import guilds.*;
import java.util.*;
import java.io.*;

public class CommandList
{
    private GuildsBasic plugin;
    
    public CommandList(final CommandSender sender, final String[] args, final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
        if (sender instanceof Player) {
            this.Player(args, (Player)sender);
        }
        else {
            this.Console(args);
        }
    }
    
    private void Player(final String[] args, final Player player) {
        if (player.hasPermission("guilds.user.list")) {
            StringBuilder msg = new StringBuilder();
            for (final Guild g : this.plugin.getGuilds()) {
                if (msg.length() == 0) {
                    msg = msg.append(g.getName());
                }
                else {
                    msg = msg.append(", ").append(g.getName());
                }
            }
            if (this.plugin.getGuilds().isEmpty()) {
                player.sendMessage(this.plugin.getMessage("NOT_GUILD"));
            }
            else {
                player.sendMessage(msg.toString());
            }
        }
        else {
            player.sendMessage(this.plugin.getMessage("NO_PERMISSION"));
        }
    }
    
    private void Console(final String[] args) {
        StringBuilder msg = new StringBuilder();
        for (final Guild g : this.plugin.getGuilds()) {
            if (msg.length() == 0) {
                msg = msg.append(g.getName());
            }
            else {
                msg = msg.append(", ").append(g.getName());
            }
        }
        if (this.plugin.getGuilds().isEmpty()) {
            this.plugin.sendConsole(this.plugin.getMessage("NOT_GUILD"));
        }
        else {
            this.plugin.sendConsole(msg.toString());
        }
        this.plugin.sendConsole(msg.toString());
    }
}
