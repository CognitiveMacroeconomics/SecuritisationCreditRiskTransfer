import java.util.ArrayList;


public class QLearningInvestorCalculationEngine {

	private static final long serialVersionUID = 1L;
	private static int ENVIRONMENT_ID = 0;
	private int environmentID;
	

	ArrayList<PortfolioAssetsState> portfolioStates;//collection of PortfolioAssetsState objects 
	//used in creating the MDP states
	ArrayList<MDPPortfolioChoiceAction> actionsSpace; //collection of all actions available to chose from 
	ArrayList<MDPPortfolioChoiceAction> pastActionsTaken; //collection of all actions chosen in the past 
	ArrayList<PortfolioAssetsState> pastStates;
	
	ArrayList<MDPCapitalMarketsState> MDPStates;//collection of PortfolioAssetsState objects 
	//used in creating the MDP states
	ArrayList<MDPCapitalMarketsState> pastMDPStates;
	
	ArrayList<QValue> qValues;

	PortfolioAssetsState currentState;
	MDPPortfolioChoiceAction currentAction;
	MDPCapitalMarketsState currentMDPState;
	

	boolean shortSelling = false; //determines if short selling is permitted in the model. It is defaulted to False
	boolean linearCostFunction = false; //determines if the model uses a linear cost function. It is defaulted to False
	boolean portfolioWeightChoiceModel;

	double linearfactor; //used as the constant parameter/multiplier for computing the linear transaction costs 
	double quadraticfactor; //used as the constant parameter/multiplier for computing the quadratic transaction costs
	double maximumPermissbleChangeInWeight; //represents the maximum change in portfolio weights this will be the range from positive to negative
	private double expectedEquityLoss;
	private double expectedCreditDefault;
	private double liabilityGrowthRate;
	private MDPModelInputParameters mdpModelInputParameters;
	private int decisionHorizon;
	private int resetPeriod;
	
	

	
	
	
	QLearningInvestorCalculationEngine(MDPModelInputParameters mdpModelInputParameters, double expectedEquityLoss, 
			double expectedCreditDefault, double liabilityGrowthRate, int decisionHorizon, int resetPeriod){
		portfolioStates = new ArrayList<PortfolioAssetsState>(); 
		pastStates = new ArrayList<PortfolioAssetsState>();
		MDPStates = new ArrayList<MDPCapitalMarketsState>(); 
		pastMDPStates = new ArrayList<MDPCapitalMarketsState>();
		actionsSpace = new ArrayList<MDPPortfolioChoiceAction>();
		pastActionsTaken = new ArrayList<MDPPortfolioChoiceAction>();
		this.setMdpModelInputParameters(mdpModelInputParameters);
		this.shortSelling = mdpModelInputParameters.isShortSelling();
		this.linearCostFunction = mdpModelInputParameters.isLinearCostFunction();
		this.portfolioWeightChoiceModel = mdpModelInputParameters.isPortfolioWeightChoiceModel();
		this.linearfactor = mdpModelInputParameters.getLinearfactor();
		this.quadraticfactor = mdpModelInputParameters.getQuadraticfactor();
		this.maximumPermissbleChangeInWeight = mdpModelInputParameters.getMaximumPermissbleChangeInWeight();
		this.expectedEquityLoss = expectedEquityLoss;
		this.expectedCreditDefault = expectedCreditDefault;
		this.liabilityGrowthRate = liabilityGrowthRate;
		this.decisionHorizon = decisionHorizon;
		this.resetPeriod = resetPeriod;
	}
	
	
	
