package pixelssky.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.PlayersManager;
import pixelssky.objects.SPlayer;

public class AfkCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player){
			Player pl =  (Player) arg0;
			SPlayer p = PlayersManager.getSPlayer(pl);
			p.setAfk(true, pl, arg3);
			
		}
		return false;
	}

}
