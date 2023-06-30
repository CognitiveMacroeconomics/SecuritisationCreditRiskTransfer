

public class EquityAssetClassState extends AssetState{
	
	public static int EQUITY_ASSET_CLASS_STATE_ID = 0;
	private int stateID;

	public EquityAssetClassState(int stateLevel, double[] stateReturns) {
		super(stateLevel, stateReturns);
		// TODO Auto-generated constructor stub
		EQUITY_ASSET_CLASS_STATE_ID++;
		stateID = EQUITY_ASSET_CLASS_STATE_ID;
	}
	
	public int getStateID(){
		return this.stateID;
	}

}
