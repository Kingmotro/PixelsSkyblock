package pixelssky.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import org.bukkit.scheduler.BukkitRunnable;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;
import pixelssky.utils.DistributedRandomNumberGenerator;
import pixelssky.utils.Inventories;
import pixelssky.utils.Locations;

public class EventListener implements Listener {
	public static DistributedRandomNumberGenerator drng = new DistributedRandomNumberGenerator();
	public static ArrayList<String> tpPlayers = new ArrayList<String>();

	@EventHandler(priority = EventPriority.HIGH)
	public void loginEvent(PlayerLoginEvent event) {
		Player pl = event.getPlayer();
		SPlayer p = DatabaseManager.getPlayer(pl.getUniqueId().toString());
		PlayersManager.setPlayer(p);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void joinEvent(PlayerJoinEvent event) {
		Player pl = event.getPlayer();
		SPlayer sp = PlayersManager.getSPlayer(pl);
		try
		{
			event.setJoinMessage("§5[Ile §d" + sp.getIsland().getName() + "§5] §d" + pl.getDisplayName() + " §5s'est §aconecté(e).");
			tpPlayers.add(pl.getUniqueId().toString());
		}catch(Exception ex){
			event.setJoinMessage("§5[Ile §dSans Île Fixe§5] §d" + pl.getDisplayName() + " §5s'est §aconecté(e).");
			tpPlayers.add(pl.getUniqueId().toString());
			pl.teleport(new Location(Bukkit.getWorld("skyworld"),-20,83,-66,0,0));
		}
		
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
		}else if(event.getInventory().getName().split(":")[0].equals("§eShop ")){
			event.setCancelled(true);
			Inventories.run_getShopMenuInventory(event);
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
	public void playerTeleportEvent(PlayerTeleportEvent event){
		try
		{
			if(event.getPlayer().isOnline()){
				Player pl =  event.getPlayer();

				
				if(pl.hasPermission("pxs.instanttp") || pl.isOp() || tpPlayers.contains(pl.getUniqueId().toString())){
					pl.playSound(pl.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 100, 100);
					try{
						tpPlayers.remove(pl.getUniqueId().toString());
					}catch(Exception ex){

					}

				}else{
					tpPlayers.add(pl.getUniqueId().toString());
					pl.sendMessage("§7Téléportation dans 2 secondes... Ne bougez pas !");
					event.setCancelled(true);
					new BukkitRunnable() {
						@Override
						public void run() {
							if(pl.getLocation().getBlockX() == event.getFrom().getBlockX() && pl.getLocation().getBlockY() == event.getFrom().getBlockY()
									&& pl.getLocation().getBlockZ() == event.getFrom().getBlockZ())
							{
								pl.teleport(event.getTo());
							}else{
								pl.sendMessage("§cAnnulé :/ (on a dit \"pas bouger\" !)");
								pl.sendMessage("§7https://fr.wiktionary.org/wiki/bouger");
							}

						}
					}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 40L);
				}
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	@EventHandler
	public void onFromTo(BlockFromToEvent event){
		Material type = event.getBlock().getType();
		if (type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.LAVA || type == Material.STATIONARY_LAVA){
			Block b = event.getToBlock();
			World w = b.getWorld();
			if (b.getType() == Material.AIR){
				if (generatesCobble(type, b)){
					/* DO WHATEVER YOU NEED WITH THE COBBLE */
					Island i = Locations.getIslandAt(b.getLocation());
					double nb = drng.getDistributedRandomNumber();
					System.out.println("ITEM TIRE" + nb);
					if(i.isMaterialUnlocked(Material.OBSIDIAN) && nb == 9){
						b.setType(Material.OBSIDIAN);
						for(int k = 0; k<10; k++){
							w.playEffect(b.getLocation().subtract(k, k, k), Effect.SMOKE, k);
							w.playEffect(b.getLocation().subtract(-k, -k, -k), Effect.SMOKE, k);

							w.playEffect(b.getLocation().subtract(-k, k, k), Effect.SMOKE, k);
							w.playEffect(b.getLocation().subtract(k, k, -k), Effect.SMOKE, k);

							w.playEffect(b.getLocation().subtract(-k, -k, k), Effect.SMOKE, k);
							w.playEffect(b.getLocation().subtract(k, -k, -k), Effect.SMOKE, k);

							w.playEffect(b.getLocation().subtract(-k, k, -k), Effect.SMOKE, k);
							w.playEffect(b.getLocation().subtract(k, -k, k), Effect.SMOKE, k);
						}
						w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 100, 100);
					}else if(i.isMaterialUnlocked(Material.DIAMOND_ORE) && nb == 8){
						b.setType(Material.DIAMOND_ORE);
						for(int k = 0; k<10; k++){
							w.playEffect(b.getLocation().subtract(k, k, k), Effect.SMOKE, k);
							w.playEffect(b.getLocation().subtract(-k, -k, -k), Effect.SMOKE, k);

							w.playEffect(b.getLocation().subtract(-k, k, k), Effect.SMOKE, k);
							w.playEffect(b.getLocation().subtract(k, k, -k), Effect.SMOKE, k);

							w.playEffect(b.getLocation().subtract(-k, -k, k), Effect.SMOKE, k);
							w.playEffect(b.getLocation().subtract(k, -k, -k), Effect.SMOKE, k);

							w.playEffect(b.getLocation().subtract(-k, k, -k), Effect.SMOKE, k);
							w.playEffect(b.getLocation().subtract(k, -k, k), Effect.SMOKE, k);
						}
						w.playSound(b.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 100, 100);
					}else if(i.isMaterialUnlocked(Material.EMERALD_ORE) && nb == 7){
						b.setType(Material.EMERALD_ORE);
						w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 100, 100);
					}else if(i.isMaterialUnlocked(Material.REDSTONE_ORE) && nb == 6){
						b.setType(Material.REDSTONE_ORE);
						w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 100, 100);
					}else if(i.isMaterialUnlocked(Material.GOLD_ORE) && nb == 5){
						b.setType(Material.GOLD_ORE);
						w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BELL, 100, 100);
					}else if(i.isMaterialUnlocked(Material.IRON_ORE) && nb == 4){
						b.setType(Material.IRON_ORE);
						w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 100, 100);
						w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 100, 100);
					}else if(i.isMaterialUnlocked(Material.COAL_ORE) &&  nb == 3){
						b.setType(Material.COAL_ORE);
						w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 100, 100);
					}else if(i.isMaterialUnlocked(Material.STONE) &&  nb == 2){
						b.setType(Material.STONE);
						w.playSound(b.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 100, 100);
					}else if(nb == 1){
						b.setType(Material.COBBLESTONE);
						w.playSound(b.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 100, 100);
					}else{

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