



import jas.engine.Sim;
import jas.engine.SimEngine;
import jas.engine.SimModel;
import jas.engine.gui.JAS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.commons.math3.distribution.BetaDistribution;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;






public class MarkoseDYangModel_V01 extends SimModel {


	public MarkoseDYangEconomy econ;
	public MarkoseDYangEnvironment env;
	public String enviromentName;
	public int maxRun = 10;




	String modelPeriodSting;
	int modelPeriod;
	boolean internalSecuritise;
	boolean userAssetLiabilityDataInput;
	boolean securitise;
	boolean securitiseLinear;
	double securitisationRate;
	double defaultProbabilityBankExpectations;
	double securitisationCostConstantFactor;
	int numberOfConstituentsPerMBSIssuanse;
	double primeMortgageLTV;
	double subprimeMortgageLTV;
	double userDefinedBankAssets;
	double userDefinedBankLiabilities;

	public double bankAssetReturn;
	public double bankLiabilityExpense;
	public double returnOnGlobalEquity;

	public int bankCount;
	public ArrayList<Bank> bankList = new ArrayList<Bank>();
	public ArrayList<Bank> failedBankList = new ArrayList<Bank>();


	//Model Creation variables
	public int simulationStartYr;
	public int simulationStartQtr;
	public int dataCallenderStartMonth;
	public ArrayList<Calendar> loanIssuanceCalenders;
	public Calendar start;
	public Calendar startUpdated;




	//Loan and Borrower Creation Variables
	public String rateTypeString;
	public RateType rateType;
	public String paymentScheduleString;
	public PaymentSchedule paymentSchedule;
	public String borrowerTypeString;
	public BorrowerType bType;
	public double assetValueMin;
	public double assetValueMax;
	public double aveIncomeMin;
	public double aveIncomeMax;
	public int resetWindow;
	public double currentRiskFreeRate;
	public double mortgageLTVRatioMin;
	public double mortgageLTVRatioMax;
	public int mortgageMaturityMax;
	public int mortgageMaturityMin;
	public int loansPerMBSIssuance;


	/**this value at the bank loan issuance level defines the probability bias for
	 * setting the loan to value ratio and average income of the borrower
	 *
	 */
	public double probabilityOfSubPrime;




	/**this value at the bank loan issuance level defines the probability bias for
	 * determining whether the loan is issued to a borrower who is severely burdened by housing cost
	 * this in turn will determine the probability that the borrower will default if the value of the
	 * house/asset underlying the mortgage falls below the market value of the mortgage
	 *
	 */
	public double probabilityOfHouseCostSevereBurden;




	/**this value at the bank loan issuance level defines the probability bias for
	 * setting the loan maturity
	 *
	 */
	public double mortgageMaturityMinProbability;




	//Life, Insurance and Pension Fund required Variables
	private int investorCount;
	public ArrayList<CInvestor> lapfList = new ArrayList<CInvestor>();
	public double opportunityCostOfFixedIncomeInvestment;
	public double lapfRegulatorySolvancyRatio;
	public double returnOnGlobalCredit;

	/**
	 * this is defined at fund valuation period by actuaries on an annual or quarterly basis
	 * It is nevertheless predifined
	 */
	public double annualExpectedPayout;
	public double annualExpectPayoutRebalancingRate;
	private double bankRegulatoryCapitalRatio;
	private String internalSecuritiseString;
	private String userAssetLiabilityDataInpuString;
	private String securitiseString;
	private String securitiseLinearString;


	/**
	 * These variables are for the user defined the structuring of MBS Issuances
	 */

	public double AA_Rated_MBS_Coupon;
	public double nonAA_Rated_MBS_Coupon;
	public double AA_Rated_MBS_Probability;
	public String lapfQuadraticCostFunctionString;
	public boolean lapfQuadraticCostFunction;
	public Double lapfPeriodicLiabilityPaymentRate;
	public Double lapfPremiumContributionsRate;
	public String shortSellingString;
	public boolean shortSelling;
	public String lapfConstantContractualObligationsString;
	public boolean lapfConstantContractualObligations;
	public Double userDefinedLAPFAssets;
	public Double userDefinedLAPFLiabilities;
	public String bnksOnlyAnalysisString;
	public boolean bnksOnlyAnalysis;

	public LAPFTraditionalAssetVariationType traditionalAssetVariationType;
	public String traditionalAssetVariationTypeString;

	public ArrayList<MDPMarkovAgent> MDPMarkovAgentList = new ArrayList<MDPMarkovAgent>();
	
	public ArrayList<Dealer_MarketMaker> brokerDealMaketMakerList = new ArrayList<Dealer_MarketMaker>();
	public Double probabilityOfEquityReturnExpectations;
	public String lapfMultiPeriodSolvancyModelString;
	public boolean lapfMultiPeriodSolvancyModel;
	public boolean interestSpreadExperiment;
	public String interestSpreadExperimentString;
	public double recoveryRateOnTraditionalAsset;
	public double recoveryRateOnCreditAsset;


	public ArrayList <Double> predefinedEquityFundReturns = new ArrayList <Double>();

	public GeoEconoPoliticalSpace world;

	private Parameters params;

	CRTApplicationFrame frame = null;
	private String multiPeriodAnalysisString;
	private boolean multiPeriodAnalysis;
	private int genericLoanResetPeriod;
	private String seniorTrancheQualityString;
	private String mezzTrancheQualityString;
	private String juniorTrancheQualityString;
	private ArrayList<Double> MBSCouponsList = new ArrayList<Double>();
	private ArrayList<Double> MBSDefaultsList = new ArrayList<Double>();
	private double seniourTrancheCoupon;
	private double seniourTrancheDefaultRate;
	private double mezzTrancheCoupon;
	private double mezzTrancheDefaultRate;
	private double juniourTrancheCoupon;
	private double juniourTrancheDefaultRate;
	private int securitisationRateDecisionHorizon;
	private double fullyIndexedContractRateSpread;
	private double genericPostRateResetDafaultRate;
	private double genericPostRateResetCoupon;
	private String constantQSecuritiseString;
	private boolean constantQSecuritise;
	
	public static String lapfTypeString;
	private String fundsExpectationsString;
	private int fundType;
	private int fundExpectations;
	private double fundExpectationsBias;
	
	
	
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
	
	
	private String decisionAnalysisPeriodEndString;
	private boolean portfolioWeightChoiceModel;
	private boolean stochasticStateTransitions;
	private boolean linearCostFunction;
	private double linearfactor;
	private double quadraticfactor;
	private double assetWieghtIncrements;
	private double maximumPermissbleChangeInWeight;
	private double changeInWeightIncrement;
	private int numberOfEpisodes;
	private int numberOfDecisionEpochs;
	private double epsilonError;
	private double gammaDiscountFactor;
	private double accuracyThreshold;
	private double RLLearningRateAlpha;
	private double RLLearningLambda;
	private int RLLearningType;
	private int RLActionSelectionType;
	private boolean periodByPeriodCalculations;
	private int numberOfIterationsHeuristic;
	private int numberOfDecisionEpochsHeuristic;
	private boolean identicalStateSelectionBias;
	private double securitisationRateIncrement;
	private double greedParameterIncrement;
	private double genericBias;
	private double maximumBias;
	private double minimumBias;
	private double loanMarketSentimentShare;
	private double hueristicCostConstantLowDemand;
	private double hueristicCostConstantMidDemand;
	private double hueristicCostConstantHighDemand;
	private double betaDistributionAlphaGeneric;
	private double betaDistributionBetaGeneric;
	private double betaDistributionAlphaMin;
	private double betaDistributionBetaMax;
	private double betaDistributionAlphaMax;
	private double betaDistributionBetaMin;
	public static String[] InvestorAgentTemprement = new String[]{"Simple","Bull","Bear","Passive","Rational"};
	
	
	public String ErevRothLearnerUpdateType = "Standard";//can be "Standard" or "Nicolaosen Variation";
	public String ErevRothProbabilityModel = "Standard";//can be "Standard" or "Gibbs-Boltzmann";
	public boolean periodByPeriodCalculationsErevRoth = true;
	public boolean identicalParameterSelection = true;
	public int numberOfIterationsErevRoth = 1000;
	public int numberOfDecisionEpochsErevRoth = 5;
	public double securitisationRateIncrementErevRoth = 0.05;//sliders
	public  double securitisationRateMaxChangeErevRoth = 0.1;//sliders
	public  int numberOfActionsErevRoth = (int) (1+ (2*(Parameters.securitisationRateMaxChangeErevRoth
			/Parameters.securitisationRateIncrementErevRoth)));
	public  double GibbsBoltzmannParameterErevRoth = 1000;
	public  double scalingParameterErevRoth = 9;
	public   double averageStartingRewardErevRoth = 100;
	public  double genericExperimentationFactor = 0.75;
	public  double maximumExperimentationFactor = 0.9;
	public  double minimumExperimentationFactor = 0.1;
	public  double  genericRecencyFactorErevRoth = 0.1;
	public  double  minRecencyFactorErevRoth = 0.1;
	public  double  maxRecencyFactorErevRoth = 0.75;
	public  double loanMarketSentimentShareErevRoth = 0.5;
	public  double betaDistributionAlphaGenericErevRoth = 2;
	public  double betaDistributionBetaGenericErevRoth = 5;
	public  double betaDistributionAlphaMinErevRoth = 1;
	public  double betaDistributionBetaMaxErevRoth = 7;
	public  double betaDistributionAlphaMaxErevRoth = 7;
	public  double betaDistributionBetaMinErevRoth = 1;
	public  int randomSeedErevRoth = 74974984;
	public  RandomEngine randomEngine;


