package pixelssky.managers;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import pixelssky.objects.SPlayer;

public class PlayersManager {

	public static ArrayList<SPlayer> players = new ArrayList<SPlayer>();

	public static void setPlayer(SPlayer p){
		if(players.contains(p)){
			players.remove(p.getUUID());
		}
		players.add(p);
	}

	public static SPlayer getSPlayer(Player p){

		for(SPlayer pl: players){
			p.sendMessage(pl.getUUID());
			if(pl.getUUID().equals(p.getUniqueId().toString()))
			{
				return pl;
			}
		}
		return DatabaseManager.getPlayer(p.getUniqueId().toString());
	}

	public static void removePlayer(Player p){

		players.remove(getSPlayer(p));

	}
}
