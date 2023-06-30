
public class MDPModelInputParameters {
	
	
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

	private int numberOfIterations;
	
	private int numberOfDecisionEpochs;

	private double epsilonError;

	private double gammaDiscountFactor;

	private double accuracyThreshold;
	
	private double RLLearningRateAlpha = 0.1;
	private double RLLearningLambda = 0.1;
	private int RLLearningType = 0;
	private int RLActionSelectionType = 0;
	boolean absoluteReturns = true;

	
	
	public MDPModelInputParameters(String decisionAnalysisPeriodEndString,	boolean portfolioWeightChoiceModel,	boolean shortSelling,	
				boolean stochasticStateTransitions,	boolean linearCostFunction,	double riskFreeRate, double linearfactor,	int numberOfIterations,	
				int numberOfDecisionEpochs,
				double quadraticfactor,	double assetWieghtIncrements,	double changeInWeightIncrement,	double maximumPermissbleChangeInWeight,	
				double epsilonError,	double gammaDiscountFactor, double accuracyThreshold, double RLLearningRateAlpha, double RLLearningLambda,
				int RLLearningType, int RLActionSelectionType){
		
		
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
		this.numberOfIterations = numberOfIterations;
		this.setNumberOfDecisionEpochs(numberOfDecisionEpochs);
		this.epsilonError = epsilonError;//
		this.gammaDiscountFactor = gammaDiscountFactor;//increasing gamma increases the time/number of iterations required to find a value 
		this.accuracyThreshold =  accuracyThreshold;
		this.setRLLearningRateAlpha(RLLearningRateAlpha);
		this.setRLLearningLambda(RLLearningLambda);
		this.setRLLearningType(RLLearningType);
		this.setRLActionSelectionType(RLActionSelectionType);
		
	}



	/**
	 * @return the stochParams
	 */
	public StochasticProcessParameters getStochParams() {
		return stochParams;
	}



	/**
	 * @param stochParams the stochParams to set
	 */
	public void setStochParams(StochasticProcessParameters stochParams) {
		this.stochParams = stochParams;
	}



	/**
	 * @return the stochPathGenModel
	 */
	public StochaststicPathGenModel getStochPathGenModel() {
		return stochPathGenModel;
	}



	/**
	 * @param stochPathGenModel the stochPathGenModel to set
	 */
	public void setStochPathGenModel(StochaststicPathGenModel stochPathGenModel) {
		this.stochPathGenModel = stochPathGenModel;
	}



	/**
	 * @return the mdpModelInputParameters
	 */
	public MDPModelInputParameters getMdpModelInputParameters() {
		return mdpModelInputParameters;
	}



	/**
	 * @param mdpModelInputParameters the mdpModelInputParameters to set
	 */
	public void setMdpModelInputParameters(
			MDPModelInputParameters mdpModelInputParameters) {
		this.mdpModelInputParameters = mdpModelInputParameters;
	}



	/**
	 * @return the decisionAnalysisPeriodEndString
	 */
	public String getDecisionAnalysisPeriodEndString() {
		return decisionAnalysisPeriodEndString;
	}



	/**
	 * @param decisionAnalysisPeriodEndString the decisionAnalysisPeriodEndString to set
	 */
	public void setDecisionAnalysisPeriodEndString(
			String decisionAnalysisPeriodEndString) {
		this.decisionAnalysisPeriodEndString = decisionAnalysisPeriodEndString;
	}



	/**
	 * @return the portfolioWeightChoiceModel
	 */
	public boolean isPortfolioWeightChoiceModel() {
		return portfolioWeightChoiceModel;
	}



	/**
	 * @param portfolioWeightChoiceModel the portfolioWeightChoiceModel to set
	 */
	public void setPortfolioWeightChoiceModel(boolean portfolioWeightChoiceModel) {
		this.portfolioWeightChoiceModel = portfolioWeightChoiceModel;
	}



