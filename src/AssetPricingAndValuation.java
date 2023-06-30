

import java.util.Vector;

public class AssetPricingAndValuation {

	// compute the net present value of monthly payments
	public static double computeNPV(double r, double pmt, int n) {
		double npv = pmt;
		double disc = 1.0 / (1.0 + r);
		for (int i = n - 1; i > 0; i--) {
			npv = pmt + disc * npv;
		}
		return npv;
	}

	public static double computeNPV(double discountRate,
			Vector<Double> seriesCashflows, int paymentCount, int timeToMaturity) {
		// TODO Auto-generated method stub
		Vector<Double> tempCF = new Vector<Double>();
		for(int t = paymentCount+1; t < seriesCashflows.size(); t++){
			tempCF.add(seriesCashflows.get(t));
		}
		double npv = 0;
		for(int i = 0; i < tempCF.size(); i++){
			npv += tempCF.get(i)/(Math.pow(1 + discountRate, i+1));
		}
		
		
		return npv;
	}

}
