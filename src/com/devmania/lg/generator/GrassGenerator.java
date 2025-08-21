package com.devmania.lg.generator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class GrassGenerator extends BlockPopulator {
	
	private LabyrintheWorldGenerator labyrinthe;
	
	public GrassGenerator(LabyrintheWorldGenerator labyrinthe) {
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
		
		Random nb = new Random();
		int n = nb.nextInt(5);
		
		for(int i = 0; i < n; i++){
			Random rx = new Random();
			Random rz = new Random();
			int x = rx.nextInt(16);
			int z = rz.nextInt(16);
			
			Location loc = new Location(world, ((chunk.getX()*16)+x), (world.getMaxHeight()-2),((chunk.getZ()*16)+z));
			
			for(int y = 0; y<(world.getMaxHeight()-2); y++){
				boolean air = false;
				for(int nz = -2; nz<2; nz++){
					for(int nx = -2; nx<=2; nx++){
						Location block = loc.clone().add(nx, 0, nz).subtract(0, y, 0);
						if(!block.getBlock().getType().isSolid()){
							air = true;
						}
						Location underBlock = block.clone().subtract(0, 1, 0);
						
						if(underBlock.getBlock().getType().equals(Material.GRASS)){
							Random r = new Random();
							
							int nr = r.nextInt(3);
							
							if(nr == 0){
								Random data = new Random();
								int ndata = data.nextInt(2)+1;
								block.getBlock().setType(Material.LONG_GRASS);
								block.getBlock().setData((byte) ndata);
							}
							
							if(nr == 1){
								block.getBlock().setType(Material.YELLOW_FLOWER);
							}
							
							if(nr == 2){
								Random data = new Random();
								int ndata = data.nextInt(9);
								block.getBlock().setType(Material.RED_ROSE);
								block.getBlock().setData((byte) ndata);
							}
						}
						
					}
				}
				
				if(!air){
					break;
				}
				
			}
			
		}
		
		
		
		
	}

}
