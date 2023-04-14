package Conflict;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SwingWorker;

import Trooper.Trooper;
import Trooper.generateSquad;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.ShootUtility;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Actions.ReactionToFireWindow;
import Actions.Spot;
import Actions.TargetedFire;
import Company.Formation.LeaderType;
import Hexes.Building;
import Hexes.Hex;
import Injuries.Injuries;
import Injuries.ResolveHits;
import Items.Weapons;
import Shoot.Shoot;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

public class BulkWindow {

	private String path = System.getProperty("user.dir") + "\\";
	// My vars

	public boolean targetFocusLock = false;
	public boolean individualListLock = false;
	public GameWindow gameWindow;
	public Game game;
	public ArrayList<BulkTrooper> bulkTroopers = new ArrayList<BulkTrooper>();
	public ArrayList<BulkTrooper> selectedBulkTroopers = new ArrayList<BulkTrooper>();
	public ArrayList<Unit> targetUnits = new ArrayList<Unit>();
	public Unit unit;
	public ArrayList<String> callsigns = new ArrayList<String>();
	public OpenUnit openUnit;

	// Jframe gui vars
	private JFrame frame;
	private JTextField textFieldDC;
	private JList individualsList;
	private JComboBox comboBoxTargetUnits;
	private JCheckBox chckbxFreeAction;
	private JSpinner caBonusSpinner;
	private JSpinner spinnerEALBonus;
	private JSpinner spinnerPercentBonus;
	private JComboBox comboBoxAimTime;
	private JCheckBox chckbxFullAuto;
	private JLabel lblAimTime;
	private JLabel lblTn;
	private JLabel lblPossibleShots;
	private JCheckBox chckbxLaser;
	private JCheckBox chckbxIrLaser;
	private JCheckBox chckbxThermals;
	private JCheckBox chckbxWeaponLights;
	private JCheckBox chckbxManualStance;
	private JComboBox comboBoxStance;
	private JSpinner spinnerNVGGen;
	private JTextField textFieldPen;
	private JComboBox comboBoxOF;
	private JComboBox comboBoxSpottingUnits;
	private JComboBox comboBoxScanArea;
	private JComboBox comboBoxAddUnit;
	private JList listSpottedUnitsArray;
	private JComboBox targetedFireFocus;
	private JCheckBox chckbxUnspottable;
	private JSpinner spinnerSpottingDifficulty;
	private JSpinner spinnerConsecutiveEALBonus;
	private JComboBox comboBoxWeapon;
	private JTextField textFieldCallsign;
	private JComboBox comboBoxTargetZone;
	private JComboBox comboBoxBuilding;
	private JComboBox comboBoxGrenadeTargets;
	private JComboBox comboBoxGrenade;
	private JSpinner spinnerGrenadeX;
	private JSpinner spinnerGrenadeY;
	private JSpinner spinnerTargetRoom;
	private JSpinner spinnerTargetFloor;
	private JSpinner spinnerThrowBonus;
	private JSpinner spinnerThrowEALBonus;
	private JSpinner spinnerLauncherX;
	private JSpinner spinnerLauncherY;
	private JCheckBox chckbxGuided;
	private JComboBox comboBoxWep;
	private JComboBox comboBoxDesignation;
	private JComboBox comboBoxSide;
	private JSpinner spinnerDivisor;

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.constructor
	 */
	public BulkWindow(Unit unit, GameWindow gameWindow, OpenUnit openUnit) {
		this.unit = unit;
		this.gameWindow = gameWindow;
		this.game = gameWindow.game;
		this.openUnit = openUnit;

		initializeWindow();
		setIndividuals();
		refreshIndividualList();
		setComboBoxes();
	}

	public BulkWindow(ArrayList<Unit> units) {

		this.unit = units.get(0);
		this.gameWindow = GameWindow.gameWindow;
		this.game = GameWindow.gameWindow.game;
		this.openUnit = null;

		ArrayList<String> sides = new ArrayList<>();
		
		ArrayList<Trooper> troopers = new ArrayList<>();

		for (Unit unit : units) {
			if(!sides.contains(unit.side)) {
				sides.add(unit.side);
			}
			
			
			for (Trooper trooper : unit.individuals) {
				troopers.add(trooper);
			}
		}
		
		if(sides.size() > 1) {
			System.out.println("Sort troopers");
			for(Trooper trooper : troopers) {
				trooper.kills = DiceRoller.randInt(0, 9);
			}
			Collections.sort(troopers, new Comparator<Trooper>() {
				   public int compare(Trooper b1, Trooper b2) {
					   
					   System.out.println("b1 CA: "+((b1.combatActions+ b1.sl) - b1.kills)+", b2 CA: "+((b2.combatActions + b2.sl) - b2.kills));
					   if((b1.combatActions+ b1.sl) - b1.kills < (b2.combatActions + b2.sl) - b2.kills)
						   return 1;
					   else if((b1.combatActions+ b1.sl) - b1.kills > (b2.combatActions + b2.sl) - b2.kills)
						   return -1;
					   else 
						   return 0;
				   }
			});
		}
		

		initializeWindow();
		setIndividuals(troopers);
		refreshIndividualList();
		setComboBoxes();
	}
	
	

	public BulkWindow(Unit unit, GameWindow gameWindow, OpenUnit openUnit, ArrayList<Trooper> cqbt) {
		this.unit = unit;
		this.gameWindow = gameWindow;
		this.game = gameWindow.game;
		this.openUnit = openUnit;
		initializeWindow();
		setIndividuals(cqbt);
		refreshIndividualList();
		setComboBoxes();
	}

