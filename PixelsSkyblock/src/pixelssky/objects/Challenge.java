package pixelssky.objects;

import java.util.ArrayList;

public class Challenge {
	public static final int TYPE_CATEGORY = 0;
	public static final int TYPE_NORMAL = 1;
	
	private ArrayList<Objective> obj = new ArrayList<Objective>();
	private ArrayList<Reward> rewards = new ArrayList<Reward>();
	private ArrayList<Challenge> subChallenges = new ArrayList<Challenge>(); //NULL SI PAS CATEGORIE
	private String name;
	private int type;
	
	
}
