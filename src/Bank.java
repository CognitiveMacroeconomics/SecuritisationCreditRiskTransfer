

import jas.engine.Sim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;

import org.jfree.data.xy.XYSeries;

/*import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
*/


public abstract class Bank extends EconomicAgent{

	protected static int bankIDs = 0;
	private final int bankID;
	private final int RSSID;
	private int failedBank;
	private int securitisingBank;
	private double interestSpread;
	private final String bankName;

	protected double averageRefAssetValue;
	protected double loanableFunds;

	protected double returnOnAssets;
	protected double returnOnLiabilities;
	protected int maturity;
	protected double securitisationRate;
	

	protected double totalAssets;
	protected double totalLiabilities;
	protected double equityCaliptal;
	protected double tier_1_Capital;
	protected double onBalanceSheetDefaultRate;
	protected double offBalanceSheetDefaultRate;
	protected double securitisationCostFactor;
	protected double securitisationCost;
	protected double cashAssets_LoanableFunds;
	protected double regulatoryCapitalRatio;
	protected double onBalanceSheetAssetSuvivalRate;
	protected double offBalanceSheetAssetSuvivalRate;
	protected double assetSurvivalRate;
	protected double sigma1;
	protected double sigma2;
	protected double capitalRetentionOrinjection; //capital injections
	protected int periodCountIn;
	protected double securitisationIssuanceAmount;
	protected double securitisationIssuanceTotalNotional;
	protected double securitisatonCreditEnhancements =0;
	
	protected double focusBankOptimalSecRateArray[];
	protected double focusBankOptimalSecCapitalAccumulationArray[];
	
	protected double ltvRatio;
	
	protected double ltvRatioRangeLower;
	protected double ltvRatioRangeUpper;
	
	protected SPV bankSPVConduit;
	
	protected PaymentSchedule MBSPaymentStructure;
	
	protected double mdRateOfReturn;
	

	protected double totalMBSIssuance;
	
	protected CorporateStatus status;

	private final Vector<Borrower> borrowers = new Vector<Borrower>();
	private final Vector<Loan> loansIssued = new Vector<Loan>();
	protected Vector<double[]> bankruptcyRangeForAssetLiabilityRatioCollection = new Vector<double[]>();
	private double returnOnEquity;
	
	protected Economy economy; 
	
	protected Enviroment environment;
	
	protected SecuritisationEngine securitisationEngine;
	
	private GeoEconoPoliticalSpace world;
//	private ContinuousSpace <Object> space;
//	private Grid<Object> grid;
	
	protected XYSeries optimalSecuritisation;
	protected ArrayList focusBankDecisionOptimalSecuritisation = new ArrayList();
	protected ArrayList seniorTrancheSecuritisation = new ArrayList();
	protected ArrayList mezzTrancheSecuritisation = new ArrayList();
	protected ArrayList juniorTrancheSecuritisation = new ArrayList();
	protected double[] focusBankOptimalSecCapitalAccumulationArraySnrTranche;
	protected double[] focusBankOptimalSecRateArraySnrTranche;
	protected double securitisationRateSnrTranche;
	protected double[] focusBankOptimalSecCapitalAccumulationArrayMezTranche;
	protected double[] focusBankOptimalSecRateArrayMezTranche;
	protected double securitisationRateMezTranche;
	protected double[] focusBankOptimalSecCapitalAccumulationArrayJnrTranche;
	protected double[] focusBankOptimalSecRateArrayJnrTranche;
	protected double securitisationRateJnrTranche;
	protected boolean learningBank;
	protected MDPSecuritisationRateIntermediateHueristic optimalSecuritisationIntermediateHueristic;
	protected ErevRothSecuritisationProblem erevRothSecuritisationModel;



