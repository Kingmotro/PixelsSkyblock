package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player){
			Player p = (Player) arg0;
			if(arg3.length == 1){
				p = Bukkit.getPlayer(arg3[1]);
			}
			if(p.getAllowFlight()){
				p.setAllowFlight(false);
				p.sendMessage("§eVous ne pouvez plus voler ... comme le §adodo !");
				p.sendMessage("§7https://fr.wikipedia.org/wiki/Dodo_(oiseau)");
			}else{
				p.setAllowFlight(true);
				p.sendMessage("§eVous pouvez voler comme §asuperman !");
				p.sendMessage("§7https://fr.wikipedia.org/wiki/Superman");
			}
			
			
		}
		return true;
	}

}
