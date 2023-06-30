import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.BetaDistribution;


public class MDPSecuritisationRateIntermediateHueristic {
	
	public static int INTERMEDIATE_HUTIRSTIC_ID;
	ArrayList<MortgageLoansState> MortgageLoansStates = new ArrayList<MortgageLoansState>();
	double heuristicCostConstantLowDemand;
	double heuristicCostConstantMidDemand;
	double heuristicCostConstantHighDemand;
	boolean heuristicLowDemand = false;
	boolean heuristicMidDemand = false;
	boolean heuristicHighDemand = false;
	double contractRate;
	double depositRate;
	double stateSelectbias;
	Random stateSelectRandomGen = new Random();
	BetaDistribution beta;
	int greedKMaxValue = 2;
	double greedIncrement;
	double greed = 1;
	double secRateIncrement;
	int secRateArrayMaxSize = 1;
	double[] capitalAccumulation;
	double[] timeEvolvingKValues;
	double[] derivedKValueDrivenoptimalSecuritisationRates;
	double liabilityT;
	double assetT;
	double[] greedKValues;
	double[] secRateArray;
	private MDPHeuristicDecisionParameters heuristicDecisionModelParameters;
	double marketDemandImpliedYield;
	private double asset;
	private double liability;
	private double regRatio;
	private double optimalSecuritisationRate;
	private double pastOptimalSecuritisationRate;
	private boolean quadraticSecuritisationCostFunction;
	private double estimatedSecuritisationCost = 0;
	private MDPHeuristicSearchSecuritisationDataContainer heuristicSearchDataContainer;
	private double assetSurvivalRate;
	
	
	public MDPSecuritisationRateIntermediateHueristic(double secRateIncrement, double greedIncrement, double stateSelectbias, 
			double hueristicCostConstantLowDemand, double hueristicCostConstantMidDemand,
	double hueristicCostConstantHighDemand){
		this.secRateIncrement = secRateIncrement;
		this.greedIncrement = greedIncrement;
		this.stateSelectbias = stateSelectbias;
		this.heuristicCostConstantLowDemand = hueristicCostConstantLowDemand;
		this.heuristicCostConstantMidDemand = hueristicCostConstantMidDemand;
		this.heuristicCostConstantHighDemand = hueristicCostConstantHighDemand;
		this.pastOptimalSecuritisationRate = 0.2;
		beta = new BetaDistribution(this.heuristicDecisionModelParameters.getBetaDistributionAlphaGeneric(),
					this.heuristicDecisionModelParameters.getBetaDistributionBetaGeneric());
		createSecuritisationRateArray(this.secRateIncrement);
		createGreedArray(this.greedIncrement);
	}
	
	
	public MDPSecuritisationRateIntermediateHueristic(MDPHeuristicDecisionParameters heuristicDecisionModelParameters) {
		// TODO Auto-generated method stub
		this.heuristicDecisionModelParameters = heuristicDecisionModelParameters;
		this.secRateIncrement = heuristicDecisionModelParameters.getSecuritisationRateIncrement();
		this.greedIncrement = heuristicDecisionModelParameters.getGreedParameterIncrement();
		this.stateSelectbias = heuristicDecisionModelParameters.getGenericBias();
		this.heuristicCostConstantLowDemand = heuristicDecisionModelParameters.getHueristicCostConstantLowDemand();
		this.heuristicCostConstantMidDemand = heuristicDecisionModelParameters.getHueristicCostConstantMidDemand();
		this.heuristicCostConstantHighDemand = heuristicDecisionModelParameters.getHueristicCostConstantHighDemand();
		beta = new BetaDistribution(this.heuristicDecisionModelParameters.getBetaDistributionAlphaGeneric(),
				this.heuristicDecisionModelParameters.getBetaDistributionBetaGeneric());
		this.pastOptimalSecuritisationRate = 0.2;
		createSecuritisationRateArray(this.secRateIncrement);
		createGreedArray(this.greedIncrement);
	}

	
	
	
	/**
	 * Triggers the series of method calls required to compute the optimisation heuristic.
	 * Note that since the action to be taken is the securitisation rate and the cost is 
	 * dependent on the securitisation rate, the simplifying assumption has been made to 
	 * use the previous period's optimal securitisation rate as the basis for determining
	 * securitisation costs assumed in planning for the current period.
	 * @param assets
	 * @param liabilities
	 * @param contractRate
	 * @param depositRate
	 * @param regRate
	 * @param impliedCoupon
	 * @param quadraticSecCost
	 */
	public void conductHeurisiticSearch(double assets, double liabilities, double contractRate, double depositRate,
			double suvRate, double regRate, double demandTm1, boolean quadraticSecCost){
		setHuristicDemandRange(demandTm1);
		setContractRate(contractRate);
		setAssetSurvivalRate(suvRate);
		setAssets(assets);
		setDepositRate(depositRate);
		setLiabilities(liabilities);
		setRegRatio(regRate);
		setQuadraticSecCost(quadraticSecCost);
		setMarketDemandImpliedYields();
		computeIntermediateHeuristicSecuritisation();
	}

	

