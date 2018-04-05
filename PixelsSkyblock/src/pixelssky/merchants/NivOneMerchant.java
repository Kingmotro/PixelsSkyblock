package pixelssky.merchants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Items;

public class NivOneMerchant extends MerchantInventory {

	@Override
	public Merchant getMerchant(SPlayer p) {
		Island i = p.getIsland();
		Merchant m = Bukkit.createMerchant("Niveau 1");
		List<MerchantRecipe> l = new ArrayList<MerchantRecipe>();
		
		MerchantRecipe r = new MerchantRecipe(Items.get(Material.STONE,(byte) 0), 1000);
		r.addIngredient(new ItemStack(Material.EMERALD, 64));
		r.addIngredient(new ItemStack(Material.EMERALD, 64));
		l.add(r);
		
		m.setRecipes(l);
		
		return m;

	}

}
