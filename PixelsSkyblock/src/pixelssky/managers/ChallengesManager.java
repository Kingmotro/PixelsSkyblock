package pixelssky.managers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import pixelssky.objects.Challenge;
import pixelssky.objects.objectives.InventoryObjective;
import pixelssky.objects.objectives.Objective;
import pixelssky.objects.objectives.OnislandObjective;
import pixelssky.objects.objectives.StatsObjective;
import pixelssky.rewards.CommandReward;
import pixelssky.rewards.GiveReward;
import pixelssky.rewards.Reward;
import pixelssky.rewards.StatsReward;

public class ChallengesManager {
	public static ArrayList<Challenge> challenges = new ArrayList<Challenge>();
	public static int number_of_challenges = 0;
	public static void init(){
		File folder = new File("plugins/PixelsSky/Challenges");
		for(File f: folder.listFiles()){
			try{
				if(!f.isDirectory()){
					//Lire le fichier : NOTE -> A la racine : que des cat�gories
					ArrayList<String> lines = FileManager.ReadAllText(f.getAbsolutePath());
					String c_name = null;
					int type = -1;
					boolean isUnlocked = false;
					Material m = Material.WHITE_WOOL;
					int i = 0;
					for(String l: lines){
						if(l.split("=")[0].equals("name")){
							c_name = l.split("=")[1];
						}else if(l.split("=")[0].equals("type")){
							type = Integer.parseInt(l.split("=")[1]);
						}
						else if(l.split("=")[0].equals("unlocked_by_default")){
							isUnlocked = Boolean.parseBoolean(l.split("=")[1]);
						}else if(l.split("=")[0].equals("material")){
							m = Material.getMaterial(l.split("=")[1]);
						}
					}
					if(m != null)
						challenges.add(new Challenge(type, c_name, isUnlocked, m));
					else
						System.out.println("Not added : " + c_name + " -> Invalid material name !");
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}

		}
		Collections.sort(challenges, Challenge.COMPARE_BY_NAME);
		init_subChallenges();
	}
	public static void init_subChallenges(){
		for(Challenge categ: challenges){
			try
			{
				File folder = new File("plugins/PixelsSky/Challenges/" + categ.getName());
				
				for(File f:folder.listFiles()){
					try{
						if(!f.isDirectory()){

							ArrayList<String> lines = FileManager.ReadAllText(f.getAbsolutePath());
							ArrayList<Objective> obj = new ArrayList<Objective>();
							ArrayList<Reward> rewards = new ArrayList<Reward>();

							String c_name = null;
							int type = -1;
							boolean can_redo = false;
							Material m = null;
							int subid = 0;
							boolean isUnlocked = false;
							for(String l: lines){
								if(l.split("=")[0].equals("name")){
									c_name = l.split("=")[1];
								}else if(l.split("=")[0].equals("unlocked_by_default")){
									isUnlocked = Boolean.parseBoolean(l.split("=")[1]);
								}else if(l.split("=")[0].equals("type")){
									type = Integer.parseInt(l.split("=")[1]);
								}else if(l.split("=")[0].equals("material")){
									m = Material.getMaterial(l.split("=")[1]);
								}else if(l.split("=")[0].equals("can_redo")){
									can_redo = Boolean.parseBoolean((l.split("=")[1]));
								}else if(l.split("=")[0].equals("objective")){
									String[] s = l.split("=")[1].split(",");
									if(s[0].equals("inventory")){
										obj.add(new InventoryObjective(Material.getMaterial(s[1]), Integer.parseInt(s[2]), s[3].equals("take")));
									}else if(s[0].equals("onisland")){
										if(s[1].equals("block"))
											obj.add(new OnislandObjective(s[1].equals("entity"), s[2],Integer.parseInt(s[3])));
										else if(s[1].equals("entity"))
											obj.add(new OnislandObjective(s[1].equals("entity"), s[2],Integer.parseInt(s[3])));
									}else if(s[0].equals("stats")){
										obj.add(new StatsObjective(s[1], s[2], s[3]));
									}
								}else if(l.split("=")[0].equals("reward")){
									String[] s = l.split("=")[1].split(",");
									if(s[0].equals("give")){
										if(s[3].split(":").length > 1){
											TreeMap<String, Integer> e = new TreeMap<String, Integer>();
											for(String ench: s[3].split(":")[1].split(";")){
												e.put(ench.split("/")[0], Integer.parseInt(ench.split("/")[1]));
											}
											rewards.add(new GiveReward(Material.getMaterial(s[1]),Integer.parseInt(s[2]), e));
										}else{
											rewards.add(new GiveReward(Material.getMaterial(s[1]),Integer.parseInt(s[2])));
										}
									}else if(s[0].equals("command")){
										rewards.add(new CommandReward(s[1], s[2]));
									}else if(s[0].equals("stats")){
										rewards.add(new StatsReward(s[1], s[2], s[3]));

									}
								}
							}
							if(m != null){
								categ.getSubChallenges().add(new Challenge(type,c_name,obj,rewards,can_redo,m, isUnlocked));
								Collections.sort(categ.getSubChallenges(), Challenge.COMPARE_BY_NAME);
							}else{
								System.out.println("->" + categ.getName() + " Not added : " + c_name + " -> Invalid material name !");
							}
						}
					}catch(Exception ex){
						Bukkit.getLogger().warning("Error : " + categ.getName() + ex.toString());
					}

				}
			}catch(Exception ex){
				Bukkit.getLogger().warning("Error : " + categ.getName());
			}
		}
	}

	public static Challenge getChallenge(String name){
		for(Challenge c: challenges){
			if(c.getName().equals(name)){
				return c;
			}
		}
		return null;
	}

}
