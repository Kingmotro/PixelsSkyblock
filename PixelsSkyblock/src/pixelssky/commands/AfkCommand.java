package pixelssky.commands;

import org.bukkit.Bukkit;
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
			Player p =  (Player) arg0;
			PlayersManager.getSPlayer(p).setAfk(true);
			Bukkit.broadcastMessage(p.getDisplayName() + "est afk");
			if(arg3.length == 1) {
				Bukkit.broadcastMessage("raison : " + arg3);
			}
			p.sendTitle("VOUS ETES AFK","");
		}
		return false;
	}

}
