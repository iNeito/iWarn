package me.ineito.iwarn.commands;

import me.ineito.iwarn.gui.WarnsGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarnsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 1) {
                if(!player.hasPermission("iwarn.view.other")) {
                    player.sendMessage("You don't have permissions to view someones warns");
                    return true;
                }
                Player reported = Bukkit.getPlayer(args[0]);
                if(reported!= null) {
                    new WarnsGUI(player, reported);
                }
            }
            if(args.length == 0) {
                if(!player.hasPermission("iwarn.view.self")) {
                    player.sendMessage("You don't have permissions to view your warns");
                    return true;
                }
                    new WarnsGUI(player, player);
            }
        }
        return true;
    }
}