	/**
	 * @return the shortSelling
	 */
	public boolean isShortSelling() {
		return shortSelling;
	}



	/**
	 * @param shortSelling the shortSelling to set
	 */
	public void setShortSelling(boolean shortSelling) {
		this.shortSelling = shortSelling;
	}



	/**
	 * @return the stochasticStateTransitions
	 */
	public boolean isStochasticStateTransitions() {
		return stochasticStateTransitions;
	}



	/**
	 * @param stochasticStateTransitions the stochasticStateTransitions to set
	 */
	public void setStochasticStateTransitions(boolean stochasticStateTransitions) {
		this.stochasticStateTransitions = stochasticStateTransitions;
	}



	/**
	 * @return the linearCostFunction
	 */
	public boolean isLinearCostFunction() {
		return linearCostFunction;
	}



	/**
	 * @param linearCostFunction the linearCostFunction to set
	 */
	public void setLinearCostFunction(boolean linearCostFunction) {
		this.linearCostFunction = linearCostFunction;
	}



	/**
	 * @return the riskFreeRate
	 */
	public double getRiskFreeRate() {
		return riskFreeRate;
	}



	/**
	 * @param riskFreeRate the riskFreeRate to set
	 */
	public void setRiskFreeRate(double riskFreeRate) {
		this.riskFreeRate = riskFreeRate;
	}



	/**
	 * @return the linearfactor
	 */
	public double getLinearfactor() {
		return linearfactor;
	}



	/**
	 * @param linearfactor the linearfactor to set
	 */
	public void setLinearfactor(double linearfactor) {
		this.linearfactor = linearfactor;
	}



	/**
	 * @return the quadraticfactor
	 */
	public double getQuadraticfactor() {
		return quadraticfactor;
	}



	/**
	 * @param quadraticfactor the quadraticfactor to set
	 */
	public void setQuadraticfactor(double quadraticfactor) {
		this.quadraticfactor = quadraticfactor;
	}



	/**
	 * @return the assetWieghtIncrements
	 */
	public double getAssetWieghtIncrements() {
		return assetWieghtIncrements;
	}



	/**
	 * @param assetWieghtIncrements the assetWieghtIncrements to set
	 */
	public void setAssetWieghtIncrements(double assetWieghtIncrements) {
		this.assetWieghtIncrements = assetWieghtIncrements;
	}



	/**
	 * @return the maximumPermissbleChangeInWeight
	 */
	public double getMaximumPermissbleChangeInWeight() {
		return maximumPermissbleChangeInWeight;
	}



	/**
	 * @param maximumPermissbleChangeInWeight the maximumPermissbleChangeInWeight to set
	 */
	public void setMaximumPermissbleChangeInWeight(
			double maximumPermissbleChangeInWeight) {
		this.maximumPermissbleChangeInWeight = maximumPermissbleChangeInWeight;
	}



	/**
	 * @return the changeInWeightIncrement
	 */
	public double getChangeInWeightIncrement() {
		return changeInWeightIncrement;
	}



	/**
	 * @param changeInWeightIncrement the changeInWeightIncrement to set
	 */
	public void setChangeInWeightIncrement(double changeInWeightIncrement) {
		this.changeInWeightIncrement = changeInWeightIncrement;
	}



	/**
	 * @return the numberOfIterations
	 */
	public int getnumberOfIterations() {
		return numberOfIterations;
	}



