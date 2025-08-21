package com.devmania.lg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.generator.ChunkGenerator;

import com.devmania.lg.generator.LabyrintheWorldGenerator;

public class PlayerFoodLevel implements Listener {
	
	@EventHandler
	public void food(FoodLevelChangeEvent event){
		ChunkGenerator generator = event.getEntity().getWorld().getGenerator();
		
		if(generator instanceof LabyrintheWorldGenerator){
			LabyrintheWorldGenerator labyGenerator = (LabyrintheWorldGenerator) generator;
			if(event.getEntity().getLocation().getChunk().equals(labyGenerator.getEnter().getChunk())){
				event.setCancelled(true);
				event.setFoodLevel(40);
			}
		}
	}
	
}
