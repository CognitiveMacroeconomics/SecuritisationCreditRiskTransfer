	
	/**
	 * The objective of this class is simply to act as a store/collector of value function outputs V(s)
	 * It will store the:
	 * 1: iteration count
	 * 2: the utility value V(s)
	 * 3: state from
	 * 4: action taken
	 * 5: state to
	 * @author Oluwasegun Bewaji
	 *
	 */

public class QValue {

	double utilityValue_v_s;
	State stateFrom;
	State stateTo;
	MDPPortfolioChoiceAction policyAction;
		
	public QValue(State stState, MDPPortfolioChoiceAction action, double v_s){
			
			this.setUtilityValue_v_s(v_s);
			this.setStateFrom(stState);
			this.setPolicyAction(action);
			
		}

		
		
		
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GEtters and Setters>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
		int iteration;
		/**
		 * @return the iteration
		 */
		public int getIteration() {
			return iteration;
		}

		/**
		 * @param iteration the iteration to set
		 */
		public void setIteration(int iteration) {
			this.iteration = iteration;
		}

		/**
		 * @return the utilityValue_v_s
		 */
		public double getUtilityValue_v_s() {
			return utilityValue_v_s;
		}

		/**
		 * @param utilityValue_v_s the utilityValue_v_s to set
		 */
		public void setUtilityValue_v_s(double utilityValue_v_s) {
			this.utilityValue_v_s = utilityValue_v_s;
		}

		/**
		 * @return the stateFrom
		 */
		public State getStateFrom() {
			return stateFrom;
		}

		/**
		 * @param stateFrom the stateFrom to set
		 */
		public void setStateFrom(State stateFrom) {
			this.stateFrom = stateFrom;
		}

		/**
		 * @return the stateTo
		 */
		public State getStateTo() {
			return stateTo;
		}

		/**
		 * @param stateTo the stateTo to set
		 */
		public void setStateTo(State stateTo) {
			this.stateTo = stateTo;
		}

		/**
		 * @return the policyAction
		 */
		public MDPPortfolioChoiceAction getPolicyAction() {
			return policyAction;
		}

		/**
		 * @param policyAction the policyAction to set
		 */
		public void setPolicyAction(MDPPortfolioChoiceAction policyAction) {
			this.policyAction = policyAction;
		}


}
