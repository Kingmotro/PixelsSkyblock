package pixelssky.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;

import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.command.SchematicCommands;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.SchematicReader;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.factory.CuboidRegionFactory;
import com.sk89q.worldedit.regions.selector.*;
import com.sk89q.worldedit.util.Countable;
import com.sk89q.worldedit.world.AbstractWorld;
import com.sk89q.worldedit.world.DataException;
import com.sk89q.worldedit.world.block.BlockType;

import pixelssky.objects.Island;

@SuppressWarnings({"deprecation" })
public class WEManager {
	public static WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	
	
	public static boolean pasteSchematics(World world, File file,Location origin) throws DataException, IOException, MaxChangedBlocksException
    {	
		
		EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(world),-1);
		Vector v1 = new Vector(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
		Vector v2 = new Vector(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
		CuboidRegion r = new CuboidRegion(new BukkitWorld(world), v1, v2);
		
		es.setFastMode(true);
		/*
        EditSession es = new EditSessionBuilder(FaweAPI.getWorld("world")).fastmode(true).build();
        
		
		SchematicReader sr = new SchematicReader(inputStream );
        MCEditSchematicFormat.getFormat(file).load(file).paste(es, new Vector(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ()), false); 
        */
        return true;
    }


	public static List<Countable<BlockType>> count(World world, Location loc1, Location loc2)
	{
		 
		EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(world),-1);
		Vector v1 = new Vector(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
		Vector v2 = new Vector(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
		CuboidRegion r = new CuboidRegion(new BukkitWorld(world), v1, v2);
		
		es.setFastMode(true);
		
		return es.getBlockDistribution(r);
	}
	public static List<? extends Entity> count_entities(World world, Location loc1, Location loc2)
	{
		EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(world), -1);
		Vector v1 = new Vector(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
		Vector v2 = new Vector(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
		CuboidRegion r = new CuboidRegion(new BukkitWorld(world), v1, v2);
		
		return es.getEntities(r);
	}
	public static void setBiome(int biomeID, Island i){
		Location loc1 = i.getEdges().get(0);
		Location loc2 = i.getEdges().get(1);
		
		//EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(Bukkit.getWorld("world")), -1);
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
