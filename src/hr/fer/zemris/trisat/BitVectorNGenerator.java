package hr.fer.zemris.trisat;

import java.util.Iterator;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {
	
	protected BitVector assignment ;
	protected int currentPosition = 0;
	
	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;
	}
	
	// Vraća iterator koji na svaki next() računa sljedećeg susjeda
	@Override
	public Iterator<MutableBitVector> iterator() {
		
		Iterator<MutableBitVector> it = new Iterator<MutableBitVector>() {
			
			@Override
			public boolean hasNext() {
				if ( currentPosition < assignment.getSize()) {
					return true;
				}
				currentPosition = 0;
				return false;
			}

			@Override
			public MutableBitVector next() {
				if ( currentPosition < assignment.getSize()) {
					MutableBitVector c = assignment.copy();
					c.set(currentPosition, !assignment.get(currentPosition));
					currentPosition++;
					return c;
				}
				return null;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		return it;
	}
	
	// Vraća kompletno susjedstvo kao jedno polje
	public MutableBitVector[] createNeighborhood() {
		BitVectorNGenerator gen = new BitVectorNGenerator(assignment);
		MutableBitVector[] neighborhood = new MutableBitVector[assignment.getSize()];
		int index = 0;
		
		for(MutableBitVector n : gen) {
			neighborhood[index++] = n;
		}

		return neighborhood;
	}
}
