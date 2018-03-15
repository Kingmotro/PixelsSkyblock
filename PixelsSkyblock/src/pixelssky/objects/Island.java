package pixelssky.objects;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import pixelssky.utils.Locations;

public class Island {

	public static String locStringFormat = "%world%><%x%><%y%><%z%";

	private int ID = 0;
	private ArrayList<Integer> playersID = new ArrayList<Integer>();
	private ArrayList<Data> data = new ArrayList<Data>();
	private Location isCenter;
	private Location isSpawn;
	private Double isLevel;
	/*
	 * IMPORTANT Placement des îles : Carré de 501 de côté 
	 * <--- 250 blocs ---><CENTRE><--- 250 blocs --->
	 * 
	 */

	public Island(int ID, String playersID, String isCenter, String isSpawn, double d) {
		this.ID = ID;
		this.isCenter = Locations.get(isCenter);
		this.isSpawn = Locations.get(isSpawn);
		this.isLevel = d;
		for (String s : playersID.split(",")) {
			try {
				this.playersID.add(Integer.parseInt(s));
			} catch (Exception ex) {
				System.out.println("INVALID_PLAYER_ID : " + ex.toString());
			}

		}
	}
	
	public ArrayList<Data> getData() {
		return data;		
	}
	
	public Data getData(String dataName) {
		for (Data d : data) {
			if (d.getDataName().equals(dataName)) {
				return d;
			}
		}
		return null;
	}
	public void addOrSetData(String dataName, Object data) {
		Data d = getData(dataName);
		if (d == null) {
			this.data.add(new Data(dataName, data));
		} else {
			d.setData(data);
		}
	}

	public void setHome(Location l) {
		isSpawn = l;
	}

	public String getMembersToString() {
		String out = "";
		try {

			for (Integer i : playersID) {
				out += "" + i + ",";
			}
		} catch (Exception ex) {
			out = "-1";
		}

		return out;
	}

	public ArrayList<Integer> getMembers() {
		return playersID;
	}

	public Location getCenter() {
		return isCenter;
	}

	public Location getSpawn() {
		return isSpawn;
	}

	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}

	public Double getLevel() {
		return isLevel;
	}

	public void calculateLevel(Player p) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
			@Override
			public void run() {
				for (int x = isCenter.getBlockX() - 250; x < isCenter.getBlockX() + 250; x++) {
					for (int z = isCenter.getBlockX() - 250; z < isCenter.getBlockX() + 250; z++) {
						for (int y = 0; y < 256; y++) {
							try {
								p.sendTitle("ID " + x + "," + y + "," + z,"" + Bukkit.getWorld("world").getBlockAt(x, y, z).getTypeId());
								wait(100);
							} catch (Exception e) {
	
							}
						}
					}
				}
			}
		});
	}

	public String loc2str(Location loc) {
		String location = locStringFormat.replaceAll("%world%", loc.getWorld().getName())
				.replaceAll("%x%", String.valueOf(loc.getX())).replaceAll("%y%", String.valueOf(loc.getY()))
				.replaceAll("%z%", String.valueOf(loc.getZ()));
		return location;

	}

	public Location str2loc(String loc) {
		String[] parts = loc.split("><");
		World world = Bukkit.getWorld(parts[0]);
		double xPos = Double.valueOf(parts[1]);
		double yPos = Double.valueOf(parts[2]);
		double zPos = Double.valueOf(parts[3]);

		return new Location(world, xPos, yPos, zPos);
	}
	
}
