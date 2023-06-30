
public class ErevRothAction extends Action{

	
	//default constructor with only one defining property
	public ErevRothAction(double propDouble) {
		super(propDouble);
		// TODO Auto-generated constructor stub
	}

	
	//default constructor with only one defining property
		public ErevRothAction(int propInt) {
			super(propInt);
			// TODO Auto-generated constructor stub
		}

	
	public static ErevRothAction createSingleIntPropertyERAction(int propInt){
		
		ErevRothAction erAction = new ErevRothAction(propInt);
		return erAction;
		
	}
	
	
	public static ErevRothAction createSingleDoublePropertyERAction(double propDouble){
		
		ErevRothAction erAction = new ErevRothAction(propDouble);
		return erAction;
		
	}
	
	
}
