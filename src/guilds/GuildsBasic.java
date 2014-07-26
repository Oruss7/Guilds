package guilds;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import guilds.commands.Commands;
import guilds.listeners.ChatListener;
import guilds.listeners.PlayerListener;
import guilds.messages.MessageType;
import guilds.utilities.Settings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.util.logging.Logger;

public class GuildsBasic extends JavaPlugin {

    private PluginManager PM;
    private Plugin wg = null;
    public String chatFormat;
    public String v = "0.0.0";
    private FileConfiguration SettingsConfig = null;
    private File SettingsConfigFile = null;
    private FileConfiguration PlayersConfig = null;
    private File PlayersConfigFile = null;
    private FileConfiguration GuildsConfig = null;
    private File GuildsConfigFile = null;
    private FileConfiguration MessagesConfig = null;
    private File MessagesConfigFile = null;
    public Map<String, Guild> PlayerGuild = new HashMap<>();
    public Map<String, Long> PlayerJoined = new HashMap<>();
    public Map<String, String> PlayerRank = new HashMap<>();
    public Map<String, String> PlayerPending = new HashMap<>();
    public Map<Player, User> PlayerUser = new HashMap<>();
    public Map<Player, Integer> BaseDelay = new HashMap<>();
    public List<Guild> GuildsList = new ArrayList<>();

