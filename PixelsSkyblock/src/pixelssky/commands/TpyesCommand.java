package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.PlayersManager;
import pixelssky.objects.SPlayer;

public class TpyesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			
			Player p = (Player) arg0;
			SPlayer sp =  PlayersManager.getSPlayer(p);
			
			if(sp.getLastTpaRequest() != null) {
				Player p2 = Bukkit.getPlayer(sp.getLastTpaRequest().getUUID());
				
				p2.sendMessage("demande acceptée");
				
				p2.teleport(p.getLocation());
				
				sp.setTpaRequest(null);
			}

			return true;
		} else {
			return false;
		}
	}

}
