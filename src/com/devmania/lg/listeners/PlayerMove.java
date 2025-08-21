package com.devmania.lg.listeners;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.generator.ChunkGenerator;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.generator.LabyrintheWorldGenerator;
import com.devmania.lg.player.PlayerData;

public class PlayerMove implements Listener {
	
	@EventHandler
	public void move(PlayerMoveEvent event){
		
		ChunkGenerator gen = event.getPlayer().getWorld().getGenerator();
		
		if(gen instanceof LabyrintheWorldGenerator){
			LabyrintheWorldGenerator generator = (LabyrintheWorldGenerator) gen;
			Location exit = generator.getExit();

			if(event.getTo().getChunk().equals(exit.getChunk())){
				
				
				PlayerData playerData = LabyrintheGenerator.getInstance().getPlayer_data().get(event.getPlayer());
				if(playerData != null){
					playerData.setScore(playerData.getScore()+1);
				}
				
				for(Player p : Bukkit.getServer().getOnlinePlayers()){
					
					if(p.equals(event.getPlayer())){
						p.kickPlayer(ChatColor.GOLD+"you win !!!");
					}
					else{
						p.kickPlayer(ChatColor.RED+event.getPlayer().getName()+" has completed the labyrinth");
					}
				}
				Bukkit.getServer().shutdown();
				return;
			}
		}
	}
	
}
