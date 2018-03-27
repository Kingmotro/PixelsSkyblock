package pixelssky.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pixelssky.objects.Island;

public class OnislandObjective extends Objective{

	public OnislandObjective() {
		super(Objective.ONISLAND);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean check(Player p, Island i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run(Player p, Island i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFailMessage(Player p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
