import java.util.ArrayList;

public class Parameters {

	private String modelPeriodSting;
	private String rateTypeString;// "Fixed";
	private String borrowerTypeString;// "individual_generic";
	private String paymentScheduleString;
	private String traditionalAssetVariationTypeString;
	private String internalSecuritiseString;// "false";
	private String userAssetLiabilityDataInpuString;
	private String securitiseString;
	private String securitiseLinearString;
	private String bnksOnlyAnalysisString;
	private String securitisationRate;
	private String defaultProbabilityBankExpectations;
	private String probabilityOfEquityReturnExpectations;
	private String securitisationCostConstantFactor;
	private String numberOfConstituentsPerMBSIssuanse;
	private String primeMortgageLTV;
	private String subprimeMortgageLTV;
	private String userDefinedBankAssets;
	private String userDefinedBankLiabilities;
	private String bankAssetReturn;
	private String bankLiabilityExpense;
	private String returnOnGlobalEquity;
	private String bankRegulatoryCapitalRatio;
	private String bankCount;
	private String investorCount;
	private String recoveryRateOnTraditionalAsset;
	private String recoveryRateOnCreditAsset;
	private String assetValueMin;
	private String assetValueMax;
	private String aveIncomeMin;
	private String aveIncomeMax;
	private String resetWindow;
	private String currentRiskFreeRate;
	private String mortgageMaturityMax;
	private String mortgageMaturityMin;
	private String focusBankString;
	/**
	 * this value at the bank loan issuance level defines the probability bias
	 * for setting the loan to value ratio and average income of the borrower
	 * 
	 */
	private String probabilityOfSubPrime;
	/**
	 * this value at the bank loan issuance level defines the probability bias
	 * for determining whether the loan is issued to a borrower who is severely
	 * burdened by housing cost this in turn will determine the probability that
	 * the borrower will default if the value of the house/asset underlying the
	 * mortgage falls below the market value of the mortgage
	 * 
	 */
	private String probabilityOfHouseCostSevereBurden;
	/**
	 * this value at the bank loan issuance level defines the probability bias
	 * for setting the loan maturity
	 * 
	 */
	private String mortgageMaturityMinProbability;
	// Life, Insurance and Pension Fund required Variables
	private String userDefinedLAPFAssets;
	// System.out.println(this.userDefinedLAPFAssets);
	private String userDefinedLAPFLiabilities;
	// System.out.println(this.userDefinedLAPFLiabilities);
	private String lapfQuadraticCostFunctionString;
	// System.out.println("lapfQuadraticCostFunction: " +
	// lapfQuadraticCostFunction);
	private String lapfConstantContractualObligationsString;
	// System.out.println("lapfConstantContractualObligations: " +
	// lapfConstantContractualObligations);
	private String lapfMultiPeriodSolvancyModelString;// "false";
	private String shortSellingString;// "false";
	// System.out.println("short sell: " + shortSelling);
	private String interestSpreadExperimentString;
	private String opportunityCostOfFixedIncomeInvestment;
	private String lapfRegulatorySolvancyRatio;
	private String lapfPeriodicLiabilityPaymentRate;
	private String lapfPremiumContributionsRate;
	private String returnOnGlobalCredit = "0.16";
	// this.returnOnGlobalCredit = (Double)
	// params.getValue("returnOnGlobalCredit");
	/**
	 * this is defined at fund valuation period by actuaries on an annual or
	 * quarterly basis It is nevertheless predifined
	 */
	private String annualExpectedPayout;
	private String annualExpectPayoutRebalancingRate;
	private String AA_Rated_MBS_Coupon;
	private String nonAA_Rated_MBS_Coupon;
	private String AA_Rated_MBS_Probability;
	private ArrayList<Double> citibankResearchMBSCouponsList = new ArrayList<Double>();
	private ArrayList<Double> citibankResearchMBSDefaultsList = new ArrayList<Double>();
	private String multiPeriodAnalysis;
	private String loanResetYear;
	private String seniorTrancheQualityString;
	private double seniourTrancheCoupon;
	private double seniourTrancheDefaultRate;
	private String mezzTrancheQualityString;
	private double mezzTrancheCoupon;
	private double mezzTrancheDefaultRate;
	private String juniorTrancheQualityString;
	private double juniourTrancheCoupon;
	private double juniourTrancheDefaultRate;
	private String securitisationRateDecisionHorizon;
	private String genericPostRateResetDafaultRate;
	private String fullyIndexedContractRateSpread;
	private String genericPostRateResetCoupon;
	private boolean constantQ;
	private String constantQSecuritiseString;
	private String lapfTypeString;
	private String fundsExpectationsString;
	

	
	private String stochasticProcessTypeStrng;
	private int stochasticProcessTypeStringIndex;
	

	private int numberOfIterations;
	private int numberOfPaths;
	private int pathLength;
	private double timeShit_dt;
	//private String assetName;
	
	private double initialCreditAssetvalue;
	private double cir_AlphaCreditAssetMeanReversion;
	private double cir_ThetaCreditAssetMeanValue;
	private double standardDeviationCreditAsset;


	private double initialTraditionalAssetvalue;
	private double driftMeanTraditionalAsset;
	private double standardDeviationTraditionalAsset;
	private double hestonLongTermVarianceTraditionalAsset;
	private double hestonMeanReversionRateTraditionalAsset;
	private double hestonVarianceVolatilityTraditionalAsset;
	private int fundType;
	private int fundExpectations;
	private String fundExpectationsBiasString;
	private double fundExpectationsBias;
	private String bankTypeString;
	private int bankType;
	private boolean heuristicSecuritisationModel;
	private boolean erevRothModel;
	
	
	
	//MDP Model Variables these variables have to be static because they are treated as global Parameters
	public static String decisionAnalysisPeriodEndString = "2003"; //possible values "2003", "2007", "Full"
	public static double discountfactor = 0.05; //the discount factor at which future payoffs will be discounted 
	public static boolean portfolioWeightChoiceModel = true; //used to determine if model will use the choice of portfolio weights or the choice of changes in portfolio weights
	
	public static double linearfactor = 0.07; //used as the constant parameter/multiplier for computing the linear transaction costs 
	public static double quadraticfactor = 0.07; //used as the constant parameter/multiplier for computing the quadratic transaction costs
	public static double assetWieghtIncrements = 0.05; //increment used to create the potfolio weights that are used to define the MDP states 
	public static double changeInWeightIncrement = 0.05; //represents the rate at which changes can be made to portfolio weights 
	public static double maximumPermissbleChangeInWeight = 0.10; 
	//value and policy iteration parameters 
	public static int numberOfEpisodes = 1000;
	public static int numberOfDecisionEpochs = 5;
	public static double epsilonError = 10*(0.01/100);//
	public static double gammaDiscountFactor = 0.1;//increasing gamma increases the time/number of iterations required to find a value 
	public static double accuracyThreshold =  0.0001;
	public static boolean stochasticStateTransitions = false;
	public static double RLLearningRateAlpha = 0.1;
	public static double RLLearningLambda = 0.1;
	public static int RLLearningType = 0;
	public static int RLActionSelectionType = 1;
	public static boolean periodByPeriodCalculationsHeuristic = true;
	public static int numberOfIterationsHeuristic = 365;
	public static int numberOfDecisionEpochsHeuristic = 5;
	public static double securitisationRateIncrement = 0.1;
	public static double greedParameterIncrement = 0.1;
	public static double genericBias = 0.5;
	public static double maximumBias = 0.5;
	public static boolean IdenticalStateSelectionBias = true;
	public static double minimumBias = 0.2;
	public static double loanMarketSentimentShare = 0.5;
	public static double betaDistributionAlphaGeneric = 2;
	public static double betaDistributionBetaGeneric = 5;
	public static double betaDistributionAlphaMin = 1;
	public static double betaDistributionBetaMax = 7;
	public static double betaDistributionAlphaMax = 5;
	public static double betaDistributionBetaMin = 1;
	
