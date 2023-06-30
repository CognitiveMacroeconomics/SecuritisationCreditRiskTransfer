

import jas.engine.Sim;

import java.util.ArrayList;

import cern.jet.random.engine.RandomEngine;



public class Economy {
	
	
	private static int ECONOMY_ID = 0;
	
	private int economyID;
	
	private String EntityID;

	long time;
	boolean stochasticMDP;
	/*all variables are declared static because all instances of the economy class 
	 * should have the same values for all these variables. Moreover, the changes made 
	 * to the global economy instance created by the model at initialization must
	 * be reflected in all other instances of the economy as used by the agents  
	 */
	
	//variables required to run the simAssetProcess method of the banks
	
	

	public static double regCapitalRatio; 
	public static double survivalRate;
	public static double AAA_Rated_MBS_Coupon; 
	public static double nonAAA_Rated_MBS_Coupon;
	public static double AAA_Rated_MBS_Probability;
	public static double assetReturn;
	public static double liabExpense;
	
	public double globalSpecifiedSecuritisationate;
	private static double lapfPeriodicLiabilityPaymentRate;
	private static double lapfPremiumContributionsRate;
	
	public double outstandingIssuance = 0;
	public double outstandingIssuanceByDefaultedBanks = 0;
	/*
	 * public void issueLoans(
	 * BorrowerType bType, (Economy) 
	 * int loansPerIssuance, (Economy)
	 * double avgIncomeMin, (Economy) 
	 * double avgIncomeMax, (Economy)
	 * Calendar start, (Environment) 
	 * int resetWindow, (Economy)
	 * double interestRateCurrent, (Economy)
	 * double ltvMin, (Economy)
	 * double ltvMax, (Economy)
	 * double probOfSubPrime, (Economy)
	 * int loanMatMin, (Economy)
	 * int loanMatMax, (Economy)
	 * double loanMatMinProb, (Economy)
	 * double loanBorrowerHousingCostBurdenProb, (Economy)
	 * RateType rateType, (Economy)
	 * PaymentSchedule paymentSchedule (Economy)
	 * ) 
	 * {
	 */



	//Loan and Borrower Creation Variables

	public static RateType rateType;
	public static PaymentSchedule paymentSchedule;
	public static BorrowerType bType;
	public static double assetValueMin;
	public static double assetValueMax;
	public static double aveIncomeMin;
	public static double aveIncomeMax;
	public static int resetWindow;
	public static double currentRiskFreeRate;
	public static double mortgageLTVRatioMin;
	public static double mortgageLTVRatioMax;
	public static int mortgageMaturityMax;
	public static int mortgageMaturityMin;
	public static int loansPerMBSIssuance;

	/**this value at the bank loan issuance level defines the probability bias for 
	 * setting the loan to value ratio and average income of the borrower
	 * 
	 */
	public static double probabilityOfSubPrime; 

	/**this value at the bank loan issuance level defines the probability bias for 
	 * determining whether the loan is issued to a borrower who is severely burdened by housing cost
	 * this in turn will determine the probability that the borrower will default if the value of the 
	 * house/asset underlying the mortgage falls below the market value of the mortgage
	 * 
	 */
	public static double probabilityOfHouseCostSevereBurden; 

	/**this value at the bank loan issuance level defines the probability bias for 
	 * setting the loan maturity
	 * 
	 */
	public static double mortgageMaturityMinProbability;


	//Life, Insurance and Pension Fund required Variables

	private static boolean lapfQuadraticCostFunction;
	public static double opportunityCostOfFixedIncomeInvestment;
	public static double lapfRegulatorySolvancyRatio;
	public static double returnOnGlobalEquity;
	private String traditionalAssetVariationTypeString;
	private ArrayList <Double> predefinedEquityFundReturns = new ArrayList <Double>();
	private ArrayList<CInvestor> InvestorsList;
	private ArrayList<Dealer_MarketMaker> brokerDealMaketMakerList;
	private ArrayList<Bank> bankList;
	private ArrayList<Bank> failedBankList = new ArrayList<Bank>();
	private boolean predefinedEquityFundReturnsBoolean = false;
	private double traditionalAssetsSurvivalRate;
	private double recoveryRateOnCreditAsset;
	private double defaultRecoveryRateOnCreditAsset;
	private double recoveryRateOnTraditionalAsset;
	public static double returnOnGlobalCredit = 0.0;
	public static double benchmarkMarketIndexLevel;
	public static double benchmarkMBSIndexLevel;
	public static double benchmarkMarketIndexReturn;
	public static double benchmarkMBSIndexReturn;
	public int globalEquityReturnChangeDirection;
	public int globalCreditReturnChangeDirection;
	public static double rationalCreditDefaults;
	public static double passiveCreditDefaults;
	public static double equityDefaults;
	public static double bullCreditDefaults;
	public static double bearCreditDefaults;
	public boolean resetPeriodPassed = false;


	/**
	 * this is defined at fund valuation period by actuaries on an annual or quarterly basis
	 * It is nevertheless predifined
	 */
	public static double annualExpectedLAPFPayout;
	public static double annualExpectLAPFPayoutRebalancingRate;
	private static int simTimeCout = 0;

	private ArrayList<Double> MBSCouponsList = new ArrayList<Double>();
	private ArrayList<Double> MBSDefaultsList = new ArrayList<Double>();
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
	private static int genericLoanResetPeriod;
	private static double genericStartingDafaultRate;
	private static double genericPostRateResetDafaultRate;
	private double fullyIndexedContractRateSpread;
	private double securitisationCostConstantFactor;
	public static double genericStartingCoupon;
	public static double genericPostRateResetCoupon;
	
	private StochasticProcessParameters stochParams;
	private StochaststicPathGenModel stochPathGenModel;
	public static double fundMDPLinearFactor;
	public static double fundMDPQuadraticFactor;
	public ArrayList<ValueFunction> valueFunctions;
	
	public MarkoseDYangModel_V01 simModel;
	
