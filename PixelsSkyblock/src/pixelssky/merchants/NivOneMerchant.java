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

	@Override
	public Merchant getFood(SPlayer p) {
		Island i = p.getIsland();
		Merchant m = Bukkit.createMerchant("Nourriture - niv1");
		List<MerchantRecipe> l = new ArrayList<MerchantRecipe>();
		
		//Pomme, Blé, graines, Pain
		
		
		MerchantRecipe pomme = new MerchantRecipe(Items.get(Material.SEEDS,(byte) 0, 2), 1000);
		pomme.addIngredient(new ItemStack(Material.EMERALD, 8));
		l.add(pomme);
		
		MerchantRecipe ble = new MerchantRecipe(Items.get(Material.WHEAT,(byte) 0,1), 1000);
		ble.addIngredient(new ItemStack(Material.EMERALD, 16));
		l.add(ble);
		
		MerchantRecipe pain = new MerchantRecipe(Items.get(Material.BREAD,(byte) 0,1), 1000);
		pain.addIngredient(new ItemStack(Material.EMERALD, 64));
		l.add(pain);
		
		//Vente
		
		MerchantRecipe pomme2 = new MerchantRecipe(Items.get(Material.EMERALD,(byte) 0, 1), 1000);
		pomme2.addIngredient(new ItemStack(Material.SEEDS, 16));
		l.add(pomme2);
		
		MerchantRecipe ble2 = new MerchantRecipe(Items.get(Material.EMERALD,(byte) 0 ,1), 1000);
		ble2.addIngredient(new ItemStack(Material.WHEAT, 2));
		l.add(ble2);
		
		MerchantRecipe pain2 = new MerchantRecipe(Items.get(Material.EMERALD,(byte) 0, 8), 1000);
		pain2.addIngredient(new ItemStack(Material.BREAD, 6));
		l.add(pain2);
		
		m.setRecipes(l);
		
		return m;
	}

	@Override
	public Merchant getAnimal(SPlayer p) {
		//Oeuf de poule ... c'est tout
		Island i = p.getIsland();
		Merchant m = Bukkit.createMerchant("Nourriture - niv1");
		List<MerchantRecipe> l = new ArrayList<MerchantRecipe>();
		
		
		MerchantRecipe poule = new MerchantRecipe(Items.get(Material.EGG,(byte) 0, 1), 1000);
		poule.addIngredient(new ItemStack(Material.EMERALD_BLOCK, 8));
		l.add(poule);
		
		//Vente
		
		MerchantRecipe poule2 = new MerchantRecipe(Items.get(Material.EMERALD,(byte) 0, 16), 1000);
		poule2.addIngredient(new ItemStack(Material.EGG, 5));
		l.add(poule2);
		
		m.setRecipes(l);
		
		return m;
	}

	@Override
	public Merchant getWood(SPlayer p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getOres(SPlayer p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getLoots(SPlayer p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getNether(SPlayer p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getEnder(SPlayer p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getStones(SPlayer p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getConcrete(SPlayer p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getClay(SPlayer p) {
		// TODO Auto-generated method stub
		return null;
	}

}
