package pixelssky.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pixelssky.objects.Island;

public class InventoryObjective extends Objective{
	Material material;
	int quantity;
	boolean take;
	int current_qte = 0;



	public InventoryObjective(Material m, int quantity, boolean take){
		super(Objective.ONISLAND);
		this.material = m;
		this.quantity = quantity;
		this.take = take;
	}

	@Override
	public boolean check(Player p, Island i) {
		return ( p.getInventory().containsAtLeast(new ItemStack(material, quantity),quantity));
	}

	@Override
	public void run(Player p, Island i) {
		if(take){
			p.getInventory().removeItem(new ItemStack(material, quantity));
		}
	}

	@Override
	public String getFailMessage(Player p) {
		return "§e-▶§4" + quantity + " §citems de §4" + material.toString() + " §cest/sont requis";

	}

	@Override
	public String getDescription() {
		return "§e-▶§4" + quantity + " §citems de §4" + material.toString();
	}

}
