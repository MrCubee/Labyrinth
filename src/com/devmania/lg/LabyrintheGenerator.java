package com.devmania.lg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import com.devmania.lg.command.LabCommand;
import com.devmania.lg.command.PointCommand;
import com.devmania.lg.command.ScoreCommand;
import com.devmania.lg.data.Steps;
import com.devmania.lg.generator.LabyrintheWorldGenerator;
import com.devmania.lg.listeners.RegisterListeners;
import com.devmania.lg.player.PlayerData;
import com.devmania.lg.player.PlayerScoreBoard;
import com.devmania.lg.player.PlayerTime;
import com.devmania.lg.player.ScoreBoardUpdate;

import util.Copy;

public class LabyrintheGenerator extends JavaPlugin{
	
	private boolean start;
	private Steps steps;
	
	private static LabyrintheGenerator instance;
	
	private int dimX, dimZ;
	private World labyrinthe;
	
	private File configPlayerFile;
	private YamlConfiguration configPlayer;
	
	
	private File scoreFile;
	private YamlConfiguration scoreConfig;
	
	private File playerDataFile;
	private YamlConfiguration playerDataConfig;
	
	private Map<Player, PlayerTime> player_time;
	private Map<Player, PlayerScoreBoard> player_scoreboard;
	private Map<Player, PlayerData> player_data;
	
	@Override
	public void onLoad() {
		steps = Steps.STARTING;
		start = true;
		instance = this;
		saveDefaultConfig();
		
		scoreFile = new File("plugins/"+this.getName()+"/playerScore.yml");
		if(!scoreFile.exists()){
			try {
				scoreFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				this.getServer().shutdown();
				return;
			}
		}
		scoreConfig = YamlConfiguration.loadConfiguration(scoreFile);
		
		playerDataFile = new File("plugins/"+this.getName()+"/playerData.yml");
		if(!playerDataFile.exists()){
			try {
				playerDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				this.getServer().shutdown();
				return;
			}
		}
		playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
		
		configPlayerFile = new File("plugins/"+this.getName()+"/playerSave.yml");
		if(configPlayerFile.exists()){
			Copy.delete(configPlayerFile);
		}
		
		try {
			configPlayerFile.createNewFile();
			configPlayer = YamlConfiguration.loadConfiguration(configPlayerFile);
		} catch (IOException e) {
			e.printStackTrace();
			this.getServer().shutdown();
			return;
		}
		
		
		File labyrinthe = new File("Labyrinthe");
		if(labyrinthe.exists()){
			Copy.delete(labyrinthe);
		}
		
		File world = new File("world");
		if(world.exists()){
			Copy.delete(world);
		}
		
		File world_nether = new File("world_nether");
		if(world_nether.exists()){
			Copy.delete(world_nether);
		}
		
		File world_the_end = new File("world_the_end");
		if(world_the_end.exists()){
			Copy.delete(world_the_end);
		}
		
		int x = this.getConfig().getInt("Labyrinthe_Size.x");
		int z = this.getConfig().getInt("Labyrinthe_Size.z");
		
		if(x <= 0){
			x = (1000/16);
		}
		
		if(z <= 0){
			z = (1000/16);
		}
		
		dimX = (x/16);
		dimZ = (z/16);
		
		
		if(dimX <= 0){
			dimX = (1000/16);
		}
		
		if(dimZ <= 0){
			dimZ = (1000/16);
		}
	}
	
