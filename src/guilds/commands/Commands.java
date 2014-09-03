package guilds.commands;

import guilds.GuildsBasic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private GuildsBasic plugin;

    public Commands(GuildsBasic guildsBasic) {
        this.plugin = guildsBasic;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("guilds")) {

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("list")) {
                    new CommandList(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("save")) {
                    new CommandSave(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("load")) {
                    new CommandLoad(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("add")) {
                    new CommandAdd(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("promote") || args[0].equalsIgnoreCase("demote")) {
                    new CommandPromote(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("kick")) {
                    new CommandKick(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("leave")) {
                    new CommandLeave(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("create")) {
                    new CommandCreate(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    new CommandRemove(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("setbase")) {
                    new CommandSetBase(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("info")) {
                    new CommandInfo(sender, args, plugin);
                } else if (args[0].equalsIgnoreCase("invite") || args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("deny")) {
                    new CommandInvite(sender, args, plugin);
                } else {
                    if (sender instanceof Player) {
                        sender.sendMessage(plugin.getMessage("COMMAND_NOT_RECOGNISED").replaceAll("%arg%", args[0]));
                    } else {
                        plugin.sendConsole(plugin.getMessage("COMMAND_NOT_RECOGNISED").replaceAll("%arg%", args[0]));
                    }
                }

            } else {
                new CommandHelp(sender, args, plugin);
            }

            return true;

        }

        if (label.equalsIgnoreCase("base")) {

            new CommandBase(sender, args, plugin);

            return true;

        }

        if (label.equalsIgnoreCase("gchat") || label.equalsIgnoreCase("g")) {

            if (args.length == 0) {
                return false;
            }

            new CommandChat(sender, args, plugin);

            return true;

        }
        return false;

    }

}
