
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * 
 * @author Oluwasegun Bewaji
 * 
 *         This class is a utility/engine class used to compute the transition
 *         probabilities of a stochastic paths
 * 
 *         it must be constructed with a given stochastic path(s) passed as an
 *         Array Where two paths are used to construct the engine, the path
 *         lengths for both paths must be identical
 * 
 *         Note: The default implementation of this class assumes that there are
 *         only 3 possible states any given asset whose path the engine is used
 *         to back out the transition probabilities.
 * 
 *         The possible states are that the asset value or rate index moves: 1:
 *         up (positive) 2: down (negative) 3: flat (zero)
 * 
 * 
 */
public class TransitionProbabilitiesEngine {

	// 2D arrays to store the transition probabilities of the assets
	// note this class implements the very basic model so -ve,flat,+ve i.e.
	// {-1,0,1} are the only states
	// for ease of understanding first build out the individual adjacent
	// transition probabilities then merge into the transition matrix
	double[] assetTraditional_Negative_to_NegFlatPos;
	double[] assetTraditional_Flat_to_NegFlatPos;
	double[] assetTraditional_Positive_to_NegFlatPos;

	double[][] assetTraditionalTransitionProbabilityMatrix;

	double[] assetCredit_Negative_to_NegFlatPos;
	double[] assetCredit_Flat_to_NegFlatPos;
	double[] assetCredit_Positive_to_NegFlatPos;

	double[][] assetCreditTransitionProbabilityMatrix;

	double[] assetTraditionalStochasticPath;
	double[] assetCreditStochasticPath;
	double initialAssetCreditReturn;
	double[] assetTraditionalReturnsPath;
	double[] assetCreditReturnsPath;

	int pathLength;
	double initialAssetTraditionalReturn;

	// the following list of integers are used to count the transitions from
	// -ve to +ve etc for asset A
	double assetTraditionalNegNextPositive;
	double assetTraditionalNegNextFlat;
	double assetTraditionalNegNextNegative;

	double assetTraditionalFltNextPositive;
	double assetTraditionalFltNextFlat;
	double assetTraditionalFltNextNegative;

	double assetTraditionalPosNextPositive;
	double assetTraditionalPosNextFlat;
	double assetTraditionalPosNextNegative;

	// the following list of integers are used to count the transitions from
	// -ve to +ve etc for asset B
	double assetCreditNegNextPositive;
	double assetCreditNegNextFlat;
	double assetCreditNegNextNegative;

	double assetCreditFltNextPositive;
	double assetCreditFltNextFlat;
	double assetCreditFltNextNegative;

	double assetCreditPosNextPositive;
	double assetCreditPosNextFlat;
	double assetCreditPosNextNegative;

	// the following is the list of probabilities of transitioning from
	// -ve to +ve etc for asset A
	double assetTraditionalProbabilityNegNextPositive;
	double assetTraditionalProbabilityNegNextFlat;
	double assetTraditionalProbabilityNegNextNegative;

	double assetTraditionalProbabilityFltNextPositive;
	double assetTraditionalProbabilityFltNextFlat;
	double assetTraditionalProbabilityFltNextNegative;

	double assetTraditionalProbabilityPosNextPositive;
	double assetTraditionalProbabilityPosNextFlat;
	double assetTraditionalProbabilityPosNextNegative;

	// the following is the list of probabilities of transitioning from
	// -ve to +ve etc for asset B
	double assetCreditProbabilityNegNextPositive;
	double assetCreditProbabilityNegNextFlat;
	double assetCreditProbabilityNegNextNegative;

	double assetCreditProbabilityFltNextPositive;
	double assetCreditProbabilityFltNextFlat;
	double assetCreditProbabilityFltNextNegative;

	double assetCreditProbabilityPosNextPositive;
	double assetCreditProbabilityPosNextFlat;
	double assetCreditProbabilityPosNextNegative;

	// the following is the list of Return of transitioning from
	// -ve to +ve etc for asset A
	double assetTraditionalReturnNegNextPositive;
	double assetTraditionalReturnNegNextFlat;
	double assetTraditionalReturnNegNextNegative;

	double assetTraditionalReturnFltNextPositive;
	double assetTraditionalReturnFltNextFlat;
	double assetTraditionalReturnFltNextNegative;

	double assetTraditionalReturnPosNextPositive;
	double assetTraditionalReturnPosNextFlat;
	double assetTraditionalReturnPosNextNegative;

	// the following is the list of Return of transitioning from
	// -ve to +ve etc for asset B
	double assetCreditReturnNegNextPositive;
	double assetCreditReturnNegNextFlat;
	double assetCreditReturnNegNextNegative;

	double assetCreditReturnFltNextPositive;
	double assetCreditReturnFltNextFlat;
	double assetCreditReturnFltNextNegative;

