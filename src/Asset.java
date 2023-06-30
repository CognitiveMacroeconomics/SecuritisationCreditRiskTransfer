

public class Asset {

	protected static int assetIDs = 0;
	private final int assetID;

	AssetType assetType;

	protected double currentValue;
	protected double effectiveDuration;
	protected double initialPrice;
	protected final double currentNotional;
	protected double yield;

	public Asset(double currentValue, AssetType type) {
		super();
		assetIDs++;
		assetID = assetIDs;
		this.currentValue = currentValue;
		initialPrice = this.currentValue;
		this.currentNotional = currentValue;
		this.setType(type);
	}

	public AssetType getType() {
		return assetType;
	}

	public void setType(AssetType type) {
		this.assetType = type;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public double getCurrentNotionalValue() {
		return currentNotional;
	}

	public int getID() {
		return assetID;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND
	// SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
