

public class Transition implements Comparable{
	
	/**
	 * This class is a generic store to house the transition probabilities from the starting state to the
	 * destination or end state with a given probabilities prob 
	 */
	public static int TRANSITION_ID = 0;
	private int transitionID;
	State startState;
	State endState;
	double probability;
	
	MDPCapitalMarketsState startMDPState;
	MDPCapitalMarketsState endMDPState;
	
	//class constructor
	public Transition(PropertiesState ss, PropertiesState es, double prob){
		TRANSITION_ID++;
		this.transitionID = TRANSITION_ID;
		this.startState = ss;
		this.endState = es;
		this.probability = prob;
	}
	
	
	//getters and setters
	
	public Transition(MDPCapitalMarketsState curntState,
			MDPCapitalMarketsState adjcentState, double transitionProbability) {
		// TODO Auto-generated constructor stub
		TRANSITION_ID++;
		this.transitionID = TRANSITION_ID;
		this.startMDPState = curntState;
		this.endMDPState = adjcentState;
		this.probability = transitionProbability;
	}


	/**
	 * this method return the destination state
	 * @return
	 */
	public State getDestinationState(){
		
		return this.endState;
	}
	
	
	/**
	 * this method return the destination state
	 * @return
	 */
	public MDPCapitalMarketsState getDestinationMDPState(){
		
		return this.endMDPState;
	}

	/**
	 * This is a generic method to return the transition probability
	 * @return
	 */
	public double getTransitionProbability(){
		return this.probability;
	}
	
	public int getTransitionID(){
		return this.transitionID;
	}


	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		int d = 0;
		if(arg0 instanceof Transition){
			d = this.transitionID - ((Transition) arg0).getTransitionID();
		}
		return d;
	}
	
	
	

}
