package com.devmania.lg.data;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class Lab{
	private List<List<String>> colones;
	public Lab(List<List<String>> colones) {
		this.colones = colones;
	}
	
	public void spawn(Location loc){
		Location b = null;
		for(List<String> c : colones){
			b = loc.clone();
			for(String ligne : c){
				b.setX(b.getBlockX()+1);
				Location s = b.clone().add(0, 1 , 0);
				if(ligne.equals("#")){
					s.getBlock().setType(Material.BEDROCK);
					b.getBlock().setType(Material.BEDROCK);
				}
				if(ligne.equals(" ")){
					s.getBlock().setType(Material.AIR);
					b.getBlock().setType(Material.AIR);
				}
				if(ligne.equals("E")){
					s.getBlock().setType(Material.EMERALD_BLOCK);
					b.getBlock().setType(Material.EMERALD_BLOCK);
				}
				if(ligne.equals("S")){
					s.getBlock().setType(Material.REDSTONE_BLOCK);
					b.getBlock().setType(Material.REDSTONE_BLOCK);
				}
			}
			loc.setZ(loc.getBlockZ()+1);
		}
	}
	
	public Coordinates getEnter(){
		int z = 0;
		for(List<String> c : colones){
			int x = 0;
			for(String ligne : c){
				if(ligne.equals("E")){
					return new Coordinates(x, z);
				}
				x++;
			}
			z++;
		}
		
		return null;
	}
	
	public Coordinates getExit(){
		int z = 0;
		for(List<String> c : colones){
			int x = 0;
			for(String ligne : c){
				if(ligne.equals("S")){
					return new Coordinates(x, z);
				}
				x++;
			}
			z++;
		}
		
		return null;
	}
	
	public String getValue(int x, int z){
		
		if((x<0) || (z<0)){
			return null;
		}
		
		if(colones.size() <= z){
			return null;
		}
		if(colones.get(z).size() <= x){
			return null;
		}
		
		return colones.get(z).get(x);
	}
	
	public List<List<String>> getColones() {
		return colones;
	}
}
