package com.devmania.lg.player;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.devmania.lg.LabyrintheGenerator;

public class PlayerData {
	
	private Player player;
	private int score, point;
	
	public PlayerData(Player player) {
		this.player = player;
	}
	
	public void load(){
		
		if(player == null){
			Bukkit.getLogger().severe("Player null on Load Data");
			score = 0;
			point = 0;
			return;
		}
		
		score = LabyrintheGenerator.getInstance().getScoreConfig().getInt(player.getUniqueId().toString()+".score");
		point = LabyrintheGenerator.getInstance().getScoreConfig().getInt(player.getUniqueId().toString()+".point");
		Bukkit.getLogger().info("Load "+player.getName()+" -> Point: "+point+" Score: "+score);
	}
	
	public void save() throws IOException{
		
		if(player == null){
			Bukkit.getLogger().severe("Player null on Save Data");
			return;
		}
		
		Bukkit.getLogger().info("Save "+player.getName()+" -> Point: "+point+" Score: "+score);
		
		if((score > 0) || (point > 0)){
			LabyrintheGenerator.getInstance().getScoreConfig().set(player.getUniqueId().toString()+".score", score);
			LabyrintheGenerator.getInstance().getScoreConfig().set(player.getUniqueId().toString()+".point", point);
			LabyrintheGenerator.getInstance().getScoreConfig().set(player.getUniqueId().toString()+".name", player.getName());
			Bukkit.getLogger().info("Save score for "+player.getName());
		}
		else{
			Bukkit.getLogger().info("Any Score for "+player.getName());
			LabyrintheGenerator.getInstance().getScoreConfig().set(player.getUniqueId().toString()+".score", null);
			LabyrintheGenerator.getInstance().getScoreConfig().set(player.getUniqueId().toString()+".point", null);
			LabyrintheGenerator.getInstance().getScoreConfig().set(player.getUniqueId().toString()+".name", null);
			LabyrintheGenerator.getInstance().getScoreConfig().set(player.getUniqueId().toString(), null);
		}
		
		LabyrintheGenerator.getInstance().getScoreConfig().save(LabyrintheGenerator.getInstance().getScoreFile());
		Bukkit.getLogger().info("Config save for "+player.getName());
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setPoint(int point) {
		this.point = point;
	}
	
	public int getPoint() {
		return point;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
