package pixelssky.objects.objectives;

import org.bukkit.entity.Player;

import pixelssky.objects.Island;

public abstract class Objective {
	public static final int ONISLAND = 0;
	public static final int STATS = 0;
	public static final int INVENTORY = 0;
	
	public int type;
	public Objective(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}
	public abstract boolean check(Player p, Island i);
	public abstract void run(Player p, Island i);
	public abstract String getFailMessage(Player p);
	public abstract String getDescription();
}
