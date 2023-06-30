

import java.util.Random;
import static java.lang.Math.sqrt;

public class Wiener {
	
	/*
	 * Wiener no-arg constructor
	 */
	public Wiener(){
		
	}//end of wiener constructor.
	
	
	public double wienerProcess(double t){
		Random rnd = new Random();
		double epsilon = rnd.nextGaussian();
		return sqrt(t)*epsilon;
		
	}

}//end of Wiener Class definition