	public MDPModelInputParameters mdpModelInputParameters;
	public QLearningConfiguration RLLearningConfiguration;
	public TransitionProbabilitiesEngine transitionProbabilityEngine;
	MDPStatesPathEngine statesPathEngine;
	public PortfolioAssetsState currentState;
	ArrayList<PortfolioAssetsState> statePath;
	ArrayList<MDPMarkovAgent> MDPMarkovAgentList;
	public static ArrayList<MDPPortfolioChoiceAction> actionsSpace; //collection of all actions available to chose from 
	
	public static ArrayList<MDPCapitalMarketsState> MDPStates;//collection of PortfolioAssetsState objects 

	public String[] InvestorAgentTemprement = null;

	private MDPHeuristicDecisionParameters heuristicDecisionModelParameters;

	private double[] mbsAllocationDataArrayUpdate;

	private double aggregateFundMBSAllocationUpdate;
	
	public MDPRothErevParameters ErevRothDecisionModelParameters;
	
	public  RandomEngine randomEngine;
	
	public boolean heuristicSecuritisationModel;
	public boolean erevRothModel;


	
	
	//no arg constructor
	public Economy(){
		ECONOMY_ID++;
		this.economyID = ECONOMY_ID;
		this.setEntityID();
		statePath = new ArrayList<PortfolioAssetsState>();
		statesPathEngine = new MDPStatesPathEngine();
		MDPMarkovAgentList = new ArrayList<MDPMarkovAgent>();
		aggregateFundMBSAllocationUpdate = 0.4;
	}
	
	
	
	//no arg constructor
	public Economy(MarkoseDYangModel_V01 model){
		ECONOMY_ID++;
		this.economyID = ECONOMY_ID;
		this.setEntityID();
		simModel = model;
		statePath = new ArrayList<PortfolioAssetsState>();
		statesPathEngine = new MDPStatesPathEngine();
		MDPMarkovAgentList = new ArrayList<MDPMarkovAgent>();
		setInvestorAgentTemprement(MarkoseDYangModel_V01.InvestorAgentTemprement);
		aggregateFundMBSAllocationUpdate = 0.4;
	}
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Process Methods>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	//	@ScheduledMethod(start = 1, interval = 1, priority = 6)
	public void setGlobalEquityRates(){
		/**
		 * This method is used to set the global return on equity
		 * steps:
		 * 1: get the constant return on equity/stocks
		 * 2: check if there are values in the predefined equity fund returns data set
		 * 3: define a variable equity data limit that acts a a recurring rate if there
		 * 	  there are no more values in the predefined data set to select from
		 * 4: if the simulation time count is less than the size of the predefined data
		 * 	  set then set the equity/stock return rate to the value at the index of the 
		 * 	  current time
		 * 5: if the limit is exceeded then select the last value in the list and set that
		 * 	  as the equity market return
		 */
		double oER = this.getReturnOnGlobalEquity();
		double eR = 0;
		double newR = 0;
		int equityDataLimit = this.predefinedEquityFundReturns.size();
		boolean stochasticMDP = MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions();
		Economy.simTimeCout = (int) Sim.getAbsoluteTime();
		//predefinedEquityFundReturnsBoolean
		if(stochasticMDP == false){
			if(this.predefinedEquityFundReturnsBoolean == true){
				if(this.predefinedEquityFundReturns.size() > 0){
					if((Economy.simTimeCout > 0) && Economy.simTimeCout <= this.predefinedEquityFundReturns.size()){
						eR = this.predefinedEquityFundReturns.get(Economy.simTimeCout-1);
//						System.out.println("new return on equity: "+ eR);
						newR = eR;
//						setSimTime();
					}else
						if(Economy.simTimeCout > this.predefinedEquityFundReturns.size()){
							int Min = 0;
							int Max = equityDataLimit;
							int randomIndex = Min + (int)(Math.random() * ((Max - Min)));
							eR = this.predefinedEquityFundReturns.get(randomIndex);
							newR = eR;
//						eR = this.predefinedEquityFundReturns.get(equityDataLimit-1); //changed March-17-2014
					}
				}
			}
			else
				if(this.traditionalAssetVariationTypeString == "transition_model"){
					if(Economy.simTimeCout > 0){
						eR = this.currentState.getEquityAssetExpectedReturn();
						if(eR == 0){
							newR = oER;
						}else{
							newR = eR;
						}
					}
				}//end non stochastic equity return check
			else
				if(this.traditionalAssetVariationTypeString == "stochastic"){
					this.generateStochasticAssetPaths();
					eR = Economy.benchmarkMarketIndexReturn;
					newR = eR;
				}
		}//end false stochasticMDP check 
		else {
			this.generateStochasticAssetPaths();
			setSimTime();
			if(this.traditionalAssetVariationTypeString == "stochastic"){
					eR = Economy.benchmarkMarketIndexReturn;
					newR = eR;
			}
			else
				if(this.predefinedEquityFundReturnsBoolean == true){
					if((Economy.simTimeCout > 0) && Economy.simTimeCout <= this.predefinedEquityFundReturns.size()){
							eR = this.predefinedEquityFundReturns.get(Economy.simTimeCout-1);
							newR = eR;
					}else
						if(Economy.simTimeCout > this.predefinedEquityFundReturns.size()){
							int Min = 0;
							int Max = equityDataLimit;
							int randomIndex = Min + (int)(Math.random() * ((Max - Min)));
							eR = this.predefinedEquityFundReturns.get(randomIndex);
							newR = eR;
//							eR = this.predefinedEquityFundReturns.get(equityDataLimit-1); //changed March-17-2014
						}
						
					}
				else
					if(this.traditionalAssetVariationTypeString == "transition_model"){
						if(Economy.simTimeCout > 0){
							eR = Economy.benchmarkMarketIndexReturn;
							if(eR == 0){
								newR = oER;
							}else{
								newR = eR;
							}
						}
					}
			}
		this.setReturnOnGlobalEquity(newR);
		this.setRecoveryRateOnCreditAsset(Economy.benchmarkMBSIndexReturn);
		
		//set direction of the equity return to determine the economic state
		if((newR - oER) > 0){
			this.globalEquityReturnChangeDirection = 1;
		}
		else 
			if((newR - oER) < 0){
				this.globalEquityReturnChangeDirection = -1;
		}
			else{
				this.globalEquityReturnChangeDirection = 0;
			}
	}
	
//	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public void setGlobalMBSRates(){
		double oldAverageFixedIncomeReturn = this.getGlobalReturnOnCredit();
		double averageFixedIncomeReturn = 0.0;
		double[] marketMakerDealerFIRates = new double[this.brokerDealMaketMakerList.size()];
		boolean stochasticMDP = MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions();
		boolean stochasticReturns = MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions();
		if(traditionalAssetVariationTypeString == "stochastic"){
			stochasticReturns = true;
		}
		else {
			stochasticReturns = false;
		}
		
//		if(!stochasticMDP && stochasticReturns){
//			averageFixedIncomeReturn = Economy.benchmarkMBSIndexReturn;
//		}
//		else 
//			if(stochasticMDP && stochasticReturns){
//			averageFixedIncomeReturn = this.currentState.getCreditAssetExpectedReturn();
//		}
//		else{
		for (int i = 0; i< this.brokerDealMaketMakerList.size(); i++){
			marketMakerDealerFIRates[i] = this.brokerDealMaketMakerList.get(i).getMarketMakerMBSRate();
			
//			System.out.println("Mortgage Rate: "+this.supplySideList.get(i).getReturnOnAssets());
		}
		
