package pixelssky.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;

public class ChatListener implements Listener{
	
	@EventHandler
	public void playerChatEvent(AsyncPlayerChatEvent event){
		Player pl = event.getPlayer();
		SPlayer p = PlayersManager.getSPlayer(pl);
		
		event.setMessage(event.getMessage().replaceAll("%", "%%"));
		
		if(event.getMessage().contains("~")){
			event.setMessage("§5§lDUDULLLE EST UN DIEU !");
			pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 100, 100);
		}
		if(event.getMessage().contains("$")){
			event.setMessage("§5§lAAA49 EST UN RICHE !");
			pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 100, 100);
		}
		for(Player player: Bukkit.getOnlinePlayers()){
			if(event.getMessage().contains(player.getDisplayName())){
				player.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 100, 100);
				player.sendTitle("§aRegardez le chat !","§d" + pl.getDisplayName() + " vous appelle !",10,10,100);
			}
		}
		if(p.getIsland() != null){
			event.setFormat("§5[Ile §d§l" + p.getIsland().getName() + "§5] §7"+ pl.getDisplayName() + " §d: §f" + event.getMessage());
		}else{
			event.setFormat("§5[§d§lSans île fixe§5] §7"+ pl.getDisplayName() + " §d: §f" + event.getMessage());
		}
		
	}

	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event){
		Player pl =  event.getEntity();
		SPlayer p = PlayersManager.getSPlayer(pl);
		if(p.getIsland() == null){
			event.setDeathMessage("§7☠ " + pl.getDisplayName() + " §la voulu défier la mort ... sans succès.");
		}else{
			event.setDeathMessage("§7☠ " + pl.getDisplayName() + " §la voulu défier la mort ... sans succès.");
			Island i = p.getIsland();
			if(i.getData("deaths") == null){
				event.setDeathMessage("§7☠ " + pl.getDisplayName() + " §lest mort(e) pour la 1ere fois !");
				i.addOrSetData("deaths", "1");
			}else{
				i.getData("deaths").add(1);
			}
			if(Double.parseDouble(i.getData("deaths").getData().toString()) >= 2){
				pl.sendMessage("§cVous n'avez plus de joker :/");
			}else{
				pl.sendMessage("§aOUF ! Joker ! lvous gardez votre stuff !");
				event.setKeepInventory(true);
				event.setKeepLevel(true);
			}
		}
	}
}
