package pixelssky.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import pixelssky.managers.PlayersManager;
import pixelssky.objects.Island;
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

	public static Inventory getIslandMenu(SPlayer p){
		Inventory inv = Bukkit.createInventory(null, 9, "§6☰ §3Menu de l'île");

		inv.setItem(0 , Items.get("§5§lTéléporation sur l'île", Material.BIRCH_DOOR_ITEM,(byte) 0));
		inv.setItem(1 , Items.get("§5§lChanger le spawn de l'île", Material.BED,(byte) 5));
		inv.setItem(2 , Items.get("§5§lNiveau de l'île", Material.EXP_BOTTLE,(byte) 0));   
		inv.setItem(3 , Items.getHead("president","§5§lInviter un joueur"));
	

		return inv;
	}
	public static void run_IslandMenu(InventoryClickEvent event){
		Player pl = (Player) event.getWhoClicked();
		SPlayer p = PlayersManager.getSPlayer(pl);
		if(event.getSlot()==0){
			pl.sendTitle("§aBienvenue sur votre île :)", "§cNe tombez pas !", 10,20,10);
			pl.teleport(p.getIsland().getSpawn());
			pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 100, 100);
			pl.closeInventory();
		} else if(event.getSlot()==1){
			pl.sendTitle("§aMise à jour effectuée :)", "§cVotre home a changé !", 10,20,10);
			p.getIsland().setHome(pl.getLocation());
			pl.playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
			pl.closeInventory();
		} else if(event.getSlot()==2){
			p.getIsland().calculateLevel(pl);
			pl.closeInventory();
		} else if(event.getSlot()==3){
			pl.openInventory(getPlayersInventory_invite(p));
		}
	}
	public static Inventory getPlayersInventory_invite(SPlayer pl){
		Island i = pl.getIsland();
		Inventory inv = Bukkit.createInventory(null, ((Bukkit.getOnlinePlayers().size())/9+1)*9, "§6✉ §3Inviter des joueurs");
		for(Player p : Bukkit.getOnlinePlayers()){
			if(!i.getMembers().contains(PlayersManager.getSPlayer(p).getID())){
				inv.addItem(Items.getHead(p));
			}
			
		}
		return inv;
	}
	public static void run_PlayersInventory_invite(InventoryClickEvent event){
		try {
			Player pl = (Player) event.getWhoClicked();
			SkullMeta skull = (SkullMeta) event.getInventory().getItem(event.getSlot()).getItemMeta();
			Player cible = Bukkit.getPlayer(skull.getOwner());
			cible.sendTitle("§a" + pl.getDisplayName() + " vous veut sur son île!", "§eFaites /is accept pour accepter !", 10,20,10);
		}catch(Exception ex){
			
		}
	}

}