	/**
	 * Note that I use the beta distribution as a means of 1: adhering to Basel II LGD conventions
	 * and 2 to have some level of correlation between losses as defined by economic states of loans
	 */
	public void computeIntermediateHeuristicSecuritisation(){
		
		double maxK = 0;
		double maxR = 0;
		double R = 0;
		double k;
		MortgageLoansState S_0;
		MortgageLoansState S_t;
		int epochs = this.heuristicDecisionModelParameters.getNumberOfDecisionEpochsHeuristic();
		int iterations = this.heuristicDecisionModelParameters.getNumberOfIterationsHeuristic();
		int rounds = epochs*iterations;
		double prob;
		double betaProb;
		double expectedReturn;
		double action_t;
		double regCapExp;
		capitalAccumulation = new double[this.greedKValues.length];
		timeEvolvingKValues = new double[this.greedKValues.length];
		derivedKValueDrivenoptimalSecuritisationRates = new double[this.greedKValues.length];

		if(isQuadraticSecCost()){
			estimatedSecuritisationCost = marketDemandImpliedYield * (this.pastOptimalSecuritisationRate 
					+  Math.pow(this.pastOptimalSecuritisationRate, 2));
		}
		else {
			estimatedSecuritisationCost = marketDemandImpliedYield * this.pastOptimalSecuritisationRate;
		}

		for(int i = 0; i < this.greedKValues.length; i++){
			R = 0;
			k = this.greedKValues[i];
			timeEvolvingKValues[i] = k;
			prob = stateSelectRandomGen.nextDouble();
			
			if(prob < (1 - this.stateSelectbias)){
				S_0 = this.MortgageLoansStates.get(0);
			} else {
				S_0 = this.MortgageLoansStates.get(1);
			}
			
			for(int t = 1; t <= rounds; t++){
				//prob = stateSelectRandomGen.nextDouble();
				betaProb = beta.inverseCumulativeProbability(prob);
				if(betaProb < (1 - this.stateSelectbias)){
					S_t = this.MortgageLoansStates.get(0);
				} else {
					S_t = this.MortgageLoansStates.get(1);
				}
				expectedReturn = S_t.getExpectedReturn();
				
				
				action_t = Rounding.roundTwoDecimals(k*(expectedReturn/estimatedSecuritisationCost));
//				action_t = Rounding.roundTwoDecimals(k*(expectedReturn/marketDemandImpliedYield));
				if(action_t > this.secRateArray[this.secRateArray.length-1]){
					action_t = 1.00;
				} else if(action_t < this.secRateArray[0]){
					action_t = 0.00;
				}
				//as with the standard securitisation model we compute -M(5) = [1-esp*(1-alpha)]A(5) - L(5). 
//				setLiabilitiesT(this.liability);
//				setAssetsT(this.asset, expectedReturn, estimatedSecuritisationCost);
//				regCapExp = 1 - (this.regRatio*(1-action_t));
				R +=  computeInjectionsT(this.asset, this.liability, expectedReturn, estimatedSecuritisationCost, action_t);
//				R +=  		regCapExp*this.assetT - this.liabilityT;
			}
			//set the array of capital accumulation rates 
			capitalAccumulation[i] = R;
			if(k == 0){
				maxR = R;
				maxK = k;
			}
			//find the optimal value of R and K
			if(R > maxR){
				maxR = R;
				maxK = k;
			}
		}// of K-loop
		
		
		double expRets = this.MortgageLoansStates.get(0).getExpectedReturn()*(1 - this.stateSelectbias) 
				+ this.MortgageLoansStates.get(1).getExpectedReturn()*this.stateSelectbias;
		this.optimalSecuritisationRate = Rounding.roundTwoDecimals(maxK*(expRets/estimatedSecuritisationCost));
		if(optimalSecuritisationRate > this.secRateArray[this.secRateArray.length-1]){
			optimalSecuritisationRate = 1.00;
		} else if(optimalSecuritisationRate < this.secRateArray[0]){
			optimalSecuritisationRate = 0.00;
		}
		this.pastOptimalSecuritisationRate = this.optimalSecuritisationRate;
		buildOptimalSecuritisationRateArray(expRets, estimatedSecuritisationCost, 
				capitalAccumulation, timeEvolvingKValues);
	}
	
	
	private void buildOptimalSecuritisationRateArray(double expRets,
			double estimatedSecuritisationCost, double[] capitalAccumulation2,
			double[] timeEvolvingKValues2) {
		// TODO Auto-generated method stub
		double secRate = 0;
		double k = 0;
		
		//loop through all k values and compute the securtisation rate then assign to the heuristic k-Value derived
		//securitisation rate array
		//once done build a new container to use as a basis for displaying the data
		for(int i = 0; i < timeEvolvingKValues2.length; i++){
			k = timeEvolvingKValues2[i];
			secRate = Rounding.roundTwoDecimals(k*(expRets/estimatedSecuritisationCost));
			if(secRate > this.secRateArray[this.secRateArray.length-1]){
				secRate = 1.00;
			} else if(secRate < this.secRateArray[0]){
				secRate = 0.00;
			}
			this.derivedKValueDrivenoptimalSecuritisationRates[i] = secRate;
		}
		/**
		 * constructor:
		 * public MDPHeuristicSearchSecuritisationDataContainer(double[] heuristicKValue, double[] kValueUtiliy, 
		 * 		double[] kValueActions)
		 */
		setHeuristicSearchDataContainer(new MDPHeuristicSearchSecuritisationDataContainer(timeEvolvingKValues2, 
				capitalAccumulation2, this.derivedKValueDrivenoptimalSecuritisationRates));
		
	}


	
	
