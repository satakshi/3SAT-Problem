package hr.fer.zemris.trisat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TriSatSolver {
	
	protected static SATFormula satFormula;
	
	private static void parseProblemFile(String problemFileName) {
		File test = new File("\\tests\\" + problemFileName );

		Scanner input;
		
		try {
			input = new Scanner(test);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			return;
		}
		
		int numberOfVariables = 0, numberOfClauses, indexClauses = 0;
		Clause [] clauses = null;
		
		while(input.hasNextLine()) {
			String in = input.nextLine();
			Scanner lineScanner = new Scanner(in);
			
			if (lineScanner.hasNext()) {
				if(lineScanner.hasNext("c")) {
					continue;
				}
				else if (lineScanner.hasNext("p")) {
					lineScanner.next();
					lineScanner.next();
					numberOfVariables = lineScanner.nextInt();
					numberOfClauses = lineScanner.nextInt();
					clauses = new Clause[numberOfClauses];
				}
				else if (lineScanner.hasNext("%")) {
					break;
				}
				else {
					int [] literals = {lineScanner.nextInt(),lineScanner.nextInt(),lineScanner.nextInt()};
					Clause c = new Clause(literals);
					clauses[indexClauses++] = c;
				}
			}
			
			lineScanner.close();
		}
		
		satFormula = new SATFormula(numberOfVariables,clauses);
		input.close();
		
	}
	
	public static BitVector AlgorithmOne() {
		long numberOfCombinations = (long) Math.pow(2, satFormula.getNumberOfVariables());
		BitVector solution = null;
		
		for (long i = 0; i < numberOfCombinations; i++) {
			boolean [] combination = new boolean[satFormula.getNumberOfVariables()];
			
			long k = i;
			for (int j = satFormula.getNumberOfVariables()-1; j >= 0 ; j--){
				combination[j] = (k % 2 == 1);
				k /= 2;
			}
				
			BitVector assignment = new BitVector(combination);
			
			if ( satFormula.isSatisfied(assignment)) {
				System.out.println(assignment.toString());
				solution = assignment;
			}
		}
		
		return solution;
	}
	
	public static int fit(BitVector assignment, SATFormula satFormula) {
		int goodness = 0;
		for( int i = 0; i < satFormula.getNumberOfClauses(); i++) { 
			if ( satFormula.getClause(i).isSatisfied(assignment) ) {
				goodness++;
			}
		}
		return goodness;
	}
	
	public static void AlgorithmTwo(){
		BitVector solution = null;
		
		BitVector assignment = new BitVector(new Random(), satFormula.getNumberOfVariables());
		int counter = 0;
		
			while(counter < 100000) {
				
				if (satFormula.isSatisfied(assignment))
				{
					solution = assignment;
					break;
				}
				int goodness = fit(assignment, satFormula);

				BitVectorNGenerator generator = new BitVectorNGenerator(assignment);
				BitVector [] neighborhood = new BitVector[satFormula.getNumberOfVariables()];
				int neighboursIndex = 0, maxFit = 0;
			
				for ( BitVector bv : generator) {
					if ( fit(bv, satFormula) > goodness ) {
						maxFit = fit(bv, satFormula);
					}
				}
				for ( BitVector bv : generator) {
					if ( fit(bv, satFormula) == maxFit ) {
						neighborhood[neighboursIndex++] = bv;
					}
				}
				
				if (neighboursIndex == 0) {
					break;
				}
				else {
					assignment = neighborhood[(int)(Math.random()%neighboursIndex)];
				}
				counter++;
			}
		
		if (solution != null) { 
			System.out.println(solution.toString());
		}
		else {
			System.out.println("Solution not found! Execute again!");
		}
		
	}

	
	public static void AlgorithmThree(){
		BitVector assignment = new BitVector(new Random(), satFormula.getNumberOfVariables());
		BitVector solution = null;
		SATFormulaStats Stats = new SATFormulaStats(satFormula);
		int numberOfBest = 2, counter = 0;
		double percentageUnitAmount = 50;
		
		while(counter < 100000) {
			if(satFormula.isSatisfied(assignment) ) {
				solution = assignment;
				break;
			}
			Stats.setAssignment(assignment, true);

			BitVectorNGenerator generator = new BitVectorNGenerator(assignment);
			PriorityQueue<AlgorithmThreeNeighbour> neighborhood = new PriorityQueue<AlgorithmThreeNeighbour>();
			
			for ( BitVector bv : generator) {
				Stats.setAssignment(bv, false);
				double Z = 0.0f;
				
				for (int i = 0; i < satFormula.getNumberOfClauses(); i++ ) {
					if(satFormula.getClause(i).isSatisfied(bv)) {
						Z+= 1.0 + percentageUnitAmount * (1-Stats.getPercentage(i));
					}
					else {
						Z-= percentageUnitAmount * (1-Stats.getPercentage(i));
					}
				}
				
				neighborhood.add(new AlgorithmThreeNeighbour(bv,Z));
			}
			
			int k = (int)((Math.random()*1000)%numberOfBest);
			
			while(k > 0) {
				neighborhood.poll();
				k--;
			}
			assignment = neighborhood.peek().getBitVector();
			counter++;
		}
		
		if (solution != null) {
			System.out.println(solution);
		}
		else {
			System.out.println("Solution not found! Execute again!");
		}
		
	}
	
	public static void main(String[] args) {
		int algorithmNumber = Integer.parseInt(args[0]);
		String problemFileName = args[1];
		
		parseProblemFile(problemFileName);
		
		if (algorithmNumber == 1) {
			AlgorithmOne();
		}
		else if (algorithmNumber == 2) {
			AlgorithmTwo();
		}
		else if ( algorithmNumber == 3) {
			AlgorithmThree();
		}
		else {
			System.out.println("Error, you can only use algorithms 1, 2 and 3.");
		}
	}

}
