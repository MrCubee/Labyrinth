package com.devmania.lg.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.generator.LabyrintheWorldGenerator;
import com.devmania.lg.player.PlayerData;
import com.devmania.lg.player.PlayerScoreBoard;
import com.devmania.lg.player.PlayerTime;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.setJoinMessage(ChatColor.GREEN+"[+] "+e.getPlayer().getName());
		joinPlayer(e.getPlayer());
	}
	
	public void newPlayer(Player p){
		p.setGameMode(GameMode.SPECTATOR);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(100);
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		p.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
		p.getInventory().addItem(new ItemStack(Material.IRON_SPADE));
		p.getInventory().addItem(new ItemStack(Material.IRON_AXE));
		p.getInventory().addItem(new ItemStack(Material.GRILLED_PORK, 10));
		p.setGameMode(GameMode.SURVIVAL);
		
		p.setBedSpawnLocation(null);
	}
	
	private void joinPlayer(final Player p){
		
		PlayerTime playerTime = new PlayerTime(p);
		
		LabyrintheGenerator.getInstance().getPlayer_time().remove(p);
		LabyrintheGenerator.getInstance().getPlayer_time().put(p, playerTime);
		
		playerTime.start();
		
		
		PlayerScoreBoard playerScoreBoard = new PlayerScoreBoard(p, LabyrintheGenerator.getInstance());
		
		LabyrintheGenerator.getInstance().getPlayer_scoreboard().remove(p);
		LabyrintheGenerator.getInstance().getPlayer_scoreboard().put(p, playerScoreBoard);
		
		
		PlayerData playerData = new PlayerData(p);
		LabyrintheGenerator.getInstance().getPlayer_data().remove(p);
		LabyrintheGenerator.getInstance().getPlayer_data().put(p, playerData);
		playerData.load();
		
		
		File file = LabyrintheGenerator.getInstance().getPlayerDataFile();
		YamlConfiguration config = LabyrintheGenerator.getInstance().getPlayerDataConfig();
		
		config.set(p.getUniqueId().toString(), p.getName());
		
		try {
			config.save(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(p.getMaxHealth() != 20){
			p.setMaxHealth(20);
			p.setHealth(20);
		}
		
		Object o = LabyrintheGenerator.getInstance().getConfigPlayer().get(p.getPlayer().getUniqueId().toString()+".location");
		
		if(o == null){
			newPlayer(p);
			p.sendTitle(ChatColor.GOLD+"The labyrinth", ChatColor.GRAY+"Welcome "+p.getName()+" in the labyrinth");
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(!p.isOnline()){
						return;
					}
					p.resetTitle();
					p.sendTitle(ChatColor.RED+"Useful information.", ChatColor.RED+"Please use the command /help .");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(!p.isOnline()){
						return;
					}
					
				}
			}).start();;
			
			World w = LabyrintheGenerator.getInstance().getLabyrinthe();
			
			if(w != null){
				ChunkGenerator gen = w.getGenerator();
				
				if(gen instanceof LabyrintheWorldGenerator){
					LabyrintheWorldGenerator generator = (LabyrintheWorldGenerator) gen;
					Location enter = generator.getEnter();
					
					if(enter != null){
						p.teleport(enter.clone().add(0, 1, 0));
						w.setSpawnLocation(enter.getBlockX(), enter.getBlockY(), enter.getBlockZ());
					}
					
				}
				
			}
			
		}
		
		p.setScoreboard(playerScoreBoard.getScoreboard());
	}

}
