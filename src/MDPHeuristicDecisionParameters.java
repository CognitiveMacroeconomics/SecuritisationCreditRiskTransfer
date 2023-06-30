
public class MDPHeuristicDecisionParameters {
	
	private double hueristicCostConstantLowDemand;
	private double hueristicCostConstantMidDemand;
	private double hueristicCostConstantHighDemand;
	private double contractRate;
	private double depositRate;
	private boolean periodByPeriodCalculationsHeuristic;
	private int numberOfIterationsHeuristic;
	private int numberOfDecisionEpochsHeuristic;
	private double securitisationRateIncrement;
	private double greedParameterIncrement;
	private double genericBias;
	private double maximumBias;
	private double loanMarketSentimentShare;
	private double minimumBias;
	private boolean IdenticalStateSelectionBias;
	private double betaDistributionAlphaGeneric;
	private double betaDistributionBetaGeneric;
	private double betaDistributionAlphaMin;
	private double betaDistributionBetaMin;
	private double betaDistributionAlphaMax;
	private double betaDistributionBetaMax;
	private double  recoveryRateOnCreditAsset;
	
	
	public MDPHeuristicDecisionParameters(
			boolean periodByPeriodCalculations, int numberOfIterations2,
			int numberOfDecisionEpochs2, boolean identicalStateSelectionBias,
			double securitisationRateIncrement, double greedParameterIncrement,
			double genericBias, double maximumBias, double minimumBias, double loanMarketSentimentShare,
			double hueristicCostConstantLowDemand, double hueristicCostConstantMidDemand, double hueristicCostConstantHighDemand,
			double betaDistributionAlphaGeneric, double betaDistributionBetaGeneric, double betaDistributionAlphaMin,
			double betaDistributionBetaMin, double betaDistributionAlphaMax, double betaDistributionBetaMax,
			double  recoveryRateOnCreditAsset) {
		// TODO Auto-generated method stub
		this.periodByPeriodCalculationsHeuristic = periodByPeriodCalculations;
		this.numberOfIterationsHeuristic = numberOfIterations2;
		this.numberOfDecisionEpochsHeuristic = numberOfDecisionEpochs2;
		this.setSecuritisationRateIncrement(securitisationRateIncrement);//
		this.greedParameterIncrement = greedParameterIncrement;
		this.genericBias = genericBias;
		this.maximumBias = maximumBias;
		this.IdenticalStateSelectionBias = identicalStateSelectionBias;
		this.minimumBias = minimumBias;
		this.loanMarketSentimentShare = loanMarketSentimentShare;
		this.hueristicCostConstantLowDemand = hueristicCostConstantLowDemand;
		this.hueristicCostConstantMidDemand = hueristicCostConstantMidDemand;
		this.hueristicCostConstantHighDemand = hueristicCostConstantHighDemand;
		this.betaDistributionAlphaGeneric = betaDistributionAlphaGeneric;
		this.betaDistributionBetaGeneric = betaDistributionBetaGeneric;
		this.betaDistributionAlphaMin = betaDistributionAlphaMin;
		this.betaDistributionBetaMin = betaDistributionBetaMin;
		this.betaDistributionAlphaMax = betaDistributionAlphaMax;
		this.betaDistributionBetaMax = betaDistributionBetaMax;
		this.setRecoveryRateOnCreditAsset(recoveryRateOnCreditAsset);
		
	}
	
	
	

	public double getSecuritisationRateIncrement() {
		return securitisationRateIncrement;
	}


	public void setSecuritisationRateIncrement(double securitisationRateIncrement) {
		this.securitisationRateIncrement = securitisationRateIncrement;
	}


	public double getHueristicCostConstantLowDemand() {
		return hueristicCostConstantLowDemand;
	}


	public void setHueristicCostConstantLowDemand(
			double hueristicCostConstantLowDemand) {
		this.hueristicCostConstantLowDemand = hueristicCostConstantLowDemand;
	}


	public double getHueristicCostConstantMidDemand() {
		return hueristicCostConstantMidDemand;
	}


	public void setHueristicCostConstantMidDemand(
			double hueristicCostConstantMidDemand) {
		this.hueristicCostConstantMidDemand = hueristicCostConstantMidDemand;
	}


	public double getHueristicCostConstantHighDemand() {
		return hueristicCostConstantHighDemand;
	}


	public void setHueristicCostConstantHighDemand(
			double hueristicCostConstantHighDemand) {
		this.hueristicCostConstantHighDemand = hueristicCostConstantHighDemand;
	}


	public double getContractRate() {
		return contractRate;
	}


	public void setContractRate(double contractRate) {
		this.contractRate = contractRate;
	}


	public double getDepositRate() {
		return depositRate;
	}


	public void setDepositRate(double depositRate) {
		this.depositRate = depositRate;
	}


	public boolean isPeriodByPeriodCalculationsHeuristic() {
		return periodByPeriodCalculationsHeuristic;
	}


	public void setPeriodByPeriodCalculationsHeuristic(
			boolean periodByPeriodCalculationsHeuristic) {
		this.periodByPeriodCalculationsHeuristic = periodByPeriodCalculationsHeuristic;
	}


