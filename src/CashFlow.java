



public class CashFlow implements Comparable {
	

	private int id;
	private int state; //(1 for in-the-black, 2 for defaulted)
	private double repayment;
	private double interestComp;
	private double princComp;
	private int volume;
	
	public CashFlow(int id, int sate, double repayment) {
		this.id = id;
		this.state = state;
		this.repayment = repayment;
		
	}
	
	public CashFlow(int id, int sate, double interestComp, double princComp) {
		this.id = id;
		this.state = state;
		this.interestComp = interestComp;
		this.princComp = princComp;
		this.repayment = this.interestComp+this.princComp;
		
	}


	public int getId() {
		return id;
	}

	public void setId(char id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getRepayment() {
		return repayment;
	}

	public void setRepayment(double repayment) {
		this.repayment = repayment;
	}
	
	public double getInterestComp() {
		return interestComp;
	}

	public double getPrincComp() {
		return princComp;
	}
		
	@Override
	public int compareTo(Object o) {
		CashFlow x=(CashFlow) o;
		if(this.repayment<x.repayment)
			return -1;
		else if(this.repayment>x.repayment)
			return 1;
		else return 0;
	}


}
