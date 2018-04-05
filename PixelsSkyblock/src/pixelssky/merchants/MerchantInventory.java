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

}
