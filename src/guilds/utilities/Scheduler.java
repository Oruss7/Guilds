package guilds.utilities;

import guilds.GuildsBasic;

import org.bukkit.entity.Player;

public class Scheduler {

	private GuildsBasic GuildsBasic;
	
	public Scheduler(GuildsBasic GuildsBasic) {
	
		this.GuildsBasic = GuildsBasic;
		
	}
	
	public int base(final Player p) {
		int i = GuildsBasic.getServer().getScheduler().scheduleSyncDelayedTask(GuildsBasic, new Runnable() {
			public void run() {
				p.teleport(GuildsBasic.getPlayerGuild(p).getBase());
				GuildsBasic.BaseDelay.remove(p);
			}
		}, GuildsBasic.getIntSetting(Settings.SET_BASE_TP_DELAY));
		return i;
	}
}
