package pixelssky.objects.objectives;

import org.bukkit.entity.Player;

import pixelssky.objects.Island;

public class StatsObjective extends Objective {
	String stat_ID;
	String value;
	String operation; //less, more, equals
	
	public StatsObjective(String stat_ID, String value, String operation) {
		super(Objective.STATS);
		this.stat_ID = stat_ID;
		this.value = value;
		this.operation = operation;
	}

	@Override
	public boolean check(Player p, Island i) {
		try
		{
			if(stat_ID.equalsIgnoreCase("Level")){
				return i.getLevel() >= Integer.parseInt(value);
			}else if(operation.equals("equals")){
				return i.getData(stat_ID).getData().toString().equals(value);
			}else if(operation.equals("more")){
				return Double.parseDouble(i.getData(stat_ID).getData().toString()) >= Double.parseDouble(value);
			}else if(operation.equals("less")){
				return Double.parseDouble(i.getData(stat_ID).getData().toString()) <= Double.parseDouble(value);
			}
			return false;
		}catch(Exception ex){
			return false;
		}
		
	}

	@Override
	public void run(Player p, Island i) {
		// TODO Rien à faire
		
	}

	@Override
	public String getFailMessage(Player p) {
		if(stat_ID.equalsIgnoreCase("Level")){
			return "§d-▶§5Atteindre le niveau §d" + value;
		}else if(operation.equals("equals")){
			return "§d-▶§5Valeur de statistique §d" + stat_ID + " §5égale à " + value;
		}else if(operation.equals("more")){
			return "§d-▶§5Valeur de statistique §d" + stat_ID + " §5supérieure ou égale à " + value;
		}else if(operation.equals("less")){
			return "§d-▶§5Valeur de statistique §d" + stat_ID + " §5inférieure ou égale à " + value;
		}
		return "Challenge invalide !";
	}

	@Override
	public String getDescription() {
		if(stat_ID.equalsIgnoreCase("Level")){
			return "§d-▶§5Atteindre le niveau §d" + value + " §5est requis !";
		}else if(operation.equals("equals")){
			return "§d-▶§5Valeur de statistique §d" + stat_ID + " §5égale à §d" + value + " §5est requis !";
		}else if(operation.equals("more")){
			return "§d-▶§5Valeur de statistique §d" + stat_ID + " §5supérieure ou égale à §d" + value + " §5est requis !";
		}else if(operation.equals("less")){
			return "§d-▶§5Valeur de statistique §d" + stat_ID + " §5inférieure ou égale à §d" + value + " §5est requis !";
		}
		return "Challenge invalide !";
	}

}
