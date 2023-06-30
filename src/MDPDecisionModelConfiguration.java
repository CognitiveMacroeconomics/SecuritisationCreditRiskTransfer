



import java.util.ArrayList;
import java.util.List;


public class MDPDecisionModelConfiguration {
	
	/**
	 * This class is used to create all the components to be used in the MDP simulation.
	 * It creates the 
	 *  1: MDPModelContext used to define:
	 *  			a) the reward functions of the agents
	 *  			b) select the initial state of the agent
	 *  			c) the possible set of actions open to the agent at each state
	 *  			d) the potentially reachable states the agent can transition to given its current state and chosen action
	 *     It is the MDPModelContext that the solvers/algorithms will be run against to determine the agents best action
	 *  2: States space capturing all possible states as defined by the data
	 *  3: Actions  space capturing all possible actions within the model
	 *  4: MDPStatesPathEngine to randomly generate a sequence of paths from an initial state to a final state. 
	 *     This is used when there are a finite number of iterations permitted
	 *  
	 *  When building these in the full CRT model, each agent (LAPF) will have its own MDPDecisionModelConfiguration and thus independent MDPModelContexts
	 *  as well as solvers/algorithms to use to find its best action in all states of the economy. The state of the economy will be based on the agents expectations of
	 *  movements in traditional assets and credit assets
	 *  and called something along the lines of CapitalMarketsmdpModelContext. 
	 *  From here the CapitalMarketsStates and CapitalMarketsPortfolioChanges can be defined and input into the CapitalMarketsmdpModelContext.
	 *  
	 *  This class can pretty much be copied and used as a CapitalMarketsmdpModelContext ImdpModelContext factory and thus called CapitalMarketsmdpModelContextFactory
	 *  
	 *  
	 */
	

	String decisionAnalysisPeriodEndString; //possible values "2003", "2007", "Full"
	int decisionAnalysisPeriod; // possible values 0, 1, 2
	double discountfactor; //the discount factor at which future payoffs will be discounted 
	List<AdjacencyMatrixContainer> JOINED_PROBABILITYMATRIX_LIST = new ArrayList<AdjacencyMatrixContainer>();//data collection on states information
	List<List<AdjacencyMatrixContainer>> JOINED_PROBABILITYMATRIX_HISTORY = new ArrayList<List<AdjacencyMatrixContainer>>();//data collection on states
	double[] crdtPredefinedExpectedReturns;//data collection on expected returns on credit asset
	double[] eqtyPredefinedExpectedReturns;//data collection on expected returns on equity asset
	double riskFreeCashAssetReturn; //used to determine if model will use the choice of portfolio weights or the choice of changes in portfolio weights
	ArrayList<double[]> transitions;//collection of transition probabilities lists used to create each PortfolioAssetsState's
																//transitions to adjacent states
	ArrayList<PortfolioAssetsState> portfolioStates;//collection of PortfolioAssetsState objects 
																				  //used in creating the MDP states
	ArrayList<PortfolioWeights> weights;//collection of PortfolioAssetsState objects 
	  //used in creating the MDP states
	ArrayList<PortfolioAction> actionsSpace; //collection of all actions available to chose from 
	ArrayList<MDPCapitalMarketsState> assetAllocationMDPStatesSpace;
	
	
	boolean shortSelling = false; //determines if short selling is permitted in the model. It is defaulted to False
	boolean linearCostFunction = false; //determines if the model uses a linear cost function. It is defaulted to False
	boolean portfolioWeightChoiceModel;
	
	double linearfactor; //used as the constant parameter/multiplier for computing the linear transaction costs 
	double quadraticfactor; //used as the constant parameter/multiplier for computing the quadratic transaction costs
	double assetWieghtIncrements; //increment used to create the potfolio weights that are used to define the MDP states 
	double changeInWeightIncrement; //represents the rate at which changes can be made to portfolio weights 
	double maximumPermissbleChangeInWeight; //represents the maximum change in portfolio weights this will be the range from positive to negative
	
	MDPCapitalMarketsInvestorContext mdpModelContext;
	