	/**
	 * Updates the states space housed in the calculation engine with new states space given by the agent
	 * @param States
	 */
	public void updateStatesSpace(ArrayList<PortfolioAssetsState> States){
		this.portfolioStates.clear();
		this.portfolioStates.addAll(States);
	}
	
	
	/**
	 * Updates the current state of the economy stored in the calculation engine with the new economic state given by economy to the agent
	 * @param State
	 */
	public void setCurrentState(PortfolioAssetsState State){
		this.currentState = State;
//		this.pastStates.add(this.currentState);
//		System.out.println(this.currentState.toString());
		double eqtyWeight = this.currentAction.getEquityAssetWeightChoice();
		double crdtWeight = this.currentAction.getCreditAssetWeightChoice();
		double cshWeight = this.currentAction.getCashAssetWeightChoice();
		int eqtyDir = this.currentState.getProperties()[0];
		int crdtDir = this.currentState.getProperties()[1];
		int cshDir = this.currentState.getProperties()[2];
		if(this.MDPStates.isEmpty() == false){
			for(MDPCapitalMarketsState mdpstate : MDPStates){
				int eD = mdpstate.getPortfolioAssetState().getProperties()[0];
				int cdD = mdpstate.getPortfolioAssetState().getProperties()[1];
				int cD = mdpstate.getPortfolioAssetState().getProperties()[2];
				double wE = mdpstate.getEquityAssetWeight();
				double wCd = mdpstate.getCreditAssetWeight();
				double wCh = mdpstate.getCashAssetWeight();
				if((eqtyDir == eD) && (crdtDir == cdD) && (cshDir == cD) && (eqtyWeight == wE) && (crdtWeight == wCd) && (cshWeight == wCh)){
					this.currentMDPState = mdpstate;
				}
			}
		}
	}
	
	
	
	
	
	/**
	 * Updates the states space housed in the calculation engine with new states space given by the agent
	 * @param States
	 */
	public void updateActionsSpace(ArrayList<MDPPortfolioChoiceAction> actions){
		if(! this.actionsSpace.isEmpty()){
			this.actionsSpace.clear();
		}
		this.actionsSpace.addAll(actions);
	}
	
	
	
	
	/**
	 * Updates the current state of the economy stored in the calculation engine with the new economic state given by economy to the agent
	 * @param State
	 */
	public void setCurrentAction(MDPPortfolioChoiceAction action){
		this.currentAction = action;
		this.pastActionsTaken.add(this.currentAction);
//		System.out.println(((MDPPortfolioChoiceAction) this.currentAction).label());
	}

	/**
	 * The following method defines the reward function.
	 * Note Rewards are computed as a function:
	 *  (w(i)*(rt(i) - rt-1(i)) + w(j)*(rt(j) - rt-1(j)) + w(k)*(rt(k) - rt-1(k))) - transactionCost(i,j,k)
	 * The method takes a boolean argument representing whether a linear cost function or a quadratic cost function is used as well as a 
	 * double array consisting of all the weight changes represented by an asset allocation action. 
	 * 
	 * The method check to confirm if the cost function is linear or quadratic
	 * then computes the transaction costs as a sum of transaction costs of each asset
	 * 
	 * Note: The linear cost function factor is included in the quadratic cost function arbitrarily. There is no particular reason for this
	 * however for the most part it will be assumed they are identical
	 * @param lnCost
	 * @param deltaWeight
	 * @return
	 */
	public double getReward(PortfolioAssetsState arg0, PortfolioAssetsState arg1, MDPPortfolioChoiceAction arg2, MDPPortfolioChoiceAction arg3) {
		// TODO Auto-generated method stub
		double reward = 0;
		if(arg0 instanceof PortfolioAssetsState && arg1 instanceof PortfolioAssetsState 
				&& arg2 instanceof MDPPortfolioChoiceAction	 && arg3 instanceof MDPPortfolioChoiceAction){
			double eqtyWeightChange = 
					arg3.getEquityAssetWeightChoice() - arg2.getEquityAssetWeightChoice();
			double crdtWeightChange = 
					arg3.getCreditAssetWeightChoice() - arg2.getCreditAssetWeightChoice();
			double cashWeightChange = 
					arg3.getCashAssetWeightChoice() - arg2.getCashAssetWeightChoice();
			double[] weightChanges = {eqtyWeightChange, crdtWeightChange, cashWeightChange};
			reward = (
						arg3.getEquityAssetWeightChoice() * (((1+arg1.getEquityAssetExpectedReturn())
						- (1+arg0.getEquityAssetExpectedReturn())))
						
						+ arg3.getCreditAssetWeightChoice() 
						* (((1+arg1.getCreditAssetExpectedReturn()*this.expectedCreditDefault)
						- (1+arg0.getCreditAssetExpectedReturn()*this.expectedCreditDefault)))
						
						+ arg3.getCashAssetWeightChoice() * (((1+arg1.getCashAssetExpectedReturn())
						)))
						- getTransactionCost(this.linearCostFunction, weightChanges);
		}
//		System.out.println("reward "+ reward);
		return reward;
	}



