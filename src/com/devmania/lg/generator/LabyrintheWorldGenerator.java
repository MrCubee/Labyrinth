package com.devmania.lg.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import com.devmania.lg.data.Lab;
import com.devmania.lg.data.Labyrinthes;

public class LabyrintheWorldGenerator extends ChunkGenerator{
	private int dimX, dimZ;
	private Lab labyrinthes;
	
	private Location enter;
	private Location exit;
	
	public LabyrintheWorldGenerator(int dimX, int dimZ) {
		Bukkit.getLogger().info("Create Labyrinthe World ...");
		this.dimX = dimX;
		this.dimZ = dimZ;
		
		Bukkit.getLogger().info("Create Labyrinthe Map ...");
		labyrinthes = new Labyrinthes(dimX, dimZ).getLabyrinthe();
		
	}
	
	private double getOctave(World world, int x, int z){
    	Random random2 = new Random(world.getSeed());
    	
    	SimplexOctaveGenerator octave = new SimplexOctaveGenerator(random2, 8);
    	octave.setScale(1/64.0);
    	double noise = octave.noise(x, z, 0.5, 0.5)*12;
    	
    	return noise;
	}
	
	@Override
	public boolean canSpawn(World world, int x, int z) {
		return true;
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		TreeGenerator tree = new TreeGenerator(this);
		SpawnerGenerator spawner = new SpawnerGenerator(this);
		SphereGenerator sphere = new SphereGenerator(this);
		GrassGenerator grass = new GrassGenerator(this);
		List<BlockPopulator> block = new ArrayList<BlockPopulator>();
		block.add(spawner);
		block.add(tree);
		block.add(grass);
		block.add(sphere);
		return block;
	}
	
    public static int xyzToByte(int x, int y, int z) {
    	return (x * 16 + z) * 256 + y;
    }
    
    @Override
    public byte[] generate(World world, Random random, int chunkx, int chunkz) {
    	
    	if(enter == null){
    		
        	double max = -999999;
        	for(int xm = 0; xm<16; xm++){
        		for(int zm = 0; zm<16; zm++){
        			double noise = (world.getMaxHeight()/4)+getOctave(world, xm+(labyrinthes.getEnter().getX()*16), zm+(labyrinthes.getEnter().getY()*16));
        			if(noise > max){
        				max = noise;
        			}
        		}
        	}
    		Bukkit.getLogger().info("generate Labyrinthe enter location ...");
    		enter = new Location(world, ((labyrinthes.getEnter().getX()*16)+(16/2)), max, ((labyrinthes.getEnter().getY()*16)+(16/2)));
    	}
    	
    	if(exit == null){
        	double max = -999999;
        	for(int xm = 0; xm<16; xm++){
        		for(int zm = 0; zm<16; zm++){
        			double noise = (world.getMaxHeight()/4)+getOctave(world, xm+(labyrinthes.getExit().getX()*16), zm+(labyrinthes.getExit().getY()*16));
        			if(noise > max){
        				max = noise;
        			}
        		}
        	}
    		Bukkit.getLogger().info("generate Labyrinthe exit location ...");
    		exit = new Location(world, ((labyrinthes.getExit().getX()*16)+(16/2)), max, ((labyrinthes.getExit().getY()*16)+(16/2)));
    	}
    	
    	
    	String value = labyrinthes.getValue(chunkx, chunkz);
    	byte[] result = new byte[65536];
    	
    	if(value == null){
    		//Bukkit.getLogger().info("generate Labyrinthe Void ...");
    		return result;
    	}
    	
    	if(value.equals("#")){
    		//Bukkit.getLogger().info("generate Labyrinthe Wall ...");
    		//result[xyzToByte(8, 8, 8)] = (byte) Material.BEDROCK.getId();
    		generateWall(result, chunkx, chunkz, world);
    		return result;
    	}
    	
    	if(value.equals(" ")){
    		//Bukkit.getLogger().info("generate Labyrinthe Path ...");
    		//result[xyzToByte(8, 8, 8)] = (byte) Material.WOOL.getId();
    		generatePath(result, chunkx, chunkz, world);
    		return result;
    	}
    	
    	if(value.equals("E")){
    		//Bukkit.getLogger().info("generate Labyrinthe Enter ...");
    		//result[xyzToByte(8, 8, 8)] = (byte) Material.EMERALD_BLOCK.getId();
    		generateEnter(result, chunkx, chunkz, world);
    		return result;
    	}
    	
    	if(value.equals("S")){
    		//Bukkit.getLogger().info("generate Labyrinthe Exit ...");
    		//result[xyzToByte(8, 8, 8)] = (byte) Material.REDSTONE_BLOCK.getId();
    		generateExit(result, chunkx, chunkz, world);
    		return result;
    	}
    	
    	//Bukkit.getLogger().info("generate finish.");
    	
    	return result;
    }
    
