

import java.util.ArrayList;
import java.util.TreeSet;



public class PortfolioAssetsState  extends PropertiesState {

	
	public static int PORTFOLIO_ASSET_STATE_ID = 0;
	private int stateID;

	
	/**
	 * the following is a generic list of asset classes that the model can
	 * be used to allocate wealth across. To add additional asset classes to the
	 * portfolio mix, simply add the name of the asset class as a state variable
	 * and augment the relevant method implementations to account for this new asset class
	 */
	AssetState equityAssetClassState;
	AssetState creditAssetClassState;
	AssetState cashAssetClassState;
	AssetState fxAssetClassState;
	AssetState commodityAssetClassState;
	AssetState realEstateAssetClassState;
	
	double equityAssetExpectedReturn;
	double creditAssetExpectedReturn;
	double cashAssetExpectedReturn;
	double portfolioStateExpectedReturn;
	
	TreeSet<Transition> adjacencyListTransition = new TreeSet<Transition>();
	ArrayList<TreeSet<Transition>> adjacencyListTransitionHistory = new ArrayList<TreeSet<Transition>>();

	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CONSTRUCTOR METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public PortfolioAssetsState(int eqtyLevel) {
		super(1);
		PORTFOLIO_ASSET_STATE_ID++;
		stateID = PORTFOLIO_ASSET_STATE_ID;
		this.prop[0] = eqtyLevel;
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
	public PortfolioAssetsState(int eqtyLevel, int crdLevel, AssetState equityState, AssetState creditState) {
		super(2);//Creates PropertiesState with 1 property
		PORTFOLIO_ASSET_STATE_ID++;
		stateID = PORTFOLIO_ASSET_STATE_ID;
		this.prop[0] = eqtyLevel;//Value of state property 1 (direction of equity asset returns). 
		this.prop[1] = crdLevel;//Value of state property 2 (direction of credit asset Returns). 
		equityAssetClassState = equityState;
		creditAssetClassState = creditState;
		setEquityAssetExpectedReturn(equityAssetClassState);
		setCreditAssetExpectedReturn(creditAssetClassState);
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
	public PortfolioAssetsState(int eqtyLevel, int crdLevel, int cshLevel, AssetState equityState, AssetState creditState, AssetState cshState) {
		super(3);//Creates PropertiesState with 1 property
		PORTFOLIO_ASSET_STATE_ID++;
		stateID = PORTFOLIO_ASSET_STATE_ID;
		this.prop[0] = cshLevel;//Value of state property 0 (direction of cash asset returns). 
		this.prop[1] = eqtyLevel;//Value of state property 1 (direction of equity asset Returns). 
		this.prop[2] = crdLevel;//Value of state property 2 (direction of credit asset Returns). 
		equityAssetClassState = equityState;
		creditAssetClassState = creditState;
		cashAssetClassState = cshState;
		setEquityAssetExpectedReturn(equityAssetClassState);
		setCreditAssetExpectedReturn(creditAssetClassState);
		setCashAssetExpectedReturn(cashAssetClassState);
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
	public static PortfolioAssetsState createDefaultTwoAssetChoiceModelPortfolioAssetState(int equityStateLevel, int creditStateLevel, 
			AssetState equityState, AssetState creditState){
		PortfolioAssetsState mdpState = new PortfolioAssetsState(equityStateLevel, creditStateLevel, 
			equityState, creditState);
		return mdpState;
	}
	
	
	/**
	 * MDP States are created by calling this and similar static methods. This gives better control as to which type of model 
	 * is being created and the number of asset classes in the portfolio allocation
	 * @param equityStateLevel
	 * @param creditStateLevel
	 * @param cashStateLevel
	 * @param equityState
	 * @param creditState
	 * @param cashState
	 */
	public static PortfolioAssetsState createDefaultThreeAssetChoiceModelPortfolioAssettate(int equityStateLevel, int creditStateLevel, int cashStateLevel,  
			AssetState equityState, AssetState creditState, AssetState cashState){
		PortfolioAssetsState mdpState = new PortfolioAssetsState(equityStateLevel, creditStateLevel, cashStateLevel,
			equityState, creditState, cashState);
		return mdpState;
	}
	
	public void addAdjecentListTransitionsStateProbability(PortfolioAssetsState currentState, PortfolioAssetsState adjcentState, double transitionProbability){
		this.adjacencyListTransition.add(new Transition(currentState, adjcentState, transitionProbability));
//		System.out.println("addAdjecentListTransitionsStateProbability: " + adjacencyListTransition.size());
	}
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	private void setDefaultStateReturn(){
		this.portfolioStateExpectedReturn = (1+cashAssetExpectedReturn) 
				+ (1+equityAssetExpectedReturn) 
				+ (1+creditAssetExpectedReturn);
		
	}


	public double getPortfolioStateExpectedReturn(){
		return this.portfolioStateExpectedReturn;
		
	}
	
	private void setCreditAssetExpectedReturn(AssetState creditAssetClassState2) {
		// TODO Auto-generated method stub
		creditAssetExpectedReturn = creditAssetClassState2.getExpectedReturn();
	}

	private void setEquityAssetExpectedReturn(AssetState equityAssetClassState2) {
		// TODO Auto-generated method stub
		equityAssetExpectedReturn = equityAssetClassState2.getExpectedReturn();
		
	}
	
	
	private void setCashAssetExpectedReturn(AssetState cashAssetClassState2) {
		// TODO Auto-generated method stub
		cashAssetExpectedReturn = cashAssetClassState2.getExpectedReturn();
	}

	
	public double getCreditAssetExpectedReturn(){
		return this.creditAssetExpectedReturn;
	}
	public double getEquityAssetExpectedReturn(){
		return this.equityAssetExpectedReturn;
	}
	
	public double getCashAssetExpectedReturn(){
		return this.cashAssetExpectedReturn;
	}

	
	public TreeSet<Transition> getAdjacencyListTransitions(){
		return this.adjacencyListTransition;
	}
	
	public void clearTransitions(){
		this.adjacencyListTransitionHistory.add(this.adjacencyListTransition);
		this.adjacencyListTransition.clear();
	}

	
	public int getStateID(){
		return this.stateID;
	}
	
	
	@Override
	public String toString(){
		String aboutState = "Portfolio Asset State: " + this.stateID 
				+ " with Traditional Asset return direction: " + this.getProperties()[1]
				+" with Credit Asset return direction: " + this.getProperties()[2]
						+ " with Traditional Asset returns: " + this.getEquityAssetExpectedReturn()
								+" with Credit Asset returns: " + this.getCreditAssetExpectedReturn();
		
		return aboutState;
	}
	
	public boolean compareTo(PortfolioAssetsState sPrime){
		
		int thisTADir = this.getProperties()[1];
		int thisCADir = this.getProperties()[2];
		
		int sPrimeTADir = sPrime.getProperties()[1];
		int sPrimeCADir = sPrime.getProperties()[2];
		
		if((sPrimeTADir == thisTADir) && (sPrimeCADir == thisCADir)){
			return true;
		}
		else{
			return false;
		}
	}


}
