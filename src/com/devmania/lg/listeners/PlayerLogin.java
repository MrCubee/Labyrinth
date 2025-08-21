package com.devmania.lg.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPreLoginEvent;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.data.Steps;


public class PlayerLogin implements Listener {
	
	@EventHandler
	public void login(PlayerLoginEvent event){
		Steps steps = LabyrintheGenerator.getInstance().getSteps();
		
		if(steps.equals(Steps.STARTING)){
			event.setKickMessage(ChatColor.RED+"Please wait until the generation of the world is finished.");
			event.setResult(Result.KICK_OTHER);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void preLogin(PlayerPreLoginEvent event){
		Steps steps = LabyrintheGenerator.getInstance().getSteps();
		
		if(steps.equals(Steps.STARTING)){
			event.setKickMessage(ChatColor.RED+"Please wait until the generation of the world is finished.");
			event.setResult(org.bukkit.event.player.PlayerPreLoginEvent.Result.KICK_OTHER);
		}
	}
	
}
