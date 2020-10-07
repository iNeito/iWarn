package me.ineito.iwarn.gui;

import me.ineito.iwarn.database.warnings.WarningManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class WarnGUI {
    public WarnGUI(Player p, Player target) {

        Inventory inventory = Bukkit.createInventory(p, 45, ChatColor.DARK_RED+"iWarn: Select Reason");

        ItemStack item = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = item.getItemMeta();

        for(String s : WarningManager.categories.keySet()) {
            meta.setDisplayName(ChatColor.DARK_RED + s);
            meta.setLore(Arrays.asList
                    (
                            ChatColor.GOLD + "Target: " + target.getName(),
                            ChatColor.RED + "[Left Click] to Warn"
                    ));
            item.setItemMeta(meta);
            inventory.addItem(item);
        }


        p.openInventory(inventory);



    }
}
