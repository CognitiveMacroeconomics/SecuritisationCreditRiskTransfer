import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;


public class MarkovDecisionProcess {
	
	ArrayList<MDPCapitalMarketsState> MDPStates;
	ArrayList<MDPPortfolioChoiceAction> actionsSpace;
	ArrayList<MDPCapitalMarketsState> reachableStates = new ArrayList<MDPCapitalMarketsState>();
	ArrayList<MDPPolicy> MDPPolicySet;
	int[] actions;
	int[] actionsAtCurrentState;
	ArrayList<MDPPortfolioChoiceAction> actionsAtCurrentStateList = new ArrayList<MDPPortfolioChoiceAction>();
	ArrayList<Integer> actionsAtCurrentStateIDList = new ArrayList<Integer>();
	int[] states;
	int numberOfStates;
	int numberOfActions;
	double[] probabilities;
	double[] deltas;
	double maxPermissibleWeightChange;
	double discountFactor;
	double linearfactor; //used as the constant parameter/multiplier for computing the linear transaction costs 
	double quadraticfactor; //used as the constant parameter/multiplier for computing the quadratic transaction costs
	private double expectedEquityLoss;
	private double expectedCreditDefault;
	private double liabilityGrowthRate;
	private int decisionHorizon;
	private int resetPeriod;
	boolean linearCostFunction = false; //determines if the model uses a linear cost function. It is defaulted to False
	private MDPModelInputParameters mdpModelInputParameters;
	boolean absoluteReturns;
	
	
	public MarkovDecisionProcess(ArrayList<MDPCapitalMarketsState> mdpStates, 
			ArrayList<MDPPortfolioChoiceAction> actSpace, MDPModelInputParameters mdpModelInputParameters,
			double expectedEquityLoss, double expectedCreditDefault, double liabilityGrowthRate, 
			int resetPeriod){
		this.MDPPolicySet = new ArrayList<MDPPolicy>();
		this.setMdpModelInputParameters(mdpModelInputParameters);
		this.MDPStates = mdpStates;
		this.actionsSpace = actSpace;
		this.numberOfStates = this.MDPStates.size();
		this.numberOfActions = this.actionsSpace.size();
		this.maxPermissibleWeightChange = this.mdpModelInputParameters.getMaximumPermissbleChangeInWeight();
		this.discountFactor = this.mdpModelInputParameters.getGammaDiscountFactor();
		this.expectedEquityLoss = expectedEquityLoss;
		this.expectedCreditDefault = expectedCreditDefault;
		this.liabilityGrowthRate = liabilityGrowthRate;
		this.linearCostFunction = this.mdpModelInputParameters.isLinearCostFunction();
		this.linearfactor = this.mdpModelInputParameters.getLinearfactor();
		this.quadraticfactor = this.mdpModelInputParameters.getQuadraticfactor();
		this.decisionHorizon = this.mdpModelInputParameters.getNumberOfDecisionEpochs();
		this.absoluteReturns = this.mdpModelInputParameters.isAbsoluteReturns();
		this.resetPeriod = resetPeriod;
		setActions(this.actionsSpace);
		setStates(this.MDPStates);
	}
	
	
	
