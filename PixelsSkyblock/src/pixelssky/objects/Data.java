package pixelssky.objects;

import org.bukkit.Location;

public class Data {
	
	private String dataName;
	private Object data;
	
	
	Data(String dName, Object d){
		dataName = dName;
		data = d;
	}
	
	public String getDataName(){
		return dataName;
	}
	
	public Object getData()
	{
		return data;
	}
	
	public void setData(Object data){
		this.data = data;
	}
	
}
