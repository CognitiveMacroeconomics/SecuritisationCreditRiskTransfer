

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PortfolioWeightsFactory {
	
	
	/**
	 * This method creates the set of weights applicable to an asset
	 */
	public static ArrayList<Double> createSingleAssetPortfolioWeightsList(double assetWeightIncrement){
		int fullPortfolioWeigt = (int) (1.0/assetWeightIncrement);//this variable is used to force portfolio weights to be always equal to a maximum of 1
		 ArrayList<Double> weightsSpace = new ArrayList<Double>();
		 for(int i = 0; i <= fullPortfolioWeigt; i++){
		        double traditionalWeight = roundTwoDecimals(i*assetWeightIncrement);
		        weightsSpace.add(traditionalWeight);
		    }
		 return weightsSpace;
	}
	
	
	
	
	
	/**
	 * This method creates the set of weights applicable two asset selections
	 */
	public static  ArrayList<PortfolioWeights> createDefaultTwoAssetPortfolioWeightsList(double assetWeightIncrement){
		int fullPortfolioWeigt = (int) (1.0/assetWeightIncrement);//this variable is used to force portfolio weights to be always equal to a maximum of 1
		 ArrayList<PortfolioWeights> weightsTwoAssetPairingSpace = new ArrayList<PortfolioWeights>();
		 PortfolioWeights portSel;
		 for(int i = 0; i <= fullPortfolioWeigt; i++){
		        double traditionalWeight = roundTwoDecimals(i*assetWeightIncrement);
		        double creditWeight = 1- traditionalWeight;
		        weightsTwoAssetPairingSpace.add(PortfolioWeights.createDefaultPorfolioWeights(traditionalWeight, creditWeight));
		    }
		 return weightsTwoAssetPairingSpace;
	}


	
	
	
	/**
	 * This method creates the set of weights applicable three asset selections
	 */
	public static  ArrayList<PortfolioWeights> createThreeAssetPortfolioWeightsList(double assetWeightIncrement){
		int fullPortfolioWeigt = (int) (1.0/assetWeightIncrement);//this variable is used to force portfolio weights to be always equal to a maximum of 1
		 ArrayList<PortfolioWeights> weightsThreeAssetPairingSpace = new ArrayList<PortfolioWeights>();
		 PortfolioWeights portSel;
		 for(int i = 0; i <= fullPortfolioWeigt; i++){
		        double traditionalWeight = roundTwoDecimals(i*assetWeightIncrement);
		        double creditWeight = 0;
		        double cashWeight = 0;
		        double selectionARemainder = roundTwoDecimals(1- traditionalWeight);
		        double increase = 0;
		        while((selectionARemainder-increase)>0){
		        	double d = increase;
		        	creditWeight = roundTwoDecimals(selectionARemainder - d);
		        	cashWeight = roundTwoDecimals(d);
		        	increase += assetWeightIncrement;
		        	weightsThreeAssetPairingSpace.add(PortfolioWeights.createEqtyCrdtCshPorfolioWeights(traditionalWeight, creditWeight, cashWeight));
		        }
//		        PortfolioWeights.createDefaultPorfolioWeights(traditionalWeight, creditWeight, cashWeight); 
		    }
		 return weightsThreeAssetPairingSpace;
	}


	
	/**
	 * Rounding values to 2 decimal places.
	 * 
	 * I want all values rounded to 2dps
	 * 
	 */
	
	public static double  roundTwoDecimals(double d) {
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
	return Double.valueOf(twoDForm.format(d));
	}
	


}
