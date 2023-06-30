



public class MDPPortfolioChoiceAction extends PortfolioAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int PORTFOLIO_ACTION_ID = -1;
	private int actionID;
	private int modelTypeAssetCount = 0;//used to identify the type of model being created 
	
	public double equityAssetWeightChoice;
	public double creditAssetWeightChoice;
	public double cashAssetWeightChoice;
	public double realEstateAssetWeightChoice;
	public double commodityAssetWeightChoice;
	private double singleAssetAction;
	private boolean shortSelling = false;
	private String label;
	
	
	
	
	/**
	 * default model and simple two asset action constructor.
	 * Note that the use of the changing weight of both assets is done explicitly so as to accommodate the potential for 
	 * shorting of positions. The values will be set in the method call that creates the choice in weights
	 * @param eqtyAstWghtChoice
	 * @param crdAstWghtChng
	 */
	private MDPPortfolioChoiceAction(double eqtyAstWghtChoice, double crdAstWghtChng){
		PORTFOLIO_ACTION_ID++;
		this.setActionID(PORTFOLIO_ACTION_ID);
		this.setEquityAssetWeightChoice(eqtyAstWghtChoice);
		this.setCreditAssetWeightChoice(crdAstWghtChng);
		
	}
	
	
	private MDPPortfolioChoiceAction(double eqtyAstWghtCh, double crdAstWghtCh, double cshAstWghtCh){
		PORTFOLIO_ACTION_ID++;
		this.setActionID(PORTFOLIO_ACTION_ID);
		this.setEquityAssetWeightChoice(eqtyAstWghtCh);
		this.setCreditAssetWeightChoice(crdAstWghtCh);
		this.setCashAssetWeightChoice(cshAstWghtCh);
		
	}

	
	public static MDPPortfolioChoiceAction createDefaultTwoEquityCreditAsset(double eqAstWghtChng, double crdAstWghtChng, boolean shrtSell){
		MDPPortfolioChoiceAction action = new MDPPortfolioChoiceAction(eqAstWghtChng, crdAstWghtChng);
		action.setShortSellBoolean(shrtSell);
		return action;
	}

	
	public static MDPPortfolioChoiceAction createDefaultThreeEquityCreditCashAsset(double eqAstWghtCh, double crdAstWghtCh, 
			double cshAstWghtCh, boolean shrtSell){
		MDPPortfolioChoiceAction action = new MDPPortfolioChoiceAction(eqAstWghtCh, crdAstWghtCh, cshAstWghtCh);
		action.setShortSellBoolean(shrtSell);
		return action;
	}

	


	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void setShortSellBoolean(boolean shrtSell) {
		// TODO Auto-generated method stub
		this.shortSelling = shrtSell;
	}

	public double getEquityAssetWeightChoice() {
		return equityAssetWeightChoice;
		
	}


	public void setEquityAssetWeightChoice(double equityAssetWeightChoice) {
		this.equityAssetWeightChoice = equityAssetWeightChoice;
		this.modelTypeAssetCount++;
	}


	public double getCreditAssetWeightChoice() {
		return creditAssetWeightChoice;
	}


	public void setCreditAssetWeightChoice(double creditAssetWeightChoice) {
		this.creditAssetWeightChoice = creditAssetWeightChoice;
		this.modelTypeAssetCount++;
	}


	public double getCashAssetWeightChoice() {
		return cashAssetWeightChoice;
	}


	public void setCashAssetWeightChoice(double cashAssetWeightChoice) {
		this.cashAssetWeightChoice = cashAssetWeightChoice;
		this.modelTypeAssetCount++;
	}


	public double getRealEstateAssetWeightChoice() {
		return realEstateAssetWeightChoice;
	}


	public void setRealEstateAssetWeightChoice(double realEstateAssetWeightChoice) {
		this.realEstateAssetWeightChoice = realEstateAssetWeightChoice;
		this.modelTypeAssetCount++;
	}


	public double getCommodityAssetWeightChoice() {
		return commodityAssetWeightChoice;
	}


	public void setCommodityAssetWeightChoice(double commodityAssetWeightChoice) {
		this.commodityAssetWeightChoice = commodityAssetWeightChoice;
		this.modelTypeAssetCount++;
	}


	public double getSingleAssetAction() {
		return singleAssetAction;
	}


	public void setSingleAssetAction(double singleAssetAction) {
		this.singleAssetAction = singleAssetAction;
		this.modelTypeAssetCount++;
	}


	public int getActionID() {
		return actionID;
	}


	public void setActionID(int actionID) {
		this.actionID = actionID;
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
	public int compareTo(MDPPortfolioChoiceAction arg0) {
		// TODO Auto-generated method stub
		int choice = 0;
		if(arg0 instanceof MDPPortfolioChoiceAction && shortSelling == false && this.modelTypeAssetCount == 2){
			choice = (int) (100*(this.getEquityAssetWeightChoice()- arg0.getEquityAssetWeightChoice()));
		}
		return choice;
	}


//	@Override
	public String label() {
		// TODO Auto-generated method stub
		if(this.modelTypeAssetCount == 3){
			label = "Portfolio Weight Choice of: Equity: " + this.getEquityAssetWeightChoice() + " Credit Asset: "+ this.getCreditAssetWeightChoice();
		}
		return label;
		
	}



}