	double assetCreditReturnPosNextPositive;
	double assetCreditReturnPosNextFlat;
	double assetCreditReturnPosNextNegative;

	public List<AdjacencyMatrixContainer> STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX = new ArrayList<AdjacencyMatrixContainer>();
	private double assetCreditStateAverageNegativeReturns;
	private double assetCreditStateAverageFlatReturns;
	private double assetCreditStateAveragePositiveReturns;
	private double assetTraditionalStateAverageNegativeReturns;
	private double assetTraditionalStateAverageFlatReturns;
	private double assetTraditionalStateAveragePositiveReturns;

	private double[] assetCreditAverageReturns;
	private double[] assetTraditionalAverageReturns;

	/**
	 * Two asset Path Constructor used to compute the joined transition
	 * probability matrix for 1 asset
	 * 
	 * @param path
	 */
	public TransitionProbabilitiesEngine(double[] path, double initAReturn) {
		this.assetTraditionalStochasticPath = path;
		this.initialAssetTraditionalReturn = initAReturn;
		this.pathLength = this.assetTraditionalStochasticPath.length;
		computeAssetTraditionalReturnsPath();
		computeAssetTraditionalStateTransitionCounts();
		computeAssetTraditionalStateTransitionProbabilities();
	}

	/**
	 * Two asset Path Constructor used to compute the joined transition
	 * probability matrix for 2 assets
	 * 
	 * @param path1
	 * @param path2
	 */
	public TransitionProbabilitiesEngine(double[] path1, double[] path2,
			double initAReturn, double initBReturn) {
		this.assetTraditionalStochasticPath = path1;
		this.assetCreditStochasticPath = path2;
		this.initialAssetTraditionalReturn = initAReturn;
		this.initialAssetCreditReturn = initBReturn;

		if (this.assetTraditionalStochasticPath.length == this.assetCreditStochasticPath.length) {
			this.pathLength = this.assetTraditionalStochasticPath.length;
			this.computeAssetTraditionalReturnsPath();
			this.computeAssetCreditReturnsPath();
			this.computeAssetTraditionalStateTransitionCounts();
			this.computeAssetCreditStateTransitionCounts();
			this.computeAssetTraditionalStateTransitionProbabilities();
			this.computeAssetCreditStateTransitionProbabilities();// computeAssetCreditStateTransitionProbabilities()
			this.computeTwoindependentAssetJointTransitionProbabilities();
			this.computeAssetCreditStateAverageReturns();
			this.computeAssetTraditionalStateAverageReturns();

		} else {
			JOptionPane.showMessageDialog(null, "ErrorMsg",
					"Stochastic Path Lengths must be Identical",
					JOptionPane.ERROR_MESSAGE);

		}
	}

	/**
	 * Computes the path of returns of asset A given the stochastic path and an
	 * initial return value defined in the class construction
	 */
	private void computeAssetTraditionalReturnsPath() {
		// initialize the returns path
		this.assetTraditionalReturnsPath = new double[this.pathLength];
		this.assetTraditionalReturnsPath[0] = this.initialAssetTraditionalReturn;// set
																					// initial
		// return value
		for (int i = 1; i < this.assetTraditionalStochasticPath.length; i++) { // for
																				// each
			// index
			// level
			// in
			// the
			// path
			// compute
			// the
			// return
			// as
			// the
			// natural
			// log
			// of the current index level divided by the previous index level
			// this.assetTraditionalReturnsPath[i] = Rounding
			// .roundFourDecimals(Math
			// .log((this.assetTraditionalStochasticPath[i] /
			// this.assetTraditionalStochasticPath[i - 1])));
			this.assetTraditionalReturnsPath[i] = Rounding
					.roundFourDecimals(((this.assetTraditionalStochasticPath[i] - this.assetTraditionalStochasticPath[i - 1]) / this.assetTraditionalStochasticPath[i - 1]));

			// this.assetTraditionalReturnsPath[i] = Rounding
			// .roundFourDecimals(((this.assetTraditionalStochasticPath[i] -
			// this.assetTraditionalStochasticPath[0])
			// / this.assetTraditionalStochasticPath[0]));

			// this.assetTraditionalReturnsPath[i] = Rounding
			// .roundFourDecimals(Math.log(this.assetTraditionalStochasticPath[i]
			// / this.assetTraditionalStochasticPath[0]));

		}

	}

