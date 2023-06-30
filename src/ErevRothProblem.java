import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.BetaDistribution;

import cern.jet.random.engine.RandomEngine;


public class ErevRothProblem {
	
	public String ErevRothLearnerUpdateType = "Standard";
	public String ProbabilityModel = "Standard";
	public int numberOfActions = 30;
	public int numberOfIterations = 1000;
	public double scalingParameter = 9;
	public double experimentationFactor = 0.49;
	public double recencyFactor = 0.1;
	public double averageStartingReward = 100;
	Random stateProbGen = new Random();
	BetaDistribution defaultProbGen = new BetaDistribution(2,5);
	double alphaBDistribution = 2;
	double betaBDistribution = 5;
	double GibbsBoltzmannParameter = 1000;
	double[] probabilityDistributionFunction;
	RandomEngine randomEngine;
	ReinforcementLearningEventGenerator eventGenerator;
	int previousAction;
	ArrayList<Double[]>probabilityDistributionFunctions;
	int[] actionPath;
	int bestActionIndex;
	double[] rewards;
	
	
	
	
	public ErevRothProblem(String updateType, String probModel, int A, int T, double gbp, double scal, 
			double ef, double rf,
			double initProp, double alpha, double beta, RandomEngine randomEngine){
		
		this.GibbsBoltzmannParameter = gbp;
		this.ErevRothLearnerUpdateType = updateType;
		this.ProbabilityModel = probModel;
		this.numberOfActions = A;
		this.numberOfIterations = T+1;//used to ensure iterations count 1 to T
		this.scalingParameter = scal;
		this.experimentationFactor = ef;
		this.recencyFactor = rf;
		this.averageStartingReward = initProp;
		this.alphaBDistribution = alpha;
		this.betaBDistribution = beta;
		this.defaultProbGen = new BetaDistribution(alpha,beta);
		this.probabilityDistributionFunction = new double[this.numberOfActions];
		this.randomEngine = randomEngine;
		this.eventGenerator = new ReinforcementLearningEventGenerator(probabilityDistributionFunction, randomEngine);
		this.probabilityDistributionFunctions = new ArrayList<Double[]>();
		this.actionPath = new int[this.numberOfIterations];
		this.rewards = new double[]{200, -300, 400, 340, 250, 35, -10,-500, -204, 450, 
				700, 295, 701,350, 400, 301, 250, -450, -1, 0, 0, 45, 100, -100};
		
	}
	
	
	public boolean actionIsPermissible(int action){
		boolean isPermitted;
		
		if((action - 1 >= 0) ||(action + 1 <= this.numberOfActions)){
			isPermitted = true;
		} else{
			isPermitted = false;
		}
		return isPermitted;
	}
	
	
	
	public double getReward(int action, int time){
		double reward = 0;
		double prob = this.stateProbGen.nextDouble();
		double defProb = this.defaultProbGen.inverseCumulativeProbability(prob);
//		if(defProb < 0.58){
//			//reward = action*(1+0.05*(1 - 0.4));
//			//reward = 1000*(1+0.05*(1 - 0.4));
//			reward = action*1000*(1+0.05*(1 - 0.4))*Math.exp(action/time);
//		}else{
//			//reward = action*(1+0.05);
//			//reward = 1000*(1+0.05);
//			reward = action*1000*(1+0.05)*Math.exp(action/time);
//		}
		reward = this.rewards[action-1];
//		System.out.println(" reward for action is: "+reward);
		return reward;
	}


	public int act(double[][] actionProbs, int currentTime) {
		// TODO Auto-generated method stub
		
		double[] pdf = new double[actionProbs.length];
		int actionIndex = 0;
		int bestActionID = 0;
//		double maxProb = 0;
		for(int i = 0; i < actionProbs.length; i++){
			pdf[i] = actionProbs[i][1];
		}
		updateDistributionFunction(pdf);
		actionIndex = this.eventGenerator.nextEventIndex();
		setBestActionIndex(actionIndex);
		bestActionID = actionIndex+1;
		this.previousAction = bestActionID;
		this.actionPath[currentTime] = bestActionID;
		return bestActionID;
	}
	
	
	public void updateDistributionFunction(double[] pdf) throws IllegalArgumentException{
		if(pdf.length == this.numberOfActions){
			this.probabilityDistributionFunction = pdf.clone();
			this.eventGenerator.setProbabilityDistributionFunction(this.probabilityDistributionFunction);
		}else {
			throw new IllegalArgumentException(
					"Cannot set the given probability values:" +
			" Expected " + this.numberOfActions+" values, but recieved " + pdf.length + 
			". Previous values will be used.");
		}
	}
	
	public double[] getPorbabilityDistributionFunction(){
		return this.eventGenerator.getProbabilityDistributionFunction();
	}
	
	private void setBestActionIndex(int indx){
		this.bestActionIndex = indx;
	}
	
	public int getBestActionIndex(){
		return this.bestActionIndex;
	}
	
	public void setAverageStartingReward(double sr){
		this.averageStartingReward = sr;
	}
	
	/**
	 * this method returns the final action of the T learning rounds
	 * For the securitisation model this can be used to output the optimal securitisation rate
	 * @return
	 */
	public int getSimulationRunAction(){
//		System.out.println("\n"+"Final Action Take: "+this.actionPath[this.actionPath.length-1]);
		return this.actionPath[this.actionPath.length-1];
	}

}
