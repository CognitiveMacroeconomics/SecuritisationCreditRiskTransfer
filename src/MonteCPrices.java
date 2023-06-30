


import static java.lang.Math.exp;
import static java.lang.Math.log;
//import static java.lang.Math.sqrt;
import static java.lang.Math.*;
import java.util.Random;


public class MonteCPrices {
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<variable declaration>>>>>>>>>>>>>>>>>>>
	
	private int iterations;
	private double finalValueChange;
	private double finalvalueln;
	private double finalValuePrice;
	private Random rand = new Random();
	private double gaussianProcess1;
	private double gaussianProcess2;
	protected int observationIndex = 0;
	
	public double[] path;
	
	

	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<Constructors>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	//no-arg constructor
	public MonteCPrices() {
	}
	
	
	public MonteCPrices(int n) {
	    iterations = n;
	}
	
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<Method Definitions>>>>>>>>>>>>>>>>>>>>>>
	
	
	//<<<<<<<Processing/Operational Method Definitions>>>>>>>>

	public double[] simValue(double mean,double sd,double time,double initialvalue)	{
	  double[] simvaluesChanges = new double[iterations];
	  ItoProcess ito = new ItoProcess();
	   for(int i = 0; i < iterations; i++){
		   simvaluesChanges[i] = ito.itoValue(mean, sd, time, initialvalue);
		   initialvalue = initialvalue+simvaluesChanges[i];
	   }
	   this.finalValueChange = initialvalue;
	  return simvaluesChanges;//returns the changes from period to period
	}
	
	
	public double[] simValueP(double mean,double sd,double time,double initialvalue){
	  double[] simvaluesPrices = new double[iterations];
	          
	  ItoProcess ito = new ItoProcess();
	   double change;
	   for(int i = 0; i < iterations; i++){
		   simvaluesPrices[i] = initialvalue;
		   change = ito.itoValue(mean, sd, time, initialvalue);
		   initialvalue += change;
	    }
	   this.finalValuePrice = initialvalue;
	  return simvaluesPrices;//returns the new price from period to period
	}
	
		
	//Continuous time implementation
	public double[] simValueln(double mean,double sd,double time,double initialvalue){
		double[] simvalues = new double[iterations];
	    double so = initialvalue;
	    initialvalue = log(initialvalue);
	    GenWiener g = new GenWiener();
	    mean = ((mean-(sd*sd))/2.0);
	    sd = (sqrt(time)*sd);
	    for(int i = 0; i < iterations; i++){
	    	simvalues[i] = initialvalue;
	    	double change = g.genWienerproc(mean, time, sd);// period to period change
	    	initialvalue = (change+initialvalue);
	    }
	    
	    this.finalvalueln = exp(initialvalue);
	    return simvalues;//returns the new prices from period to period
	}
	
	
// simple untidy fodged discretised implementation of The Cox-Ingersoll-Ross model 	
	public double[] simValueCIR(double alpha, double theta, 
			double sigma, double time, double initialValue){
		  double[] simvaluesRates = new double[iterations];
		  double mean;
		  double sd;
		          
		  ItoProcess ito = new ItoProcess();
		   double change;
		   for(int i = 0; i < iterations; i++){
			   mean = alpha*(theta - initialValue);
			   sd = sigma*sqrt(initialValue);
			   simvaluesRates[i] = initialValue;
			   change = ito.itoValue(mean, sd, time, initialValue);
			   initialValue += change;
		    }
		   this.finalValuePrice = initialValue;
		  return simvaluesRates;//returns the new price from period to period
		}
	
	
//	 simple untidy fodged discretised implementation of The Cox-Ingersoll-Ross++ (CIR++) model 	
		public double[] simValueCIRpp(double alpha, double theta, 
				double sigma, double time, double initialValue,  double jumpIntensity, double meanJumpSize,
				 double widthOfJumpSizeDistribution){
			int jump;  
			double[] simvaluesRates = new double[iterations];
			int[] jumps = new int[iterations];
			double mean;
			double sd;
		          
			ItoProcess ito = new ItoProcess();
			double change;
			double changeJump = 0;
			for(int i = 0; i < iterations; i++){
				mean = alpha*(theta - initialValue);
				sd = sigma*sqrt(initialValue);
				simvaluesRates[i] = initialValue;
				change = ito.itoValue(mean, sd, time, initialValue);
				
				/*
				 * now to define the Jump process
				 */
				jump = poission(jumpIntensity);
		
				//add the jump to jump array to store jump history.
				if(i>0) jumps[i-1] = jump;
				for(int j=0; j<jump; j++){
					changeJump += jump_size(meanJumpSize,
							widthOfJumpSizeDistribution);
					 } 
			   
			   initialValue += (change+ changeJump);
			   
		    }
		   this.finalValuePrice = initialValue;
		  return simvaluesRates; //returns the new price from period to period
		}
	
		
//		 simple untidy forged implementation of a geometric Brownian motion with stochastic jumps 
		
