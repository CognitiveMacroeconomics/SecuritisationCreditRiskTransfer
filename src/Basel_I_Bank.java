

import java.util.Vector;



public class Basel_I_Bank extends Bank {

	/*
	 * private final int RSSID; private final double interestSpread; private
	 * final String bankName;
	 * 
	 * protected double averageRefAssetValue; protected double loanableFunds;
	 * protected double ltvRatio; protected double returnOnAssets; protected
	 * double returnOnLiabilities; protected int maturity; protected double
	 * securitisationRate;
	 * 
	 * protected double totalAssets; protected double totalLiabilities;
	 * protected double equityCaliptal; protected double tier_1_Capital;
	 * protected double onBalanceSheetDefaultRate; protected double
	 * offBalanceSheetDefaultRate; protected double securitisationCostFactor;
	 * protected double cashAssets_LoanableFunds;
	 */
	
	protected Vector<double[]> bankruptcyRangeForAssetLiabilityRatioCollection = new Vector<double[]>();


	
	public Basel_I_Bank(int IDRSSD, String bankName, double totalAssets,
			double totalLiabilities, Economy econ,
			Enviroment env,  GeoEconoPoliticalSpace world, double returnOnAssets,
			double returnOnLiabilities) {

		super(IDRSSD, bankName, totalAssets, totalLiabilities, econ, env, world, 
				returnOnAssets, returnOnLiabilities);

	}
	
	
	public Basel_I_Bank() {
		super();
	}

	public Basel_I_Bank(int IDRSSD, String bankName, double totalAssets,
			double totalLiabilities, double returnOnAssets,
			double returnOnLiabilities, double averageRefAssetValue,
			int maturity, double onBalanceSheetDefaultRate,
			double offBalanceSheetDefaultRate, double securitisationCostFactor) {

		super(IDRSSD, bankName, totalAssets, totalLiabilities, returnOnAssets,
				returnOnLiabilities, averageRefAssetValue, maturity,
				onBalanceSheetDefaultRate, offBalanceSheetDefaultRate,
				securitisationCostFactor);

	}
	
	
	public Basel_I_Bank(int IDRSSD, String bankName, double totalAssets,
			double totalLiabilities, double returnOnAssets,
			double returnOnLiabilities, double assetSurvivalRate, double securitisationCostFactor) {

		super(IDRSSD, bankName, totalAssets, totalLiabilities, returnOnAssets,
				returnOnLiabilities, assetSurvivalRate, securitisationCostFactor);

	}
	
	



	@Override
	public void securitise(double sigma1, double sigma2) {
		
		this.securitisationRate = Math.max(
				((1 - this.regulatoryCapitalRatio) * sigma2 - this.regulatoryCapitalRatio * sigma1 - (this.equityCaliptal
						* this.regulatoryCapitalRatio / this.totalAssets))
						/ (2 * this.regulatoryCapitalRatio * sigma1), 0.000001);


	}

	public void computeLiability() {

		this.totalLiabilities = (1 + this.returnOnLiabilities)
				* this.totalLiabilities; // +
											// liabMovement.nextDouble()*(liability);
	}

	/* test bankruptcy without securitization */
	public Vector<double[]> testBankruptcy(double survivalProbMin,
			double survivalProbMax, double assetReturnMin,
			double assetReturnMax, double regulatoryCapitalRatio) {
		/*
		 * This method runs through every survival probability between a defined
		 * range and checks at which net interest rate spread the bank will
		 * become bankrupt given its initial starting Asset/Liability Ratio
		 */
		double netInterestSpread = 0;
		double survivalProbability = 0;
		double[] bankruptcyRangeForAssetLiabilityRatio;
		bankruptcyRangeForAssetLiabilityRatio = new double[3];
		//Vector<double[]> bankruptcyRangeForAssetLiabilityRatioCollection = new Vector<double[]>();

		for (double m = survivalProbMin; m < survivalProbMax; m += 0.02) {
			for (double ra = assetReturnMin; ra < assetReturnMax; ra += 0.02) {

				for (double rl = 0.01; rl < ra; rl += 0.02) {
					if ((m * this.totalAssets + ra * m * this.totalAssets
							- this.totalAssets * (1 - regulatoryCapitalRatio)
							- this.totalLiabilities
							* (1 - regulatoryCapitalRatio) - this.totalLiabilities
							* (1 - rl)) < 0) {
						netInterestSpread = ra - rl;
						survivalProbability = m;

					}
				}
			}
			bankruptcyRangeForAssetLiabilityRatio[0] = this.totalAssets
					/ this.totalLiabilities;
			bankruptcyRangeForAssetLiabilityRatio[1] = netInterestSpread;
			bankruptcyRangeForAssetLiabilityRatio[2] = survivalProbability;
			bankruptcyRangeForAssetLiabilityRatioCollection
					.add(bankruptcyRangeForAssetLiabilityRatio);
		}

		return bankruptcyRangeForAssetLiabilityRatioCollection;
	}