	private void setActions(ArrayList<MDPPortfolioChoiceAction> actSpace){
//		System.out.println("Setting Actions...");
		this.actions = new int[actSpace.size()];
		//loop through all actions and pass their ids to an index in the integer array
		for(int i = 0; i< actSpace.size(); i++){
			this.actions[i] = actSpace.get(i).getActionID();
		}
//		System.out.println("Actions Defined...");
	}
	
	
	private void setStates(ArrayList<MDPCapitalMarketsState> mdpStates){
//		System.out.println("Setting States...");
		this.states = new int[mdpStates.size()];
		//loop through all actions and pass their ids to an index in the integer array
		for(int i = 0; i< mdpStates.size(); i++){
			this.states[i] = mdpStates.get(i).getStateID();
		}
//		System.out.println("States Defined...");
	}
	
	
	private void setListOfActionsAtCurrentState(ArrayList<Integer> actionIDs){
		actionsAtCurrentState = new int[actionIDs.size()];
		actionsAtCurrentStateIDList = actionIDs;
		actionsAtCurrentStateList = new ArrayList<MDPPortfolioChoiceAction>();
		int id = 0;
		//loop through all actions and pass their ids to an index in the integer array
				for(int i = 0; i< actionIDs.size(); i++){
					this.actionsAtCurrentState[i] = actionIDs.get(i);
					id = actionIDs.get(i);
					for(int j = 0; j < this.actionsSpace.size(); j++){
						if(id == this.actionsSpace.get(j).getActionID()){
							actionsAtCurrentStateList.add(this.actionsSpace.get(j));
						}
					}
				}
	}
	
	
	public void setOptimalPolicy(int state, int action){
		MDPCapitalMarketsState mdpS = this.MDPStates.get(state);
		MDPPortfolioChoiceAction act = this.actionsAtCurrentStateList.get(action);
//		System.out.println(act.label());
		MDPPolicySet.add(new MDPPolicy(mdpS,act));
		
	}
	
	
	public void setDeltas(ArrayList<Double> d){
		
		this.deltas = new double[d.size()];
		
		for(int i = 0; i < d.size(); i++){
			this.deltas[i] = d.get(i);
		}
		
	}
	
	
	public double[] get_next_states(int state, int action){

		MDPCapitalMarketsState mdpS = this.MDPStates.get(state);
		MDPPortfolioChoiceAction act = this.actionsAtCurrentStateList.get(action);
		reachableStates = getReachableStates(mdpS, act);
		probabilities = new double[reachableStates.size()];
		
		PortfolioAssetsState ps = mdpS.getPortfolioAssetState();;
		PortfolioAssetsState psp;
		TreeSet<Transition> adjListTransition = ps.getAdjacencyListTransitions();;
		Iterator<Transition> itr = adjListTransition.iterator();
		Transition trans;
		double zero = 0.0;
		double prob = 0;

		while(itr.hasNext()){
			trans = itr.next();
			psp = (PortfolioAssetsState) trans.getDestinationState();
			for(int i = 0; i < reachableStates.size(); i++){
				MDPCapitalMarketsState s = reachableStates.get(i);
				if(psp.compareTo(s.getPortfolioAssetState())){	
					prob = trans.getTransitionProbability();
					probabilities[i] = prob;
//					System.out.println(prob);
				}
			}
		}
//		System.out.println(probabilities.length);
		return probabilities;
	}
	
	
	
	private ArrayList<MDPCapitalMarketsState> getReachableStates(
			MDPCapitalMarketsState mdpS, MDPPortfolioChoiceAction act) {
		// TODO Auto-generated method stub
		ArrayList<MDPCapitalMarketsState> reachStates = new ArrayList<MDPCapitalMarketsState>();
		double actEW = act.getEquityAssetWeightChoice();
		double actCdW = act.getCreditAssetWeightChoice();
		double actChW = act.getCashAssetWeightChoice();

		double mdpSEW = mdpS.getEquityAssetWeight();
		double mdpSCdW = mdpS.getCreditAssetWeight();
		double mdpSChW = mdpS.getCashAssetWeight();
		
		double eqtyDiff = Math.abs(actEW - mdpSEW);
		double crdtDiff = Math.abs(actCdW - mdpSCdW);
		double cshDiff  = Math.abs(actChW - mdpSChW);
		double eqtyDiffcrdtDiffSum = (eqtyDiff + crdtDiff)*100;
		double eqtyDiffcshDiffSum = (eqtyDiff + cshDiff)*100;
		double crdtDiffcshDiffSum = (crdtDiff + cshDiff)*100;
		int maxPermissibleWeightChangePerc = (int) (maxPermissibleWeightChange*100);
		
		double mdpSPEW = 0;
		double mdpSPCdW = 0;
		double mdpSPChW = 0;
		
		if(//confirm the validity of the action. All actions should in theory be valid if this method is called
				((int) eqtyDiffcrdtDiffSum <= maxPermissibleWeightChangePerc)
				||
				((int) eqtyDiffcshDiffSum <= maxPermissibleWeightChangePerc)
				||
				((int) crdtDiffcshDiffSum <= maxPermissibleWeightChangePerc) 
				){
			for(MDPCapitalMarketsState s : MDPStates){
				mdpSPEW = s.getEquityAssetWeight();
				mdpSPCdW = s.getCreditAssetWeight();
				mdpSPChW = s.getCashAssetWeight();
				if((mdpSPEW == actEW) && (mdpSPCdW == actCdW) && (mdpSPChW == actChW)){
					reachStates.add(s);
				}
			}
		}
		return reachStates;
	}