	public static String ErevRothLearnerUpdateType = "Nicolaosen Variation";//can be "Standard" or "Nicolaosen Variation";
	public static String ErevRothProbabilityModel = "Gibbs-Boltzmann";//can be "Standard" or "Gibbs-Boltzmann";
	public static boolean periodByPeriodCalculationsErevRoth = true;
	public static boolean identicalParameterSelection = true;
	public static int numberOfIterationsErevRoth = 1000;
	public static int numberOfDecisionEpochsErevRoth = 5;
	public static double securitisationRateIncrementErevRoth = 0.05;//sliders
	public static double securitisationRateMaxChangeErevRoth = 0.5;//sliders
	public static int numberOfActionsErevRoth = (int) (1+ (2*(Parameters.securitisationRateMaxChangeErevRoth
			/Parameters.securitisationRateIncrementErevRoth)));
	public static double GibbsBoltzmannParameterErevRoth = 1000;
	public static double scalingParameterErevRoth = 9;
	public static  double averageStartingRewardErevRoth = 100;
	public static double genericExperimentationFactor = 0.75;
	public static double maximumExperimentationFactor = 0.9;
	public static double minimumExperimentationFactor = 0.1;
	public static double  genericRecencyFactorErevRoth = 0.1;
	public static double  minRecencyFactorErevRoth = 0.1;
	public static double  maxRecencyFactorErevRoth = 0.75;
	public static double loanMarketSentimentShareErevRoth = 0.5;
	public static double betaDistributionAlphaGenericErevRoth = 2;
	public static double betaDistributionBetaGenericErevRoth = 5;
	public static double betaDistributionAlphaMinErevRoth = 1;
	public static double betaDistributionBetaMaxErevRoth = 7;
	public static double betaDistributionAlphaMaxErevRoth = 7;
	public static double betaDistributionBetaMinErevRoth = 1;
	public static int randomSeedErevRoth = 74974984;
	


	public Parameters() {

	}

	
	/**
	 * The assumption here is that parameter definitions for the MDP model are global. If agents are to be heterogeneous
	 * then these parameters will be set for each agent using a Random variable to select from a range where the predefined
	 * parameters in this static method are used as the maximum values
	 *  
	 * @param decisionAnalysisPeriodEndString
	 * @param numberOfEpisodes
	 * @param portfolioWeightChoiceModel
	 * @param shortSelling
	 * @param stochasticStateTransitions
	 * @param linearCostFunction
	 * @param riskFreeRate
	 * @param discountfactor
	 * @param linearfactor
	 * @param quadraticfactor
	 * @param assetWieghtIncrements
	 * @param changeInWeightIncrement
	 * @param maximumPermissbleChangeInWeight
	 * @param epsilonError
	 * @param gammaDiscountFactor
	 * @param accuracyThreshold
	 */
	public static void setGlobalMDPDecisionParameters(String decisionAnalysisPeriodEndString,	int numberOfEpisodes, int numberOfDecisionEpochs, 
			boolean portfolioWeightChoiceModel,	boolean stochasticStateTransitions,	
			double linearfactor, double quadraticfactor,	double assetWieghtIncrements, 
			double changeInWeightIncrement,	double maximumPermissbleChangeInWeight,	double epsilonError,
			double gammaDiscountFactor, double accuracyThreshold,
			double RLLearningRateAlpha, double RLLearningLambda,
			int RLLearningType, int RLActionSelectionType){
		
		
		Parameters.decisionAnalysisPeriodEndString = decisionAnalysisPeriodEndString;
		Parameters.portfolioWeightChoiceModel = portfolioWeightChoiceModel;
		Parameters.linearfactor = linearfactor; 
		Parameters.quadraticfactor = quadraticfactor;
		Parameters.assetWieghtIncrements = assetWieghtIncrements;
		Parameters.changeInWeightIncrement = changeInWeightIncrement;
		Parameters.maximumPermissbleChangeInWeight = maximumPermissbleChangeInWeight; 
		//value and policy iteration parameters 
		Parameters.numberOfEpisodes = numberOfEpisodes;
		Parameters.numberOfDecisionEpochs = numberOfDecisionEpochs;
		Parameters.epsilonError = epsilonError;
		Parameters.gammaDiscountFactor = gammaDiscountFactor;
		Parameters.accuracyThreshold =  accuracyThreshold;
		Parameters.stochasticStateTransitions = stochasticStateTransitions;
		Parameters.RLLearningRateAlpha = RLLearningRateAlpha;
		Parameters.RLLearningLambda = RLLearningLambda;
		Parameters.RLLearningType = RLLearningType;
		Parameters.RLActionSelectionType = RLActionSelectionType;
		
//		
//		System.out.println(Parameters.decisionAnalysisPeriodEndString + " " + Parameters.portfolioWeightChoiceModel + " " + Parameters.linearfactor + " " + 
//		Parameters.quadraticfactor + " " + Parameters.assetWieghtIncrements + " " + Parameters.changeInWeightIncrement + " " +
//				Parameters.maximumPermissbleChangeInWeight + " " +	Parameters.numberOfEpisodes + " " + "epsilonError" + " " +Parameters.epsilonError + " " +
//		Parameters.gammaDiscountFactor + " " + Parameters.accuracyThreshold + " " +	Parameters.stochasticStateTransitions + " " + Parameters.RLActionSelectionType);
		
	}
	
	/**
	 * @return the modelPeriodSting
	 */
	public String getModelPeriodSting() {
		return modelPeriodSting;
	}

	/**
	 * @param modelPeriodSting
	 *            the modelPeriodSting to set
	 */
	public void setModelPeriodSting(String modelPeriodSting) {
		this.modelPeriodSting = modelPeriodSting;
	}

	/**
	 * @return the rateTypeString
	 */
	public String getRateTypeString() {
		return rateTypeString;
	}

	/**
	 * @param rateTypeString
	 *            the rateTypeString to set
	 */
	public void setRateTypeString(String rateTypeString) {
		this.rateTypeString = rateTypeString;
	}

	/**
	 * @return the borrowerTypeString
	 */
	public String getBorrowerTypeString() {
		return borrowerTypeString;
	}

	/**
	 * @param borrowerTypeString
	 *            the borrowerTypeString to set
	 */
	public void setBorrowerTypeString(String borrowerTypeString) {
		this.borrowerTypeString = borrowerTypeString;
	}

	/**
	 * @return the paymentScheduleString
	 */
	public String getPaymentScheduleString() {
		return paymentScheduleString;
	}

	/**
	 * @param paymentScheduleString
	 *            the paymentScheduleString to set
	 */
	public void setPaymentScheduleString(String paymentScheduleString) {
		this.paymentScheduleString = paymentScheduleString;
	}

	/**
	 * @return the traditionalAssetVariationTypeString
	 */
	public String getTraditionalAssetVariationTypeString() {
		return traditionalAssetVariationTypeString;
	}

	/**
	 * @param traditionalAssetVariationTypeString
	 *            the traditionalAssetVariationTypeString to set
	 */
	public void setTraditionalAssetVariationTypeString(
			String traditionalAssetVariationTypeString) {
		this.traditionalAssetVariationTypeString = traditionalAssetVariationTypeString;
	}

	/**
	 * @return the constantQSecuritiseString
	 */
	public String getConstantQSecuritiseString() {
		return constantQSecuritiseString;
	}

	/**
	 * @param constantQSecuritiseString
	 *            the constantQSecuritiseString to set
	 */
	public void setConstantQSecuritiseString(String constantQString) {
		this.constantQSecuritiseString = constantQString;
	}
	
	
	
	/**
	 * @return the internalSecuritiseString
	 */
	public String getInternalSecuritiseString() {
		return internalSecuritiseString;
	}

	/**
	 * @param internalSecuritiseString
	 *            the internalSecuritiseString to set
	 */
	public void setInternalSecuritiseString(String internalSecuritiseString) {
		this.internalSecuritiseString = internalSecuritiseString;
	}


	/**
	 * @return the userAssetLiabilityDataInpuString
	 */
	public String getUserAssetLiabilityDataInpuString() {
		return userAssetLiabilityDataInpuString;
	}

