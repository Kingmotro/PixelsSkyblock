package pixelssky.main;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;


import pixelssky.managers.DatabaseManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Inventories;
import pixelssky.utils.Locations;

public class EventListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void loginEvent(PlayerLoginEvent event) {
		Player pl = event.getPlayer();
		SPlayer p = DatabaseManager.getPlayer(pl.getUniqueId().toString());
		PlayersManager.setPlayer(p);
		p.addOrSetData("Donnee 1", "3842");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void quitEvent(PlayerQuitEvent event) {
		Player pl = event.getPlayer();
		PlayersManager.getSPlayer(pl).saveData();
		PlayersManager.removePlayer(pl);
	}

	@EventHandler
	public void invEvent(InventoryClickEvent event){
		if(event.getInventory().getName().equals("§6✚ §3Créer une nouvelle île")){
			event.setCancelled(true);
			Inventories.run_createIslandMenu(event);
		}else if(event.getInventory().getName().equals("§6☰ §3Menu de l'île")){
			event.setCancelled(true);
			Inventories.run_IslandMenu(event);
		}else if(event.getInventory().getName().equals("§6✉ §3Inviter des joueurs")){
			event.setCancelled(true);
			Inventories.run_PlayersInventory_invite(event);
		}
		else if(event.getInventory().getName().equals("§6❔ §3Recommencer une île ?")){
			event.setCancelled(true);
			Inventories.run_ConfirmCreateIsland(event);
		}else if(event.getInventory().getName().equals("§6§3Liste des îles")){
			event.setCancelled(true);
			Inventories.run_IslandList(event);
		}
	}

	@EventHandler
	public void interactEvent(PlayerInteractEvent event){
		Player p = event.getPlayer();
		try
		{
			Island is = Locations.getIslandAt(event.getClickedBlock().getLocation());
			
			if(!is.getMembers().contains(PlayersManager.getSPlayer(p).getID())){
				event.setCancelled(true);
				p.sendTitle("§c⚠§4§lAccès refusé§c⚠", "§eVous ne faites pas partie de cette île", 10,25,10);
				p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 100, 100);
			}
		}catch(Exception ex){
			
			try{
				//Si clic dans le vide
				event.getClickedBlock().getLocation(); //-> Go Catch
				//Si pas d'ile
				event.setCancelled(true);
				p.sendTitle("§c⚠§4§lAccès refusé§c⚠", "§eVous ne faites pas partie de cette île", 10,25,10);
				p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 100, 100);
			}catch(Exception ex2){
				
			}
			
		}
		//
	}


}
