package com.devmania.lg.generator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.devmania.lg.data.Lab;

public class TreeGenerator extends BlockPopulator{
	
	private LabyrintheWorldGenerator labyrinthe;
	
	public TreeGenerator(LabyrintheWorldGenerator labyrinthe) {
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
		Random rc = new Random();
		int c = rc.nextInt(3);
		if(c != 0){
			return;
		}
		Location loc = new Location(world, ((chunk.getX()*16)+8), (world.getMaxHeight()-2),((chunk.getZ()*16)+8));
		if((labyrinthe.getLabyrinthes().getValue(chunk.getX(), chunk.getZ()) == null) || (!labyrinthe.getLabyrinthes().getValue(chunk.getX(), chunk.getZ()).equals(" "))){
			return;
		}
		while(true){
			if(loc.getBlock().getType().equals(Material.AIR)){
				loc.setY(loc.getBlockY()-1);
			}
			else{
				if((!loc.getBlock().getType().equals(Material.GRASS)) && (!loc.getBlock().getType().equals(Material.DIRT))){
					return;
				}
				loc.setY(loc.getBlockY()+1);
				break;
			}
		}
		Random r = new Random();
		int n = r.nextInt(2);
		if(n != 0){
			world.generateTree(loc, TreeType.TREE);
		}
		else{
			world.generateTree(loc, TreeType.BIG_TREE);
		}
		
	}
	
}