	public int get_num_actions(int state){
		MDPCapitalMarketsState s = this.MDPStates.get(state);
		ArrayList<Integer> actionIDs = new ArrayList<Integer>();
		double mdpSEW = s.getEquityAssetWeight();
		double mdpSCdW = s.getCreditAssetWeight();
		double mdpSChW = s.getCashAssetWeight();
		
		double mdpSPEW = 0;
		double mdpSPCdW = 0;
		double mdpSPChw = 0;
		double eqtyDiff = 0;
		double crdtDiff = 0;
		double cshDiff  = 0;
		double eqtyDiffcrdtDiffSum = 0;
		double eqtyDiffcshDiffSum = 0;
		double crdtDiffcshDiffSum = 0;
		int count = 0;
		boolean eqtyDiffcrdtDiffS = false;
		boolean eqtyDiffcshDiffS = false;
		boolean crdtDiffcshDiffS = false;
		int maxPermissibleWeightChangePerc = (int) (maxPermissibleWeightChange*100);
		
		for(MDPPortfolioChoiceAction a : this.actionsSpace){
			mdpSPEW = a.getEquityAssetWeightChoice();
			mdpSPCdW = a.getCreditAssetWeightChoice();
			mdpSPChw = a.getCashAssetWeightChoice();
			eqtyDiff = Math.abs(mdpSPEW - mdpSEW);
			crdtDiff = Math.abs(mdpSPCdW - mdpSCdW);
			cshDiff = Math.abs(mdpSPChw - mdpSChW);
			
			eqtyDiffcrdtDiffSum = (eqtyDiff + crdtDiff)*100;
			eqtyDiffcshDiffSum = (eqtyDiff + cshDiff)*100;
			crdtDiffcshDiffSum = (crdtDiff + cshDiff)*100;
			
			if(
					((int) eqtyDiffcrdtDiffSum <= maxPermissibleWeightChangePerc)
					//sum of the absolute change in both credit and equity assets is less than double the max change
					||
					((int) eqtyDiffcshDiffSum <= maxPermissibleWeightChangePerc)
					||
					((int) crdtDiffcshDiffSum <= maxPermissibleWeightChangePerc) 
					
					){
				count++;
				actionIDs.add(a.getActionID());
				
			}
		}
		
		setListOfActionsAtCurrentState(actionIDs);
		return count;
		
	}

	
	public double get_average_reward(int state, int action){
		MDPCapitalMarketsState s = this.MDPStates.get(state);
//		System.out.println(s.toString());
		double transProb = 0;
		double averageReward = 0;
		int index = 0;
		MDPPortfolioChoiceAction a = this.actionsAtCurrentStateList.get(action);
//		System.out.println(a.label());
//		System.out.println(transProb);
		for(MDPCapitalMarketsState sp :reachableStates){
			index = reachableStates.indexOf(sp);
			transProb = this.probabilities[index];
			averageReward += transProb*getReward(sp,a);
		}
		return averageReward;
	}
	
	
	public double getReward(MDPCapitalMarketsState sPrime, MDPPortfolioChoiceAction action) {
		// TODO Auto-generated method stub
		double surplus = 0;
		double surplus_t_k = 0;
		double reward = 0;
		if(sPrime instanceof MDPCapitalMarketsState
				 && action instanceof MDPPortfolioChoiceAction){
			double eqtyWeightChange = action.getEquityAssetWeightChoice();
			double crdtWeightChange = action.getCreditAssetWeightChoice();
			double cashWeightChange = action.getCashAssetWeightChoice();
			
			double[] weightChanges = {eqtyWeightChange, crdtWeightChange, cashWeightChange};
			double transCost = getTransactionCost(this.linearCostFunction, weightChanges);
			
			double nAEW = action.getEquityAssetWeightChoice();
			double nSER = sPrime.getEquityAssetExpectedReturn();
			double nACW = action.getCreditAssetWeightChoice();
			double nSCR = sPrime.getCreditAssetExpectedReturn();
			double nACshW = action.getCashAssetWeightChoice();
			double nSCshR = sPrime.getCashAssetExpectedReturn();
			double eSR = 1;
			double cSR = 1;
			
			if(this.absoluteReturns == true){
				for(int i = 1; i <= this.decisionHorizon; i++){
					//sum up all earnings over the decision horizon
					if(i <= this.resetPeriod){
						surplus += computeEarnings(nAEW, nSER, nACW, nSCR, nACshW, nSCshR, eSR, cSR) 
								- (transCost + this.liabilityGrowthRate);
					}
					else {
						eSR = ( 1 - this.expectedEquityLoss);//note that this  expectedEquityLoss is treated as the 
						//of loss given default {i.e. if agents expect losses to increase (i.e. bearish) then expectedEquityLoss > 0
						// otherwise (i.e. bullish) expectedEquityLoss < 0 
						//the default value of expectedEquityLoss = 0}
						cSR = (1 + this.expectedCreditDefault);//note that this is plus because the expectedCreditDefault is treated as the 
						//inverse of loss given default {i.e. if agents expect defaults to increase (i.e. bearish) then expectedCreditDefault < 0
						// otherwise (i.e. bullish) expectedCreditDefault > 0 }
						surplus += computeEarnings(nAEW, nSER, nACW, nSCR, nACshW, nSCshR, eSR, cSR) 
								- (transCost + this.liabilityGrowthRate);
					}
				}
				reward = surplus;
			}
				else{
					for(int i = 1; i <= this.decisionHorizon; i++){
						surplus_t_k = surplus;
						if(i <= this.resetPeriod && i > 1){
							surplus += computeEarnings(nAEW, nSER, nACW, nSCR, nACshW, nSCshR, eSR, cSR) 
									- (transCost + this.liabilityGrowthRate);
						}
						else {
							eSR = ( 1 - this.expectedEquityLoss);//note that this  expectedEquityLoss is treated as the 
							//of loss given default {i.e. if agents expect losses to increase (i.e. bearish) then expectedEquityLoss > 0
							// otherwise (i.e. bullish) expectedEquityLoss < 0 
							//the default value of expectedEquityLoss = 0}
							cSR = (1 + this.expectedCreditDefault);//note that this is plus because the expectedCreditDefault is treated as the 
							//inverse of loss given default {i.e. if agents expect defaults to increase (i.e. bearish) then expectedCreditDefault < 0
							// otherwise (i.e. bullish) expectedCreditDefault > 0 }
							surplus += computeEarnings(nAEW, nSER, nACW, nSCR, nACshW, nSCshR, eSR, cSR) 
									- (transCost + this.liabilityGrowthRate);
						}
						reward *= Math.pow((1 + Math.log(surplus/surplus_t_k)), (this.discountFactor/i)); 
				}
			}
		}
//		System.out.println("surplus "+ surplus);
		return reward;
	}
	
	
	
	
	