	MDPStatesPathEngine statesGenerator;
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CONSTRUCTOR>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public MDPDecisionModelConfiguration(TransitionProbabilitiesEngine tpEngine, String prdEndString, boolean portWeightChoice, 
			boolean shtSell, boolean lnCost, boolean stochastic, int numberOfEpisodes, 
			double riskFreeRate, double astWghtIncr, double cInWghtIncr, double maxPermChgInWght, double discntFactor, 
			double lnCostFactor, double QuadCostFactor){
		System.out.println("New MDPDecisionModelConfiguration.... ");
		this.portfolioStates = new ArrayList<PortfolioAssetsState>();
		this.weights = new ArrayList<PortfolioWeights>();
		this.transitions = new ArrayList<double[]>();
		this.actionsSpace = new ArrayList<PortfolioAction>();
		this.assetAllocationMDPStatesSpace = new ArrayList<MDPCapitalMarketsState>();
		//this will be a class construction called by the Economy class in the full simulation
		if(portWeightChoice == true){
			this.mdpModelContext = new MDPCapitalMarketsInvestorContext(shtSell, lnCost, astWghtIncr, cInWghtIncr, 
					maxPermChgInWght, discntFactor, lnCostFactor, QuadCostFactor);
//		} else{
//			this.mdpModelContext = new MDPCapitalMarketsModelContext(shtSell, lnCost, astWghtIncr, cInWghtIncr, 
//				maxPermChgInWght, discntFactor, lnCostFactor, QuadCostFactor);
		}
		this.decisionAnalysisPeriodEndString = prdEndString;
		this.setPeriodEnd(this.decisionAnalysisPeriodEndString);
		this.riskFreeCashAssetReturn = riskFreeRate;
		this.shortSelling = shtSell;
		this.linearCostFunction = lnCost;
		this.assetWieghtIncrements = astWghtIncr;
		this.changeInWeightIncrement = cInWghtIncr;
		this.maximumPermissbleChangeInWeight = maxPermChgInWght;
		this.discountfactor = discntFactor;
		this.linearfactor = lnCostFactor;
		this.quadraticfactor = QuadCostFactor;
		
		System.out.println("New MDPDecisionModelConfiguration.... ");
		//create the full set of states and actions
		this.initializeDefaultModel(tpEngine, stochastic, this.decisionAnalysisPeriodEndString, this.shortSelling, numberOfEpisodes, this.assetWieghtIncrements);

		System.out.println("New MDPDecisionModelConfiguration initializeDefaultModel.... ");
		
//		this.initialize(this.decisionAnalysisPeriodEndString, this.shortSelling, this.assetWieghtIncrements);//create the full set of states and actions

	}
	
	

	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CORE METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * set the value of the predefined transition probability space
	 * this is for training purposes only JOINED_PROBABILITYMATRIX_LIST
	 * @param decisionAnalysisPeriod
	 */
	public void setPredefinedTransitionProbabilityMatrix(int decisionAnalysisPeriod){
		DefinedTransitionProbabilities definedTransitionProbabilities = new DefinedTransitionProbabilities();
		switch(decisionAnalysisPeriod){
		case 0://using market data from period 2000 to 2003
			definedTransitionProbabilities.setOMBAX_SP500_PROBABILITYMATRIXEnd2003();
			JOINED_PROBABILITYMATRIX_LIST = definedTransitionProbabilities.OMBAX_SP500_PROBABILITYMATRIXEnd2003;
			break;
		case 1://using market data from period 2004 to 2007
			definedTransitionProbabilities.setOMBAX_SP500_PROBABILITYMATRIXEnd2007();
			JOINED_PROBABILITYMATRIX_LIST = definedTransitionProbabilities.OMBAX_SP500_PROBABILITYMATRIXEnd2007;
			break;
		case 2://using market data from the full range of periods
			definedTransitionProbabilities.setOMBAX_SP500_PROBABILITYMATRIXEndFull();
			JOINED_PROBABILITYMATRIX_LIST = definedTransitionProbabilities.OMBAX_SP500_PROBABILITYMATRIXEndFull;
			break;
		default://default to using market data from period 2000 to 2003
			definedTransitionProbabilities.setOMBAX_SP500_PROBABILITYMATRIXEnd2003();
			JOINED_PROBABILITYMATRIX_LIST = definedTransitionProbabilities.OMBAX_SP500_PROBABILITYMATRIXEnd2003;
			break;
		}
	}
	
	
	/**
	 * set the value of the credit and equity expected return arrays based on evaluation period
	 * @param decisionAnalysisPeriod
	 */
	public void setDefaultAssetClassesExpectedReturns(int decisionAnalysisPeriod){
		switch(decisionAnalysisPeriod){
		case 0://using market data from period 2000 to 2003
			crdtPredefinedExpectedReturns = DefiinedStateReturns.OMBAX_ExpectedReturns2000_3;
			eqtyPredefinedExpectedReturns = DefiinedStateReturns.SP500_ExpectedReturns2000_3;
			break;
		case 1://using market data from period 2004 to 2007
			crdtPredefinedExpectedReturns = DefiinedStateReturns.OMBAX_ExpectedReturns2004_7;
			eqtyPredefinedExpectedReturns = DefiinedStateReturns.SP500_ExpectedReturns2004_7;
			break;
		case 2://using market data from the full range of periods
			crdtPredefinedExpectedReturns = DefiinedStateReturns.OMBAX_ExpectedReturnsFull;
			eqtyPredefinedExpectedReturns = DefiinedStateReturns.SP500_ExpectedReturnsFull;
			break;
		default://default to using market data from period 2000 to 2003
			crdtPredefinedExpectedReturns = DefiinedStateReturns.OMBAX_ExpectedReturns2000_3;
			eqtyPredefinedExpectedReturns = DefiinedStateReturns.SP500_ExpectedReturns2000_3;
			break;
		}
	}
	
	
	
