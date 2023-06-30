import java.awt.Color;
import java.util.Random;

import org.apache.commons.math3.distribution.BetaDistribution;

import jas.events.ISimEventListener;
import jas.plot.IColored;


public abstract class EconomicAgent implements IColored, ISimEventListener{
	
	
	public static double heatAbsorptionFactor = 20.0;

	public static final int REPRODUCE = 0;
	public static final int CHECK_LIFE = 1;
	
	public static final int AGENT_REPRODUCTION_FACTOR = 4;
	public static final int AGENT_LIVING_FACTOR = -10;

	private int x, y;
	protected double albedo;//this is a reflection coefficient derived from the Latin albedo "whiteness" (or reflected sunlight)
	//the idea behind its use at the abstract Economic Agent class level is to reflect life
	private GeoEconoPoliticalSpace world;
	private int residualLifeTime, lifeTime;
	private Color colour;

	String EntityID;
	
	String temperament;
	
	QLearningInvestorCalculationEngine rlCalculationEngine;
	SolverAlgorithm solverAlgorithm;
	
	MDPModelInputParameters mdpModelInputParameters;
	MDPHeuristicDecisionParameters heuristicDecisionModelParameters;
	MDPRothErevParameters ErevRothDecisionModelParameters;
	MDPPortfolioChoiceAction bestMDPPolicy = null;
	TransitionProbabilitiesEngine transitionProbabilitiesEngine;
	ErevRothLearner erevRothLearner;
	ErevRothProblem erProblem;
	MDPErevRothSolver erSolver;
	
	public String ErevRothLearnerUpdateType = "Standard";
	public String ProbabilityModel = "Standard";
	public int numberOfActions = 30;
	public int numberOfIterations = 1000;
	public double scalingParameter = 9;
	public double experimentationFactor = 0.49;
	public double recencyFactor = 0.1;
	public double averageStartingReward = 100;
	double alphaBDistribution = 2;
	double betaBDistribution = 5;
	double GibbsBoltzmannParameter = 1000;

	public ActionList actionsDomainErevRoth = new ActionList();

	
	
	


	
	

	@Override
	public Color getColor() {
		int temp = (int) Math.max(0, Math.min(albedo * 255.0, 254));
		return new Color(temp, temp, temp);
	}
	
	public void setColour(Color clr) {
		this.colour = clr;
	}
	
	
	public Color getColour() {
		return colour;
	}

	
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}


	/* (non-Javadoc)
	 * @see jas.events.ISimEventListener#performAction(int)
	 */
	@Override
	public void performAction(int actionId) {
		switch (actionId)
		{
			case REPRODUCE:
				reproduce(); break;
			case CHECK_LIFE:
				checkLife(); break;
		}

	}

	/**
	 * @return
	 */
	public double getAlbedo() {
		return albedo;
	}
	
	
	public void die(){
		world.agentLayer.set(x, y, null);
		world.agentsPopulation.removeSafe(this);
	}
	
	
	public abstract void checkLife();
	
	public abstract void reproduce();

	
	private void setEntityID(){
	}
	
	public String getEntityID(){
		return EntityID;
	}
	
	/**
	 * set the parameters to be used for re-enforcement learning
	 * @param RLParams
	 */
	public void setMDPModelParameters(MDPModelInputParameters RLParams){
		this.mdpModelInputParameters = RLParams;
//		this.decisionAnalysisPeriodEndString = RLParams.getDecisionAnalysisPeriodEndString();
//		this.portfolioWeightChoiceModel = RLParams.isPortfolioWeightChoiceModel();
//		this.shortSelling = RLParams.isShortSelling();	
//		this.stochasticStateTransitions = RLParams.isStochasticStateTransitions();
//		this.linearCostFunction = RLParams.isLinearCostFunction();
//		this.riskFreeRate = RLParams.getRiskFreeRate();
//		this.linearfactor = RLParams.getLinearfactor(); 
//		this.quadraticfactor = RLParams.getQuadraticfactor();
//		this.assetWieghtIncrements = RLParams.getAssetWieghtIncrements(); 
//		this.changeInWeightIncrement = RLParams.getChangeInWeightIncrement(); 
//		this.maximumPermissbleChangeInWeight = RLParams.getMaximumPermissbleChangeInWeight(); 
//		this.numberOfEpisodes = RLParams.getNumberOfEpisodes();
//		this.epsilonError = RLParams.getEpsilonError();
//		this.gammaDiscountFactor = RLParams.getGammaDiscountFactor(); 
//		this.accuracyThreshold =  RLParams.getAccuracyThreshold();
	}

	
	public void updatetemperament(String value){
		this.temperament = value;
	}
	
	
	public ActionList getActionsDomainErevRoth() {
		return actionsDomainErevRoth;
	}

	public void setActionsDomainErevRoth(ActionList actionsDomainErevRoth) {
		this.actionsDomainErevRoth = actionsDomainErevRoth;
	}
	
	public void setMDPRothErevParameters(MDPRothErevParameters ErevRothDMParameters){
		this.ErevRothDecisionModelParameters = ErevRothDMParameters;
	}




}
