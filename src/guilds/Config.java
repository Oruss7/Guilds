package guilds;

import org.bukkit.plugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.configuration.*;
import java.util.logging.*;
import java.io.*;

public class Config
{
    private final GuildsBasic plugin;
    public CustomConfig configYML;
    public CustomConfig messagesYML;
    public CustomConfig playersYML;
    public CustomConfig guildsYML;
    
    public Config(final GuildsBasic pl) {
        this.plugin = pl;
    }
    
    public void start() {
        this.configYML = new CustomConfig((Plugin)this.plugin, "config");
        this.messagesYML = new CustomConfig((Plugin)this.plugin, "messages");
        this.playersYML = new CustomConfig((Plugin)this.plugin, "players");
        this.guildsYML = new CustomConfig((Plugin)this.plugin, "guilds");
        this.generateConfig();
        this.generateMessages();
        this.generateGuilds();
        this.generatePlayers();
    }
    
    private void generateConfig() {
        final FileConfiguration config = this.configYML.getConfig();
        config.addDefault("config.ENABLE_CHANGE_GUILD", (Object)true);
        config.addDefault("config.ENABLE_NO_GUILD", (Object)true);
        config.addDefault("config.ENABLE_DEFAULT_GUILD", (Object)false);
        config.addDefault("config.ENABLE_FRIENDLY_FIRE_PVP", (Object)true);
        config.addDefault("config.ENABLE_BASE_ON_DEATH", (Object)true);
        config.addDefault("config.ENABLE_PLAYER_GUILD_PREFIX", (Object)true);
        config.addDefault("config.ENABLE_PLAYER_LISTNAME_COLOR", (Object)false);
        config.addDefault("config.ENABLE_PLAYER_LISTNAME_GUILD", (Object)false);
        config.addDefault("config.ENABLE_GUILD_CHAT_FORMAT", (Object)false);
        config.addDefault("config.ENABLE_GUILD_JOIN_PERMISSIONS", (Object)false);
        config.addDefault("config.ENABLE_GUILD_NAME_ON_BROADCAST", (Object)false);
        config.addDefault("config.SET_GUILDS_BROADCAST_COLOR", (Object)"&d");
        config.addDefault("config.SET_DEFAULT_GUILD", (Object)"Les Voleurs");
        config.addDefault("config.SET_CHANGE_GUILD_TIME", (Object)0);
        config.addDefault("config.BASE.COOLDOWN", (Object)30);
        final String[] enableWorlds = { "world", "word2" };
        config.addDefault("config.enableWorlds", (Object)Arrays.asList(enableWorlds));
        this.configYML.saveConfig();
    }
    
