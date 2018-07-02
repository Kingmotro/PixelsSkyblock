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

			p2.sendMessage("§d" + p1.getName() + " §5veut se téléporter à vous !");
			p2.sendMessage("§a/tpyes pour accepter la demande");

			p1.sendMessage("§7En attente de confirmation ...");

			return true;
		} else {
			return false;
		}
	}

}
