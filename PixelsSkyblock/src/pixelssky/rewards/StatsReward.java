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
			return "§e-▶§2[Récompense] Valeur de statistique §a" + dName + " §2réglée à §a" + dValue;
		}else if(dOperation.equals("add")){
			return "§e-▶§2[Récompense] Valeur de statistique §a" + dName + " §2réglée à §aValeur précédente §2+§a " + dValue;
		}else if(dOperation.equals("rmv")){
			return "§e-▶§2[Récompense] Valeur de statistique §a" + dName + " §2réglée à §aValeur précédente §2-§a " + dValue;
		}
		return "Challenge invalide !";
	}

}