	/**
	 * set the value of the predefined transition probability space
	 * this is for stochastically generated transition probabilities
	 * @param decisionAnalysisPeriod
	 */
	public void setJointTransitionProbabilityMatrix(List<AdjacencyMatrixContainer> STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX_LIST){
		
		JOINED_PROBABILITYMATRIX_LIST = STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX_LIST;
	}
	
	
	/**
	 * set the value of the credit and equity expected return arrays based on stochastically generated 
	 * series
	 * @param decisionAnalysisPeriod
	 */
	public void setAssetClassesExpectedReturns(double[] assetTraditionalAverageReturns, double[] assetCreditAverageReturns){
		eqtyPredefinedExpectedReturns = assetTraditionalAverageReturns;
		crdtPredefinedExpectedReturns = assetCreditAverageReturns;
	}
	

	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<DEFAULT TWO RISKY ASSETS NO CASH>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/**
	 * This method creates the set of portfolio asset states. this does not include the weighting of each asset
	 * just the combination of the assets and the possible states each can be in with the expected returns in each
	 * of those states  
	 * @param decisionAnalysisPeriod
	 */
	public void createDefaultModelPortfolioAssetStates(int decisionAnalysisPeriod){
		/**
		 * The constructions in this method are based on the following sample definition of
		 * an adjacency matrix container with  (int crdtAssetState, int eqtyAssetState, double[] transitionProbArray)
		 *  AdjacencyMatrixContainer.createTwoAssetAdjacencyList
		 * (-1,-1, new double[] {0.169666228,0.002648894,0.166423617,0.110102552,0.001718963, 
		 * 0.107998304, 0.221107585, 0.003452016, 0.216881841})
		 */
		int eqtyDir;
		int crdtDir;
		
		/**
		 * Create the portfolio asset states and add to the <PortfolioAssetsState> StateSet
		 * 
		 * createDefaultTwoAssetChoiceModelMDPState(int equityStateLevel, int creditStateLevel, 
			AssetState equityState, AssetState creditState)
		 */
		portfolioStates.clear();
		transitions.clear();
		for(int i = 0; i<this.JOINED_PROBABILITYMATRIX_LIST.size(); i++){
			//get the equity asset level for the state
			eqtyDir = (int) JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getAssetTwoState();
			//get the credit asset level for the state
			crdtDir = (int) JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getAssetOneState();
			//get the transition probabilities for the state
			double[] transition =  JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getStateTransitionProbabilities(); 
			//add to the transition probabilities to arraylist that will be used to populate the state transitions
			transitions.add(transition);
			//create the equity asset state
			AssetState equityState = new EquityAssetClassState(eqtyDir, this.eqtyPredefinedExpectedReturns);
			//create the credit asset state
			AssetState creditState = new CreditAssetClassState(crdtDir, this.crdtPredefinedExpectedReturns);
			//create the PortfolioAssetsState object and add it to the <PortfolioAssetsState> StateSet
			portfolioStates.add(PortfolioAssetsState.createDefaultTwoAssetChoiceModelPortfolioAssetState(
					eqtyDir, crdtDir, equityState, creditState));
		}
		
		/**
		 * next step is to build out the transitions between states for each of the PortfolioAssetsState objects
		 * created above. 
		 * The relevant method in the PortfolioAssetsState class is:
		 * addAdjecentListTransitionsStateProbability(AssetAllocationMDPState adjcentState, double transitionProbability)
		 * 
		 * this construction assumes that the transitions array and the portfolioAssetStates set are of the same ordering. 
		 * So 
		 * 
		 * for each state in the list of PortfolioAssetStates
		 * ----get transition probability array from transitions
		 * ------for each state in list of PortfolioAssetStates and each transition probability in the collected transition probability array
		 * ------add the state and corresponding transition probability to the highlighted PortfolioAssetState
		 * 
		 * 
		 */
		if(portfolioStates.size() == transitions.size()){//ensures that there are equal number of states and transitions
			for(int i = 0; i< portfolioStates.size(); i++){
				double[] transition = transitions.get(i);
				for(int l = 0; l< portfolioStates.size(); l++){
					portfolioStates.get(i).addAdjecentListTransitionsStateProbability(portfolioStates.get(i), portfolioStates.get(l), transition[l]);
				}//end adding of Adjacent State Transitions Probabilities to the current state's Transitions
			}
		}
	}//end of method definition

	
	/**
	 * THis method definition is used to create the set of asset weights to be used to create the MDP model states
	 * the weights act as the basis upon which permissible actions can be defined they have no bearing on the 
	 * transitions from sttate to state beyond defining which subset of states will be possible
	 * 
	 * Note that weights are created as an arraylist of <PortfolioWeights> in the PortfolioWeightsFactory class
	 * based on the increments in the weightings set.
	 * 
	 * @param decisionAnalysisPeriod
	 * @param shortSelling
	 */
	public void createDefaultModelAssetWeights(double assetWieghtIncrements, boolean shortSelling){
		if(shortSelling == false){
			this.weights = PortfolioWeightsFactory.createDefaultTwoAssetPortfolioWeightsList(assetWieghtIncrements);
		}
	}
	
