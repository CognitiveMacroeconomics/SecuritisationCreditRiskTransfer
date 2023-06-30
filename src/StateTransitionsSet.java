


import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 
 * @author Oluwasegun Bewaji
 * 
 * This Utility Class is used to store the state to state transition probabilities in tabular form
 * It treats each Pair as the coordinate vector in which the transition probability is stored
 * this Pairs are used as the primary key for the table
 */
public class StateTransitionsSet {
	
	
	Map<Pair, Double> statesTransitionsSet;

	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Constructor>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public StateTransitionsSet(){
		this.statesTransitionsSet = new LinkedHashMap<>();
	}
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * This method puts the IState object into the states set
	 * @param t
	 */
	public void put(Pair pr, double trnProb){
		
		Integer mdpStateID = pr.getPairID();
		if(statesTransitionsSet.get(mdpStateID) == null){//check if the states is already in the set if not then add
			statesTransitionsSet.put(pr, trnProb);
		}
	
	}
	
	
}
