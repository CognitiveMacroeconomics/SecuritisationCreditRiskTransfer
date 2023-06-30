
import jas.engine.Sim;

import java.util.ArrayList;

public class MDPDummyAgent extends MDPMarkovAgent {

	double employeeWages;
	double contributionRate;
	double liquidityBuffer;
	double expectedContractualRequirements;
	double equityAssetReturn;
	double equityAssetReturnPrevious;
	double creditAssetReturn;
	double markToMarketLossesTraditionalAssets;
	double markToMarketLossesCreditAssets;
	double markToMarketLossesTotal;
	
	//stochastic path generating parameters where used
	private String agentDecisionType;
	
	
	//parameters for MDP Models
	private boolean modelIsMDP = false;
	
	private MDPDecisionEngine mdpDecisionEngine;//use when there is an MDP Model to be solved
	private StochasticProcessParameters stochParams;
	private StochaststicPathGenModel stochPathGenModel;
	
	private MDPModelInputParameters mdpModelInputParameters;
	private String decisionAnalysisPeriodEndString;
	private boolean portfolioWeightChoiceModel;
	private boolean shortSelling;
	private boolean stochasticStateTransitions;
	private boolean linearCostFunction;
	private double riskFreeRate;
	private double linearfactor;
	private double quadraticfactor;
	private double assetWieghtIncrements;
	private double maximumPermissbleChangeInWeight;
	private double changeInWeightIncrement;
	private int numberOfEpisodes;
	private double epsilonError;
	private double gammaDiscountFactor;
	private double accuracyThreshold;
	private double RLLearningRateAlpha;
	private double RLLearningLambda;
	private int RLLearningType;
	private int RLActionSelectionType;
	
	
	private double expectedEquityLoss;
	private double expectedCreditDefault;
	
	private MDPPortfolioChoiceAction bestMDPPolicy = null;
	ArrayList<ValueFunction> valueFunctions;
	private TransitionProbabilitiesEngine transitionProbabilitiesEngine = null;




	public MDPDummyAgent() {
		valueFunctions = new ArrayList<ValueFunction>();

	}

	public MDPDummyAgent(int iDRSSD, String lapfName, String sector, double totalAssets,
			double totalLiabilities, Economy econ, Enviroment env,
			GeoEconoPoliticalSpace world) {
		// TODO Auto-generated constructor stub

		super(iDRSSD, lapfName, sector, econ.getLapfRegulatorySolvancyRatio(),
				totalAssets, totalLiabilities, econ, env, world);
		valueFunctions = new ArrayList<ValueFunction>();
		this.optimalCreditAssetAllocation = 0.0;
		this.equityAssetReturnPrevious = 0;
	}

	public MDPDummyAgent(int iDRSSD, String lapfName, String sector, double totalAssets,
			Economy econ, Enviroment env, GeoEconoPoliticalSpace world) {
		// TODO Auto-generated constructor stub

		super(iDRSSD, lapfName, sector, econ.getLapfRegulatorySolvancyRatio(),
				totalAssets, econ, env, world);
		valueFunctions = new ArrayList<ValueFunction>();
		this.optimalCreditAssetAllocation = 0.0;
		this.equityAssetReturnPrevious = 0;
		
	}

