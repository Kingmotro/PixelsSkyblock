package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player){
			Player p = (Player) arg0;
			Player s = (Player) arg0;
			if(arg3.length == 2){
				p = Bukkit.getPlayer(arg3[1]);
			}
			
			if(arg3.length >= 1){
				p.setFlySpeed(Float.parseFloat(arg3[0])/10f);
				p.setWalkSpeed(Float.parseFloat(arg3[0])/10f);
				p.sendMessage("§eVitesse changée sur : §a" + arg3[0]);
				if(p != s){
					s.sendMessage("§eVitesse de §a: " + p.getDisplayName() + "§a changé sur : §a" + arg3[0] );
				}
			}
		}
		return true;
	}
}
