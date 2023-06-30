

import java.util.Vector;



public class Borrower extends EconomicAgent{

	protected static int borrowerIDs = 0;

	private final int borrowerID;
	protected int ficoScore;
	protected double income;
	protected double defaultpropensity;
	protected double defaultProbability;
	protected double assetValue;
	// protected double stigmaCost;
	// protected double probOfDefJdg;
	// protected double mnthsInFor;
	protected double ficoBias;
	protected double critialLTV;

	protected PersonalCreditRatings rating;
	protected Employment_Status emp_status;
	protected Status status;

	protected Vector<Asset> assets;
	protected Vector<Loan> loans;
	
	private Economy economy; 
	
	private Enviroment environment;
	
	GeoEconoPoliticalSpace world;

	
	@SuppressWarnings("static-access")
	public Borrower(BorrowerType type, double wage, int ficoRating,
			PersonalCreditRatings creditRating, Asset asset, Economy econ,
			Enviroment env,  GeoEconoPoliticalSpace world) {
		super();
		borrowerIDs++;
		this.borrowerID = borrowerIDs;
		this.income = wage;
		this.ficoScore = ficoRating;
		this.setFICOBiase();
		this.rating = creditRating;
		assets = new Vector<Asset>();
		loans = new Vector<Loan>();
		assets.addElement(asset);
		this.critialLTV = 0.3 + (Math.random() * (1.2 - 0.3));
		this.defaultpropensity = Math.random();
		this.emp_status = Employment_Status.employed;
		this.status = Status.Active;

	}
	
	/**
	 * new Borrower(type, earnings, ficoRating, creditRating,
					asset, borrowerDefaultPropensity)
	 * @param type
	 * @param wage
	 * @param ficoRating
	 * @param creditRating
	 * @param asset
	 *  ContinuousSpace <Object> space, 
			Grid<Object> grid, 
	 */
	
	@SuppressWarnings("static-access")
	public Borrower(BorrowerType type, double wage, int ficoRating,
			PersonalCreditRatings creditRating, Asset asset) {
		super();
		borrowerIDs++;
		this.borrowerID = borrowerIDs;
		this.income = wage;
		this.ficoScore = ficoRating;
		this.setFICOBiase();
		this.rating = creditRating;
		assets = new Vector<Asset>();
		loans = new Vector<Loan>();
		assets.addElement(asset);
		this.critialLTV = 0.3 + (Math.random() * (1.2 - 0.3));
		this.defaultpropensity = Math.random();
		this.emp_status = Employment_Status.employed;
		this.status = Status.Active;

	}
	
	
	public Borrower(BorrowerType type, double wage, int ficoRating,
			PersonalCreditRatings creditRating, Asset asset, double borrowerDefaultPropensity) {
		super();
		borrowerIDs++;
		this.borrowerID = borrowerIDs;
		this.income = wage;
		this.ficoScore = ficoRating;
		this.setFICOBiase();
		this.rating = creditRating;
		assets = new Vector<Asset>();
		loans = new Vector<Loan>();
		assets.addElement(asset);
		this.critialLTV = 0.3 + (Math.random() * (1.2 - 0.3));
		this.defaultpropensity = borrowerDefaultPropensity;
		this.emp_status = Employment_Status.employed;
		this.status = Status.Active;

	}


	@SuppressWarnings("static-access")
	public Borrower(BorrowerType type, double wage, int ficoRating,
			PersonalCreditRatings creditRating, Asset asset, double crtltv,
			double defaultprop) {
		super();
		borrowerIDs++;
		this.borrowerID = borrowerIDs;
		this.income = wage;
		this.ficoScore = ficoRating;
		this.setFICOBiase();
		this.rating = creditRating;
		assets = new Vector<Asset>();
		loans = new Vector<Loan>();
		assets.addElement(asset);
		this.critialLTV = crtltv;
		this.defaultpropensity = defaultprop;
		this.emp_status = Employment_Status.employed;
		this.status = Status.Active;

	}

	@Override
	public String toString() {
		return "Borrower [ borrowerID=" + borrowerID + ", ficoScore="
				+ ficoScore + ", credit Rating=" + rating
				+ ", defaultpropensity=" + defaultpropensity + ", Asset Type="
				+ getTypesOfAssets() + ", Number of Assets=" + assets.size()
				+ ", Value of Assets=" + getTotalValueOfAssets() + " ]";
	}