		/**
		 * public double[] simValue(double mean,double sd,double time,double initialvalue)	{
	  double[] simvaluesChanges = new double[iterations];
	  ItoProcess ito = new ItoProcess();
	   for(int i = 0; i < iterations; i++){
		   simvaluesChanges[i] = ito.itoValue(mean, sd, time, initialvalue);
		   initialvalue = initialvalue+simvaluesChanges[i];
	   }
	   this.finalValueChange = initialvalue;
	  return simvaluesChanges;//returns the changes from period to period
	}
		 * @param alpha
		 * @param theta
		 * @param sigma
		 * @param time
		 * @param initialValue
		 * @param jumpIntensity
		 * @param meanJumpSize
		 * @param widthOfJumpSizeDistribution
		 * @return
		 */
			public double[] simValueJumpDiffusionBM(double mean, 
					double sigma, double time, double initialValue,  double jumpIntensity, double meanJumpSize,
					 double widthOfJumpSizeDistribution){
				int jump;  
				double[] simvaluesRates = new double[iterations];
				int[] jumps = new int[iterations];
				double mu;
				double sd;
			          
				ItoProcess ito = new ItoProcess();
				double change;
				double changeJump = 0;
				for(int i = 0; i < iterations; i++){
					sd = sigma*sqrt(initialValue);
					simvaluesRates[i] = initialValue;
//					change = ito.itoValue(mean, sd, time, initialValue);
					change = ito.itoValue(mean, sigma, time, initialValue);
					/*
					 * now to define the Jump process
					 */
					jump = poission(jumpIntensity);
			
					//add the jump to jump array to store jump history.
					if(i>0) jumps[i-1] = jump;
					for(int j=0; j<jump; j++){
						changeJump += jump_size(meanJumpSize,
								widthOfJumpSizeDistribution);
						 } 
				   
				   initialValue += (change+ changeJump);
				   
			    }
			   this.finalValuePrice = initialValue;
			  return simvaluesRates; //returns the new price from period to period
			}
			
			
			
			/**
			 * This is the stochastic process path for the Heston stochastic volatility model
			 * The process is essentially a combination of a simple Brownian Motion for the process being modeled 
			 * and a CIR/mean reversion process for the variance/standard deviation of the Brownian Motion
			 * The first step is to compute the path for the variance
			 * Then for each itteration plug each successive value of the variance in the Brownian Motion
			 * @param mean
			 * @param sigma
			 * @param time
			 * @param initialValue
			 * @param sigmaAlpha
			 * @param sigmaTheta
			 * @param sigmaSigma
			 * @return
			 */
			public double[] simValueHestonStochastricVolatilityDiffusion(double mean, 
					double sigma, double time, double initialValue,  double sigmaAlpha, double sigmaTheta, double sigmaSigma){
				int jump;  
				double[] simvaluesRates = new double[iterations];
				double[] simSigmaPath = new double[iterations];
				double mu;
				double sd;
				ItoProcess ito = new ItoProcess();
				double change;
				
				//compute the path for the volatility term. 
				//Note that this path is calculated based on the variance not the standard deviation
				//hence the Math.pow(sigma, 2)
				simSigmaPath = simValueCIR(sigmaAlpha, sigmaTheta, 
						sigmaSigma, time, Math.pow(sigma, 2));
				
				for(int i = 0; i < iterations; i++){
					//not that since the simSigmaPath contains the variance of the Heston Process the square root
					//must be taken to return back to the basic Brownian motion process
					sd = sqrt(simSigmaPath[i])*sqrt(initialValue);
					simvaluesRates[i] = initialValue;
//					change = ito.itoValue(mean, sd, time, initialValue);
					change = ito.itoValue(mean, sigma, time, initialValue);
					/*
					 * now to define the Jump process
					 */
				   
				   initialValue += change;
				   
			    }
			   this.finalValuePrice = initialValue;
			  return simvaluesRates; //returns the new price from period to period
			}
	
	

	//<<<<<<<<methods required for jump difussions>>>>>>>>>>>
	
	
	void gaussian() {
		// Gaussian distribution
		double v1;
		double v2;
		double r2;
		double temp;
		
		do{
			v1 = 2.0*rand.nextDouble() - 1.0;
			v2 = 2.0*rand.nextDouble() - 1.0;
			r2 = pow(v1,2)+pow(v2,2);
		}while (r2>=1.0||r2==0.0);
		temp = sqrt(-2.0*log(r2)/(r2));
		gaussianProcess1 = v1*temp; // two independent unit normals
		gaussianProcess2 = v2*temp; // g1 and g2
		
	}//end of gaussian distribution implementation

	int poission(double lambda) {
		// Poison distribution
		int n=0;
		int k=1;
		double temp=1.0;
		double A = 1.0;
		double oldA = A;
		
		do {
			A = rand.nextDouble()*oldA;
			temp = Math.exp(-lambda);
			if(A<temp) n =k-1;
			else k+= 1;
			oldA =A;
		}while (A> temp || A == temp);
		
		return n;
	} //end of poison distribution implementation
	

	private double jump_size(double meanJumpSize2,
			double widthOfJumpSizeDistribution2) {
		// TODO Auto-generated method stub
		double temp;
		temp = exponential(widthOfJumpSizeDistribution2);
		if(rand.nextDouble()>0.5){
			return temp + meanJumpSize2;
		} else{
			return -temp + meanJumpSize2;	
		}
		
	}
	
	
	
	
	private double exponential(double widthOfJumpSizeDistribution2) {
		/*
		 * This method generated the exponential distribution
		 * (1/widthOfJumpSizeDistribution2)*exp(-x/widthOfJumpSizeDistribution2)
		 */
		// TODO Auto-generated method stub
		double rnd;
		do{ rnd = rand.nextDouble();
		} while(rnd == 0);
		return -widthOfJumpSizeDistribution2*Math.log(rnd);
	}




	
	

	
	//<<<<<<<Getters and Setters>>>>>>>

	public double getValuesim(){
	    return finalValueChange;
	}
	
	public double getValuesimln(){
	    return finalvalueln;
	}
	
	public double getValuesimp(){
	    return finalValueChange;
	}

}// end of MonteCPrices class definition