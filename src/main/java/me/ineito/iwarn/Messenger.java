package me.ineito.iwarn;

import me.ineito.iwarn.database.warnings.Warning;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Messenger {

    private static final String prefix = ChatColor.DARK_RED + "[* iWarn *]" + ChatColor.RESET;
    private static int whoGlobal;
    private static String warnGlobalMessage;
    private static String warnTargetMessage;
    private static String warnAdminMessage;


    public static void loadConfig(FileConfiguration config) {
        whoGlobal = config.getInt("WarnGlobalMessageReceiver");
        warnGlobalMessage =  ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("WarnGlobalMessage")));
        warnTargetMessage = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("WarnTargetMessage")));
        warnAdminMessage = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("WarnAdminMessage")));
    }



    public static void warningNotification(Warning w) {
        warnTarget(w);
        warnAdmin(w);
        if(whoGlobal > 0) {
            warnGlobal(w);
        }
    }


    public static void warnTarget(Warning w) {
        String admin = w.isConsole() ? "Console" : Bukkit.getOfflinePlayer(w.getAdminUUID()).getName();
        Player p = Bukkit.getPlayer(w.getPlayerUUID());
        String message = warnTargetMessage.replace("%target%", p.getName()).replace("%admin%", admin).replace("%reason%", w.getCategory().getReason());
        p.sendMessage(prefix + " " + message);
    }
    public static void warnAdmin(Warning w) {
        String admin = w.isConsole() ? "Console" : Bukkit.getOfflinePlayer(w.getAdminUUID()).getName();
        CommandSender sender = w.isConsole() ? Bukkit.getServer().getConsoleSender() : Bukkit.getPlayer(w.getAdminUUID());
        if(sender != null) {
            String message = warnAdminMessage.replace("%target%", Bukkit.getOfflinePlayer(w.getPlayerUUID()).getName()).replace("%admin%", admin).replace("%reason%", w.getCategory().getReason());
            sender.sendMessage(prefix + " " + message);
        }
    }
    public static void warnGlobal(Warning w) {
        String admin = w.isConsole() ? "Console" : Bukkit.getOfflinePlayer(w.getAdminUUID()).getName();
        String target = Bukkit.getOfflinePlayer(w.getPlayerUUID()).getName();
        String message = warnGlobalMessage.replace("%target%", target).replace("%admin%", admin).replace("%reason%", w.getCategory().getReason());
        for(Player p : Bukkit.getOnlinePlayers()) {
            String ps = String.valueOf(p.getUniqueId());
            String pa = String.valueOf(w.getAdminUUID());
            String pp = String.valueOf(w.getPlayerUUID());
            if(!ps.equalsIgnoreCase(pa) && !ps.equalsIgnoreCase(pp)) {
                if(whoGlobal == 1) {
                    if(p.hasPermission("iwarn.admin")) {
                        p.sendMessage(prefix + " " + message);
                    }
                } else {
                    p.sendMessage(prefix + " " + message);
                }
            }
        }
    }


}
