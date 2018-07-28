package pixelssky.objects;

import java.util.ArrayList;
import java.util.Comparator;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pixelssky.enchantements.Enchantements;
import pixelssky.managers.ChallengesManager;
import pixelssky.objects.objectives.Objective;
import pixelssky.rewards.Reward;
import pixelssky.utils.Items;

public class Challenge implements Comparable<Challenge> {
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
	private boolean isUnlockedByDefault = false;
	
	public Challenge(int type, String name, boolean unlocked, Material m, int i){
		if(type == Challenge.TYPE_CATEGORY){
			subChallenges = new ArrayList<Challenge>();
		}else{
			ChallengesManager.number_of_challenges += 1;
			obj = new ArrayList<Objective>();
			rewards = new ArrayList<Reward>();
		}
		this.name = name;
		this.type = type;
		this.isUnlockedByDefault = unlocked;
		this.m = m;
		this.i = i;
	}
	public Challenge(int type, String name, ArrayList<Objective> objectives, ArrayList<Reward> rewards, boolean can_redo, Material m, int i, boolean unlocked){
		if(type == Challenge.TYPE_CATEGORY){
			subChallenges = new ArrayList<Challenge>();
		}else{
			ChallengesManager.number_of_challenges += 1;
			obj = objectives;
			this.rewards = rewards;
		}
		this.can_redo = can_redo;
		this.name = name;
		this.type = type;
		this.m = m;
		this.i = i;
		this.isUnlockedByDefault = unlocked;
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

	public boolean isUnlocked(Island i){
		if(i.getData("unlocked" + name) != null){
			return true;
		}else{
			return isUnlockedByDefault;
		}
	}
	@SuppressWarnings("deprecation")
	public void complete(Player p, Island i){
		if((!isCompleted(i) && isUnlocked(i)) || (isCompleted(i) && can_redo)){
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
							r.run(p,i);
						}
						p.sendTitle("§aChallenge complété !", "§2" + name,10,10,100);
						i.addOrSetData("completed"+ getName(),"" + true);
						p.playSound(p.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 100, 100);
						i.broadcastMessage("§aChallenge complété ! " + "§2" + name);
						Bukkit.broadcastMessage("§5L'île §d: " + i.getName() + " §5a complété le challenge :§a " + name);
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
						p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 100, 100);
					}
				}
			});
		}else{
			p.sendTitle("§c⚠§4§lImpossible de faire§c⚠", "§eVous ne pouvez pas (re)faire ce challenge", 10,1000,10);
			p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 100, 100);
		}
	}
	public ItemStack getItem(Island i){
		if(!isUnlocked(i)){
			return Items.get("§4Challenge / niveau bloqué ! §c" + name, Material.BARRIER);
		}
		if(type == Challenge.TYPE_CATEGORY){
			return Items.get(name, m);
		}
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
				lore.add("§7Vous ne pouvez pas refaire ce challenge§l:/");
			}
			
			ItemStack it = Items.get(this.name,m, lore); 
			it.addUnsafeEnchantment(Enchantements.VALIDATED_CHALLENGE, 1);
			return it;
		}else{
			return Items.get(this.name, m, lore);
		}
	}
	@Override
	public int compareTo(Challenge o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
    public static Comparator<Challenge> COMPARE_BY_NAME = new Comparator<Challenge>() {
        public int compare(Challenge one, Challenge other) {
            return one.name.compareTo(other.name);
        }
    };
}