    public void generateWall(byte[] startedByte, int chunkx, int chunkz, World world){
    	for(int y = 0; y< world.getMaxHeight(); y++){
    		for(int z = 0; z < 16; z++){
        		for(int x = 0; x < 16; x++){
        			Random r = new Random();
        			int b = r.nextInt(3);
        			
        			if(b == 0){
        				startedByte[xyzToByte(x, y, z)] = (byte) Material.STONE.getId();
        			}
        			
        			if(b == 1){
        				startedByte[xyzToByte(x, y, z)] = (byte) Material.COBBLESTONE.getId();
        			}
        			
        			if(b == 2){
        				startedByte[xyzToByte(x, y, z)] = (byte) Material.MOSSY_COBBLESTONE.getId();
        			}
        			
        		}
    		}
    	}
    }
    
    public void generatePath(byte[] startedByte, int chunkx, int chunkz, World world){
    	
    	
		for(int z = 0; z < 16; z++){
    		for(int x = 0; x < 16; x++){
    			startedByte[xyzToByte(x, 0, z)] = (byte) Material.BEDROCK.getId();
    			
    			double noise = getOctave(world, x+(chunkx*16), z+(chunkz*16));
    			//Bukkit.getLogger().info("Surface: "+noise+" x: "+(x+(chunkx*16))+" z: "+(z+(chunkz*16)));
    			
    			
    			if(noise > world.getMaxHeight()){
    				noise = world.getMaxHeight();
    			}
    	    	
    	    	for(int y = 0; y< (world.getMaxHeight()/4)+noise; y++){
    	    		if(y < ((world.getMaxHeight()/4)-1)){
    	    			startedByte[xyzToByte(x, y, z)] = (byte) Material.STONE.getId();
    	    		}
    	    		else if((y+1) >=( (world.getMaxHeight()/4)+noise)){
    	    			startedByte[xyzToByte(x, y, z)] = (byte) Material.GRASS.getId();
    	    		}
    	    		else{
    	    			startedByte[xyzToByte(x, y, z)] = (byte) Material.DIRT.getId();
    	    		}
    	    	}
    			
    		}
		}
    	
//		for(int z = 0; z < 16; z++){
//    		for(int x = 0; x < 16; x++){
//    			
//    			startedByte[xyzToByte(x, (world.getMaxHeight()/2)-1, z)] = (byte) Material.GRASS.getId();
//    			
//    		}
//		}
    	
		for(int z = 0; z < 16; z++){
    		for(int x = 0; x < 16; x++){
    			startedByte[xyzToByte(x, (world.getMaxHeight()-1), z)] = (byte) Material.BARRIER.getId();
    		}
		}
    	
    }
    
    
    public void generateEnter(byte[] startedByte, int chunkx, int chunkz, World world){
    	
    	double max = -999999;
    	for(int xm = 0; xm<16; xm++){
    		for(int zm = 0; zm<16; zm++){
    			double noise = (world.getMaxHeight()/4)+getOctave(world, xm+(chunkx*16), zm+(chunkz*16));
    			if(noise > max){
    				max = noise;
    			}
    		}
    	}
    	
    	for(int y = 0; y< max; y++){
    		for(int z = 0; z < 16; z++){
        		for(int x = 0; x < 16; x++){
        			startedByte[xyzToByte(x, y, z)] = (byte) Material.BEDROCK.getId();
        		}
    		}
    	}
		for(int z = 0; z < 16; z++){
    		for(int x = 0; x < 16; x++){
    			startedByte[xyzToByte(x, (world.getMaxHeight()-1), z)] = (byte) Material.BARRIER.getId();
    		}
		}
    }
    
    public void generateExit(byte[] startedByte, int chunkx, int chunkz, World world){
    	
    	double max = -999999;
    	for(int xm = 0; xm<16; xm++){
    		for(int zm = 0; zm<16; zm++){
    			double noise = (world.getMaxHeight()/4)+getOctave(world, xm+(chunkx*16), zm+(chunkz*16));
    			if(noise > max){
    				max = noise;
    			}
    		}
    	}
    	
    	for(int y = 0; y< max; y++){
    		for(int z = 0; z < 16; z++){
        		for(int x = 0; x < 16; x++){
        			if(y == 0){
        				startedByte[xyzToByte(x, y, z)] = (byte) Material.BEDROCK.getId();
        				continue;
        			}
        			startedByte[xyzToByte(x, y, z)] = (byte) Material.OBSIDIAN.getId();
        		}
    		}
    	}
		for(int z = 0; z < 16; z++){
    		for(int x = 0; x < 16; x++){
    			startedByte[xyzToByte(x, (world.getMaxHeight()-1), z)] = (byte) Material.BARRIER.getId();
    		}
		}
    }
    
    public Location getEnter() {
		return enter;
	}
    
    public Location getExit() {
		return exit;
	}
    
    public int getDimX() {
		return dimX;
	}
    
    public int getDimZ() {
		return dimZ;
	}
    
    public Lab getLabyrinthes() {
		return labyrinthes;
	}
    
    public static void save(Object object, File file) throws IOException{
    	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
    	out.writeObject(object);
    	out.close();
    }
    
    public static Object load(File file) throws FileNotFoundException, IOException, ClassNotFoundException{
    	ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
    	Object o = in.readObject();
    	return o;
    }
	
}
