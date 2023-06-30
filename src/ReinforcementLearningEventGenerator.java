import cern.jet.random.engine.RandomEngine;


public class ReinforcementLearningEventGenerator {
	
	double[] probabilityDistributionFunction;
	RandomEngine randomEngine;
	
	public ReinforcementLearningEventGenerator(double[] pdf, RandomEngine rEngine){
		this.probabilityDistributionFunction = pdf;
		this.randomEngine = rEngine;
		
	}
	
	
	
	public int nextEventIndex(){
		int eventIndex = 0;
		double rndm = this.randomEngine.nextDouble();
		while(rndm > 0 && eventIndex < this.probabilityDistributionFunction.length){
			rndm -= this.probabilityDistributionFunction[eventIndex];
			eventIndex++;
		}
		return eventIndex-1;
	}
	
	
	public double[] getProbabilityDistributionFunction(){
		return this.probabilityDistributionFunction;
	}

	
	public void setProbabilityDistributionFunction(double[] pdf){
		this.probabilityDistributionFunction = pdf.clone();
	}
	
	
	public void updateEventGenerator(double[] pdf, RandomEngine rEngine){
		this.probabilityDistributionFunction = pdf;
		this.randomEngine = rEngine;
		
	}
	
	public void setRandomEngine(RandomEngine engine){
		this.randomEngine = engine;
	}
}
