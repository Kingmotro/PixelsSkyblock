package pixelssky.managers;

import java.io.File;
import java.util.ArrayList;

import pixelssky.objects.Challenge;
import pixelssky.objects.Reward;
import pixelssky.objects.objectives.InventoryObjective;
import pixelssky.objects.objectives.Objective;

public class ChallengesManager {
	public static ArrayList<Challenge> challenges = new ArrayList<Challenge>();
	
	public static void init(){
		File folder = new File("plugins/PixelsSky/Challenges");
		for(File f: folder.listFiles()){
			if(!f.isDirectory()){
				//Lire le fichier : NOTE -> A la racine : que des catégories
				ArrayList<String> lines = FileManager.ReadAllText(f.getAbsolutePath());
				String c_name = null;
				int type = -1;
				for(String l: lines){
					if(l.split("=")[0].equals("name")){
						c_name = l.split("=")[1];
					}else if(l.split("=")[0].equals("type")){
						type = Integer.parseInt(l.split("=")[1]);
					}
				}
				challenges.add(new Challenge(type, c_name));
			}
		}
		init_subChallenges();
	}
	public static void init_subChallenges(){
		for(Challenge categ: challenges){
			File folder = new File("plugins/PixelsSky/Challenges/" + categ.getName());
			for(File f:folder.listFiles()){
				if(!f.isDirectory()){
					
					ArrayList<String> lines = FileManager.ReadAllText(f.getAbsolutePath());
					ArrayList<Objective> obj = new ArrayList<Objective>();
					ArrayList<Reward> rewards = new ArrayList<Reward>();
					
					String c_name = null;
					int type = -1;
					boolean can_redo = false;
					
					for(String l: lines){
						if(l.split("=")[0].equals("name")){
							c_name = l.split("=")[1];
						}else if(l.split("=")[0].equals("type")){
							type = Integer.parseInt(l.split("=")[1]);
						}else if(l.split("=")[0].equals("can_redo")){
							can_redo = Boolean.parseBoolean((l.split("=")[1]));
						}else if(l.split("=")[0].equals("objective")){
							String[] s = l.split("=")[1].split(",");
							if(s[0].equals("inventory")){
								obj.add(new InventoryObjective(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), s[4].equals("take")));
							}
						}
					}
					categ.getSubChallenges().add(new Challenge(type,c_name,obj,rewards,can_redo));
				}
			}
		}
	}
	
	public static Challenge getChallenge(String name){
		System.out.println(name);
		for(Challenge c: challenges){
			if(c.getName().equals(name)){
				return c;
			}
		}
		return null;
	}
}
