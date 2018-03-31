package pixelssky.main;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.ChallengesManager;
import pixelssky.managers.IslandsManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Challenge;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Classement;
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
					//ouvrir pour cr�er �le
					pl.openInventory(Inventories.getCreateIslandMenu(p));
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 1000);
				}else
				{
					//Ouvrir inventaire de base
					pl.openInventory(Inventories.getIslandMenu(p));
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_GUITAR, 100, 100);

				}	

			}else if(arg3[0].equals("create")){
				if(p.getIsland() != null && p.getIsland().getMembers().size() == 1 && p.getIsland().isAdmin(p.getID())){
					pl.openInventory(Inventories.getConfirmCreateIsland());
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 1000);
				}else if((p.getIsland() != null && p.getIsland().getMembers().size() != 1) || p.getIsland().isAdmin(p.getID())){
					pl.sendTitle("�4Op�ration refus�e !", "Vous devez �tre admin et seul membre de l'�le actuelle.",10,10,100);
					pl.sendMessage("�c-> Quittez l'�le pour pouvoir en cr�er une !");
				}else if(p.getIsland() == null){
					pl.openInventory(Inventories.getCreateIslandMenu(p));
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 100, 1000);
				}

			}else if(arg3[0].equals("h")){
				pl.sendTitle("�aBienvenue sur votre �le :)", "�cNe tombez pas !", 10,20,10);
				pl.teleport(p.getIsland().getSpawn());
			}else if(arg3[0].equals("sethome"))
			{
				pl.sendTitle("�aMise � jour effectu�e :)", "�cVotre home a chang� !", 10,20,10);
				p.getIsland().setHome(pl.getLocation());
			}else if(arg3[0].equals("top"))
			{
				pl.sendMessage("Is top : ");
				for(String i : Classement.getTop()){
					pl.sendMessage("ISLAND : " + i);
				}
			}
			else if(arg3[0].equals("leave"))
			{
				if(!p.getIsland().getData("Creator").getData().toString().equals(pl.getDisplayName())){
					p.getIsland().getMembers().remove(p.getIsland().getMembers().indexOf(p.getID()));
					p.setIsland(null);
					pl.sendMessage("�1-> �aVous avez quitt� l'�le");
				}else{
					pl.sendMessage("�1-> �cLe propri�taire ne peut pas abandonner le navire !");
				}
				
				
			}
			else if(arg3[0].equals("level"))
			{
				p.getIsland().calculateLevel(pl);
			}
			else if(arg3[0].equals("invite"))
			{
				pl.openInventory(Inventories.getPlayersInventory_invite(p));
			}
			else if(arg3[0].equals("accept"))
			{	
				pl.sendTitle("�aVous venez d'accepter l'invation :p", "�cC'est parti!!!", 10, 20, 10);
				p.setIsland(IslandsManager.getIsland(p.getLastIsInvite()));
				p.getIsland().getMembers().add(p.getID());
				pl.teleport(p.getIsland().getSpawn());
				pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 100, 100);
			}else if(arg3[0].equals("try"))
			{
				Challenge c = ChallengesManager.challenges.get(0).getSubChallenges().get(0);
				c.complete(pl, p.getIsland());

			}

		}catch(Exception ex){
			System.out.println(ex.toString());
		}

		return true;
	}

}
