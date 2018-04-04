package pixelssky.main;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Inventories;
import pixelssky.utils.Locations;

public class EventListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void loginEvent(PlayerLoginEvent event) {
		Player pl = event.getPlayer();
		SPlayer p = DatabaseManager.getPlayer(pl.getUniqueId().toString());
		PlayersManager.setPlayer(p);
		p.addOrSetData("Donnee 1", "3842");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void joinEvent(PlayerJoinEvent event) {
		Player pl = event.getPlayer();
		SPlayer sp = PlayersManager.getSPlayer(pl);
		event.setJoinMessage("§5[Ile §d" + sp.getIsland().getName() + "§5] §d" + pl.getDisplayName() + " §5s'est §aconecté(e).");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void quitEvent(PlayerQuitEvent event) {
		Player pl = event.getPlayer();
		SPlayer sp = PlayersManager.getSPlayer(pl);
		event.setQuitMessage("§5[Ile §d" + sp.getIsland().getName() + "§5] §d" + pl.getDisplayName() + " §5s'est §cdéconecté(e).");
		sp.saveData();
		PlayersManager.removePlayer(pl);
	}

	@EventHandler
	public void invEvent(InventoryClickEvent event){
		if(event.getInventory().getName().equals("§6✚ §3Créer une nouvelle île")){
			event.setCancelled(true);
			Inventories.run_createIslandMenu(event);
		}else if(event.getInventory().getName().equals("§6☰ §3Menu de l'île")){
			event.setCancelled(true);
			Inventories.run_IslandMenu(event);
		}else if(event.getInventory().getName().equals("§6✉ §3Inviter des joueurs")){
			event.setCancelled(true);
			Inventories.run_PlayersInventory_invite(event);
		}
		else if(event.getInventory().getName().equals("§6❔ §3Recommencer une île ?")){
			event.setCancelled(true);
			Inventories.run_ConfirmCreateIsland(event);
		}else if(event.getInventory().getName().equals("§6§3Liste des îles")){
			event.setCancelled(true);
			Inventories.run_IslandList(event);
		}else if(event.getInventory().getName().equals("§6§3Challenges !")){
			event.setCancelled(true);
			Inventories.run_challengesMainInventory(event);
		}else if(event.getInventory().getName().split(":")[0].equals("§6§3Challenges du niveau ")){
			event.setCancelled(true);
			Inventories.run_SubChallengesInventory(event);
		}
	}

	@EventHandler
	public void interactEvent(PlayerInteractEvent event){
		Player p = event.getPlayer();
		try
		{
			Island is = Locations.getIslandAt(event.getClickedBlock().getLocation());
			if(!is.getMembers().contains(PlayersManager.getSPlayer(p).getID()) && p.getWorld().getName().equals("world")){
				event.setCancelled(true);
				p.sendTitle("§c⚠§4§lAccès refusé§c⚠", "§eVous ne faites pas partie de cette île", 10,25,10);
				p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 100, 100);
			}
		}catch(Exception ex){
			try{
				//Si clic dans le vide
				if(p.getWorld().getName().equals("world")){
					event.getClickedBlock().getLocation(); //-> Go Catch
					//Si pas d'ile
					event.setCancelled(true);
					p.sendTitle("§c⚠§4§lAccès refusé§c⚠", "§eVous ne faites pas partie de cette île", 10,25,10);
					p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 100, 100);
				}
			}catch(Exception ex2){

			}
		}
	}
	@EventHandler
	public void playerChatEvent(AsyncPlayerChatEvent event){
		Player pl = event.getPlayer();
		SPlayer p = PlayersManager.getSPlayer(pl);
		event.setMessage(event.getMessage().replaceAll("%", "%%"));
		if(event.getMessage().contains("~")){
			event.setMessage("§5§lDUDULLLE EST UN DIEU !");
			pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_SCREAM, 100, 100);
		}
		for(Player player: Bukkit.getOnlinePlayers()){
			if(event.getMessage().contains(player.getDisplayName())){
				player.playSound(pl.getLocation(), Sound.BLOCK_NOTE_BELL, 100, 100);
				player.sendTitle("§aRegardez le chat !","§d" + pl.getDisplayName() + " vous appelle !",10,10,100);
			}
		}
		event.setFormat("§5[Ile §d§l" + p.getIsland().getName() + "§5] §7"+ pl.getDisplayName() + " §d: §f" + event.getMessage());
	}

	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event){
		Player pl =  event.getEntity();
		SPlayer p = PlayersManager.getSPlayer(pl);
		if(p.getIsland() == null){
			event.setDeathMessage("§7☠ " + pl.getDisplayName() + " §la voulu défier la mort ... sans succès.");
		}else{
			event.setDeathMessage("§7☠ " + pl.getDisplayName() + " §la voulu défier la mort ... sans succès.");
			Island i = p.getIsland();
			if(i.getData("deaths") == null){
				event.setDeathMessage("§7☠ " + pl.getDisplayName() + " §lest mort(e) pour la 1ere fois !");
				i.addOrSetData("deaths", "1");
			}else{
				i.getData("deaths").add(1);
			}
			if(Double.parseDouble(i.getData("deaths").getData().toString()) >= 2){
				pl.sendMessage("§cVous n'avez plus de joker :/");
			}else{
				pl.sendMessage("§aOUF ! Joker ! lvous gardez votre stuff !");
				event.setKeepInventory(true);
				event.setKeepLevel(true);
			}
		}
	}

	@EventHandler
	public void playerRespawnEvent(PlayerRespawnEvent event){
		Player pl =  event.getPlayer();
		SPlayer p = PlayersManager.getSPlayer(pl);
		if(p.getIsland() == null){
			event.setRespawnLocation(Bukkit.getServer().getWorld("skyworld").getSpawnLocation());
		}else{
			event.setRespawnLocation(p.getIsland().getSpawn());
		}
	}

	@EventHandler
	public void playerInteractEntityEvent(PlayerInteractEntityEvent event){
		Player pl =  event.getPlayer();
		SPlayer p = PlayersManager.getSPlayer(pl);
		pl.sendMessage(event.getRightClicked().getName());
	}
	
	@EventHandler
	public void onFromTo(BlockFromToEvent event){
	    Material type = event.getBlock().getType();
	    if (type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.LAVA || type == Material.STATIONARY_LAVA){
	        Block b = event.getToBlock();
	        if (b.getType() == Material.AIR){
	            if (generatesCobble(type, b)){
	                /* DO WHATEVER YOU NEED WITH THE COBBLE */
	            	ArrayList<Material> m = new ArrayList<Material>();
	            	m.add(Material.COBBLESTONE);
	            	m.add(Material.GOLD_ORE);
	            	m.add(Material.COAL_ORE);
	            	m.add(Material.DIAMOND_ORE);
	            	m.add(Material.EMERALD_ORE);
	            	m.add(Material.REDSTONE_ORE);
	            	m.add(Material.OBSIDIAN);
	            	m.add(Material.IRON_ORE);
	            	Random r = new Random();
	            	int nb = r.nextInt(1000);
	            	Island i = Locations.getIslandAt(b.getLocation());
	            	if(i != null){
	            		double lvl = i.getLevel();
	            		if(nb + lvl < 500){
	            			b.setType(m.get(0));
	            		}else if(nb + lvl < 700){
	            			b.setType(m.get(2));
	            		}else if(nb + lvl < 750){
	            			b.setType(m.get(7));
	            		}else if(nb + lvl < 800){
	            			b.setType(m.get(1));
	            		}else if(nb + lvl < 825){
	            			b.setType(m.get(3));
	            		}else if(nb + lvl < 875){
	            			b.setType(m.get(5));
	            		}else if(nb + lvl < 900){
	            			b.setType(m.get(6));
	            		}else{
	            			b.setType(m.get(4));
	            		}
	            	}
	            	
	            }
	        }
	    }
	}

	private final BlockFace[] faces = new BlockFace[]{
	        BlockFace.SELF,
	        BlockFace.UP,
	        BlockFace.DOWN,
	        BlockFace.NORTH,
	        BlockFace.EAST,
	        BlockFace.SOUTH,
	        BlockFace.WEST
	    };

	public boolean generatesCobble(Material type, Block b){
	    Material mirrorID1 = (type == Material.WATER || type == Material.STATIONARY_WATER ? Material.LAVA : Material.WATER);
	    Material mirrorID2 = (type == Material.WATER || type == Material.STATIONARY_WATER ? Material.STATIONARY_LAVA : Material.STATIONARY_WATER);
	    for (BlockFace face : faces){
	        Block r = b.getRelative(face, 1);
	        if (r.getType() == mirrorID1 || r.getType() == mirrorID2){
	            return true;
	        }
	    }
	    return false;
	}
}
