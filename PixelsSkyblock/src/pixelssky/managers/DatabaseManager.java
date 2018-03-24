package pixelssky.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pixelssky.objects.Data;
import pixelssky.objects.Island;
import pixelssky.objects.Right;
import pixelssky.objects.SPlayer;
import pixelssky.utils.Locations;

public class DatabaseManager {

	/*
	 * TODO : Créer le système de BDD Prendre en charge les données des joueurs
	 * ainsi que des îles Toutes les données doivent être stockées intelligement Les
	 * requêtes doivent être éxecutées de façon optimisée Le mot de passe de la BDD
	 * sera stocké dans un fichier de config (plugins/PixelsSkyblock/config.txt.
	 */

	/*
	 * Structure de la BDD : Tables : Joueurs PLAYERS : ID : auto increment; UUID :
	 * text; ISLAND_ID: int; Données joueurs PLAYER_DATA : ID : auto increment;
	 * PLAYER_ID : int; DATA_NAME : text; DATA_CONTENT : text; Droits Joueurs
	 * PLAYER_RIGHTS : ID : auto increment; PLAYER_ID : int; RIGHT_NAME : text; Iles
	 * ISLAND : ID : auto increment; PLAYERS_ID : text; ISLAND_CENTER : text;
	 * ISLAND_SPAWN : text; ISLAND_LEVEL : double; Données Challenges Ile
	 * ISLAND_DATA : ID : auto increment; ISLAND_ID : int; DATA_NAME : text;
	 * DATA_CONTENT : text;
	 * 
	 */

	private static String BDD_host = "jdbc:mysql://localhost:3306/pixelsskyblock";
	private static String BDD_username = "root";
	private static String BDD_password = "";
	private static Connection conn = null;
	private static Statement stmt = null;

	/**
	 * Crée les tables si elles existent pas.
	 */
	public static void createDatabase() {
		// TODO : requête pour créer les tables si elles existent pas.

		try
		{
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();

			stmt.executeQuery("SET sql_notes = 0;");      // Temporarily disable the "Table already exists" warning
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS `PixelsSkyblock`.`ISLAND`("
					+ "ID INT PRIMARY KEY NOT NULL,"
					+ "PLAYERS_ID text NOT NULL,"
					+ "ISLAND_CENTER text NOT NULL,"
					+ "ISLAND_SPAWN text NOT NULL,"
					+ "ISLAND_LEVEL text NOT NULL);");	// Creates ISLAND (if not in DB)
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS `PixelsSkyblock`.`ISLAND_DATA`("
					+ "ISLAND_ID INT NOT NULL,"
					+ "DATA_NAME text NOT NULL,"
					+ "DATA_CONTENT text NOT NULL);");	// Creates ISLAND_DATA (if not in DB)
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS `PixelsSkyblock`.`PLAYERS`("
					+ "ID INT PRIMARY KEY NOT NULL,"
					+ "UUID text NOT NULL,"
					+ "ISLAND_ID INT NOT NULL;");	// Creates PLAYERS (if not in DB)
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS `PixelsSkyblock`.`PLAYER_DATA`("
					+ "PLAYER_ID INT NOT NULL,"
					+ "DATA_NAME text NOT NULL,"
					+ "DATA_CONTENT text NOT NULL);");	// Creates PLAYER_DATA (if not in DB)
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS `PixelsSkyblock`.`PLAYER_RIGHTS`("
					+ "ID INT PRIMARY KEY NOT NULL,"
					+ "PLAYER_ID INT NOT NULL,"
					+ "RIGHT_NAME text NOT NULL);");	// Creates PLAYER_RIGHTS (if not in DB)
			stmt.executeQuery("SET sql_notes = 1;");      // And then re-enable the warning again

			conn.close();
		}catch(Exception e){

			System.out.println("ERREUR CREATEDATABASE : " + e.toString());

		}

	}

	public static SPlayer getPlayer(String UUID) {
		// TODO : renvoyer les données du joueur
		SPlayer p = new SPlayer(""); // création joueur vide

		/*
		 * FAIRE REQUETES SQL ICI
		 * 
		 * Requête pour obtenir les données de la table PLAYERS Requête pour obtenir les
		 * données de la table PLAYER_DATA Requête pour obtenir les données de la table
		 * PLAYER_RIGHTS
		 */

		try {
			// Connection
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `players` WHERE `UUID` LIKE '" + UUID + "';");

			if (!res.isBeforeFirst()) {
				// Si le joueur n'existe pas dans la BDD on le créé
				p.init(0, UUID, -1);
				p.addOrSetData("DonnéeVide", null);
				p.addRight(Right.getRight("island.invite"));
				System.out.println("4");

				stmt.executeUpdate("INSERT INTO `players` (`ID`, `UUID`, `ISLAND_ID`) VALUES (NULL, '" + p.getUUID()
				+ "', '-1');");
				System.out.println("5");
				conn.close();
				readPlayerData(p);
			} else {
				// Chargement des éléments de base
				while (res.next()) {
					p.init(res.getInt("ID"), UUID, res.getInt("ISLAND_ID"));
					readPlayerData(p);
				}
				conn.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			System.out.println("ERREUR GETPLAYER : " + e.toString());
		}

		// p.addOrSetData("DonnéeVide", null);
		// p.addRight(Right.getRight("TypeDeDroit.Droit"));

		return p;
	}

	public static void loadIslands() {
		// TODO : renvoyer les données de l'île à partir d'un joueur
		System.out.println("PixelsSkyblock : Loading Islands !");
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `island`; ");
			while (res.next()) {
				System.out.println("Chargement... ");
				IslandsManager.setIsland(new Island(res.getInt("ID"), res.getString("PLAYERS_ID"),
						res.getString("ISLAND_CENTER"), res.getString("ISLAND_SPAWN"), res.getDouble("ISLAND_LEVEL")));
			}

			conn.close();
			for(Island i: IslandsManager.islands){
				readIslandData(i);
			}
		} catch (Exception ex) {
			System.out.println("EX_LOADING_ISLAND" + ex.toString());
		}
	}

