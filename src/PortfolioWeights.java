

import java.util.ArrayList;


public class PortfolioWeights {
	
	public static int PORTFOLIO_WEIGHTS_ID = 0;
	private int portfolioWeightID;
	private double first;// first member of pair
	private double second;// second member of pair
	private double third;// second member of pair
	private ArrayList<Double> nAssetWeights = new ArrayList<Double>();
	private int firstInt;// first member of integer pair
	private int secondInt;// second member of integer pair
	private ArrayList<Integer> nActions = new ArrayList<Integer>();
	private double assetReturn;
	
	
	private PortfolioWeights(double eqty, double crdt){
		PORTFOLIO_WEIGHTS_ID++;
		portfolioWeightID = PORTFOLIO_WEIGHTS_ID; //to ensure all portfolio weights have a unique id
		this.first = eqty;
		this.second = crdt;
	}
	
	
	private PortfolioWeights(double eqty, double crdt, double csh){
		PORTFOLIO_WEIGHTS_ID++;
		portfolioWeightID = PORTFOLIO_WEIGHTS_ID; //to ensure all portfolio weights have a unique id
		this.first = eqty;
		this.second = crdt;
		this.third = csh;
	}

	
	/**
	 * Weights are created using static create methods
	 * @param eqty
	 * @param crdt
	 * @return
	 */
	public static PortfolioWeights createDefaultPorfolioWeights(double eqty, double crdt){
		PortfolioWeights pW = new PortfolioWeights(eqty, crdt);
		return pW;
	}
	
	
	/**
	 * Weights are created using static create methods
	 * @param eqty
	 * @param crdt
	 * @param csh
	 * @return
	 */
	public static PortfolioWeights createEqtyCrdtCshPorfolioWeights(double eqty, double crdt, double csh){
		PortfolioWeights pW = new PortfolioWeights(eqty, crdt, csh);
		return pW;
	}

	
	public double getDefaultModelEquityWeight(){
		return this.first;
	}
	
	
	
	public double getDefaultModelCreditWeight(){
		return this.second;
	}
	
	
	public double getDefaultModelCashWeight(){
		return this.third;
	}
	
	
	public int size(){
		int size = 2;//default is a two asset pair
		if(!this.nAssetWeights.isEmpty()){
			//this checks if there are multiple assets in excess of 2
			size = this.nAssetWeights.size();
		} 
		else if(this.second <-2 && this.third <-2 && this.nAssetWeights.isEmpty()){
			//this checks for a single asset type approach to asset weights
			size = 1;
		}
		
		else if(this.second >=-1 && this.third >=-1 && this.nAssetWeights.isEmpty()){
			//this checks for a single asset type approach to asset weights
			size = 3;
		}

		return size;
	}

}
