package com.devmania.lg.listeners;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.player.PlayerData;

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		e.setQuitMessage(ChatColor.RED+"[-] "+e.getPlayer().getName());
		YamlConfiguration config = LabyrintheGenerator.getInstance().getConfigPlayer();
		config.set(e.getPlayer().getUniqueId().toString()+".location", e.getPlayer().getLocation());
		config.set(e.getPlayer().getUniqueId().toString()+".name", e.getPlayer().getName());
		
		try {
			config.save(LabyrintheGenerator.getInstance().getConfigPlayerFile());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		LabyrintheGenerator.getInstance().getPlayer_scoreboard().remove(e.getPlayer());
		LabyrintheGenerator.getInstance().getPlayer_time().remove(e.getPlayer());
		
		PlayerData playerData = LabyrintheGenerator.getInstance().getPlayer_data().get(e.getPlayer());
		LabyrintheGenerator.getInstance().getPlayer_data().remove(e.getPlayer());
		try {
			playerData.save();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
