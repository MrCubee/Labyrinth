package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Copy {
//	 public static void copy(String src, String dst) {
//			File source = new File(src);
//			File destination = new File(dst);
//			FileInputStream sourceFile = null;
//			FileOutputStream destinationFile = null;
//			try {
//				destination.createNewFile();
//				sourceFile = new FileInputStream(source);
//				destinationFile = new java.io.FileOutputStream(destination);
//				byte buffer[] = new byte[512 * 1024];
//				int nbRead;
//				while ((nbRead = sourceFile.read(buffer)) != -1) {
//					destinationFile.write(buffer, 0, nbRead);
//				}
//				sourceFile.close();
//				destinationFile.close();
//			} catch (Exception e) {
//				Bukkit.getServer().getLogger().info("erreur de le copy du dossier");
//			}
//		}
	public static void copy(File sourceLocation, File targetLocation) throws IOException {
		if(!sourceLocation.exists()){
			return;
		}
		if (sourceLocation.isDirectory()) {
	        copyDirectory(sourceLocation, targetLocation);
	    } else {
	        copyFile(sourceLocation, targetLocation);
	    }
	}

	private static void copyDirectory(File source, File target) throws IOException {
	    if (!target.exists()) {
	        target.mkdir();
	    }

	    for (String f : source.list()) {
	        copy(new File(source, f), new File(target, f));
	    }
	}

	private static void copyFile(File source, File target) throws IOException {        
	    try (
	            InputStream in = new FileInputStream(source);
	            OutputStream out = new FileOutputStream(target)
	    ) {
	        byte[] buf = new byte[1024];
	        int length;
	        while ((length = in.read(buf)) > 0) {
	            out.write(buf, 0, length);
	        }
	    }
	}
	public static void delete(File source){
		if(!source.exists()){
			return;
		}
		if(source.isDirectory()){
			for(File f : source.listFiles()){
				delete(f);
			}
		}
		else{
			source.delete();
		}
	}
}
