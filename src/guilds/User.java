package guilds;

import org.bukkit.entity.Player;

public class User {

	private Player Player;

	public User(Player Player) {
		this.Player = Player;
	}
	
	public Player getPlayer() {
		return Player;
	}

	public void setPlayer(Player Player) {
		this.Player = Player;
	}
	
}
