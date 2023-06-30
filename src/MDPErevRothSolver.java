import java.util.ArrayList;


public class MDPErevRothSolver {

	ErevRothAction lastAction;
	double lastSecuritisationRate;
	double newSecuritisationRate;
	double lastReward;
	double observedSecuritisationCost;
	public ActionList actionsDomainErevRoth = new ActionList();
	
	ErevRothSecuritisationProblem erSecModel;
	ErevRothLearner erSecModelLearner;
	ArrayList<ErevRothLearner> erSecModelLearnerHistory = new ArrayList<ErevRothLearner>();
	
	
	public MDPErevRothSolver(ErevRothSecuritisationProblem erSecModel){
		this.setErSecModel(erSecModel);
		this.erSecModelLearner = new ErevRothLearner(this.erSecModel);
		this.erSecModelLearnerHistory.add(this.erSecModelLearner);
		this.lastSecuritisationRate = this.erSecModel.getLastSecuritisationRate();
	}

	
	
	
	
	
	/**
	 * core method being called by agent to determine the next erev roth action
	 */
	public void determineNextAction(){
		erSecModelLearner.solveErevRothProblem();
//		int actionID = erSecModelLearner.getSimulationRunAction();
//		double SecRateChange = this.erSecModel.actionsDomainErevRoth.getActionAt(actionID-1).getActionDoubleType();

		int actionID = erSecModelLearner.getHighestFrequencyActionTaken();
//		int actionID = erSecModelLearner.getHighestPropensityActionTaken();
		double SecRateChange = this.erSecModel.actionsDomainErevRoth.getActionAt(actionID).getActionDoubleType();
//		newSecuritisationRate = lastSecuritisationRate + SecRateChange;
		newSecuritisationRate = SecRateChange;
//		System.out.println("newSecuritisationRate = "+newSecuritisationRate );
	}
	
	
	public void updateActionsDomain(ActionList actionsDomain){
		this.actionsDomainErevRoth = actionsDomain;
		this.erSecModel.setActionsDomainErevRoth(this.actionsDomainErevRoth);
	}
	
	public ErevRothSecuritisationProblem getErSecModel() {
		return erSecModel;
	}
	
	public void updateErevRothSecuritisationProblem(ErevRothSecuritisationProblem erSecModel, ActionList actionsDomain){
		this.setErSecModel(erSecModel);
		updateActionsDomain(actionsDomain);
		this.erSecModelLearner = new ErevRothLearner(this.erSecModel);
		this.erSecModelLearnerHistory.add(this.erSecModelLearner);
	}
	
	public void setErSecModel(ErevRothSecuritisationProblem erSecModel) {
		this.erSecModel = erSecModel;
	}
	public ErevRothLearner getErSecModelLearner() {
		return erSecModelLearner;
	}
	public void setErSecModelLearner(ErevRothLearner erSecModelLearner) {
		this.erSecModelLearner = erSecModelLearner;
	}

	
}
