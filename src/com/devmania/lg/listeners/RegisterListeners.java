package com.devmania.lg.listeners;

import org.bukkit.plugin.PluginManager;

import org.bukkit.Bukkit;

import com.devmania.lg.LabyrintheGenerator;

public class RegisterListeners {
	
	public RegisterListeners(LabyrintheGenerator lg) {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerJoin(), lg);
		pm.registerEvents(new PlayerQuit(), lg);
		pm.registerEvents(new PlayerChat(), lg);
		pm.registerEvents(new EntityDamageByEntity(), lg);
		pm.registerEvents(new BlockPlace(), lg);
		pm.registerEvents(new BlockBreak(), lg);
		pm.registerEvents(new PlayerMove(), lg);
		pm.registerEvents(new PlayerRespawn(), lg);
		pm.registerEvents(new PlayerFoodLevel(), lg);
		pm.registerEvents(new PlayerLogin(), lg);
		pm.registerEvents(new ServerList(), lg);
	}

}
