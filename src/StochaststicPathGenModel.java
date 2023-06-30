

import java.util.ArrayList;
import java.util.List;


public class StochaststicPathGenModel {
	
	ArrayList<AssetPath> singleIterationTraditionalAssetPaths = new ArrayList<AssetPath> ();
	ArrayList<AssetPath> singleIterationCreditAssetPaths = new ArrayList<AssetPath> ();
	StochasticProcessParameters parameters;
	AssetPath singleIterationAveragePathTraditionalAsset;
	AssetPath singleIterationAveragePathCreditAsset;
	double initialAssetValueTraditionalAsset;
	double initialAssetValueCreditAsset;
	
	double cir_ThetaCreditAssetMeanValue;//used to overwrite the initial long term mean upon loan reset
	
	double pathEndAssetValueTraditionalAsset;
	double pathEndAssetValueCreditAsset;
	
	String entityID;
	int pathID;
	int pathsSize = 0;
	
	boolean makeAdj = false;
	
	double valueAdjustment;
	TransitionProbabilitiesEngine transitionProbabilitiesEngine;
	
	
	/**
	 * Model constructor is such that a set of parameters must be provided for the model to generate an output
	 * @param params
	 */
	public StochaststicPathGenModel(StochasticProcessParameters params, String entityID){
		this.entityID = entityID;
		this.parameters = params;
		setInitialAssetValueTraditionalAsset(this.parameters.getInitialTraditionalAssetvalue());
		setInitialAssetValueCreditAsset(this.parameters.getInitialCreditAssetvalue());
		setCir_ThetaCreditAssetMeanValue(this.parameters.getCir_ThetaCreditAssetMeanValue());
		
		this.pathID = 0;
		initialize();
	}



	public void setInitialAssetValueTraditionalAsset(double initialAssetvalue2) {
		// TODO Auto-generated method stub
		this.initialAssetValueTraditionalAsset = initialAssetvalue2;
	}


	public void setInitialAssetValueCreditAsset(double initialAssetvalue2) {
		// TODO Auto-generated method stub
		this.initialAssetValueCreditAsset = initialAssetvalue2;
	}

	
	
	public double getInitialAssetvalueTraditionalAsset() {
		// TODO Auto-generated method stub
		return this.initialAssetValueTraditionalAsset;
	}


	
	public double getInitialAssetvalueCreditAsset() {
		// TODO Auto-generated method stub
		return this.initialAssetValueCreditAsset;
	}


	@Override
	public String toString(){
		return entityID + " " + this.parameters;
	}

	private void initialize() {
		// TODO Auto-generated method stub
		createPaths();
		generatePaths();
		this.pathID++;
		//this.computeSingleIterationAveragePathRandomSelection();
	}
	
	

	private void createPaths(){
		
		this.singleIterationAveragePathCreditAsset = new AssetPath(this.entityID +" Credit Asset  Path "+ this.pathID, 
				this.getInitialAssetvalueCreditAsset(), this.parameters.getPathLength());
		
		this.singleIterationAveragePathTraditionalAsset = new AssetPath(this.entityID +" Traditional Asset  Path "+ this.pathID, 
				this.getInitialAssetvalueTraditionalAsset(), this.parameters.getPathLength());
		
		singleIterationTraditionalAssetPaths.add(singleIterationAveragePathTraditionalAsset);
		singleIterationCreditAssetPaths.add(singleIterationAveragePathCreditAsset);
		
		this.pathsSize = singleIterationTraditionalAssetPaths.size();
	}

	public void generatePaths() {
		// TODO Auto-generated method stub
		
//			simple untidy discretised implementation of The Cox-Ingersoll-Ross
//			MonteCPrices.simValueCIR(double alpha, double theta, double sigma, double time, double initialValue)
		MonteCPrices mCA = new MonteCPrices(this.parameters.getPathLength());
//		System.out.println("Credit Asset Mean Value stochPathGenModel: " + this.getCir_ThetaCreditAssetMeanValue());
		double[] cApth = mCA.simValueCIR(this.parameters.getCir_AlphaCreditAssetMeanReversion(), 
				this.getCir_ThetaCreditAssetMeanValue(), 
				this.parameters.getStandardDeviationCreditAsset(), this.parameters.getTimeShit_dt(),
				this.getInitialAssetvalueCreditAsset());
		
		if(this.makeAdj == true){
			double[] cApthAdj = adjustPath(cApth);
			singleIterationCreditAssetPaths.get(singleIterationCreditAssetPaths.size()-1).setPath(cApthAdj);
			pathEndAssetValueCreditAsset = cApthAdj[cApthAdj.length-1];
		} else {
			singleIterationCreditAssetPaths.get(singleIterationCreditAssetPaths.size()-1).setPath(cApth);
			pathEndAssetValueCreditAsset = cApth[cApth.length-1];
		}

		MonteCPrices mTA = new MonteCPrices(this.parameters.getPathLength());
		double[] tApth = mTA.simValueHestonStochastricVolatilityDiffusion(
				this.parameters.getDriftMeanTraditionalAsset(), 
				this.parameters.getStandardDeviationTraditionalAsset(), this.parameters.getTimeShit_dt(),
				this.getInitialAssetvalueTraditionalAsset(), this.parameters.getHestonMeanReversionRateTraditionalAsset(), 
				this.parameters.getHestonLongTermVarianceTraditionalAsset(), this.parameters.getHestonVarianceVolatilityTraditionalAsset());
		
		singleIterationTraditionalAssetPaths.get(singleIterationTraditionalAssetPaths.size()-1).setPath(tApth);
		//get the end value of both paths
		//set the new initial asset values to the end values of the generate paths
		pathEndAssetValueTraditionalAsset = tApth[tApth.length-1];
		
		this.setInitialAssetValueCreditAsset(pathEndAssetValueCreditAsset);
		this.setInitialAssetValueTraditionalAsset(pathEndAssetValueTraditionalAsset);
		
		//create new empty paths to use for next instance of the generatePaths method
		createPaths();
	}
	
	


