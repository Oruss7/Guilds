package guilds.utilities;

import guilds.GuildsBasic;
import guilds.messages.Message;
import guilds.messages.MessageType;

import org.bukkit.entity.Player;

public class Scheduler {

	private GuildsBasic GuildsBasic;
	
	public Scheduler(GuildsBasic GuildsBasic) {
	
		this.GuildsBasic = GuildsBasic;
	}
	
	public int base(final Player p) {
		int i = GuildsBasic.getServer().getScheduler().scheduleSyncDelayedTask(GuildsBasic, new Runnable() {
			public void run() {
                                new Message(MessageType.TELEPORTATION, p, GuildsBasic);
				p.teleport(GuildsBasic.getPlayerGuild(p).getBase());
				GuildsBasic.BaseDelay.remove(p);
			}
		}, GuildsBasic.getIntSetting(Settings.SET_BASE_TP_DELAY)*20);
		return i;
	}
}
