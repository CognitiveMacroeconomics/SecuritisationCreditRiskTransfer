import java.util.ArrayList;
import java.util.Random;


public class ValueIteration {

	private double epsilon;
	private double threshold;
	private MDP mdp;
	private MarkovDecisionProcess markovDecisionProcess;
	private double[][] Q;
	private double[] V;
	private int total_states;
	private int total_actions;  // total number of actions
	private double discount;
	private ArrayList<Double> deltas;

	public ValueIteration() {
		epsilon = 0.01;
		mdp = new MDP();
		total_states = mdp.get_total_states();
		total_actions = mdp.get_total_actions();
		discount = mdp.get_discount_factor();
		
		if(discount == 1){
			threshold = this.epsilon;
		}
		else{
			threshold = this.epsilon*((1-discount)/(2*discount));
		}

		Q = new double[total_states][total_actions];
		for (int i = 0; i < total_states; i++) {
			for (int j = 0; j < total_actions; j++) {
				Q[i][j] = 0;
			}
		}
		
		V = new double[total_states];
		for (int i = 0; i < total_states; i++) {
			V[i] = 0;
		}
		
	}
	
	public ValueIteration(MarkovDecisionProcess mdp2){
//		System.out.println("Building Value Iteration Solver...");
		markovDecisionProcess = mdp2;
		total_states = markovDecisionProcess.get_total_states();
		total_actions = markovDecisionProcess.get_total_actions();
		discount = markovDecisionProcess.get_discount_factor();
		epsilon = markovDecisionProcess.getMdpModelInputParameters().getEpsilonError();
		if(discount == 1){
			threshold = (this.epsilon);
		}
		else{
			threshold = (this.epsilon*((1-discount)/(discount)));
		}

		Q = new double[total_states][total_actions];
		for (int i = 0; i < total_states; i++) {
			for (int j = 0; j < total_actions; j++) {
				Q[i][j] = 0;
			}
		}
		
		V = new double[total_states];
		for (int i = 0; i < total_states; i++) {
			V[i] = 0;
		}
//		System.out.println("Value Iteration Solver Build Complete...");
	}

	public static void main(String[] args) {
		ValueIteration vi = new ValueIteration();
		vi.solve();
		vi.compute_optimal_policy();
	}
	
	public void updateMDP(MarkovDecisionProcess mdp2){
		this.markovDecisionProcess = mdp2;
	}
	
