package pixelssky.objects.objectives;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.util.Countable;
import com.sk89q.worldedit.world.block.BlockType;

import pixelssky.objects.Island;
import pixelssky.utils.WEManager;

public class OnislandObjective extends Objective{
	boolean type = false; //0: Blocks 1: entités
	Material material;
	int quantity;
	String it_name;
	int calculated_nb;

	public OnislandObjective(boolean type, String mat_ID, int quantity) {
		super(Objective.ONISLAND);
		this.type = type;
		if(!type)
			this.material = Material.getMaterial(mat_ID);
		this.it_name = mat_ID;
		this.quantity = quantity;
	}

	@Override
	public boolean check(Player p, Island i) {
		if(!type){
			List<Countable<BlockType>> blocks = WEManager.count(Bukkit.getWorld("world"), i.getEdges().get(0), i.getEdges().get(1));
			for(Countable<BlockType> b: blocks){
				try{
				String matName = b.getID().getItemType().getId().toUpperCase().replaceAll(" ", "_").replaceAll("MINECRAFT:", "");
				if(Material.getMaterial(matName).equals(material) && b.getAmount() >= quantity){
					return true;
				}else if(Material.getMaterial(matName).equals(material)){
					calculated_nb = b.getAmount();
				}}catch(Exception ex){
					
				}
			}
			return false;
		}else{
			try{

				List<Entity> es = (List<Entity>) WEManager.count_entities(Bukkit.getWorld("world"), i.getEdges().get(0), i.getEdges().get(1));
				int qte = 0;
				for(Entity e: es){
					try{
						if(e.getState().getType().toString().equalsIgnoreCase(it_name)){
							qte += 1;
						}
					}catch(Exception ex){

					}
				}
				calculated_nb = qte;
				if(qte >= quantity){
					return true;
				}
			}catch(Exception ex){
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
		if(type){
			return "§e-▶§6" + calculated_nb + "/§l" + quantity + " §eentité(s) de §6" + it_name + " §cest/sont posée(s) sur l'île";

		}else{
			if( !(new ItemStack(material, quantity).toString()).equals("Air"))
				return "§e-▶§6" + calculated_nb + "/§l" + quantity + " §eitems de §6" + material.toString() + " §cest/sont posé(s) sur l'île";
			else
				return "§e-▶§6" + calculated_nb + "/§l" + quantity + " §eitems de §6" + material.name() + " §cest/sont posé(s) sur l'île";

		}
	}

	@Override
	public String getDescription() {
		if(type) return "§e-▶§6" + quantity + " §eentités de §6" + it_name + " §eposée(s) sur l'île";
		if(new ItemStack(material, quantity).toString().equals("Air"))
			return "§e-▶§6" + quantity + " §eitems de §6" + material.toString() + " §eposé(s) sur l'île";
		return "§e-▶§6" + quantity + " §eitems de §6" + material.toString() + " §eposé(s) sur l'île";

	}


}
