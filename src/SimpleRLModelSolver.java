import java.util.ArrayList;
import java.util.TreeSet;



/**
 * 
 * @author Oluwasegun Bewaji
 * 
 * THis class is part of the class of algorithms that will be used to solve various MDP models
 * 
 * The value iteration solver extends the generic solver class which store all common variables and methods 
 * used by all learning and dynamic programming algorithms
 * 
 * It must be constructed with at the very least a Markov Decision Process to solve. 
 *
 */
public class SimpleRLModelSolver extends SolverAlgorithm{

	/**
	 * In the base instance the MDP is described by the CapitalMarketsIEnvironment class
	 * @param mdp
	 */
	double vStar;
	MDPPortfolioChoiceAction bestPolicy = null;
	
	
	
	public SimpleRLModelSolver(QLearningInvestorCalculationEngine mdpCalcEngine) {
		// TODO Auto-generated constructor stub
		super(mdpCalcEngine);
//		System.out.println("Creating New Value Iteration Solver...");
//		System.out.println("New Value Iteration Solver has been created for the MDP Context: " + this.mdpCalculationEngine.toString());
//		
//		System.out.println("New Value Iteration Solver MDP Context History Size: " + this.MDP_Model_History.size());
		
	}
	
	/**
	 * This is where the value iteration algorithm is implemented according to the algorithm 
	 * defined in Sottun and Barto Figrue 4.3 pp 98.
	 * V(s) = Sigm{ P(s,s',a(s)) *[R(s,s',a(s)] + gamma*V(s')]
	 * For simplicity of exposition in this portfolio selection case V(s') is assumed to be equal to
	 * the expected returns in state s' where s = f{a(s)=a(s'), equity State return, credit state return, cash state return}
	 * and where a(s) = a(s') = f{Equity weight, credit weight, cash weight} is the policy being evaluated.
	 * 
	 * 1: Procedure Value_Iteration(S,A,P,R,θ)
	 * 2:           Inputs
	 * 3:                     S is the set of all states 
	 * 4:                     A is the set of all actions 
	 * 5:                     P is state transition function specifying P(s'|s,a) 
	 * 6:                     R is a reward function R(s,a,s') 
	 * 7:                     θ a threshold, θ>0 
	 * 8:           Output
	 * 9:                     π[S] approximately optimal policy 
	 * 10:                    V[S] value function 
	 * 11:           Local
	 * 12:                     real array Vk[S] is a sequence of value functions 
	 * 13:                     action array π[S] 
	 * 14:           assign V0[S] arbitrarily 
	 * 15:           k ←0 
	 * 16:           repeat
	 * 17:                     k ←k+1 
	 * 18:                     for each state s do 
	 * 19:                               Vk[s] = maxa ∑s' P(s'|s,a) (R(s,a,s')+ γVk-1[s']) 
	 * 20:           until ∀s |Vk[s]-Vk-1[s]| < θ 
	 * 21:           for each state s do 
	 * 22:                     π[s] = argmaxa ∑s' P(s'|s,a) (R(s,a,s')+ γVk[s']) 
	 * 23:           return π,Vk
	 *  
	 * @param iterations
	 * @param gamma
	 * @param accuracyThreshold
	 */
	@Override
	public void evaluatePolicy(int iterations, double gamma, double accuracyThreshold){
		
		this.gamma = gamma;
		int itn = 0;
		ArrayList<MDPPortfolioChoiceAction> policySet =  this.mdpCalculationEngine.actionsSpace;
		ArrayList<PortfolioAssetsState> statesSet  = this.mdpCalculationEngine.portfolioStates; 
		MDPPortfolioChoiceAction optAction = mdpCalculationEngine.currentAction;
		if(valueFunctions != null){
			valueFunctions.clear();
		}
		ArrayList<Double> deltas = new ArrayList<Double>();
		
		//initiate V(s)
		double V_s = 0;
		double v;
		double threshold;
//		System.out.println("New Value Iteration Solution has been initiated please wait for your solution....");
		if(gamma == 1){
			threshold = this.epsilon;
		}
		else{
			threshold = this.epsilon*(1.-gamma)/gamma;
		}
		
		do {
			itn++;
			this.delta = 0;
				for(int s = 0; s < statesSet.size(); s++){
					v = Rounding.roundSevenDecimals(V_s);
					double maxA = 0.0;
					double sum;
					for(int y = 0; y < policySet.size(); y++){
						for(int x = 0; x < policySet.size(); x++){
							if(mdpCalculationEngine.isInPermisibleRange(policySet.get(y), policySet.get(x),	
									mdpCalculationEngine.maximumPermissbleChangeInWeight)){
//								System.out.println("Action: " + ((MDPPortfolioChoiceAction) policySet.get(x)).label() 
//										+ " is valid given initial Action: " + ((MDPPortfolioChoiceAction) policySet.get(y)).label());
								//compute the summation piece of the V(s) <- max(a) SIGMA[P,R,s,a]
								//that is compute the total utility attainable for policy (a) given state (s) across all reachable states (s') having enacted policy (a)
								//at state (s)
//								sum = backUpActionRussellNorvig(statesSet.get(s), statesSet, 
//										optAction, (MDPPortfolioChoiceAction) policySet.get(x));
								sum = backUpActionSuttonBarto(statesSet.get(s), policySet.get(x));
								
//								System.out.println("Sum over all possible transitions for Action : " + ((MDPPortfolioChoiceAction) policySet.get(x)).label() 
//										+ " at state " + statesSet.get(s).toString() + " is SUM: " + sum);
								//now determine the policy a that returns the maximum utility given the initial starting state (s)
								//this is the max(a) piece of V(s) <- max(a) SIGMA[P,R,s,a]
								//set the max(a) to sum if the new sum is larger than the previous sum
								if(Rounding.roundSevenDecimals(sum) > maxA){
									System.out.println("New maxA Sum : " + Rounding.roundSevenDecimals(sum) + 
											" for Action : " + policySet.get(x).label());
									maxA = Rounding.roundSevenDecimals(sum);
									V_s = Rounding.roundSevenDecimals(maxA);
									optAction = policySet.get(x);
								}//end if statement
							}
						}
						//Now add the best policy (a) for state (s) and the corresponding maximum utility to a value function for record keeping
						valueFunctions.add(new ValueFunction(itn, V_s, statesSet.get(s), policySet.get(y), optAction));
					}
					
					
					//now determine if the maximum utility outcome is stable/convergent.
					//note that if the previous maximum utility derived from the previous best policy is less than the 
					//maximum utility derived from the new best policy, then v - V(s) < 0 which mean one is losing out on
					//utility increases if one sticks with the previous best policy. Therefore, one would switch policy to the new
					//and thus the previous delta will remain valid. 
					//Consequently delta will always be a positive value to indicate one is improving on previous policies
					this.delta = Math.max(delta, Rounding.roundSevenDecimals(Math.abs(v - V_s))) ;
//					if(s == 0 || this.delta > Rounding.roundSevenDecimals(Math.abs(v - V_s))){
//						this.delta = Rounding.roundSevenDecimals(Math.abs(v - V_s));
//						deltas.add(this.delta);
//					}
//					System.out.println("New Value Iteration previous maximum : " + v);
//					System.out.println("New Value Iteration current maximum : " + V_s);
					System.out.println("New Value Iteration Solution delta : " + this.delta);
//					System.out.println("New Value Iteration Solution epsilon*((1-gamma)/gamma)) : " + Rounding.roundSevenDecimals(this.epsilon*((1-gamma)/gamma)));
//					System.out.println("New Value Iteration Solution itn : " + itn);
				}
			this.deltaHistory.add(deltas);
		}while(this.delta > Rounding.roundSevenDecimals(this.epsilon*((1-gamma)/gamma)));
		// value iteration terminal condition which follows on from the localize stability condition
		
		valueFunctionsHistory.add(valueFunctions);
//		System.out.println("New Value Iteration Solution has been derived for the MDP Context: " + this.mdpCalculationEngine.toString());
//		System.out.println("New Value Iteration Solution is located in the Value Function space of size: " + valueFunctions.size());
//		System.out.println("New Value Iteration Solution delta : " + this.delta);
//		System.out.println("New Value Iteration optimal policy is: " 
//		+ ((MDPPortfolioChoiceAction) valueFunctions.get(valueFunctions.size()-1).getPolicyAction()).label());
	}
	
	
	
