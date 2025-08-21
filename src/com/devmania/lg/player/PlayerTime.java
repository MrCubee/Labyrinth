package com.devmania.lg.player;

import java.io.IOException;
import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;


import com.devmania.lg.LabyrintheGenerator;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class PlayerTime extends Thread {

	private Player player;
	private int seconde;
	private int newPoint, speratatePoint, givePoint;

	public PlayerTime(Player player) {
		this.player = player;
		speratatePoint = 300;
		givePoint = 20;
		seconde = LabyrintheGenerator.getInstance().getConfigPlayer().getInt(player.getUniqueId().toString() + ".time");
		newPoint = seconde+speratatePoint;
	}

	@Override
	public void run() {
		while ((player != null) && (player.isOnline())) {
			if ((!player.getGameMode().equals(GameMode.SURVIVAL))
					&& (!(player.getGameMode().equals(GameMode.ADVENTURE)))) {
				continue;
			}
			
			if(seconde == newPoint){
				newPoint = seconde+speratatePoint;
				try {
					PlayerData playerData = LabyrintheGenerator.getInstance().getPlayer_data().get(player);
					
					playerData.setPoint(playerData.getPoint()+givePoint);
					
					
					int point = LabyrintheGenerator.getInstance().getScoreConfig().getInt(player.getUniqueId().toString()+".point");
					LabyrintheGenerator.getInstance().getScoreConfig().set(player.getUniqueId().toString()+".point", point+givePoint);
					LabyrintheGenerator.getInstance().getScoreConfig().save(LabyrintheGenerator.getInstance().getScoreFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			setPlayerList(player, ChatColor.GOLD+"mc.developpermania.com:25569", ChatColor.GRAY+converteToTime(getTime()));

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			seconde++;
		}

		LabyrintheGenerator.getInstance().getConfigPlayer().set(player.getUniqueId().toString() + ".time", seconde);
		try {
			LabyrintheGenerator.getInstance().getConfigPlayer()
					.save(LabyrintheGenerator.getInstance().getConfigPlayerFile());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int[] getTime() {
		int[] time = new int[3];

		int s = seconde;
		int minutes = s / 60;
		int heures = minutes / 60;
		
		s = s - (minutes * 60);
		minutes = minutes - (heures * 60);

		time[0] = s;
		time[1] = minutes;
		time[2] = heures;

		return time;
	}

	public String converteToTime(int[] time) {
		if (time.length != 3) {
			return null;
		}

		String result = intToString(time[2]) + ":" + intToString(time[1]) + ":" + intToString(time[0]);

		return result;

	}

	public String intToString(int value) {
		String result = "";
		if (value < 10) {
			result = "0";
		}
		result = result + "" + value;

		return result;
	}

	public static void setPlayerList(Player player, String header, String footer) {
		IChatBaseComponent hj = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent fj = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = (PacketPlayOutPlayerListHeaderFooter) constructHeaderAndFooterPacket(
				hj, fj);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	private static Object constructHeaderAndFooterPacket(Object header, Object footer) {
		try {
			Object packet = PacketPlayOutPlayerListHeaderFooter.class.newInstance();
			if (header != null) {
				Field field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("a");
				field.setAccessible(true);
				field.set(packet, header);
				field.setAccessible(false);
			}
			if (footer != null) {
				Field field = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
				field.setAccessible(true);
				field.set(packet, footer);
				field.setAccessible(false);
			}
			return packet;
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

}
