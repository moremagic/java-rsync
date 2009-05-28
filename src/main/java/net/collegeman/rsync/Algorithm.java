package net.collegeman.rsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.Adler32;

import org.springframework.util.Assert;

/**
 * This class implements the rsync algorithm in unit-testable chunks.
 * 
  * @author Aaron Collegeman aaron@collegeman.net
 * @since 1.0.0
 */
public class Algorithm {

	public static Adler32 adler = new Adler32();
	
	public static MessageDigest md5;
	
	static {
		try {
			md5 = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			throw new RsyncException("Failed to intialize md5 message digest format", e);
		}
	}
	
	public static Checksums getChecksums(Settings settings, File f, int blockSize) {
		Assert.notNull(settings.getLocalRoot());
		Assert.notNull(f);
		
		Checksums checksums = new Checksums(settings.getLocalRoot(), f.getAbsolutePath());
		byte[] buffer = new byte[blockSize];
		
		try {
			InputStream stream = new FileInputStream(f);
			while (stream.read(buffer) >= 0) {
				checksums.weak.add(getWeakRollingChecksum(buffer));
				checksums.strong.add(getStrongChecksum(buffer));
			}
		} catch (FileNotFoundException e) {
			throw new RsyncException(String.format("Failed to open [%s] for reading", f.getAbsolutePath()), e);
		} catch (IOException e) {
			throw new RsyncException(String.format("Failed to read data from [%s]", f.getAbsolutePath()), e);
		}
		
		return checksums;
	}
	
	public static Long getWeakRollingChecksum(byte[] bytes) {
		adler.reset();
		adler.update(bytes);
		return adler.getValue();
	}
	
	public static byte[] getStrongChecksum(byte[] bytes) {
		md5.reset();
		return md5.digest(bytes);
	}
	
	public static class Checksums implements Serializable {
		private static final long serialVersionUID = 6753617860527533955L;
		private static Pattern normalizeSlashes = Pattern.compile("[/\\\\]+");
		private static Pattern lastSlash = Pattern.compile("/$");
		
		private List<Long> weak = new ArrayList<Long>();
		private List<Object> strong = new ArrayList<Object>();
		private String relativePath;
		
		public Checksums(String root, String absolutePath) {
			this.relativePath = getRelativePath(root, absolutePath);
		}
		
		public static String getRelativePath(String root, String absolutePath) {
			root = lastSlash.matcher(normalizeSlashes.matcher(root).replaceAll("/")).replaceAll("");
			absolutePath = normalizeSlashes.matcher(absolutePath).replaceAll("/");
			return absolutePath.replaceAll("^"+root, "");
		}
		
		public List<Long> getWeak() {
			return weak;
		}
		
		public List<Object> getStrong() {
			return strong;
		}
		
		public String getRelativePath() {
			return relativePath;
		}
	}
	
	
}
