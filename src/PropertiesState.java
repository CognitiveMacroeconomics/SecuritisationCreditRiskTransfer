

public class PropertiesState extends State{
	
	int numberOfProperties;
	int[] prop;
	
	
	public PropertiesState(int numProps){
		this.numberOfProperties = numProps;
		prop = new int[this.numberOfProperties];
	}
	
	public int[] getProperties(){
		return this.prop;
	}

}
