package me.ineito.iwarn.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class iWarnCommand  implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("help")) {
                String help = ChatColor.GRAY + "==================> "+ChatColor.DARK_RED+"[* iWarn *] "+ChatColor.GRAY+" <==================\n" +
                        ChatColor.DARK_RED+"/warn <target> <reason>   "+ChatColor.GRAY+"| warns target for reason\n" +
                        ChatColor.DARK_RED+"/warns                    "+ChatColor.GRAY+"| opens players warns\n" +
                        ChatColor.DARK_RED+"/warns <target>           "+ChatColor.GRAY+"| opens targets warns";
                if(sender instanceof Player) {
                    help = ChatColor.GRAY + "==================> "+ChatColor.DARK_RED+"[* iWarn *] "+ChatColor.GRAY+" <==================\n" +
                            ChatColor.DARK_RED+"/warn <target> <reason>   "+ChatColor.GRAY+"| warns target for reason\n" +
                            ChatColor.DARK_RED+"/warns                          "+ChatColor.GRAY+"| opens players warns\n" +
                            ChatColor.DARK_RED+"/warns <target>              "+ChatColor.GRAY+"| opens targets warns";
                }
                sender.sendMessage(help);
            }
        }
        return true;
    }
}
