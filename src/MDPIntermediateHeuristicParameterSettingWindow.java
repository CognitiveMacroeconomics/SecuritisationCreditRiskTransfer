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

/**
 * This class is used as a pop-out window to enable the user to set the MDP intermediate heuristics
 * Model Parameters
 * 
 * 
 * @author Oluwasegun Bewaji
 * 
 */
@SuppressWarnings("serial")
public class MDPIntermediateHeuristicParameterSettingWindow extends JDialog {

	// MDP Intermediate Heuristic Model Variables these variables have to be static because they are
		// treated as global Parameters
		private static double loanMarketSentimentShare = 0.5;
		// value and policy iteration parameters
		private static int numberOfIterations = 1000;
		private static int numberOfDecisionEpochs = 5;
		private static double securitisationRateIncrement = 0.01;//
		private static double greedParameterIncrement = 0.01;// increasing gamma
														// increases the time/number
														// of iterations required to
														// find a value
		private static double maximumBias = 0.5;
		private static double minimumBias = 0.2;
		private static double genericBias = 0.5;
		private static boolean periodByPeriodCalculations = true;
		private static boolean IdenticalStateSelectionBias = true;
		
		
		private static double betaDistributionAlphaGeneric = 2;
		private static double betaDistributionBetaGeneric = 5;
		private static double betaDistributionAlphaMin = 1;
		private static double betaDistributionBetaMax = 7;
		private static double betaDistributionAlphaMax = 5;
		private static double betaDistributionBetaMin = 1;

		private final JPanel contentPanel = new JPanel();
		private JTextField jTextField_numberOfIterations;
		private JTextField jTextField_MaximumBias;

		private JPanel buttonPane;
		private JButton applyButton;
		private JButton resetButton;
		private JButton closeButton;
		private JComboBox jComboBox_IdenticalStateSelectionBias;
		private JCheckBox chckbx_PeriodByPeriodCalculations;

		private JSlider jSlider_SecuritisationRateIncrement;
		private JSlider jSlider_GreedParameterIncrement;
		private JTextField jTextField_MinimumBias;
		private JTextField jTextField_LoanMarketSentimentShare;
		private JTextField jTextField_GenericBias;
		private JTextField jTextField_NumberOfEpochs;
		
		private JTextField  jTextField_BetaDistributionAlphaGeneric;
		private JTextField jTextField_BetaDistributionBetaGeneric;
		private JTextField jTextField_BetaDistributionAlphaMin;
		private JTextField jTextField_BetaDistributionBetaMax;
		private JTextField jTextField_BetaDistributionAlphaMax;
		private JTextField jTextField_BetaDistributionBetaMin;
		
