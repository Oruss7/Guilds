package guilds.commands;

import guilds.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Commands implements CommandExecutor
{
    private GuildsBasic plugin;
    
    public Commands(final GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (label.equalsIgnoreCase("guilds")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("list")) {
                    new CommandList(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("save")) {
                    new CommandSave(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("load")) {
                    new CommandLoad(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("add")) {
                    new CommandAdd(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("promote") || args[0].equalsIgnoreCase("demote")) {
                    new CommandPromote(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("kick")) {
                    new CommandKick(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("leave")) {
                    new CommandLeave(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("create")) {
                    new CommandCreate(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("remove")) {
                    new CommandRemove(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("setbase")) {
                    new CommandSetBase(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("info")) {
                    new CommandInfo(sender, args, this.plugin);
                }
                else if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("deny")) {
                    new CommandInvite(sender, args, this.plugin);
                }
                else if (sender instanceof Player) {
                    sender.sendMessage(this.plugin.getMessage("COMMAND_NOT_RECOGNISED").replaceAll("%arg%", args[0]));
                }
                else {
                    this.plugin.sendConsole(this.plugin.getMessage("COMMAND_NOT_RECOGNISED").replaceAll("%arg%", args[0]));
                }
            }
            else {
                new CommandHelp(sender, args, this.plugin);
            }
            return true;
        }
        if (label.equalsIgnoreCase("base")) {
            new CommandBase(sender, args, this.plugin);
            return true;
        }
        if (!label.equalsIgnoreCase("gchat") && !label.equalsIgnoreCase("g")) {
            return false;
        }
        if (args.length == 0) {
            return false;
        }
        new CommandChat(sender, args, this.plugin);
        return true;
    }
}