	/**
	 * The following method defines the reward function.
	 * Note Rewards are computed as a function:
	 *  (w(i)*(rt(i) - rt-1(i)) + w(j)*(rt(j) - rt-1(j)) + w(k)*(rt(k) - rt-1(k))) - transactionCost(i,j,k)
	 * The method takes a boolean argument representing whether a linear cost function or a quadratic cost function is used as well as a 
	 * double array consisting of all the weight changes represented by an asset allocation action. 
	 * 
	 * The method check to confirm if the cost function is linear or quadratic
	 * then computes the transaction costs as a sum of transaction costs of each asset
	 * 
	 * Note: The linear cost function factor is included in the quadratic cost function arbitrarily. There is no particular reason for this
	 * however for the most part it will be assumed they are identical
	 * @param lnCost
	 * @param deltaWeight
	 * @return
	 */
	public double getReward(PortfolioAssetsState arg0, PortfolioAssetsState arg1, MDPPortfolioChoiceAction arg2) {
		// TODO Auto-generated method stub
		double reward = 0;
		if(arg0 instanceof PortfolioAssetsState && arg1 instanceof PortfolioAssetsState 
				&& arg2 instanceof MDPPortfolioChoiceAction){
			double eqtyWeightChange = arg2.getEquityAssetWeightChoice();
			double crdtWeightChange = arg2.getCreditAssetWeightChoice();
			double cashWeightChange = arg2.getCashAssetWeightChoice();
			double[] weightChanges = {eqtyWeightChange, crdtWeightChange, cashWeightChange};
			
				reward = (
						arg2.getEquityAssetWeightChoice() * (((1+arg1.getEquityAssetExpectedReturn())
						- (1+arg0.getEquityAssetExpectedReturn())))
						
						+ arg2.getCreditAssetWeightChoice() 
						* (((1+arg1.getCreditAssetExpectedReturn()*this.expectedCreditDefault)
						- (1+arg0.getCreditAssetExpectedReturn()*this.expectedCreditDefault)))
						
						+ arg2.getCashAssetWeightChoice() * (((1+arg1.getCashAssetExpectedReturn())
						)))
						- getTransactionCost(this.linearCostFunction, weightChanges);
		}
//		System.out.println("reward "+ reward);
		return reward;
	}

	

	/**
	 * The following method defines the utility function. This is computed as the surplus earned over the decision horizon
	 * Note Rewards are computed as a function:
	 *  (w(i)*(rt(i)) + w(j)*(rt(j))) + w(k)*(rt(k))) - transactionCost(i,j,k)
	 * The method takes a boolean argument representing whether a linear cost function or a quadratic cost function is used as well as a 
	 * double array consisting of all the weight changes represented by an asset allocation action. 
	 * 
	 * The method check to confirm if the cost function is linear or quadratic
	 * then computes the transaction costs as a sum of transaction costs of each asset
	 * 
	 * Note: The linear cost function factor is included in the quadratic cost function arbitrarily. There is no particular reason for this
	 * however for the most part it will be assumed they are identical
	 * @param lnCost
	 * @param deltaWeight
	 * @return
	 */
	public double getFundingSurplusUtility(PortfolioAssetsState sPrime, MDPPortfolioChoiceAction startAction, MDPPortfolioChoiceAction newAction) {
		// TODO Auto-generated method stub
		double surplus = 0;
		if(sPrime instanceof PortfolioAssetsState
				&& startAction instanceof MDPPortfolioChoiceAction	 && newAction instanceof MDPPortfolioChoiceAction){
			double eqtyWeightChange = 
					newAction.getEquityAssetWeightChoice() - startAction.getEquityAssetWeightChoice();
			double crdtWeightChange = 
					newAction.getCreditAssetWeightChoice() - startAction.getCreditAssetWeightChoice();
			double cashWeightChange = 
					newAction.getCashAssetWeightChoice() - startAction.getCashAssetWeightChoice();
			
			double[] weightChanges = {eqtyWeightChange, crdtWeightChange, cashWeightChange};
			double transCost = getTransactionCost(this.linearCostFunction, weightChanges);
			
			for(int i = 1; i<=this.decisionHorizon; i++){
				
				double nAEW = newAction.getEquityAssetWeightChoice();
				double nSER = sPrime.getEquityAssetExpectedReturn();
				double nACW = newAction.getCreditAssetWeightChoice();
				double nSCR = sPrime.getCreditAssetExpectedReturn();
				double nACshW = newAction.getCashAssetWeightChoice();
				double nSCshR = sPrime.getCashAssetExpectedReturn();
				double eL = 1;
				double cL = 1;
				
				//sum up all earnings over the decision hirizon
				if(i <= this.resetPeriod){
					surplus += computeEarnings(nAEW, nSER, nACW, nSCR, nACshW, nSCshR, eL, cL) - (transCost + this.liabilityGrowthRate);
				}
				else {
					eL = ( 1 - this.expectedEquityLoss);//note that this  expectedEquityLoss is treated as the 
					//of loss given default {i.e. if agents expect losses to increase (i.e. bearish) then expectedEquityLoss > 0
					// otherwise (i.e. bullish) expectedEquityLoss < 0 
					//the default value of expectedEquityLoss = 0}
					cL = (1 + this.expectedCreditDefault);//note that this is plus because the expectedCreditDefault is treated as the 
					//inverse of loss given default {i.e. if agents expect defaults to increase (i.e. bearish) then expectedCreditDefault < 0
					// otherwise (i.e. bullish) expectedCreditDefault > 0 }
					surplus += computeEarnings(nAEW, nSER, nACW, nSCR, nACshW, nSCshR, eL, cL) - (transCost + this.liabilityGrowthRate);
				}
			}
		}
//		System.out.println("surplus "+ surplus);
		return surplus;
	}
	
	
	