	public void checkStatus(double mRate, double stigmaCost,
			double probOfDefJdg, double mnthsInFor) {
		/*
		 * Defualt condition is defined along the lines of Crawford and
		 * Rosenbulatt (1995)
		 * "Efficient Mortgage Default Option Execise: Evidence from Loss Severity"
		 * as
		 * 
		 * [assetVal +
		 * stigmaCost(outstandingBalance)+probDefJudgement*(outstandBalance
		 * -assetVal +
		 * contractRate*outstandBalance*monthsInForclosure)+outstandBalance
		 * -mktValueOfMortgage]
		 * 
		 * <=
		 * 
		 * outstandBalance + contractRate*outstandBalance*monthsInForclosure
		 * 
		 * note that mktValueOfMortgage = PMT*[paymentCount +
		 * (1+mktRate)^(maturity*12-paymentCount)]
		 * 
		 * That is to say The borrower will default if the loss from default is
		 * less than or equal to the gain from default. The loss to the borrower
		 * includes 1: the value of the assets backing the loan, 2: the
		 * transaction/stigma costs, 3: the potential deficiency judgment
		 * equaling the losses to the lender [i.e. outstandBalance-assetVal +
		 * contractRate*outstandBalance*monthsInForclosure] with a probability
		 * probOfDefJudgment which is set by the loan originating bank and can
		 * be interpreted as the quality of its loan servicing infrastructure.
		 * and 4: the difference between the current mortgage balance and the
		 * market value of the mortgage due to interest rate changes since
		 * origination.
		 * 
		 * The gain to the borrower from default includes
		 * 
		 * 1: the current mortgage balance
		 * 
		 * and
		 * 
		 * 
		 * 2: the value of the free rent during the foreclosure months [i.e.
		 * contractRate*outstandBalance*monthsInForclosure] again this can be
		 * interpreted as a measure of how effective the originating bank is at
		 * servicing loans it originates. And is therefore set by the bank.
		 */

		// setStigmaOfDefaultCost(mnthsInFor);
		// setProbabilityOfDeficiencyJudgement(mnthsInFor);
		// setMonthsinForeclosure(mnthsInFor);

		Loan ln;
		Asset tmpAsset = null;
		double aValue;
		double mktValueOfLoans;
		double lossOfDefault;
		double gainFromDefault;

		if (loans.size() > 1) {

		} else {
			// This section of code applied to the simplest case where each
			// borrower only has one loan associated with him/her
			// The process is to create a temporary loan object go

			ln = loans.get(0);
			for (int i = 0; i < assets.size(); i++) {
				if (assets.get(i).getID() == ln.getReferenceAssetID()) {
					tmpAsset = assets.get(i);
				}
			}

			mktValueOfLoans = AssetPricingAndValuation.computeNPV(mRate,
					ln.getPaymentAmount(), ln.getTimeToMaturity())
					// originally thought to do the calculation of time to maturity
					// in the method call so that 
					//timeToMaturity = this.totalNumOfPayments - this.paymentCount
					// however decided to just do this calculation in the loan class and
					// call it.
					+ ln.getPaymentAmount() * ln.getPaymentCount();// this add
																	// the value
																	// of all
																	// previous
																	// payments
																	// to the
																	// market
																	// value of
																	// remaining
																	// payments
																	// till
																	// maturity

			// loss from default
			// [assetVal +
			// stigmaCost(outstandingBalance)+probDefJudgement*(outstandBalance-assetVal
			// +
			// contractRate*outstandBalance*monthsInForclosure)+outstandBalance-mktValueOfMortgage]

			// double stigmaCost, double probOfDefJdg, double mnthsInFor
			aValue = tmpAsset.getCurrentValue();
			lossOfDefault = aValue
					+ stigmaCost
					* ln.getOutstandingBalance()
					+ probOfDefJdg
					* ((ln.getOutstandingBalance() - tmpAsset.getCurrentValue()) + (ln
							.getContractRate() / ln.getPaymentMultiplier())
							* ln.getOutstandingBalance() * mnthsInFor)
					+ (ln.getOutstandingBalance() - mktValueOfLoans
							* (1 + this.ficoBias)
							* (ln.getLoanAmount() / tmpAsset.getCurrentValue())
							/ this.critialLTV);

			// gain from default
			//
			gainFromDefault = (ln.getContractRate() / ln.getPaymentMultiplier())
					* ln.getOutstandingBalance()
					* mnthsInFor
					+ ln.getOutstandingBalance();

			if (gainFromDefault < lossOfDefault) {
				this.status = Status.Active; // loan remains active or current
			} else {
				this.status = Status.Default; // loan defaults
			}

		}

	}// end of check status method

