package pixelssky.merchants;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import pixelssky.managers.FileManager;
import pixelssky.objects.Island;
import pixelssky.utils.Items;

public class MerchantInventory {
	/*
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

/*
 * NEW SYSTEM
 */
	
	private int lvl;
	private String name;
	private ArrayList<ItemObject> items = new ArrayList<ItemObject>(); //Objet, prix
	
	
	public MerchantInventory(int lvl, String name){
		//Format fichier
		//ID:SUBID//PRICE//QTE
		this.name = name;
		this.lvl = lvl;
		
		try{
			ArrayList<String> lines = FileManager.ReadAllText("plugins/PixelsSky/Shops/" + name + lvl);
			for(String l : lines){
				if(l.split("//").length == 3){
					String itemID = l.split("//")[0].split(":")[0];
					int subID = Integer.parseInt(l.split("//")[0].split(":")[1]);
					int itemPrice = Integer.parseInt(l.split("//")[1]);
					int qte = Integer.parseInt(l.split("//")[2]);
					setItem(itemID, subID, qte, itemPrice);
				}
			}
		}catch(Exception ex){
			System.out.println("Le shop n'existe pas ... /pxs shop save est important.");
		}
	}
	
	
	/*
	 * Add an item with a price
	 */
	public void addItem(String itemID, int subID, int itemPrice, int qte){
		items.add(new ItemObject(new ItemStack(Material.getMaterial(itemID), qte, (short) subID), itemPrice));
	}

	/*
	 * Set item price by name
	 * Set price to 0 to remove
	 */
	public void setItem(String itemID, int subID, int qte, int price){
		ItemObject it = find(itemID, subID);
		if(it != null){
			it.obj.setAmount(qte);
			it.price = price;
		}else{
			items.add(new ItemObject(new ItemStack(Material.getMaterial(itemID), qte, (short) subID), price));
		}
	}
	
	public ItemObject find(String itemID, int subID){
		for(ItemObject it : items){
			if(it.obj.getType().name().equals(itemID) && it.obj.getData().getData() == (byte) subID){
				return it;
			}
		}
		return null;
	}
	
	/*
	 * Saves the PNJ to fileSystem
	 */
	public void save(){
		ArrayList<String> lines = new ArrayList<String>();
		for(ItemObject it : items){
			lines.add(it.obj.getType().toString() + ":" + it.obj.getData().getData() + "//" + it.price + "//"+ it.obj.getAmount());
		}
		FileManager.SaveFile("plugins/PixelsSky/Shops/" + name + lvl, lines);
	}
	
	/*
	 * Get the shop
	 */
	public List<MerchantRecipe> getMerchant(Island is, Player p){
		ArrayList<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
		if(is == null){
			return null;
		}else if(isUnlocked(is)){
			
			for(ItemObject it: items){
				MerchantRecipe m = new MerchantRecipe(it.obj,0, Integer.MAX_VALUE, true);
				m.addIngredient(Items.getEmeraldPrice(it.price));
				
				MerchantRecipe m2 = new MerchantRecipe(Items.getEmeraldPrice((int) Math.max(it.price * 0.2,1)),0, Integer.MAX_VALUE, true);
				m2.addIngredient(it.obj);
				recipes.add(m);
				recipes.add(m2);
			}
		}else{
			p.sendMessage("§cWhups ! Shop bloqué ! Votre île doit être niveau : §a" + lvl);
		}
		return recipes;
		
	}
	public List<MerchantRecipe> getMerchant(Island is){
		ArrayList<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>();
		if(is == null){
			return null;
		}else if(isUnlocked(is)){
			
			for(ItemObject it: items){
				MerchantRecipe m = new MerchantRecipe(it.obj,0, Integer.MAX_VALUE, true);
				m.addIngredient(Items.getEmeraldPrice(it.price));
				
				MerchantRecipe m2 = new MerchantRecipe(Items.getEmeraldPrice((int) Math.max(it.price * 0.2,1)),0, Integer.MAX_VALUE, true);
				m2.addIngredient(it.obj);
				recipes.add(m);
				recipes.add(m2);
			}
		}
		return recipes;
		
	}

	public int getLevel(){
		return lvl;
	}
	public boolean isUnlocked(Island is){
		return is.getLevel() >= lvl;
	}

}
