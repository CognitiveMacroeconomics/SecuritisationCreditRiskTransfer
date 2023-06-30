

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;



public class StateSetPortfolioAssets {
	
	/**
	 * 
	 * This is a customised method to handle the collection of feasible states.
	 * 
	 * It has been created for states purely because there is a potential to have a vast number of states of the world 
	 * and thus it is easier to ensure that duplicates do not get added to any set of states that is of interest by simply 
	 * defining the custom StateSet as a LinkedHashMap to maintain the order in which the states are added into the StatesSet
	 * and to distinguish between states by their set keys 
	 * The Map<Integer, Integer> will use the stateID of the MDP State and the stateID of the associated asset pairing states (i.e. PortfolioAssetState)
	 * 
	 */
	
	Map<Integer, PortfolioAssetsState> stateSet;
	int size;
	ArrayList<PortfolioAssetsState> statesDomain;
	
	
	
	public StateSetPortfolioAssets(){
		this.stateSet = new LinkedHashMap<>();
		this.statesDomain = new ArrayList<PortfolioAssetsState>();
		size = 0;
	}
	
	/**
	 * This method puts the IState object into the states set
	 * @param t
	 */
	public void put(PortfolioAssetsState t){
		
		if(t instanceof PortfolioAssetsState){ // check if the IState is an instance of AssetAllocationMDPState. This should not be required for the most part
			//but is done for safety reasons
			Integer mdpStateID = t.getStateID();
			if(stateSet.get(mdpStateID) == null){//check if the states is already in the set if not then add
				stateSet.put(mdpStateID, t);
				statesDomain.add(t);
				size++;
//				System.out.println("inserted " + t.toString());
			} else{
//				System.out.println("failed to insert " + t.toString());
			}
		}
	}
	
	 public State get(int k)
	    {
	       return this.stateSet.get(k);
	    }
	 
	 public Map<Integer, PortfolioAssetsState> StatesSet(){
		 return this.stateSet;
	 }
	 
	 public int size(){
		 return size;
	 }
	 
	 
	 public ArrayList<PortfolioAssetsState> getStatesDomain(){
		 return this.statesDomain;
	 }
	
	

}
