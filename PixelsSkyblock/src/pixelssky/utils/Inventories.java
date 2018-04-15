package pixelssky.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import pixelssky.managers.ChallengesManager;
import pixelssky.managers.DatabaseManager;
import pixelssky.managers.IslandsManager;
import pixelssky.managers.PlayersManager;
import pixelssky.objects.Challenge;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;

public class Inventories {

	public static Inventory getCreateIslandMenu(SPlayer p){
		Inventory inv = Bukkit.createInventory(null, 9, "§6✚ §3Créer une nouvelle île");

		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§a§l Ressources : §b★§7☆☆");
		lore.add("§a§l Niveau :       §b★§7☆☆");
		lore.add("§a§l Difficulté :    §b★★★");

		inv.setItem(1 , Items.get("§5§lÎle ultra Hard", Material.DIRT,(byte) 0, lore));

		lore = new ArrayList<String>();
		lore.add("§a§l Ressources : §b★★§7☆");
		lore.add("§a§l Niveau :       §b★§7☆☆");
		lore.add("§a§l Difficulté :    §b★★§7☆");
		lore.add("§cSi vous prennez cette île vous serez");
		lore.add("§cclassé derrière les îles ultra hard");

		inv.setItem(2 , Items.get("§5§lÎle de base", Material.APPLE,(byte) 0, lore));

		lore = new ArrayList<String>();
		lore.add("§a§l Ressources : §b★★★");
		lore.add("§a§l Niveau :       §b★★§7☆");
		lore.add("§a§l Difficulté :    §b★§7☆☆");
		lore.add("§cSi vous prennez cette île vous serez");
		lore.add("§cclassé derrière les îles de base");

		inv.setItem(3 , Items.get("§5§lÎle facile", Material.GOLD_INGOT,(byte) 0, lore));

		lore = new ArrayList<String>();
		lore.add("§a§l Ressources : §b★★★");
		lore.add("§a§l Niveau :       §b★★★");
		lore.add("§a§l Difficulté :    §7☆☆☆");
		lore.add("§cSi vous prennez cette île vous serez");
		lore.add("§4§l PAS §cclassé !!");

		inv.setItem(4 , Items.get("§5§lÎle d'exemple", Material.DIAMOND,(byte) 0, lore));

		inv.setItem(8 , Items.get("§6Voir les îles existantes", Material.ANVIL,(byte) 0));

		return inv;
	}

