

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * 
 * @author Oluwasegun Bewaji
 * 
 * This class is used to generate the successive states of the capital market as defined by the combination of states each of the assets in the 
 * economy or capital market can be in.
 * 
 * That is, given a set of states and a number of episodes/iterations it defines the path of states for the simulation
 * 
 * It is used as an implementation of an environment that sits above the PIQLE IEnvironments. 
 * 
 * It is used to ensure that all agents have face the same capital market. 
 * 
 * The PIQLE IEnvironment class is then used as a basis to define the specific state will be in at each episode. That is it will  
 *
 */
public class MDPStatesPathEngine {
	
	
	
	public PortfolioAssetsState currentState;
	
	private int numberOfEpisodes;
	
	private ArrayList<PortfolioAssetsState> capitalMarketAssetsStates = new ArrayList<PortfolioAssetsState>();
	
	private static ArrayList<PortfolioAssetsState> capitalMarketAssetsStatesPath = new ArrayList<PortfolioAssetsState>();
	
	
	/**
	 * Class Constructor. 
	 * 
	 * Though the variables defined in this class are for the most part static, to initialise them, a constructor must be declared 
	 * with the set of all possible combination of states each asset in the simulation can take  
	 * @param states
	 */
	public MDPStatesPathEngine (ArrayList<PortfolioAssetsState> states, int eps){
		capitalMarketAssetsStates = states;
		numberOfEpisodes = eps;
		setInitialState();
		generateStatesPath(numberOfEpisodes);
	}
	
	
	public MDPStatesPathEngine (){
		
	}
	
	
	
	/**
	 * selects the initial state
	 * 
	 *  It does this by randomly selecting a state from the given set of states
	 *  
	 *  The selected state is then added to the states path
	 */
	public static PortfolioAssetsState setInitialState(ArrayList<PortfolioAssetsState> states){
		int Min = 0;
		int Max = states.size();
		int sel = Min + (int)(Math.random() * ((Max - Min)));
		return states.get(sel);
	}
	
	
	/**
	 * selects the initial state
	 * 
	 *  It does this by randomly selecting a state from the given set of states
	 *  
	 *  The selected state is then added to the states path
	 */
	private void setInitialState(){
		int Min = 0;
		int Max = capitalMarketAssetsStates.size();
		int sel = Min + (int)(Math.random() * ((Max - Min)));
		currentState = capitalMarketAssetsStates.get(sel);
		getCapitalMarketAssetsStatesPath().add(currentState);
	}
	
	
	/**
	 * 
	 * This generateStatesPath method loops through the transitions of the current state's portfolio asset state
	 * add the probability to the cumulative probability and then check if the p random number is less than or equal to the cumulative
	 * if so select the transition and equate with the tempTransition
	 * 
	 * Note that transitions (defined in the Transition class) are a tree set of states and transition probabilities between states.
	 *  Each state will have multiple transitions to all other states. 
	 *  Most of these will have a zero probability
	 *  
	 *  The generateStatesPath method will randomly generate a path of transitions between states based on the probability p of the next 
	 *  state being selected as the destination state.
	 *  
	 *  if p is less than the probability of transitioning from the last state in the path to its adjacent states as in the selected transitions tree set
	 *  then that adjacent state is selected as the next state in the path
	 *  
	 * 
	 * 
	 * @param nPaths
	 */
	public void generateStatesPath(int nPaths) {
		// TODO Auto-generated method stub
		Random rnd = new Random();
		PortfolioAssetsState destState;
		for(int i = 0; i < nPaths; i++){
			double cumulativeProbability = 0.0; //set a cumulative probability
			double zero = Rounding.roundThreeDecimals(0.000);
			double tp = 0;
			double p = 0;
			int itn = 0;
			PortfolioAssetsState tmpState = getCapitalMarketAssetsStatesPath().get(getCapitalMarketAssetsStatesPath().size()-1);
			Iterator<Transition> itr = tmpState.getAdjacencyListTransitions().iterator();
			Transition transition;
			do {
				tp = 0;
				p = 0;
				transition = itr.next();
				tp = Rounding.roundThreeDecimals(transition.getTransitionProbability()); 
				cumulativeProbability += tp;
				p = rnd.nextDouble();
				if(p < cumulativeProbability){
					if(tp > zero){
						destState =  ((PortfolioAssetsState) transition.getDestinationState());
						getCapitalMarketAssetsStatesPath().add(destState);
					}
				}
			itn++;
			}while(p > cumulativeProbability || tp == zero);
		}
	}


	public ArrayList<PortfolioAssetsState> getCapitalMarketAssetsStatesPath() {
		return capitalMarketAssetsStatesPath;
	}


	public void setCapitalMarketAssetsStatesPath(
			ArrayList<PortfolioAssetsState> capitalMarketAssetsStatesPath) {
		MDPStatesPathEngine.capitalMarketAssetsStatesPath = capitalMarketAssetsStatesPath;
	}
	
	
	/**
	 * selects the next state
	 * 
	 *  It does this by randomly selecting a state from the current states transition probability adjacency list based on the
	 *  probability of transitioning between the current state and the next
	 *  
	 *  The selected state is then returned as the next state
	 */
	public static PortfolioAssetsState generateNextState(PortfolioAssetsState state){
		Random rnd = new Random();
		double cumulativeProbability = 0.0; //set a cumulative probability
		PortfolioAssetsState destState = state;
		double zero = 0.000;
		double tp = 0;
		double p = 0;
		Iterator<Transition> itr =state.getAdjacencyListTransitions().iterator();
		Transition transition;
		do {
			System.gc();
			tp = 0;
			transition = itr.next();
			tp = (transition.getTransitionProbability()); 
			cumulativeProbability += tp;
			p = rnd.nextDouble();
			if(p < cumulativeProbability){
				if(tp > zero){
					destState =  ((PortfolioAssetsState) transition.getDestinationState());
				}
			}
		}while(p > cumulativeProbability || tp == zero);
		return destState;
	}
	

	
	
	

}
