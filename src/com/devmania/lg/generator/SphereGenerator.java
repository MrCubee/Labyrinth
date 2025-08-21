package com.devmania.lg.generator;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.generator.BlockPopulator;
import util.Distance;

public class SphereGenerator extends BlockPopulator{
	
	private LabyrintheWorldGenerator labyrinthe;
	
	public SphereGenerator(LabyrintheWorldGenerator labyrinthe) {
		this.labyrinthe = labyrinthe;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		
		if(labyrinthe == null){
			return;
		}
		
		if(labyrinthe.getLabyrinthes() == null){
			return;
		}
		
		if((labyrinthe.getLabyrinthes().getValue(chunk.getX(), chunk.getZ()) == null) || (!labyrinthe.getLabyrinthes().getValue(chunk.getX(), chunk.getZ()).equals(" "))){
			return;
		}
		
		Random rc = new Random();
		
		int c = rc.nextInt(10);
		
		if(c != 1){
			return;
		}
		
		int size = 7;
		
		Location loc = new Location(world, ((chunk.getX()*16)+8), (world.getMaxHeight()-2),((chunk.getZ()*16)+8));
		
		while(true){
			if(loc.getBlock().getType().equals(Material.AIR)){
				loc.setY(loc.getBlockY()-1);
			}
			else{
				loc.setY(loc.getBlockY()+1);
				break;
			}
		}
		
		int m = ((world.getMaxHeight()-2)-loc.getBlockY())/2;
		
		loc.setY(loc.getBlockY()+m);
		
		Location start = loc.clone().subtract(size, size, size);
		
		Random pr = new Random();
		int pv = pr.nextInt(2);
		
		Random rt = new Random();
		int t = rt.nextInt(2);
		
		for(int y = 0; y < (size*2); y++){
			for(int z = 0; z < (size*2); z++){
				for(int x = 0; x < (size*2); x++){
					Location blockLocation = start.clone().add(x, y, z);
					int d = Distance.distance(blockLocation, loc);
					if(d > size){
						continue;
					}
					
					Random r = new Random();
					
					int p = r.nextInt(10);
					
					if((d == size) || (d == (size-1))){
						
						if(y >= ((size*2)-1)){
							if(t == 1){
								blockLocation.getBlock().setType(Material.SAND);
							}
							else{
								blockLocation.getBlock().setType(Material.GRASS);
							}
						}
						else{
							blockLocation.getBlock().setType(Material.DIRT);
						}
//						Random rwc = new Random();
//						int cw = rc.nextInt(DyeColor.values().length);
//						
//						DyeColor color = DyeColor.values()[cw];
//						
//						blockLocation.getBlock().setType(Material.WOOL);
//						blockLocation.getBlock().setData(color.getData());
						continue;
					}
					else if(d <= (size-3)){
						if(pv != 1){
							blockLocation.getBlock().setType(Material.LAVA);
						}
						else{
							blockLocation.getBlock().setType(Material.WATER);
						}
						continue;
					}
					
					if(p <= 2){
						blockLocation.getBlock().setType(Material.DIRT);
					}
					
					if(p == 3){
						blockLocation.getBlock().setType(Material.IRON_ORE);
					}
					
					if(p == 4){
						blockLocation.getBlock().setType(Material.GOLD_ORE);
					}
					
					if(p == 5){
						blockLocation.getBlock().setType(Material.DIAMOND_ORE);
					}
					
					if(p == 6){
						blockLocation.getBlock().setType(Material.REDSTONE_ORE);
					}
					
					if(p == 7){
						blockLocation.getBlock().setType(Material.COAL_ORE);
					}
					
					if(p == 8){
						blockLocation.getBlock().setType(Material.EMERALD_ORE);
					}
					
					if(p == 9){
						blockLocation.getBlock().setType(Material.QUARTZ_ORE);
					}
					
					
				}
			}
		}
		
		if(t == 0){
			Location tree = loc.clone().add(0, size, 0);
			world.generateTree(tree, TreeType.JUNGLE);
		}
		
		if(t == 1){
			Location wather = loc.clone().add(0, (size-1), 0);
			wather.getBlock().setType(Material.WATER);
			Location startCanne = wather.clone().add(0, 1, 0);
			
			BlockFace[] bfs = new BlockFace[]{BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
			
			for(int i = 0; i<bfs.length; i++){
				Location canneLocation = startCanne.getBlock().getRelative(bfs[i]).getLocation();
				
				if(canneLocation.equals(startCanne)){
					continue;
				}
				
				for(int h = 0; h<3; h++){
					if(canneLocation.getBlock().getType().equals(Material.AIR)){
						canneLocation.getBlock().setType(Material.SUGAR_CANE_BLOCK);
					}
					else{
						break;
					}
					canneLocation.setY(canneLocation.getBlockY()+1);
				}
				
				
			}
		}
		
	}
}
