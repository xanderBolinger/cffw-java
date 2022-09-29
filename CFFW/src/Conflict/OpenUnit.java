package Conflict;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JFrame;

import Unit.Unit;
import Unit.Unit.UnitType;
import UtilityClasses.HexGridUtility;
import UtilityClasses.UnitReorderListener;

import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import CloseCombat.CloseCombatWindow;
import HexGrid.HexGrid;
import HexGrid.HexGrid.DeployedUnit;
import Items.StaticWeaponWindow;
import Trooper.Trooper;
import Trooper.generateSquad;
import Hexes.Hex;
import javax.swing.JList;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
/*import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;*/

import Actions.Spot;

import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpenUnit implements Serializable {
	ArrayList<Trooper> troopers = new ArrayList<Trooper>();
	public int activeTrooper = 0; 
	public int activeUnitOpenUnit = 0; 
	public Unit unit; 
	public GameWindow gameWindow;
	public OpenUnit openUnit;
	public OpenTrooper openTrooper;
	private JList listIndividuals;
	private JLabel lblUnitCallsign;
	private JComboBox comboBoxConcealment;
	private JComboBox comboBoxBehavior;
	private JComboBox comboBoxSpeed;
	private JSpinner spinnerOrganization;
	private JSpinner spinnerSuppression;
	private JSpinner spinnerCommandValue;
	private JSpinner spinnerMoral;
	private JTextField textFieldUnit;
	private JLabel lblImobalized;
	private JSpinner spinnerX;
	private JSpinner spinnerY;
	private JLabel lblActive;
	private JLabel lblCloseCombat;
	private JButton btnNewButton_1;
	private JLabel lblEffectiveBurden;
	private JLabel lblNewLabel;
	private JLabel lblContact;
	private JLabel lblSpeed;
	private JLabel lblTroopersInCover;
	private JLabel lblTimeSinceContact;
	private JButton btnBulkOperations;
	private JButton btnNewButton_2;
	private JComboBox comboBoxUnitType;
	public final JFrame f; 
	private JButton btnEmbark;
	private JLabel lblFatigue;
	/*
	 * Launch the application.
	 */
	public OpenUnit(Unit unit, GameWindow window, int index) {
		//System.out.println("Open unit Window moral: "+unit.moral);
		this.unit = unit; 
		gameWindow = window;
		openUnit = this;
		troopers = unit.getTroopers();
		activeUnitOpenUnit = index; 
		
		f = new JFrame("Open Unit");
		f.setSize(699, 533);
		
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);
				
		lblImobalized = new JLabel("Imobalized: 0");
		lblImobalized.setBounds(89, 232, 100, 17);
		
		/*// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;
		y -= screenSize.height / 5;

		// Set the new frame location
		f.setLocation(x, y);
		f.setSize(699, 533);*/

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 253, 663, 235);

		lblUnitCallsign = new JLabel("Callsign: ");
		lblUnitCallsign.setBounds(10, 14, 194, 17);
		lblUnitCallsign.setFont(new Font("Calibri", Font.BOLD, 13));

		JButton button = new JButton("Next");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBounds(415, 11, 80, 23);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("Next Unit");
				
				if(gameWindow.cqbWindowOpen) {
					return; 
				}
				
				if(openTrooper != null)
					openTrooper.f.dispose();
				
				if(window.activeUnit != window.initiativeOrder.indexOf(unit)) {
					window.conflictLog.addNewLine("Selected Unit is not the active unit");
					return; 
				}
				
				new SwingWorker() {

					// Don't Update GUI
					@Override
					protected Object doInBackground() throws Exception {
						try {
							//System.out.println("Pass01 - Entered Do In Background");
							
							// Clears close combat ranges 
							clearCloseCombatRanges();
							
							
							// Saves unit
							safeSaveUnit(unit);

							// Refreshes window 
							//System.out.println("UI thread name is:"+Thread.currentThread().getName());
							//System.out.println("Pass02 - Entered Safe Next Active Unit");
							
							window.safeNextActiveUnit();

							if(window.hexGrid != null && window.hexGrid.panel.deployedUnits.size() > window.activeUnit) {
								window.hexGrid.panel.selectedUnit = window.hexGrid.panel.deployedUnits.get(window.activeUnit);
							}
							
							window.initiativeOrder.get(window.activeUnit).setFiredWeapons();
							
					      } catch(Exception e) {
					         System.out.println("toString(): " + e.toString());
					         System.out.println("getMessage(): " + e.getMessage());
					         System.out.println("StackTrace: ");
					         e.printStackTrace();
					      }
						
						
						
						//System.out.println("Pass1 - Finished Do In Background");
						return null;
					}
					
					

					// Updates GUI 
					@Override 
					protected void done() {
						//System.out.println("Pass2");
						
						// Overwrites unit
						//window.initiativeOrder.set(activeUnitOpenUnit, unit);
						// Selectes next unit in order
											
						DefaultListModel initiativeOrderList = new DefaultListModel();

						for (int i = 0; i < window.initiativeOrder.size(); i++) {
							
							// Loops through troopers in the unit
							
							for (int x = 0; x < window.initiativeOrder.get(i).getSize(); x++) {
								Trooper currentTrooper = window.initiativeOrder.get(i).getTroopers().get(x);
								currentTrooper.number = x + 1; 
							}
							
							initiativeOrderList.addElement(i+": "+window.initiativeOrder.get(i));

						}

						window.listIniativeOrder.setModel(initiativeOrderList);
						
						//System.out.println("Next Active Unit");
						
						window.guiUpdateNextActiveUnit();
						
						
						/*if(window.conflictLog.textToBeAdded.contains("Round:")) {
							
							SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
								
								@Override 
								protected Void doInBackground() throws Exception {
									
									System.out.println("Spot1");
									window.spotCycle();
									Thread.sleep(150);
									System.out.println("Spot2");
									return null; 
								}
								
							};
							
							
							worker.execute();
							
						}*/
						
						window.conflictLog.addQueuedText();
						
						// Check for random event 
						window.randomEventCheck();
						
						//System.out.println("Pass Dispose 1 Active Unit: "+window.activeUnit);
						window.currentlyOpenUnit.f.dispose();
						
						if(GameWindow.gameWindow.hexGrid != null) {
							GameWindow.gameWindow.hexGrid.panel.shownType = 
									HexGridUtility.getShownTypeFromSide(window.initiativeOrder.get(window.activeUnit).side);
						}
						window.openUnit(window.initiativeOrder.get(window.activeUnit), window.activeUnit);
						
						//System.out.println("Pass Dispose 2");
						
					}
					
					
				}.execute();
	
				//f.dispose();
				
				f.dispose();
				/*SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
					
					@Override 
					protected Void doInBackground() throws Exception {
						
						System.out.println("Pass1");
						window.safeNextActiveUnit();
						System.out.println("Pass2");
						DefaultListModel initiativeOrderList = new DefaultListModel();

						for (int i = 0; i < window.initiativeOrder.size(); i++) {
							
							// Loops through troopers in the unit
							
							for (int x = 0; x < window.initiativeOrder.get(i).getSize(); x++) {
								Trooper currentTrooper = window.initiativeOrder.get(i).getTroopers().get(x);
								currentTrooper.number = x + 1; 
							}
							
							initiativeOrderList.addElement(window.initiativeOrder.get(i));

						}

						window.listIniativeOrder.setModel(initiativeOrderList);
						System.out.println("Pass3");						
						window.guiUpdateNextActiveUnit();
						System.out.println("Pass4");
						
						return null; 
					}
					
					@Override 
					protected void done() {
						
						System.out.println("Pass5 Done");
						window.openUnit(window.initiativeOrder.get(window.activeUnit), window.activeUnit);
						f.dispose();
					
					}
					
				};
				
				worker.execute();*/
				
				
				/*while(worker.isCancelled()) {
					if(worker.isDone())
						
				}*/
				
				
				/*// Saves unit
				safeSaveUnit(unit);

				// Refreshes window 
				//System.out.println("UI thread name is:"+Thread.currentThread().getName());
				
				window.safeNextActiveUnit();
				
				DefaultListModel initiativeOrderList = new DefaultListModel();

				for (int i = 0; i < window.initiativeOrder.size(); i++) {
					
					// Loops through troopers in the unit
					
					for (int x = 0; x < window.initiativeOrder.get(i).getSize(); x++) {
						Trooper currentTrooper = window.initiativeOrder.get(i).getTroopers().get(x);
						currentTrooper.number = x + 1; 
					}
					
					initiativeOrderList.addElement(window.initiativeOrder.get(i));

				}

				window.listIniativeOrder.setModel(initiativeOrderList);
				
				System.out.println("Next Active Unit");
				
				window.guiUpdateNextActiveUnit();
				
				f.dispose();*/
				
				
				
				
				
				
			}
		});

		comboBoxConcealment = new JComboBox();
		comboBoxConcealment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				unit.concealment = comboBoxConcealment.getSelectedItem().toString();
				
			}
		});
		comboBoxConcealment.setBounds(10, 54, 146, 20);
		comboBoxConcealment.setModel(new DefaultComboBoxModel(new String[] {"No Concealment ", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5"}));
		comboBoxConcealment.setSelectedIndex(0);

		comboBoxBehavior = new JComboBox();
		comboBoxBehavior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//saveUnit(unit, window);
				
				unit.behavior = comboBoxBehavior.getSelectedItem().toString();
				
				if(gameWindow.findHex(unit.X, unit.Y) != null)
					gameWindow.findHex(unit.X, unit.Y).usedPositions -= unit.individualsInCover;
				
				unit.individualsInCover = 0; 
				for(Trooper trooper : unit.individuals) {
					if(!trooper.inBuilding(gameWindow)) 
						trooper.inCover = false; 
										
				}
				
				unit.soughtCover = false; 
				if(!unit.behavior.equals("No Contact")) {
					unit.timeSinceContact = 0;				
					unit.seekCover(gameWindow.findHex(unit.X, unit.Y), gameWindow);
				}
				
				for(Trooper trooper : unit.individuals) {
					if(!trooper.inCover && comboBoxBehavior.getSelectedIndex() != 0 && !trooper.manualStance)
						trooper.stance = "Prone";
					else if(! trooper.manualStance && comboBoxBehavior.getSelectedIndex() < 1)
						trooper.stance = "Standing";
				}

				refreshIndividuals(); 
			
			}
		});
		comboBoxBehavior.setBounds(166, 54, 107, 20);
		comboBoxBehavior.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				/*saveUnit(unit, window);
				
				for(Trooper trooper : unit.individuals) {
					trooper.inCover = false; 
				}
				
				unit.soughtCover = false; 
				if(unit.behavior.equals("Contact")) {
					unit.timeSinceContact = 0;				
					unit.seekCover(gameWindow.findHex(unit.X, unit.Y));
				}
				
				refreshIndividuals();
				refreshSpinners();*/
				
				
			}
		});
		comboBoxBehavior.setModel(new DefaultComboBoxModel(new String[] {"No Contact", "Recent Contact", "Contact"}));
		

		comboBoxSpeed = new JComboBox();
		comboBoxSpeed.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				unit.speed = (String) comboBoxSpeed.getSelectedItem();
				//saveUnit(unit, window);
				if(gameWindow.findHex(unit.X, unit.Y) != null)
					gameWindow.findHex(unit.X, unit.Y).usedPositions -= unit.individualsInCover;
				
				unit.individualsInCover = 0; 
				for(Trooper trooper : unit.individuals) {
					if(!trooper.inBuilding(gameWindow)) 
						trooper.inCover = false; 
					
				}
				
				unit.soughtCover = false; 
				if(!unit.behavior.equals("No Contact")) {
					unit.timeSinceContact = 0;				
					unit.seekCover(gameWindow.findHex(unit.X, unit.Y), gameWindow);
				}
				refreshIndividuals(); 
				
			}
		});
		comboBoxSpeed.setBounds(283, 54, 72, 20);
		comboBoxSpeed.setModel(new DefaultComboBoxModel(new String[] {"None", "Walk", "Crawl", "Rush"}));
		comboBoxSpeed.setSelectedIndex(0);

		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setBounds(221, 83, 143, 17);
		lblLocation.setFont(new Font("Calibri", Font.BOLD, 13));

		spinnerX = new JSpinner();
		spinnerX.setBounds(122, 81, 41, 20);

		spinnerY = new JSpinner();
		spinnerY.setBounds(169, 81, 42, 20);

		JButton btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnMove.setBounds(10, 80, 106, 23);
		btnMove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(unit.X == (int) spinnerX.getValue() && unit.Y == (int) spinnerY.getValue())
					return; 
					
				
				window.conflictLog.addNewLine(
						unit.side + ":: " + unit.callsign + "::  Moving from... X: " + unit.X + ", Y: " + unit.Y);
				
				// Adds to conflict log
				window.conflictLog.addNewLine(
						unit.side + ":: " + unit.callsign + "::  Moved to... X: " + unit.X + ", Y: " + unit.Y);
				
				lblLocation.setText("Location: X: " + unit.X + ", Y: " + unit.Y);
				
				//gameWindow.hexGrid.refreshDeployedUnits();
				
				unit.move(gameWindow, (int) spinnerX.getValue(), (int) spinnerY.getValue(), openUnit);
				
				refreshSpinners();
				refreshIndividuals();
				
				
			}
		});

		JLabel lblActions = new JLabel("Actions:");
		lblActions.setBounds(214, 14, 83, 17);
		lblActions.setFont(new Font("Calibri", Font.BOLD, 13));

		
		listIndividuals = new JList(UnitReorderListener.getListModel(unit.individuals, GameWindow.gameWindow.game));
		MouseAdapter listener = new UnitReorderListener(listIndividuals, unit.individuals, openUnit);
		listIndividuals.addMouseListener(listener);
	    listIndividuals.addMouseMotionListener(listener);
		listIndividuals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(troopers == null) {
					//System.out.println("Troopers Size: "+troopers.size()+", Unit Size: "+unit.individuals.size());
					return;
				}
				
				//System.out.println("Troopers Size2: "+troopers.size()+", Unit Size: "+unit.individuals.size());
				
				int index = listIndividuals.getSelectedIndex();
				if(index < 0) {
					return;
				}
				

				// This is a patch work fix, think about a better way to implement 
				String value = listIndividuals.getSelectedValue().toString();
				String numString = ""; 
				for(int i = 0; i < value.length() - 1; i++) {
					
					char c = value.charAt(i);
					if(c == ';')
						break;
					else if(Character.isDigit(c)) {
						numString += c; 
					}
				}
					
				
				int number = Integer.parseInt(numString);
				
				// Scans troopers for selected trooper 
				// Opens selected trooper
				for(int i = 0; i < troopers.size(); i++) {
					
					if(troopers.get(i).number == number) {
						openTrooper = new OpenTrooper(troopers.get(i), unit, openUnit, i, true);
					}
					
				}
				
				
		
				
			}
		});
		listIndividuals.setSelectedIndex(0);
		scrollPane.setViewportView(listIndividuals);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		lblUnitCallsign.setText("Callsign: " + unit.callsign);
		lblActions.setText("Actions: " + window.game.getCurrentAction());
		lblLocation.setText("Location: X: " + unit.X + ", Y: " + unit.Y);

		JLabel label_1 = new JLabel("ORG");
		label_1.setBounds(10, 118, 56, 14);
		label_1.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		spinnerOrganization = new JSpinner();
		spinnerOrganization.setBounds(48, 115, 40, 20);

		JLabel label_2 = new JLabel("CV");
		label_2.setBounds(271, 118, 26, 14);
		label_2.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		spinnerCommandValue = new JSpinner();
		spinnerCommandValue.setBounds(296, 115, 45, 20);

		JLabel label_3 = new JLabel("Supp");
		label_3.setBounds(98, 118, 56, 14);
		label_3.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		spinnerSuppression = new JSpinner();
		spinnerSuppression.setBounds(132, 115, 40, 20);

		JLabel label_5 = new JLabel("Personnel");
		label_5.setBounds(10, 233, 56, 14);
		label_5.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		JLabel label_6 = new JLabel("Moral");
		label_6.setBounds(182, 118, 52, 14);
		label_6.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));

		spinnerMoral = new JSpinner();
		spinnerMoral.setBounds(221, 115, 40, 20);
		
		JButton btnSplitUnit = new JButton("Split Unit");
		btnSplitUnit.setBounds(201, 173, 120, 23);
		btnSplitUnit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// Adds new unit 
				// Splits unit 
				ArrayList<Trooper> individuals = new ArrayList<Trooper>();
				generateSquad squad = new generateSquad("Clone Trooper Phase 1", "Empty");
				individuals = squad.getSquad();
				Unit newUnit = new Unit(textFieldUnit.getText(), 0, 0, individuals, 100, 0, 100, 0, 0, 20, 0, unit.behavior);
				
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
				window.initiativeOrder.add(newUnit);
				
				window.rollInitiativeOrder();
				window.refreshInitiativeOrder();
				
				// Loops through initiative order
				// Finds units that have LOS with this unit 
				// Adds new unit to the spotting units LOS
				
				for(Unit initUnit : window.initiativeOrder) {
					
					if(initUnit.lineOfSight.contains(unit)) {
						initUnit.lineOfSight.add(newUnit);
					}
					
				}
				
				
				// Finds newUnit's company 
				// Adds unit to company 
				for(int i = 0; i < window.companies.size(); i++) {
					
					if(window.companies.get(i).getName().equals(newUnit.company)) {
						window.companies.get(i).updateUnit(unit);
						window.companies.get(i).addUnit(newUnit);
						// Adds companies to setupWindow
						window.confirmCompany(window.companies.get(i), i);
						//f.dispose();
						
					}
					
				}
				
				
				
				
			}
		});
		
		JLabel lblName = new JLabel("New Callsign");
		lblName.setBounds(10, 177, 83, 14);
		lblName.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		
		textFieldUnit = new JTextField();
		textFieldUnit.setBounds(89, 174, 102, 20);
		textFieldUnit.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(594, 11, 80, 23);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Saves unit
				saveUnit(unit, window);

				// Refreshes window 
				window.refreshInitiativeOrder();
				
				//window.hexGrid.refreshDeployedUnits();
				
				// Closes this window 
				f.dispose();
				
			}
		});
		
		
		lblImobalized.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		
		JButton btnNewButton = new JButton("Activate / Deactivate");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(415, 42, 259, 23);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(unit.active) 
					unit.active = false; 
				 else 
					unit.active = true; 

				window.initiativeOrder.remove(unit);

				if(window.initiativeOrder.size() > 0)
					window.openUnit(window.initiativeOrder.get(window.activeUnit), window.activeUnit);
				
				if(unit.individuals.size() == 0) {
					unit.callsign = unit.callsign + "-Empty";
				}
				
				for(Unit otherUnit : window.initiativeOrder) {
					
					if(!otherUnit.lineOfSight.contains(unit))
						continue; 
					
					otherUnit.removeLOS(otherUnit.lineOfSight.indexOf(unit), window);
					
				}
				
				// spinners 
				refreshSpinners();
				gameWindow.refreshInitiativeOrder();
				//gameWindow.refreshUnits();
				
				
				GameWindow.gameWindow.hexGrid.panel.selectedUnit = null; 
				
				for(DeployedUnit deployedUnit : GameWindow.gameWindow.hexGrid.panel.deployedUnits) {
					
					if(deployedUnit.unit.compareTo(window.initiativeOrder.get(window.activeUnit))) {
						
						GameWindow.gameWindow.hexGrid.panel.selectedUnit = deployedUnit; 
						break;
					}
					
				}
				
				
				f.dispose();
				
				HexGrid hexGrid = GameWindow.gameWindow.hexGrid; 
				hexGrid.panel.deployedUnits.remove(getDeployedUnit(unit));
				hexGrid.frame.toFront();
				hexGrid.frame.requestFocus();
				
			}
		});
		
		lblActive = new JLabel("A/D: activated");
		lblActive.setBounds(320, 14, 83, 17);
		lblActive.setFont(new Font("Calibri", Font.BOLD, 13));
		
		JButton btnCloseCombat = new JButton("Close Combat");
		btnCloseCombat.setBounds(415, 173, 259, 23);
		btnCloseCombat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new CloseCombatWindow(unit, window, openUnit);
			}
		});
		
		lblCloseCombat = new JLabel("Close Combat: true");
		lblCloseCombat.setBounds(180, 233, 143, 14);
		lblCloseCombat.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		
		btnNewButton_1 = new JButton("Static Weapons");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new StaticWeaponWindow(unit, gameWindow, openUnit);
			}
		});
		btnNewButton_1.setBounds(415, 145, 259, 23);
		f.getContentPane().setLayout(null);
		f.getContentPane().add(lblUnitCallsign);
		f.getContentPane().add(lblActive);
		f.getContentPane().add(button);
		f.getContentPane().add(lblActions);
		f.getContentPane().add(label_1);
		f.getContentPane().add(spinnerOrganization);
		f.getContentPane().add(label_2);
		f.getContentPane().add(spinnerCommandValue);
		f.getContentPane().add(btnNewButton_1);
		f.getContentPane().add(btnCloseCombat);
		f.getContentPane().add(label_3);
		f.getContentPane().add(spinnerSuppression);
		f.getContentPane().add(label_6);
		f.getContentPane().add(spinnerMoral);
		f.getContentPane().add(lblCloseCombat);
		f.getContentPane().add(btnSplitUnit);
		f.getContentPane().add(textFieldUnit);
		f.getContentPane().add(lblName);
		f.getContentPane().add(label_5);
		f.getContentPane().add(scrollPane);
		f.getContentPane().add(btnMove);
		f.getContentPane().add(comboBoxConcealment);
		f.getContentPane().add(comboBoxBehavior);
		f.getContentPane().add(comboBoxSpeed);
		f.getContentPane().add(btnSave);
		f.getContentPane().add(spinnerX);
		f.getContentPane().add(spinnerY);
		f.getContentPane().add(lblLocation);
		f.getContentPane().add(lblImobalized);
		f.getContentPane().add(btnNewButton);
		
		lblEffectiveBurden = new JLabel("Close Combat: true");
		lblEffectiveBurden.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblEffectiveBurden.setBounds(155, 149, 143, 14);
		f.getContentPane().add(lblEffectiveBurden);
		
		JButton btnLineOfSight = new JButton("Line of Sight");
		btnLineOfSight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				new LineOfSightWindow(unit, gameWindow);
				
			}
		});
		btnLineOfSight.setBounds(415, 118, 259, 23);
		f.getContentPane().add(btnLineOfSight);
		
		lblNewLabel = new JLabel("Concealment:");
		lblNewLabel.setBounds(10, 35, 106, 14);
		f.getContentPane().add(lblNewLabel);
		
		lblContact = new JLabel("Contact:");
		lblContact.setBounds(166, 35, 85, 14);
		f.getContentPane().add(lblContact);
		
		lblSpeed = new JLabel("Speed:");
		lblSpeed.setBounds(283, 35, 72, 14);
		f.getContentPane().add(lblSpeed);
		
		lblTroopersInCover = new JLabel("Troopers In Cover: ");
		lblTroopersInCover.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblTroopersInCover.setBounds(10, 149, 152, 14);
		f.getContentPane().add(lblTroopersInCover);
		
		lblTimeSinceContact = new JLabel("Time Since Contact: ");
		lblTimeSinceContact.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblTimeSinceContact.setBounds(281, 149, 143, 14);
		f.getContentPane().add(lblTimeSinceContact);
		
		btnBulkOperations = new JButton("Bulk Operations");
		btnBulkOperations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBulkOperations.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(gameWindow.cqbWindowOpen)
					return;
				
				new BulkWindow(unit, gameWindow, openUnit);
				
			}
		});
		btnBulkOperations.setBounds(415, 93, 259, 23);
		f.getContentPane().add(btnBulkOperations);
		
		btnNewButton_2 = new JButton("Fire Missions");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new ArtilleryWindow(unit, window);
				
			}
		});
		btnNewButton_2.setBounds(415, 68, 259, 23);
		f.getContentPane().add(btnNewButton_2);
		
		comboBoxUnitType = new JComboBox();
		comboBoxUnitType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
			}
		});
		comboBoxUnitType.setBounds(89, 202, 102, 20);
		f.getContentPane().add(comboBoxUnitType);
		
		JLabel lblUnitType = new JLabel("Unit Type");
		lblUnitType.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblUnitType.setBounds(10, 208, 83, 14);
		f.getContentPane().add(lblUnitType);
		
		btnEmbark = new JButton("Embark");
		btnEmbark.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Hex hex = unit.getHex(gameWindow); 
						
				if(hex == null)
					return; 
				
				new EmbarkWindow(gameWindow, hex, unit);
				
				
				
				
				
			}
		});
		btnEmbark.setBounds(415, 201, 259, 23);
		f.getContentPane().add(btnEmbark);
		
		lblFatigue = new JLabel("Average Fatigue Points:");
		lblFatigue.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblFatigue.setBounds(333, 233, 339, 14);
		f.getContentPane().add(lblFatigue);
		
		JButton btnWait = new JButton("Wait");
		btnWait.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
