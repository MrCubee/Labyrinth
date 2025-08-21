package com.devmania.lg.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.devmania.lg.LabyrintheGenerator;
import com.devmania.lg.player.PlayerData;

public class ScoreCommand implements CommandExecutor, TabCompleter{
	
	
	LabyrintheGenerator main;
	
	public ScoreCommand(LabyrintheGenerator main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean op = true;
		Player player = null;
		if(sender instanceof Player){
			player = (Player) sender;
			if(!player.isOp()){
				op = false;
			}
		}
		
		if(args.length < 1){
			if(op){
				sender.sendMessage(ChatColor.RED+"/score give <amount> <Player>");
			}
			return true;
		}
		
		if((args.length == 1) && op){
			Player target = main.getServer().getPlayer(args[0]);
			if((target == null) || (!target.isOnline())){
				player.sendMessage(ChatColor.RED+"Player not found, or not online.");
				return true;
			}
			
			PlayerData targetData = main.getPlayer_data().get(target);
			
			if(targetData == null){
				player.sendMessage(ChatColor.RED+" Sorry, error during the reading of the scores.");
				return true;
			}
			
			sender.sendMessage(ChatColor.GREEN+"score: "+targetData.getScore());
			return true;
		}
		
		if(args[0].equals("give")){
			if(args.length < 3){
				sender.sendMessage(ChatColor.RED+"/score give <amount> <Player>");
				return true;
			}
			
			int amount = 0;
			try {
				amount = Integer.parseInt(args[1]);
			} catch (Exception e) {}
			Player target = main.getServer().getPlayer(args[2]);
			
			if((target == null) || (!target.isOnline())){
				player.sendMessage(ChatColor.RED+"Player not found, or not online.");
				return true;
			}
			
			PlayerData targetData = main.getPlayer_data().get(target);
			
			if(targetData == null){
				player.sendMessage(ChatColor.RED+" Sorry, error in the transfer of scores.");
				return true;
			}
			
			if((targetData.getScore()+amount) < 0){
				targetData.setScore(0);
			}
			else{
				targetData.setScore(targetData.getScore()+amount);
			}
			
			if(amount > 1){
				sender.sendMessage(ChatColor.GREEN+"You gave "+amount+" scores to "+target.getName());
			}
			else{
				sender.sendMessage(ChatColor.GREEN+"You gave "+amount+" score to "+target.getName());
			}
			
			return true;
			
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
		
		if(args.length <= 0){
			listArgs.add("give");
		}
		
		if(args.length > 1){
			return main.getPlayers();
		}
		
		return null;
	}

}
