package pixelssky.managers;

import java.sql.Connection;
import java.sql.DriverManager;
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
	 * PLAYERS : 		ID : auto increment; UUID : text; ISLAND_ID: int;
	 * PLAYER_DATA : 	ID : auto increment; PLAYER_ID : int; DATA_NAME : text; DATA_CONTENT : text;
	 * PLAYER_RIGHTS :	ID : auto increment; PLAYER_ID : int; RIGHT_NAME : text;
	 * ISLAND : 		ID : auto increment; PLAYERS_ID : text; ISLAND_CENTER : text; ISLAND_SPAWN : text; ISLAND_LEVEL : double;
	 * ISLAND_DATA : 	ID : auto increment; ISLAND_ID : int; DATA_NAME : text; DATA_CONTENT : text;
	 * 
	 */
	
	private static String BDD_name = "";
	private static String BDD_host = "127.0.0.1:3306";
	private static String BDD_username = "";
	private static String BDD_password = "";
	private static Connection conn = null;
	private static Statement stmt = null;
	
	public static void createDatabase(){
		
	}
	
	public static SPlayer getPlayer(String UUID){
		//TODO : renvoyer les donn�es du joueur
		SPlayer p = new SPlayer(""); // cr�ation joueur vide
		
		/*FAIRE REQUETES SQL ICI
		 * 
		 * Requ�te pour obtenir les donn�es de la table PLAYERS
		 * Requ�te pour obtenir les donn�es de la table PLAYER_DATA
		 * 
		 */
		
		p.setData(UUID, getIsland(p));  
		p.addOrSetData("Donn�eVide", null);
		p.addRight(Right.getRight("TypeDeDroit.Droit"));
		
		return p;
	}
	
	public static Island getIsland(SPlayer p){
		//TODO : renvoyer les donn�es de l'�le � partir d'un joueur
		// return null si le joueur n'a pas d'�le.
		return null;
	}
	
	public static void setPlayerData(SPlayer p){
		//TODO faire un update ou insert
		
		String sql = "INSERT INTO";

		try {

			// Connection
			conn  = DriverManager.getConnection(BDD_host, BDD_username, BDD_password);
			stmt  = conn.createStatement();
			// Request
			stmt.executeQuery(sql);
			//pas oublier de close
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setIslandData(Island island){
		//TODO faire un update
	}
}
