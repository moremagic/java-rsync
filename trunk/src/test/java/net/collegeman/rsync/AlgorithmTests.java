package net.collegeman.rsync;

import junit.framework.TestCase;

public class AlgorithmTests extends TestCase {

	public void testRelativePathAnalysis() {
		String root = "c:\\test\\path";
		String absolutePath = "c:\\test\\path\\to\\file.txt";
		assertEquals(Algorithm.Checksums.getRelativePath(root, absolutePath), "/to/file.txt");
		
		root = "/test/path";
		absolutePath = "/test/path/to/file.txt";
		assertEquals(Algorithm.Checksums.getRelativePath(root, absolutePath), "/to/file.txt");
	}
	
}
