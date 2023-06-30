import java.util.ArrayList;



/**
 * 
 * @author Oluwasegun Bewaji
 * 
 * THis class is part of the class of algorithms that will be used to solve various MDP models
 * 
 * The value iteration solver extends the generic solver class which store all common variables and methods 
 * used by all learning and dynamic programming algorithms
 * 
 * It must be constructed with at the very least a Markov Decision Process to solve. 
 *
 */


public class PortfolioMDPRLModelSolver extends SolverAlgorithm{




	/**
	 * In the base instance the MDP is described by the CapitalMarketsIEnvironment class
	 * @param mdp
	 */
	double vStar;
	MDPPortfolioChoiceAction bestPolicy = null;




	public PortfolioMDPRLModelSolver(QLearningInvestorCalculationEngine mdpCalcEngine) {
		// TODO Auto-generated constructor stub
		super(mdpCalcEngine);
	}


	public PortfolioMDPRLModelSolver(MarkovDecisionProcess mdp, MDPModelInputParameters mdpInputParameters) {
		// TODO Auto-generated constructor stub
		super(mdp, mdpInputParameters);
	}


	@Override
	public ArrayList<ArrayList<Double>> getDeltaHistory(){
		return this.deltaHistory;
	}



	// execute one trial
	@Override
	public void runTrial() {

		runEpoch();
	}

	public void runEpoch() {

		switch(learningMethod) {

		case VALUE_ITERATION : {
			this.valueIteration.solveMarkovDecisionProcess();
			this.valueIteration.computeOptimalMarkovDecisionProcessPolicy();
			break;
		}

		}
	} 


}
