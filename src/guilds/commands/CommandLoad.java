package guilds.commands;

import guilds.GuildsBasic;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLoad {

	private GuildsBasic plugin;
	
	public CommandLoad(CommandSender sender, String[] args, GuildsBasic guildsBasic) {
		
		this.plugin = guildsBasic;
		
		if (sender instanceof Player) {
			Player(args, (Player) sender);
		} else {
			Console(args);
		}		
	}
	
	private void Player(String[] args, Player player) {
		
		if (player.hasPermission("guilds.admin.load")) {
			plugin.getConfiguration().start();			
			plugin.clearScheduler();
			player.sendMessage(plugin.getMessage("LOAD"));
		} else {
			player.sendMessage(plugin.getMessage("NO_PERMISSION"));
		}		
	}
	
	private void Console(String[] args) {
		
		plugin.getConfiguration().start();
		plugin.clearScheduler();
		
		plugin.sendConsole(plugin.getMessage("LOAD"));
		
	}
	
}
