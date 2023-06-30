

import java.util.ArrayList;



public class Dealer_MarketMaker extends EconomicAgent{
	
	/**
	 * This class represents the market clearing agent
	 * this agent can be said to collect all the bids and offers 
	 * or buy and sell orders from the various other agents in the 
	 * market and determines the market clearing price
	 */
	
	ArrayList<Bank> supplySideList = new ArrayList<Bank>();
	ArrayList<CInvestor> demandSideList = new ArrayList<CInvestor>();
	protected Economy economy; 
	protected Enviroment environment;
	GeoEconoPoliticalSpace world;
	protected double fixedIncomeMBSCoupon;
	protected double returnOnGlobalEquity;
	protected String dealerName;
	double initialSupplyCount;
	
	
	public Dealer_MarketMaker(String name, ArrayList<Bank> sSideList, 
			ArrayList<CInvestor> dSideList, Economy econ, Enviroment env,
			GeoEconoPoliticalSpace world){
		
		
		this.dealerName = name;
		this.supplySideList = sSideList;
		this.initialSupplyCount = sSideList.size();
		this.demandSideList = dSideList;
		this.environment = env;
		this.economy = econ;
		this.world = world;
	}
	
	
//	@Watch(watcheeClassName = "securitization.agents.BankMarkoseDYanBaselI", 
//			watcheeFieldNames = "action_Securitised", 
//			//query = "action_Securitised = true",
//			whenToTrigger = WatcherTriggerSchedule.IMMEDIATE)
	public void setGlobalReturnOnCredit(){
//		System.out.println("first-market-maker");
		
		double annualExpectedLAPFPayout = Economy.annualExpectedLAPFPayout;
		double lapfRegulatorySolvancyRatio = Economy.lapfRegulatorySolvancyRatio;
		double returnOnGlobalEquity = Economy.returnOnGlobalEquity;
		double opportunityCostOfFixedIncomeInvestment = Economy.opportunityCostOfFixedIncomeInvestment;
		double averageSecuritisationRate = 0.0;
		double averageContractRate = 0.0;
		double[] supplySecRates = new double[supplySideList.size()];
		double[] supplyContractRates = new double[supplySideList.size()];
		double[] demandCreditWeights = new double[demandSideList.size()];
		double bankingSectorAssets = 0;
		double bankingSectorSecuritisedAssets = 0;
		double investmentSectorClaims = 0;
		double lF = this.economy.getLinearfactor();
		double qF = this.economy.getQuadraticfactor();
		double totalInvestmentCost = 0;
		double averageX = 0;
		double totalInvestmentCostAvg = 0;
		double recovery = 1;
		double lgd = 0;
		double outstandingIssuance = this.economy.getOutstandingMBSIssuance();
		double outstandingIssuanceByDefaultedBanks = this.economy.getOutstandingIssuanceByDefaultedBanks();
		double defaultRatio = 0;
		if(this.economy.getFailedBanksCount() > 0){
			defaultRatio = outstandingIssuanceByDefaultedBanks/outstandingIssuance;
		}
		for (int i = 0; i< this.supplySideList.size(); i++){
			bankingSectorAssets += this.supplySideList.get(i).getTotalAssets();
			bankingSectorSecuritisedAssets += (this.supplySideList.get(i).getTotalAssets()*
					this.supplySideList.get(i).getSecuritisationRate());
			supplySecRates[i] = this.supplySideList.get(i).getSecuritisationRate();
			
			supplyContractRates[i] = this.supplySideList.get(i).getReturnOnAssets();
		}
		
		averageSecuritisationRate = Means.geometricMean(supplySecRates);
		averageContractRate = Means.geometricMean(supplyContractRates);
		
		if(this.environment.getLAPFTypeString() == "MDP Learning"){
//			for (int i = 0; i< this.demandSideList.size(); i++){
//				//get the portfolio weight invested in the asset
//				double x = this.demandSideList.get(i).getOptimalCreditAssetWieght();
//				demandCreditWeights[i] = x;
//				//compute the total amount invested in the asset
//				//x * [C(1+p)]
//				investmentSectorClaims += this.demandSideList.get(i).getExpectedContractualRequirements()*
//						(1+this.demandSideList.get(i).getSolvencyMargin()) * x;
//				//now compute the cost component of investing in the asset
//				//(1/x)*(B*x^2) where B = (lF + qF)
//				//note that lF is divided by x because it is the linear component of B therefore cannot be multiplied by x^2 
//				//divide by x again since the return R = (1/x)*[[(alpha*A)/[C(1+p)]] + (B*x^2)] 
//				if(!this.economy.getLAPFQuadraticCostFunction()){//if the investment cost function for the investor is not quadratic
//					totalInvestmentCost += x * (lF / x);
//				} else {
//				totalInvestmentCost += (Math.pow(x, 2) * ((lF / x) + qF)) / x;
////				totalInvestmentCost += (x * ((lF / x) + qF));
//				}
//			}
//			averageX = Means.geometricMean(demandCreditWeights);
//			totalInvestmentCostAvg = (Math.pow(averageX, 2) * ((lF / averageX) + qF)) / averageX;
			for(int i = 0; i < demandSideList.size(); i++){
				demandCreditWeights[i] = demandSideList.get(i).getOptimalCreditAssetWieght();
			}
			
			int defaultedBanks = this.economy.getFailedBanksCount();
			System.out.println("\n");
			System.out.println("defaultedBanks: " + defaultedBanks);
			double defaultFactor = 0;
			double rtm1 = this.fixedIncomeMBSCoupon;
			double rt = this.economy.currentState.getCreditAssetExpectedReturn();
			this.initialSupplyCount = this.economy.getNumberOfSurvivingBanks();
			double recRateMBS = economy.getDefaultRecoveryRateOnCreditAsset();
			
			if(defaultedBanks > 0){
//				averageX = Means.geometricMean(demandCreditWeights);
				averageX = Means.arithmeticMean(demandCreditWeights);
				defaultFactor = ((defaultedBanks)/this.initialSupplyCount)
						*(this.economy.getGenericPostRateResetDafaultRate());
				defaultFactor = defaultRatio;
//				lgd = (1- economy.getRecoveryRateOnCreditAsset())*averageX;
				lgd = defaultRatio*(1-recRateMBS);
				
				recovery = (1-defaultFactor) * (averageX*recRateMBS);
			}
//			System.out.println("RecoveryRateOnCreditAsset: " + recRateMBS);
			System.out.println("defaultRatio: " + defaultRatio);
			System.out.println("lgd: " + lgd);
			
			
//			this.setMdRateOfReturn((rtm1 + rt * (1 - recovery)));
			if(rt == 0){
				this.setMdRateOfReturn(rtm1  - lgd);
//				this.setMdRateOfReturn(rtm1 *(1 - lgd));
			} else {
				this.setMdRateOfReturn((rtm1 + rt) - lgd);
//				this.setMdRateOfReturn((rtm1 + rt) * (1 -lgd));
//				this.setMdRateOfReturn((rtm1 + rt * (1 -lgd)));
			}
			this.setReturnOnGlobalEquity(returnOnGlobalEquity);
		}
		else {
		for (int i = 0; i< this.demandSideList.size(); i++){
			investmentSectorClaims += this.demandSideList.get(i).getExpectedContractualRequirements()*
					(1+this.demandSideList.get(i).getSolvencyMargin());
		}
		this.setMdRateOfReturn(investmentSectorClaims, bankingSectorSecuritisedAssets, averageSecuritisationRate,
				returnOnGlobalEquity, opportunityCostOfFixedIncomeInvestment, averageContractRate, totalInvestmentCost, averageX, totalInvestmentCostAvg);
		}
		
//		this.setMdRateOfReturn(investmentSectorClaims, bankingSectorSecuritisedAssets, averageSecuritisationRate,
//				returnOnGlobalEquity, opportunityCostOfFixedIncomeInvestment, averageContractRate, totalInvestmentCost, averageX, totalInvestmentCostAvg);
	}
	
