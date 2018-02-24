package pixelssky.objects;

import java.util.ArrayList;

public class Right {
	public static ArrayList<Right> rList = new ArrayList<Right>();
	public static void registerRight(String r){
		rList.add(new Right(r));
	}
	public static Right getRight(String r){
		for(Right ri: rList){
			if(ri.getRight().equals(r)){
				return ri;
			}
		}
		return null;
	}
	
	private String rName;
	
	
	Right(Right r){
		this.rName = r.getRight();
	}
	Right(String r){
		this.rName = r;
	}
	

	public String getRight()
	{
		return rName;
	}
}