	/** Decide whether to lend out money or not **/
	@Override
	public boolean canLend() {
		if (/* (Sim.getRnd().getIntFromTo(1,3)>=1)&& */this.equityCaliptal > this.tier_1_Capital) {
			return true;
		}
		return false;
	}

	
	//simAssetProcess number 1
	public void simAssetProcess(boolean boolsecuritize, boolean boolNonlinear, double regCapitalRatio, 
			double survivalRate, double AAAMBSCoupon, 
			double nonAAAMBSCoupon, double AAAProbability, double assetReturn, double liabExpense) {
		
		if(this.status == CorporateStatus.failed) {
			this.nolifyAll();
		}
		
		
		//compute end of current quarter equity value
		double asset = this.getTotalAssets();
		double liab  = this.getTotalLiabilities();
		this.setRegulatoryCapitalRatio(regCapitalRatio);
		double regCapRate = this.getRegulatoryCapitalRatio();
		this.setAssetSurvivalRate(survivalRate);
		double suvRate = this.getAssetSurvivalRate();
		this.setReturnOnAssets(assetReturn);
		this.setReturnOnLiabilities(liabExpense);
		double assetRtn = this.getReturnOnAssets();
		double liabExpns = this.getReturnOnLiabilities();
		this.setEquityCaliptal(asset - liab);
		double equity = this.getEquityCaliptal();
		
		
		double rnd = Math.random();
		double mbsCoupon;
		
		if(rnd < AAAProbability){
			mbsCoupon = AAAMBSCoupon;
		}else {
			mbsCoupon = nonAAAMBSCoupon;
		}

		
		this.setSigma1(suvRate, regCapRate, assetRtn);
		this.setSigma2(boolNonlinear, suvRate, regCapRate, mbsCoupon);
		
		this.testBankruptcy(regCapRate, asset, liab, equity);

//		System.out.println(equity);
//		System.out.println(asset);
//		System.out.println(equity/asset);

		
		this.securitise(boolsecuritize, boolNonlinear, sigma1, sigma2, regCapRate, asset, equity, mbsCoupon);
		
		double alpha = this.getSecuritisationRate();
		
		
//		this.setMdRateOfReturn(mbsCoupon);
		
		this.setMdRateOfReturn(mbsCoupon);
		
//		System.out.println("MBS Coupon: "+this.mdRateOfReturn);
		
		/**
		 * in this instance cash assets or loanable funds are set as the securitised amount because 
		 * I only want to model the loans that have been securitised and see the impact of defaults on 
		 * them
		 * Limiting this to only look at the securitised amounts reduces computer resources required
		 * for the calculations, by focusing on a smaller population of loans and borrowers
		 * 
		 * Note that only the loanable funds amount is set in the sim asset process method. The actual 
		 * creation of loans, borrowers, SPVs and MBS issuances is done in different methods 
		 * 
		 * NOTE: When creating loans remember that the bank loanable funds are in thousands, therefore
		 * you will need to multiple the loanable funds amount by 1000 before allocating across loans
		 * 
		 *  
		 */
		this.setCashAssets_LoanableFunds(alpha*asset);
//		System.out.println("loanable Funds: "+this.cashAssets_LoanableFunds);
		
		this.setCapitalRetentionOrinjection(boolNonlinear, alpha,  mbsCoupon, sigma1, sigma2, regCapRate, asset, equity);
		
		
		
		double inject = this.getCapitalRetentionOrinjection();
//		System.out.println(this.capitalRetentionOrinjection);
		
		this.setTotalLiabilities(liab*(1+liabExpns));
		
		double secCostMultiplier = this.getSecuritisationCost();
		
//		System.out.println(secCostMultiplier);
		
		this.setTotalAssets(assetRtn, asset, suvRate, mbsCoupon, secCostMultiplier, inject, alpha, this.environment.getBoolNonlinear());
//		System.out.println(this.getTotalAssets());
//		System.out.println(this.getTotalLiabilities());
		
		//this.report(asset, )
		
		
		
//		System.out
//				.println("===================================================");
//		System.out
//				.println("                                                   ");

	}
	
	
	//simAssetProcess number 2
	public void simAssetProcess(boolean boolsecuritize, boolean boolNonlinear, double regCapitalRatio, 
			double survivalRate, double AAAMBSCoupon, 
			double nonAAAMBSCoupon, double AAAProbability, double assetReturn, double liabExpense,
			double LAPFPayOut, double LAPFRegSolvMarg, double equityReturn, double investOpCost) {
		
		//compute end of current quarter equity value
		double asset = this.getTotalAssets();
		double liab  = this.getTotalLiabilities();
		this.setRegulatoryCapitalRatio(regCapitalRatio);
		double regCapRate = this.getRegulatoryCapitalRatio();
		this.setAssetSurvivalRate(survivalRate);
		double suvRate = this.getAssetSurvivalRate();
		this.setReturnOnAssets(assetReturn);
		this.setReturnOnLiabilities(liabExpense);
		double assetRtn = this.getReturnOnAssets();
		double liabExpns = this.getReturnOnLiabilities();
		this.setEquityCaliptal(asset - liab);
		double equity = this.getEquityCaliptal();
		
		
		double rnd = Math.random();
		double mbsCoupon;
		
		if(rnd < AAAProbability){
			mbsCoupon = AAAMBSCoupon;
		}else {
			mbsCoupon = nonAAAMBSCoupon;
		}

		
		this.setSigma1(suvRate, regCapRate, assetRtn);
		this.setSigma2(boolNonlinear, suvRate, regCapRate, mbsCoupon);
		
		this.testBankruptcy(regCapRate, asset, liab, equity);

		System.out.println(equity);
		System.out.println(asset);
		System.out.println(equity/asset);

		
		this.securitise(boolsecuritize, boolNonlinear, sigma1, sigma2, regCapRate, asset, equity, mbsCoupon);
		
		double alpha = this.getSecuritisationRate();
		
//		this.setMdRateOfReturn(mbsCoupon);
		
		this.setMdRateOfReturn(LAPFPayOut, LAPFRegSolvMarg, equityReturn, investOpCost, alpha, asset);
		
		System.out.println("MBS Coupon: "+this.mdRateOfReturn);
		
		/**
		 * in this instance cash assets or loanable funds are set as the securitised amount because 
		 * I only want to model the loans that have been securitised and see the impact of defaults on 
		 * them
		 * Limiting this to only look at the securitised amounts reduces computer resources required
		 * for the calculations, by focusing on a smaller population of loans and borrowers
		 * 
		 * Note that only the loanable funds amount is set in the sim asset process method. The actual 
		 * creation of loans, borrowers, SPVs and MBS issuances is done in different methods 
		 * 
		 * NOTE: When creating loans remember that the bank loanable funds are in thousands, therefore
		 * you will need to multiple the loanable funds amount by 1000 before allocating across loans
		 * 
		 *  
		 */
		this.setCashAssets_LoanableFunds(alpha*asset);
		System.out.println("loanable Funds: "+this.cashAssets_LoanableFunds);
		
		this.setCapitalRetentionOrinjection(boolNonlinear, alpha,  mbsCoupon, sigma1, sigma2, regCapRate, asset, equity);
		
		
		
		double inject = this.getCapitalRetentionOrinjection();
//		System.out.println(this.capitalRetentionOrinjection);
		
		this.setTotalLiabilities(liab*(1+liabExpns));
		
		double secCostMultiplier = this.getSecuritisationCost();
		
//		System.out.println(secCostMultiplier);
		
		this.setTotalAssets(assetRtn, asset, suvRate, mbsCoupon, secCostMultiplier, inject, alpha, this.environment.getBoolNonlinear());
//		this.setTotalAssets(assetRtn, asset, suvRate, secCostMultiplier, inject, alpha);
//		System.out.println(this.getTotalAssets());
//		System.out.println(this.getTotalLiabilities());
		
		//this.report(asset, )
		
		
		
		System.out
				.println("===================================================");
		System.out
				.println("                                                   ");

	}
	

