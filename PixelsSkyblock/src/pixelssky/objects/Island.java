package pixelssky.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Merchant;

import com.sk89q.worldedit.blocks.BlockMaterial;
import com.sk89q.worldedit.util.Countable;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.registry.BundledBlockData;

import pixelssky.managers.BlocksManager;
import pixelssky.managers.ChallengesManager;
import pixelssky.managers.IslandsManager;
import pixelssky.managers.PlayersManager;
import pixelssky.merchants.MerchantInventory;
import pixelssky.utils.Classement;
import pixelssky.utils.Locations;
import pixelssky.utils.StringConverter;
import pixelssky.utils.WEManager;

public class Island {
	public static final String DIFFICULTY_HARD = "HARD";
	public static final String DIFFICULTY_NORMAL = "NORMAL";
	public static final String DIFFICULTY_EASY = "EASY";
	public static final String DIFFICULTY_NONE = "EXAMPLE";

	public static final int ISLAND_SIZE = 251;

	private int ID = 0;
	private ArrayList<Integer> playersID = new ArrayList<Integer>();
	private ArrayList<Data> data = new ArrayList<Data>();
	private List<Countable<Material>> block_list = new ArrayList<Countable<Material>>();
	private TreeMap<Material , Double> block_values = new TreeMap<Material,Double>();
	private int total_blocks;
	private Location isCenter;
	private Location isSpawn;
	private Double isLevel;

	/*
	 * IMPORTANT Placement des îles : Carré de 501 de côté 
	 * <--- 125 blocs ---><CENTRE><--- 125 blocs --->
	 * 
	 */

	public Island(int ID, String playersID, String isCenter, String isSpawn, double d) {
		this.ID = ID;
		this.isCenter = Locations.get(isCenter);
		this.isSpawn = Locations.get(isSpawn);
		this.isLevel = d;
		for (String s : playersID.split(",")) {
			try {
				this.playersID.add(Integer.parseInt(s));
			} catch (Exception ex) {
				System.out.println("INVALID_PLAYER_ID : " + ex.toString());
			}
		}
		for(int i =0; i< 10; i++){

		}
		silentCalculateLevel();
	}

	public String getName(){
		if(getData("island name") == null){
			return getData("Creator").getData().toString();
		}else{
			return getData("island name").getData().toString();
		}
	}
	public String getDeaths(){
		if(getData("deaths") != null)
			return getData("deaths").getData().toString();
		return "0";
	}
	public ArrayList<Data> getData() {
		return data;		
	}

	public Data getData(String dataName) {
		for (Data d : data) {
			if (d.getDataName().equals(dataName)) {
				return d;
			}
		}
		return null;
	}
	public void addOrSetData(String dataName, Object data) {
		Data d = getData(dataName);
		if (d == null) {
			this.data.add(new Data(dataName, data));
		} else {
			d.setData(data);
		}
	}

	public void setHome(Location l) {
		isSpawn = l;
	}

	public String getMembersToString() {
		String out = "";
		try {

			for (Integer i : playersID) {
				out += "" + i + ",";
			}
		} catch (Exception ex) {
			out = "-1";
		}

		return out;
	}

	public ArrayList<Integer> getMembers() {
		return playersID;
	}

	public Location getCenter() {
		return isCenter;
	}

