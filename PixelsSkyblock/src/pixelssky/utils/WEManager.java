package pixelssky.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;
import com.sk89q.worldedit.util.Countable;
import com.sk89q.worldedit.world.DataException;
import pixelssky.objects.Island;

@SuppressWarnings({"deprecation" })
public class WEManager {
	public static WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	
	
	public static boolean pasteSchematics(World world, File file,Location origin) throws DataException, IOException, MaxChangedBlocksException
    {	
        EditSession es = new EditSessionBuilder(FaweAPI.getWorld("world")).fastmode(true).build();
        MCEditSchematicFormat.getFormat(file).load(file).paste(es, new Vector(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ()), false); 
        return true;
    }
	

	public static List<Countable<Integer>> count(World world, Location loc1, Location loc2)
	{
		EditSession es = new EditSessionBuilder(FaweAPI.getWorld("world")).fastmode(true).build();
		CuboidSelection cbs = new CuboidSelection(world, loc1 , loc2);
		
		Region r = null;
		try {
			r = cbs.getRegionSelector().getRegion();
		} catch (IncompleteRegionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return es.getBlockDistribution(r);
	}
	public static List<? extends Entity> count_entities(World world, Location loc1, Location loc2)
	{
		EditSession es = new EditSessionBuilder(FaweAPI.getWorld("world")).fastmode(true).build();
		CuboidSelection cbs = new CuboidSelection(world, loc1 , loc2);
		
		Region r = null;
		try {
			r = cbs.getRegionSelector().getRegion();
		} catch (IncompleteRegionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return es.getEntities(r);
	}
	public static void setBiome(int biomeID, Island i){
		Location loc1 = i.getEdges().get(0);
		Location loc2 = i.getEdges().get(1);
		
		EditSession es = new EditSessionBuilder(FaweAPI.getWorld("world")).fastmode(true).build();
		int x_min = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int x_max = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int y_min = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		int y_max = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		int changes = 0;
		for(int x = x_min; x <= x_max; x++){
			for(int y = y_min; y <= y_max; y++){
				changes +=1;
				Bukkit.getWorld("world").setBiome(x, y, Biome.values()[biomeID]);
			}
		}
		i.broadcastMessage("changements : " + changes + " to biome : " + Biome.values()[biomeID]);
		/*es.setBiome(new Vector2D(i.getCenter().getBlockX() & Island.ISLAND_SIZE,
				i.getCenter().getBlockZ() & Island.ISLAND_SIZE),
				new BaseBiome(0));	*/
	}
}
