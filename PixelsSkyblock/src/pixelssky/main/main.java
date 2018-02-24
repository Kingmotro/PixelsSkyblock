package pixelssky.main;

import org.bukkit.plugin.java.JavaPlugin;

import pixelssky.objects.Right;

public final class main extends JavaPlugin {
	@Override
	public void onEnable() {
		//Initialisation des droits
		Right.registerRight("island.place");
		Right.registerRight("island.break");
		Right.registerRight("island.changespawn");
		Right.registerRight("island.changebiome");
		Right.registerRight("island.invite");
		Right.registerRight("island.kick");
		//
	}

	@Override
	public void onDisable() {
		
	}
}

