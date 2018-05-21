package pixelssky.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;

import pixelssky.managers.PlayersManager;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Inventories;

public class EntityListener implements Listener{
	
	@EventHandler
	public void playerInteractEntityEvent(PlayerInteractEntityEvent event){
		Player pl =  event.getPlayer();
		SPlayer p = PlayersManager.getSPlayer(pl);
		try{
			String pnjName = event.getRightClicked().getName().substring(2).toLowerCase();
			Merchant m = p.getIsland().getMerchant(1,pnjName);
			if(m != null){
				pl.playSound(pl.getLocation(), Sound.ENTITY_VILLAGER_TRADING, 100, 1000);
				pl.openInventory(Inventories.getShopMenuInventory(pnjName, p));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		Location loc = event.getEntity().getLocation();
		
		Random randomGenerator = new Random();
		ItemStack reward = null;
		
		switch(randomGenerator.nextInt(100)) {
			//1 emerald : 40%
			case 0: reward = new ItemStack(Material.EMERALD, 1);
					break;
			//2 emerald : 25%
			case 40: reward = new ItemStack(Material.EMERALD, 2);
					break;
			//3 emerald : 10%
			case 65: reward = new ItemStack(Material.EMERALD, 3);
					break;
			//5 patates : 10%
			case 75: reward = new ItemStack(Material.BAKED_POTATO, 5);
					break;
			//1 bâton : 3%
			case 85: reward = new ItemStack(Material.STICK, 1);
					break;
			//1 cobble : 2%
			case 88: reward = new ItemStack(Material.COBBLESTONE,1);
					break;
			//queuedal : 10%
			case 90: 
					break;
			default: 
					break;
		}
		
		loc.getWorld().dropItemNaturally(loc, reward);
	}

}