	private void setMdRateOfReturn(double annualExpectedLAPFPayout,
			double bankingSectorSEcuritisedAssets, double averageSecuritisationRate, double returnOnGlobalEquity,
			double opportunityCostOfFixedIncomeInvestment,double averageContractRate, double totalInvestmentCost,
			double averageX, double totalInvestmentCostAvg) {
		// TODO Auto-generated method stub
		
		
		double Re = returnOnGlobalEquity;
		this.setReturnOnGlobalEquity(returnOnGlobalEquity);
		double theta = opportunityCostOfFixedIncomeInvestment;
		double actuarialEstimateOfLAFLiabilites = annualExpectedLAPFPayout;
		double alha_x_A = bankingSectorSEcuritisedAssets;
		double maxRate = averageContractRate;
		double uncapedMBSCouponRate = 0;
		
		
		if(this.environment.getLAPFTypeString() == "MDP Learning"){
			
				uncapedMBSCouponRate = ( (totalInvestmentCostAvg) +
						((averageSecuritisationRate)/(averageX))) - 1;
			
		} else {
			//the output of the following if else are identical for now as no true model has yet been defined for multi-period analysis
			if(this.environment.getLAPFMultiPeriodSolvancyModel() == false){
				uncapedMBSCouponRate = Re+(2*theta)*((alha_x_A)/(actuarialEstimateOfLAFLiabilites));
			}else{
				uncapedMBSCouponRate =(Re) 
						+ (2*theta)
						*((alha_x_A)/(actuarialEstimateOfLAFLiabilites));
			}
		}
		
		
//		System.out.println("Uncontrolled MBS Coupon: "+uncapedMBSCouponRate);
		/*
		 * The following statement is to ensure that when all banks in the model have failed and are issuing no further
		 * Asset Backed Securities, that the return on these assets is set to zero rather than
		 */
		if(this.economy.getPredefinedEquityFundReturnsBoolean() == false){
			if(alha_x_A > 0.0){
				this.fixedIncomeMBSCoupon = Math.min(maxRate, uncapedMBSCouponRate);
			}
			else {
				this.fixedIncomeMBSCoupon = 0.0;
			}
		} else {
			if(alha_x_A > 0.0){
				this.fixedIncomeMBSCoupon = uncapedMBSCouponRate;
			}
			else {
				this.fixedIncomeMBSCoupon = 0.0;
			}
		}
//		this.fixedIncomeMBSCoupon = uncapedMBSCouponRate;

	}





