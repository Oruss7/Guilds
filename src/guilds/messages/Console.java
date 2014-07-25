package guilds.messages;

import org.bukkit.entity.Player;

import guilds.Guild;
import guilds.GuildsBasic;
import guilds.utilities.Settings;

public class Console {
	
	public Console(MessageType msg, GuildsBasic GuildsBasic) {
		
		GuildsBasic.sendConsole(msg.getMessage());
		
	}
	
	public Console(MessageType msg, Player p, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/p/", p.getName());
		
		GuildsBasic.sendConsole(message);
		
	}
	
	public Console(MessageType msg, Guild g, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/g/", g.getName());
		
		GuildsBasic.sendConsole(message);
		
	}
	
	public Console(MessageType msg, String input1, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/p/", input1);
		message = message.replaceAll("/g/", input1);
		message = message.replaceAll("/s/", input1);
		message = message.replaceAll("/m/", input1);
		message = message.replaceAll("/a/", input1);
		message = message.replaceAll("/i/", input1);
		message = message.replaceAll("/t/", input1);
		message = message.replaceAll("/w/", input1);
		message = message.replaceAll("/b/", input1);
		
		GuildsBasic.sendConsole(message);
		
	}
	
	public Console(MessageType msg, Player p, Guild g, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/p/", p.getName());
		message = message.replaceAll("/g/", g.getName());
		
		GuildsBasic.sendConsole(message);
		
	}
	
	public Console(MessageType msg, Settings s, String input2, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/s/", s.toString());
		message = message.replaceAll("/v/", input2);
		
		GuildsBasic.sendConsole(message);
		
	}
	
	public Console(MessageType msg, MessageType m, String input2, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/m/", m.toString());
		message = message.replaceAll("/v/", input2);
		
		GuildsBasic.sendConsole(message);
		
	}
	
	public Console(MessageType msg, Guild guild, String input1, String input2, String input3, String input4, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/g/", guild.getName());
		message = message.replaceAll("/k/", input1);
		message = message.replaceAll("/d/", input2);
		message = message.replaceAll("/r/", input3);
		message = message.replaceAll("/e/", input4);
		
		GuildsBasic.sendConsole(message);
		
	}
	
	public Console(MessageType msg, String guild, String input1, String input2, String input3, String input4, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/g/", guild);
		message = message.replaceAll("/k/", input1);
		message = message.replaceAll("/d/", input2);
		message = message.replaceAll("/r/", input3);
		message = message.replaceAll("/e/", input4);
		
		GuildsBasic.sendConsole(message);
		
	}
	
	public Console(MessageType msg, Guild guild, String input1, String input2, GuildsBasic GuildsBasic) {
		
		String message = msg.getMessage();
		
		message = message.replaceAll("/g/", guild.getName());
		message = message.replaceAll("/s/", input1);
		message = message.replaceAll("/t/", input1);
		message = message.replaceAll("/v/", input2);
		
		GuildsBasic.sendConsole(message);
		
	}
	
}
