package pixelssky.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import pixelssky.managers.IslandsManager;
import pixelssky.objects.Island;

public class Locations {
	//TODO : Basic methods to get : spawn, island edges, ...
	
	public static Location get(String loc){
		String[] s = loc.split(",");
		try{
			return new Location(Bukkit.getWorld(s[0]),Double.parseDouble(s[1]),Double.parseDouble(s[2]),Double.parseDouble(s[3]),Float.parseFloat(s[4]),Float.parseFloat(s[5]));

		}catch(Exception ex){
			System.out.println("LOCATION_GET : " + ex.toString());
		}
		return null;
		
	}
	public static String toString(Location l){
		return l.getWorld().getName() + "," + l.getX() + "," + l.getY() + "," + l.getZ() + "," + l.getPitch() + "," + l.getYaw();
	}
	
	public static Island getIslandAt(Location l){
		int x = l.getBlockX();
		int z = l.getBlockZ();
		// ile 0 : 	0 < x < 510
		//			0 < z < 510
		
		// ile 1 : 	510 < x < 1020
		// 			510 < x < 1020
		
		int ID_x = x/251; 			//max_col = 30 : 		0 	1 	2 	3 	4 	...
									//						31 	32 	33 	34 	35
		int ID_z = z/251;
		int ID = 30 * ID_z + ID_x;
		if(x < 0 || z < 0){
			return null;
		}
		
		return IslandsManager.getIsland(ID);
	}
	public static Location getIsCenterByID(int ID){
		int line = ID / 30;
		int col = ID - line * 30;
		int x = col * 251 + 125;
		int y = 100;
		int z = line * 251 + 125;
		World w = Bukkit.getWorld("world");
		return new Location(w,x,y,z);
	}
	
}
