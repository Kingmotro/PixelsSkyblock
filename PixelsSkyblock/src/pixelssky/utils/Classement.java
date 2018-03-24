package pixelssky.utils;


import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import pixelssky.managers.IslandsManager;
import pixelssky.objects.Island;

public class Classement {
	
	
	private static  ArrayList<String> reverseList( ArrayList<String> myList) {
		ArrayList<String> invertedList = new ArrayList<String>();
	    for (int i = myList.size() - 1; i >= 0; i--) {
	        invertedList.add(myList.get(i));
	    }
	    return invertedList;
	}
	
	public static ArrayList<String> getTop()
	{
		ArrayList<String> classes = new ArrayList<String>();
		TreeMap<Double, Island> map = new TreeMap<Double, Island>();
		for (Island i : IslandsManager.islands)
		{
			if(i.getDifficulty().equals(Island.DIFFICULTY_HARD)){
				map.put(i.getLevel(), i);
			}else if(i.getDifficulty().equals(Island.DIFFICULTY_NORMAL)){
				map.put(i.getLevel() - 50000, i);
			}else if(i.getDifficulty().equals(Island.DIFFICULTY_EASY)){
				map.put(i.getLevel() - 100000, i);
			}
			
		}
		for(Entry<Double, Island> e : map.entrySet()) 
		{ 
			classes.add(e.getValue().getID() + "");
		}
		
		return reverseList(classes);
	}
	public static int getNB(Island i){
		return getTop().indexOf(i.getID() + "");
	}
	
}