	public void updateBetaDistribution(double alpha, double beta){
		this.beta = new BetaDistribution(alpha, beta);
	}
	
	public void setAgentEconomicExpectationsBias(double bias){
		if(!this.heuristicDecisionModelParameters.isIdenticalStateSelectionBias()){
			this.stateSelectbias = bias;
		}
	}
	
	public void setContractRate(double cr){
		this.contractRate = cr;
		updateMortgageLoanStates();
	}
	
	public void setDepositRate(double dr){
		this.depositRate = dr;
	}
	
	public double computeInjectionsT(double A, double L, double expRet, double secCost, double projSecRate){
		int epochs = this.heuristicDecisionModelParameters.getNumberOfDecisionEpochsHeuristic();
		double capInjection = 0; 
		double regCapExposure = 0;
		double[] survivalRateArray = new double[epochs+1];
		double[] assetReturnArray = new double[epochs+1];
		double[] liabilityExpenseArray = new double[epochs+1];
		double[] regCapitalRatioArray = new double[epochs+1];

		double[] productRL = new double[epochs+1];
		double[] assetArray = new double[epochs+1];
		double[] liabilityArray = new double[epochs+1];
		double[] equityArray = new double[epochs+1]; 
		double[] secCostArray = new double[epochs+1]; 
		
		double[] X = new double[epochs+1]; 
		double[] XProduct = new double[epochs+1];
		double[] XProductM1 = new double[epochs+1];
		double[] XProductForL = new double[epochs+1];
		double[] XProductForLm1 = new double[epochs+1];
		double[] productLX = new double[epochs+1];
		double[] productLXm1 = new double[epochs+1];
		double sumOfProductL;
		double sumOfProductLm1;
		double sumA;
		double sumAM1;

		for (int i = 0; i <= epochs; i++){
				survivalRateArray[i] = this.assetSurvivalRate;
				assetReturnArray[i] = expRet;
				regCapitalRatioArray[i] = this.regRatio;
				secCostArray[i] = secCost;
				liabilityExpenseArray[i] = this.depositRate;

		}
		
		assetArray[0] = A;
		liabilityArray[0] = L;
		equityArray[0] = assetArray[0] - liabilityArray[0];
		productRL[0] = Math.pow((1 + liabilityExpenseArray[0]), 0);
		
		
		
		for(int i = 1; i <= epochs; i++){
			productRL[i] = productRL[i-1]*(1+liabilityExpenseArray[i-1]); 
			liabilityArray[i] = liabilityArray[0]*productRL[i];
		}
		
		X[0] = 1;
		for(int i = 1; i <= epochs; i++){
			X[i] =  1 + (1-projSecRate)*(survivalRateArray[i] - regCapitalRatioArray[i])
					+ projSecRate + survivalRateArray[i]*assetReturnArray[i]
							- secCostArray[i];
		}
		
		for(int j = 0; j <= epochs; j++){
			XProduct[j] = 1;
			XProductM1[j] = 1;
			XProductForL[j] = 1;
			XProductForLm1[j] = 1;
			productLX[j] = 1;
			productLXm1[j] = 1;
		}
		for(int j = 0; j <= epochs; j++){
			for(int i = 0; i <= j; i++){
				XProduct[j] = XProduct[j]*X[i];
			}
		}
		for(int j = 0; j <= epochs-1; j++){
			for(int i = 0; i <= j; i++){
				XProductM1[j] = XProductM1[j]*X[i];
			}
		}
		
		/**
		 * set the product arrays for liabilities
		 */
		for(int j = 1; j <= epochs; j++){
			int k = j+1;
			for(int i = k; i <= epochs; i++){
				XProductForL[j] = XProductForL[j]*X[i];
			}
		}
		for(int j = 1; j <= epochs; j++){
			int k = j+1;
			for(int i = k; i <= epochs-1; i++){
				XProductForLm1[j] = XProductForLm1[j]*X[i];
			}
		}
		
//		System.out.println("XProductForL: "
//				+ Arrays.toString(XProductForL));
//		
//		System.out.println("XProductForLm1: "
//				+ Arrays.toString(XProductForLm1));
		
		
		sumA = assetArray[0]*XProduct[epochs];
		sumAM1 = assetArray[0]*XProductM1[epochs];
		
		/**
		 * set the asset and liabilitiy sums to zero as precaution
		 * 
		 */
		sumOfProductL = 0;
		sumOfProductLm1 = 0;
		
		/**
		 * set the product arrays for liabilities
		 */
		for(int j = 0; j <= epochs; j++){
			int k = j+1;
			if(k < epochs){
				productLX[j] = XProductForL[k]*liabilityArray[j];
			}
			else {
				productLX[j] = liabilityArray[j];
			}
		}
		for(int j = 0; j <= epochs-1; j++){
			int k = j+1;
			if(k < epochs-1){
				productLXm1[j] = XProductForLm1[k]*liabilityArray[j];
			}
			else {
				productLXm1[j] = liabilityArray[j];
			}
		}
		

//		System.out.println("productLX: "
//				+ Arrays.toString(productLX));
//		
//		System.out.println("productLXm1: "
//				+ Arrays.toString(productLXm1));
		
		
		for(int j = 0; j<=epochs-1; j++){
			sumOfProductL += productLX[j];
		}
		
		for(int j = 0; j<=epochs-2; j++){
			sumOfProductLm1 += productLXm1[j];
		}
		
		double projectionOfAatT = sumA - sumOfProductL;
		double projectionOfAatTm1 = sumAM1 - sumOfProductLm1;

		
		regCapExposure = 1 - (regCapitalRatioArray[epochs]*(1-projSecRate));
		capInjection = (regCapExposure*projectionOfAatT - liabilityArray[epochs]);
		return capInjection;
		
	}
	
	
	public void setAssetsT(double A, double expRet, double estimatedSecuritisationCost){
		int epochs = this.heuristicDecisionModelParameters.getNumberOfDecisionEpochsHeuristic();
		this.assetT = A;
		for(int i = 1; i <= epochs; i++){
			this.assetT *= (1+expRet*this.assetSurvivalRate);
			this.assetT *= (1-this.regRatio);
			this.assetT += this.pastOptimalSecuritisationRate*A*(1-estimatedSecuritisationCost);
		}
	}
	
