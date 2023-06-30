

import java.util.Vector;

public class RMBSSeries extends Asset {

	/*
	 * MBS Prepay Model Assumption
	 * 
	 * Q: What are the various behaviors for MBS prepay models and what values
	 * are expected in MBS Assumption for each?
	 * 
	 * A: There are four possible MBS prepayment models:
	 * 
	 * FHA - is based on real-world prepayment experience compiled by the
	 * Federal Housing Authority. It is rarely used anymore, because it is not
	 * very helpful as a predictor of future mortgagee behavior, which is what
	 * really matters with MBS.
	 * 
	 * CPR - stands for Constant Prepayment Rate, is an estimate of how much
	 * principal -- above and beyond the amount included in the homeowner's
	 * standard monthly mortgage payment -- will be prepaid. This is stated as a
	 * percentage of the remaining principal balance. In general, it would be
	 * reasonable to expect this value to be less than 10 (or at most 20),
	 * although MBS pools with high coupons may prepay faster if everyone
	 * refinances over a short period of time.
	 * 
	 * SMM - stands for Single Monthly Mortality, effectively the same as CPR,
	 * but with the prepayment factor stated as a monthly value. In general,
	 * this should be no more than 1 or 2, as even 2 implies an annual
	 * prepayment rate of almost 25%.
	 * 
	 * PSA - A model that describes prepayments on a gradually increasing scale
	 * from 0% to 6% CPR over the first thirty months, after which prepayments
	 * are presumed to level out at 6% for the remaining life of the mortgage.
	 * Takes into account the real-world experience that most people do not
	 * prepay mortgages during the first few years, since they are (1) less
	 * likely to move, (2) less likely to refinance, and (3) usually too
	 * strapped to throw a little extra cash into their monthly mortgage
	 * payments. Since PSA is a model, the prepayment value is stated as a
	 * percentage of the model's assumptions. Therefore, 100 PSA means 100
	 * percent of the model, or CPR building from 0 to 6%; while 150 PSA means
	 * 150 percent of the model, or CPR building from 0% to 9%, where it levels
	 * out.
	 * 
	 * Home-equity prepayment curve (HEP) is also used for HELs. The HEP
	 * consists of a 10-month period of even step-ups in the percentage of
	 * prepayments per month, starting at 2% in the 1st month, and leveling off
	 * at 20% in the 10th month.
	 */

	protected static int rmbsIDs = 0;
	private final int rmbsID;

	protected double weightedAverageMaturity;
	private final Vector<Loan> referencePool;
	protected RMBSType noteType;
	protected double passthroughRate;
	protected double weightedAverageCoupon;
	protected double mdRateOfReturn;
	protected PaymentSchedule paymentSchedule;
	// protected double notional;
	protected double serviceFees;
	protected double guaranteeFees;
	protected double constantPrepaymentRate;
	private int paymentMultiplier;
	private final Vector<Double> seriesCashflows = new Vector<Double>();
	private final Vector<Integer> couponCountID = new Vector<Integer>();
	private SeriesNotesClass noteClass;
	private int paymentCount;
	private double currentPaymentAmount;
	private final int timeToMaturity;
	private double totalNominalServicingFees;
	private double totalNominalGuaranteeFees;

