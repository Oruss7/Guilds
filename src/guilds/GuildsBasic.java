package guilds;

import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import guilds.listeners.*;
import org.bukkit.event.*;
import guilds.commands.*;
import org.bukkit.command.*;
import java.util.logging.*;
import com.sk89q.worldguard.bukkit.*;
import java.util.*;
import org.bukkit.*;

public class GuildsBasic extends JavaPlugin
{
    private Plugin wg;
    public String chatFormat;
    public String v;
    private Map<UUID, User> players;
    public Map<UUID, Guild> guilds;
    private Config config;
    
    public GuildsBasic() {
        this.wg = null;
        this.v = "0.0.0";
        this.players = new HashMap<UUID, User>();
        this.guilds = new HashMap<UUID, Guild>();
    }
    
    public void onEnable() {
        (this.config = new Config(this)).start();
        this.getLogger().info("Loaded configuration!");
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(this), (Plugin)this);
        this.getCommand("guilds").setExecutor((CommandExecutor)new Commands(this));
        this.getCommand("base").setExecutor((CommandExecutor)new Commands(this));
        this.getCommand("gchat").setExecutor((CommandExecutor)new Commands(this));
        this.getLogger().info("Registered commands, listeners!");
        this.clearScheduler();
        this.v = this.getDescription().getVersion();
        this.getLogger().log(Level.INFO, "Guilds v.{0} enabled !", this.v);
    }
    
    public void onDisable() {
        this.clearScheduler();
        this.getLogger().log(Level.INFO, "Guilds disabled !");
    }
    
    public User getUser(final UUID player) {
        return this.players.get(player);
    }
    
    public Map<UUID, User> getPlayers() {
        return this.players;
    }
    
    public void addPlayers(final User user) {
        this.players.put(user.getOfflinePlayer().getUniqueId(), user);
    }
    
    public void removePlayer(final User user) {
        this.players.remove(user.getPlayer().getUniqueId());
    }
    
    public String getMessage(final String path) {
        return this.config.messagesYML.getConfig().getString("Message." + path).replaceAll("&", "�");
    }
    
    private WorldGuardPlugin getWorldGuard() throws Exception {
        final Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)plugin;
    }
    
    public void addGuild(final Guild g) {
        this.guilds.put(g.getId(), g);
    }
    
    public Guild getGuild(final String guildName) {
        for (final Map.Entry<UUID, Guild> entry : this.guilds.entrySet()) {
            final Guild guild = entry.getValue();
            if (guild.getName().equalsIgnoreCase(guildName)) {
                return guild;
            }
        }
        return null;
    }
    
    public List<Guild> getGuilds() {
        return new ArrayList<Guild>(this.guilds.values());
    }
    
    public Guild getGuild(final UUID guildId) {
        return this.guilds.get(guildId);
    }
    
    public void removeGuild(final Guild g) {
        this.guilds.remove(g.getId());
    }
    
    public void clearScheduler() {
        Bukkit.getScheduler().cancelTasks((Plugin)this);
    }
    
    public Config getConfiguration() {
        return this.config;
    }
    
    public void sendConsole(final String message) {
        this.getLogger().log(Level.INFO, message.replaceAll("&([0-9a-fk-or])", ""));
    }
}
