package guilds.commands;

import guilds.GuildsBasic;
import guilds.messages.Console;
import guilds.messages.Message;
import guilds.messages.MessageType;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLoad {

	private GuildsBasic GuildsBasic;
	
	public CommandLoad(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {
		
		this.GuildsBasic = GuildsBasic;
		
		if (sender instanceof Player) {
			Player(args, (Player) sender);
		} else {
			Console(args);
		}
		
	}
	
	private void Player(String[] args, Player player) {
		
		if (player.hasPermission("guilds.admin.load")) {
			GuildsBasic.loadGuilds();
			GuildsBasic.loadPlayers();
			GuildsBasic.loadSettings();
			GuildsBasic.loadMessages();
			GuildsBasic.clearScheduler();
			new Message(MessageType.LOAD, player, GuildsBasic);
		} else {
			new Message(MessageType.NO_PERMISSION, player, GuildsBasic);
		}
		
	}
	
	private void Console(String[] args) {
		
		GuildsBasic.loadGuilds();
		GuildsBasic.loadPlayers();
		GuildsBasic.loadSettings();
		GuildsBasic.loadMessages();
		GuildsBasic.clearScheduler();
		
		new Console(MessageType.LOAD, GuildsBasic);
		
	}
	
}
