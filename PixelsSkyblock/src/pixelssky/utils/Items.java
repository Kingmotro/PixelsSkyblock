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

	public static ItemStack get(String ItemName, Material Mat, Byte id) {
		ItemStack i = new ItemStack(Mat,1,(byte) id);;
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(ItemName);
		i.setItemMeta(m);
		return i;

	}
	public static ItemStack getHead(Player player) {
        int lifePlayer = (int) player.getHealth();
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
}
