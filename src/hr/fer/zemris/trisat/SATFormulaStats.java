package hr.fer.zemris.trisat;

public class SATFormulaStats {
	protected SATFormula formula = null;
	protected BitVector assignment = null;
	protected double [] post; 
	private boolean isSatisfied = false;
	private int numberOfSatisfied = 0;
	private double percentageConstantUp = 0.01;
	private double percentageConstantDown = 0.1;
	
	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		post = new double[formula.getNumberOfClauses()];
	}
	
	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		isSatisfied = true;
		numberOfSatisfied = 0;
				
		for (int i = 0; i < formula.getNumberOfClauses(); i++) {
			if (formula.getClause(i).isSatisfied(assignment))
			{
				if (updatePercentages) {
					post[i] += (1-post[i])* percentageConstantUp;
				}
				numberOfSatisfied++;
			}
			else {
				if (updatePercentages) {
					post[i] += (0-post[i])*percentageConstantDown;
				}
					isSatisfied = false;
				
			}	
		}
	}
	
	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
		
	}
	
	public boolean isSatisfied() {
		return isSatisfied;
	}
	
	public double getPercentageBonus() {
		double PercentageBonus = 0;
		
		for (int i = 0; i < post.length; i++) {
			PercentageBonus += post[i];
		}
		
		return PercentageBonus;
	}
	
	public double getPercentage(int index) {
		return post[index];
	}

}
