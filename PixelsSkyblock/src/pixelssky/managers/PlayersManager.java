package pixelssky.managers;

import java.util.ArrayList;
import java.util.TreeMap;

import org.bukkit.entity.Player;

import pixelssky.objects.SPlayer;

public class PlayersManager {
	public static TreeMap<String, SPlayer> players = new TreeMap<String, SPlayer>();

	public static void setPlayer(SPlayer p){
		if(players.containsKey(p.getUUID())){
			players.remove(p.getUUID());
		}
		players.put(p.getUUID(), p);
	}

	public static SPlayer getSPlayer(Player p){
		return players.get(p.getUniqueId().toString());
	}
	
	public static void removePlayer(String UUID){
		players.remove(UUID);
	}
}