	public static MDPModelInputParameters RLModelParameters;
	public static MDPHeuristicDecisionParameters HeuristicDecisionModelParameters;
	public static MDPRothErevParameters ErevRothDecisionModelParameters;
	
	public static boolean heuristicSecuritisationModel;
	public static boolean erevRothModel;
	
	public ActionList actionsDomainErevRoth = new ActionList();
	

	public MarkoseDYangModel_V01 (CRTApplicationFrame frame){
		this.frame = frame;
	}

	public MarkoseDYangModel_V01 (){

	}


	public MarkoseDYangModel_V01 (Parameters param){
		this.params = param;
	}




	public void buildEnviroment() {
		// TODO Auto-generated method stub
		this.env = new MarkoseDYangEnvironment(this.enviromentName);
		this.env.setBoolInternalSecuritise(this.internalSecuritise);
		this.env.setBoolNonlinear(this.securitiseLinear);
		this.env.setBoolBankOnlyAnalysis(this.bnksOnlyAnalysis);
		this.env.setBoolSecuritize(this.securitise);//shortSelling
		this.env.setBoolShortselling(this.shortSelling);
		this.env.setMultiPeriodAnalysis(this.multiPeriodAnalysis);//multi-period bank decision horizon model
		this.env.setSecuritisationRateDecisionHorizon(this.securitisationRateDecisionHorizon);
		this.env.setLAPFConstantContractualObligations(this.lapfConstantContractualObligations);
		this.env.setLAPFMultiPeriodSolvancyModel(this.lapfMultiPeriodSolvancyModel);
		this.env.setSimulationYearsMultiplier(this.paymentSchedule);//this.lapfConstantContractualObligations
		this.env.setSimulationStartYr(this.simulationStartYr);
		this.env.setSimulationStartQtr(this.simulationStartQtr);
		this.env.setDataCallenderStartMonth();
		this.env.setConstantQ(this.constantQSecuritise);
		this.setLoanIssuanceStartCalender(this.simulationStartYr, this.dataCallenderStartMonth, 1);
		this.env.setDecisionAnalysisPeriodEndString(this.decisionAnalysisPeriodEndString); 
		this.env.setBoolPortfolioWeightChoiceModel(this.portfolioWeightChoiceModel); 
		this.env.setBooleanStochasticStateTransitions(this.stochasticStateTransitions);
		//the following if-else statement is to invert the selection in the gui so it is usable in the MDP model 
		if(this.lapfQuadraticCostFunction == true){
			this.env.setBooleanLinearCostFunction(false);
		} else {
			this.env.setBooleanLinearCostFunction(true);
		}
		this.env.setNumberOfEpisodes(this.numberOfEpisodes);	
		this.env.setAssetWieghtIncrements(this.assetWieghtIncrements);	
		this.env.setChangeInWeightIncrement(this.changeInWeightIncrement);	
		this.env.setMaximumPermissbleChangeInWeight(this.maximumPermissbleChangeInWeight);	
		this.env.setEpsilonError(this.epsilonError);	
		this.env.setGammaDiscountFactor(this.gammaDiscountFactor); 
		this.env.setAccuracyThreshold(this.accuracyThreshold);
		this.env.setInvestorHorizon(this.numberOfIterations);
		this.env.setInvestorCount(this.investorCount);
		this.env.setLAPFTypeString(MarkoseDYangModel_V01.lapfTypeString);
		this.env.setBankHeuristicLearning(MarkoseDYangModel_V01.heuristicSecuritisationModel);
		this.env.setBankErevRothLearning(MarkoseDYangModel_V01.erevRothModel);
	}

	public void buildEconomy() {
		// TODO Auto-generated method stub
		this.econ = new MarkoseDYangEconomy(this);
		//                            System.out.println(this.traditionalAssetVariationTypeString);
		this.econ.setInvestorAgentTemprement(MarkoseDYangModel_V01.InvestorAgentTemprement);
		this.econ.setTraditionalAssetVariationTypeString(this.traditionalAssetVariationTypeString);
		//                            System.out.println(this.predefinedEquityFundReturns.size());
		this.econ.setPredefinedEquityFundReturnsBoolean(this.traditionalAssetVariationTypeString);
		if((this.predefinedEquityFundReturns.size()) > 0){
			this.econ.setPredefinedEquityFundReturns(this.predefinedEquityFundReturns);
		}
		
		this.econ.setSeniorTrancheQualityString(this.seniorTrancheQualityString);
		this.econ.setSeniorTrancheCouponAndDefault(this.seniourTrancheCoupon,this.seniourTrancheDefaultRate);
		this.econ.setMezzTrancheQualityString(this.mezzTrancheQualityString);
		this.econ.setMezTrancheCouponAndDefault(this.mezzTrancheCoupon,this.mezzTrancheDefaultRate);
		this.econ.setJuniorTrancheQualityString(this.juniorTrancheQualityString);
		this.econ.setJuniorTrancheCouponAndDefault(this.juniourTrancheCoupon,this.juniourTrancheDefaultRate);
		this.econ.setLoanResetPeriod(this.genericLoanResetPeriod);
		this.econ.setPerTrancheMBSCoupns(this.MBSCouponsList);
		this.econ.setPerTrancheMBSDefaults(this.MBSDefaultsList);
		if(this.securitise == true) {
			this.econ.setGlobalSecuritisationRate(this.securitisationRate);
		} else {
			this.econ.setGlobalSecuritisationRate(0.0);
		}


		//                                            setMortgageMaturityMinProbability(0);


		//                            this.econ.setProbabilityOfHouseCostSevereBurden(this.probabilityOfHouseCostSevereBurden);
		this.econ.setLapfRegulatorySolvancyRatio(this.lapfRegulatorySolvancyRatio);
		//                            this.econ.setAnnualExpectedPayout(this.annualExpectedPayout);//LAPF Annual expected payout
		//                            this.econ.setAnnualExpectPayoutRebalancingRate(this.annualExpectPayoutRebalancingRate);
		this.econ.setLAPFQuadraticCostFunction(this.lapfQuadraticCostFunction);
		//                            this.econ.setLAPFPremiumContributionsRate(this.lapfPremiumContributionsRate);
		this.econ.setLAPFPeriodicLiabilityPaymentRate(this.lapfPeriodicLiabilityPaymentRate);
		this.econ.setOpportunityCostOfIncomeInvestment(this.opportunityCostOfFixedIncomeInvestment);
		this.econ.setReturnOnGlobalEquity(this.returnOnGlobalEquity);
		this.econ.setCurrentRiskFreeRate(this.currentRiskFreeRate);


		// 0.08, 0.96, 0.03, 0.16, 0.6, 0.11, 0.04
		this.econ.setRegCapitalRatio(this.bankRegulatoryCapitalRatio);
		this.econ.setSurvivalRate(1- this.defaultProbabilityBankExpectations);
		this.econ.setSecuritisationCostConstantFactor(this.securitisationCostConstantFactor);
		this.econ.setGenericDefaultsRates(1- this.defaultProbabilityBankExpectations, this.securitisationCostConstantFactor);
		
		
		this.econ.setTraditionalAssetsSurvivalRate(1-this.probabilityOfEquityReturnExpectations);
		this.econ.setRecoveryRateOnTraditionalAsset(this.recoveryRateOnTraditionalAsset);
		this.econ.setRecoveryRateOnCreditAsset(this.recoveryRateOnCreditAsset);
		this.econ.setDefaultRecoveryRateOnCreditAsset(this.recoveryRateOnCreditAsset);
		//                            econ.setAAAMBSCoupon(this.AA_Rated_MBS_Coupon);
		//                            econ.setNonAAAMBSCoupon(this.nonAA_Rated_MBS_Coupon);
		//                            econ.setAAAProbability(this.AA_Rated_MBS_Probability);
		this.econ.setAssetReturn(this.bankAssetReturn);
		this.econ.setLiabExpense(this.bankLiabilityExpense);
		this.econ.setFullyIndexedContractRateSpread(this.fullyIndexedContractRateSpread);
		this.econ.setGenericPostRateResetDefaultRate(this.genericPostRateResetDafaultRate);
		this.econ.setGenericPostRateResetCoupon(this.genericPostRateResetCoupon);
		
		StochasticProcessParameters stochParams = new StochasticProcessParameters(this.numberOfIterations, this.numberOfPaths, this.pathLength, 
				this.timeShit_dt, this.initialCreditAssetvalue,	this.cir_AlphaCreditAssetMeanReversion, this.cir_ThetaCreditAssetMeanValue, 
				this.standardDeviationCreditAsset, this.initialTraditionalAssetvalue, this.driftMeanTraditionalAsset, 
				this.standardDeviationTraditionalAsset, this.hestonLongTermVarianceTraditionalAsset, this.hestonMeanReversionRateTraditionalAsset,
				this.hestonVarianceVolatilityTraditionalAsset);
		this.econ.setStochasticProcessParameters(stochParams);
		
		boolean val;
		if(this.lapfQuadraticCostFunction == true){
			val = false;
		} else {
			val = true;
		}
		this.econ.setLinearfactor(this.linearfactor);
		this.econ.setQuadraticfactor(this.quadraticfactor);
		this.econ.setMDPModelParameters(MarkoseDYangModel_V01.RLModelParameters);
		this.econ.setMDPHeuristicModelParameters(MarkoseDYangModel_V01.HeuristicDecisionModelParameters);
		
//		this.econ.setBankHeuristicLearning(this.heuristicSecuritisationModel);
		this.econ.setRandomProbabilityEngine(this.randomEngine);

		double passiveCreditDefaults = ((1-this.defaultProbabilityBankExpectations) - (1 - this.defaultProbabilityBankExpectations))
				/(1 - this.defaultProbabilityBankExpectations);
		double rationalCreditDefaults = ((1-econ.getGenericPostRateResetDafaultRate()) - (1 - this.defaultProbabilityBankExpectations))
				/(1 - this.defaultProbabilityBankExpectations);
				
		double equityDefaults = this.probabilityOfEquityReturnExpectations;			


				double lngTermDefaultBull = params.getCitibankResearchMBSDefaultsList().get(1);
		double bullCreditDefaults = ((1-lngTermDefaultBull) - (1 - this.defaultProbabilityBankExpectations))
				/(1 - this.defaultProbabilityBankExpectations);
		//The following has been introduced on May 24, 2014 to reduce the excessive impact of changing expectations of defaults
		double bullCreditDefaultsReduced = (0.5*((1-lngTermDefaultBull) - (1 - this.defaultProbabilityBankExpectations)))
				/(1 - this.defaultProbabilityBankExpectations);
		double lngTermDefaultBear = params.getCitibankResearchMBSDefaultsList().get(params.getCitibankResearchMBSDefaultsList().size()-1);
		double bearCreditDefaults = ((1-lngTermDefaultBear) - (1 - this.defaultProbabilityBankExpectations))
				/(1 - this.defaultProbabilityBankExpectations);
		//The following has been introduced on May 24, 2014 to reduce the excessive impact of changing expectations of defaults
		double bearCreditDefaultsReduced = (0.5*((1-lngTermDefaultBear) - (1 - this.defaultProbabilityBankExpectations)))
				/(1 - this.defaultProbabilityBankExpectations);
		
		
		
		Economy.setRationalCreditDefaults(rationalCreditDefaults); 
		Economy.setPassiveCreditDefaults(passiveCreditDefaults); 
		Economy.setEquityDefaults(equityDefaults);
//		Economy.setBullCreditDefaults(bullCreditDefaults);
//		Economy.setBearCreditDefaults(bearCreditDefaults);
		//The following has been introduced on May 24, 2014 to reduce the excessive impact of changing expectations of defaults
		Economy.setBullCreditDefaults(bullCreditDefaultsReduced);
		Economy.setBearCreditDefaults(bearCreditDefaultsReduced);
		
	}






