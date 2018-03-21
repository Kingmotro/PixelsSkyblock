package pixelssky.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

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
		return null;
	}
	
}