	/**
	 * Computes the path of returns of asset B given the stochastic path and an
	 * initial return value defined in the class construction
	 */
	private void computeAssetCreditReturnsPath() {
		// initialize the returns path
		this.assetCreditReturnsPath = new double[this.pathLength];
		this.assetCreditReturnsPath[0] = this.initialAssetCreditReturn;// set
																		// initial
		// return value
		for (int i = 1; i < this.assetCreditStochasticPath.length; i++) { // for
																			// each
																			// index
																			// level
																			// in
																			// the
																			// path
																			// compute
																			// the
																			// return
																			// as
																			// the
																			// natural
																			// log
			// of the current index level divided by the previous index level
			// this.assetCreditReturnsPath[i] = Rounding
			// .roundFourDecimals(Math
			// .log((this.assetCreditStochasticPath[i] /
			// this.assetCreditStochasticPath[i - 1])));
			this.assetCreditReturnsPath[i] = Rounding
					.roundFourDecimals(((this.assetCreditStochasticPath[i] - this.assetCreditStochasticPath[i - 1]) / this.assetCreditStochasticPath[i - 1]));

			// this.assetCreditReturnsPath[i] = Rounding
			// .roundFourDecimals(((this.assetCreditStochasticPath[i] -
			// this.assetCreditStochasticPath[0]) /
			// this.assetCreditStochasticPath[0]));

			// this.assetCreditReturnsPath[i] = Rounding
			// .roundFourDecimals(Math.log(this.assetCreditStochasticPath[i]/
			// this.assetCreditStochasticPath[0]));

		}

	}

	private void computeAssetTraditionalStateTransitionCounts() {
		//
		int ntop = 0;
		int ntof = 0;
		int nton = 0;

		int ztop = 0;
		int ztof = 0;
		int zton = 0;

		int ptop = 0;
		int ptof = 0;
		int pton = 0;

		double ntopRetSum = 0;
		double ntofRetSum = 0;
		double ntonRetSum = 0;

		double ztopRetSum = 0;
		double ztofRetSum = 0;
		double ztonRetSum = 0;

		double ptopRetSum = 0;
		double ptofRetSum = 0;
		double ptonRetSum = 0;

		/**
		 * for each return element of the path of stochastic returns determine
		 * if the current return is +ve/-ve/0 then also compute the average
		 * returns in each of these states Note that the arithmetic mean is used
		 * because the objective is to capture the extreme variations in the
		 * changes in return Using the Geometric mean would dampen down these
		 * wide variations and and not capture the full risk exposures.
		 * 
		 * */

		for (int i = 0; i < this.assetTraditionalReturnsPath.length - 1; i++) {
			double rt0 = this.assetTraditionalReturnsPath[i];
			double rt1 = this.assetTraditionalReturnsPath[i + 1];
			// if the current return is negative
			if (rt0 < 0 && rt1 < 0) {
				nton++;
				ntonRetSum += rt1;
			} else if (rt0 < 0 && rt1 == 0) {
				ntof++;
				ntofRetSum += rt1;
			} else if (rt0 < 0 && rt1 > 0) {
				ntop++;
				ntopRetSum += rt1;
			}

			// if the current return is zero
			if (rt0 == 0 && rt1 < 0) {
				zton++;
				ztonRetSum += rt1;
			} else if (rt0 == 0 && rt1 == 0) {
				ztof++;
				ztofRetSum += rt1;
			} else if (rt0 == 0 && rt1 > 0) {
				ztop++;
				ztopRetSum += rt1;
			}

			// if the current return is positive
			if (rt0 > 0 && rt1 < 0) {
				pton++;
				ptonRetSum += rt1;
			} else if (rt0 > 0 && rt1 == 0) {
				ptof++;
				ptofRetSum += rt1;
			} else if (rt0 > 0 && rt1 > 0) {
				ptop++;
				ptopRetSum += rt1;
			}
		}

		// Set Count Values
		this.assetTraditionalNegNextPositive = ntop;
		this.assetTraditionalNegNextFlat = ntof;
		this.assetTraditionalNegNextNegative = nton;

		this.assetTraditionalFltNextPositive = ztop;
		this.assetTraditionalFltNextFlat = ztof;
		this.assetTraditionalFltNextNegative = zton;

		this.assetTraditionalPosNextPositive = ptop;
		this.assetTraditionalPosNextFlat = ptof;
		this.assetTraditionalPosNextNegative = pton;

		this.assetTraditionalReturnNegNextPositive = Rounding
				.roundFourDecimals(ntopRetSum
						/ this.assetTraditionalNegNextPositive);
		this.assetTraditionalReturnNegNextFlat = Rounding
				.roundFourDecimals(ntofRetSum
						/ this.assetTraditionalNegNextFlat);
		this.assetTraditionalReturnNegNextNegative = Rounding
				.roundFourDecimals(ntonRetSum
						/ this.assetTraditionalNegNextNegative);

		this.assetTraditionalReturnFltNextPositive = Rounding
				.roundFourDecimals(ztopRetSum
						/ this.assetTraditionalFltNextPositive);
		this.assetTraditionalReturnFltNextFlat = Rounding
				.roundFourDecimals(ztofRetSum
						/ this.assetTraditionalFltNextFlat);
		this.assetTraditionalReturnFltNextNegative = Rounding
				.roundFourDecimals(ztonRetSum
						/ this.assetTraditionalFltNextNegative);

		this.assetTraditionalReturnPosNextPositive = Rounding
				.roundFourDecimals(ptopRetSum
						/ this.assetTraditionalPosNextPositive);
		this.assetTraditionalReturnPosNextFlat = Rounding
				.roundFourDecimals(ptofRetSum
						/ this.assetTraditionalPosNextFlat);
		this.assetTraditionalReturnPosNextNegative = Rounding
				.roundFourDecimals(ptonRetSum
						/ this.assetTraditionalPosNextNegative);

	}

