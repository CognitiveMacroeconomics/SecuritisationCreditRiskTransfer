

public class CashAssetClassState  extends AssetState{
	
	public static int CASH_ASSET_CLASS_STATE_ID = 0;
	private int stateID;

	public CashAssetClassState(int stateLevel, double[] stateReturns) {
		super(stateLevel, stateReturns);
		// TODO Auto-generated constructor stub
		CASH_ASSET_CLASS_STATE_ID++;
		stateID = CASH_ASSET_CLASS_STATE_ID;
	}
	
	
	/**
	 * Single state cash asset constructor
	 * @param stateLevel
	 * @param stateReturn
	 */
	public CashAssetClassState(int stateLevel, double stateReturn) {
		super(stateLevel, stateReturn);
		// TODO Auto-generated constructor stub
		CASH_ASSET_CLASS_STATE_ID++;
		stateID = CASH_ASSET_CLASS_STATE_ID;
	}
	
	public int getStateID(){
		return this.stateID;
	}



}