    private void generateMessages() {
        final FileConfiguration messages = this.messagesYML.getConfig();
        messages.addDefault("Message.ALREADY_IN_GUILD", (Object)"&e%player% est d\u00e9j\u00e0 dans une guilde.");
        messages.addDefault("Message.MUST_JOIN_GUILD", (Object)"&eVous devez \u00eatre dans une guilde !");
        messages.addDefault("Message.GUILD_CHOSEN", (Object)"&eVous faites partie d'une guilde et ne pouvez pas en changer.");
        messages.addDefault("Message.NOT_IN_GUILD", (Object)"&eVous n'\u00eates pas dans une guilde");
        messages.addDefault("Message.PLAYER_NOT_IN_GUILD", (Object)"&%player% n'est pas dans une guilde.");
        messages.addDefault("Message.NOT_GUILD", (Object)"&eAucune guilde.");
        messages.addDefault("Message.GUILD_CREATED", (Object)"&eLa guilde %guild% a \u00e9t\u00e9 cr\u00e9\u00e9.");
        messages.addDefault("Message.GUILD_EXISTS", (Object)"&e%guild% existe d\u00e9j\u00e0.");
        messages.addDefault("Message.GUILD_DELETED", (Object)"&eLa guilde %guild% a \u00e9t\u00e9 supprim\u00e9e.");
        messages.addDefault("Message.GUILD_LEAVE", (Object)"&eVous venez de quitter la guilde %guild%.");
        messages.addDefault("Message.GUILD_AS_LEAVE", (Object)"&e%player% vient de quitter la guilde.");
        messages.addDefault("Message.JOIN_COMMAND", (Object)"&e/guilds join <guild>");
        messages.addDefault("Message.NO_FRIENDLY_FIRE", (Object)"&eVous ne pouvez pas attaquer des personnes de votre guilde !");
        messages.addDefault("Message.COMMAND_SETBASE", (Object)"&eParam\u00e8tres manquants... /guilds setbase [guild]");
        messages.addDefault("Message.COMMAND_MESSAGE", (Object)"&eParam\u00e8tres manquants... /guilds message <message> <value>");
        messages.addDefault("Message.COMMAND_REMOVE", (Object)"&eParam\u00e8tres manquants... /guilds remove <guild>");
        messages.addDefault("Message.COMMAND_CREATE", (Object)"&eParam\u00e8tres manquants... /guilds create <guild>");
        messages.addDefault("Message.COMMAND_KICK", (Object)"&eParam\u00e8tres manquants... /guilds kick <player>");
        messages.addDefault("Message.COMMAND_ADD", (Object)"&eParam\u00e8tres manquants... /guilds add <player> <guild>");
        messages.addDefault("Message.COMMAND_JOIN", (Object)"&eParam\u00e8tres manquants... /guilds promote <player>");
        messages.addDefault("Message.COMMAND_PROMOTE", (Object)"&eParam\u00e8tres manquants... /guilds promote/demote <player>");
        messages.addDefault("Message.COMMAND_DEMOTE", (Object)"&eParam\u00e8tres manquants... /guilds join <guild>");
        messages.addDefault("Message.GUILD_JOIN", (Object)"&e%player% vient de rejoidre la guilde.");
        messages.addDefault("Message.PLAYER_GUILD_JOIN", (Object)"&e%player% ajout\u00e9 \u00e0 la guilde %guild%");
        messages.addDefault("Message.NO_PERMISSION_JOIN", (Object)"&eVous ne pouvez pas rejoindre la guilde %guild%.");
        messages.addDefault("Message.NO_PERMISSION", (Object)"&eVous n'avez pas la permission.");
        messages.addDefault("Message.SAVE", (Object)"&esave complete.");
        messages.addDefault("Message.LOAD", (Object)"&eload complete.");
        messages.addDefault("Message.BASE_SET", (Object)"&eTanni\u00e8re de guilde %guild% d\u00e9plac\u00e9e.");
        messages.addDefault("Message.PLAYER_REMOVED_FROM_GUILD", (Object)"&e%playerTarget% a \u00e9t\u00e9 exclu de la guilde par %player%.");
        messages.addDefault("Message.YOU_REMOVED_FROM_GUILD", (Object)"&eVous avez \u00e9t\u00e9 exclu de votre guilde.");
        messages.addDefault("Message.GUILD_NOT_RECOGNISED", (Object)"&eGuilde %guild% non trouv\u00e9e.");
        messages.addDefault("Message.CAN_NOT_REMOVE", (Object)"&cVous ne pouvez pas exclure %player% car il n'est pas dans votre guilde.");
        messages.addDefault("Message.PLAYER_NOT_RECOGNISED", (Object)"&eJoueur %player% non trouv\u00e9.");
        messages.addDefault("Message.CONSOLE_ERROR", (Object)"&ecommand not supported by console.");
        messages.addDefault("Message.NOT_BOOLEAN", (Object)"&ethe value you entered is not true%false.");
        messages.addDefault("Message.NOT_INT", (Object)"&ethe value you entered is not an integer.");
        messages.addDefault("Message.NOT_DOUBLE", (Object)"&ethe value you entered is not a double.");
        messages.addDefault("Message.NOT_LONG", (Object)"&ethe value you entered is not a long.");
        messages.addDefault("Message.MESSAGE_NOT_RECOGNISED", (Object)"&e%m% non trouv\u00e9.");
        messages.addDefault("Message.MESSAGE_SET", (Object)"&e%m% set to %v%");
        messages.addDefault("Message.CHANGE_TIME", (Object)"&e%t% secondes avant de pouvoir changer de guilde.");
        messages.addDefault("Message.COMMAND_NOT_RECOGNISED", (Object)"&e/guilds %arg% non trouv\u00e9e.");
        messages.addDefault("Message.INPUT_NOT_RECOGNISED", (Object)"&einput %i% not recognised.");
        messages.addDefault("Message.TELEPORTATION", (Object)"&eT\u00e9l\u00e9portation dans votre base");
        messages.addDefault("Message.TELEPORTATION_COOLDOWN", (Object)"&cVous devez patientez avant de pouvoir de nouveau vous t\u00e9l\u00e9porter \u00e0 votre base.");
        messages.addDefault("Message.CAN_NOT_TELEPORT_HERE", (Object)"&cVous ne pouvez pas vous t\u00e9l\u00e9porter ici !");
        messages.addDefault("Message.COMMAND_INFO", (Object)"&eParam\u00e8tres manquants... /guilds info [player]");
        messages.addDefault("Message.COMMAND_INVITE", (Object)"&eParam\u00e8tres manquants... /guilds invite <player>");
        messages.addDefault("Message.ALREADY_PENDING", (Object)"&eLe joueur a d\u00e9j\u00e0 une invitation pour rejoindre la guilde %guild%");
        messages.addDefault("Message.NO_PENDING", (Object)"&eVous n'avez aucune invitation");
        messages.addDefault("Message.INVITATION", (Object)"&e%player% vous a invit\u00e9 \u00e0 rejoindre la guilde %guild%. Tapez &f/guilds accept&e pour la rejoindre ou &f/guilds deny&e pour refuser.");
        messages.addDefault("Message.PLAYER_INVITED", (Object)"&e%player% a \u00e9t\u00e9 invit\u00e9 \u00e0 rejoindre la guilde.");
        messages.addDefault("Message.INVITATION_DENY", (Object)"&e%player% a refus\u00e9 d'entrer dans votre guilde.");
        messages.addDefault("Message.YOU_DENY_INVITATION", (Object)"&eVous avez refus\u00e9 d'entrer dans la guilde %guild%.");
        messages.addDefault("Message.GUILD_LEADER", (Object)"&eVous \u00eates d\u00e9sormais chef de la guilde %guild% !");
        messages.addDefault("Message.GUILD_WITHOUT_LEADER", (Object)"&cLa guilde %guild% n'a aucun chef.");
        messages.addDefault("Message.PLAYER_NOT_IN_YOUR_GUILD", (Object)"&c%player% n'appartient pas \u00e0 votre guilde !");
        messages.addDefault("Message.PLAYER_PROMOTE", (Object)"&e%player% a \u00e9t\u00e9 promu au rang de %rank%");
        messages.addDefault("Message.PLAYER_DEMOTE", (Object)"&e%player% a \u00e9t\u00e9 r\u00e9trograd\u00e9 au rang de %rank%");
        messages.addDefault("Message.RANK_MAX", (Object)"&c%player% a d\u00e9j\u00e0 le rang maximum : %rank%");
        messages.addDefault("Message.RANK_MIN", (Object)"&c%player% a d\u00e9j\u00e0 le rang minimum : %rank%");
        messages.addDefault("Message.RANK_UNKNOW", (Object)"&c%player% a un rang inconnu : %rank%");
        messages.addDefault("Message.RANK_ALREADY_GIVE", (Object)"&cLe rang %rank% est d\u00e9j\u00e0 attribu\u00e9 \u00e0 %player% !");
        messages.addDefault("Message.GUILD", (Object)"&eGuilde");
        messages.addDefault("Message.RANK", (Object)"&eRang");
        messages.addDefault("Message.JOINED", (Object)"&eDate d'entr\u00e9e");
        this.messagesYML.saveConfig();
    }
    
