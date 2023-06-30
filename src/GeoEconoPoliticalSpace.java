import jas.engine.AgentList;
import jas.engine.Sim;
import jas.space.DblGrid;
import jas.space.ObjGrid;


/**
 * This class is to be used as a way of capturing the spatial representation of agents in all models I develop
 * for the JAS body of model development this class is identical to the implementation of DaisyWorld
 * In Fact the exact same set of variables and methods are used
 * @author Oluwasegun Bewaji
 *
 */
public class GeoEconoPoliticalSpace {
	
	
	public static double solarFluxConstant = 952;
	public static double SBConstant = 5.669E-008;
	public double solarMultiplier = 1.0;
	public static double planetAlbedo = 0.5;
	
	public double mediumTemp = 35.0;
	public double periodTemp = 25.0;
	
	public DblGrid temperatureLayer;
	public ObjGrid agentLayer;
	public ObjGrid bankLayer;
	public ObjGrid fundLayer;
	public ObjGrid dealerLayer;
	public int xSize, ySize;
	public AgentList agentsPopulation;
	public double sunPeriod;
	public int phase;
	
	public GeoEconoPoliticalSpace(int xSize, int ySize, int sunPeriod, int phase) {
		this.xSize = xSize;
		this.ySize = ySize;
		temperatureLayer = new DblGrid(xSize, ySize);
		agentLayer = new ObjGrid(xSize, ySize);
		bankLayer = new ObjGrid(xSize, ySize);
		fundLayer = new ObjGrid(xSize, ySize);
		dealerLayer = new ObjGrid(xSize, ySize);
		agentsPopulation = new AgentList();
		this.sunPeriod = sunPeriod;
		this.phase = phase;
	}
	
	
	/**
	 * This method places agents randomly on the geoEconoPolitical space
	 * @param agent
	 */
	public void placeMeRandom(EconomicAgent agent){
		int x = 0, y = 0;
		int cnt = 0;
		
		EconomicAgent who = null;
		do {
			cnt++;
			x = Sim.getRnd().getIntFromTo(0, xSize - 35);
			y = Sim.getRnd().getIntFromTo(0, ySize - 35);
			who = (EconomicAgent) agentLayer.get(x, y);			
		} while (who != null && cnt < 20);
		
		if (cnt >= 20) {
			return;
		}
		agent.setXY(x, y);
		agentLayer.set(x, y, agent);
		if(agent instanceof Bank){
			bankLayer.set(x, y, agent);
		}
		if(agent instanceof CInvestor){
			fundLayer.set(x, y, agent);
		}
		if(agent instanceof Dealer_MarketMaker){
			dealerLayer.set(x, y, agent);
		}
	}
	


	/**
	 * This method places agents at precise locations on the geoEconoPolitical space. 
	 * 
	 * For the most part in my economic models I will not be using this method 
	 * @param x
	 * @param y
	 * @param agent
	 */
	public void placeMeAt(int x, int y, EconomicAgent agent){
		if (agentLayer.get(x, y) != null) {
			throw new UnsupportedOperationException("placeMeAt: The position (" + x + "," + y + ") is not empty." );
		}
		agent.setXY(x, y);
		agentLayer.set(x, y, agent);
	}
	
	public void insulate(){
		double sun = getLocalTemperature(planetAlbedo);
		for (int x = 0; x < xSize; x++){
			for (int y = 0; y < ySize; y++){
				EconomicAgent tempAgent = (EconomicAgent) agentLayer.get(x, y);
				if (tempAgent == null){
					temperatureLayer.setDbl(x, y, sun );
				}
				else{
					temperatureLayer.setDbl(x, y, getLocalTemperature( tempAgent.getAlbedo()) );					
				}
			}
		}
	}
	
		
	public double getLocalTemperature(double albedo){
		double solarLuminosity = (solarMultiplier + (Math.sin(Sim.getAbsoluteTime() * Math.PI / sunPeriod) / 2));
		double res = Math.pow(solarLuminosity * solarFluxConstant * ( 1.0 - albedo ) / SBConstant, 0.25) - 273.0;
		//System.out.println("SUN T " + res);
		return res;
	}
	
	public double getSunTemperature(){
		return getLocalTemperature(planetAlbedo);
	}
	
	public double getAverageTemp(){
		//System.out.println("SUM " + world.temperatureLayer.sum());
		return temperatureLayer.sum() / (xSize * ySize);
	}	

}
