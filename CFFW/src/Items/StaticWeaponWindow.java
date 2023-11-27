package Items;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Actions.ReactionToFireWindow;
import Actions.TargetedFire;
import Conflict.GameWindow;
import Conflict.InjuryLog;
import Conflict.OpenUnit;
import Injuries.ResolveHits;
import Shoot.Shoot;
import Trooper.Trooper;
import Trooper.generateSquad;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.PCUtility;
import UtilityClasses.ShootUtility;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class StaticWeaponWindow {
	private JList listStay;
	private JList listFlee;

	public ArrayList<Trooper> flee = new ArrayList<>();
	public ArrayList<Trooper> stay = new ArrayList<>();
	private JTextField textFieldCallsign;
	private JList listAvailableStatics;
	private JList listEquipedStatics;
	private JList listEquipedIndividuals;
	private JSpinner spinnerAmmunitionLoaded;

	public ArrayList<Weapons> availableStaticWeapons = new ArrayList<>();
	public int selectedWeaponIndex = -1;
	public Weapons staticWeapon;
	public ArrayList<Trooper> unequipedTroopers = new ArrayList<>();

	private String path = System.getProperty("user.dir") + "\\";
	
	public ArrayList<Trooper> targetTroopers = new ArrayList<>();
	public Trooper targetTrooper;
	public Trooper gunner;
	public Unit targetUnit;
	public Unit trooperUnit;

	public GameWindow window;
	public ArrayList<Shoot> shots = new ArrayList<>();
	public int loadTime;
	public int assembleTime;
	private JList listIndividuals;
	private JLabel lblSelectedStatic;
	private JLabel lblStaticWeapon;
	private JComboBox comboBoxTargets;
	private JSpinner spinnerPercentBonus;
	private JComboBox comboBoxSuppressiveFireTargets;
	private JLabel lblProgress;
	private JLabel lblAssembledissembleCost;
	private JLabel lblAssembled;
	private JLabel lblLoadTime;
	private JLabel lblLoadProgress;
	private JLabel lblLoaded;

	public Unit unit;

	public TargetedFire targetedFire = null;
	public TargetedFire tempTF = null;
	public int TFCA;
	public int spentCA = 0;

	public boolean firedWeapons = false;
	public boolean possibleShots = true;
	public ReactionToFireWindow reaction = null;
	private JComboBox comboBoxAimTime;
	private JSpinner spinnerEALBonus;
	private JCheckBox chckbxFullAuto;
	private JCheckBox chckbxSustainedFullAuto;
	private JCheckBox chckbxMultipleTargets;
	private JCheckBox chckbxFreeAction;
	private JSpinner spinnerConsecutiveEalBonus;
	private JLabel lblPossibleShots;
	private JLabel lblAimTime;
	private JLabel lblTN;
	private JLabel lblCombatActions;
	private JLabel lblTfSpentCa;
	private JComboBox comboBoxTargetZone;
	private JLabel lblAmmunition;
	private JComboBox comboBoxPcAmmo;
	private JCheckBox chckbxHoming;
	private JCheckBox chckbxSingleShot;
	private JCheckBox chckbxMannualSup;
	private JSpinner spinnerSuppressiveRof;

	public StaticWeaponWindow(Unit unit, GameWindow window, OpenUnit openUnit) {
		this.unit = unit;
		this.window = window;
		this.trooperUnit = openUnit.unit;
		final JFrame f = new JFrame("Static Weapons");
		if(shots.size() != unit.staticWeapons.size()) {
			for(int i = 0; i < unit.staticWeapons.size() - shots.size(); i++)
			shots.add(null);
		}
		
		f.setSize(1097, 813);
		f.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(659, 46, 201, 718);
		f.getContentPane().add(scrollPane);

		listEquipedStatics = new JList();
		listEquipedStatics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (unit.staticWeapons.size() > 0) {
					selectedWeaponIndex = listEquipedStatics.getSelectedIndex();

					Weapons staticWep = unit.staticWeapons.get(listEquipedStatics.getSelectedIndex());
					staticWeapon = staticWep;
					
					lblStaticWeapon.setText(
							Integer.toString(listEquipedStatics.getSelectedIndex() + 1) + " " + staticWep.name);
					//System.out.println("Static Weapon Name: "+staticWep.name);
					//System.out.println("BC0: "+staticWeapon.pcAmmoTypes.get(0).bc.get(0));
					if(staticWeapon.pcAmmoTypes.size() > 0) {
						
						comboBoxPcAmmo.removeAllItems();

						for(PCAmmo pcAmmo : staticWeapon.pcAmmoTypes) {
							comboBoxPcAmmo.addItem(pcAmmo.name);
						}
						
					}
					
					setFields(unit);
				}

				//System.out.println("Selected Index: " + listEquipedStatics.getSelectedIndex());
				setGunner();
				comboBoxTargets.setSelectedIndex(0);
				comboBoxSuppressiveFireTargets.setSelectedIndex(0);
				guiUpdates();
				
			}
		});
		scrollPane.setViewportView(listEquipedStatics);

		// JList<? extends E> list = new JList();
		// scrollPane.setViewportView(list);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(870, 46, 201, 718);
		f.getContentPane().add(scrollPane_1);

		listAvailableStatics = new JList();
		listAvailableStatics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				//System.out.println("Static Index: " + listAvailableStatics.getSelectedIndex());

				int index = listAvailableStatics.getSelectedIndex();

				Weapons clickedWeapon = availableStaticWeapons.get(index);

				unit.addStaticWeapon(clickedWeapon);

				setFields(unit);

				listEquipedStatics.setSelectedIndex(0);

				onClickEquipedStatics(unit);

				listEquipedStatics.setSelectedIndex(0);
			}
		});
		scrollPane_1.setViewportView(listAvailableStatics);

		// JList<? extends E> list_1 = new JList();
		// scrollPane_1.setViewportView(list_1);

		JLabel lblEquipedStatics = new JLabel("Equiped Statics");
		lblEquipedStatics.setFont(new Font("Calibri", Font.BOLD, 13));
		lblEquipedStatics.setBounds(659, 21, 102, 14);
		f.getContentPane().add(lblEquipedStatics);

		JLabel lblAvailableStatics = new JLabel("Available Statics");
		lblAvailableStatics.setFont(new Font("Calibri", Font.BOLD, 13));
		lblAvailableStatics.setBounds(870, 21, 201, 14);
		f.getContentPane().add(lblAvailableStatics);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(239, 46, 201, 718);
		f.getContentPane().add(scrollPane_2);

		listEquipedIndividuals = new JList();
		listEquipedIndividuals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = listEquipedStatics.getSelectedIndex();

				if(index < 0)
					return;
				
				if (selectedWeaponIndex > -1) {

					unit.staticWeapons.get(selectedWeaponIndex).equipedTroopers
							.remove(listEquipedIndividuals.getSelectedIndex());
					PCUtility.setSlUnequip(unequipedTroopers.get(listEquipedIndividuals.getSelectedIndex()));
					setFields(unit);

				}

				listEquipedStatics.setSelectedIndex(index);
				
				setGunner();
				
			}
		});
		scrollPane_2.setViewportView(listEquipedIndividuals);

		JLabel lblEquipedIndividuals = new JLabel("Equiped Individuals:");
		lblEquipedIndividuals.setFont(new Font("Calibri", Font.BOLD, 13));
		lblEquipedIndividuals.setBounds(239, 21, 175, 14);
		f.getContentPane().add(lblEquipedIndividuals);

		JLabel lblSustainedFireTargets = new JLabel("Sustained Fire Targets:");
		lblSustainedFireTargets.setFont(new Font("Calibri", Font.BOLD, 13));
		lblSustainedFireTargets.setBounds(10, 73, 175, 14);
		f.getContentPane().add(lblSustainedFireTargets);

		JLabel lblSuppressiveFireTargets = new JLabel("Suppressive Fire Targets:");
		lblSuppressiveFireTargets.setFont(new Font("Calibri", Font.BOLD, 13));
		lblSuppressiveFireTargets.setBounds(10, 384, 175, 14);
		f.getContentPane().add(lblSuppressiveFireTargets);

		JButton btnSaveAndClose = new JButton("Update Ammo");
		btnSaveAndClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSaveAndClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				saveChanges(unit, f);

			}
		});
		btnSaveAndClose.setBounds(86, 607, 130, 23);
		f.getContentPane().add(btnSaveAndClose);

		comboBoxTargets = new JComboBox();
		comboBoxTargets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxTargets.getSelectedIndex() <= 0)
					return;

				if(listEquipedStatics.getSelectedIndex() < 0)
					return;
				
				// System.out.println("Targets Size: "+targetTroopers.size()+", Target Name:
				// "+findTarget().number+" "+findTarget().name);

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						
						Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
						
						// System.out.println("Target Changed Shots1");
						// PCShots();
						// System.out.println("Target Changed Shots2");

						try {
							String wepName = staticWeapon.name;
							int ammoIndex = -1;
							if (staticWeapon.pcAmmoTypes.size() > 0) {
								wepName = staticWeapon.name;
								ammoIndex = comboBoxPcAmmo.getSelectedIndex() > 0 ? comboBoxPcAmmo.getSelectedIndex() : 0;
								if (ammoIndex < 0) {
									GameWindow.gameWindow.conflictLog.addNewLine("Select valid ammo");
									return null;
								}
							}

							setGunner();
							shoot = ShootUtility.setTarget(unit,
									targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1)
											.returnTrooperUnit(GameWindow.gameWindow),
									shoot, gunner, targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1), wepName,
									ammoIndex);
							shoot.wep.assembled = unit.staticWeapons.get(selectedWeaponIndex).assembled;
							shoot.recalc();
							
							if (comboBoxAimTime.getSelectedIndex() == 0 && shoot != null)
								shoot.autoAim();

							if (comboBoxTargetZone.getSelectedIndex() > 0) {
								setCalledShotBounds();
							}
							
							shots.set(listEquipedStatics.getSelectedIndex(), shoot);
							
						} catch (Exception e) {
							e.printStackTrace();
						}

						return null;
					}

					@Override
					protected void done() {
						GameWindow.gameWindow.conflictLog.addQueuedText();
						guiUpdates();

					}

				};

				worker.execute();
				
			}
		});
		comboBoxTargets.setBounds(10, 97, 219, 23);
		f.getContentPane().add(comboBoxTargets);

		comboBoxSuppressiveFireTargets = new JComboBox();
		comboBoxSuppressiveFireTargets.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBoxTargets.getSelectedIndex() != 0 || comboBoxSuppressiveFireTargets.getSelectedIndex() <= 0)
				{
					System.out.println("Suppress return 1");
					return;
				}
				if(listEquipedStatics.getSelectedIndex() < 0) {
					System.out.println("Suppress return 2");
					return;
				}
				
				
				
				
				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						System.out.println("Do in background");
						
						try {
							Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
							
							String wepName = staticWeapon.name;
							int ammoIndex = -1;
							if (staticWeapon.pcAmmoTypes.size() > 0) {
								wepName = staticWeapon.name;
								ammoIndex = comboBoxPcAmmo.getSelectedIndex() > 0 ? comboBoxPcAmmo.getSelectedIndex() : 0;
								if (ammoIndex < 0) {
									GameWindow.gameWindow.conflictLog.addNewLine("Select valid ammo");
									System.out.println("Suppress return 3 select valid ammo");
									return null;
								}
							}

							setGunner();
							shoot = ShootUtility.setTargetUnit(unit,
									unit.lineOfSight.get(comboBoxSuppressiveFireTargets.getSelectedIndex() - 1), shoot, gunner,
									wepName, ammoIndex);
							shoot.wep.assembled = unit.staticWeapons.get(selectedWeaponIndex).assembled;
							shoot.recalc();
							
							if (comboBoxAimTime.getSelectedIndex() == 0 && shoot != null)
								shoot.autoAim();

							if (shoot == null)
								System.out.println("Shoot is null");

							shots.set(listEquipedStatics.getSelectedIndex(), shoot);
							System.out.println("Set shots");
						} catch(Exception e2) {
							e2.printStackTrace();
						}
						
						
						
						return null;
					}

					@Override
					protected void done() {
						GameWindow.gameWindow.conflictLog.addQueuedText();
						guiUpdates();

					}

				};

				worker.execute();
			}
		});
		comboBoxSuppressiveFireTargets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		comboBoxSuppressiveFireTargets.setBounds(10, 409, 219, 23);
		f.getContentPane().add(comboBoxSuppressiveFireTargets);

		JButton btnFire = new JButton("Fire");
		btnFire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnFire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(listEquipedStatics.getSelectedIndex() < 0)
					return;
				
				Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
				
				if (shoot == null)
					return;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						var shots = 0;
						
						try {
							System.out.println("Shoot");

							if (comboBoxSuppressiveFireTargets.getSelectedIndex() > 0){
								var manSup = (int) spinnerSuppressiveRof.getValue();
								shots = chckbxMannualSup.isSelected() && manSup <= 
										shoot.wep.suppressiveROF  ? manSup : shoot.wep.suppressiveROF;
								shoot.suppressiveFire(
										shots);
							}
							else if (chckbxFullAuto.isSelected()) {
								shoot.burst();
								shots = shoot.wep.fullAutoROF;
							}
							else {
								
								if(shoot.wep.launcherHomingInfantry)
									shoot.shot(chckbxHoming.isSelected());
								else 
									shoot.shot(false);
								
								shots = 1;
								if(chckbxSingleShot.isSelected()) {
									var freeSupp =shoot.wep.suppressiveROF / 2 + DiceRoller.roll(1, 3);
									shots = 1 + freeSupp; 
									shoot.suppressiveFireFree(
											freeSupp);
									
								}
							}
							
							GameWindow.gameWindow.conflictLog.addNewLineToQueue("Results: " + shoot.shotResults);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						unit.staticWeapons.get(listEquipedStatics.getSelectedIndex()).ammoLoaded -= shots;

						return null;
					}

					@Override
					protected void done() {
						
						// fire(window, unit, index, f, false);
						if (!chckbxFreeAction.isSelected()
								&& shoot.spentCombatActions >= shoot.shooter.combatActions) {
							spendAction();
						}

						GameWindow.gameWindow.conflictLog.addQueuedText();
						guiUpdates();
						InjuryLog.InjuryLog.printResultsToLog();
						if(shoot.target != null && (!shoot.target.alive || !shoot.target.conscious || shoot.target.HD)) {
							refreshSustainedFireTargets(trooperUnit);
						}
						
					}

				};

				worker.execute();
			}
		});
		btnFire.setBounds(10, 350, 102, 23);
		f.getContentPane().add(btnFire);

		JLabel lblAssembledissemble = new JLabel("Assemble/Dissemble:");
		lblAssembledissemble.setFont(new Font("Calibri", Font.BOLD, 13));
		lblAssembledissemble.setBounds(10, 675, 175, 14);
		f.getContentPane().add(lblAssembledissemble);

		lblAmmunition = new JLabel("Ammunition:");
		lblAmmunition.setFont(new Font("Calibri", Font.BOLD, 13));
		lblAmmunition.setBounds(10, 555, 175, 14);
		f.getContentPane().add(lblAmmunition);

		JLabel lblPercentBonus = new JLabel("Percent Bonus:");
		lblPercentBonus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPercentBonus.setBounds(141, 269, 76, 14);
		f.getContentPane().add(lblPercentBonus);

		spinnerPercentBonus = new JSpinner();
		spinnerPercentBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerPercentBonus.setBounds(141, 283, 43, 22);
		f.getContentPane().add(spinnerPercentBonus);

		lblLoaded = new JLabel("Loaded:");
		lblLoaded.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLoaded.setBounds(10, 580, 72, 14);
		f.getContentPane().add(lblLoaded);

		spinnerAmmunitionLoaded = new JSpinner();
		spinnerAmmunitionLoaded.setBounds(10, 605, 43, 22);
		f.getContentPane().add(spinnerAmmunitionLoaded);

		lblAssembled = new JLabel("Assembled:");
		lblAssembled.setBounds(20, 700, 209, 14);
		f.getContentPane().add(lblAssembled);

		lblAssembledissembleCost = new JLabel("Assemble/Dissemble Cost:");
		lblAssembledissembleCost.setBounds(20, 725, 209, 14);
		f.getContentPane().add(lblAssembledissembleCost);

		JButton btnAssembledissemble = new JButton("A/D");
		btnAssembledissemble.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
					return;
				}

				Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

				// Performs assemble/dissemble action
				if (assembleTime == 0) {
					if (staticWeapon.assembled) {
						staticWeapon.assembled = false;
					} else {
						staticWeapon.assembled = true;
					}

				} else {
					if (staticWeapon.assembleProgress != assembleTime) {
						staticWeapon.assembleProgress++;
					}

					if (staticWeapon.assembleProgress == assembleTime) {
						staticWeapon.assembleProgress = 0;
						if (staticWeapon.assembled) {
							staticWeapon.assembled = false;
						} else {
							staticWeapon.assembled = true;
						}
					}

					// Spends one Action Point for equipped individuals
					// Checks if AP Full
					// Increases spent AP
					for (Trooper trooper : staticWeapon.equipedTroopers) {
						if (window.game.getPhase() == 1) {
							if (trooper.spentPhase1 != window.game.getCurrentAction()) {

								if (trooper.spentPhase1 + 1 <= trooper.P1)
									trooper.spentPhase1 += 1;
							}
						} else {
							if (trooper.spentPhase2 != window.game.getCurrentAction()) {
								if (trooper.spentPhase2 + 1 <= trooper.P2)
									trooper.spentPhase2 += 1;
							}
						}
					}
				}

				openUnit.refreshIndividuals();

				setFields(unit);

			}
		});
		btnAssembledissemble.setBounds(142, 669, 87, 23);
		f.getContentPane().add(btnAssembledissemble);

		lblProgress = new JLabel("Progress:");
		lblProgress.setBounds(20, 750, 209, 14);
		f.getContentPane().add(lblProgress);

		JButton btnNewButton = new JButton("Remove");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (selectedWeaponIndex > -1) {

					unit.staticWeapons.remove(selectedWeaponIndex);
					listEquipedStatics.setSelectedIndex(-1);
					selectedWeaponIndex = -1;

					setFields(unit);

				}

			}
		});
		btnNewButton.setBounds(771, 17, 89, 23);
		f.getContentPane().add(btnNewButton);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(448, 46, 201, 718);
		f.getContentPane().add(scrollPane_3);

		listIndividuals = new JList();
		listIndividuals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int index = listEquipedStatics.getSelectedIndex();

				if(index < 0)
					return;
				
				// Gets currently selected unequiped individual and adds them to currently
				// selected weapon
				if (listIndividuals.getSelectedIndex() > -1 && selectedWeaponIndex > -1) {
					unit.staticWeapons.get(selectedWeaponIndex).equipedTroopers
							.add(unequipedTroopers.get(listIndividuals.getSelectedIndex()));
					PCUtility.setSlEquip(unequipedTroopers.get(listIndividuals.getSelectedIndex()));
					unequipedTroopers.remove(listIndividuals.getSelectedIndex());
				}

				setFields(unit);

				openUnit.refreshIndividuals();

				listEquipedStatics.setSelectedIndex(index);
				//refreshTroopers(unit);
				//refreshEquipedIndividuals(unit);
				setGunner();

			}
		});
		scrollPane_3.setViewportView(listIndividuals);

		JLabel lblIndividuals = new JLabel("Individuals:");
		lblIndividuals.setFont(new Font("Calibri", Font.BOLD, 13));
		lblIndividuals.setBounds(448, 21, 175, 14);
		f.getContentPane().add(lblIndividuals);

		lblSelectedStatic = new JLabel("Selected Static");
		lblSelectedStatic.setFont(new Font("Calibri", Font.BOLD, 13));
		lblSelectedStatic.setBounds(10, 21, 175, 14);
		f.getContentPane().add(lblSelectedStatic);

		lblStaticWeapon = new JLabel("Selected Static");
		lblStaticWeapon.setFont(new Font("Calibri", Font.BOLD, 13));
		lblStaticWeapon.setBounds(23, 48, 175, 14);
		f.getContentPane().add(lblStaticWeapon);

		lblLoadTime = new JLabel("Load Time:");
		lblLoadTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLoadTime.setBounds(86, 569, 143, 14);
		f.getContentPane().add(lblLoadTime);

		lblLoadProgress = new JLabel("Load Progress:");
		lblLoadProgress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLoadProgress.setBounds(86, 582, 143, 14);
		f.getContentPane().add(lblLoadProgress);

		JButton btnLoad = new JButton("Load");
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
					return;
				}

				Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

				// Spends one Action Point for equipped individuals
				// Checks if AP Full
				// Increases spent AP
				for (Trooper trooper : staticWeapon.equipedTroopers) {
					if (window.game.getPhase() == 1) {
						if (trooper.spentPhase1 != window.game.getCurrentAction()) {

							if (trooper.spentPhase1 + 1 <= trooper.P1)
								trooper.spentPhase1 += 1;
						}
					} else {
						if (trooper.spentPhase2 != window.game.getCurrentAction()) {
							if (trooper.spentPhase2 + 1 <= trooper.P2)
								trooper.spentPhase2 += 1;
						}
					}
				}

				if (loadTime == 0) {

					staticWeapon.ammoLoaded = staticWeapon.ammoCapacity;

				} else {
					if (staticWeapon.loadProgress != loadTime) {
						staticWeapon.loadProgress++;
					}

					if (staticWeapon.loadProgress == loadTime) {
						staticWeapon.loadProgress = 0;
						staticWeapon.ammoLoaded = staticWeapon.ammoCapacity;
					}

				}

				openUnit.refreshIndividuals();

				setFields(unit);
			}
		});
		btnLoad.setBounds(10, 641, 219, 23);
		f.getContentPane().add(btnLoad);

		JLabel label_2 = new JLabel("EAL Bonus:");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(142, 305, 87, 23);
		f.getContentPane().add(label_2);

		spinnerEALBonus = new JSpinner();
		spinnerEALBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerEALBonus.setBounds(141, 323, 43, 20);
		f.getContentPane().add(spinnerEALBonus);

		chckbxMultipleTargets = new JCheckBox("Multiple Targets");
		chckbxMultipleTargets.setForeground(Color.WHITE);
		chckbxMultipleTargets.setBackground(Color.DARK_GRAY);
		chckbxMultipleTargets.setBounds(10, 320, 113, 23);
		f.getContentPane().add(chckbxMultipleTargets);

		chckbxFullAuto = new JCheckBox("Full Auto");
		chckbxFullAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listEquipedStatics.getSelectedIndex() < 0)
					return;
				
				Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
				
				if(shoot != null) {
					guiUpdates();
				}
			}
		});
		chckbxFullAuto.setForeground(Color.WHITE);
		chckbxFullAuto.setBackground(Color.DARK_GRAY);
		chckbxFullAuto.setBounds(10, 268, 113, 23);
		f.getContentPane().add(chckbxFullAuto);

		chckbxSustainedFullAuto = new JCheckBox("Sustained FAB");
		chckbxSustainedFullAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.err.println("Sustained FAB not implemented");
				
			}
		});
		chckbxSustainedFullAuto.setForeground(Color.WHITE);
		chckbxSustainedFullAuto.setBackground(Color.DARK_GRAY);
		chckbxSustainedFullAuto.setBounds(10, 294, 113, 23);
		f.getContentPane().add(chckbxSustainedFullAuto);

		lblTN = new JLabel("TN: 0");
		lblTN.setForeground(Color.BLACK);
		lblTN.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTN.setBounds(10, 229, 113, 14);
		f.getContentPane().add(lblTN);

		lblPossibleShots = new JLabel("Possible Shots:");
		lblPossibleShots.setForeground(Color.BLACK);
		lblPossibleShots.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPossibleShots.setBounds(10, 213, 130, 14);
		f.getContentPane().add(lblPossibleShots);

		lblAimTime = new JLabel("Aim Time:");
		lblAimTime.setForeground(Color.BLACK);
		lblAimTime.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAimTime.setBounds(11, 244, 87, 14);
		f.getContentPane().add(lblAimTime);

		JLabel lblAimTime2 = new JLabel("Aim Time:");
		lblAimTime2.setFont(new Font("Calibri", Font.BOLD, 13));
		lblAimTime2.setBounds(10, 131, 175, 14);
		f.getContentPane().add(lblAimTime2);

		comboBoxAimTime = new JComboBox();
		comboBoxAimTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(listEquipedStatics.getSelectedIndex() < 0)
					return;
				
				Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
				
				if (shoot == null)
					return;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						if (comboBoxAimTime.getSelectedIndex() == 0)
							shoot.autoAim();
						else
							shoot.setAimTime(comboBoxAimTime.getSelectedIndex() - 1);

						if (comboBoxTargetZone.getSelectedIndex() > 0) {
							setCalledShotBounds();
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
		});
		comboBoxAimTime.setModel(new DefaultComboBoxModel(new String[] { "Auto", "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "10", "11", "12", "13", "14", "15" }));
		comboBoxAimTime.setSelectedIndex(0);
		comboBoxAimTime.setBounds(10, 148, 87, 20);
		f.getContentPane().add(comboBoxAimTime);

		JButton btnAim = new JButton("Aim");
		btnAim.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(listEquipedStatics.getSelectedIndex() < 0)
					return;
				
				Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
				
				if (shoot == null) {
					return;
				}

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						int newAim = shoot.aimTime + (gunner.combatActions - shoot.spentCombatActions);

						newAim = newAim >= shoot.wep.aimTime.size() ? shoot.wep.aimTime.size() - 1 : newAim;

						shoot.spentCombatActions += newAim - shoot.aimTime;

						shoot.setAimTime(newAim);
						
						if (!chckbxFreeAction.isSelected() && shoot.spentCombatActions >= gunner.combatActions) {
							spendAction();
						}
						
						refreshSustainedFireTargets(unit);
						openUnit.refreshIndividuals();

						return null;
					}

					@Override
					protected void done() {
						guiUpdates();
					}

				};

				worker.execute();
				

			}
		});
		btnAim.setBounds(122, 350, 107, 23);
		f.getContentPane().add(btnAim);

		chckbxFreeAction = new JCheckBox("Free Action");
		chckbxFreeAction.setForeground(Color.WHITE);
		chckbxFreeAction.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFreeAction.setBackground(Color.DARK_GRAY);
		chckbxFreeAction.setBounds(109, 147, 97, 23);
		f.getContentPane().add(chckbxFreeAction);

		spinnerConsecutiveEalBonus = new JSpinner();
		spinnerConsecutiveEalBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerConsecutiveEalBonus.setBounds(141, 244, 39, 20);
		f.getContentPane().add(spinnerConsecutiveEalBonus);

		JLabel lblCaBonus = new JLabel("Consec. EAL:");
		lblCaBonus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCaBonus.setBounds(141, 227, 76, 14);
		f.getContentPane().add(lblCaBonus);

		lblCombatActions = new JLabel("Combat Actions:");
		lblCombatActions.setForeground(Color.BLACK);
		lblCombatActions.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCombatActions.setBounds(141, 173, 88, 31);
		f.getContentPane().add(lblCombatActions);

		lblTfSpentCa = new JLabel("TF Spent CA: 0");
		lblTfSpentCa.setForeground(Color.BLACK);
		lblTfSpentCa.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTfSpentCa.setBounds(141, 202, 102, 23);
		f.getContentPane().add(lblTfSpentCa);
		
		comboBoxTargetZone = new JComboBox();
		comboBoxTargetZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					setCalledShotBounds();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		comboBoxTargetZone.setModel(new DefaultComboBoxModel(new String[] {"Auto", "Head", "Body ", "Legs"}));
		comboBoxTargetZone.setSelectedIndex(0);
		comboBoxTargetZone.setBounds(12, 188, 87, 20);
		f.getContentPane().add(comboBoxTargetZone);
		
		JLabel lblTargetZone = new JLabel("Target Zone:");
		lblTargetZone.setFont(new Font("Calibri", Font.BOLD, 13));
		lblTargetZone.setBounds(10, 172, 175, 14);
		f.getContentPane().add(lblTargetZone);
		
		comboBoxPcAmmo = new JComboBox();
		comboBoxPcAmmo.setBounds(10, 520, 219, 23);
		f.getContentPane().add(comboBoxPcAmmo);
		
		JLabel lblPcAmmo = new JLabel("PC Ammo:");
		lblPcAmmo.setFont(new Font("Calibri", Font.BOLD, 13));
		lblPcAmmo.setBounds(10, 494, 175, 14);
		f.getContentPane().add(lblPcAmmo);
		
		chckbxHoming = new JCheckBox("Homing");
		chckbxHoming.setForeground(Color.WHITE);
		chckbxHoming.setBackground(Color.DARK_GRAY);
		chckbxHoming.setBounds(10, 465, 113, 23);
		f.getContentPane().add(chckbxHoming);
		
		JButton btnFree = new JButton("Free A/D");
		btnFree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedWeaponIndex < 0) {
					return; 
				}
				
				trooperUnit.staticWeapons.get(selectedWeaponIndex).assembled = 
						!trooperUnit.staticWeapons.get(selectedWeaponIndex).assembled;
				refreshWeaponStats(trooperUnit);
			}
		});
		btnFree.setBounds(142, 700, 87, 23);
		f.getContentPane().add(btnFree);
		
		chckbxSingleShot = new JCheckBox("Single Shot");
		chckbxSingleShot.setForeground(Color.WHITE);
		chckbxSingleShot.setBackground(Color.DARK_GRAY);
		chckbxSingleShot.setBounds(130, 465, 103, 23);
		f.getContentPane().add(chckbxSingleShot);
		
		chckbxMannualSup = new JCheckBox("Manual Sup");
		chckbxMannualSup.setForeground(Color.WHITE);
		chckbxMannualSup.setBackground(Color.DARK_GRAY);
		chckbxMannualSup.setBounds(10, 440, 113, 23);
		f.getContentPane().add(chckbxMannualSup);
		
		spinnerSuppressiveRof = new JSpinner();
		spinnerSuppressiveRof.setBounds(132, 442, 43, 20);
		f.getContentPane().add(spinnerSuppressiveRof);

		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setFields(unit);
		if (unit.staticWeapons.size() > 0) {
			listEquipedStatics.setSelectedIndex(0);
			onClickEquipedStatics(unit);
			setGunner();
		}
		

	}

	public void setFields(Unit unit) {

		// Sets equipable statics list
		Weapons wep = new Weapons();
		ArrayList<Weapons> weapons = wep.getWeapons();
		availableStaticWeapons.clear();

		// listAvailableStatics
		DefaultListModel availableStaticsList = new DefaultListModel();

		for (Weapons weapon : weapons) {

			if (weapon.type.equals("Static")) {
				availableStaticsList.addElement(weapon);
				availableStaticWeapons.add(weapon);
			}
		}

		listAvailableStatics.setModel(availableStaticsList);

		refreshEquipedStatics(unit);
		refreshTroopers(unit);
		refreshEquipedIndividuals(unit);
		refreshWeaponStats(unit);
		refreshSustainedFireTargets(unit);
		refreshSuppressiveFireTargets(unit);

	}

	// Takes values from currently selected static weapon and updates the front end
	public void refreshWeaponStats(Unit unit) {
		if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
			return;
		}

		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

		int incapableTroopers = 0;

		if (staticWeapon.equipedTroopers.size() > 0) {

			for (Trooper trooper : staticWeapon.equipedTroopers) {

				int disabledLimbs = trooper.disabledArms + trooper.disabledLegs;

				if (!trooper.conscious || !trooper.alive || disabledLimbs > 1 || trooper.HD) {
					incapableTroopers++;
				}

			}

		}

		// Ammunition loaded
		spinnerAmmunitionLoaded.setValue(staticWeapon.ammoLoaded);

		// Ammunition load time
		loadTime = staticWeapon.loadTime;
		if (staticWeapon.equipedTroopers.size() - incapableTroopers > 0) {
			loadTime = loadTime / (staticWeapon.equipedTroopers.size() - incapableTroopers);
		}

		lblLoadTime.setText("Load Time: " + loadTime);

		// Ammunition load progress
		if (loadTime == 0) {
			lblLoadProgress.setText("Load Progress: N/A");
		} else {
			lblLoadProgress.setText("Load Progress: " + staticWeapon.loadProgress);
		}

		// Assembled
		lblAssembled.setText("Assembled: " + staticWeapon.assembled);

		// Assembled time
		assembleTime = staticWeapon.assembleCost;
		if (staticWeapon.equipedTroopers.size() - incapableTroopers > 0) {
			assembleTime = assembleTime / (staticWeapon.equipedTroopers.size() - incapableTroopers);
		}
		lblAssembledissembleCost.setText("Assemble Cost: " + assembleTime);

		// Assembled progress
		lblProgress.setText("Assemble Progress: " + staticWeapon.assembleProgress);

	}

	public void refreshEquipedStatics(Unit unit) {

		int index = listEquipedStatics.getSelectedIndex();

		DefaultListModel equpiedStaticsList = new DefaultListModel();

		for (Weapons weapon : unit.staticWeapons) {
			equpiedStaticsList.addElement(weapon);
		}

		listEquipedStatics.setModel(equpiedStaticsList);

		listEquipedStatics.setSelectedIndex(index);
		
		if(shots.size() != unit.staticWeapons.size()) {
			for(int i = 0; i < unit.staticWeapons.size() - shots.size(); i++)
				System.out.println("shots add null");
				shots.add(null);
		}
		
	}

	public void refreshTroopers(Unit unit) {
		// Adds individuals
		DefaultListModel troopersList = new DefaultListModel();
		unequipedTroopers.clear();

		for (Trooper trooper : unit.getTroopers()) {

			if (!unit.equipped(trooper)) {
				troopersList.addElement(trooper.toStringImprovedEquipped(GameWindow.gameWindow.game));
				unequipedTroopers.add(trooper);
			}

		}

		listIndividuals.setModel(troopersList);
	}

	// Sets equiped individual list equal to the selected weapon's crew
	public void refreshEquipedIndividuals(Unit unit) {

		if (selectedWeaponIndex > -1) {
			DefaultListModel equipedList = new DefaultListModel();

			for (Trooper equipedTrooper : unit.staticWeapons.get(selectedWeaponIndex).equipedTroopers) {
				equipedList.addElement(equipedTrooper.toStringImproved(GameWindow.gameWindow.game));
			}

			listEquipedIndividuals.setModel(equipedList);
		} else {
			DefaultListModel equipedList = new DefaultListModel();
			listEquipedIndividuals.setModel(equipedList);
		}

	}

	// Populates target individual drop down menus based off of the targets that the
	// crew can see
	public void refreshSustainedFireTargets(Unit unit) {
		if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
			return;
		}
		System.out.println("Refresh sustained fire targets;");
		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

		int index = comboBoxTargets.getSelectedIndex();
		
		comboBoxTargets.removeAllItems();
		comboBoxTargets.addItem("None");

		targetTroopers.clear();

		for (Trooper trooper : staticWeapon.equipedTroopers) {
			System.out.println("Pass 1");
			ArrayList<Trooper> targets = trooper.returnTargets();

			for (Trooper target : targets) {
				System.out.println("Pass 2");
				if (!targetTroopers.contains(target)) {

					if (!target.conscious || !target.alive || target.HD) {
						continue; 
					} else {
						System.out.println("Pass 3");
						targetTroopers.add(target);
						Trooper gunner = findGunner();
						String targetString = target.toTarget(window);
						if(gunner.storedAimTime.containsKey(target)) 
							targetString = "Targeted: "+targetString;
						comboBoxTargets.addItem(targetString);
					}

				}

			}

		}
		if(index > targetTroopers.size())
			comboBoxTargets.setSelectedIndex(0);

	}

	// Populates target individual drop down menus based off of the targets that the
	// crew can see
	public void refreshSuppressiveFireTargets(Unit unit) {
		if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
			return;
		}

		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

		comboBoxSuppressiveFireTargets.removeAllItems();
		comboBoxSuppressiveFireTargets.addItem("None");

		for (Unit targetUnit : unit.lineOfSight) {
			comboBoxSuppressiveFireTargets.addItem(targetUnit.callsign);
		}

	}

	// A methods used by available statics to automatically select the first statics
	// weapon equiped
	public void onClickEquipedStatics(Unit unit) {
		if (unit.staticWeapons.size() > 0) {
			selectedWeaponIndex = listEquipedStatics.getSelectedIndex();

			staticWeapon = unit.staticWeapons.get(listEquipedStatics.getSelectedIndex());

			lblStaticWeapon
					.setText(Integer.toString(listEquipedStatics.getSelectedIndex() + 1) + " " + staticWeapon.name);

			setFields(unit);
		}
	}

	

	// Performs sustained fire attack with the static weapon
	public void sustainedFire(Unit unit, GameWindow window) {
		if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
			return;
		}

		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

		// Removes ammo
		// combo box shots
		int shots = 0;

		// If shots zero or less
		if (shots < 1) {
			window.conflictLog.addNewLine("Selected shots equal zero!");
			return;
		}

		// If shots are greater than the available ammo
		if (shots > staticWeapon.ammoLoaded) {
			window.conflictLog.addNewLine("Not enough ammo!");
			return;
		}

		staticWeapon.ammoLoaded -= shots;

		// Rolls hits

		// Gets RWS
		int RWS = getHeavyRWS(unit);

		targetTrooper();
		targetUnit();

		Weapons weapon = unit.staticWeapons.get(selectedWeaponIndex);

		if (RWS < 0 || weapon == null || shots < 1 || targetTrooper == null || gunner == null || targetUnit == null
				|| unit == null) {
			//System.out.println("Unset variables : sustained fire failed");
			return;
		}

		TargetedFire targetedFire = new TargetedFire(RWS, (int) spinnerPercentBonus.getValue(), weapon, shots,
				targetTrooper, gunner, targetUnit, unit, false);

		int hits = targetedFire.getHits();
		int TN = targetedFire.getTN();

		window.conflictLog.addNewLine("TARGETED FIRE: " + unit.side + "::  " + unit.callsign + " to " + targetUnit.side
				+ "::  " + targetUnit.callsign + ", " + targetTrooper.name + ", Number: " + targetTrooper.number
				+ "\nTageted Fire Results:\nHITS: " + hits + "\n" + "TN: " + TN);

		if (hits > 0) {

			// Resolves hits
			resolveHits(hits, weapon);

		}

	}

	// Resolves Suppressive Fire hits
	public void resolveHits(int hits, Weapons weapon, boolean suppressiveFire, int suppressiveHits,
			Unit suppressiveTargetUnit) {

		//System.out.println("STATIC WEAPON SUPPRESSIVE HITS: " + hits);

		if (suppressiveTargetUnit.suppression + hits < 100) {
			suppressiveTargetUnit.suppression += hits;
		} else {
			suppressiveTargetUnit.suppression = 100;
		}
		if (suppressiveTargetUnit.organization - hits > 0) {
			suppressiveTargetUnit.organization -= hits;
		} else {
			suppressiveTargetUnit.organization = 0;
		}

		if (suppressiveTargetUnit.getTroopers().size() < 1)
			return;

		ArrayList<Trooper> possibleHitTrooper = new ArrayList<>();

		// Gets hit trooper
		for (Trooper trooper : suppressiveTargetUnit.getTroopers()) {
			if (trooper.alive) {
				possibleHitTrooper.add(trooper);
			}
		}

		int rollSize = possibleHitTrooper.size();
		Random rand = new Random();
		Trooper hitTrooper = possibleHitTrooper.get(rand.nextInt(rollSize));

		ResolveHits resolveHits = new ResolveHits(hitTrooper, suppressiveHits, weapon, window.conflictLog,
				suppressiveTargetUnit, trooperUnit, window);
		resolveHits.performCalculations(window.game, window.conflictLog);
		hitTrooper = resolveHits.returnTarget();

		// Reduces morale and organization for death
		// Else, reduces morale and organization for injury

		int unitSize = suppressiveTargetUnit.getSize();
		int moraleLoss = 100 / unitSize;
		if (hitTrooper.currentHP < 1) {

			if (suppressiveTargetUnit.organization - 5 < 1) {
				suppressiveTargetUnit.organization = 0;
			} else {
				suppressiveTargetUnit.organization -= 5;
			}

			if (suppressiveTargetUnit.moral - moraleLoss < 1) {
				suppressiveTargetUnit.moral = 0;
			} else {
				suppressiveTargetUnit.moral -= moraleLoss;
			}

		} else {
			if (suppressiveTargetUnit.organization - 1 < 1) {
				suppressiveTargetUnit.organization = 0;
			} else {
				suppressiveTargetUnit.organization -= 1;
			}

			if (suppressiveTargetUnit.moral - moraleLoss / 3 < 1) {
				suppressiveTargetUnit.moral = 0;
			} else {
				suppressiveTargetUnit.moral -= moraleLoss / 3;
			}
		}
	}

	// Resolves hits
	public void resolveHits(int hits, Weapons weapon) {
		if (targetUnit.suppression + hits < 100) {
			targetUnit.suppression += hits;
		} else {
			targetUnit.suppression = 100;
		}
		if (targetUnit.organization - hits > 0) {
			targetUnit.organization -= hits;
		} else {
			targetUnit.organization = 0;
		}

		ResolveHits resolveHits = new ResolveHits(targetTrooper, hits, weapon, window.conflictLog, targetUnit,
				trooperUnit, window);
		resolveHits.performCalculations(window.game, window.conflictLog);
		targetTrooper = resolveHits.returnTarget();

		// Reduces morale and organization for death
		// Else, reduces morale and organization for injury

		int unitSize = targetUnit.getSize();
		int moraleLoss = 100 / unitSize;
		if (targetTrooper.currentHP < 1) {

			if (targetUnit.organization - 5 < 1) {
				targetUnit.organization = 0;
			} else {
				targetUnit.organization -= 5;
			}

			if (targetUnit.moral - moraleLoss < 1) {
				targetUnit.moral = 0;
			} else {
				targetUnit.moral -= moraleLoss;
			}

		} else {
			if (targetUnit.organization - 1 < 1) {
				targetUnit.organization = 0;
			} else {
				targetUnit.organization -= 1;
			}

			if (targetUnit.moral - moraleLoss / 3 < 1) {
				targetUnit.moral = 0;
			} else {
				targetUnit.moral -= moraleLoss / 3;
			}
		}
	}

	// Gets target trooper
	public void targetTrooper() {
		if (targetTroopers.size() < 0) {
			return;
		}

		targetTrooper = targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1);
		//System.out.println("TARGETs Size: " + targetTroopers.size());
		//System.out.println("TARGET INDEX - 1: " + comboBoxTargets.getSelectedIndex());
		//System.out.println("TARGET TROOPER: " + targetTrooper.number + ":: " + targetTrooper.name);

		/*for (Trooper trooper : targetTroopers) {
			//System.out.println("Trooper: " + trooper.number + ":: " + trooper.name);
		}*/

	}

	// Gets the targeted Unit
	public void targetUnit() {
		if (targetTrooper == null)
			return;

		for (Unit unit : window.initiativeOrder) {

			for (Trooper trooper : unit.getTroopers()) {

				if (targetTrooper.compareTo(trooper)) {
					targetUnit = unit;
				}

			}

		}
	}

	// Sets the gunner
	// Returns heavy RWS
	public int getHeavyRWS(Unit unit) {
		if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0)
			return -1;

		if (unit.staticWeapons.get(selectedWeaponIndex).equipedTroopers.size() < 1)
			return -1;

		int RWS = -1;

		for (Trooper trooper : unit.staticWeapons.get(selectedWeaponIndex).equipedTroopers) {
			if (trooper.getSkill("Heavy") > RWS) {
				RWS = trooper.getSkill("Heavy");
				gunner = trooper;
			}

		}

		return RWS;
	}

	// Saves changes made to the equiped weappon
	public void saveChanges(Unit unit, JFrame f) {
		if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
			return;
		}

		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

		//staticWeapon.ammoLoaded = (int) spinnerAmmunitionLoaded.getValue();

		f.dispose();

	}

	public void setAction(Trooper trooper) {

		trooper.setPCStats();

		TFCA = trooper.combatActions;

	}

	public Trooper findTarget() {
		if (targetTroopers == null || targetTroopers.size() < 1 || comboBoxTargets.getSelectedIndex() == 0) {
			//System.out.println("Fail 2");
			//System.out.println("Target Troopers Fail 2: " + targetTroopers.toString());
			//System.out.println("comboBoxint: " + comboBoxTargets.getSelectedIndex());
			return null;
		}
		Trooper trooper = targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1);
		return trooper;
	}

	// Loops through units in initiaitive order
	// Looks for target unit
	public void findTargetUnit(OpenUnit window, String unitName) {
		ArrayList<Unit> units = window.gameWindow.initiativeOrder;

		for (int i = 0; i < units.size(); i++) {
			if (unitName.equals(units.get(i).callsign)) {
				targetUnit = units.get(i);
			}
		}

	}

	// Loops through units in initiaitive order
	// Looks for unit containing indvididual
	// Returns unit
	public Unit findTrooperUnit(Trooper trooper) {
		ArrayList<Unit> units = window.initiativeOrder;

		for (Unit unit : units) {

			for (Trooper trooper1 : unit.getTroopers()) {

				if (trooper1.compareTo(trooper))
					return unit;

			}

		}

		return null;

	}

	public int calculateCT(int LSF) {

		int CT = 0;

		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(new File(
					"C:\\Users\\Xander\\OneDrive - Colostate\\Xander Personal\\Code\\eclipse-workspace\\CFFW\\Leadership.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			if (LSF <= 10) {

				column = 1;

			} else {

				for (int i = 1; i < 15; i++) {
					if (LSF < worksheet.getRow(i).getCell(0).getNumericCellValue()) {
						column = i;
						break;
					}
				}
			}

			CT = (int) worksheet.getRow(column).getCell(1).getNumericCellValue();

			excelFile.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return CT;

	}

	/*public int calculateCA(double ms, int isf) {

		int CA = 0;
		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(new File(
					path+"CA.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			if (isf <= 7) {

				column = 1;

			} else {

				for (int i = 1; i < 19; i++) {
					if (isf < row.getCell(i + 1).getNumericCellValue()) {
						column = i;
						break;
					}
				}
			}

			for (int x = 1; x < 22; x++) {

				if (ms == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					CA = (int) worksheet.getRow(x).getCell(column).getNumericCellValue();
					break;
				}
			}

			excelFile.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (staticWeapon != null) {
			int incapableTroopers = 0;

			if (staticWeapon.equipedTroopers.size() > 0) {

				for (Trooper trooper2 : staticWeapon.equipedTroopers) {

					int disabledLimbs = trooper2.disabledArms + trooper2.disabledLegs;

					if (!trooper2.conscious || !trooper2.alive || disabledLimbs > 1 || trooper2.HD) {
						incapableTroopers++;
					}

				}

			}

			if (staticWeapon.equipedTroopers.size() - 1 - incapableTroopers > 0) {
				//System.out.println("Original CA: "+CA+", Adding Additional CA: "+(staticWeapon.equipedTroopers.size() - 1 - incapableTroopers));
				return CA + staticWeapon.equipedTroopers.size() - 1 - incapableTroopers;
			}
			else
				return CA;
		} else
			return CA;

	}*/

	public int defensiveALM(int isf) {

		int dAlm = 0;

		try {

			FileInputStream excelFile = new FileInputStream(new File(
					"C:\\Users\\Xander\\OneDrive - Colostate\\Xander Personal\\Code\\eclipse-workspace\\CFFW\\DefensiveALM.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			for (int i = 1; i < 41; i++) {

				if (isf == (int) worksheet.getRow(i).getCell(0).getNumericCellValue()) {
					dAlm = (int) worksheet.getRow(i).getCell(1).getNumericCellValue();

				}

			}

			excelFile.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dAlm;

	}

	public double maximumSpeed(int encum, Trooper trooper) {
		double baseSpeed = baseSpeed(encum, trooper);

		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(new File(
					path+"MaximumSpeed.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			for (int i = 1; i < 9; i++) {
				if (baseSpeed == row.getCell(i).getNumericCellValue()) {
					column = i;
					break;
				}
			}

			for (int x = 1; x < 22; x++) {

				if ((int) trooper.agi == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					baseSpeed = worksheet.getRow(x).getCell(column).getNumericCellValue();
				}
			}

			excelFile.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baseSpeed;

	}

	public double baseSpeed(int encum, Trooper trooper) {

		double baseSpeed = 0;
		int column = 0;
		try {

			FileInputStream excelFile = new FileInputStream(new File(
					path+"BaseSpeed.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet worksheet = workbook.getSheetAt(0);

			Row row = worksheet.getRow(0);

			if (encum <= 10) {

				column = 1;

			} else {

				for (int i = 1; i < 19; i++) {
					if (encum < row.getCell(i + 1).getNumericCellValue()) {
						column = i;
						break;
					}
				}
			}

			for (int x = 1; x < 22; x++) {

				if ((int) trooper.str == worksheet.getRow(x).getCell(0).getNumericCellValue()) {
					baseSpeed = worksheet.getRow(x).getCell(column).getNumericCellValue();
				}
			}

			excelFile.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baseSpeed;

	}

	public Trooper findGunner() {
		//System.out.println("Returning Gunner1");
		//System.out.println("unit.staticWeapons.size(): " + unit.staticWeapons.size()
		//		+ ", listEquipedStatics.getSelectedIndex(): " + listEquipedStatics.getSelectedIndex());
		Weapons staticWep = unit.staticWeapons.get(listEquipedStatics.getSelectedIndex());
		//System.out.println("Returning Gunner2");

		Trooper gunner = null;
		for (Trooper trooper : staticWep.equipedTroopers) {

			int disabledLimbs = trooper.disabledArms + trooper.disabledLegs;

			if (!trooper.conscious || !trooper.alive || disabledLimbs > 1 || trooper.HD) {
				continue;
			}

			if (gunner == null || trooper.sl > gunner.sl)
				gunner = trooper;
		}
		//System.out.println("Returning Gunner3, Gunner Name: "+gunner.name);
		return gunner;
	}
	
	public void setGunner() {
		gunner = findGunner();
	}

	public void aim(Trooper gunner) {

		/*
		 * if(targetedFire != null) {
		 * 
		 * targetedFire.spentAimTime++; targetedFire.aimAction(targetedFire.EAL,
		 * openTrooper.wep, openTrooper); targetedFire.possibleShots(openTrooper.wep);
		 * 
		 * if(chckbxFullAuto.isSelected()) { targetedFire.possibleShotsFullAuto(); }
		 * else { targetedFire.possibleShots(openTrooper.wep); }
		 * 
		 * for(int i = 0; i < targetedFire.sabCount; i++) {
		 * 
		 * targetedFire.EAL += new Weapons().findWeapon(openTrooper.wep).sab;
		 * targetedFire.ALMSum += new Weapons().findWeapon(openTrooper.wep).sab;
		 * 
		 * }
		 * 
		 * targetedFire.sabCount = 0;
		 * 
		 * if(targetedFire.consecutiveShots) { targetedFire.consecutiveShots = false;
		 * targetedFire.EAL -= 2; targetedFire.ALMSum -= 2; }
		 * 
		 * if(chckbxFreeAction.isSelected()) { targetedFire.spentCA -= 1; }
		 * 
		 * 
		 * }
		 */

		setAction(gunner);

		if (gunner.storedAimTime.containsKey(targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1))) {
			gunner.storedAimTime.put(targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1).identifier, TFCA - spentCA
					+ gunner.storedAimTime.get(targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1)));
		} else {
			gunner.storedAimTime.clear();
			gunner.storedAimTime.put(targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1).identifier, TFCA - spentCA);
		}

	}
	
	public void spendAction() {
		
		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

		// Spends one Action Point for equipped individuals
		// Checks if AP Full
		// Increases spent AP
		if (window.game.getPhase() == 1) {
			if (gunner.spentPhase1 != window.game.getCurrentAction()) {

				if (gunner.spentPhase1 + 1 <= gunner.P1)
					gunner.spentPhase1 += 1;
			}
		} else {
			if (gunner.spentPhase2 != window.game.getCurrentAction()) {
				if (gunner.spentPhase2 + 1 <= gunner.P2)
					gunner.spentPhase2 += 1;
			}
		}
		
		refreshEquipedIndividuals(unit);
		
	}
	
	public void bonuses() {
		if(listEquipedStatics.getSelectedIndex() < 0)
			return;
		
		Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
		
		if(shoot == null)
			return;
		
		shoot.setBonuses((int) spinnerPercentBonus.getValue(), (int) spinnerEALBonus.getValue(), (int) spinnerConsecutiveEalBonus.getValue());
		guiUpdates();
	}
	
	public void guiUpdates() {
		if(listEquipedStatics.getSelectedIndex() < 0)
			return;
		
		Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
		ArrayList<Shoot> shots = new ArrayList<>();
		shots.add(shoot);
		if (shoot == null) {
			System.out.println("Shoot is null 2");
			return;
		}
		ShootUtility.shootGuiUpdate(lblPossibleShots, lblAimTime, lblTN, lblTfSpentCa, lblAmmunition, lblCombatActions,
				chckbxFullAuto, shots);
		var ammo = unit.staticWeapons.get(listEquipedStatics.getSelectedIndex()).ammoLoaded;
		lblAmmunition.setText("Ammunition: "+
				ammo);
		spinnerAmmunitionLoaded.setValue(ammo);
	}
	
	public void setCalledShotBounds() {
		if(listEquipedStatics.getSelectedIndex() < 0)
			return;
		
		Shoot shoot = shots.get(listEquipedStatics.getSelectedIndex());
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
}
