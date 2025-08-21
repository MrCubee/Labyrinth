package com.devmania.lg.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.generator.ChunkGenerator;

import com.devmania.lg.generator.LabyrintheWorldGenerator;

public class BlockBreak implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
//		Player p = e.getPlayer();
//		if((!p.isOp()) && (!p.hasPermission("lg.build.break"))){
//			e.setCancelled(true);
//		}
		
		if(!e.getPlayer().isOp()){
			ChunkGenerator generator = e.getPlayer().getWorld().getGenerator();
			
			if(generator instanceof LabyrintheWorldGenerator){
				LabyrintheWorldGenerator lab = (LabyrintheWorldGenerator) generator;
				String result = lab.getLabyrinthes().getValue(e.getBlock().getChunk().getX(), e.getBlock().getChunk().getZ());
				if((result == null) || (!result.equals(" "))){
					e.setCancelled(true);
				}
				
			}
		}
		
	}

}