	public int getNumberOfIterationsHeuristic() {
		return numberOfIterationsHeuristic;
	}


	public void setNumberOfIterationsHeuristic(int numberOfIterationsHeuristic) {
		this.numberOfIterationsHeuristic = numberOfIterationsHeuristic;
	}


	public int getNumberOfDecisionEpochsHeuristic() {
		return numberOfDecisionEpochsHeuristic;
	}


	public void setNumberOfDecisionEpochsHeuristic(
			int numberOfDecisionEpochsHeuristic) {
		this.numberOfDecisionEpochsHeuristic = numberOfDecisionEpochsHeuristic;
	}


	public double getGreedParameterIncrement() {
		return greedParameterIncrement;
	}


	public void setGreedParameterIncrement(double greedParameterIncrement) {
		this.greedParameterIncrement = greedParameterIncrement;
	}


	public double getGenericBias() {
		return genericBias;
	}


	public void setGenericBias(double genericBias) {
		this.genericBias = genericBias;
	}


	public double getMaximumBias() {
		return maximumBias;
	}


	public void setMaximumBias(double maximumBias) {
		this.maximumBias = maximumBias;
	}


	public double getLoanMarketSentimentShare() {
		return loanMarketSentimentShare;
	}


	public void setLoanMarketSentimentShare(double loanMarketSentimentShare) {
		this.loanMarketSentimentShare = loanMarketSentimentShare;
	}


	public double getMinimumBias() {
		return minimumBias;
	}


	public void setMinimumBias(double minimumBias) {
		this.minimumBias = minimumBias;
	}


	public boolean isIdenticalStateSelectionBias() {
		return IdenticalStateSelectionBias;
	}


	public void setIdenticalStateSelectionBias(boolean identicalStateSelectionBias) {
		IdenticalStateSelectionBias = identicalStateSelectionBias;
	}




	public double getBetaDistributionAlphaGeneric() {
		return betaDistributionAlphaGeneric;
	}




	public void setBetaDistributionAlphaGeneric(double betaDistributionAlphaGeneric) {
		this.betaDistributionAlphaGeneric = betaDistributionAlphaGeneric;
	}




	public double getBetaDistributionBetaGeneric() {
		return betaDistributionBetaGeneric;
	}




	public void setBetaDistributionBetaGeneric(double betaDistributionBetaGeneric) {
		this.betaDistributionBetaGeneric = betaDistributionBetaGeneric;
	}




	public double getBetaDistributionAlphaMin() {
		return betaDistributionAlphaMin;
	}




	public void setBetaDistributionAlphaMin(double betaDistributionAlphaMin) {
		this.betaDistributionAlphaMin = betaDistributionAlphaMin;
	}




	public double getBetaDistributionBetaMin() {
		return betaDistributionBetaMin;
	}




	public void setBetaDistributionBetaMin(double betaDistributionBetaMin) {
		this.betaDistributionBetaMin = betaDistributionBetaMin;
	}




	public double getBetaDistributionAlphaMax() {
		return betaDistributionAlphaMax;
	}




	public void setBetaDistributionAlphaMax(double betaDistributionAlphaMax) {
		this.betaDistributionAlphaMax = betaDistributionAlphaMax;
	}




	public double getBetaDistributionBetaMax() {
		return betaDistributionBetaMax;
	}




	public double getRecoveryRateOnCreditAsset() {
		return recoveryRateOnCreditAsset;
	}




	public void setRecoveryRateOnCreditAsset(double recoveryRateOnCreditAsset) {
		this.recoveryRateOnCreditAsset = recoveryRateOnCreditAsset;
	}




	public void setBetaDistributionBetaMax(double betaDistributionBetaMax) {
		this.betaDistributionBetaMax = betaDistributionBetaMax;
	}
	
	
	public String toString(){
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
				+ "this.hueristicCostConstantLowDemand "
				+ "this.hueristicCostConstantMidDemand "
				+ "this.hueristicCostConstantHighDemand "
				+ "this.betaDistributionAlphaGeneric "
				+ "this.betaDistributionBetaGeneric "
				+ "this.betaDistributionAlphaMin "
				+ "this.betaDistributionBetaMin "
				+ "this.betaDistributionAlphaMax "
				+ "this.betaDistributionBetaMax "
				+ " " + this.periodByPeriodCalculationsHeuristic + " " + this.numberOfIterationsHeuristic 
				+ " " + this.numberOfDecisionEpochsHeuristic + " " + this.greedParameterIncrement
				+ " " + this.genericBias+ " " + this.maximumBias + " " + this.IdenticalStateSelectionBias 
				+ " " + this.minimumBias+ " " + this.loanMarketSentimentShare
				+ " " + this.hueristicCostConstantLowDemand + " " + this.hueristicCostConstantMidDemand
				+ " " + this.hueristicCostConstantHighDemand+ " " + this.betaDistributionAlphaGeneric
				+ " " + this.betaDistributionBetaGeneric+ " " + this.betaDistributionAlphaMin
				+ " " + this.betaDistributionBetaMin + " " + this.betaDistributionAlphaMax + " " 
				+ this.betaDistributionBetaMax);
		return discription;
	}

}
