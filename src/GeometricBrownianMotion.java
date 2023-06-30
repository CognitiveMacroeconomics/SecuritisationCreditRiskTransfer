
import static java.lang.Math.*;


public class GeometricBrownianMotion {
	
	private double pointDrift;
	private double pointSD;
	GenWiener weiner = new GenWiener();
	
	
	/*
	 * no-arg constructor for the geometric Brownian motion.
	 */
	public GeometricBrownianMotion(){
		
	}
	
	/*
	 * <<<<<<<<<<<<<<<<<<<<<<<<<<Method Definitions>>>>>>>>>>>>>>>>>>>>>>>
	 */
	//<<<<<<<<<<<<<<<<<<<<<<<< Getters and Setteres >>>>>>>>>>>>>>>>>>>>>>>>
	private void setDrift(double drift){
		pointDrift = drift;
	}
	
	private void setSD(double sd){
		pointSD = sd;
	}
	
	public double getDrift(){
		return pointDrift;
	}
	
	public double getSD(){
		return pointSD;
	}
	
	
	public double [][] expBrownian(double mu, double sigma, double times, int points){
		
		double[][] wVal = new double[points+1][4];
		wVal[0][0] = 0.0;
		wVal[0][1] = 0.0;
		wVal[0][2] = (sqrt((exp(0.0)-1)*exp(2*0.0)));
		
		double varVal;
		double interim = 0.0;
		int counter=1;
		double d = points;
		
		double driftValues = 0.0;
		
		while(counter <points){
			varVal = (sqrt((exp(counter/d)-1)*exp(2*counter/d)));
			interim = (weiner.genWienerproc(mu,times,sigma)+interim);
			wVal[counter][0] = exp(interim);
			driftValues = (driftValues+weiner.getDrift());
			//drift
			wVal[counter][1]= exp(driftValues);
			//drift  plus variance
			wVal[counter][2] = (wVal[counter][1]+ varVal);
			// drift minus variance
			wVal[counter][3] = (wVal[counter][1] - varVal);
			
			counter++;
			
		}//end of while
		
		return wVal;
		
	}// end of expBrownian
	
	public void geometricBrownian(double mu, double sigma, double time){
		/*
		 * this method assumes annual periods/ratios
		 */
		
		GenWiener w = new GenWiener();
		double process = exp(w.genWienerproc(mu, time, sigma));
		
		setDrift(exp((w.getDrift())));
		setSD( sqrt((exp(2.0*mu*(time)+ pow(sigma,2.0)*(time))
				*(exp(pow(sigma,2.0)*(time))-1))));
		
	}// end of geometricBrownian
	

	

}//end of GeometricBrownianMotion class definition.