	/**
	 * The following method defines the utility function. This is computed as the surplus earned over the decision horizon
	 * Note Rewards are computed as a function:
	 *  (w(i)*(rt(i)) + w(j)*(rt(j))) + w(k)*(rt(k))) - transactionCost(i,j,k)
	 * The method takes a boolean argument representing whether a linear cost function or a quadratic cost function is used as well as a 
	 * double array consisting of all the weight changes represented by an asset allocation action. 
	 * 
	 * The method check to confirm if the cost function is linear or quadratic
	 * then computes the transaction costs as a sum of transaction costs of each asset
	 * 
	 * Note: The linear cost function factor is included in the quadratic cost function arbitrarily. There is no particular reason for this
	 * however for the most part it will be assumed they are identical
	 * @param lnCost
	 * @param deltaWeight
	 * @return
	 */
	public double getFundingSurplusUtility(PortfolioAssetsState sPrime, MDPPortfolioChoiceAction action) {
		// TODO Auto-generated method stub
		double surplus = 0;
		if(sPrime instanceof PortfolioAssetsState
				 && action instanceof MDPPortfolioChoiceAction){
			double eqtyWeightChange = action.getEquityAssetWeightChoice();
			double crdtWeightChange = action.getCreditAssetWeightChoice();
			double cashWeightChange = action.getCashAssetWeightChoice();
			
			double[] weightChanges = {eqtyWeightChange, crdtWeightChange, cashWeightChange};
			double transCost = getTransactionCost(this.linearCostFunction, weightChanges);
			
			for(int i = 1; i <= this.decisionHorizon; i++){
				
				double nAEW = action.getEquityAssetWeightChoice();
				double nSER = sPrime.getEquityAssetExpectedReturn();
				double nACW = action.getCreditAssetWeightChoice();
				double nSCR = sPrime.getCreditAssetExpectedReturn();
				double nACshW = action.getCashAssetWeightChoice();
				double nSCshR = sPrime.getCashAssetExpectedReturn();
				double eL = 1;
				double cL = 1;
				
				//sum up all earnings over the decision hirizon
				if(i <= this.resetPeriod){
					surplus += computeEarnings(nAEW, nSER, nACW, nSCR, nACshW, nSCshR, eL, cL) 
							- (transCost + this.liabilityGrowthRate);
				}
				else {
					eL = ( 1 - this.expectedEquityLoss);//note that this  expectedEquityLoss is treated as the 
					//of loss given default {i.e. if agents expect losses to increase (i.e. bearish) then expectedEquityLoss > 0
					// otherwise (i.e. bullish) expectedEquityLoss < 0 
					//the default value of expectedEquityLoss = 0}
					cL = (1 + this.expectedCreditDefault);//note that this is plus because the expectedCreditDefault is treated as the 
					//inverse of loss given default {i.e. if agents expect defaults to increase (i.e. bearish) then expectedCreditDefault < 0
					// otherwise (i.e. bullish) expectedCreditDefault > 0 }
					surplus += computeEarnings(nAEW, nSER, nACW, nSCR, nACshW, nSCshR, eL, cL) 
							- (transCost + this.liabilityGrowthRate);
				}
			}
		}
//		System.out.println("surplus "+ surplus);
		return surplus;
	}
	
	
	