	//This is the main Constructor to be used for simple Markose & DYang model
	@SuppressWarnings("static-access")
	public Bank(int IDRSSD, String bankName, double totalAssets,
			double totalLiabilities, Economy econ,
			Enviroment env, GeoEconoPoliticalSpace world) {
		super();
		bankIDs++;
		this.failedBank = 0;
		this.bankID = bankIDs;
		this.RSSID = IDRSSD;
		this.bankName = bankName;
		this.setEconomy(econ);
		this.setEnvoronment(env);
		this.world = world;
		this.setTotalAssets(totalAssets);
		this.setTotalLiabilities(totalLiabilities);
		this.setReturnOnAssets(Economy.assetReturn);
		this.setReturnOnLiabilities(Economy.liabExpense);
		this.setInterestSpread(this.returnOnAssets - this.returnOnLiabilities);
		this.setLoanableFunds(this.assetSurvivalRate*this.totalAssets*(this.returnOnAssets - this.returnOnLiabilities));
		this.periodCountIn = 0;
		ltvRatio = Economy.mortgageLTVRatioMin;
		createSPVConduit();
		albedo = 20;
		securitisationEngine = new SecuritisationEngine();
		this.learningBank = this.environment.isHeuristicSecuritisationModel();
		this.heuristicDecisionModelParameters = this.economy.getHeuristicDecisionModelParameters();
		if(this.learningBank){
			this.optimalSecuritisationIntermediateHueristic = 
					new MDPSecuritisationRateIntermediateHueristic(this.heuristicDecisionModelParameters);//create heuristics engine
			this.optimalSecuritisationIntermediateHueristic.setContractRate(returnOnAssets);//sets return on assets and creates loan states
			this.optimalSecuritisationIntermediateHueristic.setDepositRate(returnOnLiabilities);//sets return on liabilities
			this.optimalSecuritisationIntermediateHueristic.setHuristicDemandRange(0.45);//sets the initial market demand implied coupon
		} else{
			this.learningBank = false;
		}
		
		
		
	}
	
	
	//This is the main Constructor to be used for complicated/detailed model
	@SuppressWarnings("static-access")
	public Bank(int IDRSSD, String bankName, double totalAssets,
			double totalLiabilities, Economy econ,
			Enviroment env, GeoEconoPoliticalSpace world, double returnOnAssets,
			double returnOnLiabilities) {
		super();
		bankIDs++;
		this.failedBank = 0;
		this.bankID = bankIDs;
		this.RSSID = IDRSSD;
		this.bankName = bankName;
		this.setEconomy(econ);
		this.setEnvoronment(env);
		this.world = world;
		this.setTotalAssets(totalAssets);
		this.setTotalLiabilities(totalLiabilities);
		this.setReturnOnAssets(returnOnAssets);
		this.setReturnOnLiabilities(returnOnLiabilities);
		this.setInterestSpread(this.returnOnAssets - this.returnOnLiabilities);
		this.setRegulatoryCapitalRatio(Economy.regCapitalRatio);
		this.setAssetSurvivalRate(Economy.survivalRate);
		this.setLoanableFunds(this.assetSurvivalRate*this.totalAssets*(this.returnOnAssets - this.returnOnLiabilities));
		this.periodCountIn = 0;
		ltvRatio = 0.7;
		createSPVConduit();
		albedo = 20;
		securitisationEngine = new SecuritisationEngine();
		this.learningBank = this.environment.isHeuristicSecuritisationModel();
		this.heuristicDecisionModelParameters = this.economy.getHeuristicDecisionModelParameters();
		if(this.learningBank){
			this.optimalSecuritisationIntermediateHueristic = 
					new MDPSecuritisationRateIntermediateHueristic(this.heuristicDecisionModelParameters);//create heuristics engine
			this.optimalSecuritisationIntermediateHueristic.setContractRate(returnOnAssets);//sets return on assets and creates loan states
			this.optimalSecuritisationIntermediateHueristic.setDepositRate(returnOnLiabilities);//sets return on liabilities
			this.optimalSecuritisationIntermediateHueristic.setHuristicDemandRange(0.45);//sets the initial market demand implied coupon
		} else{
			this.learningBank = false;
		}
		
		
		
	}
	
	@SuppressWarnings("static-access")
	public Bank() {
		super();
		bankIDs++;
		this.bankID = bankIDs;
		this.RSSID = 480228;
		this.bankName = "BANK OF AMERICA, NATIONAL ASSOCIATION";
		this.averageRefAssetValue = 250000;
		this.interestSpread = 0.04;
		setMaturity(20);
		loanableFunds = 250000000;
		ltvRatio = 0.7;
		albedo = 20;
		
	}
	
	
	
	/**
	 * 
	 * This constructor is for a more complex banking model that uses more data
	 * @param IDRSSD
	 * @param bankName
	 * @param totalAssets
	 * @param totalLiabilities
	 * @param returnOnAssets
	 * @param returnOnLiabilities
	 * @param averageRefAssetValue
	 * @param maturity
	 * @param onBalanceSheetDefaultRate
	 * @param offBalanceSheetDefaultRate
	 * @param securitisationCostFactor
	 */

	public Bank(int IDRSSD, String bankName, double totalAssets,
			double totalLiabilities, double returnOnAssets,
			double returnOnLiabilities, double averageRefAssetValue,
			int maturity, double onBalanceSheetDefaultRate,
			double offBalanceSheetDefaultRate, double securitisationCostFactor) {
		super();
		bankIDs++;
		this.bankID = bankIDs;
		this.RSSID = IDRSSD;
		this.bankName = bankName;
		this.setAverageRefAssetValue(averageRefAssetValue);
		this.setOffBalanceSheetDefaultRate(offBalanceSheetDefaultRate);
		this.setOnBalanceSheetDefaultRate(onBalanceSheetDefaultRate);
		this.setSecuritisationCostFactor(securitisationCostFactor);
		this.setTotalAssets(totalAssets);
		this.setTotalLiabilities(totalLiabilities);
		this.setReturnOnAssets(returnOnAssets);
		this.setReturnOnLiabilities(returnOnLiabilities);
		this.setInterestSpread(this.returnOnAssets - this.returnOnLiabilities);
		setMaturity(20);
		this.setLoanableFunds(250000000);
		ltvRatio = 0.7;
		createSPVConduit();
		albedo = 20;
		securitisationEngine = new SecuritisationEngine();

	}
	
	
	
	public Bank(int IDRSSD, String bankName, double totalAssets,
			double totalLiabilities, double returnOnAssets,
			double returnOnLiabilities,double assetSurvivalRate, double securitisationCostFactor) {
		super();
		bankIDs++;
		this.bankID = bankIDs;
		this.RSSID = IDRSSD;
		this.bankName = bankName;
		this.setAssetSurvivalRate(assetSurvivalRate);
		this.setSecuritisationCostFactor(securitisationCostFactor);
		this.setTotalAssets(totalAssets);
		this.setTotalLiabilities(totalLiabilities);
		this.setReturnOnAssets(returnOnAssets);
		this.setReturnOnLiabilities(returnOnLiabilities);
		this.setInterestSpread(this.returnOnAssets - this.returnOnLiabilities);
		this.setLoanableFunds(this.assetSurvivalRate*this.totalAssets*(this.returnOnAssets - this.returnOnLiabilities));
		this.periodCountIn = 0;
		ltvRatio = 0.7;
		createSPVConduit();
		albedo = 20;
		securitisationEngine = new SecuritisationEngine();
		
		
	}
	



