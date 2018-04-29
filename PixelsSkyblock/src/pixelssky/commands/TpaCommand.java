package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.PlayersManager;

public class TpaCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player && (arg3.length == 1)) {

			Player p1 = (Player) arg0;
			Player p2 = Bukkit.getPlayer(arg3[0]);

			PlayersManager.getSPlayer(p2).setTpaRequest(p1.getDisplayName());

			p2.sendMessage("demande de tpa de " + p1.getName());
			p2.sendMessage("/tpyes pour accpeter");

			p1.sendMessage("demande envoyée");

			return true;
		} else {
			return false;
		}
	}

}
