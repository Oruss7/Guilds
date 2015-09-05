package guilds;

import org.bukkit.*;
import java.util.*;

public class Guild
{
    private UUID id;
    private String name;
    private UUID lead;
    private String color;
    private String playerPrefix;
    private String playerSuffix;
    private Location location;
    private List<User> listMember;
    
    public Guild() {
        this.id = UUID.randomUUID();
        this.listMember = new ArrayList<User>();
    }
    
    public Guild(final UUID id, final String name, final UUID lead, final String color, final String prefix, final String suffix, final Location location) {
        this.id = id;
        this.name = name;
        this.lead = lead;
        this.color = color;
        this.playerPrefix = prefix;
        this.playerSuffix = suffix;
        this.location = location;
        this.listMember = new ArrayList<User>();
    }
    
    public UUID getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public UUID getLead() {
        return this.lead;
    }
    
    public void setLead(final UUID lead) {
        this.lead = lead;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public void setColor(final String color) {
        this.color = color;
    }
    
    public String getPlayerPrefix() {
        return this.playerPrefix;
    }
    
    public void setPlayerPrefix(final String playerPrefix) {
        this.playerPrefix = playerPrefix;
    }
    
    public String getPlayerSuffix() {
        return this.playerSuffix;
    }
    
    public void setPlayerSuffix(final String playerSuffix) {
        this.playerSuffix = playerSuffix;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation(final Location location) {
        this.location = location;
    }
    
    public void addMember(final User member) {
        if (!this.listMember.contains(member)) {
            this.listMember.add(member);
        }
    }
    
    public void removeMember(final User member) {
        if (this.listMember.contains(member)) {
            this.listMember.remove(member);
        }
    }
    
    public List<User> getListMember() {
        return this.listMember;
    }
}
