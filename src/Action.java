
public class Action {
	
	public static int ACTION_ID = 0;
	
	private int actionID;
	
	public int properties[]; //this is an integer array used to classify the discriptive 
	//properties of the action e.g. increase by [-2, -1, 0, 1, 2]
	
	public double propertiesDouble[]; //this is an double array used to classify the discriptive 
	//properties of the action e.g. increase by [-0.2, -0.1, 0.0, 0.1, 0.2]
	

	public double actionDoubleType;
	public int actionIntegerType;

	public String actionStringType;
	
	//default constructor with only one defining property
	public Action(int propInt){
		ACTION_ID++;
		actionID = ACTION_ID;
		properties = new int[1];
		properties[0] = propInt;
	}
	
	public Action(double propDouble){
		ACTION_ID++;
		actionID = ACTION_ID;
		propertiesDouble = new double[1];
		propertiesDouble[0] = propDouble;
		setActionDoubleType(propDouble);
	}
	
	
	public int[] getProperties() {
		return properties;
	}

	public void setProperties(int[] properties) {
		this.properties = properties;
	}

	public String getActionStringType() {
		return actionStringType;
	}

	public void setActionStringType(String actionStringType) {
		this.actionStringType = actionStringType;
	}

	public double getActionDoubleType() {
		return actionDoubleType;
	}

	public void setActionDoubleType(double actionDoubleType) {
		this.actionDoubleType = actionDoubleType;
	}

	public int getActionIntegerType() {
		return actionIntegerType;
	}

	public void setActionIntegerType(int actionIntegerType) {
		this.actionIntegerType = actionIntegerType;
	}

	public double[] getPropertiesDouble() {
		return propertiesDouble;
	}

	public void setPropertiesDouble(double[] propertiesDouble) {
		this.propertiesDouble = propertiesDouble;
	}



}