	/**
	 * @param userAssetLiabilityDataInpuString
	 *            the userAssetLiabilityDataInpuString to set
	 */
	public void setUserAssetLiabilityDataInpuString(
			String userAssetLiabilityDataInpuString) {
		this.userAssetLiabilityDataInpuString = userAssetLiabilityDataInpuString;
	}

	/**
	 * @return the securitiseString
	 */
	public String getSecuritiseString() {
		return securitiseString;
	}

	/**
	 * @param securitiseString
	 *            the securitiseString to set
	 */
	public void setSecuritiseString(String securitiseString) {
		this.securitiseString = securitiseString;
	}

	/**
	 * @return the securitiseLinearString
	 */
	public String getSecuritiseLinearString() {
		return securitiseLinearString;
	}

	/**
	 * @param securitiseLinearString
	 *            the securitiseLinearString to set
	 */
	public void setSecuritiseLinearString(String securitiseLinearString) {
		this.securitiseLinearString = securitiseLinearString;
	}

	/**
	 * @return the bnksOnlyAnalysisString
	 */
	public String getBnksOnlyAnalysisString() {
		return bnksOnlyAnalysisString;
	}

	/**
	 * @param bnksOnlyAnalysisString
	 *            the bnksOnlyAnalysisString to set
	 */
	public void setBnksOnlyAnalysisString(String bnksOnlyAnalysisString) {
		this.bnksOnlyAnalysisString = bnksOnlyAnalysisString;
	}

	/**
	 * @return the securitisationRate
	 */
	public String getSecuritisationRate() {
		return securitisationRate;
	}

	/**
	 * @param securitisationRate
	 *            the securitisationRate to set
	 */
	public void setSecuritisationRate(String securitisationRate) {
		this.securitisationRate = securitisationRate;
	}

	/**
	 * @return the defaultProbabilityBankExpectations
	 */
	public String getDefaultProbabilityBankExpectations() {
		return defaultProbabilityBankExpectations;
	}

	/**
	 * @param defaultProbabilityBankExpectations
	 *            the defaultProbabilityBankExpectations to set
	 */
	public void setDefaultProbabilityBankExpectations(
			String defaultProbabilityBankExpectations) {
		this.defaultProbabilityBankExpectations = defaultProbabilityBankExpectations;
	}

	/**
	 * @return the probabilityOfEquityReturnExpectations
	 */
	public String getProbabilityOfEquityReturnExpectations() {
		return probabilityOfEquityReturnExpectations;
	}

	/**
	 * @param probabilityOfEquityReturnExpectations
	 *            the probabilityOfEquityReturnExpectations to set
	 */
	public void setProbabilityOfEquityReturnExpectations(
			String probabilityOfEquityReturnExpectations) {
		this.probabilityOfEquityReturnExpectations = probabilityOfEquityReturnExpectations;
	}

	/**
	 * @return the securitisationCostConstantFactor
	 */
	public String getSecuritisationCostConstantFactor() {
		return securitisationCostConstantFactor;
	}

	/**
	 * @param securitisationCostConstantFactor
	 *            the securitisationCostConstantFactor to set
	 */
	public void setSecuritisationCostConstantFactor(
			String securitisationCostConstantFactor) {
		this.securitisationCostConstantFactor = securitisationCostConstantFactor;
	}

	/**
	 * @return the numberOfConstituentsPerMBSIssuanse
	 */
	public String getNumberOfConstituentsPerMBSIssuanse() {
		return numberOfConstituentsPerMBSIssuanse;
	}

	/**
	 * @param numberOfConstituentsPerMBSIssuanse
	 *            the numberOfConstituentsPerMBSIssuanse to set
	 */
	public void setNumberOfConstituentsPerMBSIssuanse(
			String numberOfConstituentsPerMBSIssuanse) {
		this.numberOfConstituentsPerMBSIssuanse = numberOfConstituentsPerMBSIssuanse;
	}

	/**
	 * @return the primeMortgageLTV
	 */
	public String getPrimeMortgageLTV() {
		return primeMortgageLTV;
	}

	/**
	 * @param primeMortgageLTV
	 *            the primeMortgageLTV to set
	 */
	public void setPrimeMortgageLTV(String primeMortgageLTV) {
		this.primeMortgageLTV = primeMortgageLTV;
	}

	/**
	 * @return the subprimeMortgageLTV
	 */
	public String getSubprimeMortgageLTV() {
		return subprimeMortgageLTV;
	}

	/**
	 * @param subprimeMortgageLTV
	 *            the subprimeMortgageLTV to set
	 */
	public void setSubprimeMortgageLTV(String subprimeMortgageLTV) {
		this.subprimeMortgageLTV = subprimeMortgageLTV;
	}

	/**
	 * @return the userDefinedBankAssets
	 */
	public String getUserDefinedBankAssets() {
		return userDefinedBankAssets;
	}

	/**
	 * @param userDefinedBankAssets
	 *            the userDefinedBankAssets to set
	 */
	public void setUserDefinedBankAssets(String userDefinedBankAssets) {
		this.userDefinedBankAssets = userDefinedBankAssets;
	}

	/**
	 * @return the userDefinedBankLiabilities
	 */
	public String getUserDefinedBankLiabilities() {
		return userDefinedBankLiabilities;
	}

	/**
	 * @param userDefinedBankLiabilities
	 *            the userDefinedBankLiabilities to set
	 */
	public void setUserDefinedBankLiabilities(String userDefinedBankLiabilities) {
		this.userDefinedBankLiabilities = userDefinedBankLiabilities;
	}

	/**
	 * @return the bankAssetReturn
	 */
	public String getBankAssetReturn() {
		return bankAssetReturn;
	}

	/**
	 * @param bankAssetReturn
	 *            the bankAssetReturn to set
	 */
	public void setBankAssetReturn(String bankAssetReturn) {
		this.bankAssetReturn = bankAssetReturn;
	}

	/**
	 * @return the bankLiabilityExpense
	 */
	public String getBankLiabilityExpense() {
		return bankLiabilityExpense;
	}

	/**
	 * @param bankLiabilityExpense
	 *            the bankLiabilityExpense to set
	 */
	public void setBankLiabilityExpense(String bankLiabilityExpense) {
		this.bankLiabilityExpense = bankLiabilityExpense;
	}

	/**
	 * @return the returnOnGlobalEquity
	 */
	public String getReturnOnGlobalEquity() {
		return returnOnGlobalEquity;
	}

	/**
	 * @param returnOnGlobalEquity
	 *            the returnOnGlobalEquity to set
	 */
	public void setReturnOnGlobalEquity(String returnOnGlobalEquity) {
		this.returnOnGlobalEquity = returnOnGlobalEquity;
	}

	/**
	 * @return the bankRegulatoryCapitalRatio
	 */
	public String getBankRegulatoryCapitalRatio() {
		return bankRegulatoryCapitalRatio;
	}

	/**
	 * @param bankRegulatoryCapitalRatio
	 *            the bankRegulatoryCapitalRatio to set
	 */
	public void setBankRegulatoryCapitalRatio(String bankRegulatoryCapitalRatio) {
		this.bankRegulatoryCapitalRatio = bankRegulatoryCapitalRatio;
	}

	/**
	 * @return the bankCount
	 */
	public String getBankCount() {
		return bankCount;
	}

	/**
	 * @param bankCount
	 *            the bankCount to set
	 */
	public void setBankCount(String bankCount) {
		this.bankCount = bankCount;
	}

	/**
	 * @return the investorCount
	 */
	public String getInvestorCount() {
		return investorCount;
	}

	/**
	 * @param investorCount
	 *            the investorCount to set
	 */
	public void setInvestorCount(String investorCount) {
		this.investorCount = investorCount;
	}
	
	
	/**
	 * @return the recoveryRateOnTraditionalAsset
	 */
	public String getRecoveryRateOnTraditionalAsset() {
		return recoveryRateOnTraditionalAsset;
	}

