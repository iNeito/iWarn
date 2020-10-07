package me.ineito.iwarn.database.warnings;


import java.sql.Date;
import java.util.UUID;

public class Warning {

    private  int id;
    private  UUID playerUUID;
    private  UUID adminUUID;
    private  WarningCategory category;



    private  Date dateAdded;
    private boolean console;

    public boolean isConsole() {
        return console;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public int getId() {
        return id;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public UUID getAdminUUID() {
        return adminUUID;
    }

    public WarningCategory getCategory() {return category;}

    public Warning(int id, String playerUUID, String adminUUID, WarningCategory category, Date dateAdded) {
        this.id = id;
        this.playerUUID = UUID.fromString(playerUUID);
        this.adminUUID = UUID.fromString(adminUUID);
        this.category = category;
        this.dateAdded = dateAdded;
    }
    public Warning(int id, String playerUUID, String adminUUID, WarningCategory category, Date dateAdded, boolean console) {
        this.id = id;
        this.playerUUID = UUID.fromString(playerUUID);
        if(!console) {this.adminUUID = UUID.fromString(adminUUID); }
        this.category = category;
        this.dateAdded = dateAdded;
        this.console = console;
    }

    public Warning(String playerUUID, String adminUUID, WarningCategory category, boolean console) {
        this.playerUUID = UUID.fromString(playerUUID);
        if(!console) {this.adminUUID = UUID.fromString(adminUUID); }
        this.category = category;
        this.console = console;
    }



}
