package pixelssky.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.EditSession;

import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.command.SchematicCommands;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.SchematicReader;
import com.sk89q.worldedit.regions.CuboidRegion;

import com.sk89q.worldedit.util.Countable;
import com.sk89q.worldedit.world.DataException;
import com.sk89q.worldedit.world.block.BlockType;

import pixelssky.objects.Island;

public class WEManager {
	public static WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	
	
	public static boolean pasteSchematics(World world, File file,Location origin) throws DataException, IOException, MaxChangedBlocksException
    {	
		new BukkitRunnable() {
	        public void run() {
	        	Island i = Locations.getIslandAt(origin);
	        	try{
	    			Block o = world.getBlockAt(origin.getBlockX(), origin.getBlockY() -1 , origin.getBlockZ());
	    			o.setType(Material.BEDROCK);
	    			
	    		}catch(Exception ex){
	    			ex.printStackTrace();
	    		}
	    		
	    		try{
	    			Block chest = world.getBlockAt(origin);
	    			chest.setType(Material.CHEST);
	    			
	    			Chest c = (Chest) chest.getState();
	    			
	    			c.getInventory().addItem(Items.get(Material.DIRT, 6));
	    			c.getInventory().addItem(Items.get(Material.GRASS_BLOCK, 1));
	    			c.getInventory().addItem(Items.get(Material.COBBLESTONE, 6));
	    			c.getInventory().addItem(Items.get(Material.LAVA_BUCKET, 1));
	    			c.getInventory().addItem(Items.get(Material.WATER_BUCKET, 2));
	    			c.getInventory().addItem(Items.get(Material.APPLE, 6));
	    			c.getInventory().addItem(Items.get(Material.OAK_SAPLING, 4));
	    			c.getInventory().addItem(Items.get(Material.BONE, 4));
	    			c.getInventory().addItem(Items.getHelpBook());
	    			
	    			
	    			
	    			if(i.getDifficulty().equals(Island.DIFFICULTY_NORMAL)){
	    				Block sign = chest.getRelative(BlockFace.NORTH);
	    				sign.setType(Material.WALL_SIGN);
	    				Sign s = (Sign) sign.getState();
	    				
	    				s.setLine(0, "Cette ile n'est pas");
	    				s.setLine(1, "l'île de base finale");
	    				s.setLine(2, "Serveur BETA");
	    				s.setLine(3, "1.13");
	    				
	    				s.update();
	    				for(int x = -10; x <= 10; x++){
	    					for(int z = -10; z <= 10; z++){
	    						Block o = world.getBlockAt(origin.getBlockX() + x, origin.getBlockY() -2 , origin.getBlockZ() + z);
	    		    			o.setType(Material.STONE);
	    		    			
	    		    			Block o2 = world.getBlockAt(origin.getBlockX() + x, origin.getBlockY() -1 , origin.getBlockZ() + z);
	    		    			if(!o2.getType().equals(Material.BEDROCK))
	    		    				o2.setType(Material.GRASS_BLOCK);
		    				}
	    				}
	    				
	    			}
	    		}catch(Exception ex){
	    			ex.printStackTrace();
	    		}
	        }
	    }.runTask(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"));

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