	private void setEnvoronment(Enviroment env) {
		// TODO Auto-generated method stub
		this.environment = env; 
	
	}

	private void setEconomy(Economy econ) {
		// TODO Auto-generated method stub
		this.economy = econ;
	}



	private void createSPVConduit() {
		// TODO Auto-generated method stub
		bankSPVConduit = new SPV(this.getBankName(), this.getBankID(), this.getRSSID());
	}
	
	public SPV getBankSPVConduit(){
		return bankSPVConduit;
	}


	
	
	public ErevRothSecuritisationProblem getErevRothSecuritisationModel() {
		return erevRothSecuritisationModel;
	}


	public void setErevRothSecuritisationModel(
			ErevRothSecuritisationProblem erevRothSecuritisationModel) {
		this.erevRothSecuritisationModel = erevRothSecuritisationModel;
	}


	@Override
	public String toString() {
		return "Bank [ RSSID=" + RSSID + ", bankName="
				+ bankName + ", Total Assets =" + this.totalAssets
				 + ", Total Liabilities =" + this.totalLiabilities
				 + ", Data kick in =" + this.periodCountIn
				+ " ]";
	}

	public abstract void securitise();
	
	public abstract void simAssetProcess();

	public abstract void securitise(double sigma1, double sigma2);

	public abstract void testBankruptcy();

