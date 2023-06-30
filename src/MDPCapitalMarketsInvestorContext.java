



import java.util.ArrayList;
import java.util.Random;





public class MDPCapitalMarketsInvestorContext extends MDPModelContext{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int ENVIRONMENT_ID = 0;
	private int environmentID;
	private ArrayList<MDPPortfolioChoiceAction> actionsSet = new ArrayList<MDPPortfolioChoiceAction>();
	private int numberOfAssetsCreated;
	private double expectedEquityLoss;
	private double expectedCreditDefault;
	
	
	
	
	
	
	public MDPCapitalMarketsInvestorContext(boolean shtSell, boolean lnCost, double astWghtIncr, 
			double cInWghtIncr, double maxPermChgInWght, double discntFactor, double lnCostFactor, double QuadCostFactor){
		super(shtSell, lnCost, astWghtIncr, 
				cInWghtIncr, maxPermChgInWght, discntFactor, lnCostFactor, QuadCostFactor, true);
		ENVIRONMENT_ID++;
		this.environmentID = ENVIRONMENT_ID;
		this.numberOfAssetsCreated = 3;
	}
	
	
	
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<INTERNAL METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public int getEnvironmnetID(){
		return this.environmentID;
	}
	
	@Override
	public void setActionsFullSet(ArrayList<PortfolioAction> list){
//		System.out.println("actionsSet ");
		for(int i = 0; i< list.size(); i++){
			if(list.get(i) instanceof MDPPortfolioChoiceAction){
				this.actionsSet.add((MDPPortfolioChoiceAction) list.get(i));
//				System.out.println("actionsSet action "+ this.actionsSet.get(i).label());
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
	public ArrayList<MDPPortfolioChoiceAction> getActionsPopulationSet(){
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

	
	

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< CORE METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * this method return the default initial state. 
	 * The selected state is picked randomly from the list of all possible states on the condition that this state has a portfolio asset state that 
	 * matches the first PortfolioAssetState in the global CapitalMarketAssetsStatesPath. 
	 * This ensures that all investors start on the same set of portfolio asset conditions even if their portfolio complexion differs 
	 */
	@Override
	public MDPCapitalMarketsState defaultInitialState() {
		// TODO Auto-generated method stub
		PortfolioAssetsState initPAState = statesGenerator.getCapitalMarketAssetsStatesPath().get(0);
		MDPCapitalMarketsState tiState;
		Random rand = new Random();
		int stateSelectionCode;
		
		do {
			stateSelectionCode = rand.nextInt(this.statesSet.size() - 0) + 0;
			tiState = this.statesSet.get(stateSelectionCode);
		}
		while (tiState.getPortfolioAssetState().getStateID() != initPAState.getStateID());
		
		
//		System.out.println(tiState.toString());
		this.setCurrentState(tiState);
		return tiState;
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
	public MDPPortfolioChoiceAction setInitialPortfolioWieghtPolicy() {
		// TODO Auto-generated method stub
		MDPPortfolioChoiceAction initPolicy;
		Random rand = new Random();
		int stateSelectionCode;
		ActionList initialActionSet = this.getActionList(this.getCurrentState());
		stateSelectionCode = rand.nextInt(initialActionSet.size() - 0) + 0;
		initPolicy = (MDPPortfolioChoiceAction) initialActionSet.get(stateSelectionCode);

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
	 * This method creates the set of feasible actions at a given state at a given time or decision epoch or episode
	 * 
	 * This implementation loops through the set all possible states and selects only those which comply with the maximum change in 
	 * portfolio weight constraint.
	 * 
	 * In this instance the constraint will apply across all asset classes so the change in weights assigned to equity, credit and cash 
	 * may not exceed +/- the maximum change
	 * 
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
			//Also if the action results in a state that has asset weightings in excess of =/- the maximum change permited to any asset in the portfolio
			//then that action is also not permitted
//			System.out.println("actionsSet Size "+ actionsSet.size());
			for(MDPPortfolioChoiceAction action : this.actionsSet){
				int exists = 0;
				
				/**
				 *  
				 */
				
				if(//the conditions of the if statement are broken out so as not to get lost with the brackets and make the code easier to follow

						(arg0.getNumberOfAssets() >= 2)//>= 2 is assumed because the default models use 2 or 3 defined asset classes
						&& (this.shortSelling == false)
						&&
						(this.isInPermisibleRange(action, arg0, this.maximumPermissbleChangeInWeight) == true)
								
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
	@Override
	public double getReward(MDPCapitalMarketsState arg0, MDPCapitalMarketsState arg1, PortfolioAction arg2) {
		// TODO Auto-generated method stub
		double reward = 0;
		if(arg0 instanceof MDPCapitalMarketsState && arg1 instanceof MDPCapitalMarketsState && arg2 instanceof MDPPortfolioChoiceAction){
			double eqtyWeightChange = arg1.equityAssetWeight - arg0.equityAssetWeight;
			double crdtWeightChange = arg1.creditAssetWeight - arg0.creditAssetWeight;
			double cashWeightChange = arg1.cashAssetWeight - arg0.cashAssetWeight;
			double[] weightChanges = {eqtyWeightChange, crdtWeightChange, cashWeightChange};
			
				reward = (
						arg1.getEquityAssetWeight() * (((1+arg1.getEquityAssetExpectedReturn())
						- (1+arg0.getEquityAssetExpectedReturn())))
						
						+ arg1.getCreditAssetWeight() 
						* (((1+arg1.getCreditAssetExpectedReturn()*this.expectedCreditDefault)
						- (1+arg0.getCreditAssetExpectedReturn()*this.expectedCreditDefault)))
						
						+ arg1.getCashAssetWeight() * (((1+arg1.getCashAssetExpectedReturn())
								
						
						)))
						- getTransactionCost(this.linearCostFunction, weightChanges);
		}
//		System.out.println("reward "+ reward);
		return reward;
	}

	public boolean isFinal(State arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * This implementation of the successor state calling method differs from the original version (see method below)
	 * in that is uses a globally defined transition path for all risky assets in the capital market model as defined in the 
	 * CapitalMarketStateGenerator.getCapitalMarketAssetsStatesPath() static method.
	 * 
	 * By invoking this method, it is assumed that the general states path of the underlying assets is known to the agent using this context and
	 * said agent is simply reacting to find the optimal choice over this known path given it's original starting state point.
	 * 
	 * The idea behind doing this is to have a single path over the total number of rounds or episodes that can be shared by multiple investor
	 * agents. This global state transition path is passed to the IEnvironment or CapitalMarketsInvestorEnvironment of each of the investors in the model
	 * Each investor then optimizes the best policy using Q-Learning or Value Iteration as in the PIQLE gambler model based on where it starts and its 
	 * Behavioral parameters 
	 * 
	 * The next state is selected by looping up through the PortfolioAssetsState in the CapitalMarketAssetsStatesPath list based on the round/episode
	 * 
	 * To get the actual CapitalMarketState the investor ends up in after a given action,
	 * 1: the selected PortfolioAssetsState looping through all transitions in the starting CapitalMarketState arg0
	 * 2: check the PortfolioAssetsState in CapitalMarketState arg0 matches the PortfolioAssetsState selected from the CapitalMarketAssetsStatesPath
	 * 	  and the portfolio weights defining the CapitalMarketsPortfolioChoiceAction arg1 matches the weights in the destination CapitalMarketsState of the 
	 * 	  selected Transition from CapitalMarketsState arg0 
	 * 3: If the conditions in step 2 are satisfied, then set the successor state destState to the destination CapitalMarketsState of the 
	 * 	  selected Transition from CapitalMarketsState arg0
	 * 
	 * Note: 
	 * This implementation requires that the full set of CapitalMarketsState states is known by the modeler before hand and this set is passed to all 
	 * investor agents so that the state IDs do not differ across all investor agents. That is, the set of all CapitalMarketsState states and the set of all
	 * CapitalMarketsPortfolioChoiceAction actions must be global and known.
	 * 
	 * This method will not work otherwise
	 * 
	 * (non-Javadoc)
	 * @see 
	 */
	public State macrolevelSuccessorState(State arg0, PortfolioAction arg1) {
		// TODO Auto-generated method stub
		
		this.episode++;
		PortfolioAssetsState destPAState = statesGenerator.getCapitalMarketAssetsStatesPath().get(episode);
		MDPCapitalMarketsState destState = null;

		//Loop through the transitions of the current states portfolio asset state
		//add the probability to the cumulative probability and then check if the p random number is less than or equal to the cumulative
		//if so select the transition and equate with the tempTransition
		for(Transition transition: ((MDPCapitalMarketsState) arg0).getPortfolioAssetState().getAdjacencyListTransitions()){
			MDPCapitalMarketsState tdState = (MDPCapitalMarketsState) transition.getDestinationState();
			if((
					tdState.getPortfolioAssetState().getStateID()
					 == destPAState.getStateID())
					 
					 && (compareWeights((MDPPortfolioChoiceAction) arg1, tdState) == true)
					 
					 ){
				destState = transition.getDestinationMDPState();
			}
		}
//		System.out.println("destState "+ destState.toString());
		return destState;
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
	

	

	/**
	 * This method is used to determine the full set of reachable states given an initial state and action taken at that state
	 * 
	 *  In this implementation of the IEnvironment this method is not used since the state path is defined globally for all investors
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	 public StateSetAssetAllocation reachable(MDPCapitalMarketsState arg0, PortfolioAction arg1) {
		// TODO Auto-generated method stub
		StateSetAssetAllocation reachableStates = new StateSetAssetAllocation();
		
//		System.out.println("reachable Method is running....");
		
		if(arg0 instanceof MDPCapitalMarketsState && arg1 instanceof MDPPortfolioChoiceAction){
//			System.out.println("reachable Method Check 1 passed....");
			for(MDPCapitalMarketsState state : this.statesSet){
//				System.out.println("reachable Method Check 2 started....");
				if(//the conditions of the if statement are broken out so as not to get lost with the brackets and make the code easier to follow
						isInPermisibleRange((MDPPortfolioChoiceAction) arg1, state, this.maximumPermissbleChangeInWeight)
						){
//					System.out.println("Now inserting " + state.toString());
					reachableStates.put(state);
				}//end of valid state determining if-statement
			}//end of state selection for-loop
		}// end of current state and action taken validation check if-statement 
//		System.out.println("reachable set created with size: " + reachableStates.StatesSet().size());
		return reachableStates;
	}
	 
	 /**
	  * The following method makes a call the the reachable method above and returns an ArrayList containing the set of states
	  * The  StateSetAssetAllocation contains an ArrayList that provides easier and blind access to all the states stored in the 
	  * StateSetAssetAllocation LinkedHashMap.
	  * This also makes it easier to iterate through the StateSetAssetAllocation without having to know all the state IDs used as
	  * the unique primary keys for the stored States  
	  * @param arg0
	  * @param arg1
	  * @return
	  */
	 @Override
	public ArrayList<MDPCapitalMarketsState> getCurrentReachableStates(MDPCapitalMarketsState arg0, PortfolioAction arg1){
		 StateSetAssetAllocation reachableStates = this.reachable(arg0, arg1);
		 ArrayList<MDPCapitalMarketsState> statesD = reachableStates.getStatesDomain();
		 printStates(statesD);
			return statesD;
	 }


	 private void printStates(ArrayList<MDPCapitalMarketsState> statesD){
		 for (int i = 0; i < statesD.size(); i++){
			 System.out.println("State: " + statesD.get(i).toString());
		 }
		 
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
	
	
	
	/**
	 * This method is used to determine if the portfolio weights defining a CapitalMarketsPortfolioChoiceAction are identical to 
	 * those defining a CapitalMarketsState
	 * if so the method returns a value of "true" otherwise it returns "false"
	 * @param arg1
	 * @param tdState
	 * @return
	 */
	private boolean compareWeights(MDPPortfolioChoiceAction arg1, MDPCapitalMarketsState tdState){
		double eqtyA = arg1.getEquityAssetWeightChoice();
		double crdtA = arg1.getCreditAssetWeightChoice();
		double cshA = arg1.getCashAssetWeightChoice();
		
		double eqtyS = tdState.equityAssetWeight;
		double crdtS = tdState.creditAssetWeight;
		double cshS = tdState.cashAssetWeight;
		
		if(eqtyA == eqtyS && crdtA == crdtS && cshA == cshS){
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
	private boolean isInPermisibleRange(MDPPortfolioChoiceAction action, State state, double maxChange){
		double eqtyA = action.getEquityAssetWeightChoice();
		double crdtA = action.getCreditAssetWeightChoice();
		double cshA = action.getCashAssetWeightChoice();
		MDPCapitalMarketsState capMState = (MDPCapitalMarketsState) state;//need to case the IState to a CapitalMarketsState
		double eqtyS = capMState.equityAssetWeight;
		double crdtS = capMState.creditAssetWeight;
		double cshS = capMState.cashAssetWeight;
		
		double eqtyDiff = eqtyA - eqtyS;
		double crdtDiff = crdtA - crdtS;
		double cshDiff = cshA - cshS;
		
		if(
				eqtyDiff >= -1*maxChange && eqtyDiff <= maxChange
				
				
				&& crdtDiff  >= -1*maxChange && crdtDiff <= maxChange
				
				
				&& cshDiff >= -1*maxChange && cshDiff <= maxChange
				
				){
			return true;
		} else{
			return false;
		}
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

}
