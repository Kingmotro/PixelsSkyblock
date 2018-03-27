package pixelssky.objects.objectives;

import org.bukkit.entity.Player;

import pixelssky.objects.Island;

public class StatsObjective extends Objective {

	public StatsObjective() {
		super(Objective.STATS);
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
