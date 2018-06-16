package pixelssky.objects;

import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


public class ScoreboardObject {
	
	public Player p;
	private ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard board;
	TreeMap<String,Score> scores = new TreeMap<String,Score>();
	Objective objective;
	
	public ScoreboardObject(Player p)
	{
		this.p = p;
		board = manager.getNewScoreboard();
		
		try
		{
			objective = board.registerNewObjective("sky_menu", "dummy");
		}catch(Exception coucou)
		{
			objective = board.getObjective("sky_menu");
		}
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("§d-- §5Infos du skyblock §d--");
		//AJOUT DES SCORES PAR DEFAUT
		update();
	}
	public void addScore(String ID,String obj, int value)
	{
		Score score = objective.getScore(obj);
		scores.put(ID, score);
		score.setScore(value);
	}
	public void delScore(String ID)
	{
		try
		{
			objective.getScoreboard().resetScores(scores.get(ID).getEntry());
			scores.remove(ID);
		}catch(Exception ex)
		{
			
		}
	}
	public void setScoreValue(String ID, int value)
	{
		scores.get(ID).setScore(value);
	}
	public void setScoreText(String ID, String value, int val)
	{
		try
		{
			val = scores.get(ID).getScore();
			if(scores.get(ID).getEntry() != value)
			{
				objective.getScoreboard().resetScores(scores.get(ID).getEntry());
				scores.remove(ID);
				addScore(ID,value,val);
			}
		}catch(Exception ex)
		{
			addScore(ID,value,val);
		}
		
	}
	public void update()
	{
		p.setScoreboard(board);
	}
	
}
