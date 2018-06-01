package pixelssky.objects;

import java.util.ArrayList;

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
	
	public void setAfk(boolean isAfk) {
		this.isAfk = isAfk;
	}
	
	public boolean isAfk() {
		return isAfk;
	}
}
