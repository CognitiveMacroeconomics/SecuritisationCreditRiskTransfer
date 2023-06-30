import java.util.ArrayList;
import org.jfree.data.xy.XYSeries;


public class SecuritisationEngine {
	
	public SecuritisationEngine(){ 
		
	}

	public static ArrayList<Object> securitiseHillClimbing(double sigma1, double sigma2, boolean securitiseLinear,
			int decisionHorizon, String bankName, double assets, double liabilities, Economy economy, Enviroment environment,
			double assetSurvivalRate, double securitisationCostConstantFactor, double returnOnAssets, double returnOnLiabilities, double regulatoryCapitalRatio) {
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
		
		/*
		 * this is the returned collection. It stores information about 
		 * 
		 * 0: capital injection
		 * 1: securitisation rates
		 * 2: the optimal securitisation rates
		 * 3: XYSeries to plot results
		 */
		ArrayList optimalSecuritisationResults = new ArrayList();
		
		XYSeries optimalSecuritisation = new XYSeries(bankName + ": Optimal Securitization");
		
		double maxCapitalInjection = 0.0;
		double maxAlpha = 0.0;
		int period = decisionHorizon;
		
		if (environment.getSimulationDecisionHorizon() > 0){
			period = environment.getSimulationDecisionHorizon();
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
		int maxPointIndex= 0;
		double optimalAlpha;
		
		optimalSecuritisation.clear();
		
		if(securitiseLinear == true){
			
			//setHillClimbSecRateNonLinear(period, time, alphaIncrement, secRateArray, survivalRateArray, assetReturnArray, 
			//	liabilityExpenseArray, regCapitalRatioArray, defaultRateArray, assetArray, liabilityArray, equityArray, secCostArray){  }
			secRateArray[0] = 0.0;
			for(int ia = 1; ia <= 100; ia++){
//				System.out.println("scale: "
//						+ ia*0.01);
				secRateArray[ia] = ia*alphaIncrement;
//				System.out.println("scale: "
//						+ ia*alphaIncrement);
				if(environment.getMultiPeriodAnalysis() != true){
					for(int i = 0; i < time; i++){
						survivalRateArray[i] = assetSurvivalRate;
						assetReturnArray[i] = returnOnAssets;
						liabilityExpenseArray[i] = returnOnLiabilities;
						regCapitalRatioArray[i] = regulatoryCapitalRatio;
						defaultRateArray[i] = securitisationCostConstantFactor;
					}//End of for
				} else {
					for(int i = 0; i < economy.getLoanResetPeriod(); i++){
						survivalRateArray[i] = assetSurvivalRate;
						assetReturnArray[i] = returnOnAssets;
						liabilityExpenseArray[i] = returnOnLiabilities;
						regCapitalRatioArray[i] = regulatoryCapitalRatio;
						defaultRateArray[i] = securitisationCostConstantFactor;
					}//End of for
					for(int i = economy.getLoanResetPeriod()+ 1; i <= time; i++){
						survivalRateArray[i] = 1 - economy.getGenericPostRateResetDafaultRate();
						assetReturnArray[i] = returnOnAssets + economy.getFullyIndexedContractRateSpread();
						liabilityExpenseArray[i] = returnOnLiabilities;
						regCapitalRatioArray[i] = regulatoryCapitalRatio;
						defaultRateArray[i] = economy.getGenericPostRateResetCoupon();
					}//End of for
				}
				
				
				for(int i = 0; i < 1; i++){
//					assetArray[i] = initialAssets;
//					liabilityArray[i] = initialLiabilities;
//					equityArray[i] = initialAssets
//							-initialLiabilities;
					assetArray[i] = assets;
					liabilityArray[i] = liabilities;
					equityArray[i] = assets
							-liabilities;
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
			maxPointIndex= 0;
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
			optimalAlpha = secRateArray[maxPointIndex];
//			System.out.println("bank Securitisation Rate Max: "
//					+ securitisationRate);
			

			
		}else{
			//setHillClimbSecRateNonLinear(period, time, alphaIncrement, secRateArray, survivalRateArray, assetReturnArray, 
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
				//System.out.println(getTotalAssets());
				
				if(environment.getMultiPeriodAnalysis() != true){
					for(int i = 1; i < time; i++){
						survivalRateArray[i] = assetSurvivalRate;
						assetReturnArray[i] = returnOnAssets;
						liabilityExpenseArray[i] = returnOnLiabilities;
						regCapitalRatioArray[i] = regulatoryCapitalRatio;
						defaultRateArray[i] = securitisationCostConstantFactor;
					}//End of for
				} else {
					for(int i = 1; i < economy.getLoanResetPeriod(); i++){
						survivalRateArray[i] = assetSurvivalRate;
						assetReturnArray[i] = returnOnAssets;
						liabilityExpenseArray[i] = returnOnLiabilities;
						regCapitalRatioArray[i] = regulatoryCapitalRatio;
						defaultRateArray[i] = securitisationCostConstantFactor;
					}//End of for
					for(int i = economy.getLoanResetPeriod(); i < time; i++){
						survivalRateArray[i] = 1 - economy.getGenericPostRateResetDafaultRate();
						assetReturnArray[i] = returnOnAssets + economy.getFullyIndexedContractRateSpread();
						liabilityExpenseArray[i] = returnOnLiabilities;
						regCapitalRatioArray[i] = regulatoryCapitalRatio;
						defaultRateArray[i] = economy.getGenericPostRateResetCoupon();
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
					assetArray[i] = assets;
					liabilityArray[i] = liabilities;
					equityArray[i] = assets
							-liabilities;
//					assetArray[i] = getTotalAssets();
//					liabilityArray[i] = getTotalLiabilities();
//					equityArray[i] = getTotalAssets()
//							-getTotalLiabilities();
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
			maxPointIndex= 0;
			for(int i = 0; i<100; i++){
				if(capInjectionArray[i+1]>=capInjectionArray[i]){
					maxPointIndex = i+1;
//					setPeriodInternalCalculationMax(capInjectionArray[i+1]);
//					System.out.println("bank Securitisation rate: "
//							+ secRateArray[i]);
//					System.out.println("bank Securitisation M: "
//							+ capInjectionArray[i]);
				}
//				System.out.println("bank Securitisation rate: "
//						+ secRateArray[i]);
//				setPeriodInternalCalculationCapInjection(secRateArray[i]*100);
//				System.out.println("bank Securitisation M: "
//						+ capInjectionArray[i]);
				optimalSecuritisation.add(secRateArray[i], capInjectionArray[i]);
//				setPeriodInternalCalculationMax(capInjectionArray[i]);


			}
				
		}
		double [] optimalSecCapitalAccumulationArray = capInjectionArray;
		double [] optimalSecRateArray = secRateArray;
		optimalAlpha = secRateArray[maxPointIndex];
		
		
		optimalSecuritisationResults.add(optimalSecCapitalAccumulationArray);
		optimalSecuritisationResults.add(optimalSecRateArray);
		optimalSecuritisationResults.add(optimalAlpha);
		optimalSecuritisationResults.add(optimalSecuritisation);
		
		return optimalSecuritisationResults;
	}
		 
	
	
	
	public static ArrayList<Object> securitiseHillClimbingConstantQ(boolean securitiseLinear,
			int decisionHorizon, String bankName, double assets, double liabilities, Economy economy, Enviroment environment,
			double assetSurvivalRate, double linearSecuritisationCostConstantFactor, double nonLinearSecuritisationCostConstantFactor, 
			double mbsCoupon, double returnOnAssets, double returnOnLiabilities, double regulatoryCapitalRatio) {
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
		
		/*
		 * this is the returned collection. It stores information about 
		 * 
		 * 0: capital injection
		 * 1: securitisation rates
		 * 2: the optimal securitisation rates
		 * 3: XYSeries to plot results
		 */
		ArrayList<Object> optimalSecuritisationResults = new ArrayList();
		
		XYSeries optimalSecuritisation = new XYSeries(bankName + ": Optimal Securitization");
		
		double maxCapitalInjection = 0.0;
		double maxAlpha = 0.0;
		int period = decisionHorizon;
		
		if (environment.getSimulationDecisionHorizon() > 0){
			period = environment.getSimulationDecisionHorizon();
		}
		 
		int time = period;
		double alphaIncrement = 0.01;
		double[] secRateArray = new double[101];
		
		double[] survivalRateArray = new double[time+1];
		double[] assetReturnArray = new double[time+1];
		double[] liabilityExpenseArray = new double[time+1];
		double[] regCapitalRatioArray = new double[time+1];
		double[] secCostBetaCoefArray = new double[time+1]; 
		double[] secCostThetaCoefArray = new double[time+1]; 
		double[] secCostThrancheCouponArray = new double[time+1]; 

		double[] defaultRateArray = new double[time+1];
		double[] assetArray = new double[time+1];
		double[] liabilityArray = new double[time+1];
		double[] equityArray = new double[time+1]; 
		double[] secCostArray = new double[time+1]; 
		double[] capInjectionArray = new double[101]; 
		double[] Q = new double[time+1]; 
		double[] sum = new double[time+1];
		double[] sumM1 = new double[time+1];
		double omega1;
		double omega2;
		double sumL;
		double sumLM1;
		double sumA;
		double sumAM1;
		double theta;
		int maxPointIndex= 0;
		double optimalAlpha;
		double regCapExposure;
		optimalSecuritisation.clear();
		
		
		/**
		 * assets, double liabilities, Economy economy, Enviroment environment,
			double assetSurvivalRate, double securitisationCostConstantFactor, double returnOnAssets, 
			double returnOnLiabilities, double regulatoryCapitalRatio
		 */
		for (int i = 0; i <= time; i++){
			survivalRateArray[i] = assetSurvivalRate;
			assetReturnArray[i] = returnOnAssets;
			liabilityExpenseArray[i] = returnOnLiabilities;
			regCapitalRatioArray[i] = regulatoryCapitalRatio;
			secCostBetaCoefArray[i] = linearSecuritisationCostConstantFactor;
			secCostThetaCoefArray[i] = nonLinearSecuritisationCostConstantFactor;
			secCostThrancheCouponArray[i] = mbsCoupon;
		}
		
		assetArray[0] = assets;
		liabilityArray[0] = liabilities;
		equityArray[0] = assetArray[0] - liabilityArray[0];
		
		for(int i = 1; i <= time; i++){
			liabilityArray[i] = liabilityArray[i-1]*(1 + liabilityExpenseArray[i-1]);
		}
		
		
		for(int ia = 0; ia <= 100; ia++){
			/**
			 * set the securitisation rate associated with the 
			 * index ia
			 */
			secRateArray[ia] = ia*alphaIncrement;
			/**
			 * Set the value for Q over time.
			 * That is, given a securitisation rate and 
			 * based on whether the securitisation methodology is linear or non-linear
			 * compute the Q(t)s
			 */
			for(int i = 0; i <= time; i++){
				omega1 = (survivalRateArray[i] - regCapitalRatioArray[i]) + survivalRateArray[i]*assetReturnArray[i];
				omega2 = (1 - (survivalRateArray[i] - regCapitalRatioArray[i])) - secCostBetaCoefArray[i];
					
				if(securitiseLinear == true){
					secCostArray[i] = secCostBetaCoefArray[i]*secRateArray[ia];
					Q[i] =  omega1 + (secRateArray[ia]*omega2);
				}else{//where securitisatio is non-linear
					secCostArray[i] = secCostBetaCoefArray[i]*(secRateArray[ia] + Math.pow(secRateArray[ia], 2));
					Q[i] =  omega1 + (secRateArray[ia]*omega2) - secCostThetaCoefArray[i]*Math.pow(secRateArray[ia], 2);
				}
			}
//			System.out.println("Accumulated Assets: "
//					+ Arrays.toString(Q));
			
			
			/**
			 * set the asset sum arrays to zero as precaution
			 */
			for(int i = 0; i <= time; i++){
				sum[i] = 0;
				sumM1[i] = 0;
			}
			/**
			 * set the asset and liabilitiy sums to zero as precaution
			 */
			sumL = 0;
			sumLM1 = 0;
			
			/**
			 * compute the sum of all accumulated liabilities 
			 * for each time period
			 */
			for(int j = 0; j < time; j++){
				double accum = Math.pow((1+Q[j]),j);
				if(j < time){
					double liab = liabilityArray[time-1-j];
					if(j > 0){
						sumM1[j] = sumM1[j-1] + accum*liab;
					}
					else{
						sumM1[j] = accum*liab;
					}
				}
			}
			

			sumA = assetArray[0];
			sumAM1 = assetArray[0];
			
			for(int j = 1; j <=time; j++){
				sumA = assetArray[0]*Math.pow((1+Q[j]),j);
				assetArray[j] = sumA - sumM1[j-1];
				
			}
//			System.out.println("Accumulated Liabilities: "
//					+ Arrays.toString(sumM1));
//			System.out.println("Accumulated Assets: "
//					+ Arrays.toString(assetArray));
//			System.out.println("Liabilities: "
//					+ Arrays.toString(liabilityArray));
			
			regCapExposure = 1 - (regCapitalRatioArray[time]*(1-secRateArray[ia]));
			capInjectionArray[ia] = (regCapExposure*assetArray[time] - liabilityArray[time]);
			
			
		}//end new securitisation rate calculation
		
		maxPointIndex= 0;
		for(int i = 0; i< 100; i++){
			if(capInjectionArray[i+1]>=capInjectionArray[i]){
				maxPointIndex = i+1;
//				setPeriodInternalCalculationMax(capInjectionArray[i+1]);
//				System.out.println("bank Securitisation rate: "
//						+ secRateArray[i]);
//				System.out.println("bank Securitisation M: "
//						+ capInjectionArray[i]);
			}
//			System.out.println("bank Securitisation rate: "
//					+ secRateArray[i]);
//			System.out.println("bank Securitisation M: "
//					+ capInjectionArray[i]);
//			optimalSecuritisation.add(secRateArray[i], capInjectionArray[i]);
//			setPeriodInternalCalculationCapInjection(secRateArray[i]*100);
//			setPeriodInternalCalculationMax(capInjectionArray[i]);


		}
		
		double [] optimalSecCapitalAccumulationArray = capInjectionArray;
		double [] optimalSecRateArray = secRateArray;
		optimalAlpha = secRateArray[maxPointIndex];
		
		/*
		 * from the bank calling this method
		 * this.focusBankOptimalSecCapitalAccumulationArray = capInjectionArray;
		 * this.focusBankOptimalSecRateArray = secRateArray;
		 * double optimalAlpha = secRateArray[maxPointIndex];
		 * this.securitisationRate = optimalAlpha;
		 * this.setSecuritisationRate(optimalAlpha);
		 */
		optimalSecuritisationResults.add(optimalSecCapitalAccumulationArray);
		optimalSecuritisationResults.add(optimalSecRateArray);
		optimalSecuritisationResults.add(optimalAlpha);
		optimalSecuritisationResults.add(optimalSecuritisation);
		
//		System.out.println("Sec Engine Results Size: "+optimalSecuritisationResults.size());
		return optimalSecuritisationResults;
		
	}
		 


	public static ArrayList<Object> securitiseHillClimbingTimeVariantX(boolean securitiseLinear,
			int decisionHorizon, int resetWindow, String bankName, double assets, double liabilities, Economy economy, Enviroment environment,
			double initialAssetSurvivalRate, double initialLinearSecuritisationCostConstantFactor, 
			double initialNonLinearSecuritisationCostConstantFactor, 
			double initialABSCoupon, double initialReturnOnAssets, double initialReturnOnLiabilities, double initialRegulatoryCapitalRatio,
			double postResetAssetSurvivalRate, double postResetLinearSecuritisationCostConstantFactor, 
			double postResetNonLinearSecuritisationCostConstantFactor, 
			double postResetABSCoupon, double postResetReturnOnAssets, 
			double postResetReturnOnLiabilities, double postResetRegulatoryCapitalRatio) {
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
		
		/*
		 * this is the returned collection. It stores information about 
		 * 
		 * 0: capital injection
		 * 1: securitisation rates
		 * 2: the optimal securitisation rates
		 * 3: XYSeries to plot results
		 */
		ArrayList<Object> optimalSecuritisationResults = new ArrayList();
		
		XYSeries optimalSecuritisation = new XYSeries(bankName + ": Optimal Securitization");
		
		double maxCapitalInjection = 0.0;
		double maxAlpha = 0.0;
		int period = decisionHorizon;
		int rateResetWindow = resetWindow;
		
		if (environment.getSimulationDecisionHorizon() > 0){
			period = environment.getSimulationDecisionHorizon();
		}
		
		
		 
		int time = period;
		double alphaIncrement = 0.01;
		double[] secRateArray = new double[101];
		
		double[] survivalRateArray = new double[time+1];
		double[] assetReturnArray = new double[time+1];
		double[] liabilityExpenseArray = new double[time+1];
		double[] regCapitalRatioArray = new double[time+1];
		double[] secCostBetaCoefArray = new double[time+1]; 
		double[] secCostThetaCoefArray = new double[time+1]; 
		double[] secCostThrancheCouponArray = new double[time+1]; 

		double[] productRL = new double[time+1];
		double[] assetArray = new double[time+1];
		double[] liabilityArray = new double[time+1];
		double[] equityArray = new double[time+1]; 
		double[] secCostArray = new double[time+1]; 
		double[] capInjectionArray = new double[101]; 
		double[] X = new double[time+1]; 
		double[] XProduct = new double[time+1];
		double[] XProductM1 = new double[time+1];
		double[] XProductForL = new double[time+1];
		double[] XProductForLm1 = new double[time+1];
		double[] productLX = new double[time+1];
		double[] productLXm1 = new double[time+1];
		double sumOfProductL;
		double sumOfProductLm1;
		double sumA;
		double sumAM1;
		double theta;
		int maxPointIndex= 0;
		double optimalAlpha;
		double regCapExposure;
		optimalSecuritisation.clear();
		
		
		/**
		 *  
		 * double initialAssetSurvivalRate, double initialLinearSecuritisationCostConstantFactor, 
			double initialNonLinearSecuritisationCostConstantFactor, 
			double initialABSCoupon, double initialReturnOnAssets, double initialReturnOnLiabilities, double initialRegulatoryCapitalRatio,
			double postResetAssetSurvivalRate, double postResetLinearSecuritisationCostConstantFactor, 
			double postResetNonLinearSecuritisationCostConstantFactor, 
			double postResetABSCoupon, double postResetReturnOnAssets, 
			double postResetReturnOnLiabilities, double postResetRegulatoryCapitalRatio
		 */
		for (int i = 0; i <= time; i++){
			if( i <= rateResetWindow) {
				survivalRateArray[i] = initialAssetSurvivalRate;
				assetReturnArray[i] = initialReturnOnAssets;
				liabilityExpenseArray[i] = initialReturnOnLiabilities;
				regCapitalRatioArray[i] = initialRegulatoryCapitalRatio;
				secCostBetaCoefArray[i] = initialLinearSecuritisationCostConstantFactor;
				secCostThetaCoefArray[i] = initialNonLinearSecuritisationCostConstantFactor;
				secCostThrancheCouponArray[i] = initialABSCoupon;
			} else{
				survivalRateArray[i] = postResetAssetSurvivalRate;
				assetReturnArray[i] = postResetReturnOnAssets;
				liabilityExpenseArray[i] = postResetReturnOnLiabilities;
				regCapitalRatioArray[i] = postResetRegulatoryCapitalRatio;
				secCostBetaCoefArray[i] = postResetLinearSecuritisationCostConstantFactor;
				secCostThetaCoefArray[i] = postResetNonLinearSecuritisationCostConstantFactor;
				secCostThrancheCouponArray[i] = postResetABSCoupon;
			}
		}
		
		assetArray[0] = assets;
		liabilityArray[0] = liabilities;
		equityArray[0] = assetArray[0] - liabilityArray[0];
		productRL[0] = Math.pow((1 + liabilityExpenseArray[0]), 0);
		
		
		
		for(int i = 1; i <= time; i++){
			productRL[i] = productRL[i-1]*(1+liabilityExpenseArray[i-1]); 
			liabilityArray[i] = liabilityArray[0]*productRL[i];
		}
		
		
		for(int ia = 0; ia <= 100; ia++){
			/**
			 * set the securitisation rate associated with the 
			 * index ia
			 */
			secRateArray[ia] = ia*alphaIncrement;
			/**
			 * Set the value for Q over time.
			 * That is, given a securitisation rate and 
			 * based on whether the securitisation methodology is linear or non-linear
			 * compute the Q(t)s
			 * survivalRateArray[i] = initialAssetSurvivalRate;
				assetReturnArray[i] = initialReturnOnAssets;
				liabilityExpenseArray[i] = initialReturnOnLiabilities;
				regCapitalRatioArray[i] = initialRegulatoryCapitalRatio;
				secCostBetaCoefArray[i] = initialLinearSecuritisationCostConstantFactor;
				secCostThetaCoefArray[i] = initialNonLinearSecuritisationCostConstantFactor;
				secCostThrancheCouponArray[i]
			 */
			X[0] = 1; 
			for(int i = 1; i <= time; i++){
				if(securitiseLinear == true){
					secCostArray[i] = secCostBetaCoefArray[i]*secRateArray[ia];
				}else{//where securitisatio is non-linear
					secCostArray[i] = secCostBetaCoefArray[i]*(secRateArray[ia] + Math.pow(secRateArray[ia], 2));
				}
				X[i] =  1 + (1-secRateArray[ia])*(survivalRateArray[i] - regCapitalRatioArray[i])
						+ secRateArray[ia] + survivalRateArray[i]*assetReturnArray[i]
								- secCostArray[i];
			}
			
//			System.out.println("suvivall rate: "
//					+ Arrays.toString(survivalRateArray));
//			System.out.println("Accumulation Multiplier: "
//					+ Arrays.toString(X));
			
			
			/**
			 * set all elements of all the product arrays to 1 as precaution
			 */
			for(int j = 0; j <= time; j++){
				XProduct[j] = 1;
				XProductM1[j] = 1;
				XProductForL[j] = 1;
				XProductForLm1[j] = 1;
				productLX[j] = 1;
				productLXm1[j] = 1;
			}
			for(int j = 0; j <= time; j++){
				for(int i = 0; i <= j; i++){
					XProduct[j] = XProduct[j]*X[i];
				}
			}
			for(int j = 0; j <= time-1; j++){
				for(int i = 0; i <= j; i++){
					XProductM1[j] = XProductM1[j]*X[i];
				}
			}
			
//			System.out.println("XProduct: "
//					+ Arrays.toString(XProduct));
//			
//			System.out.println("XProductM1: "
//					+ Arrays.toString(XProductM1));
			
			/**
			 * set the product arrays for liabilities
			 */
			for(int j = 1; j <= time; j++){
				int k = j+1;
				for(int i = k; i <= time; i++){
					XProductForL[j] = XProductForL[j]*X[i];
				}
			}
			for(int j = 1; j <= time; j++){
				int k = j+1;
				for(int i = k; i <= time-1; i++){
					XProductForLm1[j] = XProductForLm1[j]*X[i];
				}
			}
			
//			System.out.println("XProductForL: "
//					+ Arrays.toString(XProductForL));
//			
//			System.out.println("XProductForLm1: "
//					+ Arrays.toString(XProductForLm1));
			
			
			sumA = assetArray[0]*XProduct[time];
			sumAM1 = assetArray[0]*XProductM1[time];
			
			/**
			 * set the asset and liabilitiy sums to zero as precaution
			 * 
			 */
			sumOfProductL = 0;
			sumOfProductLm1 = 0;
			
			/**
			 * set the product arrays for liabilities
			 */
			for(int j = 0; j <= time; j++){
				int k = j+1;
				if(k < time){
					productLX[j] = XProductForL[k]*liabilityArray[j];
				}
				else {
					productLX[j] = liabilityArray[j];
				}
			}
			for(int j = 0; j <= time-1; j++){
				int k = j+1;
				if(k < time-1){
					productLXm1[j] = XProductForLm1[k]*liabilityArray[j];
				}
				else {
					productLXm1[j] = liabilityArray[j];
				}
			}
			

//			System.out.println("productLX: "
//					+ Arrays.toString(productLX));
//			
//			System.out.println("productLXm1: "
//					+ Arrays.toString(productLXm1));
			
			
			for(int j = 0; j<=time-1; j++){
				sumOfProductL += productLX[j];
			}
			
			for(int j = 0; j<=time-2; j++){
				sumOfProductLm1 += productLXm1[j];
			}
			
			double projectionOfAatT = sumA - sumOfProductL;
			double projectionOfAatTm1 = sumAM1 - sumOfProductLm1;

			
//			System.out.println("Accumulated Liabilities: "
//					+ Arrays.toString(sumM1));
//			System.out.println("Accumulated Assets: "
//					+ Arrays.toString(assetArray));
//			System.out.println("Liabilities: "
//					+ Arrays.toString(liabilityArray));
			
			regCapExposure = 1 - (regCapitalRatioArray[time]*(1-secRateArray[ia]));
			capInjectionArray[ia] = (regCapExposure*projectionOfAatT - liabilityArray[time]);
			
			
		}//end new securitisation rate calculation
		
		maxPointIndex= 0;
		for(int i = 0; i< 100; i++){
			if(capInjectionArray[i+1]>=capInjectionArray[i]){
				maxPointIndex = i+1;
//				setPeriodInternalCalculationMax(capInjectionArray[i+1]);
//				System.out.println("bank Securitisation rate: "
//						+ secRateArray[i]);
//				System.out.println("bank Securitisation M: "
//						+ capInjectionArray[i]);
			}
//			System.out.println("bank Securitisation rate: "
//					+ secRateArray[i]);
//			System.out.println("bank Securitisation M: "
//					+ capInjectionArray[i]);
//			optimalSecuritisation.add(secRateArray[i], capInjectionArray[i]);
//			setPeriodInternalCalculationCapInjection(secRateArray[i]*100);
//			setPeriodInternalCalculationMax(capInjectionArray[i]);


		}
		
		double [] optimalSecCapitalAccumulationArray = capInjectionArray;
		double [] optimalSecRateArray = secRateArray;
		optimalAlpha = secRateArray[maxPointIndex];
		
		/*
		 * from the bank calling this method
		 * this.focusBankOptimalSecCapitalAccumulationArray = capInjectionArray;
		 * this.focusBankOptimalSecRateArray = secRateArray;
		 * double optimalAlpha = secRateArray[maxPointIndex];
		 * this.securitisationRate = optimalAlpha;
		 * this.setSecuritisationRate(optimalAlpha);
		 */
		optimalSecuritisationResults.add(optimalSecCapitalAccumulationArray);
		optimalSecuritisationResults.add(optimalSecRateArray);
		optimalSecuritisationResults.add(optimalAlpha);
		optimalSecuritisationResults.add(optimalSecuritisation);
		
//		System.out.println("Sec Engine Results Size: "+optimalSecuritisationResults.size());
		return optimalSecuritisationResults;
		
	}
	
	

}