	/**
	 * @param recoveryRateOnTraditionalAsset
	 *            the recoveryRateOnTraditionalAsset to set
	 */
	public void setRecoveryRateOnTraditionalAsset(
			String recoveryRateOnTraditionalAsset) {
		this.recoveryRateOnTraditionalAsset = recoveryRateOnTraditionalAsset;
	}

	/**
	 * @return the recoveryRateOnCreditAsset
	 */
	public String getRecoveryRateOnCreditAsset() {
		return recoveryRateOnCreditAsset;
	}

	/**
	 * @param recoveryRateOnCreditAsset
	 *            the recoveryRateOnCreditAsset to set
	 */
	public void setRecoveryRateOnCreditAsset(String recoveryRateOnCreditAsset) {
		this.recoveryRateOnCreditAsset = recoveryRateOnCreditAsset;
	}

	/**
	 * @return the assetValueMin
	 */
	public String getAssetValueMin() {
		return assetValueMin;
	}

	/**
	 * @param assetValueMin
	 *            the assetValueMin to set
	 */
	public void setAssetValueMin(String assetValueMin) {
		this.assetValueMin = assetValueMin;
	}

	/**
	 * @return the assetValueMax
	 */
	public String getAssetValueMax() {
		return assetValueMax;
	}

	/**
	 * @param assetValueMax
	 *            the assetValueMax to set
	 */
	public void setAssetValueMax(String assetValueMax) {
		this.assetValueMax = assetValueMax;
	}

	/**
	 * @return the aveIncomeMin
	 */
	public String getAveIncomeMin() {
		return aveIncomeMin;
	}

	/**
	 * @param aveIncomeMin
	 *            the aveIncomeMin to set
	 */
	public void setAveIncomeMin(String aveIncomeMin) {
		this.aveIncomeMin = aveIncomeMin;
	}

	/**
	 * @return the aveIncomeMax
	 */
	public String getAveIncomeMax() {
		return aveIncomeMax;
	}

	/**
	 * @param aveIncomeMax
	 *            the aveIncomeMax to set
	 */
	public void setAveIncomeMax(String aveIncomeMax) {
		this.aveIncomeMax = aveIncomeMax;
	}

	/**
	 * @return the resetWindow
	 */
	public String getResetWindow() {
		return resetWindow;
	}

	/**
	 * @param resetWindow
	 *            the resetWindow to set
	 */
	public void setResetWindow(String resetWindow) {
		this.resetWindow = resetWindow;
	}

	/**
	 * @return the currentRiskFreeRate
	 */
	public String getCurrentRiskFreeRate() {
		return currentRiskFreeRate;
	}

	/**
	 * @param currentRiskFreeRate
	 *            the currentRiskFreeRate to set
	 */
	public void setCurrentRiskFreeRate(String currentRiskFreeRate) {
		this.currentRiskFreeRate = currentRiskFreeRate;
	}

	/**
	 * @return the mortgageMaturityMax
	 */
	public String getMortgageMaturityMax() {
		return mortgageMaturityMax;
	}

	/**
	 * @param mortgageMaturityMax
	 *            the mortgageMaturityMax to set
	 */
	public void setMortgageMaturityMax(String mortgageMaturityMax) {
		this.mortgageMaturityMax = mortgageMaturityMax;
	}

	/**
	 * @return the mortgageMaturityMin
	 */
	public String getMortgageMaturityMin() {
		return mortgageMaturityMin;
	}

	/**
	 * @param mortgageMaturityMin
	 *            the mortgageMaturityMin to set
	 */
	public void setMortgageMaturityMin(String mortgageMaturityMin) {
		this.mortgageMaturityMin = mortgageMaturityMin;
	}

	/**
	 * @return the probabilityOfSubPrime
	 */
	public String getProbabilityOfSubPrime() {
		return probabilityOfSubPrime;
	}

	/**
	 * @param probabilityOfSubPrime
	 *            the probabilityOfSubPrime to set
	 */
	public void setProbabilityOfSubPrime(String probabilityOfSubPrime) {
		this.probabilityOfSubPrime = probabilityOfSubPrime;
	}

	/**
	 * @return the probabilityOfHouseCostSevereBurden
	 */
	public String getProbabilityOfHouseCostSevereBurden() {
		return probabilityOfHouseCostSevereBurden;
	}

	/**
	 * @param probabilityOfHouseCostSevereBurden
	 *            the probabilityOfHouseCostSevereBurden to set
	 */
	public void setProbabilityOfHouseCostSevereBurden(
			String probabilityOfHouseCostSevereBurden) {
		this.probabilityOfHouseCostSevereBurden = probabilityOfHouseCostSevereBurden;
	}

	/**
	 * @return the mortgageMaturityMinProbability
	 */
	public String getMortgageMaturityMinProbability() {
		return mortgageMaturityMinProbability;
	}

	/**
	 * @param mortgageMaturityMinProbability
	 *            the mortgageMaturityMinProbability to set
	 */
	public void setMortgageMaturityMinProbability(
			String mortgageMaturityMinProbability) {
		this.mortgageMaturityMinProbability = mortgageMaturityMinProbability;
	}

	/**
	 * @return the userDefinedLAPFAssets
	 */
	public String getUserDefinedLAPFAssets() {
		return userDefinedLAPFAssets;
	}

	/**
	 * @param userDefinedLAPFAssets
	 *            the userDefinedLAPFAssets to set
	 */
	public void setUserDefinedLAPFAssets(String userDefinedLAPFAssets) {
		this.userDefinedLAPFAssets = userDefinedLAPFAssets;
	}

	/**
	 * @return the userDefinedLAPFLiabilities
	 */
	public String getUserDefinedLAPFLiabilities() {
		return userDefinedLAPFLiabilities;
	}

	/**
	 * @param userDefinedLAPFLiabilities
	 *            the userDefinedLAPFLiabilities to set
	 */
	public void setUserDefinedLAPFLiabilities(String userDefinedLAPFLiabilities) {
		this.userDefinedLAPFLiabilities = userDefinedLAPFLiabilities;
	}

	/**
	 * @return the lapfQuadraticCostFunctionString
	 */
	public String getLapfQuadraticCostFunctionString() {
		return lapfQuadraticCostFunctionString;
	}

	/**
	 * @param lapfQuadraticCostFunctionString
	 *            the lapfQuadraticCostFunctionString to set
	 */
	public void setLapfQuadraticCostFunctionString(
			String lapfQuadraticCostFunctionString) {
		this.lapfQuadraticCostFunctionString = lapfQuadraticCostFunctionString;
	}

	/**
	 * @return the lapfConstantContractualObligationsString
	 */
	public String getLapfConstantContractualObligationsString() {
		return lapfConstantContractualObligationsString;
	}

	/**
	 * @param lapfConstantContractualObligationsString
	 *            the lapfConstantContractualObligationsString to set
	 */
	public void setLapfConstantContractualObligationsString(
			String lapfConstantContractualObligationsString) {
		this.lapfConstantContractualObligationsString = lapfConstantContractualObligationsString;
	}

	/**
	 * @return the lapfMultiPeriodSolvancyModelString
	 */
	public String getLapfMultiPeriodSolvancyModelString() {
		return lapfMultiPeriodSolvancyModelString;
	}

	/**
	 * @param lapfMultiPeriodSolvancyModelString
	 *            the lapfMultiPeriodSolvancyModelString to set
	 */
	public void setLapfMultiPeriodSolvancyModelString(
			String lapfMultiPeriodSolvancyModelString) {
		this.lapfMultiPeriodSolvancyModelString = lapfMultiPeriodSolvancyModelString;
	}

	/**
	 * @return the shortSellingString
	 */
	public String getShortSellingString() {
		return shortSellingString;
	}

	/**
	 * @param shortSellingString
	 *            the shortSellingString to set
	 */
	public void setShortSellingString(String shortSellingString) {
		this.shortSellingString = shortSellingString;
	}

	/**
	 * @return the interestSpreadExperimentString
	 */
	public String getInterestSpreadExperimentString() {
		return interestSpreadExperimentString;
	}

	/**
	 * @param interestSpreadExperimentString
	 *            the interestSpreadExperimentString to set
	 */
	public void setInterestSpreadExperimentString(
			String interestSpreadExperimentString) {
		this.interestSpreadExperimentString = interestSpreadExperimentString;
	}