	public Location getSpawn() {
		World w = Bukkit.getWorld("world");
		Location cp = Locations.copyLocation(isSpawn);
		try
		{


			if(cp.getBlockY() == 0 || cp.getBlockY() == 1){
				return w.getHighestBlockAt(getCenter()).getLocation().add(0.5, 0.5, 0.5);
			}else if(w.getBlockAt(cp).getType() != Material.AIR){
				return w.getHighestBlockAt(getCenter()).getLocation().add(0.5, 0.5, 0.5);
			}
		}catch(Exception ex)
		{

		}
		return cp.add(0.5, 0.5, 0.5);
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public Double getLevel() {
		return isLevel;
	}
	public String getStringLevel(){
		DecimalFormat df = new DecimalFormat("### ###.##");
		return df.format(getLevel());
	}
	public ArrayList<Location> getEdges(){
		Location pos1 = new Location(Bukkit.getWorld("world"),isCenter.getX() - 125 ,0,isCenter.getZ() - 125);
		Location pos2 = new Location(Bukkit.getWorld("world"),isCenter.getX() + 125 ,256,isCenter.getZ() + 125);
		ArrayList<Location> l = new ArrayList<Location>();
		l.add(pos1); l.add(pos2);
		return l;
	}
	/*
	 * Usefull for async tasks
	 */
	public Island getThis(){
		return this;
	}
	public void calculateLevel(Player p) {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
			@Override
			public void run() {
				ArrayList<String> messages = new ArrayList<String>();
				try{
					p.resetTitle();
					p.sendTitle("§c⚠§4§lCalcul en cours§c⚠", "§eVeuillez patienter", 10,1000,10);
					block_values.clear();
					isLevel = 0d;
					List<Countable<BlockType>> blocks = WEManager.count(Bukkit.getWorld("world"), getEdges().get(0), getEdges().get(1));
					int total = 0;
					for(Countable<BlockType> block : blocks){
						if(!block.getID().getMaterial().equals(Material.AIR)){
							total += block.getAmount();
							try{
								String matName = block.getID().getItemType().getId().toUpperCase().replaceAll(" ", "_").replaceAll("MINECRAFT:", "");
								block_list.add(new Countable(Material.getMaterial(matName), block.getAmount()));
							}catch(Exception ex){
								
							}
						}

					}
					messages.add(total + " blocs comptés");
					double maxValue = 0;
					double minValue = Integer.MAX_VALUE;
					String max = "";
					String min = "";
					total_blocks  = total;
					for(Countable<BlockType> block : blocks){
						try{
						String matName = block.getID().getItemType().getId().toUpperCase().replaceAll(" ", "_").replaceAll("MINECRAFT:", "");
						//Passage 2
						if(!matName.equals(Material.AIR.toString()) && !matName.equals(Material.BEDROCK.toString()) && !matName.equals(Material.EMERALD_BLOCK.toString())){
							Double lvl = BlocksManager.getBlockValue(Material.getMaterial(matName), block, total);
							block_values.put(Material.getMaterial(matName), lvl);
							isLevel += lvl * block.getAmount();
							if(maxValue < lvl * block.getAmount()){
								maxValue = lvl * block.getAmount();
								max = matName;
							}
							if(minValue > lvl * block.getAmount() && lvl * block.getAmount() !=0){
								minValue = lvl * block.getAmount();
								min = matName;
							}
						}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
					p.resetTitle();
					p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
					p.sendTitle("§2§lTerminé !", "", 10,10,10);
					p.sendMessage("");
					p.sendMessage("§a§n▶ §e§l§nNiveau de votre île : §5§l" + String.format("%.2f", isLevel) + " §aProgression : +§a" + String.format("%.2f", isLevel * getProgression()));
					p.sendMessage("");
					p.sendMessage("§a✔ §e Bloc le §aplus §erentable : §5" + max + " §e(§d" + String.format("%.2f", maxValue) + " §eniveaux)");
					p.sendMessage("§4✘§e Bloc le §cmoins §erentable : §5" + min + " §e(§d" + String.format("%.2f", minValue) + " §eniveaux)");
					p.sendMessage("");
					p.sendMessage("§a§n▶ §e§l§nVous êtes : §5§l" + (Classement.getNB(getThis()) + 1) + "§e/" + IslandsManager.islands.size());
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});

	}

	public void silentCalculateLevel() {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
			@Override
			public void run() {
				/*
				block_values.clear();
				isLevel = 0d;
				List<Countable<BlockType>> blocks = WEManager.count(Bukkit.getWorld("world"), getEdges().get(0), getEdges().get(1));
				int total = 0;
				for(Countable<BlockType> block : blocks){
					if(!block.getID().getMaterial().equals(Material.AIR)){
						total += block.getAmount();	
					}

				}
				total_blocks  = total;
				setBlock_list(blocks);

				double maxValue = 0;
				double minValue = Integer.MAX_VALUE;
				for(Countable<BlockType> block : blocks){

					//Passage 2
					if(!block.getID().getMaterial().equals(Material.AIR) && !block.getID().getMaterial().equals(Material.BEDROCK) && !block.getID().getMaterial().equals(Material.EMERALD_BLOCK)){
						Double lvl = BlocksManager.getBlockValue(block.getID().getMaterial(), block, total);
						block_values.put(block.getID().getMaterial(), lvl);
						isLevel += lvl * block.getAmount();
						if(maxValue < lvl * block.getAmount()){
							maxValue = lvl * block.getAmount();
						}
						if(minValue > lvl * block.getAmount() && lvl * block.getAmount() !=0){
							minValue = lvl * block.getAmount();
						}
					}
				}
				 */
			}
		});

	}


	public void setCenter(Location isC) {
		isCenter = isC;

	}

	public boolean isAdmin(int player_ID){
		return StringConverter.getID(getData("admins").getData().toString()).contains(player_ID) || getData("Creator").getData().toString().equals(PlayersManager.getSPlayer(player_ID));
	}
	public String getDifficulty(){
		return getData("difficulty").getData().toString();
	}

	public boolean isMaterialUnlocked(Material m){
		if(this.getData("Débloqué " + m.toString()) != null){
			return true;
		}
		return false;
	}

	
	public double getBlockValue(Material m){
		try
		{
			return block_values.get(m);
		}catch(Exception ex){
			return 0;
		}
	}


	public boolean isMerchantUnlocked(int lvl, String s){
		if(this.getData("Débloqué " + s + lvl) != null || lvl == 1){
			return true;
		}
		return false;
	}

	public void broadcastMessage(String msg){
		for(Player p: Bukkit.getOnlinePlayers()){
			SPlayer sp = PlayersManager.getSPlayer(p);
			if(this.getMembers().contains(sp.getID()) || this.isAdmin(sp.getID())){
				p.sendMessage(msg);
			}
		}
	}
	public int getPriceOffset(){
		return (int) (isLevel / 10);
	}

	@Override
	public String toString(){
		return "§eÎle §b" + this.getName() + " (§7" + this.getDifficulty() +",§8 membres : (" + this.getMembersToString() + "))" + "§7 ID : §a" + this.getID();
	}

	public double getCompleted(ArrayList<Challenge> cl){
		double qte_completed = 0;
		for(Challenge c: cl){
			if(c.isCompleted(this) && !c.isCategory())
				qte_completed += 1;
			else if(c.isCategory())
				qte_completed += getCompleted(c.getSubChallenges());
		}
		return qte_completed;
	}
	public double getProgression(){
		double qte_completed = getCompleted(ChallengesManager.challenges);
		return (qte_completed / ((double) ChallengesManager.number_of_challenges));
	}

	public List<Countable<Material>> getBlock_list() {
		return block_list;
	}

	public void setBlock_list(List<Countable<Material>> block_list) {
		this.block_list = block_list;
	}

}
