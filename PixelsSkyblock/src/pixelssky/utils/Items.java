package pixelssky.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Items {

	//TODO : Basic methods to get : Items with meta, with special text, ...
	public static ItemStack get(String ItemName, Material Mat, Byte id, ArrayList<String> lore) {
		ItemStack i = new ItemStack(Mat,1,(byte) id);;
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ItemName);
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}
	public static ItemStack get(Material Mat, Byte id, ArrayList<String> lore) {
		ItemStack i = new ItemStack(Mat,1,(byte) id);;
		ItemMeta m = i.getItemMeta();
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack get(String ItemName, Material Mat, Byte id) {
		ItemStack i = new ItemStack(Mat,1,(byte) id);;
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ItemName);
		i.setItemMeta(m);
		return i;

	}
	public static ItemStack get(Material Mat, Byte id) {
		ItemStack i = new ItemStack(Mat,1,(byte) id);;
		return i;

	}

	public static ItemStack get(Material Mat, Byte id, int qte) {
		ItemStack i = new ItemStack(Mat,1,(byte) id);;
		i.setAmount(qte);
		return i;

	}
	public static ItemStack getHead(Player player) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skull = (SkullMeta) item.getItemMeta();
		skull.setDisplayName(player.getName());
		skull.setOwner(player.getName());
		item.setItemMeta(skull);
		return item;
	}

	public static ItemStack getHead(String pName, String itemName) {

		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skull = (SkullMeta) item.getItemMeta();
		skull.setDisplayName(itemName);
		skull.setOwner(pName);
		item.setItemMeta(skull);
		return item;
	}
	public static ItemStack getEmeraldPrice(int price){
		if(price <= 0)
			return get(Material.EMERALD,(byte) 0, 1);
		else if(price < 64)
			return get(Material.EMERALD,(byte) 0, price);
		else if(price <= 9 * 64)
			return get(Material.EMERALD_BLOCK,(byte) 0, price / 9);
		else if(price <= 18 * 64)
			return get(Material.DIAMOND_BLOCK,(byte) 0, price / 18);
		return get(Material.DIAMOND_BLOCK,(byte) 0, 64);
	}
}
