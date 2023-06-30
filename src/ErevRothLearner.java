import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;


public class ErevRothLearner {
	
	int numberOfActions;
	int numberOfIterations;
	int[] actions;
	double scalingParameter;//used in the initial round of propensities
	double experimentationFactor;//tendency to explore
	double recencyFactor; //forgetfulness of past rewards
	int[][] actionCount; //records how many times previously an action was selected
	int lastAction; //action taken in previous round;
	int[] actionsHistory;
	double[][] propensityMatrixQ;//propensity matrix
	double[][] actionProbabilities;//stores probability of each action
	double averageStartingReward;
	String ErevRothLearnerUpdateType = "Standard";
	String ProbabilityModel = "Standard";
	ErevRothProblem erModel;
	int simulationRun = 0;
	double GibbsBoltzmannParameter = 1000;
	double lastObservedReward;
	Calendar calendar = Calendar.getInstance();
	Date now;
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	static int printCountProbs = 0;
	static int printCountProps = 0;
	
	public ErevRothLearner(ErevRothProblem problem){
		
		this.erModel = problem;
		this.ErevRothLearnerUpdateType = this.erModel.ErevRothLearnerUpdateType;
		this.ProbabilityModel = this.erModel.ProbabilityModel;
		this.GibbsBoltzmannParameter = this.erModel.GibbsBoltzmannParameter;
		this.numberOfActions = this.erModel.numberOfActions;
		this.numberOfIterations = this.erModel.numberOfIterations;
		this.scalingParameter = this.erModel.scalingParameter;
		this.experimentationFactor = this.erModel.experimentationFactor;
		this.recencyFactor = this.erModel.recencyFactor;
		this.averageStartingReward = this.erModel.averageStartingReward;
		this.actions = new int[this.numberOfActions];
		this.actionCount = new int[this.numberOfActions][this.numberOfIterations+1];
		this.actionsHistory = new int[this.numberOfIterations];
		this.propensityMatrixQ = new double[this.numberOfActions][this.numberOfIterations+1];
		this.actionProbabilities = new double[this.numberOfActions][this.numberOfIterations+1];
		initializeLearner();
	}
	
	
	private void initializeLearner(){
		int time = 1;
		initializeActions();
		initializeActionCount();
		initializeActionProbabilities();
		initializepropensityMatrixQPropensityMatrix();
		intializePropensities();
		updateIterationActionProbabilities(time);
	}
	
	public void solveErevRothProblem(){
		for(int t = 1; t <= this.numberOfIterations; t++){
			updateLearnerQValues(t);
		}
		simulationRun++;
//		if(printCountProbs==0){		
//			printProbabilities();		
//			printPropensityMatrix();
//		}
	}
	
	public int getSimulationRunAction(){
		return this.erModel.getSimulationRunAction();
	}
	
	private void updateLearnerQValues(int currentTime){
		int time = currentTime;
		if(time < this.numberOfIterations){
			updateIterationActionProbabilities(time);
			takeAction(time);
			updatePropensities(time);
		}
		else{//this last method call is to update the final round action probabilities
			updateIterationActionProbabilities(time);
		}
		
	}
	
	private void takeAction(int time){
		int act = 0;
		double[][] actionProbs = new double[this.numberOfActions][2];
		for(int i = 0; i<this.actionProbabilities.length; i++){
			actionProbs[i][0] = this.actionProbabilities[i][0];
			actionProbs[i][1] = this.actionProbabilities[i][time];
		}
		this.lastAction = this.erModel.act(actionProbs, time);
//		System.out.print("  Action taken: " + this.lastAction);
		//now add a binarry market to identify the number of times the selected action is taken
		//if an action is not selected at a round it is assigned zero
		//if it is take it is assigned 1;
		for(int i = 0; i<this.actionCount.length; i++){
			act = this.actionCount[i][0];
			if(this.lastAction == act){
				this.actionCount[i][time] = 1;
				break;
			}else {
				this.actionCount[i][time] = 0;
			}
		}
	}
	
	
	public void initializeActions(){
		for(int i = 0; i < this.numberOfActions; i++){
			this.actions[i] = i+1;
		}
	}
	
