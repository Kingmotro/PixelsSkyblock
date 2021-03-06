package pixelssky.objects;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.IslandsManager;
import pixelssky.utils.Classement;
import pixelssky.utils.StringConverter;

public class SPlayer {
	private String UUID;
	private ArrayList<Data> data = new ArrayList<Data>();
	private ArrayList<Right> rights = new ArrayList<Right>();
	private Island island;
	private ScoreboardObject sb;
	private int id;
	private int id_last_invite = -1;
	private String lastTpaRequest;
	private boolean isAfk = false;
	private boolean protectionOverride = false;
	// TODO : Get and set
	public int getID(){
		return id;
	}
	public void setIsland(Island i){
		island = i;
	}
	public ArrayList<Right> getRights() {
		return rights;		
	}

	public ArrayList<Data> getData() {
		return data;		
	}

	public void addRight(Right r) {
		if(!rights.contains(r)){
			rights.add(r);
		}
	}

	public void removeRight(Right r) {
		if(rights.contains(r)){
			rights.remove(r);
		}
	}

	public Island getIsland(){
		return island;
	}

	public boolean hasRight(Right r){
		return rights.contains(r);
	}

	public ScoreboardObject getScoreboard(){
		return sb;
	}

	public void init(int id, String UUID, int i){
		this.UUID = UUID;
		island = IslandsManager.getIsland(i);
		this.id = id;
	}

	public String getUUID() {
		return UUID;
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

	public SPlayer(String UUID) {
		// TODO : Load or create player data
		if(!UUID.equals(""))
		{
			DatabaseManager.getPlayer(UUID);
		}
	}
	/*
	 * Classement des îles variable :
	 */

	public void syncSB(){

		try{
			java.util.UUID uniqueId = java.util.UUID.fromString(UUID);
			Player p = Bukkit.getPlayer(uniqueId);
			sb.update();
			Island i =  this.getIsland();
			DecimalFormat df = new DecimalFormat("###.##");
			Date d = new Date();
			try{
				sb.setScoreText("is_infobar1", StringConverter.convertForSB("=-=-= §bInformation d'île §d=-=-=",""), 11);
				if(i == null){
					sb.setScoreText("cmd_1", StringConverter.convertForSB("Créer son île", "/is create"), 10);
					sb.setScoreText("cmd_2", StringConverter.convertForSB("Challenges", "/c"), 9);
					sb.setScoreText("cmd_3", StringConverter.convertForSB("Aller au spawn", "/s"), 8);
					sb.setScoreText("desc1", StringConverter.convertForSB("2 Choix d'île : ", ""), 7);
					sb.setScoreText("desc1", StringConverter.convertForSB("UltraHard : ", "Dur mais fun !"), 6);
					sb.setScoreText("desc2", StringConverter.convertForSB("Normale : ", "Moyen et amusant"), 5);
				}else{
					sb.setScoreText("is_name", StringConverter.convertForSB("Nom",i.getName()), 10);
					sb.setScoreText("is_lvl", StringConverter.convertForSB("Niveau","" + df.format(i.getLevel())), 9);
					sb.setScoreText("is_prog", StringConverter.convertForSB("Progression","" + df.format(i.getProgression()*100) + "§b%"), 8);
					sb.setScoreText("is_deaths", StringConverter.convertForSB("Morts","" + i.getDeaths()), 7);
					try{
						
					
					sb.setScoreText("is_pos", StringConverter.convertForSB("Classement",(Classement.getNB(i) +1) +""), 6);
					}catch(Exception ex){
						
					}
					sb.setScoreText("is_biome", StringConverter.convertForSB("Biome","" + p.getWorld().getBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ()).name()), 5);

				}

				sb.setScoreText("is_infobar2", StringConverter.convertForSB("=-= §bInformation Serveur §d-=-=",""), 4);
				sb.setScoreText("is_lag", StringConverter.convertForSB("Lag",StringConverter.getColoredLag(Lag.getTPS())), 3);
				sb.setScoreText("is_hour", StringConverter.convertForSB("Heure",d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds()), 2);
				//sb.setScoreText("is_ping", StringConverter.convertForSB("Ping","" +((CraftPlayer) p).getHandle().ping), 1);
				sb.setScoreText("is_desc", StringConverter.convertForSB("NOUVEAU","La progression donne "), 0);
				sb.setScoreText("is_desc2", StringConverter.convertForSB("->","des niveaux =D"), -1);
			}catch(Exception ex){

			}
		}catch(Exception ex){
			java.util.UUID uniqueId = java.util.UUID.fromString(UUID);
			Player p = Bukkit.getPlayer(uniqueId);
			sb = new ScoreboardObject(p);
			syncSB();
		}
	}

	public void saveData() {
		DatabaseManager.writePlayerData(this);
		DatabaseManager.updatePlayer(this);
	}

	public void setLastIsInvite(int id){
		id_last_invite = id;
	}

	public int getLastIsInvite(){
		return id_last_invite;
	}

	public void setTpaRequest(String name) {
		lastTpaRequest = name;
	}

	public String getLastTpaRequest() {
		return lastTpaRequest;
	}

	public void setAfk(boolean isAfk, Player pl, String[] arg3) {
		String txt = "";
		if(isAfk == true){

			String reason = "§7Aucune raison";
			if(arg3.length >= 1){
				reason = "§7";
				for(String s: arg3){
					reason += s + " ";
				}
				reason = reason.substring(0, reason.length() - 1);
			}
			if(this.getIsland() != null){
				txt = "§5[Ile §d§l" + this.getIsland().getName() + "§5] §7"+ pl.getDisplayName() + " §d est afk §f(" + reason + ")";
			}else{
				txt = "§5[§d§lSans île fixe§5] §7"+ pl.getDisplayName() + " §d est afk §f(" + reason + ")";
			}
			Bukkit.broadcastMessage(txt);

			pl.sendTitle("§cVOUS ÊTES AFK","§eFaites attention aux creepers furtifs ...", 999,999,0);
		}else{
			if(this.getIsland() != null){
				txt = "§5[Ile §d§l" + this.getIsland().getName() + "§5] §7"+ pl.getDisplayName() + " §d n'est plus afk.";
			}else{
				txt = "§5[§d§lSans île fixe§5] §7"+ pl.getDisplayName() + " §d n'est plus afk.";
			}
			Bukkit.broadcastMessage(txt);
			pl.resetTitle();
		}
		this.isAfk = isAfk;
	}

	public boolean isAfk() {
		return isAfk;
	}

	public boolean getProtectionOverride() {
		return protectionOverride;
	}

	public void setProtectionOverride(boolean protectionOverride) {
		this.protectionOverride = protectionOverride;
	}
	
}
