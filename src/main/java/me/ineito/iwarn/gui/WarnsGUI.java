package me.ineito.iwarn.gui;

import me.ineito.iwarn.database.warnings.Warning;
import me.ineito.iwarn.database.warnings.WarningManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class WarnsGUI {

    public WarnsGUI(Player p, Player target) {
        Inventory inventory = Bukkit.createInventory(p ,45, ChatColor.DARK_RED+"iWarn: Warns for " + target.getName());
        ArrayList<Warning> warnings =  WarningManager.getWarnings(target.getUniqueId());
        if(warnings == null) {
            return;
        }
        int size = 36;
        if(warnings.size()<36) {
            size = warnings.size();
        }
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta= item.getItemMeta();
        for(int i = 0; i < size; i++) {
            meta.setDisplayName(ChatColor.DARK_RED+"Warning #" + (i+1));
            String admin = warnings.get(i).isConsole() ? "Console" : Bukkit.getOfflinePlayer(warnings.get(i).getAdminUUID()).getName();
            String reason =  warnings.get(i).getCategory().getReason();
            meta.setLore(
                    Arrays.asList(
                            ChatColor.RED+"Warned by: " + admin,
                            ChatColor.GOLD +"Reason: " + reason,
                            ChatColor.GOLD+"ID: " + warnings.get(i).getId()
                    )
            );
            item.setItemMeta(meta);
            inventory.addItem(item);
        }
        p.openInventory(inventory);

    }


}
