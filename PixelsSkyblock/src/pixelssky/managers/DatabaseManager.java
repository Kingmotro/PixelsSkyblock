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
	 * TODO : Cr�er le syst�me de BDD Prendre en charge les donn�es des joueurs
	 * ainsi que des �les Toutes les donn�es doivent �tre stock�es intelligement Les
	 * requ�tes doivent �tre �xecut�es de fa�on optimis�e Le mot de passe de la BDD
	 * sera stock� dans un fichier de config (plugins/PixelsSkyblock/config.txt.
	 */

	/*
	 * Structure de la BDD : Tables : Joueurs PLAYERS : ID : auto increment; UUID :
	 * text; ISLAND_ID: int; Donn�es joueurs PLAYER_DATA : ID : auto increment;
	 * PLAYER_ID : int; DATA_NAME : text; DATA_CONTENT : text; Droits Joueurs
	 * PLAYER_RIGHTS : ID : auto increment; PLAYER_ID : int; RIGHT_NAME : text; Iles
	 * ISLAND : ID : auto increment; PLAYERS_ID : text; ISLAND_CENTER : text;
	 * ISLAND_SPAWN : text; ISLAND_LEVEL : double; Donn�es Challenges Ile
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
	 * Cr�e les tables si elles existent pas.
	 */
	public static void createDatabase() {
		// TODO : requ�te pour cr�er les tables si elles existent pas.
		
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
					+ "ID INT PRIMARY KEY NOT NULL,"
					+ "ISLAND_ID INT NOT NULL,"
					+ "DATA_NAME text NOT NULL,"
					+ "DATA_CONTENT text NOT NULL);");	// Creates ISLAND_DATA (if not in DB)
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS `PixelsSkyblock`.`PLAYERS`("
					+ "ID INT PRIMARY KEY NOT NULL,"
					+ "UUID text NOT NULL,"
					+ "ISLAND_ID INT NOT NULL;");	// Creates PLAYERS (if not in DB)
			stmt.executeQuery("CREATE TABLE IF NOT EXISTS `PixelsSkyblock`.`PLAYER_DATA`("
					+ "ID INT PRIMARY KEY NOT NULL,"
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
		// TODO : renvoyer les donn�es du joueur
		SPlayer p = new SPlayer(""); // cr�ation joueur vide

		/*
		 * FAIRE REQUETES SQL ICI
		 * 
		 * Requ�te pour obtenir les donn�es de la table PLAYERS Requ�te pour obtenir les
		 * donn�es de la table PLAYER_DATA Requ�te pour obtenir les donn�es de la table
		 * PLAYER_RIGHTS
		 */

		try {
			// Connection
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `players` WHERE `UUID` LIKE '" + UUID + "';");

			if (!res.isBeforeFirst()) {
				// Si le joueur n'existe pas dans la BDD on le cr��
				p.init(0, UUID, -1);
				p.addOrSetData("Donn�eVide", null);
				p.addRight(Right.getRight("island.invite"));
				System.out.println("4");

				stmt.executeUpdate("INSERT INTO `players` (`ID`, `UUID`, `ISLAND_ID`) VALUES (NULL, '" + p.getUUID()
						+ "', '-1');");
				System.out.println("5");
				conn.close();
				readPlayerData(p);
			} else {
				// Chargement des �l�ments de base
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

		// p.addOrSetData("Donn�eVide", null);
		// p.addRight(Right.getRight("TypeDeDroit.Droit"));

		return p;
	}

	public static void loadIslands() {
		// TODO : renvoyer les donn�es de l'�le � partir d'un joueur
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
		} catch (Exception ex) {
			System.out.println("EX_LOADING_ISLAND" + ex.toString());
		}
	}

	public static void updateIslands() {
		// ATTENTION : ne fait pas l'ajout d'�les !
		System.out.println("PixelsSkyblock : Saving Islands !");
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `island`; ");
			while (res.next()) {
				System.out.println("Chargement... Mise � jour : " + res.getInt("ID"));
				System.out.println(1);
				Island i = IslandsManager.getIsland(res.getInt("ID"));
				System.out.println(i.getMembersToString());
				stmt.executeUpdate("UPDATE `island` SET `PLAYERS_ID` = '" + i.getMembersToString()
						+ "', `ISLAND_CENTER` = '" + Locations.toString(i.getCenter()) + "', `ISLAND_SPAWN` = '"
						+ Locations.toString(i.getSpawn()) + "', `ISLAND_LEVEL` = '" + i.getLevel()
						+ "' WHERE `island`.`ID` = " + i.getID() + "; ");
				System.out.println(3);
			}

			conn.close();
		} catch (Exception ex) {
			System.out.println("EX_SAVING_ISLAND" + ex.toString());
		}
	}

	public static void writePlayerData(SPlayer p) {
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			stmt.executeUpdate("DELETE FROM `player_data` WHERE `PLAYER_ID` = " + p.getID() + " ; ");
			for (Data d : p.getData()) {
				stmt.executeUpdate(
						"INSERT INTO `player_data` (`ID`, `PLAYER_ID`, `DATA_NAME`, `DATA_CONTENT`) VALUES (NULL, '"
								+ p.getID() + "', '" + d.getDataName() + "', '" + d.getData().toString() + "'); ");
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
				// Chargement des �l�ments de base
				while (res.next()) {
					p.addOrSetData(res.getString("DATA_NAME"), res.getString("DATA_CONTENT"));
				}
			}
			conn.close();
		} catch (Exception ex) {

		}
	}

	public static void setPlayerRights(SPlayer p) {
		/* Pas n�c�ssaire ? Island Data */
		/* necessaire pour droits au spawn? */
	}

	
	public static void setIslandData(Island island) {
		// TODO faire un update
		try {
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			stmt.executeUpdate("DELETE FROM `ISLAND_DATA` WHERE `ISLAND_ID` = " + island.getID() + " ; ");
			for (Data d : island.getData()) {
				stmt.executeUpdate(
						"INSERT INTO `ISLAND_DATA` (`ISLAND_ID`, `DATA_NAME`, `DATA_CONTENT`) VALUES ('"
								+ island.getID() + "', '" + d.getDataName() + "', '" + d.getData().toString() + "'); ");
			}
			conn.close();
		} catch (Exception e) {
			
			System.out.println("ERREUR SETISLANDDATA : " + e.toString());

		}
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
			}
			
			conn.close();
			p.setIsland(island);
			IslandsManager.setIsland(island);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}
}
