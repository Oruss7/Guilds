package guilds;

import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

public class User
{
    private OfflinePlayer offlinePlayer;
    private String rank;
    private UUID guild;
    private Long joined;
    private UUID invitation;
    private static final GuildsBasic plugin;
    
    public User(final OfflinePlayer Player) {
        this.offlinePlayer = Player;
    }
    
    public User(final UUID player, final UUID guild, final String rank, final Long joined, final UUID invitation) {
        this.offlinePlayer = Bukkit.getOfflinePlayer(player);
        this.guild = guild;
        this.rank = rank;
        this.joined = joined;
        this.invitation = invitation;
    }
    
    public Player getPlayer() {
        if (this.offlinePlayer.isOnline()) {
            return this.offlinePlayer.getPlayer();
        }
        return null;
    }
    
    public OfflinePlayer getOfflinePlayer() {
        return this.offlinePlayer;
    }
    
    public void setOfflinePlayer(final OfflinePlayer player) {
        this.offlinePlayer = player;
    }
    
    public static Player getPlayer(final String s) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(s)) {
                return p;
            }
        }
        return null;
    }
    
    public String getRank() {
        return this.rank;
    }
    
    public void setRank(final String rank) {
        this.rank = rank;
    }
    
    public boolean haveGuild() {
        return this.guild != null;
    }
    
    public UUID getGuild() {
        return this.guild;
    }
    
    public void setGuild(final UUID guild) {
        this.guild = guild;
    }
    
    public Long getJoined() {
        return this.joined;
    }
    
    public void setJoined(final Long joined) {
        this.joined = joined;
    }
    
    public UUID getInvitation() {
        return this.invitation;
    }
    
    public void setInvitation(final UUID invitation) {
        this.invitation = invitation;
    }
    
    public static boolean isOnline(final String s) {
        final Player p = getPlayer(s);
        return p.isOnline();
    }
    
    public int promote() {
        final Guild guild = User.plugin.getGuild(this.guild);
        if (Rank.valueOf(this.rank).equals(Rank.LEAD)) {
            return -1;
        }
        if (Rank.valueOf(this.rank).equals(Rank.MEMBER)) {
            this.rank = Rank.OFFICER.toString();
            return 0;
        }
        if (Rank.valueOf(this.rank).equals(Rank.NEWBIE)) {
            this.rank = Rank.MEMBER.toString();
            return 0;
        }
        if (!Rank.valueOf(this.rank).equals(Rank.OFFICER)) {
            return -2;
        }
        if (guild.getLead() != null) {
            return 1;
        }
        guild.setLead(this.offlinePlayer.getUniqueId());
        this.rank = Rank.LEAD.toString();
        return 0;
    }
    
    public int demote() {
        if (Rank.valueOf(this.rank).equals(Rank.LEAD)) {
            this.rank = Rank.OFFICER.toString();
            return 0;
        }
        if (Rank.valueOf(this.rank).equals(Rank.OFFICER)) {
            this.rank = Rank.MEMBER.toString();
            return 0;
        }
        if (Rank.valueOf(this.rank).equals(Rank.MEMBER)) {
            this.rank = Rank.NEWBIE.toString();
            return 0;
        }
        if (Rank.valueOf(this.rank).equals(Rank.NEWBIE)) {
            return -1;
        }
        return -2;
    }
    
    static {
        plugin = (GuildsBasic)Bukkit.getPluginManager().getPlugin("Guilds");
    }
}
