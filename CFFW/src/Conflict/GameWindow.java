package Conflict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import Company.Company;
import Conflict.SmokeStats.SmokeType;
import CorditeExpansion.Cord;
import CreateGame.SetupWindow;
import HexGrid.CalculateLOS;
import HexGrid.HexGrid;
import HexGrid.HexGrid.DeployedUnit;
import HexGrid.Shields.ShieldManager;
import Hexes.Hex;
import Hexes.HexWindow;
import Items.PersonalShield;
import Melee.MeleeCombatCalculator;
import Melee.MeleeManager;
import Melee.Window.MeleeCombatWindow;
import Shoot.Shoot;
import Trooper.Trooper;
import Unit.Unit;
import UtilityClasses.HexGridUtility;
import UtilityClasses.PCUtility;
import Vehicle.VehicleManager;
import Vehicle.Windows.VehicleCombatWindow;

import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.lang.reflect.Array;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.LayoutStyle.ComponentPlacement;

import Actions.Spot;
import Artillery.AlertWindow;
import Artillery.FireMission;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JSpinner;
import javax.swing.SwingWorker;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class GameWindow implements Serializable {
	public static int hexSize = 20;
	public ArrayList<Company> companies;
	public ArrayList<Unit> initiativeOrder = new ArrayList<Unit>();
	public Game game;
	public String visibility = "Good Visibility";
	public ArrayList<Hex> hexes = new ArrayList<Hex>();
	public CloseQuartersBattle cqb = new CloseQuartersBattle(this);
	public ConflictLog conflictLog;
	public static GameWindow gameWindow;
	
	public SetupWindow setupWindow;
	public JList listIniativeOrder;
	public JFrame f = null;
	public int activeUnit = 0;
	private JLabel lblActiveUnit;
	private JLabel lblRound;
	private JLabel lblPhase;
	private JLabel lblActions;
	public JComboBox comboBoxVisibility;
	private JButton btnStartingSpotTest;
	private JSpinner spinnerInitMod;
	private JComboBox comboBoxInitModSide;
	private JComboBox comboBoxValueMod;
	private JSpinner spinnerValueMod;
	private JSpinner spinnerActiveUnit;
	public HexGrid hexGrid;
	public boolean cqbWindowOpen = false;
	public OpenUnit currentlyOpenUnit;
	private JSpinner spinnerHexSize;
	public int hexCols;
	public int hexRows;
	private JLabel lblWind;

	public VehicleCombatWindow vehicleCombatWindow;
	
	public AlertWindow startedLoading;
	public AlertWindow finishedLoading;
	private JSpinner spinnerNextActions;
	
	
	public GameWindow() { gameWindow = this; } // Empty constructor for testing
	
	/**
	 * @wbp.parser.constructor
	 */
	public GameWindow(ArrayList<Company> companiesFromSetupWindow, SetupWindow setupWindow, Game game, boolean openUnit,
			int hexRows, int hexCols) {
		
		if(game.shieldManager == null)
			game.shieldManager = new ShieldManager();
		
		this.hexCols = hexCols;
		this.hexRows = hexRows;
		this.game = game;
		this.gameWindow = this;
		this.conflictLog = new ConflictLog();
		this.setupWindow = setupWindow;
		this.companies = companiesFromSetupWindow;
		conflictLog.addNewLine("     Round: " + game.getRound() + " Phase: " + game.getPhase());
		f = new JFrame("Conflict");
		f.setSize(700, 545);

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				// frame.dispose();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 196, 664, 302);

		listIniativeOrder = new JList();
		listIniativeOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Unit unit = initiativeOrder.get(listIniativeOrder.getSelectedIndex());
				// System.out.println("Open unit moral: "+unit.moral);

				if (currentlyOpenUnit != null)
					currentlyOpenUnit.f.dispose();
				currentlyOpenUnit = new OpenUnit(unit, gameWindow, listIniativeOrder.getSelectedIndex());
			}
		});
		scrollPane.setViewportView(listIniativeOrder);

		JButton btnRefreshUnits = new JButton("Add New Units");
		btnRefreshUnits.setBounds(10, 39, 128, 25);
		btnRefreshUnits.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				refreshUnits();

			}
		});

		lblActiveUnit = new JLabel("Active Unit: ");
		lblActiveUnit.setBounds(328, 11, 233, 17);
		lblActiveUnit.setFont(new Font("Calibri", Font.BOLD, 13));

		lblRound = new JLabel("Round:");
		lblRound.setBounds(10, 11, 113, 17);
		lblRound.setFont(new Font("Calibri", Font.BOLD, 13));

		lblPhase = new JLabel("Phase:");
		lblPhase.setBounds(129, 11, 113, 17);
		lblPhase.setFont(new Font("Calibri", Font.BOLD, 13));

		lblActions = new JLabel("Actions:");
		lblActions.setBounds(248, 11, 113, 17);
		lblActions.setFont(new Font("Calibri", Font.BOLD, 13));

		JButton btnD = new JButton("D100");
		btnD.setBounds(148, 40, 69, 23);
		btnD.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Random rand = new Random();
				int roll = rand.nextInt(100) + 1;
				conflictLog.addNewLine("Roll: " + roll);
			}
		});

		comboBoxVisibility = new JComboBox();
		comboBoxVisibility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				visibility = comboBoxVisibility.getSelectedItem().toString();
				game.setDaylightCondition(visibility);

			}
		});
		comboBoxVisibility.setBounds(219, 162, 193, 23);
		comboBoxVisibility.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

			}
		});
		comboBoxVisibility.setModel(new DefaultComboBoxModel(
				new String[] { "Good Visibility", "Dusk", "Night - Full Moon ", "Night - Half Moon", "Night - No Moon",
						"Smoke/Fog/Haze/Overcast", "Dusk - Smoke/Fog/Haze/Overcast", "Night - Smoke/Fog/Haze/Overcast",
						"No Visibility - Heavy Fog - White Out" }));

		JLabel lblVisibility = new JLabel("Visibility:");
		lblVisibility.setBounds(152, 165, 59, 17);
		lblVisibility.setFont(new Font("Calibri", Font.BOLD, 13));

		btnStartingSpotTest = new JButton("General Spot Test");
		btnStartingSpotTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnStartingSpotTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						// System.out.println("Spot1");
						spotCycle();
						// System.out.println("Spot2");
						return null;
					}

					@Override
					protected void done() {

						// System.out.println("Done");
						gameWindow.conflictLog.addQueuedText();
						gameWindow.conflictLog.addNewLine("General Spot Test Completed");
					}

				};

				worker.execute();

			}
		});
		btnStartingSpotTest.setBounds(546, 162, 128, 23);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setInitiativeOrder(companies);
		refreshInitiativeOrder();

		// Sets next active unit
		listIniativeOrder.setSelectedIndex(activeUnit);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(btnStartingSpotTest);
		f.getContentPane().add(scrollPane);
		f.getContentPane().add(lblVisibility);
		f.getContentPane().add(comboBoxVisibility);
		f.getContentPane().add(btnRefreshUnits);
		f.getContentPane().add(lblRound);
		f.getContentPane().add(lblPhase);
		f.getContentPane().add(lblActions);
		f.getContentPane().add(lblActiveUnit);
		f.getContentPane().add(btnD);

		JButton btnNewButton = new JButton("Roll Init Order");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				setInitiativeOrder(companies);

				for (Unit unit : initiativeOrder) {
					if (unit.side.equals(comboBoxInitModSide.getSelectedItem()))
						unit.initiative += (int) spinnerInitMod.getValue();
				}

				rollInitiativeOrder();
				refreshInitiativeOrder();
			}
		});
		btnNewButton.setBounds(256, 70, 128, 23);
		f.getContentPane().add(btnNewButton);

		JLabel lblInitMod = new JLabel("Init Mod:");
		lblInitMod.setFont(new Font("Calibri", Font.BOLD, 13));
		lblInitMod.setBounds(133, 73, 59, 17);
		f.getContentPane().add(lblInitMod);

		spinnerInitMod = new JSpinner();
		spinnerInitMod.setBounds(202, 71, 44, 20);
		f.getContentPane().add(spinnerInitMod);

		comboBoxInitModSide = new JComboBox();
		comboBoxInitModSide.setModel(new DefaultComboBoxModel(new String[] { "BLUFOR", "OPFOR", "IND" }));
		comboBoxInitModSide.setSelectedIndex(0);
		comboBoxInitModSide.setBounds(48, 68, 75, 23);
		f.getContentPane().add(comboBoxInitModSide);

		JLabel lblSide = new JLabel("Side:");
		lblSide.setFont(new Font("Calibri", Font.BOLD, 13));
		lblSide.setBounds(10, 72, 59, 17);
		f.getContentPane().add(lblSide);

		JLabel lblValueMod = new JLabel("Stat Mod:");
		lblValueMod.setFont(new Font("Calibri", Font.BOLD, 13));
		lblValueMod.setBounds(10, 104, 59, 17);
		f.getContentPane().add(lblValueMod);

		JButton btnNewButton_1 = new JButton("Org.");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				for (Unit unit : initiativeOrder) {

					if (unit.side.equals(comboBoxValueMod.getSelectedItem())) {

						if (unit.organization + (int) spinnerValueMod.getValue() > 100) {
							unit.organization = 100;
						} else {
							unit.organization += (int) spinnerValueMod.getValue();
						}

					}

				}

				refreshInitiativeOrder();

			}
		});
		btnNewButton_1.setBounds(369, 101, 59, 23);
		f.getContentPane().add(btnNewButton_1);

		JLabel label = new JLabel("Side:");
		label.setFont(new Font("Calibri", Font.BOLD, 13));
		label.setBounds(123, 104, 59, 17);
		f.getContentPane().add(label);

		comboBoxValueMod = new JComboBox();
		comboBoxValueMod.setModel(new DefaultComboBoxModel(new String[] { "BLUFOR", "OPFOR", "IND" }));
		comboBoxValueMod.setBounds(152, 101, 89, 23);
		f.getContentPane().add(comboBoxValueMod);

		JButton btnMorale = new JButton("Mor.");
		btnMorale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				for (Unit unit : initiativeOrder) {

					if (unit.side.equals(comboBoxValueMod.getSelectedItem())) {

						if (unit.moral + (int) spinnerValueMod.getValue() > 100) {
							unit.moral = 100;
						} else {
							unit.moral += (int) spinnerValueMod.getValue();
						}

					}

				}

				refreshInitiativeOrder();

			}
		});
		btnMorale.setBounds(309, 101, 59, 23);
		f.getContentPane().add(btnMorale);

		JButton btnSup = new JButton("Sup.");
		btnSup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				for (Unit unit : initiativeOrder) {

					if (unit.side.equals(comboBoxValueMod.getSelectedItem())) {

						if (unit.suppression + (int) spinnerValueMod.getValue() > 100) {
							unit.suppression = 100;
						} else {
							unit.suppression += (int) spinnerValueMod.getValue();
						}

					}

				}

				refreshInitiativeOrder();

			}
		});
		btnSup.setBounds(248, 101, 59, 23);
		f.getContentPane().add(btnSup);

		spinnerValueMod = new JSpinner();
		spinnerValueMod.setBounds(69, 102, 44, 20);
		f.getContentPane().add(spinnerValueMod);

		JButton btnNewButton_2 = new JButton("Hexes");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				new HexWindow(gameWindow);

			}
		});
		btnNewButton_2.setBounds(10, 162, 128, 23);
		f.getContentPane().add(btnNewButton_2);

		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Deprecated button");
				return;
				/*activeUnit = (int) spinnerActiveUnit.getValue();
				setUnitlabel();*/
			}
		});
		btnSet.setBounds(153, 132, 89, 23);
		f.getContentPane().add(btnSet);

		spinnerActiveUnit = new JSpinner();
		spinnerActiveUnit.setBounds(94, 133, 44, 20);
		f.getContentPane().add(spinnerActiveUnit);

		JLabel lblActiveUnit_1 = new JLabel("Active Unit:");
		lblActiveUnit_1.setFont(new Font("Calibri", Font.BOLD, 13));
		lblActiveUnit_1.setBounds(10, 134, 80, 17);
		f.getContentPane().add(lblActiveUnit_1);

		JButton btnClearAllSpotted = new JButton("Clear All Spotted");
		btnClearAllSpotted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						// System.out.println("Clear Spot1");

						for (Unit unit : initiativeOrder) {

							for (Trooper trooper : unit.individuals) {

								trooper.spotted.clear();

							}

						}

						conflictLog.addNewLine("Cleared spotted.");

						// System.out.println("Clear Spot2");
						return null;
					}

				};

				worker.execute();

			}
		});
		btnClearAllSpotted.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		btnClearAllSpotted.setBounds(546, 132, 128, 23);
		f.getContentPane().add(btnClearAllSpotted);

		JButton btnRefreshCompanyUnits = new JButton("Refresh Company Units");
		btnRefreshCompanyUnits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshCompanyUnits();

			}

		});
		btnRefreshCompanyUnits.setBounds(231, 39, 165, 25);
		f.getContentPane().add(btnRefreshCompanyUnits);

		JButton btnSkipTo = new JButton("Skip To");
		btnSkipTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Deprecated button");
				return;

				/*SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						int newActiveUnit = (int) spinnerActiveUnit.getValue();

						// System.out.println("Original AU: "+originalAU);
						// System.out.println("Next Active Unit: "+newActiveUnit);

						if (activeUnit == newActiveUnit) {
							safeNextActiveUnit();
						}

						ExecutorService es = Executors.newFixedThreadPool(16);

						// If on last unit
						while (activeUnit != newActiveUnit) {
							try {
								es.submit(() -> {
									safeNextActiveUnit();

								});
								try {
									TimeUnit.MILLISECONDS.sleep(75);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}

						es.shutdown();

						return null;
					}

					@Override
					protected void done() {
						conflictLog.addQueuedText();
						guiUpdateNextActiveUnit();

						if (hexGrid != null) {
							// hexGrid.panel.shownType =
							// HexGridUtility.getShownTypeFromSide(initiativeOrder.get(activeUnit).side);
						}

						openUnit(initiativeOrder.get(activeUnit), activeUnit);
						hexGrid.frame.toFront();
						hexGrid.frame.requestFocus();
					}

				};

				worker.execute();*/

			}
		});
		btnSkipTo.setBounds(248, 133, 113, 23);
		f.getContentPane().add(btnSkipTo);

		JLabel lblHexSize = new JLabel("Hex Size:");
		lblHexSize.setFont(new Font("Calibri", Font.BOLD, 13));
		lblHexSize.setBounds(546, 26, 59, 17);
		f.getContentPane().add(lblHexSize);

		spinnerHexSize = new JSpinner();
		spinnerHexSize.setBounds(546, 41, 44, 20);
		f.getContentPane().add(spinnerHexSize);

		JButton btnSetHex = new JButton("Set");
		btnSetHex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				hexSize = (int) spinnerHexSize.getValue();
				conflictLog.addNewLine("Hex Size Set To " + hexSize);
			}
		});
		btnSetHex.setBounds(600, 40, 74, 23);
		f.getContentPane().add(btnSetHex);

		JButton btnNewButton_3 = new JButton("CQB Check");
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentlyOpenUnit == null) {
					conflictLog.addNewLine(
							"Use this button at the start of a conflict, specifically when some units are begining the conflict in close quarters battle. "
									+ "Open the first unit in the init order to perform a CQB check.");
					return;
				}

				closeQuartersBattleCheck(currentlyOpenUnit);
			}
		});
		btnNewButton_3.setBounds(546, 101, 128, 23);
		f.getContentPane().add(btnNewButton_3);

		JButton btnSetContact = new JButton("Set Contact");
		btnSetContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (Unit unit : initiativeOrder) {
					unit.behavior = "Contact";
					if (gameWindow.findHex(unit.X, unit.Y) != null)
						gameWindow.findHex(unit.X, unit.Y).usedPositions -= unit.individualsInCover;

					unit.individualsInCover = 0;
					for (Trooper trooper : unit.individuals) {
						if (!trooper.inBuilding(gameWindow))
							trooper.inCover = false;

					}

					unit.soughtCover = false;
					if (!unit.behavior.equals("No Contact")) {
						unit.timeSinceContact = 0;
						unit.seekCover(gameWindow.findHex(unit.X, unit.Y), gameWindow);
					}

				}

				conflictLog.addNewLine("Set Contact.");

			}
		});
		btnSetContact.setBounds(546, 70, 128, 23);
		f.getContentPane().add(btnSetContact);

		JButton btnSelectedUnits = new JButton("Selected Units");
		btnSelectedUnits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SelectedUnitsWindow();
			}
		});
		btnSelectedUnits.setBounds(418, 162, 128, 23);
		f.getContentPane().add(btnSelectedUnits);

		JButton btnNextAction = new JButton("Next Action");
		btnNextAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HexGrid.impactHexes.clear();
				
				if(startedLoading != null)
					startedLoading.frame.dispose();
				if(finishedLoading != null)
					finishedLoading.frame.dispose();
				
				startedLoading = new AlertWindow("Loading Next Action");

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						for(int i = 0; i < (int)spinnerNextActions.getValue(); i++) {
							nextAction();					
						}
						return null;
					}

					@Override
					protected void done() {
						guiUpdateNextActiveUnit();
						// openUnit(initiativeOrder.get(activeUnit), activeUnit);
						hexGrid.frame.toFront();
						hexGrid.frame.requestFocus();
						finishedLoading = new AlertWindow("Finished Loading Next Action");
						conflictLog.addQueuedText();
						lblWind.setText(game.wind.toString());
					}

				};

				worker.execute();
				
			}
		});
		btnNextAction.setBounds(433, 132, 113, 23);
		f.getContentPane().add(btnNextAction);

		lblWind = new JLabel("Wind: ");
		lblWind.setFont(new Font("Calibri", Font.BOLD, 13));
		lblWind.setBounds(418, 26, 118, 17);
		f.getContentPane().add(lblWind);

		JButton btnWind = new JButton("Wind");
		btnWind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.wind.setStats();
				lblWind.setText(game.wind.toString());
				
				/*game.smoke.deploySmoke(new Cord(2,2), new SmokeStats(SmokeType.SMOKE_GRENADE));
				
				game.smoke.deploySmoke(new Cord(3,3), new SmokeStats(SmokeType.SMOKE_GRENADE));
				game.smoke.deploySmoke(new Cord(3,3), new SmokeStats(SmokeType.SMOKE_GRENADE));
				game.smoke.deploySmoke(new Cord(3,3), new SmokeStats(SmokeType.SMOKE_GRENADE));
				
				game.smoke.deploySmoke(new Cord(4,4), new SmokeStats(SmokeType.Howitzer155mm));
				
				game.smoke.deploySmoke(new Cord(5,5), new SmokeStats(SmokeType.Howitzer155mm));
				game.smoke.deploySmoke(new Cord(5,5), new SmokeStats(SmokeType.Howitzer155mm));*/
				
			}
		});
		btnWind.setBounds(418, 53, 118, 23);
		f.getContentPane().add(btnWind);

		// System.out.println("Init order size: "+initiativeOrder.size());
		// Opens active unit
		if (initiativeOrder != null && initiativeOrder.size() > 0) {

			// Updates Lavels
			setUnitlabel();
			setRound();
			setPhase();
			setActions();

			if (openUnit) {
				Unit unit = initiativeOrder.get(activeUnit);
				openUnit(unit, activeUnit);
			}

			// System.out.println("Load Active Unit: "+activeUnit);
		}

		if (game.getDaylightCondition().equals(""))
			comboBoxVisibility.setSelectedIndex(0);

		// Creates hex grid
		// System.out.println("Create hex grid");
		this.hexGrid = new HexGrid(initiativeOrder, gameWindow, hexRows, hexCols);
		if (hexGrid != null && hexGrid.panel.deployedUnits.size() > 0) {
			hexGrid.panel.selectedUnit = hexGrid.panel.deployedUnits.get(activeUnit);
		}

		spinnerHexSize.setValue(hexSize);
		lblWind.setText(game.wind.toString());
		
		JButton btnMelee = new JButton("Melee");
		btnMelee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MeleeCombatWindow();
			}
		});
		btnMelee.setBounds(433, 101, 113, 23);
		f.getContentPane().add(btnMelee);
		
		spinnerNextActions = new JSpinner();
		spinnerNextActions.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		spinnerNextActions.setBounds(384, 135, 44, 20);
		f.getContentPane().add(spinnerNextActions);
		
		
		vehicleCombatWindow = new VehicleCombatWindow();
		
		if(game.meleeManager == null)
			game.meleeManager = new MeleeManager();
		else 
			MeleeManager.meleeManager = game.meleeManager;
	}

	// Opens active unit window
	public void openUnit(Unit unit, int index) {
		// System.out.println("Open unit moral: "+unit.moral);

		try {
			if (currentlyOpenUnit != null)
				currentlyOpenUnit.f.dispose();
			currentlyOpenUnit = new OpenUnit(unit, gameWindow, index);
		} catch (Exception e) {
			System.out.println("toString(): " + e.toString());
			System.out.println("getMessage(): " + e.getMessage());
			System.out.println("StackTrace: ");
			e.printStackTrace();
		}

	}

	// Sets companies equal to the comapny in the setup window
	// Used to save changes made to a company during a conflict
	public void confirmCompany(Company company, int index) {
		setupWindow.setCompany(company, index);
		setupWindow.refreshCreated();
	}

	// Refresh conflict
	public void refreshConflict(SetupWindow setupWindow) {
		this.companies = setupWindow.getActivated();
		rollInitiativeOrder();
		refreshInitiativeOrder();
	}

	public void refreshInitiativeOrder() {
		DefaultListModel initiativeOrderList = new DefaultListModel();

		for (int i = 0; i < initiativeOrder.size(); i++) {

			// Loops through troopers in the unit

			for (int x = 0; x < initiativeOrder.get(i).getSize(); x++) {
				Trooper currentTrooper = initiativeOrder.get(i).getTroopers().get(x);
				currentTrooper.number = x + 1;
			}

			initiativeOrderList.addElement(i + ": " + initiativeOrder.get(i));

		}

		listIniativeOrder.setModel(initiativeOrderList);

		setUnitlabel();

		if (hexGrid != null) {
			hexGrid.refreshDeployedUnits();
		}

		
	}

	// Loops through companies
	// Adds units to units array
	// Sets units sides, command value, and initiative
	public void setInitiativeOrder(ArrayList<Company> companies) {
		Random rand = new Random();

		initiativeOrder.clear();

		for (int i = 0; i < companies.size(); i++) {
			Company company = companies.get(i);
			ArrayList<Unit> units = company.getUnits();
			for (int j = 0; j < company.getUnitsLength(); j++) {
				Unit unit = units.get(j);
				// Sets some unit stats
				unit.side = company.getSide();
				unit.company = company.getName();

				unit.getCommandValue();

				int roll = rand.nextInt(10) + 1;

				int leaderShipCommandValue = getLeaderShipCommandValue(company, unit);

				unit.initiative = roll + unit.commandValue + leaderShipCommandValue;
				conflictLog.addNewLine("Init Order Roll(" + unit.initiative + "), Roll: " + roll + ", Command Value: "
						+ unit.commandValue + leaderShipCommandValue);
				if (unit.active) {
					initiativeOrder.add(unit);
				}

			}
		}

		rollInitiativeOrder();

	}

	public int getLeaderShipCommandValue(Company company, Unit unit) {
		if (unit.individuals.size() <= 0)
			return 0;

		Trooper leader = unit.getLeader();

		int bonus = 0;

		for (Unit otherUnit : company.getUnits()) {
			for (Trooper trooper : otherUnit.individuals) {

				if (trooper.subordinates.contains(leader)) {
					bonus += trooper.getSkill("Command") / 10;
				}

			}

		}

		return bonus;

	}

	// Sets initiative order
	public void rollInitiativeOrder() {

		Unit temp;

		for (int i = 0; i < initiativeOrder.size(); i++) {

			for (int j = 0; j < initiativeOrder.size() - 1; j++) {

				if (initiativeOrder.get(j + 1).initiative > initiativeOrder.get(j).initiative) {
					temp = initiativeOrder.get(j + 1);
					initiativeOrder.set(j + 1, initiativeOrder.get(j));
					initiativeOrder.set(j, temp);

				}
			}
		}

		closeCombatCheck();
		// closeQuartersBattleCheck();
	}

	// Next active unit
	// Checks for end of actions
	// Checks for end of phase
	public void nextActiveUnit() {

		conflictLog.addNewLine("Next unit... Active Unit: " + activeUnit);

		int actions = game.getCurrentAction() + 1;

		// Increases passed active unit members by 1
		if (activeUnit > -1) {
			Unit unit = initiativeOrder.get(activeUnit);
			ArrayList<Trooper> troopers = unit.getTroopers();
			for (int j = 0; j < troopers.size(); j++) {

				// Increases time unconscious
				// Checks for wake up
				if (game.getCurrentAction() <= 3 && game.getCurrentAction() != 0) {

					if (!troopers.get(j).conscious) {

						if (troopers.get(j).timeUnconscious == troopers.get(j).incapacitationTime) {
							troopers.get(j).timeUnconscious = 0;
							troopers.get(j).incapacitationTime = 0;
							conflictLog.addNewLine(troopers.get(j).number + " " + troopers.get(j).name + " from unit:"
									+ unit.callsign + "has awoken.");
							troopers.get(j).conscious = true;
						} else {
							troopers.get(j).timeUnconscious++;
						}

					}

				}

				if (game.getPhase() == 1) {
					if (troopers.get(j).spentPhase1 != actions - 1) {

						if (troopers.get(j).spentPhase1 + 1 <= troopers.get(j).P1)
							troopers.get(j).spentPhase1 += 1;
					}
				} else {
					if (troopers.get(j).spentPhase2 != actions - 1) {

						if (troopers.get(j).spentPhase2 + 1 <= troopers.get(j).P2)
							troopers.get(j).spentPhase2 += 1;
					}
				}
			}
		}

		if (initiativeOrder == null || initiativeOrder.size() < 1) {
			return;
		}

		int size = initiativeOrder.size();
		size--;

		// If next unit is empty
		// Skips next unit
		// System.out.println("Active Unit: "+activeUnit);
		int unitCount = 1;
		boolean pass = false;
		while (!pass) {

			// Makes sure there is a unit infront of the active unit
			if (activeUnit + unitCount != initiativeOrder.size()) {

				// Checks if the next unit is the last one
				if (activeUnit + 2 == initiativeOrder.size()) {
					// Checks if the last unit is empty
					if (initiativeOrder.get(activeUnit + 1).getSize() < 1) {
						activeUnit = size;
						break;
					}
				}

				// Checks if next unit is empty
				// If unit is alive and not empty
				if (initiativeOrder.get(activeUnit + unitCount).getSize() < 1) {
					// System.out.println("Increase");
					unitCount++;
				} else {

					pass = true;
					activeUnit += unitCount;
					activeUnit--;

				}

				/*
				 * if(activeUnit + unitCount > size || activeUnit > size) { activeUnit = size;
				 * break; }
				 */

				// System.out.println("Modified Active Unit: "+activeUnit);

			} else {
				break;
			}

		}

		/*
		 * System.out.println("Next Unit: "+activeUnit+1);
		 * System.out.println("Size: "+size);
		 * System.out.println("Next Unit Size: "+initiativeOrder.get(activeUnit+1).
		 * getSize()); if(initiativeOrder.get(activeUnit+1).getSize() < 1) {
		 * System.out.println("pass"); activeUnit++; }
		 */

		// Checks for end of Initiative Order
		endOfInitOrder(size, actions);

		// System.out.println("Post activeUnit: "+activeUnit);

		// Sets next active unit
		listIniativeOrder.setSelectedIndex(activeUnit);

		// Opens active unit
		openUnit(initiativeOrder.get(activeUnit), activeUnit);

		// Updates Labels
		setUnitlabel();
		setRound();
		setPhase();
		setActions();
		
		

	}

	public void setCombatActions(Trooper shooter) {

		for (Shoot shot : Shoot.shootActions) {
			if (shot.shooter.compareTo(shooter)) {
				shot.spentCombatActions = 0;
			}
		}

	}

	// Performs nessesary checks for twenty seconds of passed time
	public void unitWaitTwentySeconds() {

		int actions = game.getCurrentAction();

		Unit unit = initiativeOrder.get(activeUnit);
		int activeTrooperCount = 0;
		int activeTroopersInCover = 0;
		// Forced Route or Pinned
		for (Trooper trooper : unit.getTroopers()) {

			if (trooper.alive && trooper.conscious) {
				activeTrooperCount++;
				if (trooper.inCover)
					activeTroopersInCover++;
			}

		}

		/*
		 * if (unit.suppression >= 50) {
		 * 
		 * if ((double) activeTrooperCount / activeTroopersInCover < 0.5) {
		 * conflictLog.addNewLine( unit.callsign +
		 * " is forced to route due to high suppression and minimal cover. This " +
		 * "unit can avoid routing if all individuals not in cover can hunker down."); }
		 * else { conflictLog.addNewLine(unit.callsign +
		 * " is pinned and cannot move until suppression " +
		 * "becomes less than 50. Or a successful command test is made, with one difficulty die for every 10 suppression. "
		 * + "And 1 bonus die for each additional leader."); }
		 * 
		 * }
		 */

		// Fire Missions
		for (FireMission fireMission : unit.fireMissions) {
			// System.out.println("Calling Advance Time Fire Mission");
			fireMission.advanceTime();
		}

		ArrayList<Trooper> troopers = unit.getTroopers();
		for (int j = 0; j < troopers.size(); j++) {
			// System.out.println("Pass Trooper: "+j);

			setCombatActions(troopers.get(j));

			if (!troopers.get(j).alive) {
				troopers.get(j).spentPhase1 = troopers.get(j).P1;
				troopers.get(j).spentPhase2 = troopers.get(j).P2;
				continue;
			}

			// Increases time unconscious
			// Checks for wake up
			if (actions <= 3 && actions != 0) {

				if (!troopers.get(j).conscious) {

					if (troopers.get(j).timeUnconscious >= troopers.get(j).incapacitationTime) {
						troopers.get(j).timeUnconscious = 0;
						troopers.get(j).incapacitationTime = 0;
						conflictLog.addNewLineToQueue(troopers.get(j).number + " " + troopers.get(j).name
								+ " from unit:" + unit.callsign + "has awoken.");
						troopers.get(j).conscious = true;

					} else {
						troopers.get(j).timeUnconscious++;
					}
					troopers.get(j).spentPhase1 = troopers.get(j).P1;
					troopers.get(j).spentPhase2 = troopers.get(j).P2;

					continue;

				}

				Trooper trooper = troopers.get(j);
				if (trooper.personalShield != null) {
					PersonalShield ps = trooper.personalShield;

					if (ps.currentShieldStrength < ps.maxShieldStrength) {

						ps.currentShieldStrength += ps.maxShieldStrength * ps.rechargeRate;

						if (ps.currentShieldStrength > ps.maxShieldStrength)
							ps.currentShieldStrength = ps.maxShieldStrength;

					}

				}

			}

			float time;

			if (gameWindow.game.getPhase() == 1) {
				if (troopers.get(j).P1 == 0)
					time = 30;
				else
					time = 60 / troopers.get(j).P1;
			} else {
				if (troopers.get(j).P2 == 0)
					time = 30;
				else
					time = 60 / troopers.get(j).P2;
			}

			if (unit.speed.equals("None")) {
				troopers.get(j).fatigueSystem.AddRecoveryTime(time);
			}

			if (game.getPhase() == 1) {
				if (troopers.get(j).spentPhase1 != actions) {

					if (troopers.get(j).spentPhase1 + 1 <= troopers.get(j).P1) {

						// troopers.get(j).spentPhase1 += 1;
					}
				}
			} else {
				if (troopers.get(j).spentPhase2 != actions) {

					if (troopers.get(j).spentPhase2 + 1 <= troopers.get(j).P2) {

						// troopers.get(j).spentPhase2 += 1;
					}
				}
			}
		}
	}

	// Performs nessesary checks for twenty seconds of passed time
	public void unitPassTwentySeconds() {

		int actions = game.getCurrentAction();

		Unit unit = initiativeOrder.get(activeUnit);
		int activeTrooperCount = 0;
		int activeTroopersInCover = 0;
		// Forced Route or Pinned
		for (Trooper trooper : unit.getTroopers()) {

			if (trooper.alive && trooper.conscious) {
				activeTrooperCount++;
				if (trooper.inCover)
					activeTroopersInCover++;
			}

		}

		/*
		 * if (unit.suppression >= 50) {
		 * 
		 * if ((double) activeTrooperCount / activeTroopersInCover < 0.5) {
		 * conflictLog.addNewLine( unit.callsign +
		 * " is forced to route due to high suppression and minimal cover. This " +
		 * "unit can avoid routing if all individuals not in cover can hunker down."); }
		 * else { conflictLog.addNewLine(unit.callsign +
		 * " is pinned and cannot move until suppression " +
		 * "becomes less than 50. Or a successful command test is made, with one difficulty die for every 10 suppression. "
		 * + "And 1 bonus die for each additional leader."); }
		 * 
		 * }
		 */

	}

	// Next active unit
	// Checks for end of actions
	// Checks for end of phase
	public void safeNextActiveUnit() {
		// System.out.println("\n\nCurrent Action: "+game.getCurrentAction()+" Active
		// Unit: "+activeUnit+"\n\n");

		if (initiativeOrder == null || initiativeOrder.size() < 1) {
			// System.out.println("Returning Safe Next Active Unit");
			return;
		}

		unitPassTwentySeconds();

		int size = initiativeOrder.size();
		size--;

		/*
		 * activeUnit++; if(activeUnit == initiativeOrder.size()) activeUnit = 0;
		 */

		/*
		 * System.out.println("Next Unit: "+activeUnit+1);
		 * System.out.println("Size: "+size);
		 * System.out.println("Next Unit Size: "+initiativeOrder.get(activeUnit+1).
		 * getSize()); if(initiativeOrder.get(activeUnit+1).getSize() < 1) {
		 * System.out.println("pass"); activeUnit++; }
		 */

		// Checks for end of Initiative Order
		// Might contain gui updates
		// System.out.println("Inside Pass1");
		endOfInitOrder(size, game.getCurrentAction() + 1);

		// System.out.println("Inside Pass2");

		// System.out.println("Post activeUnit: "+activeUnit);

		// Sets next active unit
		// listIniativeOrder.setSelectedIndex(activeUnit);

		// Opens active unit
		// System.out.println("Open Next Active Unit");

	}

	// Next active unit
	// Checks for end of actions
	// Checks for end of phase
	public void safeWaitActiveUnit() {
		// System.out.println("\n\nCurrent Action: "+game.getCurrentAction()+" Active
		// Unit: "+activeUnit+"\n\n");

		if (initiativeOrder == null || initiativeOrder.size() < 1) {
			// System.out.println("Returning Safe Next Active Unit");
			return;
		}

		unitWaitTwentySeconds();

		int size = initiativeOrder.size();
		size--;

		/*
		 * activeUnit++; if(activeUnit == initiativeOrder.size()) activeUnit = 0;
		 */

		/*
		 * System.out.println("Next Unit: "+activeUnit+1);
		 * System.out.println("Size: "+size);
		 * System.out.println("Next Unit Size: "+initiativeOrder.get(activeUnit+1).
		 * getSize()); if(initiativeOrder.get(activeUnit+1).getSize() < 1) {
		 * System.out.println("pass"); activeUnit++; }
		 */

		// Checks for end of Initiative Order
		// Might contain gui updates
		// System.out.println("Inside Pass1");
		endOfInitOrder(size, game.getCurrentAction() + 1);
		// System.out.println("Inside Pass2");

		// System.out.println("Post activeUnit: "+activeUnit);

		// Sets next active unit
		// listIniativeOrder.setSelectedIndex(activeUnit);

		// Opens active unit
		// System.out.println("Open Next Active Unit");

	}

	public void guiUpdateNextActiveUnit() {

		conflictLog.addNewLine("Next unit... Active Unit: " + activeUnit);

		// Updates Labels
		setUnitlabel();
		setRound();
		setPhase();
		setActions();

	}

	// Takes a trooper, returns true if that unit has spent its AP for this action,
	// returns false if a trooper has not
	public static boolean exhaustedTrooper(Trooper trooper) {
		if(!trooper.conscious)
			return true;
		else if(!trooper.alive)
			return true;
		
		// System.out.println("Exuasted Trooper test: "+trooper.name);
		if (GameWindow.gameWindow.game.getPhase() == 1) {
			if (trooper.spentPhase1 < trooper.P1 && trooper.spentPhase1 < GameWindow.gameWindow.game.getCurrentAction()) {
				// System.out.println("Return False");
				return false;
			}
		} else {
			if (trooper.spentPhase2 < trooper.P2 && trooper.spentPhase1 < GameWindow.gameWindow.game.getCurrentAction()) {
				// System.out.println("Return False");
				return false;
			}
		}

		// System.out.println("Return True");
		return true;
	}

	// Takes a unit, returns true if that unit has spent its AP for this action,
	// returns false if a trooper has not
	public static boolean exhaustedUnit(Unit unit) {

		// System.out.println("Entering Exhusted Unit Check");

		for (Trooper trooper : unit.getTroopers()) {
			if (!exhaustedTrooper(trooper))
				return false;
		}

		// System.out.println("Returning True");
		return true;
	}

	public static boolean mostlyExhausted(Unit unit) {

		int exausted = 0, fresh = 0;

		for (Trooper trooper : unit.getTroopers()) {
			if (exhaustedTrooper(trooper))
				exausted++;
			else
				fresh++;
		}

		if (exausted >= fresh) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean anyoneExhausted(Unit unit) {
		for (Trooper trooper : unit.getTroopers()) {
			if (exhaustedTrooper(trooper))
				return true;
		}

		// System.out.println("Returning True");
		return false;
	}

	// Returns true if all individuals have spent ap for this action, returns false
	// if they have not
	public boolean checkForAllExahusted() {

		for (Unit unit : initiativeOrder) {
			if (!exhaustedUnit(unit))
				return false;
		}

		return true;
	}

	// Takes a unit, returns true if that unit has spent its AP for this action,
	// returns false if a trooper has not
	public boolean endActionUnit(Unit unit) {

		// System.out.println("Entering End Action Unit Check");

		for (Trooper trooper : unit.getTroopers()) {
			if (game.getPhase() == 1) {
				if (trooper.spentPhase1 < trooper.P1
						&& trooper.spentPhase1 < GameWindow.gameWindow.game.getCurrentAction()) {
					// System.out.println("Returning False");
					return false;
				}
			} else {
				if (trooper.spentPhase2 < trooper.P2
						&& trooper.spentPhase2 < GameWindow.gameWindow.game.getCurrentAction()) {
					// System.out.println("Returning False");
					return false;
				}
			}
		}

		// System.out.println("Returning True");
		return true;
	}

	// Returns true if all individuals have spent ap for this action, returns false
	// if they have not
	public boolean checkForAllEndAction() {

		for (Unit unit : initiativeOrder) {
			if (!endActionUnit(unit))
				return false;
		}

		return true;
	}

	// Checks and performs tasks for the end of the init order
	public void endOfInitOrder(int size, int actions) {

		boolean endOfPhase = checkForAllExahusted();
		boolean endOfAction = checkForAllEndAction();

		if (endOfPhase) {
			conflictLog.addNewLineToQueue("End of Phase");
			// game.setCurrentAction(actions);
			// conflictLog.addNewLineToQueue("Actions: " + actions);
			// Checks for end of phase
			// Loops through units indiviudals

			// Checks for artillery fire missions
			int diff = 3 - game.getCurrentAction();
			// System.out.println("diff: "+diff);
			for (Unit unit : initiativeOrder) {
				// Fire Missions
				for (FireMission fireMission : unit.fireMissions) {
					// System.out.println("Calling Advance Time Fire Mission");
					for (int i = 0; i < diff; i++) {
						fireMission.advanceTime();
					}

				}
			}

			// Check for end of round or phase
			if (game.getPhase() == 1) {
				game.setPhase(2);
				// End of phase
				conflictLog.addNewLineToQueue("     Round: " + game.getRound() + " Phase: 2");
				endOfPhase();
			} else {
				game.setPhase(1);
				int round = game.getRound() + 1;
				game.setRound(round);
				conflictLog.addNewLineToQueue("     Round: " + game.getRound() + " Phase: 1");
				// End of round
				endOfPhase();
				endOfRound();

			}

			activeUnit = 0;
		} else if (endOfAction) {
			game.setCurrentAction(actions);
			conflictLog.addNewLineToQueue("End of Action");
			conflictLog.addNewLineToQueue("Actions: " + actions);
			activeUnit = 0;

			// System.out.println("End of action");

			// Action ended
			for (DeployedUnit deployedUnit : hexGrid.panel.deployedUnits) {
				deployedUnit.moved = false;
			}

			int currentAction = game.getCurrentAction();
			// Performs spot test
			if (currentAction == 1 || currentAction == 2 || currentAction == 3)
				spotCycle();

		} else if (activeUnit >= size) {

			for (int i = 0; i < initiativeOrder.size(); i++) {

				if (!endActionUnit(initiativeOrder.get(i))) {
					activeUnit = i;
					break;
				}

			}

		} else {
			activeUnit++;
		}

	}

	public void closeQuartersBattleCheck(OpenUnit openUnit) {

		cqb.closeQuartersBattleCheck(openUnit);

	}

	// Sets active unit label
	public void setUnitlabel() {
		if (initiativeOrder == null || initiativeOrder.size() < 1)
			return;
		else if (activeUnit > initiativeOrder.size())
			return;
		else if (activeUnit >= initiativeOrder.size()) {
			activeUnit = 0;
			return;
		} else if (initiativeOrder.get(activeUnit) == null)
			return;

		lblActiveUnit.setText("Active Unit: " + initiativeOrder.get(activeUnit).side + "::  "
				+ initiativeOrder.get(activeUnit).callsign);
	}

	// Set round
	public void setRound() {
		lblRound.setText("Round: " + game.getRound());
	}

	// Set phase
	public void setPhase() {
		lblPhase.setText("Phase: " + game.getPhase());

	}

	// Set actions
	public void setActions() {
		lblActions.setText("Actions: " + game.getCurrentAction());
	}

	// End of round
	// Loops through initiative order
	// Resets each troopers spentPhase AP to 0
	// Checks if a trooper is unconscious
	// If the trooper is unconscious increases the time unconscious by two minutes
	// Performs wake up tests is applicable
	public void endOfRound() {
		game.setCurrentAction(1);
		Random rand = new Random();

		// Loops through initiative order order
		for (int i = 0; i < initiativeOrder.size(); i++) {

			// Gets current unit
			Unit unit = initiativeOrder.get(i);

			if (unit.behavior.equals("Recent Contact")) {
				unit.timeSinceContact += 1;

			}

			/*
			 * if(unit.timeSinceContact >= 5) { unit.behavior = "No Contact";
			 * unit.timeSinceContact = 0; }
			 */

			// Gets troopers
			ArrayList<Trooper> troopers = unit.getTroopers();

			// Loops through troopers
			for (int j = 0; j < unit.getSize(); j++) {
				Trooper trooper = troopers.get(j);
				// int TN = trooper.endurance;
				// Resets spent ap
				trooper.spentPhase1 = 0;
				trooper.spentPhase2 = 0;

				// Checks if trooper is conscious
				if (!trooper.conscious) {
					/*
					 * // Increases time spent unconscious trooper.timeUnconscious += 2;
					 * 
					 * // Performs wake up tests
					 * 
					 * 
					 * 
					 * if(trooper.timeUnconscious >= 14 && trooper.currentHP > 0) {
					 * trooper.conscious = true;
					 * conflictLog.addNewLine(unit.callsign+":: "+trooper.name+", Number: "+trooper.
					 * number+" has woken up..."); trooper.timeUnconscious = 0; }
					 * 
					 * 
					 * 
					 * int roll = rand.nextInt(100) + 1;
					 * 
					 * if(trooper.currentHP < -1 * trooper.hp) { if(trooper.timeUnconscious >= 718)
					 * { if(roll > TN) {
					 * conflictLog.addNewLine(unit.callsign+":: "+trooper.name+", Number: "+trooper.
					 * number+" has died of their injuries..."); trooper.alive = false; } } } else
					 * if(trooper.currentHP < 1) { if(trooper.timeUnconscious >= 58) { if(roll <=
					 * TN) {
					 * conflictLog.addNewLine(unit.callsign+":: "+trooper.name+", Number: "+trooper.
					 * number+" has woken up..."); trooper.conscious = true; trooper.timeUnconscious
					 * = 0; } } }
					 */

				}

				// Checks if trooper is mortally wounded
				/*
				 * if(trooper.mortallyWounded) { // Increases time spent mortally wounded
				 * trooper.timeMortallyWounded += 2;
				 * 
				 * // Checks if mortal wound is stabalized, makes rolls once per hour // If the
				 * character is not stablaized then rolls occur every half hour
				 * if(trooper.timeMortallyWounded >= 58 && trooper.stabalized) { int tempTN;
				 * 
				 * int skill = trooper.physicianSkill;
				 * 
				 * if(skill > TN) { tempTN = skill; } else { tempTN = TN; }
				 * 
				 * int roll = rand.nextInt(100) + 1; if(roll > tempTN) { trooper.alive = false;
				 * conflictLog.addNewLineToQueue(unit.callsign+":: "+trooper.name+", Number: "
				 * +trooper.number+" has died of their injuries..."); }
				 * 
				 * } else if(trooper.timeMortallyWounded >= 28) { int roll = rand.nextInt(100) +
				 * 1; if(roll > TN) { trooper.alive = false;
				 * conflictLog.addNewLineToQueue(unit.callsign+":: "+trooper.name+", Number: "
				 * +trooper.number+" has died of their injuries..."); } }
				 * 
				 * }
				 */

			}
		}
	}

	// End of phase

	public void endOfPhase() {

		// Loops through all individuals, increases time passed for wounds
		// Checks unit morale, forces individuals to hunker down that are subject to the
		// morale failure

		for (int i = 0; i < initiativeOrder.size(); i++) {

			for (int x = 0; x < initiativeOrder.get(i).getSize(); x++) {

				if (initiativeOrder.get(i).moral < 30) {
					Random rand = new Random();
					int roll = rand.nextInt(10) + 1;

					if (roll < initiativeOrder.get(i).getTroopers().get(x).P1
							+ initiativeOrder.get(i).getTroopers().get(x).P2
							&& !initiativeOrder.get(i).getTroopers().get(x).entirelyMechanical) {

						if (initiativeOrder.get(i).getTroopers().get(x).inCover) {
							initiativeOrder.get(i).getTroopers().get(x).hunkerDown(gameWindow);
							conflictLog.addNewLineToQueue(initiativeOrder.get(i).getTroopers().get(x).number + " "
									+ initiativeOrder.get(i).getTroopers().get(x).name
									+ " hunkers down. Morale too low.");
						}

					}

				}

				if (initiativeOrder.get(i).suppression > 10) {
					Random rand = new Random();
					int roll = rand.nextInt(10) + 1;

					if (roll < initiativeOrder.get(i).getTroopers().get(x).P1
							+ initiativeOrder.get(i).getTroopers().get(x).P2
							&& !initiativeOrder.get(i).getTroopers().get(x).entirelyMechanical) {

						if (initiativeOrder.get(i).getTroopers().get(x).inCover) {
							initiativeOrder.get(i).getTroopers().get(x).hunkerDown(gameWindow);
							conflictLog.addNewLineToQueue(initiativeOrder.get(i).getTroopers().get(x).number + " "
									+ initiativeOrder.get(i).getTroopers().get(x).name + " hunkers down. SUPPRESSED.");
						} else {

							if (game.getPhase() == 1) {
								initiativeOrder.get(i).getTroopers().get(x).spentPhase1++;
							} else {
								initiativeOrder.get(i).getTroopers().get(x).spentPhase2++;
							}

							conflictLog.addNewLineToQueue(initiativeOrder.get(i).getTroopers().get(x).number + " "
									+ initiativeOrder.get(i).getTroopers().get(x).name + " cowers. SUPPRESSED.");
						}

					}

				}

				initiativeOrder.get(i).getTroopers().get(x).advanceTime(this, conflictLog);
			}

		}

		for (int i = 0; i < initiativeOrder.size(); i++) {
			if (initiativeOrder.get(i).suppression - 10 > 0) {
				initiativeOrder.get(i).suppression -= 5;
			} else {
				initiativeOrder.get(i).suppression = 0;
			}

			if (initiativeOrder.get(i).behavior.equals("No Contact")) {
				initiativeOrder.get(i).organization += initiativeOrder.get(i).commandValue
						+ (initiativeOrder.get(i).moral / 20);
			} else if (initiativeOrder.get(i).behavior.equals("Recent Contact")) {
				initiativeOrder.get(i).organization += initiativeOrder.get(i).commandValue
						+ (initiativeOrder.get(i).moral / 20) / 2;
			}

			if (initiativeOrder.get(i).organization > 100) {

				initiativeOrder.get(i).organization = 100;

			}
		}

		// Loops through initiative order order
		for (int i = 0; i < initiativeOrder.size(); i++) {

			// Gets current unit
			Unit unit = initiativeOrder.get(i);
			// Gets troopers
			ArrayList<Trooper> troopers = unit.getTroopers();

			// Loops through troopers
			for (int j = 0; j < unit.getSize(); j++) {
				Trooper trooper = troopers.get(j);

				// Checks for shields
				// Recharges sheilds
				if (trooper.shields > 0) {
					trooper.currentShields = trooper.shields;
				}
				
				trooper.firedTracers = false;
				trooper.firedWeapons = false;
			}

		}

		game.setCurrentAction(1);
		
	}

	// Loops through each unit
	// Rolls spot for all individuals against units that are not on their side
	public void spotCycle() {

		conflictLog.addNewLineToQueue("Spot Cycle: ");

		ExecutorService es = Executors.newFixedThreadPool(16);

		for (Iterator<Unit> iteratorInitOrder = initiativeOrder.iterator(); iteratorInitOrder.hasNext();) {

			Unit spotterUnit = iteratorInitOrder.next();
			// System.out.println("Spotter Unit: "+spotterUnit);

			for (Iterator<Trooper> troopers = spotterUnit.individuals.iterator(); troopers.hasNext();) {
				Trooper spotterTrooper = troopers.next();
				// System.out.println("Spotter Trooper: "+spotterTrooper.name);
				for (Iterator<Unit> targetUnits = spotterUnit.lineOfSight.iterator(); targetUnits.hasNext();) {

					Unit targetUnit = targetUnits.next();
					// System.out.println("Target Unit: "+targetUnit.callsign);
					// System.out.println("Entering Spot 1");
					es.submit(() -> {
						spot(targetUnit, spotterUnit, spotterTrooper);
					});

					try {
						TimeUnit.MILLISECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// System.out.println("Exiting Spot 2");
				}

			}

		}

		es.shutdown();
		/*
		 * for (int i = 0; i < initiativeOrder.size(); i++) { // Gets spotter unit Unit
		 * spotterUnit = initiativeOrder.get(i);
		 * 
		 * // Loops through troopers for(int x = 0; x <
		 * spotterUnit.getTroopers().size(); x++) {
		 * 
		 * // Clears trooper's spot spotterUnit.getTroopers().get(x).spotted.clear();
		 * 
		 * // Loops through units a second time for (int j = 0; j <
		 * initiativeOrder.size(); j++) {
		 * 
		 * // If not the same unit if(!initiativeOrder.get(j).compareTo(spotterUnit) &&
		 * !initiativeOrder.get(j).side.equals(spotterUnit.side)) {
		 * 
		 * 
		 * // Performs spot test spot(initiativeOrder.get(j), spotterUnit,
		 * spotterUnit.getTroopers().get(x));
		 * 
		 * 
		 * 
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * }
		 * 
		 * 
		 * }
		 */

	}

	// Creates new spot action and adds it to trooper
	public void spot(Unit targetUnit, Unit spotterUnit, Trooper trooper) {
		// System.out.println("Target Unit: "+targetUnit.callsign);
		// System.out.println("spotterUnit: "+spotterUnit.callsign);

		/*
		 * for (Unit unit : spotterUnit.lineOfSight) { //
		 * System.out.println("Unit in LOS: "+unit.callsign); }
		 */

		// System.out.println("spotterUnitLOS: "+spotterUnit.lineOfSight);
		if (!spotterUnit.lineOfSight.contains(targetUnit)) {
			// System.out.println("No LOS to Units: Spotter Unit: "+spotterUnit.callsign+",
			// targetUnit: "+targetUnit.callsign);
			return;
		}

		// System.out.println("New Spot Action 1");
		Spot spotAction = new Spot(gameWindow, spotterUnit, targetUnit, trooper, "180 Degrees", gameWindow.visibility,
				gameWindow.initiativeOrder, gameWindow);
		// System.out.println("New Spot Action 2");
		// Set results in trooper
		trooper.spotted.add(spotAction);
		// System.out.println("New Spot Action 3");
	}

	// Takes units from companies not already in the init order
	// Makes an init roll
	public void refreshUnits() {

		Random rand = new Random();

		ArrayList<Company> companies = setupWindow.getActivated();

		for (int i = 0; i < companies.size(); i++) {
			Company company = companies.get(i);
			ArrayList<Unit> units = company.getUnits();
			for (int j = 0; j < company.getUnitsLength(); j++) {
				Unit unit = units.get(j);

				boolean alreadyExists = false;
				for (Unit initUnit : initiativeOrder) {
					if (unit.callsign.equals(initUnit.callsign))
						alreadyExists = true;
				}

				if (!alreadyExists) {
					// Sets some unit stats
					unit.side = company.getSide();
					unit.company = company.getName();
					unit.getCommandValue();

					if (game.getPhase() == 1) {
						for (Trooper trooper : unit.getTroopers()) {

							trooper.spentPhase1 = game.getCurrentAction() - 1;

							if (game.getCurrentAction() == 1)
								trooper.spentPhase1 = 0;

						}
					} else {
						for (Trooper trooper : unit.getTroopers()) {

							trooper.spentPhase1 = trooper.P1;
							trooper.spentPhase2 = game.getCurrentAction() - 1;

							if (game.getCurrentAction() == 1)
								trooper.spentPhase2 = 0;

						}
					}

					int roll = rand.nextInt(10) + 1;

					unit.initiative = roll + unit.commandValue;
					if (unit.active) {
						initiativeOrder.add(unit);
					}
				}

			}
		}

		// Removes deactivated units
		/*
		 * for(Unit unit : initiativeOrder) {
		 * 
		 * if(!unit.active) {
		 * 
		 * initiativeOrder.remove(unit);
		 * 
		 * }
		 * 
		 * }
		 */

		rollInitiativeOrder();
		refreshInitiativeOrder();

	}

	// Loops through all units in the iniiative order, checks if close combat should
	// be initaited
	public void closeCombatCheck() {

		int size = initiativeOrder.size();

		for (int i = 0; i < size; i++) {

			Unit unit = initiativeOrder.get(i);
			boolean closeCombat = false;

			ArrayList<Trooper> flee = new ArrayList<>();
			boolean rout = false;

			for (int x = 0; x < size; x++) {

				Unit unit2 = initiativeOrder.get(x);

				if (!unit.compareTo(unit2)) {

					if (unit.X == unit2.X && unit.Y == unit2.Y && !unit.side.equals(unit2.side)) {
						closeCombat = true;
						Random rand = new Random();

						if (!unit.closeCombat) {
							unit.closeCombat = true;

							if (unit.individuals.size() > 0 && !unit.entirelyMechanical() && PCUtility.Routed(unit)) {

								int leaderShipRoll = rand.nextInt(100) + 1;

								// System.out.println("LR Roll 1: "+leaderShipRoll);

								// System.out.println("LR Roll Modded: "+leaderShipRoll);

								gameWindow.conflictLog.addNewLine("Entering Close Combat: " + unit.callsign);
								gameWindow.conflictLog.addNewLine("Command: "
										+ (unit.getLeader() != null ? unit.getLeader().getSkill("Command") : "0"));
								gameWindow.conflictLog.addNewLine("Leadership Roll: " + leaderShipRoll);
								gameWindow.conflictLog.addNewLine("Unit Morale: " + unit.moral);

								if (unit.getLeader() == null || leaderShipRoll > unit.getLeader().getSkill("Command")) {
									for (int j = 0; j < unit.individuals.size(); j++) {
										int roll = rand.nextInt(100) + 1;
										gameWindow.conflictLog.addNewLine(unit.individuals.get(j).name + " "
												+ unit.individuals.get(j).number + " Morale Roll: " + roll);

										if (roll <= PCUtility.fleeChance(unit.individuals.get(j))) {
											flee.add(unit.individuals.get(j));
											rout = true;

										}
									}
								}
							}

						}

					}

				}

			}
			if (!closeCombat) {
				unit.closeCombat = false;
			}

			if (rout) {
				unit.flee(gameWindow, unit, flee);
			}

		}

	}

	// Targets X and Y, returns hex
	public Hex findHex(int x, int y) {

		for (Hex hex : hexes) {
			if (hex.xCord == x && hex.yCord == y)
				return hex;
		}

		return null;

	}

	// Finds the differance between the two locations
	public static int hexDif(Unit targetUnit, Unit shooterUnit) {
		return hexDif(targetUnit.X, targetUnit.Y, shooterUnit.X, shooterUnit.Y);
	}

	// Finds the differance between the two locations
	public static int hexDif(int x, int y, Unit shooterUnit) {
		return hexDif(x, y, shooterUnit.X, shooterUnit.Y);
	}

	public static int hexDif(int x, int y, int x1, int y1) {
		// System.out.println("Distance: "+dist(x, y, x1, y1));
		return dist(x, y, x1, y1);
	}

	public static int dist(int y1, int x1, int y2, int x2) {

		int du = x2 - x1;
		int dv = (y2 + Math.floorDiv(x2, 2)) - (y1 + Math.floorDiv(x1, 2));

		if (du >= 0 && dv >= 0 || (du < 0 && dv < 0)) {
			return Math.max(Math.abs(du), Math.abs(dv));
		} else {
			return Math.abs(du) + Math.abs(dv);
		}

	}

	public ArrayList<Unit> getUnitsInHex(String sideSpecified, int x, int y) {

		ArrayList<Unit> units = new ArrayList<>();

		for (Unit unit : initiativeOrder) {
			if (unit.X != x || unit.Y != y)
				continue;

			if (unit.side.equals(sideSpecified) || (sideSpecified.equals("") || sideSpecified.equals("None"))) {
				units.add(unit);
			}
		}

		return units;
	}

	public ArrayList<Unit> getUnitsInHexExcludingSide(String sideSpecified, int x, int y) {

		ArrayList<Unit> units = new ArrayList<>();

		for (Unit unit : initiativeOrder) {
			if (unit.X != x || unit.Y != y)
				continue;

			// System.out.println("Add unit");
			if (!unit.side.equals(sideSpecified) || (sideSpecified.equals("") || sideSpecified.equals("None"))) {
				units.add(unit);
			}
		}

		return units;
	}

	public static String getLogHead(Trooper target) {
		if (target == null)
			return "Target is Null: ";
		else if (GameWindow.findTrooperUnit(target) == null)
			return "Unit is null: ";
		return GameWindow.findTrooperUnit(target).callsign + ", " + target.number + ": " + target.name;
	}

	public static void addTrooperEntryToLog(Trooper trooper, String text) {
		GameWindow.gameWindow.conflictLog.addNewLineToQueue(GameWindow.gameWindow.getLogHead(trooper) + " " + text);
	}

	// Loops through units in initiaitive order
	// Looks for unit containing indvididual
	// Returns unit
	public static Unit findTrooperUnit(Trooper trooper) {
		ArrayList<Unit> units = gameWindow.initiativeOrder;

		for (Unit unit : units) {

			for (Trooper trooper1 : unit.getTroopers()) {

				if (trooper1.compareTo(trooper))
					return unit;

			}

		}

		return null;

	}

	public void randomEventCheck() {
		Random rand = new Random();

		if (rand.nextInt(100) + 1 < 5) {
			conflictLog.addNewLine("Random Event Has Occured");
		}

	}

	public void refreshCompanyUnits() {
		// Auto-generated method stub
		conflictLog.addNewLine("Refreshed Units");

		for (Unit unit : initiativeOrder) {

			for (Company company : setupWindow.companies) {

				if (company.getName().equals(unit.company)) {

					for (Unit secondUnit : company.getUnits()) {

						if (secondUnit.callsign.equals(unit.callsign))
							company.getUnits().set(company.getUnits().indexOf(secondUnit), unit);

					}

				}

			}

		}

		setupWindow.refreshCreated();
	}

	public void nextAction() {
		activeUnit = 0;
		advanceTime();
		markUnmoved();
		timedEvents();

		game.setCurrentAction(game.getCurrentAction() + 1);

		int maxAction = 3;

		for (Unit unit : initiativeOrder) {
			for (Trooper trooper : unit.getTroopers()) {
				if (game.getPhase() == 1 ? (trooper.P1 > maxAction) : (trooper.P2 > maxAction)) {
					maxAction = game.getPhase() == 1 ? trooper.P1 : trooper.P2;
				}
				
			}
		}

		if (game.getCurrentAction() > maxAction) {
			advancePhase();
		}

		if(game.getCurrentAction() - 1 <= 3) {
			game.smoke.advanceTime();
			game.wind.advanceTime();
			
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("Resolve melee combat round: ");

			InjuryLog.InjuryLog.addAlreadyInjured();
			MeleeCombatCalculator.resolveMeleeCombatRound();
			InjuryLog.InjuryLog.printResultsToLog();
		}
	}

	public void advancePhase() {
		// Check for end of round or phase
		if (game.getPhase() == 1) {
			game.setPhase(2);
			// End of phase
			conflictLog.addNewLineToQueue("     Round: " + game.getRound() + " Phase: 2");
			endOfPhase();
		} else {
			game.setPhase(1);
			int round = game.getRound() + 1;
			game.setRound(round);
			conflictLog.addNewLineToQueue("     Round: " + game.getRound() + " Phase: 1");
			// End of round
			endOfPhase();
			endOfRound();

		}
	}

	public void markUnmoved() {
		for (DeployedUnit deployedUnit : hexGrid.panel.deployedUnits) {
			deployedUnit.moved = false;
		}
	}

	public void timedEvents() {
		int currentAction = game.getCurrentAction();

		if (currentAction == 1 || currentAction == 2 || currentAction == 3) {
			advanceTimeFireMissions();
			spotCycle();
		}
	}

	public void advanceTime() {
		ExecutorService es = Executors.newFixedThreadPool(16);

		for (Unit unit : initiativeOrder) {
			es.submit(() -> {

				advanceTimeUnit(unit);

			});

			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		es.shutdown();
	}

	public void advanceTimeFireMissions() {

		ExecutorService es = Executors.newFixedThreadPool(16);

		for (Unit unit : initiativeOrder) {
			es.submit(() -> {
				// Fire Missions
				for (FireMission fireMission : unit.fireMissions) {
					// System.out.println("Calling Advance Time Fire Mission");
					fireMission.advanceTime();
				}

			});

			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		es.shutdown();

	}

	public void advanceTimeUnit(Unit unit) {

		int actions = game.getCurrentAction();

		ArrayList<Trooper> troopers = unit.getTroopers();

		for (int j = 0; j < troopers.size(); j++) {
			// System.out.println("Pass Trooper: "+j);
			
			setCombatActions(troopers.get(j));

			if (!troopers.get(j).alive) {
				troopers.get(j).spentPhase1 = troopers.get(j).P1;
				troopers.get(j).spentPhase2 = troopers.get(j).P2;
				continue;
			}

			// Increases time unconscious
			// Checks for wake up
			if (actions <= 3 && actions != 0) {

				if (!troopers.get(j).conscious) {

					if (troopers.get(j).timeUnconscious >= troopers.get(j).incapacitationTime) {
						troopers.get(j).timeUnconscious = 0;
						troopers.get(j).incapacitationTime = 0;
						conflictLog.addNewLineToQueue(troopers.get(j).number + " " + troopers.get(j).name
								+ " from unit:" + unit.callsign + "has awoken.");
						troopers.get(j).conscious = true;

					} else {
						troopers.get(j).timeUnconscious++;
					}
					troopers.get(j).spentPhase1 = troopers.get(j).P1;
					troopers.get(j).spentPhase2 = troopers.get(j).P2;

					continue;

				}

				Trooper trooper = troopers.get(j);
				if (trooper.personalShield != null) {
					PersonalShield ps = trooper.personalShield;

					if (ps.currentShieldStrength < ps.maxShieldStrength) {

						ps.currentShieldStrength += ps.maxShieldStrength * ps.rechargeRate;

						if (ps.currentShieldStrength > ps.maxShieldStrength)
							ps.currentShieldStrength = ps.maxShieldStrength;

					}

				}

			}

			// System.out.println("Pass Add Recovery TIme");
			if (unit.speed.equals("None")) {
				troopers.get(j).fatigueSystem.AddRecoveryTime(20);
			}

			if (game.getPhase() == 1) {
				if (troopers.get(j).spentPhase1 != actions) {

					if (troopers.get(j).spentPhase1 + 1 <= troopers.get(j).P1) {

						troopers.get(j).spentPhase1 += 1;
					}
				}
			} else {
				if (troopers.get(j).spentPhase2 != actions) {

					if (troopers.get(j).spentPhase2 + 1 <= troopers.get(j).P2) {

						troopers.get(j).spentPhase2 += 1;
					}
				}
			}
		}
	}

	public void CalcLOS() {
		System.out.println("Calc los");
		final long startTime = System.currentTimeMillis();
		ExecutorService es = Executors.newFixedThreadPool(16);
		for (Unit unit : initiativeOrder) {
			es.submit(() -> {
				unit.lineOfSight.clear();
				
				for (Unit targetUnit : initiativeOrder) {
					if (unit.side.equals(targetUnit.side))
						continue;
					
						CalculateLOS.calc(unit, targetUnit);
	
				}
			
			});
		}
		
		es.shutdown();
		
		for(var unit : initiativeOrder) {
			ArrayList<Unit> removeUnits = new ArrayList<Unit>();
			
			for(var spottedUnit : unit.lineOfSight)
				if(!spottedUnit.lineOfSight.contains(unit))
					removeUnits.add(spottedUnit);
			for(var removeUnit : removeUnits)
				unit.lineOfSight.remove(removeUnit);
		}
		
		for (Unit unit : initiativeOrder) {

			CalculateLOS.checkSpottedTroopers(unit);

		}
		final long endTime = System.currentTimeMillis();
		System.out.println("Total CalcLOS execution time: " + (endTime - startTime));
	}
}
