package pixelssky.objects;

import org.bukkit.Location;

public class Data {
	private String dataName;
	private Object data;
	
	public Object getData()
	{
		return data;
	}
	
	public void setData(Object data){
		this.data = data;
	}
}
