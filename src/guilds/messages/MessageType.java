package guilds.messages;

public enum MessageType {

	ALREADY_IN_GUILD("&e/p/ est déjà dans une guilde."),
	MUST_JOIN_GUILD("&eVOus devez être dans une guilde !"),
	GUILD_CHOSEN("&eVous faites partie d'une guilde et ne pouvez pas en changer."),
	NOT_IN_GUILD("&eVous n'êtes pas dans une guilde"),
        NOT_GUILD("&eAucune guilde."),
	GUILD_CREATED("&eLa guilde /g/ a été créé."),
	GUILD_EXISTS("&e/g/ existe déjà."),
	GUILD_DELETED("&eLa guilde /g/ a été supprimée."),
	GUILD_LEAVE("&eVous venez de quitter votre guilde."),
	JOIN_COMMAND("&e/guilds join <guild>"),
	NO_FRIENDLY_FIRE("&eVous ne pouvez pas attaquer des personnes de votre guilde !"),
	PEACEKEEPER("&eyou are a peacekeeper!"),
	COMMAND_SETBASE("&eParamètres manquants... /guilds setbase <guild>"),
	COMMAND_SETTING("&eParamètres manquants... /guilds setting <setting> <value>"),
	COMMAND_SETTINGS("&eParamètres manquants... /guilds settings <guild> <settings> <value>"),
	COMMAND_MESSAGE("&eParamètres manquants... /guilds message <message> <value>"),
	COMMAND_TOGGLE("&eParamètres manquants... /guilds toggle <guild> <world/biome>"),
	COMMAND_WORLD("&eParamètres manquants... /guilds world <guild> <world>"),
	COMMAND_BIOME("&eParamètres manquants... /guilds biome <guild> <biome>"),
	COMMAND_REMOVE("&eParamètres manquants... /guilds remove <guild>"),
	COMMAND_CREATE("&eParamètres manquants... /guilds create <guild>"),
	COMMAND_KICK("&eParamètres manquants... /guilds kick <player>"),
	COMMAND_ADD("&eParamètres manquants... /guilds add <player> <guild>"),
	COMMAND_JOIN("&eParamètres manquants... /guilds join <guild>"),
	GUILD_JOIN("&eVous rejoignez la guilde /g/"),
	PLAYER_GUILD_JOIN("&e/p/ ajouté à la guilde /g/"),
	COOLDOWN("&e/s/ Cooling Down!"),
	NO_PERMISSION_JOIN("&eVous ne pouvez pas rejoindre la guilde /g/."),
	NO_PERMISSION("&eVous n'avez pas la permission."),
	SAVE("&esave complete."),
	LOAD("&eload complete."),
	BASE_SET("&eTannière de guilde /g/ déplacée."),
	PLAYER_REMOVED_FROM_GUILD("&e/p/ a été exclu de la guilde."),
	YOU_REMOVED_FROM_GUILD("&eVous avez été exclu de votre guilde."),
	GUILD_NOT_RECOGNISED("&eguilde /g/ non trouvée."),
	PLAYER_NOT_RECOGNISED("&ejoueur /p/ non trouvé."),
	CONSOLE_ERROR("&ecommand not supported by console."),
	SETTING_NOT_RECOGNISED("&e/s/ not recognised."),
	SETTING_SET("&e/s/ set to /v/"),
	SETTINGS_SET("&e/s/ set to /v/ for /g/"),
	TOGGLE_SET("&e/t/ set to /v/ for /g/"),
	NOT_BOOLEAN("&ethe value you entered is not true/false."),
	NOT_INT("&ethe value you entered is not an integer."),
	NOT_DOUBLE("&ethe value you entered is not a double."),
	NOT_LONG("&ethe value you entered is not a long."),
	MESSAGE_NOT_RECOGNISED("&e/m/ non trouvé."),
	MESSAGE_SET("&e/m/ set to /v/"),
	CHANGE_TIME("&e/t/ seconds before you can change guild."),
	COMMAND_NOT_RECOGNISED("&e/guilds /a/ non trouvée."),
	INPUT_NOT_RECOGNISED("&einput /i/ not recognised."),
	TOGGLE_NOT_RECOGNISED("&eplease use 'world' or 'biome' to toggle."),
	WORLD_NOT_RECOGNISED("&e/w/ not a recognised world."),
	WRONG_INPUT("&e/p/ does not have /i/ setting."),
        BASE_DELAY("&eVous serez téléporté dans /delay/ secondes."),
        TELEPORTATION("&eTéléportation dans votre base"), 
        COMMAND_INFO("&eParamètres manquants... /guilds info <player>");
    
    String str;
   
    MessageType(String str) {
      	this. str = str;
    }

    public String getMessage() {
    	return this.str;
    }

    public void setMessage(String str) {
    	this.str = str;
    }
   
    @Override
    public String toString() {
    	return super.toString();
    }
	
}