	@Override
	public void onEnable() {
		
		player_time = new HashMap<Player, PlayerTime>();
		player_scoreboard = new HashMap<Player, PlayerScoreBoard>();
		player_data = new HashMap<Player, PlayerData>();
		
		getCommand("score").setExecutor(new ScoreCommand(this));
		getCommand("point").setExecutor(new PointCommand(this));
		getCommand("lab").setExecutor(new LabCommand(this));
		
		new RegisterListeners(this);
		
		for(World w : Bukkit.getWorlds()){
			w.setGameRuleValue("naturalRegeneration", false + "");
			w.setGameRuleValue("mobGriefing", false + "");
			w.setDifficulty(Difficulty.HARD);
		}
		
		WorldCreator creator = new WorldCreator("Labyrinthe");
		creator.environment(Environment.NORMAL);
		creator.generateStructures(false);
		LabyrintheWorldGenerator generator = new LabyrintheWorldGenerator(dimX, dimZ);
		creator.generator(generator);
		labyrinthe = creator.createWorld();
		labyrinthe.setDifficulty(Difficulty.HARD);
		
		ScoreBoardUpdate update = new ScoreBoardUpdate(this);
		update.start();
		
		Chunk start_chunk = generator.getEnter().getChunk();
		Chunk end_chunk = generator.getExit().getChunk();
		
		
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(start && (Bukkit.getWorld("Labyrinthe") == null)){}
				steps = Steps.RUNNING;
			}
		}).start();
	}
	
	@Override
	public void onDisable() {
		start = false;
		steps = Steps.STOPING;
		labyrinthe.setAutoSave(false);
		File labyrinthe = new File("Labyrinthe");
		if(labyrinthe.exists()){
			Copy.delete(labyrinthe);
		}
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		LabyrintheWorldGenerator generator = new LabyrintheWorldGenerator(dimX, dimZ);
		Bukkit.getLogger().info("generate Labyrinthe class ...");
		
		return generator;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equals("help")){
			sender.sendMessage(ChatColor.GOLD+"The goal is to finish the labyrinth before the other players, and to have the most point and score.");
			sender.sendMessage("");
			sender.sendMessage(ChatColor.BLUE+"Here are the commands you have:");
			sender.sendMessage(ChatColor.BLUE+"- /help");
			sender.sendMessage(ChatColor.BLUE+"- /point send <amount> <player>");
			sender.sendMessage(ChatColor.BLUE+"- /shop");
		}
		
		return false;
		
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		String msg = e.getMessage();
		String[] args = msg.split(" ");
		if(args[0].equalsIgnoreCase("/help")){
			e.setCancelled(true);
			p.sendMessage(ChatColor.GOLD+"The goal is to finish the labyrinth before the other players, and to have the most point and score.");
			p.sendMessage("");
			p.sendMessage(ChatColor.BLUE+"Here are the commands you have:");
			p.sendMessage(ChatColor.BLUE+"- /help");
			p.sendMessage(ChatColor.BLUE+"- /point send <amount> <player>");
			p.sendMessage(ChatColor.BLUE+"- /shop");
		}
	}
	
	public List<String> getPlayers(){
		List<String> listArgs = new ArrayList<String>();
		for(Player player : this.getServer().getOnlinePlayers()){
			listArgs.add(player.getName());
		}
		return listArgs;
	}
	
	public static LabyrintheGenerator getInstance() {
		return instance;
	}
	
	public Map<Player, PlayerTime> getPlayer_time() {
		return player_time;
	}
	
	public Map<Player, PlayerScoreBoard> getPlayer_scoreboard() {
		return player_scoreboard;
	}
	
	public Map<Player, PlayerData> getPlayer_data() {
		return player_data;
	}
	
	public World getLabyrinthe() {
		return labyrinthe;
	}
	
	public YamlConfiguration getConfigPlayer() {
		return configPlayer;
	}
	
	public File getConfigPlayerFile() {
		return configPlayerFile;
	}
	
	public YamlConfiguration getScoreConfig() {
		return scoreConfig;
	}
	
	public File getScoreFile() {
		return scoreFile;
	}
	
	public File getPlayerDataFile() {
		return playerDataFile;
	}
	
	public YamlConfiguration getPlayerDataConfig() {
		return playerDataConfig;
	}
	
	public boolean isStart() {
		return start;
	}
	
	public Steps getSteps() {
		return steps;
	}
	
}
