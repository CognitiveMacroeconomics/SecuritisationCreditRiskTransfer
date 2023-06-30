

import java.util.TreeSet;




public class MDPCapitalMarketsState extends State{
	
	public static int ASSET_ALLOCATION_MDP_STATE_ID = 0;
	private int stateID;

	
	/**
	 * the following are the two primary objects that define the MDP State
	 * that is
	 * 
	 * 1: The current porfolio weights for each asset
	 * 
	 * and 
	 * 
	 * 2: The asset class state combinations in which the investor can invest
	 * 
	 * Note that each MDP stae is made up of a single porfolio weighting object and a single 
	 * portfolio asset state object
	 * 
	 * Since the MDP states are a combination of both weights and porfolio asset class states
	 * assuming that there are m weights and n portfolio asset class states, there will be
	 * m*n MDP states in total.
	 * 
	 *  This implies a much larger transition space of which based on the actions permitted the majority will
	 *  have a transition probability of zero
	 * 
	 */
	PortfolioWeights portfolioWeights;
	PortfolioAssetsState portfolioAssetState;
	PropertiesState proxyPropState;
	
	double cashAssetWeight;
	double equityAssetWeight;
	double creditAssetWeight;
	double equityAssetExpectedReturn;
	double creditAssetExpectedReturn;
	double cashAssetExpectedReturn;
	int numberOfAssets;
	double portfolioStateExpectedReturn;
	
	TreeSet<Transition> adjacencyListTransition = new TreeSet<Transition> ();

	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CONSTRUCTOR METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * The following is the default constructor for the 2 asset class model to be used for the LAPF 
	 * analysis in my PhD thesis. 
	 * 
	 * This 2 model framework actually is a 3 asset framework where the third
	 * asset is cash put into a bank account with a constant return and zero probability of loss
	 * @param eqtyLevel
	 * @param crdLevel
	 * @param equityState
	 * @param creditState
	 */
	public MDPCapitalMarketsState(MDPModelContext env, PortfolioWeights pWeights, PortfolioAssetsState pAssetState, int numAssets) {
		this.proxyPropState = new PropertiesState(pAssetState.getProperties().length);//Creates PropertiesState with 1 property
		ASSET_ALLOCATION_MDP_STATE_ID++;
		stateID = ASSET_ALLOCATION_MDP_STATE_ID;
		for(int i = 0; i< numAssets; i++){
			this.proxyPropState.prop[i] = pAssetState.getProperties()[i];//Value of state property 1 (direction of equity asset returns). 
		}
		
		this.setNumberOfAssets();
		this.setEquityAssetExpectedReturn(pAssetState.getEquityAssetExpectedReturn());//expected return on equity 
		this.setCreditAssetExpectedReturn(pAssetState.getCreditAssetExpectedReturn());//expected return on credit
		this.setCashAssetExpectedReturn(pAssetState.getCashAssetExpectedReturn());//expected return on cash
		this.equityAssetWeight = pWeights.getDefaultModelEquityWeight();//Value of state property 1 (direction of equity asset returns). 
		this.creditAssetWeight = pWeights.getDefaultModelCreditWeight();//Value of state property 2 (direction of credit asset Returns). 
		this.cashAssetWeight = pWeights.getDefaultModelCashWeight();//Value of state property 1 (direction of equity asset returns).
		this.setDefaultStateReturn();
		portfolioWeights = pWeights;
		portfolioAssetState = pAssetState;
	}
	
	
	/**
	 * The following is the default constructor for the 2 asset class model to be used for the LAPF 
	 * analysis in my PhD thesis. 
	 * 
	 * This 2 model framework actually is a 3 asset framework where the third
	 * asset is cash put into a bank account with a constant return and zero probability of loss
	 * @param eqtyLevel
	 * @param crdLevel
	 * @param equityState
	 * @param creditState
	 */
	public MDPCapitalMarketsState(int MDPStateID, PortfolioWeights pWeights, PortfolioAssetsState pAssetState, int numAssets) {
		this.proxyPropState = new PropertiesState(pAssetState.getProperties().length);//Creates PropertiesState with 1 property
		stateID = MDPStateID;
		for(int i = 0; i< numAssets; i++){
			this.proxyPropState.prop[i] = pAssetState.getProperties()[i];//Value of state property i (direction of asset return). 
		}
		
		this.setNumberOfAssets();
		this.setEquityAssetExpectedReturn(pAssetState.getEquityAssetExpectedReturn());//expected return on equity 
		this.setCreditAssetExpectedReturn(pAssetState.getCreditAssetExpectedReturn());//expected return on credit
		this.setCashAssetExpectedReturn(pAssetState.getCashAssetExpectedReturn());//expected return on cash
		this.equityAssetWeight = pWeights.getDefaultModelEquityWeight();//Value of state property 1 (direction of equity asset returns). 
		this.creditAssetWeight = pWeights.getDefaultModelCreditWeight();//Value of state property 2 (direction of credit asset Returns). 
		this.cashAssetWeight = pWeights.getDefaultModelCashWeight();//Value of state property 1 (direction of equity asset returns).
		this.setDefaultStateReturn();
		portfolioWeights = pWeights;
		portfolioAssetState = pAssetState;
	}
	
	
	