	private void computeAssetCreditStateTransitionCounts() {
		//
		int ntop = 0;
		int ntof = 0;
		int nton = 0;

		int ztop = 0;
		int ztof = 0;
		int zton = 0;

		int ptop = 0;
		int ptof = 0;
		int pton = 0;

		double ntopRetSumB = 0;
		double ntofRetSumB = 0;
		double ntonRetSumB = 0;

		double ztopRetSumB = 0;
		double ztofRetSumB = 0;
		double ztonRetSumB = 0;

		double ptopRetSumB = 0;
		double ptofRetSumB = 0;
		double ptonRetSumB = 0;

		/**
		 * for each return element of the path of stochastic returns determine
		 * if the current return is +ve/-ve/0 then also compute the average
		 * returns in each of these states Note that the arithmetic mean is used
		 * because the objective is to capture the extreme variations in the
		 * changes in return Using the Geometric mean would dampen down these
		 * wide variations and and not capture the full risk exposures.
		 * 
		 * */
		for (int i = 1; i < this.assetCreditReturnsPath.length - 1; i++) {
			double rt0 = this.assetCreditReturnsPath[i - 1];
			double rt1 = this.assetCreditReturnsPath[i];
			// if the current return is negative
			if (rt0 < 0 && rt1 < 0) {
				nton++;
				ntonRetSumB += rt1;
			} else if (rt0 < 0 && rt1 == 0) {
				ntof++;
				ntofRetSumB += rt1;
			} else if (rt0 < 0 && rt1 > 0) {
				ntop++;
				ntopRetSumB += rt1;
			}

			// if the current return is zero
			if (rt0 == 0 && rt1 < 0) {
				zton++;
				ztonRetSumB += rt1;
			} else if (rt0 == 0 && rt1 == 0) {
				ztof++;
				ztofRetSumB += rt1;
			} else if (rt0 == 0 && rt1 > 0) {
				ztop++;
				ztopRetSumB += rt1;
			}

			// if the current return is positive
			if (rt0 > 0 && rt1 < 0) {
				pton++;
				ptonRetSumB += rt1;
			} else if (rt0 > 0 && rt1 == 0) {
				ptof++;
				ptofRetSumB += rt1;
			} else if (rt0 > 0 && rt1 > 0) {
				ptop++;
				ptopRetSumB += rt1;
			}
		}

		// Set Count Values
		this.assetCreditNegNextPositive = ntop;
		this.assetCreditNegNextFlat = ntof;
		this.assetCreditNegNextNegative = nton;

		this.assetCreditFltNextPositive = ztop;
		this.assetCreditFltNextFlat = ztof;
		this.assetCreditFltNextNegative = zton;

		this.assetCreditPosNextPositive = ptop;
		this.assetCreditPosNextFlat = ptof;
		this.assetCreditPosNextNegative = pton;

		this.assetCreditReturnNegNextPositive = Rounding
				.roundFourDecimals(ntopRetSumB
						/ this.assetCreditNegNextPositive);
		this.assetCreditReturnNegNextFlat = Rounding
				.roundFourDecimals(ntofRetSumB / this.assetCreditNegNextFlat);
		this.assetCreditReturnNegNextNegative = Rounding
				.roundFourDecimals(ntonRetSumB
						/ this.assetCreditNegNextNegative);

		this.assetCreditReturnFltNextPositive = Rounding
				.roundFourDecimals(ztopRetSumB
						/ this.assetCreditFltNextPositive);
		this.assetCreditReturnFltNextFlat = Rounding
				.roundFourDecimals(ztofRetSumB / this.assetCreditFltNextFlat);
		this.assetCreditReturnFltNextNegative = Rounding
				.roundFourDecimals(ztonRetSumB
						/ this.assetCreditFltNextNegative);

		this.assetCreditReturnPosNextPositive = Rounding
				.roundFourDecimals(ptopRetSumB
						/ this.assetCreditPosNextPositive);
		this.assetCreditReturnPosNextFlat = Rounding
				.roundFourDecimals(ptofRetSumB / this.assetCreditPosNextFlat);
		this.assetCreditReturnPosNextNegative = Rounding
				.roundFourDecimals(ptonRetSumB
						/ this.assetCreditPosNextNegative);
	}

