package pixelssky.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

	public static ItemStack get(String ItemName, Material Mat, Byte id) {
		ItemStack i = new ItemStack(Mat,1,(byte) id);;
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ItemName);
		i.setItemMeta(m);
		return i;

	}
}
