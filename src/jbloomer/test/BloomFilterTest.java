package jbloomer.test;

import java.util.*;

import jbloomer.BloomFilter;
import junit.framework.TestCase;

public class BloomFilterTest extends TestCase {
	public void testRandomInts() {
		Random random = new Random();
		long seed = random.nextLong();
		
		for (int count=10; count < 1000 * 1000; count *= 2) {
			BloomFilter bloom = new BloomFilter(count, 0.001);
			
			random.setSeed(seed);
			
			for (int i=0; i < count; i++) {
				bloom.add(random.nextInt());
			}
			
			random.setSeed(seed);
			
			for (int i=0; i < count; i++) {
				assertTrue(bloom.contains(random.nextInt()));
			}
		}
	}
	
	public void testOneByOne() {
		Random random = new Random();
		
		for (int count=10; count < 1000 * 1000; count *= 2) {
			BloomFilter bloom = new BloomFilter(count, 0.001);
			
			for (int i=0; i < count; i++) {
				int key = random.nextInt();
				bloom.add(key);
				assertTrue(bloom.contains(key));
			}
		}
	}
	
	public void testHash() {
		Random random = new Random();
		HashSet<Integer> set = new HashSet<>();
		
		for (int funcs=2; funcs < 100; funcs++) {
			int key = random.nextInt();
			
			for (int i=0; i < funcs; i++) {
				int hash = BloomFilter.hash(key, i);
				assertFalse(set.contains(hash));
				set.add(hash);
			}
			
			set.clear();
		}
	}
}