	/**
	 * Computes the probabilities of asset A transitioning from all possible
	 * states to all other possible states for example the probability of moving
	 * from a state of negative returns to all possible states the sum of
	 * probabilities for each individual stating state to the next i.e. -ve ->
	 * {-ve/+ve/flat} must equal to 1
	 * 
	 */
	private void computeAssetTraditionalStateTransitionProbabilities() {
		assetTraditionalProbabilityNegNextPositive = Rounding
				.roundTwoDecimals(assetTraditionalNegNextPositive
						/ (assetTraditionalNegNextPositive
								+ assetTraditionalNegNextFlat + assetTraditionalNegNextNegative));

		assetTraditionalProbabilityNegNextFlat = Rounding
				.roundTwoDecimals(assetTraditionalNegNextFlat
						/ (assetTraditionalNegNextPositive
								+ assetTraditionalNegNextFlat + assetTraditionalNegNextNegative));

		assetTraditionalProbabilityNegNextNegative = Rounding
				.roundTwoDecimals(assetTraditionalNegNextNegative
						/ (assetTraditionalNegNextPositive
								+ assetTraditionalNegNextFlat + assetTraditionalNegNextNegative));

		// flat to others
		assetTraditionalProbabilityFltNextPositive = Rounding
				.roundTwoDecimals(assetTraditionalFltNextPositive
						/ (assetTraditionalFltNextPositive
								+ assetTraditionalFltNextFlat + assetTraditionalFltNextNegative));

		assetTraditionalProbabilityFltNextFlat = Rounding
				.roundTwoDecimals(assetTraditionalFltNextFlat
						/ (assetTraditionalFltNextPositive
								+ assetTraditionalFltNextFlat + assetTraditionalFltNextNegative));

		assetTraditionalProbabilityFltNextNegative = Rounding
				.roundTwoDecimals(assetTraditionalFltNextNegative
						/ (assetTraditionalFltNextPositive
								+ assetTraditionalFltNextFlat + assetTraditionalFltNextNegative));

		// positive to others
		assetTraditionalProbabilityPosNextPositive = Rounding
				.roundTwoDecimals(assetTraditionalPosNextPositive
						/ (assetTraditionalPosNextPositive
								+ assetTraditionalPosNextFlat + assetTraditionalPosNextNegative));

		assetTraditionalProbabilityPosNextFlat = Rounding
				.roundTwoDecimals(assetTraditionalPosNextFlat
						/ (assetTraditionalPosNextPositive
								+ assetTraditionalPosNextFlat + assetTraditionalPosNextNegative));

		assetTraditionalProbabilityPosNextNegative = Rounding
				.roundTwoDecimals(assetTraditionalPosNextNegative
						/ (assetTraditionalPosNextPositive
								+ assetTraditionalPosNextFlat + assetTraditionalPosNextNegative));

		// now set the transition adjacency lists
		assetTraditional_Negative_to_NegFlatPos = new double[] {
				assetTraditionalProbabilityNegNextNegative,
				assetTraditionalProbabilityNegNextFlat,
				assetTraditionalProbabilityNegNextPositive };

		assetTraditional_Flat_to_NegFlatPos = new double[] {
				assetTraditionalProbabilityFltNextNegative,
				assetTraditionalProbabilityFltNextFlat,
				assetTraditionalProbabilityFltNextPositive };

		assetTraditional_Positive_to_NegFlatPos = new double[] {
				assetTraditionalProbabilityPosNextNegative,
				assetTraditionalProbabilityPosNextFlat,
				assetTraditionalProbabilityPosNextPositive };

		assetTraditionalTransitionProbabilityMatrix = new double[][] {
				{ assetTraditionalProbabilityNegNextNegative,
						assetTraditionalProbabilityNegNextFlat,
						assetTraditionalProbabilityNegNextPositive },
				{ assetTraditionalProbabilityFltNextNegative,
						assetTraditionalProbabilityFltNextFlat,
						assetTraditionalProbabilityFltNextPositive },
				{ assetTraditionalProbabilityPosNextNegative,
						assetTraditionalProbabilityPosNextFlat,
						assetTraditionalProbabilityPosNextPositive } };
	}

