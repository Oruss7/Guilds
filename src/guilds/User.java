package guilds;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class User {

    private OfflinePlayer offlinePlayer;
    private String rank;
    private UUID guild;
    private Long joined;
    private UUID invitation;

    private final static GuildsBasic plugin = (GuildsBasic) Bukkit.getPluginManager().getPlugin("Guilds");

    public User(OfflinePlayer Player) {
        this.offlinePlayer = Player;
    }

    public User(UUID player, UUID guild, String rank, Long joined, UUID invitation) {
        this.offlinePlayer = Bukkit.getOfflinePlayer(player);
        this.guild = guild;
        this.rank = rank;
        this.joined = joined;
        this.invitation = invitation;
    }

    public Player getPlayer() {
        if (offlinePlayer.isOnline()) {
            return offlinePlayer.getPlayer();
        } else {
            return null;
        }
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void setOfflinePlayer(OfflinePlayer player) {
        this.offlinePlayer = player;
    }

    public static Player getPlayer(String s) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(s)) {
                return p;
            }
        }
        return null;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean haveGuild() {
        return (guild != null);
    }

    public UUID getGuild() {
        return guild;
    }

    public void setGuild(UUID guild) {
        this.guild = guild;
    }

    public Long getJoined() {
        return joined;
    }

    public void setJoined(Long joined) {
        this.joined = joined;
    }

    public UUID getInvitation() {
        return invitation;
    }

    public void setInvitation(UUID invitation) {
        this.invitation = invitation;
    }

    public static boolean isOnline(String s) {
        final Player p = getPlayer(s);
        return p.isOnline();
    }

    /**
     *
     * @return -1 = rang max, 0 ok, -2 rang inconnu , 1 chef existe deja
     */
    public int promote() {
        Guild guild = plugin.getGuild(this.guild);
        if (Rank.valueOf(rank).equals(Rank.LEAD)) {
            return -1;
        } else if (Rank.valueOf(rank).equals(Rank.MEMBER)) {
            rank = Rank.OFFICER.toString();
            return 0;
        } else if (Rank.valueOf(rank).equals(Rank.NEWBIE)) {
            rank = Rank.MEMBER.toString();
            return 0;
        } else if (Rank.valueOf(rank).equals(Rank.OFFICER)) {
            if (guild.getLead() != null) {
                return 1;
            }
            guild.setLead(offlinePlayer.getUniqueId());
            rank = Rank.LEAD.toString();
            return 0;
        }
        return -2;
    }

    /**
     *
     * @return -1 = rang min, -2 rang inconnu , 0 ok
     */
    public int demote() {
        if (Rank.valueOf(rank).equals(Rank.LEAD)) {
            rank = Rank.OFFICER.toString();
            return 0;
        } else if (Rank.valueOf(rank).equals(Rank.OFFICER)) {
            rank = Rank.MEMBER.toString();
            return 0;
        } else if (Rank.valueOf(rank).equals(Rank.MEMBER)) {
            rank = Rank.NEWBIE.toString();
            return 0;
        } else if (Rank.valueOf(rank).equals(Rank.NEWBIE)) {
            return -1;
        }
        return -2;
    }

}
