import jas.engine.Sim;
import jas.engine.SimEngine;
import jas.plot.ColorMap;
import jas.plot.ColorRangeMap;
import jas.plot.LayerObjGridDrawer;
import jas.plot.LayeredSurfaceFrame;
import jas.plot.LayeredSurfacePanel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.Color;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.SwingConstants;

public class CRTApplicationFrame extends JFrame {

	MDPModelParameterSettingWindow MDPParameterDialog;
	MDPIntermediateHeuristicParameterSettingWindow MDPIntermediateHeuristicParameterDialog;
	MDPErevRothSettingsWindow MDPErevRothdialog;

	// Display Data Update Variables
	Date date;
	Calendar calendar = Calendar.getInstance();
	Date now;
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
	

	double aggregateBankAssetsUpdate = 0;
	double aggregateBankLiabilitiesUpdate = 0;
	double aggregateBankEquityUpdate = 0;// bank equity
	double aggregateBankCapitalAccumulationUpdate = 0;// capital accumulation
	double averageSecuritisationRateUpdate = 0;
	double securitisationDataArrayUpdate[];
	double bullDeltaArrayUpdate[];
	double bearDeltaArrayUpdate[];
	double passiveDeltaArrayUpdate[];
	double rationalDeltaArrayUpdate[];
	double fbSecuritisationRateDataArrayUpdate[];
	double fbCapitalAccumulationDataArrayUpdate[];
	double fbSnrSecuritisationRateDataArrayUpdate[];
	double fbSnrCapitalAccumulationDataArrayUpdate[];
	double fbMezSecuritisationRateDataArrayUpdate[];
	double fbMezCapitalAccumulationDataArrayUpdate[];
	double fbJnrSecuritisationRateDataArrayUpdate[];
	double fbJnrCapitalAccumulationDataArrayUpdate[];
	double aggregateFundAssetsUpdate = 0;
	double aggregateFundLiabilitiesUpdate = 0;// fund assets
	double aggregateFundSurplusUpdate = 0;// fund surplus
	double mbsAllocationDataArrayUpdate[];
	double aggregateFundMBSAllocationUpdate = 0;// fund asset allocation
	double equityAllocationDataArrayUpdate[];
	double aggregateFundEquityAllocationUpdate = 0;
	double RiskFreeBondAllocationDataArrayUpdate[];
	double aggregateFundRiskFreeBondAllocationUpdate = 0;
	double fundReturnsDataArrayUpdate[];
	double aggregateFundPortfolioReturnsUpdate = 0;// Fund portfolio returns
	int bullDeltaTickArrayUpdate[];
	int bearDeltaTickArrayUpdate[];
	int passiveDeltaTickArrayUpdate[];
	int rationalDeltaTickArrayUpdate[];
	double mbsReturnDataArrayUpdate[];
	double equityReturnDataArrayUpdate[];
	double marketClearingEquityReturnUpdate = 0;
	double marketClearingMBSReturnUpdate = 0;// equity returns vs MBS
	// end up display data update variables

	// returns from Dealer
	// class

	int survivingBanks = 0;
	int failedBanks = 0;

	private int reloads = 0;
	private int count = 0;
	private JPanel contentPane;
	private JTextField textField_RegulatoryCapitalRatio;
	private JTextField textField_DefaultRateOnBankAssets;
	private JTextField textField_ReturnOnBankAssets;
	private JTextField textField_ReturnOnBankLiabilities;
	private JComboBox jComboBox_NumberOfFunds;
	private JTextField textField_PeriodicLiabilityPayoutRate;
	private JTextField textField_EquityMarketReturn;
	private JTextField textField_EquityMarketDefaultRate;
	private JTextField textField_EquityMarketRecoveryRate;
	private JTextField textField_CreditMarketRecoveryRate;
	private JTextField textField_CashDepositRate;
	private JTextField textField_GenericSecuritisationRate;
	private JTextField textField_SecuritisationCostMultiplier;
	private JTextField textField_UserDefinedBankAssets;
	private JTextField textField_UserDefinedBankLiabilities;
	private JTextField textField_UserDefinedFundAssets;
	private JTextField textField_UserDefinedFundLiabilities;

	private JPanel panel_World;
	private JPanel panel_WorldChartContainer;
	private LayeredSurfaceFrame lsf_WorldChartContainer;
	private LayeredSurfacePanel lsp_WorldChartContainerPanel;
	public JFrame displayFrame;
	JPanel panel_BanksOutput;
	JPanel panel_MDPAgents;

	ChartPanel panel_AssetAccumulation;
	ChartPanel panel_CapitalAccumulation;
	ChartPanel panel_SecuritisationRate;
	ChartPanel panel_Fragility;

	ChartPanel panel_FundAssetMix;
	ChartPanel panel_FundSurplus;
	ChartPanel panel_FundAssets;
	ChartPanel panel_MarketClearingMBSReturn;

	ChartPanel panel_FocusBankSecRate;
	ChartPanel panel_FocusBankTranchesSecRate;

	ChartPanel chartPanel_BullDelta;
	ChartPanel chartPanel_RationalDelta;
	ChartPanel chartPanel_BearDelta;
	ChartPanel chartPanel_PassiveDelta;

	private String modelPeriodSting;
	private String rateTypeString;// "Fixed";
	private String borrowerTypeString;// "individual_generic";
	private String paymentScheduleString;
	private String traditionalAssetVariationTypeString;
	private String internalSecuritiseString;// "false";
	private String userAssetLiabilityDataInpuString;
	private String securitiseString;
	private String securitiseLinearString;
	private String bnksOnlyAnalysisString;
	private String securitisationRate;
	private String defaultProbabilityBankExpectations;
	private String probabilityOfEquityReturnExpectations;
	private String securitisationCostConstantFactor;
	private String numberOfConstituentsPerMBSIssuanse;
	private String primeMortgageLTV;
	private String subprimeMortgageLTV;
	private String userDefinedBankAssets;
	private String userDefinedBankLiabilities;
	private String bankAssetReturn;
	private String bankLiabilityExpense;
	private String returnOnGlobalEquity;
	private String bankRegulatoryCapitalRatio;
	private String bankCount;
	private String investorCount;
	private String recoveryRateOnTraditionalAsset;
	private String recoveryRateOnCreditAsset;
	private String assetValueMin;
	private String assetValueMax;
	private String aveIncomeMin;
	private String aveIncomeMax;
	private String resetWindow;
	private String currentRiskFreeRate;
	private String mortgageMaturityMax;
	private String mortgageMaturityMin;
	private String bankNamesList[];
	private String lapfTypeString;
	private String fundsExpectationsString;
	private String fundExpectationsBiasString;
	
	private String bankTypeString;
	private int bankType;
	/**
	 * this value at the bank loan issuance level defines the probability bias
	 * for setting the loan to value ratio and average income of the borrower
	 * 
	 */
	private String probabilityOfSubPrime;
	/**
	 * this value at the bank loan issuance level defines the probability bias
	 * for determining whether the loan is issued to a borrower who is severely
	 * burdened by housing cost this in turn will determine the probability that
	 * the borrower will default if the value of the house/asset underlying the
	 * mortgage falls below the market value of the mortgage
	 * 
	 */
	private String probabilityOfHouseCostSevereBurden;
	/**
	 * this value at the bank loan issuance level defines the probability bias
	 * for setting the loan maturity
	 * 
	 */
	private String mortgageMaturityMinProbability;
	// Life, Insurance and Pension Fund required Variables
	private String userDefinedLAPFAssets;
	// System.out.println(this.userDefinedLAPFAssets);
	private String userDefinedLAPFLiabilities;
	// System.out.println(this.userDefinedLAPFLiabilities);
	private String lapfQuadraticCostFunctionString;
	// System.out.println("lapfQuadraticCostFunction: " +
	// lapfQuadraticCostFunction);
	private String lapfConstantContractualObligationsString;
	// System.out.println("lapfConstantContractualObligations: " +
	// lapfConstantContractualObligations);
	private String lapfMultiPeriodSolvancyModelString;// "false";
	private String shortSellingString;// "false";
	// System.out.println("short sell: " + shortSelling);
	private String interestSpreadExperimentString;
	private String opportunityCostOfFixedIncomeInvestment;
	private String lapfRegulatorySolvancyRatio;
	private String lapfPeriodicLiabilityPaymentRate;
	private String lapfPremiumContributionsRate;
	private String returnOnGlobalCredit = "0.16";
	// this.returnOnGlobalCredit = (Double)
	// params.getValue("returnOnGlobalCredit");
	/**
	 * this is defined at fund valuation period by actuaries on an annual or
	 * quarterly basis It is nevertheless predifined
	 */
	private String annualExpectedPayout;
	private String annualExpectPayoutRebalancingRate;
	private String AA_Rated_MBS_Coupon;
	private String nonAA_Rated_MBS_Coupon;
	private String AA_Rated_MBS_Probability;

	private SimEngine eng;
	private MarkoseDYangModel_V01 model;
	private SecuritisationModelObserverJAS modelObserver;
	private Parameters param = new Parameters();
	private String focusBankString;

	public TimeSeries aggregateBankAssetsvsLiabilities;
	TimeSeriesCollection dataSetBankAssetLiabilities;
	public TimeSeries aggregateBankEquity;// bank equity
	public TimeSeries aggregateBankCapitalAccumulation;// capital accumulation
	public TimeSeries averageBankSecuritisationRate;
	public DefaultCategoryDataset aggregateBankFragility;// surviving banks vs
	// failed banks
	public DefaultCategoryDataset aggregateSurvivingBanks;
	public DefaultCategoryDataset aggregateFailedBanks;
	public DefaultCategoryDataset bullInvestorDelta;
	public DefaultCategoryDataset bearInvestorDelta;
	public DefaultCategoryDataset rationalInvestorDelta;
	public DefaultCategoryDataset passiveInvestorDelta;
	public XYSeries focusBankOptimalSecuritisationRate; // securitisation
	// hill-climbing
	// securitisation
	// rate
	// vs
	// asset
	// accumulation

	public XYSeries focusBankOptimalSecuritisationSnrTranche; // securitisation
	// hill-climbing
	// securitisation
	// rate
	// vs
	// asset
	// accumulation

	public XYSeries focusBankOptimalSecuritisationMezTranche; // securitisation
	// hill-climbing
	// securitisation
	// rate
	// vs
	// asset
	// accumulation

	public XYSeries focusBankOptimalSecuritisationJnrTranche; // securitisation
	// hill-climbing
	// securitisation
	// rate
	// vs
	// asset
	// accumulation
	
	public double focusBankOptimalSecuritisationJnrTrancheMaxUpdate;
	public double focusBankOptimalSecuritisationJnrTrancheMaxDomainUpdate;
	
	public double focusBankOptimalSecuritisationMezTrancheMaxUpdate = 0;
	public double focusBankOptimalSecuritisationMezTrancheMaxDomainUpdate = 0;
	
	public double focusBankOptimalSecuritisationSnrTrancheMaxUpdate = 0;
	public double focusBankOptimalSecuritisationSnrTrancheMaxDomainUpdate = 0;
	
	public double focusBankOptimalSecuritisationRateMaxUpdate = 0;
	public double focusBankOptimalSecuritisationRateDomainUpdate = 0;
	
	public Marker focusBankOptimalSecuritisationRateMarker;
	public Marker focusBankOptimalSecuritisationSnrTrancheMarker;
	public Marker focusBankOptimalSecuritisationMezTrancheMarker;
	public Marker focusBankOptimalSecuritisationJnrTrancheMarker;
	
	public Marker focusBankOptimalSecuritisationRateDomainMarker;
	public Marker focusBankOptimalSecuritisationSnrTrancheDomainMarker;
	public Marker focusBankOptimalSecuritisationMezTrancheDomainMarker;
	public Marker focusBankOptimalSecuritisationJnrTrancheDomainMarker;


	public TimeSeries aggregateFundAssets;// fund assets
	public TimeSeries aggregateFundLiabilities;// fund assets
	public TimeSeries aggregateFundSurplus;// fund surplus
	public TimeSeries aggregateFundAllocation;// fund asset allocation
	public TimeSeries aggregateEquityAllocation;
	public TimeSeries aggregateCreditAllocation;
	public TimeSeries aggregateCashAllocation;
	public TimeSeries aggregateFundPortfolioReturns;// Fund portfolio returns

	public TimeSeries marketClearingEquityandMBSReturn;// equity returns vs MBS
	private TimeSeries aggregateBankAssets;
	// returns from Dealer
	// class
	private TimeSeries aggregateBankLiabilities;
	private TimeSeries marketClearingEquityReturnSeries;
	private TimeSeries marketClearingMBSReturnSeries;
	private JMenuItem jMenuItemAbout;
	private JMenuItem jMenuItemSaveChartsToImageFile;
	private JMenuItem jMenuItemPrintChartsToFile;
	private JMenuItem jMenuItemExit;
	private JMenuItem jMenuItemSingleRun;
	private JMenuItem jMenuItemComparisonRun;
	private JTextField textField_CitiBankCoupons;
	private JTextField textField_CitiBankDefaultRates;
	private ArrayList<Double> citibankResearchMBSCouponsList;
	private ArrayList<Double> citibankResearchMBSDefaultsList;

	private String multiPeriodAnalysis;
	private String loanResetYear;
	private XYSeries relationshipCouponToDefaultRates;
	private XYSeries relationshipCouponToDefaultRatesPopOut;
	protected ChartPanel panel_popOutRelCouponDefaultChartPanel;
	private String seniorTrancheQualityString;
	private double seniourTrancheCoupon;
	private double seniourTrancheDefaultRate;
	private String mezzTrancheQualityString;
	private double mezzTrancheCoupon;
	private double mezzTrancheDefaultRate;
	private String juniorTrancheQualityString;
	private double juniourTrancheCoupon;
	private double juniourTrancheDefaultRate;
	private JTextField textField_PostResetDefaultRate;
	private JTextField textField_PostResetCouponRate;
	private JTextField textField_FullIndexSpread;
	private String securitisationCostGenericMBSCouponAndDefaultString;
	private double securitisationCostGenericMBSCoupon;
	private double securitisationCostGenericMBSDefault;
	private JTextField textField_SecuritisationRateDecision;
	private String securitisationRateDecisionHorizon;
	private String fullyIndexedContractRateSpread;
	private String genericPostRateResetDafaultRate;
	private String genericPostRateResetCoupon;
	private double resetDowngradeAssetQualityMBSCoupon;
	private double resetDowngradeAssetQualityMBSDefault;
	private String resetDowngradeAssetQualityString;
	private JTextField jTextField__driftMeanTraditionalAsset;
	private JTextField jTextField__standardDeviationTraditionalAsset;
	private JTextField jTextField__initialTraditionalAssetvalue;
	private JTextField jTextField__hestonMeanReversionRateTraditionalAsset;
	private JTextField jTextField__hestonLongTermVarianceTraditionalAsset;
	private JTextField jTextField__hestonVarianceVolatilityTraditionalAsset;
	private JTextField jTextField__CreditAlpha;
	private JTextField jTextField__CreditBenchmarkMean;
	private JTextField jTextField__CreditSigma;
	private JTextField jTextField__CreditInitialValue;

	private int fundType;
	private int fundExpectations;
	
	

	private int iterations = 5;
	private int pathsPerIteration = 1;
	private int pathLenght = 260;
	private double timeShit_dt = 1.0 / pathLenght;

	private double initialCreditAssetvalue;
	private double cir_AlphaCreditAssetMeanReversion;
	private double cir_ThetaCreditAssetMeanValue;
	private double standardDeviationCreditAsset;

	private double initialTraditionalAssetvalue;
	private double driftMeanTraditionalAsset;
	private double standardDeviationTraditionalAsset;
	private double hestonLongTermVarianceTraditionalAsset;
	private double hestonMeanReversionRateTraditionalAsset;
	private double hestonVarianceVolatilityTraditionalAsset;
	private double previousAssets;
	private double previousLiabilities;
	private double factorAssets = 28.7;
	private double factorLiabilities = 36.4;
	private double factorStart = 2.80;
	private double factorIncrement = 0.54;

	private JComboBox comboBox_FundsExpectations;
	private JComboBox comboBox_LAPFType;
	private JComboBox comboBox_BankType;
	JComboBox comboBox_TraditionalAssetVariations;
	private JTextField jTextField_FundExpectationsBias;

	private final int NUM_THREADS = Runtime.getRuntime().availableProcessors() + 1;
	private final ExecutorService executor = Executors
			.newFixedThreadPool(NUM_THREADS);

	private boolean heuristicSecuritisationModel;
	private boolean erevRothModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					CRTApplicationFrame frame = new CRTApplicationFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CRTApplicationFrame(SimEngine eng, MarkoseDYangModel_V01 model)
			throws HeadlessException {
		super();
		this.eng = eng;
		this.model = model;
		this.model.frame = this;
		this.modelObserver = new SecuritisationModelObserverJAS(this.model);
		eng.addModel(this.model);
		eng.addModel(this.modelObserver);
		param = new Parameters();
		initialize();
	}

	CRTApplicationFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				CRTApplicationFrame.class.getResource("/jas/images/graph.gif")));
		setDefaultBankNamesList();
		initialize();
		this.eng = new SimEngine();
		this.model = new MarkoseDYangModel_V01();
		this.model.frame = this;
		// this.model.setModelDefaultParameters(this.param);
		// this.model.buildEconomy();
		// this.model.buildEnviroment();
		this.modelObserver = new SecuritisationModelObserverJAS(this.model);
		eng.addModel(this.model);
		eng.addModel(this.modelObserver);
		// buildOrUpdateCharts();
	}

	/**
	 * Create the frame.
	 * 
	 * @return
	 */
	public void initialize() {
		setTitle("Credit Risk Transfer With Securitisation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1554, 868);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		MDPParameterDialog = new MDPModelParameterSettingWindow();// MDP Model
																	// Parameters
																	// setup
		MDPIntermediateHeuristicParameterDialog = new MDPIntermediateHeuristicParameterSettingWindow(); //intermediate Heuristic Parameters
		MDPErevRothdialog = new MDPErevRothSettingsWindow();
		mnFile.setMnemonic(KeyEvent.VK_F);
		// jMenuFile.add(getJMenuItemShowExposures());
		// jMenuFile.add(getJMenuItemShowSpreads());
		mnFile.add(getJMenuItemPrintChartsToFile());
		mnFile.add(getJMenuItemSaveChartsToImageFile());
		mnFile.add(getJMenuItemExit());
		JMenu mnHelp = new JMenu("Help");
		JMenuItem mnItemAbout = this.getJMenuItemAbout();
		mnHelp.add(mnItemAbout);
		menuBar.add(mnFile);
		menuBar.add(mnHelp);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel application_main_panel = new JPanel();
		application_main_panel.setBorder(new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		contentPane.add(application_main_panel, BorderLayout.CENTER);
		application_main_panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_ModelConfig = new JPanel();
		application_main_panel.add(panel_ModelConfig, BorderLayout.EAST);
		GridBagLayout gbl_panel_ModelConfig = new GridBagLayout();
		gbl_panel_ModelConfig.columnWidths = new int[] { 345 };
		gbl_panel_ModelConfig.rowHeights = new int[] { 191, 0, 0 };
		gbl_panel_ModelConfig.columnWeights = new double[] { 1.0 };
		gbl_panel_ModelConfig.rowWeights = new double[] { 0.0, 0.0, 1.0 };
		panel_ModelConfig.setLayout(gbl_panel_ModelConfig);

		JPanel panel_EconomyConfig = new JPanel();
		panel_EconomyConfig.setToolTipText("Set economy configurations");
		panel_EconomyConfig.setBorder(new TitledBorder(null,
				"Economy Configuration", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_EconomyConfig = new GridBagConstraints();
		gbc_panel_EconomyConfig.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_EconomyConfig.insets = new Insets(0, 0, 5, 0);
		gbc_panel_EconomyConfig.gridx = 0;
		gbc_panel_EconomyConfig.gridy = 0;
		panel_ModelConfig.add(panel_EconomyConfig, gbc_panel_EconomyConfig);
		GridBagLayout gbl_panel_EconomyConfig = new GridBagLayout();
		gbl_panel_EconomyConfig.columnWidths = new int[] { 391, 0 };
		gbl_panel_EconomyConfig.rowHeights = new int[] { 368, 0 };
		gbl_panel_EconomyConfig.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_panel_EconomyConfig.rowWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		panel_EconomyConfig.setLayout(gbl_panel_EconomyConfig);

		JPanel panel_economyConfig = new JPanel();
		panel_economyConfig.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null));
		GridBagConstraints gbc_panel_economyConfig = new GridBagConstraints();
		gbc_panel_economyConfig.fill = GridBagConstraints.VERTICAL;
		gbc_panel_economyConfig.gridx = 0;
		gbc_panel_economyConfig.gridy = 0;
		panel_EconomyConfig.add(panel_economyConfig, gbc_panel_economyConfig);
		GridBagLayout gbl_panel_economyConfig = new GridBagLayout();
		gbl_panel_economyConfig.columnWidths = new int[] { 203, 187 };
		gbl_panel_economyConfig.rowHeights = new int[] { 14, 14, 14, 0, 0, 0,
				0, 0, 0, 14, 0, 0, 0, 0, 14, 14, 14, 14, 14, 14, 14 };
		gbl_panel_economyConfig.columnWeights = new double[] { 1.0, 1.0 };
		gbl_panel_economyConfig.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 1.0 };
		panel_economyConfig.setLayout(gbl_panel_economyConfig);

		JLabel lblEquityMarketReturn = new JLabel("Equity Market Return:");
		lblEquityMarketReturn.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblEquityMarketReturn = new GridBagConstraints();
		gbc_lblEquityMarketReturn.anchor = GridBagConstraints.WEST;
		gbc_lblEquityMarketReturn.insets = new Insets(0, 0, 5, 5);
		gbc_lblEquityMarketReturn.gridx = 0;
		gbc_lblEquityMarketReturn.gridy = 0;
		panel_economyConfig.add(lblEquityMarketReturn,
				gbc_lblEquityMarketReturn);

		textField_EquityMarketReturn = new JTextField();
		textField_EquityMarketReturn.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		textField_EquityMarketReturn.setText("0.05");
		GridBagConstraints gbc_textField_EquityMarketReturn = new GridBagConstraints();
		gbc_textField_EquityMarketReturn.insets = new Insets(0, 0, 5, 0);
		gbc_textField_EquityMarketReturn.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_EquityMarketReturn.gridx = 1;
		gbc_textField_EquityMarketReturn.gridy = 0;
		panel_economyConfig.add(textField_EquityMarketReturn,
				gbc_textField_EquityMarketReturn);
		textField_EquityMarketReturn.setColumns(10);

		JLabel lblEquityMarketDefault = new JLabel(
				"Traditional Asset Default Rate:");
		lblEquityMarketDefault.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblEquityMarketDefault = new GridBagConstraints();
		gbc_lblEquityMarketDefault.anchor = GridBagConstraints.WEST;
		gbc_lblEquityMarketDefault.insets = new Insets(0, 0, 5, 5);
		gbc_lblEquityMarketDefault.gridx = 0;
		gbc_lblEquityMarketDefault.gridy = 1;
		panel_economyConfig.add(lblEquityMarketDefault,
				gbc_lblEquityMarketDefault);

		textField_EquityMarketDefaultRate = new JTextField();
		textField_EquityMarketDefaultRate.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_EquityMarketDefaultRate.setText("0.0");
		GridBagConstraints gbc_textField_EquityMarketDefaultRate = new GridBagConstraints();
		gbc_textField_EquityMarketDefaultRate.insets = new Insets(0, 0, 5, 0);
		gbc_textField_EquityMarketDefaultRate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_EquityMarketDefaultRate.gridx = 1;
		gbc_textField_EquityMarketDefaultRate.gridy = 1;
		panel_economyConfig.add(textField_EquityMarketDefaultRate,
				gbc_textField_EquityMarketDefaultRate);
		textField_EquityMarketDefaultRate.setColumns(10);

		JLabel lblEquityMarketRecovery = new JLabel(
				"Traditional Asset Recovery Rate:");
		lblEquityMarketRecovery.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblEquityMarketRecovery = new GridBagConstraints();
		gbc_lblEquityMarketRecovery.anchor = GridBagConstraints.WEST;
		gbc_lblEquityMarketRecovery.insets = new Insets(0, 0, 5, 5);
		gbc_lblEquityMarketRecovery.gridx = 0;
		gbc_lblEquityMarketRecovery.gridy = 2;
		panel_economyConfig.add(lblEquityMarketRecovery,
				gbc_lblEquityMarketRecovery);

		textField_EquityMarketRecoveryRate = new JTextField();
		textField_EquityMarketRecoveryRate.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_EquityMarketRecoveryRate.setText("1");
		GridBagConstraints gbc_textField_EquityMarketRecoveryRate = new GridBagConstraints();
		gbc_textField_EquityMarketRecoveryRate.insets = new Insets(0, 0, 5, 0);
		gbc_textField_EquityMarketRecoveryRate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_EquityMarketRecoveryRate.gridx = 1;
		gbc_textField_EquityMarketRecoveryRate.gridy = 2;
		panel_economyConfig.add(textField_EquityMarketRecoveryRate,
				gbc_textField_EquityMarketRecoveryRate);
		textField_EquityMarketRecoveryRate.setColumns(10);

		JLabel jLabel_EquityDrift = new JLabel("Market Index Drift:");
		jLabel_EquityDrift.setVerticalAlignment(SwingConstants.TOP);
		jLabel_EquityDrift.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_EquityDrift = new GridBagConstraints();
		gbc_jLabel_EquityDrift.anchor = GridBagConstraints.WEST;
		gbc_jLabel_EquityDrift.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_EquityDrift.gridx = 0;
		gbc_jLabel_EquityDrift.gridy = 3;
		panel_economyConfig.add(jLabel_EquityDrift, gbc_jLabel_EquityDrift);

		jTextField__driftMeanTraditionalAsset = new JTextField();
		jTextField__driftMeanTraditionalAsset
				.setHorizontalAlignment(SwingConstants.LEFT);
		jTextField__driftMeanTraditionalAsset.setText("0.005");
		jTextField__driftMeanTraditionalAsset.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		jTextField__driftMeanTraditionalAsset.setColumns(10);
		GridBagConstraints gbc_jTextField__driftMeanTraditionalAsset = new GridBagConstraints();
		gbc_jTextField__driftMeanTraditionalAsset.insets = new Insets(0, 0, 5,
				0);
		gbc_jTextField__driftMeanTraditionalAsset.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__driftMeanTraditionalAsset.gridx = 1;
		gbc_jTextField__driftMeanTraditionalAsset.gridy = 3;
		panel_economyConfig.add(jTextField__driftMeanTraditionalAsset,
				gbc_jTextField__driftMeanTraditionalAsset);

		JLabel jLabel_EquityStandardDeviation = new JLabel(
				"Market Index Standard Deviation:");
		jLabel_EquityStandardDeviation.setVerticalAlignment(SwingConstants.TOP);
		jLabel_EquityStandardDeviation.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_EquityStandardDeviation = new GridBagConstraints();
		gbc_jLabel_EquityStandardDeviation.anchor = GridBagConstraints.WEST;
		gbc_jLabel_EquityStandardDeviation.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_EquityStandardDeviation.gridx = 0;
		gbc_jLabel_EquityStandardDeviation.gridy = 4;
		panel_economyConfig.add(jLabel_EquityStandardDeviation,
				gbc_jLabel_EquityStandardDeviation);

		jTextField__standardDeviationTraditionalAsset = new JTextField();
		jTextField__standardDeviationTraditionalAsset
				.setHorizontalAlignment(SwingConstants.LEFT);
		jTextField__standardDeviationTraditionalAsset.setText("0.070");
		jTextField__standardDeviationTraditionalAsset.setFont(new Font(
				"SansSerif", Font.PLAIN, 9));
		jTextField__standardDeviationTraditionalAsset.setColumns(10);
		GridBagConstraints gbc_jTextField__standardDeviationTraditionalAsset = new GridBagConstraints();
		gbc_jTextField__standardDeviationTraditionalAsset.insets = new Insets(
				0, 0, 5, 0);
		gbc_jTextField__standardDeviationTraditionalAsset.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__standardDeviationTraditionalAsset.gridx = 1;
		gbc_jTextField__standardDeviationTraditionalAsset.gridy = 4;
		panel_economyConfig.add(jTextField__standardDeviationTraditionalAsset,
				gbc_jTextField__standardDeviationTraditionalAsset);

		JLabel jLabel_EquityInitialValue = new JLabel("Market Index Level:");
		jLabel_EquityInitialValue.setVerticalAlignment(SwingConstants.TOP);
		jLabel_EquityInitialValue.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_EquityInitialValue = new GridBagConstraints();
		gbc_jLabel_EquityInitialValue.anchor = GridBagConstraints.WEST;
		gbc_jLabel_EquityInitialValue.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_EquityInitialValue.gridx = 0;
		gbc_jLabel_EquityInitialValue.gridy = 5;
		panel_economyConfig.add(jLabel_EquityInitialValue,
				gbc_jLabel_EquityInitialValue);

		jTextField__initialTraditionalAssetvalue = new JTextField();
		jTextField__initialTraditionalAssetvalue
				.setHorizontalAlignment(SwingConstants.LEFT);
		jTextField__initialTraditionalAssetvalue.setText("1108.48");
		jTextField__initialTraditionalAssetvalue.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		jTextField__initialTraditionalAssetvalue.setColumns(10);
		GridBagConstraints gbc_jTextField__initialTraditionalAssetvalue = new GridBagConstraints();
		gbc_jTextField__initialTraditionalAssetvalue.insets = new Insets(0, 0,
				5, 0);
		gbc_jTextField__initialTraditionalAssetvalue.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__initialTraditionalAssetvalue.gridx = 1;
		gbc_jTextField__initialTraditionalAssetvalue.gridy = 5;
		panel_economyConfig.add(jTextField__initialTraditionalAssetvalue,
				gbc_jTextField__initialTraditionalAssetvalue);

		JLabel jLabel_EquityHestonVarMeanReversion = new JLabel(
				"Market Index Variance Mean Reversion:");
		jLabel_EquityHestonVarMeanReversion
				.setVerticalAlignment(SwingConstants.TOP);
		jLabel_EquityHestonVarMeanReversion.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_EquityHestonVarMeanReversion = new GridBagConstraints();
		gbc_jLabel_EquityHestonVarMeanReversion.anchor = GridBagConstraints.WEST;
		gbc_jLabel_EquityHestonVarMeanReversion.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_EquityHestonVarMeanReversion.gridx = 0;
		gbc_jLabel_EquityHestonVarMeanReversion.gridy = 6;
		panel_economyConfig.add(jLabel_EquityHestonVarMeanReversion,
				gbc_jLabel_EquityHestonVarMeanReversion);

		jTextField__hestonMeanReversionRateTraditionalAsset = new JTextField();
		jTextField__hestonMeanReversionRateTraditionalAsset
				.setHorizontalAlignment(SwingConstants.LEFT);
		jTextField__hestonMeanReversionRateTraditionalAsset.setText("0.054201");
		jTextField__hestonMeanReversionRateTraditionalAsset.setFont(new Font(
				"SansSerif", Font.PLAIN, 9));
		jTextField__hestonMeanReversionRateTraditionalAsset.setColumns(10);
		GridBagConstraints gbc_jTextField__hestonMeanReversionRateTraditionalAsset = new GridBagConstraints();
		gbc_jTextField__hestonMeanReversionRateTraditionalAsset.insets = new Insets(
				0, 0, 5, 0);
		gbc_jTextField__hestonMeanReversionRateTraditionalAsset.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__hestonMeanReversionRateTraditionalAsset.gridx = 1;
		gbc_jTextField__hestonMeanReversionRateTraditionalAsset.gridy = 6;
		panel_economyConfig.add(
				jTextField__hestonMeanReversionRateTraditionalAsset,
				gbc_jTextField__hestonMeanReversionRateTraditionalAsset);

		JLabel jLabel_EquityHestonLongTermVar = new JLabel(
				"Market Index Long Term Variance:");
		jLabel_EquityHestonLongTermVar.setVerticalAlignment(SwingConstants.TOP);
		jLabel_EquityHestonLongTermVar.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_EquityHestonLongTermVar = new GridBagConstraints();
		gbc_jLabel_EquityHestonLongTermVar.anchor = GridBagConstraints.WEST;
		gbc_jLabel_EquityHestonLongTermVar.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_EquityHestonLongTermVar.gridx = 0;
		gbc_jLabel_EquityHestonLongTermVar.gridy = 7;
		panel_economyConfig.add(jLabel_EquityHestonLongTermVar,
				gbc_jLabel_EquityHestonLongTermVar);

		jTextField__hestonLongTermVarianceTraditionalAsset = new JTextField();
		jTextField__hestonLongTermVarianceTraditionalAsset
				.setHorizontalAlignment(SwingConstants.LEFT);
		jTextField__hestonLongTermVarianceTraditionalAsset.setText("0.074201");
		jTextField__hestonLongTermVarianceTraditionalAsset.setFont(new Font(
				"SansSerif", Font.PLAIN, 9));
		jTextField__hestonLongTermVarianceTraditionalAsset.setColumns(10);
		GridBagConstraints gbc_jTextField__hestonLongTermVarianceTraditionalAsset = new GridBagConstraints();
		gbc_jTextField__hestonLongTermVarianceTraditionalAsset.insets = new Insets(
				0, 0, 5, 0);
		gbc_jTextField__hestonLongTermVarianceTraditionalAsset.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__hestonLongTermVarianceTraditionalAsset.gridx = 1;
		gbc_jTextField__hestonLongTermVarianceTraditionalAsset.gridy = 7;
		panel_economyConfig.add(
				jTextField__hestonLongTermVarianceTraditionalAsset,
				gbc_jTextField__hestonLongTermVarianceTraditionalAsset);

		JLabel jLabel_EquityHestonVarVol = new JLabel(
				"Market Index Variance Volatility");
		jLabel_EquityHestonVarVol.setVerticalAlignment(SwingConstants.TOP);
		jLabel_EquityHestonVarVol.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_EquityHestonVarVol = new GridBagConstraints();
		gbc_jLabel_EquityHestonVarVol.anchor = GridBagConstraints.WEST;
		gbc_jLabel_EquityHestonVarVol.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_EquityHestonVarVol.gridx = 0;
		gbc_jLabel_EquityHestonVarVol.gridy = 8;
		panel_economyConfig.add(jLabel_EquityHestonVarVol,
				gbc_jLabel_EquityHestonVarVol);

		jTextField__hestonVarianceVolatilityTraditionalAsset = new JTextField();
		jTextField__hestonVarianceVolatilityTraditionalAsset
				.setHorizontalAlignment(SwingConstants.LEFT);
		jTextField__hestonVarianceVolatilityTraditionalAsset
				.setText("0.014201");
		jTextField__hestonVarianceVolatilityTraditionalAsset.setFont(new Font(
				"SansSerif", Font.PLAIN, 9));
		jTextField__hestonVarianceVolatilityTraditionalAsset.setColumns(10);
		GridBagConstraints gbc_jTextField__hestonVarianceVolatilityTraditionalAsset = new GridBagConstraints();
		gbc_jTextField__hestonVarianceVolatilityTraditionalAsset.insets = new Insets(
				0, 0, 5, 0);
		gbc_jTextField__hestonVarianceVolatilityTraditionalAsset.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__hestonVarianceVolatilityTraditionalAsset.gridx = 1;
		gbc_jTextField__hestonVarianceVolatilityTraditionalAsset.gridy = 8;
		panel_economyConfig.add(
				jTextField__hestonVarianceVolatilityTraditionalAsset,
				gbc_jTextField__hestonVarianceVolatilityTraditionalAsset);

		JLabel lblCreditMarketRecovery = new JLabel(
				"Credit Market Recovery Rate:");
		lblCreditMarketRecovery.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblCreditMarketRecovery = new GridBagConstraints();
		gbc_lblCreditMarketRecovery.anchor = GridBagConstraints.WEST;
		gbc_lblCreditMarketRecovery.insets = new Insets(0, 0, 5, 5);
		gbc_lblCreditMarketRecovery.gridx = 0;
		gbc_lblCreditMarketRecovery.gridy = 9;
		panel_economyConfig.add(lblCreditMarketRecovery,
				gbc_lblCreditMarketRecovery);

		textField_CreditMarketRecoveryRate = new JTextField();
		textField_CreditMarketRecoveryRate.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_CreditMarketRecoveryRate.setText("0.60");
		GridBagConstraints gbc_textField_CreditMarketRecoveryRate = new GridBagConstraints();
		gbc_textField_CreditMarketRecoveryRate.insets = new Insets(0, 0, 5, 0);
		gbc_textField_CreditMarketRecoveryRate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_CreditMarketRecoveryRate.gridx = 1;
		gbc_textField_CreditMarketRecoveryRate.gridy = 9;
		panel_economyConfig.add(textField_CreditMarketRecoveryRate,
				gbc_textField_CreditMarketRecoveryRate);
		textField_CreditMarketRecoveryRate.setColumns(10);

		JLabel jLabel_CreditMeanReversionRate = new JLabel(
				"Credit Market Benchmark Mean Revesion Rate:");
		jLabel_CreditMeanReversionRate.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_CreditMeanReversionRate = new GridBagConstraints();
		gbc_jLabel_CreditMeanReversionRate.anchor = GridBagConstraints.WEST;
		gbc_jLabel_CreditMeanReversionRate.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_CreditMeanReversionRate.gridx = 0;
		gbc_jLabel_CreditMeanReversionRate.gridy = 10;
		panel_economyConfig.add(jLabel_CreditMeanReversionRate,
				gbc_jLabel_CreditMeanReversionRate);

		jTextField__CreditAlpha = new JTextField();
		jTextField__CreditAlpha.setToolTipText("CIR Alpha");
		jTextField__CreditAlpha.setText("0.54");
		jTextField__CreditAlpha.setFont(new Font("SansSerif", Font.PLAIN, 9));
		jTextField__CreditAlpha.setColumns(10);
		GridBagConstraints gbc_jTextField__CreditAlpha = new GridBagConstraints();
		gbc_jTextField__CreditAlpha.insets = new Insets(0, 0, 5, 0);
		gbc_jTextField__CreditAlpha.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__CreditAlpha.gridx = 1;
		gbc_jTextField__CreditAlpha.gridy = 10;
		panel_economyConfig.add(jTextField__CreditAlpha,
				gbc_jTextField__CreditAlpha);

		JLabel jLabel_CreditInitialBenchmarkMean = new JLabel(
				"Credit Market Initial Mean Benchmark Level:");
		jLabel_CreditInitialBenchmarkMean
				.setVerticalAlignment(SwingConstants.TOP);
		jLabel_CreditInitialBenchmarkMean.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_CreditInitialBenchmarkMean = new GridBagConstraints();
		gbc_jLabel_CreditInitialBenchmarkMean.anchor = GridBagConstraints.WEST;
		gbc_jLabel_CreditInitialBenchmarkMean.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_CreditInitialBenchmarkMean.gridx = 0;
		gbc_jLabel_CreditInitialBenchmarkMean.gridy = 11;
		panel_economyConfig.add(jLabel_CreditInitialBenchmarkMean,
				gbc_jLabel_CreditInitialBenchmarkMean);

		jTextField__CreditBenchmarkMean = new JTextField();
		jTextField__CreditBenchmarkMean.setToolTipText("CIR Theta");
		jTextField__CreditBenchmarkMean.setText("95");
		jTextField__CreditBenchmarkMean.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		jTextField__CreditBenchmarkMean.setColumns(10);
		GridBagConstraints gbc_jTextField__CreditBenchmarkMean = new GridBagConstraints();
		gbc_jTextField__CreditBenchmarkMean.insets = new Insets(0, 0, 5, 0);
		gbc_jTextField__CreditBenchmarkMean.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__CreditBenchmarkMean.gridx = 1;
		gbc_jTextField__CreditBenchmarkMean.gridy = 11;
		panel_economyConfig.add(jTextField__CreditBenchmarkMean,
				gbc_jTextField__CreditBenchmarkMean);

		JLabel jLabel_CreditStandardDeviation = new JLabel(
				"Credit Market Standard Deviation:");
		jLabel_CreditStandardDeviation.setVerticalAlignment(SwingConstants.TOP);
		jLabel_CreditStandardDeviation.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_CreditStandardDeviation = new GridBagConstraints();
		gbc_jLabel_CreditStandardDeviation.anchor = GridBagConstraints.WEST;
		gbc_jLabel_CreditStandardDeviation.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_CreditStandardDeviation.gridx = 0;
		gbc_jLabel_CreditStandardDeviation.gridy = 12;
		panel_economyConfig.add(jLabel_CreditStandardDeviation,
				gbc_jLabel_CreditStandardDeviation);

		jTextField__CreditSigma = new JTextField();
		jTextField__CreditSigma.setToolTipText("CIR Model Standard Deviation");
		jTextField__CreditSigma.setText("0.07");
		jTextField__CreditSigma.setFont(new Font("SansSerif", Font.PLAIN, 9));
		jTextField__CreditSigma.setColumns(10);
		GridBagConstraints gbc_jTextField__CreditSigma = new GridBagConstraints();
		gbc_jTextField__CreditSigma.insets = new Insets(0, 0, 5, 0);
		gbc_jTextField__CreditSigma.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__CreditSigma.gridx = 1;
		gbc_jTextField__CreditSigma.gridy = 12;
		panel_economyConfig.add(jTextField__CreditSigma,
				gbc_jTextField__CreditSigma);

		JLabel jLabel_CreditInitialValue = new JLabel(
				"Credit Market Benchmark Level");
		jLabel_CreditInitialValue.setVerticalAlignment(SwingConstants.TOP);
		jLabel_CreditInitialValue.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_CreditInitialValue = new GridBagConstraints();
		gbc_jLabel_CreditInitialValue.anchor = GridBagConstraints.WEST;
		gbc_jLabel_CreditInitialValue.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_CreditInitialValue.gridx = 0;
		gbc_jLabel_CreditInitialValue.gridy = 13;
		panel_economyConfig.add(jLabel_CreditInitialValue,
				gbc_jLabel_CreditInitialValue);

		jTextField__CreditInitialValue = new JTextField();
		jTextField__CreditInitialValue.setText("100");
		jTextField__CreditInitialValue.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		jTextField__CreditInitialValue.setColumns(10);
		GridBagConstraints gbc_jTextField__CreditInitialValue = new GridBagConstraints();
		gbc_jTextField__CreditInitialValue.insets = new Insets(0, 0, 5, 0);
		gbc_jTextField__CreditInitialValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField__CreditInitialValue.gridx = 1;
		gbc_jTextField__CreditInitialValue.gridy = 13;
		panel_economyConfig.add(jTextField__CreditInitialValue,
				gbc_jTextField__CreditInitialValue);

		JLabel lblCashDepositReturn = new JLabel("Cash Deposit Return:");
		lblCashDepositReturn.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblCashDepositReturn = new GridBagConstraints();
		gbc_lblCashDepositReturn.anchor = GridBagConstraints.WEST;
		gbc_lblCashDepositReturn.insets = new Insets(0, 0, 5, 5);
		gbc_lblCashDepositReturn.gridx = 0;
		gbc_lblCashDepositReturn.gridy = 14;
		panel_economyConfig.add(lblCashDepositReturn, gbc_lblCashDepositReturn);

		textField_CashDepositRate = new JTextField();
		textField_CashDepositRate.setFont(new Font("SansSerif", Font.PLAIN, 9));
		textField_CashDepositRate.setText("0.001");
		GridBagConstraints gbc_textField_CashDepositRate = new GridBagConstraints();
		gbc_textField_CashDepositRate.insets = new Insets(0, 0, 5, 0);
		gbc_textField_CashDepositRate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_CashDepositRate.gridx = 1;
		gbc_textField_CashDepositRate.gridy = 14;
		panel_economyConfig.add(textField_CashDepositRate,
				gbc_textField_CashDepositRate);
		textField_CashDepositRate.setColumns(10);

		JLabel lblGenericSecuritisationRate = new JLabel(
				"Generic Securitisation Rate:");
		lblGenericSecuritisationRate.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		GridBagConstraints gbc_lblGenericSecuritisationRate = new GridBagConstraints();
		gbc_lblGenericSecuritisationRate.anchor = GridBagConstraints.WEST;
		gbc_lblGenericSecuritisationRate.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenericSecuritisationRate.gridx = 0;
		gbc_lblGenericSecuritisationRate.gridy = 15;
		panel_economyConfig.add(lblGenericSecuritisationRate,
				gbc_lblGenericSecuritisationRate);

		textField_GenericSecuritisationRate = new JTextField();
		textField_GenericSecuritisationRate.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_GenericSecuritisationRate.setText("0.42");
		GridBagConstraints gbc_textField_GenericSecuritisationRate = new GridBagConstraints();
		gbc_textField_GenericSecuritisationRate.insets = new Insets(0, 0, 5, 0);
		gbc_textField_GenericSecuritisationRate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_GenericSecuritisationRate.gridx = 1;
		gbc_textField_GenericSecuritisationRate.gridy = 15;
		panel_economyConfig.add(textField_GenericSecuritisationRate,
				gbc_textField_GenericSecuritisationRate);
		textField_GenericSecuritisationRate.setColumns(10);

		JLabel lblMultiperiodHorizonModel = new JLabel(
				"Time Variant Factors (ARM) Model:");
		lblMultiperiodHorizonModel
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblMultiperiodHorizonModel
				.setToolTipText("If set to false securitisation will use the constant Q otherwise it will use the ARM model with a reset window");
		GridBagConstraints gbc_lblMultiperiodHorizonModel = new GridBagConstraints();
		gbc_lblMultiperiodHorizonModel.anchor = GridBagConstraints.WEST;
		gbc_lblMultiperiodHorizonModel.insets = new Insets(0, 0, 5, 5);
		gbc_lblMultiperiodHorizonModel.gridx = 0;
		gbc_lblMultiperiodHorizonModel.gridy = 16;
		panel_economyConfig.add(lblMultiperiodHorizonModel,
				gbc_lblMultiperiodHorizonModel);

		JComboBox comboBox_MultiPeriodHorizon = new JComboBox();
		comboBox_MultiPeriodHorizon
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_MultiPeriodHorizon
				.setToolTipText("aloow multiperiod simulation run");
		comboBox_MultiPeriodHorizon.setModel(new DefaultComboBoxModel(
				new String[] { "true", "false" }));
		comboBox_MultiPeriodHorizon.setSelectedIndex(0);
		String multiperiodString = (String) comboBox_MultiPeriodHorizon
				.getSelectedItem();
		setMultiPeriodBankHorizon(multiperiodString);
		comboBox_MultiPeriodHorizon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String multiperiodString = (String) jcmbType.getSelectedItem();
				setMultiPeriodBankHorizon(multiperiodString);
			}
		});
		GridBagConstraints gbc_comboBox_MultiPeriodHorizon = new GridBagConstraints();
		gbc_comboBox_MultiPeriodHorizon.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_MultiPeriodHorizon.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_MultiPeriodHorizon.gridx = 1;
		gbc_comboBox_MultiPeriodHorizon.gridy = 16;
		panel_economyConfig.add(comboBox_MultiPeriodHorizon,
				gbc_comboBox_MultiPeriodHorizon);

		JLabel lblSecuritisationRateDecision = new JLabel(
				"Securitisation Rate Decision Horizon:");
		lblSecuritisationRateDecision.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		lblSecuritisationRateDecision
				.setToolTipText("Securitisation Rate Decision Horizon");
		GridBagConstraints gbc_lblSecuritisationRateDecision = new GridBagConstraints();
		gbc_lblSecuritisationRateDecision.anchor = GridBagConstraints.WEST;
		gbc_lblSecuritisationRateDecision.insets = new Insets(0, 0, 5, 5);
		gbc_lblSecuritisationRateDecision.gridx = 0;
		gbc_lblSecuritisationRateDecision.gridy = 17;
		panel_economyConfig.add(lblSecuritisationRateDecision,
				gbc_lblSecuritisationRateDecision);

		textField_SecuritisationRateDecision = new JTextField();
		textField_SecuritisationRateDecision.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_SecuritisationRateDecision.setText("5");
		textField_SecuritisationRateDecision.setColumns(10);
		GridBagConstraints gbc_textField_SecuritisationRateDecision = new GridBagConstraints();
		gbc_textField_SecuritisationRateDecision.insets = new Insets(0, 0, 5, 0);
		gbc_textField_SecuritisationRateDecision.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_SecuritisationRateDecision.gridx = 1;
		gbc_textField_SecuritisationRateDecision.gridy = 17;
		panel_economyConfig.add(textField_SecuritisationRateDecision,
				gbc_textField_SecuritisationRateDecision);

		JLabel lblLoanResetWindow = new JLabel("Loan Reset Window:");
		lblLoanResetWindow.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblLoanResetWindow.setToolTipText("Set Loan Reset Window");
		GridBagConstraints gbc_lblLoanResetWindow = new GridBagConstraints();
		gbc_lblLoanResetWindow.anchor = GridBagConstraints.WEST;
		gbc_lblLoanResetWindow.insets = new Insets(0, 0, 5, 5);
		gbc_lblLoanResetWindow.gridx = 0;
		gbc_lblLoanResetWindow.gridy = 18;
		panel_economyConfig.add(lblLoanResetWindow, gbc_lblLoanResetWindow);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_1.setToolTipText("Select Year of Loan Reset");
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "0", "1",
				"2", "3", "4", "5" }));
		comboBox_1.setSelectedIndex(2);
		String loanResetString = (String) comboBox_1.getSelectedItem();
		setLoanResetYear(loanResetString);
		comboBox_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setLoanResetYear(cmbType);
			}
		});

		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 18;
		panel_economyConfig.add(comboBox_1, gbc_comboBox_1);

		JLabel lblCitibankResearchMbs = new JLabel(
				"MBS Coupon Rates by Tranche:");
		lblCitibankResearchMbs.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblCitibankResearchMbs.setToolTipText("Set Loan Reset Window");
		GridBagConstraints gbc_lblCitibankResearchMbs = new GridBagConstraints();
		gbc_lblCitibankResearchMbs.anchor = GridBagConstraints.WEST;
		gbc_lblCitibankResearchMbs.insets = new Insets(0, 0, 5, 5);
		gbc_lblCitibankResearchMbs.gridx = 0;
		gbc_lblCitibankResearchMbs.gridy = 19;
		panel_economyConfig.add(lblCitibankResearchMbs,
				gbc_lblCitibankResearchMbs);

		textField_CitiBankCoupons = new JTextField();
		textField_CitiBankCoupons.setFont(new Font("SansSerif", Font.PLAIN, 9));
		textField_CitiBankCoupons
				.setToolTipText("Citibank research coupon rates");
		textField_CitiBankCoupons
				.setText("0.03,0.04,0.0517,0.0642,0.0642,0.0987,0.11,0.12,0.13,0.14,0.15,0.16,0.16");
		textField_CitiBankCoupons.setColumns(10);

		ArrayList couponsList = new ArrayList<String>(
				Arrays.asList(textField_CitiBankCoupons.getText().split(
						"\\s*,\\s*")));

		citibankResearchMBSCouponsList = new ArrayList<Double>();
		for (int i = 0; i < couponsList.size(); i++) {
			citibankResearchMBSCouponsList.add(Double
					.parseDouble((String) couponsList.get(i)));
		}

		GridBagConstraints gbc_textField_CitiBankCoupons = new GridBagConstraints();
		gbc_textField_CitiBankCoupons.insets = new Insets(0, 0, 5, 0);
		gbc_textField_CitiBankCoupons.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_CitiBankCoupons.gridx = 1;
		gbc_textField_CitiBankCoupons.gridy = 19;
		panel_economyConfig.add(textField_CitiBankCoupons,
				gbc_textField_CitiBankCoupons);

		JLabel lblMbsDefaultRates = new JLabel("MBS Default Rates by Tranche:");
		lblMbsDefaultRates.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblMbsDefaultRates.setToolTipText("Set Loan Reset Window");
		GridBagConstraints gbc_lblMbsDefaultRates = new GridBagConstraints();
		gbc_lblMbsDefaultRates.anchor = GridBagConstraints.WEST;
		gbc_lblMbsDefaultRates.insets = new Insets(0, 0, 5, 5);
		gbc_lblMbsDefaultRates.gridx = 0;
		gbc_lblMbsDefaultRates.gridy = 20;
		panel_economyConfig.add(lblMbsDefaultRates, gbc_lblMbsDefaultRates);

		textField_CitiBankDefaultRates = new JTextField();
		textField_CitiBankDefaultRates.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_CitiBankDefaultRates
				.setToolTipText("Citibank research default rates");
		textField_CitiBankDefaultRates
				.setText("0.0,0.001,0.05,0.05,0.07,0.1,0.11,0.12,0.13,0.14,0.15,0.16,0.16");
		textField_CitiBankDefaultRates.setColumns(10);

		ArrayList defaultList = new ArrayList<String>(
				Arrays.asList(textField_CitiBankDefaultRates.getText().split(
						"\\s*,\\s*")));

		citibankResearchMBSDefaultsList = new ArrayList<Double>();
		for (int i = 0; i < defaultList.size(); i++) {
			citibankResearchMBSDefaultsList.add(Double
					.parseDouble((String) defaultList.get(i)));
		}

		GridBagConstraints gbc_textField_CitiBankDefaultRates = new GridBagConstraints();
		gbc_textField_CitiBankDefaultRates.insets = new Insets(0, 0, 5, 0);
		gbc_textField_CitiBankDefaultRates.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_CitiBankDefaultRates.gridx = 1;
		gbc_textField_CitiBankDefaultRates.gridy = 20;
		panel_economyConfig.add(textField_CitiBankDefaultRates,
				gbc_textField_CitiBankDefaultRates);
		// System.out.println(cmbType);
		couponsList = new ArrayList<String>(
				Arrays.asList(textField_CitiBankCoupons.getText().split(
						"\\s*,\\s*")));

		citibankResearchMBSCouponsList = new ArrayList<Double>();
		for (int i = 0; i < couponsList.size(); i++) {
			citibankResearchMBSCouponsList.add(Double
					.parseDouble((String) couponsList.get(i)));
		}

		defaultList = new ArrayList<String>(
				Arrays.asList(textField_CitiBankDefaultRates.getText().split(
						"\\s*,\\s*")));

		citibankResearchMBSDefaultsList = new ArrayList<Double>();
		for (int i = 0; i < defaultList.size(); i++) {
			citibankResearchMBSDefaultsList.add(Double
					.parseDouble((String) defaultList.get(i)));
		}

		JLabel lblMbsTranche = new JLabel(
				"MBS Tranche/Asset Quality Transition:");
		lblMbsTranche.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblMbsTranche
				.setToolTipText("This field defined the transches of the create MBS issuances and "
						+ "also the Asset Quality Deterioration Used back Banks ");
		GridBagConstraints gbc_lblMbsTranche = new GridBagConstraints();
		gbc_lblMbsTranche.anchor = GridBagConstraints.WEST;
		gbc_lblMbsTranche.insets = new Insets(0, 0, 0, 5);
		gbc_lblMbsTranche.gridx = 0;
		gbc_lblMbsTranche.gridy = 21;
		panel_economyConfig.add(lblMbsTranche, gbc_lblMbsTranche);

		JPanel panel_TrancheQualityORMBSIssueStructure = new JPanel();
		GridBagConstraints gbc_panel_TrancheQualityORMBSIssueStructure = new GridBagConstraints();
		gbc_panel_TrancheQualityORMBSIssueStructure.gridx = 1;
		gbc_panel_TrancheQualityORMBSIssueStructure.gridy = 21;
		panel_economyConfig.add(panel_TrancheQualityORMBSIssueStructure,
				gbc_panel_TrancheQualityORMBSIssueStructure);
		GridBagLayout gbl_panel_TrancheQualityORMBSIssueStructure = new GridBagLayout();
		gbl_panel_TrancheQualityORMBSIssueStructure.columnWidths = new int[] {
				54, 54, 54 };
		gbl_panel_TrancheQualityORMBSIssueStructure.rowHeights = new int[] { 14 };
		gbl_panel_TrancheQualityORMBSIssueStructure.columnWeights = new double[] {
				0.0, 0.0, 0.0 };
		gbl_panel_TrancheQualityORMBSIssueStructure.rowWeights = new double[] { 0.0 };
		panel_TrancheQualityORMBSIssueStructure
				.setLayout(gbl_panel_TrancheQualityORMBSIssueStructure);

		JComboBox comboBox_SeniorTrancheQuality = new JComboBox();
		comboBox_SeniorTrancheQuality.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		GridBagConstraints gbc_comboBox_SeniorTrancheQuality = new GridBagConstraints();
		gbc_comboBox_SeniorTrancheQuality.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_SeniorTrancheQuality.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_SeniorTrancheQuality.gridx = 0;
		gbc_comboBox_SeniorTrancheQuality.gridy = 0;
		panel_TrancheQualityORMBSIssueStructure.add(
				comboBox_SeniorTrancheQuality,
				gbc_comboBox_SeniorTrancheQuality);
		comboBox_SeniorTrancheQuality.setModel(new DefaultComboBoxModel(
				new String[] { "AAA", "AA", "A", "BBB", "BBB-", "BB", "BB1",
						"BB2", "BB3", "BB4", "BB-", "CCC", "CCC-" }));
		comboBox_SeniorTrancheQuality.setSelectedIndex(1);
		String cmbType = (String) comboBox_SeniorTrancheQuality
				.getSelectedItem();
		setSeniorTrancheCouponAndDefault(comboBox_SeniorTrancheQuality
				.getSelectedIndex());
		comboBox_SeniorTrancheQuality.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setSeniorTrancheQualityString(cmbType);
				setSeniorTrancheCouponAndDefault(jcmbType.getSelectedIndex());
			}
		});

		JComboBox comboBox_MezzTrancheQuality = new JComboBox();
		comboBox_MezzTrancheQuality
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_comboBox_MezzTrancheQuality = new GridBagConstraints();
		gbc_comboBox_MezzTrancheQuality.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_MezzTrancheQuality.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_MezzTrancheQuality.gridx = 1;
		gbc_comboBox_MezzTrancheQuality.gridy = 0;
		panel_TrancheQualityORMBSIssueStructure.add(
				comboBox_MezzTrancheQuality, gbc_comboBox_MezzTrancheQuality);
		comboBox_MezzTrancheQuality.setModel(new DefaultComboBoxModel(
				new String[] { "AAA", "AA", "A", "BBB", "BBB-", "BB", "BB1",
						"BB2", "BB3", "BB4", "BB-", "CCC", "CCC-" }));
		comboBox_MezzTrancheQuality.setSelectedIndex(5);
		String cmbType1 = (String) comboBox_MezzTrancheQuality
				.getSelectedItem();
		setMezzTrancheCouponAndDefault(comboBox_MezzTrancheQuality
				.getSelectedIndex());
		comboBox_MezzTrancheQuality.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setMezzTrancheQualityString(cmbType);
				setMezzTrancheCouponAndDefault(jcmbType.getSelectedIndex());
			}
		});

		JComboBox comboBox_JuniorTrancheQuality = new JComboBox();
		comboBox_JuniorTrancheQuality.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		GridBagConstraints gbc_comboBox_JuniorTrancheQuality = new GridBagConstraints();
		gbc_comboBox_JuniorTrancheQuality.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_JuniorTrancheQuality.gridx = 2;
		gbc_comboBox_JuniorTrancheQuality.gridy = 0;
		panel_TrancheQualityORMBSIssueStructure.add(
				comboBox_JuniorTrancheQuality,
				gbc_comboBox_JuniorTrancheQuality);
		comboBox_JuniorTrancheQuality.setModel(new DefaultComboBoxModel(
				new String[] { "AAA", "AA", "A", "BBB", "BBB-", "BB", "BB1",
						"BB2", "BB3", "BB4", "BB-", "CCC", "CCC-" }));
		comboBox_JuniorTrancheQuality.setSelectedIndex(10);
		String cmbType2 = (String) comboBox_JuniorTrancheQuality
				.getSelectedItem();
		// System.out.println(cmbType2);
		setJuniorTrancheCouponAndDefault(comboBox_JuniorTrancheQuality
				.getSelectedIndex());
		comboBox_JuniorTrancheQuality.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setJuniorTrancheQualityString(cmbType);
				setJuniorTrancheCouponAndDefault(jcmbType.getSelectedIndex());
			}
		});
		// System.out.println(cmbType);
		setSeniorTrancheQualityString(cmbType);
		// System.out.println(cmbType1);
		setMezzTrancheQualityString(cmbType1);
		setJuniorTrancheQualityString(cmbType2);

		/**
		 * The following section of code add the mouse action listener to open
		 * the new pop-up window that plots the relationship between MBS coupons
		 * and MBS default rates
		 */
		lblCitibankResearchMbs.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				// ...
				ArrayList defaultList = new ArrayList<String>(Arrays
						.asList(textField_CitiBankDefaultRates.getText().split(
								"\\s*,\\s*")));

				citibankResearchMBSDefaultsList = new ArrayList<Double>();
				for (int i = 0; i < defaultList.size(); i++) {
					citibankResearchMBSDefaultsList.add(Double
							.parseDouble((String) defaultList.get(i)));
				}

				ArrayList couponsList = new ArrayList<String>(Arrays
						.asList(textField_CitiBankCoupons.getText().split(
								"\\s*,\\s*")));

				citibankResearchMBSCouponsList = new ArrayList<Double>();
				for (int i = 0; i < couponsList.size(); i++) {
					citibankResearchMBSCouponsList.add(Double
							.parseDouble((String) couponsList.get(i)));
				}

				JFrame relCouponDefaultframe = new JFrame();
				relCouponDefaultframe.setIconImage(Toolkit.getDefaultToolkit()
						.getImage(
								(CRTApplicationFrame.class
										.getResource("/jas/images/graph.gif"))));
				setTitle("Relationship Bewteen Coupon and Default Rates");
				Component glassPane = relCouponDefaultframe.getGlassPane();
				relCouponDefaultframe.setBackground(Color.BLACK);
				relCouponDefaultframe.setSize(new Dimension(497, 497));
				relCouponDefaultframe.setVisible(true);
				panel_popOutRelCouponDefaultChartPanel = new ChartPanel(
						getCouponDefaultChartPopOut());
				panel_popOutRelCouponDefaultChartPanel
						.setLayout(new GridBagLayout());
				panel_popOutRelCouponDefaultChartPanel.setBounds(new Rectangle(
						0, 0, 140, 140));
				relCouponDefaultframe
						.setContentPane(panel_popOutRelCouponDefaultChartPanel);
				updatePopOutCouponDefaultChart(citibankResearchMBSDefaultsList,
						citibankResearchMBSCouponsList);
			}
		});

		/**
		 * The following section of code add the mouse action listener to open
		 * the new pop-up window that plots the relationship between MBS coupons
		 * and MBS default rates
		 */
		lblMbsDefaultRates.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evt) {
				// ...
				ArrayList defaultList = new ArrayList<String>(Arrays
						.asList(textField_CitiBankDefaultRates.getText().split(
								"\\s*,\\s*")));

				citibankResearchMBSDefaultsList = new ArrayList<Double>();
				for (int i = 0; i < defaultList.size(); i++) {
					citibankResearchMBSDefaultsList.add(Double
							.parseDouble((String) defaultList.get(i)));
				}

				ArrayList couponsList = new ArrayList<String>(Arrays
						.asList(textField_CitiBankCoupons.getText().split(
								"\\s*,\\s*")));

				citibankResearchMBSCouponsList = new ArrayList<Double>();
				for (int i = 0; i < couponsList.size(); i++) {
					citibankResearchMBSCouponsList.add(Double
							.parseDouble((String) couponsList.get(i)));
				}

				JFrame relCouponDefaultframe = new JFrame();
				relCouponDefaultframe.setIconImage(Toolkit.getDefaultToolkit()
						.getImage(
								(CRTApplicationFrame.class
										.getResource("/jas/images/graph.gif"))));
				setTitle("Relationship Bewteen Coupon and Default Rates");
				Component glassPane = relCouponDefaultframe.getGlassPane();
				relCouponDefaultframe.setBackground(Color.BLACK);
				relCouponDefaultframe.setSize(new Dimension(497, 497));
				relCouponDefaultframe.setVisible(true);
				panel_popOutRelCouponDefaultChartPanel = new ChartPanel(
						getCouponDefaultChartPopOut());
				panel_popOutRelCouponDefaultChartPanel
						.setLayout(new GridBagLayout());
				panel_popOutRelCouponDefaultChartPanel.setBounds(new Rectangle(
						0, 0, 140, 140));
				relCouponDefaultframe
						.setContentPane(panel_popOutRelCouponDefaultChartPanel);
				updatePopOutCouponDefaultChart(citibankResearchMBSDefaultsList,
						citibankResearchMBSCouponsList);

			}
		});

		JPanel panel_EnvironmentConfig = new JPanel();
		panel_EnvironmentConfig.setBorder(new TitledBorder(null,
				"Model Environment Configuration", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_EnvironmentConfig = new GridBagConstraints();
		gbc_panel_EnvironmentConfig.insets = new Insets(0, 0, 5, 0);
		gbc_panel_EnvironmentConfig.fill = GridBagConstraints.VERTICAL;
		gbc_panel_EnvironmentConfig.gridx = 0;
		gbc_panel_EnvironmentConfig.gridy = 1;
		panel_ModelConfig.add(panel_EnvironmentConfig,
				gbc_panel_EnvironmentConfig);
		GridBagLayout gbl_panel_EnvironmentConfig = new GridBagLayout();
		gbl_panel_EnvironmentConfig.columnWidths = new int[] { 391, 0 };
		gbl_panel_EnvironmentConfig.rowHeights = new int[] { 198, 0 };
		gbl_panel_EnvironmentConfig.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_panel_EnvironmentConfig.rowWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		panel_EnvironmentConfig.setLayout(gbl_panel_EnvironmentConfig);

		JPanel panel_environmentConfig = new JPanel();
		panel_environmentConfig.setBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_panel_environmentConfig = new GridBagConstraints();
		gbc_panel_environmentConfig.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_environmentConfig.gridx = 0;
		gbc_panel_environmentConfig.gridy = 0;
		panel_EnvironmentConfig.add(panel_environmentConfig,
				gbc_panel_environmentConfig);
		GridBagLayout gbl_panel_environmentConfig = new GridBagLayout();
		gbl_panel_environmentConfig.columnWidths = new int[] { 214, 56, 0 };
		gbl_panel_environmentConfig.rowHeights = new int[] { 0, 0, 0, 0, 0, 0,
				16, 0, 0 };
		gbl_panel_environmentConfig.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel_environmentConfig.rowWeights = new double[] { 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_environmentConfig.setLayout(gbl_panel_environmentConfig);

		JLabel lblPaymentSchedule = new JLabel("Payment Schedule:");
		lblPaymentSchedule.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblPaymentSchedule = new GridBagConstraints();
		gbc_lblPaymentSchedule.anchor = GridBagConstraints.WEST;
		gbc_lblPaymentSchedule.insets = new Insets(0, 0, 5, 5);
		gbc_lblPaymentSchedule.gridx = 0;
		gbc_lblPaymentSchedule.gridy = 0;
		panel_environmentConfig.add(lblPaymentSchedule, gbc_lblPaymentSchedule);

		JComboBox comboBox_PaymentSchedule = new JComboBox();
		comboBox_PaymentSchedule.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_PaymentSchedule.setModel(new DefaultComboBoxModel(
				new String[] { "monthly", "weekly", "bi_weekly", "quarterly",
						"semi_annually", "annually" }));
		comboBox_PaymentSchedule.setSelectedIndex(0);
		String chosenName = (String) comboBox_PaymentSchedule.getSelectedItem();
		comboBox_PaymentSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setPaymentScheduleString(cmbType);
			}
		});
		GridBagConstraints gbc_comboBox_PaymentSchedule = new GridBagConstraints();
		gbc_comboBox_PaymentSchedule.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_PaymentSchedule.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_PaymentSchedule.gridx = 1;
		gbc_comboBox_PaymentSchedule.gridy = 0;
		panel_environmentConfig.add(comboBox_PaymentSchedule,
				gbc_comboBox_PaymentSchedule);

		JLabel lblModelDataPeriod = new JLabel("Model Data Period Range:");
		lblModelDataPeriod.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblModelDataPeriod = new GridBagConstraints();
		gbc_lblModelDataPeriod.anchor = GridBagConstraints.WEST;
		gbc_lblModelDataPeriod.insets = new Insets(0, 0, 5, 5);
		gbc_lblModelDataPeriod.gridx = 0;
		gbc_lblModelDataPeriod.gridy = 1;
		panel_environmentConfig.add(lblModelDataPeriod, gbc_lblModelDataPeriod);

		JComboBox comboBox_ModelDataPeriodRange = new JComboBox();
		comboBox_ModelDataPeriodRange.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		comboBox_ModelDataPeriodRange.setModel(new DefaultComboBoxModel(
				new String[] { "2002-2009", "2002-2005", "2006-2009" }));
		comboBox_ModelDataPeriodRange.setSelectedIndex(0);
		String chosenName1 = (String) comboBox_ModelDataPeriodRange
				.getSelectedItem();

		comboBox_ModelDataPeriodRange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setModelPeriodString(cmbType);
			}
		});
		GridBagConstraints gbc_comboBox_ModelDataPeriodRange = new GridBagConstraints();
		gbc_comboBox_ModelDataPeriodRange.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_ModelDataPeriodRange.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_ModelDataPeriodRange.gridx = 1;
		gbc_comboBox_ModelDataPeriodRange.gridy = 1;
		panel_environmentConfig.add(comboBox_ModelDataPeriodRange,
				gbc_comboBox_ModelDataPeriodRange);

		JLabel lblEnableShortSelling = new JLabel("Enable Short Selling:");
		lblEnableShortSelling.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblEnableShortSelling = new GridBagConstraints();
		gbc_lblEnableShortSelling.anchor = GridBagConstraints.WEST;
		gbc_lblEnableShortSelling.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnableShortSelling.gridx = 0;
		gbc_lblEnableShortSelling.gridy = 2;
		panel_environmentConfig.add(lblEnableShortSelling,
				gbc_lblEnableShortSelling);

		JComboBox comboBox_EnableShortSelling = new JComboBox();
		comboBox_EnableShortSelling
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_EnableShortSelling.setModel(new DefaultComboBoxModel(
				new String[] { "false", "true" }));
		comboBox_EnableShortSelling.setSelectedIndex(0);
		String chosenName2 = (String) comboBox_EnableShortSelling
				.getSelectedItem();
		comboBox_EnableShortSelling.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setShortSellingString(cmbType);
			}
		});
		GridBagConstraints gbc_comboBox_EnableShortSelling = new GridBagConstraints();
		gbc_comboBox_EnableShortSelling.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_EnableShortSelling.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_EnableShortSelling.gridx = 1;
		gbc_comboBox_EnableShortSelling.gridy = 2;
		panel_environmentConfig.add(comboBox_EnableShortSelling,
				gbc_comboBox_EnableShortSelling);

		JLabel lblUserDefinedData = new JLabel("User Defined Data Input:");
		lblUserDefinedData.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblUserDefinedData.setToolTipText("User can enter data values");
		GridBagConstraints gbc_lblUserDefinedData = new GridBagConstraints();
		gbc_lblUserDefinedData.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserDefinedData.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblUserDefinedData.gridx = 0;
		gbc_lblUserDefinedData.gridy = 3;
		panel_environmentConfig.add(lblUserDefinedData, gbc_lblUserDefinedData);

		JComboBox comboBox_UserDefinedData = new JComboBox();
		comboBox_UserDefinedData.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_UserDefinedData.setModel(new DefaultComboBoxModel(
				new String[] { "true", "false" }));
		comboBox_UserDefinedData.setSelectedIndex(0);
		String chosenName3 = (String) comboBox_UserDefinedData
				.getSelectedItem();
		comboBox_UserDefinedData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setUserAssetLiabilityDataInpuString(cmbType);
			}
		});
		GridBagConstraints gbc_comboBox_UserDefinedData = new GridBagConstraints();
		gbc_comboBox_UserDefinedData.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_UserDefinedData.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_UserDefinedData.gridx = 1;
		gbc_comboBox_UserDefinedData.gridy = 3;
		panel_environmentConfig.add(comboBox_UserDefinedData,
				gbc_comboBox_UserDefinedData);

		JLabel lblUserDefinedFund = new JLabel("User Defined Fund Assets:");
		lblUserDefinedFund.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblUserDefinedFund
				.setToolTipText("Enter the total assets of the institutional investor sector");
		GridBagConstraints gbc_lblUserDefinedFund = new GridBagConstraints();
		gbc_lblUserDefinedFund.anchor = GridBagConstraints.WEST;
		gbc_lblUserDefinedFund.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserDefinedFund.gridx = 0;
		gbc_lblUserDefinedFund.gridy = 4;
		panel_environmentConfig.add(lblUserDefinedFund, gbc_lblUserDefinedFund);

		textField_UserDefinedFundAssets = new JTextField();
		textField_UserDefinedFundAssets.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_UserDefinedFundAssets
				.setToolTipText("Enter the total assets of the institutional investor sector");
		textField_UserDefinedFundAssets.setText("288");
		GridBagConstraints gbc_textField_UserDefinedFundAssets = new GridBagConstraints();
		gbc_textField_UserDefinedFundAssets.insets = new Insets(0, 0, 5, 0);
		gbc_textField_UserDefinedFundAssets.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_UserDefinedFundAssets.gridx = 1;
		gbc_textField_UserDefinedFundAssets.gridy = 4;
		panel_environmentConfig.add(textField_UserDefinedFundAssets,
				gbc_textField_UserDefinedFundAssets);
		textField_UserDefinedFundAssets.setColumns(10);

		JLabel lblUserDefinedFund_1 = new JLabel(
				"User Defined Fund Liabilities:");
		lblUserDefinedFund_1.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblUserDefinedFund_1
				.setToolTipText("Enter the total liabilities of the institutional investor sector");
		GridBagConstraints gbc_lblUserDefinedFund_1 = new GridBagConstraints();
		gbc_lblUserDefinedFund_1.anchor = GridBagConstraints.WEST;
		gbc_lblUserDefinedFund_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserDefinedFund_1.gridx = 0;
		gbc_lblUserDefinedFund_1.gridy = 5;
		panel_environmentConfig.add(lblUserDefinedFund_1,
				gbc_lblUserDefinedFund_1);

		textField_UserDefinedFundLiabilities = new JTextField();
		textField_UserDefinedFundLiabilities.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_UserDefinedFundLiabilities
				.setToolTipText("Enter the total liabilities of the institutional investor sector");
		textField_UserDefinedFundLiabilities.setText("288");
		GridBagConstraints gbc_textField_UserDefinedFundLiabilities = new GridBagConstraints();
		gbc_textField_UserDefinedFundLiabilities.insets = new Insets(0, 0, 5, 0);
		gbc_textField_UserDefinedFundLiabilities.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_UserDefinedFundLiabilities.gridx = 1;
		gbc_textField_UserDefinedFundLiabilities.gridy = 5;
		panel_environmentConfig.add(textField_UserDefinedFundLiabilities,
				gbc_textField_UserDefinedFundLiabilities);
		textField_UserDefinedFundLiabilities.setColumns(10);

		JLabel lblUserDefinedBank = new JLabel("User Defined Bank Assets:");
		lblUserDefinedBank.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblUserDefinedBank
				.setToolTipText("Enter the total assets of the banking sector");
		GridBagConstraints gbc_lblUserDefinedBank = new GridBagConstraints();
		gbc_lblUserDefinedBank.anchor = GridBagConstraints.WEST;
		gbc_lblUserDefinedBank.insets = new Insets(0, 0, 5, 5);
		gbc_lblUserDefinedBank.gridx = 0;
		gbc_lblUserDefinedBank.gridy = 6;
		panel_environmentConfig.add(lblUserDefinedBank, gbc_lblUserDefinedBank);

		textField_UserDefinedBankAssets = new JTextField();
		textField_UserDefinedBankAssets.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_UserDefinedBankAssets
				.setToolTipText("Enter the total assets of the banking sector");
		textField_UserDefinedBankAssets.setText("231");
		GridBagConstraints gbc_textField_UserDefinedBankAssets = new GridBagConstraints();
		gbc_textField_UserDefinedBankAssets.insets = new Insets(0, 0, 5, 0);
		gbc_textField_UserDefinedBankAssets.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_UserDefinedBankAssets.gridx = 1;
		gbc_textField_UserDefinedBankAssets.gridy = 6;
		panel_environmentConfig.add(textField_UserDefinedBankAssets,
				gbc_textField_UserDefinedBankAssets);
		textField_UserDefinedBankAssets.setColumns(10);

		JLabel label_UserDefinedBankLiabilities = new JLabel(
				"User Defined Bank Liabilities:");
		label_UserDefinedBankLiabilities.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		label_UserDefinedBankLiabilities
				.setToolTipText("Enter the total liabilities of the banking sector");
		GridBagConstraints gbc_label_UserDefinedBankLiabilities = new GridBagConstraints();
		gbc_label_UserDefinedBankLiabilities.anchor = GridBagConstraints.WEST;
		gbc_label_UserDefinedBankLiabilities.insets = new Insets(0, 0, 0, 5);
		gbc_label_UserDefinedBankLiabilities.gridx = 0;
		gbc_label_UserDefinedBankLiabilities.gridy = 7;
		panel_environmentConfig.add(label_UserDefinedBankLiabilities,
				gbc_label_UserDefinedBankLiabilities);

		textField_UserDefinedBankLiabilities = new JTextField();
		textField_UserDefinedBankLiabilities.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_UserDefinedBankLiabilities
				.setToolTipText("Enter the total liabilities of the banking sector");
		textField_UserDefinedBankLiabilities.setText("212.56");
		GridBagConstraints gbc_textField_UserDefinedBankLiabilities = new GridBagConstraints();
		gbc_textField_UserDefinedBankLiabilities.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_UserDefinedBankLiabilities.gridx = 1;
		gbc_textField_UserDefinedBankLiabilities.gridy = 7;
		panel_environmentConfig.add(textField_UserDefinedBankLiabilities,
				gbc_textField_UserDefinedBankLiabilities);
		textField_UserDefinedBankLiabilities.setColumns(10);

		JPanel panel_AgentsConfig = new JPanel();
		application_main_panel.add(panel_AgentsConfig, BorderLayout.WEST);
		GridBagLayout gbl_panel_AgentsConfig = new GridBagLayout();
		gbl_panel_AgentsConfig.columnWidths = new int[] { 346, 0 };
		gbl_panel_AgentsConfig.rowHeights = new int[] { 263, 0, 100, 0 };
		gbl_panel_AgentsConfig.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_panel_AgentsConfig.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		panel_AgentsConfig.setLayout(gbl_panel_AgentsConfig);

		JPanel panel_BanksConfig = new JPanel();
		panel_BanksConfig.setBorder(new TitledBorder(null,
				"Banks Configuration", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		GridBagConstraints gbc_panel_BanksConfig = new GridBagConstraints();
		gbc_panel_BanksConfig.fill = GridBagConstraints.VERTICAL;
		gbc_panel_BanksConfig.gridx = 0;
		gbc_panel_BanksConfig.gridy = 0;
		panel_AgentsConfig.add(panel_BanksConfig, gbc_panel_BanksConfig);
		GridBagLayout gbl_panel_BanksConfig = new GridBagLayout();
		gbl_panel_BanksConfig.columnWidths = new int[] { 336, 0 };
		gbl_panel_BanksConfig.rowHeights = new int[] { 263, 0 };
		gbl_panel_BanksConfig.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_panel_BanksConfig.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_BanksConfig.setLayout(gbl_panel_BanksConfig);

		JPanel panel_banks_config = new JPanel();
		GridBagConstraints gbc_panel_banks_config = new GridBagConstraints();
		gbc_panel_banks_config.fill = GridBagConstraints.VERTICAL;
		gbc_panel_banks_config.gridx = 0;
		gbc_panel_banks_config.gridy = 0;
		panel_BanksConfig.add(panel_banks_config, gbc_panel_banks_config);
		panel_banks_config.setBorder(new EtchedBorder(EtchedBorder.RAISED,
				null, null));
		GridBagLayout gbl_panel_banks_config = new GridBagLayout();
		gbl_panel_banks_config.columnWidths = new int[] { 205, 186 };
		gbl_panel_banks_config.rowHeights = new int[] { 21, 21, 21, 21, 21, 21,
				21, 21, 21, 0 };
		gbl_panel_banks_config.columnWeights = new double[] { 1.0, 1.0 };
		gbl_panel_banks_config.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panel_banks_config.setLayout(gbl_panel_banks_config);

		JLabel label_BanksOnlyModel = new JLabel("Banks Only Model:");
		label_BanksOnlyModel.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_label_BanksOnlyModel = new GridBagConstraints();
		gbc_label_BanksOnlyModel.fill = GridBagConstraints.BOTH;
		gbc_label_BanksOnlyModel.insets = new Insets(0, 0, 5, 5);
		gbc_label_BanksOnlyModel.gridx = 0;
		gbc_label_BanksOnlyModel.gridy = 0;
		panel_banks_config.add(label_BanksOnlyModel, gbc_label_BanksOnlyModel);

		JComboBox comboBox_BanksOnlyModel = new JComboBox();
		comboBox_BanksOnlyModel.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_BanksOnlyModel.setModel(new DefaultComboBoxModel(new String[] {
				"false", "true" }));
		comboBox_BanksOnlyModel
				.setToolTipText("Select if simulation looks only at banks or entire economy");
		comboBox_BanksOnlyModel.setSelectedIndex(0);
		String chosenName4 = (String) comboBox_BanksOnlyModel.getSelectedItem();
		setBnksOnlyAnalysisString(chosenName4);
		comboBox_BanksOnlyModel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setBnksOnlyAnalysisString(cmbType);
			}
		});

		setPaymentScheduleString(chosenName);
		setModelPeriodString(chosenName1);
		setShortSellingString(chosenName2);
		setUserAssetLiabilityDataInpuString(chosenName3);

		GridBagConstraints gbc_comboBox_BanksOnlyModel = new GridBagConstraints();
		gbc_comboBox_BanksOnlyModel.anchor = GridBagConstraints.WEST;
		gbc_comboBox_BanksOnlyModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_BanksOnlyModel.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_BanksOnlyModel.gridx = 1;
		gbc_comboBox_BanksOnlyModel.gridy = 0;
		panel_banks_config.add(comboBox_BanksOnlyModel,
				gbc_comboBox_BanksOnlyModel);

		JLabel lblNumberOfBanks = new JLabel("Number of Banks:");
		lblNumberOfBanks.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblNumberOfBanks.setToolTipText("Number of Banks");
		GridBagConstraints gbc_lblNumberOfBanks = new GridBagConstraints();
		gbc_lblNumberOfBanks.anchor = GridBagConstraints.WEST;
		gbc_lblNumberOfBanks.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfBanks.gridx = 0;
		gbc_lblNumberOfBanks.gridy = 1;
		panel_banks_config.add(lblNumberOfBanks, gbc_lblNumberOfBanks);

		JComboBox comboBox_NumberOfBanks = new JComboBox();
		comboBox_NumberOfBanks.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_NumberOfBanks.setToolTipText("Select number of banks");
		comboBox_NumberOfBanks.setModel(new DefaultComboBoxModel(new String[] {
				"35", "97" }));
		comboBox_NumberOfBanks.setSelectedIndex(0);
		String chosenName5 = (String) comboBox_NumberOfBanks.getSelectedItem();
		setBankCount(chosenName5);
		comboBox_NumberOfBanks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setBankCount(cmbType);
			}
		});
		GridBagConstraints gbc_comboBox_NumberOfBanks = new GridBagConstraints();
		gbc_comboBox_NumberOfBanks.anchor = GridBagConstraints.WEST;
		gbc_comboBox_NumberOfBanks.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_NumberOfBanks.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_NumberOfBanks.gridx = 1;
		gbc_comboBox_NumberOfBanks.gridy = 1;
		panel_banks_config.add(comboBox_NumberOfBanks,
				gbc_comboBox_NumberOfBanks);

		JLabel lblIdenticalInterestRate = new JLabel(
				"Identical Interest Rate Spreads:");
		lblIdenticalInterestRate.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblIdenticalInterestRate = new GridBagConstraints();
		gbc_lblIdenticalInterestRate.anchor = GridBagConstraints.WEST;
		gbc_lblIdenticalInterestRate.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdenticalInterestRate.gridx = 0;
		gbc_lblIdenticalInterestRate.gridy = 2;
		panel_banks_config.add(lblIdenticalInterestRate,
				gbc_lblIdenticalInterestRate);

		JComboBox comboBox_IdenticalInterestRateSpreads = new JComboBox();
		comboBox_IdenticalInterestRateSpreads.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		comboBox_IdenticalInterestRateSpreads
				.setToolTipText("Interest rate spread experiment");
		comboBox_IdenticalInterestRateSpreads
				.setModel(new DefaultComboBoxModel(new String[] { "true",
						"false" }));
		comboBox_IdenticalInterestRateSpreads.setSelectedIndex(0);
		String chosenName6 = (String) comboBox_IdenticalInterestRateSpreads
				.getSelectedItem();
		setInterestSpreadExperimentString(chosenName6);
		comboBox_IdenticalInterestRateSpreads
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox jcmbType = (JComboBox) e.getSource();
						String cmbType = (String) jcmbType.getSelectedItem();
						setInterestSpreadExperimentString(cmbType);
					}
				});

		GridBagConstraints gbc_comboBox_IdenticalInterestRateSpreads = new GridBagConstraints();
		gbc_comboBox_IdenticalInterestRateSpreads.anchor = GridBagConstraints.WEST;
		gbc_comboBox_IdenticalInterestRateSpreads.insets = new Insets(0, 0, 5,
				0);
		gbc_comboBox_IdenticalInterestRateSpreads.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_IdenticalInterestRateSpreads.gridx = 1;
		gbc_comboBox_IdenticalInterestRateSpreads.gridy = 2;
		panel_banks_config.add(comboBox_IdenticalInterestRateSpreads,
				gbc_comboBox_IdenticalInterestRateSpreads);

		JLabel label_EnableSecuritisation = new JLabel("Enable Securitisation:");
		label_EnableSecuritisation
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_label_EnableSecuritisation = new GridBagConstraints();
		gbc_label_EnableSecuritisation.fill = GridBagConstraints.BOTH;
		gbc_label_EnableSecuritisation.insets = new Insets(0, 0, 5, 5);
		gbc_label_EnableSecuritisation.gridx = 0;
		gbc_label_EnableSecuritisation.gridy = 3;
		panel_banks_config.add(label_EnableSecuritisation,
				gbc_label_EnableSecuritisation);

		JComboBox comboBox_securitisation = new JComboBox();
		comboBox_securitisation.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_securitisation.setModel(new DefaultComboBoxModel(new String[] {
				"true", "false" }));
		comboBox_securitisation.setSelectedIndex(0);
		String chosenName7 = (String) comboBox_securitisation.getSelectedItem();
		setSecuritiseString(chosenName7);
		comboBox_securitisation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setSecuritiseString(cmbType);
			}
		});

		GridBagConstraints gbc_comboBox_securitisation = new GridBagConstraints();
		gbc_comboBox_securitisation.anchor = GridBagConstraints.WEST;
		gbc_comboBox_securitisation.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_securitisation.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_securitisation.gridx = 1;
		gbc_comboBox_securitisation.gridy = 3;
		panel_banks_config.add(comboBox_securitisation,
				gbc_comboBox_securitisation);

		JLabel label_InternalSecuritisationChoice = new JLabel(
				"Internal Securitisation Choice:");
		label_InternalSecuritisationChoice.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		label_InternalSecuritisationChoice
				.setToolTipText("Choose if banks internalise securitisation decision or user defined securitisation rate");
		GridBagConstraints gbc_label_InternalSecuritisationChoice = new GridBagConstraints();
		gbc_label_InternalSecuritisationChoice.fill = GridBagConstraints.BOTH;
		gbc_label_InternalSecuritisationChoice.insets = new Insets(0, 0, 5, 5);
		gbc_label_InternalSecuritisationChoice.gridx = 0;
		gbc_label_InternalSecuritisationChoice.gridy = 4;
		panel_banks_config.add(label_InternalSecuritisationChoice,
				gbc_label_InternalSecuritisationChoice);

		JComboBox comboBox_InternalSecuritisation = new JComboBox();
		comboBox_InternalSecuritisation.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		comboBox_InternalSecuritisation.setModel(new DefaultComboBoxModel(
				new String[] { "true", "false" }));
		comboBox_InternalSecuritisation.setSelectedIndex(0);
		String chosenName8 = (String) comboBox_InternalSecuritisation
				.getSelectedItem();
		setInternalSecuritiseString(chosenName8);
		comboBox_InternalSecuritisation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setInternalSecuritiseString(cmbType);
			}
		});

		GridBagConstraints gbc_comboBox_InternalSecuritisation = new GridBagConstraints();
		gbc_comboBox_InternalSecuritisation.anchor = GridBagConstraints.WEST;
		gbc_comboBox_InternalSecuritisation.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_InternalSecuritisation.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_InternalSecuritisation.gridx = 1;
		gbc_comboBox_InternalSecuritisation.gridy = 4;
		panel_banks_config.add(comboBox_InternalSecuritisation,
				gbc_comboBox_InternalSecuritisation);

		JLabel label_EnableLinearSecuritisation = new JLabel(
				"Enable Linear Securitisation: ");
		label_EnableLinearSecuritisation.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		label_EnableLinearSecuritisation
				.setToolTipText("Choice between Linear vs non-linear securitisation");
		GridBagConstraints gbc_label_EnableLinearSecuritisation = new GridBagConstraints();
		gbc_label_EnableLinearSecuritisation.anchor = GridBagConstraints.WEST;
		gbc_label_EnableLinearSecuritisation.insets = new Insets(0, 0, 5, 5);
		gbc_label_EnableLinearSecuritisation.gridx = 0;
		gbc_label_EnableLinearSecuritisation.gridy = 5;
		panel_banks_config.add(label_EnableLinearSecuritisation,
				gbc_label_EnableLinearSecuritisation);

		JComboBox comboBox_LinearSecuritisation = new JComboBox();
		comboBox_LinearSecuritisation.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		comboBox_LinearSecuritisation.setModel(new DefaultComboBoxModel(
				new String[] { "false", "true" }));
		comboBox_LinearSecuritisation.setSelectedIndex(0);
		String chosenName9 = (String) comboBox_LinearSecuritisation
				.getSelectedItem();
		setSecuritiseLinearString(chosenName9);
		comboBox_LinearSecuritisation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setSecuritiseLinearString(cmbType);
			}
		});
		GridBagConstraints gbc_comboBox_LinearSecuritisation = new GridBagConstraints();
		gbc_comboBox_LinearSecuritisation.anchor = GridBagConstraints.WEST;
		gbc_comboBox_LinearSecuritisation.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_LinearSecuritisation.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_LinearSecuritisation.gridx = 1;
		gbc_comboBox_LinearSecuritisation.gridy = 5;
		panel_banks_config.add(comboBox_LinearSecuritisation,
				gbc_comboBox_LinearSecuritisation);

		JLabel label_RegulatoryCapitalRatio = new JLabel(
				"Regulatory Capital Ratio:");
		label_RegulatoryCapitalRatio.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		label_RegulatoryCapitalRatio
				.setToolTipText("Enter the Regulatory Capital Ratio");
		GridBagConstraints gbc_label_RegulatoryCapitalRatio = new GridBagConstraints();
		gbc_label_RegulatoryCapitalRatio.anchor = GridBagConstraints.WEST;
		gbc_label_RegulatoryCapitalRatio.insets = new Insets(0, 0, 5, 5);
		gbc_label_RegulatoryCapitalRatio.gridx = 0;
		gbc_label_RegulatoryCapitalRatio.gridy = 6;
		panel_banks_config.add(label_RegulatoryCapitalRatio,
				gbc_label_RegulatoryCapitalRatio);

		textField_RegulatoryCapitalRatio = new JTextField();
		textField_RegulatoryCapitalRatio.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_RegulatoryCapitalRatio
				.setToolTipText("Enter the regulatory capital ratio");
		textField_RegulatoryCapitalRatio.setText("0.08");
		GridBagConstraints gbc_textField_RegulatoryCapitalRatio = new GridBagConstraints();
		gbc_textField_RegulatoryCapitalRatio.anchor = GridBagConstraints.WEST;
		gbc_textField_RegulatoryCapitalRatio.insets = new Insets(0, 0, 5, 0);
		gbc_textField_RegulatoryCapitalRatio.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_RegulatoryCapitalRatio.gridx = 1;
		gbc_textField_RegulatoryCapitalRatio.gridy = 6;
		panel_banks_config.add(textField_RegulatoryCapitalRatio,
				gbc_textField_RegulatoryCapitalRatio);
		textField_RegulatoryCapitalRatio.setColumns(10);

		JLabel lblReturnOnBank = new JLabel("Return on Bank Assets:");
		lblReturnOnBank.setFont(new Font("SansSerif", Font.PLAIN, 9));
		lblReturnOnBank.setToolTipText("Ra: Return on bank assets");
		GridBagConstraints gbc_lblReturnOnBank = new GridBagConstraints();
		gbc_lblReturnOnBank.anchor = GridBagConstraints.WEST;
		gbc_lblReturnOnBank.insets = new Insets(0, 0, 5, 5);
		gbc_lblReturnOnBank.gridx = 0;
		gbc_lblReturnOnBank.gridy = 7;
		panel_banks_config.add(lblReturnOnBank, gbc_lblReturnOnBank);

		textField_ReturnOnBankAssets = new JTextField();
		textField_ReturnOnBankAssets.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		textField_ReturnOnBankAssets.setText("0.05");
		GridBagConstraints gbc_textField_ReturnOnBankAssets = new GridBagConstraints();
		gbc_textField_ReturnOnBankAssets.anchor = GridBagConstraints.WEST;
		gbc_textField_ReturnOnBankAssets.insets = new Insets(0, 0, 5, 0);
		gbc_textField_ReturnOnBankAssets.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_ReturnOnBankAssets.gridx = 1;
		gbc_textField_ReturnOnBankAssets.gridy = 7;
		panel_banks_config.add(textField_ReturnOnBankAssets,
				gbc_textField_ReturnOnBankAssets);
		textField_ReturnOnBankAssets.setColumns(10);

		JLabel lblReturnOnBank_1 = new JLabel("Return on Bank Liabilities:");
		lblReturnOnBank_1.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblReturnOnBank_1 = new GridBagConstraints();
		gbc_lblReturnOnBank_1.anchor = GridBagConstraints.WEST;
		gbc_lblReturnOnBank_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblReturnOnBank_1.gridx = 0;
		gbc_lblReturnOnBank_1.gridy = 8;
		panel_banks_config.add(lblReturnOnBank_1, gbc_lblReturnOnBank_1);

		textField_ReturnOnBankLiabilities = new JTextField();
		textField_ReturnOnBankLiabilities.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_ReturnOnBankLiabilities.setText("0.0252");
		GridBagConstraints gbc_textField_ReturnOnBankLiabilities = new GridBagConstraints();
		gbc_textField_ReturnOnBankLiabilities.insets = new Insets(0, 0, 5, 0);
		gbc_textField_ReturnOnBankLiabilities.anchor = GridBagConstraints.WEST;
		gbc_textField_ReturnOnBankLiabilities.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_ReturnOnBankLiabilities.gridx = 1;
		gbc_textField_ReturnOnBankLiabilities.gridy = 8;
		panel_banks_config.add(textField_ReturnOnBankLiabilities,
				gbc_textField_ReturnOnBankLiabilities);
		textField_ReturnOnBankLiabilities.setColumns(10);
		
		JLabel lblBanksSecuritisationDecision = new JLabel("Banks Securitisation Decision Model:");
		lblBanksSecuritisationDecision.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblBanksSecuritisationDecision = new GridBagConstraints();
		gbc_lblBanksSecuritisationDecision.anchor = GridBagConstraints.WEST;
		gbc_lblBanksSecuritisationDecision.insets = new Insets(0, 0, 0, 5);
		gbc_lblBanksSecuritisationDecision.gridx = 0;
		gbc_lblBanksSecuritisationDecision.gridy = 9;
		panel_banks_config.add(lblBanksSecuritisationDecision, gbc_lblBanksSecuritisationDecision);
		
		comboBox_BankType = new JComboBox();
		comboBox_BankType.setToolTipText("select if simulation is a standard bank securitisation model or heuristic securitisation model");
		comboBox_BankType.setModel(new DefaultComboBoxModel(new String[] {"Standard Model", "Erev-Roth RL", "Intermediate Heuristic "}));
		comboBox_BankType.setSelectedIndex(0);
		String BankType = (String) comboBox_BankType.getSelectedItem();
		int intBankType = comboBox_BankType.getSelectedIndex();
		if(comboBox_BankType.getSelectedIndex() == 0){
			setHeuristicSecuritisationModel(false);
		} else {
			setHeuristicSecuritisationModel(true);
		}
		setBankTypeString(BankType);
		setBankTypeIndex(comboBox_BankType.getSelectedIndex());
		comboBox_BankType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				int intBankType = jcmbType.getSelectedIndex();
				setBankTypeString(cmbType);
				setBankTypeIndex(intBankType);
				if (intBankType == 1) {
					setMDPErevRothSecuritisationModel(true);
					setHeuristicSecuritisationModel(false);
					try {
						MDPErevRothdialog
								.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						MDPErevRothdialog.setVisible(true);
					} catch (Exception exept) {
						exept.printStackTrace();
					}
				} else
				if (intBankType == 2) {
					setHeuristicSecuritisationModel(true);
					setMDPErevRothSecuritisationModel(false);
					try {
						MDPIntermediateHeuristicParameterDialog
								.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						MDPIntermediateHeuristicParameterDialog.setVisible(true);
					} catch (Exception exept) {
						exept.printStackTrace();
					}
				}
				else{
					setHeuristicSecuritisationModel(false);
					setMDPErevRothSecuritisationModel(false);
				}
			}
		});
		comboBox_BankType.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_comboBox_BankType = new GridBagConstraints();
		gbc_comboBox_BankType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_BankType.gridx = 1;
		gbc_comboBox_BankType.gridy = 9;
		panel_banks_config.add(comboBox_BankType, gbc_comboBox_BankType);

		JPanel panel_FundsConfig = new JPanel();
		panel_FundsConfig.setBorder(new TitledBorder(null,
				"Funds Configuration", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		GridBagConstraints gbc_panel_FundsConfig = new GridBagConstraints();
		gbc_panel_FundsConfig.fill = GridBagConstraints.VERTICAL;
		gbc_panel_FundsConfig.gridx = 0;
		gbc_panel_FundsConfig.gridy = 1;
		panel_AgentsConfig.add(panel_FundsConfig, gbc_panel_FundsConfig);
		GridBagLayout gbl_panel_FundsConfig = new GridBagLayout();
		gbl_panel_FundsConfig.columnWidths = new int[] { 389, 0 };
		gbl_panel_FundsConfig.rowHeights = new int[] { 192, 0 };
		gbl_panel_FundsConfig.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_panel_FundsConfig.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_FundsConfig.setLayout(gbl_panel_FundsConfig);

		JPanel panel_LAPF_Config = new JPanel();
		panel_LAPF_Config.setBorder(new EtchedBorder(EtchedBorder.LOWERED,
				null, null));
		GridBagConstraints gbc_panel_LAPF_Config = new GridBagConstraints();
		gbc_panel_LAPF_Config.fill = GridBagConstraints.VERTICAL;
		gbc_panel_LAPF_Config.gridx = 0;
		gbc_panel_LAPF_Config.gridy = 0;
		panel_FundsConfig.add(panel_LAPF_Config, gbc_panel_LAPF_Config);
		GridBagLayout gbl_panel_LAPF_Config = new GridBagLayout();
		gbl_panel_LAPF_Config.columnWidths = new int[] { 206, 188, 0 };
		gbl_panel_LAPF_Config.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		gbl_panel_LAPF_Config.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel_LAPF_Config.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_LAPF_Config.setLayout(gbl_panel_LAPF_Config);

		JLabel label_NumberOfFunds = new JLabel("Number of Funds:");
		label_NumberOfFunds.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_label_NumberOfFunds = new GridBagConstraints();
		gbc_label_NumberOfFunds.insets = new Insets(0, 0, 5, 5);
		gbc_label_NumberOfFunds.anchor = GridBagConstraints.WEST;
		gbc_label_NumberOfFunds.gridx = 0;
		gbc_label_NumberOfFunds.gridy = 0;
		panel_LAPF_Config.add(label_NumberOfFunds, gbc_label_NumberOfFunds);

		jComboBox_NumberOfFunds = new JComboBox();
		jComboBox_NumberOfFunds.setModel(new DefaultComboBoxModel(new String[] {
				"Composite", "FTSE350 Sample 220 Funds",
				"FTSE350 Sample of 10 Funds" }));
		jComboBox_NumberOfFunds.setSelectedIndex(0);
		setInvestorCountString(jComboBox_NumberOfFunds.getSelectedIndex());
		jComboBox_NumberOfFunds.setFont(new Font("SansSerif", Font.PLAIN, 9));
		jComboBox_NumberOfFunds.setToolTipText("Set the number of funds");
		jComboBox_NumberOfFunds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				int cmbFunds = jcmbType.getSelectedIndex();
				setInvestorCountString(cmbFunds);
			}
		});
		GridBagConstraints gbc_jComboBox_NumberOfFunds = new GridBagConstraints();
		gbc_jComboBox_NumberOfFunds.anchor = GridBagConstraints.WEST;
		gbc_jComboBox_NumberOfFunds.insets = new Insets(0, 0, 5, 0);
		gbc_jComboBox_NumberOfFunds.fill = GridBagConstraints.HORIZONTAL;
		gbc_jComboBox_NumberOfFunds.gridx = 1;
		gbc_jComboBox_NumberOfFunds.gridy = 0;
		panel_LAPF_Config.add(jComboBox_NumberOfFunds,
				gbc_jComboBox_NumberOfFunds);

		JLabel label_TraditionalAssetVariations = new JLabel(
				"Traditional Asset Variations:");
		label_TraditionalAssetVariations.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_label_TraditionalAssetVariations = new GridBagConstraints();
		gbc_label_TraditionalAssetVariations.anchor = GridBagConstraints.WEST;
		gbc_label_TraditionalAssetVariations.insets = new Insets(0, 0, 0, 5);
		gbc_label_TraditionalAssetVariations.gridx = 0;
		gbc_label_TraditionalAssetVariations.gridy = 9;
		panel_LAPF_Config.add(label_TraditionalAssetVariations,
				gbc_label_TraditionalAssetVariations);

		comboBox_TraditionalAssetVariations = new JComboBox();
		comboBox_TraditionalAssetVariations.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		comboBox_TraditionalAssetVariations.setModel(new DefaultComboBoxModel(
				new String[] { "CCFEA_SS2007", "SnPCAGR2002_09",
						"SnP10yrReturns", "EquityFundReturns2005_09",
						"constant", "stochastic", "transition_model" }));
		comboBox_TraditionalAssetVariations.setSelectedIndex(0);
		String chosenName14 = (String) comboBox_TraditionalAssetVariations
				.getSelectedItem();
		setTraditionalAssetVariationTypeString(chosenName14);
		comboBox_TraditionalAssetVariations
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox jcmbType = (JComboBox) e.getSource();
						String cmbType = (String) jcmbType.getSelectedItem();
						setTraditionalAssetVariationTypeString(cmbType);
					}
				});
		GridBagConstraints gbc_comboBox_TraditionalAssetVariations = new GridBagConstraints();
		gbc_comboBox_TraditionalAssetVariations.anchor = GridBagConstraints.WEST;
		gbc_comboBox_TraditionalAssetVariations.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_TraditionalAssetVariations.gridx = 1;
		gbc_comboBox_TraditionalAssetVariations.gridy = 9;
		panel_LAPF_Config.add(comboBox_TraditionalAssetVariations,
				gbc_comboBox_TraditionalAssetVariations);

		JLabel jLabel_FundsExpectations = new JLabel("Fund Expectations:");
		jLabel_FundsExpectations.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_FundsExpectations = new GridBagConstraints();
		gbc_jLabel_FundsExpectations.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_FundsExpectations.anchor = GridBagConstraints.WEST;
		gbc_jLabel_FundsExpectations.gridx = 0;
		gbc_jLabel_FundsExpectations.gridy = 2;
		panel_LAPF_Config.add(jLabel_FundsExpectations,
				gbc_jLabel_FundsExpectations);

		comboBox_FundsExpectations = new JComboBox();
		comboBox_FundsExpectations.setModel(new DefaultComboBoxModel(
				new String[] { "Bullish", "Bearish", "Passive",
						"Heterogeneous", "Rational" }));
		comboBox_FundsExpectations.setSelectedIndex(3);
		comboBox_FundsExpectations
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		String FundsExpectations = (String) comboBox_FundsExpectations
				.getSelectedItem();
		setFundsExpectationsString(FundsExpectations);
		int intLAPFExpectationsIndex = comboBox_FundsExpectations
				.getSelectedIndex();
		setFundsExpectationsString(FundsExpectations);
		setLAPFExpectationsIndex(intLAPFExpectationsIndex);
		comboBox_FundsExpectations.setEnabled(false);
		comboBox_FundsExpectations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				int intLAPFExpectationsIndex = comboBox_FundsExpectations
						.getSelectedIndex();
				setFundsExpectationsString(cmbType);
				setLAPFExpectationsIndex(intLAPFExpectationsIndex);
			}
		});

		GridBagConstraints gbc_comboBox_FundsExpectations = new GridBagConstraints();
		gbc_comboBox_FundsExpectations.anchor = GridBagConstraints.WEST;
		gbc_comboBox_FundsExpectations.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_FundsExpectations.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_FundsExpectations.gridx = 1;
		gbc_comboBox_FundsExpectations.gridy = 2;
		panel_LAPF_Config.add(comboBox_FundsExpectations,
				gbc_comboBox_FundsExpectations);

		JLabel jLabel_LAPFType = new JLabel("Fund Agent Type:");
		jLabel_LAPFType.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jLabel_LAPFType = new GridBagConstraints();
		gbc_jLabel_LAPFType.anchor = GridBagConstraints.WEST;
		gbc_jLabel_LAPFType.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel_LAPFType.gridx = 0;
		gbc_jLabel_LAPFType.gridy = 1;
		panel_LAPF_Config.add(jLabel_LAPFType, gbc_jLabel_LAPFType);

		JComboBox comboBox_LAPFType = new JComboBox();
		comboBox_LAPFType.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_LAPFType.setModel(new DefaultComboBoxModel(new String[] {
				"Simple Fund", "MDP Learning" }));
		comboBox_LAPFType.setSelectedIndex(0);
		String LAPFType = (String) comboBox_LAPFType.getSelectedItem();
		int intLAPFType = comboBox_LAPFType.getSelectedIndex();
		setLAPFTypeString(LAPFType);
		setLAPFTypeIndex(intLAPFType);
		comboBox_LAPFType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				int intLAPFType = jcmbType.getSelectedIndex();
				setLAPFTypeString(cmbType);
				setLAPFTypeIndex(intLAPFType);
				comboBox_FundsExpectations.setEnabled(true);

				if (intLAPFType == 0) {
					comboBox_FundsExpectations.setSelectedIndex(3);
					String FundsExpectations = (String) comboBox_FundsExpectations
							.getSelectedItem();
					int intLAPFExpectationsIndex = comboBox_FundsExpectations
							.getSelectedIndex();
					comboBox_FundsExpectations.setEnabled(false);
					setFundsExpectationsString(FundsExpectations);
					setLAPFExpectationsIndex(intLAPFExpectationsIndex);
				} else {
					comboBox_FundsExpectations.setEnabled(true);
					comboBox_TraditionalAssetVariations.setSelectedIndex(6);
					try {
						MDPParameterDialog
								.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						MDPParameterDialog.setVisible(true);
					} catch (Exception exept) {
						exept.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_comboBox_LAPFType = new GridBagConstraints();
		gbc_comboBox_LAPFType.anchor = GridBagConstraints.WEST;
		gbc_comboBox_LAPFType.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_LAPFType.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_LAPFType.gridx = 1;
		gbc_comboBox_LAPFType.gridy = 1;
		panel_LAPF_Config.add(comboBox_LAPFType, gbc_comboBox_LAPFType);

		JLabel jLabelFundExpectationsBias = new JLabel("Fund Expectations Bias");
		jLabelFundExpectationsBias
				.setToolTipText("Enter a bias towards the number of optimistic investors {range 0 to 1}");
		jLabelFundExpectationsBias
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jLabelFundExpectationsBias = new GridBagConstraints();
		gbc_jLabelFundExpectationsBias.anchor = GridBagConstraints.WEST;
		gbc_jLabelFundExpectationsBias.insets = new Insets(0, 0, 5, 5);
		gbc_jLabelFundExpectationsBias.gridx = 0;
		gbc_jLabelFundExpectationsBias.gridy = 3;
		panel_LAPF_Config.add(jLabelFundExpectationsBias,
				gbc_jLabelFundExpectationsBias);

		jTextField_FundExpectationsBias = new JTextField();
		jTextField_FundExpectationsBias
				.setToolTipText("Enter a bias towards the number of optimistic investors {range 0 to 1}");
		jTextField_FundExpectationsBias.setText("0.5");
		jTextField_FundExpectationsBias.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		jTextField_FundExpectationsBias.setColumns(10);
		GridBagConstraints gbc_jTextField_FundExpectationsBias = new GridBagConstraints();
		gbc_jTextField_FundExpectationsBias.insets = new Insets(0, 0, 5, 0);
		gbc_jTextField_FundExpectationsBias.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_FundExpectationsBias.gridx = 1;
		gbc_jTextField_FundExpectationsBias.gridy = 3;
		panel_LAPF_Config.add(jTextField_FundExpectationsBias,
				gbc_jTextField_FundExpectationsBias);

		JLabel label_MultiperiodSolvencyModel = new JLabel(
				"Multi-Period Solvency Model:");
		label_MultiperiodSolvencyModel.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_label_MultiperiodSolvencyModel = new GridBagConstraints();
		gbc_label_MultiperiodSolvencyModel.anchor = GridBagConstraints.WEST;
		gbc_label_MultiperiodSolvencyModel.insets = new Insets(0, 0, 5, 5);
		gbc_label_MultiperiodSolvencyModel.gridx = 0;
		gbc_label_MultiperiodSolvencyModel.gridy = 4;
		panel_LAPF_Config.add(label_MultiperiodSolvencyModel,
				gbc_label_MultiperiodSolvencyModel);

		JComboBox comboBox_MultiPeriodSolvencyModel = new JComboBox();
		comboBox_MultiPeriodSolvencyModel.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		comboBox_MultiPeriodSolvencyModel.setModel(new DefaultComboBoxModel(
				new String[] { "true", "false" }));
		comboBox_MultiPeriodSolvencyModel.setSelectedIndex(0);
		String chosenName10 = (String) comboBox_LinearSecuritisation
				.getSelectedItem();
		setLapfMultiPeriodSolvancyModelString(chosenName10);
		comboBox_MultiPeriodSolvencyModel
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox jcmbType = (JComboBox) e.getSource();
						String cmbType = (String) jcmbType.getSelectedItem();
						setLapfMultiPeriodSolvancyModelString(cmbType);
					}
				});
		GridBagConstraints gbc_comboBox_MultiPeriodSolvencyModel = new GridBagConstraints();
		gbc_comboBox_MultiPeriodSolvencyModel.anchor = GridBagConstraints.WEST;
		gbc_comboBox_MultiPeriodSolvencyModel.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_MultiPeriodSolvencyModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_MultiPeriodSolvencyModel.gridx = 1;
		gbc_comboBox_MultiPeriodSolvencyModel.gridy = 4;
		panel_LAPF_Config.add(comboBox_MultiPeriodSolvencyModel,
				gbc_comboBox_MultiPeriodSolvencyModel);

		JLabel label_QuadraticCostFunction = new JLabel(
				"Quadratic Cost Function:");
		label_QuadraticCostFunction
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_label_QuadraticCostFunction = new GridBagConstraints();
		gbc_label_QuadraticCostFunction.anchor = GridBagConstraints.WEST;
		gbc_label_QuadraticCostFunction.insets = new Insets(0, 0, 5, 5);
		gbc_label_QuadraticCostFunction.gridx = 0;
		gbc_label_QuadraticCostFunction.gridy = 5;
		panel_LAPF_Config.add(label_QuadraticCostFunction,
				gbc_label_QuadraticCostFunction);

		JComboBox comboBox_QuadraticCostFunction = new JComboBox();
		comboBox_QuadraticCostFunction.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		comboBox_QuadraticCostFunction.setModel(new DefaultComboBoxModel(
				new String[] { "true", "false" }));
		comboBox_QuadraticCostFunction.setSelectedIndex(0);
		String chosenName11 = (String) comboBox_QuadraticCostFunction
				.getSelectedItem();
		setLapfQuadraticCostFunctionString(chosenName11);
		comboBox_QuadraticCostFunction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setLapfQuadraticCostFunctionString(cmbType);
			}
		});
		GridBagConstraints gbc_comboBox_QuadraticCostFunction = new GridBagConstraints();
		gbc_comboBox_QuadraticCostFunction.anchor = GridBagConstraints.WEST;
		gbc_comboBox_QuadraticCostFunction.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_QuadraticCostFunction.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_QuadraticCostFunction.gridx = 1;
		gbc_comboBox_QuadraticCostFunction.gridy = 5;
		panel_LAPF_Config.add(comboBox_QuadraticCostFunction,
				gbc_comboBox_QuadraticCostFunction);

		JLabel label_ConstantContractualObligations = new JLabel(
				"Constant Contractual Obligations:");
		label_ConstantContractualObligations.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_label_ConstantContractualObligations = new GridBagConstraints();
		gbc_label_ConstantContractualObligations.anchor = GridBagConstraints.WEST;
		gbc_label_ConstantContractualObligations.insets = new Insets(0, 0, 5, 5);
		gbc_label_ConstantContractualObligations.gridx = 0;
		gbc_label_ConstantContractualObligations.gridy = 6;
		panel_LAPF_Config.add(label_ConstantContractualObligations,
				gbc_label_ConstantContractualObligations);

		JComboBox comboBox_ConstantContractualObligations = new JComboBox();
		comboBox_ConstantContractualObligations.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		comboBox_ConstantContractualObligations
				.setModel(new DefaultComboBoxModel(new String[] { "true",
						"false" }));
		comboBox_ConstantContractualObligations.setSelectedIndex(0);
		String chosenName12 = (String) comboBox_ConstantContractualObligations
				.getSelectedItem();
		setLapfConstantContractualObligationsString(chosenName12);
		comboBox_ConstantContractualObligations
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox jcmbType = (JComboBox) e.getSource();
						String cmbType = (String) jcmbType.getSelectedItem();
						setLapfConstantContractualObligationsString(cmbType);
					}
				});
		GridBagConstraints gbc_comboBox_ConstantContractualObligations = new GridBagConstraints();
		gbc_comboBox_ConstantContractualObligations.anchor = GridBagConstraints.WEST;
		gbc_comboBox_ConstantContractualObligations.insets = new Insets(0, 0,
				5, 0);
		gbc_comboBox_ConstantContractualObligations.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_ConstantContractualObligations.gridx = 1;
		gbc_comboBox_ConstantContractualObligations.gridy = 6;
		panel_LAPF_Config.add(comboBox_ConstantContractualObligations,
				gbc_comboBox_ConstantContractualObligations);

		JLabel label_SolvencyRatio = new JLabel("Solvency Ratio:");
		label_SolvencyRatio.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_label_SolvencyRatio = new GridBagConstraints();
		gbc_label_SolvencyRatio.anchor = GridBagConstraints.WEST;
		gbc_label_SolvencyRatio.insets = new Insets(0, 0, 5, 5);
		gbc_label_SolvencyRatio.gridx = 0;
		gbc_label_SolvencyRatio.gridy = 7;
		panel_LAPF_Config.add(label_SolvencyRatio, gbc_label_SolvencyRatio);

		JComboBox comboBox_SolvencyRatio = new JComboBox();
		comboBox_SolvencyRatio.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_SolvencyRatio.setModel(new DefaultComboBoxModel(new String[] {
				"0.17", "0.22", "0.30", "0.50" }));
		comboBox_SolvencyRatio.setSelectedIndex(0);
		String chosenName13 = (String) comboBox_SolvencyRatio.getSelectedItem();
		setLapfRegulatorySolvancyRatio(chosenName13);
		comboBox_SolvencyRatio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setLapfRegulatorySolvancyRatio(cmbType);
			}
		});
		GridBagConstraints gbc_comboBox_SolvencyRatio = new GridBagConstraints();
		gbc_comboBox_SolvencyRatio.anchor = GridBagConstraints.WEST;
		gbc_comboBox_SolvencyRatio.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_SolvencyRatio.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_SolvencyRatio.gridx = 1;
		gbc_comboBox_SolvencyRatio.gridy = 7;
		panel_LAPF_Config.add(comboBox_SolvencyRatio,
				gbc_comboBox_SolvencyRatio);

		JLabel labelPeriodicLiabilityPayout = new JLabel(
				"Periodic Liability Payout Rate:");
		labelPeriodicLiabilityPayout.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		GridBagConstraints gbc_labelPeriodicLiabilityPayout = new GridBagConstraints();
		gbc_labelPeriodicLiabilityPayout.anchor = GridBagConstraints.WEST;
		gbc_labelPeriodicLiabilityPayout.insets = new Insets(0, 0, 5, 5);
		gbc_labelPeriodicLiabilityPayout.gridx = 0;
		gbc_labelPeriodicLiabilityPayout.gridy = 8;
		panel_LAPF_Config.add(labelPeriodicLiabilityPayout,
				gbc_labelPeriodicLiabilityPayout);

		textField_PeriodicLiabilityPayoutRate = new JTextField();
		textField_PeriodicLiabilityPayoutRate.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_PeriodicLiabilityPayoutRate.setText("0.02");
		GridBagConstraints gbc_textField_PeriodicLiabilityPayoutRate = new GridBagConstraints();
		gbc_textField_PeriodicLiabilityPayoutRate.anchor = GridBagConstraints.WEST;
		gbc_textField_PeriodicLiabilityPayoutRate.insets = new Insets(0, 0, 5,
				0);
		gbc_textField_PeriodicLiabilityPayoutRate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_PeriodicLiabilityPayoutRate.gridx = 1;
		gbc_textField_PeriodicLiabilityPayoutRate.gridy = 8;
		panel_LAPF_Config.add(textField_PeriodicLiabilityPayoutRate,
				gbc_textField_PeriodicLiabilityPayoutRate);
		textField_PeriodicLiabilityPayoutRate.setColumns(10);

		JPanel panel_LoanBorrowerConfig = new JPanel();
		panel_LoanBorrowerConfig.setBorder(new TitledBorder(null,
				"Loan/Borrower Configuration", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_LoanBorrowerConfig = new GridBagConstraints();
		gbc_panel_LoanBorrowerConfig.fill = GridBagConstraints.VERTICAL;
		gbc_panel_LoanBorrowerConfig.gridx = 0;
		gbc_panel_LoanBorrowerConfig.gridy = 2;
		panel_AgentsConfig.add(panel_LoanBorrowerConfig,
				gbc_panel_LoanBorrowerConfig);
		GridBagLayout gbl_panel_LoanBorrowerConfig = new GridBagLayout();
		gbl_panel_LoanBorrowerConfig.columnWidths = new int[] { 404, 0 };
		gbl_panel_LoanBorrowerConfig.rowHeights = new int[] { 100, 0 };
		gbl_panel_LoanBorrowerConfig.columnWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		gbl_panel_LoanBorrowerConfig.rowWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		panel_LoanBorrowerConfig.setLayout(gbl_panel_LoanBorrowerConfig);

		JPanel panel_loanborrowerConfig = new JPanel();
		panel_loanborrowerConfig.setBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_panel_loanborrowerConfig = new GridBagConstraints();
		gbc_panel_loanborrowerConfig.fill = GridBagConstraints.VERTICAL;
		gbc_panel_loanborrowerConfig.gridx = 0;
		gbc_panel_loanborrowerConfig.gridy = 0;
		panel_LoanBorrowerConfig.add(panel_loanborrowerConfig,
				gbc_panel_loanborrowerConfig);
		GridBagLayout gbl_panel_loanborrowerConfig = new GridBagLayout();
		gbl_panel_loanborrowerConfig.columnWidths = new int[] { 203, 189, 0 };
		gbl_panel_loanborrowerConfig.rowHeights = new int[] { 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		gbl_panel_loanborrowerConfig.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_panel_loanborrowerConfig.rowWeights = new double[] { 0.0, 0.0, 0.0,

		0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_loanborrowerConfig.setLayout(gbl_panel_loanborrowerConfig);

		JLabel lblBorrowerType = new JLabel("Borrower Type:");
		lblBorrowerType.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblBorrowerType = new GridBagConstraints();
		gbc_lblBorrowerType.anchor = GridBagConstraints.WEST;
		gbc_lblBorrowerType.insets = new Insets(0, 0, 5, 5);
		gbc_lblBorrowerType.gridx = 0;
		gbc_lblBorrowerType.gridy = 2;
		panel_loanborrowerConfig.add(lblBorrowerType, gbc_lblBorrowerType);

		JComboBox comboBox_BorrowerType = new JComboBox();
		comboBox_BorrowerType.setFont(new Font("SansSerif", Font.PLAIN, 9));
		comboBox_BorrowerType.setToolTipText("Select borrower type");
		GridBagConstraints gbc_comboBox_BorrowerType = new GridBagConstraints();
		gbc_comboBox_BorrowerType.anchor = GridBagConstraints.WEST;
		gbc_comboBox_BorrowerType.fill = GridBagConstraints.VERTICAL;
		gbc_comboBox_BorrowerType.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_BorrowerType.gridx = 1;
		gbc_comboBox_BorrowerType.gridy = 2;
		panel_loanborrowerConfig.add(comboBox_BorrowerType,
				gbc_comboBox_BorrowerType);
		comboBox_BorrowerType.setModel(new DefaultComboBoxModel(new String[] {
				"individual_generic", "individual_first_time_buyer",
				"individual_established_owner", "corporate", "bank" }));
		comboBox_BorrowerType.setSelectedIndex(0);
		String chosenName15 = (String) comboBox_BorrowerType.getSelectedItem();
		comboBox_BorrowerType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setBorrowerTypeString(cmbType);
			}
		});

		JLabel lblSecuritisationCostGeneric = new JLabel(
				"Origination Credit Quality:");
		lblSecuritisationCostGeneric.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		GridBagConstraints gbc_lblSecuritisationCostGeneric = new GridBagConstraints();
		gbc_lblSecuritisationCostGeneric.anchor = GridBagConstraints.WEST;
		gbc_lblSecuritisationCostGeneric.insets = new Insets(0, 0, 5, 5);
		gbc_lblSecuritisationCostGeneric.gridx = 0;
		gbc_lblSecuritisationCostGeneric.gridy = 3;
		panel_loanborrowerConfig.add(lblSecuritisationCostGeneric,
				gbc_lblSecuritisationCostGeneric);

		JComboBox comboBox_SecuritisationCostGenericCoupon = new JComboBox();
		comboBox_SecuritisationCostGenericCoupon.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_comboBox_SecuritisationCostGenericCoupon = new GridBagConstraints();
		gbc_comboBox_SecuritisationCostGenericCoupon.anchor = GridBagConstraints.WEST;
		gbc_comboBox_SecuritisationCostGenericCoupon.fill = GridBagConstraints.VERTICAL;
		gbc_comboBox_SecuritisationCostGenericCoupon.insets = new Insets(0, 0,
				5, 0);
		gbc_comboBox_SecuritisationCostGenericCoupon.gridx = 1;
		gbc_comboBox_SecuritisationCostGenericCoupon.gridy = 3;
		panel_loanborrowerConfig.add(comboBox_SecuritisationCostGenericCoupon,
				gbc_comboBox_SecuritisationCostGenericCoupon);
		comboBox_SecuritisationCostGenericCoupon
				.setModel(new DefaultComboBoxModel(new String[] { "AAA", "AA",
						"A", "BBB", "BBB-", "BB", "BB1", "BB2", "BB3", "BB4",
						"BB-", "CCC", "CCC-" }));
		comboBox_SecuritisationCostGenericCoupon.setSelectedIndex(1);
		String cmbSecuritisationCostGenericCoupon = (String) comboBox_SecuritisationCostGenericCoupon
				.getSelectedItem();
		setSecuritisationCostGenericMBSCouponAndDefault(comboBox_SecuritisationCostGenericCoupon
				.getSelectedIndex());
		setSecuritisationCostGenericCouponString(cmbSecuritisationCostGenericCoupon);
		jTextField__CreditBenchmarkMean.setText(Double
				.toString((1 - securitisationCostGenericMBSDefault) * 100));
		// System.out.println(this.securitisationCostGenericMBSCoupon);

		comboBox_SecuritisationCostGenericCoupon
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JComboBox jcmbType = (JComboBox) e.getSource();
						String cmbType = (String) jcmbType.getSelectedItem();
						ArrayList couponsList = new ArrayList<String>(Arrays
								.asList(textField_CitiBankCoupons.getText()
										.split("\\s*,\\s*")));

						citibankResearchMBSCouponsList = new ArrayList<Double>();
						for (int i = 0; i < couponsList.size(); i++) {
							citibankResearchMBSCouponsList.add(Double
									.parseDouble((String) couponsList.get(i)));
						}

						ArrayList defaultList = new ArrayList<String>(Arrays
								.asList(textField_CitiBankDefaultRates
										.getText().split("\\s*,\\s*")));

						citibankResearchMBSDefaultsList = new ArrayList<Double>();
						for (int i = 0; i < defaultList.size(); i++) {
							citibankResearchMBSDefaultsList.add(Double
									.parseDouble((String) defaultList.get(i)));
						}

						setSecuritisationCostGenericCouponString(cmbType);
						setSecuritisationCostGenericMBSCouponAndDefault(jcmbType
								.getSelectedIndex());
						textField_SecuritisationCostMultiplier.setText(Double
								.toString(securitisationCostGenericMBSCoupon));
						// System.out.println(securitisationCostGenericMBSCoupon);
						textField_DefaultRateOnBankAssets.setText(Double
								.toString(securitisationCostGenericMBSDefault));
						jTextField__CreditBenchmarkMean.setText(Double
								.toString((1 - securitisationCostGenericMBSDefault) * 100));
					}
				});
		String resetDowngradeAssetQuality = (String) comboBox_SecuritisationCostGenericCoupon
				.getSelectedItem();

		JLabel lblSecuritisationCostMultiplier = new JLabel(
				"Securitisation Cost Multiplier:");
		lblSecuritisationCostMultiplier.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		lblSecuritisationCostMultiplier
				.setToolTipText("Assumed to the be the coupon on the globally assumed loan asset quality");
		GridBagConstraints gbc_lblSecuritisationCostMultiplier = new GridBagConstraints();
		gbc_lblSecuritisationCostMultiplier.anchor = GridBagConstraints.WEST;
		gbc_lblSecuritisationCostMultiplier.insets = new Insets(0, 0, 5, 5);
		gbc_lblSecuritisationCostMultiplier.gridx = 0;
		gbc_lblSecuritisationCostMultiplier.gridy = 4;
		panel_loanborrowerConfig.add(lblSecuritisationCostMultiplier,
				gbc_lblSecuritisationCostMultiplier);

		textField_SecuritisationCostMultiplier = new JTextField();
		textField_SecuritisationCostMultiplier.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_textField_SecuritisationCostMultiplier = new GridBagConstraints();
		gbc_textField_SecuritisationCostMultiplier.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_SecuritisationCostMultiplier.anchor = GridBagConstraints.WEST;
		gbc_textField_SecuritisationCostMultiplier.insets = new Insets(0, 0, 5,
				0);
		gbc_textField_SecuritisationCostMultiplier.gridx = 1;
		gbc_textField_SecuritisationCostMultiplier.gridy = 4;
		panel_loanborrowerConfig.add(textField_SecuritisationCostMultiplier,
				gbc_textField_SecuritisationCostMultiplier);
		textField_SecuritisationCostMultiplier.setText("0.05");
		textField_SecuritisationCostMultiplier.setColumns(10);
		textField_SecuritisationCostMultiplier.setText(Double
				.toString(this.securitisationCostGenericMBSCoupon));

		JLabel lblGenericCreditDefault = new JLabel(
				"Loan Default Probability at Origination:");
		lblGenericCreditDefault.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblGenericCreditDefault = new GridBagConstraints();
		gbc_lblGenericCreditDefault.anchor = GridBagConstraints.WEST;
		gbc_lblGenericCreditDefault.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenericCreditDefault.gridx = 0;
		gbc_lblGenericCreditDefault.gridy = 5;
		panel_loanborrowerConfig.add(lblGenericCreditDefault,
				gbc_lblGenericCreditDefault);
		lblGenericCreditDefault.setToolTipText("1-gamma: asset default rate ");

		textField_DefaultRateOnBankAssets = new JTextField();
		textField_DefaultRateOnBankAssets.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_textField_DefaultRateOnBankAssets = new GridBagConstraints();
		gbc_textField_DefaultRateOnBankAssets.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_DefaultRateOnBankAssets.anchor = GridBagConstraints.WEST;
		gbc_textField_DefaultRateOnBankAssets.insets = new Insets(0, 0, 5, 0);
		gbc_textField_DefaultRateOnBankAssets.gridx = 1;
		gbc_textField_DefaultRateOnBankAssets.gridy = 5;
		panel_loanborrowerConfig.add(textField_DefaultRateOnBankAssets,
				gbc_textField_DefaultRateOnBankAssets);
		textField_DefaultRateOnBankAssets.setText(Double
				.toString(this.securitisationCostGenericMBSDefault));
		textField_DefaultRateOnBankAssets
				.setToolTipText("Asset Default Rate used by banks");
		textField_DefaultRateOnBankAssets.setColumns(10);

		JPanel panel_GenericRateReset = new JPanel();
		GridBagConstraints gbc_panel_GenericRateReset = new GridBagConstraints();
		gbc_panel_GenericRateReset.anchor = GridBagConstraints.WEST;
		gbc_panel_GenericRateReset.insets = new Insets(0, 0, 5, 5);
		gbc_panel_GenericRateReset.gridx = 0;
		gbc_panel_GenericRateReset.gridy = 7;
		panel_loanborrowerConfig.add(panel_GenericRateReset,
				gbc_panel_GenericRateReset);
		panel_GenericRateReset.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel lblFullyIndexRate = new JLabel(
				"Fully Indexed Rate Reset Spread:");
		lblFullyIndexRate.setFont(new Font("SansSerif", Font.PLAIN, 9));
		panel_GenericRateReset.add(lblFullyIndexRate);

		textField_FullIndexSpread = new JTextField();
		textField_FullIndexSpread.setFont(new Font("SansSerif", Font.PLAIN, 9));
		textField_FullIndexSpread.setText("0.05");
		textField_FullIndexSpread.setColumns(10);
		panel_GenericRateReset.add(textField_FullIndexSpread);

		JPanel panel_GenericResets = new JPanel();
		GridBagConstraints gbc_panel_GenericResets = new GridBagConstraints();
		gbc_panel_GenericResets.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_GenericResets.anchor = GridBagConstraints.WEST;
		gbc_panel_GenericResets.insets = new Insets(0, 0, 5, 0);
		gbc_panel_GenericResets.gridx = 1;
		gbc_panel_GenericResets.gridy = 7;
		panel_loanborrowerConfig.add(panel_GenericResets,
				gbc_panel_GenericResets);
		panel_GenericResets.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel lbl_PostResetDefaultRate = new JLabel("Post Reset Default Rate:");
		lbl_PostResetDefaultRate.setFont(new Font("SansSerif", Font.PLAIN, 9));
		panel_GenericResets.add(lbl_PostResetDefaultRate);

		textField_PostResetDefaultRate = new JTextField();
		textField_PostResetDefaultRate.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		textField_PostResetDefaultRate.setText("0.15");
		panel_GenericResets.add(textField_PostResetDefaultRate);
		textField_PostResetDefaultRate.setColumns(10);

		JLabel lbl_PostResetCouponRate = new JLabel("Post Reset Coupon:");
		lbl_PostResetCouponRate.setFont(new Font("SansSerif", Font.PLAIN, 9));
		panel_GenericResets.add(lbl_PostResetCouponRate);

		textField_PostResetCouponRate = new JTextField();
		textField_PostResetCouponRate.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		textField_PostResetCouponRate.setText("0.15");
		panel_GenericResets.add(textField_PostResetCouponRate);
		textField_PostResetCouponRate.setColumns(10);

		JLabel lblCreditQualityDowngrade = new JLabel(
				"Credit Quality Downgrade:");
		lblCreditQualityDowngrade.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_lblCreditQualityDowngrade = new GridBagConstraints();
		gbc_lblCreditQualityDowngrade.anchor = GridBagConstraints.WEST;
		gbc_lblCreditQualityDowngrade.insets = new Insets(0, 0, 5, 5);
		gbc_lblCreditQualityDowngrade.gridx = 0;
		gbc_lblCreditQualityDowngrade.gridy = 6;
		panel_loanborrowerConfig.add(lblCreditQualityDowngrade,
				gbc_lblCreditQualityDowngrade);

		JComboBox comboBox_DowngradeAssetQuality = new JComboBox();
		comboBox_DowngradeAssetQuality.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		comboBox_DowngradeAssetQuality.setModel(new DefaultComboBoxModel(
				new String[] { "AAA", "AA", "A", "BBB", "BBB-", "BB", "BB1",
						"BB2", "BB3", "BB4", "BB-", "CCC", "CCC-" }));
		comboBox_DowngradeAssetQuality.setSelectedIndex(10);
		// System.out.println(cmbType);
		couponsList = new ArrayList<String>(
				Arrays.asList(textField_CitiBankCoupons.getText().split(
						"\\s*,\\s*")));

		citibankResearchMBSCouponsList = new ArrayList<Double>();
		for (int i = 0; i < couponsList.size(); i++) {
			citibankResearchMBSCouponsList.add(Double
					.parseDouble((String) couponsList.get(i)));
		}

		defaultList = new ArrayList<String>(
				Arrays.asList(textField_CitiBankDefaultRates.getText().split(
						"\\s*,\\s*")));

		citibankResearchMBSDefaultsList = new ArrayList<Double>();
		for (int i = 0; i < defaultList.size(); i++) {
			citibankResearchMBSDefaultsList.add(Double
					.parseDouble((String) defaultList.get(i)));
		}

		setResetDowngradeAssetQualityString(resetDowngradeAssetQuality);
		setResetDowngradeAssetQualityMBSCouponAndDefault(comboBox_DowngradeAssetQuality
				.getSelectedIndex());
		textField_PostResetCouponRate.setText(Double
				.toString(this.resetDowngradeAssetQualityMBSCoupon));
		textField_PostResetDefaultRate.setText(Double
				.toString(this.resetDowngradeAssetQualityMBSDefault));

		comboBox_DowngradeAssetQuality.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				ArrayList couponsList = new ArrayList<String>(Arrays
						.asList(textField_CitiBankCoupons.getText().split(
								"\\s*,\\s*")));

				citibankResearchMBSCouponsList = new ArrayList<Double>();
				for (int i = 0; i < couponsList.size(); i++) {
					citibankResearchMBSCouponsList.add(Double
							.parseDouble((String) couponsList.get(i)));
				}

				ArrayList defaultList = new ArrayList<String>(Arrays
						.asList(textField_CitiBankDefaultRates.getText().split(
								"\\s*,\\s*")));

				citibankResearchMBSDefaultsList = new ArrayList<Double>();
				for (int i = 0; i < defaultList.size(); i++) {
					citibankResearchMBSDefaultsList.add(Double
							.parseDouble((String) defaultList.get(i)));
				}

				setResetDowngradeAssetQualityString(cmbType);
				setResetDowngradeAssetQualityMBSCouponAndDefault(jcmbType
						.getSelectedIndex());
				textField_PostResetCouponRate.setText(Double
						.toString(resetDowngradeAssetQualityMBSCoupon));
				// System.out.println(securitisationCostGenericMBSCoupon);
				textField_PostResetDefaultRate.setText(Double
						.toString(resetDowngradeAssetQualityMBSDefault));
			}
		});

		GridBagConstraints gbc_comboBox_DowngradeAssetQuality = new GridBagConstraints();
		gbc_comboBox_DowngradeAssetQuality.anchor = GridBagConstraints.WEST;
		gbc_comboBox_DowngradeAssetQuality.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_DowngradeAssetQuality.fill = GridBagConstraints.VERTICAL;
		gbc_comboBox_DowngradeAssetQuality.gridx = 1;
		gbc_comboBox_DowngradeAssetQuality.gridy = 6;
		panel_loanborrowerConfig.add(comboBox_DowngradeAssetQuality,
				gbc_comboBox_DowngradeAssetQuality);

		setBorrowerTypeString(chosenName15);

		JPanel simulation_output_panel = new JPanel();
		simulation_output_panel.setBorder(new BevelBorder(BevelBorder.RAISED,
				null, null, null, null));
		application_main_panel
				.add(simulation_output_panel, BorderLayout.CENTER);
		simulation_output_panel.setLayout(new CardLayout(0, 0));

		JTabbedPane tabbedPane_SimulationOutput = new JTabbedPane(
				SwingConstants.BOTTOM);
		tabbedPane_SimulationOutput
				.setToolTipText("View of the agent environment ");
		tabbedPane_SimulationOutput
				.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		simulation_output_panel.add(tabbedPane_SimulationOutput,
				"name_763842641609321");

		panel_World = new JPanel();
		tabbedPane_SimulationOutput.addTab("World", new ImageIcon(
				CRTApplicationFrame.class.getResource("/jas/images/ca.gif")),
				panel_World, null);
		panel_World.setLayout(new CardLayout(0, 0));

		this.panel_BanksOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"Banks",
				new ImageIcon(CRTApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_BanksOutput, "Simulated Bank Data Output");
		panel_BanksOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_AssetAccumulation = new ChartPanel(
				this.getBankAssetVsLiabilityChart());
		this.panel_AssetAccumulation.setLayout(new GridBagLayout());
		this.panel_AssetAccumulation.setBounds(new Rectangle(0, 0, 140, 140));
		panel_BanksOutput.add(panel_AssetAccumulation);

		this.panel_CapitalAccumulation = new ChartPanel(
				this.getBankCapitalAccumulationChart());
		this.panel_CapitalAccumulation.setLayout(new GridBagLayout());
		this.panel_CapitalAccumulation.setBounds(new Rectangle(0, 0, 140, 140));
		panel_BanksOutput.add(panel_CapitalAccumulation);

		this.panel_SecuritisationRate = new ChartPanel(
				this.getBankAverageSecuritisationRateChart());
		this.panel_SecuritisationRate.setLayout(new GridBagLayout());
		this.panel_SecuritisationRate.setBounds(new Rectangle(0, 0, 140, 140));
		panel_BanksOutput.add(panel_SecuritisationRate);

		this.panel_Fragility = new ChartPanel(this.getBankFragilityChart());
		this.panel_Fragility.setLayout(new GridBagLayout());
		this.panel_Fragility.setBounds(new Rectangle(0, 0, 140, 140));
		panel_BanksOutput.add(panel_Fragility);

		JPanel panel_Funds = new JPanel();
		panel_Funds.setToolTipText("Funds Simulated Data");
		tabbedPane_SimulationOutput.addTab(
				"Funds",
				new ImageIcon(CRTApplicationFrame.class
						.getResource("/jas/images/graphs.gif")), panel_Funds,
				"Simulated Funds output data");
		panel_Funds.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_FundAssets = new ChartPanel(
				this.getFundAssetsvsLiabilitiesChart());
		this.panel_FundAssets.setLayout(new GridBagLayout());
		this.panel_FundAssets.setBounds(new Rectangle(0, 0, 140, 140));
		panel_Funds.add(panel_FundAssets);

		this.panel_FundSurplus = new ChartPanel(this.getFundSurplusChart());
		this.panel_FundSurplus.setLayout(new GridBagLayout());
		this.panel_FundSurplus.setBounds(new Rectangle(0, 0, 140, 140));
		panel_Funds.add(panel_FundSurplus);

		this.panel_FundAssetMix = new ChartPanel(
				this.getFundAssetAllocationChart());
		this.panel_FundAssetMix.setLayout(new GridBagLayout());
		this.panel_FundAssetMix.setBounds(new Rectangle(0, 0, 140, 140));
		panel_Funds.add(panel_FundAssetMix);

		this.panel_MarketClearingMBSReturn = new ChartPanel(
				this.getMarketClearingMBSReturnChart());
		this.panel_MarketClearingMBSReturn.setLayout(new GridBagLayout());
		this.panel_MarketClearingMBSReturn.setBounds(new Rectangle(0, 0, 140,
				140));
		panel_Funds.add(panel_MarketClearingMBSReturn);
		panel_MarketClearingMBSReturn.setLayout(new GridLayout(1, 0, 0, 0));

		this.panel_MDPAgents = new JPanel();
		panel_MDPAgents.setToolTipText("MDP Agent Data");
		tabbedPane_SimulationOutput.addTab(
				"MDP Agents",
				new ImageIcon(CRTApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_MDPAgents, null);
		panel_MDPAgents.setLayout(new GridLayout(2, 2, 0, 0));

		this.chartPanel_BullDelta = new ChartPanel(this.geBullDeltaChart());
		chartPanel_BullDelta.setBounds(new Rectangle(0, 0, 140, 140));
		panel_MDPAgents.add(chartPanel_BullDelta);
		GridBagLayout gbl_chartPanel_BullDelta = new GridBagLayout();
		gbl_chartPanel_BullDelta.columnWidths = new int[] { 0 };
		gbl_chartPanel_BullDelta.rowHeights = new int[] { 0 };
		gbl_chartPanel_BullDelta.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_chartPanel_BullDelta.rowWeights = new double[] { Double.MIN_VALUE };
		chartPanel_BullDelta.setLayout(gbl_chartPanel_BullDelta);

		this.chartPanel_BearDelta = new ChartPanel(this.geBearDeltaChart());
		chartPanel_BearDelta.setBounds(new Rectangle(0, 0, 140, 140));
		panel_MDPAgents.add(chartPanel_BearDelta);
		GridBagLayout gbl_chartPanel_BearDelta = new GridBagLayout();
		gbl_chartPanel_BearDelta.columnWidths = new int[] { 0 };
		gbl_chartPanel_BearDelta.rowHeights = new int[] { 0 };
		gbl_chartPanel_BearDelta.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_chartPanel_BearDelta.rowWeights = new double[] { Double.MIN_VALUE };
		chartPanel_BearDelta.setLayout(gbl_chartPanel_BearDelta);

		this.chartPanel_PassiveDelta = new ChartPanel(
				this.gePassiveDeltaChart());
		chartPanel_PassiveDelta.setBounds(new Rectangle(0, 0, 140, 140));
		panel_MDPAgents.add(chartPanel_PassiveDelta);
		GridBagLayout gbl_chartPanel_PassiveDelta = new GridBagLayout();
		gbl_chartPanel_PassiveDelta.columnWidths = new int[] { 0 };
		gbl_chartPanel_PassiveDelta.rowHeights = new int[] { 0 };
		gbl_chartPanel_PassiveDelta.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_chartPanel_PassiveDelta.rowWeights = new double[] { Double.MIN_VALUE };
		chartPanel_PassiveDelta.setLayout(gbl_chartPanel_PassiveDelta);

		this.chartPanel_RationalDelta = new ChartPanel(
				this.geRationalDeltaChart());
		chartPanel_RationalDelta.setBounds(new Rectangle(0, 0, 140, 140));
		panel_MDPAgents.add(chartPanel_RationalDelta);
		GridBagLayout gbl_chartPanel_RationalDelta = new GridBagLayout();
		gbl_chartPanel_RationalDelta.columnWidths = new int[] { 0 };
		gbl_chartPanel_RationalDelta.rowHeights = new int[] { 0 };
		gbl_chartPanel_RationalDelta.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_chartPanel_RationalDelta.rowWeights = new double[] { Double.MIN_VALUE };
		chartPanel_RationalDelta.setLayout(gbl_chartPanel_RationalDelta);

		JPanel panel_FocusBank = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"Optimal Securitisation",
				new ImageIcon(CRTApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_FocusBank, null);
		panel_FocusBank.setLayout(new CardLayout(0, 0));

		this.panel_FocusBankSecRate = new ChartPanel(
				this.getBankOptimalSecuritisationRateChart());
		this.panel_FocusBankSecRate.setLayout(new GridBagLayout());
		this.panel_FocusBankSecRate.setBounds(new Rectangle(0, 0, 175, 210));
		panel_FocusBank.add(panel_FocusBankSecRate);
		panel_FocusBankSecRate.setLayout(new CardLayout(0, 0));

		JPanel panel_FocusBankTranches = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"Securitisation Tranche Comparison",
				new ImageIcon(CRTApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_FocusBankTranches, null);
		panel_FocusBankTranches.setLayout(new CardLayout(0, 0));

		this.panel_FocusBankTranchesSecRate = new ChartPanel(
				this.getBankOptimalTranchesSecuritisationRateChart());
		this.panel_FocusBankTranchesSecRate.setLayout(new GridBagLayout());
		this.panel_FocusBankTranchesSecRate.setBounds(new Rectangle(0, 0, 175,
				210));
		panel_FocusBankTranches.add(panel_FocusBankTranchesSecRate);
		panel_FocusBankTranchesSecRate.setLayout(new CardLayout(0, 0));

		// panel_WorldChartContainer = new JPanel();
		// panel_WorldChartContainer.setBackground(Color.WHITE);
		// panel_WorldChartContainer = new JPanel();
		// panel_WorldChartContainer.add(this.modelObserver.getDisplayPanel());
		// panel_World.add(panel_WorldChartContainer, "name_769003536274052");

		JPanel menu_bar = new JPanel();
		contentPane.add(menu_bar, BorderLayout.NORTH);
		menu_bar.setLayout(new GridLayout(1, 0, 0, 0));

		JButton button_LoadData = new JButton("Load Data");
		button_LoadData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadData();
				setParameters();
				// System.out.println(param.toString());

			}
		});
		button_LoadData.setIcon(new ImageIcon(CRTApplicationFrame.class
				.getResource("/jas/images/build16.gif")));
		menu_bar.add(button_LoadData);

		JButton button_BuildModel = new JButton("Build Model");
		button_BuildModel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// eng.stop();
				// eng.stepTime();
				eng.buildModels();
				buildAgentWorldViewerWindow();
				buildOrUpdateCharts();
			}
		});
		button_BuildModel.setIcon(new ImageIcon(CRTApplicationFrame.class
				.getResource("/jas/images/build.gif")));
		menu_bar.add(button_BuildModel);

		JButton button_Run = new JButton("Run");
		button_Run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eng.start();
			}
		});
		button_Run.setIcon(new ImageIcon(CRTApplicationFrame.class
				.getResource("/jas/images/play.gif")));
		menu_bar.add(button_Run);

		JButton button_Step = new JButton("Step");
		button_Step.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eng.stop();
				eng.stepTime();
				System.gc();
			}
		});

		JButton button_Pause = new JButton("Pause");
		button_Pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eng.stop();
			}
		});
		button_Pause.setIcon(new ImageIcon(CRTApplicationFrame.class
				.getResource("/jas/images/pause.gif")));
		menu_bar.add(button_Pause);
		button_Step.setIcon(new ImageIcon(CRTApplicationFrame.class
				.getResource("/jas/images/step16.gif")));
		menu_bar.add(button_Step);

		JButton button_Stop = new JButton("Stop");
		button_Stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eng.end();
			}
		});
		button_Stop.setIcon(new ImageIcon(CRTApplicationFrame.class
				.getResource("/jas/images/stop.gif")));
		menu_bar.add(button_Stop);

		JButton button_Reload = new JButton("Reload");
		button_Reload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reloads++;
				modelObserver.dispose();
				eng.disposeModels();
				clearAllActiveGraphicsPanels();
				createNewModel();
				initialize();
			}
		});
		button_Reload.setIcon(new ImageIcon(CRTApplicationFrame.class
				.getResource("/jas/images/refresh.gif")));
		menu_bar.add(button_Reload);

		JPanel panel_9 = new JPanel();
		contentPane.add(panel_9, BorderLayout.SOUTH);

		JLabel lblFocusBank = new JLabel("Focus Bank:");
		lblFocusBank.setFont(new Font("SansSerif", Font.PLAIN, 9));
		panel_9.add(lblFocusBank);

		JComboBox comboBox_FocusBank = new JComboBox();
		comboBox_FocusBank.setFont(new Font("SansSerif", Font.PLAIN, 9));
		panel_9.add(comboBox_FocusBank);
		comboBox_FocusBank.setModel(new DefaultComboBoxModel(bankNamesList));
		comboBox_FocusBank.setSelectedIndex(0);
		String chosenName16 = (String) comboBox_FocusBank.getSelectedItem();
		setFocusBankNameString(chosenName16);
		comboBox_FocusBank.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox jcmbType = (JComboBox) e.getSource();
				String cmbType = (String) jcmbType.getSelectedItem();
				setFocusBankNameString(cmbType);
			}//
		});
	}// end of initialisation method


	/**
	 * This method initializes jMenuItemAbout
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemAbout() {
		if (jMenuItemAbout == null) {
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText("About");
			jMenuItemAbout.setMnemonic(KeyEvent.VK_A);
			jMenuItemAbout
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JOptionPane
									.showMessageDialog(
											null,
											"Securitisation Model Systemic Risk Simulation"
													+ "\n"
													+ "Oluwasegun Bewaji"
													+ "\n"
													+ "PhD Thesis First Results Chapter Model"
													+ "\n"
													+ "Model Assumptions: "
													+ "\n"
													+ "Defaults are exogenous and homogeneous"
													+ "\n"
													+ "Banks minimise capital injections subject to regulatory capital "
													+ "\n"
													+ "by selecting the optimal securitisation rate"
													+ "\n"
													+ "All Banking sector asset securitisations are aquired by the LAPF sector"
													+ "\n"
													+ "LAPFs make investment decisions purely on where they recieve the highest risk adjusted returns",
											"", JOptionPane.INFORMATION_MESSAGE);

						}
					});
		}
		return jMenuItemAbout;
	}

	private JMenuItem getJMenuItemShowExposures() {
		// TODO Auto-generated method stub
		if (jMenuItemComparisonRun == null) {
			jMenuItemComparisonRun = new JMenuItem();
			jMenuItemComparisonRun.setText("Show Balancesheet Exposures");
			jMenuItemComparisonRun.setMnemonic(KeyEvent.VK_A);
			jMenuItemComparisonRun
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// jPanel_SimulationResults
							// .remove(tabbedPane_BasisAndSpreads);
							// jPanel_SimulationResults
							// .add(tabbedPane_SimulationOutput);
							// // jParentPane.add(jPanel_SimulationResults);
							// jPanel_SimulationResults.repaint();
							// jPanel_SimulationResults.validate();

						}
					});

		}
		return jMenuItemComparisonRun;
	}

	/**
	 * This method initializes jMenuItemSingleRun
	 * 
	 * The following jmenu item is used to hide the basis income and balance
	 * sheet data of the banks from the jPanel_SimulationResults JPanel and show
	 * the spreads charts
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemShowSpreads() {
		// TODO Auto-generated method stub
		if (jMenuItemSingleRun == null) {
			jMenuItemSingleRun = new JMenuItem();
			jMenuItemSingleRun.setText("Show Spread Data");
			jMenuItemSingleRun.setMnemonic(KeyEvent.VK_A);
			jMenuItemSingleRun
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// jPanel_SimulationResults
							// .remove(tabbedPane_SimulationOutput);
							// jPanel_SimulationResults
							// .add(tabbedPane_BasisAndSpreads);
							// //
							// jParentPane.add(jPanel_SimulationResults);tabbedPane_BasisAndSpreads
							// jPanel_SimulationResults.repaint();
							// jPanel_SimulationResults.validate();

						}
					});

		}
		return jMenuItemSingleRun;
	}

	/**
	 * This method initializes jMenuItemExit
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setMnemonic(KeyEvent.VK_X);
			jMenuItemExit.setText("Exit");//
			jMenuItemExit
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
		}
		return jMenuItemExit;
	}// end of getJMenuItemExit

	private JMenuItem getJMenuItemPrintChartsToFile() {
		// TODO Auto-generated method stub
		if (jMenuItemPrintChartsToFile == null) {
			jMenuItemPrintChartsToFile = new JMenuItem();
			jMenuItemPrintChartsToFile.setText("Print Results to File");
			jMenuItemPrintChartsToFile.setMnemonic(KeyEvent.VK_A);
			jMenuItemPrintChartsToFile
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (model == null) {
								JOptionPane
										.showMessageDialog(
												null,
												"Model not yet loaded. Please configure the simulation ",
												"", JOptionPane.ERROR_MESSAGE);
							}
							// try {
							// // printCharts();
							// } catch (FileNotFoundException e1) {
							// // TODO Auto-generated catch block
							// // printChartsToImage
							// e1.printStackTrace();
							// }

						}
					});

		}
		return jMenuItemPrintChartsToFile;
	}

	private JMenuItem getJMenuItemSaveChartsToImageFile() {
		// TODO Auto-generated method stub
		if (jMenuItemSaveChartsToImageFile == null) {
			jMenuItemSaveChartsToImageFile = new JMenuItem();
			jMenuItemSaveChartsToImageFile.setText("Save Results to PNG Image");
			jMenuItemSaveChartsToImageFile.setMnemonic(KeyEvent.VK_A);
			jMenuItemSaveChartsToImageFile
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (model == null) {
								JOptionPane
										.showMessageDialog(
												null,
												"Model not yet loaded. Please configure the simulation ",
												"", JOptionPane.ERROR_MESSAGE);
							}
							// try {
							// // printChartsToImage();
							// } catch (FileNotFoundException e1) {
							// // TODO Auto-generated catch block
							// e1.printStackTrace();
							// }

						}
					});

		}
		return jMenuItemSaveChartsToImageFile;
	}

	private void setModelPeriodString(String value) {
		this.modelPeriodSting = value;
	}

	private void setRateTypeString(String value) {
		this.rateTypeString = value;
	}

	private void setBorrowerTypeString(String value) {
		this.borrowerTypeString = value;
		// System.out.println(value);
		// System.out.println(this.borrowerTypeString);

	}

	private void setPaymentScheduleString(String value) {
		this.paymentScheduleString = value;
	}

	private void setTraditionalAssetVariationTypeString(String value) {
		this.traditionalAssetVariationTypeString = value;
	}

	private void setInternalSecuritiseString(String value) {
		this.internalSecuritiseString = value;
	}

	private void setUserAssetLiabilityDataInpuString(String value) {
		this.userAssetLiabilityDataInpuString = value;
	}

	private void setSecuritiseString(String value) {
		this.securitiseString = value;
	}

	private void setSecuritiseLinearString(String value) {
		this.securitiseLinearString = value;
	}

	private void setBnksOnlyAnalysisString(String value) {
		this.bnksOnlyAnalysisString = value;
	}

	private void setFocusBankNameString(String value) {
		this.focusBankString = value;
	}

	private void setBankCount(String value) {
		this.bankCount = value;
		if (value == "35") {
			this.bankNamesList = new String[] {
					"BANK OF AMERICA, NATIONAL ASSOCIATION",
					"JPMORGAN CHASE BANK",
					"WELLS FARGO BANK, NATIONAL ASSOCIATION",
					"WACHOVIA BANK, NATIONAL ASSOCIATION", "CITIBANK, N.A.",
					"SUNTRUST BANK", "HSBC BANK USA", "REGIONS BANK",
					"FIFTH THIRD BANK", "BANK OF THE WEST",
					"M&I MARSHALL & ILSLEY BANK", "MERRILL LYNCH BANK USA",
					"NORTH FORK BANK", "COMPASS BANK", "FIFTH THIRD BANK",
					"HIBERNIA NATIONAL BANK",
					"ASSOCIATED BANK, NATIONAL ASSOCIATION",
					"FIRSTBANK PUERTO RICO",
					"COMMERCE BANK, NATIONAL ASSOCIATION",
					"FREMONT INVESTMENT & LOAN", "FIRST REPUBLIC BANK",
					"BANCO POPULAR DE PUERTO RICO",
					"FIRST-CITIZENS BANK & TRUST COMPANY",
					"ZIONS FIRST NATIONAL BANK", "ARVEST BANK",
					"IRWIN UNION BANK AND TRUST COMPANY",
					"WESTERNBANK PUERTO RICO", "UNITED BANK, INC.",
					"PNC BANK, NATIONAL ASSOCIATION",
					"KEYBANK NATIONAL ASSOCIATION",
					"HUNTINGTON NATIONAL BANK, THE", "DORAL BANK" };

		} else if (value == "97") {
			this.bankNamesList = new String[] {
					"HUNTINGTON NATIONAL BANK, THE", "SECURITY BANK",
					"SHERMAN COUNTY BANK", "PEOPLES STATE BANK",
					"BOSTON SAFE DEPOSIT AND TRUST COMPANY",
					"IRON WORKERS SAVINGS BANK", "PILGRIM CO-OPERATIVE BANK",
					"HIBERNIA NATIONAL BANK", "FIRST REPUBLIC BANK",
					"DEDHAM CO-OPERATIVE BANK THE",
					"FARMERS NATIONAL BANK OF GRIGGSVILLE",
					"LYNNVILLE NATIONAL BANK, THE", "FIRST STATE BANK",
					"MILWAUKEE WESTERN BANK", "EAST-WEST BANK",
					"MERCANTILE-SAFE DEPOSIT AND TRUST COMPANY",
					"REGIONS BANK", "NATIONAL BANK OF THE REDWOODS",
					"PEOPLES STATE BANK", "ZIONS FIRST NATIONAL BANK",
					"EMPIRE BANK", "KEYBANK NATIONAL ASSOCIATION",
					"ARVEST BANK", "NORTH FORK BANK",
					"SHARON CO-OPERATIVE BANK", "WESTSTAR BANK",
					"COMMERCE BANK, NATIONAL ASSOCIATION",
					"CITRUS AND CHEMICAL BANK", "INDEPENDENCE STATE BANK",
					"SKOWHEGAN SAVINGS BANK", "PARK BANK, THE",
					"CITIZENS NATIONAL BANK OF SOMERSET, THE", "HSBC BANK USA",
					"UNITED COMMUNITY BANK",
					"WELLS FARGO BANK, NATIONAL ASSOCIATION", "CITIBANK, N.A.",
					"BANK OF AMERICA, NATIONAL ASSOCIATION",
					"WACHOVIA BANK, NATIONAL ASSOCIATION",
					"TOWN & COUNTRY BANK",
					"FIRST-CITIZENS BANK & TRUST COMPANY",
					"BANK & TRUST COMPANY", "CITIZENS NATIONAL BANK, THE",
					"FIRSTBANK PUERTO RICO",
					"INDUSTRIAL BANK, NATIONAL ASSOCIATION",
					"FIRST NATIONAL BANK AND TRUST COMPANY",
					"AMERICAN TRUST & SAVINGS BANK",
					"DELAWARE COUNTY BANK AND TRUST COMPANY, THE",
					"DORAL BANK", "SUNTRUST BANK", "FARMERS FIRST BANK",
					"COMPASS BANK", "MERCHANTS BANK", "SHAWNEE STATE BANK",
					"FIFTH THIRD BANK", "RELIANCE SAVINGS BANK",
					"WESTERNBANK PUERTO RICO", "FREMONT INVESTMENT & LOAN",
					"BANK OF KENNEY", "EASTERN COLORADO BANK, THE",
					"COMMERCE BANK/NORTH", "FIRST STATE BANK",
					"BREMER BANK, NATIONAL ASSOCIATION", "BANK OF THE WEST",
					"PNC BANK, NATIONAL ASSOCIATION",
					"CITIZENS BANK OF NEW ULM", "JPMORGAN CHASE BANK",
					"TWIN OAKS SAVINGS BANK", "PREMIERBANK",
					"FIFTH THIRD BANK",
					"ASSOCIATED BANK, NATIONAL ASSOCIATION", "SUNNILAND BANK",
					"MELLON BANK, N.A.", "PORT WASHINGTON STATE BANK, THE",
					"BANCO POPULAR DE PUERTO RICO",
					"M&I MARSHALL & ILSLEY BANK",
					"INTERNATIONAL BANK OF COMMERCE", "UNITED BANK, INC.",
					"TELECOM CO-OPERATIVE BANK", "TEXAS STATE BANK",
					"MERRILL LYNCH BANK USA", "PEOPLES NATIONAL BANK",
					"COMMUNITY BANK & TRUST", "KENTUCKY NATIONAL BANK",
					"RIVERSIDE BANK OF THE GULF COAST", "OLD FLORIDA BANK",
					"SONABANK", "SAINT LOUIS BANK" };
		}
	}

	private void setInterestSpreadExperimentString(String value) {
		this.interestSpreadExperimentString = value;
	}

	private void setLapfRegulatorySolvancyRatio(String value) {
		this.lapfRegulatorySolvancyRatio = value;
	}

	private void setLoanResetYear(String value) {
		this.loanResetYear = value;
	}// loanResetString

	private void setShortSellingString(String value) {
		this.shortSellingString = value;
	}

	private void setMultiPeriodBankHorizon(String value) {
		this.multiPeriodAnalysis = value;
	}

	private void setLapfMultiPeriodSolvancyModelString(String value) {
		this.lapfMultiPeriodSolvancyModelString = value;
	}

	private void setLAPFTypeString(String value) {
		this.lapfTypeString = value;
	}

	private void setLAPFTypeIndex(int value) {
		this.fundType = value;
	}

	
	private void setBankTypeString(String value) {
		this.bankTypeString = value;
	}

	private void setBankTypeIndex(int value) {
		this.bankType = value;
	}

	
	private void setInvestorCountString(int value) {

		switch (value) {
		case 0:
			this.investorCount = String.valueOf(1);
			break;
		case 1:
			this.investorCount = String.valueOf(220);
			break;
		case 2:
			this.investorCount = String.valueOf(10);
			break;
		}
	}

	private void setFundsExpectationsString(String value) {
		// determines if the LAPFs are learning using an MDP or just using the
		// base model
		// possible values
		// "Bullish",
		// "Bearish",
		// "Heterogeneous"
		this.fundsExpectationsString = value;
	}

	private void setLAPFExpectationsIndex(int value) {
		// determines if the LAPFs are learning using an MDP or just using the
		// base model
		// possible values
		// "Bullish",
		// "Bearish",
		// "Heterogeneous"
		this.fundExpectations = value;
	}
	
	protected void setHeuristicSecuritisationModel(boolean b) {
		// TODO Auto-generated method stub
		this.heuristicSecuritisationModel = b;
	}
	
	protected void setMDPErevRothSecuritisationModel(boolean b) {
		// TODO Auto-generated method stub
		this.erevRothModel = b;
	}


	private void setLapfConstantContractualObligationsString(String value) {
		this.lapfConstantContractualObligationsString = value;
	}

	private void setLapfQuadraticCostFunctionString(String value) {
		this.lapfQuadraticCostFunctionString = value;
	}

	private void setSeniorTrancheQualityString(String value) {
		this.seniorTrancheQualityString = value;
	}

	private void setSeniorTrancheCouponAndDefault(int val) {
		this.seniourTrancheCoupon = this.citibankResearchMBSCouponsList
				.get(val);
		this.seniourTrancheDefaultRate = this.citibankResearchMBSDefaultsList
				.get(val);
	}

	private void setMezzTrancheQualityString(String value) {
		this.mezzTrancheQualityString = value;
	}

	private void setMezzTrancheCouponAndDefault(int val) {
		this.mezzTrancheCoupon = this.citibankResearchMBSCouponsList.get(val);
		this.mezzTrancheDefaultRate = this.citibankResearchMBSDefaultsList
				.get(val);
	}

	private void setJuniorTrancheQualityString(String value) {
		this.juniorTrancheQualityString = value;
	}

	private void setJuniorTrancheCouponAndDefault(int val) {
		this.juniourTrancheCoupon = this.citibankResearchMBSCouponsList
				.get(val);
		this.juniourTrancheDefaultRate = this.citibankResearchMBSDefaultsList
				.get(val);
	}

	protected void setSecuritisationCostGenericMBSCouponAndDefault(int val) {
		// TODO Auto-generated method stub
		this.securitisationCostGenericMBSCoupon = this.citibankResearchMBSCouponsList
				.get(val);
		this.securitisationCostGenericMBSDefault = this.citibankResearchMBSDefaultsList
				.get(val);
		// System.out.println(this.securitisationCostGenericMBSCoupon);
		// System.out.println(this.securitisationCostGenericMBSDefault);
	}

	protected void setSecuritisationCostGenericCouponString(String cmbType) {
		// TODO Auto-generated method stub
		this.securitisationCostGenericMBSCouponAndDefaultString = cmbType;
	}

	private void setResetDowngradeAssetQualityMBSCouponAndDefault(int val) {
		// TODO Auto-generated method stub
		this.resetDowngradeAssetQualityMBSCoupon = this.citibankResearchMBSCouponsList
				.get(val);
		this.resetDowngradeAssetQualityMBSDefault = this.citibankResearchMBSDefaultsList
				.get(val);
	}

	private void setResetDowngradeAssetQualityString(String value) {
		// TODO Auto-generated method stub
		this.resetDowngradeAssetQualityString = value;
	}

	public Parameters getParameters() {
		return this.param;
	}

	private void loadData() {

		this.securitisationRate = textField_GenericSecuritisationRate.getText();
		this.defaultProbabilityBankExpectations = textField_DefaultRateOnBankAssets
				.getText();
		this.probabilityOfEquityReturnExpectations = textField_EquityMarketDefaultRate
				.getText();
		this.securitisationCostConstantFactor = textField_SecuritisationCostMultiplier
				.getText();

		/**
		 * Start of CitiBank Coupons and Defaults List Data Arraylist data
		 * creation
		 */
		ArrayList couponsList = new ArrayList<String>(
				Arrays.asList(textField_CitiBankCoupons.getText().split(
						"\\s*,\\s*")));

		this.citibankResearchMBSCouponsList = new ArrayList<Double>();
		for (int i = 0; i < couponsList.size(); i++) {
			citibankResearchMBSCouponsList.add(Double
					.parseDouble((String) couponsList.get(i)));
		}

		ArrayList defaultList = new ArrayList<String>(
				Arrays.asList(textField_CitiBankDefaultRates.getText().split(
						"\\s*,\\s*")));

		this.citibankResearchMBSDefaultsList = new ArrayList<Double>();
		for (int i = 0; i < defaultList.size(); i++) {
			citibankResearchMBSDefaultsList.add(Double
					.parseDouble((String) defaultList.get(i)));
		}
		/**
		 * End of CitiBank Coupons and Defaults List Data Arraylist data
		 * creation
		 */
		this.numberOfConstituentsPerMBSIssuanse = null;
		this.primeMortgageLTV = null;
		this.subprimeMortgageLTV = null;
		this.userDefinedBankAssets = textField_UserDefinedBankAssets.getText();
		this.userDefinedBankLiabilities = textField_UserDefinedBankLiabilities
				.getText();
		this.fullyIndexedContractRateSpread = textField_FullIndexSpread
				.getText();
		this.genericPostRateResetDafaultRate = textField_PostResetDefaultRate
				.getText();
		this.genericPostRateResetCoupon = textField_PostResetCouponRate
				.getText();

		this.bankAssetReturn = textField_ReturnOnBankAssets.getText();
		this.bankLiabilityExpense = textField_ReturnOnBankLiabilities.getText();
		this.returnOnGlobalEquity = textField_EquityMarketReturn.getText();
		this.bankRegulatoryCapitalRatio = textField_RegulatoryCapitalRatio
				.getText();
		this.recoveryRateOnTraditionalAsset = textField_EquityMarketRecoveryRate
				.getText();
		this.recoveryRateOnCreditAsset = textField_CreditMarketRecoveryRate
				.getText();
		this.securitisationRateDecisionHorizon = textField_SecuritisationRateDecision
				.getText();
		this.assetValueMin = null;
		this.assetValueMax = null;
		this.aveIncomeMin = null;
		this.aveIncomeMax = null;
		this.resetWindow = null;
		this.currentRiskFreeRate = textField_CashDepositRate.getText();
		this.mortgageMaturityMax = null;
		this.mortgageMaturityMin = null;
		this.probabilityOfSubPrime = null;
		this.probabilityOfHouseCostSevereBurden = null;
		this.mortgageMaturityMinProbability = null;
		this.userDefinedLAPFAssets = textField_UserDefinedFundAssets.getText();
		this.userDefinedLAPFLiabilities = textField_UserDefinedFundLiabilities
				.getText();
		this.opportunityCostOfFixedIncomeInvestment = textField_CashDepositRate
				.getText();
		this.lapfPeriodicLiabilityPaymentRate = textField_PeriodicLiabilityPayoutRate
				.getText();
		this.fundExpectationsBiasString = jTextField_FundExpectationsBias
				.getText();
		this.lapfPremiumContributionsRate = null;
		this.returnOnGlobalCredit = "0.16";
		this.annualExpectedPayout = null;
		this.annualExpectPayoutRebalancingRate = null;
		this.AA_Rated_MBS_Coupon = null;
		this.nonAA_Rated_MBS_Coupon = null;
		this.AA_Rated_MBS_Probability = null;
		/**
		 * In the full model with borrowers this will be set using a combo box
		 * for now default to null
		 */
		this.rateTypeString = null;

		this.initialCreditAssetvalue = Double
				.parseDouble(jTextField__CreditInitialValue.getText());
		this.cir_AlphaCreditAssetMeanReversion = Double
				.parseDouble(jTextField__CreditAlpha.getText());
		this.cir_ThetaCreditAssetMeanValue = Double
				.parseDouble(jTextField__CreditBenchmarkMean.getText());
		this.standardDeviationCreditAsset = Double
				.parseDouble(jTextField__CreditSigma.getText());

		this.initialTraditionalAssetvalue = Double
				.parseDouble(jTextField__initialTraditionalAssetvalue.getText());
		this.driftMeanTraditionalAsset = Double
				.parseDouble(jTextField__driftMeanTraditionalAsset.getText());
		this.standardDeviationTraditionalAsset = Double
				.parseDouble(jTextField__standardDeviationTraditionalAsset
						.getText());
		this.hestonLongTermVarianceTraditionalAsset = Double
				.parseDouble(jTextField__hestonMeanReversionRateTraditionalAsset
						.getText());
		this.hestonMeanReversionRateTraditionalAsset = Double
				.parseDouble(jTextField__hestonLongTermVarianceTraditionalAsset
						.getText());
		this.hestonVarianceVolatilityTraditionalAsset = Double
				.parseDouble(jTextField__hestonVarianceVolatilityTraditionalAsset
						.getText());

	}

	private void setParameters() {
		param = new Parameters();
		param.setAA_Rated_MBS_Coupon(AA_Rated_MBS_Coupon);
		param.setAA_Rated_MBS_Probability(AA_Rated_MBS_Probability);

		param.setSeniorTrancheQualityString(seniorTrancheQualityString);
		param.setSeniorTrancheCouponAndDefault(seniourTrancheCoupon,
				seniourTrancheDefaultRate);
		param.setMezzTrancheQualityString(mezzTrancheQualityString);
		param.setMezTrancheCouponAndDefault(mezzTrancheCoupon,
				mezzTrancheDefaultRate);
		param.setJuniorTrancheQualityString(juniorTrancheQualityString);
		param.setJuniorTrancheCouponAndDefault(juniourTrancheCoupon,
				juniourTrancheDefaultRate);
		param.setPerTrancheMBSCoupns(citibankResearchMBSCouponsList);
		param.setPerTrancheMBSDefaults(citibankResearchMBSDefaultsList);
		param.setSecuritisationRateDecisionHorizon(securitisationRateDecisionHorizon);
		param.setAnnualExpectedPayout(annualExpectedPayout);
		param.setAnnualExpectPayoutRebalancingRate(annualExpectPayoutRebalancingRate);
		param.setAssetValueMax(assetValueMax);
		param.setAssetValueMin(assetValueMin);
		param.setAveIncomeMax(aveIncomeMax);
		param.setAveIncomeMin(aveIncomeMin);
		param.setBankAssetReturn(bankAssetReturn);
		param.setBankCount(bankCount);
		param.setBankLiabilityExpense(bankLiabilityExpense);
		param.setBankRegulatoryCapitalRatio(bankRegulatoryCapitalRatio);
		param.setBnksOnlyAnalysisString(bnksOnlyAnalysisString);
		param.setMultiPeriodBanks(multiPeriodAnalysis);
		param.setLoanResetYear(loanResetYear);
		param.setFullyIndexedContractRateSpread(this.fullyIndexedContractRateSpread);
		param.setGenericPostRateResetDefaultRate(this.genericPostRateResetDafaultRate);
		param.setGenericPostRateResetCoupon(this.genericPostRateResetCoupon);
		param.setBorrowerTypeString(borrowerTypeString);
		param.setCurrentRiskFreeRate(currentRiskFreeRate);
		param.setDefaultProbabilityBankExpectations(defaultProbabilityBankExpectations);
		param.setInterestSpreadExperimentString(interestSpreadExperimentString);
		param.setInternalSecuritiseString(internalSecuritiseString);
		param.setInvestorCount(investorCount);
		param.setLapfConstantContractualObligationsString(lapfConstantContractualObligationsString);
		param.setLapfMultiPeriodSolvancyModelString(lapfMultiPeriodSolvancyModelString);
		param.setLapfPeriodicLiabilityPaymentRate(lapfPeriodicLiabilityPaymentRate);
		param.setLapfPremiumContributionsRate(lapfPremiumContributionsRate);
		param.setLapfQuadraticCostFunctionString(lapfQuadraticCostFunctionString);
		param.setLapfRegulatorySolvancyRatio(lapfRegulatorySolvancyRatio);
		param.setModelPeriodSting(modelPeriodSting);
		param.setMortgageMaturityMax(mortgageMaturityMax);
		param.setMortgageMaturityMin(mortgageMaturityMin);
		param.setMortgageMaturityMinProbability(mortgageMaturityMinProbability);
		param.setNonAA_Rated_MBS_Coupon(nonAA_Rated_MBS_Coupon);
		param.setNumberOfConstituentsPerMBSIssuanse(numberOfConstituentsPerMBSIssuanse);
		param.setOpportunityCostOfIncomeInvestment(opportunityCostOfFixedIncomeInvestment);
		param.setPaymentScheduleString(paymentScheduleString);
		param.setPrimeMortgageLTV(primeMortgageLTV);
		param.setProbabilityOfEquityReturnExpectations(probabilityOfEquityReturnExpectations);
		param.setProbabilityOfHouseCostSevereBurden(probabilityOfHouseCostSevereBurden);
		param.setProbabilityOfSubPrime(probabilityOfSubPrime);
		param.setRateTypeString(rateTypeString);
		param.setRecoveryRateOnCreditAsset(recoveryRateOnCreditAsset);
		param.setRecoveryRateOnTraditionalAsset(recoveryRateOnTraditionalAsset);
		param.setResetWindow(resetWindow);
		param.setReturnOnGlobalCredit(returnOnGlobalCredit);
		param.setReturnOnGlobalEquity(returnOnGlobalEquity);
		param.setSecuritisationCostConstantFactor(securitisationCostConstantFactor);
		param.setSecuritisationRate(securitisationRate);
		param.setSecuritiseLinearString(securitiseLinearString);
		param.setSecuritiseString(securitiseString);
		param.setShortSellingString(shortSellingString);
		param.setSubprimeMortgageLTV(subprimeMortgageLTV);
		param.setTraditionalAssetVariationTypeString(traditionalAssetVariationTypeString);
		param.setUserAssetLiabilityDataInpuString(userAssetLiabilityDataInpuString);
		param.setUserDefinedBankAssets(userDefinedBankAssets);
		param.setUserDefinedBankLiabilities(userDefinedBankLiabilities);
		param.setUserDefinedLAPFAssets(userDefinedLAPFAssets);
		param.setUserDefinedLAPFLiabilities(userDefinedLAPFLiabilities);
		param.setLAPFTypeString(lapfTypeString);
		param.setFundsExpectationsString(fundsExpectationsString);
		param.setFundsExpectationsBiasString(fundExpectationsBiasString);
		param.setLAPFExpectationsIndex(fundExpectations);
		param.setLAPFTypeIndex(fundType);
		
		param.setBankTypeString(bankTypeString);
		param.setBankTypeIndex(bankType);
		param.setHeuristicSecuritisationModel(heuristicSecuritisationModel);
		param.setErevRothModelSecuritisationModel(erevRothModel);

		param.setNumberOfIterations(iterations);
		param.setPathLength(pathLenght);
		param.setNumberOfPaths(pathsPerIteration);
		param.setInitialCreditAssetvalue(initialCreditAssetvalue);
		param.setInitialTraditionalAssetvalue(initialTraditionalAssetvalue);
		param.setDriftMeanTraditionalAsset(driftMeanTraditionalAsset);
		param.setStandardDeviationCreditAsset(standardDeviationCreditAsset);
		param.setStandardDeviationTraditionalAsset(standardDeviationTraditionalAsset);
		param.setTimeShit_dt(timeShit_dt);
		param.setCir_AlphaCreditAssetMeanReversion(cir_AlphaCreditAssetMeanReversion);
		param.setCir_ThetaCreditAssetMeanValue(cir_ThetaCreditAssetMeanValue);
		param.setHestonLongTermVarianceTraditionalAsset(hestonLongTermVarianceTraditionalAsset);
		param.setHestonMeanReversionRateTraditionalAsset(hestonMeanReversionRateTraditionalAsset);
		param.setHestonVarianceVolatilityTraditionalAsset(hestonVarianceVolatilityTraditionalAsset);

		// System.out.println(param.toString());
	}

	private void createNewModel() {
		/**
		 * This method is used to create new models when the reload button is
		 * clicked The method: 1: creates a new model instance 2: applied the
		 * current CRTApplicationFrame to the model 3: adds the model to the JAS
		 * SimEngine
		 * 
		 */
		// TODO Auto-generated method stub
		model = new MarkoseDYangModel_V01();
		setModelFrame();
		this.modelObserver = new SecuritisationModelObserverJAS(this.model);
		eng.addModel(this.model);
		eng.addModel(this.modelObserver);
		this.repaint();
	}

	public void setModelFrame() {
		model.frame = this;
	}

	public void buildAgentWorldViewerWindow() {
		ColorMap colRange = new ColorRangeMap(64, 0, 200,
				java.awt.Color.magenta);
		// LayerDblGridDrawer dGrid = new LayerDblGridDrawer("Survival",
		// model.getWorld().temperatureLayer, colRange);
		LayerObjGridDrawer dSpace = new LayerObjGridDrawer("Economic Agents",
				model.getWorld().agentLayer);
		lsf_WorldChartContainer = new LayeredSurfaceFrame(
				model.getWorld().xSize, model.getWorld().ySize, 4);
		lsp_WorldChartContainerPanel = new LayeredSurfacePanel(
				model.getWorld().xSize, model.getWorld().ySize, 4);
		// lsf_WorldChartContainer.addLayer(dGrid);
		lsf_WorldChartContainer.addLayer(dSpace);

		// lsp_WorldChartContainerPanel.addLayer(dGrid);
		lsp_WorldChartContainerPanel.addLayer(dSpace);

		panel_WorldChartContainer = new JPanel();
		panel_WorldChartContainer.add(this.modelObserver.getDisplayPanel());
		addWorldChartContainer();
		getWorldChartContainer();

	}

	public void addWorldChartContainer() {
		int seed = 769003537 + reloads;
		String name = new String("name_" + seed);
		panel_World.add(panel_WorldChartContainer, name);
		// panel_World.repaint();
	}

	public JPanel getWorldChartContainer() {
		// System.out.println(panel_World.toString());
		return panel_World;

	}

	public void clearAllActiveGraphicsPanels() {
		/**
		 * This method is used to clear all active graphics panels
		 */
		if (panel_WorldChartContainer != null) {
			panel_WorldChartContainer.repaint();
		}
	}

	public void setDefaultBankNamesList() {
		this.bankNamesList = new String[] {
				"BANK OF AMERICA, NATIONAL ASSOCIATION", "JPMORGAN CHASE BANK",
				"WELLS FARGO BANK, NATIONAL ASSOCIATION",
				"WACHOVIA BANK, NATIONAL ASSOCIATION", "CITIBANK, N.A.",
				"SUNTRUST BANK", "HSBC BANK USA", "REGIONS BANK",
				"FIFTH THIRD BANK", "BANK OF THE WEST",
				"M&I MARSHALL & ILSLEY BANK", "MERRILL LYNCH BANK USA",
				"NORTH FORK BANK", "COMPASS BANK", "FIFTH THIRD BANK",
				"HIBERNIA NATIONAL BANK",
				"ASSOCIATED BANK, NATIONAL ASSOCIATION",
				"FIRSTBANK PUERTO RICO", "COMMERCE BANK, NATIONAL ASSOCIATION",
				"FREMONT INVESTMENT & LOAN", "FIRST REPUBLIC BANK",
				"BANCO POPULAR DE PUERTO RICO",
				"FIRST-CITIZENS BANK & TRUST COMPANY",
				"ZIONS FIRST NATIONAL BANK", "ARVEST BANK",
				"IRWIN UNION BANK AND TRUST COMPANY",
				"WESTERNBANK PUERTO RICO", "UNITED BANK, INC.",
				"PNC BANK, NATIONAL ASSOCIATION",
				"KEYBANK NATIONAL ASSOCIATION",
				"HUNTINGTON NATIONAL BANK, THE", "DORAL BANK" };
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Methods
	// Related to the
	// Charts>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private JFreeChart getBankAssetVsLiabilityChart() {
		this.aggregateBankAssets = new TimeSeries("Bank Asset Accumulation",
				Millisecond.class);
		this.aggregateBankLiabilities = new TimeSeries(
				"Bank Liability Accumulation", Millisecond.class);
		this.dataSetBankAssetLiabilities = new TimeSeriesCollection();
		this.dataSetBankAssetLiabilities.addSeries(aggregateBankAssets);
		this.dataSetBankAssetLiabilities.addSeries(aggregateBankLiabilities);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Aggregate Asset and Liability Accumulation by Banks",
				"Time Period (Years)", "US$bn ", dataSetBankAssetLiabilities, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Aggregate Asset and Liability Accumulation by Banks",
				new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getBankEquityChart() {
		aggregateBankEquity = new TimeSeries("Bank Equity Accumulation",
				Millisecond.class);
		TimeSeriesCollection dataSetBankEquity = new TimeSeriesCollection(
				aggregateBankEquity);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Aggregate Equity Accumulation by Banks", "Time Period (Years)",
				"US$bn ", dataSetBankEquity, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Aggregate Equity Accumulation by Banks", new java.awt.Font(
						"SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getBankCapitalAccumulationChart() {
		aggregateBankCapitalAccumulation = new TimeSeries(
				"Bank Capital Accumulation", Millisecond.class);
		TimeSeriesCollection dataSetBankCapitalAccumulation = new TimeSeriesCollection(
				aggregateBankCapitalAccumulation);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Aggregate Capital Accumulation Accumulation by Banks",
				"Time Period (Years)", "US$bn ", dataSetBankCapitalAccumulation, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Aggregate Capital Accumulation Accumulation by Banks",
				new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getBankAverageSecuritisationRateChart() {
		averageBankSecuritisationRate = new TimeSeries(
				"Bank Average Securitisation Rate", Millisecond.class);
		TimeSeriesCollection dataSetBankCapitalAccumulation = new TimeSeriesCollection(
				averageBankSecuritisationRate);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Average Securitisation Rate of Banks", "Time Period (Years)", "rate ",
				dataSetBankCapitalAccumulation, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Average Securitisation Rate of Banks", new java.awt.Font(
						"SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getBankFragilityChart() {
		aggregateSurvivingBanks = new DefaultCategoryDataset();
		aggregateFailedBanks = new DefaultCategoryDataset();

		CategoryItemRenderer renderer = new BarRenderer();
		CategoryItemRenderer renderer2 = new LineAndShapeRenderer();

		// renderer.setLabelGenerator(generator);
		renderer.setItemLabelsVisible(true);
		
		CategoryPlot plot = new CategoryPlot();
		plot.setDataset(aggregateSurvivingBanks);
		plot.setRenderer(renderer);
		plot.setDataset(1, aggregateFailedBanks);
		plot.setRenderer(1, renderer2);

		// final ValueAxis rangeAxis2 = new NumberAxis("Axis 2");
		// plot.setRangeAxis(1, rangeAxis2);

		plot.setDomainAxis(new CategoryAxis("Time Period (Years)"));
		plot.setRangeAxis(new NumberAxis("Number of Banks"));
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		plot.getDomainAxis().setCategoryLabelPositions(
				CategoryLabelPositions.UP_45);

		JFreeChart chart = new JFreeChart(plot);
		chart.setTitle("Banking Sector Fragility");
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Banking Sector Fragility", new java.awt.Font("SansSerif",
						java.awt.Font.BOLD, 14)));

		return chart;
	}

	private JFreeChart getBankOptimalSecuritisationRateChart() {
		focusBankOptimalSecuritisationRate = new XYSeries(
				"Bank Optimal Securitisation");
		XYSeriesCollection dataSetFocusBankOptimalSecuritisationRate = new XYSeriesCollection(
				focusBankOptimalSecuritisationRate);
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Focus Optimal Securitisation", "Securitisation Rate",
				"Capital Accumulation",
				dataSetFocusBankOptimalSecuritisationRate,
				PlotOrientation.VERTICAL, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Focus Optimal Securitisation", new java.awt.Font("SansSerif",
						java.awt.Font.BOLD, 14)));
		
		

		XYPlot plot = chart.getXYPlot();
		Shape circle = new Ellipse2D.Double(-2, -2, 4, 4);
		MyRenderer renderer = new MyRenderer(true, true, 21);
		plot.setRenderer(renderer);
		// renderer.setSeriesShape(0, circle);
		// renderer.setSeriesPaint(0, Color.red);
		// renderer.setUseFillPaint(true);
		// renderer.setSeriesShapesFilled(0, true);
		// renderer.setSeriesShapesVisible(0, true);
		// renderer.setUseOutlinePaint(true);
		// renderer.setSeriesOutlinePaint(0, Color.red);
		chart.setBackgroundPaint(Color.white);

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));

		
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getBankOptimalTranchesSecuritisationRateChart() {
		focusBankOptimalSecuritisationSnrTranche = new XYSeries(
				"Optimal Securitisation Senior Tranche");
		focusBankOptimalSecuritisationMezTranche = new XYSeries(
				"Optimal Securitisation Mezzenine Tranche");
		focusBankOptimalSecuritisationJnrTranche = new XYSeries(
				"Optimal Securitisation Junior Tranche");
		XYSeriesCollection dataSetFocusBankOptimalSecuritisationTrancheComparison = new XYSeriesCollection();
		dataSetFocusBankOptimalSecuritisationTrancheComparison
				.addSeries(focusBankOptimalSecuritisationSnrTranche);
		dataSetFocusBankOptimalSecuritisationTrancheComparison
				.addSeries(focusBankOptimalSecuritisationMezTranche);
		dataSetFocusBankOptimalSecuritisationTrancheComparison
				.addSeries(focusBankOptimalSecuritisationJnrTranche);
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Optimal Securitisation Tranche Comparison",
				"Securitisation Rate", "Capital Accumulation",
				dataSetFocusBankOptimalSecuritisationTrancheComparison,
				PlotOrientation.VERTICAL, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Optimal Securitisation Tranche Comparison", new java.awt.Font(
						"SansSerif", java.awt.Font.BOLD, 14)));
		XYPlot plot = chart.getXYPlot();
		Shape circle = new Ellipse2D.Double(-2, -2, 4, 4);
		MyRenderer renderer = new MyRenderer(true, true, 14);
		plot.setRenderer(renderer);
		// renderer.setSeriesShape(0, circle);
		// renderer.setSeriesPaint(0, Color.red);
		// renderer.setUseFillPaint(true);
		// renderer.setSeriesShapesFilled(0, true);
		// renderer.setSeriesShapesVisible(0, true);
		// renderer.setUseOutlinePaint(true);
		// renderer.setSeriesOutlinePaint(0, Color.red);
		chart.setBackgroundPaint(Color.white);

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		Number maximum = DatasetUtilities
				.findMaximumRangeValue(dataSetFocusBankOptimalSecuritisationTrancheComparison);
		ValueMarker max = new ValueMarker(maximum.floatValue());
		max.setPaint(Color.orange);
		max.setLabel("Highest Value");
		max.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		Number xValue = DatasetUtilities
				.findMinimumDomainValue(dataSetFocusBankOptimalSecuritisationTrancheComparison);
		for (int seriesIndex = 0; seriesIndex < dataSetFocusBankOptimalSecuritisationTrancheComparison
				.getSeriesCount(); seriesIndex++) {
			for (int itemIndex = 0; itemIndex < dataSetFocusBankOptimalSecuritisationTrancheComparison
					.getItemCount(seriesIndex); itemIndex++) {
				Number yValue = dataSetFocusBankOptimalSecuritisationTrancheComparison
						.getY(seriesIndex, itemIndex);
				if (yValue.equals(maximum)) {
					if (dataSetFocusBankOptimalSecuritisationTrancheComparison
							.getX(seriesIndex, itemIndex).floatValue() > xValue
							.floatValue())
						xValue = dataSetFocusBankOptimalSecuritisationTrancheComparison
								.getX(seriesIndex, itemIndex);
				}
			}
		}
		ValueMarker maxX = new ValueMarker(xValue.floatValue());
		maxX.setPaint(Color.red);
		maxX.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		plot.addDomainMarker(maxX, Layer.BACKGROUND);

		focusBankOptimalSecuritisationSnrTrancheMarker = new ValueMarker(0.79);
		focusBankOptimalSecuritisationSnrTrancheDomainMarker = new ValueMarker(12.573139382204488);
		focusBankOptimalSecuritisationMezTrancheMarker = new ValueMarker(0.4);
		focusBankOptimalSecuritisationMezTrancheDomainMarker = new ValueMarker(-154.82801232678577);
		focusBankOptimalSecuritisationJnrTrancheMarker = new ValueMarker(0.24);
		focusBankOptimalSecuritisationJnrTrancheDomainMarker = new ValueMarker(-271.0730211272806);
	
		focusBankOptimalSecuritisationSnrTrancheMarker.setPaint(Color.red);
		focusBankOptimalSecuritisationMezTrancheMarker.setPaint(Color.blue);
		focusBankOptimalSecuritisationJnrTrancheMarker.setPaint(Color.green);
		
		Font font = new java.awt.Font("SansSerif", java.awt.Font.BOLD, 11);
		focusBankOptimalSecuritisationSnrTrancheDomainMarker.setPaint(Color.red);
		focusBankOptimalSecuritisationSnrTrancheDomainMarker.setLabel("A Tranche");
		focusBankOptimalSecuritisationSnrTrancheDomainMarker.setLabelFont(font);
		focusBankOptimalSecuritisationSnrTrancheDomainMarker.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
		focusBankOptimalSecuritisationSnrTrancheDomainMarker.setLabelTextAnchor(TextAnchor.BASELINE_RIGHT);
		
		
		focusBankOptimalSecuritisationMezTrancheDomainMarker.setPaint(Color.blue);
		focusBankOptimalSecuritisationMezTrancheDomainMarker.setLabel("BB Tranche");
		focusBankOptimalSecuritisationMezTrancheDomainMarker.setLabelFont(font);
		focusBankOptimalSecuritisationMezTrancheDomainMarker.setLabelAnchor(RectangleAnchor.TOP);
		focusBankOptimalSecuritisationMezTrancheDomainMarker.setLabelTextAnchor(TextAnchor.BASELINE_RIGHT);
		
		focusBankOptimalSecuritisationJnrTrancheDomainMarker.setPaint(Color.green);
		focusBankOptimalSecuritisationJnrTrancheDomainMarker.setLabel("BB- Tranche");
		focusBankOptimalSecuritisationJnrTrancheDomainMarker.setLabelFont(font);
		focusBankOptimalSecuritisationJnrTrancheDomainMarker.setLabelAnchor(RectangleAnchor.TOP);
		focusBankOptimalSecuritisationJnrTrancheDomainMarker.setLabelTextAnchor(TextAnchor.BASELINE_RIGHT);


		plot.addDomainMarker(focusBankOptimalSecuritisationSnrTrancheMarker);
		plot.addDomainMarker(focusBankOptimalSecuritisationMezTrancheMarker);
		plot.addDomainMarker(focusBankOptimalSecuritisationJnrTrancheMarker);
		
		plot.addRangeMarker(focusBankOptimalSecuritisationSnrTrancheDomainMarker);
		plot.addRangeMarker(focusBankOptimalSecuritisationMezTrancheDomainMarker);
		plot.addRangeMarker(focusBankOptimalSecuritisationJnrTrancheDomainMarker);
		
		
		return chart;
	}

	private JFreeChart getCouponDefaultChart() {
		relationshipCouponToDefaultRates = new XYSeries(
				"Relationship Between Coupon and Default Rate");
		XYSeriesCollection dataSetRelationshipCouponToDefaultRates = new XYSeriesCollection(
				relationshipCouponToDefaultRates);
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Relationship Between Coupon and Default Rate", "Default Rate",
				"Coupon Rate", dataSetRelationshipCouponToDefaultRates,
				PlotOrientation.VERTICAL, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Relationship Between Coupon and Default Rate",
				new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}// relationshipCouponToDefaultRatesPopOut

	private JFreeChart getCouponDefaultChartPopOut() {
		relationshipCouponToDefaultRatesPopOut = new XYSeries(
				"Relationship Between Coupon and Default Rate");
		XYSeriesCollection dataSetRelationshipCouponToDefaultRatesPopOut = new XYSeriesCollection(
				relationshipCouponToDefaultRatesPopOut);
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Relationship Between Coupon and Default Rate", "Default Rate",
				"Coupon Rate", dataSetRelationshipCouponToDefaultRatesPopOut,
				PlotOrientation.VERTICAL, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Relationship Between Coupon and Default Rate",
				new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getFundAssetsvsLiabilitiesChart() {
		aggregateFundAssets = new TimeSeries("Fund Assets", Millisecond.class);
		aggregateFundLiabilities = new TimeSeries("Fund Liabilities",
				Millisecond.class);
		TimeSeriesCollection dataSetFundAssetsvsLiabilities = new TimeSeriesCollection();
		dataSetFundAssetsvsLiabilities.addSeries(aggregateFundAssets);
		dataSetFundAssetsvsLiabilities.addSeries(aggregateFundLiabilities);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Aggregate Fund Asset-Liability Management", "Time Period (Years)",
				"US$bn ", dataSetFundAssetsvsLiabilities, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Aggregate Fund Asset-Liability Management", new java.awt.Font(
						"SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getFundSurplusChart() {
		aggregateFundSurplus = new TimeSeries("Fund Surplus", Millisecond.class);
		TimeSeriesCollection dataSetFundSurplus = new TimeSeriesCollection(
				aggregateFundSurplus);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Aggregate Fund Surplus", "Time Period (Years)", "US$bn ",
				dataSetFundSurplus, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Aggregate Fund Surplus", new java.awt.Font("SansSerif",
						java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getMarketClearingMBSReturnChart() {

		marketClearingEquityReturnSeries = new TimeSeries(
				"Market Clearing Traditional Asset Returns", Millisecond.class);
		marketClearingMBSReturnSeries = new TimeSeries(
				"Market Clearing MBS Returns", Millisecond.class);
		TimeSeriesCollection dataSetMBSReturns = new TimeSeriesCollection();
		dataSetMBSReturns.addSeries(marketClearingMBSReturnSeries);
		dataSetMBSReturns.addSeries(marketClearingEquityReturnSeries);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Market Clearing Returns", "Time Period (Years)", "rate",
				dataSetMBSReturns, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Market Clearing Returns", new java.awt.Font("SansSerif",
						java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;

	}

	private JFreeChart getFundAssetAllocationChart() {
		aggregateEquityAllocation = new TimeSeries(
				"Traditional Asset Allocation", Millisecond.class);
		aggregateCreditAllocation = new TimeSeries("Credit Asset Allocation",
				Millisecond.class);
		aggregateCashAllocation = new TimeSeries("Risk Free Bond Allocation",
				Millisecond.class);
		TimeSeriesCollection dataSetFundAssetAllocation = new TimeSeriesCollection();
		dataSetFundAssetAllocation.addSeries(aggregateCreditAllocation);
		dataSetFundAssetAllocation.addSeries(aggregateEquityAllocation);
		dataSetFundAssetAllocation.addSeries(aggregateCashAllocation);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Aggregate Fund Asset Allocation", "Time Period (Years)",
				"asset weight", dataSetFundAssetAllocation, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Aggregate Fund Asset Allocation", new java.awt.Font(
						"SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart getFundPortfolioChart() {
		aggregateFundPortfolioReturns = new TimeSeries(
				"Fund Portfolio Returns", Millisecond.class);
		TimeSeriesCollection dataSetFundPortfolioReturns = new TimeSeriesCollection(
				aggregateFundPortfolioReturns);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Aggregate Fund Portfolio Returns", "Time Period (Years)", "US$bn ",
				dataSetFundPortfolioReturns, true, true, true);
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Aggregate Fund Portfolio Returns", new java.awt.Font(
						"SansSerif", java.awt.Font.BOLD, 14)));

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);

		return chart;
	}

	private JFreeChart geBullDeltaChart() {
		bullInvestorDelta = new DefaultCategoryDataset();
		CategoryItemRenderer renderer = new BarRenderer();

		// renderer.setLabelGenerator(generator);
		renderer.setItemLabelsVisible(true);

		CategoryPlot plot = new CategoryPlot();
		plot.setDataset(bullInvestorDelta);
		plot.setRenderer(renderer);

		// final ValueAxis rangeAxis2 = new NumberAxis("Axis 2");
		// plot.setRangeAxis(1, rangeAxis2);

		plot.setDomainAxis(new CategoryAxis("Iterations"));
		plot.setRangeAxis(new NumberAxis("Delta"));
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		plot.getDomainAxis().setCategoryLabelPositions(
				CategoryLabelPositions.UP_45);

		JFreeChart chart = new JFreeChart(plot);
		chart.setTitle("Bellman Algorithm Delta: Bull");
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Bellman Algorithm Delta: Bull", new java.awt.Font("SansSerif",
						java.awt.Font.BOLD, 14)));

		return chart;
	}

	private JFreeChart geBearDeltaChart() {
		bearInvestorDelta = new DefaultCategoryDataset();
		CategoryItemRenderer renderer = new BarRenderer();

		// renderer.setLabelGenerator(generator);
		renderer.setItemLabelsVisible(true);

		CategoryPlot plot = new CategoryPlot();
		plot.setDataset(bearInvestorDelta);
		plot.setRenderer(renderer);

		// final ValueAxis rangeAxis2 = new NumberAxis("Axis 2");
		// plot.setRangeAxis(1, rangeAxis2);

		plot.setDomainAxis(new CategoryAxis("Iterations"));
		plot.setRangeAxis(new NumberAxis("Delta"));
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		plot.getDomainAxis().setCategoryLabelPositions(
				CategoryLabelPositions.UP_45);

		JFreeChart chart = new JFreeChart(plot);
		chart.setTitle("Bellman Algorithm Delta: Bear");
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Bellman Algorithm Delta: Bear", new java.awt.Font("SansSerif",
						java.awt.Font.BOLD, 14)));

		return chart;
	}

	private JFreeChart gePassiveDeltaChart() {
		passiveInvestorDelta = new DefaultCategoryDataset();
		CategoryItemRenderer renderer = new BarRenderer();

		// renderer.setLabelGenerator(generator);
		renderer.setItemLabelsVisible(true);

		CategoryPlot plot = new CategoryPlot();
		plot.setDataset(passiveInvestorDelta);
		plot.setRenderer(renderer);

		// final ValueAxis rangeAxis2 = new NumberAxis("Axis 2");
		// plot.setRangeAxis(1, rangeAxis2);

		plot.setDomainAxis(new CategoryAxis("Iterations"));
		plot.setRangeAxis(new NumberAxis("Delta"));
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		plot.getDomainAxis().setCategoryLabelPositions(
				CategoryLabelPositions.UP_45);

		JFreeChart chart = new JFreeChart(plot);
		chart.setTitle("Bellman Algorithm Delta: Passive");
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Bellman Algorithm Delta: Passive", new java.awt.Font(
						"SansSerif", java.awt.Font.BOLD, 14)));

		return chart;
	}

	private JFreeChart geRationalDeltaChart() {
		rationalInvestorDelta = new DefaultCategoryDataset();
		CategoryItemRenderer renderer = new BarRenderer();
		//CategoryToolTipGenerator toolTipGen = new CategoryToolTipGenerator(rationalInvestorDelta);

		// renderer.setLabelGenerator(generator);
		renderer.setItemLabelsVisible(true);

		CategoryPlot plot = new CategoryPlot();
		plot.setDataset(rationalInvestorDelta);
		plot.setRenderer(renderer);
		//renderer.setToolTipGenerator(toolTipGen);

		plot.setDomainAxis(new CategoryAxis("Iterations"));
		plot.setRangeAxis(new NumberAxis("Delta"));
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		plot.getDomainAxis().setCategoryLabelPositions(
				CategoryLabelPositions.UP_45);

		JFreeChart chart = new JFreeChart(plot);
		chart.setTitle("Bellman Algorithm Delta: Rational");
		chart.setBackgroundPaint(Color.lightGray);
		chart.setTitle(new org.jfree.chart.title.TextTitle(
				"Bellman Algorithm Delta: Rational", new java.awt.Font(
						"SansSerif", java.awt.Font.BOLD, 14)));

		return chart;
	}

	public void showUpdatedModelState() {

		date = new Date(107, 05, 01, (int) Sim.getAbsoluteTime(), 0, 0);

		/**
		 * Count the surviving banks
		 */
		// for (int i = 0; i < model.bankList.size(); i++) {
		// if (model.bankList.get(i).getStatus() != CorporateStatus.failed) {
		// survivingBanks++;
		// }
		// }
		//failedBanks = model.econ.getNumberOfFailedBanks();
		failedBanks = 0;
		for (int i = 0; i < model.bankList.size(); i++) {
			if (model.bankList.get(i).getStatus() == CorporateStatus.failed) {
				failedBanks++;
			}
		}
		survivingBanks = model.bankList.size() - failedBanks;
//		 System.out.println("Surviving Banks count: " + survivingBanks);
//		securitisationDataArrayUpdate = new double[survivingBanks];
		securitisationDataArrayUpdate = new double[model.bankList.size()];

		// failedBanks = model.bankList.size() - survivingBanks;

		previousAssets = aggregateBankAssetsUpdate;
		previousLiabilities = aggregateBankLiabilitiesUpdate;
		long time = Sim.getAbsoluteTime();
//		private double factorAssets = 36.4;
//		private double factorLiabilities = 36.4;
//		private double factorStart = 2.80;
//		private double factorIncrement = 0.54;
		double add = 0;
		if(time > 0){
			if(time == 1){
				add = factorAssets;
			} else {
				add = factorAssets * (factorStart+0.54);
//				add = 0;
			}
		}
		
		/**
		 * The following collates the banking sector aggregation data
		 */
//		aggregateBankAssetsUpdate = 0;
//		aggregateBankLiabilitiesUpdate = 0;
//		aggregateBankEquityUpdate = 0;// bank equity
//		aggregateBankCapitalAccumulationUpdate= 0;
		
		for (int i = 0; i < model.bankList.size(); i++) {
			if (model.bankList.get(i).getStatus() != CorporateStatus.failed) {
				aggregateBankAssetsUpdate += model.bankList.get(i)
						.getTotalAssets();
				aggregateBankLiabilitiesUpdate += model.bankList.get(i)
						.getTotalLiabilities();
				aggregateBankEquityUpdate += (model.bankList.get(i)
						.getTotalAssets() - model.bankList.get(i)
						.getTotalLiabilities());// bank equity
				aggregateBankCapitalAccumulationUpdate += model.bankList.get(i)
						.getCapitalRetentionOrinjection();
				securitisationDataArrayUpdate[i] = model.bankList.get(i)
						.getSecuritisationRate();
			}
			
			/**
			 * Here if the focus bank is the bank being looked at in the bank
			 * population list set the focusbank XYSeries item to be plotted to
			 * that of the bank from the list
			 * 
			 */
			if (this.focusBankString == model.bankList.get(i).getBankName()) {

				if (model.bankList.get(i).focusBankOptimalSecRateArray != null) {

					/**
					 * The following is to update the main optimal
					 * securitisation chart
					 */
					fbSecuritisationRateDataArrayUpdate = model.bankList.get(i).focusBankOptimalSecRateArray
							.clone();
					fbCapitalAccumulationDataArrayUpdate = model.bankList
							.get(i).focusBankOptimalSecCapitalAccumulationArray
							.clone();

					updateFocusBankOptimalSecuritisation(
							fbSecuritisationRateDataArrayUpdate,
							fbCapitalAccumulationDataArrayUpdate);

					/**
					 * The following is to update the tranche comparison optimal
					 * securitisation chart
					 */
					// senior tranche
					fbSnrSecuritisationRateDataArrayUpdate = model.bankList
							.get(i).focusBankOptimalSecRateArraySnrTranche
							.clone();
					fbSnrCapitalAccumulationDataArrayUpdate = model.bankList
							.get(i).focusBankOptimalSecCapitalAccumulationArraySnrTranche
							.clone();

					updateFocusBankOptimalSecuritisationTrancheComparisonSnrTranche(
							fbSnrSecuritisationRateDataArrayUpdate,
							fbSnrCapitalAccumulationDataArrayUpdate);

					// mezzenine tranche
					fbMezSecuritisationRateDataArrayUpdate = model.bankList
							.get(i).focusBankOptimalSecRateArrayMezTranche
							.clone();
					fbMezCapitalAccumulationDataArrayUpdate = model.bankList
							.get(i).focusBankOptimalSecCapitalAccumulationArrayMezTranche
							.clone();

					updateFocusBankOptimalSecuritisationTrancheComparisonMezTranche(
							fbMezSecuritisationRateDataArrayUpdate,
							fbMezCapitalAccumulationDataArrayUpdate);

					// junior tranche
					fbJnrSecuritisationRateDataArrayUpdate = model.bankList
							.get(i).focusBankOptimalSecRateArrayJnrTranche
							.clone();
					fbJnrCapitalAccumulationDataArrayUpdate = model.bankList
							.get(i).focusBankOptimalSecCapitalAccumulationArrayJnrTranche
							.clone();

					updateFocusBankOptimalSecuritisationTrancheComparisonJnrTranche(
							fbJnrSecuritisationRateDataArrayUpdate,
							fbJnrCapitalAccumulationDataArrayUpdate);

					repaintFocusBankTrancheComparisonSecRate();
				}

			}// end focus bank xyseries retrieval if
		}// end bank data collection for
		
//		if(failedBanks < model.bankList.size()){
//			if(failedBanks != 0){
//				aggregateBankAssetsUpdate += add*(failedBanks/model.bankList.size()); 
//			}
//			else{
//				aggregateBankAssetsUpdate += add;
//			}
//		}
		
		if(failedBanks >= model.bankList.size()){
			aggregateBankAssetsUpdate = 0;
			aggregateBankLiabilitiesUpdate = 0;
			aggregateBankEquityUpdate = 0;
		}

		averageSecuritisationRateUpdate = Means.arithmeticMean(securitisationDataArrayUpdate);

		for (int a = 0; a < model.MDPMarkovAgentList.size(); a++) {
			if ("Bull" == model.MDPMarkovAgentList.get(a).getAgentTemprement()) {
				if (((MDPInvestorAgent) model.MDPMarkovAgentList.get(a))
						.getDecisionDelta() != null) {
					/**
					 * The following is to update the bull MDP delta chart
					 */
					bullDeltaArrayUpdate = ((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta();
					bullDeltaTickArrayUpdate = new int[((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta().length];
					for (int i = 0; i < ((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta().length; i++) {
						bullDeltaTickArrayUpdate[i] = i;
					}
					updateBullDeltaArray(bullDeltaTickArrayUpdate,
							bullDeltaArrayUpdate);
				}
			}//
			if ("Bear" == model.MDPMarkovAgentList.get(a).getAgentTemprement()) {
				if (((MDPInvestorAgent) model.MDPMarkovAgentList.get(a))
						.getDecisionDelta() != null) {
					/**
					 * The following is to update the bull MDP delta chart
					 */
					bearDeltaArrayUpdate = ((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta();
					bearDeltaTickArrayUpdate = new int[((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta().length];
					for (int i = 0; i < ((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta().length; i++) {
						bearDeltaTickArrayUpdate[i] = i;
					}
					updateBearDeltaArray(bearDeltaTickArrayUpdate,
							bearDeltaArrayUpdate);
				}
			}//
			if ("Passive" == model.MDPMarkovAgentList.get(a)
					.getAgentTemprement()) {
				if (((MDPInvestorAgent) model.MDPMarkovAgentList.get(a))
						.getDecisionDelta() != null) {
					/**
					 * The following is to update the bull MDP delta chart
					 */
					passiveDeltaArrayUpdate = ((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta();
					passiveDeltaTickArrayUpdate = new int[((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta().length];
					for (int i = 0; i < ((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta().length; i++) {
						passiveDeltaTickArrayUpdate[i] = i;
					}
					updatePassiveDeltaArray(passiveDeltaTickArrayUpdate,
							passiveDeltaArrayUpdate);
				}
			}//
			if ("Rational" == model.MDPMarkovAgentList.get(a)
					.getAgentTemprement()) {
				if (((MDPInvestorAgent) model.MDPMarkovAgentList.get(a))
						.getDecisionDelta() != null) {
					/**
					 * The following is to update the bull MDP delta chart
					 */
					rationalDeltaArrayUpdate = ((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta();
					rationalDeltaTickArrayUpdate = new int[((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta().length];
					for (int i = 0; i < ((MDPInvestorAgent) model.MDPMarkovAgentList
							.get(a)).getDecisionDelta().length; i++) {
						rationalDeltaTickArrayUpdate[i] = i;
					}
					updateRationalDeltaArray(rationalDeltaTickArrayUpdate,
							rationalDeltaArrayUpdate);
				}
			}//
			repaintDeltas();
		}//

		/**
		 * Now Collate data for the funds in the model
		 * 
		 */

		mbsAllocationDataArrayUpdate = new double[model.lapfList.size()];
		equityAllocationDataArrayUpdate = new double[model.lapfList.size()];
		RiskFreeBondAllocationDataArrayUpdate = new double[model.lapfList
				.size()];
		fundReturnsDataArrayUpdate = new double[model.lapfList.size()];

		for (int i = 0; i < model.lapfList.size(); i++) {
			aggregateFundAssetsUpdate += model.lapfList.get(i)
					.getTotalNotionalAssets();
			aggregateFundLiabilitiesUpdate += model.lapfList.get(i)
					.getLiabilities();// fund
			// assets
			aggregateFundSurplusUpdate += model.lapfList.get(i)
					.getFundingSurplus();// fund
			// surplus
			mbsAllocationDataArrayUpdate[i] = model.lapfList.get(i)
					.getOptimalCreditAssetWieght();
			equityAllocationDataArrayUpdate[i] = model.lapfList.get(i)
					.getOptimalEquityAssetAllocation();
			RiskFreeBondAllocationDataArrayUpdate[i] = model.lapfList.get(i)
					.getOptimalCashAssetWieght();
			fundReturnsDataArrayUpdate[i] = model.lapfList.get(i)
					.getOptimalAssetAllocationStrtegyReturn();
		}
		aggregateFundMBSAllocationUpdate = Means
				.arithmeticMean(mbsAllocationDataArrayUpdate);// fund asset
																// allocation
		aggregateFundEquityAllocationUpdate = Means
				.arithmeticMean(equityAllocationDataArrayUpdate);

		aggregateFundRiskFreeBondAllocationUpdate = Means
				.arithmeticMean(RiskFreeBondAllocationDataArrayUpdate);

		aggregateFundPortfolioReturnsUpdate = Means
				.geometricMean(fundReturnsDataArrayUpdate);

		/**
		 * Now Collate data from the global dealer
		 */

		mbsReturnDataArrayUpdate = new double[model.brokerDealMaketMakerList
				.size()];
		equityReturnDataArrayUpdate = new double[model.brokerDealMaketMakerList
				.size()];

		for (int i = 0; i < model.brokerDealMaketMakerList.size(); i++) {

			mbsReturnDataArrayUpdate[i] = model.brokerDealMaketMakerList.get(i)
					.getMarketMakerMBSRate();
			equityReturnDataArrayUpdate[i] = model.brokerDealMaketMakerList
					.get(i).getReturnOnGlobalEquity();
		}

		marketClearingEquityReturnUpdate = Means
				.geometricMean(equityReturnDataArrayUpdate);
		marketClearingMBSReturnUpdate = Means
				.geometricMean(mbsReturnDataArrayUpdate);

		/**
		 * Now to update the series and charts
		 * 
		 */
		Millisecond ms = new Millisecond(date);

		// Dealer Market Maker
		this.marketClearingEquityReturnSeries.add(ms,
				marketClearingEquityReturnUpdate);
		this.marketClearingMBSReturnSeries.add(ms,
				marketClearingMBSReturnUpdate);

		// Banks
		this.aggregateBankAssets.add(ms, aggregateBankAssetsUpdate);
		this.aggregateBankLiabilities.add(ms, aggregateBankLiabilitiesUpdate);
		this.aggregateBankCapitalAccumulation.add(ms,
				aggregateBankCapitalAccumulationUpdate);
		this.averageBankSecuritisationRate.add(ms,
				averageSecuritisationRateUpdate);
		this.aggregateSurvivingBanks.addValue(survivingBanks,
				"Surviving Banks",
				Integer.toString((int) Sim.getAbsoluteTime()));

		this.aggregateFailedBanks.addValue((failedBanks), "Failed Banks",
				Integer.toString((int) Sim.getAbsoluteTime()));

		// Funds
		this.aggregateFundAssets.add(ms, aggregateFundAssetsUpdate);
		this.aggregateFundLiabilities.add(ms, aggregateFundLiabilitiesUpdate);
		this.aggregateFundSurplus.add(ms, aggregateFundSurplusUpdate);
		this.aggregateEquityAllocation.add(ms,
				aggregateFundEquityAllocationUpdate);
		this.aggregateCreditAllocation
				.add(ms, aggregateFundMBSAllocationUpdate);
		this.aggregateCashAllocation.add(ms,
				aggregateFundRiskFreeBondAllocationUpdate);
		System.gc();
		this.repaint();

	}

	public void clearFocusBankOptimalSecuritisation() {
		int items = this.focusBankOptimalSecuritisationRate.getItemCount();
		this.focusBankOptimalSecuritisationRate.delete(0, items);
	}

	public void buildOrUpdateCharts() {
		// panel_WorldChartContainer.add(model.getWorld());
		System.gc();
		this.repaint();

	}

	public void updateFocusBankOptimalSecuritisation(double[] x, double[] y) {

		this.focusBankOptimalSecuritisationRate.clear();
		double max = y[0];
		
		//print data to file
		printOptimalSecuritisationTranche("OptimalSecuritisation", x, y);


		for (int j = 0; j < x.length; j++) {
			
			if(y[j] > max){
				max = y[j];
				focusBankOptimalSecuritisationRateMaxUpdate = x[j];
				focusBankOptimalSecuritisationRateDomainUpdate = y[j];
				
			} 
			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			focusBankOptimalSecuritisationRate.add(x[j], y[j]);
			
		}
//		System.out.println(focusBankOptimalSecuritisationRateMaxUpdate);
//		System.out.println(focusBankOptimalSecuritisationRateDomainUpdate);
		this.panel_FocusBankSecRate.repaint();
	}

	public void updateFocusBankOptimalSecuritisationTrancheComparisonSnrTranche(
			double[] x, double[] y) {
		

		this.focusBankOptimalSecuritisationSnrTranche.clear();
		double max = y[0];
		
		//print data to file
		printOptimalSecuritisationTranche("OptimalSecuritisationSnrTranche", x, y);


		for (int j = 0; j < x.length; j++) {
			
			if(y[j] > max){
				max = y[j];
				focusBankOptimalSecuritisationSnrTrancheMaxUpdate = x[j];
				focusBankOptimalSecuritisationSnrTrancheMaxDomainUpdate = y[j];
				
			} 
			
			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			focusBankOptimalSecuritisationSnrTranche.add(x[j], y[j]);
		}
//		System.out.println(focusBankOptimalSecuritisationSnrTrancheMaxUpdate);
//		System.out.println(focusBankOptimalSecuritisationSnrTrancheMaxDomainUpdate);
//		this.panel_FocusBankSecRate.repaint();

	}

	public void updateFocusBankOptimalSecuritisationTrancheComparisonMezTranche(
			double[] x, double[] y) {

		this.focusBankOptimalSecuritisationMezTranche.clear();
		double max = y[0];
		
		//print data to file
		printOptimalSecuritisationTranche("OptimalSecuritisationMezTranche", x, y);

		for (int j = 0; j < x.length; j++) {
			
			if(y[j] > max){
				max = y[j];
				focusBankOptimalSecuritisationMezTrancheMaxUpdate = x[j];
				focusBankOptimalSecuritisationMezTrancheMaxDomainUpdate = y[j];
			} 
			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			focusBankOptimalSecuritisationMezTranche.add(x[j], y[j]);
		}
//		System.out.println(focusBankOptimalSecuritisationMezTrancheMaxUpdate);
//		System.out.println(focusBankOptimalSecuritisationMezTrancheMaxDomainUpdate);
		

	}

	public void updateFocusBankOptimalSecuritisationTrancheComparisonJnrTranche(
			double[] x, double[] y) {

		this.focusBankOptimalSecuritisationJnrTranche.clear();
		double max = y[0];
		
		

		//print data to file
		printOptimalSecuritisationTranche("OptimalSecuritisationJnrTranche", x, y);
		
		for (int j = 0; j < x.length; j++) {
			
			if(y[j] > max){
				max = y[j];
				focusBankOptimalSecuritisationJnrTrancheMaxUpdate = x[j];
				focusBankOptimalSecuritisationJnrTrancheMaxDomainUpdate = y[j];
			} 
			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			focusBankOptimalSecuritisationJnrTranche.add(x[j], y[j]);
		}
//		System.out.println(focusBankOptimalSecuritisationJnrTrancheMaxUpdate);
//		System.out.println(focusBankOptimalSecuritisationJnrTrancheMaxDomainUpdate);
		
	}
	
	public String printOptimalSecuritisationTranche(String trancheName, double[] x, double[] y){
		double Pja = 0;
		String output = "Tranche "+ trancheName + "\n";
		PrintWriter writer = null;
		now = calendar.getTime();
	    String strDate = sdfDate.format(now);
		String fileName = "Optimal Securitisation Tranche-"+ trancheName+"-";
		String fileExtention = ".csv";
		String fullFileName = fileName + strDate + fileExtention; 
//		try {
//			writer = new PrintWriter(fullFileName, "UTF-8");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		updateFocusBankOptimalSecuritisationTrancheComparisonJnrTranche(
//				fbJnrSecuritisationRateDataArrayUpdate,
//				fbJnrCapitalAccumulationDataArrayUpdate);
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(fullFileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < x.length; i++){
			output += "Securitisation Rate: ,"+ x[i] + ","+" Capital Accumulation: ,"+ y[i];
			output += "\n";
		}
		writer.println(output);
		writer.flush();
		writer.close();
		
		return output;
	}

	public void repaintFocusBankTrancheComparisonSecRate() {
		this.panel_FocusBankTranchesSecRate.repaint();
	}

	public void updateBullDeltaArray(int[] x, double[] y) {
		// this.bullInvestorDelta.clear();
		for (int j = 0; j < x.length; j++) {
			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			bullInvestorDelta.setValue(y[j], "Delta", Integer.toString(x[j]));
		}
	}

	public void updateRationalDeltaArray(int[] x, double[] y) {
		// this.rationalInvestorDelta.clear();
		for (int j = 0; j < x.length; j++) {
			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			rationalInvestorDelta.setValue(y[j], "Delta",
					Integer.toString(x[j]));
		}
	}

	public void updateBearDeltaArray(int[] x, double[] y) {
		// this.bearInvestorDelta.clear();
		for (int j = 0; j < x.length; j++) {
			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			bearInvestorDelta.setValue(y[j], "Delta", Integer.toString(x[j]));
		}
	}

	public void updatePassiveDeltaArray(int[] x, double[] y) {
		// this.bearInvestorDelta.clear();
		for (int j = 0; j < x.length; j++) {
			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			passiveInvestorDelta
					.setValue(y[j], "Delta", Integer.toString(x[j]));
		}
	}

	public void repaintDeltas() {
		System.gc();
		this.panel_MDPAgents.repaint();
	}

	public void updateCouponDefaultChart(ArrayList<Double> x,
			ArrayList<Double> y) {

		if (this.relationshipCouponToDefaultRates != null) {
			this.relationshipCouponToDefaultRates.clear();
		}

		for (int j = 0; j < x.size(); j++) {

			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			relationshipCouponToDefaultRates.add(x.get(j), y.get(j));
			// this.panel_RelationshipCouponTODefaultRate.repaint();
		}

	}

	public void updatePopOutCouponDefaultChart(ArrayList<Double> x,
			ArrayList<Double> y) {

		if (this.relationshipCouponToDefaultRatesPopOut != null) {
			this.relationshipCouponToDefaultRatesPopOut.clear();
		}

		for (int j = 0; j < x.size(); j++) {

			/**
			 * clear the content of the current series then add the new values
			 * for the current time period
			 */
			relationshipCouponToDefaultRatesPopOut.add(x.get(j), y.get(j));// inverse
																			// (Double)(1
																			// -
																			// x.get(j))
			// this.panel_RelationshipCouponTODefaultRate.repaint();
		}

	}

	private static class MyRenderer extends XYLineAndShapeRenderer {

		private List<Color> clut;

		public MyRenderer(boolean lines, boolean shapes, int n) {
			super(lines, shapes);
			clut = new ArrayList<Color>(n);
			for (int i = 0; i < n; i++) {
				clut.add(Color.getHSBColor((float) i / 2*n, 0, 0));
			}
		}

		@Override
		public Paint getItemFillPaint(int row, int column) {
			return clut.get(column);
		}
	}
}
