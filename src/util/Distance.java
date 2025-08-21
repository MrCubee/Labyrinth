package util;

import org.bukkit.Location;

public class Distance {
	public static int distance(Location l1, Location l2){
		int xl = l1.getBlockX();
		int zl = l1.getBlockZ();
		int yl = l1.getBlockY();
		int xs = l2.getBlockX();
		int zs = l2.getBlockZ();
		int ys = l2.getBlockY();
		int xx = xl - xs;
		int zz = zl - zs;
		int yy = yl - ys;
		yy = yy*yy;
		xx = xx*xx;
		zz = zz*zz;
		int xz = xx+zz+yy;
		int distance = (int) Math.sqrt(xz);
		return distance;
	}
}
