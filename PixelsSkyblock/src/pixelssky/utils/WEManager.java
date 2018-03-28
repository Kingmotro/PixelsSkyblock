package pixelssky.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

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
}
