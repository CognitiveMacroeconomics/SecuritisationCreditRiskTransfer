

public class AdjacencyMatrixContainer {
	
	int assetOneState;
	int assetTwoState;
	double[] transitionProbabilityAdjecencyMatrix;
	
	
	public static AdjacencyMatrixContainer createTwoAssetAdjacencyList(int asset1State, int asset2State, double[] transitionProbabilities){
		
		AdjacencyMatrixContainer adjecencyList = new AdjacencyMatrixContainer(asset1State, asset2State, transitionProbabilities);
		
		return adjecencyList;
	}
	
	
	private AdjacencyMatrixContainer(int asset1State, int asset2State, double[] transitionProbabilities){
		this.assetOneState = asset1State;
		this.assetTwoState = asset2State;
		this.transitionProbabilityAdjecencyMatrix = transitionProbabilities;
	}
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public double getAssetOneState(){
		return this.assetOneState;
	}
	
	
	public double getAssetTwoState(){
		return this.assetTwoState;
	}
	
	public double[] getStateTransitionProbabilities(){
		return this.transitionProbabilityAdjecencyMatrix;
	}

}
