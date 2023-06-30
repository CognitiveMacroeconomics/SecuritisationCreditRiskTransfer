
public class MDPPolicy {

	
	State stateFrom;
	State stateTo;
	MDPPortfolioChoiceAction policyAction;
		
	public MDPPolicy(State stState, MDPPortfolioChoiceAction action){
			
			this.setStateFrom(stState);
			this.setPolicyAction(action);
			
		}

		
		
		
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GEtters and Setters>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
		
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
