

public class ModelDatabase {

	public double subprimeMortgagePercentage;
	// base on data from The State of the Nation's Housing reports 2000 to 2011,
	// this was
	// 2001						2002					2003
	// 7.21% 7.21% 7.21% 7.21% 6.92% 6.92% 6.92% 6.92% 7.85% 7.85% 7.85% 7.85%
	// 2004							2005						2006Q1
	// 18.50% 18.50% 18.50% 18.50% 20.02% 20.02% 20.02% 20.02% 19.83% 20.63%
	// 21.21% 18.76% 13.68% 7.67% 4.91% 3.10%
	// from 2001Q1 to 2007Q4
	// Note that the quotes for 2001 to 2005 are based on annual numbers and
	// form 2006 to 2007 the quotes are quarterly
	public double servreHousingBurdenPercentage;
	public double moderateToServreHousingBurdenPercentage;
	// based on State of The Nation's Housing reports' data
	// this was 21.41% 23.94% 23.94% 24.82% 25.73% 27.57% 29.51% 30.18% 30.36%
	// 30.19% from 2000 to 2009
	// Note that there was no data for 2002 so it is assumed that 2001 figures
	// are carried forward to 2002.

}
