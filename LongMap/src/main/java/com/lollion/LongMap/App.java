package com.lollion.LongMap;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		LongMap<String> lm = new LongMapImpl<>(20);
		lm.put(35, "Лоллион");
		System.out.println(lm.get(35));
		// System.out.println(lm.isEmpty());
		System.out.println(lm.containsValue("Лоллион"));
		lm.clear();
		// lm.remove(35);
		// System.out.println(lm.get(35));
		System.out.println(lm.isEmpty());
		// System.out.println("finished");

	}
}