	private double backUpActionRussellNorvig(PortfolioAssetsState state,
			ArrayList<PortfolioAssetsState> statesSet,
			MDPPortfolioChoiceAction optAction, MDPPortfolioChoiceAction MDPPortfolioChoiceAction) {
		// TODO Auto-generated method stub
		//System.out.println("backUpAction Method is Running... " );
		double sum = state.getPortfolioStateExpectedReturn();
		
		
		
//		System.out.println("Total number of reachable states: " + reachableStates.size());
		TreeSet<Transition> initialStateTransitionsList = state.getAdjacencyListTransitions();
		for(int nState = 0; nState < statesSet.size(); nState++){
			int[] endStateProp = statesSet.get(nState).getProperties();
			double transitionProb = 0;
			double stateReturn  = statesSet.get(nState).getPortfolioStateExpectedReturn();
			double stateTransitionReward = mdpCalculationEngine.getReward(state, statesSet.get(nState), optAction, MDPPortfolioChoiceAction);
			//now find the appropriate transition probability.
			//loop through the transitions set of the initial state
			//till the new state (nState) portfolio asset properties are found to match that
			//of the destination state of the selected transition from the initial state's transitions set
			for(Transition iSTList : initialStateTransitionsList){
				if(identicalEndStates(iSTList, endStateProp) == true){
					transitionProb = iSTList.getTransitionProbability();
				}
			}
			sum += transitionProb * ( stateTransitionReward + stateReturn);
		}
		return sum;
	}
	
	
	/**
	 * This is added on March 19, 2014 to replace the original wrong attempted implementation of Sutton and Barto [page 102]
	 * @param state
	 * @param MDPPortfolioChoiceAction
	 * @param gamma
	 * @return
	 */
	private double backUpActionSuttonBarto(PortfolioAssetsState state,
			MDPPortfolioChoiceAction MDPPortfolioChoiceAction) {
		// TODO Auto-generated method stub
		PortfolioAssetsState sPrime;
		double sum = 0;
		double prob_s_sPrime = 0;
		double reward_s_sPrime = 0;//mdpCalculationEngine.getReward(PortfolioAssetsState arg0, PortfolioAssetsState arg1, MDPPortfolioChoiceAction arg2)
		double v_sPrime = 0;//mdpCalculationEngine.getFundingSurplusUtility(PortfolioAssetsState sPrime, MDPPortfolioChoiceAction action)
		TreeSet<Transition> initialStateTransitionsList = state.getAdjacencyListTransitions();
		for(Transition iSTList : initialStateTransitionsList){
			prob_s_sPrime = iSTList.getTransitionProbability();
			sPrime = (PortfolioAssetsState) iSTList.getDestinationState();
			v_sPrime = mdpCalculationEngine.getFundingSurplusUtility(sPrime, MDPPortfolioChoiceAction);
			reward_s_sPrime = mdpCalculationEngine.getReward(state, sPrime, MDPPortfolioChoiceAction);
			sum += prob_s_sPrime * ( reward_s_sPrime + this.gamma*v_sPrime);
		}
		return sum;
	}