	/**
	 * This method definition is used to create the MDP states space upon which policy decisions will be made
	 * it does this by going through the population of possible asset weight groupings and for each of these attaches 
	 * each
	 * portfolio state from the entire population of portfolio states. 
	 * The result is that the MDP states space will have a total of m*n states where m = total number of possible weights
	 * and n = total number of possible portfolio asset states
	 * 
	 * In the default model example used here. There are 9 possible portfolio asset states and assuming an increment 
	 * of 0.01, there will be
	 * 100 possible asset weight combinations meaning a total of 100*9 = 900 MDP model states.
	 *  
	 * @param portStates
	 * @param w
	 */
	@SuppressWarnings("unchecked")
	public void createDefaultModelAssetAllocationMDPState(ArrayList<PortfolioAssetsState> portStates,
			ArrayList<PortfolioWeights> w){
		
		int numAssts;
		for(int i = 0; i< w.size(); i++){
			for(int j = 0; j < portStates.size(); j++){
				numAssts = portStates.get(j).getProperties().length;
				assetAllocationMDPStatesSpace.add(MDPCapitalMarketsState.createDefaultAssetChoiceModelMDPState(this.mdpModelContext, w.get(i),
						portStates.get(j), numAssts));
			}
		}
	}
	
	
	/**
	 * This method creates the space of all possible actions in the MDP model.
	 * it uses the maximum permissible change and the increment value to define 
	 * the set of all actions
	 * the relevant constructor call here is 
	 * public static void createDefaultTwoEquityCreditAsset(double eqAstWghtChng, double crdAstWghtChng, boolean shrtSell)
	 * 
	 * Parameters
	 * @param maxChange
	 * @param increment
	 * 
	 * steps
	 * 1:determine if maxChange is divisible by increment. If not increase maxChange by 1 or 0.1 depending on scale
	 * 1: compute the total number of actions "actionCount"
	 * 2: loop through the total number of actions
	 * 3: at each step of the loop, add the product of the loop phase and the increment to the inverse of the maxChange
	 *    i.e. -1*maxChange + (i * increment) for  i = {0,1,2,3,4...,actionCount-1}. This algorithm doesn't work for all cases
	 *    on for increments of 0.1,0.2,0.5
	 * 
	 */
	public void createActions(double maxChange, double increment, boolean shrtSell){
		double rangeCheck = 0.01;
		//determine the scale of maxChange. If it is not a decimal with value greater than zero but less than or equal to 1
		if(maxChange >= 1 && maxChange*1 > 1){
			rangeCheck = 1;
		}
		int k =  (int) ((maxChange*100) % (increment*100)); //since both maxChange and increment are double values, taking the modulus results in a none zero
															// consequently, both numbers first need to be multiplied by 100
		while( k != 0){
			maxChange = maxChange + rangeCheck;//this increase the maxChange value if the remainder of maxChange/increment is not equal to 0
			k = (int) ((maxChange*100) % (increment*100));
		}
		
		int actionsCount = (int) ((maxChange * 2)/increment)+1; //determine the total number of actions
															   //this is computed as is because for example
															   // maxChange = 0.1 with increment 0.05 will mean
															   //there are 5 possible actions {-0.1, -0.05, 0, 0.05, 0.1} 
		//check that range is 0<mc<=1 and no short selling
		if(rangeCheck == 0.01 && shrtSell == false) {
			//now loop through creating all actions up to (actionsCount -1)
			for(int i = 0; i<actionsCount; i++){
				double eqAstWghtChng = -1*maxChange + (i*increment);
				double crdAstWghtChng = -1*eqAstWghtChng; //multiply eqAstWghtChng by -1 to ensure that the inverse weight change occurs
				this.actionsSpace.add(MDPPortfolioChoiceAction.createDefaultTwoEquityCreditAsset(eqAstWghtChng, crdAstWghtChng, shrtSell));
			}
		}
	}
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF DEFAULT TWO RISKY ASSETS NO CASH>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<DEFAULT MODEL 2: TWO RISKY ASSETS WITH CASH>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * This method creates the set of portfolio asset states. this does not include the weighting of each asset
	 * just the combination of the assets and the possible states each can be in with the expected returns in each
	 * of those states  
	 * 
	 * Note unlike the default states creation method this method contains a cash/risk-free asset with an assumed constant
	 * rate hence having no direction changes
	 * 
	 * @param decisionAnalysisPeriod
	 */
	public void createDefaultThreeAssetModelPortfolioAssetStates(){
		/**
		 * The constructions in this method are based on the following sample definition of
		 * an adjacency matrix container with  (int crdtAssetState, int eqtyAssetState, double[] transitionProbArray)
		 *  AdjacencyMatrixContainer.createTwoAssetAdjacencyList
		 * (-1,-1, new double[] {0.169666228,0.002648894,0.166423617,0.110102552,0.001718963, 
		 * 0.107998304, 0.221107585, 0.003452016, 0.216881841})
		 */
		int eqtyDir;
		int crdtDir;
		int cshDir = 0;//set to zero as the risk free asset is assumed to have no risk and so has a constant rate of return 
		
		/**
		 * Create the portfolio asset states and add to the <PortfolioAssetsState> StateSet
		 * 
		 * createDefaultTwoAssetChoiceModelMDPState(int equityStateLevel, int creditStateLevel, 
			AssetState equityState, AssetState creditState)
		 */
		for(int i = 0; i<this.JOINED_PROBABILITYMATRIX_LIST.size(); i++){
			//get the equity asset level for the state
			eqtyDir = (int) JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getAssetTwoState();
			//get the credit asset level for the state
			crdtDir = (int) JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getAssetOneState();
			//get the transition probabilities for the state
			double[] transition =  JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getStateTransitionProbabilities(); 
			//add to the transition probabilities to arraylist that will be used to populate the state transitions
			transitions.add(transition);
			//create the equity asset state
			AssetState equityState = new EquityAssetClassState(eqtyDir, this.eqtyPredefinedExpectedReturns);
			//create the credit asset state
			AssetState creditState = new CreditAssetClassState(crdtDir, this.crdtPredefinedExpectedReturns);
			//create the credit asset state
			AssetState cashState = new CashAssetClassState(crdtDir, this.riskFreeCashAssetReturn);
			//create the PortfolioAssetsState object and add it to the <PortfolioAssetsState> StateSet 
			portfolioStates.add(PortfolioAssetsState.createDefaultThreeAssetChoiceModelPortfolioAssettate(
					eqtyDir, crdtDir, cshDir, equityState, creditState, cashState));
		}
		
		/**
		 * next step is to build out the transitions between states for each of the PortfolioAssetsState objects
		 * created above. 
		 * The relevant method in the PortfolioAssetsState class is:
		 * addAdjecentListTransitionsStateProbability(AssetAllocationMDPState adjcentState, double transitionProbability)
		 * 
		 * this construction assumes that the transitions array and the portfolioAssetStates set are of the same ordering. 
		 * So 
		 * 
		 * for each state in the list of PortfolioAssetStates
		 * ----get transition probability array from transitions
		 * ------for each state in list of PortfolioAssetStates and each transition probability in the collected transition probability array
		 * ------add the state and corresponding transition probability to the highlighted PortfolioAssetState
		 * 
		 * 
		 */
		if(portfolioStates.size() == transitions.size()){//ensures that there are equal number of states and transitions
			for(int i = 0; i< portfolioStates.size(); i++){
				double[] transition = transitions.get(i);
				for(int x = 0; x < portfolioStates.size(); x++){
					portfolioStates.get(i).addAdjecentListTransitionsStateProbability(portfolioStates.get(i), portfolioStates.get(x), transition[x]);
				}//end adding of Adjacent State Transitions Probabilities to the current state's Transitions
			}
		}
		else
			if(portfolioStates.size() < transitions.size()){//ensures that there are equal number of states and transitions
				for(int i = 0; i< portfolioStates.size(); i++){
					double[] transition = transitions.get(i);
					for(int l = 0; l< portfolioStates.size(); l++){
						portfolioStates.get(i).addAdjecentListTransitionsStateProbability(portfolioStates.get(i), portfolioStates.get(l), transition[l]);
					}//end adding of Adjacent State Transitions Probabilities to the current state's Transitions
				}
			}
			else
				if(portfolioStates.size() > transitions.size()){//ensures that there are equal number of states and transitions
					int adj = portfolioStates.size() - transitions.size();
					for(int i = 0; i< portfolioStates.size()-adj; i++){
						double[] transition = transitions.get(i);
						for(int l = 0; l< portfolioStates.size(); l++){
							portfolioStates.get(i).addAdjecentListTransitionsStateProbability(portfolioStates.get(i), portfolioStates.get(l), transition[l]);
						}//end adding of Adjacent State Transitions Probabilities to the current state's Transitions
					}
					for(int i = 1; i <= adj; i++){
						portfolioStates.remove(portfolioStates.size()-i);
					}
				}
	}//end of method definition
	
	
	/**
	 * This method is constructed on the observation that once original states are created, then the population of states will never change
	 * i.e. the assumption is that all possible states of the economy or market are know. What changes from time period to time period however is the
	 * transition probability between one state and the next.
	 * 
	 * Consequently, there is only need to create one MDP Decision configuration and thus one set of state-action pairs and states and actions
	 * 
	 * This global construct can then be updated for transition probabilities and returns and distributed to all agents as the simulation progresses
	 * 
	 * 
	 * 
	 * @param STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX_LIST
	 */
	public void updateTransitions(List<AdjacencyMatrixContainer> STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX_LIST){
		/**
		 * back up the original joint transition probability matrix
		 * 
		 * then clear the original transition probability matrix
		 * 
		 * finally add all elements of the new joint transition probability matrix
		 * 
		 */
		JOINED_PROBABILITYMATRIX_HISTORY.add(this.JOINED_PROBABILITYMATRIX_LIST);
		this.JOINED_PROBABILITYMATRIX_LIST.clear();
		this.JOINED_PROBABILITYMATRIX_LIST.addAll(STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX_LIST);
		
		/**
		 * 
		 * Now to update the state transition probabilities.
		 * 
		 * Loop through the new joint probability matrix and 
		 * 
		 * for each element, select the equity asset and credit asset direction/state
		 * 
		 * remember that the cash asset is by default 0 since it is assumed that cash does not fluctuate in any significant way over time and 
		 * is thus the safe harbor
		 * 
		 * also for the selected equity-credit asset state pairing collect the adjacency list of transitions
		 * 
		 * {NOTE:
		 * 
		 * The constructions in this method are based on the following sample definition of
		 * an adjacency matrix container with  (int crdtAssetState, int eqtyAssetState, double[] transitionProbArray)
		 *  AdjacencyMatrixContainer.createTwoAssetAdjacencyList
		 * (-1,-1, new double[] {0.169666228,0.002648894,0.166423617,0.110102552,0.001718963, 
		 * 0.107998304, 0.221107585, 0.003452016, 0.216881841})
		 * 
		 * }
		 * 
		 * Once done, loop through the set of all states to find the states with the selected equity-credit asset state pairing 
		 * 
		 * for every match found, first call the clearTranstions() method of that state to archive the existing probability transitions
		 * and then empty out the collection of state-to-state transition probabilities
		 * 
		 *  Now for every state in the population of all states, create a new state-to-state transition probability  from the selected state
		 *  to all other states
		 * 
		 
		 */
		int eqtyDir;
		int crdtDir;
		int cshDir = 0;//set to zero as the risk free asset is assumed to have no risk and so has a constant rate of return 
		
		for(int i = 0; i<this.JOINED_PROBABILITYMATRIX_LIST.size(); i++){
			//get the equity asset level for the state
			eqtyDir = (int) JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getAssetTwoState();
			//get the credit asset level for the state
			crdtDir = (int) JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getAssetOneState();
			//get the transition probabilities for the state
			double[] transition =  JOINED_PROBABILITYMATRIX_LIST.get(i)
					.getStateTransitionProbabilities(); 
			//
			for(int x = 0; x < this.portfolioStates.size(); x++){
				if(eqtyDir == portfolioStates.get(x).prop[0] && crdtDir == portfolioStates.get(x).prop[1]){
					portfolioStates.get(x).clearTransitions();
					for(int y = 0; y < portfolioStates.size(); y++){
						portfolioStates.get(x).addAdjecentListTransitionsStateProbability(portfolioStates.get(x), portfolioStates.get(y), transition[y]);
					}//end adding of Adjacent State Transitions Probabilities to the current state's Transitions
				}
			}
		}
	}
	
	
	/**
	 * This method updates all the state contingent expected returns for all individual assets
	 * 
	 * This updated date is then broadcast to the agents and other classes that use the information
	 * 
	 * @param assetTraditionalAverageReturns
	 * @param assetCreditAverageReturns
	 */
	public void updateIndividualAssetStateReturns(double[] assetTraditionalAverageReturns, double[] assetCreditAverageReturns){
		/**
		 * First thing is to archive the existing state contingent expected returns
		 * 
		 * Next we overwrite the old returns with the new
		 * 
		 * Since the objective of the update is not to create new but use the existing states but update the data defining them 
		 * 
		 * 
		 * 
		 * Once the house keeping is done, 
		 * 
		 * loop through the set of all portfolio asset states
		 * 
		 * for each portfolio asset state, get its contained asset states
		 * 
		 * if the selected state is an instance of EquityAssetClassState or of CreditAssetClassState then update its state returns
		 * 
		 * 
		 */
		
		setAssetClassesExpectedReturns(assetTraditionalAverageReturns, assetCreditAverageReturns);
		
		
		for(int i = 0; i< portfolioStates.size(); i++){
			
			if(portfolioStates.get(i).equityAssetClassState instanceof EquityAssetClassState){
				portfolioStates.get(i).equityAssetClassState.updateAssetStateReturns(assetTraditionalAverageReturns);
			}
			else
				if(portfolioStates.get(i).creditAssetClassState instanceof CreditAssetClassState){
					portfolioStates.get(i).creditAssetClassState.updateAssetStateReturns(assetCreditAverageReturns);
				}
		}
		
	}

	
	/**
	 * This method definition is used to create the set of asset weights to be used to create the MDP model states
	 * the weights act as the basis upon which permissible actions can be defined they have no bearing on the 
	 * transitions from state to state beyond defining which subset of states will be possible
	 * 
	 * Note that weights are created as an arraylist of <PortfolioWeights> in the PortfolioWeightsFactory class
	 * based on the increments in the weightings set.
	 * 
	 * THis implementation will create a three asset portfolio weighting for equity, credit and cash asset classes
	 * 
	 * @param decisionAnalysisPeriod
	 * @param shortSelling
	 */
	public void createDefaultThreeAssetModelAssetWeights(double assetWieghtIncrements, boolean shortSelling){
		if(shortSelling == false){
			this.weights = PortfolioWeightsFactory.createThreeAssetPortfolioWeightsList(assetWieghtIncrements);
		}
	}
	

	
	/**
	 * This method creates the space of all possible actions in the MDP model.
	 * it is a simple implemetation that defines the actions as the portfolio selections 
	 * therefore an action is a combination of portfolio weights
	 * 
	 * Parameters
	 * @param maxChange
	 * @param increment
	 * 
	 * steps
	 * 1:determine if maxChange is divisible by increment. If not increase maxChange by 1 or 0.1 depending on scale
	 * 1: compute the total number of actions "actionCount"
	 * 2: loop through the total number of actions
	 * 3: at each step of the loop, add the product of the loop phase and the increment to the inverse of the maxChange
	 *    i.e. -1*maxChange + (i * increment) for  i = {0,1,2,3,4...,actionCount-1}. This algorithm doesn't work for all cases
	 *    on for increments of 0.1,0.2,0.5
	 * 
	 */
	public void createWeightChoiceActions(boolean shrtSell, ArrayList<PortfolioWeights> wgts){
		for(int i = 0; i<wgts.size(); i++){
				double eqAstWghtCh = wgts.get(i).getDefaultModelEquityWeight();
				double crdAstWghtCh = wgts.get(i).getDefaultModelCreditWeight();
				double cshAstWghtCh = wgts.get(i).getDefaultModelCashWeight();
				this.actionsSpace.add(MDPPortfolioChoiceAction.createDefaultThreeEquityCreditCashAsset(eqAstWghtCh, crdAstWghtCh,
						cshAstWghtCh, shrtSell));
		}
	}
	
