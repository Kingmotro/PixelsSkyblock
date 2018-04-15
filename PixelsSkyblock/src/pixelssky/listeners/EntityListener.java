package pixelssky.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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

}
