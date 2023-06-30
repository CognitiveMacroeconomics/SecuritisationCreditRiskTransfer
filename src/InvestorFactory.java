


public class InvestorFactory {
	
	
	public static CPensionFund CreatePensionFund(int investorCount,
			String modelPeriodSting, int dbCollectionCounter, int IDRSSD, Economy econ,
			Enviroment env,
			GeoEconoPoliticalSpace world) {
		// TODO Auto-generated method stub 
		
		
		CPensionFund lapf = null;
		String lapfName = null;
		String lapfSector = null;
		double totalAssets = 0;
		double totalLiabilities = 0;
//		int dbCollectionCounter = 0;
		
		
		//define column from which Assets and liabilities values are to be taken from both 
		//bankAverageLiabilitiesMTPorp30 and bankAverageMortgageLending30
//		if(modelPeriodSting == "2002-2009"){
//			dbCollectionCounter = 1;
//		} else if(modelPeriodSting == "2002-2005"){
//			dbCollectionCounter = 2;
//		} else {
//			dbCollectionCounter = 3;
//		}

//		System.out.println("Data Period: "+modelPeriodSting);
//		System.out.println("Collector Value: "+dbCollectionCounter);
//		if(investorCount == 1){
//			for(int i = 0; i < InvestorNames.investorNames1.length; i++){
//				if(IDRSSD == Integer.parseInt(InvestorNames.investorNames1[i][0])){
//					lapfName = InvestorNames.investorNames1[i][1];
//			}
//		}
//			for(int i = 0; i < GlobalLAPFAssets.avg2002to2009lapfAssets.length; i++){
//				if(IDRSSD == (int) GlobalLAPFAssets.avg2002to2009lapfAssets[i][0]){
//					totalAssets = GlobalLAPFAssets.avg2002to2009lapfAssets[i][dbCollectionCounter];
//				}
//			}//
//			
//			for(int i = 0; i < GlobalLAPFLiabilities.avg2002to2009lapfLiabilities.length; i++){
//				if(IDRSSD == (int) GlobalLAPFLiabilities.avg2002to2009lapfLiabilities[i][0]){
//					totalLiabilities = GlobalLAPFLiabilities.avg2002to2009lapfLiabilities[i][dbCollectionCounter];
////					System.out.println("total Liabilities: "+totalLiabilities);
//
//				}
//			}
	
		
		if(investorCount == 1){
			for(int i = 1; i <= 1; i++){
				if(IDRSSD == Integer.parseInt(InvestorNames.investorNames1[i][0])){
					lapfName = InvestorNames.investorNames1[i][1];
					lapfSector = InvestorNames.investorNames1[i][2];
			}
		}
			for(int i = 0; i < GlobalLAPFAssets.avg2002to2009FTSE350lapfAssets.length; i++){
				if(IDRSSD == (int) GlobalLAPFAssets.avg2002to2009FTSE350lapfAssets[i][0]){
					totalAssets = GlobalLAPFAssets.avg2002to2009FTSE350lapfAssets[i][dbCollectionCounter];
				}
			}
			
			for(int i = 0; i < GlobalLAPFLiabilities.avg2002to2009FTSE350lapfLiabilities.length; i++){
				if(IDRSSD == (int) GlobalLAPFLiabilities.avg2002to2009FTSE350lapfLiabilities[i][0]){
					totalLiabilities = GlobalLAPFLiabilities.avg2002to2009FTSE350lapfLiabilities[i][dbCollectionCounter];
				}
			}



		}
		
		
		if(investorCount > 1){
			for(int i = 2; i < InvestorNames.investorNames1.length; i++){
				if(IDRSSD == Integer.parseInt(InvestorNames.investorNames1[i][0])){
					lapfName = InvestorNames.investorNames1[i][1];
					lapfSector = InvestorNames.investorNames1[i][2];
			}
		}
			for(int i = 0; i < GlobalLAPFAssets.avg2002to2009FTSE350lapfAssets.length; i++){
					totalAssets = (GlobalLAPFAssets.avg2002to2009FTSE350lapfAssets[i][dbCollectionCounter])/investorCount;
			}
			
			for(int i = 0; i < GlobalLAPFLiabilities.avg2002to2009FTSE350lapfLiabilities.length; i++){
					totalLiabilities = (GlobalLAPFLiabilities.avg2002to2009FTSE350lapfLiabilities[i][dbCollectionCounter])/investorCount;
				
			}



		}
	
			
//			public Basel_I_Bank(int IDRSSD, String bankName, double totalAssets,
//					double totalLiabilities, double returnOnAssets,
//					double returnOnLiabilities, double averageRefAssetValue,
//					double assetSurvivalRate, double securitisationCostFactor) {

//			bnk = new Basel_I_Bank(IDRSSD, bankName, totalAssets, totalLiabilities, 
//					returnOnAssets, returnOnLiabilities, averageRefAssetValue, assetSurvivalRate, securitisationCostFactor);

			
//			lapf = new CPensionFund(IDRSSD, lapfName, totalAssets, econ, env, geoRegulatorySpace, geoRegulatoryGrid);

			lapf = new CPensionFund(IDRSSD, lapfName, lapfSector, totalAssets, totalLiabilities, econ, env, world);
		return lapf;
	}

	public static CPensionFund CreatePensionFund(int investorCount,
			String modelPeriodSting, int modelPeriod, int IDRSSD,
			Double userDefinedLAPFAssets, Double userDefinedLAPFLiabilities,
			MarkoseDYangEconomy econ, MarkoseDYangEnvironment env,
			GeoEconoPoliticalSpace world) {
		// TODO Auto-generated method stub
		
		CPensionFund lapf = null;
		String lapfName = null;
		String lapfSector = null;
		double totalAssets = 0;
		double totalLiabilities = 0;

		switch(investorCount){
		case 1: 
			for(int i = 1; i <=1 ; i++){
				if(IDRSSD == Integer.parseInt(InvestorNames.investorNames1[i][0])){
					lapfName = InvestorNames.investorNames1[i][1];
					lapfSector = InvestorNames.investorNames1[i][2];
					totalAssets = (userDefinedLAPFAssets/investorCount);
					totalLiabilities = (userDefinedLAPFLiabilities/investorCount);

				}
			}
		break;
		
		case 220: 
			for(int i = 2; i < InvestorNames.investorNames1.length; i++){//starts from 2 because the values at index 0 and 1 are composites
				if(IDRSSD == Integer.parseInt(InvestorNames.investorNames1[i][0])){
					lapfName = InvestorNames.investorNames1[i][1];
					lapfSector = InvestorNames.investorNames1[i][2];
					totalAssets = (userDefinedLAPFAssets/investorCount);
					totalLiabilities = (userDefinedLAPFLiabilities/investorCount);

				}
			}
		break;
		
		case 10: 
			for(int i = 2; i < InvestorNames.investorNames1.length; i++){//starts from 2 because the values at index 0 and 1 are composites
				if(IDRSSD == Integer.parseInt(InvestorNames.investorNames1[i][0])){
					lapfName = InvestorNames.investorNames1[i][1];
					lapfSector = InvestorNames.investorNames1[i][2];
					totalAssets = (userDefinedLAPFAssets/investorCount);
					totalLiabilities = (userDefinedLAPFLiabilities/investorCount);

				}
			}
		break;
		
		}
		
		lapf = new CPensionFund(IDRSSD, lapfName, lapfSector, totalAssets, totalLiabilities, econ, env, world);
		return lapf;

	}
	

}