	public void solve() {
	/**  Computes optimal V and optimal Q **/
		System.out.println("Value Iteration Solver is running please wait for solution...");
		deltas = new ArrayList<Double>();
		int iter = 0;
		double Delta = 2*epsilon;
		while (Delta > threshold) {
			Delta = 0;
			iter++;
			System.out.println("Iteration: " + iter);
			for (int s = 0; s < total_states; s++) {
			
				// update action-values
				int num_actions = mdp.get_num_actions(s);
				for (int a = 0; a < num_actions; a++) {
					double[] next_state_probs = mdp.get_next_states(s,a);
					double new_Q = mdp.get_average_reward(s,a);
					for (int s_next = 0; s_next < total_states; s_next++) {
						new_Q += discount*next_state_probs[s_next]*V[s_next];
					}
					Q[s][a] = new_Q;
				}
				
				double new_V = Q[s][0];
				for (int a = 1; a < num_actions; a++) {
					new_V = Math.max(new_V, Q[s][a]);
				}
				
				// update Delta
				Delta = Math.max(Delta, Math.abs(new_V - V[s]));
				V[s] = new_V;
			}
			System.out.println("Delta: " + Delta);
		}
	}
	
	
	public void compute_optimal_policy() {
		
		int[] optimal_policy = new int[total_states];
		for (int s = 0; s < total_states; s++) {
			double max_Q = Q[s][0];
			int optimal_a = 0;
			
			int num_actions = mdp.get_num_actions(s);
			for (int a = 1; a < num_actions; a++) {
				if (Q[s][a] > max_Q) {
					max_Q = Q[s][a];
					optimal_a = a;
				}
			}
			optimal_policy[s] = optimal_a;
		}
		
		// print optimal policy:
		for (int s = 0; s < total_states; s++) {
			System.out.print(optimal_policy[s] + " ");
		}		
		System.out.println();
	}
	
	
	public void solveMarkovDecisionProcess() {
		/**  Computes optimal V and optimal Q **/
//			System.out.println("Value Iteration Solver is running please wait for solution...");
			deltas = new ArrayList<Double>();
			int iter = 0;
			double Delta = 10*threshold;
			while (Delta > threshold) {
				Delta = 0;
				iter++;
//				System.out.println("Iteration: " + iter);
				for (int s = 0; s < total_states; s++) {
					// update action-values
					int num_actions = markovDecisionProcess.get_num_actions(s);
					for (int a = 0; a < num_actions; a++) {
						double[] next_state_probs = markovDecisionProcess.get_next_states(s,a);
						int total_reachable_states = next_state_probs.length;
						double new_Q = markovDecisionProcess.get_average_reward(s,a);
						for (int s_next = 0; s_next < total_reachable_states; s_next++) {
							if(markovDecisionProcess.isAbsoluteReturns() == true){
								new_Q += discount*next_state_probs[s_next]*V[s_next];
							}
							else{
								new_Q *= Math.exp(-this.discount);
							}
						}
						Q[s][a] = new_Q;
					}
					
					double new_V = Q[s][0];
					for (int a = 1; a < num_actions; a++) {
						new_V = Math.max(new_V, Q[s][a]);
					}
					
					// update Delta
					Delta = Math.max(Delta, Math.abs(new_V - V[s]));
					V[s] = new_V;
				}
				deltas.add(Delta);
//				System.out.println("Delta: " + Delta);
			}
			markovDecisionProcess.setDeltas(deltas);
		}
	
	
	public void computeOptimalMarkovDecisionProcessPolicy() {
//		System.out.print("\n");
//		System.out.println("computing Optimal Markov Decision Process Policy...");
//		System.out.print("\n");
		int[] optimal_policy = new int[total_states];
//		System.out.println("total_states: " + total_states);

		for (int s = 0; s < total_states; s++) {
			double max_Q = Q[s][0];
			int optimal_a = 0;
			int num_actions = markovDecisionProcess.get_num_actions(s);
			for (int a = 1; a < num_actions; a++) {
				if (Q[s][a] > max_Q) {
					max_Q = Q[s][a];
					optimal_a = a;
				}
			}
			optimal_policy[s] = optimal_a;
			markovDecisionProcess.setOptimalPolicy(s, optimal_a);
		}
		
		// print optimal policy:
//		for (int s = 0; s < total_states; s++) {
//			System.out.print("s: " + s);
//			System.out.print(" " + optimal_policy[s] + " ");
//		}		
//		System.out.println();
	}
		
	

}

class MDP {
	
	private static int total_states;
	private int total_actions;
	private double avg_reward;
	private double discount;
	
	public MDP() {
		total_states = 50;
		total_actions = 10;
		avg_reward = 1.0;
		discount = 0.9;
	}
	
	public double[] get_next_states(int state, int action){
		double[] next_states;
		next_states = new double[total_states];
		double sum = 0;
		for (int i = 0; i < total_states; i++) {
			next_states[i] = 1.23;
			sum += 1.23;
		}
		// normalize
		for (int i = 0; i < total_states; i++) {
			next_states[i] = next_states[i]/sum;
		}
		return next_states;
	}
	
	int get_total_states() {
		return total_states;
	}
	int get_num_actions(int state) {
		return total_actions;
	}
	
	int get_total_actions() {
		return total_actions;
	}
		
	double get_average_reward(int state, int action) {
		Random rnd = new Random();
		return avg_reward = Math.pow(Math.pow(action,3), action+state);
	}
		
	double get_discount_factor() {
		return discount;
	}
}