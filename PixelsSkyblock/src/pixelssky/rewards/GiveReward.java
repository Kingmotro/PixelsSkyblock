package pixelssky.rewards;

import java.util.TreeMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pixelssky.objects.Island;
import pixelssky.utils.Items;

public class GiveReward extends Reward{
	Material material;
	int quantity;
	TreeMap<String, Integer> enchants = new TreeMap<String, Integer>();
	/**
	 * Create REWARD
	 * @param a Material to give
	 * @param c Int quantity to give
	 */
	public GiveReward(Material a, int c){
		this.material = a;
		this.quantity = c;
	}
	
	/**
	 * 
	 * @param a Material to give
	 * @param c int Quantity to give
	 * @param e	TreeMap : list of enchants.
	 */
	public GiveReward(Material a, int c, TreeMap<String, Integer> e){
		this.material = a;
		this.quantity = c;
		this.enchants = e;
	}
	
	@Override
	public void run(Player p, Island i) {
		ItemStack it = Items.get(material);
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
		ItemStack it = Items.get(material);
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
		return "§e-▶§2[Récompense] §a" + material.toString() + " §a" + enchantments + " §2quantité : §a"  + quantity;
	}

}
