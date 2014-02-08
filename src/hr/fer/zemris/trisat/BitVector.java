package hr.fer.zemris.trisat;

import java.util.Vector;

public class BitVector {
	protected Vector <Boolean> bitVector;
	
	public BitVector(Random rand, int numberOfBits) {
		boolean[] bits = rand.Generate(numberOfBits);
		bitVector = new Vector<Boolean>(numberOfBits);
		
		for (int i = 0; i < numberOfBits; i++) {
			bitVector.add(bits[i]);
		}
	}
	
	public BitVector(boolean [] bits) {
		bitVector = new Vector<Boolean>(bits.length);
		
		for (int i = 0; i < bits.length; i++) {
			bitVector.add(bits[i]);
		}
	}
	
	public BitVector(int n) {
		bitVector = new Vector<Boolean>(n);
	}
	
	public boolean get(int index) {
		return bitVector.get(index);
	}
	
	public int getSize() {
		return bitVector.size();
	}

	@Override
	public String toString() {
		String s = "";
		
		for(Boolean b : bitVector) {
			s += b.booleanValue() ? "1" : "0";
		}
		return s;
	}

	public MutableBitVector copy()
	{
		boolean [] bits = new boolean [bitVector.size()];
		
		for(int i = 0; i < bitVector.size(); i++) {
			bits[i] = bitVector.elementAt(i);
		}
		
		return new MutableBitVector(bits);
	}
	
}
