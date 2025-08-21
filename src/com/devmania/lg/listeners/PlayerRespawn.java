package com.devmania.lg.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.generator.LabyrintheWorldGenerator;

public class PlayerRespawn implements Listener {
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event){
		
		World w = LabyrintheGenerator.getInstance().getLabyrinthe();
		
		if(w != null){
			ChunkGenerator gen = w.getGenerator();
			
			if(gen instanceof LabyrintheWorldGenerator){
				LabyrintheWorldGenerator generator = (LabyrintheWorldGenerator) gen;
				Location enter = generator.getEnter();
				
				if(enter != null){
					
					if(event.getPlayer().getBedSpawnLocation() != null){
						event.setRespawnLocation(event.getPlayer().getBedSpawnLocation());
					}
					else{
						event.setRespawnLocation(enter.clone().add(0, 1, 0));
					}
					event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_SWORD));
					event.getPlayer().getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 5));
				}
				
			}
			
		}
	}
	
}
