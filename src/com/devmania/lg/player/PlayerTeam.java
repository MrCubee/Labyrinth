package com.devmania.lg.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

public class PlayerTeam {
	
	private String name, prefix;
	private List<Player> players;
	
	public PlayerTeam(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
		this.players = new ArrayList<Player>();
	}
	
	public void update(){
		List<Player> update = new ArrayList<Player>(players.size());
		Collections.copy(players, update);
		
		for(Player updatePlayer : update){
			if((updatePlayer == null) || (!updatePlayer.isOnline())){
				players.remove(updatePlayer);
			}
		}
		
		update.clear();
		update = null;
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	
	
}
