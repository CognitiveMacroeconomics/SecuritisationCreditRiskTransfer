





public class BankFactory {
	
	private static String period1 = "2002-2009";  
	private static String period2 = "2002-2005";
	private static String period3 = "2006-2009";
	
	/*private static double totalAssets;
	private static double totalLiabilities*/;
	
	//REPAST FACTORY METHOD
	public static Bank CreatBanks(int bankCount, int modelType, int dbCollectionCounter, int IDRSSD, 
			Economy econ, Enviroment env, GeoEconoPoliticalSpace world, 
			double returnOnAssets, double returnOnLiabilities){
		
		Bank bnk = null;
		
		if(modelType == 1){
			bnk = CreateMarkoseDangBaselOneBank(bankCount, dbCollectionCounter, IDRSSD, 
						econ, env, world,returnOnAssets, returnOnLiabilities);
		}
		
		
		return bnk;
	}
	
	

	
	public static Basel_I_Bank CreateMarkoseDangBaselOneBank(int bankCount, int dbCollectionCounter, int IDRSSD,
			Economy econ, Enviroment env, GeoEconoPoliticalSpace world, double returnOnAssets, double returnOnLiabilities){
		
		Basel_I_Bank bnk = null;
		String bankName = null;
		double totalAssets = 0;
		double totalLiabilities = 0;

		
		if(bankCount == 97){
			for(int i = 0; i < BankNames.bankNames97.length; i++){
				if(IDRSSD == Integer.parseInt(BankNames.bankNames97[i][0])){
				bankName = BankNames.bankNames97[i][1];
			}
		}
		}
		if(bankCount == 97){
			
			for(int i = 0; i < BankMortgageLending.bankMortgageLending.length; i++){
				if(IDRSSD == (int) BankMortgageLending.bankMortgageLending[i][0]){
					totalAssets = BankMortgageLending.bankMortgageLending[i][dbCollectionCounter];
				}
			}
		}
		if(bankCount == 97){
			
			for(int i = 0; i < BankLiabilitiesMTProp.bankLiabilitiesMTProp.length; i++){
				if(IDRSSD == (int) BankLiabilitiesMTProp.bankLiabilitiesMTProp[i][0]){
					totalLiabilities = BankLiabilitiesMTProp.bankLiabilitiesMTProp[i][dbCollectionCounter];
				}
			}
			
		}


		if(bankCount == 244){
			for(int i = 0; i < BankNames.bankNames244.length; i++){
				if(IDRSSD == Integer.parseInt(BankNames.bankNames244[i][0])){
					bankName = BankNames.bankNames244[i][1];
				}
			}
		}
		
		
		if(bankCount == 244){
			totalAssets = (TotalsBankMortgageLending.bankMortgageLending[1][dbCollectionCounter])/
							(BankNames.bankNames244.length);
			totalLiabilities = (TotalsBankLiabilitiesMTProp.bankLiabilitiesMTProp[1][dbCollectionCounter])/
					(BankNames.bankNames244.length);
		}

		
		
			
//			public Basel_I_Bank(int IDRSSD, String bankName, double totalAssets,
//					double totalLiabilities, double returnOnAssets,
//					double returnOnLiabilities, double averageRefAssetValue,
//					double assetSurvivalRate, double securitisationCostFactor) {

//			bnk = new Basel_I_Bank(IDRSSD, bankName, totalAssets, totalLiabilities, 
//					returnOnAssets, returnOnLiabilities, averageRefAssetValue, assetSurvivalRate, securitisationCostFactor);
			
			bnk = new Basel_I_Bank(IDRSSD, bankName, totalAssets, totalLiabilities, econ, env, world,
					returnOnAssets, returnOnLiabilities);
			
			//this sets the kick in date for those banks that have no data at the simulation starting period
			if(bnk.getTotalAssets() == 0){
				for(int i = 0; i < BankMortgageLending.bankMortgageLending.length; i++){
					if(bnk.getRSSID() == (int) BankMortgageLending.bankMortgageLending[i][0]){
						double value = 0;
						for(int j = dbCollectionCounter; j < BankMortgageLending.bankMortgageLending[i].length; j++){
							
//							System.out.println(bnk.getBankName());
//							System.out.println(value);
//							System.out.println(BankMortgageLending.bankMortgageLending[i][j]);
							if(BankMortgageLending.bankMortgageLending[i][j] > 0 
									&& value == 0.0){
								value = value + BankMortgageLending.bankMortgageLending[i][j];
								bnk.setPeriodCountIn(j);
							}
						}
					}
				}
			}




		
			
//		double returnOnAssets,
//				double returnOnLiabilities, double averageRefAssetValue,
//				double assetSurvivalRate, double securitisationCostFactor)
		
		return bnk;
		
	}
	

	
	public static Bank CreatBanks(int bankCount, int modelType, int dbCollectionCounter, int IDRSSD, double returnOnAssets, double returnOnLiabilities,
			double averageRefAssetValue, double assetSurvivalRate, double securitisationCostFactor){
		
		Bank bnk = null;
		
		if(modelType == 1){
			bnk = CreateMarkoseDangBaselOneBank(bankCount, dbCollectionCounter, IDRSSD, returnOnAssets, returnOnLiabilities,
					averageRefAssetValue, assetSurvivalRate, securitisationCostFactor);
		}
		
		
		return bnk;
	}
	
	
	
	
	
	
	public static Basel_I_Bank CreateMarkoseDangBaselOneBank(int bankCount, int dbCollectionCounter, int IDRSSD, double returnOnAssets,
			double returnOnLiabilities,	double averageRefAssetValue, double assetSurvivalRate, double securitisationCostFactor){
		
		Basel_I_Bank bnk = null;
		String bankName = null;
		double totalAssets = 0;
		double totalLiabilities = 0;

		
		if(bankCount == 97){
			for(int i = 0; i < BankNames.bankNames97.length; i++){
				if(IDRSSD == Integer.parseInt(BankNames.bankNames97[i][0])){
				bankName = BankNames.bankNames97[i][1];
			}
		}
		}
		if(bankCount == 97){
			
			for(int i = 0; i < BankMortgageLending.bankMortgageLending.length; i++){
				if(IDRSSD == (int) BankMortgageLending.bankMortgageLending[i][0]){
					totalAssets = BankMortgageLending.bankMortgageLending[i][dbCollectionCounter];
				}
			}
		}
		if(bankCount == 97){
			
			for(int i = 0; i < BankLiabilitiesMTProp.bankLiabilitiesMTProp.length; i++){
				if(IDRSSD == (int) BankLiabilitiesMTProp.bankLiabilitiesMTProp[i][0]){
					totalLiabilities = BankLiabilitiesMTProp.bankLiabilitiesMTProp[i][dbCollectionCounter];
				}
			}
			
		}


		if(bankCount == 244){
			for(int i = 0; i < BankNames.bankNames244.length; i++){
				if(IDRSSD == Integer.parseInt(BankNames.bankNames244[i][0])){
					bankName = BankNames.bankNames244[i][1];
				}
			}
		}
		
		
		if(bankCount == 244){
			totalAssets = (TotalsBankMortgageLending.bankMortgageLending[1][dbCollectionCounter])/
							(BankNames.bankNames244.length);
			totalLiabilities = (TotalsBankLiabilitiesMTProp.bankLiabilitiesMTProp[1][dbCollectionCounter])/
					(BankNames.bankNames244.length);
		}

		
		
			
//			public Basel_I_Bank(int IDRSSD, String bankName, double totalAssets,
//					double totalLiabilities, double returnOnAssets,
//					double returnOnLiabilities, double averageRefAssetValue,
//					double assetSurvivalRate, double securitisationCostFactor) {

//			bnk = new Basel_I_Bank(IDRSSD, bankName, totalAssets, totalLiabilities, 
//					returnOnAssets, returnOnLiabilities, averageRefAssetValue, assetSurvivalRate, securitisationCostFactor);
			
			bnk = new Basel_I_Bank(IDRSSD, bankName, totalAssets, totalLiabilities, 
					returnOnAssets, returnOnLiabilities, assetSurvivalRate, securitisationCostFactor);
			
			//this sets the kick in date for those banks that have no data at the simulation starting period
			if(bnk.getTotalAssets() == 0){
				for(int i = 0; i < BankMortgageLending.bankMortgageLending.length; i++){
					if(bnk.getRSSID() == (int) BankMortgageLending.bankMortgageLending[i][0]){
						double value = 0;
						for(int j = dbCollectionCounter; j < BankMortgageLending.bankMortgageLending[i].length; j++){
							
//							System.out.println(bnk.getBankName());
//							System.out.println(value);
//							System.out.println(BankMortgageLending.bankMortgageLending[i][j]);
							if(BankMortgageLending.bankMortgageLending[i][j] > 0 
									&& value == 0.0){
								value = value + BankMortgageLending.bankMortgageLending[i][j];
								bnk.setPeriodCountIn(j);
							}
						}
					}
				}
			}




		
			
//		double returnOnAssets,
//				double returnOnLiabilities, double averageRefAssetValue,
//				double assetSurvivalRate, double securitisationCostFactor)
		
		return bnk;
		
	}




