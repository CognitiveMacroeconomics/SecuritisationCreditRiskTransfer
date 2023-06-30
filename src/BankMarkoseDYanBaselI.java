

import jas.engine.Sim;

import org.jfree.data.xy.XYSeries;




public class BankMarkoseDYanBaselI extends Bank {
	
	
	private boolean action_Securitised;	
	private boolean action_SimProcessed = false;
	private int currentTime = 0;
	private double initialAssets;
	private double initialLiabilities;
	private double initialEquity;
	private double periodInternalCalculationCapInjection;
	private double periodInternalCalculationMax;
	
	
//	public JFreeXYChartOutputter capInjAndSecOutputer;
	
	
	
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<REQUIRED BANK METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public BankMarkoseDYanBaselI(int IDRSSD, String bankName,
			double totalAssets, double totalLiabilities, Economy econ,
			Enviroment env, GeoEconoPoliticalSpace world, double bankAssetReturn,
			double bankLiabilityExpense) {
		// TODO Auto-generated constructor stub
		
		super(IDRSSD, bankName, totalAssets, totalLiabilities, econ, env, world,bankAssetReturn, bankLiabilityExpense);
		this.initialAssets = totalAssets;
		this.initialLiabilities = totalLiabilities;
		this.initialEquity = this.initialAssets - this.initialLiabilities;
		this.optimalSecuritisation = new XYSeries(bankName + " Optimal Securitisation");
		
	}

	
	@Override
//	@ScheduledMethod(start = 1, interval = 1, priority = 5)
	public void simAssetProcess() {
		// TODO Auto-generated method stub
//		System.out.println("first-bank");
		//compute end of current quarter equity value
		//this.action_Securitised = false;
		
		if(this.status != CorporateStatus.failed){
			this.testBankruptcy(this.regulatoryCapitalRatio, this.getTotalAssets(), this.getTotalLiabilities(),
				(this.getTotalAssets() - this.getTotalLiabilities()));
//			System.out.println("Coporate Status = "+this.status );
		}

		
		double asset = this.getTotalAssets();
		double liab  = this.getTotalLiabilities();
		this.setRegulatoryCapitalRatio(Economy.regCapitalRatio);
		double regCapRate = this.getRegulatoryCapitalRatio();
		this.setAssetSurvivalRate(Economy.survivalRate);
		double suvRate = this.getAssetSurvivalRate();
		this.setReturnOnAssets(this.returnOnAssets);
		this.setReturnOnLiabilities(this.returnOnLiabilities);
//		System.out.println(securitisationRate);
		double assetRtn = this.getReturnOnAssets();
		double liabExpns = this.getReturnOnLiabilities();
		this.setEquityCaliptal(asset - liab);
		double equity = this.getEquityCaliptal();
		double mbsDemandT_m_1;
		boolean isQuadraticSecCost = !this.environment.getBoolNonlinear();
		
		
		double rnd = Math.random();
		double mbsCoupon = this.returnOnAssets;
		
//		if(rnd < economy.AAA_Rated_MBS_Probability){
//			mbsCoupon = economy.AAA_Rated_MBS_Coupon;
//		}else {
//			mbsCoupon = economy.nonAAA_Rated_MBS_Coupon;
//		}

		
		this.setSigma1(suvRate, regCapRate, assetRtn);
		this.setSigma2(suvRate, regCapRate);
		if(this.environment.getInternalSecuritise() == true){
			/**
			 * this sets the securitisation rate based on a hill climbing type approach with a 5yr period decision horizon 
			 * Note that the securitisation decision takes place at time T = 1 and the securitisation rate 
			 * defined at this time T= 1 is used throughout the 5yr period.
			 * Hence the if(Sim.getAbsoluteTime() == 1){do this} 
			 */
			if(Sim.getAbsoluteTime() == 1){
				//this.securitiseHillClimbing(this.sigma1, this.sigma2, this.environment.getBoolNonlinear(),
					//	this.environment.getSecuritisationRateDecisionHorizon());
				
				if(this.environment.getMultiPeriodAnalysis() == false){
					
					this.setFocusBankDecisionOptimalSecuritisationConstantQ();
					
				} 
				else{
					this.setFocusBankDecisionOptimalSecuritisationARM();
				}
//				System.out.println("Sec Results Size: "+this.focusBankDecisionOptimalSecuritisation.size());
				
				/**
				 * optimalSecuritisationResults.add(optimalSecCapitalAccumulationArray);
				 * optimalSecuritisationResults.add(optimalSecRateArray);
				 * optimalSecuritisationResults.add(optimalAlpha);
				 * optimalSecuritisationResults.add(optimalSecuritisation);
				 */
				
				this.focusBankOptimalSecCapitalAccumulationArray = (double[]) this.focusBankDecisionOptimalSecuritisation.get(0);
				this.focusBankOptimalSecRateArray = (double[]) this.focusBankDecisionOptimalSecuritisation.get(1);
				this.securitisationRate = (double) this.focusBankDecisionOptimalSecuritisation.get(2);
				//this.setSecuritisationRate(optimalAlpha);
				
				/**
				 * securitiseHillClimbing(double sigma1, double sigma2, boolean securitiseLinear,
				 * int decisionHorizon, String bankName, double assets, double liabilities, Economy economy, Enviroment environment,
				 * double assetSurvivalRate, double securitisationCostConstantFactor, double returnOnAssets, 
				 * double returnOnLiabilities, double regulatoryCapitalRatio)
				 */
				double snrTrancheSurvivalRate = 1-this.economy.getSeniourTrancheDefaultRate();
				double mezTrancheSurvivalRate = 1-this.economy.getMezzTrancheDefaultRate();
				double jnrTrancheSurvivalRate = 1-this.economy.getJuniourTrancheDefaultRate();
				
				double snrTrancheCoupon = this.economy.getSeniourTrancheCoupon();
				double mezTrancheCoupon = this.economy.getMezzTrancheCoupon();
				double jnrTrancheCoupon = this.economy.getJuniourTrancheCoupon();	
				
				if(this.environment.getMultiPeriodAnalysis() == false){
					
					this.seniorTrancheSecuritisation = SecuritisationEngine.
							securitiseHillClimbingConstantQ(this.environment.getBoolNonlinear(),
									this.environment.getSecuritisationRateDecisionHorizon(), 
									this.getBankName(), this.getTotalAssets(), this.getTotalLiabilities(), this.economy, this.environment, 
									snrTrancheSurvivalRate,snrTrancheCoupon, snrTrancheCoupon, snrTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio);
					
					this.mezzTrancheSecuritisation = SecuritisationEngine.
							securitiseHillClimbingConstantQ(this.environment.getBoolNonlinear(),
									this.environment.getSecuritisationRateDecisionHorizon(), 
									this.getBankName(), this.getTotalAssets(), this.getTotalLiabilities(), this.economy, this.environment, 
									mezTrancheSurvivalRate, mezTrancheCoupon, mezTrancheCoupon, mezTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio);
					
					this.juniorTrancheSecuritisation = SecuritisationEngine.
							securitiseHillClimbingConstantQ(this.environment.getBoolNonlinear(),
									this.environment.getSecuritisationRateDecisionHorizon(), 
									this.getBankName(), this.getTotalAssets(), this.getTotalLiabilities(), this.economy, this.environment, 
									jnrTrancheSurvivalRate,jnrTrancheCoupon, jnrTrancheCoupon, jnrTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio);

					
				} 
				else{
					
					this.seniorTrancheSecuritisation = SecuritisationEngine.
							securitiseHillClimbingTimeVariantX(this.environment.getBoolNonlinear(),
									this.environment.getSecuritisationRateDecisionHorizon(), this.economy.getLoanResetPeriod(), 
									this.getBankName(), this.getTotalAssets(), this.getTotalLiabilities(), this.economy, this.environment, 
									snrTrancheSurvivalRate,snrTrancheCoupon, snrTrancheCoupon, snrTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio,
									snrTrancheSurvivalRate,snrTrancheCoupon, snrTrancheCoupon, snrTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio);
					
					this.mezzTrancheSecuritisation = SecuritisationEngine.
							securitiseHillClimbingTimeVariantX(this.environment.getBoolNonlinear(),
									this.environment.getSecuritisationRateDecisionHorizon(), this.economy.getLoanResetPeriod(), 
									this.getBankName(), this.getTotalAssets(), this.getTotalLiabilities(), this.economy, this.environment, 
									mezTrancheSurvivalRate,mezTrancheCoupon, mezTrancheCoupon, mezTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio,
									mezTrancheSurvivalRate,mezTrancheCoupon, mezTrancheCoupon, mezTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio);
					
					this.juniorTrancheSecuritisation = SecuritisationEngine.
							securitiseHillClimbingTimeVariantX(this.environment.getBoolNonlinear(),
									this.environment.getSecuritisationRateDecisionHorizon(), this.economy.getLoanResetPeriod(),
									this.getBankName(), this.getTotalAssets(), this.getTotalLiabilities(), this.economy, this.environment, 
									jnrTrancheSurvivalRate,jnrTrancheCoupon, jnrTrancheCoupon, jnrTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio,
									jnrTrancheSurvivalRate,jnrTrancheCoupon, jnrTrancheCoupon, jnrTrancheCoupon, 
									this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio);

				}
				
				this.focusBankOptimalSecCapitalAccumulationArraySnrTranche = (double[]) this.seniorTrancheSecuritisation.get(0);
				this.focusBankOptimalSecRateArraySnrTranche = (double[]) this.seniorTrancheSecuritisation.get(1);
				this.securitisationRateSnrTranche = (double) this.seniorTrancheSecuritisation.get(2);
				
				this.focusBankOptimalSecCapitalAccumulationArrayMezTranche = (double[]) this.mezzTrancheSecuritisation.get(0);
				this.focusBankOptimalSecRateArrayMezTranche = (double[]) this.mezzTrancheSecuritisation.get(1);
				this.securitisationRateMezTranche = (double) this.mezzTrancheSecuritisation.get(2);
				
				this.focusBankOptimalSecCapitalAccumulationArrayJnrTranche = (double[]) this.juniorTrancheSecuritisation.get(0);
				this.focusBankOptimalSecRateArrayJnrTranche = (double[]) this.juniorTrancheSecuritisation.get(1);
				this.securitisationRateJnrTranche = (double) this.juniorTrancheSecuritisation.get(2);
			
				if(this.learningBank){
					mbsDemandT_m_1  = this.economy.getAggregateFundMBSAllocationUpdate();
					/**
					 * public void conductHeurisiticSearch(double assets, double liabilities, double contractRate, 
					 * double depositRate,	double suvRate, double regRate, double demandTm1, boolean quadraticSecCost)
					 */
					this.optimalSecuritisationIntermediateHueristic.conductHeurisiticSearch(asset, liab, assetRtn, 
							liabExpns,	suvRate, regCapRate, mbsDemandT_m_1, isQuadraticSecCost);
					
					this.setSecuritisationRate(this.optimalSecuritisationIntermediateHueristic
							.getOptimalSecuritisationRate());
				}
				
			}
		} else{
			if(this.learningBank){
				mbsDemandT_m_1  = this.economy.getAggregateFundMBSAllocationUpdate();
				/**
				 * public void conductHeurisiticSearch(double assets, double liabilities, double contractRate, 
				 * double depositRate,	double suvRate, double regRate, double demandTm1, boolean quadraticSecCost)
				 */
				this.optimalSecuritisationIntermediateHueristic.conductHeurisiticSearch(asset, liab, assetRtn, 
						liabExpns,	suvRate, regCapRate, mbsDemandT_m_1, isQuadraticSecCost);
				
				this.setSecuritisationRate(this.optimalSecuritisationIntermediateHueristic
						.getOptimalSecuritisationRate());
			} else{
				this.setSecuritisationRate(economy.getGlobalSecuritisationRate());
			}
		}
			
		double alpha = this.securitisationRate;
		
		this.IssueMBSNotes(alpha*this.totalAssets);

		if(alpha > 0.0){
			this.action_Securitised = true;
		}
		
		
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
		
		if(alpha >0 ){
			setCashAssets_LoanableFunds(alpha*asset);
		} else {
			
			setCashAssets_LoanableFunds(this.assetSurvivalRate*this.totalAssets*(this.returnOnAssets - this.returnOnLiabilities));
		}
//		System.out.println("loanable Funds: "+this.cashAssets_LoanableFunds);
		
		this.setCapitalRetentionOrinjection(environment.getBoolSecuritize(), alpha,  mbsCoupon, sigma1, sigma2, regCapRate, asset, equity);
		
		
		
		double inject = -1*this.getCapitalRetentionOrinjection();//multiply by -1 because getCapitalRetentionOrinjection returns -M which is the 
		//proxy for bank profits and not the capital injection. i.e. it is the capital accumulation
//		System.out.println(this.capitalRetentionOrinjection);
		
		this.setReturnOnEquity(this.getCapitalRetentionOrinjection());
		
		this.setTotalLiabilities(liab*(1+liabExpns));
		
		double secCostMultiplier = this.getSecuritisationCost();
		
//		System.out.println(secCostMultiplier);
		
		setTotalAssets(assetRtn, asset, suvRate, assetRtn, secCostMultiplier, inject, alpha, this.environment.getBoolNonlinear());
		
		this.action_SimProcessed = true;
		
	}

	
	public void simCapitalProcess(){
		double asset = this.getTotalAssets();
		double liab  = this.getTotalLiabilities();
		this.setRegulatoryCapitalRatio(Economy.regCapitalRatio);
		double regCapRate = this.getRegulatoryCapitalRatio();
		this.setAssetSurvivalRate(Economy.survivalRate);
		double suvRate = this.getAssetSurvivalRate();
		this.setReturnOnAssets(this.returnOnAssets);
		this.setReturnOnLiabilities(this.returnOnLiabilities);
//		System.out.println(securitisationRate);
		double assetRtn = this.getReturnOnAssets();
		double liabExpns = this.getReturnOnLiabilities();
		this.setEquityCaliptal(asset - liab);
		double equity = this.getEquityCaliptal();
		
		double marketDemandImpliedYield = 0.0987;
		double levelOfDemand = this.economy.getAggregateFundMBSAllocationUpdate();
		this.setSigma1(suvRate, regCapRate, assetRtn);
		this.setSigma2(suvRate, regCapRate);
		
		if(this.status != CorporateStatus.failed){
			this.testBankruptcy(this.regulatoryCapitalRatio, this.getTotalAssets(), this.getTotalLiabilities(),
				(this.getTotalAssets() - this.getTotalLiabilities()));
//			System.out.println("Coporate Status = "+this.status );
		}


		
		if(levelOfDemand  < 0.35){
			marketDemandImpliedYield = 0.15;
		}
		else if((0.35 <= levelOfDemand) && (levelOfDemand <= 0.45)){
			marketDemandImpliedYield = 0.0987;
		} else {
			marketDemandImpliedYield = 0.044;
		}
		
		this.setCapitalRetentionOrinjection(environment.getBoolSecuritize(), this.securitisationRate,  
				marketDemandImpliedYield, sigma1, sigma2, regCapRate, asset, equity);
		
		this.setTotalMBSIssuance(this.securitisationRate*this.totalAssets);
		
		double inject = -1*this.getCapitalRetentionOrinjection();//multiply by -1 because getCapitalRetentionOrinjection returns -M which is the 
		//proxy for bank profits and not the capital injection. i.e. it is the capital accumulation
//		System.out.println(this.capitalRetentionOrinjection);
		
		this.setReturnOnEquity(this.getCapitalRetentionOrinjection());
		
		this.setTotalLiabilities(liab*(1+liabExpns));
		
		double secCostMultiplier = this.getSecuritisationCost();
		
//		System.out.println(secCostMultiplier);
		
		setTotalAssets(assetRtn, asset, suvRate, assetRtn, secCostMultiplier, inject, this.securitisationRate, 
				this.environment.getBoolNonlinear());

	}


