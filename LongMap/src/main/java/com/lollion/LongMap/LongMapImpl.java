package com.lollion.LongMap;

import java.util.Arrays;

public class LongMapImpl<V> implements LongMap<V> {

	private static final int MAX_HASH_TABLE_SIZE = 1 << 24;
	private static final int MAX_HASH_TABLE_SIZE_WITH_FILL_FACTOR = 1 << 20;

	private static final long[] byteTable;
	private static final long HSTART = 0xBB40E64DA205B064L;
	private static final long HMULT = 7664345821815920749L;

	static {
		byteTable = new long[256];
		long h = 0x544B2FBACAAF1684L;
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 31; j++) {
				h = (h >>> 7) ^ h;
				h = (h << 11) ^ h;
				h = (h >>> 10) ^ h;
			}
			byteTable[i] = h;
		}
	}
	private int maxValues;
	private int[] table;
	private int[] nextPtrs;
	private long[] hashValues;
	private V[] elements;
	private int nextHashValuePos;
	private int hashMask;
	private long size;

	@SuppressWarnings("unchecked")
	public LongMapImpl(int maxElements) {
		int sz = 128;
		int desiredTableSize = maxElements;
		if (desiredTableSize < MAX_HASH_TABLE_SIZE_WITH_FILL_FACTOR) {
			desiredTableSize = desiredTableSize * 4 / 3;
		}
		desiredTableSize = Math.min(desiredTableSize, MAX_HASH_TABLE_SIZE);
		while (sz < desiredTableSize) {
			sz <<= 1;
		}
		this.maxValues = maxElements;
		this.table = new int[sz];
		this.nextPtrs = new int[maxValues];
		this.hashValues = new long[maxValues];
		this.elements = (V[]) new Object[sz];
		Arrays.fill(table, -1);
		this.hashMask = sz - 1;
	}

	@Override
	public long size() {
		return size;
	}

	public V put(CharSequence key, V val) {
		return put(hash(key), val);
	}

	@Override
	public V put(long key, V value) {
		int hc = (int) key & hashMask;
		int[] table = this.table;
		int k = table[hc];
		if (k != -1) {
			int lastk;
			do {
				if (hashValues[k] == key) {
					V old = elements[k];
					elements[k] = value;
					return old;
				}
				lastk = k;
				k = nextPtrs[k];
			} while (k != -1);
			k = nextHashValuePos++;
			nextPtrs[lastk] = k;
		} else {
			k = nextHashValuePos++;
			table[hc] = k;
		}
		if (k >= maxValues) {
			throw new IllegalStateException("Hash table full (size " + size + ", k " + k);
		}
		hashValues[k] = key;
		nextPtrs[k] = -1;
		elements[k] = value;
		size++;
		return null;
	}

	@Override
	public V get(long key) {
		int hc = (int) key & hashMask;
		int[] table = this.table;
		int k = table[hc];
		if (k != -1) {
			do {
				if (hashValues[k] == key) {
					return elements[k];
				}
				k = nextPtrs[k];
			} while (k != -1);
		}
		return null;

	}

	@Override
	public V remove(long key) {
		int hc = (int) key & hashMask;
		int[] table = this.table;
		int k = table[hc];
		if (k != -1) {
			do {
				if (hashValues[k] == key) {

					elements[k] = null;
					size--;

				}
				k = nextPtrs[k];
			} while (k != -1);
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		if (this.size() > 0)
			return false;
		return true;
	}

	@Override
	public boolean containsKey(long key) {
		for (long k : hashValues) {
			if (k == key)
				return true;
		}
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		for (V val : this.elements) {
			if (value.equals(val)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public long[] keys() {
		return hashValues;
	}

	@Override
	public V[] values() {
		return elements;
	}

	@Override
	public void clear() {
		this.elements = null;
		this.hashValues = null;
		size = 0;
	}

	public V get(CharSequence hash) {
		return get(hash(hash));
	}

	public static long hash(CharSequence cs) {
		if (cs == null)
			return 1L;
		long h = HSTART;
		final long hmult = HMULT;
		final long[] ht = byteTable;
		for (int i = cs.length() - 1; i >= 0; i--) {
			char ch = cs.charAt(i);
			h = (h * hmult) ^ ht[ch & 0xff];
			h = (h * hmult) ^ ht[(ch >>> 8) & 0xff];
		}
		return h;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LongMapImpl other = (LongMapImpl) obj;
		if (!Arrays.equals(hashValues, other.hashValues))
			return false;
		return true;
	}

}