		averageFixedIncomeReturn += Means.geometricMean(marketMakerDealerFIRates);
//		}
		
		//set direction of the equity return to determine the economic state
				if((averageFixedIncomeReturn - oldAverageFixedIncomeReturn) > 0){
					this.globalCreditReturnChangeDirection = 1;
				}
				else 
					if((averageFixedIncomeReturn - oldAverageFixedIncomeReturn) < 0){
						this.globalCreditReturnChangeDirection = -1;
				}
					else{
						this.globalCreditReturnChangeDirection = 0;
					}
		Economy.setGlobalReturnOnCredit(averageFixedIncomeReturn);
//		System.out.println("MBS return: "+this.getGlobalReturnOnCredit());
	}
	
	public void updateCurrentState(){
		boolean stochasticMDP = MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions();
		ArrayList<PortfolioAssetsState> portfolioStates = this.RLLearningConfiguration.portfolioStates;
		PortfolioAssetsState state; 
		boolean validNextState = false;
		if(stochasticMDP == false){
			while(validNextState == false){
				state = MDPStatesPathEngine.generateNextState(this.currentState);
				if(state.getProperties()[1] == this.globalEquityReturnChangeDirection){
					this.globalCreditReturnChangeDirection = state.getProperties()[2];
					validNextState = true;
				}
			}
			
		}
		
		for(int i = 0; i < portfolioStates.size(); i++){
			if(portfolioStates.get(i).getProperties()[1] == this.globalEquityReturnChangeDirection 
					&& portfolioStates.get(i).getProperties()[2] == this.globalCreditReturnChangeDirection){
				portfolioStates.get(i).equityAssetExpectedReturn = this.getReturnOnGlobalEquity();
				portfolioStates.get(i).creditAssetExpectedReturn = this.getGlobalReturnOnCredit();
				this.currentState = portfolioStates.get(i);
				break;
			}
		}
	}
	
	
	/**
	 * This method is used to create and in the case of a stochastic MDP, update the base QLearningConfiguration/learning environment 
	 * to be used by all agents when making their investment decisions
	 */
	public void publishMacroeconomicEnvironment(){
		time = Sim.getAbsoluteTime();
		stochasticMDP = MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions();
		if(MarkoseDYangModel_V01.lapfTypeString == "MDP Learning"){
			if(MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions() == true){
				if(Sim.getAbsoluteTime() == 0){
					this.defineTransitionModel();
					this.RLLearningConfiguration = new QLearningConfiguration(this.transitionProbabilityEngine, this.mdpModelInputParameters,
							Economy.resetWindow, Economy.rationalCreditDefaults, Economy.equityDefaults, Economy.bullCreditDefaults, Economy.bearCreditDefaults, 
							Economy.passiveCreditDefaults, Economy.opportunityCostOfFixedIncomeInvestment);
					ArrayList<PortfolioAssetsState> portfolioStates = this.RLLearningConfiguration.portfolioStates;
					this.currentState = MDPStatesPathEngine.setInitialState(portfolioStates);
					this.globalEquityReturnChangeDirection = this.currentState.getProperties()[1];
					this.globalCreditReturnChangeDirection = this.currentState.getProperties()[2];
					this.statePath.add(this.currentState);
					Economy.MDPStates = this.RLLearningConfiguration.MDPStates;
					Economy.actionsSpace = this.RLLearningConfiguration.actionsSpace;
				}//ends simulation model time check for creation of base QLearningConfiguration 
				else{
					if(Sim.getAbsoluteTime() % MarkoseDYangModel_V01.RLModelParameters.getNumberOfDecisionEpochs() == 0){
						this.defineTransitionModel();
						this.RLLearningConfiguration.updateTransitions(this.transitionProbabilityEngine.getSTOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX());
						this.RLLearningConfiguration.updateIndividualAssetStateReturns(this.transitionProbabilityEngine.getAssetTraditionalAverageReturns(),
						this.transitionProbabilityEngine.getAssetCreditAverageReturns());
					}

					this.RLLearningConfiguration.updateMDPStates((int) Sim.getAbsoluteTime());
					setNextState();
					this.statePath.add(this.currentState);
					Economy.MDPStates = this.RLLearningConfiguration.MDPStates;
					Economy.actionsSpace = this.RLLearningConfiguration.actionsSpace;
				}//end stochastic MDP else
			}//ends stochastic MDP check
			else
				if(!MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions()){
					if(Sim.getAbsoluteTime() == 0){
						this.RLLearningConfiguration = new QLearningConfiguration(null, this.mdpModelInputParameters,
								Economy.resetWindow, Economy.rationalCreditDefaults, Economy.equityDefaults, 
								Economy.bullCreditDefaults, Economy.bearCreditDefaults, Economy.passiveCreditDefaults,
								Economy.opportunityCostOfFixedIncomeInvestment);
						ArrayList<PortfolioAssetsState> portfolioStates = this.RLLearningConfiguration.portfolioStates;
						this.currentState = MDPStatesPathEngine.setInitialState(portfolioStates);
						this.statePath.add(this.currentState);
						Economy.MDPStates = this.RLLearningConfiguration.MDPStates;
						Economy.actionsSpace = this.RLLearningConfiguration.actionsSpace;
				}//ends simulation model time check for creation of base QLearningConfiguration
					else{	
					this.RLLearningConfiguration.updateMDPStates((int) Sim.getAbsoluteTime());
//					System.out.println("MDP States Updated @ " + Sim.getAbsoluteTime());
					setNextState();
//					System.out.println("Current Economic State Updated @ " + Sim.getAbsoluteTime());
					
					this.statePath.add(this.currentState);
					
					Economy.MDPStates = this.RLLearningConfiguration.MDPStates;
					Economy.actionsSpace = this.RLLearningConfiguration.actionsSpace;
					}
			}
		}
			
	}
	
	private void setNextState(){
		this.currentState = MDPStatesPathEngine.generateNextState(this.currentState);
		this.globalEquityReturnChangeDirection = this.currentState.getProperties()[1];
		this.globalCreditReturnChangeDirection = this.currentState.getProperties()[2];
		
	}
	
	
	
	public void setSimTime(){
		simTimeCout++;
	}
	
	
