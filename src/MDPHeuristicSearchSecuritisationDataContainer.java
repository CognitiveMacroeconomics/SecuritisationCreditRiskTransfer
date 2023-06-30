
public class MDPHeuristicSearchSecuritisationDataContainer {
	public static int DATA_CONTAINER_PRIMARY_KEY = 0;
	public int dataContainerKey;
	double[] capitalUtilityAccumulation;
	double[] searchHeuristicKValues;
	double[] derivedKValueDrivenoptimalSecuritisationRates;
	
	public MDPHeuristicSearchSecuritisationDataContainer(double[] heuristicKValue, double[] kValueUtiliy, 
			double[] kValueActions){
		DATA_CONTAINER_PRIMARY_KEY++;
		dataContainerKey = DATA_CONTAINER_PRIMARY_KEY;
		setSearchHeuristicKValues(heuristicKValue);
		setCapitalUtilityAccumulation(kValueUtiliy);
		setDerivedKValueDrivenoptimalSecuritisationRates(kValueActions);
	}

	
	
	
	
	public double[] getCapitalUtilityAccumulation() {
		return capitalUtilityAccumulation;
	}

	public void setCapitalUtilityAccumulation(double[] capitalAccumulation) {
		this.capitalUtilityAccumulation = capitalAccumulation;
	}

	public double[] getSearchHeuristicKValues() {
		return searchHeuristicKValues;
	}

	public void setSearchHeuristicKValues(double[] timeEvolvingKValues) {
		this.searchHeuristicKValues = timeEvolvingKValues;
	}

	public double[] getDerivedKValueDrivenOptimalSecuritisationRates() {
		return derivedKValueDrivenoptimalSecuritisationRates;
	}

	public void setDerivedKValueDrivenoptimalSecuritisationRates(
			double[] derivedKValueDrivenoptimalSecuritisationRates) {
		this.derivedKValueDrivenoptimalSecuritisationRates = derivedKValueDrivenoptimalSecuritisationRates;
	}

}
