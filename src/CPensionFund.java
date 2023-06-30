
import java.util.ArrayList;

public class CPensionFund extends CInvestor {

	private String agentDecisionType;
	
	
	//parameters for MDP Models
	private boolean modelIsMDP = false;
	
	
	private double expectedEquityLoss;
	private double expectedCreditDefault;
	
	



	public CPensionFund() {

	}

	public CPensionFund(int iDRSSD, String lapfName, String sector, double totalAssets,
			double totalLiabilities, Economy econ, Enviroment env,
			GeoEconoPoliticalSpace world) {
		// TODO Auto-generated constructor stub

		super(iDRSSD, lapfName, sector, econ.getLapfRegulatorySolvancyRatio(),
				totalAssets, totalLiabilities, econ, env, world);
		this.setExpectedContractualRequirements(this.getLiabilities(),
				this.getSolvencyMargin());
		// this.setLiabilities(this.expectedContractualRequirements*(1+this.economy.getLAPFPeriodicLiabilityPaymentRate()));
		this.setLiquidityBuffer(this.getExpectedContractualRequirements(),
				this.getSolvencyMargin());
		this.optimalCreditAssetAllocation = 0.0;
		this.setInitialFundingSurplus();
		this.traditionalAssetHoldings.add(0.0);
		this.equityAssetReturnPrevious = 0;
	}

	public CPensionFund(int iDRSSD, String lapfName, String sector, double totalAssets,
			Economy econ, Enviroment env, GeoEconoPoliticalSpace world) {
		// TODO Auto-generated constructor stub

		super(iDRSSD, lapfName, sector, econ.getLapfRegulatorySolvancyRatio(),
				totalAssets, econ, env, world);
		this.setExpectedContractualRequirements(this.getTotalNotionalAssets(),
				this.getSolvencyMargin());
		this.setLiquidityBuffer(this.getExpectedContractualRequirements(),
				this.getSolvencyMargin());
		this.optimalCreditAssetAllocation = 0.0;
		this.setInitialFundingSurplus();
		this.traditionalAssetHoldings.add(0.0);
		this.equityAssetReturnPrevious = 0;
		
	}

	public CPensionFund(double solvencyMarg, double totalAssets) {

		super(solvencyMarg, totalAssets);
		this.setExpectedContractualRequirements(this.getTotalNotionalAssets(),
				this.getSolvencyMargin());
		this.setLiquidityBuffer(this.getExpectedContractualRequirements(),
				this.getSolvencyMargin());
		this.optimalCreditAssetAllocation = 0.0;
		this.setInitialFundingSurplus();
		this.traditionalAssetHoldings.add(0.0);
		this.equityAssetReturnPrevious = 0;

	}

	public void simSurplusProcess() {
		// System.out.println("first-fund");
		/**
		 * THis method computes the funding surplus of the LAPF at each time
		 * point as well as it's Liquidity Buffer (equations 2.14 and 2.15)
		 * 
		 * Note that the expected contractual Liabilities are fixed and defined
		 * at the outset Liabilities are assumed to grow at a stochastic rate
		 * through the stochasticLiabGrowth variable
		 * 
		 * Note the model is only interested in determining the effect of
		 * defaults on the surpluses of LAPFS
		 * 
		 * 
		 */
		this.PeriodOnPeriodNotional.add(this.getTotalNotionalAssets());
		/*
		 * Define surlus simulation process if the agents are simple LAPFs
		 * 
		 */
		this.setCreditAssetReturn(Economy.returnOnGlobalCredit);
		this.equityAssetReturn = Economy.returnOnGlobalEquity;
		this.cashAssetReturn = Economy.currentRiskFreeRate;


		if(this.getAgentDecisionType() == "Simple Fund"){
			// determine if the fund is a simple mathematical equation optimizing fund
			this.setOptimalCreditAssetWeight();
			this.creditAssetHoldings.add(this.getOptimalCreditAssetWieght());
			this.traditionalAssetHoldings.add(1 - this
					.getOptimalCreditAssetWieght());
			this.setOptimalEquityAssetAllocation(1 - this
					.getOptimalCreditAssetWieght());
			this.setOptimalCashAssetWieght(0);
		} else{//else as an MDP learning agent set the historical array of credit and traditional asset weights
			this.creditAssetHoldings.add(this.getOptimalCreditAssetWieght());
			this.traditionalAssetHoldings.add(this.getOptimalEquityAssetAllocation());
		}
		this.calculateSimpleFundingSurplus();
		double K_t_minus_1 = this.getLiquidityBuffer();
		double S_t = this.getFundingSurplus();
		double riskFreeRateOfReturn = this.economy.getCurrentRiskFreeRate();

		this.setLiquidityBuffer(riskFreeRateOfReturn, K_t_minus_1, S_t);
		// this.getLiquidityBuffer());
		this.equityAssetReturnPrevious = this.equityAssetReturn;

	}
	
	

	
	public void determineOptimalInvestmentPolicy(){

		if(this.getAgentDecisionType() == "MDP Learning"){
			


			this.bestMDPPolicy = null;
			this.bestMDPPolicyHistory.add(bestMDPPolicy);
			this.setOptimalAssetWeights(this.bestMDPPolicy.getCashAssetWeightChoice(), 
					this.bestMDPPolicy.getEquityAssetWeightChoice(), this.bestMDPPolicy.getCreditAssetWeightChoice());
			System.out.println(this.bestMDPPolicy.label());
		}
	}
	
	
	
