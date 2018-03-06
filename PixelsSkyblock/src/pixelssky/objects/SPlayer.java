package pixelssky.objects;

import java.util.ArrayList;

import pixelssky.managers.DatabaseManager;

public class SPlayer {
	private String UUID;
	private ArrayList<Data> data;
	private ArrayList<Right> rights;
	private Island island;
	private ScoreboardObject sb;
	private int id;
	
	// TODO : Get and set
	public int getID(){
		return id;
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
	
	public void init(int id, String UUID, Island i){
		this.UUID = UUID;
		island = i;
	}
	
	public String getUUID() {
		return UUID;
	}

	public Data getData(String dataName) {
		for (Data d : data) {
			if (d.getDataName().equals(d)) {
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
		DatabaseManager.setPlayerData(this);
	}


}
