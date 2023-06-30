
import java.util.ArrayList;

public class BasisTrading {
	
	double spread1, spread2, rateHedge, dailyFundingGain, tradeGain, totalTradePnL, TradeGain;
	static int maxTrancheIndex;

	ArrayList tradeOpenPosition, tradeClosePosition;
	double [][] trade;
	
	public BasisTrading(){
		tradeOpenPosition = new ArrayList();
		
		tradeClosePosition = new ArrayList();
	}
	
	public BasisTrading(double sp1, double sp2, double rH){
		this.spread1 = sp1;
		this.spread2 = sp2;
		this.rateHedge = rH;
		tradeOpenPosition = new ArrayList();
		
		tradeClosePosition = new ArrayList();

	}
	

}
