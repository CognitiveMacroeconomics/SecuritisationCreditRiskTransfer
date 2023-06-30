

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;


public class Enviroment {
	
	/**
	 * This class defines the environmental conditions under which the agents in the model operate
	 * This class does not define economic parameters or variables. Rather is defines the rules
	 * of the economic decision making game the agents play
	 * 
	 */

	Vector<ModelParameter> parameters = new Vector<ModelParameter>();

	Hashtable<Borrower, Vector<Loan>> loans = new Hashtable<Borrower, Vector<Loan>>();

	String name;

	Calendar date;
	
	boolean constantQ;
	boolean boolSecuritize;// (Environment), 
	boolean boolNonlinear;// (Environment), 
	double regCapitalRatio;// (Environment),
	boolean boolBankOnlyAnalysis;// boolean that determines if analysis is concerned with banks alone.
	//the implications  of setting this to "true" is that banks will continue to post assets and liabilities 
	//even after the become bankrupt. Thus market clearing will show negative returns and so should be ignored
	
	//Model Creation variables

	public int databaseStartYr;

	public int databaseStartQtr; 

	public int simulationStartYr; 

	public int simulationStartQtr;
	
	public int simulationYearsMultiplier; //the number to multiply the simulation number of years by to get
	//the number of time periods over which to run the simulation. This is dependent on the value of PaymentSchedule
	//applied to the borrowers/loans


	public int dataCollectionCounter;

	public int dataCallenderStartMonth;

	public ArrayList<Calendar> loanIssuanceCalenders;

	public Calendar start;

	public Calendar startUpdated;

	private boolean shortSelling;
	
	private boolean lapfConstantContractualObligations;

	private boolean internalSecuritise;

	private boolean lapfMultiPeriodSolvancyModel;

	private int decisionHorizon;

	private boolean multiPeriodAnalysis;

	private int securitisationRateDecisionHorizon;

	private String lapfTypeString;

	private String fundsExpectationsString;

	private int fundType;

	private int fundExpectations;

	private String decisionAnalysisPeriodEndString;

	private double accuracyThreshold;

	private double gammaDiscountFactor;

	private double epsilonError;

	private double maximumPermissbleChangeInWeight;

	private double changeInWeightIncrement;

	private double assetWieghtIncrements;

	private int numberOfEpisodes;

	private boolean linearCostFunction;

	private boolean stochasticStateTransitions;

	private boolean portfolioWeightChoiceModel;

	private int investorHorizon;
	
	private int investorCount;

	private boolean heuristicSecuritisationModel;

	private boolean erevRothModel;
	
	


	
	public Enviroment(String name) {
		super();
		this.name = name;
	}


	public Enviroment(String name, Calendar date) {
		super();
		this.name = name;
		this.date = date;
	}
	
	
	

	public void setBoolInternalSecuritise(boolean internalSecuritise2) {
		// TODO Auto-generated method stub
		this.internalSecuritise = internalSecuritise2;
	}

	public boolean getInternalSecuritise(){
		return this.internalSecuritise;
	}

	
	public void setBoolSecuritize(boolean bS){
		this.boolSecuritize = bS;
	}
	
	
	public boolean getBoolSecuritize(){
		return this.boolSecuritize;
	}
	
	
	public void setConstantQ(boolean cQ){
		this.constantQ = cQ;
	}
	
	
	public boolean getConstantQ(){
		return this.constantQ;
	}
	
	public void setBoolNonlinear(boolean NL){
		this.boolNonlinear = NL;
	}
	
	public boolean getBoolNonlinear(){
		return this.boolNonlinear;
	}
	
	public void setBoolBankOnlyAnalysis(boolean banksOnly){
		this.boolBankOnlyAnalysis = banksOnly;
	}
	
	public boolean getBoolBankOnlyAnalysis(){
		return this.boolBankOnlyAnalysis;
	}

	
	public void setBoolShortselling(boolean shortSelling) {
		// TODO Auto-generated method stub
		this.shortSelling = shortSelling;
	}
	
