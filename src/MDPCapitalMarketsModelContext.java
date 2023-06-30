



import java.util.ArrayList;
import java.util.Random;



public class MDPCapitalMarketsModelContext extends MDPModelContext{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int ENVIRONMENT_ID = 0;
	private int environmentID;
	private ArrayList<MDPPortfolioChangeAction> actionsSet = new ArrayList<MDPPortfolioChangeAction>();

	
	
	
	
	public MDPCapitalMarketsModelContext(boolean shtSell, boolean lnCost, double astWghtIncr, 
			double cInWghtIncr, double maxPermChgInWght, double discntFactor, double lnCostFactor, double QuadCostFactor){
		super(shtSell, lnCost, astWghtIncr, 
				cInWghtIncr, maxPermChgInWght, discntFactor, lnCostFactor, QuadCostFactor, false);
		ENVIRONMENT_ID++;
		this.environmentID = ENVIRONMENT_ID;

	}
	
	
	
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<INTERNAL METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public int getEnvironmnetID(){
		return this.environmentID;
	}
	
	@Override
	public void setActionsFullSet(ArrayList<PortfolioAction> list){
//		System.out.println("actionsSet ");
		for(int i = 0; i< list.size(); i++){
			if(list.get(i) instanceof MDPPortfolioChangeAction){
				this.actionsSet.add((MDPPortfolioChangeAction) list.get(i));
			}
		}
//		System.out.println("actionsSet "+ this.actionsSet.size());
	}
	

	@Override
	public void setStatesFullSet(ArrayList<MDPCapitalMarketsState> states){
//		System.out.println("statesSet ");
		for(int i = 0; i< states.size(); i++){
			if(states.get(i) instanceof MDPCapitalMarketsState){
				this.statesSet.add(states.get(i));
				
			}
		}
//		System.out.println("statesSet "+ this.statesSet.size());
	}
	
	
	@Override
	public ArrayList<MDPPortfolioChangeAction> getActionsPopulationSet(){
		return this.actionsSet;
	}
	
	@Override
	public ArrayList<MDPCapitalMarketsState> getStatesPopulationSet(){
		return this.statesSet;
	}
	
