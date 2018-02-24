package pixelssky.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import pixelssky.objects.Island;
import pixelssky.objects.Right;
import pixelssky.objects.SPlayer;

public class DatabaseManager {
	
	/*TODO : Créer le système de BDD
	Prendre en charge les données des joueurs ainsi que des îles
	Toutes les données doivent être stockées intelligement
	Les requêtes doivent être éxecutées de façon optimisée
	Le mot de passe de la BDD sera stocké dans un fichier de config (plugins/PixelsSkyblock/config.txt.
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
		//TODO : renvoyer les données du joueur
		SPlayer p = new SPlayer(""); // création joueur vide
		
		/*FAIRE REQUETES SQL ICI
		 * 
		 * Requête pour obtenir les données de la table PLAYERS
		 * Requête pour obtenir les données de la table PLAYER_DATA
		 * 
		 */
		
		p.setData(UUID, getIsland(p));  
		p.addOrSetData("DonnéeVide", null);
		p.addRight(Right.getRight("TypeDeDroit.Droit"));
		
		return p;
	}
	
	public static Island getIsland(SPlayer p){
		//TODO : renvoyer les données de l'île à partir d'un joueur
		// return null si le joueur n'a pas d'île.
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
