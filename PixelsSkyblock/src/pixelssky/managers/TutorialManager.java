package pixelssky.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import pixelssky.listeners.EventListener;

public class TutorialManager {
	public static void startTutorial(Player p){
		World w =  Bukkit.getWorld("skyworld");
		//Etape 1 : spawn -> Montrer, dire la commande
		new BukkitRunnable() {
			@Override
			public void run() {
				Location l = w.getSpawnLocation();
				l.setPitch(0f);
				l.setYaw(90f);
				p.teleport(l);
				p.sendTitle("§aBienvenue au skyblock.", "§eVous êtes ici au spawn",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 10L);
		new BukkitRunnable() {
			@Override
			public void run() {
				p.setVelocity(new Vector(-1,0,0));
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 40L);
		new BukkitRunnable() {
			@Override
			public void run() {
				p.setVelocity(new Vector(-1,0,0));
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 45L);
		new BukkitRunnable() {
			@Override
			public void run() {
				p.setVelocity(new Vector(-1,0,0));
				p.sendTitle("§aSautez dans le vide", "§ePour accéder aux fonctions du spawn",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 50L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aPour aller au spawn", "§ela commande est §b/spawn",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 200L);

		
		new BukkitRunnable() {
			@Override
			public void run() {
				Location l = new Location(w,-24,86,-20,-90,-23);
				EventListener.tpPlayers.add(p.getUniqueId().toString());
				p.teleport(l);
				p.sendTitle("§aVoici l'entrée du shop", "§eAllez à l'intérieur",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 300L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aLes PNJ échangent !", "§econtre de l'emeraude.",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 500L);
		
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aMais ils se débloquent !", "§eFaites les challenges pour débloquer !.",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 600L);

		
		//Etape 3 : ferme à mobs -> Montrer, montrer emeraudes
		
		new BukkitRunnable() {
			@Override
			public void run() {
				Location l = new Location(w,-19,76,-25,-142,27.9f);
				EventListener.tpPlayers.add(p.getUniqueId().toString());
				p.teleport(l);
				p.sendTitle("§aVoici un spawner.", "§eIls sont cachés partout !",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 800L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aLes mobs c'est la vie", "§eIls droppent de l'emeraude !",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 1000L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aNous allons débuter :", "§eNous allons procéder à la création de votre île.",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 1200L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aVous pouvez revoir ce tuto !", "§e(en notant les coordonées des spawners =D)",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 1400L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aVous pouvez revoir ce tuto !", "§eVous avez juste à faire §b/is tuto",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 1600L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aVous allez choisir votre île !", "§eAttention, ce choix est important.",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 1800L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aIle Ultra Hard", "§eVous ne pourrez réussir qu'en faisant les challenges.",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 2000L);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				p.sendTitle("§aIle normale", "§eIle basique faisable comme vous le souhaitez !",100,300,100);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 2200L);
		new BukkitRunnable() {
			@Override
			public void run() {
				p.performCommand("is");
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 2400L);
	}
	
	public static void startIslandTutorial(Player p){
		//Montrer le menu
		
		//Montrer les challenges et dire que c'est important.
	}
}
