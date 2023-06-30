
public class StochasticProcessParameters {
	
	
	
	
	private String stochasticProcessTypeStrng;
	private int stochasticProcessTypeStringIndex;
	

	private int numberOfIterations;
	private int numberOfPaths;
	private int pathLength;
	private double timeShit_dt;
	//private String assetName;
	
	private double initialCreditAssetvalue;
	private double cir_AlphaCreditAssetMeanReversion;
	private double cir_ThetaCreditAssetMeanValue;
	private double standardDeviationCreditAsset;


	private double initialTraditionalAssetvalue;
	private double driftMeanTraditionalAsset;
	private double standardDeviationTraditionalAsset;
	private double hestonLongTermVarianceTraditionalAsset;
	private double hestonMeanReversionRateTraditionalAsset;
	private double hestonVarianceVolatilityTraditionalAsset;
	
	
	private double jumpIntensity;
	private double jump_Mean_Size;
	private double jump_Size_Distribution_Width;
	

	
	public StochasticProcessParameters(int numberOfIterations, int numberOfPaths, int pathLength, double timeShit_dt, double initialCreditAssetvalue,
			double cir_AlphaCreditAssetMeanReversion, double cir_ThetaCreditAssetMeanValue, double standardDeviationCreditAsset, 
			double initialTraditionalAssetvalue, double driftMeanTraditionalAsset, double standardDeviationTraditionalAsset,
			double hestonLongTermVarianceTraditionalAsset, double hestonMeanReversionRateTraditionalAsset, double hestonVarianceVolatilityTraditionalAsset){
		
		
		this.setNumberOfIterations(numberOfIterations);
		this.setPathLength(pathLength);
		this.setNumberOfPaths(numberOfPaths);
		this.setInitialCreditAssetvalue(initialCreditAssetvalue);
		this.setInitialTraditionalAssetvalue(initialTraditionalAssetvalue);
		this.setDriftMeanTraditionalAsset(driftMeanTraditionalAsset);
		this.setStandardDeviationCreditAsset(standardDeviationCreditAsset);
		this.setStandardDeviationTraditionalAsset(standardDeviationTraditionalAsset);
		this.setTimeShit_dt(timeShit_dt);
		this.setCir_AlphaCreditAssetMeanReversion(cir_AlphaCreditAssetMeanReversion);
		this.setCir_ThetaCreditAssetMeanValue(cir_ThetaCreditAssetMeanValue);
		this.setHestonLongTermVarianceTraditionalAsset(hestonLongTermVarianceTraditionalAsset);
		this.setHestonMeanReversionRateTraditionalAsset(hestonMeanReversionRateTraditionalAsset);
		this.setHestonVarianceVolatilityTraditionalAsset(hestonVarianceVolatilityTraditionalAsset);
		
	}
	

	/**
	 * @return the numberOfIterations
	 */
	public int getNumberOfIterations() {
		return numberOfIterations;
	}