	private void addBrokerDealMaketMakersToEconomy(
			ArrayList<Dealer_MarketMaker> brokerDealMaketMakerList2) {
		// TODO Auto-generated method stub
		econ.addBrokerDealMaketMakersToEconomy(brokerDealMaketMakerList2);
	}

	
	private void addMarkovAgentsToEconomy(
			ArrayList<MDPMarkovAgent> MarkovAgentsList2) {
		// TODO Auto-generated method stub
		econ.addMarkovAgentsToEconomy(MarkovAgentsList2);
	}

	private void addInvestorsToEconomy(ArrayList<CInvestor> lapfList2) {
		// TODO Auto-generated method stub
		econ.addInvestorsToEconomy(lapfList2);
	}


	private void addBanksToEconomy(ArrayList<Bank> bankList2) {
		// TODO Auto-generated method stub
		econ.addBanksToEconomy(bankList2);
	}
	
	public void setModelDefaultParameters(Parameters params) {
		// TODO Auto-generated method stub
		this.setEnviromentName("FDIC Banks Securitisation Model");
		this.params = params;

		//                            this.modelPeriod = 0;


		modelPeriodSting = params.getModelPeriodSting();//"2002-2009";
		String modelPeriodSting = params.getModelPeriodSting();
		this.modelPeriodSting = modelPeriodSting;
		this.seniorTrancheQualityString = params.getSeniorTrancheQualityString();
		this.mezzTrancheQualityString = params.getMezzTrancheQualityString();
		this.juniorTrancheQualityString = params.getJuniorTrancheQualityString();
		
		
		this.seniourTrancheCoupon = params.getSeniourTrancheCoupon();
		this.seniourTrancheDefaultRate = params.getSeniourTrancheDefaultRate();

		this.mezzTrancheCoupon = params.getMezzTrancheCoupon();
		this.mezzTrancheDefaultRate = params.getMezzTrancheDefaultRate();

		this.juniourTrancheCoupon = params.getJuniourTrancheCoupon();
		this.juniourTrancheDefaultRate = params.getJuniourTrancheDefaultRate();

		
		this.MBSCouponsList = new ArrayList<Double>(params.getPerTrancheMBSCoupns().size());
		for (int i = 0; i < params.getPerTrancheMBSCoupns().size(); i++) {
			this.MBSCouponsList.add(params.getPerTrancheMBSCoupns().get(i));
		}
		

		this.MBSDefaultsList= new ArrayList<Double>(params.getPerTrancheMBSDefaults().size());
		for (int i = 0; i < params.getPerTrancheMBSDefaults().size(); i++) {
			this.MBSDefaultsList.add(params.getPerTrancheMBSDefaults().get(i));
		}
		
		
		//                            System.out.println("modelPeriodSting = " +modelPeriodSting);
		if(modelPeriodSting == "2002-2009"){
			this.modelPeriod = 1;
			this.simulationStartYr = 2002;
			this.simulationStartQtr = 1;
		}
		else if(modelPeriodSting == "2002-2005"){
			this.modelPeriod = 2;
			this.simulationStartYr = 2002;
			this.simulationStartQtr = 1;
		}
		else if(modelPeriodSting == "2006-2009"){
			this.modelPeriod = 3;
			this.simulationStartYr = 2006;
			this.simulationStartQtr = 1;
		}
		//                            System.out.println("Collector Value: "+this.modelPeriod);
		this.setDataCallenderStartMonth();
		//weekly, bi_weekly, monthly, quarterly, semi_annually, annually
		this.paymentScheduleString = params.getPaymentScheduleString();//"individual_generic";
		String tpaymentSchedule = params.getPaymentScheduleString();
		//                            System.out.println("tpaymentSchedule = " +tpaymentSchedule);
		if(tpaymentSchedule == "weekly"){
			this.paymentSchedule = PaymentSchedule.weekly;
			this.paymentScheduleString = "weekly";
		}
		else if(tpaymentSchedule == "bi_weekly"){
			this.paymentSchedule = PaymentSchedule.bi_weekly;
			this.paymentScheduleString = "bi_weekly";
		}
		else if(tpaymentSchedule == "monthly"){
			this.paymentSchedule = PaymentSchedule.monthly;    
			this.paymentScheduleString = "monthly";
		}
		else if(tpaymentSchedule == "quarterly"){
			this.paymentSchedule = PaymentSchedule.monthly;    
			this.paymentScheduleString = "monthly";
		}
		else if(tpaymentSchedule == "semi_annually"){
			this.paymentSchedule = PaymentSchedule.semi_annually;        
			this.paymentScheduleString = "semi_annually";
		}
		else if(tpaymentSchedule == "annually"){
			this.paymentSchedule = PaymentSchedule.annually;    
			this.paymentScheduleString = "annually";
		}
		this.traditionalAssetVariationTypeString = params.getTraditionalAssetVariationTypeString();
		//                            System.out.println("traditionalAssetVariationTypeString = " +traditionalAssetVariationTypeString);
		//the below was modified March-17-2014 to keep data and simulation in defined ranges 
		int valuseter = 0;
		if("constant".equals(traditionalAssetVariationTypeString)){
			valuseter = 1;
			this.traditionalAssetVariationType = LAPFTraditionalAssetVariationType.constant;
			this.maxRun = 5;
		}
		else if("CCFEA_SS2007".equals(traditionalAssetVariationTypeString)){
			valuseter = 2;
			this.traditionalAssetVariationType = LAPFTraditionalAssetVariationType.pre_definedCCFEA_SS2007;              
			this.setPredefinedEquityFundReturns(LAPFTraditionalAssetsReturns.lapfTraditionalAssetsCCFEASummerSchool2007);
			this.maxRun = this.predefinedEquityFundReturns.size();
		}
		else if("SnP10yrReturns".equals(traditionalAssetVariationTypeString)){
			valuseter = 3;
			this.traditionalAssetVariationType = LAPFTraditionalAssetVariationType.pre_definedSnP10yrReturns;
			this.setPredefinedEquityFundReturns(LAPFTraditionalAssetsReturns.snp10yearReturns);
			this.maxRun = this.predefinedEquityFundReturns.size();
		}
		else if("SnPCAGR2002_09".equals(traditionalAssetVariationTypeString)){
			valuseter = 4;
			this.traditionalAssetVariationType = LAPFTraditionalAssetVariationType.pre_definedSnPCAGR2002_09;        
			this.setPredefinedEquityFundReturns(LAPFTraditionalAssetsReturns.snpCompoundAnnualGrowthRate2002_2009);
			this.maxRun = this.predefinedEquityFundReturns.size();
		}
		else if("EquityFundReturns2005_09".equals(traditionalAssetVariationTypeString)){
			valuseter = 5;
			this.traditionalAssetVariationType = LAPFTraditionalAssetVariationType.pre_definedEquityFundReturns2005_09;
			this.setPredefinedEquityFundReturns(LAPFTraditionalAssetsReturns.equityFund5yearReturns);
			this.maxRun = this.predefinedEquityFundReturns.size();
		}
		else if("stochastic".equals(traditionalAssetVariationTypeString)){
			valuseter = 6; 
			this.traditionalAssetVariationType = LAPFTraditionalAssetVariationType.stochastic;
			this.maxRun = 5;
		}
		else if("transition_model".equals(traditionalAssetVariationTypeString)){
			valuseter = 7; 
			this.traditionalAssetVariationType = LAPFTraditionalAssetVariationType.transition_model;
//			this.maxRun = 10;
			this.maxRun = 5;
		}
		this.maxRun++;//this is to ensure that the full data range is used.
		//                            System.out.println("valuseter = " +valuseter);


		this.internalSecuritiseString = params.getInternalSecuritiseString();//"false";
		boolean boolInternalSecuritise = Boolean.parseBoolean(params.getInternalSecuritiseString());
		if(boolInternalSecuritise == true){
			this.internalSecuritise = true;
		}
		else if(boolInternalSecuritise == false){
			this.internalSecuritise = false;   
		}
		
		this.constantQSecuritiseString = params.getConstantQSecuritiseString();//"false";
		boolean constantQSecuritiseParam = Boolean.parseBoolean(params.getConstantQSecuritiseString());
		if(constantQSecuritiseParam == true){
			this.constantQSecuritise = true;
		}
		else if(constantQSecuritiseParam == false){
			this.constantQSecuritise = false;   
		}
		
		//                            System.out.println(this.userAssetLiabilityDataInpuString);
		boolean boolUserDataInput = Boolean.parseBoolean(params.getUserAssetLiabilityDataInpuString());
		if(boolUserDataInput == true){
			this.userAssetLiabilityDataInput = true;
			this.userAssetLiabilityDataInpuString = "true";
		}
		else if(boolUserDataInput == false){
			this.userAssetLiabilityDataInput = false;
			this.userAssetLiabilityDataInpuString = "false";
		}
		//                                            System.out.println("Banks userAssetLiabilityDataInput: " + userAssetLiabilityDataInput);
		this.securitiseString = params.getSecuritiseString();//"true";
		this.securitise = Boolean.parseBoolean(params.getSecuritiseString());
		this.securitiseLinearString = params.getSecuritiseLinearString();//"false";
		this.securitiseLinear = Boolean.parseBoolean(params.getSecuritiseLinearString());
		this.bnksOnlyAnalysisString = params.getBnksOnlyAnalysisString();//"false";
		this.bnksOnlyAnalysis = Boolean.parseBoolean(params.getBnksOnlyAnalysisString());
		//                            this.securitisationRate = Double.parseDouble((String) params.getValue("securitisationRate"));
		this.securitisationRate = Double.parseDouble(params.getSecuritisationRate());
		this.defaultProbabilityBankExpectations = Double.parseDouble(params.getDefaultProbabilityBankExpectations());
		this.probabilityOfEquityReturnExpectations = Double.parseDouble(params.getProbabilityOfEquityReturnExpectations());
		this.securitisationCostConstantFactor = Double.parseDouble(params.getSecuritisationCostConstantFactor());
		//                            this.numberOfConstituentsPerMBSIssuanse = (Integer) params.getValue("numberOfConstituentsPerMBSIssuanse");
		//                            this.primeMortgageLTV = (Double) params.getValue("primeMortgageLTV");
		//                            this.subprimeMortgageLTV = (Double) params.getValue("subprimeMortgageLTV");
		this.userDefinedBankAssets = Double.parseDouble(params.getUserDefinedBankAssets());
		this.userDefinedBankLiabilities = Double.parseDouble(params.getUserDefinedBankLiabilities());
		this.bankAssetReturn = Double.parseDouble(params.getBankAssetReturn());
		this.bankLiabilityExpense = Double.parseDouble(params.getBankLiabilityExpense());
		this.returnOnGlobalEquity = Double.parseDouble(params.getReturnOnGlobalEquity());
		this.bankRegulatoryCapitalRatio = Double.parseDouble(params.getBankRegulatoryCapitalRatio());
		this.bankCount = Integer.parseInt(params.getBankCount());
		this.investorCount = Integer.parseInt(params.getInvestorCount());
		this.recoveryRateOnTraditionalAsset = Double.parseDouble(params.getRecoveryRateOnTraditionalAsset());
		this.recoveryRateOnCreditAsset = Double.parseDouble(params.getRecoveryRateOnCreditAsset());
		//                            this.assetValueMin = (Double) params.getValue("assetValueMin");
		//                            this.assetValueMax = (Double) params.getValue("assetValueMax");
		//                            this.aveIncomeMin = (Double) params.getValue("aveIncomeMin");
		//                            this.aveIncomeMax = (Double) params.getValue("aveIncomeMax");
		//                            this.resetWindow = (Integer) params.getValue("resetWindow");
		 this.currentRiskFreeRate = Double.parseDouble(params.getCurrentRiskFreeRate());
		//                            this.mortgageMaturityMax = (Integer) params.getValue("mortgageMaturityMax");
		//                            this.mortgageMaturityMin = (Integer) params.getValue("mortgageMaturityMin");
		/**this value at the bank loan issuance level defines the probability bias for
		 * setting the loan to value ratio and average income of the borrower
		 *
		 */
		//                            this.probabilityOfSubPrime = (Double) params.getValue("probabilityOfSubPrime");
		/**this value at the bank loan issuance level defines the probability bias for
		 * determining whether the loan is issued to a borrower who is severely burdened by housing cost
		 * this in turn will determine the probability that the borrower will default if the value of the
		 * house/asset underlying the mortgage falls below the market value of the mortgage
		 *
		 */
		//                            this.probabilityOfHouseCostSevereBurden = (Double) params.getValue("probabilityOfHouseCostSevereBurden");
		/**this value at the bank loan issuance level defines the probability bias for
		 * setting the loan maturity
		 *
		 */
		//                            this.mortgageMaturityMinProbability = (Double) params.getValue("mortgageMaturityMinProbability");
		//Life, Insurance and Pension Fund required Variables 
		this.userDefinedLAPFAssets = Double.parseDouble(params.getUserDefinedLAPFAssets());
		//                            System.out.println(this.userDefinedLAPFAssets);
		this.userDefinedLAPFLiabilities = Double.parseDouble(params.getUserDefinedLAPFLiabilities());
		//                            System.out.println(this.userDefinedLAPFLiabilities);
		this.lapfQuadraticCostFunctionString = params.getLapfQuadraticCostFunctionString();//"false";
		this.lapfQuadraticCostFunction = Boolean.parseBoolean(params.getLapfQuadraticCostFunctionString());
		//                            System.out.println("lapfQuadraticCostFunction: " + lapfQuadraticCostFunction);
		this.lapfConstantContractualObligationsString = params.getLapfConstantContractualObligationsString();//"false";
		this.lapfConstantContractualObligations = Boolean.parseBoolean(params.getLapfConstantContractualObligationsString());
		//                            System.out.println("lapfConstantContractualObligations: " + lapfConstantContractualObligations);
		this.lapfMultiPeriodSolvancyModelString = params.getLapfMultiPeriodSolvancyModelString();//"false";
		this.lapfMultiPeriodSolvancyModel = Boolean.parseBoolean(params.getLapfMultiPeriodSolvancyModelString());
		this.shortSellingString = params.getShortSellingString();//"false";
		this.shortSelling = Boolean.parseBoolean(params.getShortSellingString());
		//                            System.out.println("short sell: " + shortSelling);
		this.multiPeriodAnalysisString = params.getMultiPeriodBanks();//"false";
		this.multiPeriodAnalysis = Boolean.parseBoolean(params.getMultiPeriodBanks());
		this.interestSpreadExperimentString = params.getInterestSpreadExperimentString();//"false";
		this.interestSpreadExperiment= Boolean.parseBoolean(params.getInterestSpreadExperimentString());
		//                            System.out.println("int spread iid: " + interestSpreadExperiment);
		this.opportunityCostOfFixedIncomeInvestment = Double.parseDouble(params.getOpportunityCostOfIncomeInvestment());
		this.lapfRegulatorySolvancyRatio = Double.parseDouble(params.getLapfRegulatorySolvancyRatio());
		this.lapfPeriodicLiabilityPaymentRate = Double.parseDouble(params.getLapfPeriodicLiabilityPaymentRate());
		//                            this.lapfPremiumContributionsRate = (Double) params.getValue("lapfPremiumContributionsRate");
		this.genericLoanResetPeriod = Integer.parseInt(params.getLoanResetYear());
		this.fullyIndexedContractRateSpread = Double.parseDouble(params.getFullyIndexedContractRateSpread());
		this.genericPostRateResetDafaultRate = Double.parseDouble(params.getGenericPostRateResetDefaultRate());
		this.genericPostRateResetCoupon = Double.parseDouble(params.getGenericPostRateResetCoupon());
		this.securitisationRateDecisionHorizon = Integer.parseInt(params.getSecuritisationRateDecisionHorizon());
		this.returnOnGlobalCredit = 0.16;
		//this.returnOnGlobalCredit = (Double) params.getValue("returnOnGlobalCredit");
		/**
		 * this is defined at fund valuation period by actuaries on an annual or quarterly basis
		 * It is nevertheless predifined
		 */
		//                            this.annualExpectedPayout= (Double) params.getValue("annualExpectedPayout");
		//                            this.annualExpectPayoutRebalancingRate = (Double) params.getValue("annualExpectPayoutRebalancingRate");
		//                            this.AA_Rated_MBS_Coupon = (Double) params.getValue("AA_Rated_MBS_Coupon");;
		//                            this.nonAA_Rated_MBS_Coupon = (Double) params.getValue("nonAA_Rated_MBS_Coupon");;
		//                            this.AA_Rated_MBS_Probability = (Double) params.getValue("AA_Rated_MBS_Probability");;
		
		
		
		//Stochastic Processes
		
		this.numberOfIterations = params.getNumberOfIterations();
		this.numberOfPaths = params.getNumberOfPaths();
		this.pathLength = params.getPathLength();
		this.timeShit_dt = params.getTimeShit_dt();
		//private String assetName;
		
		this.initialCreditAssetvalue = params.getInitialCreditAssetvalue();
		this.cir_AlphaCreditAssetMeanReversion = params.getCir_AlphaCreditAssetMeanReversion();
		this.cir_ThetaCreditAssetMeanValue = params.getCir_ThetaCreditAssetMeanValue();
		this.standardDeviationCreditAsset = params.getStandardDeviationCreditAsset();


		this.initialTraditionalAssetvalue = params.getInitialTraditionalAssetvalue();
		this.driftMeanTraditionalAsset = params.getDriftMeanTraditionalAsset();
		this.standardDeviationTraditionalAsset = params.getStandardDeviationTraditionalAsset();
		this.hestonLongTermVarianceTraditionalAsset = params.getHestonLongTermVarianceTraditionalAsset();
		this.hestonMeanReversionRateTraditionalAsset = params.getHestonMeanReversionRateTraditionalAsset();
		this.hestonVarianceVolatilityTraditionalAsset = params.getHestonVarianceVolatilityTraditionalAsset();
		this.fundType = params.getLAPFTypeIndex();
		this.fundExpectations = params.getLAPFExpectationsIndex();//{0,1,2,3}
		MarkoseDYangModel_V01.lapfTypeString = params.getLAPFTypeString();
		this.fundsExpectationsString = params.getFundsExpectationsString();
		this.fundExpectationsBias = params.getFundExpectationsBias();
		
		
		
		//Generic MDP Model Parameters
		this.decisionAnalysisPeriodEndString = Parameters.decisionAnalysisPeriodEndString;
		this.portfolioWeightChoiceModel = Parameters.portfolioWeightChoiceModel;
		this.stochasticStateTransitions = Parameters.stochasticStateTransitions;
		this.linearfactor = Parameters.linearfactor;
		this.quadraticfactor = Parameters.quadraticfactor;
		this.assetWieghtIncrements = Parameters.assetWieghtIncrements;
		this.maximumPermissbleChangeInWeight = Parameters.maximumPermissbleChangeInWeight;
		this.changeInWeightIncrement = Parameters.changeInWeightIncrement;
		this.numberOfEpisodes = Parameters.numberOfEpisodes;
		this.epsilonError = Parameters.epsilonError;
		this.gammaDiscountFactor = Parameters.gammaDiscountFactor;
		this.accuracyThreshold = Parameters.accuracyThreshold;
		this.RLLearningRateAlpha = Parameters.RLLearningRateAlpha;
		this.RLLearningLambda = Parameters.RLLearningLambda;
		this.RLLearningType = Parameters.RLLearningType;
		this.RLActionSelectionType = Parameters.RLActionSelectionType;
		this.numberOfDecisionEpochs = Parameters.numberOfDecisionEpochs;
		
		this.periodByPeriodCalculations = Parameters.periodByPeriodCalculationsHeuristic; 
		this.numberOfIterationsHeuristic = Parameters.numberOfIterationsHeuristic;
		this.numberOfDecisionEpochsHeuristic = Parameters.numberOfDecisionEpochsHeuristic;
		this.identicalStateSelectionBias = Parameters.IdenticalStateSelectionBias;
		this.securitisationRateIncrement = Parameters.securitisationRateIncrement;
		this.greedParameterIncrement = Parameters.greedParameterIncrement;
		this.genericBias = Parameters.genericBias;
		this.maximumBias = Parameters.maximumBias;
		this.minimumBias = Parameters.minimumBias;
		this.loanMarketSentimentShare = Parameters.loanMarketSentimentShare;
		this.hueristicCostConstantLowDemand = this.juniourTrancheCoupon;
		this.hueristicCostConstantMidDemand = this.mezzTrancheCoupon; 
		this.hueristicCostConstantHighDemand = this.seniourTrancheCoupon;
		this.betaDistributionAlphaGeneric = Parameters.betaDistributionAlphaGeneric;
		this.betaDistributionBetaGeneric = Parameters.betaDistributionBetaGeneric;
		this.betaDistributionAlphaMin = Parameters.betaDistributionAlphaMin;
		this.betaDistributionBetaMin = Parameters.betaDistributionBetaMin;
		this.betaDistributionAlphaMax = Parameters.betaDistributionAlphaMax;
		this.betaDistributionBetaMax = Parameters.betaDistributionBetaMax;
		this.heuristicSecuritisationModel = params.getHeuristicSecuritisationModel();
		
		
		
		
		
		this.ErevRothLearnerUpdateType = Parameters.ErevRothLearnerUpdateType;
		this.ErevRothProbabilityModel = Parameters.ErevRothProbabilityModel;
		this.periodByPeriodCalculationsErevRoth = Parameters.periodByPeriodCalculationsErevRoth;
		this.identicalParameterSelection = Parameters.identicalParameterSelection;
		this.numberOfIterationsErevRoth = Parameters.numberOfIterationsErevRoth;
		this.numberOfDecisionEpochsErevRoth = Parameters.numberOfDecisionEpochsErevRoth;
		this.securitisationRateIncrementErevRoth = Parameters.securitisationRateIncrementErevRoth;//
		this.securitisationRateMaxChangeErevRoth = Parameters.securitisationRateMaxChangeErevRoth;//
		this.numberOfActionsErevRoth = Parameters.numberOfActionsErevRoth;
		this.GibbsBoltzmannParameterErevRoth = Parameters.GibbsBoltzmannParameterErevRoth;
		this.genericExperimentationFactor = Parameters.genericExperimentationFactor;
		this.maximumExperimentationFactor = Parameters.maximumExperimentationFactor;
		this.minimumExperimentationFactor = Parameters.minimumExperimentationFactor;
		this.genericRecencyFactorErevRoth = Parameters.genericRecencyFactorErevRoth;
		this.maxRecencyFactorErevRoth = Parameters.maxRecencyFactorErevRoth;
		this.minRecencyFactorErevRoth = Parameters.minRecencyFactorErevRoth;
		this.loanMarketSentimentShareErevRoth = Parameters.loanMarketSentimentShareErevRoth;
		this.betaDistributionAlphaGenericErevRoth = Parameters.betaDistributionAlphaGenericErevRoth;
		this.betaDistributionBetaGenericErevRoth = Parameters.betaDistributionBetaGenericErevRoth;
		this.betaDistributionAlphaMinErevRoth = Parameters.betaDistributionAlphaMinErevRoth;
		this.betaDistributionBetaMaxErevRoth = Parameters.betaDistributionBetaMaxErevRoth;
		this.betaDistributionAlphaMaxErevRoth = Parameters.betaDistributionAlphaMaxErevRoth;
		this.betaDistributionBetaMinErevRoth = Parameters.betaDistributionBetaMinErevRoth;
		this.scalingParameterErevRoth = Parameters.scalingParameterErevRoth;
		this.randomSeedErevRoth = Parameters.randomSeedErevRoth;
		
		MarkoseDYangModel_V01.erevRothModel = params.isErevRothModel();
		
		if(MarkoseDYangModel_V01.erevRothModel == true){
			//set random engine
			this.randomEngine = new MersenneTwister(this.randomSeedErevRoth);
			double minChangeSize = -1*this.securitisationRateMaxChangeErevRoth;
			double changeSize = 0;
			//build set of erev-roth actions
//			for(int i = 0; i < this.numberOfActionsErevRoth; i++){
//				changeSize =  minChangeSize + (i*this.securitisationRateIncrementErevRoth);
//				this.actionsDomainErevRoth.add(i, ErevRothAction.createSingleDoublePropertyERAction(changeSize));
//			}
			for(int i = 0; i < 101; i++){
				changeSize =  0 + (i*0.01);
				this.actionsDomainErevRoth.add(i, ErevRothAction.createSingleDoublePropertyERAction(changeSize));
			}
			this.numberOfActionsErevRoth = this.actionsDomainErevRoth.size();
//			System.out.println("actionsDomainErevRoth size: " + actionsDomainErevRoth.size());
		}
		
		
		
		
		
		MarkoseDYangModel_V01.RLModelParameters = new MDPModelInputParameters(this.decisionAnalysisPeriodEndString, this.portfolioWeightChoiceModel, 
				this.shortSelling,	this.stochasticStateTransitions, this.linearCostFunction, this.opportunityCostOfFixedIncomeInvestment, this.linearfactor, 
				this.numberOfEpisodes,	this.numberOfDecisionEpochs, this.quadraticfactor, this.assetWieghtIncrements, this.changeInWeightIncrement, 
				this.maximumPermissbleChangeInWeight, 
				this.epsilonError, this.gammaDiscountFactor, this.accuracyThreshold, this.RLLearningRateAlpha,	this.RLLearningLambda,	
				this.RLLearningType, this.RLActionSelectionType);
		
		
		
		MarkoseDYangModel_V01.HeuristicDecisionModelParameters = new MDPHeuristicDecisionParameters(
				this.periodByPeriodCalculations, this.numberOfIterationsHeuristic,
				this.numberOfDecisionEpochsHeuristic, this.identicalStateSelectionBias,
				this.securitisationRateIncrement, this.greedParameterIncrement,
				this.genericBias, this.maximumBias, this.minimumBias, this.loanMarketSentimentShare,
				this.hueristicCostConstantLowDemand, this.hueristicCostConstantMidDemand, this.hueristicCostConstantHighDemand,
				this.betaDistributionAlphaGeneric,	this.betaDistributionBetaGeneric, this.betaDistributionAlphaMin,
				this.betaDistributionBetaMin, this.betaDistributionAlphaMax, 
				this.betaDistributionBetaMax, this.recoveryRateOnCreditAsset);

		
		MarkoseDYangModel_V01.ErevRothDecisionModelParameters = new MDPRothErevParameters(this.ErevRothLearnerUpdateType, 
				this.ErevRothProbabilityModel, this.periodByPeriodCalculationsErevRoth,
				this.identicalParameterSelection, 	this.numberOfIterationsErevRoth, this.numberOfDecisionEpochsErevRoth,
				this.numberOfActionsErevRoth,
				this.securitisationRateIncrementErevRoth, this.securitisationRateMaxChangeErevRoth, 
				this.GibbsBoltzmannParameterErevRoth, this.genericExperimentationFactor, 
				this.maximumExperimentationFactor, this.minimumExperimentationFactor, 
				this.genericRecencyFactorErevRoth, 
				this.maxRecencyFactorErevRoth, this.minRecencyFactorErevRoth, 
				this.loanMarketSentimentShareErevRoth,
				this.betaDistributionAlphaGenericErevRoth, this.betaDistributionBetaGenericErevRoth, this.betaDistributionAlphaMinErevRoth,
				this.betaDistributionBetaMinErevRoth, this.betaDistributionAlphaMaxErevRoth, this.betaDistributionBetaMaxErevRoth,
				this.scalingParameterErevRoth, this.randomSeedErevRoth);
		
	}


