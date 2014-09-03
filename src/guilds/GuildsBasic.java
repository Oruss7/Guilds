package guilds;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import guilds.commands.Commands;
import guilds.listeners.PlayerListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class GuildsBasic extends JavaPlugin {

    private Plugin wg = null;
    public String chatFormat;
    public String v = "0.0.0";

    // joueurs guildés
    private Map<UUID, User> players = new HashMap<>();
    public Map<UUID, Guild> guilds = new HashMap<>();
    private Config config;

    @Override
    public void onEnable() {

        config = new Config(this);
        config.start();

        getLogger().info("Loaded configuration!");

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        getCommand("guilds").setExecutor(new Commands(this));
        getCommand("base").setExecutor(new Commands(this));
        getCommand("gchat").setExecutor(new Commands(this));

        getLogger().info("Registered commands, listeners!");

        clearScheduler();

        v = this.getDescription().getVersion();
        getLogger().log(Level.INFO, "Guilds v.{0} enabled !", v);
    }

    @Override
    public void onDisable() {
        clearScheduler();
        getLogger().log(Level.INFO, "Guilds disabled !");
    }

    public User getUser(UUID player) {
        return players.get(player);
    }

    public Map<UUID, User> getPlayers() {
        return players;
    }

    public void addPlayers(User user) {
        this.players.put(user.getOfflinePlayer().getUniqueId(), user);
    }
    
    public void removePlayer(User user){
        this.players.remove(user.getPlayer().getUniqueId());
    }

    public String getMessage(String path) {
        return config.messagesYML.getConfig().getString("Message." + path).replaceAll("&", "§");
    }

    private WorldGuardPlugin getWorldGuard() throws Exception {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin) plugin;
    }
    
    public void addGuild(Guild g) {
        guilds.put(g.getId(), g);
    }
   
    public Guild getGuild(String guildName) {
        for (Entry<UUID, Guild> entry : guilds.entrySet()) {
            Guild guild = entry.getValue();
            if (guild.getName().equalsIgnoreCase(guildName)) {
                return guild;
            }
        }
        return null;
    }
    
    public List<Guild> getGuilds(){
        return new ArrayList<>(guilds.values());
    }
    
    public Guild getGuild(UUID guildId) {
        return guilds.get(guildId);
    }
    
    public void removeGuild(Guild g) {
        guilds.remove(g.getId());
    }
    
    public void clearScheduler() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public Config getConfiguration() {
        return config;
    }

    public void sendConsole(String message) {
        getLogger().log(Level.INFO,message.replaceAll("&([0-9a-fk-or])", ""));
    }
}