	//duplicates simAssetProcess number 1
	public void simAssetProcessStep() {
		
		if(this.status == CorporateStatus.failed) {
			this.nolifyAll();
		}
		
		boolean boolsecuritize = this.environment.getBoolSecuritize();
		boolean boolNonlinear = this.environment.getBoolNonlinear();
		double regCapitalRatio = this.economy.getRegCapitalRatio();
		double survivalRate = this.economy.getSurvivalRate();
		double AAAMBSCoupon;
		double nonAAAMBSCoupon;
		double AAAProbability;
		//double assetReturn;
		//double liabExpense;
		
		
		//compute end of current quarter equity value
		double asset = this.getTotalAssets();
		double liab  = this.getTotalLiabilities();
		this.setRegulatoryCapitalRatio(regCapitalRatio);
		double regCapRate = this.getRegulatoryCapitalRatio();
		this.setAssetSurvivalRate(survivalRate);
		double suvRate = this.getAssetSurvivalRate();
//		this.setReturnOnAssets(this.economy.getAssetReturn());
//		this.setReturnOnLiabilities(liabExpense);
		double assetRtn = this.getReturnOnAssets();
		double liabExpns = this.getReturnOnLiabilities();
		this.setEquityCaliptal(asset - liab);
		double equity = this.getEquityCaliptal();
		
		
		double rnd = Math.random();
		double mbsCoupon;
		
		if(rnd < this.economy.getAAAProbability()){
			mbsCoupon = this.economy.getAAAMBSCoupon();
		}else {
			mbsCoupon = this.economy.getNonAAAMBSCoupon();
		}

		
		this.setSigma1(suvRate, regCapRate, assetRtn);
		this.setSigma2(boolNonlinear, suvRate, regCapRate, mbsCoupon);
		
		this.testBankruptcy(regCapRate, asset, liab, equity);

//		System.out.println(equity);
//		System.out.println(asset);
//		System.out.println(equity/asset);

		
		this.securitise(boolsecuritize, boolNonlinear, sigma1, sigma2, regCapRate, asset, equity, mbsCoupon);
		
		double alpha = this.getSecuritisationRate();
		
		
//		this.setMdRateOfReturn(mbsCoupon);
		
		this.setMdRateOfReturn(mbsCoupon);
		
//		System.out.println("MBS Coupon: "+this.mdRateOfReturn);
		
		/**
		 * in this instance cash assets or loanable funds are set as the securitised amount because 
		 * I only want to model the loans that have been securitised and see the impact of defaults on 
		 * them
		 * Limiting this to only look at the securitised amounts reduces computer resources required
		 * for the calculations, by focusing on a smaller population of loans and borrowers
		 * 
		 * Note that only the loanable funds amount is set in the sim asset process method. The actual 
		 * creation of loans, borrowers, SPVs and MBS issuances is done in different methods 
		 * 
		 * NOTE: When creating loans remember that the bank loanable funds are in thousands, therefore
		 * you will need to multiple the loanable funds amount by 1000 before allocating across loans
		 * 
		 *  
		 */
		this.setCashAssets_LoanableFunds(alpha*asset);
//		System.out.println("loanable Funds: "+this.cashAssets_LoanableFunds);
		
		this.setCapitalRetentionOrinjection(boolNonlinear, alpha,  mbsCoupon, sigma1, sigma2, regCapRate, asset, equity);
		
		
		
		double inject = this.getCapitalRetentionOrinjection();
//		System.out.println(this.capitalRetentionOrinjection);
		
		this.setTotalLiabilities(liab*(1+liabExpns));
		
		double secCostMultiplier = this.getSecuritisationCost();
		
//		System.out.println(secCostMultiplier);
		
		this.setTotalAssets(assetRtn, asset, suvRate, mbsCoupon, secCostMultiplier, inject, alpha, this.environment.getBoolNonlinear());
//		this.setTotalAssets(assetRtn, asset, suvRate, secCostMultiplier, inject, alpha);
//		System.out.println(this.getTotalAssets());
//		System.out.println(this.getTotalLiabilities());
		
		//this.report(asset, )
		
		
		
//		System.out
//				.println("===================================================");
//		System.out
//				.println("                                                   ");

	}
	
