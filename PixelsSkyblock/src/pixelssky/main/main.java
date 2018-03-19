package pixelssky.main;

import org.bukkit.plugin.java.JavaPlugin;

import pixelssky.managers.BlocksManager;
import pixelssky.managers.DatabaseManager;
import pixelssky.objects.Right;


public final class main extends JavaPlugin {
	@Override
	public void onEnable() {
		//Initialisation des droits
		this.getLogger().info("Starting Pixels Skyblock");
		
		Right.registerRight("island.place");
		Right.registerRight("island.break");
		Right.registerRight("island.changespawn");
		Right.registerRight("island.changebiome");
		Right.registerRight("island.invite");
		Right.registerRight("island.kick");

		//is command
		this.getCommand("is").setExecutor(new IsCommand());
		
		//events
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		
		//Initialisation des îles
		DatabaseManager.loadIslands();
		
		//Lecture des valeurs de base
		BlocksManager.init_values();
	}

	@Override
	public void onDisable() {
		//Sauvegarde des îles
		DatabaseManager.updateIslands();
	}
	
}

