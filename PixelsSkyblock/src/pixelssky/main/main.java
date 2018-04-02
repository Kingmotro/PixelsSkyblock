package pixelssky.main;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import pixelssky.enchantements.Enchantements;
import pixelssky.managers.BlocksManager;
import pixelssky.managers.ChallengesManager;
import pixelssky.managers.DatabaseManager;
import pixelssky.managers.FileManager;
import pixelssky.managers.IslandsManager;
import pixelssky.objects.Island;
import pixelssky.objects.Right;
import pixelssky.worldgenerator.Generator;

public final class main extends JavaPlugin {

	@Override
	public void onEnable() {
		//Initialisation des droits
		this.getLogger().info("Starting Pixels Skyblock v1.0.2");
		//Initialisation IDENTIFIANTS
		ArrayList<String> l = FileManager.ReadAllText("plugins/PixelsSky/database.config");
		DatabaseManager.BDD_username = l.get(0);
		DatabaseManager.BDD_password = l.get(1);
		
		Right.registerRight("island.place");
		Right.registerRight("island.break");
		Right.registerRight("island.changespawn");
		Right.registerRight("island.changebiome");
		Right.registerRight("island.invite");
		Right.registerRight("island.kick");

		//Ajout des enchants
		try{
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Enchantment.registerEnchantment(Enchantements.VALIDATED_CHALLENGE);
			} catch (IllegalArgumentException e){
				//if this is thrown it means the id is already taken.
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Initialisation des îles
		DatabaseManager.loadIslands();

		//Lecture des valeurs de base
		BlocksManager.init_values();

		//Lecture des challenges
		ChallengesManager.init();
		//KICKALL
		for(Player p : Bukkit.getOnlinePlayers()){
			p.kickPlayer("Chargement du serveur... Veuillez vous reconnecter.");
		}
		//is command
		this.getCommand("is").setExecutor(new IsCommand());

		//events
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		System.out.println("Loaded !");

	}

	@Override
	public void onDisable() {
		//Sauvegarde des îles
		for(Island i: IslandsManager.islands){
			DatabaseManager.updateIsland(i);
		}

	}
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		return new Generator();
	}

}