	public void setPredefinedEquityFundReturns(
			double[] array) {
		// TODO Auto-generated method stub
		//                            System.out.println("equity Rate passed: "+array.length);
		for(int i = 0; i< array.length; i++) {
			this.predefinedEquityFundReturns.add(i, array[i]);
		}
	}




	private void setEnviromentName(String string) {
		// TODO Auto-generated method stub
		this.enviromentName = string;
	}

	private void setLoanIssuanceStartCalender(int simulationStartYr2,
			int dataCallenderStartMonth2, int i) {
		// TODO Auto-generated method stub
		start = new GregorianCalendar(simulationStartYr2, dataCallenderStartMonth2, i);
	}

	private void setDataCallenderStartMonth() {
		this.dataCallenderStartMonth = this.dataCallenderStartMonth =
				UtilityMethods.DefineLoanIssuanceCalenderMonth(this.simulationStartQtr, DataSchedule.quarterly);
	}






	public void buildAgents(){
		/**
		 * This method build the population of agents in the model
		 */
		double bankRA = 0;
		double bankRL = 0;
		Random rnd = new Random();
		int true_false = 1;
		if (!this.userAssetLiabilityDataInput){
			true_false = 0;
		}
		else{
			true_false = 1;
		}
		//            System.out.println("true_false: " + true_false);
		switch (true_false){
		case 0 :
			if(this.bankCount == 35){
				for (int i = 0; i < BankIDRSSD.bankIDRSSD30.length; i++){
					int IDRSSD = BankIDRSSD.bankIDRSSD30[i];
					if(this.interestSpreadExperiment == true){
						bankRA = this.bankAssetReturn;
						bankRL = this.bankLiabilityExpense;
					}else{
						bankRL = this.bankLiabilityExpense;
						bankRA = bankRL + rnd.nextDouble()*(this.bankAssetReturn - this.bankLiabilityExpense) ;
					}
					BankMarkoseDYanBaselI bnk = BankFactory.CreatBanks(bankCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, econ, env, world, bankRA,
							bankRL);
					world.placeMeRandom(bnk);
					world.agentsPopulation.add(bnk);
					this.bankList.add(bnk);
				}
			}else {
				for (int i = 0; i < BankIDRSSD.bankIDRSSD30.length; i++){
					int IDRSSD = BankIDRSSD.bankIDRSSD30[i];

					if(this.interestSpreadExperiment == true){
						bankRA = this.bankAssetReturn;
						bankRL = this.bankLiabilityExpense;
					}else{
						bankRL = this.bankLiabilityExpense;
						bankRA = bankRL + rnd.nextDouble()*(this.bankAssetReturn - this.bankLiabilityExpense) ;
					}
					BankMarkoseDYanBaselI bnk = BankFactory.CreatBanks(bankCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, econ, env, world, bankRA,
							bankRL);
					world.placeMeRandom(bnk);
					world.agentsPopulation.add(bnk);
					this.bankList.add(bnk);
				}
			}
			if(this.investorCount == 1){
				for (int i = 0; i < 1; i++){
					int IDRSSD = InvestorIDS.InvestorIDRSSD2[i];
					CPensionFund lapf = InvestorFactory.CreatePensionFund(investorCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, econ, env, world);
					world.placeMeRandom(lapf);
					world.agentsPopulation.add(lapf);
					this.lapfList.add(lapf);
				}
			}
			if(this.investorCount == 220){
				for (int i = 2; i < InvestorIDS.InvestorIDRSSD2.length; i++){
					int IDRSSD = InvestorIDS.InvestorIDRSSD2[i];
					CPensionFund lapf = InvestorFactory.CreatePensionFund(investorCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, econ, env, world);
					world.placeMeRandom(lapf);
					world.agentsPopulation.add(lapf);
					this.lapfList.add(lapf);
				}
			}
			if(this.investorCount == 10){
				RandomNumberGenerator rndm = new RandomNumberGenerator(220-2);
				int x = 0;
				for (int i = 0; i < 10 ; i++){
					x = 2 + rndm.nextIntegerWithoutReplacement();
					int IDRSSD = InvestorIDS.InvestorIDRSSD2[x];
					CPensionFund lapf = InvestorFactory.CreatePensionFund(investorCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, econ, env, world);
					world.placeMeRandom(lapf);
					world.agentsPopulation.add(lapf);
					this.lapfList.add(lapf);
				}
			}
			break;
		case 1 :
			if(this.bankCount == 35){
				for (int i = 0; i < BankIDRSSD.bankIDRSSD30.length; i++){
					int IDRSSD = BankIDRSSD.bankIDRSSD30[i];
					if(this.interestSpreadExperiment == true){
						bankRA = this.bankAssetReturn;
						bankRL = this.bankLiabilityExpense;
					}else{
						bankRL = this.bankLiabilityExpense;
						bankRA = bankRL + rnd.nextDouble()*(this.bankAssetReturn - this.bankLiabilityExpense) ;
					}
					BankMarkoseDYanBaselI bnk = BankFactory.CreatBanks(bankCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, econ, env, world,
							this.userDefinedBankAssets, this.userDefinedBankLiabilities,
							bankRA, bankRL);
					world.placeMeRandom(bnk);
					world.agentsPopulation.add(bnk);
					this.bankList.add(bnk);
				}
			} else if(this.bankCount == 97){
				for (int i = 0; i < BankIDRSSD.bankIDRSSD30.length; i++){
					int IDRSSD = BankIDRSSD.bankIDRSSD30[i];
					if(this.interestSpreadExperiment == true){
						bankRA = this.bankAssetReturn;
						bankRL = this.bankLiabilityExpense;
					}else{
						bankRL = this.bankLiabilityExpense;
						bankRA = bankRL + rnd.nextDouble()*(this.bankAssetReturn - this.bankLiabilityExpense) ;
					}
					BankMarkoseDYanBaselI bnk = BankFactory.CreatBanks(bankCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, econ, env, world,
							this.userDefinedBankAssets, this.userDefinedBankLiabilities,
							bankRA, bankRL);
					world.placeMeRandom(bnk);
					world.agentsPopulation.add(bnk);
					this.bankList.add(bnk);
				}
			}else {
				for (int i = 0; i < BankIDRSSD.bankIDRSSD30.length; i++){
					int IDRSSD = BankIDRSSD.bankIDRSSD30[i];
					if(this.interestSpreadExperiment == true){
						bankRA = this.bankAssetReturn;
						bankRL = this.bankLiabilityExpense;
					}else{
						bankRL = this.bankLiabilityExpense;
						bankRA = bankRL + rnd.nextDouble()*(this.bankAssetReturn - this.bankLiabilityExpense) ;
					}
					BankMarkoseDYanBaselI bnk = BankFactory.CreatBanks(bankCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, econ, env, world,
							this.userDefinedBankAssets, this.userDefinedBankLiabilities,
							bankRA, bankRL);
					world.placeMeRandom(bnk);
					world.agentsPopulation.add(bnk);
					this.bankList.add(bnk);
				}
			}
			if(this.investorCount == 1){
				for (int i = 0; i < 1; i++){
					int IDRSSD = InvestorIDS.InvestorIDRSSD2[i];
					CPensionFund lapf = InvestorFactory.CreatePensionFund(investorCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, this.userDefinedLAPFAssets, this.userDefinedLAPFLiabilities,
							econ, env, world);
					world.placeMeRandom(lapf);
					world.agentsPopulation.add(lapf);
					this.lapfList.add(lapf);
				}
			}
			if(this.investorCount == 220){
				for (int i = 2; i < InvestorIDS.InvestorIDRSSD2.length; i++){
					int IDRSSD = InvestorIDS.InvestorIDRSSD2[i];
					CPensionFund lapf = InvestorFactory.CreatePensionFund(investorCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, this.userDefinedLAPFAssets, this.userDefinedLAPFLiabilities,
							econ, env, world);
					world.placeMeRandom(lapf);
					world.agentsPopulation.add(lapf);
					this.lapfList.add(lapf);
				}
			}
			if(this.investorCount == 10){
				RandomNumberGenerator rndm = new RandomNumberGenerator(220-2);
				int x = 0;
				for (int i = 0; i < 10 ; i++){
					x = 2 + rndm.nextIntegerWithoutReplacement();
					int IDRSSD = InvestorIDS.InvestorIDRSSD2[x];
					CPensionFund lapf = InvestorFactory.CreatePensionFund(investorCount, this.modelPeriodSting,
							this.modelPeriod, IDRSSD, this.userDefinedLAPFAssets, this.userDefinedLAPFLiabilities,
							econ, env, world);
					world.placeMeRandom(lapf);
					world.agentsPopulation.add(lapf);
					this.lapfList.add(lapf);
				}
			}
			break;
		}
		//if there are any borrowers already created by the banks add these to the context
		for(int i = 0; i < this.bankList.size(); i++){
			for(int j = 0; j < this.bankList.get(i).getBorrowers().size(); j++){
				world.placeMeRandom(this.bankList.get(i).getBorrowers().get(j));
				world.agentsPopulation.add(this.bankList.get(i).getBorrowers().get(j));
			}
		}
		
		createMarkovAgents(econ);
		
		Dealer_MarketMaker dealer = 
				new Dealer_MarketMaker("MBS Market Maker",bankList, lapfList, econ, env, world);
		
		if(MarkoseDYangModel_V01.erevRothModel == true){
			updateBankErevRothDecisionMakingParameters();
		}
		
		updateInvestorDecisionMakingParameters();
		world.placeMeRandom(dealer);
		world.agentsPopulation.add(dealer);
		this.brokerDealMaketMakerList.add(dealer);
		this.addBanksToEconomy(bankList);
		this.addInvestorsToEconomy(lapfList);
		this.addBrokerDealMaketMakersToEconomy(brokerDealMaketMakerList);
		this.addMarkovAgentsToEconomy(MDPMarkovAgentList);
	}