	public void initializeActionCount(){
		int actionID = 0;
		for(int i = 0; i < this.actions.length; i++){
			actionID = i+1;
			this.actionCount[i][0] = actionID;//record the actionID in the first column of the count matrix
			for(int j = 1; j <= this.numberOfIterations; j++){
				this.actionCount[i][j] = 0;
			}
		}
	}
	
	public void initializeActionProbabilities(){
		int actionID = 0;
		for(int i = 0; i < this.actions.length; i++){
			actionID = i+1;
			this.actionProbabilities[i][0] = actionID;//record the actionID in the first column of the count matrix
			for(int j = 1; j <= this.numberOfIterations; j++){
				this.actionProbabilities[i][j] = 0;
			}
		}
	}

	
	public void initializepropensityMatrixQPropensityMatrix(){
		int actionID = 0;
		for(int i = 0; i < this.actions.length; i++){
			actionID = i+1;
			this.propensityMatrixQ[i][0] = actionID;//record the actionID in the first column 
			//of the propensityMatrixQ matrix
			for(int j = 1; j <= this.numberOfIterations; j++){
				this.propensityMatrixQ[i][j] = 0; //set all propensities to zero
			}
		}
	}
	
	private void setInitialPreviousAction(){
		int min = 1;
		int max = this.actions[this.actions.length-1];
		
	}

	
	public void intializePropensities(){
		//all propensities are initialised to qja(t) = s(I) * R(I)/A
		//R(I) = initial reward
		//A total number of feasible actions
		//S(I) = scaler
		//Qja(t) = initial propesnisty of agent j taking action a at time t where at time 0 t = I
		int A = this.actions.length;
		double RI = this.averageStartingReward;
		double sI = this.scalingParameter;
		double QI = sI*(RI/A);
		for(int i = 0; i < this.propensityMatrixQ.length; i++){
			this.propensityMatrixQ[i][1] = QI; //set all propensities to zero
		}
	}
	
	public void updateIterationActionProbabilities(int currentTime){
		/**
		 * computes the below equation for the first time period/iteration
		 * Standard:
		 *  					Qja(t+1)
		 *  Pja(t+1) =  _______________________
		 *  			SUM{m=1 to A}[Qjm(t+1)] 
		 *  
		 *  Gibbs-Boltzmann:
		 *  *  					Qja(t+1)/T
		 *  Pja(t+1) =  _______e________________
		 *  							[Qjm(t+1)]/T
		 *  			SUM{m=1 to A}  e
		 *  
		 *  
		 */
		int time = currentTime;
		double Pja = 0;
		double Qja = 0;
		double sumQjm = getSumOfPropensityQValues(time);
		int a = 0;
		if(this.ProbabilityModel == "Standard"){
		for(int i = 0; i < this.actionProbabilities.length; i++){
			a = (int) this.actionProbabilities[i][0];
			Qja = getPropensityQValue(a, time);
			Pja = Rounding.roundSevenDecimals(Qja/sumQjm);
			this.actionProbabilities[i][time] = Pja;
//			System.out.println(" action " + (i+1)+"'s probability is: "+Pja);
		}
		}
		else if (this.ProbabilityModel == "Gibbs-Boltzmann"){
			for(int i = 0; i < this.actionProbabilities.length; i++){
				a = (int) this.actionProbabilities[i][0];
				Qja = getPropensityQValue(a, time);
				Pja = Rounding.roundSevenDecimals(Math.exp(Qja/this.GibbsBoltzmannParameter)/sumQjm);
				this.actionProbabilities[i][time] = Pja;
//				System.out.println(" action " + (i+1)+"'s probability is: "+Pja);
			}
		}
	}
	
	
	public String printProbabilities(){
		double Pja = 0;
		String output = "Simulation Round "+ this.simulationRun + "\n";
		PrintWriter writer = null;
		now = calendar.getTime();
		String strDate = sdfDate.format(now);
		String fileName = "ErevRothProbabilities-"+ printCountProbs+"-";
		String fileExtention = ".csv";
		String fullFileName = fileName + strDate + fileExtention; 
//		try {
//			writer = new PrintWriter(fullFileName, "UTF-8");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		static int printCountProbs = 0;
//		static int printCountProps = 0;
//		}
		printCountProbs++;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(fullFileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < this.actionProbabilities.length; i++){
			output += "Action: ,"+ (i+1)+ ",";
			for(int j = 1; j < this.actionProbabilities[i].length; j++){
				Pja = this.actionProbabilities[i][j];
				output += " " + Pja + ", ";
			}
			output += "\n";
		}
		writer.println(output);
		writer.close();
		
		return output;
	}
	
