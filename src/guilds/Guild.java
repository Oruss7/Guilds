package guilds;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;

public class Guild {

    private UUID id;
    private String name;
    private UUID lead;
    private String color;
    private String playerPrefix;
    private String playerSuffix;
    private Location location;
    // liste des membres (utile pour le tchat de guilde)
    private List<User> listMember;


    public Guild() {
        id = UUID.randomUUID();

        listMember = new ArrayList<>();
    }

    public Guild(UUID id, String name, UUID lead, String color, String prefix, String suffix, Location location) {
        this.id = id;
        this.name = name;
        this.lead = lead;
        this.color = color;
        this.playerPrefix = prefix;
        this.playerSuffix = suffix;
        this.location = location;

        listMember = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getLead() {
        return lead;
    }

    public void setLead(UUID lead) {
        this.lead = lead;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlayerPrefix() {
        return playerPrefix;
    }

    public void setPlayerPrefix(String playerPrefix) {
        this.playerPrefix = playerPrefix;
    }

    public String getPlayerSuffix() {
        return playerSuffix;
    }

    public void setPlayerSuffix(String playerSuffix) {
        this.playerSuffix = playerSuffix;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addMember(User member) {
        if (!listMember.contains(member)) {
            listMember.add(member);
        }
    }

    public void removeMember(User member) {
        if (listMember.contains(member)) {
            listMember.remove(member);
            
        }
    }

    public List<User> getListMember() {
        return listMember;
    }

}
