package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpeedCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player){
			Player p = (Player) arg0;
			if(arg3.length == 2){
				p = Bukkit.getPlayer(arg3[1]);
			}
			if(arg3.length >= 1){
				p.setVelocity(new Vector(Float.parseFloat(arg3[0]),Float.parseFloat(arg3[0]),Float.parseFloat(arg3[0])));
				p.sendMessage("§eVitesse changée sur : §a" + arg3[0]);
			}
		}
		return true;
	}
}