	public String printPropensityMatrix(){
		double Pja = 0;
		String output = "Simulation Round "+ this.simulationRun + "\n";
		PrintWriter writer = null;
		now = calendar.getTime();
	    String strDate = sdfDate.format(now);
		String fileName = "ErevRothPropensityMatrix-"+ printCountProps+"-";
		String fileExtention = ".csv";
		String fullFileName = fileName + strDate + fileExtention; 
//		try {
//			writer = new PrintWriter(fullFileName, "UTF-8");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		printCountProps++;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(fullFileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < this.propensityMatrixQ.length; i++){
			output += "Action: ,"+ (i+1)+ ",";
			for(int j = 1; j < this.propensityMatrixQ[i].length; j++){
				Pja = this.propensityMatrixQ[i][j];
				output += " " + Pja + ", ";
			}
			output += "\n";
		}
		writer.println(output);
		writer.close();
		
		return output;
	}
	
	
	public double getPropensityQValue(int actionID, int currentTime){
		double Qja = this.propensityMatrixQ[actionID-1][currentTime];//note actionIDs start from 1 so to get the correct
		//index in the java array, we much subtract 1 from the given actionID i.e. actionID = 1 => java array index = 0 
		return Qja;
		
	}
	
	
	public double getSumOfPropensityQValues(int currentTime){
		
		double sum = 0;
		double prop = 0;
		//for all actions loop through and sum all propensities at the currentTime iteration
		if(this.ProbabilityModel == "Standard"){
		for(int i = 0; i < this.actions.length; i++){
			sum += this.propensityMatrixQ[i][currentTime]; //sum all propensities at the iteration indexed by currentTime
		}
		
		}
		else if (this.ProbabilityModel == "Gibbs-Boltzmann"){
			for(int i = 0; i < this.actions.length; i++){
				prop = this.propensityMatrixQ[i][currentTime];
				sum += Math.exp(prop/this.GibbsBoltzmannParameter); //sum all propensities at the iteration indexed by currentTime
			}
			
		}
			
		return sum;
	}

	
	public void updatePropensities(int currentTime){
		//all propensities are updated using the Erev-Roth update function:
		//				qja(t+1) = (1-r)*qja(t) + E(j, a*, a',t, A, e)
		//
		// where:
		//		qja(t) = agent propensity at time t
		//		r = recency parameter
		//		e = exploration parameter
		//		E(@) = update function
		//		
		//	Note:
		//		For a Stanard Erev-Roth Model
		//					{Ra(t)*(1-e) if a* = a'
		//		E(@) =		{or
		//					{Ra(t)*[e/(A-1)]
		//
		//
		//		For a Nicolaosen Variation to the Erev-Roth Model
		//					{Ra(t)*(1-e) if a* = a'
		//		E(@) =		{or
		//					{Qa(t-1)*[e/(A-1)]
		//
		int aStar = getPreviousAction();
		int aPrime = 0;
		double aStarReward = this.erModel.getReward(aStar, currentTime); //get reward for taking previous selected action
//		System.out.println(" aStar reward for action is: "+aStarReward);
		double updateFactor = 0;
		double experienceValue = 0;
//		double previousQja = this.propensityMatrixQ[aStar-1][currentTime]; //get the past q-value
		double previousQja = 0;
		
		double propensityUpdate = 0;
		//loop through all actions
		for(int i = 0; i < this.actions.length; i++){
			aPrime = this.actions[i];//get the action selected
			previousQja = this.propensityMatrixQ[i][currentTime-1];//get previous propensity of action i
//			System.out.println(" action " + (i+1)+"'s previous propensity is: "+previousQja);
			if(this.ErevRothLearnerUpdateType == "Standard"){//check if this is a standard Erev-Roth Model
				if(aPrime == aStar){//update rule if selected action is the same as the past action
					updateFactor = (1 - this.experimentationFactor);
				} else {//update if selected action is different from the previous action
					updateFactor = this.experimentationFactor/(this.numberOfActions-1);
				}
				experienceValue = aStarReward*updateFactor; //set the experience value update 
			} else if(this.ErevRothLearnerUpdateType == "Nicolaosen Variation"){
				//check if this is a Nicolaosen et al. (2001) zero value reward modification to the  Erev-Roth Model
				if(aPrime == aStar){
					updateFactor = (1 - this.experimentationFactor);
					experienceValue = aStarReward*updateFactor; //set the experience value update
				} else {
					updateFactor = this.experimentationFactor/(this.numberOfActions-1);
					experienceValue = previousQja*updateFactor;  //set the experience value update
				}
			}
			propensityUpdate = (1-this.recencyFactor)*previousQja + experienceValue;
			this.propensityMatrixQ[i][currentTime+1] = Rounding.roundFiveDecimals(propensityUpdate); //set all propensities to zero
		}
	}

	
	public void giveFeedBack(double lastActionReward){
		this.lastObservedReward = lastActionReward;
	}
	
	private int getPreviousAction() {
		// TODO Auto-generated method stub
		return this.lastAction;
	}


	public static void  main(String[] args) {
		
//		String ErevRothLearnerUpdateType = "Standard";
		String ErevRothLearnerUpdateType = "Nicolaosen Variation";
		//		String ProbabilityModel = "Standard";
		String ProbabilityModel = "Gibbs-Boltzmann";
		int numberOfActions = 24;
		int numberOfIterations = 1000;
		double scalingParameter = 9;
		double GibbsBoltzmannParameter = 100;
		double experimentationFactor = 0.85;
		double recencyFactor = 0.50;
		double averageStartingReward = 100;
		double alphaBDistribution = 2;
		double betaBDistribution = 5;
		int randomSeed = 74974984;
		RandomEngine randomEngine = new MersenneTwister(randomSeed);
		
		/**
		 * public ErevRothProblem(String updateType, String probModel, int A, int T, double gbp, double scal, 
		 * 							double ef, double rf, double initProp, double alpha, double beta)
		 */
		
		ErevRothProblem problem = new ErevRothProblem(ErevRothLearnerUpdateType, ProbabilityModel, numberOfActions,
				numberOfIterations, GibbsBoltzmannParameter,
				scalingParameter, experimentationFactor, recencyFactor, averageStartingReward, alphaBDistribution, 
				betaBDistribution, randomEngine);
		
		
		ErevRothLearner erLearner = new ErevRothLearner(problem);
		erLearner.solveErevRothProblem();
		erLearner.getSimulationRunAction();
		
	}
	
	public int getHighestFrequencyActionTaken(){
		int count = 0;
		int maxCount = 0;
		int maxCountIndex = 0;
		int il = this.actionCount.length;
		int jl = this.actionCount[0].length;
		for(int i = 0; i < this.actionCount.length; i++){
			count = 0;
			for(int j = 1; j < this.actionCount[i].length; j++){
				count +=  this.actionCount[i][j];
			}
			
			if(count > maxCount){
				maxCount=count;
				maxCountIndex = i;
			}
		}
		return maxCountIndex;
	}
	
	public int getHighestPropensityActionTaken(){
		double sum = 0;
		double maxSum = 0;
		int maxSumIndex = 0;
		
		
		for(int i = 0; i < this.propensityMatrixQ.length; i++){
			sum = 0;
			for(int j = 1; j < this.propensityMatrixQ[i].length; j++){
				sum += this.propensityMatrixQ[i][j];
			}
			if(sum > maxSum){
				maxSum=sum;
				maxSumIndex = i;
			}
		}
		return maxSumIndex;
	}
	

}