	public void computeLiquidityBuffer(){
		
		double K_t_minus_1 = this.getLiquidityBuffer();
		double S_t = this.getFundingSurplus();
		double riskFreeRateOfReturn = this.economy.getCurrentRiskFreeRate();

		this.setLiquidityBuffer(riskFreeRateOfReturn, K_t_minus_1, S_t);
		// this.getLiquidityBuffer());
		this.equityAssetReturnPrevious = this.equityAssetReturn;
	}

	public void simSurplusProcessManual(double riskFreeRateOfReturn,
			double creditAssetReturn, double equityAssetReturn,
			double strategyCostMultiplier, double stochasticLiabGrowth,
			boolean quadraticCostFunction) {
		/**
		 * THis method computes the funding surplus of the LAPF at each time
		 * point as well as it's Liquidity Buffer (equations 2.14 and 2.15)
		 * 
		 * Note that the expected contractual Liabilities are fixed and defined
		 * at the outset Liabilities are assumed to grow at a stochastic rate
		 * through the stochasticLiabGrowth variable
		 * 
		 * Note the model is only interested in determining the effect of
		 * defaults on the surpluses of LAPFS
		 * 
		 * 
		 */
		this.creditAssetReturn = economy.getGlobalReturnOnCredit();
		// System.out.println("MBS Coupon: "+this.creditAssetReturn);

		this.setOptimalCreditAssetWeight(creditAssetReturn, equityAssetReturn,
				strategyCostMultiplier);

		this.setFundingSurplus(creditAssetReturn, equityAssetReturn,
				this.getOptimalCreditAssetWieght(), strategyCostMultiplier,
				stochasticLiabGrowth, quadraticCostFunction);

		double K_t_minus_1 = this.getLiquidityBuffer();
		double S_t = this.getFundingSurplus();

		this.setLiquidityBuffer(riskFreeRateOfReturn, K_t_minus_1, S_t);
		// this.getLiquidityBuffer());

	}

	/**
	 * set the liquidity buffer used by the fund to absorb losses
	 * @param riskFreeRateOfReturn
	 * @param k_t_minus_1
	 * @param s_t
	 */
	private void setLiquidityBuffer(double riskFreeRateOfReturn,
			double k_t_minus_1, double s_t) {
		// TODO Auto-generated method stub
		double max_0_or_K_plus_S = Math.max(0, (k_t_minus_1 + s_t));

		double K_t = (1 + riskFreeRateOfReturn) * max_0_or_K_plus_S;

		this.setLiquidityBuffer(K_t);
	}

	/**
	 * set the initial fund surplus
	 */
	private void setInitialFundingSurplus() {
		// TODO Auto-generated method stub
		double St = 0;
		St = this.getTotalNotionalAssets()
				- this.getExpectedContractualRequirements()
				* (1 + this.getSolvencyMargin());

		this.setFundingSurplus(St);
	}
	
	private double[] updateWeightsAndNotionalArrays(ArrayList<Double> WaN){
		double[] wan = new double[WaN.size()];
		for (int i = 0; i < WaN.size(); i++) {
			wan[i] = WaN.get(i);
		}
		return wan;
	}
	