	public void issueLoans(BorrowerType bType, double assetValueMin,
			double assetValueMax, double avgIncomeMin, double avgIncomeMax,
			Calendar start, int resetWindow, double interestRateCurrent,
			RateType rateType, PaymentSchedule paymentSchedule) {

		/*
		 * To issue loans, 1: determine the loan amount: this will be a random
		 * amount within the maximum and minimum average asset value range.
		 * Since this range is passed on to ObjectFactory public static Borrower
		 * createNewBorrower(BorrowerType type, double assetValueMin, double
		 * assetValueMax, double avgIncomeMin, double avgIncomeMax)
		 * 
		 * public Loan(int issuerID, int borrowerID, int maturity, String
		 * issuerName, double loanAmount, double contractRate, RateType
		 * rateType, AssetType assetType, Asset asset, Calendar startDate,
		 * Calendar reset, PaymentSchedule paymentSchedule)
		 * 
		 * public Calendar start = new GregorianCalendar(2001, 0, 1); public
		 * Calendar reset = new GregorianCalendar(start.get(Calendar.YEAR) + 2,
		 * start.get(Calendar.MONTH), start.get(Calendar.DATE));
		 */

		double loanFunds = this.getCashAssets_LoanableFunds();//*1000;//multiplying by 1000 because bank data is in thousands
		double lnAmntSum = 0;
		double assetValue = 0;
		double lnAmnt = 0;
		double contractRate = this.interestSpread + interestRateCurrent;
		Calendar reset = new GregorianCalendar(start.get(Calendar.YEAR)
				+ resetWindow, start.get(Calendar.MONTH),
				start.get(Calendar.DATE));
		setMBSPayMentStructure(paymentSchedule);

		while (lnAmntSum < loanFunds) {
			assetValue = assetValueMin
					+ (Math.random() * (assetValueMax - assetValueMin));// create
																		// a
																		// asset
																		// value
																		// based
																		// on
																		// average
																		// market
																		// data
			lnAmnt = assetValue * this.ltvRatio;
			if (lnAmnt < (loanFunds - lnAmntSum)) {
				Borrower tmpBorrower = ObjectFactory.createNewBorrower(bType,
						assetValue, avgIncomeMin, avgIncomeMax);

				Loan ln = ObjectFactory.createNewLoan(this.bankID,
						this.getRSSID(), tmpBorrower.getBorrowerID(),
						this.maturity, this.getBankName(), lnAmnt,
						contractRate, rateType, tmpBorrower.getTypesOfAssets(),
						tmpBorrower.getIndivdualAsset(), start, reset,
						paymentSchedule);

				tmpBorrower.addLoan(ln);

				addLoans(ln);
				addBorrowers(tmpBorrower);

			} else {
				lnAmnt = loanFunds - lnAmntSum;
				assetValue = lnAmnt / this.ltvRatio;
				Borrower tmpBorrower = ObjectFactory.createNewBorrower(bType,
						assetValue, avgIncomeMin, avgIncomeMax);

				Loan ln = ObjectFactory.createNewLoan(this.bankID,
						this.getRSSID(), tmpBorrower.getBorrowerID(),
						this.maturity, this.getBankName(), lnAmnt,
						contractRate, rateType, tmpBorrower.getTypesOfAssets(),
						tmpBorrower.getIndivdualAsset(), start, reset,
						paymentSchedule);

				tmpBorrower.addLoan(ln);

				addLoans(ln);
				addBorrowers(tmpBorrower);

			}

			lnAmntSum += assetValue;
		}

		this.setCashAssets_LoanableFunds(0);// reset loanable funds to zero once all
									// loanable funds have been issued in loans

	}
	
	
	public void issueLoans(BorrowerType bType, double assetValueMin,
			double assetValueMax, double avgIncomeMin, double avgIncomeMax,
			Calendar start, int resetWindow, double interestRateCurrent, double ltvMin,
			double ltvMax, double probOfSubPrime, int loanMatMin, int loanMatMax, 
			double loanMatMinProb, double loanBorrowerHousingCostBurdenProb,
			RateType rateType, PaymentSchedule paymentSchedule) {

		/*
		 * To issue loans, 1: determine the loan amount: this will be a random
		 * amount within the maximum and minimum average asset value range.
		 * Since this range is passed on to ObjectFactory public static Borrower
		 * createNewBorrower(BorrowerType type, double assetValueMin, double
		 * assetValueMax, double avgIncomeMin, double avgIncomeMax)
		 * 
		 * public Loan(int issuerID, int borrowerID, int maturity, String
		 * issuerName, double loanAmount, double contractRate, RateType
		 * rateType, AssetType assetType, Asset asset, Calendar startDate,
		 * Calendar reset, PaymentSchedule paymentSchedule)
		 * 
		 * public Calendar start = new GregorianCalendar(2001, 0, 1); public
		 * Calendar reset = new GregorianCalendar(start.get(Calendar.YEAR) + 2,
		 * start.get(Calendar.MONTH), start.get(Calendar.DATE));
		 */

		double loanFunds = this.getCashAssets_LoanableFunds();//*1000;//multiplying by 1000 because bank data is in thousands
		double lnAmntSum = 0;
		double assetValue = 0;
		double lnAmnt = 0;
		double contractRate = this.interestSpread + interestRateCurrent;
		Calendar reset = new GregorianCalendar(start.get(Calendar.YEAR)
				+ resetWindow, start.get(Calendar.MONTH),
				start.get(Calendar.DATE));
		Random rnd = new Random();
		double loanLTVRatio = 0;
		double borrowerAvgIncome = 0;
		int loanMaturity = 0;
		double borrowerDefaultPropensity = 0;
		setMBSPayMentStructure(paymentSchedule);

		while (lnAmntSum < loanFunds) {
			assetValue = assetValueMin
					+ (Math.random() * (assetValueMax - assetValueMin));// create
																		// a
																		// asset
																		// value
																		// based
																		// on
																		// average
																		// market
																		// data
			if(rnd.nextDouble() >= probOfSubPrime){
				loanLTVRatio = ltvMax;
				borrowerAvgIncome = avgIncomeMin;
			}else {
				loanLTVRatio = ltvMin;
				borrowerAvgIncome = avgIncomeMax;
			}
			
			if(rnd.nextDouble() >= loanMatMinProb){
				loanMaturity = loanMatMax;
			}else {
				loanMaturity = loanMatMin;
			} //
			
			if(rnd.nextDouble() <= loanBorrowerHousingCostBurdenProb){
				borrowerDefaultPropensity = loanBorrowerHousingCostBurdenProb;
			}else {
				borrowerDefaultPropensity = 0;
			}

			lnAmnt = assetValue * loanLTVRatio;
			if (lnAmnt < (loanFunds - lnAmntSum)) {
				Borrower tmpBorrower = ObjectFactory.createNewBorrower(bType,
						assetValue, borrowerAvgIncome, borrowerDefaultPropensity);

				Loan ln = ObjectFactory.createNewLoan(this.bankID,
						this.getRSSID(), tmpBorrower.getBorrowerID(),
						loanMaturity, this.getBankName(), lnAmnt,
						this.getReturnOnAssets(), rateType, tmpBorrower.getTypesOfAssets(),
						tmpBorrower.getIndivdualAsset(), start, reset,
						paymentSchedule);

				tmpBorrower.addLoan(ln);

				addLoans(ln);
				addBorrowers(tmpBorrower);

			} else {
				lnAmnt = loanFunds - lnAmntSum;
				
				if(rnd.nextDouble() >= probOfSubPrime){
					loanLTVRatio = ltvMax;
					borrowerAvgIncome = avgIncomeMin;
				}else {
					loanLTVRatio = ltvMin;
					borrowerAvgIncome = avgIncomeMax;
				}
				
				if(rnd.nextDouble() >= loanMatMinProb){
					loanMaturity = loanMatMax;
				}else {
					loanMaturity = loanMatMin;
				}
				
				if(rnd.nextDouble() <= loanBorrowerHousingCostBurdenProb){
					borrowerDefaultPropensity = loanBorrowerHousingCostBurdenProb;
				}else {
					borrowerDefaultPropensity = 0;
				}

				assetValue = lnAmnt / loanLTVRatio;
				Borrower tmpBorrower = ObjectFactory.createNewBorrower(bType,
						assetValue, borrowerAvgIncome, borrowerDefaultPropensity);

				Loan ln = ObjectFactory.createNewLoan(this.bankID,
						this.getRSSID(), tmpBorrower.getBorrowerID(),
						loanMaturity, this.getBankName(), lnAmnt,
						this.getReturnOnAssets(), rateType, tmpBorrower.getTypesOfAssets(),
						tmpBorrower.getIndivdualAsset(), start, reset,
						paymentSchedule);

				tmpBorrower.addLoan(ln);

				addLoans(ln);
				addBorrowers(tmpBorrower);

			}

			lnAmntSum += assetValue;
		}

		this.setCashAssets_LoanableFunds(0);// reset loanable funds to zero once all
									// loanable funds have been issued in loans

	}

	
	public void issueLoans(BorrowerType bType, int loansPerIssuance, double avgIncomeMin, double avgIncomeMax,
			Calendar start, int resetWindow, double interestRateCurrent, double ltvMin,
			double ltvMax, double probOfSubPrime, int loanMatMin, int loanMatMax, 
			double loanMatMinProb, double loanBorrowerHousingCostBurdenProb,
			RateType rateType, PaymentSchedule paymentSchedule) {
		
		/*
		 * public void issueLoans(
		 * BorrowerType bType, (Economy) 
		 * int loansPerIssuance, (Economy)
		 * double avgIncomeMin, (Economy) 
		 * double avgIncomeMax, (Economy)
		 * Calendar start, (Environment) 
		 * int resetWindow, (Economy)
		 * double interestRateCurrent, (Economy)
		 * double ltvMin, (Economy)
		 * double ltvMax, (Economy)
		 * double probOfSubPrime, (Economy)
		 * int loanMatMin, (Economy)
		 * int loanMatMax, (Economy)
		 * double loanMatMinProb, (Economy)
		 * double loanBorrowerHousingCostBurdenProb, (Economy)
		 * RateType rateType, (Economy)
		 * PaymentSchedule paymentSchedule (Economy)
		 * ) 
		 * {
		 */

		/*
		 * To issue loans, 1: determine the loan amount: this will be a random
		 * amount within the maximum and minimum average asset value range.
		 * Since this range is passed on to ObjectFactory public static Borrower
		 * createNewBorrower(BorrowerType type, double assetValueMin, double
		 * assetValueMax, double avgIncomeMin, double avgIncomeMax)
		 * 
		 * public Loan(int issuerID, int borrowerID, int maturity, String
		 * issuerName, double loanAmount, double contractRate, RateType
		 * rateType, AssetType assetType, Asset asset, Calendar startDate,
		 * Calendar reset, PaymentSchedule paymentSchedule)
		 * 
		 * public Calendar start = new GregorianCalendar(2001, 0, 1); public
		 * Calendar reset = new GregorianCalendar(start.get(Calendar.YEAR) + 2,
		 * start.get(Calendar.MONTH), start.get(Calendar.DATE));
		 */

		double loanFunds = this.getCashAssets_LoanableFunds();//*1000;//multiplying by 1000 because bank data is in thousands
		double mdMDSReturn = this.getMdRateOfReturn();
		double lnAmntSum = 0;
		double assetValue = loanFunds/loansPerIssuance;
		double lnAmnt = 0;
		///double contractRate = this.interestSpread + interestRateCurrent;
		Vector<Loan> mbsPool = new Vector<Loan>();
		Calendar reset = new GregorianCalendar(start.get(Calendar.YEAR)
				+ resetWindow, start.get(Calendar.MONTH),
				start.get(Calendar.DATE));
		Random rnd = new Random();
		double loanLTVRatio = 0;
		double borrowerAvgIncome = 0;
		int loanMaturity = 0;
		double borrowerDefaultPropensity = 0;
		setMBSPayMentStructure(paymentSchedule);
		//bankSPVConduit.setMBSPaymentStructure(paymentSchedule);

		while (lnAmntSum < loanFunds) {
			
			if(rnd.nextDouble() >= probOfSubPrime){
				loanLTVRatio = ltvMax;
				borrowerAvgIncome = avgIncomeMin;
			}else {
				loanLTVRatio = ltvMin;
				borrowerAvgIncome = avgIncomeMax;
			}
			
			if(rnd.nextDouble() >= loanMatMinProb){
				loanMaturity = loanMatMax;
			}else {
				loanMaturity = loanMatMin;
			} //
			
			if(rnd.nextDouble() <= loanBorrowerHousingCostBurdenProb){
				borrowerDefaultPropensity = loanBorrowerHousingCostBurdenProb;
			}else {
				borrowerDefaultPropensity = 0;
			}

			lnAmnt = assetValue * loanLTVRatio;
			if (lnAmnt < (loanFunds - lnAmntSum)) {
				Borrower tmpBorrower = ObjectFactory.createNewBorrower(bType,
						assetValue, borrowerAvgIncome, borrowerDefaultPropensity);

				Loan ln = ObjectFactory.createNewLoan(this.bankID,
						this.getRSSID(), tmpBorrower.getBorrowerID(),
						loanMaturity, this.getBankName(), lnAmnt,
						this.getReturnOnAssets(), rateType, tmpBorrower.getTypesOfAssets(),
						tmpBorrower.getIndivdualAsset(), start, reset,
						paymentSchedule);

				tmpBorrower.addLoan(ln);
				
				mbsPool.add(ln);

				addLoans(ln);
				addBorrowers(tmpBorrower);

			} else {
				lnAmnt = loanFunds - lnAmntSum;
				
				if(rnd.nextDouble() >= probOfSubPrime){
					loanLTVRatio = ltvMax;
					borrowerAvgIncome = avgIncomeMin;
				}else {
					loanLTVRatio = ltvMin;
					borrowerAvgIncome = avgIncomeMax;
				}
				
				if(rnd.nextDouble() >= loanMatMinProb){
					loanMaturity = loanMatMax;
				}else {
					loanMaturity = loanMatMin;
				}
				
				if(rnd.nextDouble() <= loanBorrowerHousingCostBurdenProb){
					borrowerDefaultPropensity = loanBorrowerHousingCostBurdenProb;
				}else {
					borrowerDefaultPropensity = 0;
				}

				assetValue = lnAmnt / loanLTVRatio;
				Borrower tmpBorrower = ObjectFactory.createNewBorrower(bType,
						assetValue, borrowerAvgIncome, borrowerDefaultPropensity);

				Loan ln = ObjectFactory.createNewLoan(this.bankID,
						this.getRSSID(), tmpBorrower.getBorrowerID(),
						loanMaturity, this.getBankName(), lnAmnt,
						this.getReturnOnAssets(), rateType, tmpBorrower.getTypesOfAssets(),
						tmpBorrower.getIndivdualAsset(), start, reset,
						paymentSchedule);

				tmpBorrower.addLoan(ln);
				
				mbsPool.add(ln);
				
				
				
				addLoans(ln);
				addBorrowers(tmpBorrower);

			}
			
			
			/**
			 * 
			 * issue the MBS once the loans to be securitised are issued
			 * 
			 * 	public void issueSeries(Vector<Loan> refPool, double mdReturn,
			 * 							double grossOutstandingNotional, PaymentSchedule sched)
			 */
//			System.out.println(mbsPool.toString());
//			System.out.println(this.getMBSPayMentStructure());
			this.getBankSPVConduit().issueSeries(mbsPool, mdMDSReturn, loanFunds, this.getMBSPayMentStructure());

			lnAmntSum += assetValue;
		}

		this.setCashAssets_LoanableFunds(0);// reset loanable funds to zero once all
									// loanable funds have been issued in loans
		

	}
	
	
	protected void testBankruptcy(double capReqRate, double asset, double liab, double equity) {
		// TODO Auto-generated method stub
		
		double capReq = capReqRate*asset;
		
		
		if (asset > liab && equity >= capReq) {
//			System.out.println(" Bank is Solvent  !! ");
			
			
			//this section of code is to keep a record of spreads, survival probabilities
			double[] bankruptcyRangeForAssetLiabilityRatio;
			bankruptcyRangeForAssetLiabilityRatio = new double[2];
			bankruptcyRangeForAssetLiabilityRatio[0] = this.returnOnAssets - this.returnOnLiabilities;
			bankruptcyRangeForAssetLiabilityRatio[1] = this.onBalanceSheetDefaultRate;
			bankruptcyRangeForAssetLiabilityRatioCollection.add(bankruptcyRangeForAssetLiabilityRatio);
			
			this.status = CorporateStatus.solvent;

		} else if (asset > liab && equity < capReq) {
//			System.out.println(" Bank is Solvent but requires Capital Injection  !! ");
			this.status = CorporateStatus.equity_issuance;
			} 
//		else if (equity < 0) {
		else if (asset < liab && equity < capReq) {
//			System.out.println(" Bank is Bankrupt  !! ");
			this.status = CorporateStatus.failed;// condition for LAPF estimation
			
//			if(this.environment.getBoolBankOnlyAnalysis() == false){
				//note that the nullify all method should only be called if the model dictates that
				//analysis involves market clearing otherwise banks will continue to operate 
				//with declining or negative assets even after becoming bankrupt
				this.nolifyAll();
//			}
		}

	}
	