	private double computeEarnings(double nAEW, double nSER, double nACW,
			double nSCR, double nACshW, double nSCshR, double eL, double cL) {
		// TODO Auto-generated method stub
		double earnings =  nAEW * (1+ nSER)*eL + nACW * (1+ nSCR) * cL + nACshW * (1+ nSCshR);
		
		return earnings;
	}

	
	/**
	 * The following method defines the utility function as the investor surplus function.
	 * Note Rewards are computed as a function:
	 *  (w(i)*(rt(i)) + w(j)*(rt(j))) + w(k)*(rt(k))) - transactionCost(i,j,k)
	 * The method takes a boolean argument representing whether a linear cost function or a quadratic cost function is used as well as a 
	 * double array consisting of all the weight changes represented by an asset allocation action. 
	 * 
	 * The method check to confirm if the cost function is linear or quadratic
	 * then computes the transaction costs as a sum of transaction costs of each asset
	 * 
	 * Note: The linear cost function factor is included in the quadratic cost function arbitrarily. There is no particular reason for this
	 * however for the most part it will be assumed they are identical
	 * @param lnCost
	 * @param deltaWeight
	 * @return
	 */
	public double getMaximumFundingSurplusUtility(PortfolioAssetsState sPrime, MDPPortfolioChoiceAction startAction) {
		// TODO Auto-generated method stub
		double surplus = 0;
		double maxSurplus = 0;
		for(MDPPortfolioChoiceAction newAction : this.actionsSpace){
			if(sPrime instanceof PortfolioAssetsState
					&& startAction instanceof MDPPortfolioChoiceAction	 && newAction instanceof MDPPortfolioChoiceAction){
				double eqtyWeightChange = 
						newAction.getEquityAssetWeightChoice() - startAction.getEquityAssetWeightChoice();
				double crdtWeightChange = 
						newAction.getCreditAssetWeightChoice() - startAction.getCreditAssetWeightChoice();
				double cashWeightChange = 
						newAction.getCashAssetWeightChoice() - startAction.getCashAssetWeightChoice();

				double[] weightChanges = {eqtyWeightChange, crdtWeightChange, cashWeightChange};
				surplus = (	
						newAction.getEquityAssetWeightChoice() * 
						(
								(1+ sPrime.getEquityAssetExpectedReturn())
								*( 1- this.expectedEquityLoss) //note that this  expectedEquityLoss is treated as the 
								//of loss given default {i.e. if agents expect losses to increase (i.e. bearish) then expectedEquityLoss > 0
								// otherwise (i.e. bullish) expectedEquityLoss < 0 
								//the default value of expectedEquityLoss = 0}
								)


								+ 

								newAction.getCreditAssetWeightChoice() * 
								(
										(1+ sPrime.getCreditAssetExpectedReturn())
										*(1 + this.expectedCreditDefault) //note that this is plus because the expectedCreditDefault is treated as the 
										//inverse of loss given default {i.e. if agents expect defaults to increase (i.e. bearish) then expectedCreditDefault < 0
										// otherwise (i.e. bullish) expectedCreditDefault > 0 }
										)

										+ 

										newAction.getCashAssetWeightChoice() * (((1+sPrime.getCashAssetExpectedReturn())
												)))

												- getTransactionCost(this.linearCostFunction, weightChanges) - this.liabilityGrowthRate;

			}
			if(surplus > maxSurplus){
				maxSurplus = surplus;
			}
		}
		//		System.out.println("reward "+ reward);
		return maxSurplus;
	}
	
	
	/**
	 * The following method computes the transaction costs.
	 * The method takes a boolean argument representing whether a linear cost function or a quadratic cost function is used as well as a 
	 * double array consisting of all the weight changes represented by an asset allocation action.
	 *  
	 * transaction cost functions
	 * Linear:
	 * TC[x(i)] = dx(i)*a
	 * 
	 * Non-linear:
	 * TC[x(i)] = dx(i)*(a+b^2)
	 * 
	 * The method check to confirm if the cost function is linear or quadratic
	 * then computes the transaction costs as a sum of transaction costs of each asset
	 * 
	 * Note: The linear cost function factor is included in the quadratic cost function arbitrarily. There is no particular reason for this
	 * however for the most part it will be assumed they are identical
	 * @param lnCost
	 * @param deltaWeight
	 * @return
	 */
	public double getTransactionCost(boolean lnCost, double[] deltaWeight){
		double transactionCost = 0;
		
		if(lnCost == true){
			for(int i = 0; i < deltaWeight.length; i++){
				transactionCost = transactionCost + deltaWeight[i]*(this.linearfactor);
			}
		}else {
			for(int i = 0; i < deltaWeight.length; i++){
				transactionCost = transactionCost + deltaWeight[i]*(this.linearfactor + Math.pow(this.quadraticfactor, 2));
			}
		}
		
		return transactionCost;
	}
	
	
	