	public static void run_createIslandMenu(InventoryClickEvent event){
		try{

			//Nouvelle île
			int slot = event.getSlot();
			Player pl = (Player) event.getWhoClicked();
			SPlayer p = PlayersManager.getSPlayer((Player) event.getWhoClicked());
			if(slot == 8){
				pl.openInventory(getIslandsList(p));
			}
			Bukkit.getScheduler().runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
				@Override
				public void run() {
					if(slot <4 && slot > 0){
						if(p.getIsland() != null){
							DatabaseManager.deleteIsland(p.getIsland());
							pl.getInventory().clear();
							pl.getEnderChest().clear();
						}
						DatabaseManager.createIsland(p);
						p.getIsland().addOrSetData("Creator", pl.getDisplayName());
						p.getIsland().addOrSetData("admins", p.getID() + ",");
						if(slot == 1){
							p.getIsland().addOrSetData("difficulty", "HARD");
							try {
								WEManager.pasteSchematics(Bukkit.getWorld("world"), new File("plugins/PixelsSky/Schematics/hard.island"), p.getIsland().getCenter());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(slot == 2){
							p.getIsland().addOrSetData("difficulty", "NORMAL");
							try {
								WEManager.pasteSchematics(Bukkit.getWorld("world"), new File("plugins/PixelsSky/Schematics/normal.island"), p.getIsland().getCenter());
							}catch (Exception e) {
								e.printStackTrace();
							}
						}else if(slot == 3){
							p.getIsland().addOrSetData("difficulty", "EASY");
							try {
								WEManager.pasteSchematics(Bukkit.getWorld("world"), new File("plugins/PixelsSky/Schematics/easy.island"), p.getIsland().getCenter());
							} catch (Exception e) {
								e.printStackTrace();
							}

						}else if(slot == 4){
							p.getIsland().addOrSetData("difficulty", "NONE");
							try {
								WEManager.pasteSchematics(Bukkit.getWorld("world"), new File("plugins/PixelsSky/Schematics/none.island"), p.getIsland().getCenter());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}


					}
				}});

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PixelsSky"), new Runnable() {
				public void run() {
					pl.sendTitle("§aBienvenue sur votre île :)", "§cNe tombez pas !", 10,20,10);
					pl.teleport(p.getIsland().getSpawn());
					pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 100, 100);
					pl.closeInventory();
				}
			}, 60L);
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
	}

	public static Inventory getIslandMenu(SPlayer p){
		Inventory inv = Bukkit.createInventory(null, 9*3, "§6☰ §3Menu de l'île");
		boolean isAdmin = p.getIsland().isAdmin(p.getID());

		inv.setItem(0 , Items.get("§5§lTéléporation sur l'île", Material.BIRCH_DOOR_ITEM,(byte) 0));
		inv.setItem(1 , Items.get("§5§lSpawn du monde", Material.NETHER_STAR,(byte) 0));
		inv.setItem(2 , Items.get("§5§lNiveau de l'île", Material.EXP_BOTTLE,(byte) 0));
		inv.setItem(3 , Items.get("§5§lChallenges de l'île", Material.BOOK_AND_QUILL,(byte) 0));

		inv.setItem(6 , Items.get("§5§lValeur des blocs", Material.EMERALD,(byte) 0));
		inv.setItem(7 , Items.get("§5§lListe des îles", Material.ANVIL,(byte) 0));
		inv.setItem(8 , Items.get("§5§lInformations de l'île", Material.EMPTY_MAP,(byte) 0));


		if(isAdmin){
			inv.setItem(9 , Items.get("§5§lChanger le spawn de l'île", Material.BED,(byte) 5));
			inv.setItem(11 , Items.getHead("Mailbox","§5§lInviter un joueur"));
			inv.setItem(12 , Items.getHead("Computer","§5§lAjouter un admin"));
			inv.setItem(13 , Items.getHead("Barrier","§5§lSupprimer un admin"));
			inv.setItem(14 , Items.getHead("X","§5§lExpulser un joueur de l'île"));

			inv.setItem(16 , Items.get("§5§lProgression", Material.DIAMOND_PICKAXE,(byte) 0));

			inv.setItem(20 , Items.get("§5§lChanger le biome de l'île", Material.SAPLING,(byte) 0));
			inv.setItem(21 , Items.get("§5§lVoir éléments débloqués", Material.WORKBENCH,(byte) 0));
			inv.setItem(24 , Items.get("§5§lAjouter un membre externe à l'île", Material.CHEST,(byte) 0));
			inv.setItem(25 , Items.get("§5§lSupprimer un membre externe", Material.APPLE,(byte) 0));
		}

		return inv;
	}
	public static void run_IslandMenu(InventoryClickEvent event){
		Player pl = (Player) event.getWhoClicked();
		SPlayer p = PlayersManager.getSPlayer(pl);
		boolean isAdmin = p.getIsland().isAdmin(p.getID());
		if(event.getSlot()==0){
			pl.sendTitle("§aBienvenue sur votre île :)", "§cNe tombez pas !", 10,20,10);
			pl.teleport(p.getIsland().getSpawn());
			pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 100, 100);
			pl.closeInventory();
		}else if(event.getSlot()==1){
			pl.sendTitle("§aTéléportation au spawn", "", 10,20,10);
			pl.teleport(Bukkit.getServer().getWorld("skyworld").getSpawnLocation());
			pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 100, 100);
			pl.closeInventory();
		}else if(event.getSlot()==2){
			p.getIsland().calculateLevel(pl);
			pl.closeInventory();
		}else if(event.getSlot()==3){
			pl.openInventory(getChallengesMainInventory(p.getIsland()));
		}else if(event.getSlot()==6){
			//Valeurs des blocks
		}else if(event.getSlot()==7){
			pl.playSound(pl.getLocation(), Sound.BLOCK_NOTE_GUITAR, 100, 100);
			pl.openInventory(getIslandsList(p));
		}else if(event.getSlot()==8){
			//Infos de l'île
		} else if(event.getSlot()==9 && isAdmin){
			if(Locations.getIslandAt(pl.getLocation()) == p.getIsland()){
				pl.sendTitle("§aMise à jour effectuée :)", "§cVotre home a changé !", 10,20,10);
				p.getIsland().setHome(pl.getLocation());
				pl.playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 100);
				pl.closeInventory();
			}else{
				pl.sendTitle("§aMise à jour §cratée :(", "§cVous n'êtes pas sur votre île :/", 10,20,10);
				pl.playSound(pl.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 100, 100);
				pl.closeInventory();
			}
		}else if(event.getSlot()==12 && isAdmin){
			pl.openInventory(getAddAdminInventory(p, p.getIsland()));
		}else if(event.getSlot()==11 && isAdmin){
			pl.openInventory(getPlayersInventory_invite(p));
		}else if(event.getSlot()==13 && isAdmin){
			//Suppr admin
		}else if(event.getSlot()==14 && isAdmin){
			//Virer joueur
		}else if(event.getSlot()==16 && isAdmin){
			//Calcul progression
		}else if(event.getSlot()==20 && isAdmin){
			//Biome
		}else if(event.getSlot()==21 && isAdmin){
			//2léments débloqués
		}else if(event.getSlot()==24 && isAdmin){
			//Ajouter externe
		}else if(event.getSlot()==25 && isAdmin){
			//Suppr externe
		}
	}

	public static Inventory getPlayersInventory_invite(SPlayer pl){
		Island i = pl.getIsland();
		Inventory inv = Bukkit.createInventory(null, ((Bukkit.getOnlinePlayers().size())/9+1)*9, "§6✉ §3Inviter des joueurs");
		for(Player p : Bukkit.getOnlinePlayers()){
			if(!i.getMembers().contains(PlayersManager.getSPlayer(p).getID())){
				inv.addItem(Items.getHead(p));
			}
		}
		return inv;
	}
	public static void run_PlayersInventory_invite(InventoryClickEvent event){
		try {
			Player pl = (Player) event.getWhoClicked();
			SkullMeta skull = (SkullMeta) event.getInventory().getItem(event.getSlot()).getItemMeta();
			Player cible = Bukkit.getPlayer(skull.getOwner());
			SPlayer p_sender = PlayersManager.getSPlayer(pl);
			SPlayer p_cible = PlayersManager.getSPlayer(cible);
			p_cible.setLastIsInvite(p_sender.getIsland().getID());
			cible.sendTitle("§a" + pl.getDisplayName() + " vous veut sur son île!", "§eFaites /is accept pour accepter !", 10,20,10);
		}catch(Exception ex){

		}
	}

	public static Inventory getConfirmCreateIsland(){
		Inventory inv = Bukkit.createInventory(null, 9, "§6❔ §3Recommencer une île ?");
		inv.setItem(2 , Items.get("§cSupprimer mon île actuelle.", Material.WOOL,(byte) 14));
		inv.setItem(6 , Items.get("§aAnnuler.", Material.WOOL,(byte) 4));
		return inv;
	}
	public static void run_ConfirmCreateIsland(InventoryClickEvent event){
		int slot = event.getSlot();
		Player p = (Player) event.getWhoClicked();
		if(slot == 2){
			p.openInventory(getCreateIslandMenu(PlayersManager.getSPlayer(p)));
		}else if(slot == 6){
			p.closeInventory();
		}
	}

	public static Inventory getIslandsList(SPlayer p){
		Inventory inv = Bukkit.createInventory(null, ((IslandsManager.islands.size())/9+1)*9, "§6§3Liste des îles");
		for(Island i: IslandsManager.islands){
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§e§nNiveau de l'île :§b "+ i.getLevel());
			lore.add("§e§nNombre de membres :§b " + i.getMembers().size());
			lore.add("§e§nDifficulté :§b " + i.getData("difficulty").getData());
			if(p.getIsland() == i){
				inv.addItem(Items.get("§5§l▶Votre île", Material.STAINED_CLAY,(byte) new Random().nextInt(15), lore));
			}else{
				inv.addItem(Items.get("§5§lIle §a" + i.getName(), Material.STAINED_CLAY,(byte) new Random().nextInt(15), lore));
			}
		}
		return inv;
	}
	public static void run_IslandList(InventoryClickEvent event){

	}

	public static Inventory getChallengesMainInventory(Island i){
		Inventory inv = Bukkit.createInventory(null, ((ChallengesManager.challenges.size())/9+1)*9, "§6§3Challenges !");
		for(Challenge c : ChallengesManager.challenges){
			inv.addItem(c.getItem(i));
		}
		return inv;
	}
	public static void run_challengesMainInventory(InventoryClickEvent event){
		Player p = (Player) event.getWhoClicked();
		SPlayer sp = PlayersManager.getSPlayer(p);
		ItemStack i = event.getClickedInventory().getItem(event.getSlot());
		if(i != null){
			p.openInventory(getSubChallengeInventory(ChallengesManager.getChallenge(i.getI18NDisplayName()),sp.getIsland()));
		}
	}
	public static Inventory getSubChallengeInventory(Challenge ch, Island i){
		Inventory inv = Bukkit.createInventory(null, ((ch.getSubChallenges().size())/9+1)*9, "§6§3Challenges du niveau :" + ch.getName());
		for(Challenge c : ch.getSubChallenges()){
			inv.addItem(c.getItem(i));
		}
		return inv;
	}

	public static void run_SubChallengesInventory(InventoryClickEvent event){
		Player p = (Player) event.getWhoClicked();
		SPlayer sp = PlayersManager.getSPlayer(p);
		ItemStack i = event.getClickedInventory().getItem(event.getSlot());
		if(i != null){
			try{
				ChallengesManager.getChallenge(event.getInventory().getName().split(":")[1]).getSubChallenges().get(event.getSlot()).complete(p, sp.getIsland());
				p.closeInventory();
			}catch(Exception ex){

			}

		}
	}
	public static Inventory getAddAdminInventory(SPlayer sp, Island i){
		Inventory inv = Bukkit.createInventory(null, ((i.getMembers().size())/9+1)*9, "§6✉ §3Ajouter un admin");
		for(Player p : Bukkit.getOnlinePlayers()){
			if(i.getMembers().contains(PlayersManager.getSPlayer(p).getID()) && !i.isAdmin(PlayersManager.getSPlayer(p).getID())){
				inv.addItem(Items.getHead(p));
			}
		}
		return inv;
	}
	public static void run_getAddAdminInventory(InventoryClickEvent event){
		try {
			Player pl = (Player) event.getWhoClicked();
			SkullMeta skull = (SkullMeta) event.getInventory().getItem(event.getSlot()).getItemMeta();
			Player cible = Bukkit.getPlayer(skull.getOwner());
			SPlayer p_sender = PlayersManager.getSPlayer(pl);
			SPlayer p_cible = PlayersManager.getSPlayer(cible);
			if(p_sender.getIsland().getData("admins") != null){
				p_sender.getIsland().addOrSetData("admins", p_sender.getIsland().getData("admins").getData().toString() + p_cible.getID() + ",");
			}else{
				p_sender.getIsland().addOrSetData("admins", p_cible.getID() + ",");
			}

		}catch(Exception ex){

		}
	}

	public static Inventory getShopMenuInventory(String shopCateg, SPlayer sp){
		Inventory inv = Bukkit.createInventory(null, 9, "§eShop :" + shopCateg);
		for(int i =0; i < 10 ; i++){
			try{
				inv.addItem(Items.get("§5§l▶Niveau :" + (i + 1), sp.getIsland().getMerchantInventory(i + 1, shopCateg).getItemMenu(shopCateg), (byte) 0));
			}catch(Exception ex){
				inv.addItem(Items.get("§c§l▶Niveau bloqué :" + (i + 1), Material.BARRIER, (byte) 0));
			}
		}
		return inv;
	}
	public static void run_getShopMenuInventory(InventoryClickEvent event){
		try {
			Player pl = (Player) event.getWhoClicked();
			SPlayer p = PlayersManager.getSPlayer(pl);
			String c = event.getClickedInventory().getName().split(":")[1];
			int lvl = event.getSlot() + 1;
			if(p.getIsland().isMerchantUnlocked(lvl, c)){
				pl.openMerchant(p.getIsland().getMerchant(lvl, c), true);
			}else{
				pl.openInventory(getShopMenuInventory(c, p));
			}
		}catch(Exception ex){

		}
	}

}
