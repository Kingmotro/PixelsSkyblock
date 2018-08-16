package pixelssky.commands;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pixelssky.managers.DatabaseManager;
import pixelssky.managers.IslandsManager;
import pixelssky.managers.PlayersManager;
import pixelssky.merchants.MerchantCategory;
import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;

public class IsAdminCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if (arg0 instanceof Player) {
			Player p = (Player) arg0;

			if (arg3.length == 0) {
				p.sendMessage("Aide admin : ");
				p.sendMessage("/pxs list : Liste des îles");
				p.sendMessage("/pxs goto [ID] ou [NOM] : Se téléporter sur l'ile avec un ID");
				p.sendMessage("/pxs shop add [CATEG] [entityType]");
				p.sendMessage("/pxs shop addlvl [name] [level]");
				p.sendMessage("/pxs shop set [CATEG] [LVL] ID QTE PRICE");
				p.sendMessage("/pxs shop save");
			} else {
				if (arg3[0].equals("list")) {
					p.sendMessage("== Liste des îles ==");
					for (Island i : IslandsManager.islands) {
						try {
							p.sendMessage(i.toString());
						} catch (Exception ex) {
							p.sendMessage("ERROR : " + ex.toString());
						}
					}
				} else if (arg3[0].equals("goto") && arg3.length > 1) {
					try {
						int ID = Integer.parseInt(arg3[1]);
						p.teleport(IslandsManager.getIsland(ID).getSpawn());
					} catch (Exception ex) {
						Island is = null;
						for (Island i : IslandsManager.islands) {
							if (i.toString().toLowerCase().contains(arg3[1].toLowerCase()))
								is = i;
						}
						if(is != null)
							p.teleport(is.getSpawn());
						else
							p.sendMessage("§cTéléportation impossible, l'île n'a pas été trouvée.");
					}

				} else if (arg3[0].equals("protection")) {
					SPlayer sp = PlayersManager.getSPlayer(p);
					sp.setProtectionOverride(!sp.getProtectionOverride());
					p.sendMessage("§5Mode admin : §a" + sp.getProtectionOverride());
				} else if (arg3[0].equals("clean")) {
					int i = 0;
					for(World w : Bukkit.getWorlds())
						for (Chunk c : w.getLoadedChunks()) {
							c.unload(true);
							i += 1;
						}
					p.sendMessage(i + " chunks déchargés");
				} else if (arg3[0].equals("ram")) {
					p.sendMessage("§5Total :§d" + Runtime.getRuntime().totalMemory());
					p.sendMessage("§5Libre :§d" + Runtime.getRuntime().freeMemory());
				} else if (arg3[0].equals("save")) {
					p.sendMessage("Sauvegarde des îles ...");
					Bukkit.getScheduler().runTaskAsynchronously(
							Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
								public void run() {
									DatabaseManager.openConnection();
									for (Island i : IslandsManager.islands) {
										DatabaseManager.updateIsland(i);
									}
									DatabaseManager.closeConnection();
								}
							});
				} else if (arg3[0].equals("shop")) {
					if (arg3[1].equals("add")) {
						MerchantCategory.add(arg3[2], p.getLocation(), arg3[3]);
					} else if (arg3[1].equals("addlvl")) {
						MerchantCategory.get(arg3[2]).addMerchant(arg3[2], Integer.parseInt(arg3[3]));
					} else if (arg3[1].equals("set")) {
						String itemID = arg3[4];
						int itemPrice = Integer.parseInt(arg3[6]);
						int qte = Integer.parseInt(arg3[5]);
						MerchantCategory.get(arg3[2]).getMerchant(Integer.parseInt(arg3[3])).addItem(itemID,
								itemPrice, qte);
					} else if (arg3[1].equals("save")) {
						MerchantCategory.save();
					}

				}
			}
		} else {
			if (arg3.length == 0) {
				System.out.println("Aide admin : ");
				System.out.println("/pxs list : Liste des îles");
				// System.out.println("/pxs goto [ID] : Se téléporter sur l'ile avec un ID");
				System.out.println("/pxs shop add [CATEG] [entityType]");
				System.out.println("/pxs shop addlvl [name] [level]");
				System.out.println("/pxs shop set [CATEG] [LVL] ID QTE PRICE");
				System.out.println("/pxs shop save");
			} else {
				if (arg3[0].equals("list")) {
					System.out.println("== Liste des îles ==");
					for (Island i : IslandsManager.islands) {
						try {
							System.out.println(i.toString());
						} catch (Exception ex) {
							System.out.println("ERROR : " + ex.toString());
						}
					}
				} else if (arg3[0].equals("goto") && arg3.length > 1) {
					System.out.println("non tu restes ici");
				} else if (arg3[0].equals("protection")) {
					System.out.println("la console est deja protégée");
				} else if (arg3[0].equals("clean")) {
					int i = 0;
					for(World w : Bukkit.getWorlds())
						for (Chunk c : w.getLoadedChunks()) {
							c.unload(true);
							i += 1;
						}
					System.out.println(i + " chunks déchargés");
				} else if (arg3[0].equals("ram")) {
					System.out.println("§5Total :§d" + Runtime.getRuntime().totalMemory());
					System.out.println("§5Libre :§d" + Runtime.getRuntime().freeMemory());
				} else if (arg3[0].equals("save")) {
					System.out.println("Sauvegarde des îles ...");
					Bukkit.getScheduler().runTaskAsynchronously(
							Bukkit.getPluginManager().getPlugin("PixelsSkyblock"), new Runnable() {
								public void run() {
									DatabaseManager.openConnection();
									for (Island i : IslandsManager.islands) {
										DatabaseManager.updateIsland(i);
									}
									DatabaseManager.closeConnection();
								}
							});
				} else if (arg3[0].equals("shop")) {
					if (arg3[1].equals("add")) {
						System.out.println("commande UNIQUEMENT disponible en jeu");
					} else if (arg3[1].equals("addlvl")) {
						MerchantCategory.get(arg3[2]).addMerchant(arg3[2], Integer.parseInt(arg3[3]));
					} else if (arg3[1].equals("set")) {
						String itemID = arg3[4];
						int itemPrice = Integer.parseInt(arg3[6]);
						int qte = Integer.parseInt(arg3[5]);
						MerchantCategory.get(arg3[2]).getMerchant(Integer.parseInt(arg3[3])).addItem(itemID,
								itemPrice, qte);
					} else if (arg3[1].equals("save")) {
						MerchantCategory.save();
					}

				}
			}
		}

		return true;
	}

}