	/**
	 * Computes the probabilities of asset B transitioning from all possible
	 * states to all other possible states for example the probability of moving
	 * from a state of negative returns to all possible states the sum of
	 * probabilities for each individual stating state to the next i.e. -ve ->
	 * {-ve/+ve/flat} must equal to 1
	 * 
	 */
	private void computeAssetCreditStateTransitionProbabilities() {
		// negative to others
		assetCreditProbabilityNegNextPositive = Rounding
				.roundTwoDecimals((assetCreditNegNextPositive / (assetCreditNegNextPositive
						+ assetCreditNegNextFlat + assetCreditNegNextNegative)));

		assetCreditProbabilityNegNextFlat = Rounding
				.roundTwoDecimals((assetCreditNegNextFlat / (assetCreditNegNextPositive
						+ assetCreditNegNextFlat + assetCreditNegNextNegative)));

		assetCreditProbabilityNegNextNegative = Rounding
				.roundTwoDecimals((assetCreditNegNextNegative / (assetCreditNegNextPositive
						+ assetCreditNegNextFlat + assetCreditNegNextNegative)));

		// flat to others
		assetCreditProbabilityFltNextPositive = Rounding
				.roundTwoDecimals((assetCreditFltNextPositive / (assetCreditFltNextPositive
						+ assetCreditFltNextFlat + assetCreditFltNextNegative)));

		assetCreditProbabilityFltNextFlat = Rounding
				.roundTwoDecimals((assetCreditFltNextFlat / (assetCreditFltNextPositive
						+ assetCreditFltNextFlat + assetCreditFltNextNegative)));

		assetCreditProbabilityFltNextNegative = Rounding
				.roundTwoDecimals((assetCreditFltNextNegative / (assetCreditFltNextPositive
						+ assetCreditFltNextFlat + assetCreditFltNextNegative)));

		// positive to others
		assetCreditProbabilityPosNextPositive = Rounding
				.roundTwoDecimals((assetCreditPosNextPositive / (assetCreditPosNextPositive
						+ assetCreditPosNextFlat + assetCreditPosNextNegative)));

		assetCreditProbabilityPosNextFlat = Rounding
				.roundTwoDecimals((assetCreditPosNextFlat / (assetCreditPosNextPositive
						+ assetCreditPosNextFlat + assetCreditPosNextNegative)));

		assetCreditProbabilityPosNextNegative = Rounding
				.roundTwoDecimals((assetCreditPosNextNegative / (assetCreditPosNextPositive
						+ assetCreditPosNextFlat + assetCreditPosNextNegative)));

		// now set the transition adjacency lists
		assetCredit_Negative_to_NegFlatPos = new double[] {
				assetCreditProbabilityNegNextNegative,
				assetCreditProbabilityNegNextFlat,
				assetCreditProbabilityNegNextPositive };

		assetCredit_Flat_to_NegFlatPos = new double[] {
				assetCreditProbabilityFltNextNegative,
				assetCreditProbabilityFltNextFlat,
				assetCreditProbabilityFltNextPositive };

		assetCredit_Positive_to_NegFlatPos = new double[] {
				assetCreditProbabilityPosNextNegative,
				assetCreditProbabilityPosNextFlat,
				assetCreditProbabilityPosNextPositive };

		assetCreditTransitionProbabilityMatrix = new double[][] {
				{ assetCreditProbabilityNegNextNegative,
						assetCreditProbabilityNegNextFlat,
						assetCreditProbabilityNegNextPositive },
				{ assetCreditProbabilityFltNextNegative,
						assetCreditProbabilityFltNextFlat,
						assetCreditProbabilityFltNextPositive },
				{ assetCreditProbabilityPosNextNegative,
						assetCreditProbabilityPosNextFlat,
						assetCreditProbabilityPosNextPositive } };
	}

	/**
	 * Computes the average returns for each end state.
	 * 
	 * This is note dependent on the transition between but rather it is the
	 * average realised return for each state.
	 * 
	 * That is it computes the average for all -ve states, all flat states and
	 * all +ve states
	 * 
	 * To do this first of all count the number of -ve, flat and +ve states
	 * 
	 * then sum up all the recorded returns for each of those states and
	 * 
	 * then divide by the total number of each of the states.
	 * 
	 * Note:
	 * 
	 * The average is taken as the arithmetic mean rather than the geometric
	 * mean because the objective is to capture as much of the magnitudinal
	 * changes in the return THis is particularly important for the credit asset
	 * where default rates impact value
	 * 
	 * 
	 */
	private void computeAssetTraditionalStateAverageReturns() {
		int negT = 0;
		int flatT = 0;
		int posT = 0;

		double negRetT = 0;
		double flatRetT = 0;
		double posRetT = 0;

		this.assetTraditionalAverageReturns = new double[3];

		for (int i = 1; i < this.assetTraditionalReturnsPath.length; i++) {
			if (this.assetTraditionalReturnsPath[i] < 0.0000) {
				negT++;
				negRetT += this.assetTraditionalReturnsPath[i];
			} else if (this.assetTraditionalReturnsPath[i] == 0) {
				flatT++;
				flatRetT += this.assetTraditionalReturnsPath[i];

			} else if (this.assetTraditionalReturnsPath[i] > 0.0000) {
				posT++;
				posRetT += this.assetTraditionalReturnsPath[i];
			}
		}

		this.assetTraditionalStateAverageNegativeReturns = Rounding
				.roundFourDecimals(negRetT / negT);

		this.assetTraditionalStateAverageFlatReturns = Rounding
				.roundFourDecimals(flatRetT / flatT);

		this.assetTraditionalStateAveragePositiveReturns = Rounding
				.roundFourDecimals(posRetT / posT);

		this.assetTraditionalAverageReturns[0] = this.assetTraditionalStateAverageNegativeReturns;
		this.assetTraditionalAverageReturns[1] = this.assetTraditionalStateAverageFlatReturns;
		this.assetTraditionalAverageReturns[2] = this.assetTraditionalStateAveragePositiveReturns;
	}

