package pixelssky.rewards;

import org.bukkit.entity.Player;

import pixelssky.objects.Island;

public class StatsReward extends Reward{
	private String dName;
	private String dValue;
	private String dOperation;

	public StatsReward(String dataName, String dataValue, String dataOperation){
		dName = dataName;
		dValue = dataValue;
		dOperation = dataOperation;
	}

	@Override
	public void run(Player p, Island i) {
		try{
			//La donnée existe
			if(dOperation.equalsIgnoreCase("set")){
				i.getData(dName).setData(dValue);
			}else if(dOperation.equalsIgnoreCase("add")){
				i.getData(dName).add(Double.parseDouble(dValue));
			}else if(dOperation.equalsIgnoreCase("rmv")){
				i.getData(dName).rmv(Double.parseDouble(dValue));
			}	
		}catch(Exception ex){
			//La donnée n'existe pas
			i.addOrSetData(dName, 0);
			run(p,i);
		}
		
	}

	@Override
	public String getDescription() {
		if(dOperation.equals("set")){
			if(dName.contains("unlocked"))
				return "§e-▶§2[Récompense] Challenge §a" + dName.split("unlocked")[1] + " §2Débloqué";
			return "§e-▶§2[Récompense] stat §a" + dName + " §2mise à §a" + dValue;
		}else if(dOperation.equals("add")){
			return "§e-▶§2[Récompense] stat §a" + dName + " §2mise à §aValeur préc. §2+§a " + dValue;
		}else if(dOperation.equals("rmv")){
			return "§e-▶§2[Récompense] stat §a" + dName + " §2mise à §aValeur préc. §2-§a " + dValue;
		}
		return "Challenge invalide !";
	}

}
