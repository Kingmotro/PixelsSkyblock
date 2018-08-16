package pixelssky.merchants;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Merchant;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import pixelssky.managers.FileManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Island;
import pixelssky.utils.Items;
import pixelssky.utils.Locations;

public class MerchantCategory {
	public static ArrayList<MerchantCategory> mCategories = new ArrayList<MerchantCategory>();
	
	private ArrayList<MerchantInventory> merchants = new ArrayList<MerchantInventory>();
	public String categName;	
	public Location pnjLocation;
	public EntityType pnjType = EntityType.VILLAGER;
	public NPC npc;
	
	public static void load(){
		ArrayList<String> lines = FileManager.ReadAllText("plugins/PixelsSky/Shops/categories.categ");
		for(String l : lines){
			String[] data = l.split("//");
			MerchantCategory m = new MerchantCategory(data[0]);
			m.pnjLocation = Locations.get(data[1]);
			m.pnjType = EntityType.fromName(data[2]);
			m.npc = CitizensAPI.getNPCRegistry().createNPC(m.pnjType, "§a" + m.categName);
			m.npc.spawn(m.pnjLocation);
			mCategories.add(m);
			File f = new File("plugins/PixelsSky/Shops/");
			for(File f2 : f.listFiles()){
				if(!f2.isDirectory()){
					if(f2.getName().contains(data[0])){
						String fName = f2.getName();
						try{
							m.addMerchant(data[0], Integer.parseInt(fName.replaceAll(data[0], "")));
						}catch(Exception ex){
							ex.printStackTrace();
							System.out.println(Integer.parseInt(fName.replaceAll(data[0], "")));
						}
					}
				}
			}
		}
	}
	
	public static void save(){
		//Sauv de toutes les catégories
		ArrayList<String> lines = new ArrayList<String>();
		for(MerchantCategory mCateg : mCategories){
			lines.add(mCateg.categName + "//" + Locations.toString(mCateg.pnjLocation) + "//" + mCateg.pnjType.getName());
			for(MerchantInventory mInv: mCateg.merchants)
			{
				mInv.save();
			}
		}
		FileManager.SaveFile("plugins/PixelsSky/Shops/categories.categ", lines);
	}
	
	public static MerchantCategory get(String name){
		for(MerchantCategory m : mCategories){
			if(m.categName.equals(name)){
				return m;
			}
		}
		return null;
	}
	public static void add(String categ, Location l, String e){
		MerchantCategory m = new MerchantCategory(categ);
		m.pnjLocation = l;
		m.pnjType = EntityType.fromName(e);
		m.npc = CitizensAPI.getNPCRegistry().createNPC(m.pnjType, "§a" + m.categName);
		m.npc.spawn(m.pnjLocation);
		mCategories.add(m);
	}
	
	
	public MerchantCategory(String categorie){
		categName = categorie;
	}
	public void addMerchant(String name, int level){
		merchants.add(new MerchantInventory(level, name));
	}
	
	public MerchantInventory getMerchant(int level){
		for(MerchantInventory m : merchants){
			if(m.getLevel() == level){
				return m;
			}
		}
		return null;
	}
	
	public Inventory getMainMenu(Island is){
		Inventory inv = Bukkit.createInventory(null, ((merchants.size())/9+1)*9, "§5Shop :" + categName);
		for(MerchantInventory m: merchants){
			if(m.isUnlocked(is)){
				try{
					inv.addItem(Items.get("§eNiveau :" + m.getLevel(), m.getMerchant(is).get(0).getResult().getType()));
				}catch(Exception ex){
					
				}
			}else{
				inv.addItem(Items.get("§c§l▶Niveau bloqué :" + m.getLevel(), Material.BARRIER));
			}
			
		}
		return inv;
	}
	
	public void openMerchant(Island is, Player p, int lvl){
		MerchantInventory m = getMerchant(lvl);
		Merchant merchant = Bukkit.createMerchant(categName + " " + m.getLevel());
		merchant.setRecipes(m.getMerchant(is, p));
		p.openMerchant(merchant, true);
	}
	
	public void runInventory(InventoryClickEvent e){
		String lvl = e.getClickedInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().split(":")[1];
		Player p = (Player) e.getWhoClicked();
		this.openMerchant(PlayersManager.getSPlayer(p).getIsland(), p, Integer.parseInt(lvl));
	}
}