    private void generatePlayers() {
        final FileConfiguration players = this.playersYML.getConfig();
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.LastSeenAs", (Object)"Heimdal");
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.Guild", (Object)"067e6162-3b6f-4ae2-a171-2470b63dff00");
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.Rank", (Object)"LEAD");
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.Joined", (Object)null);
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.Invitation", (Object)null);
        this.playersYML.saveConfig();
        for (final String key : players.getConfigurationSection("Players").getKeys(false)) {
            final String guild = players.getString("Players." + key + ".Guild");
            final String rank = players.getString("Players." + key + ".Rank");
            final Long joined = players.getLong("Players." + key + ".Joined");
            final String invitation = players.getString("Players." + key + ".Invitation");
            final OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(key));
            UUID UUID_Guild = null;
            UUID UUID_invitation = null;
            if (guild != null) {
                UUID_Guild = UUID.fromString(guild);
            }
            if (invitation != null) {
                UUID_invitation = UUID.fromString(invitation);
            }
            if (player == null) {
                this.plugin.sendConsole("[players.yml] Can not find player with UUID " + key);
            }
            else {
                final User user = new User(player.getUniqueId(), UUID_Guild, rank, joined, UUID_invitation);
                this.plugin.addPlayers(user);
                final Guild guildPlayer = this.plugin.getGuild(UUID_Guild);
                if (guildPlayer != null) {
                    guildPlayer.addMember(user);
                }
                else {
                    this.plugin.sendConsole("[player.yml] Can not find guild with UUID " + UUID_Guild);
                }
            }
        }
    }
    
    public void savePlayers() {
        final FileConfiguration players = this.playersYML.getConfig();
        players.set("Players", (Object)null);
        for (final UUID UUID_Player : this.plugin.getPlayers().keySet()) {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(UUID_Player);
            if (player != null) {
                players.set("Players." + player.getUniqueId().toString() + ".LastSeenAs", (Object)player.getName());
                players.set("Players." + player.getUniqueId().toString() + ".Guild", (Object)((this.plugin.getUser(player.getUniqueId()).getGuild() != null) ? this.plugin.getUser(player.getUniqueId()).getGuild().toString() : null));
                players.set("Players." + player.getUniqueId().toString() + ".Rank", (Object)this.plugin.getUser(player.getUniqueId()).getRank());
                players.set("Players." + player.getUniqueId().toString() + ".Joined", (Object)this.plugin.getUser(player.getUniqueId()).getJoined());
                players.set("Players." + player.getUniqueId().toString() + ".Invitation", (Object)((this.plugin.getUser(player.getUniqueId()).getInvitation() != null) ? this.plugin.getUser(player.getUniqueId()).getInvitation().toString() : null));
            }
            else {
                this.plugin.sendConsole("Player not found " + UUID_Player);
            }
        }
        this.playersYML.saveConfig();
        this.plugin.sendConsole("Players saved !");
    }
    
    private void generateGuilds() {
        final FileConfiguration guilds = this.guildsYML.getConfig();
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Name", (Object)"Justice");
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Lead", (Object)"d4810f3a-46e5-41fe-831b-cafdd3a9ff72");
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.World", (Object)"world");
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.X", (Object)32);
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.Y", (Object)65);
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.Z", (Object)48);
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.Yaw", (Object)0L);
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.Pitch", (Object)0L);
        this.guildsYML.saveConfig();
        for (final String key : guilds.getConfigurationSection("Guilds").getKeys(false)) {
            final String name = guilds.getString("Guilds." + key + ".Name");
            final String lead = guilds.getString("Guilds." + key + ".Lead");
            final String color = guilds.getString("Guilds." + key + ".Color");
            final String prefix = guilds.getString("Guilds." + key + ".Prefix");
            final String suffix = guilds.getString("Guilds." + key + ".Suffix");
            final Location base = new Location(Bukkit.getWorld(guilds.getString("Guilds." + key + ".Base.World")), (double)guilds.getInt("Guilds." + key + ".Base.X"), (double)guilds.getInt("Guilds." + key + ".Base.Y"), (double)guilds.getInt("Guilds." + key + ".Base.Z"), (float)guilds.getDouble("Guilds." + key + ".Base.Yaw"), (float)guilds.getDouble("Guilds." + key + ".Base.Pitch"));
            final UUID UUID_Lead = null;
            if (lead != null) {
                UUID.fromString(lead);
            }
            final Guild g = new Guild(UUID.fromString(key), name, UUID_Lead, color, prefix, suffix, base);
            this.plugin.addGuild(g);
        }
    }
    
    public void saveGuilds() {
        final FileConfiguration guilds = this.guildsYML.getConfig();
        guilds.set("Guilds", (Object)null);
        for (final Guild guild : this.plugin.getGuilds()) {
            guilds.set("Guilds." + guild.getId().toString() + ".Name", (Object)guild.getName());
            guilds.set("Guilds." + guild.getId().toString() + ".Lead", (Object)((guild.getLead() != null) ? guild.getLead().toString() : null));
            guilds.set("Guilds." + guild.getId().toString() + ".Color", (Object)guild.getColor());
            guilds.set("Guilds." + guild.getId().toString() + ".Prefix", (Object)guild.getPlayerPrefix());
            guilds.set("Guilds." + guild.getId().toString() + ".Suffix", (Object)guild.getPlayerSuffix());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.World", (Object)guild.getLocation().getWorld().getName());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.X", (Object)guild.getLocation().getBlockX());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.Y", (Object)guild.getLocation().getBlockY());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.Z", (Object)guild.getLocation().getBlockZ());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.Yaw", (Object)guild.getLocation().getYaw());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.Pitch", (Object)guild.getLocation().getPitch());
        }
        this.guildsYML.saveConfig();
        this.plugin.sendConsole("Guilds saved !");
    }
    
    public class CustomConfig
    {
        private final Plugin pl;
        private final String name;
        private final File file;
        private FileConfiguration fileConfig;
        
        public CustomConfig(final Plugin pl, final String name) {
            this.pl = pl;
            this.name = name;
            this.file = new File(pl.getDataFolder(), name + (name.contains(".yml") ? "" : ".yml"));
        }
        
        public FileConfiguration getConfig() {
            if (this.fileConfig == null) {
                this.reloadConfig();
            }
            return this.fileConfig;
        }
        
        private void reloadConfig() {
            this.fileConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
            final InputStream defConfigStream = this.pl.getResource(this.name + ".yml");
            if (defConfigStream != null) {
                final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                this.fileConfig.setDefaults((Configuration)defConfig);
            }
        }
        
        public void saveConfig() {
            try {
                this.getConfig().options().copyDefaults(true);
                this.getConfig().save(this.file);
                this.reloadConfig();
            }
            catch (IOException ex) {
                this.pl.getLogger().log(Level.WARNING, "Couldn''t save {0}.yml", this.name);
            }
        }
    }
}
