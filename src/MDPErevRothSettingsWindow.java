import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class MDPErevRothSettingsWindow extends JDialog {
	
	
	public static String ErevRothLearnerUpdateType = "Nicolaosen Variation";//can be "Standard" or "Nicolaosen Variation";
	public static String ErevRothProbabilityModel = "Gibbs-Boltzmann";//can be "Standard" or "Gibbs-Boltzmann";
	public static boolean periodByPeriodCalculationsErevRoth = true;
	public static boolean identicalParameterSelection = true;
	public static int numberOfIterationsErevRoth = 10000;
	public static int numberOfDecisionEpochsErevRoth = 5;
	public static double securitisationRateIncrementErevRoth = 0.05;//sliders
	public static double securitisationRateMaxChangeErevRoth = 0.5;//sliders
	public static int numberOfActionsErevRoth = (int) (1+ (2*(Parameters.securitisationRateMaxChangeErevRoth
			/Parameters.securitisationRateIncrementErevRoth)));
	public static double GibbsBoltzmannParameterErevRoth = 1000;
	public static double scalingParameterErevRoth = 9;
	public static  double averageStartingRewardErevRoth = 100;
	public static double genericExperimentationFactor = 0.75;
	public static double maximumExperimentationFactor = 0.9;
	public static double minimumExperimentationFactor = 0.1;
	public static double  genericRecencyFactorErevRoth = 0.1;
	public static double  minRecencyFactorErevRoth = 0.1;
	public static double  maxRecencyFactorErevRoth = 0.75;
	public static double loanMarketSentimentShareErevRoth = 0.5;
	public static double betaDistributionAlphaGenericErevRoth = 2;
	public static double betaDistributionBetaGenericErevRoth = 5;
	public static double betaDistributionAlphaMinErevRoth = 1;
	public static double betaDistributionBetaMaxErevRoth = 7;
	public static double betaDistributionAlphaMaxErevRoth = 7;
	public static double betaDistributionBetaMinErevRoth = 1;
	public static int randomSeedErevRoth = 74974984;

	private final JPanel contentPanel = new JPanel();


	private JTextField jTextField_numberOfIterations;
	private JTextField jTextField_maximumExperimentationFactor;

	private JPanel buttonPane;
	private JButton applyButton;
	private JButton resetButton;
	private JButton closeButton;
	private JComboBox jComboBox_ErevRothLearnerUpdateType;
	private JComboBox jComboBox_ErevRothProbabilityModel;
	private JComboBox jComboBox_IdenticalStateSelectionBias;
	private JCheckBox chckbx_PeriodByPeriodCalculations;

	private JSlider jSlider_SecuritisationRateIncrement;
	private JSlider jSlider_SecuritisationRateMaxChange;
	private JTextField jTextField_minimumExperimentationFactor;
	private JTextField jTextField_LoanMarketSentimentShare;
	private JTextField jTextField_genericExperimentationFactor;
	private JTextField jTextField_NumberOfEpochs;
	
	private JTextField jTextField_maxRecencyFactorErevRoth;
	private JTextField jTextField_minRecencyFactorErevRoth;
	private JTextField jTextField_genericRecencyFactorErevRoth;
	
	private JTextField jTextField_scalingFactorErevRoth;
	
	private JTextField  jTextField_BetaDistributionAlphaGeneric;
	private JTextField jTextField_BetaDistributionBetaGeneric;
	private JTextField jTextField_BetaDistributionAlphaMin;
	private JTextField jTextField_BetaDistributionBetaMax;
	private JTextField jTextField_BetaDistributionAlphaMax;
	private JTextField jTextField_BetaDistributionBetaMin;
	
	private JTextField jTextField_GibbsBoltzmannParameterErevRoth;
	private JTextField jTextField_randomSeedErevRoth;
	
	JLabel jLabel_SecuritisationRateIncrement;

/**
 * Launch the application.
 */
public static void main(String[] args) {
	try {
		MDPErevRothSettingsWindow dialog = new MDPErevRothSettingsWindow();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * Create the dialog.
 */
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
		contentPanel.add(jPanel_MDPParameters, "Erev-Roth Model Parameters");
		GridBagLayout gbl_jPanel_MDPParameters = new GridBagLayout();
		gbl_jPanel_MDPParameters.columnWidths = new int[] { 637, 0 };
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
				JPanel jPanel_SecuritisationRateIncrement = new JPanel();
				GridBagConstraints gbc_jPanel_SecuritisationRateIncrement = new GridBagConstraints();
				gbc_jPanel_SecuritisationRateIncrement.fill = GridBagConstraints.BOTH;
				gbc_jPanel_SecuritisationRateIncrement.insets = new Insets(0, 0, 5, 0);
				gbc_jPanel_SecuritisationRateIncrement.gridx = 0;
				gbc_jPanel_SecuritisationRateIncrement.gridy = 0;
				JPanel_GammaEpsilon.add(jPanel_SecuritisationRateIncrement,
						gbc_jPanel_SecuritisationRateIncrement);
				GridBagLayout gbl_jPanel_SecuritisationRateIncrement = new GridBagLayout();
				gbl_jPanel_SecuritisationRateIncrement.columnWidths = new int[] { 101,
						335, 0 };
				gbl_jPanel_SecuritisationRateIncrement.rowHeights = new int[] { 0, 0 };
				gbl_jPanel_SecuritisationRateIncrement.columnWeights = new double[] { 0.0,
						0.0, Double.MIN_VALUE };
				gbl_jPanel_SecuritisationRateIncrement.rowWeights = new double[] { 0.0,
						Double.MIN_VALUE };
				jPanel_SecuritisationRateIncrement.setLayout(gbl_jPanel_SecuritisationRateIncrement);
				{
					jLabel_SecuritisationRateIncrement = new JLabel(
							"Securitisation Rate Increase (%)               ");
					jLabel_SecuritisationRateIncrement
							.setFont(new Font("Tahoma", Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_SecuritisationRateIncrement = new GridBagConstraints();
					gbc_jLabel_SecuritisationRateIncrement.fill = GridBagConstraints.BOTH;
					gbc_jLabel_SecuritisationRateIncrement.anchor = GridBagConstraints.WEST;
					gbc_jLabel_SecuritisationRateIncrement.insets = new Insets(0, 0, 0, 5);
					gbc_jLabel_SecuritisationRateIncrement.gridx = 0;
					gbc_jLabel_SecuritisationRateIncrement.gridy = 0;
					jPanel_SecuritisationRateIncrement.add(jLabel_SecuritisationRateIncrement,
							gbc_jLabel_SecuritisationRateIncrement);
				}
				{
					jSlider_SecuritisationRateIncrement = new JSlider();
					jSlider_SecuritisationRateIncrement.setMaximum(100);
					jSlider_SecuritisationRateIncrement.setPaintTicks(true);
					jSlider_SecuritisationRateIncrement.setPaintLabels(true);
					jSlider_SecuritisationRateIncrement.setValue(5);
					double value = jSlider_SecuritisationRateIncrement.getValue();
					setSecuritisationRateIncrement(value);
					jSlider_SecuritisationRateIncrement
							.setToolTipText("Securitisation Rate Increment");
					jSlider_SecuritisationRateIncrement.setMinorTickSpacing(5);
					jSlider_SecuritisationRateIncrement.setMajorTickSpacing(10);
					jSlider_SecuritisationRateIncrement.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jSlider_SecuritisationRateIncrement.setBorder(null);
					SecuritisationRateIncrementChangeListener eelstnr = new SecuritisationRateIncrementChangeListener();// internal
																							// class
					jSlider_SecuritisationRateIncrement.addChangeListener(eelstnr);
					GridBagConstraints gbc_jSlider_SecuritisationRateIncrement = new GridBagConstraints();
					gbc_jSlider_SecuritisationRateIncrement.fill = GridBagConstraints.BOTH;
					gbc_jSlider_SecuritisationRateIncrement.anchor = GridBagConstraints.WEST;
					gbc_jSlider_SecuritisationRateIncrement.gridx = 1;
					gbc_jSlider_SecuritisationRateIncrement.gridy = 0;
					jPanel_SecuritisationRateIncrement.add(jSlider_SecuritisationRateIncrement,
							gbc_jSlider_SecuritisationRateIncrement);
				}
			}
			{
				JPanel jPanel_GreedParameterIncrement = new JPanel();
				GridBagConstraints gbc_jPanel_GreedParameterIncrement = new GridBagConstraints();
				gbc_jPanel_GreedParameterIncrement.anchor = GridBagConstraints.NORTHWEST;
				gbc_jPanel_GreedParameterIncrement.gridx = 0;
				gbc_jPanel_GreedParameterIncrement.gridy = 1;
				JPanel_GammaEpsilon.add(jPanel_GreedParameterIncrement, gbc_jPanel_GreedParameterIncrement);
				GridBagLayout gbl_jPanel_GreedParameterIncrement = new GridBagLayout();
				gbl_jPanel_GreedParameterIncrement.columnWidths = new int[] { 101, 336, 0 };
				gbl_jPanel_GreedParameterIncrement.rowHeights = new int[] { 0, 0 };
				gbl_jPanel_GreedParameterIncrement.columnWeights = new double[] { 0.0, 0.0,
						Double.MIN_VALUE };
				gbl_jPanel_GreedParameterIncrement.rowWeights = new double[] { 0.0,
						Double.MIN_VALUE };
				jPanel_GreedParameterIncrement.setLayout(gbl_jPanel_GreedParameterIncrement);
				{
					JLabel jLabel_GreedParameterIncrement = new JLabel(
							"Maximum Change: Securitisation Rate (%)");
					jLabel_GreedParameterIncrement.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_GreedParameterIncrement = new GridBagConstraints();
					gbc_jLabel_GreedParameterIncrement.fill = GridBagConstraints.BOTH;
					gbc_jLabel_GreedParameterIncrement.anchor = GridBagConstraints.WEST;
					gbc_jLabel_GreedParameterIncrement.insets = new Insets(0,
							0, 0, 5);
					gbc_jLabel_GreedParameterIncrement.gridx = 0;
					gbc_jLabel_GreedParameterIncrement.gridy = 0;
					jPanel_GreedParameterIncrement.add(jLabel_GreedParameterIncrement,
							gbc_jLabel_GreedParameterIncrement);
				}
				{
					jSlider_SecuritisationRateMaxChange = new JSlider();
					jSlider_SecuritisationRateMaxChange.setMaximum(100);
					jSlider_SecuritisationRateMaxChange.setPaintLabels(true);
					jSlider_SecuritisationRateMaxChange.setPaintTicks(true);
					double valueG = jSlider_SecuritisationRateMaxChange.getValue();
					setSecuritisationRateMaxChange(valueG);
					jSlider_SecuritisationRateMaxChange
							.setToolTipText("Greed Parameter Increment");
					jSlider_SecuritisationRateMaxChange.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jSlider_SecuritisationRateMaxChange.setBorder(null);
					jSlider_SecuritisationRateMaxChange.setMajorTickSpacing(10);
					jSlider_SecuritisationRateMaxChange.setMinorTickSpacing(5);
					SecuritisationRateMaxChangeChangeListener gdflstnr = new SecuritisationRateMaxChangeChangeListener();// internal
																											// class
					jSlider_SecuritisationRateMaxChange.addChangeListener(gdflstnr);
					GridBagConstraints gbc_jSlider_SecuritisationRateMaxChange = new GridBagConstraints();
					gbc_jSlider_SecuritisationRateMaxChange.anchor = GridBagConstraints.WEST;
					gbc_jSlider_SecuritisationRateMaxChange.fill = GridBagConstraints.BOTH;
					gbc_jSlider_SecuritisationRateMaxChange.gridx = 1;
					gbc_jSlider_SecuritisationRateMaxChange.gridy = 0;
					jPanel_GreedParameterIncrement.add(jSlider_SecuritisationRateMaxChange,
							gbc_jSlider_SecuritisationRateMaxChange);
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
					0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_JPanel_NoneSliderParameters.columnWeights = new double[] {
					0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
			gbl_JPanel_NoneSliderParameters.rowWeights = new double[] {
					0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			JPanel_NoneSliderParameters
					.setLayout(gbl_JPanel_NoneSliderParameters);
			
			//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF SLIDERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			
			
			{
				JLabel jLabel_ErevRothLearnerUpdateType = new JLabel(
						"Erev-Roth Update Model");
				jLabel_ErevRothLearnerUpdateType.setFont(new Font(
						"Tahoma", Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_ErevRothLearnerUpdateType = new GridBagConstraints();
				gbc_jLabel_ErevRothLearnerUpdateType.anchor = GridBagConstraints.WEST;
				gbc_jLabel_ErevRothLearnerUpdateType.insets = new Insets(
						0, 0, 5, 5);
				gbc_jLabel_ErevRothLearnerUpdateType.gridx = 0;
				gbc_jLabel_ErevRothLearnerUpdateType.gridy = 0;
				JPanel_NoneSliderParameters.add(
						jLabel_ErevRothLearnerUpdateType,
						gbc_jLabel_ErevRothLearnerUpdateType);
			}
			{
				jComboBox_ErevRothLearnerUpdateType = new JComboBox();
				jComboBox_ErevRothLearnerUpdateType
						.setModel(new DefaultComboBoxModel(new String[] {
								"Standard", "Nicolaosen Variation" }));
				jComboBox_ErevRothLearnerUpdateType.setSelectedIndex(1);
				String value = (String) jComboBox_ErevRothLearnerUpdateType
						.getSelectedItem();
				setErevRothLearnerUpdateType(value);
				jComboBox_ErevRothLearnerUpdateType.setFont(new Font(
						"Tahoma", Font.PLAIN, 9));
				jComboBox_ErevRothLearnerUpdateType
						.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								JComboBox jcmbType = (JComboBox) e
										.getSource();
								String value = (String) jcmbType
										.getSelectedItem();
								setErevRothLearnerUpdateType(value);
							}
						});
				GridBagConstraints gbc_jComboBox_ErevRothLearnerUpdateType = new GridBagConstraints();
				gbc_jComboBox_ErevRothLearnerUpdateType.fill = GridBagConstraints.BOTH;
				gbc_jComboBox_ErevRothLearnerUpdateType.anchor = GridBagConstraints.WEST;
				gbc_jComboBox_ErevRothLearnerUpdateType.insets = new Insets(
						0, 0, 5, 5);
				gbc_jComboBox_ErevRothLearnerUpdateType.gridx = 1;
				gbc_jComboBox_ErevRothLearnerUpdateType.gridy = 0;
				JPanel_NoneSliderParameters.add(
						jComboBox_ErevRothLearnerUpdateType,
						gbc_jComboBox_ErevRothLearnerUpdateType);
			
			}
			
			
			{
				JLabel jLabel_ErevRothProbabilityModel = new JLabel(
						"Erev-Roth Probability Update Model");
				jLabel_ErevRothProbabilityModel.setFont(new Font(
						"Tahoma", Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_ErevRothProbabilityModel = new GridBagConstraints();
				gbc_jLabel_ErevRothProbabilityModel.anchor = GridBagConstraints.WEST;
				gbc_jLabel_ErevRothProbabilityModel.insets = new Insets(
						0, 0, 5, 5);
				gbc_jLabel_ErevRothProbabilityModel.gridx = 2;
				gbc_jLabel_ErevRothProbabilityModel.gridy = 0;
				JPanel_NoneSliderParameters.add(
						jLabel_ErevRothProbabilityModel,
						gbc_jLabel_ErevRothProbabilityModel);
			}
			{
				jComboBox_ErevRothProbabilityModel = new JComboBox();
				jComboBox_ErevRothProbabilityModel
						.setModel(new DefaultComboBoxModel(new String[] {
								"Standard", "Gibbs-Boltzmann" }));
				jComboBox_ErevRothProbabilityModel.setSelectedIndex(1);
				String value = (String) jComboBox_ErevRothProbabilityModel
						.getSelectedItem();
				setErevRothProbabilityModel(value);
				jComboBox_ErevRothProbabilityModel.setFont(new Font(
						"Tahoma", Font.PLAIN, 9));
				jComboBox_ErevRothProbabilityModel
						.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								JComboBox jcmbType = (JComboBox) e
										.getSource();
								String value = (String) jcmbType
										.getSelectedItem();
								setErevRothProbabilityModel(value);
							}
						});
				GridBagConstraints gbc_jComboBox_ErevRothProbabilityModel = new GridBagConstraints();
				gbc_jComboBox_ErevRothProbabilityModel.fill = GridBagConstraints.BOTH;
				gbc_jComboBox_ErevRothProbabilityModel.anchor = GridBagConstraints.EAST;
				gbc_jComboBox_ErevRothProbabilityModel.insets = new Insets(0, 0, 5, 0);
				gbc_jComboBox_ErevRothProbabilityModel.gridx = 3;
				gbc_jComboBox_ErevRothProbabilityModel.gridy = 0;
				JPanel_NoneSliderParameters.add(
						jComboBox_ErevRothProbabilityModel,
						gbc_jComboBox_ErevRothProbabilityModel);
			
			}
			
			
			
			{
				JLabel jLabel_IdenticalStateSelectionBias = new JLabel(
						"Identical State Selection Bias");
				jLabel_IdenticalStateSelectionBias.setFont(new Font(
						"Tahoma", Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_IdenticalStateSelectionBias = new GridBagConstraints();
				gbc_jLabel_IdenticalStateSelectionBias.anchor = GridBagConstraints.WEST;
				gbc_jLabel_IdenticalStateSelectionBias.insets = new Insets(
						0, 0, 5, 5);
				gbc_jLabel_IdenticalStateSelectionBias.gridx = 0;
				gbc_jLabel_IdenticalStateSelectionBias.gridy = 1;
				JPanel_NoneSliderParameters.add(
						jLabel_IdenticalStateSelectionBias,
						gbc_jLabel_IdenticalStateSelectionBias);
			}
			{
				jComboBox_IdenticalStateSelectionBias = new JComboBox();
				jComboBox_IdenticalStateSelectionBias
						.setModel(new DefaultComboBoxModel(new String[] {
								"false", "true" }));
				jComboBox_IdenticalStateSelectionBias.setSelectedIndex(1);
				int value = jComboBox_IdenticalStateSelectionBias
						.getSelectedIndex();
				setIdenticalStateSelectionBias(value);
				jComboBox_IdenticalStateSelectionBias.setFont(new Font(
						"Tahoma", Font.PLAIN, 9));
				jComboBox_IdenticalStateSelectionBias
						.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								JComboBox jcmbType = (JComboBox) e
										.getSource();
								int value = jcmbType
										.getSelectedIndex();
								setIdenticalStateSelectionBias(value);
							}
						});
				GridBagConstraints gbc_jComboBox_StochasticStateTransitions = new GridBagConstraints();
				gbc_jComboBox_StochasticStateTransitions.fill = GridBagConstraints.BOTH;
				gbc_jComboBox_StochasticStateTransitions.anchor = GridBagConstraints.WEST;
				gbc_jComboBox_StochasticStateTransitions.insets = new Insets(
						0, 0, 5, 5);
				gbc_jComboBox_StochasticStateTransitions.gridx = 1;
				gbc_jComboBox_StochasticStateTransitions.gridy = 1;
				JPanel_NoneSliderParameters.add(
						jComboBox_IdenticalStateSelectionBias,
						gbc_jComboBox_StochasticStateTransitions);
			}
			
			{
				JLabel jLabel_NumberOfEpochs = new JLabel(
						"Number of Epochs");
				jLabel_NumberOfEpochs.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_NumberOfEpochs = new GridBagConstraints();
				gbc_jLabel_NumberOfEpochs.anchor = GridBagConstraints.WEST;
				gbc_jLabel_NumberOfEpochs.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_NumberOfEpochs.gridx = 0;
				gbc_jLabel_NumberOfEpochs.gridy = 2;
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
				gbc_jTextField_NumberOfEpochs.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_NumberOfEpochs.gridx = 1;
				gbc_jTextField_NumberOfEpochs.gridy = 2;
				JPanel_NoneSliderParameters.add(jTextField_NumberOfEpochs,
						gbc_jTextField_NumberOfEpochs);
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
				gbc_jLabel_numberOfIterations.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_numberOfIterations.gridx = 2;
				gbc_jLabel_numberOfIterations.gridy = 2;
				JPanel_NoneSliderParameters.add(jLabel_numberOfIterations,
						gbc_jLabel_numberOfIterations);
			}
			{
				jTextField_numberOfIterations = new JTextField();
				jTextField_numberOfIterations.setText("10000");
				jTextField_numberOfIterations.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_numberOfIterations.setColumns(10);
				GridBagConstraints gbc_jTextField_numberOfIterations = new GridBagConstraints();
				gbc_jTextField_numberOfIterations.insets = new Insets(0, 0, 5, 0);
				gbc_jTextField_numberOfIterations.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_numberOfIterations.gridx = 3;
				gbc_jTextField_numberOfIterations.gridy = 2;
				JPanel_NoneSliderParameters.add(
						jTextField_numberOfIterations,
						gbc_jTextField_numberOfIterations);
			}
			
			
			{
				JLabel jLabel_GibbsBoltzmannParameterErevRoth = new JLabel("Gibbs-Boltzmann Parameter");
				jLabel_GibbsBoltzmannParameterErevRoth.setToolTipText("Gibbs-Boltzmann Parameter");
				jLabel_GibbsBoltzmannParameterErevRoth.setFont(new Font("Tahoma", Font.PLAIN,
						9));
				GridBagConstraints gbc_jLabel_GibbsBoltzmannParameterErevRoth = new GridBagConstraints();
				gbc_jLabel_GibbsBoltzmannParameterErevRoth.anchor = GridBagConstraints.WEST;
				gbc_jLabel_GibbsBoltzmannParameterErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_GibbsBoltzmannParameterErevRoth.gridx = 0;
				gbc_jLabel_GibbsBoltzmannParameterErevRoth.gridy = 3;
				JPanel_NoneSliderParameters.add(jLabel_GibbsBoltzmannParameterErevRoth,
						gbc_jLabel_GibbsBoltzmannParameterErevRoth);
			}
			{
				jTextField_GibbsBoltzmannParameterErevRoth = new JTextField();
				jTextField_GibbsBoltzmannParameterErevRoth.setToolTipText("Gibbs-Boltzmann Parameter");
				jTextField_GibbsBoltzmannParameterErevRoth.setText("1");
				jTextField_GibbsBoltzmannParameterErevRoth.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_GibbsBoltzmannParameterErevRoth.setColumns(10);
				GridBagConstraints gbc_jTextField_GibbsBoltzmannParameterErevRoth = new GridBagConstraints();
				gbc_jTextField_GibbsBoltzmannParameterErevRoth.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_GibbsBoltzmannParameterErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_GibbsBoltzmannParameterErevRoth.gridx = 1;
				gbc_jTextField_GibbsBoltzmannParameterErevRoth.gridy = 3;
				JPanel_NoneSliderParameters.add(jTextField_GibbsBoltzmannParameterErevRoth,
						gbc_jTextField_GibbsBoltzmannParameterErevRoth);
			}
			{
				JLabel jLabel_randomSeedErevRoth = new JLabel(
						"Random Seed");
				jLabel_randomSeedErevRoth.setToolTipText("Random Seed");
				jLabel_randomSeedErevRoth.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_randomSeedErevRoth = new GridBagConstraints();
				gbc_jLabel_randomSeedErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_randomSeedErevRoth.anchor = GridBagConstraints.WEST;
				gbc_jLabel_randomSeedErevRoth.gridx = 2;
				gbc_jLabel_randomSeedErevRoth.gridy = 3;
				JPanel_NoneSliderParameters.add(jLabel_randomSeedErevRoth,
						gbc_jLabel_randomSeedErevRoth);
			}
			{
				jTextField_randomSeedErevRoth = new JTextField();
				jTextField_randomSeedErevRoth.setToolTipText("Random Seed");
				jTextField_randomSeedErevRoth.setText("74974984");
				jTextField_randomSeedErevRoth.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_randomSeedErevRoth.setColumns(10);
				GridBagConstraints gbc_jTextField_randomSeedErevRoth = new GridBagConstraints();
				gbc_jTextField_randomSeedErevRoth.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_randomSeedErevRoth.insets = new Insets(0, 0,
						5, 0);
				gbc_jTextField_randomSeedErevRoth.gridx = 3;
				gbc_jTextField_randomSeedErevRoth.gridy = 3;
				JPanel_NoneSliderParameters.add(
						jTextField_randomSeedErevRoth,
						gbc_jTextField_randomSeedErevRoth);
			}
			
			//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			
			
			{
				JLabel jLabel_minimumExperimentationFactor = new JLabel("Minimum Experimentation Factor");
				jLabel_minimumExperimentationFactor.setToolTipText("Minimum Experimentation Factor");
				jLabel_minimumExperimentationFactor.setFont(new Font("Tahoma", Font.PLAIN,
						9));
				GridBagConstraints gbc_jLabel_minimumExperimentationFactor = new GridBagConstraints();
				gbc_jLabel_minimumExperimentationFactor.anchor = GridBagConstraints.WEST;
				gbc_jLabel_minimumExperimentationFactor.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_minimumExperimentationFactor.gridx = 0;
				gbc_jLabel_minimumExperimentationFactor.gridy = 4;
				JPanel_NoneSliderParameters.add(jLabel_minimumExperimentationFactor,
						gbc_jLabel_minimumExperimentationFactor);
			}
			{
				jTextField_minimumExperimentationFactor = new JTextField();
				jTextField_minimumExperimentationFactor.setToolTipText("Minimum Experimentation Factor 0<min<1");
				jTextField_minimumExperimentationFactor.setText("0.1");
				jTextField_minimumExperimentationFactor.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_minimumExperimentationFactor.setColumns(10);
				GridBagConstraints gbc_jTextField_minimumExperimentationFactor = new GridBagConstraints();
				gbc_jTextField_minimumExperimentationFactor.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_minimumExperimentationFactor.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_minimumExperimentationFactor.gridx = 1;
				gbc_jTextField_minimumExperimentationFactor.gridy = 4;
				JPanel_NoneSliderParameters.add(jTextField_minimumExperimentationFactor,
						gbc_jTextField_minimumExperimentationFactor);
			}
			{
				JLabel jLabel_maximumExperimentationFactor = new JLabel(
						"Maximum Experimentation Factor");
				jLabel_maximumExperimentationFactor.setToolTipText("Maximum Experimentation Factor");
				jLabel_maximumExperimentationFactor.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_maximumExperimentationFactor = new GridBagConstraints();
				gbc_jLabel_maximumExperimentationFactor.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_maximumExperimentationFactor.anchor = GridBagConstraints.WEST;
				gbc_jLabel_maximumExperimentationFactor.gridx = 2;
				gbc_jLabel_maximumExperimentationFactor.gridy = 4;
				JPanel_NoneSliderParameters.add(jLabel_maximumExperimentationFactor,
						gbc_jLabel_maximumExperimentationFactor);
			}
			{
				jTextField_maximumExperimentationFactor = new JTextField();
				jTextField_maximumExperimentationFactor.setToolTipText("Maximum Experimentation Factor min <= max <1");
				jTextField_maximumExperimentationFactor.setText("0.9");
				jTextField_maximumExperimentationFactor.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_maximumExperimentationFactor.setColumns(10);
				GridBagConstraints gbc_jTextField_maximumExperimentationFactor = new GridBagConstraints();
				gbc_jTextField_maximumExperimentationFactor.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_maximumExperimentationFactor.insets = new Insets(0, 0,
						5, 0);
				gbc_jTextField_maximumExperimentationFactor.gridx = 3;
				gbc_jTextField_maximumExperimentationFactor.gridy = 4;
				JPanel_NoneSliderParameters.add(
						jTextField_maximumExperimentationFactor,
						gbc_jTextField_maximumExperimentationFactor);
			}
			{
				JLabel jLabel_genericExperimentationFactor = new JLabel("Generic Experimentation Factor");
				jLabel_genericExperimentationFactor.setToolTipText("genericExperimentationFactor");
				jLabel_genericExperimentationFactor.setFont(new Font("Tahoma", Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_genericExperimentationFactor = new GridBagConstraints();
				gbc_jLabel_genericExperimentationFactor.anchor = GridBagConstraints.WEST;
				gbc_jLabel_genericExperimentationFactor.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_genericExperimentationFactor.gridx = 0;
				gbc_jLabel_genericExperimentationFactor.gridy = 5;
				JPanel_NoneSliderParameters.add(jLabel_genericExperimentationFactor,
						gbc_jLabel_genericExperimentationFactor);
			}
			{
				jTextField_genericExperimentationFactor = new JTextField();
				jTextField_genericExperimentationFactor.setToolTipText("Generic Experimentation Factor");
				jTextField_genericExperimentationFactor.setText("0.90");
				jTextField_genericExperimentationFactor
						.setFont(new Font("Tahoma", Font.PLAIN, 9));
				jTextField_genericExperimentationFactor.setColumns(10);
				GridBagConstraints gbc_jTextField_genericExperimentationFactor = new GridBagConstraints();
				gbc_jTextField_genericExperimentationFactor.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_genericExperimentationFactor.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_genericExperimentationFactor.gridx = 1;
				gbc_jTextField_genericExperimentationFactor.gridy = 5;
				JPanel_NoneSliderParameters.add(jTextField_genericExperimentationFactor,
						gbc_jTextField_genericExperimentationFactor);
			}
			
			//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<RECENCY FACTOR>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			{
				JLabel jLabel_genericRecencyFactorErevRoth = new JLabel("Generic Recency Factor");
				jLabel_genericRecencyFactorErevRoth.setToolTipText("generic Recency Factor");
				jLabel_genericRecencyFactorErevRoth.setFont(new Font("Tahoma", Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_genericRecencyFactorErevRoth = new GridBagConstraints();
				gbc_jLabel_genericRecencyFactorErevRoth.anchor = GridBagConstraints.WEST;
				gbc_jLabel_genericRecencyFactorErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_genericRecencyFactorErevRoth.gridx = 2;
				gbc_jLabel_genericRecencyFactorErevRoth.gridy = 5;
				JPanel_NoneSliderParameters.add(jLabel_genericRecencyFactorErevRoth,
						gbc_jLabel_genericRecencyFactorErevRoth);
			}
			{
				jTextField_genericRecencyFactorErevRoth = new JTextField();
				jTextField_genericRecencyFactorErevRoth.setToolTipText("Generic Recency Factor");
				jTextField_genericRecencyFactorErevRoth.setText("0.20");
				jTextField_genericRecencyFactorErevRoth
						.setFont(new Font("Tahoma", Font.PLAIN, 9));
				jTextField_genericRecencyFactorErevRoth.setColumns(10);
				GridBagConstraints gbc_jTextField_genericRecencyFactorErevRoth = new GridBagConstraints();
				gbc_jTextField_genericRecencyFactorErevRoth.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_genericRecencyFactorErevRoth.insets = new Insets(0, 0, 5, 0);
				gbc_jTextField_genericRecencyFactorErevRoth.gridx = 3;
				gbc_jTextField_genericRecencyFactorErevRoth.gridy = 5;
				JPanel_NoneSliderParameters.add(jTextField_genericRecencyFactorErevRoth,
						gbc_jTextField_genericRecencyFactorErevRoth);
			}
			
			
			{
				JLabel jLabel_minRecencyFactorErevRoth = new JLabel("Minimum Recency Factor");
				jLabel_minRecencyFactorErevRoth.setToolTipText("Minumum Recency Factor");
				jLabel_minRecencyFactorErevRoth.setFont(new Font("Tahoma", Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_minRecencyFactorErevRoth = new GridBagConstraints();
				gbc_jLabel_minRecencyFactorErevRoth.anchor = GridBagConstraints.WEST;
				gbc_jLabel_minRecencyFactorErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_minRecencyFactorErevRoth.gridx = 0;
				gbc_jLabel_minRecencyFactorErevRoth.gridy = 6;
				JPanel_NoneSliderParameters.add(jLabel_minRecencyFactorErevRoth,
						gbc_jLabel_minRecencyFactorErevRoth);
			}
			{
				jTextField_minRecencyFactorErevRoth = new JTextField();
				jTextField_minRecencyFactorErevRoth.setToolTipText("Minimum Recency Factor");
				jTextField_minRecencyFactorErevRoth.setText("0.1");
				jTextField_minRecencyFactorErevRoth
						.setFont(new Font("Tahoma", Font.PLAIN, 9));
				jTextField_minRecencyFactorErevRoth.setColumns(10);
				GridBagConstraints gbc_jTextField_minRecencyFactorErevRoth = new GridBagConstraints();
				gbc_jTextField_minRecencyFactorErevRoth.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_minRecencyFactorErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_minRecencyFactorErevRoth.gridx = 1;
				gbc_jTextField_minRecencyFactorErevRoth.gridy = 6;
				JPanel_NoneSliderParameters.add(jTextField_minRecencyFactorErevRoth,
						gbc_jTextField_minRecencyFactorErevRoth);
			}
			
			
			{
				JLabel jLabel_maxRecencyFactorErevRoth = new JLabel("Maximum Recency Factor");
				jLabel_maxRecencyFactorErevRoth.setToolTipText("Maximum Recency Factor");
				jLabel_maxRecencyFactorErevRoth.setFont(new Font("Tahoma", Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_maxRecencyFactorErevRoth = new GridBagConstraints();
				gbc_jLabel_maxRecencyFactorErevRoth.anchor = GridBagConstraints.WEST;
				gbc_jLabel_maxRecencyFactorErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_maxRecencyFactorErevRoth.gridx = 2;
				gbc_jLabel_maxRecencyFactorErevRoth.gridy = 6;
				JPanel_NoneSliderParameters.add(jLabel_maxRecencyFactorErevRoth,
						gbc_jLabel_maxRecencyFactorErevRoth);
			}
			{
				jTextField_maxRecencyFactorErevRoth = new JTextField();
				jTextField_maxRecencyFactorErevRoth.setToolTipText("Maximum Recency Factor");
				jTextField_maxRecencyFactorErevRoth.setText("0.75");
				jTextField_maxRecencyFactorErevRoth
						.setFont(new Font("Tahoma", Font.PLAIN, 9));
				jTextField_maxRecencyFactorErevRoth.setColumns(10);
				GridBagConstraints gbc_jTextField_maxRecencyFactorErevRoth = new GridBagConstraints();
				gbc_jTextField_maxRecencyFactorErevRoth.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_maxRecencyFactorErevRoth.insets = new Insets(0, 0, 5, 0);
				gbc_jTextField_maxRecencyFactorErevRoth.gridx = 3;
				gbc_jTextField_maxRecencyFactorErevRoth.gridy = 6;
				JPanel_NoneSliderParameters.add(jTextField_maxRecencyFactorErevRoth,
						gbc_jTextField_maxRecencyFactorErevRoth);
			}
			{
				JLabel jLabel_scalingFactorErevRoth = new JLabel(
						"Scaling Factor");
				jLabel_scalingFactorErevRoth.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_scalingFactorErevRoth = new GridBagConstraints();
				gbc_jLabel_scalingFactorErevRoth.anchor = GridBagConstraints.WEST;
				gbc_jLabel_scalingFactorErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_scalingFactorErevRoth.gridx = 0;
				gbc_jLabel_scalingFactorErevRoth.gridy = 7;
				JPanel_NoneSliderParameters.add(jLabel_scalingFactorErevRoth,
						gbc_jLabel_scalingFactorErevRoth);
			}
			{
				jTextField_scalingFactorErevRoth = new JTextField();
				jTextField_scalingFactorErevRoth.setText("9");
				jTextField_scalingFactorErevRoth.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_scalingFactorErevRoth.setColumns(10);
				GridBagConstraints gbc_jTextField_scalingFactorErevRoth = new GridBagConstraints();
				gbc_jTextField_scalingFactorErevRoth.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_scalingFactorErevRoth.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_scalingFactorErevRoth.gridx = 1;
				gbc_jTextField_scalingFactorErevRoth.gridy = 7;
				JPanel_NoneSliderParameters.add(jTextField_scalingFactorErevRoth,
						gbc_jTextField_scalingFactorErevRoth);
			}
			
			
			{
				JLabel jLabel_LoanMarketSentiment = new JLabel(
						"Loan Market Sentiment");
				jLabel_LoanMarketSentiment.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_LoanMarketSentiment = new GridBagConstraints();
				gbc_jLabel_LoanMarketSentiment.anchor = GridBagConstraints.WEST;
				gbc_jLabel_LoanMarketSentiment.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_LoanMarketSentiment.gridx = 2;
				gbc_jLabel_LoanMarketSentiment.gridy = 7;
				JPanel_NoneSliderParameters.add(jLabel_LoanMarketSentiment,
						gbc_jLabel_LoanMarketSentiment);
			}
			{
				jTextField_LoanMarketSentimentShare = new JTextField();
				jTextField_LoanMarketSentimentShare.setText("0.5");
				jTextField_LoanMarketSentimentShare.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_LoanMarketSentimentShare.setColumns(10);
				GridBagConstraints gbc_jTextField_LoanMarketSentiment = new GridBagConstraints();
				gbc_jTextField_LoanMarketSentiment.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_LoanMarketSentiment.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_LoanMarketSentiment.gridx = 3;
				gbc_jTextField_LoanMarketSentiment.gridy = 7;
				JPanel_NoneSliderParameters.add(jTextField_LoanMarketSentimentShare,
						gbc_jTextField_LoanMarketSentiment);
			}
			
			
			
			
			// Generiic Alpha Beta Beta Distribution
			{
				JLabel jLabel_BetaDistributionAlphaGeneric = new JLabel(
						"Generic Beta Distribution Alpha");
				jLabel_BetaDistributionAlphaGeneric
						.setToolTipText("Generic Beta Distribution Alpha");
				jLabel_BetaDistributionAlphaGeneric.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_BetaDistributionAlphaGeneric = new GridBagConstraints();
				gbc_jLabel_BetaDistributionAlphaGeneric.anchor = GridBagConstraints.WEST;
				gbc_jLabel_BetaDistributionAlphaGeneric.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_BetaDistributionAlphaGeneric.gridx = 0;
				gbc_jLabel_BetaDistributionAlphaGeneric.gridy = 8;
				JPanel_NoneSliderParameters.add(jLabel_BetaDistributionAlphaGeneric,
						gbc_jLabel_BetaDistributionAlphaGeneric);
			}
			{
				jTextField_BetaDistributionAlphaGeneric = new JTextField();
				jTextField_BetaDistributionAlphaGeneric.setText("2");
				jTextField_BetaDistributionAlphaGeneric.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_BetaDistributionAlphaGeneric.setColumns(10);
				GridBagConstraints gbc_jTextField_BetaDistributionAlphaGeneric = new GridBagConstraints();
				gbc_jTextField_BetaDistributionAlphaGeneric.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_BetaDistributionAlphaGeneric.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_BetaDistributionAlphaGeneric.gridx = 1;
				gbc_jTextField_BetaDistributionAlphaGeneric.gridy = 8;
				JPanel_NoneSliderParameters.add(
						jTextField_BetaDistributionAlphaGeneric,
						gbc_jTextField_BetaDistributionAlphaGeneric);
			}
			{
				JLabel jLabel_BetaDistributionBetaGeneric = new JLabel(
						"Generic Beta Distribution Beta");
				jLabel_BetaDistributionBetaGeneric
						.setToolTipText("Generic Beta Distribution Beta");
				jLabel_BetaDistributionBetaGeneric.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_BetaDistributionBetaGeneric = new GridBagConstraints();
				gbc_jLabel_BetaDistributionBetaGeneric.anchor = GridBagConstraints.WEST;
				gbc_jLabel_BetaDistributionBetaGeneric.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_BetaDistributionBetaGeneric.gridx = 2;
				gbc_jLabel_BetaDistributionBetaGeneric.gridy = 8;
				JPanel_NoneSliderParameters.add(jLabel_BetaDistributionBetaGeneric,
						gbc_jLabel_BetaDistributionBetaGeneric);
			}
			{
				jTextField_BetaDistributionBetaGeneric = new JTextField();
				jTextField_BetaDistributionBetaGeneric.setText("5");
				jTextField_BetaDistributionBetaGeneric.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_BetaDistributionBetaGeneric.setColumns(10);
				GridBagConstraints gbc_jTextField_BetaDistributionBetaGeneric = new GridBagConstraints();
				gbc_jTextField_BetaDistributionBetaGeneric.insets = new Insets(0, 0, 5, 0);
				gbc_jTextField_BetaDistributionBetaGeneric.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_BetaDistributionBetaGeneric.gridx = 3;
				gbc_jTextField_BetaDistributionBetaGeneric.gridy = 8;
				JPanel_NoneSliderParameters.add(
						jTextField_BetaDistributionBetaGeneric,
						gbc_jTextField_BetaDistributionBetaGeneric);
			}
			// Minimum Alpha Beta Beta Distribution
			{
				JLabel jLabel_BetaDistributionAlphaMin = new JLabel(
						"Minimum Beta Distribution Alpha");
				jLabel_BetaDistributionAlphaMin
						.setToolTipText("Minimum Beta Distribution Alpha");
				jLabel_BetaDistributionAlphaMin.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_BetaDistributionAlphaMin = new GridBagConstraints();
				gbc_jLabel_BetaDistributionAlphaMin.anchor = GridBagConstraints.WEST;
				gbc_jLabel_BetaDistributionAlphaMin.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_BetaDistributionAlphaMin.gridx = 0;
				gbc_jLabel_BetaDistributionAlphaMin.gridy = 9;
				JPanel_NoneSliderParameters.add(jLabel_BetaDistributionAlphaMin,
						gbc_jLabel_BetaDistributionAlphaMin);
			}
			{
				jTextField_BetaDistributionAlphaMin = new JTextField();
				jTextField_BetaDistributionAlphaMin.setText("1");
				jTextField_BetaDistributionAlphaMin.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_BetaDistributionAlphaMin.setColumns(10);
				GridBagConstraints gbc_jTextField_BetaDistributionAlphaMin = new GridBagConstraints();
				gbc_jTextField_BetaDistributionAlphaMin.insets = new Insets(0, 0, 5, 5);
				gbc_jTextField_BetaDistributionAlphaMin.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_BetaDistributionAlphaMin.gridx = 1;
				gbc_jTextField_BetaDistributionAlphaMin.gridy = 9;
				JPanel_NoneSliderParameters.add(
						jTextField_BetaDistributionAlphaMin,
						gbc_jTextField_BetaDistributionAlphaMin);
			}
			{
				JLabel jLabel_BetaDistributionBetaMin = new JLabel(
						"Minimum Beta Distribution Beta");
				jLabel_BetaDistributionBetaMin
						.setToolTipText("Minimum Beta Distribution Beta");
				jLabel_BetaDistributionBetaMin.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_BetaDistributionBetaMin = new GridBagConstraints();
				gbc_jLabel_BetaDistributionBetaMin.anchor = GridBagConstraints.WEST;
				gbc_jLabel_BetaDistributionBetaMin.insets = new Insets(0, 0, 5, 5);
				gbc_jLabel_BetaDistributionBetaMin.gridx = 2;
				gbc_jLabel_BetaDistributionBetaMin.gridy = 9;
				JPanel_NoneSliderParameters.add(jLabel_BetaDistributionBetaMin,
						gbc_jLabel_BetaDistributionBetaMin);
			}
			{
				jTextField_BetaDistributionBetaMin = new JTextField();
				jTextField_BetaDistributionBetaMin.setText("1");
				jTextField_BetaDistributionBetaMin.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_BetaDistributionBetaMin.setColumns(10);
				GridBagConstraints gbc_jTextField_BetaDistributionBetaMin = new GridBagConstraints();
				gbc_jTextField_BetaDistributionBetaMin.insets = new Insets(0, 0, 5, 0);
				gbc_jTextField_BetaDistributionBetaMin.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_BetaDistributionBetaMin.gridx = 3;
				gbc_jTextField_BetaDistributionBetaMin.gridy = 9;
				JPanel_NoneSliderParameters.add(
						jTextField_BetaDistributionBetaMin,
						gbc_jTextField_BetaDistributionBetaMin);
			}
			// Maximum Alpha Beta Beta Distribution
			{
				JLabel jLabel_BetaDistributionAlphaMax = new JLabel(
						"Maximum Beta Distribution Alpha");
				jLabel_BetaDistributionAlphaMax
						.setToolTipText("Maximum Beta Distribution Alpha");
				jLabel_BetaDistributionAlphaMax.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_BetaDistributionAlphaMax = new GridBagConstraints();
				gbc_jLabel_BetaDistributionAlphaMax.anchor = GridBagConstraints.WEST;
				gbc_jLabel_BetaDistributionAlphaMax.insets = new Insets(0, 0, 0, 5);
				gbc_jLabel_BetaDistributionAlphaMax.gridx = 0;
				gbc_jLabel_BetaDistributionAlphaMax.gridy = 10;
				JPanel_NoneSliderParameters.add(jLabel_BetaDistributionAlphaMax,
						gbc_jLabel_BetaDistributionAlphaMax);
			}
			{
				jTextField_BetaDistributionAlphaMax = new JTextField();
				jTextField_BetaDistributionAlphaMax.setText("5");
				jTextField_BetaDistributionAlphaMax.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_BetaDistributionAlphaMax.setColumns(10);
				GridBagConstraints gbc_jTextField_BetaDistributionAlphaMax = new GridBagConstraints();
				gbc_jTextField_BetaDistributionAlphaMax.insets = new Insets(0, 0, 0, 5);
				gbc_jTextField_BetaDistributionAlphaMax.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_BetaDistributionAlphaMax.gridx = 1;
				gbc_jTextField_BetaDistributionAlphaMax.gridy = 10;
				JPanel_NoneSliderParameters.add(
						jTextField_BetaDistributionAlphaMax,
						gbc_jTextField_BetaDistributionAlphaMax);
			}
			{
				JLabel jLabel_BetaDistributionBetaMax = new JLabel(
						"Maximum Beta Distribution Beta");
				jLabel_BetaDistributionBetaMax
						.setToolTipText("Maximum Beta Distribution Beta");
				jLabel_BetaDistributionBetaMax.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				GridBagConstraints gbc_jLabel_BetaDistributionBetaMax = new GridBagConstraints();
				gbc_jLabel_BetaDistributionBetaMax.anchor = GridBagConstraints.WEST;
				gbc_jLabel_BetaDistributionBetaMax.insets = new Insets(0, 0, 0, 5);
				gbc_jLabel_BetaDistributionBetaMax.gridx = 2;
				gbc_jLabel_BetaDistributionBetaMax.gridy = 10;
				JPanel_NoneSliderParameters.add(jLabel_BetaDistributionBetaMax,
						gbc_jLabel_BetaDistributionBetaMax);
			}
			{
				jTextField_BetaDistributionBetaMax = new JTextField();
				jTextField_BetaDistributionBetaMax.setText("7");
				jTextField_BetaDistributionBetaMax.setFont(new Font("Tahoma",
						Font.PLAIN, 9));
				jTextField_BetaDistributionBetaMax.setColumns(10);
				GridBagConstraints gbc_jTextField_BetaDistributionBetaMax = new GridBagConstraints();
				gbc_jTextField_BetaDistributionBetaMax.fill = GridBagConstraints.HORIZONTAL;
				gbc_jTextField_BetaDistributionBetaMax.gridx = 3;
				gbc_jTextField_BetaDistributionBetaMax.gridy = 10;
				JPanel_NoneSliderParameters.add(
						jTextField_BetaDistributionBetaMax,
						gbc_jTextField_BetaDistributionBetaMax);
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
					Parameters.setGlobalMDPErevRothDecisionParameters(ErevRothLearnerUpdateType, ErevRothProbabilityModel,
							periodByPeriodCalculationsErevRoth, identicalParameterSelection, numberOfIterationsErevRoth,
							numberOfDecisionEpochsErevRoth, securitisationRateIncrementErevRoth, securitisationRateMaxChangeErevRoth,
							GibbsBoltzmannParameterErevRoth, genericExperimentationFactor,
							maximumExperimentationFactor, minimumExperimentationFactor, genericRecencyFactorErevRoth, 
							maxRecencyFactorErevRoth, minRecencyFactorErevRoth, loanMarketSentimentShareErevRoth,
							betaDistributionAlphaGenericErevRoth, betaDistributionBetaGenericErevRoth, betaDistributionAlphaMinErevRoth,
							betaDistributionBetaMinErevRoth, betaDistributionAlphaMaxErevRoth, betaDistributionBetaMaxErevRoth, 
							scalingParameterErevRoth, randomSeedErevRoth);
				}
			});
			{
				chckbx_PeriodByPeriodCalculations = new JCheckBox(
						"Run Each Simulation Period");
				chckbx_PeriodByPeriodCalculations.setSelected(true);
				chckbx_PeriodByPeriodCalculations
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JCheckBox jcbType = (JCheckBox) e
								.getSource();
						boolean value = jcbType.isSelected();
						setPeriodByPeriodCalculations(value);
					}
				});
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
					chckbx_PeriodByPeriodCalculations.setSelected(true);
					/**
					 * setGlobalMDPErevRothDecisionParameters(
			String ErevRothLearnerUpdateType, String ErevRothProbabilityModel, boolean periodByPeriodCalculationsErevRoth,
			boolean identicalParameterSelection, 	int numberOfIterations2, int numberOfDecisionEpochs2,
			double securitisationRateIncrementErevRoth, double securitisationRateMaxChangeErevRoth, 
			double GibbsBoltzmannParameterErevRoth, double genericExperimentationFactor, 
			double maximumExperimentationFactor, double minimumExperimentationFactor, 
			double genericRecencyFactorErevRoth, 
			double maxRecencyFactorErevRoth, double minRecencyFactorErevRoth, 
			double loanMarketSentimentShare,
			double betaDistributionAlphaGeneric, double betaDistributionBetaGeneric, double betaDistributionAlphaMin,
			double betaDistributionBetaMin, double betaDistributionAlphaMax, double betaDistributionBetaMax,
			double scalingParameterErevRoth)
					 */
					Parameters.setGlobalMDPErevRothDecisionParameters(ErevRothLearnerUpdateType, ErevRothProbabilityModel,
							periodByPeriodCalculationsErevRoth, identicalParameterSelection, numberOfIterationsErevRoth,
							numberOfDecisionEpochsErevRoth, securitisationRateIncrementErevRoth, securitisationRateMaxChangeErevRoth,
							GibbsBoltzmannParameterErevRoth, genericExperimentationFactor,
							maximumExperimentationFactor, minimumExperimentationFactor, genericRecencyFactorErevRoth, 
							maxRecencyFactorErevRoth, minRecencyFactorErevRoth, loanMarketSentimentShareErevRoth,
							betaDistributionAlphaGenericErevRoth, betaDistributionBetaGenericErevRoth, betaDistributionAlphaMinErevRoth,
							betaDistributionBetaMinErevRoth, betaDistributionAlphaMaxErevRoth, betaDistributionBetaMaxErevRoth, 
							scalingParameterErevRoth, randomSeedErevRoth);
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

protected void setPeriodByPeriodCalculations(boolean value) {
	// TODO Auto-generated method stub
	MDPErevRothSettingsWindow.periodByPeriodCalculationsErevRoth = value;
}

public MDPErevRothSettingsWindow() {
	initialize();

}

// Applies values in text fields to the MDP model factors
private void applyMDPParameters() {

	MDPErevRothSettingsWindow.numberOfIterationsErevRoth = Integer
			.parseInt(jTextField_numberOfIterations.getText());
	MDPErevRothSettingsWindow.numberOfDecisionEpochsErevRoth = Integer
			.parseInt(jTextField_NumberOfEpochs.getText());
	MDPErevRothSettingsWindow.maxRecencyFactorErevRoth = Double
			.parseDouble(jTextField_maxRecencyFactorErevRoth.getText());
	MDPErevRothSettingsWindow.minRecencyFactorErevRoth = Double
			.parseDouble(jTextField_minRecencyFactorErevRoth.getText());
	MDPErevRothSettingsWindow.genericRecencyFactorErevRoth = Double
			.parseDouble(jTextField_genericRecencyFactorErevRoth.getText());
	
	MDPErevRothSettingsWindow.maximumExperimentationFactor = Double
			.parseDouble(jTextField_maximumExperimentationFactor.getText());
	MDPErevRothSettingsWindow.minimumExperimentationFactor = Double
			.parseDouble(jTextField_minimumExperimentationFactor.getText());
	MDPErevRothSettingsWindow.genericExperimentationFactor = Double
			.parseDouble(jTextField_genericExperimentationFactor.getText());
	
	MDPErevRothSettingsWindow.loanMarketSentimentShareErevRoth = Double
			.parseDouble(jTextField_LoanMarketSentimentShare.getText());
	
	MDPErevRothSettingsWindow.betaDistributionAlphaGenericErevRoth = Double
			.parseDouble(jTextField_BetaDistributionAlphaGeneric.getText());
	MDPErevRothSettingsWindow.betaDistributionBetaGenericErevRoth = Double
			.parseDouble(jTextField_BetaDistributionBetaGeneric.getText());
	MDPErevRothSettingsWindow.betaDistributionAlphaMinErevRoth = Double
			.parseDouble(jTextField_BetaDistributionAlphaMin.getText());
	MDPErevRothSettingsWindow.betaDistributionBetaMaxErevRoth = Double
			.parseDouble(jTextField_BetaDistributionBetaMax.getText());
	MDPErevRothSettingsWindow.betaDistributionAlphaMaxErevRoth = Double
			.parseDouble(jTextField_BetaDistributionAlphaMax.getText());
	MDPErevRothSettingsWindow.betaDistributionBetaMinErevRoth = Double
			.parseDouble(jTextField_BetaDistributionBetaMin.getText());
	
	MDPErevRothSettingsWindow.scalingParameterErevRoth = Double
			.parseDouble(jTextField_scalingFactorErevRoth.getText());
	MDPErevRothSettingsWindow.randomSeedErevRoth = Integer
			.parseInt(jTextField_randomSeedErevRoth.getText());
	MDPErevRothSettingsWindow.GibbsBoltzmannParameterErevRoth = Double
			.parseDouble(jTextField_GibbsBoltzmannParameterErevRoth.getText());
	

}

/**
 * resets the MDP model default values
 */
private void resetMDPParameters() {
	// MDP Model Variables these variables have to be static because they
	// are treated as global Parameters
	MDPErevRothSettingsWindow.ErevRothLearnerUpdateType = "Standard";//can be "Standard" or "Nicolaosen Variation";
	MDPErevRothSettingsWindow.ErevRothProbabilityModel = "Standard";//can be "Standard" or "Gibbs-Boltzmann";
	MDPErevRothSettingsWindow.periodByPeriodCalculationsErevRoth = true;
	MDPErevRothSettingsWindow.identicalParameterSelection = true;
	MDPErevRothSettingsWindow.numberOfIterationsErevRoth = 1000;
	MDPErevRothSettingsWindow.numberOfDecisionEpochsErevRoth = 5;
	MDPErevRothSettingsWindow.securitisationRateIncrementErevRoth = 0.05;//sliders
	MDPErevRothSettingsWindow.securitisationRateMaxChangeErevRoth = 0.1;//sliders
	MDPErevRothSettingsWindow.numberOfActionsErevRoth = (int) (1+ (2*(Parameters.securitisationRateMaxChangeErevRoth
			/Parameters.securitisationRateIncrementErevRoth)));
	MDPErevRothSettingsWindow.GibbsBoltzmannParameterErevRoth = 1000;
	MDPErevRothSettingsWindow.scalingParameterErevRoth = 9;
	MDPErevRothSettingsWindow.averageStartingRewardErevRoth = 100;
	MDPErevRothSettingsWindow.genericExperimentationFactor = 0.75;
	MDPErevRothSettingsWindow.maximumExperimentationFactor = 0.9;
	MDPErevRothSettingsWindow.minimumExperimentationFactor = 0.1;
	MDPErevRothSettingsWindow.genericRecencyFactorErevRoth = 0.1;
	MDPErevRothSettingsWindow.minRecencyFactorErevRoth = 0.1;
	MDPErevRothSettingsWindow.maxRecencyFactorErevRoth = 0.75;
	MDPErevRothSettingsWindow.loanMarketSentimentShareErevRoth = 0.5;
	MDPErevRothSettingsWindow.betaDistributionAlphaGenericErevRoth = 2;
	MDPErevRothSettingsWindow.betaDistributionBetaGenericErevRoth = 5;
	MDPErevRothSettingsWindow.betaDistributionAlphaMinErevRoth = 1;
	MDPErevRothSettingsWindow.betaDistributionBetaMaxErevRoth = 7;
	MDPErevRothSettingsWindow.betaDistributionAlphaMaxErevRoth = 7;
	MDPErevRothSettingsWindow.betaDistributionBetaMinErevRoth = 1;
	MDPErevRothSettingsWindow.randomSeedErevRoth = 74974984;
		}


/**
 * Determine if the creation of transition probabilities are to be agent
 * calculated i.e. endogenous or use predefined data i.e. exogenous
 * 
 * @param value
 */
private void setIdenticalStateSelectionBias(int value) {
	// TODO Auto-generated method stub
	if (value == 0) {
		identicalParameterSelection = false;
	} else {
		identicalParameterSelection = true;
	}
}



/**
 * set the update model type
 * 
 * @param value
 */
private void setErevRothLearnerUpdateType(String value) {
	// TODO Auto-generated method stub
	ErevRothLearnerUpdateType = value;
	
}


/**
 * set the probability update model type
 * 
 * @param value
 */
private void setErevRothProbabilityModel(String value) {
	// TODO Auto-generated method stub
		ErevRothProbabilityModel = value;
	
}



private void setSecuritisationRateIncrement(double value) {

	securitisationRateIncrementErevRoth = value  / 100;
}

private void setSecuritisationRateMaxChange(double value) {

	securitisationRateMaxChangeErevRoth = value / 100;
}

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CORE UTILITY CLASSES: SLIDER
// CHANGE LISTENERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

/**
 * EpsilonError jSlider change listener
 * 
 * @author Oluwasegun Bewaji
 * 
 */
class SecuritisationRateIncrementChangeListener implements ChangeListener {
	SecuritisationRateIncrementChangeListener() {
	}

	@Override
	public synchronized void stateChanged(ChangeEvent e) {
		double value = jSlider_SecuritisationRateIncrement.getValue();
		setSecuritisationRateIncrement(value);
	}
}

/**
 * GammaDistountFactor jSlider change listener
 * 
 * @author Oluwasegun Bewaji
 * 
 */
class SecuritisationRateMaxChangeChangeListener implements ChangeListener {
	SecuritisationRateMaxChangeChangeListener() {
	}

	@Override
	public synchronized void stateChanged(ChangeEvent e) {
		double value = jSlider_SecuritisationRateMaxChange.getValue();
		setSecuritisationRateMaxChange(value);
	}
}

}
