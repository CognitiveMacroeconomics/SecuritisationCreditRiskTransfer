import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import java.awt.GridBagLayout;

import javax.swing.JSlider;

import java.awt.GridBagConstraints;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;

import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.WindowConstants;

/**
 * This class is used as a pop-out window to enable the user to set the MDP
 * Model Parameters
 * 
 * 
 * @author Oluwasegun Bewaji
 * 
 */
@SuppressWarnings("serial")
public class MDPModelParameterSettingWindow extends JDialog {

	// MDP Model Variables these variables have to be static because they are
	// treated as global Parameters
	private static String decisionAnalysisPeriodEndString = "2003"; // possible
																	// values
																	// "2003",
																	// "2007",
																	// "Full"
	private static boolean portfolioWeightChoiceModel = true; // used to
																// determine if
																// model will
																// use the
																// choice of
																// portfolio
																// weights or
																// the choice of
																// changes in
																// portfolio
																// weights

	private static double linearfactor = 0.07; // used as the constant
												// parameter/multiplier for
												// computing the linear
												// transaction costs
	private static double quadraticfactor = 0.07; // used as the constant
													// parameter/multiplier for
													// computing the quadratic
													// transaction costs
	private static double assetWieghtIncrements = 0.05; // increment used to
														// create the potfolio
														// weights that are used
														// to define the MDP
														// states
	private static double changeInWeightIncrement = 0.05; // represents the rate
															// at which changes
															// can be made to
															// portfolio weights
	private static double maximumPermissbleChangeInWeight = 0.10;
	// value and policy iteration parameters
	private static int numberOfIterations = 1000;
	private static int numberOfDecisionEpochs = 5;
	private static double epsilonError = 10 * (0.01 / 100);//
	private static double gammaDiscountFactor = 0.1;// increasing gamma
													// increases the time/number
													// of iterations required to
													// find a value
	private static double accuracyThreshold = 0.0001;
	private static double RLLearningRateAlpha = 0.1;
	private static double RLLearningLambda = 0.1;
	private static boolean stochasticStateTransitions = false;

	private static int RLLearningType = 0;
	private static int RLActionSelectionType = 0;

	private final JPanel contentPanel = new JPanel();
	private JTextField jTextField_AssetWieghtIncrements;
	private JTextField jTextField_ChangeInWeightIncrement;
	private JTextField jTextField_MaximumPermissbleChangeInWeight;
	private JTextField jTextField_LinearFactor;
	private JTextField jTextField_QuadraticFactor;
	private JTextField jTextField_numberOfIterations;
	private JTextField jTextField_AccuracyThreshold;

	private JPanel buttonPane;
	private JButton applyButton;
	private JButton resetButton;
	private JButton closeButton;

	private JComboBox jComboBox_PortfolioWeightChoiceModel;
	private JComboBox jComboBox_StochasticStateTransitions;
	private JComboBox jComboBox_DecisionPeriodEnd;

	private JSlider jSlider_EpsilonError;
	private JSlider jSlider_GammaDistountFactor;
	private JTextField jTextField_LearningRate;
	private JTextField jTextField_RandomParameter;
	private JTextField jTextField_Lambda;
	private JTextField jTextField_NumberOfEpochs;