	@Override
	public String toString(){
		return "Capital Market Environment: " + this.getEnvironmnetID() + " Population of Possible States: " + this.statesSet.size()
				+ " Population of Possible Actions: " + this.actionsSet.size();
	}

	
	

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<PIQLE CORE METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	
	/**
	 * this method return the default initial state. 
	 * The selected state is picked randomly from the list of all possible states
	 */
	@Override
	public MDPCapitalMarketsState defaultInitialState() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int stateSelectionCode = rand.nextInt(this.statesSet.size() - 0) + 0;
//		System.out.println(((CapitalMarketsState) this.statesSet.get(stateSelectionCode)).toString());
		this.setCurrentState((this.statesSet.get(stateSelectionCode)));
		return (this.statesSet.get(stateSelectionCode));
	}

	private void setCurrentState(MDPCapitalMarketsState capitalMarketsState) {
		// TODO Auto-generated method stub
		this.currentState = capitalMarketsState;
	}
	
	@Override
	public MDPCapitalMarketsState getCurrentState(){
		return this.currentState;
	}


	
	/**
	 * this method return the default initial policy. 
	 * The selected state is picked randomly from the list of all possible states on the condition that this state has a portfolio asset state that 
	 * matches the first PortfolioAssetState in the global CapitalMarketAssetsStatesPath. 
	 * This ensures that all investors start on the same set of portfolio asset conditions even if their portfolio complexion differs 
	 */
	public MDPPortfolioChangeAction setInitialPortfolioWieghtPolicy() {
		// TODO Auto-generated method stub
		MDPPortfolioChangeAction initPolicy;
		Random rand = new Random();
		int stateSelectionCode;
		stateSelectionCode = rand.nextInt(this.actionsSet.size() - 0) + 0;
		initPolicy = this.actionsSet.get(stateSelectionCode);

		//		System.out.println(tiState.toString());
		this.setCurrentPolicy(initPolicy);
		return initPolicy;
	}


	public void setCurrentPolicy(PortfolioAction policy){
		this.currentAction  = policy;
	}
	
	
	@Override
	public PortfolioAction getCurrentPolicy(){
		return this.currentAction;
	}




	
	/**
	 * This method creates the set of feasible actions at a given state at a given time or decision epoch
	 */
	@Override
	public ActionList getActionList(MDPCapitalMarketsState arg0) {
		// Note that the time does not matter in this method since any action in the action set is feasible at all times
		ActionList actionList = new ActionList(arg0);//Note that ActionsSet is an implementation
		//of a java TreeSet so elements must be unique
		if(arg0 instanceof MDPCapitalMarketsState){
			//the purpose of the following loops is to ensure that there is a reachable state in the states set for any given action in the action space
			//if you can not reach a state in the global states set by taking an action in the action set regardless of transition probabilities, 
			//then the action is not feasible and should not be added to the set of actions feasible at the current state.
//			System.out.println("actionsSet Size "+ actionsSet.size());
			for(MDPPortfolioChangeAction action : this.actionsSet){
				int exists = 0;
//				for(CapitalMarketsState state : this.statesSet){
//					if(//the conditions of the if statement are broken out so as not to get lost with the brackets and make the code easier to follow
//
//							(((CapitalMarketsState) arg0).getNumberOfAssets() == 2)
//							&&
//							(
//									(((CapitalMarketsState) arg0).getEquityAssetWeight() + action.getEquityAssetWeightChange())
//									== state.getEquityAssetWeight())
//									&& 
//									(
//											(((CapitalMarketsState) arg0).getCreditAssetWeight() + action.getCreditAssetWeightChange())
//											== state.getCreditAssetWeight())
//							){
//						exists++;//if there is a state that can be reached from the current state by taking an action action then increment the exists integer 
//					}//end if
//				}//end actions for-loop
				
				/**
				 * this alternative approach to selecting the feasible actions at any given state simply checks if for a 2-asset model which precludes 
				 * short selling the change in weight signified by each successive action in the action set results in a valid asset allocation in the 
				 * equity asset (i.e. 0 <= x <=1)
				 * This obviously assumes all actions in the actions set are legal
				 * Note that being a 2 asset model with no short selling, one can get away with simply looking at a single asset to determine if a legal
				 * action is feasible 
				 */
				
				if(//the conditions of the if statement are broken out so as not to get lost with the brackets and make the code easier to follow

						(arg0.getNumberOfAssets() == 2)
						&& (this.shortSelling == false)
						&&
						(
								(arg0.getEquityAssetWeight() + action.getEquityAssetWeightChange())
								>= 0)
								&& 
								(
										(arg0.getEquityAssetWeight() + action.getEquityAssetWeightChange())
										<= 1)
						){
					exists++;//if there is a state that can be reached from the current state by taking an action action then increment the exists integer 
				}//end if
				if(exists>0){//if after looping through all states the exist integer is greater than 0 then add the corresponding action to the action list
					actionList.add(action);
				}
			}//end global states space for-loop
		}//end confirmation check on the type of the state argument arg0 being passed to the method
//		System.out.println("actionList "+ actionList.toString());
//		System.out.println("actionList Size "+ actionList.size());
		return actionList;//return set of feasible actions at the given state arg0
	}

	

	/**
	 * The following method defines the reward function.
	 * Note Rewards are computed as a function:
	 *  (w(i)*(rt(i) - rt-1(i)) + w(j)*(rt(j) - rt-1(j))) - transactionCost(i,j)
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
	@Override
	public double getReward(MDPCapitalMarketsState arg0, MDPCapitalMarketsState arg1, PortfolioAction arg2) {
		// TODO Auto-generated method stub
		double reward = 0;
		if(arg0 instanceof MDPCapitalMarketsState && arg1 instanceof MDPCapitalMarketsState && arg2 instanceof MDPPortfolioChangeAction){
			if(//the conditions of the if statement are broken out so as not to get lost with the brackets and make the code easier to follow
						
						(arg0.getNumberOfAssets() == 2)
						){
				
				double[] weightChanges = {((MDPPortfolioChangeAction) arg2).getEquityAssetWeightChange(), 
						((MDPPortfolioChangeAction) arg2).getCreditAssetWeightChange()};
				
				reward = (
						arg1.getEquityAssetWeight() * (1+((arg1.equityAssetExpectedReturn)
						- (arg0.equityAssetExpectedReturn)))
						
						+ arg1.getCreditAssetWeight() * (1+((arg1.creditAssetExpectedReturn)
						- (arg0.creditAssetExpectedReturn)))
						)
						- getTransactionCost(this.linearCostFunction, weightChanges);
				}//end of valid state determining if-statement
		}
//		System.out.println("reward "+ reward);
		return reward;
	}


	
	/**This set defines the set of reachable states from a given state after a specific action is taken at
	 * a given time or decision epoch
	 * 
	 * It then randomly selects the next state at random from the reachable set.
	 * 
	 * It borrows from the reachable method in the jMarkov library and implements much the same structure.
	 * 
	 * To determine if a state is reachable from the current state,
	 * 1: determine if the current state and proposed action are instances of the types of states and actions being looked at
	 * 	  in this instance the states must be of type <AssetAllocationMDPState> and actions of type <AssetAllocationAction>
	 * 2: pick the state from the global population of states
	 * 3: check how many assets ate being modeled for
	 * 4: for each of the assets being modeled for check if the weight change applied to that asset in the current state by the selected action 
	 * 	  would result in the same weights as those of the state selected from the global states space 
	 * 
	 * Note that a custom StatesSetAssetAllocation object is created and implemented as a java LinkedHashMap, so states added to the set will have to be unique
	 * and ordered according to the order in which they are added into the states set.
	 * 
	 * The order actually does not matter since the assumption from the procedure listed above is that all states assigned to the set are reachable given the 
	 * action that is selected. Consequently only the transition probability matters.
	 * The probability is defined using a uniform random distribution
	 * However, this can be set to any distribution. To do this in the main CRT model the Apache Commons Math library will be used and a list of distributions
	 * will be selected to sample from  
	 * 
	 * 
	 * *  
	 *  To obtain the transitions from state to state the states are selected based on their probabilities using a sequence of statements like
	 *  this:
	 *  double p = Math.random(); 
	 *  double cumulativeProbability = 0.0; 
	 *  for (Item item : items) {
	 *      cumulativeProbability += item.probability();
	 *          if (p <= cumulativeProbability && item.probability() != 0) {
	 *                  return item;
	 *         }
	 *  }
	 *   
	 * 
	 * (non-Javadoc)
	 * @see 
	 */
	@Override
	public MDPCapitalMarketsState successorState(MDPCapitalMarketsState arg0, PortfolioAction arg1) {
		// TODO Auto-generated method stub
		
		
		StateSetAssetAllocation reachableStates =  reachable(arg0, arg1);//get the set of reachable states
		MDPCapitalMarketsState destState = arg0;
		double p = Math.random(); //generate a random probability number
		double cumulativeProbability = 0.0; //set a cumulative probability
		Transition tempTransition = null; //define a temporary transition which will be assigned a value of the portfolio asset state
										 // that is picked by transition probability
		//Loop through the transitions of the current states portfolio asset state
		//add the probability to the cumulative probability and then check if the p random number is less than or equal to the cumulative
		//if so select the transition and equate with the tempTransition
		for(Transition transition: arg0.getPortfolioAssetState().getAdjacencyListTransitions()){
			cumulativeProbability += transition.getTransitionProbability();
			if(p <= cumulativeProbability && transition.getTransitionProbability() != 0){
				tempTransition = transition;
			}
		}
		
		//now loop through the set of reachable states and pick the state that has the same portfolio asset sate as that in the temp transition
		for(State state: reachableStates.StatesSet().values()){
			if(((MDPCapitalMarketsState) state).getPortfolioAssetState().getStateID() 
					== ((PortfolioAssetsState) tempTransition.getDestinationState()).getStateID()){
				destState =  (MDPCapitalMarketsState) state;
			}
		}
//		System.out.println("destState "+ destState.toString());
		return destState;
	}
	
	
	public StateSetAssetAllocation reachable(State arg0, PortfolioAction arg1) {
		// TODO Auto-generated method stub
		StateSetAssetAllocation reachableStates = new StateSetAssetAllocation();
		
		if(arg0 instanceof MDPCapitalMarketsState && arg1 instanceof MDPPortfolioChangeAction){
			for(MDPCapitalMarketsState state : this.statesSet){
				if(//the conditions of the if statement are broken out so as not to get lost with the brackets and make the code easier to follow
						
						(((MDPCapitalMarketsState) arg0).getNumberOfAssets() == 2)
						&&
						(
						(((MDPCapitalMarketsState) arg0).getEquityAssetWeight() + ((MDPPortfolioChangeAction) arg1).getEquityAssetWeightChange())
						== state.getEquityAssetWeight())
						&& 
						(
						(((MDPCapitalMarketsState) arg0).getCreditAssetWeight() + ((MDPPortfolioChangeAction) arg1).getCreditAssetWeightChange())
						== state.getCreditAssetWeight())
						){
					reachableStates.put(state);
				}//end of valid state determining if-statement
			}//end of state selection for-loop
		}// end of current state and action taken validation check if-statement 
		return reachableStates;
	}


	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY METHOD>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
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






	@Override
	public ArrayList<MDPCapitalMarketsState> getCurrentReachableStates(
			MDPCapitalMarketsState state, PortfolioAction policy) {
		// TODO Auto-generated method stub
		return null;
	}


}