	//duplication of simAssetProcess number 2 for use in Repast
	public void step(boolean boolsecuritize, boolean boolNonlinear, double regCapitalRatio, 
			double survivalRate, double AAAMBSCoupon, 
			double nonAAAMBSCoupon, double AAAProbability, double assetReturn, double liabExpense,
			double LAPFPayOut, double LAPFRegSolvMarg, double equityReturn, double investOpCost) {
		
		//compute end of current quarter equity value
		double asset = this.getTotalAssets();
		double liab  = this.getTotalLiabilities();
		this.setRegulatoryCapitalRatio(regCapitalRatio);
		double regCapRate = this.getRegulatoryCapitalRatio();
		this.setAssetSurvivalRate(survivalRate);
		double suvRate = this.getAssetSurvivalRate();
		this.setReturnOnAssets(assetReturn);
		this.setReturnOnLiabilities(liabExpense);
		double assetRtn = this.getReturnOnAssets();
		double liabExpns = this.getReturnOnLiabilities();
		this.setEquityCaliptal(asset - liab);
		double equity = this.getEquityCaliptal();
		
		
		double rnd = Math.random();
		double mbsCoupon;
		
		if(rnd < AAAProbability){
			mbsCoupon = AAAMBSCoupon;
		}else {
			mbsCoupon = nonAAAMBSCoupon;
		}

		
		this.setSigma1(suvRate, regCapRate, assetRtn);
		this.setSigma2(boolNonlinear, suvRate, regCapRate, mbsCoupon);
		
		this.testBankruptcy(regCapRate, asset, liab, equity);

		System.out.println(equity);
		System.out.println(asset);
		System.out.println(equity/asset);

		
		this.securitise(boolsecuritize, boolNonlinear, sigma1, sigma2, regCapRate, asset, equity, mbsCoupon);
		
		double alpha = this.getSecuritisationRate();
		
//		this.setMdRateOfReturn(mbsCoupon);
		
		this.setMdRateOfReturn(LAPFPayOut, LAPFRegSolvMarg, equityReturn, investOpCost, alpha, asset);
		
		System.out.println("MBS Coupon: "+this.mdRateOfReturn);
		
		/**
		 * in this instance cash assets or loanable funds are set as the securitised amount because 
		 * I only want to model the loans that have been securitised and see the impact of defaults on 
		 * them
		 * Limiting this to only look at the securitised amounts reduces computer resources required
		 * for the calculations, by focusing on a smaller population of loans and borrowers
		 * 
		 * Note that only the loanable funds amount is set in the sim asset process method. The actual 
		 * creation of loans, borrowers, SPVs and MBS issuances is done in different methods 
		 * 
		 * NOTE: When creating loans remember that the bank loanable funds are in thousands, therefore
		 * you will need to multiple the loanable funds amount by 1000 before allocating across loans
		 * 
		 *  
		 */
		this.setCashAssets_LoanableFunds(alpha*asset);
		System.out.println("loanable Funds: "+this.cashAssets_LoanableFunds);
		
		this.setCapitalRetentionOrinjection(boolNonlinear, alpha,  mbsCoupon, sigma1, sigma2, regCapRate, asset, equity);
		
		
		
		double inject = this.getCapitalRetentionOrinjection();
//		System.out.println(this.capitalRetentionOrinjection);
		
		this.setTotalLiabilities(liab*(1+liabExpns));
		
		double secCostMultiplier = this.getSecuritisationCost();
		
//		System.out.println(secCostMultiplier);
		
		this.setTotalAssets(assetRtn, asset, suvRate, mbsCoupon, secCostMultiplier, inject, alpha, this.environment.getBoolNonlinear());
//		this.setTotalAssets(assetRtn, asset, suvRate, secCostMultiplier, inject, alpha);
//		System.out.println(this.getTotalAssets());
//		System.out.println(this.getTotalLiabilities());
		
		//this.report(asset, )
		
		
		
		System.out
				.println("===================================================");
		System.out
				.println("                                                   ");

	}
	
