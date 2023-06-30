



public class Statement {

	private final int loanID;
	private final int bankID;
	private final int borrowerID;
	private final double paymentAmount;
	private final double principalComponent;
	private final double interestComponet;
	private double repayment;
	private StatementStatus status;
	private CashFlow cashflow;

	public Statement(int loanID, int bankID, int borrowerID,
			double paymentAmount, double principalComponent,
			double interestComponet) {
		super();
		this.loanID = loanID;
		this.bankID = bankID;
		this.borrowerID = borrowerID;
		this.paymentAmount = paymentAmount;
		this.principalComponent = principalComponent;
		this.interestComponet = interestComponet;
		this.status = StatementStatus.unpaided;
		repayment = 0;
	}

	@Override
	public String toString() {
		return "Statement [loanID = " + loanID + ", bankID = " + bankID
				+ ", borrowerID = " + borrowerID + ", paymentAmount = "
				+ paymentAmount + ", principalComponent = " + principalComponent
				+ ", interestComponet = " + interestComponet + ", status = " + status
				+ "]";
	}

	public int getLoanID() {
		return loanID;
	}

	public StatementStatus isPaid() {
		return status;
	}
	
	public void setStatus(boolean borrowerWillPay) {
		if(borrowerWillPay == true){
			this.status = StatementStatus.paid;
		}else{
			this.status = StatementStatus.unpaided;
		}
	}


	public int getBankID() {
		return bankID;
	}

	public int getBorrowerID() {
		return borrowerID;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public double getInterestComponet() {
		return interestComponet;
	}

	public double getPrincipalComponent() {
		return principalComponent;
	}

}