	private JComboBox jComboBox_ActionSelectionTypes;
	private JComboBox jComboBox_LearningTypes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MDPModelParameterSettingWindow dialog = new MDPModelParameterSettingWindow();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	private void initialize() {

		setBounds(100, 100, 553, 434);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				MDPModelParameterSettingWindow.class
						.getResource("/jas/images/graph.gif")));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new CardLayout(0, 0));
		{
			JPanel jPanel_MDPParameters = new JPanel();
			jPanel_MDPParameters.setBorder(null);
			contentPanel.add(jPanel_MDPParameters, "MDP Model Parameters");
			GridBagLayout gbl_jPanel_MDPParameters = new GridBagLayout();
			gbl_jPanel_MDPParameters.columnWidths = new int[] { 551, 0 };
			gbl_jPanel_MDPParameters.rowHeights = new int[] { 187, 85, 0 };
			gbl_jPanel_MDPParameters.columnWeights = new double[] { 1.0,
					Double.MIN_VALUE };
			gbl_jPanel_MDPParameters.rowWeights = new double[] { 0.0, 1.0,
					Double.MIN_VALUE };
			jPanel_MDPParameters.setLayout(gbl_jPanel_MDPParameters);
			{
				JPanel JPanel_GammaEpsilon = new JPanel();
				GridBagConstraints gbc_JPanel_GammaEpsilon = new GridBagConstraints();
				gbc_JPanel_GammaEpsilon.insets = new Insets(0, 0, 5, 0);
				gbc_JPanel_GammaEpsilon.anchor = GridBagConstraints.NORTHWEST;
				gbc_JPanel_GammaEpsilon.gridx = 0;
				gbc_JPanel_GammaEpsilon.gridy = 1;
				jPanel_MDPParameters.add(JPanel_GammaEpsilon,
						gbc_JPanel_GammaEpsilon);
				GridBagLayout gbl_JPanel_GammaEpsilon = new GridBagLayout();
				gbl_JPanel_GammaEpsilon.columnWidths = new int[] { 269, 0 };
				gbl_JPanel_GammaEpsilon.rowHeights = new int[] { 29, 29, 0 };
				gbl_JPanel_GammaEpsilon.columnWeights = new double[] { 0.0,
						Double.MIN_VALUE };
				gbl_JPanel_GammaEpsilon.rowWeights = new double[] { 0.0, 0.0,
						Double.MIN_VALUE };
				JPanel_GammaEpsilon.setLayout(gbl_JPanel_GammaEpsilon);
				{
					JPanel jPanel_EpsilonError = new JPanel();
					GridBagConstraints gbc_jPanel_EpsilonError = new GridBagConstraints();
					gbc_jPanel_EpsilonError.fill = GridBagConstraints.BOTH;
					gbc_jPanel_EpsilonError.insets = new Insets(0, 0, 5, 0);
					gbc_jPanel_EpsilonError.gridx = 0;
					gbc_jPanel_EpsilonError.gridy = 0;
					JPanel_GammaEpsilon.add(jPanel_EpsilonError,
							gbc_jPanel_EpsilonError);
					GridBagLayout gbl_jPanel_EpsilonError = new GridBagLayout();
					gbl_jPanel_EpsilonError.columnWidths = new int[] { 101,
							335, 0 };
					gbl_jPanel_EpsilonError.rowHeights = new int[] { 0, 0 };
					gbl_jPanel_EpsilonError.columnWeights = new double[] { 0.0,
							0.0, Double.MIN_VALUE };
					gbl_jPanel_EpsilonError.rowWeights = new double[] { 0.0,
							Double.MIN_VALUE };
					jPanel_EpsilonError.setLayout(gbl_jPanel_EpsilonError);
					{
						JLabel jLabel_Epsilon = new JLabel(
								"Epsilon/Error (bps)");
						jLabel_Epsilon
								.setFont(new Font("Tahoma", Font.PLAIN, 9));
						GridBagConstraints gbc_jLabel_Epsilon = new GridBagConstraints();
						gbc_jLabel_Epsilon.fill = GridBagConstraints.BOTH;
						gbc_jLabel_Epsilon.anchor = GridBagConstraints.WEST;
						gbc_jLabel_Epsilon.insets = new Insets(0, 0, 0, 5);
						gbc_jLabel_Epsilon.gridx = 0;
						gbc_jLabel_Epsilon.gridy = 0;
						jPanel_EpsilonError.add(jLabel_Epsilon,
								gbc_jLabel_Epsilon);
					}
					{
						jSlider_EpsilonError = new JSlider();
						jSlider_EpsilonError.setPaintTicks(true);
						jSlider_EpsilonError.setPaintLabels(true);
						jSlider_EpsilonError.setValue(10);
						double value = jSlider_EpsilonError.getValue();
						setEpsilonError(value);
						jSlider_EpsilonError
								.setToolTipText("Sets the level of epsilon");
						jSlider_EpsilonError.setMinorTickSpacing(5);
						jSlider_EpsilonError.setMajorTickSpacing(10);
						jSlider_EpsilonError.setFont(new Font("Tahoma",
								Font.PLAIN, 9));
						jSlider_EpsilonError.setBorder(null);
						EpsilonErrorChangeListener eelstnr = new EpsilonErrorChangeListener();// internal
																								// class
						jSlider_EpsilonError.addChangeListener(eelstnr);
						GridBagConstraints gbc_jSlider_EpsilonError = new GridBagConstraints();
						gbc_jSlider_EpsilonError.fill = GridBagConstraints.BOTH;
						gbc_jSlider_EpsilonError.anchor = GridBagConstraints.WEST;
						gbc_jSlider_EpsilonError.gridx = 1;
						gbc_jSlider_EpsilonError.gridy = 0;
						jPanel_EpsilonError.add(jSlider_EpsilonError,
								gbc_jSlider_EpsilonError);
					}
				}
				{
					JPanel jPanel_Gamma = new JPanel();
					GridBagConstraints gbc_jPanel_Gamma = new GridBagConstraints();
					gbc_jPanel_Gamma.anchor = GridBagConstraints.NORTHWEST;
					gbc_jPanel_Gamma.gridx = 0;
					gbc_jPanel_Gamma.gridy = 1;
					JPanel_GammaEpsilon.add(jPanel_Gamma, gbc_jPanel_Gamma);
					GridBagLayout gbl_jPanel_Gamma = new GridBagLayout();
					gbl_jPanel_Gamma.columnWidths = new int[] { 101, 336, 0 };
					gbl_jPanel_Gamma.rowHeights = new int[] { 0, 0 };
					gbl_jPanel_Gamma.columnWeights = new double[] { 0.0, 0.0,
							Double.MIN_VALUE };
					gbl_jPanel_Gamma.rowWeights = new double[] { 0.0,
							Double.MIN_VALUE };
					jPanel_Gamma.setLayout(gbl_jPanel_Gamma);
					{
						JLabel jLabel_GammaDiscountFactor = new JLabel(
								"Discount Factor (%)");
						jLabel_GammaDiscountFactor.setFont(new Font("Tahoma",
								Font.PLAIN, 9));
						GridBagConstraints gbc_jLabel_GammaDiscountFactor = new GridBagConstraints();
						gbc_jLabel_GammaDiscountFactor.fill = GridBagConstraints.BOTH;
						gbc_jLabel_GammaDiscountFactor.anchor = GridBagConstraints.WEST;
						gbc_jLabel_GammaDiscountFactor.insets = new Insets(0,
								0, 0, 5);
						gbc_jLabel_GammaDiscountFactor.gridx = 0;
						gbc_jLabel_GammaDiscountFactor.gridy = 0;
						jPanel_Gamma.add(jLabel_GammaDiscountFactor,
								gbc_jLabel_GammaDiscountFactor);
					}
					{
						jSlider_GammaDistountFactor = new JSlider();
						jSlider_GammaDistountFactor.setPaintLabels(true);
						jSlider_GammaDistountFactor.setPaintTicks(true);
						jSlider_GammaDistountFactor.setValue(70);
						double valueG = jSlider_GammaDistountFactor.getValue();
						setGammaDistountFactor(valueG);
						jSlider_GammaDistountFactor
								.setToolTipText("Sets the level of gamma");
						jSlider_GammaDistountFactor.setFont(new Font("Tahoma",
								Font.PLAIN, 9));
						jSlider_GammaDistountFactor.setBorder(null);
						jSlider_GammaDistountFactor.setMajorTickSpacing(10);
						jSlider_GammaDistountFactor.setMinorTickSpacing(5);
						GammaDistountFactorChangeListener gdflstnr = new GammaDistountFactorChangeListener();// internal
																												// class
						jSlider_GammaDistountFactor.addChangeListener(gdflstnr);
						GridBagConstraints gbc_jSlider_GammaDistountFactor = new GridBagConstraints();
						gbc_jSlider_GammaDistountFactor.anchor = GridBagConstraints.WEST;
						gbc_jSlider_GammaDistountFactor.fill = GridBagConstraints.BOTH;
						gbc_jSlider_GammaDistountFactor.gridx = 1;
						gbc_jSlider_GammaDistountFactor.gridy = 0;
						jPanel_Gamma.add(jSlider_GammaDistountFactor,
								gbc_jSlider_GammaDistountFactor);
					}
				}
			}
			{
				JPanel JPanel_NoneSliderParameters = new JPanel();
				GridBagConstraints gbc_JPanel_NoneSliderParameters = new GridBagConstraints();
				gbc_JPanel_NoneSliderParameters.fill = GridBagConstraints.BOTH;
				gbc_JPanel_NoneSliderParameters.gridx = 0;
				gbc_JPanel_NoneSliderParameters.gridy = 0;
				jPanel_MDPParameters.add(JPanel_NoneSliderParameters,
						gbc_JPanel_NoneSliderParameters);
				GridBagLayout gbl_JPanel_NoneSliderParameters = new GridBagLayout();
				gbl_JPanel_NoneSliderParameters.columnWidths = new int[] { 134,
						127, 97, 0, 0 };
				gbl_JPanel_NoneSliderParameters.rowHeights = new int[] { 22, 0,
						0, 0, 0, 0, 0, 0, 0 };
				gbl_JPanel_NoneSliderParameters.columnWeights = new double[] {
						0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
				gbl_JPanel_NoneSliderParameters.rowWeights = new double[] {
						0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
						Double.MIN_VALUE };
				JPanel_NoneSliderParameters
						.setLayout(gbl_JPanel_NoneSliderParameters);
				{
					JLabel jLabel_StochasticStateTransitions = new JLabel(
							"Internalised State Transitions");
					jLabel_StochasticStateTransitions.setFont(new Font(
							"Tahoma", Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_StochasticStateTransitions = new GridBagConstraints();
					gbc_jLabel_StochasticStateTransitions.anchor = GridBagConstraints.WEST;
					gbc_jLabel_StochasticStateTransitions.insets = new Insets(
							0, 0, 5, 5);
					gbc_jLabel_StochasticStateTransitions.gridx = 0;
					gbc_jLabel_StochasticStateTransitions.gridy = 0;
					JPanel_NoneSliderParameters.add(
							jLabel_StochasticStateTransitions,
							gbc_jLabel_StochasticStateTransitions);
				}
				{
					jComboBox_StochasticStateTransitions = new JComboBox();
					jComboBox_StochasticStateTransitions
							.setModel(new DefaultComboBoxModel(new String[] {
									"false", "true" }));
					jComboBox_StochasticStateTransitions.setSelectedIndex(0);
					jComboBox_StochasticStateTransitions.setFont(new Font(
							"Tahoma", Font.PLAIN, 9));
					jComboBox_StochasticStateTransitions
							.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									JComboBox jcmbType = (JComboBox) e
											.getSource();
									int cmbTransitionsType = jcmbType
											.getSelectedIndex();
									setStochasticStateTransitionsBoolean(cmbTransitionsType);
								}
							});
					GridBagConstraints gbc_jComboBox_StochasticStateTransitions = new GridBagConstraints();
					gbc_jComboBox_StochasticStateTransitions.fill = GridBagConstraints.BOTH;
					gbc_jComboBox_StochasticStateTransitions.anchor = GridBagConstraints.WEST;
					gbc_jComboBox_StochasticStateTransitions.insets = new Insets(
							0, 0, 5, 5);
					gbc_jComboBox_StochasticStateTransitions.gridx = 1;
					gbc_jComboBox_StochasticStateTransitions.gridy = 0;
					JPanel_NoneSliderParameters.add(
							jComboBox_StochasticStateTransitions,
							gbc_jComboBox_StochasticStateTransitions);
				}
				{
					JLabel jLabrl_LearningTypes = new JLabel("Learning Types");
					jLabrl_LearningTypes.setFont(new Font("Tahoma", Font.PLAIN,
							9));
					GridBagConstraints gbc_jLabrl_LearningTypes = new GridBagConstraints();
					gbc_jLabrl_LearningTypes.anchor = GridBagConstraints.WEST;
					gbc_jLabrl_LearningTypes.insets = new Insets(0, 0, 5, 5);
					gbc_jLabrl_LearningTypes.gridx = 2;
					gbc_jLabrl_LearningTypes.gridy = 0;
					JPanel_NoneSliderParameters.add(jLabrl_LearningTypes,
							gbc_jLabrl_LearningTypes);
				}
				{
					jComboBox_LearningTypes = new JComboBox();
					jComboBox_LearningTypes.setModel(new DefaultComboBoxModel(
							new String[] { "VALUE ITERATION", "Q-LEARNING",
									"SARSA", "Q_LAMBDA" }));
					jComboBox_LearningTypes.setSelectedIndex(0);
					jComboBox_LearningTypes.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jComboBox_LearningTypes
							.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									JComboBox jcmbType = (JComboBox) e
											.getSource();
									String cmbLearningType = (String) jcmbType
											.getSelectedItem();
									setLearningTypes(cmbLearningType);
								}
							});
					GridBagConstraints gbc_jComboBox_LearningTypes = new GridBagConstraints();
					gbc_jComboBox_LearningTypes.fill = GridBagConstraints.HORIZONTAL;
					gbc_jComboBox_LearningTypes.insets = new Insets(0, 0, 5, 0);
					gbc_jComboBox_LearningTypes.gridx = 3;
					gbc_jComboBox_LearningTypes.gridy = 0;
					JPanel_NoneSliderParameters.add(jComboBox_LearningTypes,
							gbc_jComboBox_LearningTypes);
				}
				{
					JLabel jLabel_PreDefinedTransitions = new JLabel(
							"Pre-defined Transitions Data");
					jLabel_PreDefinedTransitions.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_PreDefinedTransitions = new GridBagConstraints();
					gbc_jLabel_PreDefinedTransitions.insets = new Insets(0, 0,
							5, 5);
					gbc_jLabel_PreDefinedTransitions.fill = GridBagConstraints.VERTICAL;
					gbc_jLabel_PreDefinedTransitions.anchor = GridBagConstraints.WEST;
					gbc_jLabel_PreDefinedTransitions.gridx = 0;
					gbc_jLabel_PreDefinedTransitions.gridy = 1;
					JPanel_NoneSliderParameters.add(
							jLabel_PreDefinedTransitions,
							gbc_jLabel_PreDefinedTransitions);
				}
				{
					jComboBox_DecisionPeriodEnd = new JComboBox();
					jComboBox_DecisionPeriodEnd.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jComboBox_DecisionPeriodEnd
							.setModel(new DefaultComboBoxModel(new String[] {
									"2003", "2007", "Full" }));
					jComboBox_DecisionPeriodEnd.setSelectedIndex(0);
					jComboBox_DecisionPeriodEnd
							.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									JComboBox jcmbType = (JComboBox) e
											.getSource();
									String cmbTransitionsType = (String) jcmbType
											.getSelectedItem();
									setPreDefinedMDPStartPeriod(cmbTransitionsType);
								}
							});
					GridBagConstraints gbc_jComboBox_DecisionPeriodEnd = new GridBagConstraints();
					gbc_jComboBox_DecisionPeriodEnd.fill = GridBagConstraints.BOTH;
					gbc_jComboBox_DecisionPeriodEnd.anchor = GridBagConstraints.WEST;
					gbc_jComboBox_DecisionPeriodEnd.insets = new Insets(0, 0,
							5, 5);
					gbc_jComboBox_DecisionPeriodEnd.gridx = 1;
					gbc_jComboBox_DecisionPeriodEnd.gridy = 1;
					JPanel_NoneSliderParameters.add(
							jComboBox_DecisionPeriodEnd,
							gbc_jComboBox_DecisionPeriodEnd);
				}
				{
					JLabel jLabel_ActionSelectionTypes = new JLabel(
							"Action Selection Types");
					jLabel_ActionSelectionTypes.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_ActionSelectionTypes = new GridBagConstraints();
					gbc_jLabel_ActionSelectionTypes.anchor = GridBagConstraints.WEST;
					gbc_jLabel_ActionSelectionTypes.insets = new Insets(0, 0,
							5, 5);
					gbc_jLabel_ActionSelectionTypes.gridx = 2;
					gbc_jLabel_ActionSelectionTypes.gridy = 1;
					JPanel_NoneSliderParameters.add(
							jLabel_ActionSelectionTypes,
							gbc_jLabel_ActionSelectionTypes);
				}
				{
					jComboBox_ActionSelectionTypes = new JComboBox();
					jComboBox_ActionSelectionTypes
							.setModel(new DefaultComboBoxModel(new String[] {
									"NONE", "E-GREEDY", "SOFTMAX" }));
					jComboBox_ActionSelectionTypes.setSelectedIndex(1);
					jComboBox_ActionSelectionTypes.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jComboBox_ActionSelectionTypes
							.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									JComboBox jcmbType = (JComboBox) e
											.getSource();
									String cmbActionSelectionType = (String) jcmbType
											.getSelectedItem();
									setActionSelectionType(cmbActionSelectionType);
								}
							});
					GridBagConstraints gbc_jComboBox_ActionSelectionTypes = new GridBagConstraints();
					gbc_jComboBox_ActionSelectionTypes.insets = new Insets(0,
							0, 5, 0);
					gbc_jComboBox_ActionSelectionTypes.fill = GridBagConstraints.HORIZONTAL;
					gbc_jComboBox_ActionSelectionTypes.gridx = 3;
					gbc_jComboBox_ActionSelectionTypes.gridy = 1;
					JPanel_NoneSliderParameters.add(
							jComboBox_ActionSelectionTypes,
							gbc_jComboBox_ActionSelectionTypes);
				}
				{
					JLabel jLabel_PortfolioSelectionType = new JLabel(
							"Portfolio Selection Type");
					jLabel_PortfolioSelectionType.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_PortfolioSelectionType = new GridBagConstraints();
					gbc_jLabel_PortfolioSelectionType.anchor = GridBagConstraints.WEST;
					gbc_jLabel_PortfolioSelectionType.insets = new Insets(0, 0,
							5, 5);
					gbc_jLabel_PortfolioSelectionType.gridx = 0;
					gbc_jLabel_PortfolioSelectionType.gridy = 2;
					JPanel_NoneSliderParameters.add(
							jLabel_PortfolioSelectionType,
							gbc_jLabel_PortfolioSelectionType);
				}
				{
					jComboBox_PortfolioWeightChoiceModel = new JComboBox();
					jComboBox_PortfolioWeightChoiceModel.setFont(new Font(
							"Tahoma", Font.PLAIN, 9));
					jComboBox_PortfolioWeightChoiceModel
							.setModel(new DefaultComboBoxModel(new String[] {
									"Asset Weight Choice",
									"Asset Weight Change" }));
					jComboBox_PortfolioWeightChoiceModel.setSelectedIndex(0);
					jComboBox_PortfolioWeightChoiceModel
							.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									JComboBox jcmbType = (JComboBox) e
											.getSource();
									String cmbTransitionsType = (String) jcmbType
											.getSelectedItem();
									setPortfolioWeightChoiceModel(cmbTransitionsType);
								}
							});
					GridBagConstraints gbc_jComboBox_PortfolioWeightChoiceModel = new GridBagConstraints();
					gbc_jComboBox_PortfolioWeightChoiceModel.fill = GridBagConstraints.BOTH;
					gbc_jComboBox_PortfolioWeightChoiceModel.insets = new Insets(
							0, 0, 5, 5);
					gbc_jComboBox_PortfolioWeightChoiceModel.anchor = GridBagConstraints.WEST;
					gbc_jComboBox_PortfolioWeightChoiceModel.gridx = 1;
					gbc_jComboBox_PortfolioWeightChoiceModel.gridy = 2;
					JPanel_NoneSliderParameters.add(
							jComboBox_PortfolioWeightChoiceModel,
							gbc_jComboBox_PortfolioWeightChoiceModel);
				}
				{
					JLabel jLabel_AccuracyThreshold = new JLabel(
							"Accuracy Threshold");
					jLabel_AccuracyThreshold.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_AccuracyThreshold = new GridBagConstraints();
					gbc_jLabel_AccuracyThreshold.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_AccuracyThreshold.anchor = GridBagConstraints.WEST;
					gbc_jLabel_AccuracyThreshold.gridx = 2;
					gbc_jLabel_AccuracyThreshold.gridy = 2;
					JPanel_NoneSliderParameters.add(jLabel_AccuracyThreshold,
							gbc_jLabel_AccuracyThreshold);
				}
				{
					jTextField_AccuracyThreshold = new JTextField();
					jTextField_AccuracyThreshold.setText("0.0001");
					jTextField_AccuracyThreshold.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_AccuracyThreshold.setColumns(10);
					GridBagConstraints gbc_jTextField_AccuracyThreshold = new GridBagConstraints();
					gbc_jTextField_AccuracyThreshold.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_AccuracyThreshold.insets = new Insets(0, 0,
							5, 0);
					gbc_jTextField_AccuracyThreshold.gridx = 3;
					gbc_jTextField_AccuracyThreshold.gridy = 2;
					JPanel_NoneSliderParameters.add(
							jTextField_AccuracyThreshold,
							gbc_jTextField_AccuracyThreshold);
				}
				{
					JLabel jLabel_AssetWeightIncrements = new JLabel(
							"Asset Weight Increments");
					jLabel_AssetWeightIncrements.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_AssetWeightIncrements = new GridBagConstraints();
					gbc_jLabel_AssetWeightIncrements.fill = GridBagConstraints.VERTICAL;
					gbc_jLabel_AssetWeightIncrements.anchor = GridBagConstraints.WEST;
					gbc_jLabel_AssetWeightIncrements.insets = new Insets(0, 0,
							5, 5);
					gbc_jLabel_AssetWeightIncrements.gridx = 0;
					gbc_jLabel_AssetWeightIncrements.gridy = 3;
					JPanel_NoneSliderParameters.add(
							jLabel_AssetWeightIncrements,
							gbc_jLabel_AssetWeightIncrements);
				}
				{
					jTextField_AssetWieghtIncrements = new JTextField();
					jTextField_AssetWieghtIncrements.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_AssetWieghtIncrements.setText("0.05");
					GridBagConstraints gbc_jTextField_AssetWieghtIncrements = new GridBagConstraints();
					gbc_jTextField_AssetWieghtIncrements.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_AssetWieghtIncrements.insets = new Insets(0,
							0, 5, 5);
					gbc_jTextField_AssetWieghtIncrements.gridx = 1;
					gbc_jTextField_AssetWieghtIncrements.gridy = 3;
					JPanel_NoneSliderParameters.add(
							jTextField_AssetWieghtIncrements,
							gbc_jTextField_AssetWieghtIncrements);
					jTextField_AssetWieghtIncrements.setColumns(10);
				}
				{
					JLabel jLabel_LearningRate = new JLabel("Learning Rate");
					jLabel_LearningRate.setFont(new Font("Tahoma", Font.PLAIN,
							9));
					GridBagConstraints gbc_jLabel_LearningRate = new GridBagConstraints();
					gbc_jLabel_LearningRate.anchor = GridBagConstraints.WEST;
					gbc_jLabel_LearningRate.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_LearningRate.gridx = 2;
					gbc_jLabel_LearningRate.gridy = 3;
					JPanel_NoneSliderParameters.add(jLabel_LearningRate,
							gbc_jLabel_LearningRate);
				}
				{
					jTextField_LearningRate = new JTextField();
					jTextField_LearningRate.setText("1.0");
					jTextField_LearningRate.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_LearningRate.setColumns(10);
					GridBagConstraints gbc_jTextField_LearningRate = new GridBagConstraints();
					gbc_jTextField_LearningRate.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_LearningRate.insets = new Insets(0, 0, 5, 0);
					gbc_jTextField_LearningRate.gridx = 3;
					gbc_jTextField_LearningRate.gridy = 3;
					JPanel_NoneSliderParameters.add(jTextField_LearningRate,
							gbc_jTextField_LearningRate);
				}
				{
					JLabel jLabel_ChangeInAssetWeightIncrements = new JLabel(
							"Weight Change Increments");
					jLabel_ChangeInAssetWeightIncrements.setFont(new Font(
							"Tahoma", Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_ChangeInAssetWeightIncrements = new GridBagConstraints();
					gbc_jLabel_ChangeInAssetWeightIncrements.anchor = GridBagConstraints.WEST;
					gbc_jLabel_ChangeInAssetWeightIncrements.insets = new Insets(
							0, 0, 5, 5);
					gbc_jLabel_ChangeInAssetWeightIncrements.gridx = 0;
					gbc_jLabel_ChangeInAssetWeightIncrements.gridy = 4;
					JPanel_NoneSliderParameters.add(
							jLabel_ChangeInAssetWeightIncrements,
							gbc_jLabel_ChangeInAssetWeightIncrements);
				}
				{
					jTextField_ChangeInWeightIncrement = new JTextField();
					jTextField_ChangeInWeightIncrement.setText("0.05");
					jTextField_ChangeInWeightIncrement.setFont(new Font(
							"Tahoma", Font.PLAIN, 9));
					jTextField_ChangeInWeightIncrement.setColumns(10);
					GridBagConstraints gbc_jTextField_ChangeInWeightIncrement = new GridBagConstraints();
					gbc_jTextField_ChangeInWeightIncrement.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_ChangeInWeightIncrement.insets = new Insets(
							0, 0, 5, 5);
					gbc_jTextField_ChangeInWeightIncrement.gridx = 1;
					gbc_jTextField_ChangeInWeightIncrement.gridy = 4;
					JPanel_NoneSliderParameters.add(
							jTextField_ChangeInWeightIncrement,
							gbc_jTextField_ChangeInWeightIncrement);
				}
				{
					JLabel jLabel_ExplorationRate = new JLabel(
							"Exploration Rate");
					jLabel_ExplorationRate.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_ExplorationRate = new GridBagConstraints();
					gbc_jLabel_ExplorationRate.anchor = GridBagConstraints.WEST;
					gbc_jLabel_ExplorationRate.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_ExplorationRate.gridx = 2;
					gbc_jLabel_ExplorationRate.gridy = 4;
					JPanel_NoneSliderParameters.add(jLabel_ExplorationRate,
							gbc_jLabel_ExplorationRate);
				}
				{
					jTextField_RandomParameter = new JTextField();
					jTextField_RandomParameter.setText("0.5");
					jTextField_RandomParameter.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_RandomParameter.setColumns(10);
					GridBagConstraints gbc_jTextField_RandomParameter = new GridBagConstraints();
					gbc_jTextField_RandomParameter.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_RandomParameter.insets = new Insets(0, 0, 5,
							0);
					gbc_jTextField_RandomParameter.gridx = 3;
					gbc_jTextField_RandomParameter.gridy = 4;
					JPanel_NoneSliderParameters.add(jTextField_RandomParameter,
							gbc_jTextField_RandomParameter);
				}
				{
					JLabel jLabel_MaximumPermissbleChangeInWeight = new JLabel(
							"Maximum Permissble Change");
					jLabel_MaximumPermissbleChangeInWeight.setFont(new Font(
							"Tahoma", Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_MaximumPermissbleChangeInWeight = new GridBagConstraints();
					gbc_jLabel_MaximumPermissbleChangeInWeight.anchor = GridBagConstraints.WEST;
					gbc_jLabel_MaximumPermissbleChangeInWeight.insets = new Insets(
							0, 0, 5, 5);
					gbc_jLabel_MaximumPermissbleChangeInWeight.gridx = 0;
					gbc_jLabel_MaximumPermissbleChangeInWeight.gridy = 5;
					JPanel_NoneSliderParameters.add(
							jLabel_MaximumPermissbleChangeInWeight,
							gbc_jLabel_MaximumPermissbleChangeInWeight);
				}
				{
					jTextField_MaximumPermissbleChangeInWeight = new JTextField();
					jTextField_MaximumPermissbleChangeInWeight.setText("0.15");
					jTextField_MaximumPermissbleChangeInWeight
							.setFont(new Font("Tahoma", Font.PLAIN, 9));
					jTextField_MaximumPermissbleChangeInWeight.setColumns(10);
					GridBagConstraints gbc_jTextField_MaximumPermissbleChangeInWeight = new GridBagConstraints();
					gbc_jTextField_MaximumPermissbleChangeInWeight.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_MaximumPermissbleChangeInWeight.insets = new Insets(
							0, 0, 5, 5);
					gbc_jTextField_MaximumPermissbleChangeInWeight.gridx = 1;
					gbc_jTextField_MaximumPermissbleChangeInWeight.gridy = 5;
					JPanel_NoneSliderParameters.add(
							jTextField_MaximumPermissbleChangeInWeight,
							gbc_jTextField_MaximumPermissbleChangeInWeight);
				}
				{
					JLabel jLabel_Lambda = new JLabel("Lambda");
					jLabel_Lambda.setFont(new Font("Tahoma", Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_Lambda = new GridBagConstraints();
					gbc_jLabel_Lambda.anchor = GridBagConstraints.WEST;
					gbc_jLabel_Lambda.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_Lambda.gridx = 2;
					gbc_jLabel_Lambda.gridy = 5;
					JPanel_NoneSliderParameters.add(jLabel_Lambda,
							gbc_jLabel_Lambda);
				}
				{
					jTextField_Lambda = new JTextField();
					jTextField_Lambda.setText("0.1");
					jTextField_Lambda
							.setFont(new Font("Tahoma", Font.PLAIN, 9));
					jTextField_Lambda.setColumns(10);
					GridBagConstraints gbc_jTextField_Lambda = new GridBagConstraints();
					gbc_jTextField_Lambda.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_Lambda.insets = new Insets(0, 0, 5, 0);
					gbc_jTextField_Lambda.gridx = 3;
					gbc_jTextField_Lambda.gridy = 5;
					JPanel_NoneSliderParameters.add(jTextField_Lambda,
							gbc_jTextField_Lambda);
				}
				{
					JLabel jLabelLinearCostFactor = new JLabel(
							"Linear Cost Factor");
					jLabelLinearCostFactor.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabelLinearCostFactor = new GridBagConstraints();
					gbc_jLabelLinearCostFactor.anchor = GridBagConstraints.WEST;
					gbc_jLabelLinearCostFactor.insets = new Insets(0, 0, 5, 5);
					gbc_jLabelLinearCostFactor.gridx = 0;
					gbc_jLabelLinearCostFactor.gridy = 6;
					JPanel_NoneSliderParameters.add(jLabelLinearCostFactor,
							gbc_jLabelLinearCostFactor);
				}
				{
					jTextField_LinearFactor = new JTextField();
					jTextField_LinearFactor.setText("0.07");
					jTextField_LinearFactor.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_LinearFactor.setColumns(10);
					GridBagConstraints gbc_jTextField_LinearFactor = new GridBagConstraints();
					gbc_jTextField_LinearFactor.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_LinearFactor.insets = new Insets(0, 0, 5, 5);
					gbc_jTextField_LinearFactor.gridx = 1;
					gbc_jTextField_LinearFactor.gridy = 6;
					JPanel_NoneSliderParameters.add(jTextField_LinearFactor,
							gbc_jTextField_LinearFactor);
				}
				{
					JLabel jLabel_NumberOfEpochs = new JLabel(
							"Number of Epochs");
					jLabel_NumberOfEpochs.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_NumberOfEpochs = new GridBagConstraints();
					gbc_jLabel_NumberOfEpochs.anchor = GridBagConstraints.WEST;
					gbc_jLabel_NumberOfEpochs.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_NumberOfEpochs.gridx = 2;
					gbc_jLabel_NumberOfEpochs.gridy = 6;
					JPanel_NoneSliderParameters.add(jLabel_NumberOfEpochs,
							gbc_jLabel_NumberOfEpochs);
				}
				{
					jTextField_NumberOfEpochs = new JTextField();
					jTextField_NumberOfEpochs.setText("5");
					jTextField_NumberOfEpochs.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_NumberOfEpochs.setColumns(10);
					GridBagConstraints gbc_jTextField_NumberOfEpochs = new GridBagConstraints();
					gbc_jTextField_NumberOfEpochs.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_NumberOfEpochs.insets = new Insets(0, 0, 5,
							0);
					gbc_jTextField_NumberOfEpochs.gridx = 3;
					gbc_jTextField_NumberOfEpochs.gridy = 6;
					JPanel_NoneSliderParameters.add(jTextField_NumberOfEpochs,
							gbc_jTextField_NumberOfEpochs);
				}
				{
					JLabel jLabel_QuadraticCostFactor = new JLabel(
							"Quadratic Cost Factor");
					jLabel_QuadraticCostFactor.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_QuadraticCostFactor = new GridBagConstraints();
					gbc_jLabel_QuadraticCostFactor.anchor = GridBagConstraints.WEST;
					gbc_jLabel_QuadraticCostFactor.insets = new Insets(0, 0, 0,
							5);
					gbc_jLabel_QuadraticCostFactor.gridx = 0;
					gbc_jLabel_QuadraticCostFactor.gridy = 7;
					JPanel_NoneSliderParameters.add(jLabel_QuadraticCostFactor,
							gbc_jLabel_QuadraticCostFactor);
				}
				{
					jTextField_QuadraticFactor = new JTextField();
					jTextField_QuadraticFactor.setText("0.09");
					jTextField_QuadraticFactor.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_QuadraticFactor.setColumns(10);
					GridBagConstraints gbc_jTextField_QuadraticFactor = new GridBagConstraints();
					gbc_jTextField_QuadraticFactor.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_QuadraticFactor.insets = new Insets(0, 0, 0,
							5);
					gbc_jTextField_QuadraticFactor.gridx = 1;
					gbc_jTextField_QuadraticFactor.gridy = 7;
					JPanel_NoneSliderParameters.add(jTextField_QuadraticFactor,
							gbc_jTextField_QuadraticFactor);
				}
				{
					JLabel jLabel_numberOfIterations = new JLabel(
							"Number of Iterations");
					jLabel_numberOfIterations
							.setToolTipText("Number of iterations per epoch");
					jLabel_numberOfIterations.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_numberOfIterations = new GridBagConstraints();
					gbc_jLabel_numberOfIterations.anchor = GridBagConstraints.WEST;
					gbc_jLabel_numberOfIterations.insets = new Insets(0, 0, 0,
							5);
					gbc_jLabel_numberOfIterations.gridx = 2;
					gbc_jLabel_numberOfIterations.gridy = 7;
					JPanel_NoneSliderParameters.add(jLabel_numberOfIterations,
							gbc_jLabel_numberOfIterations);
				}
				{
					jTextField_numberOfIterations = new JTextField();
					jTextField_numberOfIterations.setText("1000");
					jTextField_numberOfIterations.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_numberOfIterations.setColumns(10);
					GridBagConstraints gbc_jTextField_numberOfIterations = new GridBagConstraints();
					gbc_jTextField_numberOfIterations.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_numberOfIterations.gridx = 3;
					gbc_jTextField_numberOfIterations.gridy = 7;
					JPanel_NoneSliderParameters.add(
							jTextField_numberOfIterations,
							gbc_jTextField_numberOfIterations);
				}
			}
		}
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				applyButton = new JButton("Apply");
				applyButton.setActionCommand("Apply");
				applyButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						applyMDPParameters();
						Parameters.setGlobalMDPDecisionParameters(
								decisionAnalysisPeriodEndString,
								numberOfIterations, numberOfDecisionEpochs,
								portfolioWeightChoiceModel,
								stochasticStateTransitions, linearfactor,
								quadraticfactor, assetWieghtIncrements,
								changeInWeightIncrement,
								maximumPermissbleChangeInWeight, epsilonError,
								gammaDiscountFactor, accuracyThreshold,
								RLLearningRateAlpha, RLLearningLambda,
								RLLearningType, RLActionSelectionType);
					}
				});
				{
					JCheckBox chckbx_PeriodByPeriodCalculations = new JCheckBox(
							"Run MDP Solution Each Simulation Period");
					buttonPane.add(chckbx_PeriodByPeriodCalculations);
				}
				buttonPane.add(applyButton);
				getRootPane().setDefaultButton(applyButton);
			}
			{
				resetButton = new JButton("Reset");
				resetButton.setActionCommand("Reset");
				resetButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						resetMDPParameters();
						initialize();
						Parameters.setGlobalMDPDecisionParameters(
								decisionAnalysisPeriodEndString,
								numberOfIterations, numberOfDecisionEpochs,
								portfolioWeightChoiceModel,
								stochasticStateTransitions, linearfactor,
								quadraticfactor, assetWieghtIncrements,
								changeInWeightIncrement,
								maximumPermissbleChangeInWeight, epsilonError,
								gammaDiscountFactor, accuracyThreshold,
								RLLearningRateAlpha, RLLearningLambda,
								RLLearningType, RLActionSelectionType);
					}
				});
				buttonPane.add(resetButton);
			}

			{
				closeButton = new JButton("Close");
				closeButton.setActionCommand("Close");
				closeButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});

				buttonPane.add(closeButton);
			}
		}

		this.repaint();
	}

	public MDPModelParameterSettingWindow() {
		initialize();

	}

	// Applies values in text fields to the MDP model factors
	private void applyMDPParameters() {

		MDPModelParameterSettingWindow.assetWieghtIncrements = Double
				.parseDouble(jTextField_AssetWieghtIncrements.getText());
		MDPModelParameterSettingWindow.changeInWeightIncrement = Double
				.parseDouble(jTextField_ChangeInWeightIncrement.getText());
		MDPModelParameterSettingWindow.maximumPermissbleChangeInWeight = Double
				.parseDouble(jTextField_MaximumPermissbleChangeInWeight
						.getText());
		MDPModelParameterSettingWindow.linearfactor = Double
				.parseDouble(jTextField_LinearFactor.getText());
		MDPModelParameterSettingWindow.quadraticfactor = Double
				.parseDouble(jTextField_QuadraticFactor.getText());
		MDPModelParameterSettingWindow.numberOfIterations = Integer
				.parseInt(jTextField_numberOfIterations.getText());
		MDPModelParameterSettingWindow.numberOfDecisionEpochs = Integer
				.parseInt(jTextField_NumberOfEpochs.getText());
		MDPModelParameterSettingWindow.accuracyThreshold = Double
				.parseDouble(jTextField_AccuracyThreshold.getText());
		MDPModelParameterSettingWindow.RLLearningRateAlpha = Double
				.parseDouble(jTextField_LearningRate.getText());
		MDPModelParameterSettingWindow.RLLearningLambda = Double
				.parseDouble(jTextField_Lambda.getText());

	}

	/**
	 * resets the MDP model default values
	 */
	private void resetMDPParameters() {
		// MDP Model Variables these variables have to be static because they
		// are treated as global Parameters
		MDPModelParameterSettingWindow.decisionAnalysisPeriodEndString = "2003";
		MDPModelParameterSettingWindow.portfolioWeightChoiceModel = true;
		MDPModelParameterSettingWindow.linearfactor = 0.07;
		MDPModelParameterSettingWindow.quadraticfactor = 0.07;
		MDPModelParameterSettingWindow.assetWieghtIncrements = 0.05;
		MDPModelParameterSettingWindow.changeInWeightIncrement = 0.05;
		MDPModelParameterSettingWindow.maximumPermissbleChangeInWeight = 0.10;
		MDPModelParameterSettingWindow.numberOfIterations = 1000;
		MDPModelParameterSettingWindow.numberOfDecisionEpochs = 5;
		MDPModelParameterSettingWindow.epsilonError = 10 * (0.01 / 100);//
		MDPModelParameterSettingWindow.gammaDiscountFactor = 0.1;
		MDPModelParameterSettingWindow.RLLearningLambda = 0.1;
		MDPModelParameterSettingWindow.accuracyThreshold = 0.0001;
		MDPModelParameterSettingWindow.stochasticStateTransitions = false;
		MDPModelParameterSettingWindow.RLLearningRateAlpha = 0.1;
		MDPModelParameterSettingWindow.RLLearningType = 0;
		MDPModelParameterSettingWindow.RLActionSelectionType = 1;

	}

	/**
	 * determine if model will be based on changes in asset weights or the
	 * actual selection of asset weigths
	 * 
	 * @param value
	 */
	private void setPortfolioWeightChoiceModel(String value) {
		if (value == "Asset Weight Choice") {
			portfolioWeightChoiceModel = true;
		} else {
			portfolioWeightChoiceModel = false;
		}
	}

	/**
	 * Determine which predefined transition probability and asset state returns
	 * data set will be used
	 * 
	 * @param value
	 */
	private void setPreDefinedMDPStartPeriod(String value) {
		decisionAnalysisPeriodEndString = value;
	}

	/**
	 * Determine if the creation of transition probabilities are to be agent
	 * calculated i.e. endogenous or use predefined data i.e. exogenous
	 * 
	 * @param value
	 */
	private void setStochasticStateTransitionsBoolean(int value) {
		// TODO Auto-generated method stub
		if (value == 0) {
			stochasticStateTransitions = false;
		} else {
			stochasticStateTransitions = true;
		}
	}

	private void setLearningTypes(String value) {
		if (value == "VALUE ITERATION") {
			RLLearningType = 0;
		} else if (value == "Q-LEARNING") {
			RLLearningType = 1;
		} else if (value == "SARSA") {
			RLLearningType = 2;
		} else if (value == "Q_LAMBDA") {
			RLLearningType = 3;
		}
	}

	private void setActionSelectionType(String value) {
		if (value == "NONE") {
			RLActionSelectionType = 0;
		} else if (value == "E-GREEDY") {
			RLActionSelectionType = 1;
		} else if (value == "SOFTMAX") {
			RLActionSelectionType = 2;
		}
	}

	private void setEpsilonError(double value) {

		epsilonError = value * (0.01 / 100);
	}

	private void setGammaDistountFactor(double value) {

		gammaDiscountFactor = value / 100;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CORE UTILITY CLASSES: SLIDER
	// CHANGE LISTENERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	/**
	 * EpsilonError jSlider change listener
	 * 
	 * @author Oluwasegun Bewaji
	 * 
	 */
	class EpsilonErrorChangeListener implements ChangeListener {
		EpsilonErrorChangeListener() {
		}

		@Override
		public synchronized void stateChanged(ChangeEvent e) {
			double value = jSlider_EpsilonError.getValue();
			setEpsilonError(value);
		}
	}

	/**
	 * GammaDistountFactor jSlider change listener
	 * 
	 * @author Oluwasegun Bewaji
	 * 
	 */
	class GammaDistountFactorChangeListener implements ChangeListener {
		GammaDistountFactorChangeListener() {
		}

		@Override
		public synchronized void stateChanged(ChangeEvent e) {
			double value = jSlider_GammaDistountFactor.getValue();
			setGammaDistountFactor(value);
		}
	}

}
