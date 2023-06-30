

public class AssetPath {
	
	public static int ASSET_PATH_ID;
	
	private int assetPathID;
	private double initialValue;
	private double currentValue;
	private String Asset_Name;
	private String Asset_Path_Name;
	private double[] path;

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Asset Path Constructor>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * An asset path must be constructed with an asset name, initial value and 
	 * number of iterations/time length over which the path is generated 
	 * 
	 * @param AssetName
	 * @param initVal
	 * @param iterations
	 */
	public AssetPath(String AssetName, double initVal, int iterations){
		ASSET_PATH_ID++;
		assetPathID = ASSET_PATH_ID;
		this.path = new double[iterations];
		this.setInitialValue(initVal);
		this.setAssetName(AssetName);
		this.setAssetPathName();
	}

	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Getters and Setters>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private void setAssetPathName() {
		// set the path name
		this.Asset_Path_Name = this.Asset_Name + ": " + this.assetPathID;
	}

	private void setAssetName(String assetName) {
		// Set the asset name
		this.Asset_Name = assetName;
	}

	private void setInitialValue(double initVal) {
		// set the initial value of the asset
		this.initialValue = initVal;
	}
	
	public String getAssetName(){
		return this.Asset_Name;
	}
	
	public String getAssetPathName() {
		// set the path name
		return this.Asset_Path_Name;
	}
	
	public double[] getPath(){
		return this.path;
	}

	public void setPath(double[] p){
		this.path = p;
	}
	
	@Override
	public String toString(){
		return this.getAssetPathName();
	}
	
}
