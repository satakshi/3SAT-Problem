package hr.fer.zemris.trisat;

public class Random {
	protected static int seed = 999;
	
	public Random(int seedRandomizer) {
		seed = seedRandomizer;
	}
	
	public Random() {}
	
	public static boolean[] Generate(int n) {
		boolean [] random = new boolean[n];
		
		for (int i = 0; i < n; i++) {
			random[i] = (int)((Math.random() * seed * 10) % 2) == 0;
		}
		return random;
	}
}
