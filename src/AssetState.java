

/**
 * 
 * This class, the AssetState class simply stores the state of any one particular asset
 * in terms of its state direction [i.e. up, down, flat {1, -1, 0}]
 * 
 * it is used to distinguish between an assets own state and the environment and portfolio states; as well as keep all three independent and separate 
 * 
 * @author Oluwasegun Bewaji
 *
 */
public class AssetState extends PropertiesState {

	
	double[] expectedReturnsArray;
	double expectedReturn;
	
	/**
	 * The following is a constructor for a 1 property defined asset state
	 * Theoretically one could have multiple properties to use to define a  particular state
	 * @param stateLevel
	 * @param stateReturns
	 */
	public AssetState(int stateLevel, double[] stateReturns) {
		super(1); //Creates PropertiesState with 1 property
		this.prop[0] = stateLevel; //Value of state property. 
		expectedReturnsArray = stateReturns;
		setDefaultModelExtectedReturn(stateReturns);//set expected return at the current state
	}
	
	/**
	 * The following is a constructor for a 1 property defined single state asset 
	 * This will typically only be used in the case of a risk free asset where there is no stochastic direct change
	 * Theoretically one could have multiple properties to use to define a  particular state
	 * @param stateLevel
	 * @param stateReturn
	 */
	public AssetState(int stateLevel, double stateReturn) {
		super(1); //Creates PropertiesState with 1 property
		this.prop[0] = stateLevel; //Value of state property. 
		setDefaultModelExtectedReturn(stateReturn);//set expected return at the current state
	}
	
	
	public double getExpectedReturn(){
		return expectedReturn;
	}
	
	/**
	 * THe following set method sets the default expected return for the state.
	 * This is the default setup assuming 3 possible asset states defined by the direction of the 
	 * asset returns {-ve, 0, +ve} i.e. {-1,0,1}
	 * The direction of the asset return is stored by default in the first element of the state 
	 * properties array "prop[0]"
	 * @param stateReturns
	 */
	public void setDefaultModelExtectedReturn(double[] stateReturns) {
		
		this.expectedReturn = stateReturns[this.prop[0] + 1];
		

	}
	
	
	/**
	 * THe following set method sets the generic expected return for a single state asset.
	 * This will typically only be used in the case of a risk free asset where there is no stochastic direct change
	 * @param stateReturn
	 */
	public void setDefaultModelExtectedReturn(double stateReturn) {
		
		expectedReturn = stateReturn;
		
	}
	
	public int getDefaultModelStateLevel(){
		return this.prop[0];
	}
	
	public void updateAssetStateReturns(double[] stateReturns){
		setDefaultModelExtectedReturn(stateReturns);
	}
	
	public void updateStateReturn(double ret){
		setDefaultModelExtectedReturn(ret);
	}
	
	

}
