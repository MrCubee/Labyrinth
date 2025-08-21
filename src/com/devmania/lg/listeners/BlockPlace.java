package com.devmania.lg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.generator.ChunkGenerator;

import com.devmania.lg.generator.LabyrintheWorldGenerator;

public class BlockPlace implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
//		Player p = e.getPlayer();
//		String name_block = e.getBlock().getType().name();
//		if((!p.isOp()) && (!p.hasPermission("lg.build.place")) && (!name_block.contentEquals("_ORE"))){
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
