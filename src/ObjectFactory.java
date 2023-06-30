

import java.util.Calendar;



public class ObjectFactory {

	public static Borrower createNewBorrower(BorrowerType type,
			double assetValueMin, double assetValueMax, double avgIncomeMin,
			double avgIncomeMax, double borrowerDefaultPropensity) {
		double assetValue = assetValueMin
				+ (Math.random() * (assetValueMax - assetValueMin));// create a
																	// asset
																	// value
																	// based on
																	// average
																	// market
																	// data

		AssetType astType = AssetType.Cash;
		if (type != BorrowerType.corporate || type != BorrowerType.bank) {
			astType = AssetType.ResidentialProperty;
		} else {

		}

		Asset asset = ObjectFactory.createNewAsset(assetValue, astType);

		int ficoRating = ObjectFactory.getRatings(type);
		double earnings = avgIncomeMin
				+ (Math.random() * (avgIncomeMax - avgIncomeMin));// create a
																	// earnings'income
																	// level
																	// based on
																	// average
																	// market
																	// data
		PersonalCreditRatings creditRating = ObjectFactory
				.getPCRatings(ficoRating);

		Borrower borrower = null;

		if (type != BorrowerType.corporate || type != BorrowerType.bank) {
			borrower = new Borrower(type, earnings, ficoRating, creditRating,
					asset);
		} else {

		}
		return borrower;
	}

	public static Borrower createNewBorrower(BorrowerType type,
			double assetValue, double avgIncomeMin, double avgIncomeMax, double borrowerDefaultPropensity) {

		AssetType astType = AssetType.Cash;
		if (type != BorrowerType.corporate || type != BorrowerType.bank) {
			astType = AssetType.ResidentialProperty;
		} else {

		}

		Asset asset = ObjectFactory.createNewAsset(assetValue, astType);

		int ficoRating = ObjectFactory.getRatings(type);
		double earnings = avgIncomeMin
				+ (Math.random() * (avgIncomeMax - avgIncomeMin));// create a
																	// earnings'income
																	// level
																	// based on
																	// average
																	// market
																	// data
		PersonalCreditRatings creditRating = ObjectFactory
				.getPCRatings(ficoRating);

		Borrower borrower = null;

		if (type != BorrowerType.corporate || type != BorrowerType.bank) {
			borrower = new Borrower(type, earnings, ficoRating, creditRating,
					asset);
		} else {

		}
		return borrower;
	}

	public static Asset createNewAsset(double value, AssetType type) {
		Asset asset = new Asset(value, type);
		return asset;
	}

	public static Loan createNewLoan(int issuerID, int issuerRSSID,
			int borrowerID, int maturity, String issuerName, double loanAmount,
			double contractRate, RateType rateType, AssetType assetType,
			Asset asset, Calendar startDate, Calendar reset,
			PaymentSchedule paymentSchedule) {
		Loan loan = new Loan(issuerID, borrowerID, maturity, issuerRSSID,
				issuerName, loanAmount, contractRate, rateType, assetType,
				asset, startDate, reset, paymentSchedule);
		return loan;
	}

	private static int getRatings(BorrowerType type) {
		int rating = 0;

		if (type != BorrowerType.corporate || type != BorrowerType.bank) {

			rating = 300 + (int) (Math.random() * (850 - 300) + 1);// create
																	// a
																	// random
																	// fico
																	// rating
		}
		return rating;

	}

	private static PersonalCreditRatings getPCRatings(int rating) {

		PersonalCreditRatings creditRating = PersonalCreditRatings.fico_350_579;

		if (rating < 580) {
			creditRating = PersonalCreditRatings.fico_350_579;
		} else if (580 <= rating || rating < 620) {
			creditRating = PersonalCreditRatings.fico_580_619;
		} else if (620 <= rating || rating < 680) {
			creditRating = PersonalCreditRatings.fico_620_679;
		} else if (620 <= rating || rating < 680) {
			creditRating = PersonalCreditRatings.fico_620_679;
		} else if (680 <= rating || rating < 720) {
			creditRating = PersonalCreditRatings.fico_680_719;
		} else if (720 <= rating || rating <= 850) {
			creditRating = PersonalCreditRatings.fico_720_850;
		}

		return creditRating;

	}

	
	public static Borrower createNewBorrower(BorrowerType type,
			double assetValue, double borrowerAvgIncome, double borrowerDefaultPropensity) {

		AssetType astType = AssetType.Cash;
		if (type != BorrowerType.corporate || type != BorrowerType.bank) {
			astType = AssetType.ResidentialProperty;
		} else {

		}

		Asset asset = ObjectFactory.createNewAsset(assetValue, astType);

		int ficoRating = ObjectFactory.getRatings(type);
		double earnings = borrowerAvgIncome;
		PersonalCreditRatings creditRating = ObjectFactory
				.getPCRatings(ficoRating);

		Borrower borrower = null;

		if (type != BorrowerType.corporate || type != BorrowerType.bank) {
			borrower = new Borrower(type, earnings, ficoRating, creditRating,
					asset, borrowerDefaultPropensity);
		} else {

		}
		return borrower;
	}

}
