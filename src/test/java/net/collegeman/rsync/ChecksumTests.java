package net.collegeman.rsync;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.collegeman.rsync.checksum.RollingChecksum;

public class ChecksumTests extends TestCase {
	
	public void testRollingChecksum() {
		
		String phrase = "There can be only one";
		
		int blockSize = 10;
		
		RollingChecksum checksum = new RollingChecksum(phrase.getBytes(), blockSize);
		
		List<Long> checksums = new ArrayList<Long>();
		while (checksum.next())
			checksums.add(checksum.weak());
		
		for (int i=0; i<=phrase.length()-blockSize; i++) {
			String sub = phrase.substring(i, i+blockSize);
			assertEquals(RollingChecksum.sum(sub.getBytes()), checksums.get(i));
		}
		
	}

}