	/**
	 * Computes the average returns for each end state.
	 * 
	 * This is note dependent on the transition between but rather it is the
	 * average realised return for each state.
	 * 
	 * That is it computes the average for all -ve states, all flat states and
	 * all +ve states
	 * 
	 * To do this first of all count the number of -ve, flat and +ve states
	 * 
	 * then sum up all the recorded returns for each of those states and
	 * 
	 * then divide by the total number of each of the states.
	 * 
	 * Note 1:
	 * 
	 * The average is taken as the arithmetic mean rather than the geometric
	 * mean because the objective is to capture as much of the magnitudinal
	 * changes in the return THis is particularly important for the credit asset
	 * where default rates impact value
	 * 
	 * Note 2:
	 * 
	 * The final returns have been multiplied by 10 to ensure the significant
	 * magnitude of the returns and portfolio impact This is not appropriate,
	 * however it aid better computation of the MDP to be used
	 * 
	 * 
	 */
	private void computeAssetCreditStateAverageReturns() {
		int neg = 0;
		int flat = 0;
		int pos = 0;

		double negRet = 0;
		double flatRet = 0;
		double posRet = 0;

		this.assetCreditAverageReturns = new double[3];

		for (int i = 1; i < this.assetCreditReturnsPath.length; i++) {
			if (this.assetCreditReturnsPath[i] < 0.0000) {
				neg++;
				negRet += this.assetCreditReturnsPath[i];
			} else if (this.assetCreditReturnsPath[i] == 0) {
				flat++;
				flatRet += this.assetCreditReturnsPath[i];

			} else if (this.assetCreditReturnsPath[i] > 0.0000) {
				pos++;
				posRet += this.assetCreditReturnsPath[i];
			}
		}

		this.assetCreditStateAverageNegativeReturns = Rounding
				.roundFourDecimals(negRet / neg);

		this.assetCreditStateAverageFlatReturns = Rounding
				.roundFourDecimals(flatRet / flat);

		this.assetCreditStateAveragePositiveReturns = Rounding
				.roundFourDecimals(posRet / pos);

		this.assetCreditAverageReturns[0] = this.assetCreditStateAverageNegativeReturns;
		this.assetCreditAverageReturns[1] = this.assetCreditStateAverageFlatReturns;
		this.assetCreditAverageReturns[2] = this.assetCreditStateAveragePositiveReturns;

	}

	private void computeTwoindependentAssetJointTransitionProbabilities() {

		// empty the STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX list if not
		// empty
		if (STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX.size() > 0) {
			STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX.clear();
		}

		// first loop through the rows of the first asset's transition
		// probability matrix
		for (int g = 0; g < this.assetTraditionalTransitionProbabilityMatrix.length; g++) {
			// now loop through the rows of the second asset's transition
			// probability matrix
			for (int h = 0; h < this.assetCreditTransitionProbabilityMatrix.length; h++) {
				double[] transProbArray = new double[this.assetTraditionalTransitionProbabilityMatrix[0].length
						* this.assetCreditTransitionProbabilityMatrix[0].length];
				int incr = 0; // integer to enable scaling through
								// transProbArray array
				// now for each element of the selected row of the first asset's
				// transition probability matrix
				// multiply by each element of the selected row of the second
				// asset's transition probability matrix
				for (int i = 0; i < this.assetTraditionalTransitionProbabilityMatrix[g].length; i++) {

					// now loop through each element of the selected row of the
					// second assets transition probability matrix
					for (int l = 0; l < this.assetCreditTransitionProbabilityMatrix[h].length; l++) {
						transProbArray[incr] = Rounding
								.roundTwoDecimals(this.assetTraditionalTransitionProbabilityMatrix[g][i]
										* this.assetCreditTransitionProbabilityMatrix[h][l]);
						incr++;// increment incr to apply to next value in loop
					}
				}// end column for loop
					// add the transition list to the joined probability matrix
				// note that the g - 1 and h - 1 refer to the states {-1, 0, 1}
				STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX
						.add(AdjacencyMatrixContainer
								.createTwoAssetAdjacencyList(g - 1, h - 1,
										transProbArray));
			}
		}

	}