	public static BankMarkoseDYanBaselI CreatBanks(int bankCount,
			String modelPeriodSting, int dbCollectionCounter, int IDRSSD, Economy econ,
			Enviroment env,
			GeoEconoPoliticalSpace world, double bankAssetReturn,
			double bankLiabilityExpense) {
		// TODO Auto-generated method stub 
		
		
		BankMarkoseDYanBaselI bnk = null;
		String bankName = null;
		double totalAssets = 0;
		double totalLiabilities = 0;
//		int dbCollectionCounter = 0;
		

		
		//define column from which Assets and liabilities values are to be taken from both 
		//bankAverageLiabilitiesMTPorp30 and bankAverageMortgageLending30
//		if(modelPeriodSting == period1){
//			dbCollectionCounter = 1;
//		} else if(modelPeriodSting == period2){
//			dbCollectionCounter = 2;
//		} else {
//			dbCollectionCounter = 3;
//		}
//		System.out.println("Banks: ");
//		System.out.println("Data Period: "+modelPeriodSting);
//		System.out.println("Collector Value: "+dbCollectionCounter);

		
		if(bankCount == 35){
			for(int i = 0; i < BankNames.bankNames30.length; i++){
				if(IDRSSD == Integer.parseInt(BankNames.bankNames30[i][0])){
				bankName = BankNames.bankNames30[i][1];
			}
		}
			for(int i = 0; i < BankAverageMortgageLending.bankAverageMortgageLending30.length; i++){
				if(IDRSSD == (int) BankAverageMortgageLending.bankAverageMortgageLending30[i][0]){
					totalAssets = BankAverageMortgageLending.bankAverageMortgageLending30[i][dbCollectionCounter];
				}
			}
			
			for(int i = 0; i < BankAverageLiabilitiesMTProp.bankAverageLiabilitiesMTPorp30.length; i++){
				if(IDRSSD == (int) BankAverageLiabilitiesMTProp.bankAverageLiabilitiesMTPorp30[i][0]){
					totalLiabilities = BankAverageLiabilitiesMTProp.bankAverageLiabilitiesMTPorp30[i][dbCollectionCounter];
				}
			}
		}
	
			
//			public Basel_I_Bank(int IDRSSD, String bankName, double totalAssets,
//					double totalLiabilities, double returnOnAssets,
//					double returnOnLiabilities, double averageRefAssetValue,
//					double assetSurvivalRate, double securitisationCostFactor) {

//			bnk = new Basel_I_Bank(IDRSSD, bankName, totalAssets, totalLiabilities, 
//					returnOnAssets, returnOnLiabilities, averageRefAssetValue, assetSurvivalRate, securitisationCostFactor);
			
			bnk = new BankMarkoseDYanBaselI(IDRSSD, bankName, totalAssets, totalLiabilities, econ, env, world,
					bankAssetReturn, bankLiabilityExpense);
		return bnk;
	}




