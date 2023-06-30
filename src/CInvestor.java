import jas.engine.Sim;

import java.util.ArrayList;








public class CInvestor extends EconomicAgent{
	double liabilities;

	double assetValue;
	double totalNotionalAssets;
	int numOfAssets;
	double costOfFunding;
	double globalAssetReturn;
	double globalLiabilityExpense;
	double fundingSurplus;
	double solvencyMargin;
	double liquidityBuffer;
	double expectedContractualRequirements;
	double optimalCashAllocation;
	double optimalEquityAssetAllocation;
	double optimalCreditAssetAllocation;
	double cir_ThetaCreditAssetMeanValueExpectedDefaults;
	double expectedEquityLoss;
	double expectedCreditDefault;
	double employeeWages;
	double contributionRate;
	double equityAssetReturn;
	double cashAssetReturn;
	double equityAssetReturnPrevious;
	double creditAssetReturn;
	double markToMarketLossesTraditionalAssets;
	double markToMarketLossesCreditAssets;
	double markToMarketLossesTotal;

	ArrayList creditAssetHoldings = new ArrayList<Double>();
	ArrayList traditionalAssetHoldings = new ArrayList<Double>();
	ArrayList PeriodOnPeriodNotional = new ArrayList<Double>();
	
	
	ArrayList<MDPPortfolioChoiceAction> bestMDPPolicyHistory;
	MDPPortfolioChoiceAction bestPolicy;//this is created with the type MDPPortfolioChoiceAction
	protected Economy economy; 
	public PortfolioAssetsState currentState;
	protected Enviroment environment;
	GeoEconoPoliticalSpace world;

	double optimalAssetAllocationStrtegyReturn;
	
	protected static int investorIDs = 0;
	private final int investorID;
	private int investorDataBaseID;
	private String investorName;
	private String investorSector;
	private ArrayList<MDPPolicy> MDPPolicySet;
	
	
	ArrayList<MDPCapitalMarketsState> MDPStates = new ArrayList<MDPCapitalMarketsState>();//collection of PortfolioAssetsState objects 
	//used in creating the MDP states
	MDPCapitalMarketsState currentMDPState;
	ArrayList<ArrayList<MDPCapitalMarketsState>> MDPStatesHistoy = new ArrayList<ArrayList<MDPCapitalMarketsState>>();
	ArrayList<QValue> qValues = new ArrayList<QValue>();
	

	
	public CInvestor(double solvencyMarg, double totalAssets){
		
		investorIDs++;
		this.investorID = investorIDs;
		this.setTotalNotionalAssets(totalAssets);
		this.setSolvencyMargin(solvencyMarg);
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
	}
	

	public CInvestor(){
		
		investorIDs++;
		this.investorID = investorIDs;
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
	}
	
	
	public CInvestor(int iDRSSD, String investorName, String investorSector, double lapfRegulatorySolvancyRatio, double totalAssets,
			double totalLiabilities, Economy econ, Enviroment env,
			GeoEconoPoliticalSpace world) {
		// TODO Auto-generated constructor stub
		investorIDs++;
		this.investorID = investorIDs;
		this.investorDataBaseID = iDRSSD;
		this.investorName = investorName;
		this.investorSector = investorSector;
		this.setEntityID();
		this.economy = econ;
		this.environment = env;
		this.world = world;
		this.setLiabilities(totalLiabilities);
		this.setTotalNotionalAssets(totalAssets);
		this.setSolvencyMargin(lapfRegulatorySolvancyRatio);
		this.setGlobalLiabilityExpense(econ.getLAPFPeriodicLiabilityPaymentRate());//this sets (1/(1+this.getSolvencyMargin())) * (1+ globalLiabilityExpense)
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
	}
	
