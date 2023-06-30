import java.util.ArrayList;



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
public class PolicyIterationSolver extends SolverAlgorithm{

	/**
	 * In the base instance the MDP is described by the CapitalMarketsIEnvironment class
	 * @param mdp
	 */
	double vStar;
	
	
	
	public PolicyIterationSolver(MDPCapitalMarketsInvestorContext mdp) {
		super(mdp);
		// TODO Auto-generated constructor stub
		
	}
	
	/**
	 * This is where the policy iteration algorithm is implemented according to the alghorithm 
	 * defined in Sottun and Barto Figrue 4.3 pp 98.
	 * V(s) = Sigm{ P(s,s',a(s)) *[R(s,s',a(s)] + gamma*V(s')]
	 * For simplicity of exposition in this portfolio selection case V(s') is assumed to be equal to
	 * the expected returns in state s' where s = f{a(s)=a(s'), equity State return, credit state return, cash state return}
	 * and where a(s) = a(s') = f{Equity weight, credit weight, cash weight} is the policy being evaluated.
	 * 
	 * The objective here is to loop through all states s' reachable from the initial state s always taking policy action a(s)
	 * Also note that since 
	 * 1: each MDPCapitalMarketsState is characterised by a PortfolioAssetState of form {equityDir, creditDir} and 
	 * portfolio weights {equityWeight, creditWeight, cashWeight} 
	 * 2: the evaluated policy is a portfolio weights choice,
	 * the set of selected MDPCapitalMarketsState states s' member of S_eval will necessarily be characterized by the portfolio weight
	 * selection defined by the policy being evaluated. Hence only a small subset S_eval of the entire MDPCapitalMarketsState space S will be 
	 * evaluated. A very rough worst scenario guess would be that each policy evaluation consists of 9million states.
	 * 
	 *    THe algorithm goes as follows
	 *    1: set initial state and policy action
	 *    2: collect state space of all reachable states given selected policy action
	 *    3: solve V(s) for each of the n states in the reachable set 
	 *    4: if Absolute[v - V(s)] > delta > epsilon for state s under policy a(s) then
	 *    		delta = Absolute[V(s) - v] 
	 *    		repeat step 3-4 until [v - V(s)] < delta < epsilon
	 *    		if [v - V(s)] < delta < epsilon holds then store V(s)
	 *    5: 
	 *  
	 * @param iterations
	 * @param gamma
	 */
	public void evaluatePolicy(int iterations, double gamma){
		this.delta = 0.0;
		this.gamma = gamma;
		double v = 0.0;
		PortfolioAction policy = this.initialPolicy;
		MDPCapitalMarketsState state = this.mdpState;
		ArrayList<MDPCapitalMarketsState> statesSet  = mdp.getCurrentReachableStates(state, policy); 
		for(int itn = 0; itn < iterations; itn++){
			for(int s = 0; s < statesSet.size(); s++){
				
			}
			
		}
		
		
	}
	
	

}