	public static void updateIsland(Island i) {
		// ATTENTION : ne fait pas l'ajout d'îles !
		System.out.println("PixelsSkyblock : Saving Islands !");
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request

			System.out.println("Chargement... Mise à jour : " + i.getID());

			stmt.executeUpdate("UPDATE `island` SET `PLAYERS_ID` = '" + i.getMembersToString()
			+ "', `ISLAND_CENTER` = '" + Locations.toString(i.getCenter()) + "', `ISLAND_SPAWN` = '"
			+ Locations.toString(i.getSpawn()) + "', `ISLAND_LEVEL` = '" + i.getLevel()
			+ "' WHERE `island`.`ID` = " + i.getID() + "; ");
			System.out.println(3);


			conn.close();
			writeIslandData(i);
		} catch (Exception ex) {
			System.out.println("EX_SAVING_ISLAND" + ex.toString());
		}
	}

	public static void updatePlayer(SPlayer p) {
		// ATTENTION : ne fait pas l'ajout d'îles !
		System.out.println("PixelsSkyblock : Saving Player !");
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request


			stmt.executeUpdate("UPDATE `players` SET "
					+ "`ISLAND_ID` = '" + p.getIsland().getID() + "'"
					+ " WHERE `players`.`ID` = " + p.getID() + "; ");
			System.out.println(3);


			conn.close();
		} catch (Exception ex) {
			System.out.println("EX_SAVING_ISLAND" + ex.toString());
		}
	}

	public static void writePlayerData(SPlayer p) {
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM `player_data` WHERE `PLAYER_ID` = " + p.getID() + " ; ");
			for (Data d : p.getData()) {
				stmt.executeUpdate(
						"INSERT INTO `player_data` (`PLAYER_ID`, `DATA_NAME`, `DATA_CONTENT`) VALUES ('"
								+ p.getID() + "', '" + d.getDataName() + "', '" + d.getData().toString() + "'); ");
			}
			conn.close();

		} catch (Exception ex) {

		}
	}
	
	public static void writeIslandData(Island i) {
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM `island_data` WHERE `ISLAND_ID` = " + i.getID() + " ; ");
			for (Data d : i.getData()) {
				stmt.executeUpdate(
						"INSERT INTO `island_data` (`ISLAND_ID`, `DATA_NAME`, `DATA_CONTENT`) VALUES ('"
								+ i.getID() + "', '" + d.getDataName() + "', '" + d.getData().toString() + "'); ");
			}
			conn.close();

		} catch (Exception ex) {

		}
	}

	public static void readIslandData(Island i) {
		// TODO faire un update ou insert
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `island_data` WHERE  `ISLAND_ID` = " + i.getID() + " ; ");
			if (!res.isBeforeFirst()) {

			} else {
				// Chargement des éléments de base
				while (res.next()) {
					i.addOrSetData(res.getString("DATA_NAME"), res.getString("DATA_CONTENT"));
				}
			}
			conn.close();
		} catch (Exception ex) {

		}
	}
	
	public static void readPlayerData(SPlayer p) {
		// TODO faire un update ou insert
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `player_data` WHERE  `PLAYER_ID` = " + p.getID() + " ; ");
			if (!res.isBeforeFirst()) {

			} else {
				// Chargement des éléments de base
				while (res.next()) {
					p.addOrSetData(res.getString("DATA_NAME"), res.getString("DATA_CONTENT"));
				}
			}
			conn.close();
		} catch (Exception ex) {

		}
	}

	public static void setPlayerRights(SPlayer p) {
		/* Pas nécéssaire ? Island Data */
		/* necessaire pour droits au spawn? */
	}


	/**
	 * Deletes an island
	 * 
	 */
	public static void deleteIsland(Island island){
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `ISLAND` WHERE  `ID` = " + island.getID() + " ; ");
			if (res.isBeforeFirst()) {
				stmt.executeUpdate("DELETE FROM `ISLAND` WHERE  `ID` = " + island.getID() + " ; ");
			}
			conn.close();
			IslandsManager.removeIsland(island.getID());
		} catch (SQLException e) {
			System.out.println(e.toString());
		}

	}

	/**
	 * creates an island and delete if one already exist
	 * 
	 */
	public static void createIsland(SPlayer p) {

		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			Island island = new Island(0,p.getID() + ",","world,0,0,0,0,0", "world,0,100,0,0,0", 0);

			stmt.executeUpdate("INSERT INTO `ISLAND` (`PLAYERS_ID`, `ISLAND_CENTER`, `ISLAND_SPAWN`, `ISLAND_LEVEL`) VALUES ('"
					+ island.getMembersToString() + "', '" + Locations.toString(island.getCenter()) + "', '"
					+ Locations.toString(island.getSpawn()) + "', '" + island.getLevel() + "'); ");

			ResultSet res = stmt.executeQuery("SELECT `ID` FROM `ISLAND` WHERE  `ISLAND_CENTER` = '" +Locations.toString(island.getCenter()) + "';");

			while (res.next()) {
				island.setID(res.getInt("ID"));
				island.setCenter(Locations.getIsCenterByID(island.getID()));
				island.setHome(Locations.getIsCenterByID(island.getID()));	
			}

			conn.close();
			p.setIsland(island);
			IslandsManager.setIsland(island);
			updatePlayer(p);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}
}