	public boolean getBoolShortselling(){
		return this.shortSelling;
	}
	

	
	
	
	public int getDataCallenderStartMonth() {
		return dataCallenderStartMonth;
	}

	public ArrayList<Calendar> getLoanIssuanceCalenders() {
		return loanIssuanceCalenders;
	}


	public Calendar getLoanIssuanceStart() {
		return start;
	}

	public Calendar getStartUpdated() {
		return startUpdated;
	}

	
	
	public void validateSimulationStartYear() {

		if(this.simulationStartYr < this.databaseStartYr){
			System.out.print("Data input Error: Simulation Start Year can not be before the databse start year");
			System.out.print("\n");
			System.out.print("Default values set for simulation Start Year");
			System.out.print("\n");
			this.setSimulationStartYr(this.databaseStartYr);
			System.out.print("\n");
		}

	}

	public void validateSimulationStartQuarter(){

		if(this.simulationStartQtr < this.databaseStartQtr & this.simulationStartYr <= this.databaseStartYr){
			System.out.print("Data input Error: Simulation Start Quarter is invalid given the simulation start year and the databse start period");
			System.out.print("\n");
			System.out.print("Default values set for simulation Start Quarter");
			System.out.print("\n");
			this.setSimulationStartYr(this.databaseStartQtr);
			System.out.print("\n");
		}

	}
	
	public void setLAPFConstantContractualObligations(
			boolean lapfConstantContractualObligations) {
		// TODO Auto-generated method stub
		this.lapfConstantContractualObligations = lapfConstantContractualObligations;
	}


	
	public boolean getLAPFConstantContractualObligations() {
		// TODO Auto-generated method stub
		return this.lapfConstantContractualObligations;
	}

	public void setLAPFMultiPeriodSolvancyModel(
			boolean lapfMultiPeriodSolvancyModel) {
		// TODO Auto-generated method stub
		this.lapfMultiPeriodSolvancyModel = lapfMultiPeriodSolvancyModel;
	}


	
	public boolean getLAPFMultiPeriodSolvancyModel() {
		// TODO Auto-generated method stub
		return this.lapfMultiPeriodSolvancyModel;
	}

	private void setLoanIssuanceStartCalender(int simulationStartYr2,
			int dataCallenderStartMonth2, int i) {
		// TODO Auto-generated method stub
		start = new GregorianCalendar(simulationStartYr2, dataCallenderStartMonth2, i);
	}

	public void setDataCallenderStartMonth() {
		this.dataCallenderStartMonth = this.dataCallenderStartMonth = 
				UtilityMethods.DefineLoanIssuanceCalenderMonth(this.simulationStartQtr, DataSchedule.quarterly);

	}

	public void setDataCollectionCounter() {
		this.dataCollectionCounter = UtilityMethods.DefineDataCollectionStartCounter(
				this.databaseStartYr, this.databaseStartQtr, this.simulationStartYr, this.simulationStartQtr);
	}

	public int getDatabaseStartYr() {
		return databaseStartYr;
	}


	public void setDatabaseStartYr(int databaseStartYr) {
		this.databaseStartYr = databaseStartYr;
	}


	public int getDatabaseStartQtr() {
		return databaseStartQtr;
	}


	public void setDatabaseStartQtr(int databaseStartQtr) {
		this.databaseStartQtr = databaseStartQtr;
	}


	public int getSimulationStartYr() {
		return simulationStartYr;
	}


	public void setSimulationStartYr(int simulationStartYr) {
		this.simulationStartYr = simulationStartYr;
	}


	public int getSimulationStartQtr() {
		return simulationStartQtr;
	}


	public void setSimulationStartQtr(int simulationStartQtr) {
		this.simulationStartQtr = simulationStartQtr;
	}
	
	public int getSimulationYearsMultiplier() {
		return simulationYearsMultiplier;
	}


