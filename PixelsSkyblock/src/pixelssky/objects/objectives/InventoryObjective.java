package pixelssky.objects.objectives;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pixelssky.objects.Island;

public class InventoryObjective extends Objective{
	int itemID;
	int itemSubID;
	int quantity;
	boolean take;
	int current_qte = 0;



	public InventoryObjective(int itemID, int itemSubID, int quantity, boolean take){
		super(Objective.ONISLAND);
		this.itemID = itemID;
		this.itemSubID = itemSubID;
		this.quantity = quantity;
		this.take = take;
	}

	@Override
	public boolean check(Player p, Island i) {
		return ( p.getInventory().containsAtLeast(new ItemStack(Material.getMaterial(itemID),quantity,(byte) itemSubID),quantity));
	}

	@Override
	public void run(Player p, Island i) {
		if(take){
			p.getInventory().removeItem(new ItemStack(Material.getMaterial(itemID),quantity,(byte) itemSubID));
		}
	}

	@Override
	public String getFailMessage(Player p) {
		return "§e-▶§4" + quantity + " §citems de §4" + new ItemStack(Material.getMaterial(itemID),quantity,(byte) itemSubID).getI18NDisplayName() + " §cest/sont requis";

	}

}
