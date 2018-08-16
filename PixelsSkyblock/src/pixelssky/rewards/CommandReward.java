package pixelssky.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import pixelssky.objects.Island;


public class CommandReward extends Reward {
	String command;
	String desc;
	
	public CommandReward(String c, String d){
		command = c;
		desc = d;
	}
	
	@Override
	public void run(Player p, Island i) {
		new BukkitRunnable() {
	        public void run() {
	        	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("@PLAYER", p.getDisplayName()));
	        }
	    }.runTask(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"));
		
	}

	@Override
	public String getDescription() {
		return "§e-▶§2[Récompense] §a" + desc;
	}
}
