package pixelssky.listeners;

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
import pixelssky.merchants.MerchantCategory;
import pixelssky.objects.Data;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;
import pixelssky.utils.DistributedRandomNumberGenerator;
import pixelssky.utils.Inventories;
import pixelssky.utils.Items;

public class EntityListener implements Listener{
	public static DistributedRandomNumberGenerator drng = new DistributedRandomNumberGenerator();
	
	@EventHandler
	public void playerInteractEntityEvent(PlayerInteractEntityEvent event){
		Player pl =  event.getPlayer();
		SPlayer p = PlayersManager.getSPlayer(pl);
		try{
			String pnjName = event.getRightClicked().getName().substring(2);
			pl.openInventory(MerchantCategory.get(pnjName).getMainMenu(p.getIsland()));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event){
		Location loc = event.getEntity().getLocation();
		
		ItemStack reward = null;
		
		switch(drng.getDistributedRandomNumber()) {
			case 1: reward = Items.get(Material.AIR, (byte) 0, 1) ;
					break;
			case 2: reward = Items.get(Material.AIR, (byte) 0, 2) ;
					break;
			case 3: reward = new ItemStack(Material.EMERALD, 1);
					break;
			case 4: reward = new ItemStack(Material.STICK, 1);
					break;
			case 5: reward = new ItemStack(Material.EMERALD, 2);
					break;
			case 6: reward = new ItemStack(Material.WOOD_BUTTON, 1);
					break;
			case 7: reward = new ItemStack(Material.WOOD_SPADE,1);
					break;
			case 8: reward = new ItemStack(Material.COBBLESTONE,5);
					break;
			case 9: reward = new ItemStack(Material.EMERALD_BLOCK,1);
					break;
			case 10: reward = new ItemStack(Material.DIAMOND,1);
					break;
			default: 
					break;
		}
		
		loc.getWorld().dropItemNaturally(loc, reward);
		if(event.getEntity().getKiller() instanceof Player){
			SPlayer sp = PlayersManager.getSPlayer(event.getEntity().getKiller());
			Island i = sp.getIsland();
			if(i != null){
				Data d = i.getData(Data.KILLED_MOBS + ":" + event.getEntity().getName());
				if(d != null){
					d.add(1);
				}else{
					i.addOrSetData(Data.KILLED_MOBS + ":" + event.getEntity().getName(), 1);
				}
			}
		}
		
		loc.getWorld().dropItemNaturally(loc, reward);
		
	}
	

	
}