	public double getTotalValueOfAssets() {
		double sum = 0;
		for (int i = 0; i < this.assets.size(); i++) {
			sum += assets.get(i).getCurrentValue();
		}
		return sum;
	}

	public AssetType getTypesOfAssets() {
		AssetType type = AssetType.Cash;
		if (this.assets.size() > 1) {

		} else {
			type = assets.get(0).getType();
		}
		return type;
	}
	
	public Asset getIndivdualAsset() {
		Asset ast = null;
		if (this.assets.size() > 1) {

		} else {
			ast = assets.get(0);
		}
		
		return ast;
	}
	
	public Asset getIndivdualAsset(int assetID) {
		Asset ast = null;
		if (this.assets.size() > 1) {

		} else {
			ast = assets.get(0);
		}
		
		return ast;
	}



	private void setMonthsinForeclosure(double minFor) {
		// TODO Auto-generated method stub

	}

	private void setProbabilityOfDeficiencyJudgement(double probDefJdg) {
		// TODO Auto-generated method stub

	}

	private void setStigmaOfDefaultCost(double stigCost) {
		// TODO Auto-generated method stub

	}

	private void setFICOBiase() {

		int step;
		double x;

		if (this.ficoScore > 620) {
			step = (850 - 620) / 2;
			if (this.ficoScore < 620 + step) {
				x = 0 + ((this.ficoScore - 620) / step);
			} else {
				x = 1 + ((this.ficoScore - 620) / step);
			}
			this.ficoBias = Math.tanh(x);

		} else {
			step = (620 - 300) / 2;
			if (this.ficoScore < 620 - step) {
				x = -2 + ((this.ficoScore - 300) / step);
			} else {
				x = -1 + ((this.ficoScore - (620 - step)) / step);
			}
			this.ficoBias = Math.tanh(x);
		}

	}

	public void addLoan(Loan ln) {
		this.loans.addElement(ln);

	}

	public int getFicoScore() {
		return ficoScore;
	}

	public void setFicoScore(int ficoScore) {
		this.ficoScore = ficoScore;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getDefaultpropensity() {
		return defaultpropensity;
	}

	public void setDefaultpropensity(double defaultpropensity) {
		this.defaultpropensity = defaultpropensity;
	}

	public double getFicoBias() {
		return ficoBias;
	}

	public void setFicoBias(double ficoBias) {
		this.ficoBias = ficoBias;
	}

	public double getCritialLTV() {
		return critialLTV;
	}

	public void setCritialLTV(double critialLTV) {
		this.critialLTV = critialLTV;
	}

	public PersonalCreditRatings getRating() {
		return rating;
	}

	public void setRating(PersonalCreditRatings rating) {
		this.rating = rating;
	}

	public Employment_Status getEmp_status() {
		return emp_status;
	}

	public void setEmp_status(Employment_Status emp_status) {
		this.emp_status = emp_status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Vector<Asset> getAssets() {
		return assets;
	}

	public void setAssets(Vector<Asset> assets) {
		this.assets = assets;
	}

	public Vector<Loan> getLoans() {
		return loans;
	}
	
	public void identifyLoans() {
		for (int i = 0; i < this.loans.size(); i++) {
			System.out.print(loans.get(i).toString());
		}
	}


	public void setLoans(Vector<Loan> loans) {
		this.loans = loans;
	}

	public int getBorrowerID() {
		return borrowerID;
	}

	@Override
	public void checkLife() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reproduce() {
		// TODO Auto-generated method stub
		
	}
}