	/**
	 * This method calculates total exposures leaving up until the current time period
	 * It is useful when attempting to determine the losses or gains to be applied to the portfolio 
	 * after each iteration
	 * @param weightsSeries
	 * @param exposureSeries
	 * @return
	 */
	private double computeExistingExposures(double[] weightsSeries, double[] exposureSeries){
		
		double assetExposuresExisting = 0;
		
		for (int i = 0; i < weightsSeries.length; i++) {
			double x = weightsSeries[i];
			int xIndex = i;
			double At_1 = 0;
			for (int j = 0; j < exposureSeries.length; j++) {
				if (xIndex == j) {
					At_1 = exposureSeries[j];
				}
			}
			assetExposuresExisting += At_1 * x;
		}
		
		return assetExposuresExisting;
	}
	
	/**
	 * the assumption behind this method is that only risky assets will have transaction costs
	 * anything invested in cash will not have transaction costs associated with them
	 * ideally omega will be different for each asset
	 * @param riskCostFactors
	 * @param riskAssetWeights
	 * @return
	 */
	private double computePortfolioStrategyCosts(double[] riskCostFactors, double[] riskAssetWeights){
		
		double strategycost = 0;
		double fx = Economy.opportunityCostOfFixedIncomeInvestment;

		if(riskCostFactors.length == riskAssetWeights.length){
			for(int i = 0; i < riskAssetWeights.length; i++){
				if (economy.getLAPFQuadraticCostFunction() == true) {
						strategycost +=  riskCostFactors[i]* Math.pow(riskAssetWeights[i],2);
				} else{
					strategycost +=  riskCostFactors[i]* riskAssetWeights[i];
				}
			}
		} else {
			for(int i = 0; i < riskAssetWeights.length; i++){
				if (economy.getLAPFQuadraticCostFunction() == true) {
						strategycost +=  fx* Math.pow(riskAssetWeights[i],2);
				} else{
					strategycost +=  fx* riskAssetWeights[i];
				}
			}
		}
		return strategycost;
	}