	public MDPDummyAgent(double solvencyMarg, double totalAssets) {

		super(solvencyMarg, totalAssets);
		valueFunctions = new ArrayList<ValueFunction>();
		this.optimalCreditAssetAllocation = 0.0;
		this.equityAssetReturnPrevious = 0;

	}


	
	public void determineOptimalInvestmentPolicy(){
	
//		private MDPDecisionEngine mdpDecisionEngine;//use when there is an MDP Model to be solved
//		private StochasticProcessParameters stochParams;
//		private StochaststicPathGenModel stochPathGenModel;
//		
//		private MDPModelInputParameters mdpModelInputParameters;
		
		//this.mdpDecisionEngine = new MDPDecisionEngine();
		createStochaststicPathGenModel();
		if(Sim.getAbsoluteTime() % 5 == 0){
//		if(MarkoseDYangModel_V02NonJAS.currentAbsoluteSimTime == 1){
			if(this.getAgentDecisionType() == "MDP Learning"){
				if(this.getMDPModelInputParameters().isStochasticStateTransitions() == true){
					this.generateStochasticAssetPaths();
					this.mdpDecisionEngine.setDecisionModelFactors(this.transitionProbabilitiesEngine, 
							this.getMDPModelInputParameters());
						
				} else{
					double adj =  Rounding
							.roundFourDecimals(((1-this.cir_ThetaCreditAssetMeanValueExpectedDefaults)
									- this.getStochasticProcessParameters().getCir_ThetaCreditAssetMeanValue())
									/this.getStochasticProcessParameters().getCir_ThetaCreditAssetMeanValue());
					this.setExpectedCreditDefault(adj);
					System.out.println("adj " + adj);
					this.setExpectedEquityLoss(this.economy.getTraditionalAssetsSurvivalRate());
					System.out.println("this.economy.getTraditionalAssetsSurvivalRate() " 
					+ this.economy.getTraditionalAssetsSurvivalRate());
					System.out.println("this.getMDPModelInputParameters() " + this.getMDPModelInputParameters().toString());
					this.getMDPDecisionEngine().setDecisionModelFactors(this.getMDPModelInputParameters());
					System.out.println("this.getMDPModelInputParameters() " + this.getMDPModelInputParameters().toString());
				}
				this.getMDPDecisionEngine().updateDefaultExpectations(this.getExpectedEquityLoss(), this.getExpectedCreditDefault());
				this.getMDPDecisionEngine().initiateSolution();
				this.setValueFunctions(this.getMDPDecisionEngine().getValueFunctions());
				
				this.bestMDPPolicy = this.getMDPDecisionEngine().getBestPolicy();
				this.bestMDPPolicyHistory.add(bestMDPPolicy);
				this.setOptimalAssetWeights(this.bestMDPPolicy.getCashAssetWeightChoice(), 
						this.bestMDPPolicy.getEquityAssetWeightChoice(), this.bestMDPPolicy.getCreditAssetWeightChoice());
				System.out.println(this.bestMDPPolicy.label());
			}
		}
			
	}
	

	
	public void generateStochasticAssetPaths(){
		/*Note in the agent class this method uses the for-loop to generate the paths for the decision horizon
		/ it is from these paths that the transition probabilities are defined. The method will be modified slightly to 
		* allow the changing of the credit asset reset default rate. This change will actually occur in the generator model
		*/
		for(int i = 1; i <= this.environment.getInvestorHorizon(); i++){ 
			if(i >= (this.economy.getLoanResetPeriod() + 1)){
				//if the iteration is on or after the loan reset period then all generated paths are adjusted upwards or downwards by the change in default rate
				//			double adj =  Rounding
				//					.roundFourDecimals(Math.log((1-Economy.genericPostRateResetDafaultRate)/Economy.genericStartingDafaultRate));
				double adj =  Rounding
						.roundFourDecimals(((1-this.cir_ThetaCreditAssetMeanValueExpectedDefaults)
								- this.getStochasticProcessParameters().getCir_ThetaCreditAssetMeanValue())
								/this.getStochasticProcessParameters().getCir_ThetaCreditAssetMeanValue());
				boolean makeAdj = true;
				this.getStochasticPathGenModel().makeValueAdjustment(makeAdj, adj);
				this.setExpectedCreditDefault(adj);
				this.setExpectedEquityLoss(this.economy.getTraditionalAssetsSurvivalRate());
			} 
			this.getStochasticPathGenModel().generatePaths();
			if(i == this.environment.getInvestorHorizon()){
				//once the paths are generated for the entire decision horizon, transition probabilities are generated
				this.transitionProbabilitiesEngine = this.getStochasticPathGenModel().setTransitionProbability();
			}

		}
	}

	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MDP MODEL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	

	/**
	 * @return the expectedEquityLoss
	 */
	public double getExpectedEquityLoss() {
		return expectedEquityLoss;
	}






	/**
	 * @param expectedEquityLoss the expectedEquityLoss to set
	 */
	public void setExpectedEquityLoss(double expectedEquityLoss) {
		this.expectedEquityLoss = expectedEquityLoss;
	}


	/**
	 * @return the expectedCreditDefault
	 */
	public double getExpectedCreditDefault() {
		return expectedCreditDefault;
	}



	/**
	 * @param expectedCreditDefault the expectedCreditDefault to set
	 */
	public void setExpectedCreditDefault(double expectedCreditDefault) {
		this.expectedCreditDefault = expectedCreditDefault;
	}

	
	/**
	 * @return the mdpDecisionEngine
	 */
	public MDPDecisionEngine getMDPDecisionEngine() {
		return mdpDecisionEngine;
	}

	/**
	 * @param mdpDecisionEngine the mdpDecisionEngine to set
	 */
	public void setMDPDecisionEngine(MDPDecisionEngine mdpDecisionEngine) {
		this.mdpDecisionEngine = mdpDecisionEngine;
	}

	
	/**
	 * @param mdpDecisionEngine the mdpDecisionEngine to set
	 */
	public void createNewMDPDecisionEngine() {
		this.mdpDecisionEngine = new MDPDecisionEngine();
	}
	
