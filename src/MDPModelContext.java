

import java.util.ArrayList;



public abstract class MDPModelContext{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<PortfolioAction> actionsSet = new ArrayList<PortfolioAction>();
	public ArrayList<MDPCapitalMarketsState> statesSet = new ArrayList<MDPCapitalMarketsState>();
	MDPStatesPathEngine statesGenerator;//states space
	public int episode;
	
	double discountfactor; //the discount factor at which future payoffs will be discounted 
	boolean shortSelling = false; //determines if short selling is permitted in the model. It is defaulted to False
	boolean linearCostFunction = false; //determines if the model uses a linear cost function. It is defaulted to False
	double linearfactor; //used as the constant parameter/multiplier for computing the linear transaction costs 
	double quadraticfactor; //used as the constant parameter/multiplier for computing the quadratic transaction costs
	double assetWieghtIncrements; //increment used to create the potfolio weights that are used to define the MDP states 
	double changeInWeightIncrement; //represents the rate at which changes can be made to portfolio weights 
	double maximumPermissbleChangeInWeight; //represents the maximum change in portfolio weights this will be the range from positive to negative
	MDPCapitalMarketsState currentState;
	PortfolioAction currentAction;
	boolean portfolioWieghtChoiceBoolean;
	
	
	public MDPModelContext(boolean shtSell, boolean lnCost, double astWghtIncr, 
			double cInWghtIncr, double maxPermChgInWght, double discntFactor, double lnCostFactor, double QuadCostFactor, boolean prtWghtChc){
		this.shortSelling = shtSell;
		this.linearCostFunction = lnCost;
		this.assetWieghtIncrements = astWghtIncr;
		this.changeInWeightIncrement = cInWghtIncr;
		this.maximumPermissbleChangeInWeight = maxPermChgInWght;
		this.discountfactor = discntFactor;
		this.linearfactor = lnCostFactor;
		this.quadraticfactor = QuadCostFactor;
		this.episode = 0;
		portfolioWieghtChoiceBoolean = prtWghtChc;
	}
	



	
	public void setCapitalMarketStateGenerator(MDPStatesPathEngine statesGen){
		this.statesGenerator = statesGen;
		
	}
	
	public boolean isPortfolioWieghtChoice(){
		return portfolioWieghtChoiceBoolean;
		
	}
	

	public abstract MDPCapitalMarketsState successorState(MDPCapitalMarketsState arg0, PortfolioAction arg1);
	
	public abstract void setActionsFullSet(ArrayList<PortfolioAction> list);
	
	public abstract MDPCapitalMarketsState defaultInitialState();
	
	public abstract ActionList getActionList(MDPCapitalMarketsState arg0);
	
	public abstract double getReward(MDPCapitalMarketsState arg0, MDPCapitalMarketsState arg1, PortfolioAction arg2);
	
	public abstract void setStatesFullSet(ArrayList<MDPCapitalMarketsState> states);
	
	public abstract ArrayList<?> getActionsPopulationSet();
	
	public abstract ArrayList<?> getStatesPopulationSet();
	
//	public abstract void setCurrentState();
	
	public abstract MDPCapitalMarketsState getCurrentState();
	
//	public abstract void setCurrentPolicy();
	
	public abstract PortfolioAction getCurrentPolicy();



	public abstract ArrayList<MDPCapitalMarketsState> getCurrentReachableStates(
			MDPCapitalMarketsState state, PortfolioAction policy);
	


}
