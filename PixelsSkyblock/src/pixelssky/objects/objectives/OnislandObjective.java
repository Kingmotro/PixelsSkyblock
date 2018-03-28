package pixelssky.objects.objectives;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.util.Countable;

import pixelssky.objects.Island;
import pixelssky.utils.WEManager;

public class OnislandObjective extends Objective{
	boolean type = false; //0: Blocks 1: entités
	int mat_ID;
	int sub_id;
	int quantity;
	String it_name;

	public OnislandObjective(boolean type, int mat_ID,int sub_id, int quantity) {
		super(Objective.ONISLAND);
		this.type = type;
		this.mat_ID = mat_ID;
		this.quantity = quantity;
		this.sub_id = sub_id;
	}
	public OnislandObjective(boolean type, String mat_ID,int sub_id, int quantity) {
		super(Objective.ONISLAND);
		this.type = type;
		this.it_name = mat_ID;
		this.quantity = quantity;
		this.sub_id = sub_id;
	}

	@Override
	public boolean check(Player p, Island i) {
		if(!type){
			List<Countable<Integer>> blocks = WEManager.count(Bukkit.getWorld("world"), i.getEdges().get(0), i.getEdges().get(1));
			for(Countable<Integer> b: blocks){
				if(b.getID() == mat_ID && b.getAmount() >= quantity){
					return true;
				}
			}
			return false;
		}else{
			try{
				
				List<Entity> es = (List<Entity>) WEManager.count_entities(Bukkit.getWorld("world"), i.getEdges().get(0), i.getEdges().get(1));
				int qte = 0;
				for(Entity e: es){
					try{
						if(e.getState().getTypeId().toString().equals(it_name)){
							qte += 1;
						}
					}catch(Exception ex){
						
					}
				}
				if(qte >= quantity){
					return true;
				}
			}catch(Exception ex){
				System.out.println(ex.toString());
			}
			
		}
		return false;
	}

	@Override
	public void run(Player p, Island i) {
		// Rien à faire : du moment que le check est validé

	}

	@Override
	public String getFailMessage(Player p) {
		// TODO Auto-generated method stub
		return "§e-▶§4" + quantity + " §citems de §4" + new ItemStack(Material.getMaterial(mat_ID),quantity,(byte) sub_id).getI18NDisplayName() + " §cest/sont requis posés sur l'île";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "§e-▶§6" + quantity + " §eitems de §6" + new ItemStack(Material.getMaterial(mat_ID),quantity,(byte) sub_id).getI18NDisplayName() + " §eposés sur l'île";
	}

}
