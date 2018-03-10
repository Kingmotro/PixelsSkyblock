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

	public static void createDatabase() {
		// TODO : requête pour créer les tables si elles existent pas.
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

			if(!res.isBeforeFirst()) {
				// Si le joueur n'existe pas dans la BDD on le créé
				p.init(0, UUID, -1);
				p.addOrSetData("DonnéeVide", null);
				p.addRight(Right.getRight("island.invite"));
				System.out.println("4");

				stmt.executeUpdate("INSERT INTO `players` (`ID`, `UUID`, `ISLAND_ID`) VALUES (NULL, '" + p.getUUID() + "', '-1');");
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

		//p.addOrSetData("DonnéeVide", null);
		//p.addRight(Right.getRight("TypeDeDroit.Droit"));

		return p;
	}

	public static void loadIslands() {
		// TODO : renvoyer les données de l'île à partir d'un joueur
		System.out.println("PixelsSkyblock : Loading Islands !");
		try
		{
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `island`; ");
			while (res.next()) {
				System.out.println("Chargement... ");
				IslandsManager.setIsland(new Island(res.getInt("ID"), res.getString("PLAYERS_ID"), res.getString("ISLAND_CENTER"), res.getString("ISLAND_SPAWN"), res.getDouble("ISLAND_LEVEL")));
			}

			conn.close();
		}catch(Exception ex){
			System.out.println("EX_LOADING_ISLAND" + ex.toString());
		}
	}

	public static void updateIslands()
	{
		//ATTENTION : ne fait pas l'ajout d'îles !
		System.out.println("PixelsSkyblock : Saving Islands !");
		try
		{
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `island`; ");
			while (res.next()) {
				System.out.println("Chargement... Mise à jour : " + res.getInt("ID"));
				System.out.println(1);
				Island i = IslandsManager.getIsland(res.getInt("ID"));
				System.out.println(i.getMembersToString());
				stmt.executeUpdate("UPDATE `island` SET `PLAYERS_ID` = '" + i.getMembersToString() +
						"', `ISLAND_CENTER` = '" + Locations.toString(i.getCenter()) + 
						"', `ISLAND_SPAWN` = '" + Locations.toString(i.getSpawn()) + 
						"', `ISLAND_LEVEL` = '" + i.getLevel() +
						"' WHERE `island`.`ID` = " + i.getID() +
						"; ");
				System.out.println(3);
			}

			conn.close();
		}catch(Exception ex){
			System.out.println("EX_SAVING_ISLAND" + ex.toString());
		}
	}

	public static void writePlayerData(SPlayer p){
		try
		{
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			stmt.executeUpdate("DELETE FROM `player_data` WHERE `PLAYER_ID` = " +  p.getID() + " ; ");
			for(Data d:p.getData()){
				stmt.executeUpdate("INSERT INTO `player_data` (`ID`, `PLAYER_ID`, `DATA_NAME`, `DATA_CONTENT`) VALUES (NULL, '" +  p.getID() + "', '" +  d.getDataName() + "', '" +  d.getData().toString() + "'); ");
			}
			conn.close();
		}catch(Exception ex){

		}
	}

	public static void readPlayerData(SPlayer p) {
		// TODO faire un update ou insert
		try
		{
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `player_data` WHERE  `PLAYER_ID` = " +  p.getID() + " ; ");
			if(!res.isBeforeFirst()) {

			} else {
				// Chargement des éléments de base
				while (res.next()) {
					p.addOrSetData(res.getString("DATA_NAME"), res.getString("DATA_CONTENT"));
				}
			}
			conn.close();
		}catch(Exception ex){

		}
	}

	public static void setPlayerRights(SPlayer p) {
		/*Pas nécéssaire ? Island Data*/
		/* necessaire pour droits au spawn? */
	}
	
	public static void setIslandData(Island island) {
		// TODO faire un update
	}
	
	/**
	 * creates an island and delete if one already exist
	 * 
	 * */
	public static void createIsland(Island island) {
		
		try
		{
			conn = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery("SELECT * FROM `ISLAND` WHERE  `ISLAND_ID` = " +  island.getID() + " ; ");
			if(res.isBeforeFirst()) {
				stmt.executeQuery("DELETE FROM `ISLAND` WHERE  `ISLAND_ID` = " +  island.getID() + " ; ");
			}
			
			stmt.executeQuery("INSERT INTO `ISLAND` (`ID`, `PLAYER_ID`, `ISLAND_CENTER`, `ISLAND_SPAWN`, `ISLAND_LEVEL`) VALUES ('" + island.getID() + "', '" + island.getMembersToString() + "', '" + island.loc2str(island.getCenter())  + "', '" + island.loc2str(island.getSpawn()) + "', '" + island.getLevel() + "'); ");
			
			conn.close();
	}catch(Exception ex){

	}
		
	}
}
