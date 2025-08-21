package com.devmania.lg.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.generator.LabyrintheWorldGenerator;

public class LabCommand implements CommandExecutor, TabCompleter{
	LabyrintheGenerator main;
	
	public LabCommand(LabyrintheGenerator main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if((args == null) || (args.length < 0)){
			sender.sendMessage("/lab getEnter");
			sender.sendMessage("/lab getExit");
			return true;
		}
		
		if(!(sender instanceof Player)){
			return true;
		}
		
		Player p = (Player) sender;
		
		if(!p.isOp()){
			return true;
		} 
		
		ChunkGenerator generator = p.getWorld().getGenerator();
		
		if(!(generator instanceof LabyrintheWorldGenerator)){
			p.sendMessage("Error01");
		}
		
		LabyrintheWorldGenerator generatorLab = (LabyrintheWorldGenerator) generator;
		
		if(args[0].equalsIgnoreCase("getEnter")){
			Location l = generatorLab.getEnter();
			p.sendMessage("x: "+l.getBlockX()+" y: "+l.getBlockY()+" z: "+l.getBlockZ());
		}
		
		if(args[0].equalsIgnoreCase("getExit")){
			Location l = generatorLab.getExit();
			p.sendMessage("x: "+l.getBlockX()+" y: "+l.getBlockY()+" z: "+l.getBlockZ());
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		if(sender instanceof Player){
			player = (Player) sender;
			if(!player.isOp()){
				return null;
			}
		}
		List<String> listArgs = new ArrayList<String>();
		listArgs.add("getenter");
		listArgs.add("getexit");
		
		return listArgs;
	}
}
