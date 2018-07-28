package pixelssky.managers;

import java.util.ArrayList;

import org.bukkit.Material;

import com.sk89q.worldedit.blocks.BlockMaterial;
import com.sk89q.worldedit.util.Countable;
import com.sk89q.worldedit.world.block.BlockType;


public class BlocksManager {
	public static ArrayList<Countable<String>> blocs_values = new ArrayList<Countable<String>>();
	
	public static Double getBlockValue(Material blockMaterial,Countable<BlockType> block, int total){
		try{
			double percent = block.getAmount() / Double.parseDouble(total + "");  // Pourcentage de ce type de bloc sur l'�le
			double val = 1 * (1 - percent) * getValue(blockMaterial.toString());
			if (blockMaterial.equals(Material.EMERALD_BLOCK))
				return 0d;
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