	private void setMdRateOfReturn(double lAPFPayOut, double lAPFRegSolvMarg,
			double equityReturn, double investOpCost, double secRate, double tAssets) {
		// TODO Auto-generated method stub
		
		double Re = equityReturn;
		double sigma = investOpCost;
		double C = lAPFPayOut;
		double rho = lAPFRegSolvMarg;
		double alpha = secRate;
		double A = tAssets;
		
		this.mdRateOfReturn = Re + 2*sigma*((alpha*A)/(C*(1+rho)));
		
	}

//	protected void setTotalAssets(double assetRtn, double asset, double suvRate,
//			double secCostMultiplier, double inject, double alpha) {
//		// TODO Auto-generated method stub
//		this.totalAssets = suvRate*(1-alpha)*asset+ alpha*asset + 
//				assetRtn*suvRate*asset - inject - secCostMultiplier*asset;
//	}

//	protected void setCapitalRetentionOrinjection(boolean boolNonlinear, double alpha, double mbsCoupon, double sigma1,
//			double sigma2, double regCapRate, double asset, double equity) {
//		// TODO Auto-generated method stub
////		double recursivityMultiplier;
////		if(boolNonlinear == true) {
////			recursivityMultiplier = sigma1 + alpha*sigma2 - alpha*mbsCoupon;
////		} else {
////			recursivityMultiplier = sigma1 + alpha*sigma2;
////		}
//		this.capitalRetentionOrinjection = regCapRate*(1-alpha)*asset - equity;
//	}

