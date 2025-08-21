package com.devmania.lg.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.devmania.lg.LabyrintheGenerator;

public class PlayerScoreBoard{
	
	private boolean paire;
	
	private LabyrintheGenerator main;
	private Player player;
	private Scoreboard scoreboard;
	
	public PlayerScoreBoard(Player player, LabyrintheGenerator main) {
		paire = false;
		this.main = main;
		this.player = player;
		init();
	}
	
	private void init(){
		scoreboard = main.getServer().getScoreboardManager().getNewScoreboard();
	}
	
	protected void update(){
		
		PlayerTime playerTime = main.getPlayer_time().get(player);
		PlayerData playerData = main.getPlayer_data().get(player);
		int point = 0, score = 0;
		
		if(playerData != null){
			point = playerData.getPoint();
			score = playerData.getScore();
		}
		
		Objective update;
		
		
		if(paire){
			update = scoreboard.registerNewObjective(player.getName(), "dummy");
		}
		else{
			update = scoreboard.registerNewObjective(player.getName()+"2", "dummy");
		}
		
		update.setDisplayName(ChatColor.DARK_RED+"Labyrinth");
		
		update.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE+"score: "+score)).setScore(-1);
		update.getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW+"point: "+point)).setScore(-2);
		
		
		Objective o;
		try {
			if(paire){
				o = scoreboard.getObjective(player.getName()+"2");
			}
			else{
				o = scoreboard.getObjective(player.getName());
			}
			
			o.setDisplaySlot(null);
			o.unregister();
		} catch (Exception e) {}
		
		update.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		paire = !paire;
	}
	
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	
}