	public void setSimulationYearsMultiplier(int simulationYearsMultiplier) {
		this.simulationYearsMultiplier = simulationYearsMultiplier;
	}
	
	
	public void setSimulationYearsMultiplier(PaymentSchedule paymentSchedule2) {
		// TODO Auto-generated method stub
		if(paymentSchedule2 == PaymentSchedule.annually){
			this.setSimulationYearsMultiplier(1);
		}
		else if(paymentSchedule2 == PaymentSchedule.monthly){
			this.setSimulationYearsMultiplier(12);
		}
		else if(paymentSchedule2 == PaymentSchedule.quarterly){
			this.setSimulationYearsMultiplier(4);
		}
		else if(paymentSchedule2 == PaymentSchedule.semi_annually){
			this.setSimulationYearsMultiplier(2);
		}
		else if(paymentSchedule2 == PaymentSchedule.weekly){
			this.setSimulationYearsMultiplier(52);
		}
	}
	
	public void setSimulationDecisionHorizon(int dh){
		this.decisionHorizon = dh;
	}

	
	public int getSimulationDecisionHorizon(){
		return this.decisionHorizon;
	}

	
	public void setMultiPeriodAnalysis(boolean multiPeriodAnalysis) {
		// TODO Auto-generated method stub
		this.multiPeriodAnalysis = multiPeriodAnalysis;
//		System.out.println(this.multiPeriodAnalysis);
	}
	
	
	public void setSecuritisationRateDecisionHorizon(
			int mpb) {
		this.securitisationRateDecisionHorizon = mpb;
//		System.out.println(this.securitisationRateDecisionHorizon);
	}


	public int getSecuritisationRateDecisionHorizon() {
//		System.out.println(this.securitisationRateDecisionHorizon);
		return this.securitisationRateDecisionHorizon;
	}


	
	/**
	 * @return the multiPeriodAnalysis
	 */
	public boolean getMultiPeriodAnalysis() {
		return this.multiPeriodAnalysis;
	}



	/**
	 * @param lapfTypeString the lapfTypeString to set
	*/
	public void setLAPFTypeString(String lapfTypeString) {
		//determines if the LAPFs are learning using an MDP or just using the base model
		//possible values 
		//"Simple Fund", 
		//"MDP Learning"
		this.lapfTypeString = lapfTypeString;
	}

	public String getLAPFTypeString() {
		return this.lapfTypeString;
	}
	
	/**
	 * @param lapfExpectationsString the fundsExpectationsString to set
	*/
	public void setFundsExpectationsString(String lapfExpectationsString) {
		//determines if the LAPFs are learning using an MDP or just using the base model
		//possible values 
		//"Bullish", 
		//"Bearish",
		//"Heterogeneous"
		this.fundsExpectationsString = lapfExpectationsString;
	}

	public String getFundsExpectationsString() {
		return this.fundsExpectationsString;
	}
	


	public void setLAPFTypeIndex(int value) {
		this.fundType = value;
	}

	
	public void setLAPFExpectationsIndex(int value) {
		// determines if the LAPFs are learning using an MDP or just using the
		// base model
		// possible values
		// "Bullish",
		// "Bearish",
		// "Heterogeneous"
		this.fundExpectations = value;
	}

	
	public int getLAPFTypeIndex() {
		return fundType;
	}

	
	public int getLAPFExpectationsIndex() {
		// determines if the LAPFs are learning using an MDP or just using the
		// base model
		// possible values
		// "Bullish",
		// "Bearish",
		// "Heterogeneous"
		return fundExpectations;
	}
	
	
	
	public void setDecisionAnalysisPeriodEndString(String value){
		this.decisionAnalysisPeriodEndString = value;
	}
	
	
	public void setBoolPortfolioWeightChoiceModel(boolean value){
		this.portfolioWeightChoiceModel = value;
	} 
	
	
	public void setBooleanStochasticStateTransitions(boolean value){
		this.stochasticStateTransitions = value;
	} 
	
	
	public void setBooleanLinearCostFunction(boolean value){
		this.linearCostFunction = value;
	}
	
	
	
