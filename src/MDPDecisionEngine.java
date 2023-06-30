import java.util.ArrayList;

/**
 * This is the class that the market investment agents will interface with to derive their optimal portfolio allocation
 * The all required method calls to all Markov Decision Process related classes are accessible from this MDPDecisionEngine class
 * 
 *  The only required imports the MDP parameters and the TransitionProbabilityEngine which is where the state transition probabilities and state 
 *  returns are housed.
 *  
 *  To use this class, follow the four following steps from the client class:
 *  
 *  1: create an instance of the class 
 *  	MDPDecisionEngine();
 *  
 *  2: set the MDP Model Parameters
 *  	setDecisionModelFactors(args(0)............args(n)); this where all values and the TransitionProbabilityEngine are provided
 *  
 *  3: initiate the process to obtain the solution to the MDP
 *  	initiateSolution(); for now this is purely a value iteration process. At a future date the policy iteration will be added
 *  
 *  4: to extract the best policy given the provide MDP 
 *  	getBestPolicy(); 
 *  
 *  Note that the getDeltaHistory() method can be call to collate the delta values to depict conversion. getDeltaHistory returns a 2D ArrayList of doubles.
 *  Each of the contained ArrayLists relates to the computed delta for each of the Value Iterations.
 *  
 *  
 *  
 * @author Oluwasegun Bewaji
 *
 */



public class MDPDecisionEngine {
	
	String decisionAnalysisPeriodEndString = "2004"; //possible values "2003", "2007", "Full"
	double discountfactor = 0.05; //the discount factor at which future payoffs will be discounted 
	boolean portfolioWeightChoiceModel = true; //used to determine if model will use the choice of portfolio weights or the choice of changes in portfolio weights
	boolean shortSelling = false; //determines if short selling is permitted in the model. It is defaulted to False
	double riskFreeRate = 0.1;//the higher the return on risk free relative to the others the more likely the investor will shift the entire portfolio towards cash
	boolean linearCostFunction = false; //determines if the model uses a linear cost function. It is defaulted to False
	
	double linearfactor = 0.07; //used as the constant parameter/multiplier for computing the linear transaction costs 
	double quadraticfactor = 0.07; //used as the constant parameter/multiplier for computing the quadratic transaction costs
	double assetWieghtIncrements = 0.05; //increment used to create the potfolio weights that are used to define the MDP states 
	double changeInWeightIncrement = 0.05; //represents the rate at which changes can be made to portfolio weights 
	double maximumPermissbleChangeInWeight = 0.10; 
	
	

	//value and policy iteration parameters 
	int numberOfIterations = 1000;
	double epsilonError = 10*(0.01/100);//
	double gammaDiscountFactor = 0.1;//increasing gamma increases the time/number of iterations required to find a value 
	double accuracyThreshold =  0.0001;
	boolean stochasticStateTransitions = false;

	ValueIterationSolver solverValIteration;
	
	TransitionProbabilitiesEngine tpEngine = null;
	
	
	MDPDecisionModelConfiguration infiniteMDPAAProbConfig;
	ArrayList<MDPDecisionModelConfiguration> mdpConfigurationHistory;
	