	public CInvestor(int iDRSSD, String investorName, String investorSector, double lapfRegulatorySolvancyRatio, double totalAssets,
			Economy econ, Enviroment env,
			GeoEconoPoliticalSpace world) {
		// TODO Auto-generated constructor stub
		investorIDs++;
		this.investorID = investorIDs;
		this.investorDataBaseID = iDRSSD;
		this.investorName = investorName;
		this.investorSector = investorSector;
		this.setEntityID();
		this.economy = econ;
		this.environment = env;
		this.world = world;
		this.setTotalNotionalAssets(totalAssets);
		this.setSolvencyMargin(lapfRegulatorySolvancyRatio);
		this.setGlobalLiabilityExpense(econ.getLAPFPeriodicLiabilityPaymentRate());//this sets (1/(1+this.getSolvencyMargin())) * (1+ globalLiabilityExpense)
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
	}
	
	
	/**
	 * The makePortfolioDecision method determines what the optimal allocation of assets is for the investor agent
	 * 
	 * it calls both the agent's RLCalculationEngine and SolverAlgorithm classes in retrieving the best portfolio choices given the 
	 * environment/economic parameters and transition probabilities 
	 */
	public void makePortfolioDecision(){
		if(Sim.getAbsoluteTime() == 0){
			this.setInitialPolicy();
			setOptimalAssetWeights(this.bestMDPPolicy.getCashAssetWeightChoice(), this.bestMDPPolicy.getEquityAssetWeightChoice(),
					this.bestMDPPolicy.getCreditAssetWeightChoice());
		}
		else{
			updateRLPolicy();
//			System.out.println("Calculation Engine Updated");
			setOptimalAssetWeights(this.bestMDPPolicy.getCashAssetWeightChoice(), this.bestMDPPolicy.getEquityAssetWeightChoice(),
					this.bestMDPPolicy.getCreditAssetWeightChoice());
		}
	}

	
	public void setInitialPolicy(){
		int Min = 0;
		int Max = this.economy.RLLearningConfiguration.actionsSpace.size();
		int sel = Min + (int)(Math.random() * ((Max - Min)));
		if(this.environment.getInvestorCount() > 1) {
			this.bestMDPPolicy = this.economy.RLLearningConfiguration.actionsSpace.get(sel);
		} else{
			for(int i = 0; i < this.economy.RLLearningConfiguration.actionsSpace.size(); i++){
				if(this.economy.RLLearningConfiguration.actionsSpace.get(i).creditAssetWeightChoice == 0.4
						&& 
						this.economy.RLLearningConfiguration.actionsSpace.get(i).equityAssetWeightChoice == 0.5
						){
					this.bestMDPPolicy = this.economy.RLLearningConfiguration.actionsSpace.get(i);
				}
			}
		}
//		System.out.println(this.bestMDPPolicy.label());
		setMDPStatesSet(this.economy.RLLearningConfiguration.MDPStates);
		setCurrentMDPState(this.economy.currentState,this.bestMDPPolicy);
	}
	
	
	private void setMDPStatesSet(ArrayList<MDPCapitalMarketsState> mdpStates2) {
		// TODO Auto-generated method stub
		this.MDPStates = mdpStates2;
	}


	public void setCurrentMDPState(PortfolioAssetsState econState, MDPPortfolioChoiceAction currentAction) {
		// TODO Auto-generated method stub
		PortfolioAssetsState paState;
		double eqtyWeightMDP = 0;
		double crdtWeightMDP = 0;
		double cshWeightMDP = 0;
		double eqtyWeight = currentAction.getEquityAssetWeightChoice();
		double crdtWeight = currentAction.getCreditAssetWeightChoice();
		double cshWeight = currentAction.getCashAssetWeightChoice();
		
		for(MDPCapitalMarketsState s: this.MDPStates){
			paState = s.getPortfolioAssetState();
			eqtyWeightMDP = s.getEquityAssetWeight();
			crdtWeightMDP = s.getCreditAssetWeight();
			cshWeightMDP = s.getCashAssetWeight();
			if(econState.compareTo(paState) && (eqtyWeight == eqtyWeightMDP)
					 && (crdtWeight == crdtWeightMDP) && (cshWeight == cshWeightMDP)){
				this.currentMDPState = s;
//				System.out.println(this.currentMDPState.toString());
				break;
			}
		}
//		System.gc();
	}

	
	public void updateRLPolicy(){
//		System.out.println("update RLCalculation Engine Domains...");
		//determine investor's current MDP portfolio state given change in economc conditions and previous action
		setCurrentMDPState(this.economy.currentState,this.bestMDPPolicy);
		//determine the policy set and best action given the set of policies 
		definePolicySet();
		setOptimalPolicy();
	}
	
	/**
	 * this method sets the optimal policy as derived from the Markov decision process
	 */
	private void definePolicySet(){
		for(MDPMarkovAgent agnt : this.economy.getMDPMarkovAgentList()){
			if(this.temperament == agnt.getAgentTemprement()){
				MDPPolicySet = ((MDPInvestorAgent) agnt).getMDPPolicySet();
				break;
			}
		}
	}