	public ArrayList<AssetPath> getSingleIterationCreditAssetPaths(){
		
		return this.singleIterationCreditAssetPaths;
	}
	
	public ArrayList<AssetPath> getSingleIterationTraditionalAssetPaths(){
		
		return this.singleIterationTraditionalAssetPaths;
	}
	
	
	public AssetPath getSingleIterationAveragePathTraditionalAsset(){
		
		return this.singleIterationAveragePathTraditionalAsset;
	}

	
	public AssetPath getSingleIterationAveragePathCreditAsset(){
		
		return this.singleIterationAveragePathCreditAsset;
	}


		
		public TransitionProbabilitiesEngine setTransitionProbability(){
			//TransitionProbabilitiesEngine(double[] path1, double[] path2, double initAReturn, double initBReturn)
			
			//there are 2 paths just to create the joint probability distribution for 2 assets
			//in the agent based model you will have 1 combined large path for 5yr each year consisting of 260 observations
			//i.e. 5yrPath = [yr1Path, yr2Path, yr3Path, yr4Pth, yr5Path]
			double[] combinedPathTraditionalAsset = margePaths(this.singleIterationTraditionalAssetPaths);
			double[] combinedPathCreditAsset = margePaths(this.singleIterationCreditAssetPaths);
			
			//this creates a cumulative transition probability matix for 2 assets 
			this.transitionProbabilitiesEngine = 
					new TransitionProbabilitiesEngine(combinedPathTraditionalAsset, combinedPathCreditAsset, 0, 0);
			List<AdjacencyMatrixContainer> STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX = transitionProbabilitiesEngine.getJoinedTransitionProbabilityMatrix();
			return this.transitionProbabilitiesEngine;
		}
		
		
		/**
		 * The following method is used to merge all generated 1 period paths for a given asset 
		 * into a larger combined path for the total observation period  
		 * @param AssetPaths
		 * @return
		 */
		public double[] margePaths(ArrayList<AssetPath> AssetPaths){
			double[] combinedPath;
			int combindePathLength = 0;
			int position = 0;// position of value in combined path
			//derive the total combined path length by looping through all the paths in the AssetPaths arrayList
			for(int i = 0; i < AssetPaths.size(); i++){
				combindePathLength += AssetPaths.get(i).getPath().length;
			}
			
			//initiate the combined path double array
			combinedPath = new double[combindePathLength];
			
			
			//for each asset path in the AssetPaths ArrayList
			//collect the path which is an array of double values and for 
			//every double value x in that list of doubles add to the 
			//combinedPath array in the appropriate position
			//once all paths are exhausted the loop ends
			for(int i = 0; i < AssetPaths.size(); i++){
				for(double x : AssetPaths.get(i).getPath()){
					combinedPath[position] = x;
					position++;
				}
			}
			
			
			return combinedPath;
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
		
		
		public TransitionProbabilitiesEngine getTransitionProbabilitiesEngine(){
			return transitionProbabilitiesEngine;
		}
		
		
		public void makeValueAdjustment(boolean makeAdj, double adj){
			this.makeAdj = makeAdj;
			this.valueAdjustment = adj;
		}
		
		private double[] adjustPath(double[] cApth) {
			// TODO Auto-generated method stub
			double[] adjPath = new double[cApth.length];
			
			for(int i = 0; i < cApth.length; i++){
				adjPath[i] = Rounding
						.roundFourDecimals(cApth[i]*(1 + this.valueAdjustment));
			}
			
			return adjPath;
		}

		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UNUSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		public void computeSingleIterationAveragePath(){
		}
		
		
		
		public void computeSingleIterationAveragePathRandomSelection(){
		}

			
			
	
	
	
	

}
