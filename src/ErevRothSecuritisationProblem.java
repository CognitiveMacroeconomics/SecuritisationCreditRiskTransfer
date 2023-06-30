import java.util.ArrayList;

import cern.jet.random.engine.RandomEngine;


public class ErevRothSecuritisationProblem extends ErevRothProblem{
	
	ArrayList<MortgageLoansState> MortgageLoansStates = new ArrayList<MortgageLoansState>();
	
	ErevRothAction lastAction;
	int numberOfEpochs;
	double initialSecuritisationRate;
	double lastSecuritisationRate;
	double newSecuritisationRate;
	double lastReward;
	double observedSecuritisationCost;
	public ActionList actionsDomainErevRoth = new ActionList();
	double[] capitalAccumulation;
	boolean quadraticSecCost;
	double stateSelectbias;
	double contractRate;
	double depositRate;
	double marketDemandImpliedYield;
	double marketDemand;
	private double asset;
	private double liability;
	private double regRatio;
	private double assetSurvivalRate;
	private double postResetSurvivalRate;
	protected double securitisationCostFactor;

	private double creditRecoveryRate;

	private double assetT;

	private double liabilityT;

	private boolean heuristicLowDemand;

	private boolean heuristicMidDemand;

	private boolean heuristicHighDemand;
	

	public ErevRothSecuritisationProblem(String updateType, String probModel,
			int A, int T,  int epochs, double gbp, double scal, double ef, double rf,
			double initProp, double alpha, double beta, RandomEngine randomEngine) {
		super(updateType, probModel, A, T, gbp, scal, ef, rf, initProp, alpha, beta, randomEngine);
		// TODO Auto-generated constructor stub
		this.numberOfEpochs = epochs;
		this.heuristicLowDemand = false;
		this.heuristicMidDemand = true;
		this.heuristicHighDemand = false;
		setMarketDemandImpliedYield();
	}

	
	
	
	public double getReward(int action, int time){
		double reward = 0;
		reward = computeSecuritisationActionCapitalInjection(action);
		return reward;
		
	}


	
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	/**
	 * Note that I use the beta distribution as a means of 1: adhering to Basel II LGD conventions
	 * and 2 to have some level of correlation between losses as defined by economic states of loans
	 */
	public double computeSecuritisationActionCapitalInjection(int action){
		this.setLastSecuritisationRate(this.getNewSecuritisationRate());
		double avarageR = 0;
		double R = 0;
		MortgageLoansState S_0;
		MortgageLoansState S_t;
		int epochs = this.numberOfEpochs;
		double estimatedSecuritisationCost = 0;
		double prob;
		double betaProb;
		double expectedReturn = this.contractRate;
//		double action_t = Rounding.roundTwoDecimals(this.lastSecuritisationRate 
//				+ this.actionsDomainErevRoth.getActionAt(action-1).propertiesDouble[0]);
		double action_t = Rounding.roundTwoDecimals(this.actionsDomainErevRoth.getActionAt(action-1).propertiesDouble[0]);
//		System.out.print(" action_t = "+action_t);
		//set bounds for securitisation rate 0 to 1
//		if(action_t > 1.00){
//			action_t = this.lastSecuritisationRate;
//		} else if(action_t < 0.00){
//			action_t = this.lastSecuritisationRate;
//		}
//		System.out.print(" action_t mod = "+action_t);
		
		this.setNewSecuritisationRate(action_t);
		if(isQuadraticSecCost()){
			estimatedSecuritisationCost = (securitisationCostFactor + marketDemandImpliedYield*0.33) * (action_t 
					+  Math.pow(action_t, 2));
		}
		else {
			estimatedSecuritisationCost = (securitisationCostFactor + marketDemandImpliedYield*0.33) * action_t;
		}
//		System.out.println(" action_t mod = "+action_t);
//		System.out.print(" estimatedSecuritisationCost = "+estimatedSecuritisationCost);
		prob = this.stateProbGen.nextDouble();
		
		if(prob < (1 - this.stateSelectbias)){
			S_0 = this.MortgageLoansStates.get(0);
		} else {
			S_0 = this.MortgageLoansStates.get(1);
		}
		
		
//		for(int t = 1; t <= epochs; t++){
//			//prob = stateSelectRandomGen.nextDouble();
//			betaProb = defaultProbGen.inverseCumulativeProbability(prob);
//			if(betaProb < (this.stateSelectbias)){
//				S_t = this.MortgageLoansStates.get(0);
//			} else {
//				S_t = this.MortgageLoansStates.get(1);
//			}
//			expectedReturn = S_t.getExpectedReturn();
//			//as with the standard securitisation model we compute -M(5) = [1-esp*(1-alpha)]A(5) - L(5). 
////			setLiabilitiesT(this.liability);
////			setAssetsT(this.asset, expectedReturn, estimatedSecuritisationCost);
////			regCapExp = 1 - (this.regRatio*(1-action_t));
//			R +=  computeInjectionsT(this.asset, this.liability, expectedReturn, estimatedSecuritisationCost, action_t);
////			R +=  		regCapExp*this.assetT - this.liabilityT;
//		}
		R =  computeInjectionsT(this.asset, this.liability, expectedReturn, estimatedSecuritisationCost, action_t);

//		avarageR = Rounding.roundFourDecimals(R/epochs);		
		avarageR = Rounding.roundFourDecimals(R);		
		
		return avarageR;
	}
	
	

