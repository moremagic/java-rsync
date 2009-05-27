package net.collegeman.rsync;

import java.util.List;
import java.util.zip.Adler32;

import junit.framework.TestCase;

import org.springframework.util.CollectionUtils;

public class RsyncTests extends TestCase {

	public void testAdlerForRollingChecksum() {
		Adler32 adler = new Adler32();
		
		String phrase = "There can be only one.";
		byte[] inPhrase = phrase.getBytes();
		
		byte[] oneToTen = new byte[10];
		for (int i=0; i<10; i++)
			oneToTen[i] = inPhrase[i];
		
		adler.update(oneToTen);
		long checksum = adler.getValue();
	}
	
	public void testWeakRollingChecksum() {
		String phrase = "There can be only one.";
		String incongruent = "Unless there are two.";
		assertEquals(Rsync.getWeakRollingChecksum(phrase.getBytes()), Rsync.getWeakRollingChecksum(phrase.getBytes()));
		assertFalse(Rsync.getWeakRollingChecksum(phrase.getBytes()).equals(Rsync.getWeakRollingChecksum(incongruent.getBytes())));
	}
	
	public void testRelativePathAnalysis() {
		String root = "c:\\test\\path";
		String absolutePath = "c:\\test\\path\\to\\file.txt";
		assertEquals(Rsync.Checksums.getRelativePath(root, absolutePath), "/to/file.txt");
		
		root = "/test/path";
		absolutePath = "/test/path/to/file.txt";
		assertEquals(Rsync.Checksums.getRelativePath(root, absolutePath), "/to/file.txt");
	}
	
}
