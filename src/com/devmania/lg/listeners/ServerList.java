package com.devmania.lg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.generator.ChunkGenerator;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.data.Steps;
import com.devmania.lg.generator.LabyrintheWorldGenerator;

public class ServerList implements Listener {
	
	@EventHandler
	public void pingList(ServerListPingEvent event){
		Steps steps = LabyrintheGenerator.getInstance().getSteps();
		
		if(steps.equals(Steps.STARTING)){
			event.setMotd(ChatColor.GRAY+"Generation of the Labyrinth ...");
		}
		
		if(steps.equals(Steps.RUNNING)){
			ChunkGenerator gen = LabyrintheGenerator.getInstance().getLabyrinthe().getGenerator();
			int dimx = -1;
			int dimz = -1;
			if(gen instanceof LabyrintheWorldGenerator){
				LabyrintheWorldGenerator generator = (LabyrintheWorldGenerator) gen;
				dimx = generator.getDimX();
				dimz = generator.getDimZ();
			}
			
			if((dimx <= 0) || (dimz <= 0)){
				event.setMotd(ChatColor.GREEN+"The Labyrinth");
			}
			else{
				event.setMotd(ChatColor.GREEN+"The Labyrinth   "+dimx+"x"+dimz+" chunks");
			}
		}
		
		if(steps.equals(Steps.STOPING)){
			event.setMotd(ChatColor.RED+"Stopping the server ...");
		}
	}
	
}
