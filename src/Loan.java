

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

public class Loan {

	protected static int loanIDs = 0;
	private final int loanID;
	protected int issuerID;
	protected int issuerRSSID;
	protected int borrowerID;
	protected int referenceAssetID;
	protected int maturity; // in years
	protected int totalNumberOfPayments; // in years
	protected int timeToMaturity;
	protected String issuerName;
	protected double loanAmount;
	protected double contractRate;
	protected double paymentAmount;
	protected double principalPayment;
	protected double interestPayment;
	protected double paymentFactor;
	protected double periodRate;
	protected double outstandingBalance;

	protected Vector<Statement> statements;
	protected RateType rateType;
	protected AssetType assetType;
	protected Asset referenceAsset;
	protected Calendar startDate;
	protected Calendar endDate;
	protected Calendar reset;
	protected Status status;
	protected PaymentSchedule paymentSchedule;
	private int paymentMultiplier;

	private int statementIndex = 0;

	@SuppressWarnings("static-access")
	public Loan(int issuerID, int borrowerID, int maturity, int issuerRSSID,
			String issuerName, double loanAmount, double contractRate,
			RateType rateType, AssetType assetType, Asset asset,
			Calendar startDate, Calendar reset, PaymentSchedule paymentSchedule) {
		super();
		Loan.loanIDs++;
		this.loanID = loanIDs;
		this.issuerRSSID = issuerRSSID;
		this.issuerID = issuerID;
		this.borrowerID = borrowerID;
		this.maturity = maturity;
		this.issuerName = issuerName;
		this.loanAmount = loanAmount;
		this.contractRate = contractRate;
		this.rateType = rateType;
		this.assetType = assetType;
		this.referenceAsset = asset;
		this.startDate = new GregorianCalendar(startDate.get(Calendar.YEAR),
				startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
		this.reset = new GregorianCalendar(reset.get(Calendar.YEAR),
				reset.get(Calendar.MONTH), reset.get(Calendar.DATE));
		this.endDate = new GregorianCalendar(startDate.get(Calendar.YEAR)
				+ this.maturity, startDate.get(Calendar.MONTH),
				startDate.get(Calendar.DATE));
		this.statements = new Vector<Statement>();
		this.paymentSchedule = paymentSchedule;
		this.status = Status.Active;

		switch (this.paymentSchedule) {
		case weekly:
			setPaymentMultiplier(52);
			setTotalNumberOfPayments(this.paymentMultiplier);
			setTimeToMaturity(this.totalNumberOfPayments);
			break;

		case bi_weekly:
			setPaymentMultiplier(26);
			setTotalNumberOfPayments(this.paymentMultiplier);
			break;

		case monthly:
			setPaymentMultiplier(12);
			setTotalNumberOfPayments(this.paymentMultiplier);
			break;

		case quarterly:
			setPaymentMultiplier(4);
			setTotalNumberOfPayments(this.paymentMultiplier);
			break;
		case semi_annually:
			setPaymentMultiplier(2);
			setTotalNumberOfPayments(this.paymentMultiplier);
			break;
		case annually:
			setPaymentMultiplier(1);
			setTotalNumberOfPayments(this.paymentMultiplier);
			break;

		}// end switch

		this.activateLoan();

		// System.out.println(this.outstandingBalance + " , "
		// + this.interestPayment);
		// this.setOutstandingBalance(this.loanAmount);

	}// end constructor definition issuerRSSID

	@Override
	public String toString() {
		return "Loan [loanID=" + loanID + ", issuerID=" + issuerID
				+ ", borrowerID=" + borrowerID + ", Asset Type="
				+ getTypesOfReferenceAsset() + ", maturity=" + maturity
				+ ", issuerName=" + issuerName + ", issuerRSSID=" + issuerRSSID
				+ ", loanAmount=" + loanAmount + ", contractRate = "
				+ contractRate + ", paymentAmount=" + paymentAmount
				+ ", OutStanding Balance = " + outstandingBalance
				+ ", Principal Payment = " + principalPayment
				+ ", Interest Payment = " + interestPayment
				+ ", Payment Multiplier = " + paymentMultiplier
				+ ", Payment Schedule = " + paymentSchedule
				+ ", Total Number of Payment = " + totalNumberOfPayments
				+ ", rateType = " + rateType + ", startDate = "
				+ printCalendar(startDate) + ", endDate = "
				+ printCalendar(endDate) + ", reset = " + printCalendar(reset)
				+ "]" + "\n" + "Statement " + statementIndex + " : "
				+ statements.get(statementIndex - 1).toString();
	}

	private AssetType getTypesOfReferenceAsset() {
		// TODO Auto-generated method stub
		return this.referenceAsset.getType();
	}

	private void activateLoan() {
		/*
		 * This sets the initial payment/cash flow related values for the loan
		 */
		this.setPaymentFactor();
		this.setPayments();
		this.setOutstandingBalance(0);
		this.setInterestComponent(0);
		this.setPrincipalComponent();
		this.generateStatement();
	}

	private void setPaymentFactor() {
		/*
		 * The Payment Factor is defined as
		 * 
		 * pf = [1/(c/12) - 1/((c/12)*(1+c/12)^n)] see The Securitisation
		 * Markets Handbook: Structures and Dynamics of Mortgage and asset
		 * Backed Securities pp 30
		 */
		this.periodRate = (this.contractRate / this.paymentMultiplier);
		this.paymentFactor = (1 / this.periodRate)
				- (1 / (this.periodRate * Math.pow(1 + this.periodRate,
						this.totalNumberOfPayments)));
	}

	private double getCurrentPaymentFactor(int paymentCount) {
		double factor = 0.0;
		int paymentsRemaining = this.totalNumberOfPayments - paymentCount;
		factor = (1 - (1 / (Math.pow(1 + this.periodRate, paymentsRemaining))))
				/ this.periodRate;
		return factor;
	}

	// sets the outstanding balance
	private void setOutstandingBalance(int paymentCount) {
		/*
		 * the outstanding balance is defined by
		 * 
		 * OB(m − 1) = OB(0)*{ [(1 + c/12)^n − (1 + c/12)^m−1] /(1 +
		 * c/12)^n − 1}
		 * 
		 * this original formulation of the outstanding loan balance does not
		 * appear to work properly so it was changed to OB(t) = PMT *[1 -
		 * (1/(1+c/12)^(n - t)) / c/12] where PMT = loanAmount * [1/(c/12) -
		 * 1/((c/12)*(1+c/12)^n)]
		 */
		// this.outstandingBalance = this.loanAmount
		// * ((Math.pow((1 + this.contractRate / this.paymentMultiplier),
		// this.totalNumberOfPayments)
		// - Math.pow((1 + this.contractRate / this.paymentMultiplier),
		// paymentCount)) / Math.pow((1 + this.contractRate /
		// this.paymentMultiplier), this.totalNumberOfPayments - 1));

		double factor = getCurrentPaymentFactor(paymentCount);
		if (paymentCount > 0) {
			this.outstandingBalance = (this.loanAmount / this.paymentFactor)
					* factor;
		} else {
			this.outstandingBalance = this.loanAmount;
		}

	}// end of setOutstandingbalance

	public double getCurrentOutstandingBalance(int paymentCount) {
		/*
		 * the outstanding balance is defined by
		 * 
		 * OB(m − 1) = OB(0)*{ [(1 + c/12)^n − (1 + c/12)^m−1] /(1 +
		 * c/12)^n − 1}
		 * 
		 * this original formulation of the outstanding loan balance does not
		 * appear to work properly so it was changed to OB(t) = PMT *[1 -
		 * (1/(1+c/12)^(n - t)) / c/12] where PMT = loanAmount * [1/(c/12) -
		 * 1/((c/12)*(1+c/12)^n)]
		 */
		// this.outstandingBalance = this.loanAmount
		// * ((Math.pow((1 + this.contractRate / this.paymentMultiplier),
		// this.totalNumberOfPayments)
		// - Math.pow((1 + this.contractRate / this.paymentMultiplier),
		// paymentCount)) / Math.pow((1 + this.contractRate /
		// this.paymentMultiplier), this.totalNumberOfPayments - 1));

		double outBalance = 0.0;

		double factor = getCurrentPaymentFactor(paymentCount);
		if (factor == 0.0)
			return outBalance;
		if (paymentCount > 0) {
			outBalance = (this.loanAmount / factor) * factor;
		} else {
			outBalance = this.loanAmount;
		}
		return outBalance;

	}// end of setOutstandingbalance

	private void setPayments() {
		/*
		 * 
		 * for a normal maturing loan MP = OB(0)*{[(c/12)*(1 + c/12)^n]/[(1 +
		 * c/12)^n − 1]}
		 * 
		 * 
		 * OB(m − 1) = OB(0)*{[(1 + c/12)^n − (1 + c/12)^m−1]/(1 + c/12)^n
		 * − 1}
		 * 
		 * 
		 * for a perpetuity perMP = facevalue*r/12months in a year
		 */

		// this.paymentAmount = this.loanAmount
		// * (((this.contractRate / this.paymentMultiplier) * Math.pow(
		// (1 + (this.contractRate / this.paymentMultiplier)),
		// this.totalNumberOfPayments))
		// /
		// ((Math.pow((1 + (this.contractRate / this.paymentMultiplier)),
		// this.totalNumberOfPayments)) - 1));

		this.paymentAmount = this.loanAmount / this.paymentFactor;

	}// end set payments

	private void setInterestComponent(int paymentCount) {

		double bal;
		if (paymentCount > 0) {
			bal = getPreviousOutstandingBalance(paymentCount - 1);
		} else {
			bal = this.loanAmount;
		}
		this.interestPayment = this.periodRate * bal;

	}

	private void setPrincipalComponent() {
		this.principalPayment = this.paymentAmount - this.interestPayment;
	}

	private double getPreviousOutstandingBalance(int paymentCount) {
		/*
		 * the outstanding balance is defined by
		 * 
		 * OB(m − 1) = OB(0)*{ [(1 + c/12)^n − (1 + c/12)^m−1] /(1 +
		 * c/12)^n − 1}
		 */

		double outBal_t_1;
		double factor = getCurrentPaymentFactor(paymentCount);
		outBal_t_1 = (this.loanAmount / this.paymentFactor) * factor;
		return outBal_t_1;

	}// end of setOutstandingbalance

	private void generateStatement() {
		// Statement(int loanID, int bankID, int borrowerID,
		// double paymentAmount, double principalComponent,
		// double interestComponet)
		Statement s = new Statement(this.loanID, this.issuerRSSID,
				this.borrowerID, this.paymentAmount, this.principalPayment,
				this.interestPayment);
		statements.add(s);
		this.statementIndex++;

	}

	public int getLoanID() {
		return loanID;
	}

	public int getIssuerID() {
		return issuerID;
	}

	public void setIssuerID(int issuerID) {
		this.issuerID = issuerID;
	}

	public int getBorrowerID() {
		return borrowerID;
	}

	public void setBorrowerID(int borrowerID) {
		this.borrowerID = borrowerID;
	}

	public int getMaturity() {
		return maturity;
	}

	public void setMaturity(int maturity) {
		this.maturity = maturity;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getContractRate() {
		return contractRate;
	}

	public void setContractRate(double contractRate) {
		this.contractRate = contractRate;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public double getPrincipalPayment() {
		return principalPayment;
	}

	public double getPaymentMultiplier() {
		return paymentMultiplier;
	}

	public void setPrincipalPayment(double principalPayment) {
		this.principalPayment = principalPayment;
	}

	public double getInterestPayment() {
		return interestPayment;
	}

	public int getTotalNumberOfPayments() {
		return totalNumberOfPayments;
	}

	public int getTimeToMaturity() {
		// TODO Auto-generated method stub
		return timeToMaturity;
	}

	public int getPaymentCount() {
		return (this.totalNumberOfPayments - this.timeToMaturity);
	}// referenceAssetID

	public int getReferenceAssetID() {
		return this.referenceAsset.getID();
	}// referenceAssetID

	public void setInterestPayment(double interestPayment) {
		this.interestPayment = interestPayment;
	}

	public Vector<Statement> getStatements() {
		return statements;
	}

	public RateType getRateType() {
		return rateType;
	}

	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getReset() {
		return reset;
	}

	public void setReset(Calendar reset) {
		this.reset = reset;
	}

	public double getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(double outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	private void setTotalNumberOfPayments(int i) {
		// TODO Auto-generated method stub
		this.totalNumberOfPayments = this.maturity * i;
	}

	private void setTimeToMaturity(int totalNumberOfPayments2) {
		// TODO Auto-generated method stub
		this.timeToMaturity = totalNumberOfPayments2;
	}

	private void setTimeToMaturity() {
		// TODO Auto-generated method stub
		this.timeToMaturity--;
	}

	private void setPaymentMultiplier(int i) {
		// TODO Auto-generated method stub
		this.paymentMultiplier = i;
	}

	private String printCalendar(Calendar calendar) {
		// define output format and print
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
		String date = sdf.format(calendar.getTime());
		return date;
		// System.out.println(date);

	}

}
