package pixelssky.managers;

import pixelssky.objects.Island;
import pixelssky.objects.SPlayer;

public class DatabaseManager {
	
	/*TODO : Cr�er le syst�me de BDD
	Prendre en charge les donn�es des joueurs ainsi que des �les
	Toutes les donn�es doivent �tre stock�es intelligement
	Les requ�tes doivent �tre �xecut�es de fa�on optimis�e
	Le mot de passe de la BDD sera stock� dans un fichier de config (plugins/PixelsSkyblock/config.txt.
	*/
	
	private String BDD_name = "";
	private String BDD_host = "127.0.0.1:3306";
	private String BDD_username = "";
	private String BDD_password = "";
	
	public void createDatabase(){
		
	}
	
	public SPlayer getPlayer(String UUID){
		//TODO : renvoyer les donn�es du joueur
		return null;
	}
	
	public Island getIsland(SPlayer p){
		//TODO : renvoyer les donn�es de l'�le � partir d'un joueur
		return null;
	}
	
	public void setPlayerData(SPlayer p){
		//TODO faire un update
	}
	
	public void setIslandData(Island island){
		//TODO faire un update
	}
}
