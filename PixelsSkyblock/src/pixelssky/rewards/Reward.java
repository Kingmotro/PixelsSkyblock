package pixelssky.rewards;

import org.bukkit.entity.Player;

import pixelssky.objects.Island;

public abstract class Reward {
	public abstract void run(Player p, Island i);
	public abstract String getDescription();
}
