package pixelssky.merchants;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import pixelssky.managers.FileManager;
import pixelssky.objects.Island;
import pixelssky.utils.Items;

public class MerchantInventory {
	public int lvl;
	private TreeMap<String,Merchant> merchants = new TreeMap<String,Merchant>();
	private Island is = null;
	
	public MerchantInventory(int lvl, Island i){
		is = i;
		this.lvl = lvl;
		String folder = "plugins/PixelsSky/Shops/";
		String[] subFolders = {"food", "animal", "wood", "ores", "loots", "nether", "ender", "stones", "concrete", "clay"};
		for(String f: subFolders){
			try
			{
				ArrayList<String> lines = FileManager.ReadAllText(folder + f + "/niv" + lvl);
				Merchant m = Bukkit.createMerchant("§e§l" + f + "§1Niveau §a" + lvl);
				List<MerchantRecipe> l = new ArrayList<MerchantRecipe>();
				
				for(String line: lines){
					String[] elements = line.split(";");
					String[] objet = elements[0].split(":");
					String[] achat = elements[1].split(":");
					String[] vente = elements[2].split(":");
					i.broadcastMessage(i.getPriceOffset() + "");
					MerchantRecipe a = new MerchantRecipe(Items.get(Material.getMaterial(objet[0]),(byte) Integer.parseInt(objet[1]),Integer.parseInt(objet[2])), 1000);
					a.addIngredient(Items.getEmeraldPrice(Integer.parseInt(achat[1]) + i.getPriceOffset())); //new ItemStack(Material.getMaterial(achat[0]), Integer.parseInt(achat[1]))
					try
					{
						a.addIngredient(Items.getEmeraldPrice(Integer.parseInt(achat[3]) + i.getPriceOffset()));
					}catch(Exception ex){
						
					}
					
					MerchantRecipe r = new MerchantRecipe(Items.getEmeraldPrice(Integer.parseInt(vente[1]) - i.getPriceOffset()), 1000);
					r.addIngredient(new ItemStack(Items.get(Material.getMaterial(objet[0]),(byte) Integer.parseInt(objet[1]),Integer.parseInt(objet[2]))));
					
					l.add(a);
					l.add(r);
					
				}
				m.setRecipes(l);
				merchants.put(f, m);
			}catch(Exception ex){
				Bukkit.getLogger().severe("§7Missing shop data :" + f + " niv" + lvl);
			}
		}
	}

	public TreeMap<String,Merchant> getMerchants() {
		return merchants;
	}

	public void setMerchants(TreeMap<String,Merchant> merchants) {
		this.merchants = merchants;
	}
	
	public Merchant getMerchant(String s){
		return merchants.get(s);
	}
	
	public Material getItemMenu(String s){
		Merchant m = getMerchant(s);
		if(m.getRecipe(0) != null && is.isMerchantUnlocked(lvl, s)){
			return m.getRecipe(0).getResult().getType();
		}else{
			return Material.AIR;
		}
		
	}
}