	protected void nolifyAll() {
		// TODO Auto-generated method stub
		this.setTotalAssets(0.0);
		this.setTotalLiabilities(0.0);
		this.setEquityCaliptal(0.0);
		this.setAssetSurvivalRate(0.0);
		this.setCashAssets_LoanableFunds(0.0);
		this.setReturnOnAssets(0.0);
		this.setReturnOnLiabilities(0.0);
		this.setReturnOnEquity(0.0);
	}
	
	protected void setCapitalRetentionOrinjection(boolean boolNonlinear, double alpha, double mbsCoupon, double sigma1,
			double sigma2, double regCapRate, double asset, double equity) {
		// TODO Auto-generated method stub
//		double recursivityMultiplier;
//		if(boolNonlinear == true) {
//			recursivityMultiplier = sigma1 + alpha*sigma2 - alpha*mbsCoupon;
//		} else {
//			recursivityMultiplier = sigma1 + alpha*sigma2;
//		}
		this.capitalRetentionOrinjection = -1*(this.regulatoryCapitalRatio*(1-this.securitisationRate)
				*this.totalAssets - this.equityCaliptal);
	}



	
	protected void setMBSPayMentStructure(PaymentSchedule paymentSchedule) {
		// TODO Auto-generated method stub
		MBSPaymentStructure = paymentSchedule;
	}
	
	
	public PaymentSchedule getMBSPayMentStructure() {
		// TODO Auto-generated method stub
		return MBSPaymentStructure;
	}