	/**
	 * @return the opportunityCostOfFixedIncomeInvestment
	 */
	public String getOpportunityCostOfIncomeInvestment() {
		return opportunityCostOfFixedIncomeInvestment;
	}

	/**
	 * @param opportunityCostOfFixedIncomeInvestment
	 *            the opportunityCostOfFixedIncomeInvestment to set
	 */
	public void setOpportunityCostOfIncomeInvestment(
			String opportunityCostOfFixedIncomeInvestment) {
		this.opportunityCostOfFixedIncomeInvestment = opportunityCostOfFixedIncomeInvestment;
	}

	/**
	 * @return the lapfRegulatorySolvancyRatio
	 */
	public String getLapfRegulatorySolvancyRatio() {
		return lapfRegulatorySolvancyRatio;
	}

	/**
	 * @param lapfRegulatorySolvancyRatio
	 *            the lapfRegulatorySolvancyRatio to set
	 */
	public void setLapfRegulatorySolvancyRatio(
			String lapfRegulatorySolvancyRatio) {
		this.lapfRegulatorySolvancyRatio = lapfRegulatorySolvancyRatio;
	}
	
	
	public void setMultiPeriodBanks(
			String mpb) {
		this.multiPeriodAnalysis = mpb;
	}
	
	public void setSecuritisationRateDecisionHorizon(
			String mpb) {
		this.securitisationRateDecisionHorizon = mpb;
//		System.out.println(securitisationRateDecisionHorizon);
	}


	public void setLoanResetYear(
			String lry) {
		this.loanResetYear = lry;
	}
	
	
	public String getMultiPeriodBanks() {
		return this.multiPeriodAnalysis;
	}

	public String getLoanResetYear() {
		return this.loanResetYear;
	}
	
	

	public String getSecuritisationRateDecisionHorizon() {
		return this.securitisationRateDecisionHorizon;
	}


	/**
	 * @return the lapfPeriodicLiabilityPaymentRate
	 */
	public String getLapfPeriodicLiabilityPaymentRate() {
		return lapfPeriodicLiabilityPaymentRate;
	}

	/**
	 * @param lapfPeriodicLiabilityPaymentRate
	 *            the lapfPeriodicLiabilityPaymentRate to set
	 */
	public void setLapfPeriodicLiabilityPaymentRate(
			String lapfPeriodicLiabilityPaymentRate) {
		this.lapfPeriodicLiabilityPaymentRate = lapfPeriodicLiabilityPaymentRate;
	}

	/**
	 * @return the lapfPremiumContributionsRate
	 */
	public String getLapfPremiumContributionsRate() {
		return lapfPremiumContributionsRate;
	}

	/**
	 * @param lapfPremiumContributionsRate
	 *            the lapfPremiumContributionsRate to set
	 */
	public void setLapfPremiumContributionsRate(
			String lapfPremiumContributionsRate) {
		this.lapfPremiumContributionsRate = lapfPremiumContributionsRate;
	}

	/**
	 * @return the returnOnGlobalCredit
	 */
	public String getReturnOnGlobalCredit() {
		return returnOnGlobalCredit;
	}

	/**
	 * @param returnOnGlobalCredit
	 *            the returnOnGlobalCredit to set
	 */
	public void setReturnOnGlobalCredit(String returnOnGlobalCredit) {
		this.returnOnGlobalCredit = returnOnGlobalCredit;
	}

	/**
	 * @return the annualExpectedPayout
	 */
	public String getAnnualExpectedPayout() {
		return annualExpectedPayout;
	}

	/**
	 * @param annualExpectedPayout
	 *            the annualExpectedPayout to set
	 */
	public void setAnnualExpectedPayout(String annualExpectedPayout) {
		this.annualExpectedPayout = annualExpectedPayout;
	}

	/**
	 * @return the annualExpectPayoutRebalancingRate
	 */
	public String getAnnualExpectPayoutRebalancingRate() {
		return annualExpectPayoutRebalancingRate;
	}

	/**
	 * @param annualExpectPayoutRebalancingRate
	 *            the annualExpectPayoutRebalancingRate to set
	 */
	public void setAnnualExpectPayoutRebalancingRate(
			String annualExpectPayoutRebalancingRate) {
		this.annualExpectPayoutRebalancingRate = annualExpectPayoutRebalancingRate;
	}

	/**
	 * @return the aA_Rated_MBS_Coupon
	 */
	public String getAA_Rated_MBS_Coupon() {
		return AA_Rated_MBS_Coupon;
	}

	/**
	 * @param aA_Rated_MBS_Coupon
	 *            the aA_Rated_MBS_Coupon to set
	 */
	public void setAA_Rated_MBS_Coupon(String aA_Rated_MBS_Coupon) {
		AA_Rated_MBS_Coupon = aA_Rated_MBS_Coupon;
	}

	/**
	 * @return the nonAA_Rated_MBS_Coupon
	 */
	public String getNonAA_Rated_MBS_Coupon() {
		return nonAA_Rated_MBS_Coupon;
	}

	/**
	 * @param nonAA_Rated_MBS_Coupon
	 *            the nonAA_Rated_MBS_Coupon to set
	 */
	public void setNonAA_Rated_MBS_Coupon(String nonAA_Rated_MBS_Coupon) {
		this.nonAA_Rated_MBS_Coupon = nonAA_Rated_MBS_Coupon;
	}

	/**
	 * @return the aA_Rated_MBS_Probability
	 */
	public String getAA_Rated_MBS_Probability() {
		return AA_Rated_MBS_Probability;
	}

	/**
	 * @param aA_Rated_MBS_Probability
	 *            the aA_Rated_MBS_Probability to set
	 */
	public void setAA_Rated_MBS_Probability(String aA_Rated_MBS_Probability) {
		AA_Rated_MBS_Probability = aA_Rated_MBS_Probability;
	}

	public String getValue(String string) {
		// TODO Auto-generated method stub
		return string;
	}
	
	private void setFocusBankNameString(String value) {
		this.focusBankString = value;
	}
	
	public String getFocusBankNameString() {
		return this.focusBankString;
	}
	
	
	public void setPerTrancheMBSCoupns(ArrayList<Double> couponList){
		for (int i = 0; i < couponList.size(); i++) {
			this.citibankResearchMBSCouponsList.add(couponList.get(i));
		}
//		System.out.println(this.citibankResearchMBSCouponsList.toString());
	    
	}
	public void setPerTrancheMBSDefaults(ArrayList<Double> defaultList){
		for (int i = 0; i < defaultList.size(); i++) {
			this.citibankResearchMBSDefaultsList.add(defaultList.get(i));
		}
//		System.out.println(this.citibankResearchMBSDefaultsList.toString());
	    
	}
	
	
	public ArrayList<Double> getPerTrancheMBSCoupns(){
		return this.citibankResearchMBSCouponsList;
	}

	
	public ArrayList<Double> getPerTrancheMBSDefaults(){
		return this.citibankResearchMBSDefaultsList;
	}
	
	
	
	@Override
	public String toString(){
		return new String(this.getClass() + " " + this.AA_Rated_MBS_Probability 
				+ " " + this.borrowerTypeString + " " + this.internalSecuritiseString 
				+ " " + this.lapfQuadraticCostFunctionString);
		
	}

	public void setSeniorTrancheQualityString(String sTQS) {
		// TODO Auto-generated method stub
		this.seniorTrancheQualityString = sTQS;
//		System.out.println(this.seniorTrancheQualityString);
	}
	public void setMezzTrancheQualityString(String mTQS) {
		// TODO Auto-generated method stub
		this.mezzTrancheQualityString = mTQS;
//		System.out.println(this.mezzTrancheQualityString);
	}
	
	public void setJuniorTrancheQualityString(String jTQS) {
		// TODO Auto-generated method stub
		this.juniorTrancheQualityString = jTQS;
//		System.out.println(this.juniorTrancheQualityString);
	}