	/**
	 * @param numberOfIterations the numberOfIterations to set
	 */
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}



	/**
	 * @return the numberOfPaths
	 */
	public int getNumberOfPaths() {
		return numberOfPaths;
	}



	/**
	 * @param numberOfPaths the numberOfPaths to set
	 */
	public void setNumberOfPaths(int numberOfPaths) {
		this.numberOfPaths = numberOfPaths;
	}



	/**
	 * @return the pathLength
	 */
	public int getPathLength() {
		return pathLength;
	}



	/**
	 * @param pathLength the pathLength to set
	 */
	public void setPathLength(int pathLength) {
		this.pathLength = pathLength;
	}


	/**
	 * @return the initialAssetvalue
	 */
	public double getInitialCreditAssetvalue() {
		return initialCreditAssetvalue;
	}



	/**
	 * @param initialAssetvalue the initialAssetvalue to set
	 */
	public void setInitialCreditAssetvalue(double initialCAssetvalue) {
		this.initialCreditAssetvalue = initialCAssetvalue;
	}


	/**
	 * @return the initialAssetvalue
	 */
	public double getInitialTraditionalAssetvalue() {
		return initialTraditionalAssetvalue;
	}



	/**
	 * @param initialAssetvalue the initialAssetvalue to set
	 */
	public void setInitialTraditionalAssetvalue(double initialTAssetvalue) {
		this.initialTraditionalAssetvalue = initialTAssetvalue;
	}


	

	/**
	 * @return the driftMean
	 * 
	 *
	 *
	 */
	public double getDriftMeanTraditionalAsset() {
		return driftMeanTraditionalAsset;
	}



	/**
	 * @param driftMean the driftMean to set
	 */
	public void setDriftMeanTraditionalAsset(double driftMean) {
		this.driftMeanTraditionalAsset = driftMean;
	}



	/**
	 * @return the standardDeviationCreditAsset
	 */
	public double getStandardDeviationCreditAsset() {
		return standardDeviationCreditAsset;
	}



	/**
	 * @param standardDeviation the standardDeviationCreditAsset to set
	 */
	public void setStandardDeviationCreditAsset(double standardDeviation) {
		this.standardDeviationCreditAsset = standardDeviation;
	}



	/**
	 * @return the standardDeviationTraditionalAsset
	 */
	public double getStandardDeviationTraditionalAsset() {
		return standardDeviationTraditionalAsset;
	}



	/**
	 * @param standardDeviation the standardDeviationTraditionalAsset to set
	 */
	public void setStandardDeviationTraditionalAsset(double standardDeviation) {
		this.standardDeviationTraditionalAsset = standardDeviation;
	}



	/**
	 * @return the timeShit_dt
	 */
	public double getTimeShit_dt() {
		return timeShit_dt;
	}



	/**
	 * @param timeShit_dt the timeShit_dt to set
	 */
	public void setTimeShit_dt(double timeShit_dt) {
		this.timeShit_dt = timeShit_dt;
	}


	/**
	 * @return the cir_AlphaCreditAssetMeanReversion
	 */
	public double getCir_AlphaCreditAssetMeanReversion() {
		return cir_AlphaCreditAssetMeanReversion;
	}


	/**
	 * @param cir_Alpha the cir_AlphaCreditAssetMeanReversion to set
	 */
	public void setCir_AlphaCreditAssetMeanReversion(double cir_Alpha) {
		this.cir_AlphaCreditAssetMeanReversion = cir_Alpha;
	}


	/**
	 * @return the cir_ThetaCreditAssetMeanValue
	 */
	public double getCir_ThetaCreditAssetMeanValue() {
		return cir_ThetaCreditAssetMeanValue;
	}



	/**
	 * @param cir_Theta the cir_ThetaCreditAssetMeanValue to set
	 */
	public void setCir_ThetaCreditAssetMeanValue(double cir_Theta) {
		this.cir_ThetaCreditAssetMeanValue = cir_Theta;
	}

	
	
	/**
	 * @return the hestonLongTermVarianceTraditionalAsset
	 */
	public double getHestonLongTermVarianceTraditionalAsset() {
		return hestonLongTermVarianceTraditionalAsset;
	}



	/**
	 * @param hestonLongTermVariance the hestonLongTermVarianceTraditionalAsset to set
	 */
	public void setHestonLongTermVarianceTraditionalAsset(double hestonLongTermVariance) {
		this.hestonLongTermVarianceTraditionalAsset = hestonLongTermVariance;
	}



	/**
	 * @return the hestonLongTermVarianceTraditionalAsset
	 */
	public double getHestonMeanReversionRateTraditionalAsset() {
		return hestonLongTermVarianceTraditionalAsset;
	}



	/**
	 * @param hestonMeanReversionRate the hestonMeanReversionRateTraditionalAsset to set
	 */
	public void setHestonMeanReversionRateTraditionalAsset(double hestonMeanReversionRate) {
		this.hestonMeanReversionRateTraditionalAsset = hestonMeanReversionRate;
	}



	/**
	 * @return the hestonVarianceVolatilityTraditionalAsset
	 */
	public double getHestonVarianceVolatilityTraditionalAsset() {
		return hestonVarianceVolatilityTraditionalAsset;
	}



	/**
	 * @param hestonVarianceVolatility the hestonVarianceVolatilityTraditionalAsset to set
	 */
	public void setHestonVarianceVolatilityTraditionalAsset(double hestonVarianceVolatility) {
		this.hestonVarianceVolatilityTraditionalAsset = hestonVarianceVolatility;
	}
	
	
	


	public void setStochasticProcessTypeStringIndex(
			int stochasticProcessTypeStringIndex) {
		// TODO Auto-generated method stub
		this.stochasticProcessTypeStringIndex = stochasticProcessTypeStringIndex;
		
	}



	public void setStochasticProcessTypeStrng(String stochasticProcessTypeStrng) {
		// TODO Auto-generated method stub
		this.stochasticProcessTypeStrng = stochasticProcessTypeStrng;
	}


	public int getStochasticProcessTypeStringIndex() {
		// TODO Auto-generated method stub
		return stochasticProcessTypeStringIndex;
		
	}



	public String getStochasticProcessTypeStrng() {
		// TODO Auto-generated method stub
		return stochasticProcessTypeStrng;
	}
	
	
	
	
	
	/**
	 * 
	 * Not used
	 */

	/**
	 * @return the jumpIntensity
	 */
	public double getJumpIntensity() {
		return jumpIntensity;
	}



	/**
	 * @param jumpIntensity the jumpIntensity to set
	 */
	public void setJumpIntensity(double jumpIntensity) {
		this.jumpIntensity = jumpIntensity;
	}



	/**
	 * @return the jump_Mean_Size
	 */
	public double getJump_Mean_Size() {
		return jump_Mean_Size;
	}



	/**
	 * @param jump_Mean_Size the jump_Mean_Size to set
	 */
	public void setJump_Mean_Size(double jump_Mean_Size) {
		this.jump_Mean_Size = jump_Mean_Size;
	}



	/**
	 * @return the jump_Size_Distribution_Width
	 */
	public double getJump_Size_Distribution_Width() {
		return jump_Size_Distribution_Width;
	}



	/**
	 * @param jump_Size_Distribution_Width the jump_Size_Distribution_Width to set
	 */
	public void setJump_Size_Distribution_Width(double jump_Size_Distribution_Width) {
		this.jump_Size_Distribution_Width = jump_Size_Distribution_Width;
	}
	
	
	
