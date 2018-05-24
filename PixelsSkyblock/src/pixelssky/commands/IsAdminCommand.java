package pixelssky.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IsAdminCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		try{
			//JOUEUR
			Player p = (Player) arg0;
			if(arg3.length == 0){
				p.sendMessage("Aide admin : ");
				p.sendMessage("/pxs list : Liste des îles");
			}else
			{
				if(arg3[0].equals("list")){
					
				}
			}
		}catch(Exception ex){
			//CONSOLE
		}
		return true;
	}

}