	protected void setReturnOnEquity(double requiredCapitalInjection) {
		this.returnOnEquity = -1 * requiredCapitalInjection
				/ this.equityCaliptal;
	}
	
	public void setPeriodCountIn(int kickIn){
		this.periodCountIn = kickIn;
	}

	public abstract boolean canLend();

	public double getAverageRefAssetValue() {
		return averageRefAssetValue;
	}

	public void setAverageRefAssetValue(double averageRefAssetValue) {
		this.averageRefAssetValue = averageRefAssetValue;
	}

	public double getLoanableFunds() {
		return cashAssets_LoanableFunds;
	}

	private void setLoanableFunds(double loanableFunds) {
		this.cashAssets_LoanableFunds = loanableFunds;
	}

	private void addLoans(Loan ln) {
		this.loansIssued.add(ln);
	}

	private void addBorrowers(Borrower br) {
		this.borrowers.add(br);
	}

	public double getLtvRatio() {
		return ltvRatio;
	}

	private void setLtvRatio(double ltvRatio) {
		this.ltvRatio = ltvRatio;
	}

	public int getMaturity() {
		return maturity;
	}

	private void setMaturity(int maturity) {
		this.maturity = maturity;
	}

	public int getBankID() {
		return bankID;
	}

	public int getRSSID() {
		return RSSID;
	}

	public String getBankName() {
		return bankName;
	}

	public Vector<Borrower> getBorrowers() {
		return borrowers;
	}

	public Vector<Loan> getLoansissued() {
		return loansIssued;
	}

	public double getSecuritisationRate() {
		return securitisationRate;
	}

	public void setSecuritisationRate(double securitisationRate) {
		this.securitisationRate = securitisationRate;
	}

	public double getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(double totalAssets) {
		this.totalAssets = totalAssets;
	}
	
