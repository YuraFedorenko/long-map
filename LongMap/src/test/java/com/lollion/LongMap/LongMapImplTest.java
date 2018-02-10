/**
 * 
 */
package com.lollion.LongMap;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * @author Лоллион
 *
 */
class LongMapImplTest {

	LongMap<String> lm = new LongMapImpl<>(20);

	/**
	 * Test method for
	 * {@link com.lollion.LongMap.LongMapImpl#put(long, java.lang.Object)}.
	 */
	@Test
	void testPutLongV() {
		lm.put(987654322, "Lollion");
		Assert.assertTrue(lm.size() == 1);
	}

	/**
	 * Test method for {@link com.lollion.LongMap.LongMapImpl#get(long)}.
	 */
	@Test
	void testGetLong() {
		lm.put(987654322, "Lollion");
		Assert.assertTrue(lm.get(987654322).equals("Lollion"));
	}

	/**
	 * Test method for {@link com.lollion.LongMap.LongMapImpl#remove(long)}.
	 */
	@Test
	void testRemove() {
		lm.put(987654322, "Lollion");
		lm.remove(987654322);
		Assert.assertTrue(lm.size() == 0);
	}

	/**
	 * Test method for {@link com.lollion.LongMap.LongMapImpl#isEmpty()}.
	 */
	@Test
	void testIsEmpty() {
		lm.put(987654322, "Lollion");
		Assert.assertTrue(lm.size() == 1);
		Assert.assertFalse(lm.size() == 0);
	}

	/**
	 * Test method for {@link com.lollion.LongMap.LongMapImpl#containsKey(long)}.
	 */
	@Test
	void testContainsKey() {
		lm.put(987654322, "Lollion");
		Assert.assertTrue(lm.containsKey(987654322));
	}

	/**
	 * Test method for
	 * {@link com.lollion.LongMap.LongMapImpl#containsValue(java.lang.Object)}.
	 */
	@Test
	void testContainsValue() {
		lm.put(987654322, "Lollion");
		Assert.assertTrue(lm.containsValue("Lollion"));
	}

	/**
	 * Test method for {@link com.lollion.LongMap.LongMapImpl#clear()}.
	 */
	@Test
	void testClear() {
		lm.put(987654322, "Lollion");
		lm.clear();
		Assert.assertTrue(lm.size() == 0);
	}

}
