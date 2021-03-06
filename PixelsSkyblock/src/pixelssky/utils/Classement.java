package pixelssky.utils;


import java.util.ArrayList;
import java.util.Map.Entry;

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
			double prog = i.getProgression();
			double lvl = i.getLevel();
			if(i.getDifficulty().equals(Island.DIFFICULTY_HARD)){
				map.put(lvl + lvl * prog, i);
			}else if(i.getDifficulty().equals(Island.DIFFICULTY_NORMAL)){
				map.put(lvl + lvl * prog - 99999, i);
			}else if(i.getDifficulty().equals(Island.DIFFICULTY_EASY)){
				map.put(lvl + lvl * prog - 99999, i);
			}else{
				map.put(lvl + lvl * prog - 99999, i);
			}
			
		}
		int nb = map.size() + 1 ;
		for(Entry<Double, Island> e : map.entrySet()) 
		{ 
			nb -= 1;
			Island i = e.getValue();
			classes.add("§a§l" + nb + " : §e[" + i.getDifficulty() + "]" + " §b" + i.getName() + " §e" + String.format("%.2f", i.getProgression()*i.getLevel() + i.getLevel()) + " bonus : §a+" + String.format("%.2f", i.getProgression()*i.getLevel()) +
					"§7[☠ "+ i.getDeaths() + ", Challenges : " + String.format("%.2f", i.getProgression()*100)+ "%]");
		}
		
		return reverseList(classes);
	}
	public static int getNB(Island is){
		TreeMap<Double, Island> map = new TreeMap<Double, Island>();
		for (Island i : IslandsManager.islands)
		{
			if(i.getDifficulty().equals(Island.DIFFICULTY_HARD)){
				map.put(i.getLevel() + i.getProgression()* i.getLevel(), i);
			}else if(i.getDifficulty().equals(Island.DIFFICULTY_NORMAL)){
				map.put(i.getLevel()+ i.getProgression()* i.getLevel() - 99999, i);
			}else if(i.getDifficulty().equals(Island.DIFFICULTY_EASY)){
				map.put(i.getLevel()+ i.getProgression()* i.getLevel() - 99999, i);
			}
			
		}
		int nb = map.size();
		for(Entry<Double, Island> e : map.entrySet()) 
		{ 
			nb -= 1;
			Island i = e.getValue();
			if(i.equals(is))
				return nb;
		}
		return nb;
	}
	
}
