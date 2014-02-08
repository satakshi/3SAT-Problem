package hr.fer.zemris.trisat;

import java.util.Vector;

public class SATFormula {
	protected Vector <Clause> formulaVector = new Vector<Clause>();
	protected int numberOfVariables;
	
	public SATFormula(int numberOfVariables, Clause[] clauses) {
		for (int i = 0; i < clauses.length; i++) {
			formulaVector.add(clauses[i]);
		}
		this.numberOfVariables = numberOfVariables;
	}
	
	public int getNumberOfVariables() {
		return this.numberOfVariables;
	}
	
	public int getNumberOfClauses() {
		return formulaVector.size();
	}
	
	public Clause getClause(int index) {
		return formulaVector.elementAt(index);
	}
	
	public boolean isSatisfied(BitVector assignment) {
		boolean clause = true;
		
		for (int i = 0; i < formulaVector.size(); i++) {
			clause &= formulaVector.elementAt(i).isSatisfied(assignment);
		}
		return clause;
	}
	
	@Override
	public String toString() {
		return formulaVector.toString();
	}

}