	/**
	 * @param numberOfIterations the numberOfIterations to set
	 */
	public void setnumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}



	/**
	 * @return the epsilonError
	 */
	public double getEpsilonError() {
		return epsilonError;
	}



	/**
	 * @param epsilonError the epsilonError to set
	 */
	public void setEpsilonError(double epsilonError) {
		this.epsilonError = epsilonError;
	}



	/**
	 * @return the gammaDiscountFactor
	 */
	public double getGammaDiscountFactor() {
		return gammaDiscountFactor;
	}



	/**
	 * @param gammaDiscountFactor the gammaDiscountFactor to set
	 */
	public void setGammaDiscountFactor(double gammaDiscountFactor) {
		this.gammaDiscountFactor = gammaDiscountFactor;
	}



	/**
	 * @return the accuracyThreshold
	 */
	public double getAccuracyThreshold() {
		return accuracyThreshold;
	}



	/**
	 * @param accuracyThreshold the accuracyThreshold to set
	 */
	public void setAccuracyThreshold(double accuracyThreshold) {
		this.accuracyThreshold = accuracyThreshold;
	}
	
	
	/**
	 * @return the rLLearningRateAlpha
	 */
	public double getRLLearningRateAlpha() {
		return RLLearningRateAlpha;
	}



	/**
	 * @param rLLearningRateAlpha the rLLearningRateAlpha to set
	 */
	public void setRLLearningRateAlpha(double rLLearningRateAlpha) {
		RLLearningRateAlpha = rLLearningRateAlpha;
	}



	/**
	 * @return the rLLearningLambda
	 */
	public double getRLLearningLambda() {
		return RLLearningLambda;
	}



	/**
	 * @param rLLearningLambda the rLLearningLambda to set
	 */
	public void setRLLearningLambda(double rLLearningLambda) {
		RLLearningLambda = rLLearningLambda;
	}



	/**
	 * @return the rLLearningType
	 */
	public int getRLLearningType() {
		return RLLearningType;
	}



	/**
	 * @param rLLearningType the rLLearningType to set
	 */
	public void setRLLearningType(int rLLearningType) {
		RLLearningType = rLLearningType;
	}



	/**
	 * @return the RLActionSelectionType
	 */
	public int getRLActionSelectionType() {
		return RLActionSelectionType;
	}



	/**
	 * @param RLActionSelectionType the RLActionSelectionType to set
	 */
	public void setRLActionSelectionType(
			int RLActionSelectionType) {
		this.RLActionSelectionType = RLActionSelectionType;
	}



	/**
	 * @return the numberOfDecisionEpochs
	 */
	public int getNumberOfDecisionEpochs() {
		return numberOfDecisionEpochs;
	}



	/**
	 * @param numberOfDecisionEpochs the numberOfDecisionEpochs to set
	 */
	public void setNumberOfDecisionEpochs(int numberOfDecisionEpochs) {
		this.numberOfDecisionEpochs = numberOfDecisionEpochs;
	}

	
	public void setAbsoluteReturns(boolean absRet) {
		// TODO Auto-generated method stub
		this.absoluteReturns = absRet;
	}
	
	public boolean isAbsoluteReturns() {
		// TODO Auto-generated method stub
		return this.absoluteReturns;
	}

	@Override
	public String toString(){
		
		return 
				("decisionAnalysisPeriodEndString: " + this.decisionAnalysisPeriodEndString + " stochasticStateTransitions: " + 	
		this.portfolioWeightChoiceModel + " decisionAnalysisPeriodEndString: " + 	this.shortSelling + " decisionAnalysisPeriodEndString: " + 	
				this.stochasticStateTransitions + " linearCostFunction: " + 	this.linearCostFunction + " riskFreeRate: " + 
		this.riskFreeRate + " linearfactor: " +  this.linearfactor + " numberOfIterations: " + 	this.numberOfIterations	
		 + " quadraticfactor: " + this.quadraticfactor + " assetWieghtIncrements: " + 	this.assetWieghtIncrements
		 + " changeInWeightIncrement: " + 	this.changeInWeightIncrement + " maximumPermissbleChangeInWeight: " + 	this.maximumPermissbleChangeInWeight	
		 + " epsilonError: " + this.epsilonError + " gammaDiscountFactor: " + 	this.gammaDiscountFactor
		 + " accuracyThreshold: " +  this.accuracyThreshold);
	}




}