	/**
	 * @return the seniorTrancheQualityString
	 */
	public String getSeniorTrancheQualityString() {
		return this.seniorTrancheQualityString;
	}
	/**
	 * @return the mezzTrancheQualityString
	 */
	public String getMezzTrancheQualityString() {
		return this.mezzTrancheQualityString;
	}

	/**
	 * @return the juniorTrancheQualityString
	 */
	public String getJuniorTrancheQualityString() {
		return this.juniorTrancheQualityString;
	}


	public void setSeniorTrancheCouponAndDefault(double seniourTrancheCoupon,
			double seniourTrancheDefaultRate) {
		// TODO Auto-generated method stub
		this.seniourTrancheCoupon = seniourTrancheCoupon;
		this.seniourTrancheDefaultRate = seniourTrancheDefaultRate;
//		System.out.println(this.seniourTrancheCoupon);
//		System.out.println(this.seniourTrancheDefaultRate);

	}


	public void setMezTrancheCouponAndDefault(double mezzTrancheCoupon,
			double mezzTrancheDefaultRate) {
		// TODO Auto-generated method stub
		this.mezzTrancheCoupon = mezzTrancheCoupon;
		this.mezzTrancheDefaultRate = mezzTrancheDefaultRate;
//		System.out.println(this.mezzTrancheCoupon);
//		System.out.println(this.mezzTrancheDefaultRate);

	}


	public void setJuniorTrancheCouponAndDefault(double juniourTrancheCoupon,
			double juniourTrancheDefaultRate) {
		// TODO Auto-generated method stub
		this.juniourTrancheCoupon = juniourTrancheCoupon;
		this.juniourTrancheDefaultRate = juniourTrancheDefaultRate;
//		System.out.println(this.juniourTrancheCoupon);
//		System.out.println(this.juniourTrancheDefaultRate);

	}
	
	
	
	public void setConstantQ(boolean cQ){
		this.constantQ = cQ;
	}
	
	
	public boolean getConstantQ(){
		return this.constantQ;
	}
	
	
	
	
	/**
	 * @return the focusBankString
	 */
	public String getFocusBankString() {
		return focusBankString;
	}
	
	


	/**
	 * @return the citibankResearchMBSCouponsList
	 */
	public ArrayList<Double> getCitibankResearchMBSCouponsList() {
		return citibankResearchMBSCouponsList;
	}

	/**
	 * @return the citibankResearchMBSDefaultsList
	 */
	public ArrayList<Double> getCitibankResearchMBSDefaultsList() {
		return citibankResearchMBSDefaultsList;
	}

	/**
	 * @return the seniourTrancheCoupon
	 */
	public Double getSeniourTrancheCoupon() {
		return seniourTrancheCoupon;
	}

	/**
	 * @return the seniourTrancheDefaultRate
	 */
	public Double getSeniourTrancheDefaultRate() {
		return seniourTrancheDefaultRate;
	}


	/**
	 * @return the mezzTrancheCoupon
	 */
	public Double getMezzTrancheCoupon() {
		return mezzTrancheCoupon;
	}

	/**
	 * @return the mezzTrancheDefaultRate
	 */
	public Double getMezzTrancheDefaultRate() {
		return mezzTrancheDefaultRate;
	}



	/**
	 * @return the juniourTrancheCoupon
	 */
	public Double getJuniourTrancheCoupon() {
		return juniourTrancheCoupon;
	}

	/**
	 * @return the juniourTrancheDefaultRate
	 */
	public Double getJuniourTrancheDefaultRate() {
		return juniourTrancheDefaultRate;
	}

	public void setFullyIndexedContractRateSpread(
			String value) {
		// TODO Auto-generated method stub
		this.fullyIndexedContractRateSpread = value;
		
	}

	public void setGenericPostRateResetDefaultRate(
			String value) {
		// TODO Auto-generated method stub
		this.genericPostRateResetDafaultRate = value;
	}
	

	public void setGenericPostRateResetCoupon(
			String value) {
		// TODO Auto-generated method stub
		this.genericPostRateResetCoupon = value;
	}

	
	public String getFullyIndexedContractRateSpread() {
		// TODO Auto-generated method stub
		return this.fullyIndexedContractRateSpread;
		
	}

	public String getGenericPostRateResetDefaultRate() {
		// TODO Auto-generated method stub
		return this.genericPostRateResetDafaultRate;
	}

	public String getGenericPostRateResetCoupon() {
		// TODO Auto-generated method stub
		return this.genericPostRateResetCoupon;
	}
	
	
	

	/**
	 * @return the numberOfIterations
	 */
	public int getNumberOfIterations() {
		return numberOfIterations;
	}