	private void setFocusBankDecisionOptimalSecuritisationConstantQ() {
		// TODO Auto-generated method stub
		/**
		 * (boolean securitiseLinear,
int decisionHorizon, String bankName, double assets, double liabilities, Economy economy, Enviroment environment,
double assetSurvivalRate, double linearSecuritisationCostConstantFactor, double nonLinearSecuritisationCostConstantFactor, 
double mbsCoupon, double returnOnAssets, double returnOnLiabilities, double regulatoryCapitalRatio)
		 */
		this.focusBankDecisionOptimalSecuritisation = SecuritisationEngine.securitiseHillClimbingConstantQ(
				this.environment.getBoolNonlinear(),
				this.environment.getSecuritisationRateDecisionHorizon(), 
				this.getBankName(), this.getTotalAssets(), this.getTotalLiabilities(), this.economy, this.environment, 
				this.economy.getSurvivalRate() ,this.economy.getSecuritisationCostConstantFactor(), 
				this.economy.getSecuritisationCostConstantFactor(), this.economy.getSecuritisationCostConstantFactor(),
				this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio);
		
	}
	
	
	private void setFocusBankDecisionOptimalSecuritisationARM() {
		// TODO Auto-generated method stub
		/**
		 * securitiseHillClimbingTimeVariantX(boolean securitiseLinear,
			int decisionHorizon, int resetWindow, String bankName, double assets, double liabilities, Economy economy, Enviroment environment,
			
			double initialAssetSurvivalRate, double initialLinearSecuritisationCostConstantFactor, 
			double initialNonLinearSecuritisationCostConstantFactor, 
			double initialABSCoupon, double initialReturnOnAssets, double initialReturnOnLiabilities, double initialRegulatoryCapitalRatio,
			
			double postResetAssetSurvivalRate, double postResetLinearSecuritisationCostConstantFactor, 
			double postResetNonLinearSecuritisationCostConstantFactor, 
			double postResetABSCoupon, double postResetReturnOnAssets, 
			double postResetReturnOnLiabilities, double postResetRegulatoryCapitalRatio)
		 */
		this.focusBankDecisionOptimalSecuritisation = SecuritisationEngine.securitiseHillClimbingTimeVariantX(
				this.environment.getBoolNonlinear(),
				this.environment.getSecuritisationRateDecisionHorizon(), this.economy.getLoanResetPeriod(),
				this.getBankName(), this.getTotalAssets(), this.getTotalLiabilities(), this.economy, this.environment, 
				
				this.economy.getSurvivalRate() ,this.economy.getSecuritisationCostConstantFactor(), 
				this.economy.getSecuritisationCostConstantFactor(), this.economy.getSecuritisationCostConstantFactor(),
				this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio,
				
				(1-this.economy.getGenericPostRateResetDafaultRate()), this.economy.getGenericPostRateResetCoupon(),
				this.economy.getGenericPostRateResetCoupon(), this.economy.getGenericPostRateResetCoupon(),
				this.returnOnAssets, this.returnOnLiabilities, this.regulatoryCapitalRatio);
		
	}


