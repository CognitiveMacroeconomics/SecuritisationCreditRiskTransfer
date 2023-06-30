import java.util.ArrayList;
import java.util.Vector;



public class SolverAlgorithm {

	//Learning Types
	public static final int VALUE_ITERATION = 0;
	public static final int Q_LEARNING = 1;
	public static final int SARSA = 2;
	public static final int Q_LAMBDA = 3;

	//Action selection types
	public static final int E_GREEDY = 1;
	public static final int SOFTMAX = 2;

	public int learningMethod;
	public int actionSelection;

	ArrayList<MDPModelContext> MDP_Model_History;
	ArrayList<ValueFunction> valueFunctions;
	ArrayList<ArrayList<ValueFunction>> valueFunctionsHistory;
	ArrayList<ArrayList<Double>> deltaHistory;
	MDPCapitalMarketsInvestorContext mdp;
	QLearningInvestorCalculationEngine mdpCalculationEngine;
	MarkovDecisionProcess markovDecisionProcessContext;
	ValueIteration valueIteration;

	/**
	 * The error threshold to stop the iteration 
	 */
	double epsilon = 1e-6;

	int numIterations;
	int epochs = 5;
	int horizon;
	PortfolioAction initialPolicy;
	boolean isPortfolioWieghtChoice;
	int policyK;
	double alpha;
	double gamma;
	double delta;
	double lambda;
	double qLearningReward;
	double temp = 1;
	long timer;
	boolean running = true;
	Runnable runnable;
	boolean random = false;
	MDPCapitalMarketsState mdpState;
	PortfolioAssetsState newQLearningState;
	PortfolioAssetsState qLearningState;
	Vector trace = new Vector();


	
	public SolverAlgorithm(MarkovDecisionProcess mdp, MDPModelInputParameters mdpInputParameters){
		setMarkovDecisionModelContext(mdp);
		initiatedValueIteration(this.markovDecisionProcessContext);
		learningMethod = mdpInputParameters.getRLLearningType();
	}
	

	private void initiatedValueIteration(
			MarkovDecisionProcess markovDecisionProcessContext2) {
		// TODO Auto-generated method stub
		this.valueIteration = new ValueIteration(this.markovDecisionProcessContext);
	}


	private void setMarkovDecisionModelContext(MarkovDecisionProcess mdp2) {
		// TODO Auto-generated method stub
		this.markovDecisionProcessContext = mdp2;
	}


	public ValueIteration getMarkovDecisionProcessValueIterationAlgorithm(){
		return this.valueIteration;
	}
	
	
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<PRE-HARM CODE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public SolverAlgorithm(MDPCapitalMarketsInvestorContext mdp){
		MDP_Model_History = new ArrayList<MDPModelContext>();
		valueFunctionsHistory = new ArrayList<ArrayList<ValueFunction>>();
		valueFunctions = new ArrayList<ValueFunction>();
		deltaHistory = new ArrayList<ArrayList<Double>>();
		setMDPSolverDefaults(mdp);
		delta = 0;
		gamma = 0;
		temp = 1;
	}


	
	public SolverAlgorithm(QLearningInvestorCalculationEngine mdpCalcEngine){
		this.mdpCalculationEngine = mdpCalcEngine;
		MDP_Model_History = new ArrayList<MDPModelContext>();
		valueFunctionsHistory = new ArrayList<ArrayList<ValueFunction>>();
		valueFunctions = new ArrayList<ValueFunction>();
		deltaHistory = new ArrayList<ArrayList<Double>>();
		alpha = this.mdpCalculationEngine.getMdpModelInputParameters().getRLLearningRateAlpha();
		lambda = this.mdpCalculationEngine.getMdpModelInputParameters().getRLLearningLambda();
		learningMethod = this.mdpCalculationEngine.getMdpModelInputParameters().getRLLearningType();
		actionSelection = this.mdpCalculationEngine.getMdpModelInputParameters().getRLActionSelectionType();
		epochs = this.mdpCalculationEngine.getMdpModelInputParameters().getNumberOfDecisionEpochs();
		horizon = epochs*250;
		delta = 0;
		gamma = 0;
		temp = 1;
	}

	public SolverAlgorithm(){
		MDP_Model_History = new ArrayList<MDPModelContext>();
		valueFunctionsHistory = new ArrayList<ArrayList<ValueFunction>>();
		valueFunctions = new ArrayList<ValueFunction>();
		deltaHistory = new ArrayList<ArrayList<Double>>();
		delta = 0;
		gamma = 0;
		temp = 1;
	}

	/**
	 * Set the stop threshold.
	 * @param epsilon The error threshold.
	 */
	public void setError(double epsilon){
		this.epsilon = epsilon;
		//		System.out.println("New Epsilon/Variation Threshold: " + this.epsilon);
	}

	public int getNumberIterations() {
		return numIterations;
	}

	private void setMDPSolverDefaults(MDPCapitalMarketsInvestorContext mdp){
		this.mdp = mdp;
		mdpState = mdp.getCurrentState();
		initialPolicy = mdp.getCurrentPolicy();
		isPortfolioWieghtChoice = mdp.isPortfolioWieghtChoice();
		MDP_Model_History.add(mdp);
	}

	public void evaluatePolicy(int iterations, double gamma, double accuracyThreshold){

	}


	public int solve(int iterations, double gamma, double accuracyThreshold){
		return numIterations = 0;
	}


	public MDPPortfolioChoiceAction extractBestPolicy(){
		return null;
	}

	public void updateMDP(MDPCapitalMarketsInvestorContext mdp) {
		setMDPSolverDefaults(mdp);
		//		System.out.println("New Value Iteration Solver has been updated with the MDP Context: " + this.mdp.toString());
		//		
		//		System.out.println("New Value Iteration Solver MDP Context History Size: " + this.MDP_Model_History.size());
	}
	
	public void runTrial(int iterations, double gamma, double accuracyThreshold) {
		
	}

	public void runTrial() {
		
	}

	
	/**
	 * @return the mDP_Model_History
	 */
	public ArrayList<MDPModelContext> getMDP_Model_History() {
		return MDP_Model_History;
	}

	/**
	 * @param mDP_Model_History the mDP_Model_History to set
	 */
	public void setMDP_Model_History(ArrayList<MDPModelContext> mDP_Model_History) {
		MDP_Model_History = mDP_Model_History;
	}

	/**
	 * @return the valueFunctions
	 */
	public ArrayList<ValueFunction> getValueFunctions() {
		return valueFunctions;
	}

	/**
	 * @param valueFunctions the valueFunctions to set
	 */
	public void setValueFunctions(ArrayList<ValueFunction> valueFunctions) {
		this.valueFunctions = valueFunctions;
	}

	/**
	 * @return the valueFunctionsHistory
	 */
	public ArrayList<ArrayList<ValueFunction>> getValueFunctionsHistory() {
		return valueFunctionsHistory;
	}

	/**
	 * @param valueFunctionsHistory the valueFunctionsHistory to set
	 */
	public void setValueFunctionsHistory(
			ArrayList<ArrayList<ValueFunction>> valueFunctionsHistory) {
		this.valueFunctionsHistory = valueFunctionsHistory;
	}

	/**
	 * @return the deltaHistory
	 */
	public ArrayList<ArrayList<Double>> getDeltaHistory() {
		return deltaHistory;
	}

	/**
	 * @param deltaHistory the deltaHistory to set
	 */
	public void setDeltaHistory(ArrayList<ArrayList<Double>> deltaHistory) {
		this.deltaHistory = deltaHistory;
	}

	public State resetState(){
		return null;
	}

	public State getNextState(){
		return null;
	}






}