	protected void setTotalAssets(double assetRtn, double asset, double suvRate, double mbsTrancheCoupon,
			double secCostMultiplier, double inject, double alpha, boolean securitiseLinear) {
		// TODO Auto-generated method stub
		double securitisationCostFunction = 0;
		double pastAssets = this.getTotalAssets();
		if(securitiseLinear == true){
			securitisationCostFunction = secCostMultiplier*asset;
		}else {
			securitisationCostFunction = (alpha + Math.pow(alpha, 2))*(mbsTrancheCoupon + this.securitisatonCreditEnhancements);
		}
		
		this.totalAssets = suvRate*(1-alpha)*asset+ alpha*asset + 
				assetRtn*suvRate*asset - inject - securitisationCostFunction;
				
	}

	public double getTotalMBSIssuance() {
		return totalMBSIssuance;
	}


	public void setTotalMBSIssuance(double totalMBSIssuance) {
		this.totalMBSIssuance += totalMBSIssuance;
	}


	public double getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(double totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public double getEquityCaliptal() {
		return equityCaliptal;
	}

	public void setEquityCaliptal(double equityCaliptal) {
		this.equityCaliptal = equityCaliptal;
	}

	public double getTier_1_Capital() {
		return tier_1_Capital;
	}

	public void setTier_1_Capital(double tier_1_Capital) {
		this.tier_1_Capital = tier_1_Capital;
	}

	public double getOnBalanceSheetDefaultRate() {
		return onBalanceSheetDefaultRate;
	}

	public void setOnBalanceSheetDefaultRate(double onBalanceSheetDefaultRate) {
		this.onBalanceSheetDefaultRate = onBalanceSheetDefaultRate;
	}

	public double getOffBalanceSheetDefaultRate() {
		return offBalanceSheetDefaultRate;
	}

	public void setOffBalanceSheetDefaultRate(double offBalanceSheetDefaultRate) {
		this.offBalanceSheetDefaultRate = offBalanceSheetDefaultRate;
	}

	public double getSecuritisationCostFactor() {
		return securitisationCostFactor;
	}

	public void setSecuritisationCostFactor(double securitisationCostFactor) {
		this.securitisationCostFactor = securitisationCostFactor;
	}

	public double getCashAssets_LoanableFunds() {
		return cashAssets_LoanableFunds;
	}

	public void setCashAssets_LoanableFunds(double cashAssets_LoanableFunds) {
		this.cashAssets_LoanableFunds = cashAssets_LoanableFunds;
	}

	public double getReturnOnAssets() {
		return returnOnAssets;
	}

	public void setReturnOnAssets(double returnOnAssets) {
		this.returnOnAssets = returnOnAssets;
	}

	public double getReturnOnLiabilities() {
		return returnOnLiabilities;
	}

	public void setReturnOnLiabilities(double returnOnLiabilities) {
		this.returnOnLiabilities = returnOnLiabilities;
	}

	public double getInterestSpread() {
		return interestSpread;
	}

	public void setInterestSpread(double spread) {
		this.interestSpread = spread;
	}
	
	public static int getBankIDs() {
		return bankIDs;
	}



	public static void setBankIDs(int bankIDs) {
		Bank.bankIDs = bankIDs;
	}



	public double getLtvRatioRangeLower() {
		return ltvRatioRangeLower;
	}
	
	
	public double getMdRateOfReturn() {
		return mdRateOfReturn;
	}


	public void setMdRateOfReturn(double mdRateOfReturn) {
		this.mdRateOfReturn = mdRateOfReturn;
	}




	public void setLtvRatioRangeLower(double ltvRatioRangeLower) {
		this.ltvRatioRangeLower = ltvRatioRangeLower;
	}



	public double getLtvRatioRangeUpper() {
		return ltvRatioRangeUpper;
	}



	public void setLtvRatioRangeUpper(double ltvRatioRangeUpper) {
		this.ltvRatioRangeUpper = ltvRatioRangeUpper;
	}



	public double getSecuritisationCost() {
		return securitisationCost;
	}



	public void setSecuritisationCost(double securitisationCost) {
		this.securitisationCost = securitisationCost;
	}



	public double getRegulatoryCapitalRatio() {
		return regulatoryCapitalRatio;
	}



	public void setRegulatoryCapitalRatio(double regulatoryCapitalRatio) {
		this.regulatoryCapitalRatio = regulatoryCapitalRatio;
	}



	public double getOnBalanceSheetAssetSuvivalRate() {
		return onBalanceSheetAssetSuvivalRate;
	}



	public void setOnBalanceSheetAssetSuvivalRate(
			double onBalanceSheetAssetSuvivalRate) {
		this.onBalanceSheetAssetSuvivalRate = onBalanceSheetAssetSuvivalRate;
	}



	public double getOffBalanceSheetAssetSuvivalRate() {
		return offBalanceSheetAssetSuvivalRate;
	}



	public void setOffBalanceSheetAssetSuvivalRate(
			double offBalanceSheetAssetSuvivalRate) {
		this.offBalanceSheetAssetSuvivalRate = offBalanceSheetAssetSuvivalRate;
	}



	public double getAssetSurvivalRate() {
		return assetSurvivalRate;
	}



	public void setAssetSurvivalRate(double assetSurvivalRate) {
		this.assetSurvivalRate = assetSurvivalRate;
	}



	public CorporateStatus getStatus() {
		return status;
	}



	public void setStatus(CorporateStatus status) {
		if(status == CorporateStatus.failed){
			this.failedBank = 1;
		} else {
			this.status = status;
			if(status == CorporateStatus.securitisation){
				this.securitisingBank = 1;
			}
		}
	}
	
	public int getFaildBankIdentifier(){
		return this.failedBank;
	}

	public int getSecuritisingdBankIdentifier(){
		return this.securitisingBank;
	}


	public double getReturnOnEquity() {
		return returnOnEquity;
	}
	
	public double getCapitalRetentionOrinjection(){
		return capitalRetentionOrinjection;
	}
	
	public void setSecuritisationIssuanceAmount(double amount){
		this.securitisationIssuanceAmount = amount;
	}
	
	public void setSecuritisationIssuanceTotalNotional(double amount){
		this.securitisationIssuanceTotalNotional = amount;
	}
	public double getSecuritisationIssuanceTotalNotional(){
		return securitisationIssuanceTotalNotional;
	}
	
	public double getSecuritisationIssuanceAmount(double amount){
		return securitisationIssuanceAmount;
	}


	public void securitise(double sigma1, double sigma2,
			boolean securitiseLinear) {
		// TODO Auto-generated method stub
		
	}
	
	public void IssueMBSNotes(double d) {
		// TODO Auto-generated method stub
		
	}
	
	
	public XYSeries getCapitalInjectionAndSecuritisationSeries(){
		return this.optimalSecuritisation;
	}
	
	
	public double[] getFocusBankOptimalSecRateArray(){
		return this.focusBankOptimalSecRateArray;
	}
	public double[] getFocusBankOptimalSecCapitalAccumulationArray(){
		return this.focusBankOptimalSecCapitalAccumulationArray;
	}
	
	

	public void initializeErethRothSecuritisationModel(){
		this.erevRothSecuritisationModel.updateLearningEnvironmentData(this.totalAssets, this.totalLiabilities, 
				-749, this.regulatoryCapitalRatio, this.returnOnAssets, 
				this.returnOnLiabilities, 
				this.economy.getRecoveryRateOnCreditAsset(), this.assetSurvivalRate,
				1-this.economy.getGenericPostRateResetDafaultRate());
		this.erevRothSecuritisationModel.setQuadraticSecCost(!this.environment.getBoolNonlinear());// using the ! to inverse the getBoolNonlinear
		//the getBoolNonlinear actually returns true if the model uses a linear securitisation function
		this.erevRothSecuritisationModel.setLoanMarketStateBias(
				this.ErevRothDecisionModelParameters.loanMarketSentimentShareErevRoth);
		this.erSolver = new MDPErevRothSolver(this.erevRothSecuritisationModel);
		this.erSolver.updateActionsDomain(this.actionsDomainErevRoth);
	}
	
	/**
	 * this is the method to call to make the securitisation decision each simulation time period
	 * Note:
	 * This method will only pick the next securitisation rate if the simulation time is > 0
	 */
	public void getNextSecuritisationRate(){

//		double[] demandCreditWeights = new double[this.economy.getInvestorList().size()];
//		double averageX = 0;
//		for(int i = 0; i < this.economy.getInvestorList().size(); i++){
//			demandCreditWeights[i] = this.economy.getInvestorList().get(i).getOptimalCreditAssetWieght();
//		}
//		
//		averageX = Means.arithmeticMean(demandCreditWeights);
//		System.out.println("averageX = "+averageX );

		if(Sim.getAbsoluteTime()>0){
			if(this.status != CorporateStatus.failed){
				this.testBankruptcy(this.regulatoryCapitalRatio, this.getTotalAssets(), this.getTotalLiabilities(),
						(this.getTotalAssets() - this.getTotalLiabilities()));
				//			System.out.println("Coporate Status = "+this.status );
			}
			if(this.status != CorporateStatus.failed){
				this.erSolver.determineNextAction();
				double secRate = this.erSolver.newSecuritisationRate;
				this.setSecuritisationRate(secRate);
			}
			else{
				this.setSecuritisationRate(0);
				this.nolifyAll();
			}
		}
//		System.out.println("SecuritisationRate = "+this.getSecuritisationRate() );
	}
	
	/**
	 * this is the method to call after each simulation iteration
	 * after the economy collates all the demand
	 */
	public void updateErevRothSecuritisationModel(){
		System.gc();
		this.erevRothSecuritisationModel.setInitialSecuritisationRate(this.securitisationRate);
		this.erevRothSecuritisationModel.setSecuritisationCostFactor(this.securitisationCostFactor);
		this.erevRothSecuritisationModel.updateLearningEnvironmentData(this.totalAssets, this.totalLiabilities, 
				this.capitalRetentionOrinjection, this.regulatoryCapitalRatio, this.returnOnAssets, 
				this.returnOnLiabilities, 
				this.economy.getRecoveryRateOnCreditAsset(), this.assetSurvivalRate,
				1-this.economy.getGenericPostRateResetDafaultRate());
		ArrayList<CInvestor> demandSideList = new ArrayList<CInvestor>();
		demandSideList = this.economy.getInvestorList();
		double[] demandCreditWeights = new double[demandSideList.size()];
		
		for(int i = 0; i < demandSideList.size(); i++){
			demandCreditWeights[i] = demandSideList.get(i).getOptimalCreditAssetWieght();
		}
		double demand = Means.arithmeticMean(demandCreditWeights);
//		System.out.println("demand = "+demand );
//		double demand = this.economy.getAggregateFundMBSAllocationUpdate();
		this.erevRothSecuritisationModel.setMarketDemandSettings(demand);
		this.erevRothSecuritisationModel.setQuadraticSecCost(!this.environment.getBoolNonlinear());// using the ! to inverse the getBoolNonlinear
		//the getBoolNonlinear actually returns true if the model uses a linear securitisation function
		this.erevRothSecuritisationModel.setLoanMarketStateBias(
				this.ErevRothDecisionModelParameters.loanMarketSentimentShareErevRoth);
		this.erSolver.updateErevRothSecuritisationProblem(this.erevRothSecuritisationModel, this.actionsDomainErevRoth);
		
	}


}
