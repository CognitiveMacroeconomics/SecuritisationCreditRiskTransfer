

public class CreditAssetClassState  extends AssetState{
	
	public static int CREDIT_ASSET_CLASS_STATE_ID = 0;
	private int stateID;

	public CreditAssetClassState(int stateLevel, double[] stateReturns) {
		super(stateLevel, stateReturns);
		// TODO Auto-generated constructor stub
		CREDIT_ASSET_CLASS_STATE_ID++;
		stateID = CREDIT_ASSET_CLASS_STATE_ID;
	}
	
	public int getStateID(){
		return this.stateID;
	}



}
