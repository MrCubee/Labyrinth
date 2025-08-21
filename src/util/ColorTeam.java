package util;

import org.bukkit.ChatColor;

public enum ColorTeam {
	AQUA(ChatColor.AQUA),
	BLUE(ChatColor.BLUE),
	DARK_AQUA(ChatColor.DARK_AQUA),
	DARK_BLUE(ChatColor.DARK_BLUE),
	DARK_GRAY(ChatColor.DARK_GRAY),
	DARK_GREEN(ChatColor.DARK_GREEN),
	DARK_PURPLE(ChatColor.DARK_PURPLE),
	DARK_RED(ChatColor.DARK_RED),
	GOLD(ChatColor.GOLD),
	GRAY(ChatColor.GRAY),
	GREEN(ChatColor.GREEN),
	RED(ChatColor.RED),
	WHITE(ChatColor.WHITE),
	YELLOW(ChatColor.YELLOW);
	private ChatColor color;
	private ColorTeam(ChatColor color) {
		this.color = color;
	}
	public ChatColor getColor() {
		return color;
	}
}