	private void securitise(boolean boolsecuritize, boolean boolNonlinear,
			double sigma1, double sigma2, double regCapRate, double asset, double equity, double coupon) {
		// TODO Auto-generated method stub
		double alpha;
		double secCost;
		
		if(this.status == CorporateStatus.failed) {
			this.nolifyAll();
		}
		
		
		double sqrtFirstPart = Math.pow((regCapRate*sigma2 - coupon + coupon*regCapRate),2);
		double sqrtSecondPart = 3*coupon*regCapRate*(sigma2 + regCapRate*(sigma1 - sigma2)+ (equity/asset));
		double denominator = 3*coupon*regCapRate;
		
		if(boolsecuritize == true && boolNonlinear == true){
			alpha = (regCapRate*sigma2 - coupon + coupon*regCapRate 
					- Math.sqrt(sqrtFirstPart + sqrtSecondPart))/denominator;
			/**
			 * Note Alpha will always be large and negative. The securitisation model formula does not return fractions/decimal
			 * but instead whole numbers. Therefore, when setting the securitisation rate to be used in the capital accumulation and asset
			 * accumulation process, the alpha value should be divided by 100 and multiplied by -1.
			 * Hence, secRate = -1*alpha/100
			 */
			this.setSecuritisationCost((-1*alpha/100)*coupon + Math.pow((-1*alpha/100), 2)*coupon);
		}
		else if(boolsecuritize == true && boolNonlinear == false){
			alpha = ((1- regCapRate)*sigma2 - regCapRate*sigma1 - regCapRate* (equity/asset)) 
					/(2*sigma2*regCapRate);
			/**
			 * Note Alpha will always be large and negative. The securitisation model formula does not return fractions/decimal
			 * but instead whole numbers. Therefore, when setting the securitisation rate to be used in the capital accumulation and asset
			 * accumulation process, the alpha value should be divided by 100 and multiplied by -1.
			 * Hence, secRate = -1*alpha/100
			 */
			this.setSecuritisationCost((-1*alpha/100)*coupon);
		} else{
			alpha = 0.0;
		}
//		System.out.println("==============="+ alpha + "==================");
		/**
		 * Note Alpha will always be large and negative. The securitisation model formula does not return fractions/decimal
		 * but instead whole numbers. Therefore, when setting the securitisation rate to be used in the capital accumulation and asset
		 * accumulation process, the alpha value should be divided by 100 and multiplied by -1.
		 * Hence, secRate = -1*alpha/100
		 */
		this.setSecuritisationRate(-1*alpha/100);
		this.setSecuritisationIssuanceTotalNotional(asset*(-1*alpha/100));
//		System.out.println("==============="+ this.securitisationRate + "==================");
		
	}

//	protected void nolifyAll() {
//		// TODO Auto-generated method stub
//		this.setTotalAssets(0.0);
//		this.setTotalLiabilities(0.0);
//		this.setEquityCaliptal(0.0);
//		this.setAssetSurvivalRate(0.0);
//		this.setCashAssets_LoanableFunds(0.0);
//		this.setReturnOnAssets(0.0);
//		this.setReturnOnLiabilities(0.0);
//		this.setReturnOnEquity(0.0);
//	}

