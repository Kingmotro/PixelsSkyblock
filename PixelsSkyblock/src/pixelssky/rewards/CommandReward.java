package pixelssky.rewards;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("@PLAYER", p.getDisplayName()));
	}

	@Override
	public String getDescription() {
		return "§e-▶§2[Récompense] §a" + desc;
	}
}