    public void onEnable() {

        PM = getServer().getPluginManager();

        try {
            wg = getWorldGuard();
        } catch (Exception ex) {
            this.getLogger().log(Level.SEVERE, "Worldguard not found", ex);
            //PM.disablePlugin(this);
        }

        File SettingsFile = new File(getDataFolder(), "settings.yml");
        File PlayersFile = new File(getDataFolder(), "players.yml");
        File GuildsFile = new File(getDataFolder(), "guilds.yml");
        File MessagesFile = new File(getDataFolder(), "messages.yml");

        if (!SettingsFile.exists()) {
            defaultConfig("settings.yml");
        }

        if (!PlayersFile.exists()) {
            defaultConfig("players.yml");
        }

        if (!GuildsFile.exists()) {
            defaultConfig("guilds.yml");
        }

        if (!MessagesFile.exists()) {
            defaultConfig("messages.yml");
        }

        loadSettings();
        loadMessages();
        loadGuilds();
        loadPlayers();

        PM.registerEvents(new PlayerListener(this), this);
        PM.registerEvents(new ChatListener(this), this);

        getCommand("guilds").setExecutor(new Commands(this));
        getCommand("base").setExecutor(new Commands(this));
        getCommand("gchat").setExecutor(new Commands(this));

        v = this.getDescription().getVersion();

        clearScheduler();

        PlayerUser.clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerUser.put(p, newPlayerUser(p));
            Guild g = getPlayerGuild(p);
            World w = p.getWorld();
            if (g != null) {
                g.addOnline();
            }
        }
    }

    public void onDisable() {

        saveGuilds();
        savePlayers();
        saveSettings();
        saveMessages();

        clearScheduler();

    }

    private WorldGuardPlugin getWorldGuard() throws Exception {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public void defaultConfig(String file) {

        if (!new File(getDataFolder(), file).exists()) {
            saveResource(file, false);
        }

    }

    public Long getPlayerJoined(Player p) {
        if (PlayerJoined.get(p.getName()) != null) {
            return PlayerJoined.get(p.getName());
        } else {
            return 0L;
        }
    }

    public String getPlayerPending(Player p) {
        String key = null;
        String value = null;
        for (Map.Entry<String, String> pending : PlayerPending.entrySet()) {
            key = pending.getKey();
            if (pending.getValue() == null) {
                value = "";
            } else {
                value = pending.getValue();
            }
             sendConsole("joueur : "+key + "pending : " + value);
        }
        return PlayerPending.get(p.getName());
    }

    public void setPlayerPending(Player p, Guild g) {
        if (PlayerPending.get(p.getName()) != null) {
            PlayerPending.remove(p.getName());
        }
        PlayerPending.put(p.getName(), g.getName());
    }

    public void setPlayerJoined(Player p, Long l) {
        if (PlayerJoined.get(p.getName()) != null) {
            PlayerJoined.remove(p.getName());
        }
        PlayerJoined.put(p.getName(), l);
    }

    public User getPlayerUser(Player p) {
        return PlayerUser.get(p);
    }

    public Guild getPlayerGuild(Player p) {

        if (PlayerGuild.get(p.getName()) != null) {
            return PlayerGuild.get(p.getName());
        } else if (getEnabled(Settings.ENABLE_DEFAULT_GUILD)) {
            if (getGuild(getSetting(Settings.SET_DEFAULT_GUILD)) != null) {
                return getGuild(getSetting(Settings.SET_DEFAULT_GUILD));
            }
        }

        return null;

    }

    public void setPlayerGuild(Player p, Guild g) {

        if (PlayerGuild.containsKey(p.getName())) {
            PlayerGuild.remove(p.getName());
        }
        PlayerGuild.put(p.getName(), g);

    }

    public Guild getGuild(String g) {
        for (Guild guild : GuildsList) {
            if (guild.getName().equalsIgnoreCase(g)) {
                return guild;
            }
        }
        return null;
    }

    public Plugin getWg() {
        return wg;
    }

    public void loadMessages() {

        sendConsole("Loading messages.yml");

        MessagesConfigFile = new File(getDataFolder(), "messages.yml");

        MessagesConfig = YamlConfiguration.loadConfiguration(MessagesConfigFile);

        InputStream defConfigStream = getResource("messages.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(MessagesConfigFile);
            MessagesConfig.setDefaults(defConfig);
        }

        for (MessageType m : MessageType.values()) {
            if (SettingsConfig.isSet(m.toString())) {
                m.setMessage(SettingsConfig.getString(m.toString()));
            }

        }

    }

    public void loadSettings() {

        sendConsole("Loading settings.yml");

        SettingsConfigFile = new File(getDataFolder(), "settings.yml");

        SettingsConfig = YamlConfiguration.loadConfiguration(SettingsConfigFile);

        InputStream defConfigStream = getResource("settings.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(SettingsConfigFile);
            SettingsConfig.setDefaults(defConfig);
        }

        for (Settings s : Settings.values()) {
            if (SettingsConfig.isSet(s.toString())) {
                if (s.isBoolean()) {
                    s.setSetting(SettingsConfig.getBoolean(s.toString()));
                }
                if (s.isInteger()) {
                    s.setSetting(SettingsConfig.getInt(s.toString()));
                }
                if (s.isString()) {
                    s.setSetting(SettingsConfig.getString(s.toString()));
                }
                if (s.isLong()) {
                    s.setSetting(SettingsConfig.getLong(s.toString()));
                }
            }
        }

    }

    public void saveMessages() {

        sendConsole("Saving messages.yml");

        MessagesConfigFile = new File(getDataFolder(), "messages.yml");
        MessagesConfig = YamlConfiguration.loadConfiguration(MessagesConfigFile);

        InputStream defConfigStream = getResource("messages.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(MessagesConfigFile);
            MessagesConfig.setDefaults(defConfig);
        }

        Set<String> keys = MessagesConfig.getConfigurationSection("").getKeys(false);

        for (String str : keys) {
            MessagesConfig.set(str, null);
        }

        for (MessageType m : MessageType.values()) {
            MessagesConfig.set(m.toString(), m.getMessage());
        }

        try {
            MessagesConfig.options().copyDefaults(true);
            MessagesConfig.save(MessagesConfigFile);
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + MessagesConfigFile, ex);
        }

    }

    public void saveSettings() {

        sendConsole("Saving settings.yml");

        SettingsConfigFile = new File(getDataFolder(), "settings.yml");
        SettingsConfig = YamlConfiguration.loadConfiguration(SettingsConfigFile);

        InputStream defConfigStream = getResource("settings.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(SettingsConfigFile);
            SettingsConfig.setDefaults(defConfig);
        }

        Set<String> keys = SettingsConfig.getConfigurationSection("").getKeys(false);

        for (String str : keys) {
            SettingsConfig.set(str, null);
        }

        for (Settings s : Settings.values()) {
            SettingsConfig.set(s.toString(), s.getSetting());
        }

        try {
            SettingsConfig.options().copyDefaults(true);
            SettingsConfig.save(SettingsConfigFile);
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + SettingsConfigFile, ex);
        }

    }

    public void loadGuilds() {

        sendConsole("Loading guilds.yml");

        GuildsList.clear();

        GuildsConfigFile = new File(getDataFolder(), "guilds.yml");
        GuildsConfig = YamlConfiguration.loadConfiguration(GuildsConfigFile);

        InputStream defConfigStream = getResource("guilds.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(GuildsConfigFile);
            GuildsConfig.setDefaults(defConfig);
        }

        Set<String> g = GuildsConfig.getConfigurationSection("").getKeys(false);

        if (g.isEmpty()) {
            // No Guilds
        } else {
            for (String str : g) {
                loadGuild(str);
            }
        }

    }

    public void loadGuild(String str) {

        String path = "";

        Guild guild = new Guild();
        GuildsList.add(guild);

        guild.setName(str);

        path = str + ".Settings";

        if (GuildsConfig.isSet(path)) {
            guild.setColor(GuildsConfig.getString(str + ".Settings.Color", ""));
            guild.setPlayerPrefix(GuildsConfig.getString(str + ".Settings.Prefix", ""));
            guild.setPlayerSuffix(GuildsConfig.getString(str + ".Settings.Suffix", ""));
            guild.setLead(GuildsConfig.getString(str + ".Settings.Lead", ""));
        } else {
            guild.setColor(GuildsConfig.getString(str + ".settings.color", ""));
            guild.setPlayerPrefix(GuildsConfig.getString(str + ".settings.prefix", ""));
            guild.setPlayerSuffix(GuildsConfig.getString(str + ".settings.suffix", ""));
            guild.setLead(GuildsConfig.getString(str + ".Settings.Lead", ""));
        }

        path = str + ".Base";

        if (GuildsConfig.isSet(path)) {
            World world = Bukkit.getWorld(GuildsConfig.getString(str + ".Base.World", "world"));
            if (world == null) {
                world = Bukkit.getWorlds().get(0);
            }
            double x = GuildsConfig.getDouble(str + ".Base.X", 0);
            double y = GuildsConfig.getDouble(str + ".Base.Y", 0);
            double z = GuildsConfig.getDouble(str + ".Base.Z", 0);
            float yaw = (float) GuildsConfig.getDouble(str + ".Base.Yaw", 0);
            float pitch = (float) GuildsConfig.getDouble(str + ".Base.Pitch", 0);
            guild.setBase(new Location(world, x, y, z, yaw, pitch));
        } else {
            World world = Bukkit.getWorld(GuildsConfig.getString(str + ".base.world", "world"));
            if (world == null) {
                world = Bukkit.getWorlds().get(0);
            }
            double x = GuildsConfig.getDouble(str + ".base.x", 0);
            double y = GuildsConfig.getDouble(str + ".base.y", 0);
            double z = GuildsConfig.getDouble(str + ".base.z", 0);
            float yaw = (float) GuildsConfig.getDouble(str + ".base.yaw", 0);
            float pitch = (float) GuildsConfig.getDouble(str + ".base.pitch", 0);
            guild.setBase(new Location(world, x, y, z, yaw, pitch));
        }

        path = str + ".Worlds";

        if (GuildsConfig.isSet(path)) {
            for (World w : Bukkit.getServer().getWorlds()) {
                if (GuildsConfig.getBoolean(str + ".Worlds." + w.getName(), false)) {
                    guild.addWorld(w);
                }
            }
        } else {
            for (World w : Bukkit.getServer().getWorlds()) {
                if (GuildsConfig.getBoolean(str + ".worlds." + w.getName(), false)) {
                    guild.addWorld(w);
                }
            }
        }
    }

    public void saveGuilds() {

        sendConsole("Saving guilds.yml");

        GuildsConfigFile = new File(getDataFolder(), "guilds.yml");
        GuildsConfig = YamlConfiguration.loadConfiguration(GuildsConfigFile);

        InputStream defConfigStream = getResource("guilds.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(GuildsConfigFile);
            GuildsConfig.setDefaults(defConfig);
        }

        Set<String> ply = GuildsConfig.getConfigurationSection("").getKeys(false);

        for (String str : ply) {
            GuildsConfig.set(str, null);
        }

        if (!GuildsList.isEmpty()) {

            Set<String> keys = GuildsConfig.getConfigurationSection("").getKeys(false);

            for (String str : keys) {
                GuildsConfig.set(str, null);
            }

            for (Guild g : GuildsList) {

                String name = g.getName();

                GuildsConfig.set(name + ".Settings.Color", g.getColor());
                GuildsConfig.set(name + ".Settings.Prefix", g.getPlayerPrefix());
                GuildsConfig.set(name + ".Settings.Suffix", g.getPlayerSuffix());
                GuildsConfig.set(name + ".Settings.Lead", g.getLead());

                for (World w : Bukkit.getServer().getWorlds()) {
                    if (g.getWorlds().contains(w)) {
                        GuildsConfig.set(name + ".Worlds." + w.getName(), true);
                    } else {
                        GuildsConfig.set(name + ".Worlds." + w.getName(), false);
                    }
                }

                GuildsConfig.set(name + ".Base.World", g.getBase().getWorld().getName());
                GuildsConfig.set(name + ".Base.X", g.getBase().getX());
                GuildsConfig.set(name + ".Base.Y", g.getBase().getY());
                GuildsConfig.set(name + ".Base.Z", g.getBase().getZ());
                GuildsConfig.set(name + ".Base.Yaw", g.getBase().getYaw());
                GuildsConfig.set(name + ".Base.Pitch", g.getBase().getPitch());

            }

        }

        try {
            GuildsConfig.save(GuildsConfigFile);
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + GuildsConfigFile, ex);
        }

    }

    public User newPlayerUser(Player p) {

        return new User(p);

    }

    public void loadPlayers() {

        sendConsole("Loading players.yml");

        PlayersConfigFile = new File(getDataFolder(), "players.yml");
        PlayersConfig = YamlConfiguration.loadConfiguration(PlayersConfigFile);

        InputStream defConfigStream = getResource("players.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(PlayersConfigFile);
            PlayersConfig.setDefaults(defConfig);
        }

        Set<String> p = PlayersConfig.getConfigurationSection("").getKeys(false);

        PlayerGuild.clear();

        if (p.isEmpty()) {
            return; // No Players
        } else {
            for (String str : p) {
                Guild guild = null;
                for (Guild g : GuildsList) {
                    if (g.getName().equalsIgnoreCase(PlayersConfig.getString(str + ".Guild"))) {
                        guild = g;
                    }
                }
                if (guild == null) {
                    PlayerGuild.put(str, null);
                    PlayerJoined.put(str, 0L);
                    PlayerRank.put(str, null);
                    PlayerPending.put(str, PlayersConfig.getString(str + ".Pending", null));
                } else {
                    PlayerGuild.put(str, guild);
                    PlayerJoined.put(str, PlayersConfig.getLong(str + ".Joined", 0L));
                    PlayerRank.put(str, PlayersConfig.getString(str + ".Rank", null));
                    PlayerPending.put(str, PlayersConfig.getString(str + ".Pending", null));
                }
            }
        }

    }

    public void savePlayers() {

        sendConsole("Saving players.yml");

        PlayersConfigFile = new File(getDataFolder(), "players.yml");
        PlayersConfig = YamlConfiguration.loadConfiguration(PlayersConfigFile);

        InputStream defConfigStream = getResource("players.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(PlayersConfigFile);
            PlayersConfig.setDefaults(defConfig);
        }

        Set<String> ply = PlayersConfig.getConfigurationSection("").getKeys(false);

        for (String str : ply) {
            PlayersConfig.set(str, null);
        }

        if (!PlayerGuild.isEmpty()) {
            String key = null;
            String value = null;
            for (Map.Entry<String, Guild> guild : PlayerGuild.entrySet()) {
                key = guild.getKey() + ".Guild";
                if (guild.getValue() == null) {
                    value = null;
                } else {
                    value = guild.getValue().getName();
                }
                PlayersConfig.set(key, value);
            }
            key = null;
            value = null;
            for (Map.Entry<String, String> rank : PlayerRank.entrySet()) {
                key = rank.getKey() + ".Rank";
                if (rank.getValue() == null) {
                    value = null;
                } else {
                    value = rank.getValue();
                }
                PlayersConfig.set(key, value);
            }
            Long lng = 0L;
            for (Map.Entry<String, Long> joined : PlayerJoined.entrySet()) {
                key = joined.getKey() + ".Joined";
                if (joined.getValue() == null) {
                    lng = null;
                } else {
                    lng = joined.getValue();
                }
                PlayersConfig.set(key, lng);
            }
            key = null;
            value = null;
            for (Map.Entry<String, String> pending : PlayerPending.entrySet()) {
                key = pending.getKey() + ".Pending";
                if (pending.getValue() == null) {
                    value = null;
                } else {
                    value = pending.getValue();
                }
                PlayersConfig.set(key, value);
            }
        }

        try {
            PlayersConfig.save(PlayersConfigFile);
        } catch (IOException ex) {
            this.getLogger().log(Level.SEVERE, "Could not save config to " + PlayersConfigFile, ex);
        }

    }

    public void sendMessage(Player p, String msg) {

        String prefix = "";

        if (getEnabled(Settings.ENABLE_CHAT_COLOR)) {
            msg = msg.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
        } else {
            msg = msg.replaceAll("&([0-9a-fk-or])", "");
        }

        if (getEnabled(Settings.ENABLE_GUILD_NAME_ON_BROADCAST)) {
            Guild g = getPlayerGuild(p);
            if (g != null) {
                prefix = getSetting(Settings.SET_GUILDS_BROADCAST_COLOR) + "[" + g.getName() + "] ";
            } else {
                prefix = getSetting(Settings.SET_GUILDS_BROADCAST_COLOR) + "[Guilds] ";
            }
        } else {
            prefix = getSetting(Settings.SET_GUILDS_BROADCAST_COLOR) + "[Guilds] ";
        }

        prefix = prefix.replaceAll("&([0-9a-fk-or])", "\u00A7$1");

        p.sendMessage(prefix + msg);
    }

    public void sendConsole(String msg) {

        msg = "[Guilds] " + msg;
        msg = msg.replaceAll("&([0-9a-fk-or])", "");
        System.out.println(msg);

    }

    public void clearScheduler() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public boolean getEnabled(Settings s) {
        Object setting = s.getSetting();
        if (s.isBoolean()) {
            return ((Boolean) setting).booleanValue();
        } else {
            return false;
        }
    }

    public int getIntSetting(Settings s) {
        Object setting = s.getSetting();
        if (s.isInteger()) {
            return ((Integer) setting).intValue();
        } else {
            return 0;
        }
    }

    public long getLongSetting(Settings s) {
        Object setting = s.getSetting();
        if (s.isLong()) {
            return ((Long) setting).longValue();
        } else {
            return 0;
        }
    }

    public String getSetting(Settings s) {
        Object setting = s.getSetting();
        if (s.isString()) {
            return ((String) setting).toString();
        } else {
            return "";
        }
    }

    public void printGuild(Guild g) {
        sendConsole(g.getName());
    }
}