	public static BankMarkoseDYanBaselI CreatBanks(int bankCount,
			String modelPeriodSting, int dbCollectionCounter, int IDRSSD,
			MarkoseDYangEconomy econ, MarkoseDYangEnvironment env,
			GeoEconoPoliticalSpace world, double userDefinedBankAssets,
			double userDefinedBankLiabilities, double bankAssetReturn,
			double bankLiabilityExpense) {
		// TODO Auto-generated method stub

		BankMarkoseDYanBaselI bnk = null;
		String bankName = null;
		double totalAssets = 0;
		double totalLiabilities = 0;
//		int dbCollectionCounter = 0;
		

		
		//define column from which Assets and liabilities values are to be taken from both 
		//bankAverageLiabilitiesMTPorp30 and bankAverageMortgageLending30
//		if(modelPeriodSting == period1){
//			dbCollectionCounter = 1;
//		} else if(modelPeriodSting == period2){
//			dbCollectionCounter = 2;
//		} else {
//			dbCollectionCounter = 3;
//		}
//		System.out.println("Banks: ");
//		System.out.println("Data Period: "+modelPeriodSting);
//		System.out.println("Collector Value: "+dbCollectionCounter);
		
		switch(bankCount){
		case 35: 
			for(int i = 0; i < BankNames.bankNames30.length; i++){
				if(IDRSSD == Integer.parseInt(BankNames.bankNames30[i][0])){
				bankName = BankNames.bankNames30[i][1];
				totalAssets = (userDefinedBankAssets/BankNames.bankNames30.length);
				totalLiabilities = (userDefinedBankLiabilities/BankNames.bankNames30.length);
				}
			}
		break;
			
		case 97: 
			for(int i = 0; i < BankNames.bankNames97.length; i++){
				if(IDRSSD == Integer.parseInt(BankNames.bankNames97[i][0])){
				bankName = BankNames.bankNames97[i][1];
				totalAssets = (userDefinedBankAssets/BankNames.bankNames97.length);
				totalLiabilities = (userDefinedBankLiabilities/BankNames.bankNames97.length);
				}
			}
		break;
		
		case 244: 
			for(int i = 0; i < BankNames.bankNames244.length; i++){
				if(IDRSSD == Integer.parseInt(BankNames.bankNames244[i][0])){
				bankName = BankNames.bankNames244[i][1];
				totalAssets = (userDefinedBankAssets/BankNames.bankNames244.length);
				totalLiabilities = (userDefinedBankLiabilities/BankNames.bankNames244.length);
				}
			}
		break;


				
				
		}

		
//		if(bankCount == 35){
//			for(int i = 0; i < BankNames.bankNames30.length; i++){
//				if(IDRSSD == Integer.parseInt(BankNames.bankNames30[i][0])){
//				bankName = BankNames.bankNames30[i][1];
//				totalAssets = (userDefinedBankAssets/BankNames.bankNames30.length);
//				totalLiabilities = (userDefinedBankLiabilities/BankNames.bankNames30.length);
//				}
//			}
//		}

			
			bnk = new BankMarkoseDYanBaselI(IDRSSD, bankName, totalAssets, totalLiabilities, econ, env, world,
					bankAssetReturn, bankLiabilityExpense);
		return bnk;


	}
	
	
//	private static void setAssets(double assets){
//		totalAssets = assets;
//	}
//	
//	
//	private static void setLiabilities(double liab){
//		totalLiabilities = liab;
//	}
//

}