	/**
	 * This method is used to compute surpluses for a 3 asset model consisting of cash, MBS and Equity
	 * Works for both the Simple Fund Model and the MDP Learning Fund Model
	 */
	public void calculateSimpleFundingSurplus() {
		// TODO Auto-generated method stub

		double C = this.getExpectedContractualRequirements();
		double St = 0;
		double rho = this.getSolvencyMargin();
		double creditAssetReturn2 = this.creditAssetReturn;
		double equityAssetReturn2 = economy.getReturnOnGlobalEquity();
		double cshAssetReturn2 = economy.getCurrentRiskFreeRate();
		double creditStrategyCostMultiplier = economy
				.getOpportunityCostOfIncomeInvestment();
		double equityStrategyCostMultiplier = economy
				.getOpportunityCostOfIncomeInvestment();
		double stochasticLiabGrowth = economy
				.getLAPFPeriodicLiabilityPaymentRate();
		double[] riskAssetWeights = {this.getOptimalCreditAssetWieght(), this.getOptimalEquityAssetAllocation()};
		double[] riskAssetTransactionCosts = {creditStrategyCostMultiplier, equityStrategyCostMultiplier};
		
		double assetGrowth = 0;
		double strategycost = 0;
		double strategyReturn = 0;

		double[] previousperiodCreditAssetWeights = updateWeightsAndNotionalArrays(this.creditAssetHoldings);
		double[] previousperiodTraditionalAssetWeights = updateWeightsAndNotionalArrays(this.traditionalAssetHoldings);
		double[] previousperiodAssetNotional = updateWeightsAndNotionalArrays(this.PeriodOnPeriodNotional);
		double creditExposuresExisting = computeExistingExposures(previousperiodCreditAssetWeights, previousperiodAssetNotional);
		double traditionalAssetExposuresExisting = computeExistingExposures(previousperiodTraditionalAssetWeights, previousperiodAssetNotional);

		double creditAssetRecoveryRate = this.economy
				.getRecoveryRateOnCreditAsset();
		double traditionalAssetRecoveryRate = this.economy
				.getRecoveryRateOnTraditionalAsset();
		double creditLGD = (1 - this.economy.getSurvivalRate())
				* (1 - creditAssetRecoveryRate);
		double equityLGD = this.economy
				.getTraditionalAssetsSurvivalRate() * (1- traditionalAssetRecoveryRate);

		double markToMarketLossesOnCreditAsset = creditExposuresExisting
				* ((1 - this.economy.getSurvivalRate()))
				* (1 - creditAssetRecoveryRate);

		double markToMarketLossesOnTraditionalAsset = traditionalAssetExposuresExisting
				* ((this.economy.getTraditionalAssetsSurvivalRate()))
				* (1 - traditionalAssetRecoveryRate);

		this.setMarkToMarketLossesCreditAssets(markToMarketLossesOnCreditAsset);
		this.setMarkToMarketLossesTraditionalAssets(markToMarketLossesOnTraditionalAsset);
		this.setMarkToMarketLossesTotal(markToMarketLossesOnTraditionalAsset
				+ markToMarketLossesOnCreditAsset);
		
		strategycost = computePortfolioStrategyCosts(riskAssetTransactionCosts, riskAssetWeights);


		strategyReturn = 
				this.getOptimalCashAssetWieght() * (1 + cshAssetReturn2)
				+
				this.getOptimalEquityAssetAllocation() * (1 + equityAssetReturn2)* (1-equityLGD)
				+ 
				this.getOptimalCreditAssetWieght() * (1 + creditAssetReturn2)* (1-creditLGD)
				- strategycost;


		if (this.environment.getLAPFMultiPeriodSolvancyModel() == false) {
			St = (C * (1 + rho))
			* (
					this.getOptimalCashAssetWieght() * (1 + cshAssetReturn2)
					+
					this.getOptimalEquityAssetAllocation() * (1 + equityAssetReturn2)
					+ 
					this.getOptimalCreditAssetWieght() * (1 + creditAssetReturn2)
					- 
					strategycost - (1 / (1 + rho))
					* (1 + stochasticLiabGrowth))
					- (markToMarketLossesOnCreditAsset + markToMarketLossesOnTraditionalAsset);
		} else {

			St = (this.getTotalNotionalAssets())
					* (
							this.getOptimalCashAssetWieght() * (1 + cshAssetReturn2)
							+
							this.getOptimalEquityAssetAllocation() * (1 + equityAssetReturn2)
							+ 
							this.getOptimalCreditAssetWieght() * (1 + creditAssetReturn2)
							 - strategycost - (1 / (1 + rho))
							* (1 + stochasticLiabGrowth)) 
							- (markToMarketLossesOnCreditAsset + markToMarketLossesOnTraditionalAsset);
		}


		this.setFundingSurplus(St);
		

		if (this.environment.getLAPFConstantContractualObligations() == false) {
			if (this.environment.getLAPFMultiPeriodSolvancyModel() == false) {
				assetGrowth = (C * (1 + rho))
						* (this.getOptimalCashAssetWieght() * (1 + cshAssetReturn2)
								+
								this.getOptimalEquityAssetAllocation() * (1 + equityAssetReturn2)
								+ 
								this.getOptimalCreditAssetWieght() * (1 + creditAssetReturn2)
								 - strategycost);
			} else {
				assetGrowth = (this.getTotalNotionalAssets())
						* (this.getOptimalCashAssetWieght() * (1 + cshAssetReturn2)
								+
								this.getOptimalEquityAssetAllocation() * (1 + equityAssetReturn2)
								+ 
								this.getOptimalCreditAssetWieght() * (1 + creditAssetReturn2)
								 - strategycost - (1 / (1 + rho))
								* (1 + stochasticLiabGrowth))
						- (markToMarketLossesOnCreditAsset + markToMarketLossesOnTraditionalAsset);
			}

		} else {
			assetGrowth = (this.getTotalNotionalAssets()) * strategyReturn;
		}

		this.setOptimalAssetAllocationStrtegyReturn(strategyReturn - 1);

		this.setTotalNotionalAssets(assetGrowth);
		this.setLiabilities((1 + stochasticLiabGrowth) * this.liabilities);

		if (this.environment.getLAPFConstantContractualObligations() == false) {
			this.setExpectedContractualRequirements(this.getLiabilities(),
					this.getSolvencyMargin());
		}
	}

