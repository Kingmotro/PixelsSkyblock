package pixelssky.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Inventories;
import pixelssky.utils.WEManager;

public class IsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		//Sous commandes : go, h, home, spawn, l, level, lvl, sethome, top, rank, reset
		try{


			Player pl = (Player) arg0;
			SPlayer p = PlayersManager.getSPlayer(pl);
			
			if(arg3.length == 0){
				if(p.getIsland() == null)
				{
					//ouvrir pour créer île
					pl.openInventory(Inventories.getCreateIslandMenu(p));
				}else
				{
					pl.sendMessage("" + p.getIsland().getID());
					//Ouvrir inventaire de base
				}	

			}else if(arg3[0].equals("create")){
				if(p.getIsland() != null){
					DatabaseManager.deleteIsland(p.getIsland());
				}
				DatabaseManager.createIsland(p);

			}else if(arg3[0].equals("h")){

				pl.sendTitle("§aBienvenue sur votre île :)", "§cNe tombez pas !", 10,20,10);

				pl.teleport(p.getIsland().getSpawn());

			}else if(arg3[0].equals("sethome"))
			{
				pl.sendTitle("§aMise à jour effectuée :)", "§cVotre home a changé !", 10,20,10);
				p.getIsland().setHome(pl.getLocation());
			}else if(arg3[0].equals("level"))
			{
				p.getIsland().calculateLevel(pl);
			}
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		return true;
	}

}