//	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public void recordFailedBanks(){
		if(this.failedBankList.size()< this.bankList.size()){
			for (int i = 0; i< this.bankList.size(); i++){
				this.outstandingIssuance += this.bankList.get(i).getTotalMBSIssuance();
				if(this.bankList.get(i).getStatus() == CorporateStatus.failed){
					this.failedBankList.add(this.bankList.get(i));
					this.outstandingIssuanceByDefaultedBanks += this.bankList.get(i).getTotalMBSIssuance();
				}
			}
		}
	}
	
	public ArrayList<Bank> getFailedBankList(){
		return this.failedBankList;
	}
	
	public int getFailedBanksCount(){
		int count = 0;
		for (int i = 0; i< this.bankList.size(); i++){
			if(this.bankList.get(i).getStatus() == CorporateStatus.failed){
				count++;
			}
		}
		return count;
	}
	


	public double getOutstandingMBSIssuance(){
		return this.outstandingIssuance;
	}
	
	
	public double getOutstandingIssuanceByDefaultedBanks(){
		return this.outstandingIssuanceByDefaultedBanks;
	}
	
	
	public void setPredefinedEquityFundReturnsBoolean(String traditionalAssetVariationTypeString){
		if(traditionalAssetVariationTypeString != "constant" && traditionalAssetVariationTypeString != "stochastic" 
				&& traditionalAssetVariationTypeString != "transition_model"){
			this.predefinedEquityFundReturnsBoolean = true;
		} else{
			this.predefinedEquityFundReturnsBoolean = false;
		}
	}
	
	public boolean getPredefinedEquityFundReturnsBoolean(){
		return this.predefinedEquityFundReturnsBoolean;
	}
	
	
	
	public void generateStochasticAssetPaths(){
		//Note in the agent class this method uses the for-loop to generate the paths for the decision horizon
		//it is from these paths that the transition probabilities are defined. The method will be modified slightly to 
		//allow the changing of the credit asset reset default rate. This change will actually occur in the generator model
		//for(int i = 0; i < this.stochParams.getNumberOfIterations(); i++){ 
		
		double mbsLvlt0 = Economy.benchmarkMBSIndexLevel;	
		double mktLvlt0 = Economy.benchmarkMarketIndexLevel;
		if(Economy.simTimeCout >= (Economy.genericLoanResetPeriod + 1)){
			double adj =  (((1-Economy.genericPostRateResetDafaultRate)
							-Economy.genericStartingDafaultRate)/Economy.genericStartingDafaultRate);
			
			boolean makeAdj = true;
			this.stochPathGenModel.makeValueAdjustment(makeAdj, adj);
		} 
		this.stochPathGenModel.generatePaths();
		
		setBenchmarkMarketIndexLevel(this.stochPathGenModel.getInitialAssetvalueTraditionalAsset());
		setBenchmarkMBSIndexLevel(this.stochPathGenModel.getInitialAssetvalueCreditAsset());
		setBenchmarkMarketIndexReturn(mktLvlt0,Economy.benchmarkMarketIndexLevel);
		setBenchmarkMBSIndexReturn(mbsLvlt0,Economy.benchmarkMBSIndexLevel);
	}

	
	private void defineTransitionModel(){
		
		for(int i = 1; i<= MarkoseDYangModel_V01.RLModelParameters.getNumberOfDecisionEpochs(); i++){
			if(i >= (Economy.genericLoanResetPeriod + 1) && resetPeriodPassed == false){
				double adj =  (((1-Economy.genericPostRateResetDafaultRate)
								-Economy.genericStartingDafaultRate)/Economy.genericStartingDafaultRate);
				
				boolean makeAdj = true;
				this.stochPathGenModel.makeValueAdjustment(makeAdj, adj);
			}
			this.stochPathGenModel.generatePaths();
			if(i % MarkoseDYangModel_V01.RLModelParameters.getNumberOfDecisionEpochs() == 0 && Economy.simTimeCout != 0){
				this.stochPathGenModel.setTransitionProbability();
				updateMacroeconomicTransitionEngine(this.stochPathGenModel.getTransitionProbabilitiesEngine());
			}
		}

	}
	
	
	public void updateMacroeconomicTransitionEngine(TransitionProbabilitiesEngine tpEngine){
		this.transitionProbabilityEngine = tpEngine;
//		System.out.println("The Current Transition Probability Engine is: " + this.transitionProbabilityEngine.toString());
	}
	
	
	public void simInvestorMDPProcess(){
		for(int i = 0; i < this.InvestorsList.size(); i++){
//			System.out.println(this.InvestorsList.size());
			((CPensionFund) this.InvestorsList.get(i)).determineOptimalInvestmentPolicy();
		}
		for(int i = 0; i < this.InvestorsList.size(); i++){
//			System.out.println(((CPensionFund) this.InvestorsList.get(i)).getOptimalCreditAssetWieght());
			
		}
	}
	
	
	
	public void setMDPModelParameters(MDPModelInputParameters RLParams){
		this.mdpModelInputParameters = RLParams;
	}
	
	

	public void computeCurrentCreditMarketSecuritiesDemand(){
		
		/**
		 * Now Collate data for the funds in the model
		 * 
		 */
		this.mbsAllocationDataArrayUpdate = new double[this.InvestorsList.size()];
		for (int i = 0; i < this.InvestorsList.size(); i++) {
			this.mbsAllocationDataArrayUpdate[i] = this.InvestorsList.get(i)
					.getOptimalCreditAssetWieght();
			}
		this.setAggregateFundMBSAllocationUpdate(Means
				.arithmeticMean(this.mbsAllocationDataArrayUpdate));// fund asset
	}
	
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	public double getRegCapitalRatio() {
		return regCapitalRatio;
	}

	public void setRegCapitalRatio(double regCapitalRatio) {
		Economy.regCapitalRatio = regCapitalRatio;
	}

	public double getSurvivalRate() {
		return survivalRate;
	}

	public void setSurvivalRate(double survivalRate) {
		Economy.survivalRate = survivalRate;
	}
	
	
	public double getTraditionalAssetsSurvivalRate() {
		return traditionalAssetsSurvivalRate;
	}

	public void setTraditionalAssetsSurvivalRate(double d) {
		// TODO Auto-generated method stub
		this.traditionalAssetsSurvivalRate = d;
	}
	
	public double getRecoveryRateOnTraditionalAsset() {
		return recoveryRateOnTraditionalAsset;
	}

	public void setRecoveryRateOnTraditionalAsset(double d) {
		// TODO Auto-generated method stub
		this.recoveryRateOnTraditionalAsset = d;
	}
	
	public double getRecoveryRateOnCreditAsset() {
		return recoveryRateOnCreditAsset;
	}

	public void setRecoveryRateOnCreditAsset(double d) {
		// TODO Auto-generated method stub
		this.recoveryRateOnCreditAsset = d;

	}
	
	
	public void setDefaultRecoveryRateOnCreditAsset(double d) {
		// TODO Auto-generated method stub
		this.defaultRecoveryRateOnCreditAsset = d;
	}


	public double getDefaultRecoveryRateOnCreditAsset() {
		return defaultRecoveryRateOnCreditAsset;
	}

	
	public double getAAAMBSCoupon() {
		return AAA_Rated_MBS_Coupon;
	}

	public void setAAAMBSCoupon(double aAAMBSCoupon) {
		AAA_Rated_MBS_Coupon = aAAMBSCoupon;
	}

	public double getNonAAAMBSCoupon() {
		return nonAAA_Rated_MBS_Coupon;
	}

	public void setNonAAAMBSCoupon(double nonAAAMBSCoupon) {
		Economy.nonAAA_Rated_MBS_Coupon = nonAAAMBSCoupon;
	}

	public double getAAAProbability() {
		return AAA_Rated_MBS_Probability;
	}

	public void setAAAProbability(double aAAProbability) {
		AAA_Rated_MBS_Probability = aAAProbability;
	}

	public double getAssetReturn() {
		return assetReturn;
	}

	public void setAssetReturn(double assetReturn) {
		Economy.assetReturn = assetReturn;
	}

	public double getLiabExpense() {
		return liabExpense;
	}

	public void setLiabExpense(double liabExpense) {
		Economy.liabExpense = liabExpense;
	}
	
	
	public BorrowerType getbType() {
		return bType;
	}

	public double getAssetValueMin() {
		return assetValueMin;
	}

	public double getAssetValueMax() {
		return assetValueMax;
	}

	public double getAveIncomeMin() {
		return aveIncomeMin;
	}

	public double getAveIncomeMax() {
		return aveIncomeMax;
	}

	public int getResetWindow() {
		return resetWindow;
	}

	public double getCurrentRiskFreeRate() {
		return currentRiskFreeRate;
	}

	public void setMortgageMaturityMinProbability(double d) {
		// TODO Auto-generated method stub
		mortgageMaturityMinProbability = d;
	}

	public void setMortgageMaturityMin(int i) {
		// TODO Auto-generated method stub
		mortgageMaturityMin = i;
	}

	public void setMortgageMaturityMax(int i) {
		// TODO Auto-generated method stub
		mortgageMaturityMax = i;
	}

	public int getMortgageMaturityMin() {
		// TODO Auto-generated method stub
		return mortgageMaturityMin;
	}

	public double getMortgageMaturityMinProbability() {
		// TODO Auto-generated method stub
		return mortgageMaturityMinProbability;
	}

	public int getMortgageMaturityMax() {
		// TODO Auto-generated method stub
		return mortgageMaturityMax;
	}

	public double getProbabilityOfHouseCostSevereBurden() {
		return probabilityOfHouseCostSevereBurden;
	}

	public void setProbabilityOfHouseCostSevereBurden(
			double probabilityOfHouseCostSevereBurden) {
		Economy.probabilityOfHouseCostSevereBurden = probabilityOfHouseCostSevereBurden;
	}


	public int getLoansPerMBSIssuance() {
		return loansPerMBSIssuance;
	}


	public void setLoansPerMBSIssuance(int loansPerMBSIssuance) {
		Economy.loansPerMBSIssuance = loansPerMBSIssuance;
	}
	
	public static void setGlobalReturnOnCredit(double gRC){
		returnOnGlobalCredit = gRC;
	}

	public double getGlobalReturnOnCredit(){
		return returnOnGlobalCredit;
	}
	
	public int getNumberOfFailedBanks(){
//		System.out.println("number of failed banks: "+this.failedBankList.size());
		return this.failedBankList.size();
		
	}

	
	public int getNumberOfSurvivingBanks(){
		return this.bankList.size();
	}