	private void setSigma2(boolean boolNonlinear, double suvRate, double regCapRate, double coupon) {
		// TODO Auto-generated method stub
		if(boolNonlinear == true){
			this.sigma2 = (1-(suvRate - regCapRate)); //equation 12
		}else{
			this.sigma2 = (1-(suvRate - regCapRate)) - coupon;
		}
	}

	private void setSigma1(double suvRate, double regCapRate, double assetRtn) {
		// TODO Auto-generated method stub
		this.sigma1 = ((suvRate - regCapRate) + suvRate*assetRtn); //equation 12
	}
	

//	private void testBankruptcy(double capReqRate, double asset, double liab, double equity) {
//		// TODO Auto-generated method stub
//		
//		double capReq = capReqRate*asset;
//		
//		
//		if (asset > liab && equity >= capReq) {
////			System.out.println(" Bank is Solvent  !! ");
//			
//			
//			//this section of code is to keep a record of spreads, survival probabilities
//			double[] bankruptcyRangeForAssetLiabilityRatio;
//			bankruptcyRangeForAssetLiabilityRatio = new double[2];
//			bankruptcyRangeForAssetLiabilityRatio[0] = this.returnOnAssets - this.returnOnLiabilities;
//			bankruptcyRangeForAssetLiabilityRatio[1] = this.onBalanceSheetDefaultRate;
//			bankruptcyRangeForAssetLiabilityRatioCollection.add(bankruptcyRangeForAssetLiabilityRatio);
//			
//			this.status = CorporateStatus.solvent;
//
//		} else if (asset > liab && equity < capReq) {
////			System.out.println(" Bank is Solvent but requires Capital Injection  !! ");
//			this.status = CorporateStatus.equity_issuance;
//			} 
//		else if (equity < 0) {
////			System.out.println(" Bank is Bankrupt  !! ");
//			this.status = CorporateStatus.failed;// condition for LAPF estimation
//			this.nolifyAll();
//		}
//
//	}
	

	private void testBankruptcy(double capReq, double sigma1, double sigma2, boolean boolsecuritize) {
		// TODO Auto-generated method stub
		if (this.totalAssets > this.totalLiabilities && this.equityCaliptal > capReq) {
//			System.out.println(this.getBankName() + " Bank is Solvent  !! ");
			double[] bankruptcyRangeForAssetLiabilityRatio;
			bankruptcyRangeForAssetLiabilityRatio = new double[3];
			
			bankruptcyRangeForAssetLiabilityRatio[0] = 
				bankruptcyRangeForAssetLiabilityRatio[1] = this.returnOnAssets - this.returnOnLiabilities;
			bankruptcyRangeForAssetLiabilityRatio[2] = this.onBalanceSheetDefaultRate;
			bankruptcyRangeForAssetLiabilityRatioCollection.add(bankruptcyRangeForAssetLiabilityRatio);
			this.status = CorporateStatus.solvent;

		} else if (this.totalAssets > this.totalLiabilities && this.equityCaliptal < capReq) {
//			System.out.println(this.getBankName() + " Bank is Solvent but requires Capital Injection  !! ");
			if (!boolsecuritize) {
				this.securitisationRate = 0;
				this.status = CorporateStatus.equity_issuance;
			} else {
				/*
				 * if securitization is involved, recompute alpha for next
				 * period
				 */
				this.securitise(sigma1, sigma2);
				this.status = CorporateStatus.securitisation;
				//this.secP.setRefValue(this.secP.getRefValue() + alpha * asset);
//				System.out.println("New alpha for Bank Agent(" + (this.getBankID())	+ ")=" + this.securitisationRate);
			}
		}

		else if (this.equityCaliptal < 0) {
//			System.out.println(this.getBankName() + " Bank is Bankrupt  !! ");
			this.status = CorporateStatus.failed;// condition for LAPF estimation
		}

	}

	@Override
	public void securitise() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testBankruptcy() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void simAssetProcess() {
		// TODO Auto-generated method stub
		
	}

    /**
     * The following two methods are the abstract methods listed in the EconomicAgent abstract class
     * They can be used for more specific things such as setting the agents color etc
     */
	@Override
	public void checkLife() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void reproduce() {
		// TODO Auto-generated method stub
		
	}
}
