package pixelssky.managers;

import java.util.ArrayList;

import org.bukkit.Material;

import com.sk89q.worldedit.util.Countable;


public class BlocksManager {
	public static ArrayList<Countable<String>> blocs_values = new ArrayList<Countable<String>>();
	
	public static Double getBlockValue(int ID,Countable<Integer> blocs, int total){
		try{
			double percent = blocs.getAmount() / Double.parseDouble(total + "");  // Pourcentage de ce type de bloc sur l'île
			double val = 1 * (1 - percent) * getValue(Material.getMaterial(blocs.getID()).toString()); 
			
			System.out.println(Material.getMaterial(blocs.getID()).toString() + " taux = " + percent + " valeur = " + val + "BASE = " + getValue(Material.getMaterial(blocs.getID()).toString()));
			return val;
		}catch(Exception ex){
			System.out.println("ERROR GET_BLOCK_VALUE : " + ex.toString());
			return 1d;
		}
	}
	public static void init_values(){
		System.out.println("ADDING");
		ArrayList<String> lines = FileManager.ReadAllText("plugins/PixelsSky/blocs.txt");
		for (String t: lines){
			try
			{
				System.out.println(t);
				blocs_values.add(new Countable<String>(t.split(":")[0], Integer.parseInt(t.split(":")[1].replaceAll(" ", ""))));
			}catch(Exception ex){
				System.out.println(ex.toString());
			}
			
		}
	}
	public static Double getValue(String s){
		try{
			for(Countable<String> b:blocs_values){
				if(b.getID().equals(s)){
					return b.getAmount()*0.01;
				}
			}
			return 0d;
		}catch(Exception ex){
			return 0d;
		}
	}
	
	
}
