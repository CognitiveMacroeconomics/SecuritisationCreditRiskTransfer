import java.util.Random;


public class ProbabilityDistributions {
	
	
	final static public Random RANDOM = new Random(System.currentTimeMillis()); 

    /**
     * This method generates random numbers with a skewed normal/gausian distribution
     * 
     * The parameters do the following:
     * min - the minimum skewed value possible
     * max - the maximum skewed value possible
     * skew - the degree to which the values cluster around the mode of the distribution; higher values mean tighter clustering
     * bias - the tendency of the mode to approach the min, max or midpoint value; positive values bias toward max, negative values toward left
     * @param min
     * @param max
     * @param skew
     * @param bias
     * @return
     */
	static public double nextSkewedBoundedDouble(double min, double max, double skew, double bias) { 
        double range = max - min; 
        double mid = min + range / 2.0; 
        double unitGaussian = RANDOM.nextGaussian(); 
        double biasFactor = Math.exp(bias); 
        double retval = mid+(range*(biasFactor/(biasFactor+Math.exp(-unitGaussian/skew))-0.5)); 
        return retval; 
    }
	
	
	
    /**
     * This method generates random numbers with a skewed normal/gausian distribution
     * 
     * The parameters do the following:
     * min - the minimum skewed value possible
     * max - the maximum skewed value possible
     * skew - the degree to which the values cluster around the mode of the distribution; higher values mean tighter clustering
     * bias - the tendency of the mode to approach the min, max or midpoint value; positive values bias toward max, negative values toward left
     * @param min
     * @param max
     * @param skew
     * @param bias
     * @return
     */
	static public int nextSkewedBoundedInteger(int min, int max, double skew, double bias) { 
        int range = max - min; 
        int mid = min + range / 2; 
        double unitGaussian = RANDOM.nextGaussian(); 
        double biasFactor = Math.exp(bias); 
        int retval = (int) (mid+(range*(biasFactor/(biasFactor+Math.exp(-unitGaussian/skew))-0.5))); 
        return retval; 
    }


}
