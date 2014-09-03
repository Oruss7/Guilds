package guilds;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {

    private final GuildsBasic plugin;
    public CustomConfig configYML, messagesYML, playersYML, guildsYML;

    public Config(GuildsBasic pl) {
        this.plugin = pl;
    }

    public void start() {
        configYML = new CustomConfig(plugin, "config");
        messagesYML = new CustomConfig(plugin, "messages");
        playersYML = new CustomConfig(plugin, "players");
        guildsYML = new CustomConfig(plugin, "guilds");
        generateConfig();
        generateMessages();
        generateGuilds();
        generatePlayers();

    }

    private void generateConfig() {
        FileConfiguration config = configYML.getConfig();

        config.addDefault("config.ENABLE_CHANGE_GUILD", true);
        config.addDefault("config.ENABLE_NO_GUILD", true);
        config.addDefault("config.ENABLE_DEFAULT_GUILD", false);
        config.addDefault("config.ENABLE_FRIENDLY_FIRE_PVP", true);
        config.addDefault("config.ENABLE_BASE_ON_DEATH", true);
        config.addDefault("config.ENABLE_PLAYER_GUILD_PREFIX", true);
        config.addDefault("config.ENABLE_PLAYER_LISTNAME_COLOR", false);
        config.addDefault("config.ENABLE_PLAYER_LISTNAME_GUILD", false);
        config.addDefault("config.ENABLE_GUILD_CHAT_FORMAT", false);
        config.addDefault("config.ENABLE_GUILD_JOIN_PERMISSIONS", false);
        config.addDefault("config.ENABLE_GUILD_NAME_ON_BROADCAST", false);
        config.addDefault("config.SET_GUILDS_BROADCAST_COLOR", "&d");
        config.addDefault("config.SET_DEFAULT_GUILD", "Les Voleurs");
        config.addDefault("config.SET_CHANGE_GUILD_TIME", 0);
        config.addDefault("config.BASE.COOLDOWN", 30);
        String[] enableWorlds = {"world", "word2"};
        config.addDefault("config.enableWorlds", Arrays.asList(enableWorlds));

        configYML.saveConfig();
    }

    private void generateMessages() {
        FileConfiguration messages = messagesYML.getConfig();

        messages.addDefault("Message.ALREADY_IN_GUILD", "&e%player% est déjà dans une guilde.");
        messages.addDefault("Message.MUST_JOIN_GUILD", "&eVous devez être dans une guilde !");
        messages.addDefault("Message.GUILD_CHOSEN", "&eVous faites partie d'une guilde et ne pouvez pas en changer.");
        messages.addDefault("Message.NOT_IN_GUILD", "&eVous n'êtes pas dans une guilde");
        messages.addDefault("Message.PLAYER_NOT_IN_GUILD", "&%player% n'est pas dans une guilde.");
        messages.addDefault("Message.NOT_GUILD", "&eAucune guilde.");
        messages.addDefault("Message.GUILD_CREATED", "&eLa guilde %guild% a été créé.");
        messages.addDefault("Message.GUILD_EXISTS", "&e%guild% existe déjà.");
        messages.addDefault("Message.GUILD_DELETED", "&eLa guilde %guild% a été supprimée.");
        messages.addDefault("Message.GUILD_LEAVE", "&eVous venez de quitter la guilde %guild%.");
        messages.addDefault("Message.GUILD_AS_LEAVE", "&e%player% vient de quitter la guilde.");
        messages.addDefault("Message.JOIN_COMMAND", "&e/guilds join <guild>");
        messages.addDefault("Message.NO_FRIENDLY_FIRE", "&eVous ne pouvez pas attaquer des personnes de votre guilde !");
        messages.addDefault("Message.COMMAND_SETBASE", "&eParamètres manquants... /guilds setbase [guild]");
        messages.addDefault("Message.COMMAND_MESSAGE", "&eParamètres manquants... /guilds message <message> <value>");
        messages.addDefault("Message.COMMAND_REMOVE", "&eParamètres manquants... /guilds remove <guild>");
        messages.addDefault("Message.COMMAND_CREATE", "&eParamètres manquants... /guilds create <guild>");
        messages.addDefault("Message.COMMAND_KICK", "&eParamètres manquants... /guilds kick <player>");
        messages.addDefault("Message.COMMAND_ADD", "&eParamètres manquants... /guilds add <player> <guild>");
        messages.addDefault("Message.COMMAND_JOIN", "&eParamètres manquants... /guilds promote <player>");
        messages.addDefault("Message.COMMAND_PROMOTE", "&eParamètres manquants... /guilds promote/demote <player>");
        messages.addDefault("Message.COMMAND_DEMOTE", "&eParamètres manquants... /guilds join <guild>");
        messages.addDefault("Message.GUILD_JOIN", "&e%player% vient de rejoidre la guilde.");
        messages.addDefault("Message.PLAYER_GUILD_JOIN", "&e%player% ajouté à la guilde %guild%");
        messages.addDefault("Message.NO_PERMISSION_JOIN", "&eVous ne pouvez pas rejoindre la guilde %guild%.");
        messages.addDefault("Message.NO_PERMISSION", "&eVous n'avez pas la permission.");
        messages.addDefault("Message.SAVE", "&esave complete.");
        messages.addDefault("Message.LOAD", "&eload complete.");
        messages.addDefault("Message.BASE_SET", "&eTannière de guilde %guild% déplacée.");
        messages.addDefault("Message.PLAYER_REMOVED_FROM_GUILD", "&e%playerTarget% a été exclu de la guilde par %player%.");
        messages.addDefault("Message.YOU_REMOVED_FROM_GUILD", "&eVous avez été exclu de votre guilde.");
        messages.addDefault("Message.GUILD_NOT_RECOGNISED", "&eGuilde %guild% non trouvée.");
        messages.addDefault("Message.CAN_NOT_REMOVE", "&cVous ne pouvez pas exclure %player% car il n'est pas dans votre guilde.");
        messages.addDefault("Message.PLAYER_NOT_RECOGNISED", "&eJoueur %player% non trouvé.");
        messages.addDefault("Message.CONSOLE_ERROR", "&ecommand not supported by console.");
        messages.addDefault("Message.NOT_BOOLEAN", "&ethe value you entered is not true%false.");
        messages.addDefault("Message.NOT_INT", "&ethe value you entered is not an integer.");
        messages.addDefault("Message.NOT_DOUBLE", "&ethe value you entered is not a double.");
        messages.addDefault("Message.NOT_LONG", "&ethe value you entered is not a long.");
        messages.addDefault("Message.MESSAGE_NOT_RECOGNISED", "&e%m% non trouvé.");
        messages.addDefault("Message.MESSAGE_SET", "&e%m% set to %v%");
        messages.addDefault("Message.CHANGE_TIME", "&e%t% secondes avant de pouvoir changer de guilde.");
        messages.addDefault("Message.COMMAND_NOT_RECOGNISED", "&e/guilds %arg% non trouvée.");
        messages.addDefault("Message.INPUT_NOT_RECOGNISED", "&einput %i% not recognised.");
        messages.addDefault("Message.TELEPORTATION", "&eTéléportation dans votre base");
        messages.addDefault("Message.TELEPORTATION_COOLDOWN", "&cVous devez patientez avant de pouvoir de nouveau vous téléporter à votre base.");
        messages.addDefault("Message.CAN_NOT_TELEPORT_HERE", "&cVous ne pouvez pas vous téléporter ici !");
        messages.addDefault("Message.COMMAND_INFO", "&eParamètres manquants... /guilds info [player]");
        messages.addDefault("Message.COMMAND_INVITE", "&eParamètres manquants... /guilds invite <player>");
        messages.addDefault("Message.ALREADY_PENDING", "&eLe joueur a déjà une invitation pour rejoindre la guilde %guild%");
        messages.addDefault("Message.NO_PENDING", "&eVous n'avez aucune invitation");
        messages.addDefault("Message.INVITATION", "&e%player% vous a invité à rejoindre la guilde %guild%. Tapez &f/guilds accept&e pour la rejoindre ou &f/guilds deny&e pour refuser.");
        messages.addDefault("Message.PLAYER_INVITED", "&e%player% a été invité à rejoindre la guilde.");
        messages.addDefault("Message.INVITATION_DENY", "&e%player% a refusé d'entrer dans votre guilde.");
        messages.addDefault("Message.YOU_DENY_INVITATION", "&eVous avez refusé d'entrer dans la guilde %guild%.");
        messages.addDefault("Message.GUILD_LEADER", "&eVous êtes désormais chef de la guilde %guild% !");
        messages.addDefault("Message.GUILD_WITHOUT_LEADER", "&cLa guilde %guild% n'a aucun chef.");
        messages.addDefault("Message.PLAYER_NOT_IN_YOUR_GUILD", "&c%player% n'appartient pas à votre guilde !");
        messages.addDefault("Message.PLAYER_PROMOTE", "&e%player% a été promu au rang de %rank%");
        messages.addDefault("Message.PLAYER_DEMOTE", "&e%player% a été rétrogradé au rang de %rank%");
        messages.addDefault("Message.RANK_MAX", "&c%player% a déjà le rang maximum : %rank%");
        messages.addDefault("Message.RANK_MIN", "&c%player% a déjà le rang minimum : %rank%");
        messages.addDefault("Message.RANK_UNKNOW", "&c%player% a un rang inconnu : %rank%");
        messages.addDefault("Message.RANK_ALREADY_GIVE", "&cLe rang %rank% est déjà attribué à %player% !");
        messages.addDefault("Message.GUILD", "&eGuilde");
        messages.addDefault("Message.RANK", "&eRang");
        messages.addDefault("Message.JOINED", "&eDate d'entrée");

        messagesYML.saveConfig();
    }

    private void generatePlayers() {
        FileConfiguration players = playersYML.getConfig();
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.LastSeenAs", "Heimdal");
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.Guild", "067e6162-3b6f-4ae2-a171-2470b63dff00");
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.Rank", "LEAD");
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.Joined", null);
        players.addDefault("Players.d4810f3a-46e5-40fe-831b-cafdd3a9ff72.Invitation", null);
        playersYML.saveConfig();
        for (String key : players.getConfigurationSection("Players").getKeys(false)) {
            String guild = players.getString("Players." + key + ".Guild");
            String rank = players.getString("Players." + key + ".Rank");
            Long joined = players.getLong("Players." + key + ".Joined");
            String invitation = players.getString("Players." + key + ".Invitation");
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(key));
            UUID UUID_Guild = null;
            UUID UUID_invitation = null;
            if (guild != null) {
                UUID_Guild = UUID.fromString(guild);
            }
            if (invitation != null) {
                UUID_invitation = UUID.fromString(invitation);
            }
            if (player == null) {
                plugin.sendConsole("[players.yml] Can not find player with UUID " + key);
            } else {
                User user = new User(player.getUniqueId(), UUID_Guild, rank, joined, UUID_invitation);
                plugin.addPlayers(user);
                Guild guildPlayer = plugin.getGuild(UUID_Guild);
                if (guildPlayer != null) {
                    guildPlayer.addMember(user);
                } else {
                    plugin.sendConsole("[player.yml] Can not find guild with UUID " + UUID_Guild);
                }

            }
        }
    }

    public void savePlayers() {
        FileConfiguration players = playersYML.getConfig();
        players.set("Players", null);
        for (UUID UUID_Player : plugin.getPlayers().keySet()) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID_Player);
            if (player != null) {
                players.set("Players." + player.getUniqueId().toString() + ".LastSeenAs", player.getName());
                players.set("Players." + player.getUniqueId().toString() + ".Guild", plugin.getUser(player.getUniqueId()).getGuild() != null ? plugin.getUser(player.getUniqueId()).getGuild().toString() : null);
                players.set("Players." + player.getUniqueId().toString() + ".Rank", plugin.getUser(player.getUniqueId()).getRank());
                players.set("Players." + player.getUniqueId().toString() + ".Joined", plugin.getUser(player.getUniqueId()).getJoined());
                players.set("Players." + player.getUniqueId().toString() + ".Invitation", plugin.getUser(player.getUniqueId()).getInvitation() != null ? plugin.getUser(player.getUniqueId()).getInvitation().toString() : null);
            } else {
                plugin.sendConsole("Player not found " + UUID_Player);
            }
        }
        playersYML.saveConfig();
        plugin.sendConsole("Players saved !");
    }

    private void generateGuilds() {
        FileConfiguration guilds = guildsYML.getConfig();
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Name", "Justice");
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Lead", "d4810f3a-46e5-41fe-831b-cafdd3a9ff72");
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.World", "world");
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.X", 32);
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.Y", 65);
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.Z", 48);
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.Yaw", 0L);
        guilds.addDefault("Guilds.067e6162-3b6f-4ae2-a171-2470b63dff00.Base.Pitch", 0L);
        guildsYML.saveConfig();

        for (String key : guilds.getConfigurationSection("Guilds").getKeys(false)) {
            String name = guilds.getString("Guilds." + key + ".Name");
            String lead = guilds.getString("Guilds." + key + ".Lead");
            String color = guilds.getString("Guilds." + key + ".Color");
            String prefix = guilds.getString("Guilds." + key + ".Prefix");
            String suffix = guilds.getString("Guilds." + key + ".Suffix");
            Location base = new Location(Bukkit.getWorld(guilds.getString("Guilds." + key + ".Base.World")), guilds.getInt("Guilds." + key + ".Base.X"), guilds.getInt("Guilds." + key + ".Base.Y"), guilds.getInt("Guilds." + key + ".Base.Z"), (float) guilds.getDouble("Guilds." + key + ".Base.Yaw"), (float) guilds.getDouble("Guilds." + key + ".Base.Pitch"));
            UUID UUID_Lead = null;
            if (lead != null) {
                UUID.fromString(lead);
            }
            Guild g = new Guild(UUID.fromString(key), name, UUID_Lead, color, prefix, suffix, base);
            plugin.addGuild(g);
        }
    }

    public void saveGuilds() {
        FileConfiguration guilds = guildsYML.getConfig();
        guilds.set("Guilds", null);
        for (Guild guild : plugin.getGuilds()) {
            guilds.set("Guilds." + guild.getId().toString() + ".Name", guild.getName());
            guilds.set("Guilds." + guild.getId().toString() + ".Lead", guild.getLead() != null ? guild.getLead().toString() : null);
            guilds.set("Guilds." + guild.getId().toString() + ".Color", guild.getColor());
            guilds.set("Guilds." + guild.getId().toString() + ".Prefix", guild.getPlayerPrefix());
            guilds.set("Guilds." + guild.getId().toString() + ".Suffix", guild.getPlayerSuffix());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.World", guild.getLocation().getWorld().getName());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.X", guild.getLocation().getBlockX());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.Y", guild.getLocation().getBlockY());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.Z", guild.getLocation().getBlockZ());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.Yaw", guild.getLocation().getYaw());
            guilds.set("Guilds." + guild.getId().toString() + ".Base.Pitch", guild.getLocation().getPitch());
        }
        guildsYML.saveConfig();
        plugin.sendConsole("Guilds saved !");
    }

    public class CustomConfig {

        private final Plugin pl;
        private final String name;
        private final File file;
        private FileConfiguration fileConfig;

        public CustomConfig(Plugin pl, String name) {
            this.pl = pl;
            this.name = name;
            file = new File(pl.getDataFolder(), name + (name.contains(".yml") ? "" : ".yml"));
        }

        public FileConfiguration getConfig() {
            if (fileConfig == null) {
                reloadConfig();
            }
            return fileConfig;
        }

        private void reloadConfig() {
            fileConfig = YamlConfiguration.loadConfiguration(file);
            InputStream defConfigStream = pl.getResource(name + ".yml");
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                fileConfig.setDefaults(defConfig);
            }
        }

        public void saveConfig() {
            try {
                getConfig().options().copyDefaults(true);
                getConfig().save(file);
                reloadConfig();
            } catch (IOException ex) {
                pl.getLogger().log(Level.WARNING, "Couldn''t save {0}.yml", name);
            }
        }
    }
}
