package pixelssky.rewards;

import java.util.TreeMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pixelssky.objects.Island;
import pixelssky.utils.Items;

public class GiveReward extends Reward{
	int item_ID;
	int item_SubID;
	int quantity;
	TreeMap<String, Integer> enchants = new TreeMap<String, Integer>();
	
	public GiveReward(int a, int b, int c){
		this.item_ID = a;
		this.item_SubID = b;
		this.quantity = c;
	}
	
	public GiveReward(int a, int b, int c, TreeMap<String, Integer> e){
		this.item_ID = a;
		this.item_SubID = b;
		this.quantity = c;
		this.enchants = e;
	}
	
	@Override
	public void run(Player p, Island i) {
		ItemStack it = Items.get(Material.getMaterial(item_ID), (byte) item_SubID);
		it.setAmount(quantity);
		for(String e:enchants.keySet()){
			try{
				it.addEnchantment(Enchantment.getByName(e), enchants.get(e));
			}catch(Exception ex){
				
			}
		}
		try{
			p.getInventory().addItem(it);
		}catch(Exception ex){
			p.getWorld().dropItem(p.getLocation(), it);
		}
	}

	@Override
	public String getDescription() {
		ItemStack it = Items.get(Material.getMaterial(item_ID), (byte) item_SubID);
		it.setAmount(quantity);
		String enchantments = "";
		for(String e:enchants.keySet()){
			try{
				it.addEnchantment(Enchantment.getByName(e), enchants.get(e));
				enchantments += e + " ";
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return "§e-▶§2[Récompense] §a" + it.getI18NDisplayName() + " §a" + enchantments + " §2quantité : §a"  + quantity;
	}

}
