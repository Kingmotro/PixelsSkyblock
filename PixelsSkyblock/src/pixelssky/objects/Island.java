package pixelssky.objects;

import java.util.ArrayList;

import org.bukkit.Location;

import pixelssky.utils.Locations;

public class Island {
	private int ID = 0;
	private ArrayList<Integer> playersID = new ArrayList<Integer>();
	private Location isCenter;
	private Location isSpawn;
	private Double isLevel;
	
	
	public Island(int ID, String playersID, String isCenter, String isSpawn, double d){
		this.ID = ID;
		this.isCenter = Locations.get(isCenter);
		this.isSpawn = Locations.get(isSpawn);
		this.isLevel = d;
		for(String s: playersID.split(",")){
			try
			{
				this.playersID.add(Integer.parseInt(s));
			}catch(Exception ex){
				System.out.println("INVALID_PLAYER_ID : " + ex.toString());
			}
			
		}
	}
	
	public void setHome(Location l)
	{
		isSpawn = l;
	}
	
	public String getMembersToString(){
		String out = "";
		try
		{
			
			for(Integer i : playersID){
				out += "" + i + ",";
			}
		}catch(Exception ex){
			out = "-1";
		}
		
		return out;
	}
	public ArrayList<Integer> getMembers(){
		return playersID;
	}
	public Location getCenter()
	{
		return isCenter;
	}
	public Location getSpawn()
	{
		return isSpawn;
	}
	public int getID(){
		return ID;
	}
	
	public Double getLevel(){
		return isLevel;
	}
}
