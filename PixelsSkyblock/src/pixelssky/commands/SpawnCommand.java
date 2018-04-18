package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player){
			Player pl = (Player) arg0;
			pl.sendTitle("§aTéléportation au spawn", "", 10,20,10);
			pl.teleport(Bukkit.getServer().getWorld("skyworld").getSpawnLocation());
			pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 100, 100);
			return true;
		}else{
			return false;
		}
	}
}
