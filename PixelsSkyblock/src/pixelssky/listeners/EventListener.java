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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import org.bukkit.scheduler.BukkitRunnable;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.PlayersManager;
import pixelssky.merchants.MerchantCategory;
import pixelssky.objects.Data;
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
			event.setJoinMessage("§5[Ile §d" + sp.getIsland().getName() + "§5] §d" + pl.getDisplayName() + " §5s'est §aconnecté(e).");
			tpPlayers.add(pl.getUniqueId().toString());
		}catch(Exception ex){
			event.setJoinMessage("§5[Ile §dSans Île Fixe§5] §d" + pl.getDisplayName() + " §5s'est §aconnecté(e).");
			tpPlayers.add(pl.getUniqueId().toString());
			pl.teleport(new Location(Bukkit.getWorld("skyworld"),-20,83,-66,0,0));
		}
		
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void quitEvent(PlayerQuitEvent event) {
		Player pl = event.getPlayer();
		SPlayer sp = PlayersManager.getSPlayer(pl);
		new BukkitRunnable() {
			@Override
			public void run() {
				sp.saveData();
				PlayersManager.players.remove(sp);
			}
		}.runTaskLater(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), 40L);
		try
		{
			event.setQuitMessage("§5[Ile §d" + sp.getIsland().getName() + "§5] §d" + pl.getDisplayName() + " §5s'est §cdéconnecté(e).");
			
			
		}catch(Exception ex){
			event.setQuitMessage("§5[Ile §dSans Île Fixe§5] §d" + pl.getDisplayName() + " §5s'est §adéconnecté(e).");
		}
		
		
	}

	@EventHandler
	public void invEvent(InventoryClickEvent event){
		try{
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
			}else if(event.getInventory().getName().equals("§6Liste des îles")){
				event.setCancelled(true);
				Inventories.run_IslandList(event);
			}else if(event.getInventory().getName().equals("§6Challenges !")){
				event.setCancelled(true);
				Inventories.run_challengesMainInventory(event);
			}else if(event.getInventory().getName().split(":")[0].equals("§6Challenges du niveau ")){
				event.setCancelled(true);
				Inventories.run_SubChallengesInventory(event);
			}else if(event.getInventory().getName().split(":")[0].equals("§5Shop ")){
				event.setCancelled(true);
				String categ = event.getInventory().getName().split(":")[1];

				MerchantCategory.get(categ).runInventory(event);
			}else if(event.getInventory().getName().split(":")[0].equals("§eChanger de biome")){
				event.setCancelled(true);
				Inventories.run_getBiomeMenu(event);
			}else if(event.getInventory().getName().equals("§eValeur des blocs :")){
				event.setCancelled(true);
				Inventories.run_getBlockValues(event);
			}
		}catch(Exception ex){
			
		}
		
	}

	@EventHandler
	public void interactEvent(PlayerInteractEvent event){
		Player p = event.getPlayer();
		try
		{
			Island is = Locations.getIslandAt(event.getClickedBlock().getLocation());
			if(p.getWorld().getName().equals("world")) {
				//protection d'ile
				SPlayer sp = PlayersManager.getSPlayer(p);
				if(!is.getMembers().contains(PlayersManager.getSPlayer(p).getID()) && !sp.getProtectionOverride()){
					event.setCancelled(true);
					p.sendTitle("§c⚠§4§lAccès refusé§c⚠", "§eVous ne faites pas partie de cette île", 10,25,10);
					p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
				//clic droit sur obsi avec 1 seau 
				} else if(p.getItemInHand().getType() == Material.BUCKET && p.getItemInHand().getAmount() == 1 && event.getClickedBlock().getType() == Material.OBSIDIAN) {
					event.setCancelled(true);
					event.getClickedBlock().setType(Material.AIR);
					p.getItemInHand().setType(Material.LAVA_BUCKET);
				}
			}
		}catch(Exception ex){
			try{
				//Si clic dans le vide
				if(p.getWorld().getName().equals("world")){
					event.getClickedBlock().getLocation(); //-> Go Catch
					//Si pas d'ile
					event.setCancelled(true);
					p.sendTitle("§c⚠§4§lAccès refusé§c⚠", "§eVous ne faites pas partie de cette île", 10,25,10);
					p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
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
					pl.playSound(pl.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1, 1);
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
								tpPlayers.remove(pl.getUniqueId().toString());
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

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFromTo(BlockFromToEvent event){
		Material type = event.getBlock().getType();
		if (type == Material.WATER || type == Material.LAVA){
			Block b = event.getToBlock();
			World w = b.getWorld();
			if (b.getType() == Material.AIR){
				if (generatesCobble(type, b)){
					/* DO WHATEVER YOU NEED WITH THE COBBLE */
					Bukkit.getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
						public void run() {
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
								w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
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
								w.playSound(b.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
							}else if(i.isMaterialUnlocked(Material.EMERALD_ORE) && nb == 7){
								b.setType(Material.EMERALD_ORE);
								w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
							}else if(i.isMaterialUnlocked(Material.REDSTONE_ORE) && nb == 6){
								b.setType(Material.REDSTONE_ORE);
								w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
							}else if(i.isMaterialUnlocked(Material.GOLD_ORE) && nb == 5){
								b.setType(Material.GOLD_ORE);
								w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
							}else if(i.isMaterialUnlocked(Material.IRON_ORE) && nb == 4){
								b.setType(Material.IRON_ORE);
								w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
								w.playSound(b.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 1);
							}else if(i.isMaterialUnlocked(Material.COAL_ORE) &&  nb == 3){
								b.setType(Material.COAL_ORE);
								w.playSound(b.getLocation(), Sound.BLOCK_PISTON_EXTEND, 1, 1);
							}else if(i.isMaterialUnlocked(Material.STONE) &&  nb == 2){
								b.setType(Material.STONE);
								w.playSound(b.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
							}else if(nb == 1){
								b.setType(Material.COBBLESTONE);
								w.playSound(b.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 1);
							}else{
		
							}
						}
					}, 1);
				}
			}
		}
	}
	

	@EventHandler(priority = EventPriority.HIGH)
	public void placedBlock(BlockPlaceEvent event) {
		try{
			SPlayer sp = PlayersManager.getSPlayer(event.getPlayer());
			Island i = sp.getIsland();
			if(i != null){
				Data d = i.getData(Data.PLACED_BLOCKS + ":" + event.getBlock().getType().toString());
				if(d != null){
					d.add(1);
				}else{
					i.addOrSetData(Data.PLACED_BLOCKS + ":" +  event.getBlock().getType().toString(), 1);
				}
			}
		}catch(Exception ex){
			
		}
			
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void brokenBlock(BlockBreakEvent event) {
		try{
			SPlayer sp = PlayersManager.getSPlayer(event.getPlayer());
			Island i = sp.getIsland();
			if(i != null){
				Data d = i.getData(Data.BROKEN_BLOCKS + ":" + event.getBlock().getType().toString());
				if(d != null){
					d.add(1);
				}else{
					i.addOrSetData(Data.BROKEN_BLOCKS + ":" +  event.getBlock().getType().toString(), 1);
				}
			}
		}catch(Exception ex){
			
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
		Material mirrorID1 = (type == Material.WATER || type == Material.WATER ? Material.LAVA : Material.WATER);
		Material mirrorID2 = (type == Material.WATER || type == Material.WATER ? Material.LAVA : Material.WATER);
		for (BlockFace face : faces){
			Block r = b.getRelative(face, 1);
			if (r.getType() == mirrorID1 || r.getType() == mirrorID2){
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	 public void onMove(PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();
		
		if(from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
			Player p = event.getPlayer();
			SPlayer sp =  PlayersManager.getSPlayer(p);
			if(sp.isAfk()) {
				sp.setAfk(false, p, null);
			}
		}
	 }
}
