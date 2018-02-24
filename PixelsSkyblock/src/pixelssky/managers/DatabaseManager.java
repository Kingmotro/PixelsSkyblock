package pixelssky.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pixelssky.objects.Island;
import pixelssky.objects.Right;
import pixelssky.objects.SPlayer;

public class DatabaseManager {
	
	/*TODO : Cr�er le syst�me de BDD
	Prendre en charge les donn�es des joueurs ainsi que des �les
	Toutes les donn�es doivent �tre stock�es intelligement
	Les requ�tes doivent �tre �xecut�es de fa�on optimis�e
	Le mot de passe de la BDD sera stock� dans un fichier de config (plugins/PixelsSkyblock/config.txt.
	*/
	
	/*Structure de la BDD :
	 * Tables :
	 * Joueurs 					PLAYERS : 		ID : auto increment; UUID : text; ISLAND_ID: int;
	 * Donn�es joueurs			PLAYER_DATA : 	ID : auto increment; PLAYER_ID : int; DATA_NAME : text; DATA_CONTENT : text;
	 * Droits Joueurs			PLAYER_RIGHTS :	ID : auto increment; PLAYER_ID : int; RIGHT_NAME : text;
	 * Iles						ISLAND : 		ID : auto increment; PLAYERS_ID : text; ISLAND_CENTER : text; ISLAND_SPAWN : text; ISLAND_LEVEL : double;
	 * Donn�es Challenges Ile	ISLAND_DATA : 	ID : auto increment; ISLAND_ID : int; DATA_NAME : text; DATA_CONTENT : text;
	 * 
	 */
	
	private static String BDD_name = "SKYBLOCK";
	private static String BDD_host = "jdbc:mysql://localhost:3306/SKYBLOCK";
	private static String BDD_username = "";
	private static String BDD_password = "";
	private static Connection conn = null;
	private static Statement stmt = null;
	
	public static void createDatabase(){
		//TODO : requ�te pour cr�er les tables si elles existent pas.
	}
	
	public static SPlayer getPlayer(String UUID){
		//TODO : renvoyer les donn�es du joueur
		SPlayer p = new SPlayer(""); // cr�ation joueur vide
		
		/*FAIRE REQUETES SQL ICI
		 * 
		 * Requ�te pour obtenir les donn�es de la table PLAYERS
		 * Requ�te pour obtenir les donn�es de la table PLAYER_DATA
		 * Requ�te pour obtenir les donn�es de la table PLAYER_RIGHTS
		 */
		
		try {
			// Connection
			conn  = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt  = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery( "SELECT *  FROM PLAYERS WHERE `PLAYERS`.`UUID` = "+ p.getUUID() + ";" );

			if(res.getFetchSize() == 0){
				//Si le joueur n'existe pas dans la BDD on le cr��
				conn.close();
				p.init(0 ,UUID, null);  
				p.addOrSetData("Donn�eVide", null);
				p.addRight(Right.getRight("island.invite"));
				setPlayerData(p);
				
			}else{
				//Chargement des �l�ments de base
				while ( res.next() ) {
					p.init(res.getInt("ID"), UUID, getIsland(res.getInt("ISLAND_ID")));
				}
				conn.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		p.addOrSetData("Donn�eVide", null);
		p.addRight(Right.getRight("TypeDeDroit.Droit"));
		
		return p;
	}
	
	public static Island getIsland(int ID){
		//TODO : renvoyer les donn�es de l'�le � partir d'un joueur
		// return null si le joueur n'a pas d'�le.
		return null;
	}
	
	public static void setPlayerData(SPlayer p){
		//TODO faire un update ou insert
		try {
			// Connection
			conn  = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt  = conn.createStatement();
			// Request
			ResultSet res = stmt.executeQuery( "SELECT ID  FROM PLAYERS WHERE `PLAYERS`.`UUID` = "+ p.getUUID() + ";" );
			int nb_val = 0;
			int playerID = Integer.MIN_VALUE;
			
			while ( res.next() ) {
				nb_val +=1;
			    playerID = res.getInt("ID");
			}
			
			if(nb_val == 0){
				//Si le joueur n'existe pas dans la BDD on le cr��
				stmt.executeUpdate( "INSERT INTO PLAYERS (UUID, ISLAND_ID) VALUES ('"+ p.getUUID() + "', '"+ p.getIsland().getID() + "');" );
				//On met � jour le player avec le nouvel ID
				ResultSet res2 = stmt.executeQuery( "SELECT ID FROM PLAYERS WHERE `PLAYERS`.`UUID` = "+ p.getUUID() + ";" );
				while ( res2.next() ) {
				    playerID = res.getInt("ID");
				}
				p.init(playerID, p.getUUID(),p.getIsland());
			}else{
				//On met � jour
				stmt.executeUpdate("UPDATE `players` SET `ISLAND_ID` = '"+ p.getIsland().getID() + "' WHERE `PLAYERS`.`ID` = "+ playerID + ";");
			}
			
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setIslandData(Island island){
		//TODO faire un update
	}
}
