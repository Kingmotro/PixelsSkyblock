package pixelssky.objects;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pixelssky.enchantements.Enchantements;
import pixelssky.objects.objectives.Objective;
import pixelssky.rewards.CommandReward;
import pixelssky.rewards.Reward;
import pixelssky.utils.Items;

public class Challenge {
	public static final int TYPE_CATEGORY = 0;
	public static final int TYPE_NORMAL = 1;

	private ArrayList<Objective> obj = new ArrayList<Objective>();
	private ArrayList<Reward> rewards = new ArrayList<Reward>();
	private ArrayList<Challenge> subChallenges; //NULL SI PAS CATEGORIE
	private String name;
	private int type;
	private boolean can_redo = false;
	private Material m = Material.STONE;
	private int i = 0;

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
	public Challenge(int type, String name, ArrayList<Objective> objectives, ArrayList<Reward> rewards, boolean can_redo, Material m, int i){
		if(type == Challenge.TYPE_CATEGORY){
			subChallenges = new ArrayList<Challenge>();
		}else{
			obj = objectives;
			this.rewards = rewards;
		}
		this.can_redo = can_redo;
		this.name = name;
		this.type = type;
		this.m = m;
		this.i = i;
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

	public boolean isCompleted(Island i){
		return i.getData("completed"+ this.name) != null;
	}
	public boolean can_redo(){
		return can_redo;
	}

	public boolean isUnlocked(){
		return false;
	}
	@SuppressWarnings("deprecation")
	public void complete(Player p, Island i){
		if(!isCompleted(i) || (isCompleted(i) && can_redo)){
			Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
				@Override
				public void run() {
					p.sendTitle("§c⚠§4§lCalcul en cours§c⚠", "§eVeuillez patienter", 10,1000,10);
					p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 100, 100);
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
						for(Reward r : rewards){
							if(!(r instanceof CommandReward)){
								r.run(p,i);
							}
						}
						p.sendTitle("§aChallenge complété !", "§2" + name,10,10,100);
						i.addOrSetData("completed"+ getName(),"" + true);
						p.playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 100, 100);
						for(int k = 0; k<10; k++){
							p.playEffect(p.getLocation().subtract(k, k, k), Effect.SMOKE, k);
							p.playEffect(p.getLocation().subtract(-k, -k, -k), Effect.SMOKE, k);

							p.playEffect(p.getLocation().subtract(-k, k, k), Effect.SMOKE, k);
							p.playEffect(p.getLocation().subtract(k, k, -k), Effect.SMOKE, k);

							p.playEffect(p.getLocation().subtract(-k, -k, k), Effect.SMOKE, k);
							p.playEffect(p.getLocation().subtract(k, -k, -k), Effect.SMOKE, k);

							p.playEffect(p.getLocation().subtract(-k, k, -k), Effect.SMOKE, k);
							p.playEffect(p.getLocation().subtract(k, -k, k), Effect.SMOKE, k);
						}
					}else{
						p.sendTitle("§cChallenge raté :/","§4" + name,10,10,100);
						p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 100, 100);
					}
				}
			});
			for(Reward r : rewards){
				if(r instanceof CommandReward){
					r.run(p,i);
				}
				
			}
		}else{
			p.sendTitle("§c⚠§4§lImpossible de refaire§c⚠", "§eCe challenge n'est pas refaisable", 10,1000,10);
		}
	}
	public ItemStack getItem(Island i){
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§b▊▊▊▊▊▊▊▊   Objectif(s) ▊▊▊▊▊▊▊▊");
		for(Objective o : obj){
			lore.add(o.getDescription());
		}
		lore.add("§b▊▊▊▊▊▊▊   Récompense(s) ▊▊▊▊▊▊▊");
		for(Reward r: rewards){
			lore.add(r.getDescription());
		}
		
		if(this.isCompleted(i)){
			if(can_redo){
				lore.add("§7Vous avez déjà complété ce challenge");
				lore.add("§7§lMAIS vous pouvez le refaire !");
			}else{
				lore.add("§7Ce challenge n'est pas refaisable §l:/");
			}
			
			ItemStack it = Items.get(this.name,m, (byte) this.i, lore); 
			it.addUnsafeEnchantment(Enchantements.VALIDATED_CHALLENGE, 1);
			return it;
		}else{
			return Items.get(this.name, m, (byte) this.i, lore);
		}
	}
}