	public void setNumberOfEpisodes(int value){
		this.numberOfEpisodes = value;
	}
	
	
	public void setAssetWieghtIncrements(double value){
		this.assetWieghtIncrements = value;
	}
	
	
	public void setChangeInWeightIncrement(double value){
		this.changeInWeightIncrement = value;
	}
	
	
	public void setMaximumPermissbleChangeInWeight(double value){
		this.maximumPermissbleChangeInWeight = value;
	}
	
	
	public void setEpsilonError(double value){
		this.epsilonError = value;
	}
	
	
	public void setGammaDiscountFactor(double value){
		this.gammaDiscountFactor = value;
	}
	
	
	public void setAccuracyThreshold(double value){
		this.accuracyThreshold = value;
	}



	
	
	public String getDecisionAnalysisPeriodEndString(){
		return this.decisionAnalysisPeriodEndString;
	}
	
	
	public boolean getBoolPortfolioWeightChoiceModel(){
		return this.portfolioWeightChoiceModel;
	} 
	
	
	public boolean getBooleanStochasticStateTransitions(){
		return this.stochasticStateTransitions;
	} 
	
	
	public boolean getBooleanLinearCostFunction(){
		return this.linearCostFunction;
	}
	
	
	
	public int getNumberOfEpisodes(){
		return this.numberOfEpisodes;
	}
	
	
	public double getAssetWieghtIncrements(){
		return this.assetWieghtIncrements;
	}
	
	
	public double getChangeInWeightIncrement(){
		return this.changeInWeightIncrement;
	}
	
	
	public double getMaximumPermissbleChangeInWeight(){
		return this.maximumPermissbleChangeInWeight;
	}
	
	
	public double getEpsilonError(){
		return this.epsilonError;
	}
	
	
	public double getGammaDiscountFactor(){
		return this.gammaDiscountFactor;
	}
	
	
	public double getAccuracyThreshold(){
		return this.accuracyThreshold;
	}

	
	
	public void setInvestorHorizon(int numberOfIterations) {
		// TODO Auto-generated method stub
		this.investorHorizon = numberOfIterations;
	}


	public int getInvestorHorizon() {
		// TODO Auto-generated method stub
		return investorHorizon;
	}



	public void setBankHeuristicLearning(boolean heuristicSecuritisationModel) {
		// TODO Auto-generated method stub
		this.setHeuristicSecuritisationModel(heuristicSecuritisationModel);
	}


	public boolean isHeuristicSecuritisationModel() {
		return heuristicSecuritisationModel;
	}


	private void setHeuristicSecuritisationModel(boolean heuristicSecuritisationModel) {
		this.heuristicSecuritisationModel = heuristicSecuritisationModel;
	}

	
	public void setBankErevRothLearning(boolean erevRothModel) {
		// TODO Auto-generated method stub
		this.setErevRothModel(erevRothModel);
	}



	public boolean isErevRothModel() {
		return erevRothModel;
	}


	private void setErevRothModel(boolean erevRothModel) {
		this.erevRothModel = erevRothModel;
	}

	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<OLD METHODS WHICH MAKE NO SENSE NOW>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	


	/**
	 * @return the investorCount
	 */
	public int getInvestorCount() {
		return investorCount;
	}


	/**
	 * @param investorCount the investorCount to set
	 */
	public void setInvestorCount(int investorCount) {
		this.investorCount = investorCount;
	}


	public Vector<ModelParameter> getParameters() {
		return parameters;
	}

	public void setParameters(Vector<ModelParameter> parameters) {
		this.parameters = parameters;
	}

	public Hashtable<Borrower, Vector<Loan>> getLoans() {
		return loans;
	}

	public void setLoans(Hashtable<Borrower, Vector<Loan>> loans) {
		this.loans = loans;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
	

	@Override
	public String toString() {
		return "Enviroment [parameters=" + parameters + ", loans=" + loans
				+ ", name=" + name + ", date=" + date + "]";
	}

}