	/**
	 * @param numberOfIterations the numberOfIterations to set
	 */
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}



	/**
	 * @return the numberOfPaths
	 */
	public int getNumberOfPaths() {
		return numberOfPaths;
	}



	/**
	 * @param numberOfPaths the numberOfPaths to set
	 */
	public void setNumberOfPaths(int numberOfPaths) {
		this.numberOfPaths = numberOfPaths;
	}



	/**
	 * @return the pathLength
	 */
	public int getPathLength() {
		return pathLength;
	}



	/**
	 * @param pathLength the pathLength to set
	 */
	public void setPathLength(int pathLength) {
		this.pathLength = pathLength;
	}


	/**
	 * @return the initialAssetvalue
	 */
	public double getInitialCreditAssetvalue() {
		return initialCreditAssetvalue;
	}



	/**
	 * @param initialAssetvalue the initialAssetvalue to set
	 */
	public void setInitialCreditAssetvalue(double initialCAssetvalue) {
		this.initialCreditAssetvalue = initialCAssetvalue;
	}


	/**
	 * @return the initialAssetvalue
	 */
	public double getInitialTraditionalAssetvalue() {
		return initialTraditionalAssetvalue;
	}



	/**
	 * @param initialAssetvalue the initialAssetvalue to set
	 */
	public void setInitialTraditionalAssetvalue(double initialTAssetvalue) {
		this.initialTraditionalAssetvalue = initialTAssetvalue;
	}


	

	/**
	 * @return the driftMean
	 * 
	 *
	 *
	 */
	public double getDriftMeanTraditionalAsset() {
		return driftMeanTraditionalAsset;
	}



	/**
	 * @param driftMean the driftMean to set
	 */
	public void setDriftMeanTraditionalAsset(double driftMean) {
		this.driftMeanTraditionalAsset = driftMean;
	}



	/**
	 * @return the standardDeviationCreditAsset
	 */
	public double getStandardDeviationCreditAsset() {
		return standardDeviationCreditAsset;
	}



	/**
	 * @param standardDeviation the standardDeviationCreditAsset to set
	 */
	public void setStandardDeviationCreditAsset(double standardDeviation) {
		this.standardDeviationCreditAsset = standardDeviation;
	}



	/**
	 * @return the standardDeviationTraditionalAsset
	 */
	public double getStandardDeviationTraditionalAsset() {
		return standardDeviationTraditionalAsset;
	}



	/**
	 * @param standardDeviation the standardDeviationTraditionalAsset to set
	 */
	public void setStandardDeviationTraditionalAsset(double standardDeviation) {
		this.standardDeviationTraditionalAsset = standardDeviation;
	}



	/**
	 * @return the timeShit_dt
	 */
	public double getTimeShit_dt() {
		return timeShit_dt;
	}



	/**
	 * @param timeShit_dt the timeShit_dt to set
	 */
	public void setTimeShit_dt(double timeShit_dt) {
		this.timeShit_dt = timeShit_dt;
	}


	/**
	 * @return the cir_AlphaCreditAssetMeanReversion
	 */
	public double getCir_AlphaCreditAssetMeanReversion() {
		return cir_AlphaCreditAssetMeanReversion;
	}


	/**
	 * @param cir_Alpha the cir_AlphaCreditAssetMeanReversion to set
	 */
	public void setCir_AlphaCreditAssetMeanReversion(double cir_Alpha) {
		this.cir_AlphaCreditAssetMeanReversion = cir_Alpha;
	}


	/**
	 * @return the cir_ThetaCreditAssetMeanValue
	 */
	public double getCir_ThetaCreditAssetMeanValue() {
		return cir_ThetaCreditAssetMeanValue;
	}



	/**
	 * @param cir_Theta the cir_ThetaCreditAssetMeanValue to set
	 */
	public void setCir_ThetaCreditAssetMeanValue(double cir_Theta) {
		this.cir_ThetaCreditAssetMeanValue = cir_Theta;
	}

	
	
	/**
	 * @return the hestonLongTermVarianceTraditionalAsset
	 */
	public double getHestonLongTermVarianceTraditionalAsset() {
		return hestonLongTermVarianceTraditionalAsset;
	}



	/**
	 * @param hestonLongTermVariance the hestonLongTermVarianceTraditionalAsset to set
	 */
	public void setHestonLongTermVarianceTraditionalAsset(double hestonLongTermVariance) {
		this.hestonLongTermVarianceTraditionalAsset = hestonLongTermVariance;
	}



	/**
	 * @return the hestonLongTermVarianceTraditionalAsset
	 */
	public double getHestonMeanReversionRateTraditionalAsset() {
		return hestonLongTermVarianceTraditionalAsset;
	}



	/**
	 * @param hestonMeanReversionRate the hestonMeanReversionRateTraditionalAsset to set
	 */
	public void setHestonMeanReversionRateTraditionalAsset(double hestonMeanReversionRate) {
		this.hestonMeanReversionRateTraditionalAsset = hestonMeanReversionRate;
	}



	/**
	 * @return the hestonVarianceVolatilityTraditionalAsset
	 */
	public double getHestonVarianceVolatilityTraditionalAsset() {
		return hestonVarianceVolatilityTraditionalAsset;
	}



	/**
	 * @param hestonVarianceVolatility the hestonVarianceVolatilityTraditionalAsset to set
	 */
	public void setHestonVarianceVolatilityTraditionalAsset(double hestonVarianceVolatility) {
		this.hestonVarianceVolatilityTraditionalAsset = hestonVarianceVolatility;
	}
	

	/**
	 * @param lapfTypeString the lapfTypeString to set
	*/
	public void setLAPFTypeString(String lapfTypeString) {
		//determines if the LAPFs are learning using an MDP or just using the base model
		//possible values 
		//"Simple Fund", 
		//"MDP Learning"
		this.lapfTypeString = lapfTypeString;
	}

	public String getLAPFTypeString() {
		return this.lapfTypeString;
	}
	
	/**
	 * @param lapfExpectationsString the fundsExpectationsString to set
	*/
	public void setFundsExpectationsString(String lapfExpectationsString) {
		//determines if the LAPFs are learning using an MDP or just using the base model
		//possible values 
		//"Bullish", 
		//"Bearish",
		//"Heterogeneous"
		this.fundsExpectationsString = lapfExpectationsString;
	}

	public String getFundsExpectationsString() {
		return this.fundsExpectationsString;
	}
	

	public void setLAPFTypeIndex(int value) {
		this.fundType = value;
	}

	
	public void setLAPFExpectationsIndex(int value) {
		// determines if the LAPFs are learning using an MDP or just using the
		// base model
		// possible values
		// "Bullish",
		// "Bearish",
		// "Heterogeneous"
		this.fundExpectations = value;
	}

	
	public int getLAPFTypeIndex() {
		return fundType;
	}

	
	public int getLAPFExpectationsIndex() {
		// determines if the LAPFs are learning using an MDP or just using the
		// base model
		// possible values
		// "Bullish",
		// "Bearish",
		// "Heterogeneous"
		return fundExpectations;
	}


	public void setFundsExpectationsBiasString(String value) {
		// TODO Auto-generated method stub
		this.fundExpectationsBiasString = value;
		this.fundExpectationsBias = Double.parseDouble(fundExpectationsBiasString);
	}


	public double getFundExpectationsBias() {
		// TODO Auto-generated method stub
		return fundExpectationsBias;
	}


	/**
	 * @return the rLLearningRateAlpha
	 */
	public static double getRLLearningRateAlpha() {
		return RLLearningRateAlpha;
	}


	/**
	 * @param rLLearningRateAlpha the rLLearningRateAlpha to set
	 */
	public void setRLLearningRateAlpha(double rLLearningRateAlpha) {
		Parameters.RLLearningRateAlpha = rLLearningRateAlpha;
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
		Parameters.RLLearningLambda = rLLearningLambda;
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
		Parameters.RLLearningType = rLLearningType;
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
		Parameters.RLActionSelectionType = RLActionSelectionType;
	}


	public void setBankTypeString(String bankTypeString) {
		//determines if the Banks are learning using an MDP or just using the base model
				//possible values 
				//"Simple Model", 
				//"Intermediate Heuristic"
				this.bankTypeString = bankTypeString;
		
	}
	
	public String getBankTypeString() {
		//possible values 
				//"Simple Model", 
				//"Intermediate Heuristic"
				return this.bankTypeString;
		
	} 


	public void setBankTypeIndex(int bankType) {
		// TODO Auto-generated method stub
		this.bankType = bankType;
	}


	public int getBankTypeIndex() {
		// TODO Auto-generated method stub
		return this.bankType;
	}


	public void setHeuristicSecuritisationModel(
			boolean heuristicSecuritisationModel) {
		// TODO Auto-generated method stub
		this.heuristicSecuritisationModel = heuristicSecuritisationModel;
	}

	public boolean getHeuristicSecuritisationModel() {
		// TODO Auto-generated method stub
		return this.heuristicSecuritisationModel;
	}
	
	public void setErevRothModelSecuritisationModel(boolean erevRothModel) {
		// TODO Auto-generated method stub
		this.setErevRothModel(erevRothModel);
	}


	public boolean isErevRothModel() {
		return erevRothModel;
	}


	private void setErevRothModel(boolean erevRothModel) {
		this.erevRothModel = erevRothModel;
	}



	public static void setGlobalMDPHeuristicDecisionParameters(
			boolean periodByPeriodCalculations, int numberOfIterations2,
			int numberOfDecisionEpochs2, boolean identicalStateSelectionBias,
			double securitisationRateIncrement, double greedParameterIncrement,
			double genericBias, double maximumBias, double minimumBias, double loanMarketSentimentShare,
			double betaDistributionAlphaGeneric, double betaDistributionBetaGeneric, double betaDistributionAlphaMin,
			double betaDistributionBetaMin, double betaDistributionAlphaMax, double betaDistributionBetaMax) {
		// TODO Auto-generated method stub
		Parameters.periodByPeriodCalculationsHeuristic = periodByPeriodCalculations;
		Parameters.numberOfIterationsHeuristic = numberOfIterations2;
		Parameters.numberOfDecisionEpochsHeuristic = numberOfDecisionEpochs2;
		Parameters.securitisationRateIncrement = securitisationRateIncrement;//
		Parameters.greedParameterIncrement = greedParameterIncrement;
		Parameters.genericBias = genericBias;
		Parameters.maximumBias = maximumBias;
		Parameters.IdenticalStateSelectionBias = identicalStateSelectionBias;
		Parameters.minimumBias = minimumBias;
		Parameters.loanMarketSentimentShare = loanMarketSentimentShare;
		Parameters.betaDistributionAlphaGeneric = betaDistributionAlphaGeneric;
		Parameters.betaDistributionBetaGeneric = betaDistributionBetaGeneric;
		Parameters.betaDistributionAlphaMin = betaDistributionAlphaMin;
		Parameters.betaDistributionBetaMax = betaDistributionBetaMax;
		Parameters.betaDistributionAlphaMax = betaDistributionAlphaMax;
		Parameters.betaDistributionBetaMin = betaDistributionBetaMin;
		
//		System.out.println(toStringHeuristics());
	}
	
	
	
	public static void setGlobalMDPErevRothDecisionParameters(
			String ErevRothLearnerUpdateType, String ErevRothProbabilityModel, boolean periodByPeriodCalculationsErevRoth,
			boolean identicalParameterSelection, 	int numberOfIterations2, int numberOfDecisionEpochs2,
			double securitisationRateIncrementErevRoth, double securitisationRateMaxChangeErevRoth, 
			double GibbsBoltzmannParameterErevRoth, double genericExperimentationFactor, 
			double maximumExperimentationFactor, double minimumExperimentationFactor, 
			double genericRecencyFactorErevRoth, 
			double maxRecencyFactorErevRoth, double minRecencyFactorErevRoth, 
			double loanMarketSentimentShare,
			double betaDistributionAlphaGeneric, double betaDistributionBetaGeneric, double betaDistributionAlphaMin,
			double betaDistributionBetaMin, double betaDistributionAlphaMax, double betaDistributionBetaMax,
			double scalingParameterErevRoth, int randomSeedErevRoth) {
		// TODO Auto-generated method stub
		Parameters.ErevRothLearnerUpdateType = ErevRothLearnerUpdateType;
		Parameters.ErevRothProbabilityModel = ErevRothProbabilityModel;
		Parameters.periodByPeriodCalculationsErevRoth = periodByPeriodCalculationsErevRoth;
		Parameters.identicalParameterSelection = identicalParameterSelection;
		Parameters.numberOfIterationsErevRoth = numberOfIterations2;
		Parameters.numberOfDecisionEpochsErevRoth = numberOfDecisionEpochs2;
		Parameters.securitisationRateIncrementErevRoth = securitisationRateIncrementErevRoth;//
		Parameters.securitisationRateMaxChangeErevRoth = securitisationRateMaxChangeErevRoth;//
		Parameters.numberOfActionsErevRoth = (int) (1+ (2*(Parameters.securitisationRateMaxChangeErevRoth
				/Parameters.securitisationRateIncrementErevRoth)));
		Parameters.GibbsBoltzmannParameterErevRoth = GibbsBoltzmannParameterErevRoth;
		Parameters.genericExperimentationFactor = genericExperimentationFactor;
		Parameters.maximumExperimentationFactor = maximumExperimentationFactor;
		Parameters.minimumExperimentationFactor = minimumExperimentationFactor;
		Parameters.genericRecencyFactorErevRoth = genericRecencyFactorErevRoth;
		Parameters.maxRecencyFactorErevRoth = maxRecencyFactorErevRoth;
		Parameters.minRecencyFactorErevRoth = minRecencyFactorErevRoth;
		Parameters.loanMarketSentimentShareErevRoth = loanMarketSentimentShare;
		Parameters.betaDistributionAlphaGenericErevRoth = betaDistributionAlphaGeneric;
		Parameters.betaDistributionBetaGenericErevRoth = betaDistributionBetaGeneric;
		Parameters.betaDistributionAlphaMinErevRoth = betaDistributionAlphaMin;
		Parameters.betaDistributionBetaMaxErevRoth = betaDistributionBetaMax;
		Parameters.betaDistributionAlphaMaxErevRoth = betaDistributionAlphaMax;
		Parameters.betaDistributionBetaMinErevRoth = betaDistributionBetaMin;
		Parameters.scalingParameterErevRoth = scalingParameterErevRoth;
		Parameters.randomSeedErevRoth = randomSeedErevRoth;
		
		
//	System.out.println(toStringErevRoth());
	}

	
	public static String toStringHeuristics(){
		String discription = new String(" "
				+ "this.periodByPeriodCalculationsHeuristic "
				+ "this.numberOfIterationsHeuristic "
				+ "this.numberOfDecisionEpochsHeuristic "
				+ "this.greedParameterIncrement "
				+ "this.genericBias "
				+ "this.maximumBias "
				+ "this.IdenticalStateSelectionBias "
				+ "this.minimumBias "
				+ "this.loanMarketSentimentShare "
				+ "this.betaDistributionAlphaGeneric "
				+ "this.betaDistributionBetaGeneric "
				+ "this.betaDistributionAlphaMin "
				+ "this.betaDistributionBetaMin "
				+ "this.betaDistributionAlphaMax "
				+ "this.betaDistributionBetaMax "
				+ "\n"
				+ " " + Parameters.periodByPeriodCalculationsHeuristic + " " + Parameters.numberOfIterationsHeuristic 
				+ " " + Parameters.numberOfDecisionEpochsHeuristic + " " + Parameters.greedParameterIncrement
				+ " " + Parameters.genericBias+ " " + Parameters.maximumBias + " " + Parameters.IdenticalStateSelectionBias 
				+ " " + Parameters.minimumBias+ " " + Parameters.loanMarketSentimentShare
				+  " " + Parameters.betaDistributionAlphaGeneric
				+ " " + Parameters.betaDistributionBetaGeneric+ " " + Parameters.betaDistributionAlphaMin
				+ " " + Parameters.betaDistributionBetaMin + " " + Parameters.betaDistributionAlphaMax + " " 
				+ Parameters.betaDistributionBetaMax);
		return discription;
	}
	
	
	public static String toStringErevRoth(){
		String discription = new String(" "
				+ "Parameters.ErevRothLearnerUpdateType "
				+ "Parameters.ErevRothProbabilityModel "
				+ "Parameters.periodByPeriodCalculationsErevRoth "
				+ "Parameters.identicalParameterSelection "
				+ "Parameters.numberOfIterationsErevRoth "
				+ "Parameters.numberOfDecisionEpochsErevRoth "
				+ "Parameters.securitisationRateMaxChangeErevRoth "
				+ "Parameters.securitisationRateIncrementErevRoth "
				+ "Parameters.numberOfActionsErevRoth "
				+ "Parameters.GibbsBoltzmannParameterErevRoth "
				+ "Parameters.genericExperimentationFactor "
				+ "Parameters.maximumExperimentationFactor "
				+ "Parameters.minimumExperimentationFactor "
				+ "Parameters.genericRecencyFactorErevRoth "
				+ "Parameters.maxRecencyFactorErevRoth "
				
				+ "Parameters.minRecencyFactorErevRoth "
				+ "Parameters.betaDistributionAlphaGenericErevRoth "
				+ "Parameters.betaDistributionBetaGenericErevRoth "
				+ "Parameters.GibbsBoltzmannParameterErevRoth "
				+ "Parameters.betaDistributionAlphaMinErevRoth "
				+ "Parameters.betaDistributionBetaMaxErevRoth "
				+ "Parameters.betaDistributionAlphaMaxErevRoth "
				+ "Parameters.betaDistributionBetaMinErevRoth "
				+ "Parameters.scalingParameterErevRoth "
				+ "Parameters.randomSeedErevRoth "
				+ "\n"
				+ " " + Parameters.ErevRothLearnerUpdateType + " " + Parameters.ErevRothProbabilityModel 
				+ " " + Parameters.periodByPeriodCalculationsErevRoth + " " + Parameters.identicalParameterSelection
				+ " " + Parameters.numberOfIterationsErevRoth+ " " + Parameters.numberOfDecisionEpochsErevRoth + " " 
				+ Parameters.securitisationRateMaxChangeErevRoth 
				+ " " + Parameters.securitisationRateIncrementErevRoth + " " + Parameters.numberOfActionsErevRoth
				+  " " + Parameters.GibbsBoltzmannParameterErevRoth
				+ " " + Parameters.genericExperimentationFactor + " " + Parameters.maximumExperimentationFactor
				+ " " + Parameters.minimumExperimentationFactor + " " + Parameters.genericRecencyFactorErevRoth + " " 
				+ Parameters.maxRecencyFactorErevRoth
				
				+ " " + Parameters.minRecencyFactorErevRoth + " " + Parameters.betaDistributionAlphaGenericErevRoth
				+  " " + Parameters.betaDistributionBetaGenericErevRoth
				+ " " + Parameters.betaDistributionAlphaMinErevRoth + " " + Parameters.betaDistributionBetaMaxErevRoth
				+ " " + Parameters.betaDistributionAlphaMaxErevRoth + " " + Parameters.betaDistributionBetaMinErevRoth + " " 
				+ Parameters.scalingParameterErevRoth  + " " + Parameters.randomSeedErevRoth + " "
				);
		return discription;
	}


}
