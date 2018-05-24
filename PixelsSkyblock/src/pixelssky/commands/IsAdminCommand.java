package pixelssky.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.IslandsManager;
import pixelssky.objects.Island;

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
					p.sendMessage("== Liste des îles ==");
					for(Island i: IslandsManager.islands){
						try{
							p.sendMessage(i.toString());
						}catch(Exception ex)
						{
							p.sendMessage("ERROR : " + ex.toString());
						}
					}
				}
			}
		}catch(Exception ex){
			//CONSOLE
		}
		return true;
	}

}
