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
import Conflict.OpenUnit;
import Injuries.ResolveHits;
import Trooper.Trooper;
import Trooper.generateSquad;
import Unit.Unit;

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
	public int loadTime;
	public int assembleTime;
	private JList listIndividuals;
	private JLabel lblSelectedStatic;
	private JLabel lblStaticWeapon;
	private JComboBox comboBoxTargets;
	private JSpinner spinnerPercentBonus;
	private JComboBox comboBoxSuppressiveFireTargets;
	private JComboBox comboBoxSuppressiveFireShots;
	private JSpinner spinnerSuppresiveFireBonus;
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
	private JSpinner spinnerCABonus;
	private JLabel lblPossibleShots;
	private JLabel lblAimTime;
	private JLabel lblTN;
	private JLabel lblCombatActions;
	private JLabel lblTfSpentCa;
	private JComboBox comboBoxTargetZone;

	public StaticWeaponWindow(Unit unit, GameWindow window, OpenUnit openUnit) {
		this.unit = unit;
		this.window = window;
		this.trooperUnit = openUnit.unit;
		final JFrame f = new JFrame("Static Weapons");
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

					lblStaticWeapon.setText(
							Integer.toString(listEquipedStatics.getSelectedIndex() + 1) + " " + staticWep.name);

					setFields(unit);
				}

				//System.out.println("Selected Index: " + listEquipedStatics.getSelectedIndex());

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

				if (selectedWeaponIndex > -1) {

					unit.staticWeapons.get(selectedWeaponIndex).equipedTroopers
							.remove(listEquipedIndividuals.getSelectedIndex());

					setFields(unit);

				}

				listEquipedStatics.setSelectedIndex(index);

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

		JButton btnSaveAndClose = new JButton("Save");
		btnSaveAndClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				saveChanges(unit, f);

			}
		});
		btnSaveAndClose.setBounds(141, 699, 87, 23);
		f.getContentPane().add(btnSaveAndClose);

		comboBoxTargets = new JComboBox();
		comboBoxTargets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxTargets.getSelectedIndex() == 0) {
					//System.out.println("Combo Box Targets Return");
					return;
				}

				comboBoxAimTime.setSelectedIndex(0);
				targetedFire = null;
				possibleShots = true;
				reaction = null;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						//System.out.println("Target Changed Shots1");
						Trooper gunner = findGunner();
						if (gunner == null) {
							return null;
						}

						//System.out.println("Target Changed Shots2");
						PCShots(gunner);
						//System.out.println("Target Changed Shots3");

						return null;
					}

					@Override
					protected void done() {
						Trooper gunner = findGunner(); 
						if (gunner == null) {
							window.conflictLog.addNewLine("No avaiable gunner.");
							return;
						}
						PCFireGuiUpdates(gunner);

					}

				};

				worker.execute();

			}
		});
		comboBoxTargets.setBounds(10, 97, 219, 23);
		f.getContentPane().add(comboBoxTargets);

		comboBoxSuppressiveFireTargets = new JComboBox();
		comboBoxSuppressiveFireTargets.setBounds(10, 409, 219, 23);
		f.getContentPane().add(comboBoxSuppressiveFireTargets);

		JButton btnFire = new JButton("Fire");
		btnFire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
					return;
				}

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						Trooper gunner = findGunner();
						if (gunner == null)
							return null;

						//System.out.println("Shots1");
						PCFire(gunner);
						PCShots(gunner);
						gunner.storedAimTime.clear();
						//System.out.println("Shots2");
						if(!chckbxFreeAction.isSelected()) {
							spendAction();
						}
						return null;
					}

					@Override
					protected void done() {

						//System.out.println("Done");
						Trooper gunner = findGunner(); 
						if (gunner == null) {
							window.conflictLog.addNewLine("No avaiable gunner.");
							return;
						}
						PCFireGuiUpdates(gunner);
						//setFields(unit);
						// fire(window, unit, index, f, false);
						if (!chckbxMultipleTargets.isSelected() && possibleShots == false && reaction == null) {
							if (reaction != null) {
								reaction.frame.dispose();
								reaction = null;
							}
							
							spendAction();
							
							// actionSpent(window, index);
							// window.openNext(true);
							// f.dispose();

						} 
						
						openUnit.refreshIndividuals();
						
					}

				};

				worker.execute();

				/*
				 * Weapons staticWeapon = unit.staticWeapons.get(selectedWeapon);
				 * 
				 * // Spends one Action Point for equipped individuals // Checks if AP Full //
				 * Increases spent AP for(Trooper trooper : staticWeapon.equipedTroopers) { if
				 * (window.game.getPhase() == 1) { if(trooper.spentPhase1 !=
				 * window.game.getCurrentAction()) {
				 * 
				 * if(trooper.spentPhase1 + 1 <= trooper.P1) trooper.spentPhase1 += 1; } } else
				 * { if(trooper.spentPhase2 != window.game.getCurrentAction()) {
				 * if(trooper.spentPhase2 + 1 <= trooper.P2) trooper.spentPhase2 += 1; } } }
				 * 
				 * sustainedFire(unit, window); openUnit.refreshIndividuals();
				 * 
				 * setFields(unit);
				 */
			}
		});
		btnFire.setBounds(10, 350, 102, 23);
		f.getContentPane().add(btnFire);

		JLabel lblAssembledissemble = new JLabel("Assemble/Dissemble:");
		lblAssembledissemble.setFont(new Font("Calibri", Font.BOLD, 13));
		lblAssembledissemble.setBounds(10, 675, 175, 14);
		f.getContentPane().add(lblAssembledissemble);

		JLabel lblAmmunition = new JLabel("Ammunition:");
		lblAmmunition.setFont(new Font("Calibri", Font.BOLD, 13));
		lblAmmunition.setBounds(10, 555, 175, 14);
		f.getContentPane().add(lblAmmunition);

		JLabel lblOtherBonus = new JLabel("Other Bonus:");
		lblOtherBonus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOtherBonus.setBounds(141, 269, 76, 14);
		f.getContentPane().add(lblOtherBonus);

		spinnerPercentBonus = new JSpinner();
		spinnerPercentBonus.setBounds(141, 283, 43, 22);
		f.getContentPane().add(spinnerPercentBonus);

		JButton button = new JButton("Fire");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0) {
					return;
				}
				
				findGunner().storedAimTime.clear();

				spendAction();

				suppresiveFire(unit, window);
				
				openUnit.refreshSpinners();
				openUnit.refreshIndividuals();

				setFields(unit);
			}
		});
		button.setBounds(10, 508, 219, 23);
		f.getContentPane().add(button);

		comboBoxSuppressiveFireShots = new JComboBox();
		comboBoxSuppressiveFireShots.setBounds(10, 468, 89, 23);
		f.getContentPane().add(comboBoxSuppressiveFireShots);

		spinnerSuppresiveFireBonus = new JSpinner();
		spinnerSuppresiveFireBonus.setBounds(109, 469, 72, 22);
		f.getContentPane().add(spinnerSuppresiveFireBonus);

		JLabel label = new JLabel("Other Bonus:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(109, 443, 89, 14);
		f.getContentPane().add(label);

		JLabel label_1 = new JLabel("Shots:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(10, 443, 89, 14);
		f.getContentPane().add(label_1);

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

				// Gets currently selected unequiped individual and adds them to currently
				// selected weapon
				if (listIndividuals.getSelectedIndex() > -1 && selectedWeaponIndex > -1) {
					unit.staticWeapons.get(selectedWeaponIndex).equipedTroopers
							.add(unequipedTroopers.get(listIndividuals.getSelectedIndex()));
					unequipedTroopers.remove(listIndividuals.getSelectedIndex());
				}

				setFields(unit);

				openUnit.refreshIndividuals();

				listEquipedStatics.setSelectedIndex(index);

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
		lblLoadTime.setBounds(86, 580, 143, 14);
		f.getContentPane().add(lblLoadTime);

		lblLoadProgress = new JLabel("Load Progress:");
		lblLoadProgress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblLoadProgress.setBounds(86, 609, 143, 14);
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

				// Performs assemble/dissemble action
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
				if (targetedFire != null) {

					if (targetedFire.consecutiveShots) {
						targetedFire.EAL -= 2;
						targetedFire.ALMSum -= 2;
						targetedFire.consecutiveShots = false;
					}

				}

				Trooper gunner = findGunner();
				if (gunner == null) {
					window.conflictLog.addNewLine("No avaiable gunner.");
					return;
				}
				PCShots(gunner);
			}
		});
		chckbxFullAuto.setForeground(Color.WHITE);
		chckbxFullAuto.setBackground(Color.DARK_GRAY);
		chckbxFullAuto.setBounds(10, 268, 113, 23);
		f.getContentPane().add(chckbxFullAuto);

		chckbxSustainedFullAuto = new JCheckBox("Sustained FAB");
		chckbxSustainedFullAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (targetedFire != null) {

					if (targetedFire.consecutiveShots) {
						targetedFire.EAL -= 2;
						targetedFire.ALMSum -= 2;
						targetedFire.consecutiveShots = false;
					}

				}

				Trooper gunner = findGunner();
				if (gunner == null) {
					window.conflictLog.addNewLine("No avaiable gunner.");
					return;
				}
				PCShots(gunner);
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

				if (comboBoxAimTime.getSelectedIndex() == 0)
					return;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						//System.out.println("Aim Time - Target Changed Shots1");
						Trooper gunner = findGunner();
						if (gunner == null)
							return null;

						//System.out.println("Aim Time - Target Changed Shots2");
						PCShots(gunner);
						//System.out.println("Aim Time - Target Changed Shots3");

						return null;
					}

					@Override
					protected void done() {
						Trooper gunner = findGunner();
						if (gunner == null) {
							window.conflictLog.addNewLine("No avaiable gunner.");
							return;
						}
						PCFireGuiUpdates(gunner);

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

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						Trooper gunner = findGunner();
						if (gunner == null)
							return null;
						aim(gunner);
						PCShots(gunner);
						if(!chckbxFreeAction.isSelected()) {
							spendAction();
						}
							

						return null;
					}

					@Override
					protected void done() {
						Trooper gunner = findGunner(); 
						if (gunner == null) {
							window.conflictLog.addNewLine("No avaiable gunner.");
							return;
						}
						PCFireGuiUpdates(gunner);
						openUnit.refreshIndividuals();
						refreshSustainedFireTargets(unit);
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

		spinnerCABonus = new JSpinner();
		spinnerCABonus.setBounds(141, 244, 39, 20);
		f.getContentPane().add(spinnerCABonus);

		JLabel lblCaBonus = new JLabel("CA Bonus:");
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
		comboBoxTargetZone.setModel(new DefaultComboBoxModel(new String[] {"Auto", "Head", "Body ", "Legs"}));
		comboBoxTargetZone.setSelectedIndex(0);
		comboBoxTargetZone.setBounds(12, 188, 87, 20);
		f.getContentPane().add(comboBoxTargetZone);
		
		JLabel lblTargetZone = new JLabel("Target Zone:");
		lblTargetZone.setFont(new Font("Calibri", Font.BOLD, 13));
		lblTargetZone.setBounds(10, 172, 175, 14);
		f.getContentPane().add(lblTargetZone);

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
		comboBoxSuppressiveFireShots.setSelectedIndex(comboBoxSuppressiveFireShots.getItemCount() - 1);

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

		/*
		 * ComboBoxSustainedFire (shots) int size = staticWeapon.getTargetedROF() + 1;
		 * String[] shots = new String[size]; shots[0] = "0";
		 * 
		 * for (int i = 0; i < shots.length - 1; i++) { shots[i + 1] = String.valueOf(i
		 * + 1); }
		 * 
		 * DefaultComboBoxModel model = new DefaultComboBoxModel(shots);
		 */

		// ComboBoxSuppresiveFire (shots)

		comboBoxSuppressiveFireShots.removeAllItems();
		comboBoxSuppressiveFireShots.addItem(0);
		for (int i = 0; i < staticWeapon.suppressiveROF; i++) {
			comboBoxSuppressiveFireShots.addItem(i + 1);
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
				equipedList.addElement(equipedTrooper);
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
		
		comboBoxTargets.setSelectedIndex(index);

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

		for (Unit targetUnit : window.initiativeOrder) {

			if (targetUnit.side != unit.side) {
				comboBoxSuppressiveFireTargets.addItem(targetUnit.callsign);
			}

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

	// Performs suppressie fire attack with the static weapon
	public void suppresiveFire(Unit unit, GameWindow window) {
		if (unit.staticWeapons.size() < 1 || selectedWeaponIndex < 0
				|| comboBoxSuppressiveFireTargets.getSelectedIndex() < 1) {
			return;
		}

		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);

		ArrayList<Unit> targetableUnits = new ArrayList<>();

		for (Unit initUnit : window.initiativeOrder) {
			if (!initUnit.side.equals(unit.side))
				targetableUnits.add(initUnit);
		}

		Unit suppressiveTargetUnit = targetableUnits.get(comboBoxSuppressiveFireTargets.getSelectedIndex() - 1);

		// Removes ammo
		int shots = comboBoxSuppressiveFireShots.getSelectedIndex();

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

		if (RWS < 5) {
			RWS = 5;
		}

		targetTrooper = null;

		Weapons weapon = unit.staticWeapons.get(selectedWeaponIndex);

		if (RWS < 0 || weapon == null || shots < 1 || gunner == null || suppressiveTargetUnit == null || unit == null) {
			//System.out.println("Unset variables : sustained fire failed");
			return;
		}

		TargetedFire suppressiveFire = new TargetedFire(RWS, (int) spinnerPercentBonus.getValue(), weapon, shots,
				targetTrooper, gunner, suppressiveTargetUnit, unit, true);

		int hits = suppressiveFire.getHits();
		int TN = suppressiveFire.getTN();

		window.conflictLog.addNewLine("SUPPRESSIVE FIRE: " + unit.side + "::  " + unit.callsign + " to "
				+ suppressiveTargetUnit.side + "::  " + suppressiveTargetUnit.callsign
				+ "\nSuppressive Fire Results:\nHITS: " + hits + "\n" + "TN: " + TN);

		Random rand = new Random();

		int suppressiveHits = 0;

		for (int i = 0; i < hits; i++) {
			int roll = rand.nextInt(100) + 1;

			if (roll == 1) {
				suppressiveHits++;
			}
		}

		resolveHits(hits, weapon, true, suppressiveHits, suppressiveTargetUnit);

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

		staticWeapon.ammoLoaded = (int) spinnerAmmunitionLoaded.getValue();

		f.dispose();

	}

	// Sets possible shots based off of current Selected Aim Time
	public void PCShots(Trooper gunner) {
		if (comboBoxTargets.getSelectedIndex() < 1 || targetTroopers.size() < 1)
			return;

		//System.out.println("PC Shots");
		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);
		String trooperWep = gunner.wep;
		gunner.wep = staticWeapon.name;
		
		Trooper shooterTrooper = gunner;
		Trooper targetTrooper = findTarget();
		Unit targetUnit = findTrooperUnit(targetTrooper);
		Unit shooterUnit = findTrooperUnit(shooterTrooper);

		int maxAim = 0;

		if (comboBoxAimTime.getSelectedIndex() > 0) {

			maxAim = comboBoxAimTime.getSelectedIndex() - 1;
		}

		setAction(gunner);

		if (targetedFire == null) {
			TargetedFire tf = new TargetedFire(shooterTrooper, targetTrooper, shooterUnit, targetUnit, window, maxAim,
					TFCA + (int) spinnerCABonus.getValue(), (int) spinnerEALBonus.getValue(),
					(int) spinnerPercentBonus.getValue(), 0, gunner.wep);

			
			
			if (chckbxFullAuto.isSelected()) {
				tf.setFullAuto();
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

				if (staticWeapon.equipedTroopers.size() - 1 - incapableTroopers > 0)
					tf.allottedCA += staticWeapon.equipedTroopers.size() - 1 - incapableTroopers;
			}

			tf.spentCA = spentCA;
			tempTF = tf;

			/*
			 * targetedFire = tf; reaction = null; possibleShots = true;
			 */
			/*
			 * lblPossibleShots.setText("Possible Shots: "+(tf.possibleShots-tf.shotsTaken))
			 * ; lblAimTime.setText("Aim Time: "+tf.spentAimTime);
			 * lblTN.setText("Target Number: "+tf.TN);
			 * lblTfSpentCa.setText("TF Spent CA: "+0);
			 */
		} else {

			if (chckbxFullAuto.isSelected()) {
				targetedFire.setFullAuto();
			}

			if (targetedFire.shotsTaken > 0 && !targetedFire.consecutiveShots && !chckbxFullAuto.isSelected()) {
				targetedFire.EAL += 2;
				targetedFire.ALMSum += 2;
				targetedFire.consecutiveShots = true;
			}

			targetedFire.setTargetNumber();

			/*
			 * lblPossibleShots.setText("Possible Shots: "+(targetedFire.possibleShots-
			 * targetedFire.shotsTaken));
			 * lblAimTime.setText("Aim Time: "+targetedFire.spentAimTime);
			 * targetedFire.setTargetNumber();
			 * 
			 * lblTfSpentCa.setText("TF Spent CA: "+targetedFire.spentCA);
			 * lblTN.setText("Target Number: "+targetedFire.TN);
			 */
		}

		// TargetedFire(Trooper shooterTrooper, Trooper targetTrooper, Unit shooterUnit
		// , Unit targetUnit, GameWindow game, int maxAim)
		gunner.wep = trooperWep; 
	}

	public void PCFireGuiUpdates(Trooper gunner) {
		if(comboBoxTargets.getSelectedIndex() < 1)
			return; 

		if(targetedFire == null && tempTF != null) {
			//System.out.println("TF Null, Temp TF Not Null");
			lblPossibleShots.setText("Possible Shots: "+(tempTF.possibleShots-tempTF.shotsTaken));
			lblAimTime.setText("Aim Time: "+tempTF.spentAimTime);
			lblTN.setText("Target Number: "+tempTF.TN);
			lblTfSpentCa.setText("TF Spent CA: "+tempTF.spentCA);
		} else if(targetedFire != null) {
			//System.out.println("TF Not Null");
			//System.out.println("Possible Shots: "+targetedFire.possibleShots+", Shots Taken: "+targetedFire.shotsTaken);
			lblPossibleShots.setText("Possible Shots: "+(targetedFire.possibleShots-targetedFire.shotsTaken));
			lblAimTime.setText("Aim Time: "+targetedFire.spentAimTime);
			lblTN.setText("Target Number: "+targetedFire.TN);
			lblTfSpentCa.setText("TF Spent CA: "+targetedFire.spentCA);
		}

		spinnerAmmunitionLoaded.setValue(staticWeapon.ammoLoaded);
		lblCombatActions.setText("TF CA: "+TFCA);		
		window.conflictLog.addQueuedText();
		window.refreshInitiativeOrder();
		//refreshTargets(); 
	}

	public void PCFire(Trooper gunner) {
		Weapons staticWeapon = unit.staticWeapons.get(selectedWeaponIndex);
		String trooperWep = gunner.wep;
		gunner.wep = staticWeapon.name;

		// Checks for out of ammo
		if (staticWeapon.ammoLoaded == 0) {
			// textPaneTargetedFire.setText("OUT OF AMMO");
			return;
		}

		if (staticWeapon.fullAutoROF == 0 && chckbxFullAuto.isSelected()) {
			window.conflictLog.addNewLineToQueue("This weapon is not full auto capable.");
			return;
		}

		if (comboBoxTargets.getSelectedIndex() < 1)
			return;
		Trooper shooterTrooper = gunner;
		Trooper targetTrooper = findTarget();
		Unit targetUnit = findTrooperUnit(targetTrooper);
		Unit shooterUnit = findTrooperUnit(shooterTrooper);

		int maxAim = 0;

		if (comboBoxAimTime.getSelectedIndex() > 0) {

			maxAim = comboBoxAimTime.getSelectedIndex() - 1;
		}

		setAction(gunner);
		TargetedFire tf = new TargetedFire(shooterTrooper, targetTrooper, shooterUnit, targetUnit, window, maxAim,
				TFCA + (int) spinnerCABonus.getValue(), (int) spinnerEALBonus.getValue(),
				(int) spinnerPercentBonus.getValue(), 0, gunner.wep);

		tf.spentCA = spentCA;

		if (targetedFire == null) {
			targetedFire = tf;
			reaction = null;
			possibleShots = true;
		} else if (!tf.targetTrooper.compareTo(targetedFire.targetTrooper)) {
			targetedFire = tf;
		}

		targetedFire.PCHits = 0;

		if (possibleShots) {

			if (chckbxFullAuto.isSelected()) {
				targetedFire.fullAutoBurst(chckbxSustainedFullAuto.isSelected());
				if (chckbxFreeAction.isSelected() && chckbxSustainedFullAuto.isSelected()) {
					targetedFire.spentCA -= 1;
				} else if (chckbxFreeAction.isSelected()) {
					targetedFire.spentCA -= 2;
				}
			} else {
				targetedFire.shot(comboBoxTargetZone.getSelectedIndex());
				if (chckbxFreeAction.isSelected()) {
					targetedFire.spentCA -= 1;
				}
			}

			//System.out.println("TARGETED FIRE: ");
			//System.out.println("targetedFire.shotsTaken: " + targetedFire.shotsTaken);
			//System.out.println("targetedFire.timeToReaction: " + targetedFire.timeToReaction);
			if (targetedFire.shotsTaken >= targetedFire.timeToReaction && targetedFire.shotsTaken != 0
					&& this.reaction == null && targetTrooper.alive && targetTrooper.conscious
					&& targetTrooper.canAct(window.game)) {
				// React
				//System.out.println("REACTION");
				// ReactionToFireWindow reaction = new ReactionToFireWindow(shooterTrooper,
				// targetTrooper, windowOpenTrooper, gameWindow);
				this.reaction = reaction;

			}
		}

		if(targetedFire.PCHits > 0) {
			ResolveHits resolveHits = new ResolveHits(targetTrooper, targetedFire.PCHits, new Weapons().findWeapon(shooterTrooper.wep), 
					GameWindow.gameWindow.conflictLog, targetTrooper.returnTrooperUnit(GameWindow.gameWindow), shooterUnit, GameWindow.gameWindow);
			
			if(targetedFire.calledShot) {
				resolveHits.calledShot = true; 
				resolveHits.calledShotBounds = targetedFire.calledShotBounds; 
				
			}
			
			if (targetTrooper.returnTrooperUnit(GameWindow.gameWindow).suppression + targetedFire.PCHits < 100) {
				targetTrooper.returnTrooperUnit(GameWindow.gameWindow).suppression++;
			} else {
				targetTrooper.returnTrooperUnit(GameWindow.gameWindow).suppression = 100;
			}
			if (targetTrooper.returnTrooperUnit(GameWindow.gameWindow).organization - targetedFire.PCHits > 0) {
				targetTrooper.returnTrooperUnit(GameWindow.gameWindow).organization--;
			} else {
				targetTrooper.returnTrooperUnit(GameWindow.gameWindow).organization = 0;
			}
			
			resolveHits.performCalculations(GameWindow.gameWindow.game, GameWindow.gameWindow.conflictLog);
		}
		if (targetedFire.possibleShots <= targetedFire.shotsTaken) {
			// Shot ends
			/*
			 * lblPossibleShots.setText("Possible Shots: None");
			 * lblAimTime.setText("Aim Time: N/A"); lblTN.setText("Target Number: N/A");
			 */
			reaction = null;
			possibleShots = false;
			targetedFire = null;
			PCShots(gunner);
		}

		if (chckbxFullAuto.isSelected()) {
			if (staticWeapon.ammoLoaded - staticWeapon.fullAutoROF <= 0) {
				staticWeapon.ammoLoaded = 0;
			} else {
				staticWeapon.ammoLoaded -= staticWeapon.fullAutoROF;
			}

		} else {
			int roll = new Random().nextInt(6) + 1;
			
			if(staticWeapon.ammoLoaded - roll < 0)
				staticWeapon.ammoLoaded = 0; 
			else 
				staticWeapon.ammoLoaded -= roll;
		}

		if (!targetTrooper.alive) {

			if (chckbxMultipleTargets.isSelected()) {

				targetedFire = null;
				possibleShots = true;
				reaction = null;
				PCShots(gunner);
			} else {
				// Performed after swing worker is done
				/*
				 * actionSpent(openUnit, index); openUnit.openNext(true); f.dispose();
				 */
			}

		}

		// setDetails(openTrooper);

		gunner.wep = trooperWep;

		// setDetails(openTrooper);

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

	public int calculateCA(double ms, int isf) {

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

	}

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

			if (gunner == null || trooper.getSkill("Heavy") > gunner.getSkill("Heavy"))
				gunner = trooper;
		}
		//System.out.println("Returning Gunner3, Gunner Name: "+gunner.name);
		return gunner;
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
			gunner.storedAimTime.put(targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1), TFCA - spentCA
					+ gunner.storedAimTime.get(targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1)));
		} else {
			gunner.storedAimTime.clear();
			gunner.storedAimTime.put(targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1), TFCA - spentCA);
		}

	}
	
	public void spendAction() {
		
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
		
	}
}