	private void createMarkovAgents(Economy econ) {
		// TODO Auto-generated method stub
		//String agentName, String agentSector, String agentTemprerement, double totalAssets,Economy econ
		String name = null;
		String sector = "Pension Fund";
		String tempr = null;
		double asset = 100;
		for(int i = 1; i < MarkoseDYangModel_V01.InvestorAgentTemprement.length; i++){
			tempr = MarkoseDYangModel_V01.InvestorAgentTemprement[i];
			name = "Pension Fund " + tempr;
			this.MDPMarkovAgentList.add(new MDPInvestorAgent(name, sector, tempr, asset, econ));
		}
	}

	/**
	 * THis method is used to define the finer behavioural details of the investors
	 * i.e. their expectations of market defaults and losses (Bulls vs Bears)
	 * define the numerical factor value of the expected losses to be applied to the reward functions of the MDP
	 */
	private void updateInvestorDecisionMakingParameters() {
		// TODO Auto-generated method stub
		Random rnd = new Random();
		double lngTermDefault = 0;
		String temperament = null;
		double cirTheta_CreditAssetMeanValue = 0;
		if(MarkoseDYangModel_V01.lapfTypeString == "MDP Learning" && (this.fundsExpectationsString != "Rational")){
			if(this.fundsExpectationsString != "Heterogeneous"){
				for(int i = 0; i < this.lapfList.size(); i++){
					((CPensionFund) this.lapfList.get(i)).setAgentDecisionType(MarkoseDYangModel_V01.lapfTypeString);
					
					if(this.fundsExpectationsString == "Bullish"){

						//sets the optimistic default rate to the AAA- default rate
//						lngTermDefault = params.getCitibankResearchMBSDefaultsList().get(1);
						
						
						//The following has been introduced on May 24, 2014 to reduce the excessive impact of changing expectations of 
						//defaults
						double lngTermDefaultBull = params.getCitibankResearchMBSDefaultsList().get(1);
						double bullCreditDefaultsReduced = 0.5*(this.defaultProbabilityBankExpectations - lngTermDefaultBull);
//								(0.5*((1-lngTermDefaultBull) - (1 - this.defaultProbabilityBankExpectations)))
//								/(1 - this.defaultProbabilityBankExpectations);
						lngTermDefault = bullCreditDefaultsReduced;
						
						temperament = MarkoseDYangModel_V01.InvestorAgentTemprement[1];//"Bull";
					} 
					else 
						if(this.fundsExpectationsString == "Bearish"){
						lngTermDefault = params.getCitibankResearchMBSDefaultsList().get(params.getCitibankResearchMBSDefaultsList().size()-1);
						//sets the pessimistic default rate to the CCC- default rate
						temperament = MarkoseDYangModel_V01.InvestorAgentTemprement[2];//"Bear";
						}
						else 
							if(this.fundsExpectationsString == "Passive"){
							lngTermDefault = this.defaultProbabilityBankExpectations;
							//sets the pessimistic default rate to the CCC- default rate
							temperament = MarkoseDYangModel_V01.InvestorAgentTemprement[3];//"Passive";
							}
					cirTheta_CreditAssetMeanValue = ((1-lngTermDefault) - (1 - this.defaultProbabilityBankExpectations))
							/(1 - this.defaultProbabilityBankExpectations);
					this.lapfList.get(i).setCIR_ThetaCreditAssetMeanValueExpectedDefaults(cirTheta_CreditAssetMeanValue);
					this.lapfList.get(i).updatetemperament(temperament);
				}
			} else { 
				double split = (1- this.fundExpectationsBias)/2;
				for(int i = 0; i < this.lapfList.size(); i++){
					//if investor agents are heterogenous....
					//for now heterogeneouity is found in the choice of default expectations (.i.e. cirTheta_CreditAssetMeanValue)
					//This is used in the MDP calculation engine to factor down returns
					if(rnd.nextDouble() > this.fundExpectationsBias){
						lngTermDefault = params.getCitibankResearchMBSDefaultsList().get(1);//sets the optimistic default rate to the AAA- default rate
						temperament = MarkoseDYangModel_V01.InvestorAgentTemprement[1];//"Bull";
						
					} else {
						if(rnd.nextDouble() > split){
							lngTermDefault = params.getCitibankResearchMBSDefaultsList().get(params.getCitibankResearchMBSDefaultsList().size()-1);
							//sets the pessimistic default rate to the CCC- default rate
							temperament = MarkoseDYangModel_V01.InvestorAgentTemprement[2];//"Bear";
						}
						else{
							lngTermDefault = this.defaultProbabilityBankExpectations;
							//sets the pessimistic default rate to the CCC- default rate
							temperament = MarkoseDYangModel_V01.InvestorAgentTemprement[3];//"Passive";
						}
						
					}
					cirTheta_CreditAssetMeanValue = ((1-lngTermDefault) - (1 - this.defaultProbabilityBankExpectations))
							/(1 - this.defaultProbabilityBankExpectations);
					((CPensionFund) this.lapfList.get(i)).setAgentDecisionType(MarkoseDYangModel_V01.lapfTypeString);
					this.lapfList.get(i).setExpectedEquityLoss(this.probabilityOfEquityReturnExpectations);
					this.lapfList.get(i).setExpectedCreditDefault(cirTheta_CreditAssetMeanValue);
					this.lapfList.get(i).updatetemperament(temperament);
				}
			}//end else
		}
		else if(MarkoseDYangModel_V01.lapfTypeString == "MDP Learning" && (this.fundsExpectationsString == "Rational")){
			cirTheta_CreditAssetMeanValue =  ((1-econ.getGenericPostRateResetDafaultRate()) - (1 - this.defaultProbabilityBankExpectations))
					/(1 - this.defaultProbabilityBankExpectations);
			for(int i = 0; i < this.lapfList.size(); i++){
				((CPensionFund) this.lapfList.get(i)).setAgentDecisionType(MarkoseDYangModel_V01.lapfTypeString);
				temperament = MarkoseDYangModel_V01.InvestorAgentTemprement[MarkoseDYangModel_V01.InvestorAgentTemprement.length-1];//"Rational";
				this.lapfList.get(i).setExpectedEquityLoss(this.probabilityOfEquityReturnExpectations);
				this.lapfList.get(i).setExpectedCreditDefault(cirTheta_CreditAssetMeanValue);
				this.lapfList.get(i).updatetemperament(temperament);
				
			}//end for-loop
			
		}//end else-if
		else{//sets agent decision type to "Simple"
			for(int i = 0; i < this.lapfList.size(); i++){
				((CPensionFund) this.lapfList.get(i)).setAgentDecisionType(MarkoseDYangModel_V01.lapfTypeString);
				this.lapfList.get(i).updatetemperament(MarkoseDYangModel_V01.InvestorAgentTemprement[0]);//("Simple");
			}
		}
		
		
	}//end method
	

