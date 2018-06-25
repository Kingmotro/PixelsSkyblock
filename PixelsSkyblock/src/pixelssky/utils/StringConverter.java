package pixelssky.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pixelssky.objects.Lag;

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
		String s = "§d§l" + name + "§b : " + value;
		if(value.equals(""))
			s = "§d" + name ;
		
		
		if(s.length() > 39){
			return s.substring(0, 39);
		}
		return s;
	}
	public static String getColoredLag(Double l){
		Double lag = 100 - Lag.getTPS() * 5;
		DecimalFormat df = new DecimalFormat("###.##");
		String[] colors = {"§2", "§a", "§e", "§6", "§c", "§4"};
		if(lag < 2)
			return colors[0] + df.format(lag) + "§b%";
		else if(lag < 4)
			return colors[1] + df.format(lag) + "§b%";
		else if(lag < 5)
			return colors[2] + df.format(lag) + "§b%";
		else if(lag < 6)
			return colors[3] + df.format(lag) + "§b%";
		else if(lag < 7)
			return colors[4] + df.format(lag) + "§b%";
		else
			return colors[5] + df.format(lag) + "§b%";
	}
}