/*	public void setMultiTheadingForSirvivabilityCurve(int TorF){

		//multiTheadingForSirvivabilityCurve = TorF;

		this.multiTheadingForSirvivabilityCurve = TorF;

		if(this.multiTheadingForSirvivabilityCurve == 2){

			setAllBanks(true);
		}

	}//end of setMultiTheadingForSirvivabilityCurve
*/
	
	public double getOpportunityCostOfIncomeInvestment() {
		return opportunityCostOfFixedIncomeInvestment;
	}

	public void setOpportunityCostOfIncomeInvestment(
			double opportunityCostOfFixedIncomeInvestment) {
		Economy.opportunityCostOfFixedIncomeInvestment = opportunityCostOfFixedIncomeInvestment;
	}

	public double getLapfRegulatorySolvancyRatio() {
		return lapfRegulatorySolvancyRatio;
	}

	public void setLapfRegulatorySolvancyRatio(double lapfRegulatorySolvancyRatio) {
		Economy.lapfRegulatorySolvancyRatio = lapfRegulatorySolvancyRatio;
	}

	public double getAnnualExpectedPayout() {
		return annualExpectedLAPFPayout;
	}

	public void setAnnualExpectedPayout(double annualExpectedPayout) {
		Economy.annualExpectedLAPFPayout = annualExpectedPayout;
	}

	public double getAnnualExpectPayoutRebalancingRate() {
		return annualExpectLAPFPayoutRebalancingRate;
	}


	public void setAnnualExpectPayoutRebalancingRate(
			double annualExpectPayoutRebalancingRate) {
		Economy.annualExpectLAPFPayoutRebalancingRate = annualExpectPayoutRebalancingRate;
	}
	
	public void setBorrowerType(BorrowerType individualGeneric) {
		// TODO Auto-generated method stub
		bType = individualGeneric;
	}


	public void setCurrentRiskFreeRate(double d) {
		// TODO Auto-generated method stub
		currentRiskFreeRate = d;
	}

	public void setResetWindow(int i) {
		// TODO Auto-generated method stub
		resetWindow = i;
	}

	public void setAveIncomeMax(double d) {
		// TODO Auto-generated method stub
		aveIncomeMax = d;
	}

	public void setAveIncomeMin(double d) {
		// TODO Auto-generated method stub
		aveIncomeMin = d;
	}

	public void setAssetValueMax(double d) {
		// TODO Auto-generated method stub
		assetValueMax = d;
	}

	public void setAssetValueMin(double d) {
		// TODO Auto-generated method stub
		assetValueMin = d;
	}
	
	
	public double getReturnOnGlobalEquity() {
		return returnOnGlobalEquity;
	}


	public void setReturnOnGlobalEquity(double returnOnGlobalEquity) {
		Economy.returnOnGlobalEquity = returnOnGlobalEquity;
	}
	

	public void setPredefinedEquityFundReturns(
			ArrayList <Double> arrayList) {
		// TODO Auto-generated method stub
		this.predefinedEquityFundReturns = arrayList;
//		System.out.println("equity Rate: "+this.predefinedEquityFundReturns.size());
	}
	
	public ArrayList <Double> getPredefinedEquityFundReturns() {
		// TODO Auto-generated method stub
		return this.predefinedEquityFundReturns;
	}

	
	
	
	public void addBrokerDealMaketMakersToEconomy(
			ArrayList<Dealer_MarketMaker> brokerDealMaketMakerList2) {
		// TODO Auto-generated method stub
		this.brokerDealMaketMakerList = brokerDealMaketMakerList2;
//		System.out.println("dealers: "+this.brokerDealMaketMakerList.size());

	}


	public void addMarkovAgentsToEconomy(
			ArrayList<MDPMarkovAgent> MarkovAgentsList2) {
		// TODO Auto-generated method stub
		this.MDPMarkovAgentList = MarkovAgentsList2;
	}



	public void addInvestorsToEconomy(ArrayList<CInvestor> lapfList2) {
		// TODO Auto-generated method stub
		this.InvestorsList = lapfList2;
//		System.out.println("investors: "+this.InvestorsList.size());

	}



	public ArrayList<CInvestor> getInvestorList(){
		return this.InvestorsList;
	}
	
	public ArrayList<MDPMarkovAgent> getMDPMarkovAgentList(){
		return this.MDPMarkovAgentList;
	}



	public void addBanksToEconomy(ArrayList<Bank> bankList2) {
		// TODO Auto-generated method stub
		this.bankList = bankList2;
//		System.out.println("banks: "+this.bankList.size());

	}

	
	public double getMortgageLTVRatioMin() {
		return mortgageLTVRatioMin;
	}




	public void setMortgageLTVRatioMin(double mortgageLTVRatioMin) {
		Economy.mortgageLTVRatioMin = mortgageLTVRatioMin;
	}




	public double getMortgageLTVRatioMax() {
		return mortgageLTVRatioMax;
	}




	public void setMortgageLTVRatioMax(double mortgageLTVRatioMax) {
		Economy.mortgageLTVRatioMax = mortgageLTVRatioMax;
	}
	
	
	
	public double getProbabilityOfSubPrime() {
		return probabilityOfSubPrime;
	}




	public void setProbabilityOfSubPrime(double probabilityOfSubPrime) {
		Economy.probabilityOfSubPrime = probabilityOfSubPrime;
	}



	public void setGlobalSecuritisationRate(double secRate){
		this.globalSpecifiedSecuritisationate = secRate;
	}
	
	public double getGlobalSecuritisationRate(){
		return this.globalSpecifiedSecuritisationate;
	}
	

	public void setLAPFQuadraticCostFunction(boolean lapfQuadCostFunction) {
		// TODO Auto-generated method stub
		Economy.lapfQuadraticCostFunction = lapfQuadCostFunction;
	}
	
	public void setLAPFPremiumContributionsRate(
			double lapfPremiumContributionsRate) {
		// TODO Auto-generated method stub
		Economy.lapfPremiumContributionsRate = lapfPremiumContributionsRate;
	}
	
	public double getLAPFPremiumContributionsRate(){
		return Economy.lapfPremiumContributionsRate;
	}


	public void setLAPFPeriodicLiabilityPaymentRate(double lapfPeriodicLiabilityPaymentRate) {
		// TODO Auto-generated method stub
		Economy.lapfPeriodicLiabilityPaymentRate = lapfPeriodicLiabilityPaymentRate;
	}
	
	public double getLAPFPeriodicLiabilityPaymentRate(){
		return Economy.lapfPeriodicLiabilityPaymentRate;
	}


	
	public boolean getLAPFQuadraticCostFunction(){
		return lapfQuadraticCostFunction;
	}
	
	
	
	public void setSeniorTrancheQualityString(String seniorTrancheQualityString) {
		// TODO Auto-generated method stub
		this.seniorTrancheQualityString = seniorTrancheQualityString;
//		System.out.println(this.seniorTrancheQualityString);
	}

	public void setSeniorTrancheCouponAndDefault(double seniourTrancheCoupon,
			double seniourTrancheDefaultRate) {
		// TODO Auto-generated method stub
		this.seniourTrancheCoupon = seniourTrancheCoupon;
		this.seniourTrancheDefaultRate = seniourTrancheDefaultRate;
//		System.out.println(this.seniourTrancheCoupon);
//		System.out.println(this.seniourTrancheDefaultRate);
	}

	public void setMezzTrancheQualityString(String mezzTrancheQualityString) {
		// TODO Auto-generated method stub
		this.mezzTrancheQualityString = mezzTrancheQualityString;
//		System.out.println(this.mezzTrancheQualityString);
	}

	public void setMezTrancheCouponAndDefault(double mezzTrancheCoupon,
			double mezzTrancheDefaultRate) {
		// TODO Auto-generated method stub
		this.mezzTrancheCoupon = mezzTrancheCoupon;
		this.mezzTrancheDefaultRate = mezzTrancheDefaultRate;
//		System.out.println(this.mezzTrancheCoupon);
//		System.out.println(this.mezzTrancheDefaultRate);
	}

	public void setJuniorTrancheQualityString(String juniorTrancheQualityString) {
		// TODO Auto-generated method stub
		this.juniorTrancheQualityString = juniorTrancheQualityString;
//		System.out.println(this.juniorTrancheQualityString);
	}

	public void setJuniorTrancheCouponAndDefault(double juniourTrancheCoupon,
			double juniourTrancheDefaultRate) {
		// TODO Auto-generated method stub
		this.juniourTrancheCoupon = juniourTrancheCoupon;
		this.juniourTrancheDefaultRate = juniourTrancheDefaultRate;
//		System.out.println(this.juniourTrancheCoupon);
//		System.out.println(this.juniourTrancheDefaultRate);
	}
	
	public void setPerTrancheMBSCoupns(ArrayList<Double> couponList){
		this.MBSCouponsList = new ArrayList<Double>(couponList.size());
		for (int i = 0; i < couponList.size(); i++) {
			this.MBSCouponsList.add(couponList.get(i));
		}
	    
	}
	public void setPerTrancheMBSDefaults(ArrayList<Double> defaultList){
		this.MBSDefaultsList= new ArrayList<Double>(defaultList.size());
		for (int i = 0; i < defaultList.size(); i++) {
			this.MBSDefaultsList.add(defaultList.get(i));
		}
	    
	}
	
	
	public void setLoanResetPeriod(int genericLoanResetPeriod) {
		// TODO Auto-generated method stub
		Economy.genericLoanResetPeriod = genericLoanResetPeriod;
//		System.out.println(this.genericLoanResetPeriod);
	}


	
	
	public ArrayList<Double> getPerTrancheMBSCoupns(){
		return this.MBSCouponsList;
	}

	
	public ArrayList<Double> getPerTrancheMBSDefaults(){
		return this.MBSDefaultsList;
	}
	


	/**
	 * @return the seniorTrancheQualityString
	 */
	public String getSeniorTrancheQualityString() {
		return seniorTrancheQualityString;
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
	 * @return the mezzTrancheQualityString
	 */
	public String getMezzTrancheQualityString() {
		return mezzTrancheQualityString;
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
	 * @return the juniorTrancheQualityString
	 */
	public String getJuniorTrancheQualityString() {
		return juniorTrancheQualityString;
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
	
	
	public int getLoanResetPeriod() {
		// TODO Auto-generated method stub
		return Economy.genericLoanResetPeriod;
	}
	
	
	public void setFullyIndexedContractRateSpread(
			double value) {
		// TODO Auto-generated method stub
		this.fullyIndexedContractRateSpread = value;
		
	}

	public void setGenericPostRateResetDefaultRate(
			double value) {
		// TODO Auto-generated method stub
		Economy.genericPostRateResetDafaultRate = value;
	}
	
	public void setGenericPostRateResetCoupon(
			double value) {
		// TODO Auto-generated method stub
		Economy.genericPostRateResetCoupon = value;
	}

	
	public double getFullyIndexedContractRateSpread() {
		// TODO Auto-generated method stub
		return this.fullyIndexedContractRateSpread;
		
	}

	public double getGenericPostRateResetDafaultRate() {
		// TODO Auto-generated method stub
		return Economy.genericPostRateResetDafaultRate;
	}

	
	public double getGenericPostRateResetCoupon() {
		// TODO Auto-generated method stub
		return Economy.genericPostRateResetCoupon;
	}


	/**
	 * @return the securitisationCostConstantFactor
	 */
	public double getSecuritisationCostConstantFactor() {
		return securitisationCostConstantFactor;
	}

	/**
	 * @param securitisationCostConstantFactor
	 *            the securitisationCostConstantFactor to set
	 */
	public void setSecuritisationCostConstantFactor(
			double securitisationCostConstantFactor) {
		this.securitisationCostConstantFactor = securitisationCostConstantFactor;
	}


	
	public void setGenericDefaultsRates( double defaultRate,
			double coupon) {
		Economy.genericStartingDafaultRate = defaultRate;
		Economy.genericStartingCoupon = coupon;
	}

	
	public void setStochasticProcessParameters(
			StochasticProcessParameters stochParams) {
		// TODO Auto-generated method stub
		this.stochParams = stochParams;
		setBenchmarkMarketIndexLevel(this.stochParams.getInitialTraditionalAssetvalue());
		setBenchmarkMBSIndexLevel(this.stochParams.getInitialCreditAssetvalue());
		this.createStochaststicPathGenModel();
	}

	
	public StochasticProcessParameters getStochasticProcessParameters() {
		// TODO Auto-generated method stub
		return stochParams;
	}

	
	public void createStochaststicPathGenModel() {
		// TODO Auto-generated method stub
		this.stochPathGenModel = new StochaststicPathGenModel(this.getStochasticProcessParameters(), this.getEntityID());
		
	}
	
	
	public void setRandomProbabilityEngine(RandomEngine rndmEngine){
		this.randomEngine = rndmEngine;
	}

	
	public void setLinearfactor(double value){
		Economy.fundMDPLinearFactor = value;
	}
	public void setQuadraticfactor(double value){
		Economy.fundMDPQuadraticFactor = value;
	}
	

	
	public double getLinearfactor(){
		return Economy.fundMDPLinearFactor;
	}
	public double getQuadraticfactor(){
		return Economy.fundMDPQuadraticFactor;
	}
	
	
	private void setBenchmarkMarketIndexLevel(double value){
		benchmarkMarketIndexLevel = value;
	}
	private void setBenchmarkMBSIndexLevel(double value){
		benchmarkMBSIndexLevel = value;
	}
	private void setBenchmarkMarketIndexReturn(double t0Value, double t1Value){
		if(!MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions() && traditionalAssetVariationTypeString == "stochastic"){
			int  T = this.stochParams.getPathLength();
			benchmarkMarketIndexReturn = Math.pow(1 + ((t1Value-t0Value)/t0Value), T/100) - 1;
		}
		else{
			benchmarkMarketIndexReturn = ((t1Value-t0Value)/t0Value);
		}
	}
	private void setBenchmarkMBSIndexReturn(double t0Value, double t1Value){
		if(!MarkoseDYangModel_V01.RLModelParameters.isStochasticStateTransitions() && traditionalAssetVariationTypeString == "stochastic"){
			int  T = this.stochParams.getPathLength();
			benchmarkMBSIndexReturn = Math.pow(1 + ((t1Value-t0Value)/t0Value), T/100) - 1;
		}
		else{
			benchmarkMBSIndexReturn = ((t1Value-t0Value)/t0Value);
		}
	}
	
	
	private double getBenchmarkMarketIndexLevel(){
		return benchmarkMarketIndexLevel;
	}
	private double getBenchmarkMBSIndexLevel(){
		return benchmarkMBSIndexLevel;
	}
	private double getBenchmarkMarketIndexReturn(){
		return benchmarkMarketIndexReturn;
	}
	private double getBenchmarkMBSIndexReturn(){
		return benchmarkMBSIndexReturn;
	}
	
	
	public void setTraditionalAssetVariationTypeString(
			String traditionalAssetVariationTypeString) {
		// TODO Auto-generated method stub
		this.traditionalAssetVariationTypeString = traditionalAssetVariationTypeString;
	}

	
	/**
	 * @return the rationalCreditDefaults
	 */
	public static double getRationalCreditDefaults() {
		return rationalCreditDefaults;
	}

	
	public static double getPassiveCreditDefaults(){
		return passiveCreditDefaults;
	}


	/**
	 * @param rationalCreditDefaults the rationalCreditDefaults to set
	 */
	public static void setRationalCreditDefaults(double rationalCreditDefaults) {
		Economy.rationalCreditDefaults = rationalCreditDefaults;
	}


	public static void setPassiveCreditDefaults(double passiveCreditDefaults){
		Economy.passiveCreditDefaults = passiveCreditDefaults;
	}
	

	/**
	 * @return the equityDefaults
	 */
	public static double getEquityDefaults() {
		return equityDefaults;
	}



	/**
	 * @param equityDefaults the equityDefaults to set
	 */
	public static void setEquityDefaults(double equityDefaults) {
		Economy.equityDefaults = equityDefaults;
	}



	/**
	 * @return the bullCreditDefaults
	 */
	public static double getBullCreditDefaults() {
		return bullCreditDefaults;
	}



	/**
	 * @param bullCreditDefaults the bullCreditDefaults to set
	 */
	public static void setBullCreditDefaults(double bullCreditDefaults) {
		Economy.bullCreditDefaults = bullCreditDefaults;
	}


	public void setMDPHeuristicModelParameters(
			MDPHeuristicDecisionParameters heuristicDecisionModelParameters) {
		// TODO Auto-generated method stub
		this.setHeuristicDecisionModelParameters(heuristicDecisionModelParameters);
	}
	

	

	/**
	 * @return the bearCreditDefaults
	 */
	public static double getBearCreditDefaults() {
		return bearCreditDefaults;
	}



	/**
	 * @param bearCreditDefaults the bearCreditDefaults to set
	 */
	public static void setBearCreditDefaults(double bearCreditDefaults) {
		Economy.bearCreditDefaults = bearCreditDefaults;
	}
	

	public void setInvestorAgentTemprement(String[] investorAgentTemprement) {
		// TODO Auto-generated method stub
		this.InvestorAgentTemprement = new String[investorAgentTemprement.length];
	    System.arraycopy(investorAgentTemprement, 0, this.InvestorAgentTemprement, 0, this.InvestorAgentTemprement.length);
//		System.out.println(this.InvestorAgentTemprement[this.InvestorAgentTemprement.length-1]);
	}

	
	public long getCurrentSimTime(){
		return Sim.getAbsoluteTime();
	}



	private void setEntityID(){
		this.EntityID = this.getClass() + " "+ this.economyID;
	}
	
	public String getEntityID(){
		return EntityID;
	}



	public MDPHeuristicDecisionParameters getHeuristicDecisionModelParameters() {
		return heuristicDecisionModelParameters;
	}



	public void setHeuristicDecisionModelParameters(
			MDPHeuristicDecisionParameters heuristicDecisionModelParameters) {
		this.heuristicDecisionModelParameters = heuristicDecisionModelParameters;
	}



	public double getAggregateFundMBSAllocationUpdate() {
		return aggregateFundMBSAllocationUpdate;
	}



	public void setAggregateFundMBSAllocationUpdate(
			double aggregateFundMBSAllocationUpdate) {
		this.aggregateFundMBSAllocationUpdate = aggregateFundMBSAllocationUpdate;
	}
	
	
	public void setMDPRothErevParameters( MDPRothErevParameters ErevRothDecisionModelParameters){
		 this.ErevRothDecisionModelParameters =  ErevRothDecisionModelParameters;
	}
	
	
	public MDPRothErevParameters getMDPRothErevParameters(){
		 return this.ErevRothDecisionModelParameters;
	}

}
