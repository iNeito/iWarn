package me.ineito.iwarn.commands;

import me.ineito.iwarn.Messenger;
import me.ineito.iwarn.database.warnings.Warning;
import me.ineito.iwarn.database.warnings.WarningManager;
import me.ineito.iwarn.gui.WarnGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class WarnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                Player reported = Bukkit.getPlayer(args[0]);
                if(reported!= null) {
                    new WarnGUI(player, reported);
                }
            }
        }
            if(args.length == 2) {
                Player reported = Bukkit.getPlayer(args[0]);
                String category = args[1];
                if(!WarningManager.categories.containsKey(category)) {
                    sender.sendMessage("This category doesn't exist");
                    return true;
                }
                if(reported!= null) {
                    Warning warning = null;
                    if(sender instanceof Player) {
                        Player player = (Player) sender;
                        if(!player.hasPermission("iwarn.give")) {
                            player.sendMessage("You don't have permissions to warn someone");
                            return true;
                        }
                        if(reported.hasPermission("iwarn.bypass") && reported.getUniqueId() != player.getUniqueId()) {
                            player.sendMessage("You don't have permission to warn this person");
                            return true;
                        }
                        warning = new Warning(String.valueOf(reported.getUniqueId()), String.valueOf(player.getUniqueId()), WarningManager.categories.get(category), false);
                    } else {
                    warning = new Warning(String.valueOf(reported.getUniqueId()), null, WarningManager.categories.get(category), true);
                    }

                    int i = WarningManager.createWarning(warning);
                    Messenger.warningNotification(warning);
                    String cmd = warning.getCategory().getPunishments().get(i);
                    if(cmd != null) {
                        String cmd2 = cmd.replace("%player%", reported.getName());
                        Bukkit.dispatchCommand(sender, cmd2);
                    }
                }
            }
        return true;
    }
}
