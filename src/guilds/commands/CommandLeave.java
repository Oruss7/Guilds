package guilds.commands;

import guilds.GuildsBasic;
import guilds.messages.Console;
import guilds.messages.Message;
import guilds.messages.MessageType;
import guilds.utilities.Settings;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLeave {

	private GuildsBasic GuildsBasic;
	
	public CommandLeave(CommandSender sender, String[] args, GuildsBasic GuildsBasic) {
		
		this.GuildsBasic = GuildsBasic;
		
		if (sender instanceof Player) {
			Player(args, (Player) sender);
		} else {
			Console(args);
		}
		
	}
	
	private void Player(String[] args, Player p) {
		
		if (p.hasPermission("guilds.user.leave")) {
			if (GuildsBasic.getEnabled(Settings.ENABLE_CHANGE_GUILD)) {
				
				if (GuildsBasic.getPlayerGuild(p) != null) {
					GuildsBasic.getPlayerGuild(p).subtractOnline();
				}
				
				if (GuildsBasic.PlayerGuild.remove(p.getName()) == null){
                                    GuildsBasic.sendConsole("player "+p.getName()+" not in guild");
                                }
                                    
				GuildsBasic.PlayerRank.remove(p.getName());
				new Message(MessageType.GUILD_LEAVE, p, GuildsBasic);
				
				GuildsBasic.savePlayers();
				GuildsBasic.loadPlayers();
				
			} else {
				new Message(MessageType.GUILD_CHOSEN, p, GuildsBasic);
			}
		} else {
			new Message(MessageType.NO_PERMISSION, p, GuildsBasic);
		}
		
	}
	
	private void Console(String[] args) {
		
		new Console(MessageType.CONSOLE_ERROR, GuildsBasic);
		
	}
	
}
