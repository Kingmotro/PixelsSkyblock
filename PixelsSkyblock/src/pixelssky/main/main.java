package pixelssky.main;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import pixelssky.commands.AfkCommand;
import pixelssky.commands.ChallengeCommand;
import pixelssky.commands.FlyCommand;
import pixelssky.commands.IsAdminCommand;
import pixelssky.commands.IsCommand;
import pixelssky.commands.SpawnCommand;
import pixelssky.commands.SpeedCommand;
import pixelssky.commands.TpaCommand;
import pixelssky.commands.TpyesCommand;
import pixelssky.commands.VanishCommand;
import pixelssky.commands.gmCommand;
import pixelssky.enchantements.Enchantements;
import pixelssky.listeners.ChatListener;
import pixelssky.listeners.EntityListener;
import pixelssky.listeners.EventListener;
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

		EventListener.drng.addNumber(9, 2d/256d);  //2
		EventListener.drng.addNumber(8, 1d/256d);  //3
		EventListener.drng.addNumber(7, 10d/256d); //13
		EventListener.drng.addNumber(6, 15d/256d); //28
		EventListener.drng.addNumber(5, 5d/256d);  //32
		EventListener.drng.addNumber(4, 4d/256d);  //36
		EventListener.drng.addNumber(3, 15d/256d); //51
		EventListener.drng.addNumber(2, 100d/256d);//151
		EventListener.drng.addNumber(1, 104d/256d);//256
		
		EntityListener.drng.addNumber(10, 1d/256d);	//1
		EntityListener.drng.addNumber(9, 7d/256d);	//8
		EntityListener.drng.addNumber(8, 12d/256d);	//20
		EntityListener.drng.addNumber(7, 10d/256d);	//30
		EntityListener.drng.addNumber(6, 17d/256d);	//47
		EntityListener.drng.addNumber(5, 13d/256d);	//60
		EntityListener.drng.addNumber(4, 25d/256d);	//85
		EntityListener.drng.addNumber(3, 45d/256d);	//130
		EntityListener.drng.addNumber(2, 63d/256d);	//193
		EntityListener.drng.addNumber(1, 63d/256d);	//256
		
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
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Initialisation des iles
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
		
		//gm command
		this.getCommand("gm").setExecutor(new gmCommand());
		
		//speed command
		this.getCommand("speed").setExecutor(new SpeedCommand());
		
		//fly command
		this.getCommand("fly").setExecutor(new FlyCommand());
		
		//afk command
		this.getCommand("afk").setExecutor(new AfkCommand());
		
		//challenge command
		this.getCommand("challenge").setExecutor(new ChallengeCommand());
		
		//spawn command
		this.getCommand("spawn").setExecutor(new SpawnCommand());
		
		//tpa command
		this.getCommand("tpa").setExecutor(new TpaCommand());
		
		//tpyes command
		this.getCommand("tpyes").setExecutor(new TpyesCommand());
		
		//pxs command
		this.getCommand("pxs").setExecutor(new IsAdminCommand());
		
		//vanish command
		this.getCommand("vanish").setExecutor(new VanishCommand());
		
		//events
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		
		System.out.println("Loaded !");

	}

	@Override
	public void onDisable() {
		//Sauvegarde des �les
		for(Island i: IslandsManager.islands){
			DatabaseManager.updateIsland(i);
		}

	}
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		return new Generator();
	}

}

