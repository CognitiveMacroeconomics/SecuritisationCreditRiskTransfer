

import java.util.Random;
import java.util.Vector;



public class SPV {

	protected static int SPVIDs = 0;
	private final int spvID;

	private final Vector<RMBSSeries> rmbsIssued = new Vector<RMBSSeries>();
	private final Vector<Double> struct = new Vector<Double>();
	private final String sponsorName;
	private final int sponsorID;
	private final int sponsorRSSID;
	private PaymentSchedule paymentSchedule;
	private TrancheStructure trancheStructure;
	private int paymentMultiplier;
	private double classAprob;
	private double classBprob;
	private double classCprob;
	private double classZprob;

	public SPV(String spnrNme, int spnrID, int spnrRSSID,
			PaymentSchedule sched, TrancheStructure ts) {
		SPVIDs++;
		this.spvID = SPVIDs;

		this.sponsorName = spnrNme;
		this.sponsorID = spnrID;
		this.sponsorRSSID = spnrRSSID;
		this.paymentSchedule = sched;
		this.trancheStructure = ts;

		switch (this.paymentSchedule) {
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

		switch (this.trancheStructure) { // , ,
		case TS_100_0:
			this.struct.add(0, 0.9);
			this.struct.add(1, 0.10);
			this.struct.add(2, 0.0);
			this.struct.add(3, 0.0);

			setTranchingStructure(this.struct);
			break;

		case TS_80_15_5:
			this.struct.add(0, 0.8);
			this.struct.add(1, 0.15);
			this.struct.add(2, 0.0);
			this.struct.add(3, 0.05);
			setTranchingStructure(this.struct);
			break;

		case TS_90_10:
			this.struct.add(0, 0.9);
			this.struct.add(1, 0.10);
			this.struct.add(2, 0.0);
			this.struct.add(3, 0.0);

			setTranchingStructure(this.struct);
			break;

		case TS_75_10_10_5:
			this.struct.add(0, 0.75);
			this.struct.add(1, 0.10);
			this.struct.add(2, 0.10);
			this.struct.add(3, 0.05);

			setTranchingStructure(this.struct);
			break;

		}// end switch

	}
	
	
	public SPV(String spnrNme, int spnrID, int spnrRSSID) {
		SPVIDs++;
		this.spvID = SPVIDs;
		this.sponsorName = spnrNme;
		this.sponsorID = spnrID;
		this.sponsorRSSID = spnrRSSID;
	}


	@Override
	public String toString() {
		return "SPV [ SPV ID=" + spvID + ", Sponsor RSSID=" + sponsorRSSID
				+ ", Sponsor Name=" + sponsorName + ", Number of Issuances="
				+ rmbsIssued.size() + " ]";
	}

	private void setTranchingStructure(Vector<Double> struct2) {
		// TODO Auto-generated method stub

		this.classAprob = struct2.get(0);
		this.classBprob = struct2.get(1);
		this.classCprob = struct2.get(2);
		this.classZprob = struct2.get(3);

	}

	private void setPaymentMultiplier(int i) {
		// TODO Auto-generated method stub
		this.paymentMultiplier = i;
	}

	public void issueSeries(Vector<Loan> refPool,
			double grossOutstandingNotional, double cpr) {
		Random rand = new Random();
		double prob = rand.nextGaussian();
		SeriesNotesClass snc;

		if (prob >= this.classAprob) {
			snc = SeriesNotesClass.Class_A;
		} else if (this.classBprob <= prob && prob < this.classAprob) {
			snc = SeriesNotesClass.Class_B;
		} else if (this.classCprob <= prob && prob < this.classBprob) {
			snc = SeriesNotesClass.Class_C;
		} else {
			snc = SeriesNotesClass.Class_Z;
		}
		RMBSSeries series = new RMBSSeries(grossOutstandingNotional, refPool,
				snc, this.paymentMultiplier, cpr);

		this.rmbsIssued.add(series);
	}

	
	
	public void issueSeries(Vector<Loan> refPool, double mdReturn,
			double grossOutstandingNotional, PaymentSchedule sched) {
		/**
		 * public RMBSSeries(double currentValue, Vector<Loan> refPool,
			double mdReturn, SeriesNotesClass noteClass, int paymentMplier,
			PaymentSchedule sched)
		 */

		SeriesNotesClass snc;

		if (mdReturn <= 0.03) {
			snc = SeriesNotesClass.Class_AAA;
		} else if (0.04 <= mdReturn && mdReturn < 0.06) {
			snc = SeriesNotesClass.Class_A;
		} else if (0.06 <= mdReturn && mdReturn < 0.07) {
			snc = SeriesNotesClass.Class_BBB;
		} else if (0.07 <= mdReturn && mdReturn <= 0.1) {
			snc = SeriesNotesClass.Class_BB;
		}else if (0.11 <= mdReturn && mdReturn < 0.16) {
			snc = SeriesNotesClass.Class_B;
		}else {
			snc = SeriesNotesClass.Class_Z;
		}
		
		RMBSSeries series = new RMBSSeries(grossOutstandingNotional, 
				refPool, mdReturn, snc, sched);

		this.rmbsIssued.add(series);
	}

	
	
	public Vector<RMBSSeries> getRMBSIssued() {

		return this.rmbsIssued;
	}

}