	private ArrayList getActionSetType(boolean actionType){
		
		ArrayList list;
		if(actionType){
			list = new ArrayList<MDPPortfolioChoiceAction>();
			list = mdp.getActionsPopulationSet();
		}
		else{
			list = new ArrayList<MDPPortfolioChangeAction>();
			list = mdp.getActionsPopulationSet();
		}
		
		return list;
	}
	
	
	/**
	 * 
	 * This method does a comparison of the properties of the destination state contained in an initial state's 
	 * Transition {Transition initialStateTransition} and the properties {int[] endStateProp} of some exogenously provided end state
	 * 
	 * if both end state properties are identical then the method returns TRUE else if returns FALSE 
	 * 
	 * @param initialStateTransition
	 * @param endStateProp
	 * @return
	 */
	public boolean identicalEndStates(Transition initialStateTransition, int[] endStateProp) {
		// TODO Auto-generated method stub
		boolean d = false;
		int[] tempProp;
		int dCount = 0;
		if(initialStateTransition instanceof Transition){
			tempProp = ((PropertiesState) initialStateTransition.getDestinationState()).getProperties();
			if(tempProp.length == endStateProp.length){
				//loop through all the elements of both endStateProp and tempProp. 
				//Since by definition each element of both should reference the same asset class
				//the properties value i.e. asset return movement direction should be identical
				for(int i = 0; i < endStateProp.length; i++){
					for(int j = 0; j < tempProp.length; j++){
						if(endStateProp[i] == tempProp[j]){
							dCount++;
						}
					}
					if(dCount < endStateProp.length){	//if all elements are identical, then dCount == endStateProp.length
						d = false;	
					} else{
						d = true;
					}
				}
			}
		}
		return d;
	}

	
//	/**
//	 * This method returns the best policy action by looping through all entries in the value iteration ArrayList and 
//	 * selecting the action where the largest utility is observed
//	 * @return
//	 */
//	public MDPPortfolioChoiceAction extractBestPolicy(){
////		System.out.println("Extracting the best policy now, please wait... ");
//		double max = 0;
//		for(int i = 0; i < valueFunctions.size(); i++){
//			double vs = valueFunctions.get(i).getUtilityValue_v_s();
//			if(vs > max){
//				max = vs;
//				bestPolicy = (MDPPortfolioChoiceAction) valueFunctions.get(i).getPolicyAction();
//			}
//		}
////		System.out.println("The best policy is " + bestPolicy.label());
//		return bestPolicy;
//		
//	}
	
	
	/**
	 * This method returns the best policy action by looping through all entries in the value iteration ArrayList and 
	 * selecting the action where the largest utility is observed
	 * @return
	 */
	@Override
	public MDPPortfolioChoiceAction extractBestPolicy(){
//		System.out.println("Extracting the best policy now, please wait... ");
		int max = valueFunctions.size()-1;
		bestPolicy = (MDPPortfolioChoiceAction) valueFunctions.get(max).getPolicyAction();

//		System.out.println("The best policy is " + bestPolicy.label());
		return bestPolicy;
		
	}

	
	@Override
	public ArrayList<ArrayList<Double>> getDeltaHistory(){
		return this.deltaHistory;
	}
	
	
	
