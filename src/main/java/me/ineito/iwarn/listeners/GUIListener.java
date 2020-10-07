package me.ineito.iwarn.listeners;

import me.ineito.iwarn.Messenger;
import me.ineito.iwarn.database.warnings.Warning;
import me.ineito.iwarn.database.warnings.WarningManager;
import me.ineito.iwarn.gui.WarnsGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player)event.getView().getPlayer();
        if(event.getClickedInventory()==null) {
            return;
        }
        if (event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
            if(event.getCurrentItem() != null) {
                ItemStack item = event.getCurrentItem();
                Material type = item.getType();
                String title = ChatColor.stripColor(event.getView().getTitle());
                String name = ChatColor.stripColor(title.split(": ")[1]);


                if(title.contains("iWarn: Select Reason")) {
                    if(type == Material.GOLD_BLOCK) {
                        String target = item.getItemMeta().getLore().get(0).split(":")[1].trim();
                        String reason = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                        Player targetPlayer = Bukkit.getPlayer(target);
                        Warning warning = new Warning(String.valueOf(targetPlayer.getUniqueId()), String.valueOf(player.getUniqueId()), WarningManager.categories.get(reason), false);
                        int i = WarningManager.createWarning(warning);
                        Messenger.warningNotification(warning);
                        String cmd = warning.getCategory().getPunishments().get(i);
                        if(cmd != null) {
                            String cmd2 = cmd.replace("%player%", targetPlayer.getName());
                            Bukkit.dispatchCommand(player, cmd2);
                        }

                        event.setCancelled(true);
                        player.closeInventory();
                    }
                }




                if(title.contains("iWarn: Warns for ")) {
                    name = ChatColor.stripColor(title.split(": Warns for")[1]).trim();
                    if(type == Material.BOOK) {
                        String[] lore = item.getItemMeta().getLore().get(2).split(":");
                        int id = Integer.parseInt(lore[1].trim());
                        WarningManager.removeWarning(id);
                        System.out.println("Removed warning " + id + " from Warnings Table");
                    }
                    event.setCancelled(true);
                    player.closeInventory();
                    Player target = Bukkit.getPlayer(name);
                    if(target != null) {
                        new WarnsGUI(player, target);
                    }
                }
            }
        }
    }

}
