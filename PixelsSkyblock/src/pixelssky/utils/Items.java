package pixelssky.utils;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
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
	
	public static ItemStack getHelpBook(){
		
        ItemStack is = get(Material.WRITTEN_BOOK);
        BookMeta b = (BookMeta) is.getItemMeta();
        b.setAuthor("§aPixels Crafters Network");
        b.addPage("§6§lBienvenue sur le skyblock !\n§3Vous trouverez ici un mini guide.");
        b.addPage("§cAttention, ce skyblock n'est pas simple !\n\n§lVous devrez suivre ce guide, Il vous explique comment BIEN commencer.");
        b.addPage("§cPosez la terre de votre coffre de démarrage et plantez y un arbre.\n§4N'utilisez pas la cobble"
        		+ "\n\n§cFaites le pousser avec les os.\n\n§aEnsuite agrandissez (mais avec le bois!)");
        b.addPage("§aPour gagner d'autres blocks, faites §5§l/c §aet completez les challenges !\n§7Vous gagnerez des niveaux bonus, des joker de vie, des items SF, ...");
        b.setTitle("§d§lIMPORTANT A LIRE");
        is.setItemMeta(b);
        return is;
	}
}
