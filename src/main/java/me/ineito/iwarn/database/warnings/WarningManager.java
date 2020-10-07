package me.ineito.iwarn.database.warnings;

import me.ineito.iwarn.database.Database;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class WarningManager {


    public static HashMap<String, WarningCategory> categories;

    public static void loadCategories(FileConfiguration config) {
        categories = new HashMap<>();
        ConfigurationSection section = config.getConfigurationSection("WarnCategories");
        if(section == null) {
            return;
        }
        Set<String> set = config.getConfigurationSection("WarnCategories").getKeys(false);
        if(set.size() == 0) {
            return;
        }
        for(String name : set) {
            String reason = config.getString("WarnCategories."+name+".Reason");
            if(reason == null) {reason = name;}
            Set<String> punishments = config.getConfigurationSection("WarnCategories."+name+".Punishments").getKeys(false);
            HashMap<Integer, String> resultPunishments = new HashMap<>();
            if(punishments.size() !=0) {
                for(String p : punishments) {
                    int i = -1;
                    try {
                        i = Integer.parseInt(p);
                    } catch (NumberFormatException ex) {
                        continue;
                    }
                    String command = config.getString("WarnCategories."+name+".Punishments."+p+".Command");
                    resultPunishments.put(i, command);
                }
            }
            WarningCategory category = new WarningCategory(name, reason, resultPunishments);
            categories.put(name, category);
        }

        clearRemovedReasons();
    }

    public static void clearRemovedReasons() {

        String condition = "";
        int i = 0;
        for(String s : categories.keySet()) {
            if(i == 0) {
                condition += " NOT Reason='"+s+"'";
            } else {
                condition += " AND NOT Reason='"+s+"'";
            }
            i++;
        }
        String query = "DELETE FROM Warnings WHERE" + condition;
        Connection connection = Database.getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.execute();

            connection.close();
        } catch (SQLException e) {
            System.out.println("There was an error adding warning for player to warnings table");
            System.out.println(e.getMessage());
        }
    }



    public static int createWarning(Warning warning) {
        Connection connection = Database.getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("INSERT INTO Warnings(PlayerUUID, AdminUUID, Reason) VALUES(?, ?, ?)");
            statement.setString(1, String.valueOf(warning.getPlayerUUID()) );
            if(warning.isConsole()) {
                statement.setString(2 , "Console");
            } else {
                statement.setString(2 , String.valueOf(warning.getAdminUUID()));
            }
            statement.setString(3 , warning.getCategory().getSlug());
            statement.execute();
            connection.close();

        } catch (SQLException e) {
            System.out.println("There was an error adding warning for player to warnings table");
        }

        return getWarningCount(warning);


    }

    public static int getWarningCount(Warning warning) {
        Connection connection = Database.getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT count(*) FROM Warnings WHERE PlayerUUID = ? AND Reason = ?");
            statement.setString(1, String.valueOf(warning.getPlayerUUID() ));
            statement.setString(2, warning.getCategory().getSlug());
            ResultSet res = statement.executeQuery();
            res.next();
            int count = res.getInt("count(*)");
            connection.close();
            return count;
        } catch (SQLException e) {
            System.out.println("There was an error adding warning for player to warnings table");
        }
        return 0;
    }

    public static void removeWarning(int id) {
        Connection connection = Database.getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("DELETE FROM Warnings WHERE WarningID = ?");
            statement.setInt(1, id);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            System.out.println("There was an error removing warning from player ");
        }
    }

    public static ArrayList<Warning> getWarnings(UUID uuid) {
        Connection connection = Database.getConnection();
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement("SELECT * FROM Warnings WHERE PlayerUUID = ? ORDER BY WarningID DESC");
            statement.setString(1, String.valueOf(uuid));
            ResultSet rows = statement.executeQuery();
            ArrayList<Warning> warnings = new ArrayList<>();

            while (rows.next()) {
                int id = rows.getInt("WarningID");
                String playerUUID = rows.getString("PlayerUUID");
                String adminUUID = rows.getString("AdminUUID");
                String reason = rows.getString("Reason");
                Date date = rows.getDate("DateAdded");
                Warning warning = null;
                if(adminUUID.equalsIgnoreCase("console")) {
                    warning = new Warning(id, playerUUID, null, categories.get(reason), date, true);
                } else {
                    warning = new Warning(id, playerUUID, adminUUID, categories.get(reason), date);
                }
                warnings.add(warning);
            }
            connection.close();
            return warnings;
        } catch (SQLException e) {
            System.out.println("There was an error getting warnings for player");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