	/**
	 * The following method computes the transaction costs.
	 * The method takes a boolean argument representing whether a linear cost function or a quadratic cost function is used as well as a 
	 * double array consisting of all the weight changes represented by an asset allocation action. 
	 * 
	 * The method check to confirm if the cost function is linear or quadratic
	 * then computes the transaction costs as a sum of transaction costs of each asset
	 * 
	 * Note: The linear cost function factor is included in the quadratic cost function arbitrarily. There is no particular reason for this
	 * however for the most part it will be assumed they are identical
	 * @param lnCost
	 * @param deltaWeight
	 * @return
	 */
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



	/**
	 * This model is called to initialize the infinite horizon MDP
	 * It creates
	 * 1: the probability matrix to be used
	 * 2: the expected returns associated with each state of each of the assets
	 * 3: the combined asset states set and assigns the appropriate transition probabilities
	 * 4: the population of possible portfolio weights
	 * 5: the final MDP model states sets based on the combination of the combined assets states set and the 
	 * 	  population of possible weights
	 * @param prdEndString
	 * @param shtSell
	 * @param astWghtIncr
	 * @return
	 */
	protected void initialize(String prdEndString, boolean shtSell, double astWghtIncr) {
		// TODO Auto-generated method stub
		this.setPeriodEnd(prdEndString);
		this.setPredefinedTransitionProbabilityMatrix(this.decisionAnalysisPeriod);
		this.setDefaultAssetClassesExpectedReturns(this.decisionAnalysisPeriod);
		this.createDefaultModelPortfolioAssetStates(this.decisionAnalysisPeriod);
		this.createDefaultModelAssetWeights(astWghtIncr,shtSell);
		this.createDefaultModelAssetAllocationMDPState(portfolioStates, weights);
		this.createActions(this.maximumPermissbleChangeInWeight, this.changeInWeightIncrement, this.shortSelling);
		this.mdpModelContext.setStatesFullSet(this.assetAllocationMDPStatesSpace);//add the full set of states to the mdpModelContext
		this.mdpModelContext.setActionsFullSet(this.actionsSpace);//add the full set of actions to the mdpModelContext
		this.mdpModelContext.defaultInitialState();

	}
	

	
	/**
	 * This model is called to initialize the infinite horizon MDP
	 * It creates
	 * 1: the probability matrix to be used
	 * 2: the expected returns associated with each state of each of the assets
	 * 3: the combined asset states set and assigns the appropriate transition probabilities
	 * 4: the population of possible portfolio weights
	 * 5: the final MDP model states sets based on the combination of the combined assets states set and the 
	 * 	  population of possible weights
	 * @param prdEndString
	 * @param shtSell
	 * @param astWghtIncr
	 * @return
	 */
	protected void initializeDefaultModel(TransitionProbabilitiesEngine tpEngine, boolean stochastic, String prdEndString, 
			boolean shtSell, int numberOfEpisodes, double astWghtIncr) {
		// TODO Auto-generated method stub
		System.out.println("New MDPDecisionModelConfiguration initializeDefaultModel.... ");
		this.setPeriodEnd(prdEndString);
		if(stochastic == false) {
			this.setPredefinedTransitionProbabilityMatrix(this.decisionAnalysisPeriod);
			this.setDefaultAssetClassesExpectedReturns(this.decisionAnalysisPeriod);
		} else{
			this.setJointTransitionProbabilityMatrix(tpEngine.getSTOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX());
			System.out.println(tpEngine.getSTOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX().toString());
			this.setAssetClassesExpectedReturns(tpEngine.getAssetTraditionalAverageReturns(), tpEngine.getAssetCreditAverageReturns());
		}
		
		this.createDefaultThreeAssetModelPortfolioAssetStates();
		this.createDefaultThreeAssetModelAssetWeights(astWghtIncr,shtSell);
		this.createDefaultModelAssetAllocationMDPState(portfolioStates, weights);
		this.createWeightChoiceActions(this.shortSelling, this.weights);
		this.statesGenerator = new MDPStatesPathEngine(portfolioStates, numberOfEpisodes);
		this.mdpModelContext.setCapitalMarketStateGenerator(statesGenerator);
		this.mdpModelContext.setStatesFullSet(this.assetAllocationMDPStatesSpace);//add the full set of states to the mdpModelContext
		this.mdpModelContext.setActionsFullSet(this.actionsSpace);//add the full set of actions to the mdpModelContext
		this.mdpModelContext.defaultInitialState();
		this.mdpModelContext.setInitialPortfolioWieghtPolicy();

	}
	
	/**
	 * Set the codifying value representing the training data to be used
	 * This will need to be amended as more data sets are introduced
	 * 
	 * @param prdEndString
	 */
	private void setPeriodEnd(String prdEndString){
		if(prdEndString == "2003"){
			this.decisionAnalysisPeriod = 0;
		} else
			if(prdEndString == "2007"){
				this.decisionAnalysisPeriod = 1;
			} else
			if(prdEndString == "Full"){
				this.decisionAnalysisPeriod = 2;
			}
	}



}