@Override
public String toString(){
		
		return 
				("numberOfIterations: " + this.numberOfIterations + " numberOfPaths: " + 	
		this.numberOfPaths + " pathLength: " + 	this.pathLength + " timeShit_dt: " + 	
				this.timeShit_dt + " initialCreditAssetvalue: " + 	this.initialCreditAssetvalue + " cir_AlphaCreditAssetMeanReversion: " + 
		this.cir_AlphaCreditAssetMeanReversion + " cir_ThetaCreditAssetMeanValue: " +  this.cir_ThetaCreditAssetMeanValue 
		+ " standardDeviationCreditAsset: " + 	this.standardDeviationCreditAsset	
		 + " initialTraditionalAssetvalue: " + this.initialTraditionalAssetvalue + " driftMeanTraditionalAsset: " + 	this.driftMeanTraditionalAsset
		 + " standardDeviationTraditionalAsset: " + 	this.standardDeviationTraditionalAsset 
		 + " hestonLongTermVarianceTraditionalAsset: " + 	this.hestonLongTermVarianceTraditionalAsset	
		 + " hestonMeanReversionRateTraditionalAsset: " + this.hestonMeanReversionRateTraditionalAsset 
		 + " hestonVarianceVolatilityTraditionalAsset: " + 	this.hestonVarianceVolatilityTraditionalAsset);
	}


}