	public void setLiabilitiesT(double L){
		int epochs = this.heuristicDecisionModelParameters.getNumberOfDecisionEpochsHeuristic();
		this.liabilityT = L;
		for(int i = 1; i <= epochs; i++){
			this.liabilityT *= (1+this.depositRate);
		}
	}
	
	private void setAssetSurvivalRate(double suvRate) {
		// TODO Auto-generated method stub
		this.assetSurvivalRate = suvRate;
	}


	
	public void setAssets(double A){
		this.asset = A;
	}
	
	public void setLiabilities(double L){
		this.liability = L;
	}
	
	public void setRegRatio(double e){
		this.regRatio = e;
	}
	
	public void setMarketDemandImpliedYields(){
		if(this.heuristicHighDemand){
			marketDemandImpliedYield = this.heuristicCostConstantHighDemand;
		}
		else if(this.heuristicMidDemand){
			marketDemandImpliedYield = this.heuristicCostConstantMidDemand;
		} else {
			marketDemandImpliedYield = this.heuristicCostConstantLowDemand;
		}
	}
	
	
	
	public void updateMortgageLoanStates(){
		double erDef = this.heuristicDecisionModelParameters.getRecoveryRateOnCreditAsset()*this.contractRate;
		
		if(this.MortgageLoansStates.isEmpty()){
			this.MortgageLoansStates.add(new MortgageLoansState(-1, erDef));
			this.MortgageLoansStates.add(new MortgageLoansState(-1, this.contractRate));
		} else {
			this.MortgageLoansStates.get(0).updateStateReturn(erDef);
			this.MortgageLoansStates.get(1).updateStateReturn(this.contractRate);
		}
		
	}
	
