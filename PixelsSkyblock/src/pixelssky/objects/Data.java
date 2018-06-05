package pixelssky.objects;

import pixelssky.utils.StringConverter;

public class Data {
	
	public static final String DEATHS = "deaths"; //nb
	public static final String DEATHS_BY_REASON = "deaths_by_reason"; //deaths_by_reason:RAISON -> nb
	public static final String PLACED_BLOCKS = "placed_blocks"; //placed_blocks:BLOCK_MATERIAL -> nb
	public static final String BROKEN_BLOCKS = "broken_blocks"; //broken_blocks:BLOCK_MATERIAL -> nb
	public static final String KILLED_MOBS = "killed_mobs"; //killed_mobs:MOB_TYPE -> nb
	
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
	
	public String toString()
	{
		return StringConverter.getDataToString(data);
	}
	public void add(double val){
		try{
			 data = "" + (Double.parseDouble((String) data) + val);
		}catch(Exception ex){
			System.out.println("Erreur : la donnée n'est pas numérique.");
		}

	}
	public void rmv(double val){
		try{
			 data = "" + (Double.parseDouble((String) data) - val);
		}catch(Exception ex){
			System.out.println("Erreur : la donnée n'est pas numérique.");
		}

	}
	
}