	@Override
	public int solve(int iterations, double gamma, double accuracyThreshold){

		this.gamma = gamma;
		int itn = 0;
		ArrayList<MDPPortfolioChoiceAction> policySet =  this.mdpCalculationEngine.actionsSpace;
		ArrayList<PortfolioAssetsState> statesSet  = this.mdpCalculationEngine.portfolioStates; 
		MDPPortfolioChoiceAction startAction = this.mdpCalculationEngine.currentAction;
		PortfolioAssetsState startState = this.mdpCalculationEngine.currentState;
		if(valueFunctions != null){
			valueFunctions.clear();
		}
		ArrayList<Double> deltas = new ArrayList<Double>();

		//initiate V(s)
		//	double maxCurrentUtility = -1e30;
		double threshold;
		//	System.out.println("New Value Iteration Solution has been initiated please wait for your solution....");
		if(gamma == 1){
			threshold = this.epsilon;
		}
		else{
			threshold = this.epsilon*(1.-gamma)/gamma;
		}

		boolean finished = false;
		
		int itrn = mdpCalculationEngine.getMdpModelInputParameters().getnumberOfIterations();

		numIterations = 0;
		while(!finished) {
//		while(numIterations < itrn) {
			double maxError = -1.;
			//			double maxError = accuracyThreshold;

			for(PortfolioAssetsState state : statesSet) {

				//				double utility = mdp.getUtility(state);
				//				double reward = mdp.getReward(state);

				double maxCurrentUtil = -1e30;
				MDPPortfolioChoiceAction optAction = null;
				double utility = 0;
				double reward = 0;
				// The following while loop computes \max_a\sum T(s,a,s')U(s')startState
				for(MDPPortfolioChoiceAction newAction : policySet){
					if(this.mdpCalculationEngine.isInPermisibleRange(startAction, newAction, 
							this.mdpCalculationEngine.maximumPermissbleChangeInWeight)){
						//utility = this.mdpCalculationEngine.getFundingSurplusUtility(state, startAction, newAction);
						reward = this.mdpCalculationEngine.getReward(startState, state, startAction, newAction);

						TreeSet<Transition> T = state.getAdjacencyListTransitions();
						double nextUtil = 0;
						for(Transition transition : T){
							double prob = transition.getTransitionProbability();
							PortfolioAssetsState sPrime = (PortfolioAssetsState) transition.getDestinationState();
							nextUtil += (prob * mdpCalculationEngine.getFundingSurplusUtility(sPrime, startAction, newAction));//approximation assuming startAction 
						}
						//					int s = T.size();
						//					
						//					for(int i=0; i<s; ++i) {
						//						Transition t=(Transition)T..get(i);
						//						double prob=t.probability;
						//						State sPrime=t.nextState;
						//						nextUtil += (prob * mdp.getUtility(sPrime));
						//					}

						if(nextUtil > maxCurrentUtil){
							maxCurrentUtil = Rounding.roundSevenDecimals(nextUtil);
							optAction = newAction;
						}
					}
					startAction = newAction;
				}

				maxCurrentUtil = reward + gamma*maxCurrentUtil;
				//				mdp.setUtility(state, maxCurrentUtil);
				//				mdp.setAction(state, optAction);
				//Now add the best policy (a) for state (s) and the corresponding maximum utility to a value function for record keeping
				valueFunctions.add(new ValueFunction(itn, maxCurrentUtil, state, optAction));

				double currentError = Math.abs(maxCurrentUtil-utility);
				utility = maxCurrentUtil;
				startState = state;
				
				if(currentError>maxError){
					maxError = currentError;
				}

			}

			numIterations ++;
			if(maxError < threshold){
				finished = true;
			}

			System.out.println("Iteration: "+numIterations + " error:"+maxError);

		}

		return numIterations;
	}
	
	
	   // execute one trial
		@Override
		public void runTrial() {
//			System.out.println( "Learning! ("+epochs+" epochs)\n" );
//			for( int i = 0 ; i < epochs ; i++ ) {
//					if( ! running ) break;
			
					runEpoch(mdpCalculationEngine.getMdpModelInputParameters().getnumberOfIterations(), 
							mdpCalculationEngine.getMdpModelInputParameters().getGammaDiscountFactor(),
							mdpCalculationEngine.getMdpModelInputParameters().getAccuracyThreshold());
//					
//				if( i % 1000 == 0 ) {
//				    // give text output
//				    timer = ( System.currentTimeMillis() - timer );
//				    System.out.println("Epoch: " + i + " : " + timer);
//				    timer = System.currentTimeMillis();
//				}
//			}
		}

