package pixelssky.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import pixelssky.objects.SPlayer;

public class Inventories {
	
	public static Inventory getCreateIslandMenu(SPlayer p){
		Inventory inv = Bukkit.createInventory(null, 9, "§6✚ §3Créer une nouvelle île");

		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a§l Ressources : §b★§7☆☆");
		lore.add("§a§l Niveau :       §b★§7☆☆");
		lore.add("§a§l Difficulté :    §b★★★");
		
		inv.setItem(1 , Items.get("§5§lÎle ultra Hard", Material.DIRT,(byte) 0, lore));
		
		lore = new ArrayList<String>();
		lore.add("§a§l Ressources : §b★★§7☆");
		lore.add("§a§l Niveau :       §b★§7☆☆");
		lore.add("§a§l Difficulté :    §b★★§7☆");
		lore.add("§cSi vous prennez cette île vous serez");
		lore.add("§cclassé derrière les îles ultra hard");
		
		inv.setItem(2 , Items.get("§5§lÎle de base", Material.APPLE,(byte) 0, lore));
		
		lore = new ArrayList<String>();
		lore.add("§a§l Ressources : §b★★★");
		lore.add("§a§l Niveau :       §b★★§7☆");
		lore.add("§a§l Difficulté :    §b★§7☆☆");
		lore.add("§cSi vous prennez cette île vous serez");
		lore.add("§cclassé derrière les îles de base");
		
		inv.setItem(3 , Items.get("§5§lÎle facile", Material.GOLD_INGOT,(byte) 0, lore));
		
		lore = new ArrayList<String>();
		lore.add("§a§l Ressources : §b★★★");
		lore.add("§a§l Niveau :       §b★★★");
		lore.add("§a§l Difficulté :    §7☆☆☆");
		lore.add("§cSi vous prennez cette île vous serez");
		lore.add("§4§l PAS §cclassé !!");
		
		inv.setItem(4 , Items.get("§5§lÎle d'exemple", Material.DIAMOND,(byte) 0, lore));
		
		inv.setItem(8 , Items.get("§6Rejoindre une équipe", Material.ANVIL,(byte) 0));
		
		return inv;
	}
	
	public static void run_createIslandMenu(InventoryClickEvent event){
		
		event.getWhoClicked().sendMessage("Clic sur item : " + event.getSlot() + " item : " + event.getInventory().getItem(event.getSlot()).getI18NDisplayName());
		
	}

}
