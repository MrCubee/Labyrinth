package com.devmania.lg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
//		Player p = e.getPlayer();
//		if((!e.getMessage().startsWith("/")) && (!p.isOp()) && (!p.hasPermission("lg.chat.speak"))){
//			e.setCancelled(true);
//		}
	}

}
