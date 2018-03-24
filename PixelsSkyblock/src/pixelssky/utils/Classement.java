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
			map.put(i.getLevel(), i);

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
