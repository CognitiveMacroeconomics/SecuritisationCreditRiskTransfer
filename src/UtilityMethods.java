

import java.util.Calendar;
import java.util.GregorianCalendar;



public class UtilityMethods {
	
	public static int DefineDataCollectionStartCounter(int dbStartYr, int dbStartQtr, int modelStartYr, int modelStartQtr){
		
		int dataCollectionStartCounter = (4*(modelStartYr - dbStartYr) + 
				(4 - (4 + dbStartQtr - 1)) + modelStartQtr);
		
		return dataCollectionStartCounter;
	}
	
	public static int DefineLoanIssuanceCalenderMonth(int modelStartQtr, DataSchedule schedule){
		
		int multiplier = 0;
		
		if(schedule == DataSchedule.quarterly){
			multiplier = 3;
		}
		
//		int loanIssuanceCalenderMonth = (modelStartQtr*multiplier) - 1;
		
		/**
		 * The following line assumes that loans are issued on the first day of the
		 * quarter following the accounting period/quarter in which the securitisation 
		 * decisions are made
		 * 
		 * NOTE For this it is assumed that the process is that the banks
		 * 1: look at thier assets and liabilities at quater end
		 * 2: determine the securitisation and hence capital accumulation that will impact 
		 * their assets in the next quarter
		 * 3: the loans that are securitised are issued at quarter end 
		 */
		int loanIssuanceCalenderMonth = (modelStartQtr*multiplier);
		
		return loanIssuanceCalenderMonth;
	}
	
	
	public static Calendar CalenderIncrementer(Calendar oldCal, int simTime){
		
		int month = 0;
		int yearAdd = 0;
		int valueStore = 0;
		Calendar newCal;
		
		if(simTime * 3 < 12){
			month = simTime*3;
			yearAdd = 0;
		} else if (simTime * 3 == 12) {
			month = 0;
			yearAdd = 1;
		} else if (simTime * 3 > 12 && (simTime * 3)/12 != 0){
			yearAdd = (simTime * 3)/12;
			month = ((simTime * 3) - 12*yearAdd);
		}else if (simTime * 3 > 12 && (simTime * 3)/12 == 0){
			yearAdd = (simTime * 3)/12;
			month = 0;
		}
		
//		int newYear = oldCal.get(Calendar.YEAR) + yearAdd;
//		
//		int newMonth = oldCal.MONTH + month;
		
		newCal = new GregorianCalendar(oldCal.get(Calendar.YEAR) + yearAdd, month, 1);
		
		
		
		return newCal;
	}



}
