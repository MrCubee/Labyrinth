package com.devmania.lg.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
public class TeamData{
	
	private String name;
	private ChatColor color;
	private List<PlayerData> players;
	public TeamData(String name, ChatColor color) {
		this.name = name;
		this.color = color;
		players = new ArrayList<PlayerData>();
	}
	
	public void addPlayer(Player player){
		
	}
}
