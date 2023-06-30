import java.util.ArrayList;








public class MDPMarkovAgent extends EconomicAgent{
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
	
	protected Economy economy; 
	
	protected Enviroment environment;
	
	GeoEconoPoliticalSpace world;


	double optimalAssetAllocationStrtegyReturn;
	
	
	protected static int MDPMarkovAgentID = 0;
	private final int agentID;
	private int agentDataBaseID;
	private String agentName;
	private String agentSector;
	private String agentTemprement;
	
	MDPPortfolioChoiceAction bestPolicy;//this is created with the type MDPPortfolioChoiceAction
	ArrayList<MDPPortfolioChoiceAction> MDPPolicyDomain;
	ArrayList<MDPPortfolioChoiceAction> bestMDPPolicyHistory;
	
	
	ArrayList<MDPCapitalMarketsState> MDPStatesDomain;//collection of PortfolioAssetsState objects 
	//used in creating the MDP states
	MDPCapitalMarketsState currentMDPState;
	ArrayList<ArrayList<MDPCapitalMarketsState>> MDPStatesHistoy;
	ArrayList<QValue> qValues = new ArrayList<QValue>();
	MarkovDecisionProcess mdp;
	ArrayList<MDPPolicy> MDPPolicySet;
	double[] deltas;
	
	
	public MDPMarkovAgent(double solvencyMarg, double totalAssets){
		
		MDPMarkovAgentID++;
		this.agentID = MDPMarkovAgentID;
		this.setTotalNotionalAssets(totalAssets);
		this.setSolvencyMargin(solvencyMarg);
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicyDomain = new ArrayList<MDPPortfolioChoiceAction>();
		MDPStatesDomain = new ArrayList<MDPCapitalMarketsState>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
	}
	

	public MDPMarkovAgent(){
		
		MDPMarkovAgentID++;
		this.agentID = MDPMarkovAgentID;
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicyDomain = new ArrayList<MDPPortfolioChoiceAction>();
		MDPStatesDomain = new ArrayList<MDPCapitalMarketsState>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
		
	}
	
	
	public MDPMarkovAgent(int iDRSSD, String agentName, String agentSector, double lapfRegulatorySolvancyRatio, double totalAssets,
			double totalLiabilities, Economy econ, Enviroment env,
			GeoEconoPoliticalSpace world) {
		// TODO Auto-generated constructor stub
		MDPMarkovAgentID++;
		this.agentID = MDPMarkovAgentID;
		this.agentDataBaseID = iDRSSD;
		this.agentName = agentName;
		this.agentSector = agentSector;
		this.setEntityID();
		this.economy = econ;
		this.environment = env;
		this.world = world;
		this.setLiabilities(totalLiabilities);
		this.setTotalNotionalAssets(totalAssets);
		this.setSolvencyMargin(lapfRegulatorySolvancyRatio);
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicyDomain = new ArrayList<MDPPortfolioChoiceAction>();
		MDPStatesDomain = new ArrayList<MDPCapitalMarketsState>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
	}
	
	public MDPMarkovAgent(int iDRSSD, String agentName, String agentSector, double lapfRegulatorySolvancyRatio, double totalAssets,
			Economy econ, Enviroment env,
			GeoEconoPoliticalSpace world) {
		// TODO Auto-generated constructor stub
		MDPMarkovAgentID++;
		this.agentID = MDPMarkovAgentID;
		this.agentDataBaseID = iDRSSD;
		this.agentName = agentName;
		this.agentSector = agentSector;
		this.setEntityID();
		this.economy = econ;
		this.environment = env;
		this.world = world;
		this.setTotalNotionalAssets(totalAssets);
		this.setSolvencyMargin(lapfRegulatorySolvancyRatio);
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicyDomain = new ArrayList<MDPPortfolioChoiceAction>();
		MDPStatesDomain = new ArrayList<MDPCapitalMarketsState>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
	}

	
	public MDPMarkovAgent(String agentName, String agentSector, String agentTemprerement, double totalAssets,
			Economy econ) {
		// TODO Auto-generated constructor stub
		MDPMarkovAgentID++;
		this.agentID = MDPMarkovAgentID;
		this.agentName = agentName;
		this.agentSector = agentSector;
		this.setAgentTemprement(agentTemprerement);
		this.setEntityID();
		this.economy = econ;
		this.setTotalNotionalAssets(totalAssets);
		bestMDPPolicyHistory = new ArrayList<MDPPortfolioChoiceAction>();
		MDPPolicyDomain = new ArrayList<MDPPortfolioChoiceAction>();
		MDPStatesDomain = new ArrayList<MDPCapitalMarketsState>();
		MDPPolicySet = new ArrayList<MDPPolicy>();
	}



	public void setOptimalAssetWeights(double cashWght, double equityWght, double creditWght){
		this.optimalCashAllocation = cashWght;
		this.optimalEquityAssetAllocation = equityWght;
		this.optimalCreditAssetAllocation = creditWght;
	}
			

	public void setCIR_ThetaCreditAssetMeanValueExpectedDefaults(double newTheta){
		this.cir_ThetaCreditAssetMeanValueExpectedDefaults = newTheta;
	}
	
	public String getagentName() {
		return agentName;
	}
	
	public String getagentSector() {
		return agentSector;
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
		this.globalLiabilityExpense = globalLiabilityExpense;
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

	
	public String getAgentTemprement() {
		return agentTemprement;
	}


	public void setAgentTemprement(String agentTemprement) {
		this.agentTemprement = agentTemprement;
	}
	
	public MarkovDecisionProcess getMarkovDecisionProcess(){
		return this.mdp;
	}

	
	public void setMDPPolicySet(){
		this.MDPPolicySet = this.mdp.MDPPolicySet;
	}
	
	public ArrayList<MDPPolicy> getMDPPolicySet(){
		return this.MDPPolicySet;
	}

	private void setEntityID(){
		this.EntityID = this.agentName + " "+ this.agentSector + " "+ this.agentDataBaseID;
	}
	
	@Override
	public String getEntityID(){
		return EntityID;
	}


	public int getagentID() {
		return agentID;
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
