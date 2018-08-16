package pixelssky.tests;



import java.io.File;

import org.bukkit.Material;
import org.junit.Test;

import pixelssky.merchants.MerchantInventory;

public class MerchantInventoryTest {

	@Test
	public void test() {
		try{
			File dir = new File("plugins/PixelsSky/Shops/");
			dir.mkdirs();
			MerchantInventory m = new MerchantInventory(1, "shoptest");
			m.setItem(Material.ACACIA_DOOR.name(), 0, 100, 10);
			m.save();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}

}