	/**
	 * This method is used to determine the full set of reachable states given an initial state and action taken at that state
	 * 
	 * Permissible
	 *  In this implementation of the IEnvironment this method is not used since the state path is defined globally for all investors
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	 public ArrayList<MDPPortfolioChoiceAction> permissibleActions(PortfolioAssetsState arg0, MDPPortfolioChoiceAction arg1) {
		// TODO Auto-generated method stub
		 ArrayList<MDPPortfolioChoiceAction> permittedActions = new ArrayList<MDPPortfolioChoiceAction>();
		
//		System.out.println("reachable Method is running....");
		
		if(arg0 instanceof PortfolioAssetsState && arg1 instanceof MDPPortfolioChoiceAction){
//			System.out.println("reachable Method Check 1 passed....");
			for(MDPPortfolioChoiceAction act : this.actionsSpace){
//				System.out.println("reachable Method Check 2 started....");
				if(isInPermisibleRange(arg1, act, this.maximumPermissbleChangeInWeight)){
					permittedActions.add(act);
				}//end of valid action determining if-statement
			}//end of state selection for-loop
		}// end of current state and action taken validation check if-statement 
		return permittedActions;
	}


	 private void printStates(ArrayList<PortfolioAssetsState> statesD){
		 for (int i = 0; i < statesD.size(); i++){
			 System.out.println("State: " + statesD.get(i).toString());
		 }
		 
	 }
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY METHOD>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	
	/**
	 * This method is used to determine if the portfolio weights defining a CapitalMarketsPortfolioChoiceAction are in the permissible difference range to 
	 * those defining a CapitalMarketsState
	 * THe method compares the difference in weights between the action and the state to the maximum permitted portfolio weight change
	 * if so the method returns a value of "true" otherwise it returns "false"
	 * 
	 * @param arg1
	 * @param arg0
	 * @return
	 */
	public boolean isInPermisibleRange(MDPPortfolioChoiceAction action, State state, double maxChange){
		double eqtyA = action.getEquityAssetWeightChoice();
		double crdtA = action.getCreditAssetWeightChoice();
		double cshA = action.getCashAssetWeightChoice();
		MDPCapitalMarketsState capMState = (MDPCapitalMarketsState) state;//need to case the IState to a CapitalMarketsState
		double eqtyS = capMState.equityAssetWeight;
		double crdtS = capMState.creditAssetWeight;
		double cshS = capMState.cashAssetWeight;
		
		double eqtyDiff = Math.abs(eqtyA - eqtyS);
		double crdtDiff = Math.abs(crdtA - crdtS);
		double cshDiff = Math.abs(cshA - cshS);
		
		if(
				((eqtyDiff + crdtDiff) <= (maxChange))//sum of the absolute change in both credit and equity assets is less than double the max change
				||
				((eqtyDiff + cshDiff) <= (maxChange))
				||
				((crdtDiff + cshDiff) <= (maxChange))
				
				){
			return true;
		} else{
			return false;
		}
	}


	
	/**
	 * This method is used to determine if the portfolio weights defining a CapitalMarketsPortfolioChoiceAction are in the permissible difference range to 
	 * those defining a CapitalMarketsState
	 * THe method compares the difference in weights between the action and the state to the maximum permitted portfolio weight change
	 * if so the method returns a value of "true" otherwise it returns "false"
	 * 
	 * @param arg1
	 * @param arg0
	 * @return
	 */
	public boolean isInPermisibleRange(MDPPortfolioChoiceAction action, MDPPortfolioChoiceAction actionNext, double maxChange){
		double eqtyCA = action.getEquityAssetWeightChoice();
		double crdtCA = action.getCreditAssetWeightChoice();
		double cshCA = action.getCashAssetWeightChoice();


		double eqtyNA = actionNext.getEquityAssetWeightChoice();
		double crdtNA = actionNext.getCreditAssetWeightChoice();
		double cshNA = actionNext.getCashAssetWeightChoice();
			
		double eqtyDiff = Math.abs(eqtyCA - eqtyNA);
		double crdtDiff = Math.abs(crdtCA - crdtNA);
		double cshDiff = Math.abs(cshCA - cshNA);
		
		if(
				((eqtyDiff + crdtDiff) <= (maxChange))//sum of the absolute change in both credit and equity assets is less than double the max change
				||
				((eqtyDiff + cshDiff) <= (maxChange))
				||
				((crdtDiff + cshDiff) <= (maxChange))
				
				){
			return true;
		} else{
			return false;
		}
	}
	
	
	/**
	 * This method is an implementation of the parent class implementation to determine if a state is reachable from a
	 * successor state by taking an action
	 * @param state
	 * @param action
	 * @param sPrime
	 * @return
	 */
	public boolean isReachable(State state, MDPPortfolioChoiceAction action, State sPrime){
		
		double sEWeight = ((MDPCapitalMarketsState) state).getEquityAssetWeight();
		double sCrdWeight = ((MDPCapitalMarketsState) state).getCreditAssetWeight();
		double sCshwight = ((MDPCapitalMarketsState) state).getCashAssetWeight();
		
		double sPrimeEWeight = ((MDPCapitalMarketsState) sPrime).getEquityAssetWeight();
		double sPrimeCrdWeight = ((MDPCapitalMarketsState) sPrime).getCreditAssetWeight();
		double sPrimeCshwight = ((MDPCapitalMarketsState) sPrime).getCashAssetWeight();
		
		double eqtyCA = action.getEquityAssetWeightChoice();
		double crdtCA = action.getCreditAssetWeightChoice();
		double cshCA = action.getCashAssetWeightChoice();
		
		if((eqtyCA == sPrimeEWeight) && (crdtCA == sPrimeCrdWeight) && (cshCA == sPrimeCshwight)){
			
			return true;
			
		}{
			return false;
		}
		
	}
	
