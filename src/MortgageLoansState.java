
public class MortgageLoansState  extends AssetState{
	
	public static int MORTGAGE_LOAN_CLASS_STATE_ID = 0;
	private int stateID;

	public MortgageLoansState(int stateLevel, double stateReturn) {
		super(stateLevel, stateReturn);
		// TODO Auto-generated constructor stub
		MORTGAGE_LOAN_CLASS_STATE_ID++;
		stateID = MORTGAGE_LOAN_CLASS_STATE_ID;
	}
	
	public int getStateID(){
		return this.stateID;
	}


}
