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
public class ValueIterationSolver extends SolverAlgorithm{

	/**
	 * In the base instance the MDP is described by the CapitalMarketsIEnvironment class
	 * @param mdp
	 */
	double vStar;
//	ArrayList<ValueFunction> valueFunctions;
//	ArrayList<ArrayList<ValueFunction>> valueFunctionsHistory;
//	ArrayList<ArrayList<Double>> deltaHistory;
	MDPPortfolioChoiceAction bestPolicy = null;
	
	
	
	public ValueIterationSolver(MDPCapitalMarketsInvestorContext mdp) {
		super(mdp);
		// TODO Auto-generated constructor stub
		
		System.out.println("Creating New Value Iteration Solver...");
//		System.out.println("New Value Iteration Solver has been created for the MDP Context: " + this.mdp.toString());
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
		ArrayList<MDPPortfolioChoiceAction> policySet =  mdp.getActionsPopulationSet();
		ArrayList<MDPCapitalMarketsState> statesSet  = mdp.getStatesPopulationSet(); 
		if(valueFunctions != null){
			valueFunctions.clear();
		}
		ArrayList<Double> deltas = new ArrayList<Double>();
		
		//initiate V(s)
		double V_s = 0;
		double v;
		StateSetAssetAllocation reachableStatesDomain;
		System.out.println("New Value Iteration Solution has been initiated please wait for your solution....");
		
		do {
			itn++;
			this.delta = 0;
				for(int s = 0; s < statesSet.size(); s++){
					v = Rounding.roundSevenDecimals(V_s);
					double maxA = 0.0;
					double sum;
					PortfolioAction optAction = null;
					for(int x = 0; x < policySet.size(); x++){
						double stateTransitionRewardFromArbInitial = mdp.getReward(mdp.getCurrentState(), statesSet.get(s), policySet.get(x));
						reachableStatesDomain = this.mdp.reachable(statesSet.get(s), policySet.get(x));
						//compute the summation piece of the V(s) <- max(a) SIGMA[P,R,s,a]
						//that is compute the total utility attainable for policy (a) given state (s) across all reachable states (s') having enacted policy (a)
						//at state (s)
						sum = backUpActionRussellNorvig(statesSet.get(s), reachableStatesDomain, 
								policySet.get(x));
						
						//now determine the policy a that returns the maximum utility given the initial starting state (s)
						//this is the max(a) piece of V(s) <- max(a) SIGMA[P,R,s,a]
						//set the max(a) to sum if the new sum is larger than the previous sum
						if(Rounding.roundSevenDecimals(sum) > maxA){
							maxA = Rounding.roundSevenDecimals(sum);
							V_s = stateTransitionRewardFromArbInitial + gamma * Rounding.roundSevenDecimals(maxA);
							optAction = policySet.get(x);
						}//end if statement
					}
					//Now add the best policy (a) for state (s) and the corresponding maximum utility to a value function for record keeping
					valueFunctions.add(new ValueFunction(itn, V_s, statesSet.get(s), optAction));
					
					//now determine if the maximum utility outcome is stable/convergent.
					//note that if the previous maximum utility derived from the previous best policy is less than the 
					//maximum utility derived from the new best policy, then v - V(s) < 0 which mean one is losing out on
					//utility increases if one sticks with the previous best policy. Therefore, one would switch policy to the new
					//and thus the previous delta will remain valid. 
					//Consequently delta will always be a positive value to indicate one is improving on previous policies
					if(s == 0 || this.delta > Rounding.roundSevenDecimals(Math.abs(v - V_s))){
						this.delta = Rounding.roundSevenDecimals(Math.abs(v - V_s));
						deltas.add(this.delta);
					}
					System.out.println("New Value Iteration previous maximum : " + v);
					System.out.println("New Value Iteration current maximum : " + V_s);
					System.out.println("New Value Iteration Solution delta : " + this.delta);
					System.out.println("New Value Iteration Solution epsilon*((1-gamma)/gamma)) : " + Rounding.roundSevenDecimals(this.epsilon*((1-gamma)/gamma)));
					System.out.println("New Value Iteration Solution itn : " + itn);
				}
			this.deltaHistory.add(deltas);
		}while(this.delta > Rounding.roundSevenDecimals(this.epsilon*((1-gamma)/gamma)));
		// value iteration terminal condition which follows on from the localize stability condition
		
		valueFunctionsHistory.add(valueFunctions);
		System.out.println("New Value Iteration Solution has been derived for the MDP Context: " + this.mdp.toString());
		System.out.println("New Value Iteration Solution is located in the Value Function space of size: " + valueFunctions.size());
		System.out.println("New Value Iteration Solution delta : " + this.delta);
		System.out.println("New Value Iteration optimal policy is: " 
		+ ((MDPPortfolioChoiceAction) valueFunctions.get(valueFunctions.size()-1).getPolicyAction()).label());
	}
	
	
	