	private void setMdRateOfReturn(double annualExpectedLAPFPayout,
			double lapfRegulatorySolvancyRatio, double returnOnGlobalEquity,
			double opportunityCostOfFixedIncomeInvestment,
			double averageSecuritisationRate, double bankingSectorAssets, double averageContractRate) {
		// TODO Auto-generated method stub
		
		
		double Re = returnOnGlobalEquity;
		double sigma = opportunityCostOfFixedIncomeInvestment;
		double C = annualExpectedLAPFPayout;
		double rho = lapfRegulatorySolvancyRatio;
		double alpha = averageSecuritisationRate;
		double A = bankingSectorAssets;
		double maxRate = averageContractRate;
		double uncapedMBSCouponRate = Re + 2*sigma*((alpha*A)/(C*(1+rho)));
		
		this.fixedIncomeMBSCoupon = Math.min(maxRate, uncapedMBSCouponRate);
//		this.fixedIncomeMBSCoupon = uncapedMBSCouponRate;

	}
	
	private void setMdRateOfReturn(double rateOfReturn) {
		// TODO Auto-generated method stub
		
		this.fixedIncomeMBSCoupon = rateOfReturn;

	}
	
	private void setReturnOnGlobalEquity(double returnOnGlobalEquity2) {
		// TODO Auto-generated method stub
		this.returnOnGlobalEquity = returnOnGlobalEquity2;
	}

	
	
	public String getInvestorName() {
		return dealerName;
	}
	
	public double getMarketMakerMBSCoupon(){
		return this.fixedIncomeMBSCoupon*100;
	}

	public double getMarketMakerMBSRate(){
		return this.fixedIncomeMBSCoupon;
	}
	
	public double getReturnOnGlobalEquity(){
		return this.returnOnGlobalEquity;
	}


	@Override
	public void checkLife() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void reproduce() {
		// TODO Auto-generated method stub
		
	}


	
}
