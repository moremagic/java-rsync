package net.collegeman.rsync;

import junit.framework.TestCase;

public class RsyncTests extends TestCase {

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