	private double computeEarnings(double nAEW, double nSER, double nACW,
			double nSCR, double nACshW, double nSCshR, double eSR, double cSR) {
		// TODO Auto-generated method stub
		double earnings =  nAEW * (1+ nSER)*eSR + nACW * (1+ nSCR) * cSR + nACshW * (1+ nSCshR);
		
		return earnings;
	}
	
	public double getTransactionCost(boolean lnCost, double[] deltaWeight){
		double transactionCost = 0;
		
		if(lnCost == true){
			for(int i = 0; i < deltaWeight.length; i++){
				transactionCost = transactionCost + deltaWeight[i]*(this.linearfactor);
			}
		}else {
			for(int i = 0; i < deltaWeight.length; i++){
				transactionCost = transactionCost + deltaWeight[i]*(this.linearfactor + Math.pow(this.quadraticfactor, 2));
			}
		}
		
		return transactionCost;
	}
	

	public int get_total_actions(){
		return this.numberOfActions;
	}
	
	public int get_total_states(){
		return this.numberOfStates;
	}
	
	
	public double get_discount_factor(){
		return this.discountFactor;
	}
	
	
	/**
	 * @param mdpModelInputParameters the mdpModelInputParameters to set
	 */
	public void setMdpModelInputParameters(MDPModelInputParameters mdpModelInputParameters) {
		this.mdpModelInputParameters = mdpModelInputParameters;
	}


	/**
	 * @param mdpModelInputParameters the mdpModelInputParameters to set
	 */
	public MDPModelInputParameters getMdpModelInputParameters() {
		return this.mdpModelInputParameters;
	}

	
	public void updateMDPStates(ArrayList<MDPCapitalMarketsState> states){
		Collections.copy(this.MDPStates,states);
	}
	
	
	public void updateActionsSpace(ArrayList<MDPPortfolioChoiceAction> actions){
		 Collections.copy(this.actionsSpace,actions);
	}
	
	public boolean isAbsoluteReturns(){
		return this.absoluteReturns;
	}

}
