package com.devmania.lg.player;

import java.util.Collection;

import org.bukkit.entity.Player;

import com.devmania.lg.LabyrintheGenerator;

public class ScoreBoardUpdate extends Thread{
	
	private LabyrintheGenerator main;
	
	public ScoreBoardUpdate(LabyrintheGenerator main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		while(main.isStart()){
			try {
				
				for(Player player : main.getServer().getOnlinePlayers()){
					if(player.isOnline() && main.isStart()){
						PlayerScoreBoard score = main.getPlayer_scoreboard().get(player);
						if(score != null){
							score.update();
						}
					}
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
