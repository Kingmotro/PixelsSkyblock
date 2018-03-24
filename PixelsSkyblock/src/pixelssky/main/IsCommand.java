package pixelssky.main;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pixelssky.managers.IslandsManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Inventories;

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
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 1000);
				}else
				{
					//Ouvrir inventaire de base
					pl.openInventory(Inventories.getIslandMenu(p));
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_GUITAR, 100, 100);

				}	

			}else if(arg3[0].equals("create")){
				if(p.getIsland() != null){
					pl.openInventory(Inventories.getConfirmCreateIsland());
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 1000);
				}else{
					pl.openInventory(Inventories.getCreateIslandMenu(p));
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 1000);
				}
				
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
			else if(arg3[0].equals("invite"))
			{
				pl.openInventory(Inventories.getPlayersInventory_invite(p));
			}
			else if(arg3[0].equals("accept"))
			{	
				pl.sendTitle("§aVous venez d'accepter l'invation :p", "§cC'est parti!!!", 10, 20, 10);
				p.setIsland(IslandsManager.getIsland(p.getLastIsInvite()));
				p.getIsland().getMembers().add(p.getID());
				pl.teleport(p.getIsland().getSpawn());
				pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 100, 100);
			}
			
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		return true;
	}

}