	private void setFundingSurplus(double creditAssetReturn2,
			double equityAssetReturn2, double weight,
			double strategyCostMultiplier, double stochasticLiabGrowth,
			boolean quadraticCostFunction) {
		// TODO Auto-generated method stub

		double fx = 0;
		double C = this.getExpectedContractualRequirements();
		double St = 0;
		double rho = this.getSolvencyMargin();

		if (quadraticCostFunction == true) {
			fx = Math.pow(weight, 2);
		} else {
			fx = weight;
		}

		St = C
				* (1 + rho)
				* ((1 - weight) * equityAssetReturn2 + weight
						* creditAssetReturn2 - fx * strategyCostMultiplier - (1 / (1 + rho))
						* stochasticLiabGrowth);

		this.setFundingSurplus(St);

	}

	private void setOptimalCreditAssetWeight() {
		// TODO Auto-generated method stub
		double xCredit = (1 / (2 * economy
				.getOpportunityCostOfIncomeInvestment()))
				* (this.creditAssetReturn - economy.getReturnOnGlobalEquity());

		if (Economy.returnOnGlobalCredit == 0) {
			this.optimalCreditAssetAllocation = 0;
		} else {
			if (this.environment.getBoolShortselling() == true) {
				this.optimalCreditAssetAllocation = xCredit;
			} else {
				if (xCredit < 0) {
					this.optimalCreditAssetAllocation = 0;
				} else if (xCredit < 1 && xCredit > 0) {
					this.optimalCreditAssetAllocation = xCredit;
				} else {
					this.optimalCreditAssetAllocation = 1.0;
				}
			}

		}
	}

	private void setOptimalCreditAssetWeight(double creditAssetReturn2,
			double equityAssetReturn2, double strategyCostMultiplier) {
		// TODO Auto-generated method stub

		this.optimalCreditAssetAllocation = (1 / (2 * strategyCostMultiplier))
				* (creditAssetReturn2 - equityAssetReturn2);

	}

	private void setExpectedContractualRequirements(double totalAssets,
			double solvencyMarg) {
		this.expectedContractualRequirements = totalAssets / (1 + solvencyMarg);
	}
	
		

	@Override
	public double getExpectedContractualRequirements() {
		// TODO Auto-generated method stub
		return this.expectedContractualRequirements;
	}

	private void setLiquidityBuffer(double expectedContractualRequirements2,
			double solvencyMargin) {
		// TODO Auto-generated method stub

		this.liquidityBuffer = expectedContractualRequirements2
				* solvencyMargin;

	}

	private void setCreditAssetReturn(double CAR) {
		this.creditAssetReturn = CAR;
	}

	@Override
	public double getLiquidityBuffer() {
		return liquidityBuffer;
	}

	@Override
	public void setLiquidityBuffer(double liquidityBuffer) {
		this.liquidityBuffer = liquidityBuffer;
	}

	public double getMarkToMarketLossesTraditionalAssets() {
		return markToMarketLossesTraditionalAssets;
	}

	public void setMarkToMarketLossesTraditionalAssets(
			double markToMarketLossesTraditionalAssets) {
		this.markToMarketLossesTraditionalAssets = markToMarketLossesTraditionalAssets;
	}

	public double getMarkToMarketLossesCreditAssets() {
		return markToMarketLossesCreditAssets;
	}

	public void setMarkToMarketLossesCreditAssets(
			double markToMarketLossesCreditAssets) {
		this.markToMarketLossesCreditAssets = markToMarketLossesCreditAssets;
	}

	public double getMarkToMarketLossesTotal() {
		return markToMarketLossesTotal;
	}

	public void setMarkToMarketLossesTotal(double markToMarketLossesTotal) {
		this.markToMarketLossesTotal = markToMarketLossesTotal;
	}
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MDP MODEL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	

	/**
	 * @return the expectedEquityLoss
	 */
	@Override
	public double getExpectedEquityLoss() {
		return expectedEquityLoss;
	}






	/**
	 * @param expectedEquityLoss the expectedEquityLoss to set
	 */
	@Override
	public void setExpectedEquityLoss(double expectedEquityLoss) {
		this.expectedEquityLoss = expectedEquityLoss;
	}


	/**
	 * @return the expectedCreditDefault
	 */
	@Override
	public double getExpectedCreditDefault() {
		return expectedCreditDefault;
	}



	/**
	 * @param expectedCreditDefault the expectedCreditDefault to set
	 */
	@Override
	public void setExpectedCreditDefault(double expectedCreditDefault) {
		this.expectedCreditDefault = expectedCreditDefault;
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


}
