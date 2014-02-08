package hr.fer.zemris.trisat;

public class AlgorithmThreeNeighbour implements Comparable<AlgorithmThreeNeighbour> {
	private BitVector assignment;
	private Double Z = 0.0;
	
	public AlgorithmThreeNeighbour(BitVector assignment, double Z) {
		this.assignment = assignment;
		this.Z = Z;
	}
	
	public BitVector getBitVector()
	{
		return assignment;
	}
	
	public Double getZ() {
		return Z;
		
	}

	@Override
	public int compareTo(AlgorithmThreeNeighbour o) {
		return o.getZ().compareTo(this.Z);
	}

}