	private void initializeWindow() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1113, 733);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;

		// Set the new frame location
		frame.setLocation(x, y);

		JButton btnClearSpotted = new JButton("Remove All Spotted");
		btnClearSpotted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnClearSpotted.setBounds(784, 211, 249, 23);
		btnClearSpotted.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						// System.out.println("Clear Spot1");

						for (Trooper trooper : unit.individuals) {

							trooper.spotted.clear();

						}

						// System.out.println("Clear Spot2");
						return null;
					}

				};

				worker.execute();

			}
		});

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(784, 149, 143, 20);
		comboBox.setForeground(Color.BLACK);
		// comboBox.setSelectedIndex(0);

		JLabel label = new JLabel("Remove Spotted");
		label.setBounds(782, 123, 226, 31);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Calibri", Font.PLAIN, 12));

		JButton button = new JButton("Remove");
		button.setBounds(944, 148, 89, 23);
		button.setForeground(Color.BLACK);

		JButton button_1 = new JButton("Spot Hex");
		button_1.setBounds(944, 104, 89, 23);
		button_1.setForeground(Color.BLACK);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(944, 74, 89, 20);
		comboBox_1.setForeground(Color.BLACK);
		// comboBox_1.setSelectedIndex(0);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(882, 105, 40, 20);
		spinner.setForeground(Color.BLACK);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(808, 105, 40, 20);
		spinner_1.setForeground(Color.BLACK);

		JLabel label_1 = new JLabel("Y: ");
		label_1.setBounds(858, 100, 30, 31);
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Calibri", Font.PLAIN, 12));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 205, 445, 351);
		frame.getContentPane().add(scrollPane);
		individualsList = new JList();
		individualsList.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

				JList theList = (JList) e.getSource();
				int index = theList.locationToIndex(e.getPoint());
				if (index > -1) {
					theList.setToolTipText(null);
					String text = "<html>";
					// System.out.println("Target Paint");
					for (Trooper trooper : bulkTroopers.get(index).targetTroopers) {
						text += trooper.findTargetUnit(gameWindow) + ":: " + trooper.number + ":: " + trooper.name;
						// System.out.println("Loop");
						if (!trooper.compareTo(bulkTroopers.get(index).targetTroopers
								.get(bulkTroopers.get(index).targetTroopers.size() - 1)))
							text += "<br>";

					}

					if (text.equals("<html>"))
						theList.setToolTipText(null);
					else
						theList.setToolTipText(text + "</html>");
				}

			}
		});
		individualsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {

				if (arg0.getValueIsAdjusting() || individualsList.getSelectedIndices().length < 1 || individualListLock)
					return;

				selected();

				// System.out.println("Entry Count:
				// "+individualsList.getSelectedValuesList().size());
			}
		});
		
		
		
		individualsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(individualsList);
		
		
		comboBoxAddUnit = new JComboBox();
		comboBoxAddUnit.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxAddUnit.setSelectedIndex(0);
		comboBoxAddUnit.setBounds(784, 74, 143, 20);
		comboBoxAddUnit.setForeground(Color.BLACK);
		// comboBox_2.setSelectedIndex(0);

		JLabel label_2 = new JLabel("X: ");
		label_2.setBounds(784, 100, 30, 31);
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Calibri", Font.PLAIN, 12));

		JButton btnAddSpotted = new JButton("Add Individual");
		btnAddSpotted.setBounds(784, 180, 115, 23);
		btnAddSpotted.setForeground(Color.BLACK);

		JButton button_3 = new JButton("Add Whole Unit");
		button_3.setBounds(909, 180, 124, 23);
		button_3.setForeground(Color.BLACK);

		JLabel label_3 = new JLabel("Individual");
		label_3.setBounds(944, 43, 143, 31);
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Calibri", Font.PLAIN, 12));

		JLabel label_4 = new JLabel("Unit");
		label_4.setBounds(784, 43, 143, 31);
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("Calibri", Font.PLAIN, 12));

		JLabel label_5 = new JLabel("Add Spotted");
		label_5.setBounds(784, 11, 162, 31);
		label_5.setForeground(Color.BLACK);
		label_5.setFont(new Font("Calibri", Font.PLAIN, 14));
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnClearSpotted);
		frame.getContentPane().add(label_5);
		frame.getContentPane().add(label_4);
		frame.getContentPane().add(comboBoxAddUnit);
		frame.getContentPane().add(label_3);
		frame.getContentPane().add(comboBox_1);
		frame.getContentPane().add(btnAddSpotted);
		frame.getContentPane().add(button_3);
		frame.getContentPane().add(spinner_1);
		frame.getContentPane().add(label_2);
		frame.getContentPane().add(label_1);
		frame.getContentPane().add(spinner);
		frame.getContentPane().add(button_1);
		frame.getContentPane().add(comboBox);
		frame.getContentPane().add(label);
		frame.getContentPane().add(button);

		JLabel label_6 = new JLabel("PEN");
		label_6.setForeground(Color.BLACK);
		label_6.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_6.setDoubleBuffered(true);
		label_6.setBounds(784, 311, 30, 31);
		frame.getContentPane().add(label_6);

		textFieldPen = new JTextField();
		textFieldPen.setForeground(Color.BLACK);
		textFieldPen.setColumns(10);
		textFieldPen.setBounds(814, 316, 34, 20);
		frame.getContentPane().add(textFieldPen);

		JLabel label_7 = new JLabel("DC");
		label_7.setForeground(Color.BLACK);
		label_7.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_7.setDoubleBuffered(true);
		label_7.setBounds(858, 311, 30, 31);
		frame.getContentPane().add(label_7);

		textFieldDC = new JTextField();
		textFieldDC.setForeground(Color.BLACK);
		textFieldDC.setColumns(10);
		textFieldDC.setBounds(882, 316, 40, 20);
		frame.getContentPane().add(textFieldDC);

		comboBoxOF = new JComboBox();
		comboBoxOF.setModel(new DefaultComboBoxModel(new String[] { "Open", "Fire" }));
		comboBoxOF.setSelectedIndex(0);
		comboBoxOF.setForeground(Color.BLACK);
		// comboBox_3.setSelectedIndex(0);
		comboBoxOF.setBounds(967, 316, 62, 20);
		frame.getContentPane().add(comboBoxOF);

		JLabel label_8 = new JLabel("O/F");
		label_8.setForeground(Color.BLACK);
		label_8.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_8.setDoubleBuffered(true);
		label_8.setBounds(932, 311, 30, 31);
		frame.getContentPane().add(label_8);

		JButton button_4 = new JButton("Add Injury");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

					Trooper trooper = bulkTrooper.trooper;

					ResolveHits resolveHits = new ResolveHits(trooper);

					Injuries newInjury = resolveHits.getPCHitsManual(Integer.parseInt(textFieldPen.getText()),
							Integer.parseInt(textFieldDC.getText()), comboBoxOF.getSelectedIndex());

					if (newInjury == null) {
						gameWindow.conflictLog.addNewLine("EPEN < 0.5");
					}

				}

			}
		});
		button_4.setForeground(Color.BLACK);
		button_4.setBounds(882, 347, 147, 23);
		frame.getContentPane().add(button_4);

		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] { "Random Loc." }));
		comboBox_4.setSelectedIndex(0);
		comboBox_4.setForeground(Color.BLACK);
		// comboBox_4.setSelectedIndex(0);
		comboBox_4.setBounds(784, 348, 92, 20);
		frame.getContentPane().add(comboBox_4);

		JLabel lblAddInjuries = new JLabel("Add Injuries");
		lblAddInjuries.setForeground(Color.BLACK);
		lblAddInjuries.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblAddInjuries.setBounds(784, 293, 162, 23);
		frame.getContentPane().add(lblAddInjuries);

		listSpottedUnitsArray = new JList();
		listSpottedUnitsArray.setForeground(Color.BLACK);
		listSpottedUnitsArray.setBackground(Color.WHITE);
		listSpottedUnitsArray.setBounds(625, 43, 143, 160);
		frame.getContentPane().add(listSpottedUnitsArray);

		JLabel label_9 = new JLabel("Spotting Units");
		label_9.setForeground(Color.BLACK);
		label_9.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_9.setBounds(625, 11, 369, 31);
		frame.getContentPane().add(label_9);

		spinnerNVGGen = new JSpinner();
		spinnerNVGGen.setForeground(Color.BLACK);
		spinnerNVGGen.setBounds(625, 320, 34, 20);
		frame.getContentPane().add(spinnerNVGGen);

		JLabel label_10 = new JLabel("Gen:");
		label_10.setForeground(Color.BLACK);
		label_10.setFont(new Font("Calibri", Font.PLAIN, 12));
		label_10.setBounds(596, 317, 40, 28);
		frame.getContentPane().add(label_10);

		JButton button_2 = new JButton("Add Thermals");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					Trooper trooper = bulkTrooper.trooper;

					trooper.thermalVision = true;

				}

			}
		});
		button_2.setForeground(Color.BLACK);
		button_2.setBounds(669, 319, 97, 23);
		frame.getContentPane().add(button_2);

		JButton button_5 = new JButton("Add NVGs");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					Trooper trooper = bulkTrooper.trooper;

					if ((int) spinnerNVGGen.getValue() < 1 || (int) spinnerNVGGen.getValue() > 5) {
						gameWindow.conflictLog.addNewLine("NVG Gen not a value from 1 to 5.");
						return;
					}

					trooper.nightVision = true;
					trooper.nightVisionEffectiveness = (int) spinnerNVGGen.getValue();

				}

			}
		});
		button_5.setForeground(Color.BLACK);
		button_5.setBounds(479, 322, 114, 23);
		frame.getContentPane().add(button_5);

		JCheckBox checkBox = new JCheckBox("NVGs");
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

					Trooper trooper = bulkTrooper.trooper;

					if (!trooper.nightVision) {
						gameWindow.conflictLog.addNewLine("This trooper does not have night vision.");
						return;
					}

					if (chckbxWeaponLights.isSelected())
						trooper.nightVisionInUse = true;
					else
						trooper.nightVisionInUse = false;

				}

			}
		});
		checkBox.setForeground(Color.BLACK);
		checkBox.setFont(new Font("Calibri", Font.BOLD, 12));
		checkBox.setBackground(Color.WHITE);
		checkBox.setBounds(625, 293, 143, 23);
		frame.getContentPane().add(checkBox);

		JButton button_6 = new JButton("Clear");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				listSpottedUnitsArray.removeAll();
				callsigns.clear();

				DefaultListModel listSpottedUnits = new DefaultListModel();

				for (int i = 0; i < callsigns.size(); i++) {
					listSpottedUnits.addElement(callsigns.get(i));

				}

				listSpottedUnitsArray.setModel(listSpottedUnits);

			}
		});
		button_6.setForeground(Color.BLACK);
		button_6.setBounds(479, 126, 136, 25);
		frame.getContentPane().add(button_6);

		JButton button_7 = new JButton("Spot All");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						ExecutorService es = Executors.newFixedThreadPool(16);
						
						try {
							
							
							
							for (Trooper trooper : getSelectedTroopers()) {

								
								es.submit(() -> {
									// System.out.println("Spot Test All 1");
									spotTestAll(trooper, unit);
									// System.out.println("Spot Test All 2");

									// If not a free test
									if (!chckbxFreeAction.isSelected()) {
										actionSpent(trooper);
									}
								});
								
								try {
									TimeUnit.MILLISECONDS.sleep(75);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

							}

							es.shutdown();
							
							// refreshTargets();
						} catch (Exception e2) {
							System.out.println("toString(): " + e2.toString());
							System.out.println("getMessage(): " + e2.getMessage());
							System.out.println("StackTrace: ");
							e2.printStackTrace();
						}

						return null;
					}

					@Override
					protected void done() {

						// Clears list
						listSpottedUnitsArray.removeAll();
						callsigns.clear();

						DefaultListModel listSpottedUnits = new DefaultListModel();

						for (int i = 0; i < callsigns.size(); i++) {
							listSpottedUnits.addElement(callsigns.get(i));

						}

						listSpottedUnitsArray.setModel(listSpottedUnits);

						refreshIndividualList();
						
						GameWindow.gameWindow.conflictLog.addQueuedText();

					}

				};

				worker.execute();

			}
		});
		button_7.setForeground(Color.BLACK);
		button_7.setBounds(479, 154, 136, 25);
		frame.getContentPane().add(button_7);

		chckbxFreeAction = new JCheckBox("Free Action");
		chckbxFreeAction.setForeground(Color.BLACK);
		chckbxFreeAction.setFont(new Font("Calibri", Font.BOLD, 12));
		chckbxFreeAction.setBackground(Color.WHITE);
		chckbxFreeAction.setBounds(86, 169, 97, 27);
		frame.getContentPane().add(chckbxFreeAction);

		comboBoxScanArea = new JComboBox();
		comboBoxScanArea
				.setModel(new DefaultComboBoxModel(new String[] { "60 Degrees", "180 Degrees", "20 Yard Hex" }));
		comboBoxScanArea.setSelectedIndex(0);
		// comboBox_5.setSelectedIndex(0);
		comboBoxScanArea.setForeground(Color.BLACK);
		comboBoxScanArea.setBounds(479, 183, 136, 20);
		frame.getContentPane().add(comboBoxScanArea);

		JButton button_8 = new JButton("Roll Spot");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						try {

							ExecutorService es = Executors.newFixedThreadPool(16);
							
							for (Trooper trooper : getSelectedTroopers()) {

								es.submit(() -> {
									// Loops through all signs, performs spotting test
									for (int i = 0; i < callsigns.size(); i++) {
										// System.out.println("Spot Test 1");
										spotTest(callsigns.get(i), trooper, unit);
										// System.out.println("Spot Test 2");
									}

									// If not a free test
									if (!chckbxFreeAction.isSelected()) {
										actionSpent(trooper);
									}
								});
								
								try {
									TimeUnit.MILLISECONDS.sleep(75);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
				

							}
							
							es.shutdown();
							
							

						} catch (Exception e2) {
							System.out.println("toString(): " + e2.toString());
							System.out.println("getMessage(): " + e2.getMessage());
							System.out.println("StackTrace: ");
							e2.printStackTrace();
						}

						return null;
					}

					@Override
					protected void done() {

						// Clears list
						listSpottedUnitsArray.removeAll();
						callsigns.clear();

						DefaultListModel listSpottedUnits = new DefaultListModel();

						for (int i = 0; i < callsigns.size(); i++) {
							listSpottedUnits.addElement(callsigns.get(i));

						}

						listSpottedUnitsArray.setModel(listSpottedUnits);

						refreshIndividualList();
						// refreshTargets();
						GameWindow.gameWindow.conflictLog.addQueuedText();
					}

				};

				worker.execute();

			}
		});
		button_8.setForeground(Color.BLACK);
		button_8.setBounds(479, 99, 136, 25);
		frame.getContentPane().add(button_8);

		JButton button_9 = new JButton("Add Unit");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBoxSpottingUnits.getSelectedIndex() > 0) {

					String callsign = comboBoxSpottingUnits.getSelectedItem().toString();

					if (!callsigns.contains(callsign)) {
						callsigns.add(callsign);
					}

					listSpottedUnitsArray.removeAll();

					DefaultListModel listSpottedUnits = new DefaultListModel();

					for (int i = 0; i < callsigns.size(); i++) {
						listSpottedUnits.addElement(callsigns.get(i));

					}

					listSpottedUnitsArray.setModel(listSpottedUnits);

				}

			}
		});
		button_9.setForeground(Color.BLACK);
		button_9.setBounds(479, 71, 136, 25);
		frame.getContentPane().add(button_9);

		comboBoxSpottingUnits = new JComboBox();
		comboBoxSpottingUnits.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxSpottingUnits.setSelectedIndex(0);
		comboBoxSpottingUnits.setForeground(Color.BLACK);
		// comboBox_6.setSelectedIndex(0);
		comboBoxSpottingUnits.setBounds(479, 43, 136, 23);
		frame.getContentPane().add(comboBoxSpottingUnits);

		JLabel label_11 = new JLabel("Spot");
		label_11.setForeground(Color.BLACK);
		label_11.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_11.setBounds(479, 11, 53, 31);
		frame.getContentPane().add(label_11);

		comboBoxStance = new JComboBox();
		comboBoxStance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					Trooper trooper = bulkTrooper.trooper;

					if (comboBoxStance.getSelectedItem().toString().equals(trooper.stance)) {
						return;
					}

					// System.out.println("Changing Stance, Trooper Stance: |"+trooper.stance+"| Box
					// Stance: |"+ comboBoxStance.getSelectedItem().toString()+"|");
					trooper.stance = comboBoxStance.getSelectedItem().toString();

					if (bulkTrooper.targetedFire != null) {
						bulkTrooper.targetedFire.spentCA++;
					} else {

						bulkTrooper.spentCA++;
					}

					try {
						// PCShots(bulkTrooper, getTargetTrooper(bulkTrooper));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

				PCFireGuiUpdates();

			}
		});
		comboBoxStance.setModel(new DefaultComboBoxModel(new String[] { "Standing ", "Crouched", "Prone" }));
		comboBoxStance.setForeground(Color.BLACK);
		comboBoxStance.setBounds(479, 347, 147, 23);
		frame.getContentPane().add(comboBoxStance);

		JLabel label_12 = new JLabel("Misc.");
		label_12.setForeground(Color.BLACK);
		label_12.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_12.setBounds(479, 235, 146, 31);
		frame.getContentPane().add(label_12);

		chckbxManualStance = new JCheckBox("Manual Stance");
		chckbxManualStance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					Trooper trooper = bulkTrooper.trooper;

					if (chckbxManualStance.isSelected())
						trooper.manualStance = true;
					else
						trooper.manualStance = false;

				}

			}
		});
		chckbxManualStance.setForeground(Color.BLACK);
		chckbxManualStance.setFont(new Font("Calibri", Font.BOLD, 12));
		chckbxManualStance.setBackground(Color.WHITE);
		chckbxManualStance.setBounds(637, 348, 131, 23);
		frame.getContentPane().add(chckbxManualStance);

		chckbxLaser = new JCheckBox("Laser");
		chckbxLaser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

					if (!getWeapon(bulkTrooper.trooper).laser) {
						gameWindow.conflictLog.addNewLine("This trooper does not have a laser pointer.");
						return;
					}

					if (chckbxLaser.isSelected())
						bulkTrooper.trooper.weaponLaserOn = true;
					else
						bulkTrooper.trooper.weaponLaserOn = false;

				}

			}
		});
		chckbxLaser.setForeground(Color.BLACK);
		chckbxLaser.setFont(new Font("Calibri", Font.BOLD, 12));
		chckbxLaser.setBackground(Color.WHITE);
		chckbxLaser.setBounds(479, 267, 74, 23);
		frame.getContentPane().add(chckbxLaser);

		chckbxIrLaser = new JCheckBox("IR Laser");
		chckbxIrLaser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

					if (!getWeapon(bulkTrooper.trooper).irLaser) {
						gameWindow.conflictLog.addNewLine("This trooper does not have a IR laser pointer.");
						return;
					}

					if (chckbxIrLaser.isSelected())
						bulkTrooper.trooper.weaponIRLaserOn = true;
					else
						bulkTrooper.trooper.weaponIRLaserOn = false;

				}

			}
		});
		chckbxIrLaser.setForeground(Color.BLACK);
		chckbxIrLaser.setFont(new Font("Calibri", Font.BOLD, 12));
		chckbxIrLaser.setBackground(Color.WHITE);
		chckbxIrLaser.setBounds(559, 267, 74, 23);
		frame.getContentPane().add(chckbxIrLaser);

		chckbxWeaponLights = new JCheckBox("Weapon Lights");
		chckbxWeaponLights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

					Trooper trooper = bulkTrooper.trooper;

					if (!getWeapon(trooper).light) {
						gameWindow.conflictLog.addNewLine("This trooper does not have weapon lights.");
						return;
					}

					if (chckbxWeaponLights.isSelected())
						trooper.weaponLightOn = true;
					else
						trooper.weaponLightOn = false;

				}

			}
		});
		chckbxWeaponLights.setForeground(Color.BLACK);
		chckbxWeaponLights.setFont(new Font("Calibri", Font.BOLD, 12));
		chckbxWeaponLights.setBackground(Color.WHITE);
		chckbxWeaponLights.setBounds(479, 293, 142, 23);
		frame.getContentPane().add(chckbxWeaponLights);

		chckbxThermals = new JCheckBox("Thermals");
		chckbxThermals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

					if (!bulkTrooper.trooper.thermalVision) {
						gameWindow.conflictLog.addNewLine("This trooper does not have thermal vision.");
						return;
					}

					if (chckbxThermals.isSelected())
						bulkTrooper.trooper.thermalVisionInUse = true;
					else
						bulkTrooper.trooper.thermalVisionInUse = false;

				}

			}
		});
		chckbxThermals.setForeground(Color.BLACK);
		chckbxThermals.setFont(new Font("Calibri", Font.BOLD, 12));
		chckbxThermals.setBackground(Color.WHITE);
		chckbxThermals.setBounds(639, 267, 129, 23);
		frame.getContentPane().add(chckbxThermals);

		JLabel label_13 = new JLabel("CA Bonus:");
		label_13.setForeground(Color.BLACK);
		label_13.setFont(new Font("Calibri", Font.PLAIN, 12));
		label_13.setBounds(479, 567, 74, 31);
		frame.getContentPane().add(label_13);

		caBonusSpinner = new JSpinner();
		caBonusSpinner.setBounds(550, 572, 34, 20);
		frame.getContentPane().add(caBonusSpinner);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

					bulkTrooper.CA += (int) caBonusSpinner.getValue();

				}

			}
		});
		btnApply.setForeground(Color.BLACK);
		btnApply.setBounds(596, 570, 114, 23);
		frame.getContentPane().add(btnApply);

		JLabel label_16 = new JLabel("Starting Aim T:");
		label_16.setBackground(Color.WHITE);
		label_16.setForeground(Color.BLACK);
		label_16.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_16.setBounds(480, 442, 114, 31);
		frame.getContentPane().add(label_16);

		comboBoxAimTime = new JComboBox();
		comboBoxAimTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (individualsList.getSelectedIndices().length < 1)
					return;

				for (BulkTrooper trooper : selectedBulkTroopers) {

					Shoot shoot = trooper.shoot;

					if (shoot == null)
						continue;

					SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

						@Override
						protected Void doInBackground() throws Exception {

							if (comboBoxAimTime.getSelectedIndex() == 0)
								shoot.autoAim();
							else
								shoot.setAimTime(comboBoxAimTime.getSelectedIndex() - 1);

							if (comboBoxTargetZone.getSelectedIndex() > 0) {
								setCalledShotBounds(shoot);
							}

							return null;
						}

						@Override
						protected void done() {
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							guiUpdates();

						}

					};

					worker.execute();
				}

			}
		});
		comboBoxAimTime.setModel(new DefaultComboBoxModel(new String[] { "Auto", "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "10", "11", "12", "13", "14", "15" }));

		comboBoxAimTime.setBackground(Color.WHITE);
		comboBoxAimTime.setForeground(Color.BLACK);
		// comboBox_10.setSelectedIndex(0);
		comboBoxAimTime.setBounds(479, 470, 87, 20);
		frame.getContentPane().add(comboBoxAimTime);

		comboBoxTargetZone = new JComboBox();
		comboBoxTargetZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper trooper : selectedBulkTroopers) {
					try {
						setCalledShotBounds(trooper.shoot);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				guiUpdates();

			}
		});
		comboBoxTargetZone.setModel(new DefaultComboBoxModel(new String[] { "Auto", "Head", "Body", "Legs" }));
		comboBoxTargetZone.setSelectedIndex(0);
		comboBoxTargetZone.setBackground(Color.WHITE);
		comboBoxTargetZone.setForeground(Color.BLACK);
		// comboBox_11.setSelectedIndex(0);
		comboBoxTargetZone.setBounds(596, 470, 113, 20);
		frame.getContentPane().add(comboBoxTargetZone);

		JLabel label_17 = new JLabel("Target Zone:");
		label_17.setBackground(Color.WHITE);
		label_17.setForeground(Color.BLACK);
		label_17.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_17.setBounds(596, 442, 114, 31);
		frame.getContentPane().add(label_17);

		spinnerEALBonus = new JSpinner();
		spinnerEALBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerEALBonus.setBackground(Color.WHITE);
		spinnerEALBonus.setForeground(Color.BLACK);
		spinnerEALBonus.setBounds(479, 536, 74, 20);
		frame.getContentPane().add(spinnerEALBonus);

		JLabel label_18 = new JLabel("EAL Bonus:");
		label_18.setBackground(Color.WHITE);
		label_18.setForeground(Color.BLACK);
		label_18.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_18.setBounds(479, 497, 87, 31);
		frame.getContentPane().add(label_18);

		spinnerPercentBonus = new JSpinner();
		spinnerPercentBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerPercentBonus.setBackground(Color.WHITE);
		spinnerPercentBonus.setForeground(Color.BLACK);
		spinnerPercentBonus.setBounds(563, 536, 74, 20);
		frame.getContentPane().add(spinnerPercentBonus);

		JLabel label_19 = new JLabel("% Bonus:");
		label_19.setBackground(Color.WHITE);
		label_19.setForeground(Color.BLACK);
		label_19.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_19.setBounds(563, 497, 74, 31);
		frame.getContentPane().add(label_19);

		JButton button_10 = new JButton("Aim");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						
						for (BulkTrooper bulkTrooper : selectedBulkTroopers) {
							
							int newAim = bulkTrooper.shoot.aimTime + (bulkTrooper.trooper.combatActions - bulkTrooper.shoot.spentCombatActions);

							newAim = newAim >= bulkTrooper.shoot.wep.aimTime.size() ? bulkTrooper.shoot.wep.aimTime.size() - 1 : newAim;

							bulkTrooper.shoot.spentCombatActions += newAim - bulkTrooper.shoot.aimTime;

							bulkTrooper.shoot.setAimTime(newAim);
							
							if (!chckbxFreeAction.isSelected() && bulkTrooper.shoot.spentCombatActions >= bulkTrooper.trooper.combatActions) {
								actionSpent(bulkTrooper.trooper);
							}
							bulkTrooper.shootReset = false;

						}
						
						return null;
					}

					@Override
					protected void done() {

						guiUpdates();
						refreshIndividualList();
					}

				};

				worker.execute();

			}
		});
		button_10.setForeground(Color.BLACK);
		button_10.setBounds(648, 535, 87, 23);
		frame.getContentPane().add(button_10);

		lblAimTime = new JLabel("Mean Aim Time:");
		lblAimTime.setBackground(Color.WHITE);
		lblAimTime.setForeground(Color.BLACK);
		lblAimTime.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblAimTime.setBounds(647, 501, 136, 23);
		frame.getContentPane().add(lblAimTime);

		JButton btnSingle = new JButton("Single");
		btnSingle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						InjuryLog.InjuryLog.addAlreadyInjured();

						try {

							ExecutorService es = Executors.newFixedThreadPool(16);

							for (BulkTrooper bulkTrooper : selectedBulkTroopers) {
								Shoot shoot = bulkTrooper.shoot;
								if (shoot == null)
									continue;

								es.submit(() -> {
									System.out.println("Single Fire");
									try {

										if (comboBoxTargetUnits.getSelectedIndex() > 0)
											shoot.suppressiveFire(shoot.wep.suppressiveROF);
										else if (chckbxFullAuto.isSelected())
											shoot.burst();
										else
											shoot.shot(chckbxGuided.isSelected());

										try {
											TimeUnit.MILLISECONDS.sleep(15);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										
										valleyValidTargetCheck(shoot, bulkTrooper);
										
										GameWindow.gameWindow.conflictLog
												.addNewLineToQueue("Results: " + shoot.shotResults);
										//System.out.println("Supp results: "+shoot.shotResults);
										
										
									} catch (Exception e) {
										e.printStackTrace();
									}
								});

								try {
									TimeUnit.MILLISECONDS.sleep(75);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								bulkTrooper.shootReset = false;

							}

							try {
								TimeUnit.MILLISECONDS.sleep(250);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							es.shutdown();

						} catch (Exception e2) {
							e2.printStackTrace();
						}

						return null;
					}

					@Override
					protected void done() {
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						for(BulkTrooper bulkTrooper : selectedBulkTroopers) {
							if (!freeAction() && ((bulkTrooper.shoot.spentCombatActions >= bulkTrooper.shoot.shooter.combatActions) 
									|| comboBoxTargetUnits.getSelectedIndex() > 0)) {
								System.out.println("Action spent suppress");
								actionSpent(bulkTrooper.trooper);
							}
						}
						
						guiUpdates();
						refreshIndividualList();
						InjuryLog.InjuryLog.printResultsToLog();
						gameWindow.conflictLog.addQueuedText();
					}

				};

				worker.execute();

			}
		});
		btnSingle.setForeground(Color.BLACK);
		btnSingle.setBounds(744, 535, 87, 23);
		frame.getContentPane().add(btnSingle);

		lblTn = new JLabel("Mean TN: 0");
		lblTn.setBackground(Color.WHITE);
		lblTn.setForeground(Color.BLACK);
		lblTn.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblTn.setBounds(963, 501, 124, 23);
		frame.getContentPane().add(lblTn);

		chckbxFullAuto = new JCheckBox("Full Auto");
		chckbxFullAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				guiUpdates();
				//refreshIndividualList();
			}
		});
		chckbxFullAuto.setForeground(Color.BLACK);
		chckbxFullAuto.setBackground(Color.WHITE);
		chckbxFullAuto.setBounds(715, 469, 74, 23);
		frame.getContentPane().add(chckbxFullAuto);

		comboBoxTargetUnits = new JComboBox();
		comboBoxTargetUnits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						try {

							ExecutorService es = Executors.newFixedThreadPool(16);

							ArrayList<BulkTrooper> currentlySelectedBulkTroopers = getSelectedBulkTroopers();
							ArrayList<BulkTrooper> removeTrooper = new ArrayList<>();

							for (BulkTrooper bulkTrooper : selectedBulkTroopers) {
								if (!currentlySelectedBulkTroopers.contains(bulkTrooper)) {
									removeTrooper.add(bulkTrooper);
								}
							}

							for (BulkTrooper bulkTrooper : removeTrooper) {
								selectedBulkTroopers.remove(bulkTrooper);
							}

							
							for (BulkTrooper bulkTrooper : currentlySelectedBulkTroopers) {
							
								
								
								es.submit(() -> {
									System.out.println("Submit Suppressive Fire");
									try {
										if(comboBoxTargetUnits.getSelectedIndex() > 1) {
											Unit shooterUnit = bulkTrooper.trooper.returnTrooperUnit(GameWindow.gameWindow);
											bulkTrooper.shoot = ShootUtility.setTargetUnit(shooterUnit, targetUnits.get(comboBoxTargetUnits.getSelectedIndex() - 2),
													bulkTrooper.shoot, bulkTrooper.trooper, bulkTrooper.trooper.wep, -1);
											
											if(bulkTrooper.shootReset) {
												bulkTrooper.shoot.spentCombatActions = 0; 
												bulkTrooper.shoot.previouslySpentCa = 0;
											}
											
											System.out.println("Create bulk suppressive shot: "+(bulkTrooper.shoot == null ? "is null" : "not null"));
										} else if(comboBoxTargetUnits.getSelectedIndex() == 1){
											
											Unit shooterUnit = bulkTrooper.trooper.returnTrooperUnit(GameWindow.gameWindow);
											
											bulkTrooper.shoot = ShootUtility.setTargetUnit(shooterUnit, 
													shooterUnit.lineOfSight.get(DiceRoller.randInt(0, shooterUnit.lineOfSight.size()-1)),
													bulkTrooper.shoot, bulkTrooper.trooper, bulkTrooper.trooper.wep, -1);
											
											if(bulkTrooper.shootReset) {
												bulkTrooper.shoot.spentCombatActions = 0; 
												bulkTrooper.shoot.previouslySpentCa = 0;
											}
											
											System.out.println("Create bulk suppressive shot: "+(bulkTrooper.shoot == null ? "is null" : "not null"));
											
										} else if(comboBoxTargetUnits.getSelectedIndex() <= 0){
											setValidTarget(bulkTrooper);
										}

										if (comboBoxAimTime.getSelectedIndex() == 0 && bulkTrooper.shoot != null)
											bulkTrooper.shoot.autoAim();
										
										if (comboBoxTargetZone.getSelectedIndex() > 0 && comboBoxTargetUnits.getSelectedIndex() == 0) {
											setCalledShotBounds(bulkTrooper.shoot);
										}

									} catch (Exception e) {
										e.printStackTrace();
									}
								});
								try {
									TimeUnit.MILLISECONDS.sleep(75);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								if(!selectedBulkTroopers.contains(bulkTrooper))
									selectedBulkTroopers.add(bulkTrooper);
							}

							try {
								TimeUnit.MILLISECONDS.sleep(150);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							es.shutdown();

							System.out.println("Finished Threads");

						} catch (Exception e) {
							e.printStackTrace();
						}

						return null;
					}

					@Override
					protected void done() {
						try {
							TimeUnit.MILLISECONDS.sleep(250);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						gameWindow.conflictLog.addQueuedText();
						guiUpdates();
						System.out.println("Selected Bulk Troopers Suppression Size: " + selectedBulkTroopers.size());
					}

				};

				worker.execute();
				
			}
		});
		comboBoxTargetUnits.setForeground(Color.BLACK);
		// comboBox_12.setSelectedIndex(0);
		comboBoxTargetUnits.setBounds(559, 617, 178, 21);
		frame.getContentPane().add(comboBoxTargetUnits);

		JLabel label_23 = new JLabel("Taget Unit: ");
		label_23.setForeground(Color.BLACK);
		label_23.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_23.setBounds(479, 614, 125, 31);
		frame.getContentPane().add(label_23);

		JLabel label_27 = new JLabel("Suppressive Fire");
		label_27.setForeground(Color.BLACK);
		label_27.setFont(new Font("Calibri", Font.PLAIN, 18));
		label_27.setBounds(479, 596, 221, 23);
		frame.getContentPane().add(label_27);

		JLabel lblTargetedFire = new JLabel("Targeted Fire");
		lblTargetedFire.setForeground(Color.BLACK);
		lblTargetedFire.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblTargetedFire.setBounds(479, 422, 221, 23);
		frame.getContentPane().add(lblTargetedFire);

		JLabel lblBulkOperations = new JLabel("Bulk Operations");
		lblBulkOperations.setForeground(Color.BLACK);
		lblBulkOperations.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblBulkOperations.setBounds(10, 10, 147, 20);
		frame.getContentPane().add(lblBulkOperations);

		JLabel lblIndividuals = new JLabel("Individuals");
		lblIndividuals.setForeground(Color.BLACK);
		lblIndividuals.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblIndividuals.setBackground(Color.WHITE);
		lblIndividuals.setBounds(10, 171, 87, 23);
		frame.getContentPane().add(lblIndividuals);

		

		lblPossibleShots = new JLabel("Mean Possible Shots:");
		lblPossibleShots.setForeground(Color.BLACK);
		lblPossibleShots.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblPossibleShots.setBackground(Color.WHITE);
		lblPossibleShots.setBounds(784, 501, 178, 23);
		frame.getContentPane().add(lblPossibleShots);

		JButton btnVolley = new JButton("Volley");
		btnVolley.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					volley();
				} catch (Exception ecx) {
					ecx.printStackTrace();
				}

			}
		});
		btnVolley.setForeground(Color.BLACK);
		btnVolley.setBounds(841, 535, 87, 23);
		frame.getContentPane().add(btnVolley);

		JButton button_7_1 = new JButton("HD");
		button_7_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					if (!bulkTrooper.trooper.inCover) {
						gameWindow.conflictLog.addNewLine(bulkTrooper.trooper.number + " " + bulkTrooper.trooper.name
								+ " is not in cover and can't hunker down.");
						continue;
					}

					if (bulkTrooper.trooper.HD)
						bulkTrooper.trooper.HD = false;
					else
						bulkTrooper.trooper.HD = true;

					if (!chckbxFreeAction.isSelected())
						actionSpent(bulkTrooper.trooper);

				}

				refreshIndividualList();

			}
		});
		button_7_1.setForeground(Color.BLACK);
		button_7_1.setBounds(479, 212, 136, 25);
		frame.getContentPane().add(button_7_1);

		targetedFireFocus = new JComboBox();
		targetedFireFocus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (individualsList.getSelectedIndices().length < 1 || targetFocusLock)
					return;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						try {

							ExecutorService es = Executors.newFixedThreadPool(16);

							ArrayList<BulkTrooper> currentlySelectedBulkTroopers = getSelectedBulkTroopers();
							ArrayList<BulkTrooper> removeTrooper = new ArrayList<>();

							for (BulkTrooper bulkTrooper : selectedBulkTroopers) {
								if (!currentlySelectedBulkTroopers.contains(bulkTrooper)) {
									removeTrooper.add(bulkTrooper);
								}
							}

							for (BulkTrooper bulkTrooper : removeTrooper) {
								selectedBulkTroopers.remove(bulkTrooper);
							}

							for (BulkTrooper bulkTrooper : currentlySelectedBulkTroopers) {
								
								if (bulkTrooper.targetTroopers.size() > 0) {
									es.submit(() -> {
										System.out.println("Submit");
										try {
											setValidTarget(bulkTrooper);

											if (comboBoxAimTime.getSelectedIndex() == 0)
												bulkTrooper.shoot.autoAim();

											if (comboBoxTargetZone.getSelectedIndex() > 0) {
												setCalledShotBounds(bulkTrooper.shoot);
											}

										} catch (Exception e) {
											e.printStackTrace();
										}
									});
								}
								
								if(!selectedBulkTroopers.contains(bulkTrooper))
									selectedBulkTroopers.add(bulkTrooper);
							}

							es.shutdown();

							System.out.println("Finished Threads");

							
						} catch (Exception e) {
							e.printStackTrace();
						}

						return null;
					}

					@Override
					protected void done() {
						try {
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						// System.out.println("Done");
						gameWindow.conflictLog.addQueuedText();
						// PCFireGuiUpdates();
						guiUpdates();
						System.out.println("Selected Bulk Troopers Size: " + selectedBulkTroopers.size());
					}

				};

				worker.execute();

			}
		});
		targetedFireFocus.setModel(new DefaultComboBoxModel(new String[] { "No Target Unit Focus" }));
		targetedFireFocus.setForeground(Color.BLACK);
		targetedFireFocus.setBackground(Color.WHITE);
		targetedFireFocus.setBounds(944, 535, 143, 20);
		frame.getContentPane().add(targetedFireFocus);

		JButton btnFresh = new JButton("Fresh");
		btnFresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// System.out.println("Phase: "+game.getPhase()+", Action:
				// "+game.getCurrentAction());

				//individualsList.clearSelection();
				//ArrayList<Integer> indexes = new ArrayList<Integer>();
				addFresh();
				// System.out.println("Set Indexes: "+indices.length);
			}
		});
		btnFresh.setForeground(Color.BLACK);
		btnFresh.setBounds(123, 27, 75, 23);
		frame.getContentPane().add(btnFresh);

		chckbxUnspottable = new JCheckBox("Unspottable");
		chckbxUnspottable.setForeground(Color.BLACK);
		chckbxUnspottable.setFont(new Font("Calibri", Font.BOLD, 12));
		chckbxUnspottable.setBackground(Color.WHITE);
		chckbxUnspottable.setBounds(864, 261, 92, 20);
		frame.getContentPane().add(chckbxUnspottable);

		JLabel label_18_1 = new JLabel("Spotting Difficulty:");
		label_18_1.setForeground(Color.BLACK);
		label_18_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_18_1.setBackground(Color.WHITE);
		label_18_1.setBounds(784, 244, 143, 20);
		frame.getContentPane().add(label_18_1);

		spinnerSpottingDifficulty = new JSpinner();
		spinnerSpottingDifficulty.setForeground(Color.BLACK);
		spinnerSpottingDifficulty.setBackground(Color.WHITE);
		spinnerSpottingDifficulty.setBounds(784, 260, 74, 20);
		frame.getContentPane().add(spinnerSpottingDifficulty);

		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

					bulkTrooper.trooper.spottingDifficulty = (int) spinnerSpottingDifficulty.getValue();
					bulkTrooper.trooper.unspottable = chckbxUnspottable.isSelected();

					if (bulkTrooper.trooper.unspottable) {

						for (Unit unit : gameWindow.initiativeOrder) {

							for (Trooper trooper : unit.individuals) {

								if (trooper == bulkTrooper.trooper)
									continue;

								for (Spot spot : trooper.spotted) {

									if (spot.spottedIndividuals.contains(bulkTrooper.trooper)) {
										spot.spottedIndividuals.remove(bulkTrooper.trooper);
									}

								}

							}

						}

					}

				}

			}
		});
		btnSet.setForeground(Color.BLACK);
		btnSet.setBounds(967, 259, 66, 23);
		frame.getContentPane().add(btnSet);

		JLabel label_18_2 = new JLabel("Consecutive EAL Bonus:");
		label_18_2.setForeground(Color.BLACK);
		label_18_2.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_18_2.setBackground(Color.WHITE);
		label_18_2.setBounds(720, 567, 162, 31);
		frame.getContentPane().add(label_18_2);

		spinnerConsecutiveEALBonus = new JSpinner();
		spinnerConsecutiveEALBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerConsecutiveEALBonus.setForeground(Color.BLACK);
		spinnerConsecutiveEALBonus.setBackground(Color.WHITE);
		spinnerConsecutiveEALBonus.setBounds(872, 571, 74, 20);
		frame.getContentPane().add(spinnerConsecutiveEALBonus);

		JLabel lblSetWeapons = new JLabel("Set Weapons");
		lblSetWeapons.setForeground(Color.BLACK);
		lblSetWeapons.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblSetWeapons.setBounds(479, 375, 162, 20);
		frame.getContentPane().add(lblSetWeapons);

		comboBoxWeapon = new JComboBox();
		comboBoxWeapon.setModel(new DefaultComboBoxModel(new String[] { "None", "DC15A", "DC15A-ion", "DC15LE", "DC15X",
				"DC15S", "DC17m", "DC17 Sniper", "Z6", "Westar M5", "E5", "E5S", "E5C", "MA37", "M392 DMR", "M739 SAW",
				"Type-51 Carbine", "Type-52 Rifle", "Type-52 Pistol" }));
		comboBoxWeapon.setForeground(Color.BLACK);
		comboBoxWeapon.setBounds(479, 395, 147, 23);
		frame.getContentPane().add(comboBoxWeapon);

		JButton btnSet_1 = new JButton("Set");
		btnSet_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (comboBoxWeapon.getSelectedIndex() < 0) {
					return;
				}

				for (BulkTrooper trooper : selectedBulkTroopers) {

					trooper.trooper.wep = comboBoxWeapon.getSelectedItem().toString();
					
					if(trooper.shoot != null) {
						trooper.shoot.updateWeapon(trooper.trooper.wep);
					}

				}

				gameWindow.conflictLog.addNewLine("Weapons set");
				
				refreshIndividualList();
				
				if (openUnit != null)
					openUnit.refreshIndividuals();

			}
		});
		btnSet_1.setForeground(Color.BLACK);
		btnSet_1.setBounds(637, 395, 97, 23);
		frame.getContentPane().add(btnSet_1);

		textFieldCallsign = new JTextField();
		textFieldCallsign.setBounds(744, 396, 155, 20);
		frame.getContentPane().add(textFieldCallsign);
		textFieldCallsign.setColumns(10);

		JLabel lblJoinUnit = new JLabel("Transfer to Unit");
		lblJoinUnit.setForeground(Color.BLACK);
		lblJoinUnit.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblJoinUnit.setBounds(744, 375, 162, 20);
		frame.getContentPane().add(lblJoinUnit);

		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnTransfer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				boolean found = false;

				int targetUnitIndex = 0;

				// Checks for valid unit name
				if (textFieldCallsign.getText().equals("Enter Callsign")
						|| textFieldCallsign.getText().equals("Enter valid callsign...")) {
					textFieldCallsign.setText("Enter valid callsign...");

				}

				// Loops through initiative order
				for (int i = 0; i < gameWindow.initiativeOrder.size(); i++) {

					if (textFieldCallsign.getText().equals(gameWindow.initiativeOrder.get(i).callsign)) {
						found = true;
						targetUnitIndex = i;
						break;
					}

				}

				// Reports to user
				if (!found) {
					textFieldCallsign.setText("Enter valid callsign...");
				} else {

					for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

						Trooper trooper = bulkTrooper.trooper;

						// Adds trooper
						if(!gameWindow.initiativeOrder.get(targetUnitIndex).individuals.contains(trooper))
							gameWindow.initiativeOrder.get(targetUnitIndex).addToUnit(trooper);

						// Removes trooper from unit
						for (int i = 0; i < unit.getSize(); i++) {
							if (trooper.compareTo(unit.individuals.get(i))) {
								unit.individuals.remove(i);
								break;
							}
						}

						// Checks if individuals in initiative order that are spotting this trooper have
						// LOS to his new unit
						// If not, this trooper is removed from their LOS
						for (Unit initUnit : gameWindow.initiativeOrder) {

							// For unit that is not on the same side as this trooper
							if (!initUnit.side.equals(trooper.returnTrooperUnit(gameWindow))) {

								// If initUnit does not have LOS to this trooper's unit
								if (!initUnit.lineOfSight.contains(trooper.returnTrooperUnit(gameWindow))) {
									// Loops through individuals
									// Loops through spotted action
									// Finds this trooper
									// Removes this trooper
									for (Trooper spottingTrooper : initUnit.individuals) {

										for (Spot spotAction : spottingTrooper.spotted) {

											for (Trooper spottedTrooper : spotAction.spottedIndividuals) {

												if (spottedTrooper.compareTo(trooper))
													spotAction.spottedIndividuals.remove(spottedTrooper);

											}

										}

									}

								}

							}

						}
					}

					gameWindow.initiativeOrder.get(targetUnitIndex)
							.seekCover(gameWindow.findHex(gameWindow.initiativeOrder.get(targetUnitIndex).X,
									gameWindow.initiativeOrder.get(targetUnitIndex).Y), gameWindow);

					bulkTroopers.clear();

					if (!gameWindow.cqbWindowOpen)
						setIndividuals();

					refreshIndividualList();

					// Refreshes windows
					if (openUnit != null)
						openUnit.refreshIndividuals();
					// window.gameWindow.rollInitiativeOrder();
					gameWindow.refreshInitiativeOrder();
				}
			}
		});
		btnTransfer.setForeground(Color.BLACK);
		btnTransfer.setBounds(954, 421, 97, 23);
		frame.getContentPane().add(btnTransfer);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (gameWindow.cqbWindowOpen) {
					gameWindow.cqbWindowOpen = false;
				}

				for(BulkTrooper bulkTrooper : bulkTroopers) {
					
					if(GameWindow.gameWindow.game.getPhase() == 1) {
						if(bulkTrooper.trooper.spentPhase1 < GameWindow.gameWindow.game.getCurrentAction() && bulkTrooper.shoot != null) {
							
							bulkTrooper.shoot.aimTime = bulkTrooper.shoot.startingAimTime;
							if(bulkTrooper.shoot.target != null) {
								bulkTrooper.trooper.storedAimTime.put(bulkTrooper.shoot.target, bulkTrooper.shoot.aimTime);
							}
							
						}
					} else {
						if(bulkTrooper.trooper.spentPhase2 < GameWindow.gameWindow.game.getCurrentAction() && bulkTrooper.shoot != null) {
							
							bulkTrooper.shoot.aimTime = bulkTrooper.shoot.startingAimTime;
							if(bulkTrooper.shoot.target != null) {
								bulkTrooper.trooper.storedAimTime.put(bulkTrooper.shoot.target, bulkTrooper.shoot.aimTime);
							}
							
						}
					}
					
				}
				
				frame.dispose();

			}
		});
		btnClose.setBounds(998, 642, 89, 41);
		frame.getContentPane().add(btnClose);

		JButton btnClear = new JButton("Clear Aim");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					bulkTrooper.trooper.storedAimTime.clear();
					bulkTrooper.targetTroopers.clear();
					bulkTrooper.setTargets();
				}
			}
		});
		btnClear.setForeground(Color.BLACK);
		btnClear.setBounds(954, 570, 97, 23);
		frame.getContentPane().add(btnClear);

		JButton btnResetFp = new JButton("Reset FP");
		btnResetFp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					bulkTrooper.trooper.fatigueSystem.fatiguePoints.set(0.0);
				}

				GameWindow.gameWindow.conflictLog.addNewLine("Reset FP for selected troopers.");

			}
		});
		btnResetFp.setForeground(Color.BLACK);
		btnResetFp.setBounds(995, 614, 92, 23);
		frame.getContentPane().add(btnResetFp);

		JButton btnPass = new JButton("Pass");
		btnPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					actionSpent(bulkTrooper.trooper);
				}
				refreshIndividualList();
			}
		});
		btnPass.setForeground(Color.BLACK);
		btnPass.setBounds(198, 169, 115, 23);
		frame.getContentPane().add(btnPass);

		JButton btnShooters = new JButton("Shooters");
		btnShooters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				individualsList.clearSelection();
				ArrayList<Integer> indexes = new ArrayList<Integer>();

				for (BulkTrooper bulkTrooper : bulkTroopers) {

					if (game.getPhase() == 1) {
						// System.out.println("Spent Phsae 1: "+bulkTrooper.trooper.spentPhase1);
						if (bulkTrooper.trooper.spentPhase1 < game.getCurrentAction()
								&& bulkTrooper.trooper.spentPhase1 < bulkTrooper.trooper.P1
								&& bulkTrooper.targetTroopers.size() > 0) {
							indexes.add(bulkTroopers.indexOf(bulkTrooper));
						}

					} else {
						// System.out.println("Spent Phsae 2: "+bulkTrooper.trooper.spentPhase2);
						if (bulkTrooper.trooper.spentPhase2 < game.getCurrentAction()
								&& bulkTrooper.trooper.spentPhase2 < bulkTrooper.trooper.P2
								&& bulkTrooper.targetTroopers.size() > 0) {
							indexes.add(bulkTroopers.indexOf(bulkTrooper));
						}
					}

				}

				int[] indices = indexes.stream().mapToInt(i -> i).toArray();
				
				individualListLock = true; 
				individualsList.setSelectedIndices(indices);
				individualListLock = false; 
				selected();
			}
		});
		btnShooters.setForeground(Color.BLACK);
		btnShooters.setBounds(300, 27, 87, 23);
		frame.getContentPane().add(btnShooters);

		JButton btnAiming = new JButton("Aiming");
		btnAiming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				individualsList.clearSelection();
				ArrayList<Integer> indexes = new ArrayList<Integer>();

				for (BulkTrooper bulkTrooper : bulkTroopers) {

					if (game.getPhase() == 1) {
						// System.out.println("Spent Phsae 1: "+bulkTrooper.trooper.spentPhase1);
						if (bulkTrooper.trooper.spentPhase1 < game.getCurrentAction()
								&& bulkTrooper.trooper.spentPhase1 < bulkTrooper.trooper.P1
								&& bulkTrooper.trooper.storedAimTime.size() > 0) {
							indexes.add(bulkTroopers.indexOf(bulkTrooper));
						}

					} else {
						// System.out.println("Spent Phsae 2: "+bulkTrooper.trooper.spentPhase2);
						if (bulkTrooper.trooper.spentPhase2 < game.getCurrentAction()
								&& bulkTrooper.trooper.spentPhase2 < bulkTrooper.trooper.P2
								&& bulkTrooper.trooper.storedAimTime.size() > 0) {
							indexes.add(bulkTroopers.indexOf(bulkTrooper));
						}
					}

				}

				int[] indices = indexes.stream().mapToInt(i -> i).toArray();

				individualListLock = true; 
				individualsList.setSelectedIndices(indices);
				individualListLock = false; 
				selected();
			}
		});
		btnAiming.setForeground(Color.BLACK);
		btnAiming.setBounds(208, 27, 82, 23);
		frame.getContentPane().add(btnAiming);

		JLabel lblLauncher = new JLabel("Launcher");
		lblLauncher.setForeground(Color.BLACK);
		lblLauncher.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblLauncher.setBounds(479, 634, 136, 20);
		frame.getContentPane().add(lblLauncher);

		JLabel lblGrenade = new JLabel("Grenade: ");
		lblGrenade.setForeground(Color.BLACK);
		lblGrenade.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblGrenade.setBounds(10, 567, 136, 31);
		frame.getContentPane().add(lblGrenade);

		comboBoxGrenade = new JComboBox();
		comboBoxGrenade.setBounds(10, 597, 136, 20);
		frame.getContentPane().add(comboBoxGrenade);

		comboBoxGrenadeTargets = new JComboBox();
		comboBoxGrenadeTargets.setBounds(157, 597, 136, 20);
		frame.getContentPane().add(comboBoxGrenadeTargets);

		JLabel label_16_1 = new JLabel("Taget Individual: ");
		label_16_1.setForeground(Color.BLACK);
		label_16_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_16_1.setBounds(157, 567, 121, 31);
		frame.getContentPane().add(label_16_1);

		JLabel lblOr = new JLabel("OR");
		lblOr.setForeground(Color.BLACK);
		lblOr.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblOr.setBounds(20, 616, 16, 31);
		frame.getContentPane().add(lblOr);

		JLabel lblX = new JLabel("X:");
		lblX.setForeground(Color.BLACK);
		lblX.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblX.setBounds(71, 619, 16, 31);
		frame.getContentPane().add(lblX);

		spinnerGrenadeX = new JSpinner();
		spinnerGrenadeX.setBounds(86, 623, 40, 20);
		frame.getContentPane().add(spinnerGrenadeX);

		JLabel lblY = new JLabel("Y:");
		lblY.setForeground(Color.BLACK);
		lblY.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblY.setBounds(135, 619, 16, 31);
		frame.getContentPane().add(lblY);

		spinnerGrenadeY = new JSpinner();
		spinnerGrenadeY.setBounds(150, 623, 40, 20);
		frame.getContentPane().add(spinnerGrenadeY);

		JLabel lblHex = new JLabel("Hex:");
		lblHex.setForeground(Color.BLACK);
		lblHex.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblHex.setBounds(38, 616, 40, 31);
		frame.getContentPane().add(lblHex);

		JLabel label_10_1 = new JLabel("OR");
		label_10_1.setForeground(Color.BLACK);
		label_10_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		label_10_1.setBounds(200, 616, 16, 31);
		frame.getContentPane().add(label_10_1);

		comboBoxBuilding = new JComboBox();
		comboBoxBuilding.setSelectedIndex(-1);
		comboBoxBuilding.setBounds(298, 621, 136, 20);
		frame.getContentPane().add(comboBoxBuilding);

		JLabel lblBuilding_1 = new JLabel("Building:");
		lblBuilding_1.setForeground(Color.BLACK);
		lblBuilding_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblBuilding_1.setBounds(226, 616, 62, 31);
		frame.getContentPane().add(lblBuilding_1);

		spinnerTargetRoom = new JSpinner();
		spinnerTargetRoom.setBounds(71, 653, 40, 20);
		frame.getContentPane().add(spinnerTargetRoom);

		JLabel lblTargetRoom = new JLabel("Room:");
		lblTargetRoom.setForeground(Color.BLACK);
		lblTargetRoom.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblTargetRoom.setBounds(10, 650, 53, 31);
		frame.getContentPane().add(lblTargetRoom);

		spinnerTargetFloor = new JSpinner();
		spinnerTargetFloor.setBounds(170, 653, 40, 20);
		frame.getContentPane().add(spinnerTargetFloor);

		JLabel lblFloor = new JLabel("Floor:");
		lblFloor.setForeground(Color.BLACK);
		lblFloor.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblFloor.setBounds(123, 652, 53, 31);
		frame.getContentPane().add(lblFloor);

		JButton btnThrow = new JButton("Throw");
		btnThrow.setBounds(303, 588, 125, 23);
		frame.getContentPane().add(btnThrow);

		spinnerThrowBonus = new JSpinner();
		spinnerThrowBonus.setBounds(308, 653, 40, 20);
		frame.getContentPane().add(spinnerThrowBonus);

		spinnerThrowEALBonus = new JSpinner();
		spinnerThrowEALBonus.setBounds(430, 653, 39, 20);
		frame.getContentPane().add(spinnerThrowEALBonus);

		JLabel label_15 = new JLabel("Other Bonus:");
		label_15.setForeground(Color.BLACK);
		label_15.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_15.setBounds(226, 650, 87, 31);
		frame.getContentPane().add(label_15);

		JLabel lblEalBonus = new JLabel("EAL Bonus:");
		lblEalBonus.setForeground(Color.BLACK);
		lblEalBonus.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblEalBonus.setBounds(354, 650, 80, 31);
		frame.getContentPane().add(lblEalBonus);

		JComboBox comboBoxLauncher = new JComboBox();
		comboBoxLauncher.setBounds(479, 655, 136, 20);
		frame.getContentPane().add(comboBoxLauncher);

		JLabel label_20 = new JLabel("X:");
		label_20.setForeground(Color.BLACK);
		label_20.setFont(new Font("Calibri", Font.PLAIN, 12));
		label_20.setBounds(705, 652, 16, 31);
		frame.getContentPane().add(label_20);

		spinnerLauncherX = new JSpinner();
		spinnerLauncherX.setBounds(720, 656, 40, 20);
		frame.getContentPane().add(spinnerLauncherX);

		JLabel label_21 = new JLabel("Y:");
		label_21.setForeground(Color.BLACK);
		label_21.setFont(new Font("Calibri", Font.PLAIN, 12));
		label_21.setBounds(769, 652, 16, 31);
		frame.getContentPane().add(label_21);

		spinnerLauncherY = new JSpinner();
		spinnerLauncherY.setBounds(784, 656, 40, 20);
		frame.getContentPane().add(spinnerLauncherY);

		JLabel label_22 = new JLabel("Target Hex:");
		label_22.setForeground(Color.BLACK);
		label_22.setFont(new Font("Calibri", Font.PLAIN, 12));
		label_22.setBounds(730, 635, 87, 23);
		frame.getContentPane().add(label_22);

		JButton btnShootHex = new JButton("Shoot Hex");
		btnShootHex.setBounds(833, 654, 89, 23);
		frame.getContentPane().add(btnShootHex);

		JComboBox comboBoxAmmoTypeLauncher = new JComboBox();
		comboBoxAmmoTypeLauncher.setSelectedIndex(-1);
		comboBoxAmmoTypeLauncher.setBounds(626, 655, 74, 20);
		frame.getContentPane().add(comboBoxAmmoTypeLauncher);

		chckbxGuided = new JCheckBox("Guided");
		chckbxGuided.setForeground(Color.WHITE);
		chckbxGuided.setBackground(Color.DARK_GRAY);
		chckbxGuided.setBounds(796, 469, 80, 23);
		frame.getContentPane().add(chckbxGuided);
		
		JButton btnCreateTransfer = new JButton("Create & Transfer");
		btnCreateTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Adds new unit 
				// Splits unit 
				ArrayList<Trooper> individuals = new ArrayList<Trooper>();
				generateSquad squad = new generateSquad("Clone Trooper Phase 1", "Empty");
				individuals = squad.getSquad();
				Unit newUnit = new Unit(textFieldCallsign.getText(), 0, 0, individuals, 100, 0, 100, 0, 0, 20, 0, unit.behavior);
				
				//Unit newUnit = unit.copyUnit(unit); 
				newUnit.side = unit.side;
				newUnit.initiative = unit.initiative;
				newUnit.organization = unit.organization;
				unit.organization = unit.organization; 
				
				newUnit.concealment = unit.concealment;
				newUnit.suppression = unit.suppression;
				newUnit.moral = unit.moral;
				newUnit.cohesion = unit.cohesion;
				newUnit.company = unit.company;
				newUnit.X = unit.X;
				newUnit.Y = unit.Y;
				newUnit.behavior = unit.behavior;
				newUnit.lineOfSight = new ArrayList<Unit>(unit.lineOfSight);
				//Collections.copy(newUnit.lineOfSight, unit.lineOfSight);
				//newUnit.lineOfSight = Collections.copy(unit.lineOfSight);
				gameWindow.initiativeOrder.add(newUnit);
				
				gameWindow.rollInitiativeOrder();
				gameWindow.refreshInitiativeOrder();
				
				// Loops through initiative order
				// Finds units that have LOS with this unit 
				// Adds new unit to the spotting units LOS
				
				for(Unit initUnit : gameWindow.initiativeOrder) {
					
					if(initUnit.lineOfSight.contains(unit)) {
						initUnit.lineOfSight.add(newUnit);
					}
					
				}
				
				
				// Finds newUnit's company 
				// Adds unit to company 
				for(int i = 0; i < gameWindow.companies.size(); i++) {
					
					if(gameWindow.companies.get(i).getName().equals(newUnit.company) && gameWindow.companies.get(i).getSide().equals(newUnit.side)) {
						gameWindow.companies.get(i).updateUnit(unit);
						gameWindow.companies.get(i).addUnit(newUnit);
						// Adds companies to setupWindow
						gameWindow.confirmCompany(gameWindow.companies.get(i), i);
						//f.dispose();
						
					}
					
				}
				
				
				boolean found = false;

				int targetUnitIndex = 0;

				// Checks for valid unit name
				if (textFieldCallsign.getText().equals("Enter Callsign")
						|| textFieldCallsign.getText().equals("Enter valid callsign...")) {
					textFieldCallsign.setText("Enter valid callsign...");

				}

				// Loops through initiative order
				for (int i = 0; i < gameWindow.initiativeOrder.size(); i++) {

					if (textFieldCallsign.getText().equals(gameWindow.initiativeOrder.get(i).callsign)) {
						found = true;
						targetUnitIndex = i;
						break;
					}

				}

				// Reports to user
				if (!found) {
					textFieldCallsign.setText("Enter valid callsign...");
				} else {

					for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

						Trooper trooper = bulkTrooper.trooper;

						// Adds trooper
						if(!gameWindow.initiativeOrder.get(targetUnitIndex).individuals.contains(trooper))
							gameWindow.initiativeOrder.get(targetUnitIndex).addToUnit(trooper);

						// Removes trooper from unit
						for (int i = 0; i < unit.getSize(); i++) {
							if (trooper.compareTo(unit.individuals.get(i))) {
								unit.individuals.remove(i);
								break;
							}
						}

						// Checks if individuals in initiative order that are spotting this trooper have
						// LOS to his new unit
						// If not, this trooper is removed from their LOS
						for (Unit initUnit : gameWindow.initiativeOrder) {

							// For unit that is not on the same side as this trooper
							if (!initUnit.side.equals(trooper.returnTrooperUnit(gameWindow))) {

								// If initUnit does not have LOS to this trooper's unit
								if (!initUnit.lineOfSight.contains(trooper.returnTrooperUnit(gameWindow))) {
									// Loops through individuals
									// Loops through spotted action
									// Finds this trooper
									// Removes this trooper
									for (Trooper spottingTrooper : initUnit.individuals) {

										for (Spot spotAction : spottingTrooper.spotted) {

											for (Trooper spottedTrooper : spotAction.spottedIndividuals) {

												if (spottedTrooper.compareTo(trooper))
													spotAction.spottedIndividuals.remove(spottedTrooper);

											}

										}

									}

								}

							}

						}
					}

					gameWindow.initiativeOrder.get(targetUnitIndex)
							.seekCover(gameWindow.findHex(gameWindow.initiativeOrder.get(targetUnitIndex).X,
									gameWindow.initiativeOrder.get(targetUnitIndex).Y), gameWindow);

					bulkTroopers.clear();

					if (!gameWindow.cqbWindowOpen)
						setIndividuals();

					refreshIndividualList();

					// Refreshes windows
					if (openUnit != null)
						openUnit.refreshIndividuals();
					// window.gameWindow.rollInitiativeOrder();
					gameWindow.refreshInitiativeOrder();
				}
			}
		});
		btnCreateTransfer.setForeground(Color.BLACK);
		btnCreateTransfer.setBounds(909, 395, 142, 23);
		frame.getContentPane().add(btnCreateTransfer);
		
		JLabel lblSelectFraction = new JLabel("Select Divisor");
		lblSelectFraction.setForeground(Color.BLACK);
		lblSelectFraction.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSelectFraction.setBackground(Color.WHITE);
		lblSelectFraction.setBounds(10, 29, 87, 23);
		frame.getContentPane().add(lblSelectFraction);
		
		spinnerDivisor = new JSpinner();
		spinnerDivisor.setModel(new SpinnerNumberModel(Integer.valueOf(1), null, null, Integer.valueOf(1)));
		spinnerDivisor.setBounds(10, 46, 51, 20);
		frame.getContentPane().add(spinnerDivisor);
		
		comboBoxWep = new JComboBox();
		comboBoxWep.setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
		comboBoxWep.setBounds(10, 125, 115, 22);
		frame.getContentPane().add(comboBoxWep);
		
		comboBoxDesignation = new JComboBox();
		comboBoxDesignation.setModel(new DefaultComboBoxModel(new String[] {"N/A"}));
		comboBoxDesignation.setBounds(134, 125, 115, 22);
		frame.getContentPane().add(comboBoxDesignation);
		
		JLabel lblSelectFraction_1 = new JLabel("Weapon");
		lblSelectFraction_1.setForeground(Color.BLACK);
		lblSelectFraction_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSelectFraction_1.setBackground(Color.WHITE);
		lblSelectFraction_1.setBounds(10, 105, 87, 23);
		frame.getContentPane().add(lblSelectFraction_1);
		
		JLabel lblSelectFraction_1_1 = new JLabel("Add");
		lblSelectFraction_1_1.setForeground(Color.BLACK);
		lblSelectFraction_1_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSelectFraction_1_1.setBackground(Color.WHITE);
		lblSelectFraction_1_1.setBounds(170, 9, 87, 23);
		frame.getContentPane().add(lblSelectFraction_1_1);
		
		JLabel lblSelectFraction_1_1_1 = new JLabel("Remove");
		lblSelectFraction_1_1_1.setForeground(Color.BLACK);
		lblSelectFraction_1_1_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSelectFraction_1_1_1.setBackground(Color.WHITE);
		lblSelectFraction_1_1_1.setBounds(170, 52, 87, 20);
		frame.getContentPane().add(lblSelectFraction_1_1_1);
		
		JButton btnFresh_1 = new JButton("Fresh");
		btnFresh_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				removeFresh();
				
				
			}
		});
		btnFresh_1.setForeground(Color.BLACK);
		btnFresh_1.setBounds(123, 73, 75, 23);
		frame.getContentPane().add(btnFresh_1);
		
		JButton btnAiming_1 = new JButton("Aiming");
		btnAiming_1.setForeground(Color.BLACK);
		btnAiming_1.setBounds(208, 73, 82, 23);
		frame.getContentPane().add(btnAiming_1);
		
		JButton btnShooters_1 = new JButton("Shooters");
		btnShooters_1.setForeground(Color.BLACK);
		btnShooters_1.setBounds(300, 73, 87, 23);
		frame.getContentPane().add(btnShooters_1);
		
		comboBoxSide = new JComboBox();
		comboBoxSide.setModel(new DefaultComboBoxModel(new String[] {"N/A", "BLUFOR", "OPFOR"}));
		comboBoxSide.setBounds(259, 125, 115, 22);
		frame.getContentPane().add(comboBoxSide);
		
		JLabel lblSelectFraction_1_2 = new JLabel("Designation");
		lblSelectFraction_1_2.setForeground(Color.BLACK);
		lblSelectFraction_1_2.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSelectFraction_1_2.setBackground(Color.WHITE);
		lblSelectFraction_1_2.setBounds(134, 105, 87, 23);
		frame.getContentPane().add(lblSelectFraction_1_2);
		
		JLabel lblSelectFraction_1_2_1 = new JLabel("Side");
		lblSelectFraction_1_2_1.setForeground(Color.BLACK);
		lblSelectFraction_1_2_1.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblSelectFraction_1_2_1.setBackground(Color.WHITE);
		lblSelectFraction_1_2_1.setBounds(259, 106, 87, 23);
		frame.getContentPane().add(lblSelectFraction_1_2_1);
		
		JButton btnRemoveAp = new JButton("Remove AP");
		btnRemoveAp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemoveAp.setForeground(Color.BLACK);
		btnRemoveAp.setBounds(319, 169, 115, 23);
		frame.getContentPane().add(btnRemoveAp);
		
		JButton btnExh = new JButton("Exh");
		btnExh.setForeground(Color.BLACK);
		btnExh.setBounds(397, 27, 71, 23);
		frame.getContentPane().add(btnExh);
		
		JButton btnExh_1 = new JButton("Exh");
		btnExh_1.setForeground(Color.BLACK);
		btnExh_1.setBounds(397, 73, 71, 23);
		frame.getContentPane().add(btnExh_1);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectTroopers(new ArrayList<Integer>());
			}
		});
		btnNewButton.setBounds(10, 73, 89, 23);
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
	}
	
	public ArrayList<Integer> getIndexes() {
		ArrayList<Integer> indexes = new ArrayList<Integer>();
		
		for(int integer : individualsList.getSelectedIndices()) {
			indexes.add(integer);
		}
		return indexes;
	}
	
	public void addFresh() {
		ArrayList<Integer> indexes = getIndexes();
		int count = 1;
		
		for (BulkTrooper bulkTrooper : bulkTroopers) {
			if(indexes.contains(bulkTroopers.indexOf(bulkTrooper)))
				continue;
			
			if(freshTrooper(bulkTrooper) 
					&& validTrooper(bulkTrooper.trooper)
					&& count % (int) spinnerDivisor.getValue() == 0) {
				indexes.add(bulkTroopers.indexOf(bulkTrooper));
				count++;
			} else if(freshTrooper(bulkTrooper) && validTrooper(bulkTrooper.trooper)) {
				count++; 
			}

		}

		selectTroopers(indexes);
	}
	
	public void removeFresh() {
		ArrayList<Integer> indexes = getIndexes();
		int count = 1;
		
		for (BulkTrooper bulkTrooper : bulkTroopers) {
			if(!indexes.contains(bulkTroopers.indexOf(bulkTrooper)))
				continue;
			
			if(freshTrooper(bulkTrooper) 
					&& validTrooper(bulkTrooper.trooper)
					&& count % (int) spinnerDivisor.getValue() == 0) {
				indexes.remove((Object) bulkTroopers.indexOf(bulkTrooper));
				count++;
			} else if(freshTrooper(bulkTrooper) && validTrooper(bulkTrooper.trooper)) {
				count++; 
			}

		}

		selectTroopers(indexes);
	}

	public void selectTroopers(ArrayList<Integer> indexes) {
		int[] indices = indexes.stream().mapToInt(i -> i).toArray();

		individualListLock = true; 
		individualsList.setSelectedIndices(indices);
		individualListLock = false; 
		selected();
	}
	
	public boolean freshTrooper(BulkTrooper bulkTrooper) {
		int maxAp = game.getPhase() == 1 ? bulkTrooper.trooper.P1 : bulkTrooper.trooper.P2;
		int sepntAp = game.getPhase() == 1 ? bulkTrooper.trooper.spentPhase1 : bulkTrooper.trooper.spentPhase2;
		return sepntAp < maxAp ? true : false;
	}
	
	public boolean aimingTrooper() {
		return false; 
	}
	
	public boolean extTrooper(BulkTrooper bulkTrooper) {
		return !freshTrooper(bulkTrooper);
	}
	
	public boolean validTrooper(Trooper trooper) {
		if(comboBoxWep.getSelectedIndex() != 0 && !trooper.wep.equals(comboBoxWep.getSelectedItem().toString())) {
			return false;
		}
		
		if(comboBoxDesignation.getSelectedIndex() != 0 && 
				!trooper.designation.equals(comboBoxDesignation.getSelectedItem().toString())) {
			return false;
		}
		
		if(comboBoxSide.getSelectedIndex() != 0 && !trooper.returnTrooperUnit(GameWindow.gameWindow)
				.side.equals(comboBoxSide.getSelectedItem().toString())) {
			return false;
		}
		
		return true;
	}
	
	public boolean trooperAlreadyAdded(Trooper trooper) {

		for (BulkTrooper bulkTrooper : bulkTroopers) {

			if (bulkTrooper.trooper.compareTo(trooper))
				return true;

		}

		return false;
	}

	// Creates bulk trooper classes and populates the arraylist
	public void setIndividuals() {

		for (Trooper trooper : unit.individuals) {
			if (trooper.alive == false || !trooper.conscious || trooperAlreadyAdded(trooper))
				continue;

			for (Unit losUnit : trooper.returnTrooperUnit(GameWindow.gameWindow).lineOfSight) {
				if (!targetUnits.contains(losUnit))
					targetUnits.add(losUnit);
			}

			bulkTroopers.add(new BulkTrooper(trooper));
		}

		// refreshIndividualList();

	}

	// Sets bulk troopers for those in CQB
	public void setIndividuals(ArrayList<Trooper> cqbt) {

		for (Trooper trooper : cqbt) {

			// System.out.println("Trooper: "+trooper.name+", Code:
			// "+System.identityHashCode(trooper));
			// System.out.println("CQB Target: "+trooper.closeCombatTarget.name+", Code:
			// "+System.identityHashCode(trooper.closeCombatTarget));

			if (trooper.alive == false || !trooper.conscious || trooperAlreadyAdded(trooper))
				continue;

			for (Unit losUnit : trooper.returnTrooperUnit(GameWindow.gameWindow).lineOfSight) {
				if (!targetUnits.contains(losUnit))
					targetUnits.add(losUnit);
			}

			bulkTroopers.add(new BulkTrooper(trooper));
		}

		// refreshIndividualList();

	}

	// Updates all combo boxes
	public void setComboBoxes() {

		// Adds sup targets to dropdown menu
		setSuppressiveFireTargets();

		// Spotting Combo Boxes
		setSpottingUnits();

		// Set focus unit
		setTargetFocus();
		
		
		// Select Unit Combo Boxes 
		selectConditions();
	}
	
	public void selectConditions() {
		
		comboBoxWep.removeAllItems();
		
		ArrayList<String> weapons = new ArrayList<String>();
		
		for(BulkTrooper bulkTrooper : bulkTroopers) {
			if(weapons.contains(bulkTrooper.trooper.wep))
				continue; 
			weapons.add(bulkTrooper.trooper.wep);
		}
		
		comboBoxWep.addItem("N/A");
		for(String str : weapons)
			comboBoxWep.addItem(str);
		comboBoxWep.setSelectedIndex(0);
		
		
		comboBoxDesignation.removeAllItems();
		ArrayList<String> designations = new ArrayList<String>();
		
		for(BulkTrooper bulkTrooper : bulkTroopers) {
			if(designations.contains(bulkTrooper.trooper.designation))
				continue; 
			designations.add(bulkTrooper.trooper.designation);
		}
		
		comboBoxDesignation.addItem("N/A");
		for(String str : designations)
			comboBoxDesignation.addItem(str);
		comboBoxDesignation.setSelectedIndex(0);
		
		
		
	}

	public void setTargetFocus() {
		targetFocusLock = true; 

		targetedFireFocus.removeAllItems();
		targetedFireFocus.addItem("None");

		for (Unit unit : getValidTargetUnits()) {
			targetedFireFocus.addItem(unit.callsign);
		}

		targetedFireFocus.setSelectedIndex(0);
		
		targetFocusLock = false;
	}

	public ArrayList<Unit> getValidTargetUnits() {
		ArrayList<Unit> validTargetUnits = new ArrayList<>();

		for (Unit unit : targetUnits) {

			boolean validTarget = true;

			for (BulkTrooper trooper : getSelectedBulkTroopers()) {

				boolean hasValidTarget = false;

				for (Trooper targetTrooper : unit.individuals) {

					if (trooper.targetTroopers.contains(targetTrooper)) {
						hasValidTarget = true;
					}
				}

				if (!hasValidTarget) {
					validTarget = false;
				}

			}

			if (validTarget && !validTargetUnits.contains(unit)) {
				validTargetUnits.add(unit);
			}

		}

		return validTargetUnits;
	}

	// Sets spotting combo boxes
	public void setSpottingUnits() {

		for (Unit losUnit : targetUnits) {

			comboBoxSpottingUnits.addItem(losUnit.callsign);

		}

	}

	public void setSuppressiveFireTargets() {

		comboBoxTargetUnits.removeAllItems();
		comboBoxTargetUnits.addItem("None");
		comboBoxTargetUnits.addItem("Random");
		if (unit.lineOfSight.size() < 1)
			return;

		for (Unit unit : targetUnits) {

			comboBoxTargetUnits.addItem(unit.callsign);
		}

		comboBoxTargetUnits.setSelectedIndex(0);

	}

	public int getRWSSuppressive(Trooper trooper) {
		// System.out.println("Get rws");
		int rws = 0;
		if (trooper == null) {
			// System.out.println("Trooper is null");
			return rws;
		}

		String weaponType = new Weapons().findWeapon(trooper.wep).type;

		if (weaponType.equals("Rifle")) {
			// System.out.println("Match: Trooper rifle rws: " + trooper.rifleRWS);
			rws = trooper.getSkill("Rifle");
		} else if (weaponType.equals("Heavy")) {
			// System.out.println("Match: Trooper Heavy rws: " + trooper.heavyRWS);
			rws = trooper.getSkill("Heavy");
		} else if (weaponType.equals("Subgun")) {
			rws = trooper.getSkill("Subgun");
		} else if (weaponType.equals("Launcher")) {
			rws = trooper.getSkill("Launcher");
		} else if (weaponType.equals("Pistol")) {
			rws = trooper.getSkill("Pistol");
		}

		// Apply GURPS missing arm penalty
		if (trooper.disabledArms > 0) {
			rws -= 20;
		}

		return rws;
	}

	// Suppressive fire action
	public void fireSuppressive(Trooper trooper) {
		// System.out.println("Line 750, suppression pass");
		Weapons weapon = new Weapons().findWeapon(trooper.wep);
		Unit targetUnit = unit.lineOfSight.get(comboBoxTargetUnits.getSelectedIndex());
		Random rand = new Random();

		int shots = weapon.suppressiveROF;
		boolean canShoot;
		canShoot = trooper.inventory.fireShots(shots, new Weapons().findWeapon(trooper.wep));

		// Checks for out of ammo
		if (!canShoot) {
			gameWindow.conflictLog.addNewLineToQueue("Out out ammo!");
			// textPaneSuppressiveFire.setText("OUT OF AMMO");
			return;
		}

		int RWS = 0;
		RWS = getRWSSuppressive(trooper);

		if (RWS < 5) {
			RWS = 5;
		}

		int bonus = 0;

		// Subtracts ammo
		if (trooper.ammo < shots) {
			gameWindow.conflictLog.addNewLineToQueue("Out out ammo!");
			return;
		}

		if (shots == 0) {
			gameWindow.conflictLog.addNewLineToQueue("Select shots!");
			return;
		}

		Trooper target = null;
		// System.out.println("Line 783, suppression pass");

		// Gets target from selected unit
		if (targetUnit.getTroopers() == null || targetUnit.getSize() < 1 || allDead(targetUnit)) {
			gameWindow.conflictLog.addNewLineToQueue("No Targets in Unit");
			return;
		}

		boolean rolling = true;
		while (rolling) {
			// System.out.println("Line 794, rolling pass");
			int roll = rand.nextInt(targetUnit.getSize());
			target = targetUnit.getTroopers().get(roll);
			if (target.alive) {
				rolling = false;
			}
		}

		if (target != null) {

			// System.out.println("Line 808, suppression pass");
			// Sets target unit
			targetUnit = null;

			// Loops through intiiative order units and then individuals
			// Finds target's unit
			for (int i = 0; i < gameWindow.initiativeOrder.size(); i++) {
				Unit tempUnit = gameWindow.initiativeOrder.get(i);
				ArrayList<Trooper> tempTroopers = tempUnit.getTroopers();
				for (int j = 0; j < tempUnit.getSize(); j++) {
					if (tempTroopers.get(j).compareTo(target)) {
						targetUnit = tempUnit;
					}
				}
			}

			if (targetUnit == null) {
				return;
			}

			if (weapon.tracers) {
				trooper.firedTracers = true;
			} else {
				trooper.firedTracers = false;
			}

			// System.out.println("Weapon: "+weapon);
			// System.out.println("RWS: "+RWS);
			TargetedFire targetedFire = new TargetedFire(RWS, bonus, weapon, shots, target, trooper, targetUnit, unit,
					true);

			// Get hits
			if (targetedFire != null) {

				int hits = targetedFire.getHits();
				int TN = targetedFire.getTN();

				// System.out.println("Line 849, suppression pass");

				gameWindow.conflictLog.addNewLineToQueue("SUPPRESSIVE FIRE: " + unit.side + "::  " + unit.callsign
						+ " to " + targetUnit.side + "::  " + targetUnit.callsign
						+ "\nSuppressive Fire Results:\nHITS: " + hits + "\n" + "TN: " + TN);
				// Subtracts suppression, moral and organization
				for (int i = 0; i < gameWindow.initiativeOrder.size(); i++) {
					if (gameWindow.initiativeOrder.get(i).compareTo(targetUnit)) {
						// System.out.println("Pass suppression");
						Unit tempUnit = gameWindow.initiativeOrder.get(i);

						int x = tempUnit.X;
						int y = tempUnit.Y;

						for (Unit potentialTarget : gameWindow.initiativeOrder) {

							if (potentialTarget.X == x && potentialTarget.Y == y) {
								if (potentialTarget.suppression + hits / 2 < 100) {
									potentialTarget.suppression += hits / 2;
								} else {
									potentialTarget.suppression = 100;
								}
								if (potentialTarget.organization - hits > 0) {
									potentialTarget.organization -= hits;
								} else {
									potentialTarget.organization = 0;
								}
							}

						}

						if (tempUnit.suppression + hits / 2 < 100) {
							tempUnit.suppression += hits / 2;
						} else {
							tempUnit.suppression = 100;
						}
						if (tempUnit.organization - hits > 0) {
							tempUnit.organization -= hits;
						} else {
							tempUnit.organization = 0;
						}
						/*
						 * if (tempUnit.moral - hits > 0) { tempUnit.moral -= hits; } else {
						 * tempUnit.moral = 0; }
						 */

						for (Unit collateralUnit : GameWindow.gameWindow.initiativeOrder) {

							if (collateralUnit.X != tempUnit.X || collateralUnit.Y != tempUnit.Y
									|| tempUnit.compareTo(collateralUnit)) {
								continue;
							}

							if (collateralUnit.suppression + hits / 2 < 100) {
								collateralUnit.suppression += hits / 2;
							} else {
								collateralUnit.suppression = 100;
							}
							if (collateralUnit.organization - hits > 0) {
								collateralUnit.organization -= hits;
							} else {
								collateralUnit.organization = 0;
							}

						}

						int trooperHits = 0;
						// Checks each hit for a strike against a individual in the target unit
						for (int j = 0; j < hits; j++) {
							int roll = rand.nextInt(100) + 1;
							int hitTN = 1;

							if (target.getBuilding() != null && target.getBuilding().getHexSize() < 200) {
								hitTN = 11;
							} else if (target.getBuilding() != null && target.getBuilding().getHexSize() < 400) {
								hitTN = 6;
							}

							if (roll <= hitTN) {
								trooperHits++;
							}
						}

						if (trooperHits > 0) {
							// System.out.println("pass trooper hits");
							ResolveHits resolveHits = new ResolveHits(target, trooperHits, weapon,
									gameWindow.conflictLog, tempUnit, unit, gameWindow);
							resolveHits.performCalculations(gameWindow.game, gameWindow.conflictLog);
							target = resolveHits.returnTarget();

						}

						tempUnit.setIndividual(target, target.number);

						gameWindow.initiativeOrder.set(i, tempUnit);

					}
				}

			}

			trooper.ammo -= shots;

			// System.out.println("Line 926, suppression pass");

			if (shots > 10) {
				int roll = rand.nextInt(8);

				if (trooper.ammo - roll < 0) {
					trooper.ammo = 0;
				} else {
					trooper.ammo -= roll;
				}

			} else {
				int roll = rand.nextInt(6);

				if (trooper.ammo - roll < 0) {
					trooper.ammo = 0;
				} else {
					trooper.ammo -= roll;
				}

			}

			// window.openUnit.troopers.set(index, trooper);

			// window.openUnit.refreshIndividuals();

			// Adds action point, if it is not a free action
			if (!chckbxFreeAction.isSelected()) {
				actionSpent(trooper);
			}

		}

	}

	public void actionSpent(Trooper trooper) {
		
		System.out.println("Action spent");
		
		if (game.getPhase() == 1)
			trooper.spentPhase1++;
		else
			trooper.spentPhase2++;

	}

	// Gets selected individuals from bulk trooper
	public ArrayList<Trooper> getSelectedTroopers() {

		ArrayList<Trooper> troopers = new ArrayList<Trooper>();
		// System.out.println("Get Individuals 1");
		int[] indexes = individualsList.getSelectedIndices();
		// System.out.println("Get Individuals 2, indexes: "+indexes.length);
		for (int index : indexes) {

			troopers.add(bulkTroopers.get(index).trooper);

		}

		// System.out.println("Get Individuals 3");
		return troopers;
	}

	public ArrayList<BulkTrooper> getSelectedBulkTroopers() {

		ArrayList<BulkTrooper> troopers = new ArrayList<BulkTrooper>();
		// System.out.println("Get Individuals 1");
		int[] indexes = individualsList.getSelectedIndices();
		// System.out.println("Get Individuals, indexes: "+indexes.length);
		for (int index : indexes) {

			troopers.add(bulkTroopers.get(index));

		}

		// System.out.println("Get Individuals 3");
		return troopers;
	}

	// Gets trooper and returns trooper from the trooper's number
	// Might not work
	/*
	 * public Trooper findTrooperFromString(String trooperString) {
	 * 
	 * String value = trooperString; String numString = "";
	 * System.out.println("Find Trooper from string 1"); for(int i = 0; i <
	 * value.length() - 1; i++) {
	 * System.out.println("Find Trooper from string loop"); char c =
	 * value.charAt(i); if(c == ';') break; else if(Character.isDigit(c)) {
	 * numString += c; } }
	 * 
	 * 
	 * Trooper trooper = unit.individuals.get(Integer.parseInt(numString) - 1);
	 * System.out.println("Find Trooper from string 2, trooper: "+trooper.toString()
	 * ); return trooper; }
	 */

	public boolean allDead(Unit unit) {

		boolean allDead = true;

		for (Trooper trooper : unit.individuals)
			if (trooper.alive)
				allDead = false;

		return allDead;

	}

	// Refreshes front end changes to the troopers
	public void refreshIndividualList() {
		individualListLock = true; 
		
		int[] indices = individualsList.getSelectedIndices();

		individualsList.removeAll();

		DefaultListModel listModel = new DefaultListModel();

		for (BulkTrooper individual : bulkTroopers) {

			listModel.addElement(individual.bulkToString());

		}

		individualsList.setModel(listModel);

		individualsList.setSelectedIndices(indices);

		if (openUnit != null)
			openUnit.refreshIndividuals();
		
		individualListLock = false;
	}

	private class BulkTrooper {
		public Trooper trooper;
		public int spentCA;
		public int CA;
		public ArrayList<Trooper> targetTroopers = new ArrayList<Trooper>();
		public Trooper bestTargetTrooper = null;
		public TargetedFire tempTF;
		public TargetedFire targetedFire;
		public boolean possibleShots = true;
		public String wepPercent;
		public int sl;
		public Shoot shoot;
		public boolean shootReset = true;

		public BulkTrooper(Trooper trooper) {
			// System.out.println("Constructor");
			this.trooper = trooper;
			spentCA = 0;
			setAction();
			setTargets();

			this.CA = trooper.combatActions;
			this.wepPercent = trooper.weaponPercent;
			this.sl = trooper.sl;

		}

		// Returns a string for the individual output in the individuals list
		// Shows things like spent ca and ca
		public String bulkToString() {

			String rslt = "";
			rslt += trooper.number + "; " + trooper.name + " ";

			if (targetedFire != null && !targetedFire.fullAutoResults.equals("")) {
				rslt += "Full Auto: " + targetedFire.fullAutoResults + ", ";
			} else if (tempTF != null && !tempTF.fullAutoResults.equals("")) {
				rslt += "Full Auto: " + tempTF.fullAutoResults + ", ";
			}

			if (trooper.storedAimTime.size() > 0)
				rslt += "AIMING: ";

			if (trooper.HD) {
				rslt += "HUNKERED DOWN: ";
			}

			if (trooper.inCover) {
				rslt += "IN COVER: ";
			}

			if (trooper.disabledLegs > 1) {
				rslt += "IMOBALIZED: ";
			} else if (trooper.disabledLegs > 0) {
				rslt += "CRIP-LEG: ";
			}

			if (!trooper.conscious) {
				rslt += "UNCONSCIOUS: ";
			}

			if (trooper.personalShield != null)
				rslt += "CSS: " + trooper.personalShield.currentShieldStrength + " ";

			if (trooper.physicalDamage > 0)
				rslt += "PD: " + trooper.physicalDamage + ", ";

			if (trooper.ionDamage > 0)
				rslt += "ID: " + trooper.ionDamage + ", ";
			rslt += "P1: " + trooper.spentPhase1 + "/" + trooper.P1 + ", P2: " + trooper.spentPhase2 + "/" + trooper.P2
					+ " ";
			rslt += "CA: " + spentCA + "/" + CA + ", ";

			ArrayList<Trooper> spotted = new ArrayList<>();

			for (Spot spot : trooper.spotted) {

				for (Trooper trooper : spot.spottedIndividuals) {

					if (validTarget(trooper) && !spotted.contains(trooper)) {
						spotted.add(trooper);
					}

				}

			}

			rslt += "SC: " + spotted.size() + ", ";
			rslt += wepPercent + "%, SL: " + sl + ", ";
			rslt += "Ammo: " + trooper.ammo + ", ";
			rslt += "Weapon: " + trooper.wep;

			if (game.getPhase() == 1) {
				if (trooper.spentPhase1 >= trooper.P1 || trooper.spentPhase1 >= game.getCurrentAction())
					rslt = "Exhausted: " + rslt;
			} else {
				if (trooper.spentPhase2 >= trooper.P2 || trooper.spentPhase2 >= game.getCurrentAction())
					rslt = "Exhausted: " + rslt;
			}
			
			String leaderType = trooper.leaderType == LeaderType.NONE ? "" : trooper.leaderType.toString()+":: ";
			
			return trooper.returnTrooperUnit(GameWindow.gameWindow).callsign + ":: " + leaderType+ rslt;

		}

		public void setTargets() {

			targetTroopers.clear();

			for (Spot spot : trooper.spotted) {

				for (Trooper spottedTrooper : spot.spottedIndividuals)
					if (validTarget(spottedTrooper) && !targetTroopers.contains(spottedTrooper))
						targetTroopers.add(spottedTrooper);

			}

		}

		public void setAction() {

			trooper.setPCStats();
			CA = trooper.combatActions;
		}

	}

	public void aim(BulkTrooper bulkTrooper, int aimTime, Trooper targetTrooper) {
		// System.out.println("Target: "+targetTrooper.name);
		Trooper trooper = bulkTrooper.trooper;
		int maxAim = new Weapons().findWeapon(trooper.wep).aimTime.size();
		// System.out.println("Trooper Stored Aim Size:
		// "+bulkTrooper.trooper.storedAimTime.size());
		// Get stored aim time
		Hashtable<Trooper, Integer> storedAim;

		// If set to auto, uses maximum amount of remaining aim
		if (aimTime <= 0) {

			if (trooper.storedAimTime.containsKey(targetTrooper)) {
				// System.out.println("Pass 1");
				int additionalAim = bulkTrooper.CA - bulkTrooper.spentCA;
				int currentAim = trooper.storedAimTime.get(targetTrooper);
				int newAim;

				if (currentAim + additionalAim > maxAim - 1) {
					// System.out.println("Pass 1-1");
					newAim = maxAim;
					bulkTrooper.spentCA += maxAim - currentAim;
				} else {
					// System.out.println("Pass 1-2");
					newAim = currentAim += additionalAim;
					bulkTrooper.spentCA += additionalAim;
				}

				trooper.storedAimTime.put(targetTrooper, newAim);
			} else {
				// System.out.println("Pass 2");
				trooper.storedAimTime.clear();
				int newAim = bulkTrooper.CA - bulkTrooper.spentCA;
				trooper.storedAimTime.put(targetTrooper, newAim);
				bulkTrooper.spentCA += newAim;
			}

		}
		// Otherwise, goes to specified aim
		else {
			// System.out.println("Pass 3");
			trooper.storedAimTime.clear();
			trooper.storedAimTime.put(targetTrooper, aimTime - 1);
			bulkTrooper.spentCA += aimTime - 1;
		}

	}

	public void setValidTarget(BulkTrooper bulkTrooper) throws Exception {
		Trooper targetTrooper;

		targetTrooper = getTargetTrooper(bulkTrooper);

		if (validTarget(targetTrooper)) {
			// PCShots(bulkTrooper, targetTrooper);
			bulkTrooper.shoot = ShootUtility.setTarget(bulkTrooper.trooper.returnTrooperUnit(GameWindow.gameWindow), targetTrooper.returnTrooperUnit(gameWindow),
					bulkTrooper.shoot, bulkTrooper.trooper, targetTrooper, bulkTrooper.trooper.wep, -1);
			if(bulkTrooper.shootReset) {
				bulkTrooper.shoot.spentCombatActions = 0; 
				bulkTrooper.shoot.previouslySpentCa = 0;
			}
		} else {
			// SC: # displayed in list could be spotted troopers 
			// Multithreading could be leading to errors where ui doesn't get set or lists don't get updated / cleared 
			throw new Exception(bulkTrooper.trooper.number + " " + bulkTrooper.trooper.name + " no valid target");
		}

	}

	public void bulkTrooperShoot(BulkTrooper bulkTrooper) throws Exception {

		Trooper targetTrooper;

		if (bulkTrooper.targetedFire != null) {
			targetTrooper = bulkTrooper.targetedFire.targetTrooper;
		} else if (bulkTrooper.tempTF != null) {
			targetTrooper = bulkTrooper.tempTF.targetTrooper;
		} else {
			throw new Exception(bulkTrooper.trooper.number + " " + bulkTrooper.trooper.name
					+ " trooper shot no valid target exception.");
		}

		if (validTarget(targetTrooper))
			PCFire(bulkTrooper, targetTrooper);
		else {
			bulkTrooper.targetedFire = null;
			bulkTrooper.tempTF = null;
			GameWindow.gameWindow.conflictLog.addNewLineToQueue("No valid target. Ending string of fire.");
			return;
		}

		if (bulkTrooper.targetedFire == null)
			return;
		else
			bulkTrooper.targetedFire.EAL += (int) spinnerConsecutiveEALBonus.getValue();

		if (bulkTrooper.targetedFire.TN < 0 || !validTarget(targetTrooper)) {

			// bulkTrooper.targetTroopers.remove(targetTrooper);
			GameWindow.gameWindow.conflictLog
					.addNewLineToQueue("TN less than 1, target may be dead. Ending string of fire.");
			bulkTrooper.targetTroopers.clear();
			bulkTrooper.setTargets();
			bulkTrooper.targetedFire = null;
			bulkTrooper.bestTargetTrooper = null;
			bulkTrooper.tempTF = null;

			for (BulkTrooper t : bulkTroopers) {
				t.targetTroopers.clear();
				t.setTargets();
			}

			bulkTrooper.trooper.storedAimTime.clear();
			if (!freeAction() && (bulkTrooper.spentCA >= bulkTrooper.CA && bulkTrooper.possibleShots == false)) {
				bulkTrooper.trooper.storedAimTime.clear();
				actionSpent(bulkTrooper.trooper);

			}
			return;
		} else {
			// PCShots(bulkTrooper, targetTrooper);
		}

		if (!freeAction() && (bulkTrooper.spentCA >= bulkTrooper.CA && bulkTrooper.possibleShots == false)) {
			bulkTrooper.trooper.storedAimTime.clear();
			actionSpent(bulkTrooper.trooper);

		}
	}

	// Loops through units in initiaitive order
	// Looks for unit containing indvididual
	// Returns unit
	public Unit findTrooperUnit(Trooper trooper) {
		ArrayList<Unit> units = gameWindow.initiativeOrder;

		for (Unit unit : units) {

			for (Trooper trooper1 : unit.getTroopers()) {

				if (trooper1.compareTo(trooper)) {
					// System.out.println("Found Unit: "+unit.callsign);
					return unit;
				}

			}

		}

		return null;

	}

	public boolean validTarget(Trooper target) {

		if (target == null || !target.alive || !target.conscious || target.HD) {
			return false;
		}

		return true;
	}

	public Trooper getTargetTrooper(BulkTrooper bulkTrooper) throws Exception {
		if(bulkTrooper.trooper.storedAimTime.size() > 0) {
			
			for(Trooper target : bulkTrooper.targetTroopers) {
				if(bulkTrooper.trooper.storedAimTime.containsKey(target)) {
					return target;
				}
			}
			
		}
		
		if (bulkTrooper.bestTargetTrooper != null && validTarget(bulkTrooper.bestTargetTrooper))
			return bulkTrooper.bestTargetTrooper;
		else
			bulkTrooper.bestTargetTrooper = null;

		/**/
		// Sets random target trooper
		// System.out.println("Target Troopers size:
		// "+bulkTrooper.targetTroopers.size());
		// shuffleList(bulkTrooper.targetTroopers);

		Trooper targetTrooper = null;

		Unit targetUnit = null;

		Unit trooperUnit = GameWindow.gameWindow.findTrooperUnit(bulkTrooper.trooper);

		for (Unit unit : GameWindow.gameWindow.initiativeOrder) {
			if (unit.side.equals(trooperUnit.side) || unit.individuals.size() < 1)
				continue;

			boolean validTargets = false;

			for (Trooper trooper : unit.individuals) {
				if (validTarget(trooper) && bulkTrooper.targetTroopers.contains(trooper)) {
					// System.out.println("Valid Targets");
					validTargets = true;
					break;
				}
			}

			if (!validTargets)
				continue;

			if (targetUnit == null) {
				targetUnit = unit;
			} else if (GameWindow.hexDif(targetUnit, trooperUnit) > GameWindow.hexDif(unit, trooperUnit)) {
				targetUnit = unit;
			}

		}

		if (targetedFireFocus.getSelectedIndex() > 0 && getValidTargetUnits().size() > 0) {
			targetUnit = getValidTargetUnits().get(targetedFireFocus.getSelectedIndex() - 1);
			System.out.println("Focus Target Unit: " + targetUnit.callsign);
		}

		if (targetUnit == null)
			throw new Exception(bulkTrooper.trooper.number + " " + bulkTrooper.trooper.name
					+ " getTargetTrooper Target Unit is Null");

		ArrayList<Trooper> targetTrooperArray = new ArrayList<Trooper>();
		for (Trooper trooper : targetUnit.individuals) {
			if (bulkTrooper.targetTroopers.contains(trooper)) {
				targetTrooperArray.add(trooper);
			}
		}

		if (targetTrooperArray.size() == 0) {
			throw new Exception(
					bulkTrooper.trooper.number + " " + bulkTrooper.trooper.name + " targetTrooperArray is empty.");
		}

		targetTrooper = targetTrooperArray.get(DiceRoller.randInt(0, targetTrooperArray.size() - 1));

		for (Trooper trooper : targetUnit.individuals) {
			if (!targetTrooper.inCover)
				break;

			if (!bulkTrooper.targetTroopers.contains(trooper))
				continue;

			targetTrooper = trooper;
		}

		bulkTrooper.bestTargetTrooper = targetTrooper;
		return targetTrooper;

	}

	public void PCFireGuiUpdates() {
		if (getSelectedBulkTroopers().size() < 1)
			return;

		int meanTN = 0;
		for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

			if (bulkTrooper.targetedFire != null)
				meanTN += bulkTrooper.targetedFire.TN;
			else if (bulkTrooper.tempTF != null)
				meanTN += bulkTrooper.tempTF.TN;

		}
		meanTN /= getSelectedBulkTroopers().size();
		lblTn.setText("Mean TN: " + meanTN);

		int aimTime = 0;
		for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

			if (bulkTrooper.targetedFire != null)
				aimTime += bulkTrooper.targetedFire.spentAimTime;
			else if (bulkTrooper.tempTF != null)
				aimTime += bulkTrooper.tempTF.spentAimTime;

		}
		aimTime /= getSelectedBulkTroopers().size();
		lblAimTime.setText("Mean Aim Time: " + aimTime);

		int possibleShots = 0;
		for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {

			if (bulkTrooper.targetedFire != null) {
				// System.out.println("temp tf not null");
				possibleShots += bulkTrooper.targetedFire.possibleShots - bulkTrooper.targetedFire.shotsTaken;

			} else if (bulkTrooper.tempTF != null) {
				// System.out.println("temp tf not null");
				possibleShots += bulkTrooper.tempTF.possibleShots - bulkTrooper.tempTF.shotsTaken;

			}

		}
		possibleShots /= getSelectedBulkTroopers().size();
		lblPossibleShots.setText("Mean Possible Shots: " + possibleShots);

		// lblAmmo.setText("Ammo: "+bulkTrooper.trooper.ammo);
		// lblCombatActions.setText("TF CA: "+bulkTrooper.CA);
		gameWindow.conflictLog.addQueuedText();
		gameWindow.refreshInitiativeOrder();
		// refreshTargets();

	}

	public void PCFire(BulkTrooper bulkTrooper, Trooper targetTrooper) {
		// TargetedFire tempTF = bulkTrooper.tempTF;

		Trooper trooper = bulkTrooper.trooper;

		boolean shots;

		if (chckbxFullAuto.isSelected()) {
			shots = trooper.inventory.fireShots(new Weapons().findWeapon(trooper.wep).fullAutoROF,
					new Weapons().findWeapon(trooper.wep));
		} else {
			int roll = new Random().nextInt(3) + 1;
			shots = trooper.inventory.fireShots(roll, new Weapons().findWeapon(trooper.wep));
		}

		// Checks for out of ammo
		if (!shots) {
			// textPaneTargetedFire.setText("OUT OF AMMO");
			gameWindow.conflictLog
					.addNewLineToQueue("Trooper: " + trooper.number + ": " + trooper.name + " is out of ammo");
			return;
		}

		if (new Weapons().findWeapon(trooper.wep).fullAutoROF == 0 && chckbxFullAuto.isSelected()) {
			gameWindow.conflictLog.addNewLineToQueue(
					"Trooper: " + trooper.number + ": " + trooper.name + "'s weapon is not full auto capable.");
		}

		Trooper shooterTrooper = trooper;

		Unit targetUnit = findTrooperUnit(targetTrooper);
		Unit shooterUnit = findTrooperUnit(shooterTrooper);

		for (Trooper t : bulkTrooper.targetTroopers) {
			if (shooterTrooper.storedAimTime.containsKey(t)) {
				targetTrooper = t;
				break;
			}
		}

		int maxAim = comboBoxAimTime.getSelectedIndex() - 1;

		TargetedFire tf = new TargetedFire(shooterTrooper, targetTrooper, shooterUnit, targetUnit, gameWindow, maxAim,
				bulkTrooper.CA - bulkTrooper.spentCA,
				(int) spinnerEALBonus.getValue() + (int) spinnerConsecutiveEALBonus.getValue(),
				(int) spinnerPercentBonus.getValue(), 0, shooterTrooper.wep);

		tf.spentCA = bulkTrooper.spentCA;

		if (bulkTrooper.targetedFire == null) {
			bulkTrooper.targetedFire = tf;
			// reaction = null;
			bulkTrooper.possibleShots = true;
		} else if (!tf.targetTrooper.compareTo(bulkTrooper.targetedFire.targetTrooper)) {
			bulkTrooper.targetedFire = tf;
		}

		bulkTrooper.targetedFire.PCHits = 0;

		if (bulkTrooper.possibleShots) {

			if (chckbxFullAuto.isSelected()) {
				bulkTrooper.targetedFire.fullAutoBurst(true);
				if (chckbxFreeAction.isSelected()) {
					bulkTrooper.targetedFire.spentCA -= 1;
				} else if (chckbxFreeAction.isSelected()) {
					bulkTrooper.targetedFire.spentCA -= 2;
				}
			} else {
				bulkTrooper.targetedFire.shot(comboBoxTargetZone.getSelectedIndex());
				if (chckbxFreeAction.isSelected()) {
					bulkTrooper.targetedFire.spentCA -= 1;
				}
			}

			// System.out.println("TARGETED FIRE: ");
			// System.out.println("targetedFire.shotsTaken:
			// "+bulkTrooper.targetedFire.shotsTaken);
			// System.out.println("targetedFire.timeToReaction:
			// "+bulkTrooper.targetedFire.timeToReaction);
			/*
			 * if(targetedFire.shotsTaken >= targetedFire.timeToReaction &&
			 * targetedFire.shotsTaken != 0 && this.reaction == null && targetTrooper.alive
			 * && targetTrooper.conscious && targetTrooper.canAct(gameWindow.game)) { //
			 * React System.out.println("REACTION"); //ReactionToFireWindow reaction = new
			 * ReactionToFireWindow(shooterTrooper, targetTrooper, windowOpenTrooper,
			 * gameWindow); //this.reaction = reaction;
			 * 
			 * }
			 */
		}

		if (bulkTrooper.targetedFire.PCHits > 0) {
			ResolveHits resolveHits = new ResolveHits(targetTrooper, bulkTrooper.targetedFire.PCHits,
					new Weapons().findWeapon(shooterTrooper.wep), gameWindow.conflictLog,
					targetTrooper.returnTrooperUnit(gameWindow), shooterUnit, gameWindow);

			if (bulkTrooper.targetedFire.calledShot) {
				resolveHits.calledShot = true;
				resolveHits.calledShotBounds = bulkTrooper.targetedFire.calledShotBounds;

			}

			if (targetTrooper.returnTrooperUnit(gameWindow).suppression + bulkTrooper.targetedFire.PCHits < 100) {
				targetTrooper.returnTrooperUnit(gameWindow).suppression += bulkTrooper.targetedFire.PCHits;
			} else {
				targetTrooper.returnTrooperUnit(gameWindow).suppression = 100;
			}
			if (targetTrooper.returnTrooperUnit(gameWindow).organization - bulkTrooper.targetedFire.PCHits > 0) {
				targetTrooper.returnTrooperUnit(gameWindow).organization -= bulkTrooper.targetedFire.PCHits;
			} else {
				targetTrooper.returnTrooperUnit(gameWindow).organization = 0;
			}

			resolveHits.performCalculations(gameWindow.game, gameWindow.conflictLog);
			InjuryLog.InjuryLog.addTrooper(targetTrooper);
		}

		if (bulkTrooper.targetedFire.possibleShots <= bulkTrooper.targetedFire.shotsTaken) {
			// Shot ends
			/*
			 * lblPossibleShots.setText("Possible Shots: None");
			 * lblAimTime.setText("Aim Time: N/A"); lblTN.setText("Target Number: N/A");
			 */
			// reaction = null;
			bulkTrooper.possibleShots = false;
			// bulkTrooper.targetedFire = null;
			// PCShots(bulkTrooper);
		}

		if (chckbxFullAuto.isSelected()) {

			bulkTrooper.spentCA += 2;
		} else {

			bulkTrooper.spentCA++;
		}

		/*
		 * if(!targetTrooper.alive) {
		 * 
		 * if(chckbxMultipleTargets.isSelected()) {
		 * 
		 * targetedFire = null; possibleShots = true; reaction = null; PCShots(); } else
		 * { // Performed after swing worker is done actionSpent(openUnit, index);
		 * openUnit.openNext(true); f.dispose(); }
		 * 
		 * 
		 * }
		 */

		// setDetails(openTrooper);

	}

	// Gets weapon
	public Weapons getWeapon(Trooper trooper) {

		return new Weapons().findWeapon(trooper.wep);
	}

	public Unit findUnit(String callsign) {

		for (Unit unit : gameWindow.initiativeOrder)
			if (unit.callsign.equals(callsign))
				return unit;

		return null;
	}

	// Spot test
	public void spotTest(String targetCallsign, Trooper trooper, Unit unit) {

		// Find spotter unit
		Unit spotterUnit = unit;

		// Find target unit
		Unit targetUnit = findUnit(targetCallsign);

		Spot spotAction = new Spot(gameWindow, spotterUnit, targetUnit, trooper,
				comboBoxScanArea.getSelectedItem().toString(), gameWindow.visibility, gameWindow.initiativeOrder,
				gameWindow);

		// Print results
		spotAction.displayResultsQueue(gameWindow, spotAction);

		// Set results in trooper
		trooper.spotted.add(spotAction);
		// Refresh trooper
		// refreshTrooper(trooper);
		// window.openUnit.troopers.set(index, trooper);

	}

	// Spot test
	public void spotTestAll(Trooper trooper, Unit unit) {

		// Find spotter unit
		Unit spotterUnit = unit;

		for (Unit targetUnit : unit.lineOfSight) {

			Spot spotAction = new Spot(gameWindow, spotterUnit, targetUnit, trooper,
					comboBoxScanArea.getSelectedItem().toString(), gameWindow.visibility, gameWindow.initiativeOrder,
					gameWindow);

			spotAction.displayResultsQueue(gameWindow, spotAction);

			// Set results in trooper
			trooper.spotted.add(spotAction);
		}

	}

	public boolean hasTargets(BulkTrooper bulkTrooper) {

		return true;

	}

	public boolean freeAction() {
		return chckbxFreeAction.isSelected();
	}

	public void volley() {

		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {

				InjuryLog.InjuryLog.addAlreadyInjured();
				ArrayList<BulkTrooper> troops = selectedBulkTroopers;

				ExecutorService es = Executors.newFixedThreadPool(16);

				for (BulkTrooper bulkTrooper : troops) {

					Shoot shoot = bulkTrooper.shoot;

					if (shoot == null)
						continue;

					es.submit(() -> {
						System.out.println("Submit");
						try {

							
							int shots = 1; 
							while (shoot.spentCombatActions < shoot.shooter.combatActions && 
									(validTarget(shoot.target) || comboBoxTargetUnits.getSelectedIndex() > 0)
									&& !shoot.outOfAmmo) {

								System.out.println("volley shot: "+shots);
								
								if (comboBoxTargetUnits.getSelectedIndex() > 0)
									shoot.suppressiveFire(shoot.wep.suppressiveROF);
								else if (chckbxFullAuto.isSelected())
									shoot.burst();
								else
									shoot.shot(chckbxGuided.isSelected());

								GameWindow.gameWindow.conflictLog.addNewLineToQueue("Results: " + shoot.shotResults);

								valleyValidTargetCheck(shoot, bulkTrooper);
								shots++;
								System.out.println("Volley CA test: "+(shoot.spentCombatActions < shoot.shooter.combatActions));
								System.out.println("Volley Valid Target Test: "+validTarget(shoot.target));
								System.out.println("Volley Valid Supp Target Test: "+(comboBoxTargetUnits.getSelectedIndex() > 0));
								System.out.println("Volley Out of Ammo Test: "+(!shoot.outOfAmmo));
							}
							
							
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				es.shutdown();

				return null;
			}

			@Override
			protected void done() {

				try {
					TimeUnit.MILLISECONDS.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				for(BulkTrooper bulkTrooper : selectedBulkTroopers) {
					if(!chckbxFreeAction.isSelected()) {
						System.out.println("volley action spent");
						actionSpent(bulkTrooper.trooper);
					}
				}
				
				System.out.println("volley gui updates");
				guiUpdates();
				refreshIndividualList();
				InjuryLog.InjuryLog.printResultsToLog();
				gameWindow.conflictLog.addQueuedText();
				// individualsList.setSelectedIndex(-1);

			}

		};

		worker.execute();

	}

	public void valleyValidTargetCheck(Shoot shoot, BulkTrooper bulkTrooper) throws Exception {
		if (!validTarget(shoot.target)) {
			bulkTrooper.setTargets();

			if (bulkTrooper.targetTroopers.size() > 0) {
				setValidTarget(bulkTrooper);

				if (comboBoxAimTime.getSelectedIndex() == 0)
					bulkTrooper.shoot.autoAim();

				if (comboBoxTargetZone.getSelectedIndex() > 0) {
					setCalledShotBounds(bulkTrooper.shoot);
				}
			}

		}
	}

	public void ordnanceComboboxes() {
		/*
		 * comboBoxBuilding.removeAllItems(); comboBoxBuilding.addItem("None"); Hex hex
		 * = GameWindow.gameWindow.findHex(trooperUnit.X, trooperUnit.Y); if
		 * (trooperBuilding == null && hex != null) { for (Building building :
		 * hex.buildings) {
		 * 
		 * comboBoxBuilding.addItem(building.name);
		 * 
		 * } comboBoxBuilding.setSelectedIndex(0); } else if (hex != null) {
		 * comboBoxBuilding.removeAllItems(); comboBoxBuilding.addItem("None");
		 * comboBoxBuilding.addItem("ALREADY INSIDE");
		 * comboBoxBuilding.setSelectedIndex(1); }
		 */
	}

	public void guiUpdates() {

		ArrayList<Shoot> shots = new ArrayList<>();

		for (BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
			if (bulkTrooper.shoot == null) {
				System.out.println("Shoot is null 2");
				continue;
			}
			shots.add(bulkTrooper.shoot);
		}

		ShootUtility.shootGuiUpdate(lblPossibleShots, lblAimTime, lblTn, null, null, null, chckbxFullAuto, shots);
	}

	public void setCalledShotBounds(Shoot shoot) {
		if (shoot == null) {
			System.out.println("shoot is null set called shot bounds");
			return;
		}

		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {
					int index = comboBoxTargetZone.getSelectedIndex();
					System.out.println("Size ALM Pre: " + shoot.sizeALM);
					if (index == 0) {
						System.out.println("Clear called shot");
						shoot.calledShotBounds.clear();
						shoot.calledShotLocation = "";
					} else {
						System.out.println("set called shot");
						shoot.setCalledShotBounds(comboBoxTargetZone.getSelectedIndex());
					}
					System.out.println("Size ALM POST: " + shoot.sizeALM);
					shoot.setALM();
					shoot.setEAL();
					shoot.setSingleTn();
					shoot.setFullAutoTn();
					shoot.setSuppressiveTn();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void done() {

				guiUpdates();

			}

		};

		worker.execute();
	}

	public void bonuses() {

		for (BulkTrooper bulkTrooper : selectedBulkTroopers) {
			if (bulkTrooper.shoot == null) {
				System.out.println("Shoot is null 2 bonuses");
				continue;
			}
			bulkTrooper.shoot.setBonuses((int) spinnerPercentBonus.getValue(), (int) spinnerEALBonus.getValue(),
					(int) spinnerConsecutiveEALBonus.getValue());
		}

		guiUpdates();

	}

	public void selected() {
		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {
				try {

					ExecutorService es = Executors.newFixedThreadPool(16);

					ArrayList<BulkTrooper> currentlySelectedBulkTroopers = getSelectedBulkTroopers();
					ArrayList<BulkTrooper> removeTrooper = new ArrayList<>();

					for (BulkTrooper bulkTrooper : selectedBulkTroopers) {
						if (!currentlySelectedBulkTroopers.contains(bulkTrooper)) {
							removeTrooper.add(bulkTrooper);
						}
					}

					for (BulkTrooper bulkTrooper : removeTrooper) {
						selectedBulkTroopers.remove(bulkTrooper);
					}

					for (BulkTrooper bulkTrooper : currentlySelectedBulkTroopers) {
						if (selectedBulkTroopers.contains(bulkTrooper))
							continue;

						if (bulkTrooper.targetTroopers.size() > 0) {
							es.submit(() -> {
								System.out.println("Submit");
								try {
									
									if(comboBoxTargetUnits.getSelectedIndex() > 0)
										bulkTrooper.shoot = ShootUtility.setTargetUnit(unit, targetUnits.get(comboBoxTargetUnits.getSelectedIndex() -1),
												bulkTrooper.shoot, bulkTrooper.trooper, bulkTrooper.trooper.wep, -1);
									else 
										setValidTarget(bulkTrooper);

									if (comboBoxAimTime.getSelectedIndex() == 0)
										bulkTrooper.shoot.autoAim();

									if (comboBoxTargetZone.getSelectedIndex() > 0 && comboBoxTargetUnits.getSelectedIndex() == 0) {
										setCalledShotBounds(bulkTrooper.shoot);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							});
						}
						
						if(!selectedBulkTroopers.contains(bulkTrooper))
							selectedBulkTroopers.add(bulkTrooper);
					}

					/*
					 * for(BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
					 * if(bulkTrooper.targetTroopers.size() < 1) { continue; }
					 * 
					 * 
					 * es.submit(() -> { System.out.println("Submit"); try {
					 * setValidTarget(bulkTrooper); } catch (Exception e) { e.printStackTrace(); }
					 * }); }
					 */

					es.shutdown();

					System.out.println("Finished Threads");

				} catch (Exception e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void done() {
				/*
				 * targetedFireFocus.removeAllItems();
				 * targetedFireFocus.addItem("Targeted Fire Focus"); targetUnits.clear();
				 * for(BulkTrooper bulkTrooper : getSelectedBulkTroopers()) {
				 * 
				 * for(Trooper targetTrooper : bulkTrooper.targetTroopers) { Unit targetUnit =
				 * findTrooperUnit(targetTrooper);
				 * 
				 * if(targetUnits.contains(targetUnit)) continue;
				 * 
				 * targetUnits.add(targetUnit);
				 * targetedFireFocus.addItem(findTrooperUnit(targetTrooper).callsign);
				 * 
				 * }
				 * 
				 * }
				 */

				// System.out.println("Done");
				
				
				selectedGuiUpdates();
			}

		};

		worker.execute();
	}
	
	public void selectedGuiUpdates() {
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setTargetFocus();
		gameWindow.conflictLog.addQueuedText();
		// PCFireGuiUpdates();
		guiUpdates();
		System.out.println("Selected Bulk Troopers Size: " + selectedBulkTroopers.size());
	}
}
