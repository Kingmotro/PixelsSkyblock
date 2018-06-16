package pixelssky.utils;

import java.util.ArrayList;

public class StringConverter {
	public static String getDataToString(Object d){
		//TODO ...
		return "";
	}
	public static ArrayList<Integer> getID(String data){
		ArrayList<Integer> d = new ArrayList<Integer>();
		for(String t:data.split(",")){
			d.add(Integer.parseInt(t));
		}
		return d;
	}
	public static String convertForSB(String name, String value){
		String s = "§d" + name + "§5 : " + value;
		if(s.length() > 39){
			return s.substring(0, 39);
		}
		return s;
	}
}