	private void setQuadraticSecCost(boolean quadraticSecCost) {
		// TODO Auto-generated method stub
		this.quadraticSecuritisationCostFunction = quadraticSecCost;
	}

	private boolean isQuadraticSecCost() {
		// TODO Auto-generated method stub
		return this.quadraticSecuritisationCostFunction;
	}


	/**
	 * The createGreedArray creates an array of K values for the heuristic
	 * defaults to an array size of 201 increamenting by 0.01
	 * @param greedIncrement2
	 */
	private void createGreedArray(double inc) {
		// TODO Auto-generated method stub
		int length = (int) ((int) greedKMaxValue/inc);
		greedKValues =  new double[length+1]; 
		
		for(int i = 0; i < greedKValues.length; i++){
			greedKValues[i] = i*inc;
		}
	}




	public double getOptimalSecuritisationRate() {
		return optimalSecuritisationRate;
	}


	public void setOptimalSecuritisationRate(double optimalSecuritisationRate) {
		this.optimalSecuritisationRate = optimalSecuritisationRate;
	}


	/**
	 * The createSecuritisationRateArray creates an array of securitisation rates for the heuristic
	 * @param greedIncrement2
	 */
	private void createSecuritisationRateArray(double inc) {
		// TODO Auto-generated method stub
		int length = (int) ((int) secRateArrayMaxSize/inc);
		secRateArray = new double[length+1]; 
		
		for(int i = 0; i < secRateArray.length; i++){
			secRateArray[i] = i*inc;
		}
	}





	/**
	 * The setHuristicDemandRange method set the value of the boolean which defines the cost/coupon factor to use in determining the
	 * the heuristic policy 
	 * @param level
	 */
	public void setHuristicDemandRange(double level){
		
		if(level < 0.3){
			this.heuristicLowDemand = true;
			this.heuristicMidDemand = false;
			this.heuristicHighDemand = false;
		} else if((0.3 <= level) && (level <= 0.6)){
			this.heuristicMidDemand = true;
			this.heuristicLowDemand = false;
			this.heuristicHighDemand = false;
		}
		else{
			this.heuristicHighDemand = true;
			this.heuristicMidDemand = false;
			this.heuristicLowDemand = false;
		}
		
	}


	public MDPHeuristicSearchSecuritisationDataContainer getHeuristicSearchDataContainer() {
		return heuristicSearchDataContainer;
	}


	public void setHeuristicSearchDataContainer(
			MDPHeuristicSearchSecuritisationDataContainer heuristicSearchDataContainer) {
		this.heuristicSearchDataContainer = heuristicSearchDataContainer;
	}
	
	

}