	/**
	 * @return the stochParams
	 */
	public StochasticProcessParameters getStochasticProcessParameters() {
		return stochParams;
	}

	/**
	 * @param stochParams the stochParams to set
	 */
	public void setStochasticProcessParameters(StochasticProcessParameters stochParams) {
		this.stochParams = stochParams;
		this.createStochaststicPathGenModel();
	}
	
	public void createStochaststicPathGenModel() {
		// TODO Auto-generated method stub
		this.stochPathGenModel = new StochaststicPathGenModel(this.getStochasticProcessParameters(), getEntityID());
		
	}

	/**
	 * @return the stochPathGenModel
	 */
	public StochaststicPathGenModel getStochasticPathGenModel() {
		return stochPathGenModel;
	}

	/**
	 * @param stochPathGenModel the stochPathGenModel to set
	 */
	public void setStochasticPathGenModel(StochaststicPathGenModel stochPathGenModel) {
		this.stochPathGenModel = stochPathGenModel;
	}

	/**
	 * @return the modelIsMDP
	 */
	public boolean isMDPModel() {
		return modelIsMDP;
	}

	/**
	 * @param modelIsMDP the modelIsMDP to set
	 */
	public void setMDPModelBoolean(boolean modelIsMDP) {
		this.modelIsMDP = modelIsMDP;
	}
	
	
	public void setMDPModelParameters(String decisionAnalysisPeriodEndString,	boolean portfolioWeightChoiceModel,	boolean shortSelling,	
			boolean stochasticStateTransitions,	boolean linearCostFunction,	double riskFreeRate, double linearfactor,	int numberOfEpisodes,	
			double quadraticfactor,	double assetWieghtIncrements,	double changeInWeightIncrement,	double maximumPermissbleChangeInWeight,	
			double epsilonError,	double gammaDiscountFactor, double accuracyThreshold){
		
		this.decisionAnalysisPeriodEndString = decisionAnalysisPeriodEndString; //possible values "2003", "2007", "Full"
		this.portfolioWeightChoiceModel = portfolioWeightChoiceModel; //used to determine if model will use the choice of portfolio weights or the choice of changes in portfolio weights
		this.shortSelling = shortSelling;	
		this.stochasticStateTransitions = stochasticStateTransitions;
		this.linearCostFunction = linearCostFunction;
		this.riskFreeRate = riskFreeRate;
		this.linearfactor = linearfactor; //used as the constant parameter/multiplier for computing the linear transaction costs 
		this.quadraticfactor = quadraticfactor; //used as the constant parameter/multiplier for computing the quadratic transaction costs
		this.assetWieghtIncrements = assetWieghtIncrements; //increment used to create the potfolio weights that are used to define the MDP states 
		this.changeInWeightIncrement = changeInWeightIncrement; //represents the rate at which changes can be made to portfolio weights 
		this.maximumPermissbleChangeInWeight = maximumPermissbleChangeInWeight; 
			//value and policy iteration parameters 
		this.numberOfEpisodes = numberOfEpisodes;
		this.epsilonError = epsilonError;//
//		System.out.println("epsilonError " + this.epsilonError);
		this.gammaDiscountFactor = gammaDiscountFactor;//increasing gamma increases the time/number of iterations required to find a value 
		this.accuracyThreshold =  accuracyThreshold;
		
		
//		this.mdpModelInputParameters = new MDPModelInputParameters(this.decisionAnalysisPeriodEndString,	this.portfolioWeightChoiceModel,	this.shortSelling,	
//				this.stochasticStateTransitions,	this.linearCostFunction,	this.riskFreeRate, this.linearfactor,	this.numberOfEpisodes,	
//				this.quadraticfactor,	this.assetWieghtIncrements,	this.changeInWeightIncrement,	this.maximumPermissbleChangeInWeight,	
//				this.epsilonError,	this.gammaDiscountFactor, this.accuracyThreshold);
		
	}

	
	
	public MDPModelInputParameters getMDPModelInputParameters(){
		return this.mdpModelInputParameters;
		
	}

	/**
	 * @return the agentDecisionType
	 */
	public String getAgentDecisionType() {
		return agentDecisionType;
	}

	/**
	 * @param agentDecisionType the agentDecisionType to set
	 */
	public void setAgentDecisionType(String agentDecisionType) {
		this.agentDecisionType = agentDecisionType;
	}
	
	/**
	 * @return the valueFunctions
	 */
	public ArrayList<ValueFunction> getValueFunctions() {
		return valueFunctions;
	}

	/**
	 * @param valueFunctions the valueFunctions to set
	 */
	public void setValueFunctions(ArrayList<ValueFunction> valueFunctions) {
		this.valueFunctions = valueFunctions;
	}



}
