package com.devmania.lg.listeners;

import java.io.IOException;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.generator.ChunkGenerator;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.generator.LabyrintheWorldGenerator;
import com.devmania.lg.player.PlayerData;

public class EntityDamageByEntity implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		
		PlayerData playerData = LabyrintheGenerator.getInstance().getPlayer_data().get(event.getEntity());
		
		if(playerData != null){
			
			if((playerData.getPoint()-10) < 0){
				playerData.setPoint(0);
			}
			else{
				playerData.setPoint(playerData.getPoint()-10);
			}
		}
		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e){
		ChunkGenerator gen = e.getEntity().getWorld().getGenerator();
		
		if(!(gen instanceof LabyrintheWorldGenerator)){
			return;
		}
		
		LabyrintheWorldGenerator lab = (LabyrintheWorldGenerator) gen;
		
		if(lab.getEnter().getChunk().equals(e.getEntity().getLocation().getChunk())){
			if(e.getDamager() instanceof Player){
				Player damager = (Player) e.getDamager();
				
				if(damager.isOp()){
					return;
				}
				
			}
			e.setCancelled(true);
			return;
		}
		
		if(e.getDamager() instanceof Player){
			Player damager = (Player) e.getDamager();
			PlayerData playerData = LabyrintheGenerator.getInstance().getPlayer_data().get(damager);
			
			if(e.getEntity() instanceof Player){
				if(((int)(((Player)e.getEntity()).getHealth()-e.getDamage())) <= 0){
					if(playerData != null){
						playerData.setPoint(playerData.getPoint()+2);
					}
				}
			}
			else if(e.getEntity() instanceof LivingEntity){
				LivingEntity entity = (LivingEntity) e.getEntity();
				if(((int)(entity.getHealth()-e.getDamage())) <= 0){
					if(playerData !=  null){
						playerData.setPoint(playerData.getPoint()+1);
					}
				}
			}
			
		}
		
	}


}