	@Override
	public void securitise() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void securitise(double sigma1, double sigma2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void securitise(double sigma1, double sigma2, boolean securitiseLinear) {
		
		double alpha = 0.0;
		
		if(securitiseLinear == true){
			alpha = Math.max(
				(((1 - this.regulatoryCapitalRatio) * sigma2) - (this.regulatoryCapitalRatio * sigma1) - (this.equityCaliptal
						* this.regulatoryCapitalRatio / this.totalAssets))
						/ (2 * this.regulatoryCapitalRatio * sigma2), 0.0)/100;
		}else{
			double rootVar1 = (this.regulatoryCapitalRatio * sigma2 - this.securitisationCostFactor 
					+ this.securitisationCostFactor*this.regulatoryCapitalRatio);
			double rootVar2 = 3*this.securitisationCostFactor*this.regulatoryCapitalRatio;
			double rootVar3 = (sigma2 + (this.regulatoryCapitalRatio*(sigma1 - sigma2)) 
					+ (this.equityCaliptal/this.totalAssets));
			double epsilonSigma2 = (this.regulatoryCapitalRatio* sigma2);
			double thetaEpsilon = (this.regulatoryCapitalRatio*this.securitisationCostFactor);
			
			alpha = Math.max(
					((epsilonSigma2 -this.securitisationCostFactor + thetaEpsilon
							- Math.sqrt((Math.pow(rootVar1, 2))+ rootVar2*rootVar3))
							/(rootVar2)),0.0);
					
		}
		 
		if(this.environment.getBoolSecuritize() == true) {
			if(Double.isNaN(alpha) || Double.isInfinite(alpha)) {
			 this.securitisationRate = 0.0;
			 }else {
				 this.securitisationRate = alpha;
				 }
		}else {
			 this.securitisationRate = 0.0;
			 }
		
		 


	}
	
	
	
	public void securitiseHillClimbing(double sigma1, double sigma2, boolean securitiseLinear, int decisionHorizon) {
		/**
		 * This is my java translation of the MatLab code written for Yang Dong's Ph.D thesis
		 * 
		 * It works by taking the values for the survival rate, decision horizon, the regulatory captial ratio 
		 * and for each time period determines the maximum value of capital injections. 
		 * 
		 * It does this by simply increasing the securitisation rate Alpha by the alphaIncreament variable in this case
		 * 0.01 and then once it has exhausted the possible values of Alpha, it loops through the array of resulting values
		 * for the capital injections and picks the maximum value
		 */
		
		double maxCapitalInjection = 0.0;
		double maxAlpha = 0.0;
		int period = decisionHorizon;
		
		if (this.environment.getSimulationDecisionHorizon() > 0){
			period = this.environment.getSimulationDecisionHorizon();
		}
		 
		int time = period + 1;
		double alphaIncrement = 0.01;
		double[] secRateArray = new double[101];
		double[] survivalRateArray = new double[time+1];
		double[] assetReturnArray = new double[time+1];
		double[] liabilityExpenseArray = new double[time+1];
		double[] regCapitalRatioArray = new double[time+1];
		double[] defaultRateArray = new double[time+1];
		double[] assetArray = new double[time+1];
		double[] liabilityArray = new double[time+1];
		double[] equityArray = new double[time+1]; 
		double[] secCostArray = new double[time+1]; 
		double[] capInjectionArray = new double[101]; 
		double[] Q = new double[time+1]; 
		double[] sum = new double[time+1];
		double sumL;
		double sumA;
		double theta;
		
		this.optimalSecuritisation.clear();
		
		if(securitiseLinear == true){
			
			//this.setHillClimbSecRateNonLinear(period, time, alphaIncrement, secRateArray, survivalRateArray, assetReturnArray, 
			//	liabilityExpenseArray, regCapitalRatioArray, defaultRateArray, assetArray, liabilityArray, equityArray, secCostArray){  }
			secRateArray[0] = 0.0;
			for(int ia = 1; ia <= 100; ia++){
//				System.out.println("scale: "
//						+ ia*0.01);
				secRateArray[ia] = ia*alphaIncrement;
//				System.out.println("scale: "
//						+ ia*alphaIncrement);
				if(this.environment.getMultiPeriodAnalysis() != true){
					for(int i = 0; i < time; i++){
						survivalRateArray[i] = this.assetSurvivalRate;
						assetReturnArray[i] = this.returnOnAssets;
						liabilityExpenseArray[i] = this.returnOnLiabilities;
						regCapitalRatioArray[i] = this.regulatoryCapitalRatio;
						defaultRateArray[i] = this.economy.getSecuritisationCostConstantFactor();
					}//End of for
				} else {
					for(int i = 0; i < this.economy.getLoanResetPeriod(); i++){
						survivalRateArray[i] = this.assetSurvivalRate;
						assetReturnArray[i] = this.returnOnAssets;
						liabilityExpenseArray[i] = this.returnOnLiabilities;
						regCapitalRatioArray[i] = this.regulatoryCapitalRatio;
						defaultRateArray[i] = this.economy.getSecuritisationCostConstantFactor();
					}//End of for
					for(int i = this.economy.getLoanResetPeriod()+ 1; i <= time; i++){
						survivalRateArray[i] = 1 - this.economy.getGenericPostRateResetDafaultRate();
						assetReturnArray[i] = this.returnOnAssets + this.economy.getFullyIndexedContractRateSpread();
						liabilityExpenseArray[i] = this.returnOnLiabilities;
						regCapitalRatioArray[i] = this.regulatoryCapitalRatio;
						defaultRateArray[i] = this.economy.getGenericPostRateResetCoupon();
					}//End of for
				}
				
				
				for(int i = 0; i < 1; i++){
//					assetArray[i] = this.initialAssets;
//					liabilityArray[i] = this.initialLiabilities;
//					equityArray[i] = this.initialAssets
//							-this.initialLiabilities;
					assetArray[i] = this.getTotalAssets();
					liabilityArray[i] = this.getTotalLiabilities();
					equityArray[i] = this.getTotalAssets()
							-this.getTotalLiabilities();
					//secCostArray[i] = 0;
					secCostArray[i] = defaultRateArray[i]*secRateArray[ia];
				}
				
				for(int i = 1; i < time; i++){
					liabilityArray[i] = liabilityArray[i-1]*(1+liabilityExpenseArray[i]);
				}
				
				for(int i = 0; i <= time-1; i++){
					secCostArray[i] = defaultRateArray[i]*secRateArray[ia];//Securitisation Cost
					//Q(i)=(1+alpha(ia))+(1-alpha(ia))*(gamma(i)-ep(i))+gamma(i)*rA(i)-cost(i);
					Q[i] = (1+secRateArray[ia])
							+(1-secRateArray[ia])*
							(survivalRateArray[i] - regCapitalRatioArray[i]) 
									+ survivalRateArray[i]
									*assetReturnArray[i]-secCostArray[i];
				}
				for(int i = 0; i < time; i++){
					sum[i]=1;
				}
				sumL = 0;
				for(int j = 0; j < time; j++){
					int i = j+1;
					if(i<time){
						for(int k = i; k<= time-1; k++){
							sum[j] = sum[j]*Q[k];
						}
						sum[j] = sum[j]*liabilityArray[j];
						
					}else{
							sum[j]=liabilityArray[j];
						}
					//Lsum=Lsum+sum(j);
					sumL = sumL + sum[j];
				}
				
				sumA = assetArray[0];
				for(int j = 0; j < time; j++){
					sumA = sumA*Q[j];
					
				}
				
				assetArray[time] = sumA-sumL;
				/*
				 *   for j=1:t-1
    					

							theta=(ep(t)*(1-alpha(ia))-1);
							M(ia)=-(theta*A(t)+L(t));
				 */
				theta = (regCapitalRatioArray[time]*(1-secRateArray[ia])-1);
//				System.out.println("scale: "
//						+ ia);
//				System.out.println("scale: "
//						+ secRateArray[ia]);
				capInjectionArray[ia] = -(theta*assetArray[time]+liabilityArray[time]);
			}
			
			/**
			 * mpoint=1;
			 * for i=1:100
			 *   if M(i+1)>=M(i) mpoint=i+1; end;
			 *   end;
			 *   opalpha=alpha(mpoint);
			 */
			int maxPointIndex= 0;
			for(int i = 0; i<100; i++){
				if(capInjectionArray[i+1]>=capInjectionArray[i]){
					maxPointIndex = i+1;
//					System.out.println("bank Securitisation rate: "
//							+ secRateArray[i]);
//					System.out.println("bank Securitisation M: "
//							+ capInjectionArray[i]);
				}
//				System.out.println("bank Securitisation rate: "
//						+ secRateArray[i]);
//				System.out.println("bank Securitisation M: "
//						+ capInjectionArray[i]);

			}
			double optimalAlpha = secRateArray[maxPointIndex];
			this.securitisationRate = optimalAlpha;
			this.setSecuritisationRate(optimalAlpha);
//			System.out.println("bank Securitisation Rate Max: "
//					+ this.securitisationRate);
			

			
		}else{
			//this.setHillClimbSecRateNonLinear(period, time, alphaIncrement, secRateArray, survivalRateArray, assetReturnArray, 
			//	liabilityExpenseArray, regCapitalRatioArray, defaultRateArray, assetArray, liabilityArray, equityArray, secCostArray){  }
			//secRateArray[0] = 0.0;
			

			/** MatLab Code:
			 * 
			 * int=1/100;
			 * for ia=1:101 
			 * alpha(ia)=ia*int;
			 * %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			 * period=5;
			 * t=period+1;
			 * 
			 * for i=1:t
			 * gamma(i)=0.9;
			 * rA(i)=0.1;
			 * rL(i)=0.03;
			 * ep(i)=0.08;
			 * rC(i)=1-gamma(i);
			 * end;
			 */
			for(int ia = 0; ia <= 100; ia++){
//				System.out.println("scale: "
//						+ ia*0.01);
				secRateArray[ia] = ia*alphaIncrement;
//				System.out.println("scale: "
//						+ ia*alphaIncrement);
//			} //added 2013-08-28
			
//			for(int t = 1; t <= time; t++){	//added 2013-08-28
				//System.out.println(this.getTotalAssets());
				
				if(this.environment.getMultiPeriodAnalysis() != true){
					for(int i = 1; i < time; i++){
						survivalRateArray[i] = this.assetSurvivalRate;
						assetReturnArray[i] = this.returnOnAssets;
						liabilityExpenseArray[i] = this.returnOnLiabilities;
						regCapitalRatioArray[i] = this.regulatoryCapitalRatio;
						defaultRateArray[i] = this.economy.getSecuritisationCostConstantFactor();
					}//End of for
				} else {
					for(int i = 1; i < this.economy.getLoanResetPeriod(); i++){
						survivalRateArray[i] = this.assetSurvivalRate;
						assetReturnArray[i] = this.returnOnAssets;
						liabilityExpenseArray[i] = this.returnOnLiabilities;
						regCapitalRatioArray[i] = this.regulatoryCapitalRatio;
						defaultRateArray[i] = this.economy.getSecuritisationCostConstantFactor();
					}//End of for
					for(int i = this.economy.getLoanResetPeriod(); i < time; i++){
						survivalRateArray[i] = 1 - this.economy.getGenericPostRateResetDafaultRate();
						assetReturnArray[i] = this.returnOnAssets + this.economy.getFullyIndexedContractRateSpread();
						liabilityExpenseArray[i] = this.returnOnLiabilities;
						regCapitalRatioArray[i] = this.regulatoryCapitalRatio;
						defaultRateArray[i] = this.economy.getGenericPostRateResetCoupon();
					}//End of for
				}


				/** MatLab Code:
				 * 
				 * A(1)=100;
				 * L(1)=92;
				 * E(1)=A(1)-L(1);
				 * 
				 */
				for(int i = 0; i < 1; i++){
					assetArray[i] = this.initialAssets;
					liabilityArray[i] = this.initialLiabilities;
					equityArray[i] = this.initialAssets
							-this.initialLiabilities;
//					assetArray[i] = this.getTotalAssets();
//					liabilityArray[i] = this.getTotalLiabilities();
//					equityArray[i] = this.getTotalAssets()
//							-this.getTotalLiabilities();
					//secCostArray[i] = 0;
//					for(int ia = 0; ia <= 100; ia++){//added 2013-08-28
//					secCostArray[i] = defaultRateArray[i]*
//							(secRateArray[ia] + 
//									Math.pow(secRateArray[ia], 2));
////					}//added 2013-08-28
				}
				
				/** MatLab Code:
				 * 
				 * for i=2:t
				 * L(i)=L(i-1)*(1+rL(i));
				 * end;
				 */			
				for(int i = 1; i <= time; i++){//added 2013-08-28 was <=time
					liabilityArray[i] = liabilityArray[i-1]*(1+liabilityExpenseArray[i]);
				}
				
				/** MatLab Code:
				 * 
				 * %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
				 * for i=1:t-1;
				 * %cost(i)=rC(i)*alpha(ia)+rC(i)*(alpha(ia)^2);
				 * cost(i)=rC(i)*(alpha(ia)+(alpha(ia)^2));
				 * Q(i)=(1+alpha(ia))+(1-alpha(ia))*(gamma(i)-ep(i))+gamma(i)*rA(i)-cost(i);
				 * end;
				 * %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
				 */			
				for(int i = 0; i < time; i++){//added 2013-08-28 was <=time-1
//					for(int ia = 0; ia <= 100; ia++){//added 2013-08-28
					secCostArray[i] = defaultRateArray[i]*
							(secRateArray[ia] + 
									Math.pow(secRateArray[ia], 2));
					
					/** MatLab Code:
					 * 
					 * Q(i)=(1+alpha(ia))+(1-alpha(ia))*(gamma(i)-ep(i))+gamma(i)*rA(i)-cost(i);
					 */
					Q[i] = (1+secRateArray[ia])
							+(1-secRateArray[ia])*
							(survivalRateArray[i] - regCapitalRatioArray[i])
									+ survivalRateArray[i]
									*assetReturnArray[i]-secCostArray[i];
//					}//added 2013-08-28
					//System.out.println(Q[i]);
				}
				
				/** MatLab Code:
				 * 
				 * for i=1:period
				 * sum(i)=1;
				 * end;
				 */
				for(int i = 0; i < period; i++){
					sum[i]=1;
				}
				
				/** MatLab Code:
				 * 
				 * Lsum=0;
				 */
				sumL = 0;
				
				
				/** MatLab Code:
				 * 
				 * for j=1:t-1
				 * i=j+1;
				 * if i<t
				 * for k=i:t-1
				 * sum(j)=sum(j)*Q(k);
				 * end;
				 * sum(j)=sum(j)*L(j);
				 * else sum(j)=L(j);
				 * end;
				 */
				for(int j = 1; j < time; j++){//j=1
					int i = j+1;
					if(i<time){
						for(int k = i; k< time; k++){//k<= time-1
							sum[j] = sum[j]*Q[k];
						}
						sum[j] = sum[j]*liabilityArray[j];
						
					}else{
							sum[j]=liabilityArray[j];
						}
					/** MatLab Code:
					 * 
					 * Lsum=Lsum+sum(j);
					 *///
					sumL = sumL + sum[j];
//					System.out.println(sumL);
				}
				
//				for(int p = 0; p < sum.length; p++){
//					System.out.println(sum[p]);
//				}

				/** MatLab Code:
				 * 
				 * Asum=A(1);
				 * for j=1:t-1;
				 * Asum=Asum*Q(j);
				 * end;
				 * A(t)=Asum-Lsum;
				 */ 
				sumA = assetArray[0];
				for(int j = 1; j < time; j++){//j=1
					sumA = sumA*Q[j];
				//	System.out.println(sumA);
				}
				
				assetArray[time] = sumA-sumL;
				//System.out.println(assetArray[time]);
				
				/**MatLab Code:
				 * 
				 *   for j=1:t-1
    					

							theta=(ep(t)*(1-alpha(ia))-1);
							M(ia)=-(theta*A(t)+L(t));
				 */
//				for(int ia = 0; ia <= 100; ia++){//added 2013-08-28
				theta = (regCapitalRatioArray[time]*(1-secRateArray[ia])-1);
				
//				System.out.println("scale: "
//						+ ia);
//				System.out.println("scale: "
//						+ secRateArray[ia]);
				capInjectionArray[ia] = -(theta*assetArray[time]+liabilityArray[time]);
//				System.out.println(capInjectionArray[ia]);
				this.setPeriodInternalCalculationCapInjection(capInjectionArray[ia]);
//				}//added 2013-08-28
			}
			
			/** MatLab Code:
			 * 
			 * mpoint=1;
			 * for i=1:100
			 *   if M(i+1)>=M(i) mpoint=i+1; end;
			 *   end;
			 *   opalpha=alpha(mpoint);
			 */
			int maxPointIndex= 0;
			for(int i = 0; i<100; i++){
				if(capInjectionArray[i+1]>=capInjectionArray[i]){
					maxPointIndex = i+1;
					this.setPeriodInternalCalculationMax(capInjectionArray[i+1]);
//					System.out.println("bank Securitisation rate: "
//							+ secRateArray[i]);
//					System.out.println("bank Securitisation M: "
//							+ capInjectionArray[i]);
				}
//				System.out.println("bank Securitisation rate: "
//						+ secRateArray[i]);
//				this.setPeriodInternalCalculationCapInjection(secRateArray[i]*100);
//				System.out.println("bank Securitisation M: "
//						+ capInjectionArray[i]);
				this.optimalSecuritisation.add(secRateArray[i], capInjectionArray[i]);
//				this.setPeriodInternalCalculationMax(capInjectionArray[i]);


			}
			this.focusBankOptimalSecCapitalAccumulationArray = capInjectionArray;
			this.focusBankOptimalSecRateArray = secRateArray;
			double optimalAlpha = secRateArray[maxPointIndex];
			this.securitisationRate = optimalAlpha;
			this.setSecuritisationRate(optimalAlpha);
//			System.out.println("bank Securitisation Rate Max: "
//					+ this.securitisationRate);
			
		}
		 
		
		 


	}
	

	private void setPeriodInternalCalculationMax(double capInjectionArray) {
		// TODO Auto-generated method stub
		this.periodInternalCalculationMax = capInjectionArray;
	}


	private void setPeriodInternalCalculationCapInjection(double d) {
		// TODO Auto-generated method stub
		this.periodInternalCalculationCapInjection = d;
	}


	private void setSigma2(double suvRate, double regCapRate) {
		// TODO Auto-generated method stub
		this.sigma2 = (1-(suvRate - regCapRate)); //equation 12
		
	}

	
	private void setSigma2(boolean securitiseLinear, double suvRate, double regCapRate, double coupon) {
		// TODO Auto-generated method stub
		if(securitiseLinear == false){
			this.sigma2 = (1-(suvRate - regCapRate)); //equation 12
		}else{
			this.sigma2 = (1-(suvRate - regCapRate)) - coupon; //equation 7
		}
	}

	private void setSigma1(double suvRate, double regCapRate, double assetRtn) {
		// TODO Auto-generated method stub
		this.sigma1 = ((suvRate - regCapRate) + suvRate*assetRtn); //equation 12
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
	
	public double getPeriodInternalCalculationMax() {
		// TODO Auto-generated method stub
		return this.periodInternalCalculationMax;
	}


	public double getPeriodInternalCalculationCapInjection() {
		// TODO Auto-generated method stub
		return this.periodInternalCalculationCapInjection;
	}
	



	@Override
	public void testBankruptcy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canLend() {
		// TODO Auto-generated method stub
		return false;
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
