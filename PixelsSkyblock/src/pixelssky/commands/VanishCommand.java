package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class VanishCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;
			if (arg3.length == 1) {
				p = Bukkit.getPlayer(arg3[1]);
			}
			if (p.canSee(p)) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					player.hidePlayer(p);
				}
				p.sendMessage("Vanish, et les " + p.getName() + " s'évanouissent");
				p.sendMessage(ChatColor.RED + "!!! si un jour se connecte il te vera !!!");
			} else {
				for (Player player : Bukkit.getOnlinePlayers()) {
					player.showPlayer(p);
				}
				p.sendMessage("JEEEE TEEEEE VOOOOIIIIIS");
				p.sendMessage(ChatColor.UNDERLINE + "...enfait tout le monde peut le faire maintenant...");
			}
			return true;
		}
		return false;
	}

}