		   // execute one trial
			@Override
			public void runTrial(int iterations, double gamma, double accuracyThreshold) {
//				System.out.println( "Learning! ("+epochs+" epochs)\n" );
//				for( int i = 0 ; i < epochs ; i++ ) {
//						if( ! running ){
//							break;
//						}
				
						runEpoch(mdpCalculationEngine.getMdpModelInputParameters().getnumberOfIterations(), 
								mdpCalculationEngine.getMdpModelInputParameters().getGammaDiscountFactor(),
								mdpCalculationEngine.getMdpModelInputParameters().getAccuracyThreshold());
						
//					if( i % 1000 == 0 ) {
//					    // give text output
//					    timer = ( System.currentTimeMillis() - timer );
//					    System.out.println("Epoch: " + i + " : " + timer);
//					    timer = System.currentTimeMillis();
//					}
//				}
			}

	
	
	// execute one epoch solve(int iterations, double gamma, double accuracyThreshold)
	public void runEpoch(int iterations, double gamma, double accuracyThreshold) {
	
		// Reset state to start position defined by the world.
		qLearningState = (PortfolioAssetsState) mdpCalculationEngine.resetState();
		int itn = 0;
		this.gamma = gamma;
		ArrayList<MDPPortfolioChoiceAction> policySet =  this.mdpCalculationEngine.actionsSpace;
		ArrayList<PortfolioAssetsState> statesSet  = this.mdpCalculationEngine.portfolioStates; 
		MDPPortfolioChoiceAction startAction = this.mdpCalculationEngine.currentAction;
		MDPPortfolioChoiceAction oldAction = this.mdpCalculationEngine.currentAction;
		PortfolioAssetsState startState = this.mdpCalculationEngine.currentState;
		qLearningState = startState;
		
		
		switch(learningMethod) {
		
		case VALUE_ITERATION : {
			//			solve(iterations, gamma, accuracyThreshold);
			evaluatePolicy(iterations, gamma, accuracyThreshold);
			break;
		}
	    
		case Q_LEARNING : {
	    
	    	double this_Q;
		    double max_Q;
		    double new_Q;
		    

			while(itn < iterations) {
		        if( ! running ) {
			    	break;
			    }
		        else{
		        	MDPPortfolioChoiceAction newAction = selectAction(qLearningState);
		        	newQLearningState = (PortfolioAssetsState) mdpCalculationEngine.getNextState();
		        	//public double getReward(PortfolioAssetsState arg0, PortfolioAssetsState arg1, MDPPortfolioChoiceAction arg2, MDPPortfolioChoiceAction arg3)
//		        	System.out.println( "newQLearningState ...: " + newQLearningState.toString());

		        	qLearningReward = mdpCalculationEngine.getReward(qLearningState, newQLearningState, oldAction, newAction);
		        	//getFundingSurplusUtility(PortfolioAssetsState sPrime, MDPPortfolioChoiceAction startAction, MDPPortfolioChoiceAction newAction) 
		        	this_Q = mdpCalculationEngine.getFundingSurplusUtility(qLearningState, oldAction, newAction);//this seems counter intuitive
		        	//However it can be interpreated as given the economic environment the investor is in what is the surplus from the new action

		        	//getMaximumFundingSurplusUtility(PortfolioAssetsState sPrime, MDPPortfolioChoiceAction startAction)
		        	max_Q = mdpCalculationEngine.getMaximumFundingSurplusUtility(newQLearningState, newAction);
		        	//from above this translates to what is the maximum surplus attainable moving forward from the new state 
		        	//baring in mind the action chosen at the action taken at the previous state

		        	// Calculate new Value for Q
		        	new_Q = this_Q + alpha * (qLearningReward + gamma * max_Q - this_Q);
		        	valueFunctions.add(new ValueFunction(itn, new_Q, qLearningState, newAction));
		        	//policy.setQValue( state, action, new_Q );

		        	oldAction = newAction;
		        	//startAction = newAction;
		        	// Set state to the new state.
		        	qLearningState = newQLearningState;
		        	itn++;
		        }
			}
	    }

	case SARSA : {
	    
	    double this_Q;
	    double next_Q;
	    double new_Q;

	    while(itn < iterations) {
		
		    if( ! running ) {
		    	break;
		    }
		    
		    newQLearningState = (PortfolioAssetsState) mdpCalculationEngine.getNextState();
		    
		    
//		    reward = thisWorld.getReward();
		    
		    MDPPortfolioChoiceAction newAction = selectAction(qLearningState);   		    
		    qLearningReward = mdpCalculationEngine.getReward(startState, qLearningState, startAction, oldAction);
//		    	newaction = selectAction( newstate );
		    
		   	this_Q = mdpCalculationEngine.getFundingSurplusUtility(qLearningState, startAction, oldAction);
//		    this_Q = policy.getQValue( state, oldAction);
		    next_Q = mdpCalculationEngine.getFundingSurplusUtility(newQLearningState, oldAction, newAction); 
//		    next_Q = policy.getQValue( newstate, newaction );
		    
		    new_Q = this_Q + alpha * (qLearningReward + gamma * next_Q - this_Q);
		    
		    valueFunctions.add(new ValueFunction(itn, new_Q, qLearningState, newAction));
//		    policy.setQValue( state, action, new_Q );
		    
		    // Set state to the new state and action to the new action.
		    startAction = oldAction;
		    oldAction = newAction;
		    startState = qLearningState;
		    qLearningState = newQLearningState;
//		    state = newstate;
//		    action = newaction;
		    itn++;
		}
		
	}

	case Q_LAMBDA : {
	    
		
		} // case
	} // switch
    } 
	
	
	
