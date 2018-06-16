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

import com.sk89q.worldedit.util.Countable;

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
	private List<Countable<Integer>> block_list;
	private TreeMap<Integer,Double> block_values = new TreeMap<Integer,Double>();
	private int total_blocks;
	private Location isCenter;
	private Location isSpawn;
	private Double isLevel;
	private TreeMap<Integer, MerchantInventory> m = new TreeMap<Integer, MerchantInventory>();
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
			m.put(i, new MerchantInventory(i, this));
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
		try
		{
			Location l = w.getHighestBlockAt(isSpawn).getLocation();
			if(l.getBlockY() == 0 || l.getBlockY() == 1){
				return w.getHighestBlockAt(getCenter()).getLocation().add(0.5, 0.5, 0.5);
			}else if(w.getBlockAt(l).getType() != Material.AIR){
				return w.getHighestBlockAt(getCenter()).getLocation().add(0.5, 0.5, 0.5);
			}
		}catch(Exception ex)
		{

		}
		return w.getHighestBlockAt(isSpawn).getLocation().add(0.5, 0.5, 0.5);
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
				p.resetTitle();
				p.sendTitle("§c⚠§4§lCalcul en cours§c⚠", "§eVeuillez patienter", 10,1000,10);
				block_values.clear();
				isLevel = 0d;
				List<Countable<Integer>> blocks = WEManager.count(Bukkit.getWorld("world"), getEdges().get(0), getEdges().get(1));
				int total = 0;
				for(Countable<Integer> block : blocks){
					if(block.getID() != 0){
						total += block.getAmount();	
					}

				}
				double maxValue = 0;
				double minValue = Integer.MAX_VALUE;
				String max = "";
				String min = "";
				total_blocks  = total;
				setBlock_list(blocks);
				for(Countable<Integer> block : blocks){
					//Passage 2
					if(block.getID() !=0 && block.getID() !=7 && block.getID() !=133){
						Double lvl = BlocksManager.getBlockValue(block.getID(), block, total);
						block_values.put(block.getID(), lvl);
						isLevel += lvl * block.getAmount();
						if(maxValue < lvl * block.getAmount()){
							maxValue = lvl * block.getAmount();
							max = Material.getMaterial(block.getID()).toString();
						}
						if(minValue > lvl * block.getAmount() && lvl * block.getAmount() !=0){
							minValue = lvl * block.getAmount();
							min = Material.getMaterial(block.getID()).toString();
						}
					}
				}
				p.resetTitle();
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
				p.sendTitle("§2§lTerminé !", "", 10,10,10);
				p.sendMessage("");
				p.sendMessage("§a§n▶ §e§l§nNiveau de votre île : §5§l" + String.format("%.2f", isLevel));
				p.sendMessage("");
				p.sendMessage("§a✔ §e Bloc le §aplus §erentable : §5" + max + " §e(§d" + String.format("%.2f", maxValue) + " §eniveaux)");
				p.sendMessage("§4✘§e Bloc le §cmoins §erentable : §5" + min + " §e(§d" + String.format("%.2f", minValue) + " §eniveaux)");
				p.sendMessage("");
				p.sendMessage("§a§n▶ §e§l§nVous êtes : §5§l" + (Classement.getNB(getThis()) + 1) + "§e/" + IslandsManager.islands.size());
			}
		});

	}

	public void silentCalculateLevel() {
		Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
			@Override
			public void run() {
				block_values.clear();
				isLevel = 0d;
				List<Countable<Integer>> blocks = WEManager.count(Bukkit.getWorld("world"), getEdges().get(0), getEdges().get(1));
				int total = 0;
				for(Countable<Integer> block : blocks){
					if(block.getID() != 0){
						total += block.getAmount();	
					}

				}
				total_blocks  = total;
				setBlock_list(blocks);

				double maxValue = 0;
				double minValue = Integer.MAX_VALUE;
				for(Countable<Integer> block : blocks){
					//Passage 2
					if(block.getID() !=0 && block.getID() !=7 && block.getID() !=133){
						Double lvl = BlocksManager.getBlockValue(block.getID(), block, total);
						block_values.put(block.getID(), lvl);
						isLevel += lvl * block.getAmount();
						if(maxValue < lvl * block.getAmount()){
							maxValue = lvl * block.getAmount();
						}
						if(minValue > lvl * block.getAmount() && lvl * block.getAmount() !=0){
							minValue = lvl * block.getAmount();
						}
					}
				}

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

	@SuppressWarnings("deprecation")
	public double getBlockValue(Material m){
		int id = m.getId();
		try
		{
			return block_values.get(id);
		}catch(Exception ex){
			return Double.MAX_VALUE;
		}
	}
	public Merchant getMerchant(int lvl, String s){
		if(isMerchantUnlocked(lvl, s)){
			return m.get(lvl).getMerchant(s);
		}
		return null;
	}
	public MerchantInventory getMerchantInventory(int lvl, String s){
		return m.get(lvl);
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

	public List<Countable<Integer>> getBlock_list() {
		return block_list;
	}

	public void setBlock_list(List<Countable<Integer>> block_list) {
		this.block_list = block_list;
	}
	
}
