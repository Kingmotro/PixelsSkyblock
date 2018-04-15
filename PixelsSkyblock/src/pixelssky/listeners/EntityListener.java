package pixelssky.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Merchant;

import pixelssky.managers.PlayersManager;
import pixelssky.objects.SPlayer;

public class EntityListener implements Listener{
	
	@EventHandler
	public void playerInteractEntityEvent(PlayerInteractEntityEvent event){
		Player pl =  event.getPlayer();
		SPlayer p = PlayersManager.getSPlayer(pl);
		try{
			String pnjName = event.getRightClicked().getName().substring(2).toLowerCase();
			Merchant m = p.getIsland().getMerchant(1,pnjName);
			if(m != null){
				pl.openMerchant(m, true);
			}
			pl.sendMessage(pnjName);
		}catch(Exception ex){

		}
	}

}
