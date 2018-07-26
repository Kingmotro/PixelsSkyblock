package pixelssky.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.PlayersManager;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Inventories;

public class ChallengeCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player){
			Player p = (Player) arg0;
			SPlayer pl = PlayersManager.getSPlayer(p);
			if(pl.getIsland() != null){
				p.openInventory(Inventories.getChallengesMainInventory(pl.getIsland()));
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 1000);
			}
		}else{
			arg0.sendMessage("La console ne peut pas effectuer cette commande.");
		}
		return true;
	}

}