	private MDPPortfolioChoiceAction selectAction(PortfolioAssetsState state) {

		ArrayList<QValue> qValues = mdpCalculationEngine.getQValuesAt(state);
		MDPPortfolioChoiceAction selectedAction = null;
		int selectedActionIndex = -1;
		
		switch (actionSelection) {

		case E_GREEDY : {

			random = false;
			double maxQ = -Double.MAX_VALUE;
			int maxDV = 0;

			//Explore
			if ( Math.random() < epsilon ) {
				selectedAction = null;
				selectedActionIndex = -1;
				random = true;
			}
			else {

				for(int action = 0; action < qValues.size(); action++ ) {

					if( qValues.get(action).getUtilityValue_v_s() > maxQ ) {
						selectedAction = qValues.get(action).getPolicyAction();
						selectedActionIndex = action;
						maxQ = qValues.get(action).getUtilityValue_v_s();
						maxDV = 0;
					}
					else if(qValues.get(action).getUtilityValue_v_s() == maxQ) {
						maxDV++;
					}
				}

				if( maxDV > 0 ) {
					int randomIndex = (int) ( Math.random() * ( maxDV + 1 ) );
					selectedAction = qValues.get(randomIndex).getPolicyAction();
					selectedActionIndex = randomIndex;
				}
			}

			// Select random action if all qValues == 0 or exploring.
			if ( selectedAction == null || selectedActionIndex == -1 ) {

				// System.out.println( "Exploring ..." );
				int explorationActionIndex = (int) (Math.random() * qValues.size());
				selectedAction = qValues.get(explorationActionIndex).getPolicyAction();
				selectedActionIndex = explorationActionIndex;
			}

			// Choose new action if not valid. isInPermisibleRange(MDPPortfolioChoiceAction action, MDPPortfolioChoiceAction actionNext, double maxChange)
			while(!mdpCalculationEngine.isInPermisibleRange(mdpCalculationEngine.currentAction, selectedAction,
					mdpCalculationEngine.maximumPermissbleChangeInWeight) ) {
				int explorationActionIndex = (int) (Math.random() * qValues.size());
				selectedAction = qValues.get(explorationActionIndex).getPolicyAction();
				// System.out.println( "Invalid action, new one:" + selectedAction);
			}

			break;
		}

		case SOFTMAX : {
			int action;
			double prob[] = new double[qValues.size()];
			double sumProb = 0;

			for(action = 0 ; action < qValues.size() ; action++ ) {
				prob[action] = Math.exp( qValues.get(action).getUtilityValue_v_s() / temp );
				sumProb += prob[action];
			}
			for(action = 0 ; action < qValues.size() ; action++ ){
				prob[action] = prob[action] / sumProb;
			}

			boolean valid = false;
			double rndValue;
			double offset;

			while(!valid) {

				rndValue = Math.random();
				offset = 0;

				for(action = 0 ; action < qValues.size() ; action++ ) {
					if(!valid){
						if(rndValue > offset && rndValue < offset + prob[action]){
							//	System.out.println( " Action Select Condition met... ");
							selectedAction = qValues.get(action).getPolicyAction();
							//	System.out.println( "Action " + ((MDPPortfolioChoiceAction) selectedAction).label() + " chosen with " + prob[action] );

							if(mdpCalculationEngine.isInPermisibleRange(mdpCalculationEngine.currentAction, selectedAction,
									mdpCalculationEngine.maximumPermissbleChangeInWeight)){
								valid = true;
							}
						}
					}
					offset += prob[action];
				}

//				if(mdpCalculationEngine.isInPermisibleRange(mdpCalculationEngine.currentAction, selectedAction,
//						mdpCalculationEngine.maximumPermissbleChangeInWeight)){
//					valid = true;
//				}
			}
				break;

			}
		}

		return selectedAction;
		}

	
	

}