	public MDPCapitalMarketsState(MDPModelContext env) {
	}
	
	

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<STATIC CONSTRUCTION METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * MDP States are created by calling this and similar static methods. This gives better control as to which type of model 
	 * is being created and the number of asset classes in the portfolio allocation
	 * @param equityStateLevel
	 * @param creditStateLevel
	 * @param equityState
	 * @param creditState
	 */
	public static MDPCapitalMarketsState createDefaultAssetChoiceModelMDPState(MDPModelContext env, PortfolioWeights pWeights, 
			PortfolioAssetsState pAssetState, int numAsts){
		MDPCapitalMarketsState mdpState = new MDPCapitalMarketsState(env, pWeights, pAssetState, numAsts);
		return mdpState;
	}
	
	
	
	public static MDPCapitalMarketsState createAgentLevelPortfolioAllocationMDPState(int MDPStateID, PortfolioWeights pWeights, 
			PortfolioAssetsState pAssetState, int numAsts){
		MDPCapitalMarketsState mdpState = new MDPCapitalMarketsState(MDPStateID, pWeights, pAssetState, numAsts);
		return mdpState;
	}
	
	public void addAdjecentListTransitionsStateProbability(MDPCapitalMarketsState curntState, MDPCapitalMarketsState adjcentState, double transitionProbability){
		this.adjacencyListTransition.add(new Transition(curntState, adjcentState, transitionProbability));
	}
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	private void setCreditAssetExpectedReturn(double creditAssetReturn) {
		// TODO Auto-generated method stub
		creditAssetExpectedReturn = creditAssetReturn;
	}

	private void setEquityAssetExpectedReturn(double equityAssetReturn) {
		// TODO Auto-generated method stub
		equityAssetExpectedReturn = equityAssetReturn;
		
	}
	
	private void setCashAssetExpectedReturn(double cshAssetReturn) {
		// TODO Auto-generated method stub
		cashAssetExpectedReturn = cshAssetReturn;
	}


	/**
	 * @return the portfolioWeights
	 */
	public PortfolioWeights getPortfolioWeights() {
		return portfolioWeights;
	}



	/**
	 * @param portfolioWeights the portfolioWeights to set
	 */
	public void setPortfolioWeights(PortfolioWeights portfolioWeights) {
		this.portfolioWeights = portfolioWeights;
	}



	/**
	 * @return the portfolioAssetState
	 */
	public PortfolioAssetsState getPortfolioAssetState() {
		return portfolioAssetState;
	}



	/**
	 * @param portfolioAssetState the portfolioAssetState to set
	 */
	public void setPortfolioAssetState(PortfolioAssetsState portfolioAssetState) {
		this.portfolioAssetState = portfolioAssetState;
	}



	/**
	 * @return the equityAssetWeight
	 */
	public double getEquityAssetWeight() {
		return equityAssetWeight;
	}



	/**
	 * @param equityAssetWeight the equityAssetWeight to set
	 */
	public void setEquityAssetWeight(double equityAssetWeight) {
		this.equityAssetWeight = equityAssetWeight;
	}


	/**
	 * @param creditAssetWeight the creditAssetWeight to set
	 */
	public void setCreditAssetWeight(double creditAssetWeight) {
		this.creditAssetWeight = creditAssetWeight;
	}


