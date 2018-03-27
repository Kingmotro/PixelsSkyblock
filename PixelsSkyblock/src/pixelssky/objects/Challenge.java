package pixelssky.objects;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import pixelssky.objects.objectives.Objective;

public class Challenge {
	public static final int TYPE_CATEGORY = 0;
	public static final int TYPE_NORMAL = 1;
	
	private ArrayList<Objective> obj = new ArrayList<Objective>();
	private ArrayList<Reward> rewards = new ArrayList<Reward>();
	private ArrayList<Challenge> subChallenges; //NULL SI PAS CATEGORIE
	private String name;
	private int type;
	private boolean can_redo = false;
	
	public Challenge(int type, String name){
		if(type == Challenge.TYPE_CATEGORY){
			subChallenges = new ArrayList<Challenge>();
		}else{
			obj = new ArrayList<Objective>();
			rewards = new ArrayList<Reward>();
		}
		this.name = name;
		this.type = type;
	}
	public Challenge(int type, String name, ArrayList<Objective> objectives, ArrayList<Reward> rewards, boolean can_redo){
		if(type == Challenge.TYPE_CATEGORY){
			subChallenges = new ArrayList<Challenge>();
		}else{
			obj = objectives;
			this.rewards = rewards;
		}
		this.can_redo = can_redo;
		this.name = name;
		this.type = type;
	}
	
	public ArrayList<Objective> getObjectives(){
		return obj;
	}
	
	public ArrayList<Challenge> getSubChallenges(){
		return subChallenges;
	}
	public ArrayList<Reward> getRewards(){
		return rewards;
	}
	public int getType(){
		return type;
	}
	public boolean isCategory(){
		return Challenge.TYPE_CATEGORY == type;
	}
	public String getName(){
		return name;
	}
	
	public boolean completed(){
		return false;
	}
	public boolean can_redo(){
		return can_redo;
	}
	
	public boolean isUnlocked(){
		return false;
	}
	public void complete(Player p, Island i){
		boolean ok = true;
		for(Objective o : obj){
			if(!o.check(p, i)){
				ok = false;
				p.sendMessage(o.getFailMessage(p));
			}
		}
		if(ok){
			for(Objective o : obj){
				o.run(p,i);
			}
			p.sendTitle("Challenge complété !",name);
		}else{
			p.sendTitle("Challenge raté :/",name);
		}
	}
}
