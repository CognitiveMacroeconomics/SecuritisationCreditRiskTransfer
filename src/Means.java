import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Means {
	
	
	
	 /**
	  * Returns the geometric mean of a data set with zero and positive decimal values
	  * @param data
	  * @return
	  */
    public static double geometricMean(double[] data)
    {
        double sum = 1.0; 
        if(data.length >0){
        	sum = data[0];
        }
        double geoMean = 0;
 
        
        if(data.length > 1){
        	for (int i = 1; i < data.length; i++){
        		if(data[i] == 0){
        			sum *= 1;
        		} else{
        			sum *= data[i];
        		}
            }
        }
        
 
        geoMean = Math.pow(sum, (1.0 / data.length));
//        System.out.println("Geometric Mean: "+geoMean);
        return geoMean;
    }
    
    
	 /**
	  *  Returns the geometric mean of a data set with zero, negative and positive decimal values
	  * @param data
	  * @return
	  */
    public static double geometricMeanWithNegative(double[] data)
    {
        double sum = 1.0; 
        if(data.length >0){
        	sum = data[0];
        }
        double geoMean = 0;
 
        
        if(data.length > 1){
        	for (int i = 1; i < data.length; i++){
        			sum *= (1 + data[i]);
            }
        }
        
 
        geoMean = Math.pow(sum, (1.0 / data.length)) - 1;
//        System.out.println("Geometric Mean: "+geoMean);
        return geoMean;
    }
    
    
	 /**
	  * Returns the geometric mean of a data set
	  * @param data
	  * @return
	  */
    public static double arithmeticMean(double[] data)
    {
        double sum = data[0];
        double arithMean = 0;
 
        for (int i = 1; i < data.length; i++)
        {
            sum += data[i];
        }
 
        arithMean = (sum / data.length);
//        System.out.println("Geometric Mean: "+geoMean);
        return arithMean;
    }
    
    
	 /**
	  * Returns the median of a data set
	  * @param data
	  * @return
	  */

    public static double median(double[] data) {
        int middle = data.length/2;
        if (data.length%2 == 1) {
            return data[middle];
        } else {
            return (data[middle-1] + data[middle]) / 2.0;
        }
    }
    
    /**
	  * Returns the mode of a data set
	  * @param data
	  * @return
	  */
    public static double mode(double data[]) {
    	int maxCount = 0;
        double maxValue = 0;

        for (int i = 0; i < data.length; ++i) {
            int count = 0;
            for (int j = 0; j < data.length; ++j) {
                if (data[j] == data[i]){
                	++count;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                maxValue = data[i];
            }
        }

        return maxValue;
    }
    
    
    /**
  	  * Returns the modes of a multi-modal data set
  	  * @param data
  	  * @return
  	  */
    public static List<Double> multipleMode(final double [] data) {
        final List<Double> modes = new ArrayList<Double>();
        final Map<Double, Double> countMap = new HashMap<Double, Double>();

        double max = -1;

        for (final double n : data) {
            double count = 0;

            if (countMap.containsKey(n)) {
                count = countMap.get(n) + 1;
            } else {
                count = 1;
            }

            countMap.put(n, count);

            if (count > max) {
                max = count;
            }
        }

        for (final Map.Entry<Double, Double> tuple : countMap.entrySet()) {
            if (tuple.getValue() == max) {
                modes.add(tuple.getKey());
            }
        }

        return modes;
    }


}
