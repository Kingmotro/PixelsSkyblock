package pixelssky.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.IslandsManager;
import pixelssky.managers.PlayersManager;
import pixelssky.managers.TutorialManager;

import pixelssky.objects.Data;
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
					//ouvrir pour créer île
					pl.openInventory(Inventories.getCreateIslandMenu(p));
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 1000);
				}else
				{
					//Ouvrir inventaire de base
					pl.openInventory(Inventories.getIslandMenu(p));
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 100, 100);

				}	
			}else if(arg3[0].equals("help")){
				pl.sendMessage("§a§l=== §eAide du skyblock §a§l===");
				pl.sendMessage("§b/is §e: menu de l'île.");
				pl.sendMessage("§b/is create §e: créer une île.");
				pl.sendMessage("§b/is c §e: challenges.");
				pl.sendMessage("§b/is h §e: home de l'île");
				pl.sendMessage("§b/is sethome §e: régler le spawn de l'île.");
				pl.sendMessage("§b/is top §e: classement des îles");
				pl.sendMessage("§b/is name [nom] §e: changer le nom de l'île.");
			}else if(arg3[0].equals("create") || arg3[0].equals("restart")){
				if(p.getIsland() != null && p.getIsland().getMembers().size() == 1 && p.getIsland().isAdmin(p.getID())){
					pl.openInventory(Inventories.getConfirmCreateIsland());
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 1000);
				}else if((p.getIsland() != null && p.getIsland().getMembers().size() != 1) || p.getIsland().isAdmin(p.getID())){
					pl.sendTitle("§4Opération refusée !", "Vous devez être admin et seul membre de l'île actuelle.",10,10,100);
					pl.sendMessage("§c-> Quittez l'île pour pouvoir en créer une !");
				}else if(p.getIsland() == null){
					pl.openInventory(Inventories.getCreateIslandMenu(p));
					pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 1000);
				}

			}else if(arg3[0].equals("c")){
				pl.openInventory(Inventories.getChallengesMainInventory(p.getIsland()));
				pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 100, 1000);
			}else if(arg3[0].equals("h")){
				pl.sendTitle("§aBienvenue sur votre île :)", "§cNe tombez pas !", 10,20,10);
				pl.teleport(p.getIsland().getSpawn());
			}else if(arg3[0].equals("sethome"))
			{
				pl.sendTitle("§aMise à jour effectuée :)", "§cVotre home a changé !", 10,20,10);
				p.getIsland().setHome(pl.getLocation());
			}else if(arg3[0].equals("top"))
			{
				ArrayList<String> top = Classement.getTop();
				if(arg3.length == 1){
					try{
						pl.sendMessage("§e===§5 Classement des îles §71->10 §e===");
						for(int n = 0; n < 10; n++){
							pl.sendMessage(top.get(n));
						}
					}catch(Exception ex)
					{
						//Il y a moins de 10 iles.
					}
				}else{
					try{
						int n = Integer.parseInt(arg3[1]) - 1;
						pl.sendMessage("§e===§5 Classement des îles §7" + (n*10 + 1) + "->"+ ((n+1)*10) +"§e===");
						
						for(int nb = (n*10); nb < ((n+1)*10); nb++){
							try{
								pl.sendMessage(top.get(nb));
							}catch(Exception ex){
								
							}		
						}
					}catch(Exception ex){
						pl.sendMessage("§cCommande invalide : /is top [Numéro de page]");
					}
				}
				
			}
			else if(arg3[0].equals("leave"))
			{
				if(!p.getIsland().getData("Creator").getData().toString().equals(pl.getDisplayName())){
					p.getIsland().getMembers().remove(p.getIsland().getMembers().indexOf(p.getID()));
					pl.getInventory().clear();
					pl.getEnderChest().clear();
					p.setIsland(null);
					pl.sendMessage("§1-> §aVous avez quitté l'île");
				}else if(p.getIsland().getMembers().size() <= 1){
					DatabaseManager.deleteIsland(p.getIsland());
					pl.getInventory().clear();
					pl.getEnderChest().clear();
					p.setIsland(null);
					pl.sendMessage("§1-> §aVous avez quitté l'île... Elle est desormais en ruines !");
					//EventListener.tpPlayers.add(pl.getUniqueId().toString());
					pl.teleport(Bukkit.getServer().getWorld("skyworld").getSpawnLocation());
				}else
				{
					pl.sendMessage("§1-> §cLe propriétaire ne peut pas abandonner le navire !");
				}
				
				
			}else if(arg3[0].equalsIgnoreCase("stats"))
			{
				for(Data d : p.getIsland().getData()){
					pl.sendMessage(d.getDataName() + " : " + d.getData().toString());
				}
			}else if(arg3[0].equalsIgnoreCase("name"))
			{
				p.getIsland().addOrSetData("island name", arg3[1]);
				pl.sendMessage("§a✔ §e Nom de l'île changé sur : §a" + arg3[1]);
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
				try{
					if(p.getLastIsInvite() != -1) {
						if(!p.getIsland().getData("Creator").getData().toString().equals(pl.getDisplayName()) || p.getIsland().getMembers().size() != 1){
							pl.sendTitle("§aVous venez d'accepter l'invation :p", "§cC'est parti!!!", 10, 20, 10);
							p.setIsland(IslandsManager.getIsland(p.getLastIsInvite()));
							p.getIsland().getMembers().add(p.getID());
							pl.teleport(p.getIsland().getSpawn());
							pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
						}else{
							pl.sendMessage("§1-> §cLe propriétaire ne peut pas abandonner le navire ! Sauf s'il est vide !");
						}
					} else {
						pl.sendMessage("§1-> §cVous n'avez recu aucune invitation !");
					}
				}catch(Exception ex){
					pl.sendTitle("§aVous venez d'accepter l'invation :p", "§cC'est parti!!!", 10, 20, 10);
					p.setIsland(IslandsManager.getIsland(p.getLastIsInvite()));
					p.getIsland().getMembers().add(p.getID());
					pl.teleport(p.getIsland().getSpawn());
					pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
				}
				
				
			}else if(arg3[0].equals("s"))
			{
				Bukkit.dispatchCommand(pl, "spawn");
			}else if(arg3[0].equals("tuto"))
			{
				TutorialManager.startTutorial(pl);
			}

		}catch(Exception ex){
			System.out.println(ex.toString());
		}

		return true;
	}

}