	private double backUpActionRussellNorvig(MDPCapitalMarketsState mdpCapitalMarketsState,
			StateSetAssetAllocation reachableStatesDomain,
			PortfolioAction portfolioAction) {
		// TODO Auto-generated method stub
		//System.out.println("backUpAction Method is Running... " );
		double sum = mdpCapitalMarketsState.getPortfolioStateExpectedReturn();
		
		
		ArrayList<MDPCapitalMarketsState> reachableStates = reachableStatesDomain.getStatesDomain();
//		System.out.println("Total number of reachable states: " + reachableStates.size());
		TreeSet<Transition> initialStateTransitionsList = mdpCapitalMarketsState.getPortfolioAssetState().getAdjacencyListTransitions();
		for(int nState = 0; nState < reachableStates.size(); nState++){
			int[] endStateProp = reachableStates.get(nState).getPortfolioAssetState().getProperties();
			double transitionProb = 0;
			double stateReturn  = reachableStates.get(nState).getPortfolioStateExpectedReturn();
			double stateTransitionReward = mdp.getReward(mdpCapitalMarketsState, reachableStates.get(nState), portfolioAction);
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
	
	
	
	private double backUpActionSuttonBarto(MDPCapitalMarketsState mdpCapitalMarketsState,
			StateSetAssetAllocation reachableStatesDomain,
			PortfolioAction portfolioAction, double gamma) {
		// TODO Auto-generated method stub
		double sum = mdpCapitalMarketsState.getPortfolioStateExpectedReturn();
		
		
		ArrayList<MDPCapitalMarketsState> reachableStates = reachableStatesDomain.getStatesDomain();
//		System.out.println("Total number of reachable states: " + reachableStates.size());
		TreeSet<Transition> initialStateTransitionsList = mdpCapitalMarketsState.getPortfolioAssetState().getAdjacencyListTransitions();
		for(int nState = 0; nState < reachableStates.size(); nState++){
			int[] endStateProp = reachableStates.get(nState).getPortfolioAssetState().getProperties();
			double transitionProb = 0;
			double stateReturn  = reachableStates.get(nState).getPortfolioStateExpectedReturn();
			double stateTransitionReward = mdp.getReward(mdpCapitalMarketsState, reachableStates.get(nState), portfolioAction);
			//now find the appropriate transition probability.
			//loop through the transitions set of the initial state
			//till the new state (nState) portfolio asset properties are found to match that
			//of the destination state of the selected transition from the initial state's transitions set
			for(Transition iSTList : initialStateTransitionsList){
				if(identicalEndStates(iSTList, endStateProp) == true){
					transitionProb = iSTList.getTransitionProbability();
				}
			}
			sum += transitionProb * ( stateTransitionReward + gamma * stateReturn);
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

	
	/**
	 * This method returns the best policy action by looping through all entries in the value iteration ArrayList and 
	 * selecting the action where the largest utility is observed
	 * @return
	 */
	@Override
	public MDPPortfolioChoiceAction extractBestPolicy(){
//		System.out.println("Extracting the best policy now, please wait... ");
		double max = 0;
		for(int i = 0; i < valueFunctions.size(); i++){
			double vs = valueFunctions.get(i).getUtilityValue_v_s();
			if(vs > max){
				max = vs;
				bestPolicy = (MDPPortfolioChoiceAction) valueFunctions.get(i).getPolicyAction();
			}
		}
//		System.out.println("The best policy is " + bestPolicy.label());
		return bestPolicy;
		
	}
	
	
	@Override
	public ArrayList<ArrayList<Double>> getDeltaHistory(){
		return this.deltaHistory;
	}
	
	

}
