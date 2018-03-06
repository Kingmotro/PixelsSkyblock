package pixelssky.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.PlayersManager;
import pixelssky.objects.SPlayer;

public class IsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		//Sous commandes : go, h, home, spawn, l, level, lvl, sethome, top, rank, reset
		
		Player pl = (Player) arg0;
		SPlayer p = PlayersManager.getSPlayer(pl);
		if(arg3.length == 0){
			if(p.getIsland() == null)
			{
				//ouvrir pour créer île
			}else
			{
				
			}	
			//Ouvrir inventaire de base
		}else{
			
		}
		
		return true;
	}

}
