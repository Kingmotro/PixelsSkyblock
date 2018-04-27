package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gmCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player){
			Player p = (Player) arg0;
			Player s = (Player) arg0;
			if(arg3.length == 2){
				p = Bukkit.getPlayer(arg3[1]);
			}
			if(arg3.length >= 1){
				if(arg3[0].equals("0")){
					p.setGameMode(GameMode.SURVIVAL);
				}
				if(arg3[0].equals("1")){
					p.setGameMode(GameMode.CREATIVE);
				}
				else if(arg3[0].equals("2")){
					p.setGameMode(GameMode.ADVENTURE);
				}
				else if(arg3[0].equals("3")){
					p.setGameMode(GameMode.SPECTATOR);
				}
				p.sendMessage("§eGamemode changé : §a" + p.getGameMode().name());
				if(p != s){
					s.sendMessage("§eGamemode de §a: " + p.getDisplayName() + "§a chang§ : §a" + p.getGameMode().name());
				}
			}
		}
		
		return true;
	}

}
