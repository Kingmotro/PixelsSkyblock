package pixelssky.objects;

import java.util.ArrayList;
import pixelssky.utils.StringConverter;

/*AKEKOUKOU*/

public class SPlayer{
	private String UUID;
	private ArrayList<Data> data;
	private ArrayList<Right> rights;
	private Island island;
	private ScoreboardObject sb;
	
	//TODO : Get and set
	
	SPlayer(String UUID)
	{
		//TODO : Load or create player data
	}
	
	public void saveData(){
		//TODO : Save all player data, UUID, Island ID and rights
		//Note : Data has to be saved in string
		//Note : Rights has to be saved in string
		
		for(Data d: data){
			
		}		
		for(Right r: rights){
			
		}
		
	}
	public String getUUID(){
		return UUID;
	}
	
	public Data getData(String dataName)
	{
		for(Data d:data){
			if(d.getDataName().equals(d))
			{
				return d;
			}
		}
		return null;
	}
	
	public void addOrSetData(String dataName, Object data){
		Data d = getData(dataName);
		if(d == null){
			this.data.add(new Data(dataName, data));
		}else{
			d.setData(data);
		}
	}
	
	
}