	MDPPortfolioChoiceAction bestPolicy = null;

	
	public MDPDecisionEngine(){
		mdpConfigurationHistory = new ArrayList<MDPDecisionModelConfiguration>();
	}
	
	
	public void setDecisionModelFactors(TransitionProbabilitiesEngine tpEngine, String decisionAnalysisPeriodEndString,	int numberOfIterations, 
			boolean portfolioWeightChoiceModel,	boolean shortSelling,	boolean stochasticStateTransitions,	boolean linearCostFunction,	
			double riskFreeRate, double discountfactor,	double linearfactor, double quadraticfactor,	double assetWieghtIncrements, 
			double changeInWeightIncrement,	double maximumPermissbleChangeInWeight,	double epsilonError,
			double gammaDiscountFactor, double accuracyThreshold){
		
		
		this.decisionAnalysisPeriodEndString = decisionAnalysisPeriodEndString;
		this.discountfactor = discountfactor;
		this.portfolioWeightChoiceModel = portfolioWeightChoiceModel;
		this.shortSelling = shortSelling; 
		this.riskFreeRate = riskFreeRate;
		this.linearCostFunction = linearCostFunction;
		
		this.linearfactor = linearfactor; 
		this.quadraticfactor = quadraticfactor;
		this.assetWieghtIncrements = assetWieghtIncrements;
		this.changeInWeightIncrement = changeInWeightIncrement;
		this.maximumPermissbleChangeInWeight = maximumPermissbleChangeInWeight; 
		
		

		//value and policy iteration parameters 
		this.numberOfIterations = numberOfIterations;
		this.epsilonError = epsilonError;
		this.gammaDiscountFactor = gammaDiscountFactor;
		this.accuracyThreshold =  accuracyThreshold;
		this.stochasticStateTransitions = stochasticStateTransitions;
		
		initializeTransitionProbabilitiesEngine(tpEngine);
		initializeMDP();
		initiateMDPValueIterationSolver();
		
	}
	
	
	public void setDecisionModelFactors(TransitionProbabilitiesEngine tpEngine, MDPModelInputParameters mdpModelInputs){
		
		this.decisionAnalysisPeriodEndString = mdpModelInputs.getDecisionAnalysisPeriodEndString();
		this.portfolioWeightChoiceModel = mdpModelInputs.isPortfolioWeightChoiceModel();
		this.shortSelling = mdpModelInputs.isShortSelling(); 
		this.riskFreeRate = mdpModelInputs.getRiskFreeRate();
		this.linearCostFunction = mdpModelInputs.isLinearCostFunction();
		
		this.linearfactor = mdpModelInputs.getLinearfactor(); 
		this.quadraticfactor = mdpModelInputs.getQuadraticfactor();
		this.assetWieghtIncrements = mdpModelInputs.getAssetWieghtIncrements();
		this.changeInWeightIncrement = mdpModelInputs.getChangeInWeightIncrement();
		this.maximumPermissbleChangeInWeight = mdpModelInputs.getMaximumPermissbleChangeInWeight(); 
		
		

		//value and policy iteration parameters 
		this.numberOfIterations = mdpModelInputs.getnumberOfIterations();
		this.epsilonError = mdpModelInputs.getEpsilonError();
		this.gammaDiscountFactor = mdpModelInputs.getGammaDiscountFactor();
		this.accuracyThreshold =  mdpModelInputs.getAccuracyThreshold();
		this.stochasticStateTransitions = mdpModelInputs.isStochasticStateTransitions();
		
		initializeTransitionProbabilitiesEngine(tpEngine);
		initializeMDP();
		initiateMDPValueIterationSolver();
		
	}

	
	public void setDecisionModelFactors(MDPModelInputParameters mdpModelInputs){
		
		System.out.println("setDecisionModelFactors.... ");
		
		this.decisionAnalysisPeriodEndString = mdpModelInputs.getDecisionAnalysisPeriodEndString();
		this.portfolioWeightChoiceModel = mdpModelInputs.isPortfolioWeightChoiceModel();
		this.shortSelling = mdpModelInputs.isShortSelling(); 
		this.riskFreeRate = mdpModelInputs.getRiskFreeRate();
		this.linearCostFunction = mdpModelInputs.isLinearCostFunction();
		
		this.linearfactor = mdpModelInputs.getLinearfactor(); 
		this.quadraticfactor = mdpModelInputs.getQuadraticfactor();
		this.assetWieghtIncrements = mdpModelInputs.getAssetWieghtIncrements();
		this.changeInWeightIncrement = mdpModelInputs.getChangeInWeightIncrement();
		this.maximumPermissbleChangeInWeight = mdpModelInputs.getMaximumPermissbleChangeInWeight(); 
		
		

		//value and policy iteration parameters 
		this.numberOfIterations = mdpModelInputs.getnumberOfIterations();
		this.epsilonError = mdpModelInputs.getEpsilonError();
		this.gammaDiscountFactor = mdpModelInputs.getGammaDiscountFactor();
		this.accuracyThreshold =  mdpModelInputs.getAccuracyThreshold();
		this.stochasticStateTransitions = mdpModelInputs.isStochasticStateTransitions();
		
		initializeTransitionProbabilitiesEngine(null);
		initializeMDP();
		initiateMDPValueIterationSolver();
		
	}

	
	