	/**
	 * THis method defines the Erev-Roth model for the individual banks
	 * if the model uses indentical models then each bank shares a generic ErevRothSecuritisationProblem
	 * otherwise a new one is created for each of the banks
	 */
	private void updateBankErevRothDecisionMakingParameters(){
		String erUpdateType = this.ErevRothLearnerUpdateType;
		String ProbModel = this.ErevRothProbabilityModel;
		int nActions = this.numberOfActionsErevRoth;
		int nIterations = this.numberOfIterationsErevRoth;
		int nDEpochs = this.numberOfDecisionEpochsErevRoth;
		double scalParameter = this.scalingParameterErevRoth;
		double expFactor = this.genericExperimentationFactor;
		double recFactor = this.genericRecencyFactorErevRoth;
		double aStartingReward = this.averageStartingRewardErevRoth;
		double alphaBDistrib = this.betaDistributionAlphaGenericErevRoth;
		double betaBDistrib = this.betaDistributionBetaGenericErevRoth;
		double GBParameter = this.GibbsBoltzmannParameterErevRoth;
		ErevRothSecuritisationProblem erevRothSecuritisationModel;
		double secRate;
		double assets; 
		double liabilities;
		double capRatio;
		double contRate;
		double depRate;
		double recRate = this.recoveryRateOnCreditAsset;
		double suvRate;
		double suvRatePR = 1-this.genericPostRateResetDafaultRate;
		
		if(this.identicalParameterSelection == true){
			//create shared Erev-Roth Problem
			//ErevRothSecuritisationProblem(String updateType, String probModel, int A, int T, double gbp, double scal, 
			//                              double ef, double rf, double initProp, double alpha, double beta, 
			//								RandomEngine randomEngine)
			//
			
			erevRothSecuritisationModel = new ErevRothSecuritisationProblem(
					erUpdateType, 	ProbModel, nActions, nIterations, nDEpochs, GBParameter, scalParameter, expFactor, recFactor, 
					aStartingReward, alphaBDistrib, betaBDistrib, this.randomEngine);
			secRate = 0.25;
			erevRothSecuritisationModel.setInitialSecuritisationRate(secRate);
			for(int i = 0; i < this.bankList.size(); i++){
				this.bankList.get(i).setMDPRothErevParameters(MarkoseDYangModel_V01.ErevRothDecisionModelParameters);
				this.bankList.get(i).setActionsDomainErevRoth(this.actionsDomainErevRoth);
				assets = this.bankList.get(i).getTier_1_Capital(); 
				liabilities = this.bankList.get(i).getTotalLiabilities();
				capRatio = this.bankList.get(i).getRegulatoryCapitalRatio();
				contRate = this.bankList.get(i).getReturnOnAssets();
				depRate = this.bankList.get(i).getReturnOnLiabilities();
				suvRate = this.bankList.get(i).getAssetSurvivalRate();
				erevRothSecuritisationModel.updateLearningEnvironmentData(assets, liabilities, 0, capRatio, 
						contRate, depRate, recRate, suvRate, suvRatePR);
				this.bankList.get(i).setErevRothSecuritisationModel(erevRothSecuritisationModel);
				this.bankList.get(i).setSecuritisationRate(secRate);
				this.bankList.get(i).initializeErethRothSecuritisationModel();
			}
		}
		else {
			for(int i = 0; i < this.bankList.size(); i++){
				this.bankList.get(i).setMDPRothErevParameters(MarkoseDYangModel_V01.ErevRothDecisionModelParameters);
				this.bankList.get(i).setActionsDomainErevRoth(this.actionsDomainErevRoth);
				alphaBDistrib = (this.betaDistributionAlphaMinErevRoth) 
					+ (Math.random()* ( this.betaDistributionAlphaMaxErevRoth - this.betaDistributionAlphaMinErevRoth));
				betaBDistrib = (this.betaDistributionBetaMinErevRoth) 
					+ (Math.random()* ( this.betaDistributionBetaMaxErevRoth - this.betaDistributionBetaMinErevRoth));
				expFactor = (this.minimumExperimentationFactor) 
					+ (Math.random()* ( this.maximumExperimentationFactor - this.minimumExperimentationFactor));
				recFactor = (this.minRecencyFactorErevRoth) 
					+ (Math.random()* ( this.maxRecencyFactorErevRoth - this.minRecencyFactorErevRoth));
				
				erevRothSecuritisationModel = new ErevRothSecuritisationProblem(
						erUpdateType, 	ProbModel, nActions, nIterations, nDEpochs, GBParameter, scalParameter, expFactor, recFactor, 
						aStartingReward, alphaBDistrib, betaBDistrib, this.randomEngine);
				secRate = Rounding.roundTwoDecimals(0.25 + Math.random()* (1 - 0.25));
				assets = this.bankList.get(i).getTier_1_Capital(); 
				liabilities = this.bankList.get(i).getTotalLiabilities();
				capRatio = this.bankList.get(i).getRegulatoryCapitalRatio();
				contRate = this.bankList.get(i).getReturnOnAssets();
				depRate = this.bankList.get(i).getReturnOnLiabilities();
				suvRate = this.bankList.get(i).getAssetSurvivalRate();
				erevRothSecuritisationModel.updateLearningEnvironmentData(assets, liabilities, 0, capRatio, 
						contRate, depRate, recRate, suvRate, suvRatePR);
				this.bankList.get(i).setErevRothSecuritisationModel(erevRothSecuritisationModel);
				this.bankList.get(i).setSecuritisationRate(secRate);
				this.bankList.get(i).initializeErethRothSecuritisationModel();
			}
		}
	}
	
	
	public void buildActions(){
		eventList.scheduleSimple(0, 99999999, this, "time0");
		eventList.scheduleCollection(0, 1, this.world.agentsPopulation, getObjectClass("BankMarkoseDYanBaselI"),"getColor");
		if(MarkoseDYangModel_V01.lapfTypeString == "Simple Fund"){
			eventList.scheduleSimple(1, 1, this.econ,"setGlobalEquityRates");
			if(MarkoseDYangModel_V01.erevRothModel == false){
				eventList.scheduleCollection(1, 1, bankList, getObjectClass("BankMarkoseDYanBaselI"),"simAssetProcess");
			} else{
				eventList.scheduleCollection(1, 1, bankList, getObjectClass("BankMarkoseDYanBaselI"),"getNextSecuritisationRate");
				
			}
			eventList.scheduleCollection(1, 1, brokerDealMaketMakerList, getObjectClass("Dealer_MarketMaker"),"setGlobalReturnOnCredit");
			eventList.scheduleSimple(1, 1, this.econ,"recordFailedBanks");
			eventList.scheduleSimple(1, 1, this.econ,"setGlobalMBSRates");
			eventList.scheduleCollection(1, 1, lapfList, getObjectClass("CPensionFund"),"simSurplusProcess");
		} else if(MarkoseDYangModel_V01.lapfTypeString == "MDP Learning"){
			if(MarkoseDYangModel_V01.erevRothModel == false){
				eventList.scheduleCollection(1, 1, bankList, getObjectClass("BankMarkoseDYanBaselI"),"simAssetProcess");
			}
			else{
				eventList.scheduleCollection(1, 1, bankList, getObjectClass("BankMarkoseDYanBaselI"),"getNextSecuritisationRate");
			}
			if(Sim.getAbsoluteTime() == 0) {
				eventList.scheduleSimple(0, 1, this.econ,"setGlobalEquityRates");
				eventList.scheduleSimple(0, 1, this.econ,"publishMacroeconomicEnvironment");
				eventList.scheduleCollection(0, 1, MDPMarkovAgentList, getObjectClass("MDPInvestorAgent"),"makeMDPPortfolioDecision");
				eventList.scheduleCollection(0, 1, lapfList, getObjectClass("CPensionFund"),"makePortfolioDecision");
			} else{
				if(((int) Sim.getAbsoluteTime()) % 5 == 0) {
					eventList.scheduleCollection(1, 1, MDPMarkovAgentList, getObjectClass("MDPInvestorAgent"),"makeMDPPortfolioDecision");
				}
				eventList.scheduleCollection(1, 1, lapfList, getObjectClass("CPensionFund"),"makePortfolioDecision");
				eventList.scheduleSimple(1, 1, this.econ,"setGlobalEquityRates");
				eventList.scheduleSimple(1, 1, this.econ,"publishMacroeconomicEnvironment");
				eventList.scheduleSimple(1, 1, this.econ,"computeCurrentCreditMarketSecuritiesDemand");
			}
			eventList.scheduleSimple(1, 1, this.econ,"recordFailedBanks");
			eventList.scheduleCollection(1, 1, brokerDealMaketMakerList, getObjectClass("Dealer_MarketMaker"),"setGlobalReturnOnCredit");
			eventList.scheduleSimple(1, 1, this.econ,"setGlobalMBSRates");
			eventList.scheduleCollection(1, 1, lapfList, getObjectClass("CPensionFund"),"simSurplusProcess");
			eventList.scheduleSimple(1, 1, this.econ,"computeCurrentCreditMarketSecuritiesDemand");
			if(MarkoseDYangModel_V01.erevRothModel == true){
				eventList.scheduleCollection(1, 1, bankList, getObjectClass("BankMarkoseDYanBaselI"),"updateErevRothSecuritisationModel");
				eventList.scheduleCollection(1, 1, bankList, getObjectClass("BankMarkoseDYanBaselI"),"simCapitalProcess");
			}
		}
		
		 if (this.frame != null){
			 eventList.scheduleSimple(0, 1, this.frame, "showUpdatedModelState");
		 }
		eventList.scheduleSystem(this.maxRun, Sim.EVENT_SIMULATION_END);
	}

	

