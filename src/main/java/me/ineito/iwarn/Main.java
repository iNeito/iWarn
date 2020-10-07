package me.ineito.iwarn;
import me.ineito.iwarn.commands.WarnCommand;
import me.ineito.iwarn.commands.WarnsCommand;
import me.ineito.iwarn.commands.iWarnCommand;
import me.ineito.iwarn.database.Database;

import me.ineito.iwarn.database.warnings.WarningManager;
import me.ineito.iwarn.listeners.GUIListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.MessageDigest;

public final class Main extends JavaPlugin {
    public static String getConnectionUrl() {
        return connectionUrl;
    }

    private static String connectionUrl;
    @Override
    public void onEnable() {
        connectionUrl = "jdbc:h2:"+getDataFolder().getAbsolutePath() + "/data/database";
        saveDefaultConfig();
        WarningManager.loadCategories(getConfig());
        Messenger.loadConfig(getConfig());
        Database.initDatabase();
        getCommand("warn").setExecutor(new WarnCommand());
        getCommand("warns").setExecutor(new WarnsCommand());
        getCommand("iwarn").setExecutor(new iWarnCommand());
        getServer().getPluginManager().registerEvents(new GUIListener(),this);
    }
}
