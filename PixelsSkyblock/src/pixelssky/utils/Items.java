package pixelssky.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Items {

	//TODO : Basic methods to get : Items with meta, with special text, ...
	public static ItemStack get(String ItemName, Material Mat, ArrayList<String> lore) {
		ItemStack i = new ItemStack(Mat,1);;
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ItemName);
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public static ItemStack get(Material Mat, ArrayList<String> lore) {
		ItemStack i = new ItemStack(Mat,1);;
		ItemMeta m = i.getItemMeta();
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack get(String ItemName, Material Mat) {
		ItemStack i = new ItemStack(Mat,1);;
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ItemName);
		i.setItemMeta(m);
		return i;

	}
	public static ItemStack get(Material Mat) {
		ItemStack i = new ItemStack(Mat,1);;
		return i;

	}

	public static ItemStack get(Material Mat, int qte) {
		ItemStack i = new ItemStack(Mat,1);
		i.setAmount(qte);
		return i;

	}
	public static ItemStack getHead(Player player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta skull = (SkullMeta) item.getItemMeta();
		skull.setDisplayName(player.getName());
		skull.setOwningPlayer(player);
		item.setItemMeta(skull);
		return item;
	}
	
	@Deprecated
	public static ItemStack getHead(String pName, String itemName) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta skull = (SkullMeta) item.getItemMeta();
		skull.setDisplayName(itemName);
		skull.setOwner(pName);
		item.setItemMeta(skull);
		return item;
	}
	
	public static ItemStack getEmeraldPrice(int price){
		if(price <= 0)
			return get(Material.EMERALD, 1);
		else if(price < 64)
			return get(Material.EMERALD, price);
		else if(price <= 9 * 64)
			return get(Material.EMERALD_BLOCK, price / 9);
		else if(price <= 18 * 64)
			return get(Material.DIAMOND_BLOCK, price / 18);
		return get(Material.DIAMOND_BLOCK, 64);
	}
}
