package pixelssky.objects;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.IslandsManager;

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

	public void saveData() {
		// TODO : Save all player data, UUID, Island ID and rights
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
}
