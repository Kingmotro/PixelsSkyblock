package pixelssky.objects;

import java.util.ArrayList;

public class Right {
	public static ArrayList<Right> rList = new ArrayList<Right>();
	public static void registerRight(String r){
		rList.add(new Right(r));
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