	public void time0(){
		

	}
	

	@Override
	public void buildModel() {
		// TODO Auto-generated method stub

		world = new GeoEconoPoliticalSpace(175,175,365,1);
		Parameters params = this.frame.getParameters();
		setModelDefaultParameters(params);
		buildEconomy();
		buildEnviroment();
		buildAgents();
		buildActions();
	}



	public void buildModel(Parameters params) {
		// TODO Auto-generated method stub

		world = new GeoEconoPoliticalSpace(175,175,365,1);
		setModelDefaultParameters(params);
		buildEconomy();
		buildEnviroment();
		buildAgents();
		buildActions();
	}
	
	public GeoEconoPoliticalSpace getWorld(){
		return this.world;
	}




	@Override
	public void setParameters(){
		//                            ParameterFrame parameterFrame = new ParameterFrame(this, "Credit Risk Transfer Model", path + "parameters.xml");
		//                            addSimWindow(parameterFrame);
		Sim.openProbe(this.params, "Parameters");
	}



	public void setNodeColour (EconomicAgent node)
	{
		if (Sim.getAbsoluteTime() == 0)	{
		}
	}






	/**
	 * this models Main method
	 */
	public static void main(String[] args) {
		SimEngine eng=new SimEngine();
		JAS jas=new JAS(eng);
		jas.setVisible(true);

		MarkoseDYangModel_V01 m=new MarkoseDYangModel_V01();
		eng.addModel(m);
		m.setParameters();



	}







}