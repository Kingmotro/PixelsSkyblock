package pixelssky.merchants;

import org.bukkit.inventory.Merchant;

import pixelssky.objects.SPlayer;

public abstract class MerchantInventory {
	public static Merchant getMerchantFromName(String name, SPlayer p){
		if(name.equals("§6Blocks de base")){
			return new NivOneMerchant().getMerchant(p);
		}
		return null;
	}


	public String name;
	public abstract Merchant getMerchant(SPlayer p);
	
	public abstract Merchant getFood(SPlayer p);
	public abstract Merchant getAnimal(SPlayer p);
	public abstract Merchant getWood(SPlayer p);
	public abstract Merchant getOres(SPlayer p);
	public abstract Merchant getLoots(SPlayer p);
	public abstract Merchant getNether(SPlayer p);
	public abstract Merchant getEnder(SPlayer p);
	public abstract Merchant getStones(SPlayer p);
	public abstract Merchant getConcrete(SPlayer p);
	public abstract Merchant getClay(SPlayer p);
	
	
	
}