	public void setMarketDemandSettings(double demand){
		this.setMarketDemandRange(demand);
		this.setMarketDemandImpliedYield();
//		System.out.println("demand = "+demand);
		
	}
	public void setMarketDemandImpliedYield(){
		if(this.heuristicHighDemand){
			marketDemandImpliedYield = 0.0;
//			marketDemandImpliedYield = 0.044;
		}
		else if(this.heuristicMidDemand){
//			marketDemandImpliedYield = 0.0987;
			marketDemandImpliedYield = 0.50;
		} else {
			marketDemandImpliedYield = 0.75;
//			marketDemandImpliedYield = 0.15;
		}
//		System.out.println("marketDemandImpliedYield = "+marketDemandImpliedYield);
		
	}
	
	/**
	 * The setHuristicDemandRange method set the value of the boolean which defines the cost/coupon factor to use in determining the
	 * the heuristic policy 
	 * @param level
	 */
	public void setMarketDemandRange(double level){
		
		if(level < 0.25){
			this.heuristicLowDemand = true;
			this.heuristicMidDemand = false;
			this.heuristicHighDemand = false;
		} else if((0.25 <= level) && (level < 0.50)){
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


	public double computeInjectionsT(double A, double L, double expRet, double secCost, double projSecRate){
		int epochs = this.numberOfEpochs;
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
				if(i>2){
					survivalRateArray[i] = this.postResetSurvivalRate;
				}

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
		sumAM1 = assetArray[0]*XProductM1[epochs-1];
		
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
	
	
	public void setAssetsT(double A, double expRet, double secRate, double estimatedSecuritisationCost){
		int epochs = this.numberOfEpochs;
		this.assetT = A;
		for(int i = 1; i <= epochs; i++){
			this.assetT *= (1+expRet*this.assetSurvivalRate);
			this.assetT *= (1-this.regRatio);
			this.assetT += secRate*A*(1-estimatedSecuritisationCost);
		}
	}
	
	public void setLiabilitiesT(double L){
		int epochs = this.numberOfEpochs;
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
	
	
	private boolean isQuadraticSecCost() {
		// TODO Auto-generated method stub
		return quadraticSecCost;
	}

	
	public void setQuadraticSecCost(boolean secCostBool){
		this.quadraticSecCost = secCostBool;
	}


	public void setContractRate(double cr){
		this.contractRate = cr;
	}
	
	public void setDepositRate(double dr){
		this.depositRate = dr;
	}
	
	public void setCreditRecoveryRate(double cRR){
		this.creditRecoveryRate = cRR;
	}
	

	public void updateLearningEnvironmentData(double assets, double liabilities, double injections, double capRatio,
			double contRate, double depRate, double recRate, double suvRate, double suvRatePR){
		setAssets(assets);
		setLiabilities(liabilities);
		setAverageStartingReward(injections);
		setRegRatio(capRatio);
		setContractRate(contRate);
		setDepositRate(depRate);
		setCreditRecoveryRate(recRate);
		setAssetSurvivalRate(suvRate);
		setPostRestSurvivalRate(suvRatePR);
		updateMortgageLoanStates();
	}

	private void setPostRestSurvivalRate(double srpr){
		this.postResetSurvivalRate = srpr;
	}
	
	
	public void updateMortgageLoanStates(){
		double erDef = this.creditRecoveryRate*this.contractRate;
		
		if(this.MortgageLoansStates.isEmpty()){
			this.MortgageLoansStates.add(new MortgageLoansState(0, erDef));
			this.MortgageLoansStates.add(new MortgageLoansState(1, this.contractRate));
		} else {
			this.MortgageLoansStates.get(0).updateStateReturn(erDef);
			this.MortgageLoansStates.get(1).updateStateReturn(this.contractRate);
		}
		
	}





	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public ErevRothAction getLastAction() {
		return lastAction;
	}

	public void setLastAction(ErevRothAction lastAction) {
		this.lastAction = lastAction;
	}

	public double getLastSecuritisationRate() {
		return lastSecuritisationRate;
	}

	public void setLastSecuritisationRate(double lastSecuritisationRate) {
		this.lastSecuritisationRate = lastSecuritisationRate;
	}

	public double getNewSecuritisationRate() {
		return newSecuritisationRate;
	}

	public void setNewSecuritisationRate(double newSecuritisationRate) {
		this.newSecuritisationRate = newSecuritisationRate;
	}


	public double getInitialSecuritisationRate() {
		return initialSecuritisationRate;
	}

	public void setInitialSecuritisationRate(double newSecuritisationRate) {
		this.initialSecuritisationRate = newSecuritisationRate;
		setLastSecuritisationRate(this.initialSecuritisationRate);
		setNewSecuritisationRate(this.initialSecuritisationRate);
	}
	
	public double getLastReward() {
		return lastReward;
	}

	public void setLastReward(double lastReward) {
		this.lastReward = lastReward;
	}

	public double getObservedSecuritisationCost() {
		return observedSecuritisationCost;
	}

	public void setObservedSecuritisationCost(double observedSecuritisationCost) {
		this.observedSecuritisationCost = observedSecuritisationCost;
	}
	
	public void setObservedMBSMarketDemand(double demand){
		this.marketDemand = demand;
	}

	public ActionList getActionsDomainErevRoth() {
		return actionsDomainErevRoth;
	}

	public void setActionsDomainErevRoth(ActionList actionsDomainErevRoth) {
		this.actionsDomainErevRoth = actionsDomainErevRoth;
	}
	
	
	public void setLoanMarketStateBias(double bias){
		this.stateSelectbias = bias;
	}
	
	public double getSecuritisationCostFactor() {
		return securitisationCostFactor;
	}

	public void setSecuritisationCostFactor(double securitisationCostFactor) {
		this.securitisationCostFactor = securitisationCostFactor;
	}
	
	 
}
