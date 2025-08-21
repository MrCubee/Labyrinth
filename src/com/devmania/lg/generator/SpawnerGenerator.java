package com.devmania.lg.generator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.CreatureType;
import org.bukkit.generator.BlockPopulator;

public class SpawnerGenerator extends BlockPopulator{
	
	private LabyrintheWorldGenerator labyrinthe;
	
	public SpawnerGenerator(LabyrintheWorldGenerator labyrinthe) {
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
		
		int c = rc.nextInt(7);
		
		if(c != 0){
			return;
		}
		
		Random rx = new Random();
		Random rz = new Random();
		
		int x = rx.nextInt(16);
		int z = rz.nextInt(16);
		
		Location loc = new Location(world, ((chunk.getX()*16)+x), (world.getMaxHeight()-2),((chunk.getZ()*16)+z));
		
		
		while(true){
			if(loc.getBlock().getType().equals(Material.AIR)){
				loc.setY(loc.getBlockY()-1);
			}
			else{
				loc.setY(loc.getBlockY()-1);
				break;
			}
		}
		
		
		loc.getBlock().setType(Material.MOB_SPAWNER);
		
		CreatureSpawner spawner = (CreatureSpawner) loc.getBlock().getState();
		
		Random r = new Random();
		int t = r.nextInt(11);
		
		if(t == 0){
			spawner.setCreatureType(CreatureType.CHICKEN);
		}
		
		if(t == 1){
			spawner.setCreatureType(CreatureType.PIG);
		}
		
		if(t == 2){
			spawner.setCreatureType(CreatureType.COW);
		}
		
		if(t == 3){
			spawner.setCreatureType(CreatureType.CREEPER);
		}
		
		if(t == 4){
			spawner.setCreatureType(CreatureType.ZOMBIE);
		}
		
		if(t == 5){
			spawner.setCreatureType(CreatureType.SLIME);
		}
		
		if(t == 6){
			spawner.setCreatureType(CreatureType.SHEEP);
		}
		
		if(t == 7){
			spawner.setCreatureType(CreatureType.CAVE_SPIDER);
		}
		
		if(t == 8){
			spawner.setCreatureType(CreatureType.SPIDER);
		}
		
		if(t == 9){
			spawner.setCreatureType(CreatureType.SKELETON);
		}
		
		if(t == 10){
			spawner.setCreatureType(CreatureType.GHAST);
		}
		
		spawner.update();
		
		
	}
	
}
