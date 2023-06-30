
import static java.lang.Math.*;



/**
 * 
 * @author segun
 *
 *this class computes the generalised Wiener process where the parameters 
 *are functions of the underlying variable
 *
 */


public class ItoProcess {

	
	//<<<<<<<<<<<<<<<<<<<<<<<variable declaration>>>>>>>>>>>>>>>>>>>
	
	private double sdChanges;
	private double meanValue;
	private double changeBase;
	
	
	//<<<<<<<<<<<<<<<<<<<<<<<<<<Constructor>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	public ItoProcess(){
		
	}
	
	//<<<<<<<<<<<<<<<<<<<<<<<<Method Definitions>>>>>>>>>>>>>>>>>>>>>>
	
	
		//<<<<<<<Processing/Operational Method Definitions>>>>>>>>
	  /**
	  *
	  * @param mu mean value
	  * @param sigma The variance
	  * @param timedelta time periods for each step
	  * @param basevalue the starting value
	  * @return The change in the base value
	  */
	public double itoValue(double mu, double sigma, double timedelta,double basevalue)
	{
	     setSd(basevalue*(sigma*sqrt(timedelta)));
	     GenWiener g = new GenWiener();
	     mu = mu*basevalue;
	     sigma = sigma*basevalue;
	     double change = ( g.genWienerproc(mu, timedelta, sigma));
	     setChange(change);
	     setMean(g.getDrift());
	     return change;
	   }
	
	
		//<<<<<<<Getters and Setters>>>>>>>
	private void setChange(double changevalue){
		changeBase = changevalue;
	}
	
	public double getBaseValChange(){
		return changeBase;
	}
	
	private void setSd(double sd)
	{
	  sdChanges=sd;
	}
	public double getSd()
	{
	  return sdChanges;
	}
	private void setMean(double drift)
	{
	  meanValue=drift;
	}
	public double getMean()
	{
	  return meanValue;
	}

	
}// end of Itorocess class definition