//System.out.println("Next Unit");
				
				if(gameWindow.cqbWindowOpen) {
					return; 
				}
				
				if(openTrooper != null)
					openTrooper.f.dispose();
				
				if(window.activeUnit != window.initiativeOrder.indexOf(unit)) {
					window.conflictLog.addNewLine("Selected Unit is not the active unit");
					return; 
				}
				
				new SwingWorker() {

					// Don't Update GUI
					@Override
					protected Object doInBackground() throws Exception {
						try {
							//System.out.println("Pass01 - Entered Do In Background");
							
							// Clears close combat ranges 
							clearCloseCombatRanges();
							
							
							// Saves unit
							safeSaveUnit(unit);

							// Refreshes window 
							//System.out.println("UI thread name is:"+Thread.currentThread().getName());
							//System.out.println("Pass02 - Entered Safe Next Active Unit");
							
							window.safeWaitActiveUnit();

							if(window.hexGrid != null) {
								window.hexGrid.panel.selectedUnit = window.hexGrid.panel.deployedUnits.get(window.activeUnit);
							}
							
							window.initiativeOrder.get(window.activeUnit).setFiredWeapons();
							
					      } catch(Exception e) {
					         System.out.println("toString(): " + e.toString());
					         System.out.println("getMessage(): " + e.getMessage());
					         System.out.println("StackTrace: ");
					         e.printStackTrace();
					      }
						
						
						
						//System.out.println("Pass1 - Finished Do In Background");
						return null;
					}
					
					

					// Updates GUI 
					@Override 
					protected void done() {
						//System.out.println("Pass2");
						
						// Overwrites unit
						//window.initiativeOrder.set(activeUnitOpenUnit, unit);
						// Selectes next unit in order
											
						DefaultListModel initiativeOrderList = new DefaultListModel();

						for (int i = 0; i < window.initiativeOrder.size(); i++) {
							
							// Loops through troopers in the unit
							
							for (int x = 0; x < window.initiativeOrder.get(i).getSize(); x++) {
								Trooper currentTrooper = window.initiativeOrder.get(i).getTroopers().get(x);
								currentTrooper.number = x + 1; 
							}
							
							initiativeOrderList.addElement(i+": "+window.initiativeOrder.get(i));

						}

						window.listIniativeOrder.setModel(initiativeOrderList);
						
						//System.out.println("Next Active Unit");
						
						window.guiUpdateNextActiveUnit();
						
						window.conflictLog.addQueuedText();
						
						// Check for random event 
						window.randomEventCheck();
						
						//System.out.println("Pass Dispose 1 Active Unit: "+window.activeUnit);
						window.currentlyOpenUnit.f.dispose();
						window.openUnit(window.initiativeOrder.get(window.activeUnit), window.activeUnit);
						
						//System.out.println("Pass Dispose 2");
						
					}
					
					
				}.execute();
					
				f.dispose();
				
				
				
			}
		});
		btnWait.setBounds(505, 11, 80, 23);
		f.getContentPane().add(btnWait);
		

		// Sets spinners, must be executed before saveUnit();
		refreshSpinners();
		refreshIndividuals(); 
		
		// Sets unit comboBoxes
		String speed = unit.speed;
		if (speed.equals("None")) {
			comboBoxSpeed.setSelectedIndex(0);
		} else if (speed.equals("Walk")) {
			comboBoxSpeed.setSelectedIndex(1);
		} else if (speed.equals("Crawl")) {
			comboBoxSpeed.setSelectedIndex(2);
		} else if (speed.equals("Rush")) {
			comboBoxSpeed.setSelectedIndex(3);
		}

		String concealment = unit.concealment;
		if (concealment.equals("No Concealment")) {
			comboBoxConcealment.setSelectedIndex(0);
		} else if (concealment.equals("Level 1")) {
			comboBoxConcealment.setSelectedIndex(1);
		} else if (concealment.equals("Level 2")) {
			comboBoxConcealment.setSelectedIndex(2);
		} else if (concealment.equals("Level 3")) {
			comboBoxConcealment.setSelectedIndex(3);
		} else if (concealment.equals("Level 4")) {
			comboBoxConcealment.setSelectedIndex(4);
		} else if (concealment.equals("Level 5")) {
			comboBoxConcealment.setSelectedIndex(5);
		}
		
		int count = 0; 
		for(UnitType unitType : UnitType.values()) {
			
			comboBoxUnitType.addItem(unitType.toString());
			
			if(unitType == unit.unitType)
				comboBoxUnitType.setSelectedIndex(count);
			
			count++; 
		}
		
		// Sets spinners
		refreshSpinners();
		refreshIndividuals(); 

		unit.calculateBurden();
		
		// Opens first individual
		activeTrooper = 0; 
		// Currently set not to open next 
		//openNext(true); 
				
		gameWindow.closeQuartersBattleCheck(openUnit);
						
		//System.out.println(unit.callsign+ " Behavior: "+unit.behavior);
		String behavior = unit.behavior;
		
		if(behavior.equals("No Contact")) {
			comboBoxBehavior.setSelectedIndex(0);
		} else if(behavior.equals("Recent Contact")) {
			comboBoxBehavior.setSelectedIndex(1);
		} else {
			comboBoxBehavior.setSelectedIndex(2);
		} 
		
	}
	
	public DeployedUnit getDeployedUnit(Unit unit) {
		
		
		for(DeployedUnit deployedUnit : GameWindow.gameWindow.hexGrid.panel.deployedUnits) {
			
			if(deployedUnit.unit.compareTo(unit)) {
				return deployedUnit; 
			}
			
		}
		
		return null; 
	}
	

	// Refresh troopers
	public void refreshIndividuals() {
		if (troopers == null) {
			return;
		} else if (troopers.size() == 0) {
			return;
		}

		DefaultListModel individualList = new DefaultListModel();

		int imobalized = 0; 
		
		// Loops through troopers in the unit
		
		for (int i = 0; i < troopers.size(); i++) {
			//System.out.println("Pass Refresh Individuals Loop 1");
			Trooper currentTrooper = troopers.get(i);
			//System.out.println("Pass Refresh Individuals Loop Trooper: "+currentTrooper.number+" "+currentTrooper.name+" alive: "+currentTrooper.alive);
			currentTrooper.number = i + 1; 
			// Only adds alive troopers to the list of individuals in the squad 
			if(currentTrooper.alive) {
				//System.out.println("Pass Refresh Individuals Loop 2");
				if(unit.equipped(currentTrooper)) {
					//System.out.println("Pass Refresh Individuals Loop -- equipped");
					individualList.addElement(currentTrooper.toStringImprovedEquipped(gameWindow.game));
				} else {
					//System.out.println("Pass Refresh Individuals Loop 4 - added");
					individualList.addElement(currentTrooper.toStringImproved(gameWindow.game));
				}
				
				if( currentTrooper.disabledLegs > 0 || !currentTrooper.conscious) {
					imobalized += 1; 
				}
			}

		}
		//System.out.println("Pass Refresh Individuals Loop 6");
		lblImobalized.setText("Imobalized: "+imobalized);
		listIndividuals.setModel(individualList);
		
		unit.calculateBurden(); 
		//System.out.println("Unit: "+unit.callsign);
		//System.out.println("effectiveBurden: "+unit.effectiveBurden);
		//System.out.println("Pass Refresh Individuals Loop 7");
	}
	
	
	// Overwrites a trooper
	// Used in save changes edit individual
	// Set unit
	public void setTrooper(Trooper trooper, int index) {
		troopers.set(index, trooper);
	}

	
	// Delete Individual
	public void deleteIndividual(int index) {
		troopers.remove(index);
	}

	
	// Saves changes made to unit without updating the gui 
	public void safeSaveUnit(Unit unit) {
		
		//System.out.println("Save unit moral: "+unit.moral);
		
		// Saves changes to unit, walk speed, concealment, ect
		unit.concealment = comboBoxConcealment.getSelectedItem().toString();
		unit.speed = comboBoxSpeed.getSelectedItem().toString();
		unit.behavior = comboBoxBehavior.getSelectedItem().toString();

		//System.out.println(unit.callsign+ " Safe Set Behavior: "+unit.behavior);
		
		// Values from spinners
		//unit.cohesion = (int) spinnerCohesion.getValue();

		unit.organization = (int) spinnerOrganization.getValue();
		unit.suppression = (int) spinnerSuppression.getValue();
		unit.moral = (int) spinnerMoral.getValue();
		
		//System.out.println("Save unit moral 1: "+unit.moral);

		//unit.radius = (int) spinnerRadius.getValue();
		unit.commandValue = (int) spinnerCommandValue.getValue();
		//unit.fatiuge = (int) spinnerFatiuge.getValue();

		//unit.floor = (int) spinnerFloors.getValue();
		
		/*if(chckbxInBuilding.isSelected()) {
			unit.inBuilding = true; 
		} else {
			unit.inBuilding = false; 
		}*/
		
	}
	
	// Saves changes made to unit
	public void saveUnit(Unit unit, GameWindow window) {
		
		//System.out.println("Save unit moral: "+unit.moral);
		
		// Saves changes to unit, walk speed, concealment, ect
		unit.concealment = comboBoxConcealment.getSelectedItem().toString();
		unit.speed = comboBoxSpeed.getSelectedItem().toString();
		unit.behavior = comboBoxBehavior.getSelectedItem().toString();

		//System.out.println(unit.callsign+" Set Behavior: "+unit.behavior);
		
		// Values from spinners
		//unit.cohesion = (int) spinnerCohesion.getValue();

		unit.organization = (int) spinnerOrganization.getValue();
		unit.suppression = (int) spinnerSuppression.getValue();
		unit.moral = (int) spinnerMoral.getValue();
		
		//System.out.println("Save unit moral 1: "+unit.moral);

		//unit.radius = (int) spinnerRadius.getValue();
		unit.commandValue = (int) spinnerCommandValue.getValue();
		//unit.fatiuge = (int) spinnerFatiuge.getValue();

		//unit.floor = (int) spinnerFloors.getValue();
		
		/*if(chckbxInBuilding.isSelected()) {
			unit.inBuilding = true; 
		} else {
			unit.inBuilding = false; 
		}*/
		
		//System.out.println("Unit Type changed");
		
		int count = 0; 
		for(UnitType unitType : UnitType.values()) {
			
			if(count == comboBoxUnitType.getSelectedIndex()) {
				//System.out.println("Unit Type Actually Changed");
				unit.unitType = unitType; 
				break; 
			}
			
			count++;
		}
		
		window.refreshInitiativeOrder();
		
		// Overwrites unit
		//window.initiativeOrder.set(activeUnitOpenUnit, unit);

	}
	
	// sets / refreshes spinners 
	public void refreshSpinners() {
		spinnerX.setValue(unit.X);
		spinnerY.setValue(unit.Y);
		spinnerOrganization.setValue((int) unit.organization);
		spinnerSuppression.setValue((int) unit.suppression);
		spinnerMoral.setValue((int) unit.moral);
		spinnerCommandValue.setValue((int) unit.commandValue);
		
		if(unit.active) {
			lblActive.setText("A/D: A");
		} else {
			lblActive.setText("A/D: D");
		}
		
		if(unit.closeCombat) {
			lblCloseCombat.setText("Close Combat: true");
		} else {
			lblCloseCombat.setText("Close Combat: false");
		}
		
		/*if(unit.inBuilding) {
			chckbxInBuilding.setSelected(true);
		} else {
			chckbxInBuilding.setSelected(false);
		}*/
		
		lblTroopersInCover.setText("Troopers In Cover: "+unit.individualsInCover+"/"+unit.getSize());
		lblEffectiveBurden.setText("Effective Burden: "+unit.effectiveBurden);
		lblTimeSinceContact.setText("Time Since Contact: "+unit.timeSinceContact);
		
		
		double fatiguePointsTotal = 0; 
		
		for(Trooper trooper : unit.individuals) {
			
			fatiguePointsTotal += trooper.fatigueSystem.fatiguePoints.get();
			
		}
		
		lblFatigue.setText("Average Fatigue Points: "+(fatiguePointsTotal/unit.individuals.size()));
				
		
	}
	
	// Opens next trooper
	public OpenTrooper openNext(boolean open) {
		
		//System.out.println("Open Next Pass");
		
		if(troopers == null || troopers.size() < 1) {
			return null;
		}
		
		// Checks if all troopers are dead or unconscious
		boolean allDead = true;
		for(int i = 0; i < troopers.size(); i++) {
			if(troopers.get(i).alive && troopers.get(i).conscious) {
				allDead = false;
			}
		}
		if(allDead) {
			return null;
		}
		
		
		int exhaustedCount = 0; 
		while(activeTrooper != troopers.size()) {
			if(exhaustedCount == troopers.size())
				break; 
			
			boolean exhausted = false; 
			
			Trooper trooper = troopers.get(activeTrooper);
			
			// If the trooper is not alive or is unconscious, or eqiuped to a static weapon, opens next trooper 
			if(!trooper.alive || !trooper.conscious || unit.equipped(trooper)) {
				exhausted = true;
				exhaustedCount++; 
			}
			
			
			
			if (gameWindow.game.getPhase() == 1) {
				if(trooper.spentPhase1 >= gameWindow.game.getCurrentAction() || trooper.spentPhase1 >= trooper.P1) {
					exhausted = true;
					exhaustedCount++; 
				}
			} else {
				if(trooper.spentPhase2 >= gameWindow.game.getCurrentAction() || trooper.spentPhase2 >= trooper.P2) {
					exhausted = true; 
					exhaustedCount++;
				}
			}
			
			if(!exhausted && open && !trooper.HD) {
				this.openTrooper = new OpenTrooper(trooper, unit, openUnit, activeTrooper, true);
				return this.openTrooper;
			}
			
			
			if(activeTrooper == troopers.size() - 1) {
				activeTrooper = 0; 
			} else {
				activeTrooper++; 
			}
			
			
		}
		
		
		
		return null;
		
	}
	
	public void clearCloseCombatRanges() {
		
		for(Trooper trooper : unit.individuals) {
			
			trooper.pcRanges.clear();
			
			for(Unit otherUnit : gameWindow.initiativeOrder) {
				
				for(Trooper otherTrooper : otherUnit.individuals) {
					
					if(otherTrooper.pcRanges.containsKey(trooper)) 
						otherTrooper.pcRanges.remove(trooper);
					
				}
				
			}
			
		}
		
		
	}
}
