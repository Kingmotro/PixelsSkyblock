package pixelssky.main;

import java.util.TreeMap;

import org.apache.commons.io.output.ThresholdingOutputStream;
import org.bukkit.plugin.java.JavaPlugin;

import pixelssky.managers.PlayersManager;
import pixelssky.objects.Right;
import pixelssky.objects.SPlayer;

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
	}

	@Override
	public void onDisable() {
		
	}
	
}

