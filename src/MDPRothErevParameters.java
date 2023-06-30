import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.BetaDistribution;

import cern.jet.random.engine.RandomEngine;


public class MDPRothErevParameters {
	
	
	public String ErevRothLearnerUpdateType = "Standard";//can be "Standard" or "Nicolaosen Variation";
	public String ErevRothProbabilityModel = "Standard";//can be "Standard" or "Gibbs-Boltzmann";
	public boolean periodByPeriodCalculationsErevRoth = true;
	public boolean identicalParameterSelection = true;
	public int numberOfIterationsErevRoth = 1000;
	public int numberOfDecisionEpochsErevRoth = 5;
	public double securitisationRateIncrementErevRoth = 0.05;//sliders
	public double securitisationRateMaxChangeErevRoth = 0.5;//sliders
	public int numberOfActionsErevRoth = (int) (1+ (2*(Parameters.securitisationRateMaxChangeErevRoth
			/Parameters.securitisationRateIncrementErevRoth)));
	public double GibbsBoltzmannParameterErevRoth = 1000;
	public double scalingParameterErevRoth = 9;
	public  double averageStartingRewardErevRoth = 100;
	public double genericExperimentationFactor = 0.75;
	public double maximumExperimentationFactor = 0.9;
	public double minimumExperimentationFactor = 0.1;
	public double  genericRecencyFactorErevRoth = 0.1;
	public double  minRecencyFactorErevRoth = 0.1;
	public double  maxRecencyFactorErevRoth = 0.75;
	public double loanMarketSentimentShareErevRoth = 0.5;
	public double betaDistributionAlphaGenericErevRoth = 2;
	public double betaDistributionBetaGenericErevRoth = 5;
	public double betaDistributionAlphaMinErevRoth = 1;
	public double betaDistributionBetaMaxErevRoth = 7;
	public double betaDistributionAlphaMaxErevRoth = 7;
	public double betaDistributionBetaMinErevRoth = 1;
	public int randomSeedErevRoth = 74974984;
	

	public MDPRothErevParameters(
			String ErevRothLearnerUpdateType, String ErevRothProbabilityModel, boolean periodByPeriodCalculationsErevRoth,
			boolean identicalParameterSelection, 	int numberOfIterations2, int numberOfDecisionEpochs2, int numberOfactions,
			double securitisationRateIncrementErevRoth, double securitisationRateMaxChangeErevRoth, 
			double GibbsBoltzmannParameterErevRoth, double genericExperimentationFactor, 
			double maximumExperimentationFactor, double minimumExperimentationFactor, 
			double genericRecencyFactorErevRoth, 
			double maxRecencyFactorErevRoth, double minRecencyFactorErevRoth, 
			double loanMarketSentimentShare,
			double betaDistributionAlphaGeneric, double betaDistributionBetaGeneric, double betaDistributionAlphaMin,
			double betaDistributionBetaMin, double betaDistributionAlphaMax, double betaDistributionBetaMax,
			double scalingParameterErevRoth, int randomSeedErevRoth){
		
		this.ErevRothLearnerUpdateType = ErevRothLearnerUpdateType;
		this.ErevRothProbabilityModel = ErevRothProbabilityModel;
		this.periodByPeriodCalculationsErevRoth = periodByPeriodCalculationsErevRoth;
		this.identicalParameterSelection = identicalParameterSelection;
		this.numberOfIterationsErevRoth = numberOfIterations2;
		this.numberOfDecisionEpochsErevRoth = numberOfDecisionEpochs2;
		this.securitisationRateIncrementErevRoth = securitisationRateIncrementErevRoth;//
		this.securitisationRateMaxChangeErevRoth = securitisationRateMaxChangeErevRoth;//
		this.numberOfActionsErevRoth = numberOfactions;
		this.GibbsBoltzmannParameterErevRoth = GibbsBoltzmannParameterErevRoth;
		this.genericExperimentationFactor = genericExperimentationFactor;
		this.maximumExperimentationFactor = maximumExperimentationFactor;
		this.minimumExperimentationFactor = minimumExperimentationFactor;
		this.genericRecencyFactorErevRoth = genericRecencyFactorErevRoth;
		this.maxRecencyFactorErevRoth = maxRecencyFactorErevRoth;
		this.minRecencyFactorErevRoth = minRecencyFactorErevRoth;
		this.loanMarketSentimentShareErevRoth = loanMarketSentimentShare;
		this.betaDistributionAlphaGenericErevRoth = betaDistributionAlphaGeneric;
		this.betaDistributionBetaGenericErevRoth = betaDistributionBetaGeneric;
		this.betaDistributionAlphaMinErevRoth = betaDistributionAlphaMin;
		this.betaDistributionBetaMaxErevRoth = betaDistributionBetaMax;
		this.betaDistributionAlphaMaxErevRoth = betaDistributionAlphaMax;
		this.betaDistributionBetaMinErevRoth = betaDistributionBetaMin;
		this.scalingParameterErevRoth = scalingParameterErevRoth;
		this.randomSeedErevRoth = randomSeedErevRoth;
		
	}
	
	
	
}