	/**
	 * @return the creditAssetWeight
	 */
	public double getCreditAssetWeight() {
		return creditAssetWeight;
	}


	/**
	 * @param creditAssetWeight the creditAssetWeight to set
	 */
	public void setCashAssetWeight(double cshAssetWeight) {
		this.cashAssetWeight = cshAssetWeight;
	}

	
	/**
	 * @return the creditAssetWeight
	 */
	public double getCashAssetWeight() {
		return cashAssetWeight;
	}





	/**
	 * @return the adjacencyListTransition
	 */
	public TreeSet<Transition> getAdjacencyListTransition() {
		return adjacencyListTransition;
	}



	/**
	 * @param adjacencyListTransition the adjacencyListTransition to set
	 */
	public void setAdjacencyListTransition(
			TreeSet<Transition> adjListTransition) {
		this.adjacencyListTransition = adjListTransition;
	}



	/**
	 * @return the equityAssetExpectedReturn
	 */
	public double getEquityAssetExpectedReturn() {
		return equityAssetExpectedReturn;
	}



	/**
	 * @return the creditAssetExpectedReturn
	 */
	public double getCreditAssetExpectedReturn() {
		return creditAssetExpectedReturn;
	}
	
	
	/**
	 * @return the creditAssetExpectedReturn
	 */
	public double getCashAssetExpectedReturn() {
		return cashAssetExpectedReturn;
	}
	

	private void setNumberOfAssets() {
		// TODO Auto-generated method stub
		this.numberOfAssets = this.proxyPropState.prop.length;
	}


	public int getNumberOfAssets() {
		// TODO Auto-generated method stub
		return numberOfAssets;
	}
	
	private void setDefaultStateReturn(){
		this.portfolioStateExpectedReturn = cashAssetWeight*(1+cashAssetExpectedReturn) 
				+ equityAssetWeight*(1+equityAssetExpectedReturn) 
				+ creditAssetWeight*(1+creditAssetExpectedReturn);
		
	}


	public double getPortfolioStateExpectedReturn(){
		return this.portfolioStateExpectedReturn;
		
	}

	/**
	 * @param stateID the stateID to set
	 */
	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	
	public int getStateID(){
		return this.stateID;
	}
	
	@Override
	public String toString(){
		return "MDP State: " + this.getStateID() + " Equity Asset State: " + this.getPortfolioAssetState().getProperties()[1]
				+ " Credit Asset State: " + this.getPortfolioAssetState().getProperties()[2]
						+ " Equity Asset Expected Return: " + this.getEquityAssetExpectedReturn()
								+ " Credit Asset Expected Return: " + this.getCreditAssetExpectedReturn()
								+ " Risk Free Asset Return: " + this.getCashAssetExpectedReturn()
								+ " Equity Asset Weight: " + this.getEquityAssetWeight()
								+ " Credit Asset Weight: " + this.getCreditAssetWeight()
								+ " Risk Free Asset Weight: " + this.getCashAssetWeight();
	}
	
	
	public PropertiesState getProxyPropState(){
		return this.proxyPropState;
	}
	
	
	/**
	 * CompareTo method used to compare the current state to the input state
	 * if the states are identical in terms of their equity, credit and cash weights 
	 * as well as their equity, credit and cash returns direction,
	 * then return true otherwise the method will return false
	 * @param s
	 * @return
	 */
	public boolean compareTo(MDPCapitalMarketsState s){
		double sew = s.getEquityAssetWeight();
		double scdtw = s.getCreditAssetWeight();
		double scshw = s.getCashAssetWeight();
		double ew = this.getEquityAssetWeight();
		double cdtw = this.getCreditAssetWeight();
		double cshw = this.getCashAssetWeight();
		int sed = s.getProxyPropState().prop[1];
		int scdtd = s.getProxyPropState().prop[2];
		int scshd = s.getProxyPropState().prop[0];
		int ed = this.getProxyPropState().prop[1];
		int cdtd = this.getProxyPropState().prop[2];
		int cshd = this.getProxyPropState().prop[0];
		
		if(	(sed == ed) && (scdtd == cdtd) && (scshd == cshd) 
				&& 
				(sew == ew) && (scdtw == cdtw) && (scshw == cshw)
				){
			return true;
		} else{
			return false;
		}
		
		
	}



}
