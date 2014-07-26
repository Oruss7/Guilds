package guilds;

import org.bukkit.Bukkit;
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

    public static Player getPlayer(String s) {
        for (final Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(s)) {
                return p;
            }
        }
        return null;
    }

    public static boolean isOnline(String s) {
        final Player p = getPlayer(s);
        return p.isOnline();
    }

}