	private void initializeTransitionProbabilitiesEngine(TransitionProbabilitiesEngine tpEngine){
//		System.out.println("initializeTransitionProbabilitiesEngine.... ");
		this.tpEngine = tpEngine;
//		System.out.println("initializeTransitionProbabilitiesEngine.... ");
	}
	
	private void initializeMDP(){
		
		/**
		 * CapitalMarketsConfiguration(TransitionProbabilitiesEngine tpEngine, String prdEndString, boolean portWeightChoice,
		 * 			boolean shtSell, boolean lnCost, boolean stochastic, int numberOfIterations, 
		 * 			double riskFreeRate, double astWghtIncr, double cInWghtIncr, double maxPermChgInWght, double discntFactor, 
		 * 			double lnCostFactor, double QuadCostFactor)
		 */
		System.out.println("initializeMDP.... ");
		this.infiniteMDPAAProbConfig = new MDPDecisionModelConfiguration(this.tpEngine, this.decisionAnalysisPeriodEndString, 
				this.portfolioWeightChoiceModel, 
				this.shortSelling, this.linearCostFunction, this.stochasticStateTransitions, 
				this.numberOfIterations, this.riskFreeRate, this.assetWieghtIncrements, this.changeInWeightIncrement, 
				this.maximumPermissbleChangeInWeight, this.discountfactor, this.linearfactor, this.quadraticfactor);
		System.out.println("New MDPDecisionModelConfiguration.... " + this.infiniteMDPAAProbConfig.toString());
//		mdpConfigurationHistory.add(infiniteMDPAAProbConfig);
		System.out.println("initializeMDP.... ");
		
	}
	
	/**
	 * When this method is invoked it automatically updates the epsilon with the user defined value
	 * the default value is 1.0-E6
	 */
	private void initiateMDPValueIterationSolver(){
//		System.out.println("initiateMDPValueIterationSolver.... ");
		this.solverValIteration = new ValueIterationSolver(this.infiniteMDPAAProbConfig.mdpModelContext);
		this.solverValIteration.setError(this.epsilonError);
//		System.out.println("initiateMDPValueIterationSolver.... ");
	}
	
	
	public void initiateSolution(){
		this.solverValIteration.evaluatePolicy(numberOfIterations, gammaDiscountFactor, accuracyThreshold);
	}
	
	
	public ValueIterationSolver getMDPValueIterationSolution(){
		return this.solverValIteration;
	}
	
	
	public  MDPPortfolioChoiceAction getBestPolicy(){
		bestPolicy = this.solverValIteration.extractBestPolicy();
		return bestPolicy;
	}
	
	
	public ArrayList<ArrayList<Double>> getDeltaHistory(){
		return this.solverValIteration.getDeltaHistory();
	}
	
	/**
	 * @return the valueFunctions
	 */
	public ArrayList<ValueFunction> getValueFunctions() {
		return this.solverValIteration.getValueFunctions();
	}



	public void updateDefaultExpectations(double expectedEquityLoss,
			double expectedCreditDefault) {
		// TODO Auto-generated method stub
		this.infiniteMDPAAProbConfig.mdpModelContext.setExpectedEquityLoss(expectedEquityLoss);
		this.infiniteMDPAAProbConfig.mdpModelContext.setExpectedCreditDefault(expectedCreditDefault);
	}
	


}
	
