package jbloomer;

public class BloomFilter {
	private final byte[] bytes;
	private final int bits, funcs;
	
	public BloomFilter(int bits, int funcs, byte[] bytes) {
		this.bytes = bytes;
		this.bits = bits;
		this.funcs = funcs;
	}
	
	public BloomFilter(int bits, int funcs) {
		this(bits, funcs, new byte[align(bits)]);
	}
	
	public BloomFilter(double n, double p) {
		double m, k;
		
		m = -n*Math.log(p) / Math.pow(Math.log(2), 2);
		bits = (int)Math.ceil(m);
		k = m/n * Math.log(2);
		funcs = (int)Math.ceil(k);
		bytes = new byte[align(bits)];
	}
	
	public int getBitSize() {
		return bits;
	}
	
	public int getByteSize() {
		return bytes.length;
	}
	
	private static int align(int bits) {
		if ((bits % 8) == 0) {
			return bits / 8;
		}
		
		return 1 + bits / 8;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public void setBytes(byte[] src) {
		System.arraycopy(src, 0, bytes, 0, bytes.length);
	}
	
	public boolean contains(int key) {
		for (int i=0; i < funcs; i++) {
			int bit = hash(key, i);
			bit = Math.abs(bit % bits);
			
			if (check(bit) == false) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean add(int key) {
		boolean added = true;
		
		for (int i=0; i < funcs; i++) {
			int bit = hash(key, i);
			bit = Math.abs(bit % bits);
			added &= set(bit);
		}
		
		return added;
	}
	
	private boolean check(int n) {
		int byteOffset = n / 8;
		int relBit = n - (byteOffset * 8);
		int value = bytes[byteOffset] & 0xFF;
		int mask = 1 << relBit;
		return (value & mask) == mask;
	}
	
	private boolean set(int n) {
		int byteOffset = n / 8;
		int relBit = n - (byteOffset * 8);
		int mask = 1 << relBit;
		int value = bytes[byteOffset] & 0xFF;
		bytes[byteOffset] |= mask;
		return (value & mask) != mask;
	}
	
	public static final int hash(int key, int i) {
		key ^= i + H1;
		i ^= key + H2;
		return H3 ^ (key + i);
	}
	
	private static final int H1 = 0x9E0355C4;
	private static final int H2 = 0x1D744EBF;
	private static final int H3 = 0x2B412648;
}
