import jas.engine.Sim;









public class MDPInvestorAgent extends MDPMarkovAgent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected State currentState = null;
	protected MDPModelContext universe;
	protected MDPPortfolioChoiceAction lastAction = null;
	protected State oldState = null;
	protected boolean learningEnabled = true;
	protected double reward = 0.0D;
	double expectedEquityLoss;
	double expectedCreditDefault;
	int reset;

	
	
	/**
	 * THis class is the test generalisation of the LAPF or investor class
	 * @param s
	 *
	 */
	public MDPInvestorAgent(MDPModelContext s) {
		
		universe = s;
		setInitialState(universe.getCurrentState());
		
	}
	
	
	public MDPInvestorAgent(String agentName, String agentSector, String agentTemprerement, double totalAssets,
			Economy econ) {
		// TODO Auto-generated constructor stub
		super(agentName, agentSector, agentTemprerement, totalAssets, econ);
		this.reset = this.economy.getLoanResetPeriod();
	}

	

	public State getCurrentState() {
		return this.currentState;
	}

	public void setInitialState(State s) {
		this.oldState = s;
		this.currentState = s;
	}

	
	public void setLearningStatus(boolean learningStatus) {
		this.learningEnabled = learningStatus;
	}
	
	
	
	
	
	/**
	 * The makePortfolioDecision method determines what the optimal allocation of assets is for the investor agent
	 * 
	 * it calls both the agent's RLCalculationEngine and SolverAlgorithm classes in retrieving the best portfolio choices given the 
	 * environment/economic parameters and transition probabilities 
	 * 
	 * ArrayList<MDPPortfolioChoiceAction>();
		MDPStatesDomain = new ArrayList<MDPCapitalMarketsState>()
	 */
	public void makeMDPPortfolioDecision(){
//		if(this.economy.getCurrentSimTime() == 0){
		this.MDPPolicyDomain = Economy.actionsSpace; 
		this.MDPStatesDomain = Economy.MDPStates;
		if(Sim.getAbsoluteTime() == 0){
			this.initializeMDP();
		}
		else{
			updateMarkovDecisionProcessDomains();
			determineOptimalMDPPolicy();
		}
	}
	
	
	public void initializeMDP(){
		setMDPAgentExpectations();
		initialiseMarkovDecisionProcess();
		updateMarkovDecisionProcessDomains();
		initialiseMDPSolverAlgorithm();
	}
	
	
	private void setMDPAgentExpectations() {
		// TODO Auto-generated method stub
		if(this.getAgentTemprement() == "Bull"){
			this.expectedCreditDefault = this.economy.RLLearningConfiguration.bullCreditDefaults;
			this.expectedEquityLoss = this.economy.RLLearningConfiguration.equityDefaults;
		} else
			if(this.getAgentTemprement() == "Bear"){
				this.expectedCreditDefault = this.economy.RLLearningConfiguration.bearCreditDefaults;
				this.expectedEquityLoss = this.economy.RLLearningConfiguration.equityDefaults;
			} else
				if(this.getAgentTemprement() == "Rational"){
					this.expectedCreditDefault = this.economy.RLLearningConfiguration.rationalCreditDefaults;
					this.expectedEquityLoss = this.economy.RLLearningConfiguration.equityDefaults;
				} else
					if(this.getAgentTemprement() == "Passive"){
						this.expectedCreditDefault = this.economy.RLLearningConfiguration.passiveCreditDefaults;
						this.expectedEquityLoss = this.economy.RLLearningConfiguration.equityDefaults;
					}

	}


	private void initialiseMarkovDecisionProcess(){
//		System.out.println("Initialising Markov Decision Process...");
		this.mdp = new MarkovDecisionProcess(this.MDPStatesDomain, this.MDPPolicyDomain, 
				this.economy.mdpModelInputParameters, this.expectedEquityLoss, this.expectedCreditDefault, 
				this.globalLiabilityExpense, this.reset);
	}
	
	
	private void initialiseMDPSolverAlgorithm(){
		this.solverAlgorithm = new PortfolioMDPRLModelSolver(this.mdp, 
				this.mdp.getMdpModelInputParameters());
	}
	
	public void updateMarkovDecisionProcessDomains(){
		this.mdp.updateActionsSpace(this.MDPPolicyDomain);
		this.mdp.updateMDPStates(this.MDPStatesDomain);
	}
	
	public void determineOptimalMDPPolicy(){
		this.solverAlgorithm.runTrial();
		this.setMDPPolicySet();
		this.setDecisionDelta();
	}
	
	
	private void setDecisionDelta(){
		this.deltas = this.mdp.deltas;
	}
	
	
	public double[] getDecisionDelta(){
		return this.deltas;
	}
	

}
