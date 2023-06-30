



public class MDPPortfolioChangeAction extends PortfolioAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int PORTFOLIO_ACTION_ID = 0;
	private int actionID;
	private int modelTypeAssetCount = 0;//used to identify the type of model being created 
	
	private double equityAssetWeightChange;
	private double creditAssetWeightChange;
	private double cashAssetWeightChange;
	private double realEstateAssetWeightChange;
	private double commodityAssetWeightChange;
	private double singleAssetAction;
	private boolean shortSelling = false;
	private String label;
	
	
	
	
	/**
	 * default model and simple two asset action constructor.
	 * Note that the use of the changing weight of both assets is done explicitly so as to accommodate the potential for 
	 * shorting of positions. The values will be set in the method call that creates the change in weights
	 * @param eqtyAstWghtChange
	 * @param crdAstWghtChng
	 */
	private MDPPortfolioChangeAction(double eqtyAstWghtChange, double crdAstWghtChng){
		PORTFOLIO_ACTION_ID++;
		this.actionID = PORTFOLIO_ACTION_ID;
		this.setEquityAssetWeightChange(eqtyAstWghtChange);
		this.setCreditAssetWeightChange(crdAstWghtChng);
		
	}
	
	
	public static MDPPortfolioChangeAction createDefaultTwoEquityCreditAsset(double eqAstWghtChng, double crdAstWghtChng, boolean shrtSell){
		MDPPortfolioChangeAction action = new MDPPortfolioChangeAction(eqAstWghtChng, crdAstWghtChng);
		action.setShortSellBoolean(shrtSell);
		return action;
	}

	
	


	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void setShortSellBoolean(boolean shrtSell) {
		// TODO Auto-generated method stub
		this.shortSelling = shrtSell;
	}

	public double getEquityAssetWeightChange() {
		return equityAssetWeightChange;
		
	}


	public void setEquityAssetWeightChange(double equityAssetWeightChange) {
		this.equityAssetWeightChange = equityAssetWeightChange;
		this.modelTypeAssetCount++;
	}


	public double getCreditAssetWeightChange() {
		return creditAssetWeightChange;
	}


	public void setCreditAssetWeightChange(double creditAssetWeightChange) {
		this.creditAssetWeightChange = creditAssetWeightChange;
		this.modelTypeAssetCount++;
	}


	public double getCashAssetWeightChange() {
		return cashAssetWeightChange;
	}


	public void setCashAssetWeightChange(double cashAssetWeightChange) {
		this.cashAssetWeightChange = cashAssetWeightChange;
		this.modelTypeAssetCount++;
	}


	public double getRealEstateAssetWeightChange() {
		return realEstateAssetWeightChange;
	}


	public void setRealEstateAssetWeightChange(double realEstateAssetWeightChange) {
		this.realEstateAssetWeightChange = realEstateAssetWeightChange;
		this.modelTypeAssetCount++;
	}


	public double getCommodityAssetWeightChange() {
		return commodityAssetWeightChange;
	}


	public void setCommodityAssetWeightChange(double commodityAssetWeightChange) {
		this.commodityAssetWeightChange = commodityAssetWeightChange;
		this.modelTypeAssetCount++;
	}


	public double getSingleAssetAction() {
		return singleAssetAction;
	}


	public void setSingleAssetAction(double singleAssetAction) {
		this.singleAssetAction = singleAssetAction;
		this.modelTypeAssetCount++;
	}


	/**
	 * This is a required method for jMDP/jMarkov
	 * It is defaulted to returning an int from the API
	 * 
	 * Since portfolio weights are of the form double, to get around this problem the following 
	 * implementation of the compareTo(Action a) method simply converts the decimal doubles to percentage ints
	 * 
	 * Hence, 
	 * 		0.05 - -0.05 = 0.1
	 * 		-0.1 - 0.1 = -0.2
	 * becomes
	 * 		100*(0.05 - -0.05) = 10
	 * 		100*(-0.1 - 0.1) = -20
	 * 
	 * This is easier to use however one should remember that anything used in the comparison should be converted to 
	 * the int form percentage. 
	 */
//	@Override
	public int compareTo(MDPPortfolioChangeAction arg0) {
		// TODO Auto-generated method stub
		int change = 0;
		if(arg0 instanceof MDPPortfolioChangeAction && shortSelling == false && this.modelTypeAssetCount == 2){
			change = (int) (100*(this.getEquityAssetWeightChange()- arg0.getEquityAssetWeightChange()));
		}
		return change;
	}


//	@Override
	public String label() {
		// TODO Auto-generated method stub
		if(this.modelTypeAssetCount == 2){
			label = "Portfolio Weight Change of: Equity: " + this.getEquityAssetWeightChange() + " Credit Asset: "+ this.getCreditAssetWeightChange();
		}
		return label;
		
	}


}