	public State resetState(){
		return this.currentState;
	}
	
	/**
	 * for now this will return a random state
	 * @return
	 */
	public State getNextState(){
		//ProbabilityDistributions.nextSkewedBoundedInteger(int min, int max, double skew, double bias)
		int Min = 0;
		int Max = this.portfolioStates.size();
		int randomIndex = Min + (int)(Math.random() * ((Max - Min)));
		State state = this.portfolioStates.get(randomIndex);
		return state;
	}

	
	/**
	 * For RL Learning using Q-values or partially observable MDP first need to define the initial set of Q-Values 
	 * @param qValues2 
	 */
	public void setQValues(ArrayList<QValue> qValues2){
		if(this.qValues == null){
			this.qValues = new ArrayList<QValue>();
		}else if(!this.qValues.isEmpty()) {
			this.qValues.clear();
		}
		this.qValues = qValues2;
	}
	
	
	/**
	 * For RL Learning using Q-values or partially observable MDP first need to define the initial set of Q-Values 
	 * @param qValues2 
	 */
	public void setQValues(){
		if(this.qValues == null){
			this.qValues = new ArrayList<QValue>();
		}else if(!this.qValues.isEmpty()) {
			this.qValues.clear();
		}
		for(State state : this.portfolioStates){
			for(MDPPortfolioChoiceAction actionStart : this.actionsSpace){//added 17-March-2014 to increase q-value state and create genuine comparisons
				for(MDPPortfolioChoiceAction action : this.actionsSpace){
					//getFundingSurplusUtility(PortfolioAssetsState sPrime, MDPPortfolioChoiceAction startAction, MDPPortfolioChoiceAction newAction) 
					double qval = getFundingSurplusUtility((PortfolioAssetsState) state, actionStart, action);
					this.qValues.add(new QValue(state, action, qval));
				}
			}

		}

	}
	
	
	public ArrayList<QValue> getQValuesAt(State state){
		ArrayList<QValue> qVals = new ArrayList<QValue>();
		
		for(QValue qv : this.qValues){
			if(((PortfolioAssetsState) qv.getStateFrom()).compareTo((PortfolioAssetsState) state) == true){
				qVals.add(qv);
			}
		}
		
		return qVals;
	}



