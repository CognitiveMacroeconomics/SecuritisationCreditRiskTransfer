



public class MDPDecisionModel {
	
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
	int numberOfEpisodes = 1000;
	double epsilon = 10*(0.01/100);//
	double gamma = 0.1;//increasing gamma increases the time/number of iterations required to find a value 
	double accuracyThreshold =  0.0001;

	ValueIterationSolver solverValIteration;
	
	TransitionProbabilitiesEngine tpEngine = null;
	boolean stochastic = false;
	
	MDPDecisionModelConfiguration infiniteMDPAAProbConfig;
	MDPInvestorAgent lapf;
	
	
	MDPDecisionModelConfiguration infiniteMDPAAProbConfig1;
	MDPInvestorAgent lapf1;

	
	MDPDecisionModelConfiguration infiniteMDPAAProbConfig2;
	MDPInvestorAgent lapf2;

	
	MDPDecisionModelConfiguration infiniteMDPAAProbConfig3;
	MDPInvestorAgent lapf3;


	
	public MDPDecisionModel(){
		
		/**
		 * CapitalMarketsConfiguration(TransitionProbabilitiesEngine tpEngine, String prdEndString, boolean portWeightChoice,
		 * 			boolean shtSell, boolean lnCost, boolean stochastic, int numberOfEpisodes, 
		 * 			double riskFreeRate, double astWghtIncr, double cInWghtIncr, double maxPermChgInWght, double discntFactor, 
		 * 			double lnCostFactor, double QuadCostFactor)
		 */
		infiniteMDPAAProbConfig = new MDPDecisionModelConfiguration(tpEngine, decisionAnalysisPeriodEndString, portfolioWeightChoiceModel, shortSelling, 
				linearCostFunction, stochastic, numberOfEpisodes, riskFreeRate, assetWieghtIncrements, changeInWeightIncrement, maximumPermissbleChangeInWeight, 
				discountfactor, linearfactor, quadraticfactor);

		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	MDPDecisionModel model = new MDPDecisionModel();
	MDPDecisionModel model1 = new MDPDecisionModel();
	MDPDecisionModel model2 = new MDPDecisionModel();
	MDPDecisionModel model3 = new MDPDecisionModel();
	
	model.infiniteMDPAAProbConfig.mdpModelContext.getActionList(model.infiniteMDPAAProbConfig.mdpModelContext.getCurrentState());
	model1.infiniteMDPAAProbConfig.mdpModelContext.getActionList(model1.infiniteMDPAAProbConfig.mdpModelContext.getCurrentState());
	model2.infiniteMDPAAProbConfig.mdpModelContext.getActionList(model2.infiniteMDPAAProbConfig.mdpModelContext.getCurrentState());
	model3.infiniteMDPAAProbConfig.mdpModelContext.getActionList(model3.infiniteMDPAAProbConfig.mdpModelContext.getCurrentState());

	
	System.out.println("Total Number of Possible Actions: " + 
			model.infiniteMDPAAProbConfig.mdpModelContext.getActionsPopulationSet().size());
	
	System.out.println("Total Number of Possible Actions: " + 
			model.infiniteMDPAAProbConfig.mdpModelContext.getActionsPopulationSet().size());
	
	System.out.println("Total Number of Possible Actions at State 1: " + 
			model.infiniteMDPAAProbConfig.mdpModelContext.getActionList(model.infiniteMDPAAProbConfig.mdpModelContext.getCurrentState()).size());
	
	
	model.lapf = new MDPInvestorAgent(model.infiniteMDPAAProbConfig.mdpModelContext);
	model1.lapf = new MDPInvestorAgent(model1.infiniteMDPAAProbConfig.mdpModelContext);
	model2.lapf = new MDPInvestorAgent(model2.infiniteMDPAAProbConfig.mdpModelContext);
	model3.lapf = new MDPInvestorAgent(model2.infiniteMDPAAProbConfig.mdpModelContext);
	
	System.out.println("State 1: " + model.lapf.getCurrentState());
//	lapf.choose();
	System.out.println("State 2: " + model.lapf.getCurrentState());
	
	System.out.println("State 1: " + model1.lapf.getCurrentState());
//	lapf.choose();
	System.out.println("State 2: " + model1.lapf.getCurrentState());
	
	System.out.println("State 1: " + model2.lapf.getCurrentState());
//	lapf.choose();
	System.out.println("State 2: " + model2.lapf.getCurrentState());
	
	System.out.println("State 1: " + model3.lapf.getCurrentState());
//	lapf.choose();
	System.out.println("State 2: " + model3.lapf.getCurrentState());
	
//	infiniteMDPAAProbConfig.environment.getReward(infiniteMDPAAProbConfig.environment.getCurrentState(), 
//			infiniteMDPAAProbConfig.environment.successorState(infiniteMDPAAProbConfig.environment.getCurrentState(), 
//					infiniteMDPAAProbConfig.environment.getActionsPopulationSet().get(0)),
//			infiniteMDPAAProbConfig.environment.getActionsPopulationSet().get(0));

	
	model.solverValIteration = new ValueIterationSolver(model.infiniteMDPAAProbConfig.mdpModelContext);
	model.solverValIteration.setError(model.epsilon);
//	model.solverValIteration.updateMDP(model.infiniteMDPAAProbConfig.mdpModelContext);
	model.solverValIteration.evaluatePolicy(model.numberOfEpisodes, model.gamma, model.accuracyThreshold);
	
	
	model1.solverValIteration = new ValueIterationSolver(model1.infiniteMDPAAProbConfig.mdpModelContext);
	model1.solverValIteration.setError(model1.epsilon);
//	model.solverValIteration.updateMDP(model.infiniteMDPAAProbConfig.mdpModelContext);
	model1.solverValIteration.evaluatePolicy(model1.numberOfEpisodes, model1.gamma, model1.accuracyThreshold);
	
	model2.solverValIteration = new ValueIterationSolver(model2.infiniteMDPAAProbConfig.mdpModelContext);
	model2.solverValIteration.setError(model2.epsilon);
//	model.solverValIteration.updateMDP(model.infiniteMDPAAProbConfig.mdpModelContext);
	model2.solverValIteration.evaluatePolicy(model2.numberOfEpisodes, model2.gamma, model2.accuracyThreshold);
	
	model3.solverValIteration = new ValueIterationSolver(model3.infiniteMDPAAProbConfig.mdpModelContext);
	model3.solverValIteration.setError(model3.epsilon);
//	model.solverValIteration.updateMDP(model.infiniteMDPAAProbConfig.mdpModelContext);
	model3.solverValIteration.evaluatePolicy(model3.numberOfEpisodes, model3.gamma, model3.accuracyThreshold);

	
	}
}
	
