package pixelssky.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.IslandsManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;

public class IsAdminCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		try{
			//JOUEUR
			Player p = (Player) arg0;
			if(arg3.length == 0){
				p.sendMessage("Aide admin : ");
				p.sendMessage("/pxs list : Liste des îles");
				p.sendMessage("/pxs goto [ID] : Se téléporter sur l'ile avec un ID");
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
				}else if(arg3[0].equals("goto") && arg3.length > 1 ){
					try{
						int ID = Integer.parseInt(arg3[1]);
						p.teleport(IslandsManager.getIsland(ID).getSpawn());
					}catch(Exception ex){
						Island is = null;
						for(Island i : IslandsManager.islands){
							if(i.toString().contains(arg3[1]))
								is = i;
						}
						p.teleport(is.getSpawn());
					}
					
					
				}else if(arg3[0].equals("protection")){
					SPlayer sp = PlayersManager.getSPlayer(p);
					sp.setProtectionOverride(!sp.getProtectionOverride());
					p.sendMessage("§5Mode admin : §a" + sp.getProtectionOverride());
				}else if(arg3[0].equals("save")){
					p.sendMessage("Sauvegarde des îles ...");
					DatabaseManager.openConnection();
					for(Island i: IslandsManager.islands){
						DatabaseManager.updateIsland(i);
					}
					DatabaseManager.closeConnection();
				}
			}
		}catch(Exception ex){
			//CONSOLE
			
		}
		return true;
	}

}
