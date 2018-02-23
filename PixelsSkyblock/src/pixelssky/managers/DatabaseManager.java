package pixelssky.managers;

import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;

public class DatabaseManager {
	
	/*TODO : Créer le système de BDD
	Prendre en charge les données des joueurs ainsi que des îles
	Toutes les données doivent être stockées intelligement
	Les requêtes doivent être éxecutées de façon optimisée
	Le mot de passe de la BDD sera stocké dans un fichier de config (plugins/PixelsSkyblock/config.txt.
	*/
	
	private String BDD_name = "";
	private String BDD_host = "127.0.0.1:3306";
	private String BDD_username = "";
	private String BDD_password = "";
	
	public void createDatabase(){
		
	}
	
	public SPlayer getPlayer(String UUID){
		//TODO : renvoyer les données du joueur
		return null;
	}
	
	public Island getIsland(SPlayer p){
		//TODO : renvoyer les données de l'île à partir d'un joueur
		return null;
	}
	
	public void setPlayerData(SPlayer p){
		//TODO faire un update
	}
	
	public void setIslandData(Island island){
		//TODO faire un update
	}
}