	/**
	 * @return the mdpModelInputParameters
	 */
	public MDPModelInputParameters getMdpModelInputParameters() {
		return mdpModelInputParameters;
	}



	/**
	 * @param mdpModelInputParameters the mdpModelInputParameters to set
	 */
	public void setMdpModelInputParameters(MDPModelInputParameters mdpModelInputParameters) {
		this.mdpModelInputParameters = mdpModelInputParameters;
	}



	public void setMDPModelStateSpace(
			ArrayList<MDPCapitalMarketsState> mdpStates2) {
		// TODO Auto-generated method stub
		this.MDPStates = mdpStates2;
	}



	public void setCurrentMDPState() {
		// TODO Auto-generated method stub
		PortfolioAssetsState paState;
		double eqtyWeightMDP = 0;
		double crdtWeightMDP = 0;
		double cshWeightMDP = 0;
		double eqtyWeight = this.currentAction.getEquityAssetWeightChoice();
		double crdtWeight = this.currentAction.getCreditAssetWeightChoice();
		double cshWeight = this.currentAction.getCashAssetWeightChoice();
		
		for(MDPCapitalMarketsState s: this.MDPStates){
			paState = s.getPortfolioAssetState();
			eqtyWeightMDP = s.getEquityAssetWeight();
			crdtWeightMDP = s.getCreditAssetWeight();
			cshWeightMDP = s.getCashAssetWeight();
			if(this.currentState.compareTo(paState) && (eqtyWeight == eqtyWeightMDP)
					 && (crdtWeight == crdtWeightMDP) && (cshWeight == cshWeightMDP)){
				this.currentMDPState = s;
//				System.out.println(this.currentMDPState.toString());
			}
		}
	}



	public void buildPortfolioAllocationMDPStates(
			ArrayList<MDPCapitalMarketsState> mDPStates2) {
		// TODO Auto-generated method stub
		this.MDPStates = mDPStates2;
	}



	/**
	 * @return the expectedEquityLoss
	 */
	public double getExpectedEquityLoss() {
		return expectedEquityLoss;
	}



	/**
	 * @param expectedEquityLoss the expectedEquityLoss to set
	 */
	public void setExpectedEquityLoss(double expectedEquityLoss) {
		this.expectedEquityLoss = expectedEquityLoss;
	}



	/**
	 * @return the expectedCreditDefault
	 */
	public double getExpectedCreditDefault() {
		return expectedCreditDefault;
	}



	/**
	 * @param expectedCreditDefault the expectedCreditDefault to set
	 */
	public void setExpectedCreditDefault(double expectedCreditDefault) {
		this.expectedCreditDefault = expectedCreditDefault;
	}



	/**
	 * @return the liabilityGrowthRate
	 */
	public double getLiabilityGrowthRate() {
		return liabilityGrowthRate;
	}



	/**
	 * @param liabilityGrowthRate the liabilityGrowthRate to set
	 */
	public void setLiabilityGrowthRate(double liabilityGrowthRate) {
		this.liabilityGrowthRate = liabilityGrowthRate;
	}



	/**
	 * @return the resetPeriod
	 */
	public int getResetPeriod() {
		return resetPeriod;
	}



	/**
	 * @param resetPeriod the resetPeriod to set
	 */
	public void setResetPeriod(int resetPeriod) {
		this.resetPeriod = resetPeriod;
	}
	
	
	
	
	
}
