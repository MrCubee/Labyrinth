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

public class PointCommand implements CommandExecutor, TabCompleter{
	
	LabyrintheGenerator main;
	
	public PointCommand(LabyrintheGenerator main) {
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
			sender.sendMessage(ChatColor.BLUE+"/point send <amount> <Player>");
			if(op){
				sender.sendMessage(ChatColor.RED+"/point give <amount> <Player>");
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
				player.sendMessage(ChatColor.RED+" Sorry, error during the reading of the points.");
				return true;
			}
			
			sender.sendMessage(ChatColor.GREEN+"point: "+targetData.getPoint());
			return true;
		}
		
		if(args[0].equals("give")){
			if(args.length < 3){
				sender.sendMessage(ChatColor.RED+"/point give <amount> <Player>");
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
				player.sendMessage(ChatColor.RED+" Sorry, error in the transfer of points.");
				return true;
			}
			
			targetData.setPoint(targetData.getPoint()+amount);
			
			if(amount > 1){
				sender.sendMessage(ChatColor.GREEN+"You gave "+amount+" points to "+target.getName());
			}
			else{
				sender.sendMessage(ChatColor.GREEN+"You gave "+amount+" point to "+target.getName());
			}
			
			return true;
			
		}
		
		if(args[0].equals("send")){
			
			if(player == null){
				sender.sendMessage(ChatColor.RED+"You must be a player to execute this command.");
				return true;
			}
			
			if(args.length < 3){
				sender.sendMessage(ChatColor.RED+"/point send <amount> <Player>");
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
			
			if(player.equals(target)){
				player.sendMessage(ChatColor.RED+"");
			}
			
			if(amount <= 0){
				player.sendMessage(ChatColor.RED+"The amount must be greater than 0.");
				return true;
			}
			
			PlayerData playerData = main.getPlayer_data().get(player);
			PlayerData targetData = main.getPlayer_data().get(target);
			
			if((playerData == null)  || (targetData == null)){
				player.sendMessage(ChatColor.RED+" Sorry, error in the transfer of points.");
				return true;
			}
			
			if(playerData.getPoint() < amount){
				player.sendMessage(ChatColor.RED+"You do not have enough points.");
				return true;
			}
			
			playerData.setPoint(playerData.getPoint()-amount);
			targetData.setPoint(targetData.getPoint()+amount);
			
			if(amount > 1){
				player.sendMessage(ChatColor.GREEN+"You sent "+amount+" points to "+target.getName()+".");
				target.sendMessage(ChatColor.GOLD+"You received "+amount+" points from "+player.getName()+".");
			}
			else{
				player.sendMessage(ChatColor.GREEN+"You sent "+amount+" point to "+target.getName());
				target.sendMessage(ChatColor.GOLD+"You received "+amount+" point from "+player.getName()+".");
			}
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		boolean op = true;
		Player player;
		if(sender instanceof Player){
			player = (Player) sender;
			if(!player.isOp()){
				op = false;
			}
		}
		
		List<String> listArgs = new ArrayList<String>();
		
		if(args.length <= 0){
			listArgs.add("send");
			if(op){
				listArgs.add("give");
			}
			return listArgs;
		}
		
		if(args.length > 1){
			return main.getPlayers();
		}
		
		return null;
		
		
	}
	
}
