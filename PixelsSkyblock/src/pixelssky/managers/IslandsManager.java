package pixelssky.managers;

import java.util.ArrayList;

import pixelssky.objects.Island;
import pixelssky.utils.Locations;

public class IslandsManager {


	public static ArrayList<Island> islands = new ArrayList<Island>();

	public static void setIsland(Island i){
		if(islands.contains(i)){
			removeIsland(i.getID());
		}
		islands.add(i);
		System.out.println("ADDED ISLAND ID : " + i.getID());
		System.out.println("NB ISLANDS : " + islands.size());
		System.out.println("IS CENTER: " + Locations.toString(i.getSpawn()));
		System.out.println("IS CENTER: " + Locations.toString(i.getCenter()));
	}

	public static Island getIsland(int ID){

		for(Island pl: islands){
			if(pl.getID() == ID)
			{
				return pl;
			}
		}
		return null;
	}

	public static void removeIsland(int id){
		for(Island i: islands){
			if(i.getID() == id)
				islands.remove(i);
		}
		

	}


}