	public RMBSSeries(double currentValue, Vector<Loan> refPool,
			SeriesNotesClass noteClass, int paymentMplier, double cpr) {
		super(currentValue, AssetType.AssetBackedSecurity);
		// TODO Auto-generated constructor stub
		rmbsIDs++;
		this.rmbsID = rmbsIDs;
		this.referencePool = refPool;
		this.paymentMultiplier = paymentMplier;
		this.constantPrepaymentRate = cpr / this.paymentMultiplier;
		this.serviceFees = 0.0025;
		this.guaranteeFees = 0.0025;
		this.paymentCount = 0;
		this.setWieghtedAverageMaturity();
		this.setWieghtedAverageCoupon();
		this.setTotalNominalServicingFees();
		this.setTotalNominalGuaranteeFees();
		seriesCashflows.add(0, 0.0);
		generateCashflows();
		setCouponCountID();
		this.timeToMaturity = (int) this.weightedAverageMaturity
				* this.paymentMultiplier;
		computeEffectiveDuration();
		// this.computeCurrentYield();
	}
	
	
	public RMBSSeries(double currentValue, Vector<Loan> refPool,
			double mdReturn, SeriesNotesClass noteClass, int paymentMplier) {
		super(currentValue, AssetType.AssetBackedSecurity);
		// TODO Auto-generated constructor stub
		rmbsIDs++;
		this.rmbsID = rmbsIDs;
		this.referencePool = refPool;
		this.paymentMultiplier = paymentMplier;
		this.paymentCount = 0;
		this.setWieghtedAverageMaturity();
		this.setWieghtedAverageCoupon();
		this.setMDRateOfReturn(mdReturn);
		this.setTotalNominalServicingFees();
		this.setTotalNominalGuaranteeFees();
		seriesCashflows.add(0, 0.0);
		generateCashflows();
		setCouponCountID();
		this.timeToMaturity = (int) this.weightedAverageMaturity
				* this.paymentMultiplier;
		computeEffectiveDuration();
		// this.computeCurrentYield();
	}


	
	public RMBSSeries(double currentValue, Vector<Loan> refPool,
			double mdReturn, SeriesNotesClass noteClass,
			PaymentSchedule sched) {
		super(currentValue, AssetType.AssetBackedSecurity);
		// TODO Auto-generated constructor stub
		rmbsIDs++;
		this.rmbsID = rmbsIDs;
		this.referencePool = refPool;
		
		switch (sched) {
		case weekly:
			setPaymentMultiplier(52);
			break;

		case bi_weekly:
			setPaymentMultiplier(26);
			break;

		case monthly:
			setPaymentMultiplier(12);
			break;

		case quarterly:
			setPaymentMultiplier(4);
			break;
		case semi_annually:
			setPaymentMultiplier(2);
			break;
		case annually:
			setPaymentMultiplier(1);
			break;

		}// end switch

		this.paymentCount = 0;
		this.setWieghtedAverageMaturity();
		this.setWieghtedAverageCoupon();
		this.setMDRateOfReturn(mdReturn);
		this.setTotalNominalServicingFees();
		this.setTotalNominalGuaranteeFees();
		seriesCashflows.add(0, 0.0);
		generateCashflows();
		setCouponCountID();
		this.timeToMaturity = (int) this.weightedAverageMaturity
				* this.paymentMultiplier;
		computeEffectiveDuration();
		// this.computeCurrentYield();
	}

	
	
	private void setMDRateOfReturn(double mdReturn) {
		// TODO Auto-generated method stub
		mdRateOfReturn = mdReturn;
	}
	
	
	public double getMDRateOfReturn() {
		// TODO Auto-generated method stub
		return mdRateOfReturn;
	}



	private void setTotalNominalGuaranteeFees() {
		// TODO Auto-generated method stub
		this.totalNominalGuaranteeFees = 0;
		for (int i = 0; i < this.referencePool.size(); i++) {
			this.totalNominalGuaranteeFees += this.referencePool.get(i).outstandingBalance
					* this.guaranteeFees;
		}

	}

	private void setTotalNominalServicingFees() {
		// TODO Auto-generated method stub
		this.totalNominalServicingFees = 0;
		for (int i = 0; i < this.referencePool.size(); i++) {
			this.totalNominalServicingFees += this.referencePool.get(i).outstandingBalance
					* this.serviceFees;
		}

	}

	private void setWieghtedAverageMaturity() {
		// TODO Auto-generated method stub

		double stps = 0;
		// double weightedAverageMaturity = 0;

		for (int i = 0; i < this.referencePool.size(); i++) {
			stps += this.referencePool.get(i).getOutstandingBalance();
		}

		for (int j = 0; j < this.referencePool.size(); j++) {
			this.weightedAverageMaturity += ((this.referencePool.get(j)
					.getOutstandingBalance() / stps) * this.referencePool
					.get(j).getMaturity());
		}

	}

	private void setWieghtedAverageCoupon() {
		// TODO Auto-generated method stub

		double stps = 0;
		// double weightedAverageMaturity = 0;

		for (int i = 0; i < this.referencePool.size(); i++) {
			stps += this.referencePool.get(i).getOutstandingBalance();
		}

		for (int j = 0; j < this.referencePool.size(); j++) {
			this.weightedAverageCoupon += ((this.referencePool.get(j)
					.getOutstandingBalance() / stps) * this.referencePool
					.get(j).getContractRate());
		}

	}

	@Override
	public String toString() {//
		return "Loan [rmbsID=" + rmbsID + ", RMBS Notional="
				+ this.getCurrentNotionalValue() + " " + "Pool Size: "
				+ referencePool.size() + " " + "Constant Prepayment Rate: "
				+ this.constantPrepaymentRate + " Wiegthed Average Coupon: "
				+ this.weightedAverageCoupon + " Wieghted Average Maturity: "
				+ this.weightedAverageMaturity + " Yield: " + this.yield
				+ " Effective Duration: " + this.effectiveDuration + " ]"
				+ "\n" + "Time Period/Coupon Count: "
				+ couponCountID.toString() + "\n"
				+ "RMBS Periodic Cash Flows: " + seriesCashflows.toString();
	}