	/**
	 * this method is used to select the optimal action given the current MDP state
	 */
	private void setOptimalPolicy(){
		MDPCapitalMarketsState s;
		//loop through the policy set for the current MDP portfolio state
		//once picked set the best action to the optimal policy action 
		//for the current MDP state
		for(MDPPolicy policy: this.MDPPolicySet){
			s = (MDPCapitalMarketsState) policy.getStateFrom();
			if(this.currentMDPState.compareTo(s) == true){
				this.bestMDPPolicy = policy.getPolicyAction();
				break;//once the optimal policy action is found for the current state
				//break out of the for-loop
			}
			
		}
	}

	public void setOptimalAssetWeights(double cashWght, double equityWght, double creditWght){
		this.optimalCashAllocation = cashWght;
		this.optimalEquityAssetAllocation = equityWght;
		this.optimalCreditAssetAllocation = creditWght;
	}
	
	
			

	public void setCIR_ThetaCreditAssetMeanValueExpectedDefaults(double newTheta){
		this.cir_ThetaCreditAssetMeanValueExpectedDefaults = newTheta;
	}
	
	public String getInvestorName() {
		return investorName;
	}
	
	public String getInvestorSector() {
		return investorSector;
	}
	
	public double getLiabilities() {
		return liabilities;
	}
	public void setLiabilities(double liabilities) {
		this.liabilities = liabilities;
	}
	public double getAssetValue() {
		return assetValue;
	}
	public void setAssetValue(double assetValue) {
		this.assetValue = assetValue;
	}
	public double getTotalNotionalAssets() {
		return totalNotionalAssets;
	}
	public void setTotalNotionalAssets(double totalNotionalAssets) {
		this.totalNotionalAssets = totalNotionalAssets;
	}
	public int getNumOfAssets() {
		return numOfAssets;
	}
	public void setNumOfAssets(int numOfAssets) {
		this.numOfAssets = numOfAssets;
	}
	public double getCostOfFunding() {
		return costOfFunding;
	}
	public void setCostOfFunding(double costOfFunding) {
		this.costOfFunding = costOfFunding;
	}
	public double getGlobalAssetReturn() {
		return globalAssetReturn;
	}
	public void setGlobalAssetReturn(double globalAssetReturn) {
		this.globalAssetReturn = globalAssetReturn;
	}
	public double getGlobalLiabilityExpense() {
		return globalLiabilityExpense;
	}
	public void setGlobalLiabilityExpense(double globalLiabilityExpense) {
		this.globalLiabilityExpense = (1/(1+this.getSolvencyMargin())) * (1+ globalLiabilityExpense);
	}
	public double getFundingSurplus() {
		return fundingSurplus;
	}
	public void setFundingSurplus(double fundingSurplus) {
		this.fundingSurplus = fundingSurplus;
	}
	
	public double getSolvencyMargin() {
		return solvencyMargin;
	}

	public void setSolvencyMargin(double solvencyMargin) {
		this.solvencyMargin = solvencyMargin;
	}
	
	public double getOptimalCreditAssetWieght() {
		return optimalCreditAssetAllocation;
	}


	public void setOptimalCreditAssetWieght(double optimalCreditAssetWieght) {
		this.optimalCreditAssetAllocation = optimalCreditAssetWieght;
	}
	
	public void setOptimalCashAssetWieght(double optimalCashAssetWieght) {
		this.optimalCashAllocation = optimalCashAssetWieght;
	}
	
	public double getOptimalCashAssetWieght() {
		return optimalCashAllocation;
	}


	public double getOptimalEquityAssetAllocation() {
		return optimalEquityAssetAllocation;
	}

	public void setOptimalEquityAssetAllocation(double optimalEquityAssetAllocation) {
		this.optimalEquityAssetAllocation = optimalEquityAssetAllocation;
	}
	

	
	public double getLiquidityBuffer() {
		return liquidityBuffer;
	}


	public void setLiquidityBuffer(double liquidityBuffer) {
		this.liquidityBuffer = liquidityBuffer;
	}
	
	public double getExpectedContractualRequirements(){
		return expectedContractualRequirements;
	}
	
	public double getOptimalAssetAllocationStrtegyReturn() {
		return optimalAssetAllocationStrtegyReturn;
	}

	public void setOptimalAssetAllocationStrtegyReturn(double optimalAssetAllocationStrtegyReturn) {
		optimalAssetAllocationStrtegyReturn = optimalAssetAllocationStrtegyReturn;
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


	
	private void setEntityID(){
		this.EntityID = this.investorName + " "+ this.investorSector + " "+ this.investorDataBaseID;
	}
	
	@Override
	public String getEntityID(){
		return EntityID;
	}


	public int getInvestorID() {
		return investorID;
	}
	

	
	@Override
	public void checkLife() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void reproduce() {
		// TODO Auto-generated method stub
		
	}

	

}
