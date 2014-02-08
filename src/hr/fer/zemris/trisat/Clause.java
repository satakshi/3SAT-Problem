package hr.fer.zemris.trisat;

import java.util.Vector;

public class Clause {
	protected Vector<Integer> clauseVector;
	
	public Clause(int[] indexes) {
		clauseVector = new Vector<Integer>(indexes.length);
		
		for(int i = 0; i < indexes.length; i++ ) {
			clauseVector.add(indexes[i]);
		}
	}
	
	public int getSize() {
		return clauseVector.size();
	}
	
	public int getLiteral(int index) {
		return clauseVector.elementAt(index);
		
	}
	
	public boolean isSatisfied(BitVector assignment) {
		boolean clause = false;
		
		for (int i = 0; i < clauseVector.size(); i++) {
			if (clauseVector.elementAt(i) < 0) {
				clause |= !assignment.get(Math.abs(clauseVector.elementAt(i))-1);
			}
			else {
				clause |= assignment.get(clauseVector.elementAt(i)-1);
			}
		}
		
		return clause;
	}
	
	@Override
	public String toString() {
		return clauseVector.toString();
	}
}