	private void generateCashflows() {
		int intWAM = (int) this.weightedAverageMaturity
				* this.paymentMultiplier;

		for (int t = 0; t < intWAM; t++) {
			double seriescashflow_t = 0;
			double pmtSum = 0;
			double outstandingBalanceSum_t = 0;
			double outstandingBalanceSum_t_1 = 0;
			for (int i = 0; i < this.referencePool.size(); i++) {
				Loan ln = this.referencePool.get(i);
				pmtSum += ln.getPaymentAmount();
				if (ln.maturity * this.paymentMultiplier < t - 1) {

					outstandingBalanceSum_t += ln
							.getCurrentOutstandingBalance(t);
					// System.out.println("outstandingBalanceSum_t===>"
					// + outstandingBalanceSum_t
					// + "Jerry see T value====>" + t);
					// double tempSum = ln.getCurrentOutstandingBalance(t - 1);
					outstandingBalanceSum_t_1 += ln
							.getCurrentOutstandingBalance(t - 1);
					// outstandingBalanceSum_t_1 += tempSum;
					// System.out.println("outstandingBalanceSum_t_1===>"
					// + outstandingBalanceSum_t_1
					// + "Jerry see T value====>" + (t - 1));
					// outstandingBalanceSum_t += ln
					// .getCurrentOutstandingBalance(t);
				}

			}// end inner loan summation for
			/*
			 * The time t series cash flow is taken from Stone & Zissu (2005)
			 * "Securitization Markets Handbook: Structures and Dynamics of Mortgage and Asset-Backed Securities"
			 * pp 50 equation 3.1
			 * 
			 * PT = m0*((1-CPR)^t-1)*[PMT + CPR*Bt - (s+g)*Bt-1]
			 * 
			 * where m0 is the number of loans in the pool at time t = 0
			 */
			seriescashflow_t = Math.pow((1 - this.constantPrepaymentRate),
					t - 1)
					* (pmtSum + this.constantPrepaymentRate
							* outstandingBalanceSum_t - (serviceFees + guaranteeFees)
							* outstandingBalanceSum_t_1);
			seriesCashflows.add(t + 1, seriescashflow_t);
		}// end time related for
			// System.out.print("\n" + seriesCashflows.toString() + "\n");

	}

	public void makePayment() {
		this.paymentCount++;
		this.currentPaymentAmount = this.seriesCashflows.get(paymentCount);
	}

	public void computePrice(double discountRate) {

		this.currentValue = AssetPricingAndValuation.computeNPV(discountRate
				/ this.paymentMultiplier, this.seriesCashflows,
				this.paymentCount, this.timeToMaturity);
		// computeNPV(double r, double pmt, int n) need to overload this method
		// to handle vectors
	}

	public void computeEffectiveDuration() {

		double basisChange = 0.01;
		double baseRate = 0.1;

		double mainPrice = AssetPricingAndValuation.computeNPV(baseRate
				/ this.paymentMultiplier, this.seriesCashflows,
				this.paymentCount, this.timeToMaturity);

		double Price_m100bps = AssetPricingAndValuation.computeNPV(
				(baseRate - basisChange) / this.paymentMultiplier,
				this.seriesCashflows, this.paymentCount, this.timeToMaturity);

		double Price_p100bps = AssetPricingAndValuation.computeNPV(
				(baseRate + basisChange) / this.paymentMultiplier,
				this.seriesCashflows, this.paymentCount, this.timeToMaturity);

		this.effectiveDuration = 0;

		this.effectiveDuration = (Price_m100bps - Price_p100bps)
				/ this.initialPrice
				* ((baseRate + basisChange) - (baseRate - basisChange));

		// computeNPV(double r, double pmt, int n) need to overload this method
		// to handle vectors
	}

	public void computeCurrentYield() {

		// System.out.println(this.weightedAverageCoupon);
		// System.out.println(this.initialPrice);
		// System.out.println(this.currentValue);

		this.yield = (this.weightedAverageCoupon * this.initialPrice)
				/ this.currentValue;

	}

	private void setCouponCountID() {
		int intWAM = (int) this.weightedAverageMaturity
				* this.paymentMultiplier;
		for (int i = 0; i <= intWAM; i++) {
			this.couponCountID.add(i, i);
		}
	}
	
	private void setPaymentMultiplier(int i) {
		// TODO Auto-generated method stub
		this.paymentMultiplier = i;
	}


}