	public List<AdjacencyMatrixContainer> getJoinedTransitionProbabilityMatrix() {
		return this.STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX;
	}

	/**
	 * @return the assetTraditionalTransitionProbabilityMatrix
	 */
	public double[][] getAssetTraditionalTransitionProbabilityMatrix() {
		return assetTraditionalTransitionProbabilityMatrix;
	}

	/**
	 * @return the assetCreditTransitionProbabilityMatrix
	 */
	public double[][] getAssetCreditTransitionProbabilityMatrix() {
		return assetCreditTransitionProbabilityMatrix;
	}

	/**
	 * @return the sTOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX
	 */
	public List<AdjacencyMatrixContainer> getSTOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX() {
		return STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX;
	}

	/**
	 * @return the assetCreditAverageReturns
	 */
	public double[] getAssetCreditAverageReturns() {
		return assetCreditAverageReturns;
	}

	/**
	 * @return the assetTraditionalAverageReturns
	 */
	public double[] getAssetTraditionalAverageReturns() {
		return assetTraditionalAverageReturns;
	}

	@Override
	public String toString() {
		return ("STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX "
				+ STOCHASTIC_GENERATED_JOINED_PROBABILITYMATRIX.toString()
				+ "\r\n" + "assetCreditStateAverageNegativeReturns: "
				+ assetCreditStateAverageNegativeReturns
				+ " assetCreditStateAverageFlatReturns: "
				+ assetCreditStateAverageFlatReturns
				+ " assetCreditStateAveragePositiveReturns: "
				+ assetCreditStateAveragePositiveReturns + "\r\n"
				+ " assetTraditionalStateAverageNegativeReturns: "
				+ assetTraditionalStateAverageNegativeReturns
				+ " assetTraditionalStateAverageFlatReturns: "
				+ assetTraditionalStateAverageFlatReturns
				+ " assetTraditionalStateAveragePositiveReturns: "
				+ assetTraditionalStateAveragePositiveReturns + "\r\n"
				+ "assetCreditProbabilityNegNextNegative: "
				+ assetCreditProbabilityNegNextNegative
				+ " assetCreditProbabilityNegNextFlat: "
				+ assetCreditProbabilityNegNextFlat
				+ " assetCreditProbabilityNegNextPositive: "
				+ assetCreditProbabilityNegNextPositive + "\r\n"
				+ " assetCreditProbabilityFltNextNegative: "
				+ assetCreditProbabilityFltNextNegative
				+ " assetCreditProbabilityFltNextFlat: "
				+ assetCreditProbabilityFltNextFlat
				+ " assetCreditProbabilityFltNextPositive: "
				+ assetCreditProbabilityFltNextPositive + "\r\n"
				+ " assetCreditProbabilityPosNextNegative: "
				+ assetCreditProbabilityPosNextNegative
				+ " assetCreditProbabilityPosNextFlat: "
				+ assetCreditProbabilityPosNextFlat
				+ " assetCreditProbabilityPosNextPositive: "
				+ assetCreditProbabilityPosNextPositive + "\r\n"
				+ "assetTraditionalProbabilityNegNextNegative: "
				+ assetTraditionalProbabilityNegNextNegative
				+ " assetTraditionalProbabilityNegNextFlat: "
				+ assetTraditionalProbabilityNegNextFlat
				+ " assetTraditionalProbabilityNegNextPositive: "
				+ assetTraditionalProbabilityNegNextPositive + "\r\n"
				+ "assetTraditionalProbabilityFltNextNegative: "
				+ assetTraditionalProbabilityFltNextNegative
				+ " assetTraditionalProbabilityFltNextFlat: "
				+ assetTraditionalProbabilityFltNextFlat
				+ " assetTraditionalProbabilityFltNextPositive: "
				+ assetTraditionalProbabilityFltNextPositive + "\r\n"
				+ "assetTraditionalProbabilityPosNextNegative: "
				+ assetTraditionalProbabilityPosNextNegative
				+ " assetTraditionalProbabilityPosNextFlat: "
				+ assetTraditionalProbabilityPosNextFlat
				+ " assetTraditionalProbabilityPosNextPositive: " + assetTraditionalProbabilityPosNextPositive);
	}

}
