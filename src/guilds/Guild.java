package guilds;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class Guild {
	
	private int ONLINE;
	
	private String NAME;
	private String COLOR;
	private String PLAYER_PREFIX;
	private String PLAYER_SUFFIX;
	
	private Location LOCATION;
	
	private List<World> WORLDS = new ArrayList<>();

	public void New(GuildsBasic GuildsBasic) {
		setColor("");
		setPlayerPrefix("");
		setPlayerSuffix("");
	}
	
	public int getOnline() {
		return ONLINE;
	}
	
	public void addOnline() {
		ONLINE++;
	}
	
	public void subtractOnline() {
		ONLINE--;
	}
	
	public List<World> getWorlds() {
		return WORLDS;
	}
	
	public void addWorld(World w) {
		WORLDS.add(w);
	}
	
	public void removeWorld(World w) {
		WORLDS.remove(w);
	}
	
	public Location getBase() {
		return LOCATION;
	}
	
	public void setBase(Location l) {
		LOCATION = l;
	}
	
	public void setBaseWorld(World w) {
		LOCATION.setWorld(w);
	}

	public String getName() {
		return NAME;
	}

	public void setName(String NAME) {
		this.NAME = NAME;
	}
	
	public String getColor() {
		return COLOR;
	}

	public void setColor(String COLOR) {
		this.COLOR = COLOR;
	}

	public String getPlayerPrefix() {
		return PLAYER_PREFIX;
	}

	public void setPlayerPrefix(String PLAYER_PREFIX) {
		this.PLAYER_PREFIX = PLAYER_PREFIX;
	}

	public String getPlayerSuffix() {
		return PLAYER_SUFFIX;
	}

	public void setPlayerSuffix(String PLAYER_SUFFIX) {
		this.PLAYER_SUFFIX = PLAYER_SUFFIX;
	}
}