		JLabel jLabel_SecuritisationRateIncrement;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MDPIntermediateHeuristicParameterSettingWindow dialog = new MDPIntermediateHeuristicParameterSettingWindow();
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
								"Securitisation Rate Increase (%)");
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
						jSlider_SecuritisationRateIncrement.setMaximum(10);
						jSlider_SecuritisationRateIncrement.setPaintTicks(true);
						jSlider_SecuritisationRateIncrement.setPaintLabels(true);
						jSlider_SecuritisationRateIncrement.setValue(1);
						double value = jSlider_SecuritisationRateIncrement.getValue();
						setSecuritisationRateIncrement(value);
						jSlider_SecuritisationRateIncrement
								.setToolTipText("Securitisation Rate Increment");
						jSlider_SecuritisationRateIncrement.setMinorTickSpacing(5);
						jSlider_SecuritisationRateIncrement.setMajorTickSpacing(1);
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
								"Greed Parameter Increase (%)");
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
						jSlider_GreedParameterIncrement = new JSlider();
						jSlider_GreedParameterIncrement.setMaximum(10);
						jSlider_GreedParameterIncrement.setPaintLabels(true);
						jSlider_GreedParameterIncrement.setPaintTicks(true);
						jSlider_GreedParameterIncrement.setValue(5);
						double valueG = jSlider_GreedParameterIncrement.getValue();
						setGreedParameterIncrement(valueG);
						jSlider_GreedParameterIncrement
								.setToolTipText("Greed Parameter Increment");
						jSlider_GreedParameterIncrement.setFont(new Font("Tahoma",
								Font.PLAIN, 9));
						jSlider_GreedParameterIncrement.setBorder(null);
						jSlider_GreedParameterIncrement.setMajorTickSpacing(1);
						jSlider_GreedParameterIncrement.setMinorTickSpacing(5);
						GreedParameterIncrementChangeListener gdflstnr = new GreedParameterIncrementChangeListener();// internal
																												// class
						jSlider_GreedParameterIncrement.addChangeListener(gdflstnr);
						GridBagConstraints gbc_jSlider_GreedParameterIncrement = new GridBagConstraints();
						gbc_jSlider_GreedParameterIncrement.anchor = GridBagConstraints.WEST;
						gbc_jSlider_GreedParameterIncrement.fill = GridBagConstraints.BOTH;
						gbc_jSlider_GreedParameterIncrement.gridx = 1;
						gbc_jSlider_GreedParameterIncrement.gridy = 0;
						jPanel_GreedParameterIncrement.add(jSlider_GreedParameterIncrement,
								gbc_jSlider_GreedParameterIncrement);
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
					JLabel jLabel_IdenticalStateSelectionBias = new JLabel(
							"Identical State Selection Bias");
					jLabel_IdenticalStateSelectionBias.setFont(new Font(
							"Tahoma", Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_IdenticalStateSelectionBias = new GridBagConstraints();
					gbc_jLabel_IdenticalStateSelectionBias.anchor = GridBagConstraints.WEST;
					gbc_jLabel_IdenticalStateSelectionBias.insets = new Insets(
							0, 0, 5, 5);
					gbc_jLabel_IdenticalStateSelectionBias.gridx = 0;
					gbc_jLabel_IdenticalStateSelectionBias.gridy = 0;
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
					gbc_jComboBox_StochasticStateTransitions.gridy = 0;
					JPanel_NoneSliderParameters.add(
							jComboBox_IdenticalStateSelectionBias,
							gbc_jComboBox_StochasticStateTransitions);
				}
				{
					JLabel jLabel_MinimumBias = new JLabel("Minimum Bias");
					jLabel_MinimumBias.setToolTipText("Minimum Bias");
					jLabel_MinimumBias.setFont(new Font("Tahoma", Font.PLAIN,
							9));
					GridBagConstraints gbc_jLabel_MinimumBias = new GridBagConstraints();
					gbc_jLabel_MinimumBias.anchor = GridBagConstraints.WEST;
					gbc_jLabel_MinimumBias.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_MinimumBias.gridx = 0;
					gbc_jLabel_MinimumBias.gridy = 1;
					JPanel_NoneSliderParameters.add(jLabel_MinimumBias,
							gbc_jLabel_MinimumBias);
				}
				{
					jTextField_MinimumBias = new JTextField();
					jTextField_MinimumBias.setToolTipText("Minimum Bias 0<min<1");
					jTextField_MinimumBias.setText("0.2");
					jTextField_MinimumBias.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_MinimumBias.setColumns(10);
					GridBagConstraints gbc_jTextField_MinimumBias = new GridBagConstraints();
					gbc_jTextField_MinimumBias.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_MinimumBias.insets = new Insets(0, 0, 5, 5);
					gbc_jTextField_MinimumBias.gridx = 1;
					gbc_jTextField_MinimumBias.gridy = 1;
					JPanel_NoneSliderParameters.add(jTextField_MinimumBias,
							gbc_jTextField_MinimumBias);
				}
				{
					JLabel jLabel_MaximumBias = new JLabel(
							"Maximum Bias");
					jLabel_MaximumBias.setToolTipText("Maximum Bias");
					jLabel_MaximumBias.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_MaximumBias = new GridBagConstraints();
					gbc_jLabel_MaximumBias.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_MaximumBias.anchor = GridBagConstraints.WEST;
					gbc_jLabel_MaximumBias.gridx = 2;
					gbc_jLabel_MaximumBias.gridy = 1;
					JPanel_NoneSliderParameters.add(jLabel_MaximumBias,
							gbc_jLabel_MaximumBias);
				}
				{
					jTextField_MaximumBias = new JTextField();
					jTextField_MaximumBias.setToolTipText("Maximum Bias min <= max <1");
					jTextField_MaximumBias.setText("0.5");
					jTextField_MaximumBias.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_MaximumBias.setColumns(10);
					GridBagConstraints gbc_jTextField_MaximumBias = new GridBagConstraints();
					gbc_jTextField_MaximumBias.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_MaximumBias.insets = new Insets(0, 0,
							5, 0);
					gbc_jTextField_MaximumBias.gridx = 3;
					gbc_jTextField_MaximumBias.gridy = 1;
					JPanel_NoneSliderParameters.add(
							jTextField_MaximumBias,
							gbc_jTextField_MaximumBias);
				}
				{
					JLabel jLabel_GenericBias = new JLabel("Generic Bias");
					jLabel_GenericBias.setToolTipText("GenericBias");
					jLabel_GenericBias.setFont(new Font("Tahoma", Font.PLAIN, 9));
					GridBagConstraints gbc_jLabel_GenericBias = new GridBagConstraints();
					gbc_jLabel_GenericBias.anchor = GridBagConstraints.WEST;
					gbc_jLabel_GenericBias.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_GenericBias.gridx = 0;
					gbc_jLabel_GenericBias.gridy = 2;
					JPanel_NoneSliderParameters.add(jLabel_GenericBias,
							gbc_jLabel_GenericBias);
				}
				{
					jTextField_GenericBias = new JTextField();
					jTextField_GenericBias.setToolTipText("Generic Bias towards optimisim");
					jTextField_GenericBias.setText("0.5");
					jTextField_GenericBias
							.setFont(new Font("Tahoma", Font.PLAIN, 9));
					jTextField_GenericBias.setColumns(10);
					GridBagConstraints gbc_jTextField_GenericBias = new GridBagConstraints();
					gbc_jTextField_GenericBias.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_GenericBias.insets = new Insets(0, 0, 5, 5);
					gbc_jTextField_GenericBias.gridx = 1;
					gbc_jTextField_GenericBias.gridy = 2;
					JPanel_NoneSliderParameters.add(jTextField_GenericBias,
							gbc_jTextField_GenericBias);
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
					gbc_jLabel_LoanMarketSentiment.gridy = 2;
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
					gbc_jTextField_LoanMarketSentiment.insets = new Insets(0, 0, 5, 0);
					gbc_jTextField_LoanMarketSentiment.gridx = 3;
					gbc_jTextField_LoanMarketSentiment.gridy = 2;
					JPanel_NoneSliderParameters.add(jTextField_LoanMarketSentimentShare,
							gbc_jTextField_LoanMarketSentiment);
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
					gbc_jLabel_NumberOfEpochs.gridy = 3;
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
					gbc_jTextField_NumberOfEpochs.gridy = 3;
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
					gbc_jLabel_numberOfIterations.gridy = 3;
					JPanel_NoneSliderParameters.add(jLabel_numberOfIterations,
							gbc_jLabel_numberOfIterations);
				}
				{
					jTextField_numberOfIterations = new JTextField();
					jTextField_numberOfIterations.setText("12");
					jTextField_numberOfIterations.setFont(new Font("Tahoma",
							Font.PLAIN, 9));
					jTextField_numberOfIterations.setColumns(10);
					GridBagConstraints gbc_jTextField_numberOfIterations = new GridBagConstraints();
					gbc_jTextField_numberOfIterations.insets = new Insets(0, 0, 5, 0);
					gbc_jTextField_numberOfIterations.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_numberOfIterations.gridx = 3;
					gbc_jTextField_numberOfIterations.gridy = 3;
					JPanel_NoneSliderParameters.add(
							jTextField_numberOfIterations,
							gbc_jTextField_numberOfIterations);
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
					gbc_jLabel_BetaDistributionAlphaGeneric.gridy = 4;
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
					gbc_jTextField_BetaDistributionAlphaGeneric.insets = new Insets(0, 0, 5, 0);
					gbc_jTextField_BetaDistributionAlphaGeneric.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_BetaDistributionAlphaGeneric.gridx = 1;
					gbc_jTextField_BetaDistributionAlphaGeneric.gridy = 4;
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
					gbc_jLabel_BetaDistributionBetaGeneric.gridy = 4;
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
					gbc_jTextField_BetaDistributionBetaGeneric.gridy = 4;
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
					gbc_jLabel_BetaDistributionAlphaMin.gridy = 5;
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
					gbc_jTextField_BetaDistributionAlphaMin.insets = new Insets(0, 0, 5, 0);
					gbc_jTextField_BetaDistributionAlphaMin.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_BetaDistributionAlphaMin.gridx = 1;
					gbc_jTextField_BetaDistributionAlphaMin.gridy = 5;
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
					gbc_jLabel_BetaDistributionBetaMin.gridy = 5;
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
					gbc_jTextField_BetaDistributionBetaMin.gridy = 5;
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
					gbc_jLabel_BetaDistributionAlphaMax.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_BetaDistributionAlphaMax.gridx = 0;
					gbc_jLabel_BetaDistributionAlphaMax.gridy = 6;
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
					gbc_jTextField_BetaDistributionAlphaMax.insets = new Insets(0, 0, 5, 0);
					gbc_jTextField_BetaDistributionAlphaMax.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_BetaDistributionAlphaMax.gridx = 1;
					gbc_jTextField_BetaDistributionAlphaMax.gridy = 6;
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
					gbc_jLabel_BetaDistributionBetaMax.insets = new Insets(0, 0, 5, 5);
					gbc_jLabel_BetaDistributionBetaMax.gridx = 2;
					gbc_jLabel_BetaDistributionBetaMax.gridy = 6;
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
					gbc_jTextField_BetaDistributionBetaMax.insets = new Insets(0, 0, 5, 0);
					gbc_jTextField_BetaDistributionBetaMax.fill = GridBagConstraints.HORIZONTAL;
					gbc_jTextField_BetaDistributionBetaMax.gridx = 3;
					gbc_jTextField_BetaDistributionBetaMax.gridy = 6;
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
						Parameters.setGlobalMDPHeuristicDecisionParameters(periodByPeriodCalculations,
								numberOfIterations, numberOfDecisionEpochs,
								IdenticalStateSelectionBias, securitisationRateIncrement,
								greedParameterIncrement, genericBias,
								maximumBias, minimumBias, loanMarketSentimentShare,
								betaDistributionAlphaGeneric, betaDistributionBetaGeneric, betaDistributionAlphaMin,
								betaDistributionBetaMin, betaDistributionAlphaMax, betaDistributionBetaMax);
					}
				});
				{
					chckbx_PeriodByPeriodCalculations = new JCheckBox(
							"Run Heuristic Each Simulation Period");
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
						Parameters.setGlobalMDPHeuristicDecisionParameters(periodByPeriodCalculations,
								numberOfIterations, numberOfDecisionEpochs,
								IdenticalStateSelectionBias, securitisationRateIncrement,
								greedParameterIncrement, genericBias,
								maximumBias, minimumBias, loanMarketSentimentShare,
								betaDistributionAlphaGeneric, betaDistributionBetaGeneric, betaDistributionAlphaMin,
								betaDistributionBetaMin, betaDistributionAlphaMax, betaDistributionBetaMax);
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
		MDPIntermediateHeuristicParameterSettingWindow.periodByPeriodCalculations = value;
	}

	public MDPIntermediateHeuristicParameterSettingWindow() {
		initialize();

	}

	// Applies values in text fields to the MDP model factors
	private void applyMDPParameters() {

		MDPIntermediateHeuristicParameterSettingWindow.numberOfIterations = Integer
				.parseInt(jTextField_numberOfIterations.getText());
		MDPIntermediateHeuristicParameterSettingWindow.numberOfDecisionEpochs = Integer
				.parseInt(jTextField_NumberOfEpochs.getText());
		MDPIntermediateHeuristicParameterSettingWindow.maximumBias = Double
				.parseDouble(jTextField_MaximumBias.getText());
		MDPIntermediateHeuristicParameterSettingWindow.minimumBias = Double
				.parseDouble(jTextField_MinimumBias.getText());
		MDPIntermediateHeuristicParameterSettingWindow.genericBias = Double
				.parseDouble(jTextField_GenericBias.getText());
		MDPIntermediateHeuristicParameterSettingWindow.loanMarketSentimentShare = Double
				.parseDouble(jTextField_LoanMarketSentimentShare.getText());
		
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionAlphaGeneric = Double
				.parseDouble(jTextField_BetaDistributionAlphaGeneric.getText());
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionBetaGeneric = Double
				.parseDouble(jTextField_BetaDistributionBetaGeneric.getText());
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionAlphaMin = Double
				.parseDouble(jTextField_BetaDistributionAlphaMin.getText());
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionBetaMax = Double
				.parseDouble(jTextField_BetaDistributionBetaMax.getText());
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionAlphaMax = Double
				.parseDouble(jTextField_BetaDistributionAlphaMax.getText());
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionBetaMin = Double
				.parseDouble(jTextField_BetaDistributionBetaMin.getText());
		

	}

	/**
	 * resets the MDP model default values
	 */
	private void resetMDPParameters() {
		// MDP Model Variables these variables have to be static because they
		// are treated as global Parameters
		MDPIntermediateHeuristicParameterSettingWindow.periodByPeriodCalculations = true;
		MDPIntermediateHeuristicParameterSettingWindow.numberOfIterations = 1000;
		MDPIntermediateHeuristicParameterSettingWindow.numberOfDecisionEpochs = 5;
		MDPIntermediateHeuristicParameterSettingWindow.securitisationRateIncrement = 0.1;//
		MDPIntermediateHeuristicParameterSettingWindow.greedParameterIncrement = 0.1;
		MDPIntermediateHeuristicParameterSettingWindow.genericBias = 0.5;
		MDPIntermediateHeuristicParameterSettingWindow.maximumBias = 0.5;
		MDPIntermediateHeuristicParameterSettingWindow.IdenticalStateSelectionBias = true;
		MDPIntermediateHeuristicParameterSettingWindow.minimumBias = 0.2;
		MDPIntermediateHeuristicParameterSettingWindow.loanMarketSentimentShare = 0.5;
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionAlphaGeneric = 2;
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionBetaGeneric = 5;
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionAlphaMin = 1;
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionBetaMax = 7;
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionAlphaMax = 5;
		MDPIntermediateHeuristicParameterSettingWindow.betaDistributionBetaMin = 1;
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
			IdenticalStateSelectionBias = false;
		} else {
			IdenticalStateSelectionBias = true;
		}
	}


	private void setSecuritisationRateIncrement(double value) {

		securitisationRateIncrement = value  / 100;
	}

	private void setGreedParameterIncrement(double value) {

		greedParameterIncrement = value / 100;
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
	class GreedParameterIncrementChangeListener implements ChangeListener {
		GreedParameterIncrementChangeListener() {
		}

		@Override
		public synchronized void stateChanged(ChangeEvent e) {
			double value = jSlider_GreedParameterIncrement.getValue();
			setGreedParameterIncrement(value);
		}
	}

	
	
	

}
