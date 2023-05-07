package Conflict;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SwingWorker;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Actions.GrenadeThrow;
import Actions.Launcher;
import Actions.ReactionToFire;
import Actions.ReactionToFireWindow;
import Actions.Spot;
import Actions.TargetedFire;
import CharacterBuilder.CharacterBuilderWindow;
import Company.EditCompany;
import Hexes.Building;
import Hexes.Building.Floor;
import Hexes.Hex;
import Injuries.Explosion;
import Injuries.Injuries;
import Injuries.ManualInjury;
import Injuries.ResolveHits;
import Items.Ammo;
import Items.Armor;
import Items.PCAmmo;
import Items.PersonalShield;
import Items.PersonalShield.ShieldType;
import Items.Weapons;
import Shoot.Shoot;
import Items.Armor.ArmorType;
import Items.Item.ItemType;
import Items.Container;
import Items.Item;
import Actions.PcGrenadeThrow;
import Trooper.IndividualStats;
import Trooper.Skills;
import Trooper.Trooper;
import Unit.EditUnit;
import Unit.Unit;
import UtilityClasses.DiceRoller;
import UtilityClasses.PCUtility;
import UtilityClasses.ShootUtility;
import UtilityClasses.SwingUtility;
import UtilityClasses.TrooperUtility;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class OpenTrooper implements Serializable {

	public Unit targetUnit;
	Shoot shoot;
	public boolean shootReset = true;
	public ArrayList<Trooper> spottedTroopers = new ArrayList<Trooper>();
	public ArrayList<Trooper> targetTroopers = new ArrayList<Trooper>();
	public TargetedFire targetedFire = null;
	public TargetedFire tempTF = null;
	public int TFCA;
	public int spentCA = 0;
	public OpenTrooper windowOpenTrooper;
	public ArrayList<Trooper> meleeTargets = new ArrayList<>();
	public ArrayList<Trooper> grenadeTargets = new ArrayList<>();
	public OpenUnit openUnit;
	public Unit trooperUnit;
	public Trooper openTrooper;
	public Building trooperBuilding;
	public JFrame f;
	public int index;
	public GameWindow gameWindow;
	public ArrayList<ManualInjury> injuries = new ArrayList<ManualInjury>();
	public ArrayList<String> callsigns = new ArrayList<String>();
	public boolean firedWeapons = false;
	public boolean possibleShots = true;
	public ReactionToFireWindow reaction = null;
	private JLabel lblName;
	private JLabel labelRank;
	private JLabel lblDesignation;
	private JLabel lblVet;
	private JLabel labelConscious;
	private JLabel lblP1;
	private JLabel lblP2;
	private JLabel lblWep;
	private JLabel lblAmmo;
	private JTextField textFieldName;
	private JTextField textFieldRank;
	private JTextField textFieldVet;
	private JTextField textFieldWeapon;
	private JSpinner spinnerAmmo;
	private JSpinner spinnerStr;
	private JSpinner spinnerWit;
	private JSpinner spinnerSoc;
	private JSpinner spinnerWil;
	private JSpinner spinnerPer;
	private JSpinner spinnerHlt;
	private JSpinner spinnerAgi;
	private JSpinner spinnerBallance;
	private JSpinner spinnerClimb;
	private JSpinner spinnerComposure;
	private JSpinner spinnerDodge;
	private JSpinner spinnerEndurance;
	private JSpinner spinnerExpression;
	private JSpinner spinnerGrapple;
	private JSpinner spinnerHold;
	private JSpinner spinnerJump;
	private JSpinner spinnerLift;
	private JSpinner spinnerResistPain;
	private JSpinner spinnerSearch;
	private JSpinner spinnerSpot;
	private JSpinner spinnerStealth;
	private JSpinner spinnerCamo;
	private JSpinner spinnerCalm;
	private JSpinner spinnerDiplomacy;
	private JSpinner spinnerBarter;
	private JSpinner spinnerThrow;
	private JSpinner spinnerSwim;
	private JSpinner spinnerNavigation;
	private JSpinner spinnerFirstAid;
	private JSpinner spinnerExplosives;
	private JSpinner spinnerRifle;
	private JSpinner spinnerLauncher;
	private JSpinner spinnerSubgun;
	private JSpinner spinnerHeavy;
	private JSpinner spinnerPistol;
	private JSpinner spinnerComputers;
	private JSpinner spinnerPersuade;
	private JSpinner spinnerIntimidate;
	private JSpinner spinnerDetMotives;
	private JSpinner spinnerTactics;
	private JSpinner spinnerCommand;
	private JSpinner spinnerSkill;
	private JTextPane textPaneNotes;
	private JTextField textFieldRole;
	private JCheckBox chckbxFreeSpotTest;
	private JComboBox comboBoxScanArea;
	private JList listSpotted;
	private JComboBox comboBoxAimTime;
	private JSpinner spinnerEALBonus;
	private JComboBox comboBoxTargets;
	private JTextPane textPaneTargetedFire;
	private JComboBox comboBoxWeapon;
	private JTextField textFieldCallsign;
	private JComboBox comboBoxTargetUnits;
	private JLabel labelAlive;
	private JLabel lblHD;
	private JLabel lblMaxHp;
	private JLabel lblCurrentHp;
	private JSpinner advancedMedicineSpinner;
	private JSpinner spinnerKills;
	private JTextPane textPaneEquipment;
	private JLabel lblMaxShields_1;
	private JLabel lblCurrentShields_1;
	private JButton btnRemoveSpotted;
	private JComboBox comboBoxRemoveSpotted;
	private JButton btnAddSpotted;
	private JComboBox comboBoxAddIndividual;
	private JComboBox comboBoxAddUnit;
	private JComboBox comboBoxSpottingUnits;
	private JComboBox comboBoxMeleeTargets;
	private JComboBox comboBoxMeleeWeapon;
	private JSpinner spinnerMeleeBonus;
	private JComboBox comboBoxGrenade;
	private JSpinner spinnerThrowBonus;
	private JSpinner spinnerGrenadeX;
	private JSpinner spinnerGrenadeY;
	private JComboBox comboBoxGrenadeTargets;
	private JComboBox comboBoxLauncher;
	private JSpinner spinnerLauncherBonus;
	private JSpinner spinnerLauncherY;
	private JSpinner spinnerLauncherX;
	private JComboBox comboBoxAmmoTypeLauncher;
	private JComboBox comboBoxLauncherConcealment;
	private JCheckBox chckbxFreeAction;
	private JCheckBox chckbxReacted;
	private JSpinner spinnerArmor;
	private JSpinner spinnerArmorLeg;
	private JSpinner spinnerArmorArm;
	private JSpinner spinnerArmorHead;
	private JList listInjuries;
	private JLabel labelCTP;
	private JLabel labelRR;
	private JLabel labelTimePassed;
	private JTextField textFieldPen;
	private JTextField textFieldDC;
	private JComboBox comboBoxOF;
	private JSpinner spinnerCapacity;
	private JSpinner spinnerEncumberance;
	private JButton buttonExport;
	private JLabel lblIncipactationTime;
	private JSpinner spinnerSpotHexX;
	private JSpinner spinnerSpotHexY;
	private JLabel lblCombatActions;
	public JLabel lblPossibleShots;
	private JLabel lblAimTime;
	private JLabel lblTN;
	private JCheckBox chckbxFullAuto;
	private JCheckBox chckbxSustainedFullAuto;
	private JComboBox comboBoxTargetZone;
	private JCheckBox chckbxMultipleTargets;
	private JSpinner spinnerCABonus;
	private JSpinner spinnerPercentBonus;
	private JLabel lblTfSpentCa;
	private JButton btnRollSpot;
	private JComboBox comboBoxPassActivity;
	private JLabel lblFatiguePoints;
	private JLabel lblAV;
	private JComboBox comboBoxStance;
	private JCheckBox chckbxManualStance;
	private JCheckBox chckbxWeaponLights;
	private JCheckBox chckbxNvgs;
	private JSpinner spinnerNVGGen;
	private JButton btnAddNvgs;
	private JCheckBox chckbxIrLaser;
	private JCheckBox chckbxLaser;
	private JCheckBox chckbxThermals;
	private JList listSpottedUnitsArray;
	private JSpinner spinnerTimeMortallyWounded;
	private JSpinner spinnerTimeUnconscious;
	private JCheckBox checkBoxStabalized;
	private JCheckBox checkBoxMortalWound;
	private JSpinner spinnerDisabledLegs;
	private JSpinner spinnerDisabledArms;
	private JSpinner spinnerLegs;
	private JSpinner spinnerArms;
	private JCheckBox checkBoxAwake;
	private JCheckBox checkBoxAlive;
	private JSpinner spinnerCurrentHp;
	private JSpinner spinnerMaxHP;
	private JSpinner spinnerShieldChance;
	private JSpinner spinnerCurrentShields;
	private JSpinner spinnerMaxShields;
	private JSpinner smallUnitTacticsSpinner;
	private JSpinner negotiationsSpinner;
	private JSpinner longRangeOpticsSpinner;
	private JSpinner arSystemsSpinner;
	private JSpinner rawPowerSpinner;
	private JSpinner authoritySpinner;
	private JSpinner assualtOperationsSpinner;
	private JSpinner akSystemsSpinner;
	private JSpinner silentOperationsSpinner;
	private JSpinner reloadDrillsSpinner;
	private JSpinner triggerDisciplineSpinner;
	private JSpinner recoilControlSpinner;
	private JSpinner fighterSpinner;
	private JSpinner covertMovementSpinner;
	private JSpinner cleanOperationsSpinner;
	private JSpinner spinnerSpottingDifficulty;
	private JCheckBox checkBoxUnspottable;
	private JSpinner spinnerPhysiciansSkill;
	private JSpinner spinnerAidMod;
	private JSpinner spinnerRRMod;
	private JCheckBox checkBoxFirstAid;
	private JSpinner spinnerConsecutiveEALBonus;
	private JSpinner spinner2YardHexRange;
	private JSpinner spinnerKO;
	private JSpinner spinnerFighterRanks;
	private JTextField textFieldHitLocationLower;
	private JTextField textFieldHitLocationUpper;
	private JTextField textFieldSecondHitLocationLower;
	private JTextField textFieldSecondHitLocationUpper;
	private JSpinner spinnerIonDamage;
	private JSpinner spinnerPCSize;
	private JSpinner spinnerThrowEALBonus;
	private JSpinner spinnerMagnification;
	private JCheckBox chckbxHoming;
	private JSpinner spinnerTargetRoom;
	private JComboBox comboBoxBuilding;
	private JSpinner spinnerPD;
	private JSpinner spinnerTargetFloor;
	private JList shieldList;
	private JList armorList;
	private JComboBox comboBoxArmor;
	private JComboBox comboBoxPersonalShield;
	private JSpinner spinnerCurrentShieldStrength;
	private JSpinner spinnerItemCount;
	private JList listItems;
	private JList listInventory;
	private JCheckBox chkbxOverRideInventory;
	private JLabel lblArmorPage_1;
	private JLabel lblEncumberance;

	/**
	 * Launch the application.
	 */
	public OpenTrooper(Trooper trooper, Unit unit, OpenUnit window, int index, boolean openNext) {
		final JFrame f = new JFrame("Open Trooper");
		f.setSize(917, 820);
		f.getContentPane().setLayout(null);
		this.f = f;
		openUnit = window;
		trooperUnit = unit;
		gameWindow = openUnit.gameWindow;
		openTrooper = trooper;
		windowOpenTrooper = this;

		this.index = index;
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

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		JPanel panelIndividual = new JPanel();
		panelIndividual.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Details", null, panelIndividual, null);

		JTabbedPane tabbedPane2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane2.setBounds(0, 48, 1000, 840);

		lblP1 = new JLabel("P1:");
		lblP1.setBounds(10, 11, 53, 31);
		lblP1.setForeground(Color.WHITE);
		lblP1.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblP2 = new JLabel("P2:");
		lblP2.setBounds(69, 11, 53, 31);
		lblP2.setForeground(Color.WHITE);
		lblP2.setFont(new Font("Calibri", Font.PLAIN, 15));

		JButton btnPass = new JButton("Pass");
		btnPass.setBounds(655, 14, 86, 23);
		btnPass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Trooper trooper = window.openUnit.troopers.get(index);
				float time;

				if (gameWindow.game.getPhase() == 1) {
					time = 60 / trooper.P1;
				} else {
					time = 60 / trooper.P2;
				}
				// Checks if AP Full
				// Increases spent AP
				if (window.gameWindow.game.getPhase() == 1) {
					if (trooper.spentPhase1 != window.gameWindow.game.getCurrentAction()) {
						if (comboBoxPassActivity.getSelectedItem().toString() == "Hard") {
							trooper.fatigueSystem.AddStrenuousActivityTime(time);
						} else if (comboBoxPassActivity.getSelectedItem().toString() == "Light") {
							trooper.fatigueSystem.AddLightActivityTime(time);
						} else {
							trooper.fatigueSystem.AddRecoveryTime(time);
						}

						trooper.spentPhase1 += 1;
					}
				} else {
					if (trooper.spentPhase2 != window.gameWindow.game.getCurrentAction()) {

						if (comboBoxPassActivity.getSelectedItem().toString() == "Hard") {
							trooper.fatigueSystem.AddStrenuousActivityTime(time);
						} else if (comboBoxPassActivity.getSelectedItem().toString() == "Light") {
							trooper.fatigueSystem.AddLightActivityTime(time);
						} else {
							trooper.fatigueSystem.AddRecoveryTime(time);
						}

						trooper.spentPhase2 += 1;
					}
				}

				window.openUnit.refreshIndividuals();
				if (openNext) {
					window.openNext(true);
				}
				saveChanges();
				f.dispose();
			}
		});

		JButton btnHunkerDown = new JButton("Hunker Down");
		btnHunkerDown.setBounds(501, 14, 144, 23);
		btnHunkerDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						if (trooper.HD)
							trooper.HD = false;
						else
							trooper.hunkerDown(gameWindow);

						if (window.gameWindow.game.getPhase() == 1) {
							trooper.spentPhase1 += 1;
						} else {
							trooper.spentPhase2 += 1;
						}
						return null;
					}

					@Override
					protected void done() {
						window.openUnit.refreshIndividuals();
						if (openNext) {
							window.openNext(true);
						}
						f.dispose();
					}

				};

				worker.execute();
				
				

			}
		});

		JButton button_1 = new JButton("Save Changes");
		button_1.setBounds(377, 13, 118, 25);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saveChanges();
			}
		});

		JPanel panelStats = new JPanel();
		panelStats.setBackground(Color.DARK_GRAY);
		tabbedPane2.addTab("Stats", null, panelStats, null);

		spinnerEncumberance = new JSpinner();
		spinnerEncumberance.setBounds(677, 434, 47, 20);
		panelStats.add(spinnerEncumberance);

		JLabel lblEncum_1 = new JLabel("Capacity:");
		lblEncum_1.setForeground(Color.WHITE);
		lblEncum_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblEncum_1.setDoubleBuffered(true);
		lblEncum_1.setBounds(737, 429, 72, 31);
		panelStats.add(lblEncum_1);

		spinnerCapacity = new JSpinner();
		spinnerCapacity.setBounds(804, 434, 47, 20);
		panelStats.add(spinnerCapacity);

		JLabel lblBasic = new JLabel("Basic");
		lblBasic.setBounds(45, 44, 53, 31);
		lblBasic.setFont(new Font("Calibri", Font.PLAIN, 25));
		lblBasic.setForeground(Color.WHITE);

		lblName = new JLabel("Name:");
		lblName.setBounds(45, 81, 226, 31);
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 15));

		labelRank = new JLabel("Rank:");
		labelRank.setBounds(45, 118, 226, 31);
		labelRank.setForeground(Color.WHITE);
		labelRank.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblDesignation = new JLabel("Role:");
		lblDesignation.setBounds(45, 155, 226, 31);
		lblDesignation.setForeground(Color.WHITE);
		lblDesignation.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblVet = new JLabel("Vet:");
		lblVet.setBounds(45, 197, 226, 31);
		lblVet.setForeground(Color.WHITE);
		lblVet.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblInjuries = new JLabel("Equipment");
		lblInjuries.setBounds(501, 426, 110, 31);
		lblInjuries.setForeground(Color.WHITE);
		lblInjuries.setFont(new Font("Calibri", Font.PLAIN, 25));

		JScrollPane scrollPaneInjuries = new JScrollPane();
		scrollPaneInjuries.setBounds(490, 468, 364, 205);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(466, 44, 105, 31);
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Calibri", Font.PLAIN, 25));

		labelConscious = new JLabel("Conscious:");
		labelConscious.setBounds(466, 81, 112, 31);
		labelConscious.setForeground(Color.WHITE);
		labelConscious.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblSpotted = new JLabel("Spotted");
		lblSpotted.setBounds(45, 426, 105, 31);
		lblSpotted.setForeground(Color.WHITE);
		lblSpotted.setFont(new Font("Calibri", Font.PLAIN, 25));

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(45, 468, 427, 205);

		labelAlive = new JLabel("Alive:");
		labelAlive.setBounds(466, 116, 112, 31);
		labelAlive.setForeground(Color.WHITE);
		labelAlive.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblHD = new JLabel("HD: ");
		lblHD.setBounds(466, 155, 112, 31);
		lblHD.setForeground(Color.WHITE);
		lblHD.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblHD.setDoubleBuffered(true);

		JLabel lblHealth = new JLabel("Health");
		lblHealth.setBounds(309, 44, 105, 31);
		lblHealth.setForeground(Color.WHITE);
		lblHealth.setFont(new Font("Calibri", Font.PLAIN, 25));

		lblMaxHp = new JLabel("Max HP:");
		lblMaxHp.setBounds(309, 81, 105, 31);
		lblMaxHp.setForeground(Color.WHITE);
		lblMaxHp.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblCurrentHp = new JLabel("Current HP:");
		lblCurrentHp.setBounds(309, 116, 119, 31);
		lblCurrentHp.setForeground(Color.WHITE);
		lblCurrentHp.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblMaxShields_1 = new JLabel("Max Shields: 0");
		lblMaxShields_1.setBounds(309, 153, 119, 31);
		lblMaxShields_1.setForeground(Color.WHITE);
		lblMaxShields_1.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblCurrentShields_1 = new JLabel("Current Shields: 0");
		lblCurrentShields_1.setBounds(309, 188, 153, 31);
		lblCurrentShields_1.setForeground(Color.WHITE);
		lblCurrentShields_1.setFont(new Font("Calibri", Font.PLAIN, 15));

		textPaneEquipment = new JTextPane();
		textPaneEquipment.setBackground(Color.DARK_GRAY);
		textPaneEquipment.setForeground(Color.WHITE);
		textPaneEquipment.setCaretColor(Color.WHITE);
		scrollPaneInjuries.setViewportView(textPaneEquipment);

		listSpotted = new JList();
		listSpotted.setBackground(Color.DARK_GRAY);
		listSpotted.setForeground(Color.WHITE);
		scrollPane_3.setViewportView(listSpotted);
		panelStats.setLayout(null);
		panelStats.add(lblSpotted);
		panelStats.add(scrollPane_3);
		panelStats.add(lblInjuries);
		panelStats.add(scrollPaneInjuries);
		panelStats.add(lblHD);
		panelStats.add(lblHealth);
		panelStats.add(lblMaxHp);
		panelStats.add(lblCurrentHp);
		panelStats.add(lblMaxShields_1);
		panelStats.add(lblCurrentShields_1);
		panelStats.add(lblBasic);
		panelStats.add(lblStatus);
		panelStats.add(lblName);
		panelStats.add(lblVet);
		panelStats.add(lblDesignation);
		panelStats.add(labelRank);
		panelStats.add(labelConscious);
		panelStats.add(labelAlive);

		JLabel lblAddSpotted = new JLabel("Add Spotted");
		lblAddSpotted.setForeground(Color.WHITE);
		lblAddSpotted.setFont(new Font("Calibri", Font.PLAIN, 25));
		lblAddSpotted.setBounds(45, 239, 162, 31);
		panelStats.add(lblAddSpotted);

		JLabel lblRemoveSpotted = new JLabel("Remove Spotted");
		lblRemoveSpotted.setForeground(Color.WHITE);
		lblRemoveSpotted.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblRemoveSpotted.setBounds(45, 352, 226, 31);
		panelStats.add(lblRemoveSpotted);

		comboBoxRemoveSpotted = new JComboBox();
		comboBoxRemoveSpotted.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxRemoveSpotted.setSelectedIndex(0);
		comboBoxRemoveSpotted.setBounds(45, 390, 143, 20);
		panelStats.add(comboBoxRemoveSpotted);

		btnRemoveSpotted = new JButton("Remove");
		btnRemoveSpotted.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (comboBoxRemoveSpotted.getSelectedIndex() > 0) {

					// Loops through spotted actions, and spotted indiviudals until the correct
					// individual is found
					// Removes individual
					// Refreshes spotted

					Trooper removeTrooper = spottedTroopers.get(comboBoxRemoveSpotted.getSelectedIndex() - 1);

					for (int i = 0; i < trooper.spotted.size(); i++) {

						for (int x = 0; x < trooper.spotted.get(i).spottedIndividuals.size(); x++) {
							if (trooper.spotted.get(i).spottedIndividuals.get(x).compareTo(removeTrooper)) {
								trooper.spotted.get(i).spottedIndividuals.remove(x);
							}
						}

					}

				}

				// Refreshes spotted
				openTrooper.spotted = trooper.spotted;
				refreshSpotted(trooper);

			}
		});
		btnRemoveSpotted.setBounds(205, 389, 89, 23);
		panelStats.add(btnRemoveSpotted);

		JLabel lblUnit = new JLabel("Unit");
		lblUnit.setForeground(Color.WHITE);
		lblUnit.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblUnit.setBounds(45, 271, 143, 31);
		panelStats.add(lblUnit);

		JLabel lblIndividual = new JLabel("Individual");
		lblIndividual.setForeground(Color.WHITE);
		lblIndividual.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblIndividual.setBounds(205, 271, 143, 31);
		panelStats.add(lblIndividual);

		comboBoxAddIndividual = new JComboBox();
		comboBoxAddIndividual.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxAddIndividual.setSelectedIndex(0);
		comboBoxAddIndividual.setBounds(205, 302, 89, 20);
		panelStats.add(comboBoxAddIndividual);

		comboBoxAddUnit = new JComboBox();
		comboBoxAddUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Clears comboBox
				if (comboBoxAddIndividual.getItemCount() > 1) {
					comboBoxAddIndividual.removeAllItems();
					comboBoxAddIndividual.addItem("None");
					comboBoxAddIndividual.setSelectedIndex(0);
				}

				if (comboBoxAddUnit.getSelectedIndex() > 0) {
					// Gets selected unit
					Unit selectedUnit = gameWindow.initiativeOrder.get(comboBoxAddUnit.getSelectedIndex() - 1);

					// Loops through unit
					for (int i = 0; i < selectedUnit.getSize(); i++) {
						Trooper trooper = selectedUnit.getTroopers().get(i);
						comboBoxAddIndividual.addItem(i + 1 + ": " + trooper.name);

					}
				}

			}
		});

		comboBoxAddUnit.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxAddUnit.setSelectedIndex(0);
		comboBoxAddUnit.setBounds(45, 302, 143, 20);
		panelStats.add(comboBoxAddUnit);

		btnAddSpotted = new JButton("Add");
		btnAddSpotted.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (comboBoxAddIndividual.getSelectedIndex() > 0) {
					comboBoxAddIndividual.getSelectedIndex();
					Unit addUnit = gameWindow.initiativeOrder.get(comboBoxAddUnit.getSelectedIndex() - 1);
					Trooper addTrooper = addUnit.getTroopers().get(comboBoxAddIndividual.getSelectedIndex() - 1);

					// System.out.println("addUnit: "+addUnit);
					// System.out.println("addTrooper: "+addTrooper);

					Spot spotAction = new Spot();
					spotAction.spottedUnit = addUnit;
					spotAction.spottedIndividuals.add(addTrooper);

					boolean duplicateUnit = false;
					boolean duplicateTrooper = false;

					// Checks if trooper already spotted
					// Loops through current spotted units and individuals
					for (int i = 0; i < trooper.spotted.size(); i++) {
						Unit spottedUnit = trooper.spotted.get(i).spottedUnit;

						// Checks if duplicate unit
						if (spottedUnit.compareTo(addUnit)) {
							duplicateUnit = true;
						}

						// Loops through spotted unit's troopers
						for (int x = 0; x < spottedUnit.getSize(); x++) {

							// Loops through all spotted individuals
							for (int j = 0; j < trooper.spotted.get(i).spottedIndividuals.size(); j++) {

								Trooper spottedTrooper = trooper.spotted.get(i).spottedIndividuals.get(j);

								// Checks if duplicate trooper
								if (spottedTrooper.compareTo(addTrooper)) {
									duplicateTrooper = true;
								}
							}

						}

					}

					// If the trooper is a duplicate and already spotted, prints out a message to
					// the log
					// Otherwise, proceedes with adding the trooper to the spotted array
					// If new unit, adds new spot action
					// If duplicate unit, finds the unit, and adds the trooper
					if (duplicateTrooper) {
						gameWindow.conflictLog.addNewLine("Select a trooper that is not already spotted...");
					} else {

						if (duplicateUnit) {
							for (int i = 0; i < trooper.spotted.size(); i++) {
								Unit spottedUnit = trooper.spotted.get(i).spottedUnit;

								if (spottedUnit.compareTo(addUnit)) {
									trooper.spotted.get(i).spottedIndividuals.add(addTrooper);
								}

							}

						} else {
							trooper.spotted.add(spotAction);
						}

					}

					// Refreshes spotted
					openTrooper.spotted = trooper.spotted;
					refreshSpotted(trooper);

				} else {
					gameWindow.conflictLog.addNewLine("Select a unit that contains individuals...");
				}

				// Refreshes spotted
				openTrooper.spotted = trooper.spotted;
				refreshSpotted(trooper);

			}
		});
		btnAddSpotted.setBounds(309, 301, 89, 23);
		panelStats.add(btnAddSpotted);

		JButton btnAddWholeUnit = new JButton("Add Whole Unit");
		btnAddWholeUnit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (comboBoxAddUnit.getSelectedIndex() > 0) {
					Unit addUnit = gameWindow.initiativeOrder.get(comboBoxAddUnit.getSelectedIndex() - 1);

					// System.out.println("addUnit: "+addUnit);

					boolean found = false;

					// Loops through spotted actions, checks if people have been spotted from this
					// unit already
					if (trooper.spotted.size() > 0) {
						for (int i = 0; i < trooper.spotted.size(); i++) {

							if (trooper.spotted.get(i).spottedUnit.compareTo(addUnit)) {
								found = true;
							}

						}
					}

					if (!found) {

						Spot spot = new Spot();
						spot.spottedUnit = addUnit;

						// Loops through units troopers, adds them to a new spot action
						for (int i = 0; i < addUnit.getTroopers().size(); i++) {

							spot.spottedIndividuals.add(addUnit.getTroopers().get(i));

						}

						trooper.spotted.add(spot);

					} else {

						// Finds spot action
						for (int i = 0; i < trooper.spotted.size(); i++) {

							if (trooper.spotted.get(i).spottedUnit.compareTo(addUnit)) {

								// Loops through target unit's troopers, if duplicate trooper, skip trooper,
								// otherwise add trooper to the existing spot action
								for (int x = 0; x < trooper.spotted.get(i).spottedUnit.getTroopers().size(); x++) {
									trooper.spotted.get(i).spottedIndividuals
											.add(trooper.spotted.get(i).spottedUnit.getTroopers().get(x));
								}

							}

						}

					}

					// Refreshes spotted
					openTrooper.spotted = trooper.spotted;
					refreshSpotted(trooper);

				}

				// Refreshes spotted
				openTrooper.spotted = trooper.spotted;
				refreshSpotted(trooper);

			}
		});
		btnAddWholeUnit.setBounds(408, 301, 124, 23);
		panelStats.add(btnAddWholeUnit);

		JLabel lblInjuries_1 = new JLabel("Injuries");
		lblInjuries_1.setForeground(Color.WHITE);
		lblInjuries_1.setFont(new Font("Calibri", Font.PLAIN, 25));
		lblInjuries_1.setBounds(581, 44, 105, 31);
		panelStats.add(lblInjuries_1);

		comboBoxAimTime = new JComboBox();
		comboBoxAimTime.setBounds(329, 270, 87, 23);
		comboBoxAimTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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
		comboBoxAimTime.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

			}
		});
		comboBoxAimTime.setModel(new DefaultComboBoxModel(new String[] { "Auto", "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "10", "11", "12", "13", "14", "15" }));
		comboBoxAimTime.setSelectedIndex(0);

		JPanel panelActions = new JPanel();
		panelActions.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Actions", null, panelActions, null);

		btnRollSpot = new JButton("Roll Spot");
		btnRollSpot.setBounds(237, 149, 101, 23);
		btnRollSpot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRollSpot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println("Roll Spot Pass");
				/*
				 * String targetCallsign = textFieldTargetUnit.getText();
				 * spotTest(targetCallsign, window, trooper, unit, index); if (openNext) {
				 * window.openNext(true); }
				 * 
				 * f.dispose();
				 */

				// Loops through all signs, performs spotting test
				for (int i = 0; i < callsigns.size(); i++) {
					spotTest(callsigns.get(i), window, trooper, unit, index);
				}

				// Clears list
				listSpottedUnitsArray.removeAll();
				callsigns.clear();

				DefaultListModel listSpottedUnits = new DefaultListModel();

				for (int i = 0; i < callsigns.size(); i++) {
					listSpottedUnits.addElement(callsigns.get(i));

				}

				listSpottedUnitsArray.setModel(listSpottedUnits);

				// If not a free test
				if (!chckbxFreeSpotTest.isSelected()) {
					// Opens next window
					if (openNext) {
						window.openNext(true);
					}

					// Closes window
					f.dispose();
				}

			}
		});

		JLabel lblSpot_1 = new JLabel("Spot");
		lblSpot_1.setBounds(81, 74, 53, 31);
		lblSpot_1.setForeground(Color.WHITE);
		lblSpot_1.setFont(new Font("Calibri", Font.PLAIN, 25));

		chckbxFreeSpotTest = new JCheckBox("Free Spot Test");
		chckbxFreeSpotTest.setBounds(344, 148, 101, 25);
		chckbxFreeSpotTest.setFont(new Font("Calibri", Font.PLAIN, 13));
		chckbxFreeSpotTest.setForeground(Color.WHITE);
		chckbxFreeSpotTest.setBackground(Color.DARK_GRAY);

		comboBoxScanArea = new JComboBox();
		comboBoxScanArea.setBounds(348, 122, 86, 20);
		comboBoxScanArea
				.setModel(new DefaultComboBoxModel(new String[] { "60 Degrees", "180 Degrees", "20 Yard Hex" }));

		JLabel lblSustainedFire = new JLabel("Targeted Fire");
		lblSustainedFire.setBounds(79, 212, 148, 31);
		lblSustainedFire.setForeground(Color.WHITE);
		lblSustainedFire.setFont(new Font("Calibri", Font.PLAIN, 25));

		comboBoxTargets = new JComboBox();
		comboBoxTargets.setBounds(14, 270, 163, 23);
		comboBoxTargets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (comboBoxTargets.getSelectedIndex() <= 0)
					return;

				// System.out.println("Targets Size: "+targetTroopers.size()+", Target Name:
				// "+findTarget().number+" "+findTarget().name);

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						// System.out.println("Target Changed Shots1");
						// PCShots();
						// System.out.println("Target Changed Shots2");

						try {
							String wepName = trooper.wep;
							int ammoIndex = -1;
							if (comboBoxLauncher.getSelectedIndex() > 0) {
								wepName = comboBoxLauncher.getSelectedItem().toString();
								ammoIndex = comboBoxAmmoTypeLauncher.getSelectedIndex();
								if (ammoIndex < 0) {
									GameWindow.gameWindow.conflictLog.addNewLineToQueue("Select valid ammo");
									return null;
								}
							}

							shoot = ShootUtility.setTarget(unit,
									targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1)
											.returnTrooperUnit(gameWindow),
									shoot, trooper, targetTroopers.get(comboBoxTargets.getSelectedIndex() - 1), wepName,
									(ammoIndex == -1 ? ShootUtility.getPcAmmoIndex(trooper) : ammoIndex));
							
							if(shootReset) {
								shoot.previouslySpentCa = 0; 
								shoot.spentCombatActions = 0;
							}

							if (comboBoxAimTime.getSelectedIndex() == 0)
								shoot.autoAim();

							if (comboBoxTargetZone.getSelectedIndex() > 0) {
								setCalledShotBounds();
							}
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
		});
		comboBoxTargets.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				/*
				 * comboBoxAimTime.setSelectedIndex(0); targetedFire = null; possibleShots =
				 * true; reaction = null;
				 * 
				 * if(comboBoxTargets.getSelectedIndex() == 0) return;
				 * 
				 * SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
				 * 
				 * @Override protected Void doInBackground() throws Exception {
				 * 
				 * System.out.println("Target Changed Shots1"); PCShots();
				 * System.out.println("Target Changed Shots2");
				 * 
				 * return null; }
				 * 
				 * @Override protected void done() {
				 * 
				 * PCFireGuiUpdates();
				 * 
				 * }
				 * 
				 * };
				 * 
				 * worker.execute();
				 */

			}
		});

		comboBoxTargets.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxTargets.setSelectedIndex(0);

		JLabel lblTagetIndividual = new JLabel("Taget Individual: ");
		lblTagetIndividual.setBounds(25, 244, 125, 23);
		lblTagetIndividual.setForeground(Color.WHITE);
		lblTagetIndividual.setFont(new Font("Calibri", Font.PLAIN, 15));

		comboBoxWeapon = new JComboBox();
		comboBoxWeapon.setBounds(187, 270, 136, 23);
		comboBoxWeapon.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				if (comboBoxWeapon.getSelectedIndex() <= 0)
					return;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {
						Weapons weapon = new Weapons();
						weapon = weapon.findWeapon(comboBoxWeapon.getSelectedItem().toString());

						// array of shots size
						int size = weapon.getTargetedROF() + 1;
						String[] shots = new String[size];
						shots[0] = "0";

						for (int i = 0; i < shots.length - 1; i++) {
							shots[i + 1] = String.valueOf(i + 1);
						}

						DefaultComboBoxModel model = new DefaultComboBoxModel(shots);

						if (shoot != null) {
							shoot.updateWeapon(weapon.name);
							shoot.recalc();
							if (comboBoxTargetZone.getSelectedIndex() > 0) {
								setCalledShotBounds();
							}
						}

						comboBoxLauncher.setSelectedIndex(0);

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
		comboBoxWeapon.setModel(new DefaultComboBoxModel(
				new String[] { "None", "DC15A", "DC15A-ion", "DC15LE", "DC15X", "DC20", "DC15S", "DC17m", "DC17 Sniper",
						"Z6", "Westar M5", "M1", "E5", "E5S", "E5C", "MA37", "M392 DMR", "BR55", "M739 SAW", "M6G",
						"SRS99", "Type-51 Carbine", "Type-25 Rifle", "Type-25 Pistol", "Type-50 SRS", "Type-51 DER" }));
		comboBoxWeapon.setSelectedIndex(0);

		JLabel lblWeapon_1 = new JLabel("Weapon:");
		lblWeapon_1.setBounds(187, 244, 114, 23);
		lblWeapon_1.setForeground(Color.WHITE);
		lblWeapon_1.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblShots = new JLabel("Starting Aim T:");
		lblShots.setBounds(302, 244, 114, 23);
		lblShots.setForeground(Color.WHITE);
		lblShots.setFont(new Font("Calibri", Font.PLAIN, 15));

		JButton btnFire = new JButton("Fire");
		btnFire.setBounds(659, 269, 87, 23);
		btnFire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (shoot == null)
					return;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						try {
							System.out.println("Shoot");

							if (comboBoxTargetUnits.getSelectedIndex() > 0)
								shoot.suppressiveFire(shoot.wep.suppressiveROF);
							else if (chckbxFullAuto.isSelected()) {
								shoot.burst();
								shoot.suppressiveFire(shoot.wep.suppressiveROF / 2 + DiceRoller.randInt(1, 3));
							} else {
								shoot.shot(chckbxHoming.isSelected());
								shoot.suppressiveFire(shoot.wep.suppressiveROF / 2 + DiceRoller.randInt(1, 3));
							}
							System.out.println("Open Trooper Shoot");
							GameWindow.gameWindow.conflictLog.addNewLineToQueue("Results: " + shoot.shotResults);
						} catch (Exception e) {
							e.printStackTrace();
						}

						shootReset = false;

						return null;
					}

					@Override
					protected void done() {

						// fire(window, unit, index, f, false);
						if (openNext && !chckbxFreeAction.isSelected()
								&& shoot.spentCombatActions >= shoot.shooter.combatActions) {
							actionSpent(window, index);
							window.openNext(true);
							f.dispose();
						}

						refreshInventory();
						GameWindow.gameWindow.conflictLog.addQueuedText();
						guiUpdates();

						if (shoot.target == null || !shoot.target.alive || !shoot.target.conscious || shoot.target.HD) {
							refreshTargets();
						}

					}

				};

				worker.execute();

			}
		});
		btnFire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});

		JLabel lblEALBonus = new JLabel("% Bonus:");
		lblEALBonus.setBounds(625, 182, 74, 31);
		lblEALBonus.setForeground(Color.WHITE);
		lblEALBonus.setFont(new Font("Calibri", Font.PLAIN, 15));

		spinnerEALBonus = new JSpinner();
		spinnerEALBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerEALBonus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		spinnerEALBonus.setBounds(541, 220, 74, 20);

		textPaneTargetedFire = new JTextPane();
		textPaneTargetedFire.setBounds(10, 343, 6, 64);
		textPaneTargetedFire.setForeground(Color.WHITE);
		textPaneTargetedFire.setFont(new Font("Calibri", Font.PLAIN, 13));
		textPaneTargetedFire.setBackground(Color.DARK_GRAY);

		JLabel lblSuppressiveFire = new JLabel("Suppressive Fire");
		lblSuppressiveFire.setBounds(81, 300, 221, 31);
		lblSuppressiveFire.setForeground(Color.WHITE);
		lblSuppressiveFire.setFont(new Font("Calibri", Font.PLAIN, 18));

		JLabel lblTagetUnit_1 = new JLabel("Taget Unit: ");
		lblTagetUnit_1.setBounds(26, 325, 125, 23);
		lblTagetUnit_1.setForeground(Color.WHITE);
		lblTagetUnit_1.setFont(new Font("Calibri", Font.PLAIN, 15));

		comboBoxTargetUnits = new JComboBox();
		comboBoxTargetUnits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBoxTargets.getSelectedIndex() != 0 || comboBoxTargetUnits.getSelectedIndex() <= 0)
					return;

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						String wepName = trooper.wep;
						int ammoIndex = -1;
						if (comboBoxLauncher.getSelectedIndex() > 0) {
							wepName = comboBoxLauncher.getSelectedItem().toString();
							ammoIndex = comboBoxAmmoTypeLauncher.getSelectedIndex();
							if (ammoIndex < 0) {
								GameWindow.gameWindow.conflictLog.addNewLineToQueue("Select valid ammo");
								return null;
							}
						}

						shoot = ShootUtility.setTargetUnit(unit,
								unit.lineOfSight.get(comboBoxTargetUnits.getSelectedIndex() - 1), shoot, trooper,
								wepName, (ammoIndex == -1 ? ShootUtility.getPcAmmoIndex(trooper) : ammoIndex));

						if (shootReset) {
							shoot.previouslySpentCa = 0;
							shoot.spentCombatActions = 0;
						}

						if (comboBoxAimTime.getSelectedIndex() == 0)
							shoot.autoAim();

						if (shoot == null)
							System.out.println("Shoot is null");

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
		comboBoxTargetUnits.setBounds(10, 345, 136, 20);
		comboBoxTargetUnits.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxTargetUnits.setSelectedIndex(0);
		panelActions.setLayout(null);
		panelActions.add(lblSpot_1);
		panelActions.add(lblSuppressiveFire);
		panelActions.add(btnRollSpot);
		panelActions.add(comboBoxScanArea);
		panelActions.add(chckbxFreeSpotTest);
		panelActions.add(lblSustainedFire);
		panelActions.add(lblTagetUnit_1);
		panelActions.add(comboBoxTargetUnits);
		panelActions.add(textPaneTargetedFire);
		panelActions.add(lblTagetIndividual);
		panelActions.add(comboBoxTargets);
		panelActions.add(lblWeapon_1);
		panelActions.add(lblShots);
		panelActions.add(comboBoxWeapon);
		panelActions.add(comboBoxAimTime);
		panelActions.add(lblEALBonus);
		panelActions.add(spinnerEALBonus);
		panelActions.add(btnFire);

		JLabel lblSpottingUnits = new JLabel("Spotting Units");
		lblSpottingUnits.setBounds(560, 74, 369, 31);
		lblSpottingUnits.setForeground(Color.WHITE);
		lblSpottingUnits.setFont(new Font("Calibri", Font.PLAIN, 25));
		panelActions.add(lblSpottingUnits);

		comboBoxSpottingUnits = new JComboBox();
		comboBoxSpottingUnits.setBounds(91, 121, 136, 23);
		comboBoxSpottingUnits.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxSpottingUnits.setSelectedIndex(0);
		panelActions.add(comboBoxSpottingUnits);

		JButton btnAddUnit = new JButton("Add Unit");
		btnAddUnit.setBounds(237, 120, 101, 23);
		btnAddUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddUnit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

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
		panelActions.add(btnAddUnit);

		JLabel lblAddUnit = new JLabel("Add Unit: ");
		lblAddUnit.setBounds(91, 94, 70, 31);
		lblAddUnit.setForeground(Color.WHITE);
		lblAddUnit.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblAddUnit);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(444, 121, 101, 23);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				listSpottedUnitsArray.removeAll();
				callsigns.clear();

				DefaultListModel listSpottedUnits = new DefaultListModel();

				for (int i = 0; i < callsigns.size(); i++) {
					listSpottedUnits.addElement(callsigns.get(i));

				}

				listSpottedUnitsArray.setModel(listSpottedUnits);

			}
		});
		panelActions.add(btnClear);

		JLabel lblCloseCombat = new JLabel("Close Combat:");
		lblCloseCombat.setBounds(81, 375, 221, 31);
		lblCloseCombat.setForeground(Color.WHITE);
		lblCloseCombat.setFont(new Font("Calibri", Font.PLAIN, 18));
		panelActions.add(lblCloseCombat);

		JLabel label_13 = new JLabel("Taget Individual: ");
		label_13.setBounds(24, 395, 121, 31);
		label_13.setForeground(Color.WHITE);
		label_13.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(label_13);

		JLabel lblMeleeWeapon = new JLabel("Melee Weapon:");
		lblMeleeWeapon.setBounds(151, 395, 136, 31);
		lblMeleeWeapon.setForeground(Color.WHITE);
		lblMeleeWeapon.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblMeleeWeapon);

		JLabel label_14 = new JLabel("Other Bonus:");
		label_14.setBounds(293, 395, 87, 31);
		label_14.setForeground(Color.WHITE);
		label_14.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(label_14);

		spinnerMeleeBonus = new JSpinner();
		spinnerMeleeBonus.setBounds(293, 426, 74, 20);
		panelActions.add(spinnerMeleeBonus);

		JButton button_2 = new JButton("Fire");
		button_2.setBounds(386, 425, 59, 23);
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println("Pass 2556");
				meleeAttack(openUnit, openTrooper);
				if (openNext && !chckbxFreeAction.isSelected()) {
					window.openNext(true);
					f.dispose();

				}
			}
		});
		panelActions.add(button_2);

		comboBoxMeleeWeapon = new JComboBox();
		comboBoxMeleeWeapon.setBounds(151, 426, 136, 20);
		comboBoxMeleeWeapon.setModel(new DefaultComboBoxModel(new String[] { "None", "Vibroknife" }));
		comboBoxMeleeWeapon.setSelectedIndex(0);
		panelActions.add(comboBoxMeleeWeapon);

		comboBoxMeleeTargets = new JComboBox();
		comboBoxMeleeTargets.setBounds(9, 426, 136, 20);
		comboBoxMeleeTargets.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxMeleeTargets.setSelectedIndex(0);
		panelActions.add(comboBoxMeleeTargets);

		JLabel lblHandGrenades = new JLabel("Hand Grenades:");
		lblHandGrenades.setBounds(77, 456, 221, 31);
		lblHandGrenades.setForeground(Color.WHITE);
		lblHandGrenades.setFont(new Font("Calibri", Font.PLAIN, 18));
		panelActions.add(lblHandGrenades);

		JLabel lblGrenade = new JLabel("Grenade: ");
		lblGrenade.setBounds(10, 484, 136, 31);
		lblGrenade.setForeground(Color.WHITE);
		lblGrenade.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblGrenade);

		comboBoxGrenade = new JComboBox();
		comboBoxGrenade.setBounds(10, 514, 136, 20);
		comboBoxGrenade.setModel(new DefaultComboBoxModel(new String[] {"None", "Class-A Thermal Detonator", "Class-A Haywire Grenade", "M9 Frag", "Type-1 Plasma Grenade", "RGD-5", "Nacht-5 Smoke Grenade"}));
		comboBoxGrenade.setSelectedIndex(0);
		panelActions.add(comboBoxGrenade);

		JLabel label_15 = new JLabel("Other Bonus:");
		label_15.setBounds(692, 541, 87, 31);
		label_15.setForeground(Color.WHITE);
		label_15.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(label_15);

		spinnerThrowBonus = new JSpinner();
		spinnerThrowBonus.setBounds(696, 571, 74, 20);
		panelActions.add(spinnerThrowBonus);

		JButton btnThrow = new JButton("Throw");
		btnThrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxGrenade.getSelectedItem().equals("None")) {
					window.gameWindow.conflictLog.addNewLine("Select Grenade!");
					return;
				}

				if (!openTrooper.inventory.containsItem((String) comboBoxGrenade.getSelectedItem())
						&& !chkbxOverRideInventory.isSelected()) {
					GameWindow.gameWindow.conflictLog.addNewLine("Could not make attack, trooper does not have item.");
					return;
				}

				Trooper target;

				if (grenadeTargets.size() < 1 || comboBoxGrenadeTargets.getSelectedIndex() < 1) {
					target = null;
				} else {
					target = grenadeTargets.get(comboBoxGrenadeTargets.getSelectedIndex() - 1);
				}

				PcGrenadeThrow nade = new PcGrenadeThrow(trooper, target, (String) comboBoxGrenade.getSelectedItem(),
						(int) spinnerThrowBonus.getValue(), (int) spinnerThrowEALBonus.getValue());

				// If target is a building
				if (target == null && (int) spinnerTargetRoom.getValue() > 0
						&& (comboBoxBuilding.getSelectedItem().toString().equals("ALREADY INSIDE")
								|| comboBoxBuilding.getSelectedIndex() > 0)) {

					Hex hex = gameWindow.findHex(trooperUnit.X, trooperUnit.Y);

					if (trooperBuilding == null) {
						Building building = hex.buildings.get(comboBoxBuilding.getSelectedIndex() - 1);

						if ((int) spinnerTargetFloor.getValue() - 1 >= building.floors.size()
								|| (int) spinnerTargetFloor.getValue() - 1 < 0) {
							gameWindow.conflictLog.addNewLine("Input a valid floor.");
							return;
						}

						nade.tossIntoRoom(
								building.floors.get((int) spinnerTargetFloor.getValue() - 1).rooms
										.get((int) spinnerTargetRoom.getValue() - 1),
								true, (int) spinnerTargetFloor.getValue(), trooperUnit.X, trooperUnit.Y, building.name);
					} else if (comboBoxBuilding.getSelectedItem().toString().equals("ALREADY INSIDE")) {
						nade.tossIntoRoom(
								trooperBuilding.getTrooperFloor(trooper).rooms
										.get((int) spinnerTargetRoom.getValue() - 1),
								false, 0, trooperUnit.X, trooperUnit.Y, trooperBuilding.name);

					}

					// If target is a hex
				} else {
					nade.toss((int) spinnerGrenadeX.getValue(), (int) spinnerGrenadeY.getValue());
				}

				if (openTrooper.inventory.containsItem((String) comboBoxGrenade.getSelectedItem())) {
					try {
						openTrooper.inventory.removeItem((String) comboBoxGrenade.getSelectedItem());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				GameWindow.gameWindow.conflictLog.addQueuedText();

				refreshTargets();
				refreshInventory();
				if (!chckbxFreeAction.isSelected())
					actionSpent(window, index);

				gameWindow.refreshInitiativeOrder();
			}
		});
		btnThrow.setBounds(738, 513, 125, 23);
		btnThrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		panelActions.add(btnThrow);

		JLabel lblTargetHex = new JLabel("Target Hex:");
		lblTargetHex.setBounds(355, 484, 87, 31);
		lblTargetHex.setForeground(Color.WHITE);
		lblTargetHex.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblTargetHex);

		JLabel lblX = new JLabel("X:");
		lblX.setBounds(340, 510, 16, 31);
		lblX.setForeground(Color.WHITE);
		lblX.setFont(new Font("Calibri", Font.PLAIN, 12));
		panelActions.add(lblX);

		spinnerGrenadeX = new JSpinner();
		spinnerGrenadeX.setBounds(355, 514, 40, 20);
		panelActions.add(spinnerGrenadeX);

		spinnerGrenadeY = new JSpinner();
		spinnerGrenadeY.setBounds(419, 514, 40, 20);
		panelActions.add(spinnerGrenadeY);

		JLabel lblY = new JLabel("Y:");
		lblY.setBounds(404, 510, 16, 31);
		lblY.setForeground(Color.WHITE);
		lblY.setFont(new Font("Calibri", Font.PLAIN, 12));
		panelActions.add(lblY);

		comboBoxGrenadeTargets = new JComboBox();
		comboBoxGrenadeTargets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxAimTime.setSelectedIndex(0);
				if (targetedFire != null) {
					spentCA = targetedFire.spentCA + targetedFire.spentAimTime;
					targetedFire = null;
				}
				possibleShots = true;
				reaction = null;

				if (comboBoxTargets.getSelectedIndex() == 0)
					return;

				// System.out.println("Targets Size: "+targetTroopers.size()+", Target Name:
				// "+findTarget().number+" "+findTarget().name);

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						// System.out.println("Target Changed Shots1");
						PCShots();
						// System.out.println("Target Changed Shots2");

						return null;
					}

					@Override
					protected void done() {

						PCFireGuiUpdates();

					}

				};

				worker.execute();
			}
		});
		comboBoxGrenadeTargets.setBounds(157, 514, 136, 20);
		comboBoxGrenadeTargets.setModel(new DefaultComboBoxModel(new String[] { "None" }));
		comboBoxGrenadeTargets.setSelectedIndex(0);
		panelActions.add(comboBoxGrenadeTargets);

		JLabel label_16 = new JLabel("Taget Individual: ");
		label_16.setBounds(157, 484, 121, 31);
		label_16.setForeground(Color.WHITE);
		label_16.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(label_16);

		JLabel lblLauncher_1 = new JLabel("Launcher:");
		lblLauncher_1.setBounds(79, 544, 221, 31);
		lblLauncher_1.setForeground(Color.WHITE);
		lblLauncher_1.setFont(new Font("Calibri", Font.PLAIN, 25));
		panelActions.add(lblLauncher_1);

		comboBoxLauncher = new JComboBox();
		comboBoxLauncher.setBounds(10, 603, 136, 20);
		comboBoxLauncher.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				if (comboBoxLauncher.getSelectedItem().equals("None")) {
					comboBoxAmmoTypeLauncher.removeAllItems();
					return;
				}

				if (comboBoxLauncher.getSelectedIndex() < 0) {
					return;
				}

				DefaultComboBoxModel ammoTypes = new DefaultComboBoxModel();

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						// Sets ammo types
						Weapons weapon = new Weapons();
						weapon.getWeapons();
						Weapons launcher = weapon.findWeapon((String) comboBoxLauncher.getSelectedItem());
						// System.out.println("Launcher Name: "+launcher.name);

						for (PCAmmo ammo : launcher.pcAmmoTypes) {
							// System.out.println("ADDED Name: "+ammo.name);
							ammoTypes.addElement(ammo.name);

						}

						if (shoot != null) {

							shoot.pcAmmo = launcher.pcAmmoTypes.get(0);
							shoot.updateWeapon(launcher.name);
							System.out.println("Update Weapon");
							shoot.recalc();
							if (comboBoxTargetZone.getSelectedIndex() > 0) {
								setCalledShotBounds();
							}
						}

						comboBoxWeapon.setSelectedIndex(0);

						return null;
					}

					@Override
					protected void done() {

						comboBoxAmmoTypeLauncher.setModel(ammoTypes);
						// PCFireGuiUpdates();
						guiUpdates();

					}

				};

				worker.execute();

			}
		});
		comboBoxLauncher
				.setModel(new DefaultComboBoxModel(new String[] { "None", "RPS-6", "DC40", "B2RR", "DC17 Rocket" }));
		comboBoxLauncher.setSelectedIndex(0);
		panelActions.add(comboBoxLauncher);

		JLabel lblLauncher_2 = new JLabel("Launcher: ");
		lblLauncher_2.setBounds(10, 573, 136, 31);
		lblLauncher_2.setForeground(Color.WHITE);
		lblLauncher_2.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblLauncher_2);

		spinnerLauncherBonus = new JSpinner();
		spinnerLauncherBonus.setBounds(156, 603, 74, 20);
		panelActions.add(spinnerLauncherBonus);

		JLabel label_19 = new JLabel("Other Bonus:");
		label_19.setBounds(152, 573, 87, 31);
		label_19.setForeground(Color.WHITE);
		label_19.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(label_19);

		spinnerLauncherX = new JSpinner();
		spinnerLauncherX.setBounds(264, 603, 40, 20);
		panelActions.add(spinnerLauncherX);

		JLabel label_20 = new JLabel("X:");
		label_20.setBounds(249, 599, 16, 31);
		label_20.setForeground(Color.WHITE);
		label_20.setFont(new Font("Calibri", Font.PLAIN, 12));
		panelActions.add(label_20);

		JLabel label_21 = new JLabel("Y:");
		label_21.setBounds(313, 599, 16, 31);
		label_21.setForeground(Color.WHITE);
		label_21.setFont(new Font("Calibri", Font.PLAIN, 12));
		panelActions.add(label_21);

		spinnerLauncherY = new JSpinner();
		spinnerLauncherY.setBounds(328, 603, 40, 20);
		panelActions.add(spinnerLauncherY);

		JLabel label_22 = new JLabel("Target Hex:");
		label_22.setBounds(264, 573, 87, 31);
		label_22.setForeground(Color.WHITE);
		label_22.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(label_22);

		JLabel lblAmmoType = new JLabel("Ammo Type:");
		lblAmmoType.setBounds(26, 633, 125, 16);
		lblAmmoType.setForeground(Color.WHITE);
		lblAmmoType.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblAmmoType);

		comboBoxAmmoTypeLauncher = new JComboBox();
		comboBoxAmmoTypeLauncher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBoxAmmoTypeLauncher.getSelectedIndex() < 0) {
					return;
				}

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						Weapons weapon = new Weapons();
						weapon.getWeapons();
						Weapons launcher = weapon.findWeapon((String) comboBoxLauncher.getSelectedItem());

						if (shoot != null) {
							shoot.pcAmmo = launcher.pcAmmoTypes.get(comboBoxAmmoTypeLauncher.getSelectedIndex());
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
		comboBoxAmmoTypeLauncher.setBounds(10, 651, 136, 20);
		comboBoxAmmoTypeLauncher.setSelectedIndex(-1);
		panelActions.add(comboBoxAmmoTypeLauncher);

		comboBoxLauncherConcealment = new JComboBox();
		comboBoxLauncherConcealment.setBounds(152, 651, 136, 20);
		comboBoxLauncherConcealment.setModel(new DefaultComboBoxModel(
				new String[] { "No Concealment ", "Level 1", "Level 2", "Level 3", "Level 4", "Level 5" }));
		comboBoxLauncherConcealment.setSelectedIndex(0);
		panelActions.add(comboBoxLauncherConcealment);

		JLabel lblHexConcealment = new JLabel("Hex Concealment:");
		lblHexConcealment.setBounds(152, 818, 136, 31);
		lblHexConcealment.setForeground(Color.WHITE);
		lblHexConcealment.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblHexConcealment);

		chckbxFreeAction = new JCheckBox("Free Action");
		chckbxFreeAction.setBounds(14, 156, 97, 23);
		chckbxFreeAction.setBackground(Color.DARK_GRAY);
		chckbxFreeAction.setFont(new Font("Calibri", Font.BOLD, 15));
		chckbxFreeAction.setForeground(Color.WHITE);
		panelActions.add(chckbxFreeAction);

		chckbxReacted = new JCheckBox("Reacted");
		chckbxReacted.setBounds(113, 156, 86, 23);
		chckbxReacted.setForeground(Color.WHITE);
		chckbxReacted.setFont(new Font("Calibri", Font.BOLD, 15));
		chckbxReacted.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxReacted);

		lblCombatActions = new JLabel("Combat Actions:");
		lblCombatActions.setBounds(258, 203, 65, 31);
		lblCombatActions.setForeground(Color.WHITE);
		lblCombatActions.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblCombatActions);

		JLabel lblCaBonus = new JLabel("CA Bonus:");
		lblCaBonus.setBounds(459, 182, 74, 31);
		lblCaBonus.setForeground(Color.WHITE);
		lblCaBonus.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblCaBonus);

		spinnerCABonus = new JSpinner();
		spinnerCABonus.setBounds(459, 219, 73, 20);
		panelActions.add(spinnerCABonus);

		JLabel label_18 = new JLabel("EAL Bonus:");
		label_18.setBounds(541, 182, 87, 31);
		label_18.setForeground(Color.WHITE);
		label_18.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(label_18);

		spinnerPercentBonus = new JSpinner();
		spinnerPercentBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerPercentBonus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		spinnerPercentBonus.setBounds(625, 220, 74, 20);
		panelActions.add(spinnerPercentBonus);

		lblPossibleShots = new JLabel("Possible Shots:");
		lblPossibleShots.setBounds(669, 328, 222, 20);
		lblPossibleShots.setForeground(Color.WHITE);
		lblPossibleShots.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblPossibleShots);

		lblAimTime = new JLabel("Aim Time:");
		lblAimTime.setBounds(551, 242, 178, 23);
		lblAimTime.setForeground(Color.WHITE);
		lblAimTime.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblAimTime);

		lblTN = new JLabel("TN: 0");
		lblTN.setBounds(669, 300, 222, 23);
		lblTN.setForeground(Color.WHITE);
		lblTN.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblTN);

		chckbxFullAuto = new JCheckBox("Full Auto");
		chckbxFullAuto.setBounds(462, 296, 74, 23);
		chckbxFullAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (shoot != null) {
					guiUpdates();
				}
			}
		});
		chckbxFullAuto.setForeground(Color.WHITE);
		chckbxFullAuto.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxFullAuto);

		chckbxSustainedFullAuto = new JCheckBox("Sustained FAB");
		chckbxSustainedFullAuto.setBounds(551, 296, 113, 23);
		chckbxSustainedFullAuto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (targetedFire != null) {

					if (targetedFire.consecutiveShots) {
						targetedFire.EAL -= 2;
						targetedFire.ALMSum -= 2;
						targetedFire.consecutiveShots = false;
					}

				}

				PCShots();

			}
		});
		chckbxSustainedFullAuto.setForeground(Color.WHITE);
		chckbxSustainedFullAuto.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxSustainedFullAuto);

		JButton btnAim = new JButton("Aim");
		btnAim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (shoot == null) {
					return;
				}

				SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						int newAim = shoot.aimTime + (openTrooper.combatActions - shoot.spentCombatActions);

						newAim = newAim >= shoot.wep.aimTime.size() ? shoot.wep.aimTime.size() - 1 : newAim;

						shoot.spentCombatActions += newAim - shoot.aimTime;

						shoot.setAimTime(newAim);
						System.out.println();
						if (!chckbxFreeAction.isSelected() && shoot.spentCombatActions >= openTrooper.combatActions) {

							actionSpent(window, index);
							if (openNext)
								window.openNext(true);
							f.dispose();

						}

						shootReset = false;

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
		btnAim.setBounds(551, 269, 87, 23);
		btnAim.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		panelActions.add(btnAim);

		JLabel lblTargetZone = new JLabel("Target Zone:");
		lblTargetZone.setBounds(426, 243, 114, 23);
		lblTargetZone.setForeground(Color.WHITE);
		lblTargetZone.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblTargetZone);

		comboBoxTargetZone = new JComboBox();
		comboBoxTargetZone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					setCalledShotBounds();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				guiUpdates();

			}
		});
		comboBoxTargetZone.setBounds(427, 270, 113, 23);
		comboBoxTargetZone.setModel(new DefaultComboBoxModel(new String[] { "None", "Head", "Mid Body", "Legs" }));
		comboBoxTargetZone.setSelectedIndex(0);
		panelActions.add(comboBoxTargetZone);

		chckbxMultipleTargets = new JCheckBox("Multiple Targets");
		chckbxMultipleTargets.setBounds(339, 296, 121, 23);
		chckbxMultipleTargets.setForeground(Color.WHITE);
		chckbxMultipleTargets.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxMultipleTargets);

		lblTfSpentCa = new JLabel("TF Spent CA: 0");
		lblTfSpentCa.setBounds(348, 203, 102, 31);
		lblTfSpentCa.setForeground(Color.WHITE);
		lblTfSpentCa.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblTfSpentCa);

		JButton btnSpotAllUnits = new JButton("Spot All");
		btnSpotAllUnits.setBounds(444, 149, 101, 23);
		btnSpotAllUnits.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (trooper.returnTrooperUnit(gameWindow).lineOfSight.size() <= 0)
					return;

				if (comboBoxSpottingUnits.getComponentCount() > 1) {

					for (int i = 1; i < comboBoxSpottingUnits.getComponentCount(); i++) {

						String callsign = (String) comboBoxSpottingUnits.getItemAt(i);

						if (!callsigns.contains(callsign)) {
							callsigns.add(callsign);
						}

						listSpottedUnitsArray.removeAll();

						DefaultListModel listSpottedUnits = new DefaultListModel();

						for (int x = 0; x < callsigns.size(); x++) {
							listSpottedUnits.addElement(callsigns.get(x));

						}

						listSpottedUnitsArray.setModel(listSpottedUnits);
					}

					// System.out.println("Pass Roll Spot Programatical click");
					btnRollSpot.doClick();

					for (int i = 0; i < callsigns.size(); i++) {
						spotTest(callsigns.get(i), window, trooper, unit, index);
					}

					// Clears list
					listSpottedUnitsArray.removeAll();
					callsigns.clear();

					DefaultListModel listSpottedUnits = new DefaultListModel();

					for (int i = 0; i < callsigns.size(); i++) {
						listSpottedUnits.addElement(callsigns.get(i));

					}

					listSpottedUnitsArray.setModel(listSpottedUnits);

					// If not a free test
					if (!chckbxFreeSpotTest.isSelected()) {
						// Opens next window
						if (openNext) {
							window.openNext(true);
						}

						// Closes window
						f.dispose();
					}

				}

			}
		});
		panelActions.add(btnSpotAllUnits);

		JLabel lblStance = new JLabel("Misc.");
		lblStance.setBounds(81, 11, 146, 31);
		lblStance.setForeground(Color.WHITE);
		lblStance.setFont(new Font("Calibri", Font.PLAIN, 25));
		panelActions.add(lblStance);

		comboBoxStance = new JComboBox();
		comboBoxStance.setBounds(91, 42, 136, 23);
		comboBoxStance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBoxStance.getSelectedItem().toString().equals(trooper.stance)) {
					return;
				}

				System.out.println("Changing Stance, Trooper Stance: |" + trooper.stance + "| Box Stance: |"
						+ comboBoxStance.getSelectedItem().toString() + "|");
				trooper.stance = comboBoxStance.getSelectedItem().toString();

				if (targetedFire != null) {
					targetedFire.spentCA++;
				} else {

					spentCA++;
				}
				PCShots();
				PCFireGuiUpdates();
			}
		});
		comboBoxStance.setModel(new DefaultComboBoxModel(new String[] { "Standing", "Crouched", "Prone" }));
		panelActions.add(comboBoxStance);

		chckbxManualStance = new JCheckBox("Manual Stance");
		chckbxManualStance.setBounds(237, 42, 136, 23);
		chckbxManualStance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxManualStance.isSelected())
					trooper.manualStance = true;
				else
					trooper.manualStance = false;

				// System.out.println("Manual Stance: "+trooper.manualStance);
			}
		});
		chckbxManualStance.setForeground(Color.WHITE);
		chckbxManualStance.setFont(new Font("Calibri", Font.BOLD, 15));
		chckbxManualStance.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxManualStance);

		chckbxWeaponLights = new JCheckBox("Weapon Lights");
		chckbxWeaponLights.setBounds(372, 42, 142, 23);
		chckbxWeaponLights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!getWeapon().light) {
					gameWindow.conflictLog.addNewLine("This trooper does not have weapon lights.");
					return;
				}

				if (chckbxWeaponLights.isSelected())
					trooper.weaponLightOn = true;
				else
					trooper.weaponLightOn = false;

			}
		});
		chckbxWeaponLights.setForeground(Color.WHITE);
		chckbxWeaponLights.setFont(new Font("Calibri", Font.BOLD, 15));
		chckbxWeaponLights.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxWeaponLights);

		chckbxNvgs = new JCheckBox("NVGs");
		chckbxNvgs.setBounds(524, 42, 61, 23);
		chckbxNvgs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!trooper.nightVision) {
					gameWindow.conflictLog.addNewLine("This trooper does not have night vision.");
					return;
				}

				if (chckbxWeaponLights.isSelected())
					trooper.nightVisionInUse = true;
				else
					trooper.nightVisionInUse = false;

			}
		});
		chckbxNvgs.setForeground(Color.WHITE);
		chckbxNvgs.setFont(new Font("Calibri", Font.BOLD, 15));
		chckbxNvgs.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxNvgs);

		JLabel lblGen = new JLabel("Gen:");
		lblGen.setBounds(619, 40, 40, 28);
		lblGen.setForeground(Color.WHITE);
		lblGen.setFont(new Font("Calibri", Font.PLAIN, 15));
		panelActions.add(lblGen);

		spinnerNVGGen = new JSpinner();
		spinnerNVGGen.setBounds(664, 44, 34, 20);
		panelActions.add(spinnerNVGGen);

		btnAddNvgs = new JButton("Add NVGs");
		btnAddNvgs.setBounds(619, 11, 114, 23);
		btnAddNvgs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if ((int) spinnerNVGGen.getValue() < 1 || (int) spinnerNVGGen.getValue() > 5) {
					gameWindow.conflictLog.addNewLine("NVG Gen not a value from 1 to 5.");
					return;
				}

				trooper.nightVision = true;
				trooper.nightVisionEffectiveness = (int) spinnerNVGGen.getValue();

			}
		});
		panelActions.add(btnAddNvgs);

		chckbxLaser = new JCheckBox("Laser");
		chckbxLaser.setBounds(237, 11, 74, 23);
		chckbxLaser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!getWeapon().laser) {
					gameWindow.conflictLog.addNewLine("This trooper does not have a laser pointer.");
					return;
				}

				if (chckbxLaser.isSelected())
					trooper.weaponLaserOn = true;
				else
					trooper.weaponLaserOn = false;

			}
		});
		chckbxLaser.setForeground(Color.WHITE);
		chckbxLaser.setFont(new Font("Calibri", Font.BOLD, 15));
		chckbxLaser.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxLaser);

		chckbxIrLaser = new JCheckBox("IR Laser");
		chckbxIrLaser.setBounds(372, 11, 138, 23);
		chckbxIrLaser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!getWeapon().irLaser) {
					gameWindow.conflictLog.addNewLine("This trooper does not have a IR laser pointer.");
					return;
				}

				if (chckbxIrLaser.isSelected())
					trooper.weaponIRLaserOn = true;
				else
					trooper.weaponIRLaserOn = false;
			}
		});
		chckbxIrLaser.setForeground(Color.WHITE);
		chckbxIrLaser.setFont(new Font("Calibri", Font.BOLD, 15));
		chckbxIrLaser.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxIrLaser);

		chckbxThermals = new JCheckBox("Thermals");
		chckbxThermals.setBounds(524, 11, 91, 23);
		chckbxThermals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!trooper.thermalVision) {
					gameWindow.conflictLog.addNewLine("This trooper does not have thermal vision.");
					return;
				}

				if (chckbxThermals.isSelected())
					trooper.thermalVisionInUse = true;
				else
					trooper.thermalVisionInUse = false;

			}
		});
		chckbxThermals.setForeground(Color.WHITE);
		chckbxThermals.setFont(new Font("Calibri", Font.BOLD, 15));
		chckbxThermals.setBackground(Color.DARK_GRAY);
		panelActions.add(chckbxThermals);

		JButton btnAddThermals = new JButton("Add Thermals");
		btnAddThermals.setBounds(743, 11, 130, 23);
		btnAddThermals.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				trooper.thermalVision = true;

			}
		});
		panelActions.add(btnAddThermals);

		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(570, 115, 215, 64);
		panelActions.add(scrollPane_6);

		listSpottedUnitsArray = new JList();
		listSpottedUnitsArray.setBackground(Color.DARK_GRAY);
		listSpottedUnitsArray.setForeground(Color.WHITE);
		scrollPane_6.setViewportView(listSpottedUnitsArray);

		JLabel lblConsecutiveEalBonus = new JLabel("Consecutive EAL Bonus:");
		lblConsecutiveEalBonus.setForeground(Color.WHITE);
		lblConsecutiveEalBonus.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblConsecutiveEalBonus.setBounds(709, 186, 164, 23);
		panelActions.add(lblConsecutiveEalBonus);

		spinnerConsecutiveEALBonus = new JSpinner();
		spinnerConsecutiveEALBonus.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				bonuses();
			}
		});
		spinnerConsecutiveEALBonus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		spinnerConsecutiveEALBonus.setBounds(709, 220, 85, 20);
		panelActions.add(spinnerConsecutiveEALBonus);

		spinner2YardHexRange = new JSpinner();
		spinner2YardHexRange.setBounds(756, 270, 74, 22);
		panelActions.add(spinner2YardHexRange);

		JLabel lblYardHexes = new JLabel("2 Yard Hexes Range");
		lblYardHexes.setForeground(Color.WHITE);
		lblYardHexes.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblYardHexes.setBounds(756, 242, 135, 31);
		panelActions.add(lblYardHexes);

		spinnerThrowEALBonus = new JSpinner();
		spinnerThrowEALBonus.setBounds(789, 571, 74, 20);
		panelActions.add(spinnerThrowEALBonus);

		JLabel lblEalBonus = new JLabel("EAL Bonus:");
		lblEalBonus.setForeground(Color.WHITE);
		lblEalBonus.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblEalBonus.setBounds(785, 541, 87, 31);
		panelActions.add(lblEalBonus);

		JLabel lblOr = new JLabel("OR");
		lblOr.setForeground(Color.WHITE);
		lblOr.setFont(new Font("Calibri", Font.PLAIN, 12));
		lblOr.setBounds(307, 510, 16, 31);
		panelActions.add(lblOr);

		chckbxHoming = new JCheckBox("Homing");
		chckbxHoming.setForeground(Color.WHITE);
		chckbxHoming.setBackground(Color.DARK_GRAY);
		chckbxHoming.setBounds(258, 296, 80, 23);
		panelActions.add(chckbxHoming);

		JButton btnShootHex = new JButton("Shoot Hex");
		btnShootHex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					String name = (String) comboBoxLauncher.getSelectedItem() + ": "
							+ comboBoxAmmoTypeLauncher.getSelectedItem().toString() + " round";

					if (!openTrooper.inventory.containsItem(name) && !chkbxOverRideInventory.isSelected()) {
						GameWindow.gameWindow.conflictLog
								.addNewLine("Could not make attack, trooper does not have item. Name: " + name);
						return;
					}

					int hexDiff = GameWindow.hexDif((int) spinnerLauncherX.getValue(),
							(int) spinnerLauncherY.getValue(), unit);

					int rangeInPcHexes = hexDiff * GameWindow.hexSize;

					InjuryLog.InjuryLog.addAlreadyInjured(x, y);

					// 32 is target size of 20 yard hex
					int EAL = PCUtility.findVisibiltyALM(unit, trooper, rangeInPcHexes) + 32
							+ PCUtility.findRangeALM(rangeInPcHexes) + PCUtility.getSL("Launcher", trooper)
							+ new Weapons().findWeapon(comboBoxLauncher.getSelectedItem().toString()).aimTime
									.get(new Weapons().findWeapon(comboBoxLauncher.getSelectedItem().toString()).aimTime
											.size() - 1);

					int TN = PCUtility.getOddsOfHitting(true, EAL);
					int roll = DiceRoller.randInt(0, 99);

					GameWindow.addTrooperEntryToLog(trooper, "fires rocket into hex, X: "
							+ (int) spinnerLauncherX.getValue() + ", Y: " + (int) spinnerLauncherY.getValue());

					if (roll <= TN) {

						GameWindow.gameWindow.conflictLog
								.addNewLineToQueue("Launcher Hits. TN: " + TN + ", Roll: " + roll);
						Explosion explosion = new Explosion(
								new Weapons().findWeapon(comboBoxLauncher.getSelectedItem().toString()).pcAmmoTypes
										.get(comboBoxAmmoTypeLauncher.getSelectedIndex()));
						explosion.explodeHex((int) spinnerLauncherX.getValue(), (int) spinnerLauncherY.getValue(),
								"None");
						GameWindow.gameWindow.conflictLog.addNewLineToQueue("Launcher EXPLOSION\n" + "Total BC Dealt: "
								+ explosion.totalBC + "\n" + "Total Shrap Hits: " + explosion.totalShrapHits + "\n");
						InjuryLog.InjuryLog.printResultsToLog();
					} else {
						GameWindow.gameWindow.conflictLog
								.addNewLineToQueue("Launcher misses. TN: " + TN + ", Roll: " + roll);
					}

					// System.out.println("Name: "+name);
					if (!chkbxOverRideInventory.isSelected()) {
						try {
							openTrooper.inventory.removeItem(name);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					refreshInventory();
					GameWindow.gameWindow.conflictLog.addQueuedText();
				} catch (Exception exception) {
					exception.printStackTrace();
				}

			}
		});
		btnShootHex.setBounds(386, 602, 128, 23);
		panelActions.add(btnShootHex);

		JLabel label_10 = new JLabel("OR");
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("Calibri", Font.PLAIN, 12));
		label_10.setBounds(469, 510, 16, 31);
		panelActions.add(label_10);

		JLabel lblTargetRoom = new JLabel("Room:");
		lblTargetRoom.setForeground(Color.WHITE);
		lblTargetRoom.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblTargetRoom.setBounds(643, 484, 53, 31);
		panelActions.add(lblTargetRoom);

		spinnerTargetRoom = new JSpinner();
		spinnerTargetRoom.setBounds(641, 514, 40, 20);
		panelActions.add(spinnerTargetRoom);

		comboBoxBuilding = new JComboBox();
		comboBoxBuilding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBoxBuilding.getSelectedIndex() < 0)
					return;
				else if (comboBoxBuilding.getSelectedIndex() == 0 && trooper.inBuilding(gameWindow)
						&& trooperBuilding != null) {
					Floor floor = trooperBuilding.getTrooperFloor(trooper);
					SpinnerModel model = new SpinnerNumberModel(1, 1, floor.rooms.size(), 1);
					spinnerTargetRoom.setModel(model);
					return;
				} else if (comboBoxBuilding.getSelectedItem().toString().equals("None"))
					return;

				Building selectedBuilding = GameWindow.gameWindow.findHex(trooperUnit.X, trooperUnit.Y).buildings
						.get(comboBoxBuilding.getSelectedIndex() - 1);
				SpinnerModel model = new SpinnerNumberModel(1, 1, selectedBuilding.floors.get(0).rooms.size(), 1);
				spinnerTargetRoom.setModel(model);
				// from 0 to 9, in 1.0 steps start value 5
				// SpinnerModel model1 = new SpinnerNumberModel(5.0, 0.0, 9.0, 1.0);
			}
		});
		comboBoxBuilding.setSelectedIndex(-1);
		comboBoxBuilding.setBounds(495, 514, 136, 20);
		panelActions.add(comboBoxBuilding);

		JLabel lblBuilding = new JLabel("Target Building:");
		lblBuilding.setForeground(Color.WHITE);
		lblBuilding.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblBuilding.setBounds(495, 484, 121, 31);
		panelActions.add(lblBuilding);

		spinnerTargetFloor = new JSpinner();
		spinnerTargetFloor.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		spinnerTargetFloor.setBounds(691, 514, 40, 20);
		panelActions.add(spinnerTargetFloor);

		JLabel lblFloor = new JLabel("Floor:");
		lblFloor.setForeground(Color.WHITE);
		lblFloor.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblFloor.setBounds(693, 484, 53, 31);
		panelActions.add(lblFloor);

		chkbxOverRideInventory = new JCheckBox("Override Inventory");
		chkbxOverRideInventory.setForeground(Color.WHITE);
		chkbxOverRideInventory.setBackground(Color.DARK_GRAY);
		chkbxOverRideInventory.setBounds(113, 182, 165, 23);
		panelActions.add(chkbxOverRideInventory);

		JPanel panelIndividualEdit = new JPanel();
		panelIndividualEdit.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Full Details + Edit", null, panelIndividualEdit, null);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panelIndividualEdit = new GroupLayout(panelIndividualEdit);
		gl_panelIndividualEdit.setHorizontalGroup(gl_panelIndividualEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndividualEdit.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE).addContainerGap()));
		gl_panelIndividualEdit.setVerticalGroup(gl_panelIndividualEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndividualEdit.createSequentialGroup().addGap(12)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE).addContainerGap()));

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 65, 185, 45);
		panel_1.setBackground(Color.DARK_GRAY);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(201, 65, 160, 45);
		panel_2.setBackground(Color.DARK_GRAY);

		JLabel lblStr = new JLabel("Str");
		lblStr.setForeground(Color.WHITE);
		lblStr.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerStr = new JSpinner();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(
						gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup().addContainerGap().addComponent(lblStr)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerStr,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(89, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE).addComponent(lblStr).addComponent(
								spinnerStr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(201, 116, 160, 45);
		panel_3.setBackground(Color.DARK_GRAY);

		JLabel lblWit = new JLabel("Wit");
		lblWit.setForeground(Color.WHITE);
		lblWit.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerWit = new JSpinner();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3
				.setHorizontalGroup(
						gl_panel_3.createParallelGroup(Alignment.LEADING).addGap(0, 113, Short.MAX_VALUE)
								.addGroup(gl_panel_3.createSequentialGroup().addContainerGap().addComponent(lblWit)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerWit,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(89, Short.MAX_VALUE)));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_3.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE).addComponent(lblWit).addComponent(
								spinnerWit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_3.setLayout(gl_panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(201, 171, 160, 45);
		panel_4.setBackground(Color.DARK_GRAY);

		JLabel lblSoc = new JLabel("Soc");
		lblSoc.setForeground(Color.WHITE);
		lblSoc.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerSoc = new JSpinner();
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4
				.setHorizontalGroup(
						gl_panel_4.createParallelGroup(Alignment.LEADING).addGap(0, 113, Short.MAX_VALUE)
								.addGroup(gl_panel_4.createSequentialGroup().addContainerGap().addComponent(lblSoc)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerSoc,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(89, Short.MAX_VALUE)));
		gl_panel_4.setVerticalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_4.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE).addComponent(lblSoc).addComponent(
								spinnerSoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_4.setLayout(gl_panel_4);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(201, 226, 160, 45);
		panel_5.setBackground(Color.DARK_GRAY);

		JLabel lblWil = new JLabel("Wil");
		lblWil.setForeground(Color.WHITE);
		lblWil.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerWil = new JSpinner();
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5
				.setHorizontalGroup(
						gl_panel_5.createParallelGroup(Alignment.LEADING).addGap(0, 113, Short.MAX_VALUE)
								.addGroup(gl_panel_5.createSequentialGroup().addContainerGap().addComponent(lblWil)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerWil,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(89, Short.MAX_VALUE)));
		gl_panel_5.setVerticalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_5.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE).addComponent(lblWil).addComponent(
								spinnerWil, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_5.setLayout(gl_panel_5);

		JPanel panel_6 = new JPanel();
		panel_6.setBounds(201, 281, 160, 45);
		panel_6.setBackground(Color.DARK_GRAY);

		JLabel lblPer = new JLabel("Per");
		lblPer.setForeground(Color.WHITE);
		lblPer.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerPer = new JSpinner();
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6
				.setHorizontalGroup(
						gl_panel_6.createParallelGroup(Alignment.LEADING).addGap(0, 113, Short.MAX_VALUE)
								.addGroup(gl_panel_6.createSequentialGroup().addContainerGap().addComponent(lblPer)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerPer,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(89, Short.MAX_VALUE)));
		gl_panel_6.setVerticalGroup(gl_panel_6.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_6.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE).addComponent(lblPer).addComponent(
								spinnerPer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_6.setLayout(gl_panel_6);

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(201, 336, 160, 45);
		panel_7.setBackground(Color.DARK_GRAY);

		JLabel lblHlt = new JLabel("Hlt");
		lblHlt.setForeground(Color.WHITE);
		lblHlt.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerHlt = new JSpinner();
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7
				.setHorizontalGroup(
						gl_panel_7.createParallelGroup(Alignment.LEADING).addGap(0, 113, Short.MAX_VALUE)
								.addGroup(gl_panel_7.createSequentialGroup().addContainerGap().addComponent(lblHlt)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerHlt,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(89, Short.MAX_VALUE)));
		gl_panel_7.setVerticalGroup(gl_panel_7.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_7.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE).addComponent(lblHlt).addComponent(
								spinnerHlt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_7.setLayout(gl_panel_7);

		JPanel panel_8 = new JPanel();
		panel_8.setBounds(201, 391, 160, 45);
		panel_8.setBackground(Color.DARK_GRAY);

		JLabel lblAgi = new JLabel("Agi");
		lblAgi.setForeground(Color.WHITE);
		lblAgi.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerAgi = new JSpinner();
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8
				.setHorizontalGroup(
						gl_panel_8.createParallelGroup(Alignment.LEADING).addGap(0, 113, Short.MAX_VALUE)
								.addGroup(gl_panel_8.createSequentialGroup().addContainerGap().addComponent(lblAgi)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerAgi,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(89, Short.MAX_VALUE)));
		gl_panel_8.setVerticalGroup(gl_panel_8.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_8.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE).addComponent(lblAgi).addComponent(
								spinnerAgi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_8.setLayout(gl_panel_8);

		JPanel panel_9 = new JPanel();
		panel_9.setBounds(10, 116, 185, 45);
		panel_9.setBackground(Color.DARK_GRAY);

		JLabel lblRank = new JLabel("Rank");
		lblRank.setForeground(Color.WHITE);
		lblRank.setFont(new Font("Calibri", Font.PLAIN, 13));

		textFieldRank = new JTextField();
		textFieldRank.setText((String) null);
		textFieldRank.setColumns(10);
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(gl_panel_9.createParallelGroup(Alignment.LEADING).addGap(0, 185, Short.MAX_VALUE)
				.addGroup(gl_panel_9.createSequentialGroup().addContainerGap().addComponent(lblRank).addGap(18)
						.addComponent(textFieldRank, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_9.setVerticalGroup(gl_panel_9.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_9.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE).addComponent(lblRank).addComponent(
								textFieldRank, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_9.setLayout(gl_panel_9);

		JPanel panel_10 = new JPanel();
		panel_10.setBounds(10, 171, 185, 45);
		panel_10.setBackground(Color.DARK_GRAY);

		JLabel lblRole = new JLabel("Role");
		lblRole.setForeground(Color.WHITE);
		lblRole.setFont(new Font("Calibri", Font.PLAIN, 13));

		textFieldRole = new JTextField();
		textFieldRole.setColumns(10);
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup().addContainerGap().addComponent(lblRole)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textFieldRole, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_10.setVerticalGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_10.createParallelGroup(Alignment.BASELINE).addComponent(lblRole)
								.addComponent(textFieldRole, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_10.setLayout(gl_panel_10);

		JPanel panel_11 = new JPanel();
		panel_11.setBounds(10, 226, 185, 45);
		panel_11.setBackground(Color.DARK_GRAY);

		JLabel lblVet_1 = new JLabel("Vet");
		lblVet_1.setForeground(Color.WHITE);
		lblVet_1.setFont(new Font("Calibri", Font.PLAIN, 13));

		textFieldVet = new JTextField();
		textFieldVet.setText((String) null);
		textFieldVet.setColumns(10);
		GroupLayout gl_panel_11 = new GroupLayout(panel_11);
		gl_panel_11
				.setHorizontalGroup(gl_panel_11.createParallelGroup(Alignment.LEADING).addGap(0, 185, Short.MAX_VALUE)
						.addGroup(gl_panel_11.createSequentialGroup().addContainerGap().addComponent(lblVet_1)
								.addGap(18).addComponent(textFieldVet, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
								.addContainerGap()));
		gl_panel_11.setVerticalGroup(gl_panel_11.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_11.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_11.createParallelGroup(Alignment.BASELINE).addComponent(lblVet_1)
								.addComponent(textFieldVet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_11.setLayout(gl_panel_11);

		JPanel panel_12 = new JPanel();
		panel_12.setBounds(10, 281, 185, 45);
		panel_12.setBackground(Color.DARK_GRAY);

		JLabel lblWeapon = new JLabel("Weapon");
		lblWeapon.setForeground(Color.WHITE);
		lblWeapon.setFont(new Font("Calibri", Font.PLAIN, 13));

		textFieldWeapon = new JTextField();
		textFieldWeapon.setText((String) null);
		textFieldWeapon.setColumns(10);
		GroupLayout gl_panel_12 = new GroupLayout(panel_12);
		gl_panel_12.setHorizontalGroup(gl_panel_12.createParallelGroup(Alignment.LEADING)
				.addGap(0, 185, Short.MAX_VALUE)
				.addGroup(gl_panel_12.createSequentialGroup().addContainerGap().addComponent(lblWeapon).addGap(18)
						.addComponent(textFieldWeapon, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_12.setVerticalGroup(gl_panel_12.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_12.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_12.createParallelGroup(Alignment.BASELINE).addComponent(lblWeapon)
								.addComponent(textFieldWeapon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_12.setLayout(gl_panel_12);

		JPanel panel_13 = new JPanel();
		panel_13.setBounds(10, 336, 185, 45);
		panel_13.setBackground(Color.DARK_GRAY);

		JLabel lblAmmo_1 = new JLabel("Ammo");
		lblAmmo_1.setForeground(Color.WHITE);
		lblAmmo_1.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerAmmo = new JSpinner();
		GroupLayout gl_panel_13 = new GroupLayout(panel_13);
		gl_panel_13
				.setHorizontalGroup(
						gl_panel_13.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_13.createSequentialGroup().addContainerGap().addComponent(lblAmmo_1)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerAmmo,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(86, Short.MAX_VALUE)));
		gl_panel_13.setVerticalGroup(gl_panel_13.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_13
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_13.createParallelGroup(Alignment.BASELINE).addComponent(lblAmmo_1).addComponent(
						spinnerAmmo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(14, Short.MAX_VALUE)));
		panel_13.setLayout(gl_panel_13);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(371, 10, 160, 733);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(549, 10, 160, 733);

		JPanel panel_50 = new JPanel();
		panel_50.setBounds(10, 391, 185, 45);
		panel_50.setBackground(Color.DARK_GRAY);

		JLabel lblSkill = new JLabel("Skill");
		lblSkill.setForeground(Color.WHITE);
		lblSkill.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerSkill = new JSpinner();
		GroupLayout gl_panel_50 = new GroupLayout(panel_50);
		gl_panel_50
				.setHorizontalGroup(
						gl_panel_50.createParallelGroup(Alignment.LEADING).addGap(0, 185, Short.MAX_VALUE)
								.addGroup(gl_panel_50.createSequentialGroup().addContainerGap().addComponent(lblSkill)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(spinnerSkill,
												GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(86, Short.MAX_VALUE)));
		gl_panel_50.setVerticalGroup(gl_panel_50.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_50.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_50.createParallelGroup(Alignment.BASELINE).addComponent(lblSkill)
								.addComponent(spinnerSkill, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_50.setLayout(gl_panel_50);

		textPaneNotes = new JTextPane();
		textPaneNotes.setBounds(10, 504, 161, 333);
		textPaneNotes.setForeground(Color.WHITE);
		textPaneNotes.setFont(new Font("Calibri", Font.PLAIN, 13));
		textPaneNotes.setCaretColor(Color.BLACK);
		textPaneNotes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPaneNotes.setBackground(Color.DARK_GRAY);

		JPanel panel_51 = new JPanel();
		panel_51.setBounds(10, 446, 185, 45);
		panel_51.setBackground(Color.DARK_GRAY);

		JLabel label = new JLabel("Notes:");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Calibri", Font.PLAIN, 15));
		GroupLayout gl_panel_51 = new GroupLayout(panel_51);
		gl_panel_51.setHorizontalGroup(gl_panel_51.createParallelGroup(Alignment.LEADING)
				.addGap(0, 185, Short.MAX_VALUE).addGroup(gl_panel_51.createSequentialGroup().addContainerGap()
						.addComponent(label).addContainerGap(134, Short.MAX_VALUE)));
		gl_panel_51.setVerticalGroup(gl_panel_51.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_51.createSequentialGroup().addContainerGap().addComponent(label).addContainerGap(15,
						Short.MAX_VALUE)));
		panel_51.setLayout(gl_panel_51);

		JButton btnAddToUnit = new JButton("Add to Unit");
		btnAddToUnit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddToUnit.setBounds(10, 10, 127, 25);
		btnAddToUnit.addMouseListener(new MouseAdapter() {
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
				} else if (!unit.individuals.contains(openTrooper)) {
					textFieldCallsign.setText("Trooper not in current unit...");
				} else if (gameWindow.initiativeOrder.get(targetUnitIndex).individuals.contains(openTrooper)) {
					textFieldCallsign.setText("Trooper already in target unit...");
				} else {

					// Adds trooper
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

					gameWindow.initiativeOrder.get(targetUnitIndex)
							.seekCover(gameWindow.findHex(gameWindow.initiativeOrder.get(targetUnitIndex).X,
									gameWindow.initiativeOrder.get(targetUnitIndex).Y), gameWindow);

					// Refreshes windows
					window.refreshIndividuals();
					// window.gameWindow.rollInitiativeOrder();
					window.gameWindow.refreshInitiativeOrder();

					f.dispose();
				}

			}
		});

		textFieldCallsign = new JTextField();
		textFieldCallsign.setBounds(147, 10, 214, 25);
		textFieldCallsign.setColumns(10);

		JPanel panel_78 = new JPanel();
		panel_78.setBounds(201, 446, 160, 45);
		panel_78.setBackground(Color.DARK_GRAY);

		JLabel lblKills = new JLabel("Kills");
		lblKills.setForeground(Color.WHITE);
		lblKills.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerKills = new JSpinner();
		GroupLayout gl_panel_78 = new GroupLayout(panel_78);
		gl_panel_78.setHorizontalGroup(gl_panel_78.createParallelGroup(Alignment.LEADING)
				.addGap(0, 153, Short.MAX_VALUE).addGap(0, 153, Short.MAX_VALUE)
				.addGroup(gl_panel_78.createSequentialGroup().addContainerGap().addComponent(lblKills)
						.addPreferredGap(ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
						.addComponent(spinnerKills, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_78.setVerticalGroup(gl_panel_78.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_78.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_78.createParallelGroup(Alignment.BASELINE).addComponent(lblKills)
								.addComponent(spinnerKills, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_78.setLayout(gl_panel_78);

		JPanel panel_83 = new JPanel();
		panel_83.setBounds(717, 807, 236, 45);
		panel_83.setBackground(Color.DARK_GRAY);
		GroupLayout gl_panel_83 = new GroupLayout(panel_83);
		gl_panel_83
				.setHorizontalGroup(gl_panel_83.createParallelGroup(Alignment.LEADING).addGap(0, 236, Short.MAX_VALUE));
		gl_panel_83.setVerticalGroup(gl_panel_83.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE));
		panel_83.setLayout(gl_panel_83);

		JPanel panel_89 = new JPanel();
		panel_89.setBounds(201, 504, 160, 45);
		panel_89.setBackground(Color.DARK_GRAY);

		JLabel lblArmorLeg = new JLabel("Armor");
		lblArmorLeg.setForeground(Color.WHITE);
		lblArmorLeg.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerArmor = new JSpinner();
		GroupLayout gl_panel_89 = new GroupLayout(panel_89);
		gl_panel_89.setHorizontalGroup(gl_panel_89.createParallelGroup(Alignment.LEADING)
				.addGap(0, 113, Short.MAX_VALUE).addGap(0, 113, Short.MAX_VALUE)
				.addGroup(gl_panel_89.createSequentialGroup().addContainerGap().addComponent(lblArmorLeg)
						.addPreferredGap(ComponentPlacement.UNRELATED, 23, Short.MAX_VALUE)
						.addComponent(spinnerArmor, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_89.setVerticalGroup(gl_panel_89.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_89.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_89.createParallelGroup(Alignment.BASELINE).addComponent(lblArmorLeg)
								.addComponent(spinnerArmor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_89.setLayout(gl_panel_89);

		JPanel panel_90 = new JPanel();
		panel_90.setBounds(201, 559, 160, 45);
		panel_90.setBackground(Color.DARK_GRAY);

		JLabel lblArmorLeg_1 = new JLabel("Armor Leg");
		lblArmorLeg_1.setForeground(Color.WHITE);
		lblArmorLeg_1.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerArmorLeg = new JSpinner();
		GroupLayout gl_panel_90 = new GroupLayout(panel_90);
		gl_panel_90.setHorizontalGroup(gl_panel_90.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_90.createSequentialGroup().addContainerGap().addComponent(lblArmorLeg_1)
						.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
						.addComponent(spinnerArmorLeg, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_90.setVerticalGroup(gl_panel_90.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_90.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_90.createParallelGroup(Alignment.BASELINE).addComponent(lblArmorLeg_1)
								.addComponent(spinnerArmorLeg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_90.setLayout(gl_panel_90);

		JPanel panel_91 = new JPanel();
		panel_91.setBounds(201, 614, 160, 45);
		panel_91.setBackground(Color.DARK_GRAY);

		JLabel lblArmorArm = new JLabel("Armor Arm");
		lblArmorArm.setForeground(Color.WHITE);
		lblArmorArm.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerArmorArm = new JSpinner();
		GroupLayout gl_panel_91 = new GroupLayout(panel_91);
		gl_panel_91.setHorizontalGroup(gl_panel_91.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_91.createSequentialGroup().addContainerGap().addComponent(lblArmorArm)
						.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
						.addComponent(spinnerArmorArm, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_91.setVerticalGroup(gl_panel_91.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_91.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_91.createParallelGroup(Alignment.BASELINE).addComponent(lblArmorArm)
								.addComponent(spinnerArmorArm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_91.setLayout(gl_panel_91);

		JPanel panel_92 = new JPanel();
		panel_92.setBounds(201, 669, 160, 45);
		panel_92.setBackground(Color.DARK_GRAY);

		JLabel lblArmorHead = new JLabel("Armor Head");
		lblArmorHead.setForeground(Color.WHITE);
		lblArmorHead.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerArmorHead = new JSpinner();
		GroupLayout gl_panel_92 = new GroupLayout(panel_92);
		gl_panel_92.setHorizontalGroup(gl_panel_92.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_92.createSequentialGroup().addContainerGap().addComponent(lblArmorHead)
						.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
						.addComponent(spinnerArmorHead, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_92.setVerticalGroup(gl_panel_92.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_92.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_92.createParallelGroup(Alignment.BASELINE).addComponent(lblArmorHead)
								.addComponent(spinnerArmorHead, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_92.setLayout(gl_panel_92);

		JPanel panel_33 = new JPanel();
		panel_33.setBackground(Color.DARK_GRAY);
		scrollPane_2.setViewportView(panel_33);
		panel_33.setLayout(null);

		JPanel panel_34 = new JPanel();
		panel_34.setBackground(Color.DARK_GRAY);
		panel_34.setBounds(10, 11, 138, 32);
		panel_33.add(panel_34);

		JLabel lblCommand = new JLabel("Command");
		lblCommand.setForeground(Color.WHITE);
		lblCommand.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerCommand = new JSpinner();
		GroupLayout gl_panel_34 = new GroupLayout(panel_34);
		gl_panel_34.setHorizontalGroup(gl_panel_34.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_34.createSequentialGroup().addContainerGap().addComponent(lblCommand)
						.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
						.addComponent(spinnerCommand, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_34.setVerticalGroup(gl_panel_34.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_34.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_34.createParallelGroup(Alignment.BASELINE).addComponent(lblCommand)
								.addComponent(spinnerCommand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_34.setLayout(gl_panel_34);

		JPanel panel_35 = new JPanel();
		panel_35.setBackground(Color.DARK_GRAY);
		panel_35.setBounds(10, 49, 138, 32);
		panel_33.add(panel_35);

		JLabel lblTactics = new JLabel("Tactics");
		lblTactics.setForeground(Color.WHITE);
		lblTactics.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerTactics = new JSpinner();
		GroupLayout gl_panel_35 = new GroupLayout(panel_35);
		gl_panel_35.setHorizontalGroup(gl_panel_35.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_35.createSequentialGroup().addContainerGap().addComponent(lblTactics)
						.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
						.addComponent(spinnerTactics, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_35.setVerticalGroup(gl_panel_35.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_35.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_35.createParallelGroup(Alignment.BASELINE).addComponent(lblTactics)
								.addComponent(spinnerTactics, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_35.setLayout(gl_panel_35);

		JPanel panel_36 = new JPanel();
		panel_36.setBackground(Color.DARK_GRAY);
		panel_36.setBounds(10, 87, 138, 32);
		panel_33.add(panel_36);

		JLabel lblDetMotives = new JLabel("Det Motives");
		lblDetMotives.setForeground(Color.WHITE);
		lblDetMotives.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerDetMotives = new JSpinner();
		GroupLayout gl_panel_36 = new GroupLayout(panel_36);
		gl_panel_36.setHorizontalGroup(gl_panel_36.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_36.createSequentialGroup().addContainerGap().addComponent(lblDetMotives).addGap(18)
						.addComponent(spinnerDetMotives, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(17, Short.MAX_VALUE)));
		gl_panel_36.setVerticalGroup(gl_panel_36.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_36.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_36.createParallelGroup(Alignment.BASELINE).addComponent(lblDetMotives)
								.addComponent(spinnerDetMotives, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_36.setLayout(gl_panel_36);

		JPanel panel_37 = new JPanel();
		panel_37.setBackground(Color.DARK_GRAY);
		panel_37.setBounds(10, 130, 138, 32);
		panel_33.add(panel_37);

		JLabel lblIntimidate = new JLabel("Intimidate");
		lblIntimidate.setForeground(Color.WHITE);
		lblIntimidate.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerIntimidate = new JSpinner();
		GroupLayout gl_panel_37 = new GroupLayout(panel_37);
		gl_panel_37.setHorizontalGroup(gl_panel_37.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_37.createSequentialGroup().addContainerGap().addComponent(lblIntimidate)
						.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
						.addComponent(spinnerIntimidate, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_37.setVerticalGroup(gl_panel_37.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_37.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_37.createParallelGroup(Alignment.BASELINE).addComponent(lblIntimidate)
								.addComponent(spinnerIntimidate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_37.setLayout(gl_panel_37);

		JPanel panel_38 = new JPanel();
		panel_38.setBackground(Color.DARK_GRAY);
		panel_38.setBounds(10, 168, 138, 32);
		panel_33.add(panel_38);

		JLabel lblPersuade = new JLabel("Persuade");
		lblPersuade.setForeground(Color.WHITE);
		lblPersuade.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerPersuade = new JSpinner();
		GroupLayout gl_panel_38 = new GroupLayout(panel_38);
		gl_panel_38.setHorizontalGroup(gl_panel_38.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_38.createSequentialGroup().addContainerGap().addComponent(lblPersuade)
						.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
						.addComponent(spinnerPersuade, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_38.setVerticalGroup(gl_panel_38.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_38.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_38.createParallelGroup(Alignment.LEADING)
								.addComponent(spinnerPersuade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPersuade))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_38.setLayout(gl_panel_38);

		JPanel panel_39 = new JPanel();
		panel_39.setBackground(Color.DARK_GRAY);
		panel_39.setBounds(10, 206, 138, 32);
		panel_33.add(panel_39);

		JLabel lblComputers = new JLabel("Computers");
		lblComputers.setForeground(Color.WHITE);
		lblComputers.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerComputers = new JSpinner();
		GroupLayout gl_panel_39 = new GroupLayout(panel_39);
		gl_panel_39.setHorizontalGroup(gl_panel_39.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_39.createSequentialGroup().addContainerGap().addComponent(lblComputers)
						.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
						.addComponent(spinnerComputers, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_39.setVerticalGroup(gl_panel_39.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_39.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_39.createParallelGroup(Alignment.BASELINE).addComponent(lblComputers)
								.addComponent(spinnerComputers, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_39.setLayout(gl_panel_39);

		JPanel panel_40 = new JPanel();
		panel_40.setBackground(Color.DARK_GRAY);
		panel_40.setBounds(10, 249, 138, 32);
		panel_33.add(panel_40);

		JLabel lblPistol = new JLabel("Pistol");
		lblPistol.setForeground(Color.WHITE);
		lblPistol.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerPistol = new JSpinner();
		GroupLayout gl_panel_40 = new GroupLayout(panel_40);
		gl_panel_40.setHorizontalGroup(gl_panel_40.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_40.createSequentialGroup().addContainerGap().addComponent(lblPistol)
						.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
						.addComponent(spinnerPistol, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_40.setVerticalGroup(gl_panel_40.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_40.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_40.createParallelGroup(Alignment.BASELINE).addComponent(lblPistol)
								.addComponent(spinnerPistol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_40.setLayout(gl_panel_40);

		JPanel panel_41 = new JPanel();
		panel_41.setBackground(Color.DARK_GRAY);
		panel_41.setBounds(10, 287, 138, 32);
		panel_33.add(panel_41);

		JLabel lblHeavy = new JLabel("Heavy");
		lblHeavy.setForeground(Color.WHITE);
		lblHeavy.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerHeavy = new JSpinner();
		GroupLayout gl_panel_41 = new GroupLayout(panel_41);
		gl_panel_41.setHorizontalGroup(gl_panel_41.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_41.createSequentialGroup().addContainerGap().addComponent(lblHeavy)
						.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
						.addComponent(spinnerHeavy, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_41.setVerticalGroup(gl_panel_41.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_41.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_41.createParallelGroup(Alignment.BASELINE).addComponent(lblHeavy)
								.addComponent(spinnerHeavy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_41.setLayout(gl_panel_41);

		JPanel panel_42 = new JPanel();
		panel_42.setBackground(Color.DARK_GRAY);
		panel_42.setBounds(10, 325, 138, 32);
		panel_33.add(panel_42);

		JLabel lblSubgun = new JLabel("Subgun");
		lblSubgun.setForeground(Color.WHITE);
		lblSubgun.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerSubgun = new JSpinner();
		GroupLayout gl_panel_42 = new GroupLayout(panel_42);
		gl_panel_42.setHorizontalGroup(gl_panel_42.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_42.createSequentialGroup().addContainerGap().addComponent(lblSubgun)
						.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
						.addComponent(spinnerSubgun, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_42.setVerticalGroup(gl_panel_42.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_42.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_42.createParallelGroup(Alignment.BASELINE).addComponent(lblSubgun)
								.addComponent(spinnerSubgun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_42.setLayout(gl_panel_42);

		JPanel panel_43 = new JPanel();
		panel_43.setBackground(Color.DARK_GRAY);
		panel_43.setBounds(10, 368, 138, 32);
		panel_33.add(panel_43);

		JLabel lblLauncher = new JLabel("Launcher");
		lblLauncher.setForeground(Color.WHITE);
		lblLauncher.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerLauncher = new JSpinner();
		GroupLayout gl_panel_43 = new GroupLayout(panel_43);
		gl_panel_43.setHorizontalGroup(gl_panel_43.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_43.createSequentialGroup().addContainerGap().addComponent(lblLauncher)
						.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
						.addComponent(spinnerLauncher, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_43.setVerticalGroup(gl_panel_43.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_43.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_43.createParallelGroup(Alignment.BASELINE).addComponent(lblLauncher)
								.addComponent(spinnerLauncher, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_43.setLayout(gl_panel_43);

		JPanel panel_44 = new JPanel();
		panel_44.setBackground(Color.DARK_GRAY);
		panel_44.setBounds(10, 406, 138, 32);
		panel_33.add(panel_44);

		JLabel lblRifle = new JLabel("Rifle");
		lblRifle.setForeground(Color.WHITE);
		lblRifle.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerRifle = new JSpinner();
		GroupLayout gl_panel_44 = new GroupLayout(panel_44);
		gl_panel_44.setHorizontalGroup(gl_panel_44.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_44.createSequentialGroup().addContainerGap().addComponent(lblRifle)
						.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
						.addComponent(spinnerRifle, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_44.setVerticalGroup(gl_panel_44.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_44.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_44.createParallelGroup(Alignment.BASELINE).addComponent(lblRifle)
								.addComponent(spinnerRifle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_44.setLayout(gl_panel_44);

		JPanel panel_45 = new JPanel();
		panel_45.setBackground(Color.DARK_GRAY);
		panel_45.setBounds(10, 444, 138, 32);
		panel_33.add(panel_45);

		JLabel lblExplosives = new JLabel("Explosives");
		lblExplosives.setForeground(Color.WHITE);
		lblExplosives.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerExplosives = new JSpinner();
		GroupLayout gl_panel_45 = new GroupLayout(panel_45);
		gl_panel_45.setHorizontalGroup(gl_panel_45.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_45.createSequentialGroup().addContainerGap().addComponent(lblExplosives)
						.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
						.addComponent(spinnerExplosives, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_45.setVerticalGroup(gl_panel_45.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_45.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_45.createParallelGroup(Alignment.BASELINE).addComponent(lblExplosives)
								.addComponent(spinnerExplosives, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_45.setLayout(gl_panel_45);

		JPanel panel_46 = new JPanel();
		panel_46.setBackground(Color.DARK_GRAY);
		panel_46.setBounds(10, 487, 138, 32);
		panel_33.add(panel_46);

		JLabel lblFirstAid = new JLabel("First Aid");
		lblFirstAid.setForeground(Color.WHITE);
		lblFirstAid.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerFirstAid = new JSpinner();
		GroupLayout gl_panel_46 = new GroupLayout(panel_46);
		gl_panel_46.setHorizontalGroup(gl_panel_46.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_46.createSequentialGroup().addContainerGap().addComponent(lblFirstAid)
						.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
						.addComponent(spinnerFirstAid, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_46.setVerticalGroup(gl_panel_46.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_46.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_46.createParallelGroup(Alignment.BASELINE).addComponent(lblFirstAid)
								.addComponent(spinnerFirstAid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_46.setLayout(gl_panel_46);

		JPanel panel_47 = new JPanel();
		panel_47.setBackground(Color.DARK_GRAY);
		panel_47.setBounds(10, 573, 138, 32);
		panel_33.add(panel_47);

		JLabel lblNavigation = new JLabel("Navigation");
		lblNavigation.setForeground(Color.WHITE);
		lblNavigation.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerNavigation = new JSpinner();
		GroupLayout gl_panel_47 = new GroupLayout(panel_47);
		gl_panel_47.setHorizontalGroup(gl_panel_47.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_47.createSequentialGroup().addContainerGap().addComponent(lblNavigation)
						.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
						.addComponent(spinnerNavigation, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_47.setVerticalGroup(gl_panel_47.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_47.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_47.createParallelGroup(Alignment.BASELINE).addComponent(lblNavigation)
								.addComponent(spinnerNavigation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_47.setLayout(gl_panel_47);

		JPanel panel_48 = new JPanel();
		panel_48.setBackground(Color.DARK_GRAY);
		panel_48.setBounds(10, 611, 138, 32);
		panel_33.add(panel_48);

		JLabel lblSwim = new JLabel("Swim");
		lblSwim.setForeground(Color.WHITE);
		lblSwim.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerSwim = new JSpinner();
		GroupLayout gl_panel_48 = new GroupLayout(panel_48);
		gl_panel_48.setHorizontalGroup(gl_panel_48.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_48.createSequentialGroup().addContainerGap().addComponent(lblSwim)
						.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
						.addComponent(spinnerSwim, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_48.setVerticalGroup(gl_panel_48.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_48.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_48.createParallelGroup(Alignment.BASELINE).addComponent(lblSwim)
								.addComponent(spinnerSwim, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_48.setLayout(gl_panel_48);

		JPanel panel_49 = new JPanel();
		panel_49.setBackground(Color.DARK_GRAY);
		panel_49.setBounds(10, 654, 138, 32);
		panel_33.add(panel_49);

		JLabel lblThrow = new JLabel("Throw");
		lblThrow.setForeground(Color.WHITE);
		lblThrow.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerThrow = new JSpinner();
		GroupLayout gl_panel_49 = new GroupLayout(panel_49);
		gl_panel_49.setHorizontalGroup(gl_panel_49.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_49.createSequentialGroup().addContainerGap().addComponent(lblThrow)
						.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
						.addComponent(spinnerThrow, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_49.setVerticalGroup(gl_panel_49.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_49.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_49.createParallelGroup(Alignment.BASELINE).addComponent(lblThrow)
								.addComponent(spinnerThrow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_49.setLayout(gl_panel_49);

		JPanel panel_62 = new JPanel();
		panel_62.setBackground(Color.DARK_GRAY);
		panel_62.setBounds(10, 530, 138, 32);
		panel_33.add(panel_62);

		JLabel lblAdvancedMedicine = new JLabel("Adv Medicine");
		lblAdvancedMedicine.setForeground(Color.WHITE);
		lblAdvancedMedicine.setFont(new Font("Calibri", Font.PLAIN, 13));

		advancedMedicineSpinner = new JSpinner();
		GroupLayout gl_panel_62 = new GroupLayout(panel_62);
		gl_panel_62.setHorizontalGroup(gl_panel_62.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_62.createSequentialGroup().addContainerGap().addComponent(lblAdvancedMedicine)
						.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE).addComponent(
								advancedMedicineSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_62.setVerticalGroup(gl_panel_62.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_62.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_62.createParallelGroup(Alignment.BASELINE).addComponent(lblAdvancedMedicine)
								.addComponent(advancedMedicineSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_62.setLayout(gl_panel_62);

		JPanel panel_14 = new JPanel();
		panel_14.setBackground(Color.DARK_GRAY);
		scrollPane_1.setViewportView(panel_14);

		JPanel panel_15 = new JPanel();
		panel_15.setBounds(10, 11, 138, 32);
		panel_15.setBackground(Color.DARK_GRAY);

		JLabel lblBallance = new JLabel("Ballance");
		lblBallance.setForeground(Color.WHITE);
		lblBallance.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerBallance = new JSpinner();
		GroupLayout gl_panel_15 = new GroupLayout(panel_15);
		gl_panel_15.setHorizontalGroup(gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_15.createSequentialGroup().addContainerGap().addComponent(lblBallance)
						.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
						.addComponent(spinnerBallance, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_15.setVerticalGroup(gl_panel_15.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_15
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_15.createParallelGroup(Alignment.BASELINE).addComponent(lblBallance).addComponent(
						spinnerBallance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_15.setLayout(gl_panel_15);

		JPanel panel_16 = new JPanel();
		panel_16.setBounds(10, 49, 138, 32);
		panel_16.setBackground(Color.DARK_GRAY);

		JLabel lblClim = new JLabel("Climb");
		lblClim.setForeground(Color.WHITE);
		lblClim.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerClimb = new JSpinner();
		GroupLayout gl_panel_16 = new GroupLayout(panel_16);
		gl_panel_16.setHorizontalGroup(gl_panel_16.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_16.createSequentialGroup().addContainerGap().addComponent(lblClim)
						.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
						.addComponent(spinnerClimb, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_16.setVerticalGroup(gl_panel_16.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_16.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_16.createParallelGroup(Alignment.BASELINE).addComponent(lblClim)
								.addComponent(spinnerClimb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_16.setLayout(gl_panel_16);

		JPanel panel_17 = new JPanel();
		panel_17.setBounds(10, 87, 138, 32);
		panel_17.setBackground(Color.DARK_GRAY);

		JLabel lblComposure = new JLabel("Composure");
		lblComposure.setForeground(Color.WHITE);
		lblComposure.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerComposure = new JSpinner();
		GroupLayout gl_panel_17 = new GroupLayout(panel_17);
		gl_panel_17.setHorizontalGroup(gl_panel_17.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_17.createSequentialGroup().addContainerGap().addComponent(lblComposure).addGap(18)
						.addComponent(spinnerComposure, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(17, Short.MAX_VALUE)));
		gl_panel_17.setVerticalGroup(gl_panel_17.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_17.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_17.createParallelGroup(Alignment.BASELINE).addComponent(lblComposure)
								.addComponent(spinnerComposure, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_17.setLayout(gl_panel_17);

		JPanel panel_18 = new JPanel();
		panel_18.setBounds(10, 130, 138, 32);
		panel_18.setBackground(Color.DARK_GRAY);

		JLabel lblDodge = new JLabel("Dodge");
		lblDodge.setForeground(Color.WHITE);
		lblDodge.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerDodge = new JSpinner();
		GroupLayout gl_panel_18 = new GroupLayout(panel_18);
		gl_panel_18.setHorizontalGroup(gl_panel_18.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_18.createSequentialGroup().addContainerGap().addComponent(lblDodge)
						.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
						.addComponent(spinnerDodge, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_18.setVerticalGroup(gl_panel_18.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_18.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_18.createParallelGroup(Alignment.BASELINE).addComponent(lblDodge)
								.addComponent(spinnerDodge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_18.setLayout(gl_panel_18);

		JPanel panel_19 = new JPanel();
		panel_19.setBounds(10, 168, 138, 32);
		panel_19.setBackground(Color.DARK_GRAY);

		JLabel lblEndurance = new JLabel("Endurance");
		lblEndurance.setForeground(Color.WHITE);
		lblEndurance.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerEndurance = new JSpinner();
		GroupLayout gl_panel_19 = new GroupLayout(panel_19);
		gl_panel_19.setHorizontalGroup(gl_panel_19.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_19.createSequentialGroup().addContainerGap().addComponent(lblEndurance)
						.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
						.addComponent(spinnerEndurance, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_19.setVerticalGroup(gl_panel_19.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_19.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_19.createParallelGroup(Alignment.BASELINE).addComponent(lblEndurance)
								.addComponent(spinnerEndurance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_19.setLayout(gl_panel_19);

		JPanel panel_20 = new JPanel();
		panel_20.setBounds(10, 206, 138, 32);
		panel_20.setBackground(Color.DARK_GRAY);

		JLabel lblExpression = new JLabel("Expression");
		lblExpression.setForeground(Color.WHITE);
		lblExpression.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerExpression = new JSpinner();
		GroupLayout gl_panel_20 = new GroupLayout(panel_20);
		gl_panel_20.setHorizontalGroup(gl_panel_20.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_20.createSequentialGroup().addContainerGap().addComponent(lblExpression)
						.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
						.addComponent(spinnerExpression, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_20.setVerticalGroup(gl_panel_20.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_20.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_20.createParallelGroup(Alignment.BASELINE).addComponent(lblExpression)
								.addComponent(spinnerExpression, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_20.setLayout(gl_panel_20);
		panel_14.setLayout(null);
		panel_14.add(panel_15);
		panel_14.add(panel_16);
		panel_14.add(panel_17);
		panel_14.add(panel_18);
		panel_14.add(panel_19);
		panel_14.add(panel_20);

		JPanel panel_21 = new JPanel();
		panel_21.setBackground(Color.DARK_GRAY);
		panel_21.setBounds(10, 249, 138, 32);
		panel_14.add(panel_21);

		JLabel lblGrapple = new JLabel("Grapple");
		lblGrapple.setForeground(Color.WHITE);
		lblGrapple.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerGrapple = new JSpinner();
		GroupLayout gl_panel_21 = new GroupLayout(panel_21);
		gl_panel_21.setHorizontalGroup(gl_panel_21.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_21.createSequentialGroup().addContainerGap().addComponent(lblGrapple)
						.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
						.addComponent(spinnerGrapple, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_21.setVerticalGroup(gl_panel_21.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_21
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_21.createParallelGroup(Alignment.BASELINE).addComponent(lblGrapple).addComponent(
						spinnerGrapple, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_21.setLayout(gl_panel_21);

		JPanel panel_22 = new JPanel();
		panel_22.setBackground(Color.DARK_GRAY);
		panel_22.setBounds(10, 287, 138, 32);
		panel_14.add(panel_22);

		JLabel lblHold = new JLabel("Hold");
		lblHold.setForeground(Color.WHITE);
		lblHold.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerHold = new JSpinner();
		GroupLayout gl_panel_22 = new GroupLayout(panel_22);
		gl_panel_22.setHorizontalGroup(gl_panel_22.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_22.createSequentialGroup().addContainerGap().addComponent(lblHold)
						.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
						.addComponent(spinnerHold, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_22.setVerticalGroup(gl_panel_22.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_22.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_22.createParallelGroup(Alignment.BASELINE).addComponent(lblHold)
								.addComponent(spinnerHold, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_22.setLayout(gl_panel_22);

		JPanel panel_23 = new JPanel();
		panel_23.setBackground(Color.DARK_GRAY);
		panel_23.setBounds(10, 325, 138, 32);
		panel_14.add(panel_23);

		JLabel lblJump = new JLabel("Jump");
		lblJump.setForeground(Color.WHITE);
		lblJump.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerJump = new JSpinner();
		GroupLayout gl_panel_23 = new GroupLayout(panel_23);
		gl_panel_23.setHorizontalGroup(gl_panel_23.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_23.createSequentialGroup().addContainerGap().addComponent(lblJump)
						.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
						.addComponent(spinnerJump, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_23.setVerticalGroup(gl_panel_23.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_23.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_23.createParallelGroup(Alignment.BASELINE).addComponent(lblJump)
								.addComponent(spinnerJump, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_23.setLayout(gl_panel_23);

		JPanel panel_24 = new JPanel();
		panel_24.setBackground(Color.DARK_GRAY);
		panel_24.setBounds(10, 368, 138, 32);
		panel_14.add(panel_24);

		JLabel lblLift = new JLabel("Lift");
		lblLift.setForeground(Color.WHITE);
		lblLift.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerLift = new JSpinner();
		GroupLayout gl_panel_24 = new GroupLayout(panel_24);
		gl_panel_24.setHorizontalGroup(gl_panel_24.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_24.createSequentialGroup().addContainerGap().addComponent(lblLift)
						.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
						.addComponent(spinnerLift, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_24.setVerticalGroup(gl_panel_24.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_24.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_24.createParallelGroup(Alignment.BASELINE).addComponent(lblLift)
								.addComponent(spinnerLift, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_24.setLayout(gl_panel_24);

		JPanel panel_25 = new JPanel();
		panel_25.setBackground(Color.DARK_GRAY);
		panel_25.setBounds(10, 406, 138, 32);
		panel_14.add(panel_25);

		JLabel lblResistPain = new JLabel("Resist Pain");
		lblResistPain.setForeground(Color.WHITE);
		lblResistPain.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerResistPain = new JSpinner();
		GroupLayout gl_panel_25 = new GroupLayout(panel_25);
		gl_panel_25.setHorizontalGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_25.createSequentialGroup().addContainerGap().addComponent(lblResistPain)
						.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
						.addComponent(spinnerResistPain, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_25.setVerticalGroup(gl_panel_25.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_25.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE).addComponent(lblResistPain)
								.addComponent(spinnerResistPain, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_25.setLayout(gl_panel_25);

		JPanel panel_26 = new JPanel();
		panel_26.setBackground(Color.DARK_GRAY);
		panel_26.setBounds(10, 444, 138, 32);
		panel_14.add(panel_26);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setForeground(Color.WHITE);
		lblSearch.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerSearch = new JSpinner();
		GroupLayout gl_panel_26 = new GroupLayout(panel_26);
		gl_panel_26.setHorizontalGroup(gl_panel_26.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_26.createSequentialGroup().addContainerGap().addComponent(lblSearch)
						.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
						.addComponent(spinnerSearch, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_26.setVerticalGroup(gl_panel_26.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_26.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_26.createParallelGroup(Alignment.BASELINE).addComponent(lblSearch)
								.addComponent(spinnerSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_26.setLayout(gl_panel_26);

		JPanel panel_27 = new JPanel();
		panel_27.setBackground(Color.DARK_GRAY);
		panel_27.setBounds(10, 487, 138, 32);
		panel_14.add(panel_27);

		JLabel lblSpot = new JLabel("Spot");
		lblSpot.setForeground(Color.WHITE);
		lblSpot.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerSpot = new JSpinner();
		GroupLayout gl_panel_27 = new GroupLayout(panel_27);
		gl_panel_27.setHorizontalGroup(gl_panel_27.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_27.createSequentialGroup().addContainerGap().addComponent(lblSpot)
						.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
						.addComponent(spinnerSpot, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_27.setVerticalGroup(gl_panel_27.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_27
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_27.createParallelGroup(Alignment.BASELINE).addComponent(lblSpot).addComponent(
						spinnerSpot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_27.setLayout(gl_panel_27);

		JPanel panel_28 = new JPanel();
		panel_28.setBackground(Color.DARK_GRAY);
		panel_28.setBounds(10, 525, 138, 32);
		panel_14.add(panel_28);

		JLabel lblStealth = new JLabel("Stealth");
		lblStealth.setForeground(Color.WHITE);
		lblStealth.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerStealth = new JSpinner();
		GroupLayout gl_panel_28 = new GroupLayout(panel_28);
		gl_panel_28.setHorizontalGroup(gl_panel_28.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_28.createSequentialGroup().addContainerGap().addComponent(lblStealth)
						.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
						.addComponent(spinnerStealth, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_28.setVerticalGroup(gl_panel_28.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_28.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_28.createParallelGroup(Alignment.BASELINE).addComponent(lblStealth)
								.addComponent(spinnerStealth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_28.setLayout(gl_panel_28);

		JPanel panel_29 = new JPanel();
		panel_29.setBackground(Color.DARK_GRAY);
		panel_29.setBounds(10, 563, 138, 32);
		panel_14.add(panel_29);

		JLabel lblCamo = new JLabel("Camo");
		lblCamo.setForeground(Color.WHITE);
		lblCamo.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerCamo = new JSpinner();
		GroupLayout gl_panel_29 = new GroupLayout(panel_29);
		gl_panel_29.setHorizontalGroup(gl_panel_29.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_29.createSequentialGroup().addContainerGap().addComponent(lblCamo)
						.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
						.addComponent(spinnerCamo, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_29.setVerticalGroup(gl_panel_29.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_29.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_29.createParallelGroup(Alignment.BASELINE).addComponent(lblCamo)
								.addComponent(spinnerCamo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_29.setLayout(gl_panel_29);

		JPanel panel_30 = new JPanel();
		panel_30.setBackground(Color.DARK_GRAY);
		panel_30.setBounds(10, 606, 138, 32);
		panel_14.add(panel_30);

		JLabel lblCalm = new JLabel("Calm");
		lblCalm.setForeground(Color.WHITE);
		lblCalm.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerCalm = new JSpinner();
		GroupLayout gl_panel_30 = new GroupLayout(panel_30);
		gl_panel_30.setHorizontalGroup(gl_panel_30.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_30.createSequentialGroup().addContainerGap().addComponent(lblCalm)
						.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
						.addComponent(spinnerCalm, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_30.setVerticalGroup(gl_panel_30.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_30.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_30.createParallelGroup(Alignment.BASELINE).addComponent(lblCalm)
								.addComponent(spinnerCalm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_30.setLayout(gl_panel_30);

		JPanel panel_31 = new JPanel();
		panel_31.setBackground(Color.DARK_GRAY);
		panel_31.setBounds(10, 644, 138, 32);
		panel_14.add(panel_31);

		JLabel lblDiplomancy = new JLabel("Diplomancy");
		lblDiplomancy.setForeground(Color.WHITE);
		lblDiplomancy.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerDiplomacy = new JSpinner();
		GroupLayout gl_panel_31 = new GroupLayout(panel_31);
		gl_panel_31.setHorizontalGroup(gl_panel_31.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_31.createSequentialGroup().addContainerGap().addComponent(lblDiplomancy).addGap(18)
						.addComponent(spinnerDiplomacy, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(17, Short.MAX_VALUE)));
		gl_panel_31.setVerticalGroup(gl_panel_31.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_31.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_31.createParallelGroup(Alignment.BASELINE).addComponent(lblDiplomancy)
								.addComponent(spinnerDiplomacy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_31.setLayout(gl_panel_31);

		JPanel panel_32 = new JPanel();
		panel_32.setBackground(Color.DARK_GRAY);
		panel_32.setBounds(10, 682, 138, 32);
		panel_14.add(panel_32);

		JLabel lblBarter = new JLabel("Barter");
		lblBarter.setForeground(Color.WHITE);
		lblBarter.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerBarter = new JSpinner();
		GroupLayout gl_panel_32 = new GroupLayout(panel_32);
		gl_panel_32.setHorizontalGroup(gl_panel_32.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_32.createSequentialGroup().addContainerGap().addComponent(lblBarter)
						.addPreferredGap(ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
						.addComponent(spinnerBarter, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_32.setVerticalGroup(gl_panel_32.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_32.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_32.createParallelGroup(Alignment.BASELINE).addComponent(lblBarter)
								.addComponent(spinnerBarter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_32.setLayout(gl_panel_32);

		textFieldName = new JTextField();
		textFieldName.setColumns(10);

		JLabel lblNameEdit = new JLabel("Name");
		lblNameEdit.setForeground(Color.WHITE);
		lblNameEdit.setFont(new Font("Calibri", Font.PLAIN, 13));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(lblNameEdit).addGap(18)
						.addComponent(textFieldName, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblNameEdit)
								.addComponent(textFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(null);
		panel.add(btnAddToUnit);
		panel.add(textFieldCallsign);
		panel.add(panel_1);
		panel.add(panel_9);
		panel.add(panel_10);
		panel.add(panel_11);
		panel.add(panel_12);
		panel.add(panel_13);
		panel.add(panel_50);
		panel.add(panel_8);
		panel.add(panel_7);
		panel.add(panel_6);
		panel.add(panel_5);
		panel.add(panel_4);
		panel.add(panel_3);
		panel.add(panel_2);
		panel.add(panel_51);
		panel.add(textPaneNotes);
		panel.add(panel_89);
		panel.add(panel_78);
		panel.add(panel_90);
		panel.add(panel_91);
		panel.add(panel_92);
		panel.add(scrollPane_1);
		panel.add(scrollPane_2);
		panel.add(panel_83);

		JPanel panel_61 = new JPanel();
		panel_61.setLayout(null);
		panel_61.setBackground(Color.DARK_GRAY);
		panel_61.setBounds(729, 10, 137, 733);
		panel.add(panel_61);

		JPanel panel_63 = new JPanel();
		panel_63.setBackground(Color.DARK_GRAY);
		panel_63.setBounds(0, 298, 138, 32);
		panel_61.add(panel_63);

		JLabel lblSilentOp = new JLabel("Silent Op.");
		lblSilentOp.setForeground(Color.WHITE);
		lblSilentOp.setFont(new Font("Calibri", Font.PLAIN, 13));

		silentOperationsSpinner = new JSpinner();
		GroupLayout gl_panel_63 = new GroupLayout(panel_63);
		gl_panel_63.setHorizontalGroup(gl_panel_63.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_63.createSequentialGroup().addContainerGap().addComponent(lblSilentOp)
						.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE).addComponent(
								silentOperationsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_63.setVerticalGroup(gl_panel_63.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_63.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_63.createParallelGroup(Alignment.BASELINE).addComponent(lblSilentOp)
								.addComponent(silentOperationsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_63.setLayout(gl_panel_63);

		JPanel panel_64 = new JPanel();
		panel_64.setBackground(Color.DARK_GRAY);
		panel_64.setBounds(0, 255, 138, 32);
		panel_61.add(panel_64);

		JLabel lblReloadDrills = new JLabel("Reload Drills");
		lblReloadDrills.setForeground(Color.WHITE);
		lblReloadDrills.setFont(new Font("Calibri", Font.PLAIN, 13));

		reloadDrillsSpinner = new JSpinner();
		GroupLayout gl_panel_64 = new GroupLayout(panel_64);
		gl_panel_64.setHorizontalGroup(gl_panel_64.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_64.createSequentialGroup().addContainerGap().addComponent(lblReloadDrills)
						.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE).addComponent(
								reloadDrillsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_64.setVerticalGroup(gl_panel_64.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_64.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_64.createParallelGroup(Alignment.BASELINE).addComponent(lblReloadDrills)
								.addComponent(reloadDrillsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_64.setLayout(gl_panel_64);

		JPanel panel_65 = new JPanel();
		panel_65.setBackground(Color.DARK_GRAY);
		panel_65.setBounds(0, 217, 138, 32);
		panel_61.add(panel_65);

		JLabel lblTriggerDisc = new JLabel("Trigger Disc.");
		lblTriggerDisc.setForeground(Color.WHITE);
		lblTriggerDisc.setFont(new Font("Calibri", Font.PLAIN, 13));

		triggerDisciplineSpinner = new JSpinner();
		GroupLayout gl_panel_65 = new GroupLayout(panel_65);
		gl_panel_65.setHorizontalGroup(gl_panel_65.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_65.createSequentialGroup().addContainerGap().addComponent(lblTriggerDisc)
						.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE).addComponent(
								triggerDisciplineSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_65.setVerticalGroup(gl_panel_65.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_65.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_65.createParallelGroup(Alignment.BASELINE).addComponent(lblTriggerDisc)
								.addComponent(triggerDisciplineSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_65.setLayout(gl_panel_65);

		JPanel panel_66 = new JPanel();
		panel_66.setBackground(Color.DARK_GRAY);
		panel_66.setBounds(0, 174, 138, 32);
		panel_61.add(panel_66);

		JLabel lblRecoilCont = new JLabel("Recoil Cont.");
		lblRecoilCont.setForeground(Color.WHITE);
		lblRecoilCont.setFont(new Font("Calibri", Font.PLAIN, 13));

		recoilControlSpinner = new JSpinner();
		GroupLayout gl_panel_66 = new GroupLayout(panel_66);
		gl_panel_66.setHorizontalGroup(gl_panel_66.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_66.createSequentialGroup().addContainerGap().addComponent(lblRecoilCont)
						.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(
								recoilControlSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_66.setVerticalGroup(gl_panel_66.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_66.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_66.createParallelGroup(Alignment.BASELINE).addComponent(lblRecoilCont)
								.addComponent(recoilControlSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_66.setLayout(gl_panel_66);

		JPanel panel_67 = new JPanel();
		panel_67.setBackground(Color.DARK_GRAY);
		panel_67.setBounds(0, 92, 138, 32);
		panel_61.add(panel_67);

		JLabel lblFighter = new JLabel("Fighter");
		lblFighter.setForeground(Color.WHITE);
		lblFighter.setFont(new Font("Calibri", Font.PLAIN, 13));

		fighterSpinner = new JSpinner();
		GroupLayout gl_panel_67 = new GroupLayout(panel_67);
		gl_panel_67.setHorizontalGroup(gl_panel_67.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_67.createSequentialGroup().addContainerGap().addComponent(lblFighter)
						.addPreferredGap(ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
						.addComponent(fighterSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_67.setVerticalGroup(gl_panel_67.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_67.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_67.createParallelGroup(Alignment.BASELINE).addComponent(lblFighter)
								.addComponent(fighterSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_67.setLayout(gl_panel_67);

		JPanel panel_68 = new JPanel();
		panel_68.setBackground(Color.DARK_GRAY);
		panel_68.setBounds(0, 49, 138, 32);
		panel_61.add(panel_68);

		JLabel lblCovertMov = new JLabel("Covert Mov.");
		lblCovertMov.setForeground(Color.WHITE);
		lblCovertMov.setFont(new Font("Calibri", Font.PLAIN, 13));

		covertMovementSpinner = new JSpinner();
		GroupLayout gl_panel_68 = new GroupLayout(panel_68);
		gl_panel_68.setHorizontalGroup(gl_panel_68.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_68.createSequentialGroup().addContainerGap().addComponent(lblCovertMov)
						.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(
								covertMovementSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_68.setVerticalGroup(gl_panel_68.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_68.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_68.createParallelGroup(Alignment.BASELINE).addComponent(lblCovertMov)
								.addComponent(covertMovementSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_68.setLayout(gl_panel_68);

		JPanel panel_69 = new JPanel();
		panel_69.setBackground(Color.DARK_GRAY);
		panel_69.setBounds(0, 11, 138, 32);
		panel_61.add(panel_69);

		JLabel lblCleanOp = new JLabel("Clean Op.");
		lblCleanOp.setForeground(Color.WHITE);
		lblCleanOp.setFont(new Font("Calibri", Font.PLAIN, 13));

		cleanOperationsSpinner = new JSpinner();
		GroupLayout gl_panel_69 = new GroupLayout(panel_69);
		gl_panel_69.setHorizontalGroup(gl_panel_69.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_69.createSequentialGroup().addContainerGap().addComponent(lblCleanOp)
						.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE).addComponent(
								cleanOperationsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_69.setVerticalGroup(gl_panel_69.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_69.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_69.createParallelGroup(Alignment.BASELINE).addComponent(lblCleanOp)
								.addComponent(cleanOperationsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_69.setLayout(gl_panel_69);

		JPanel panel_70 = new JPanel();
		panel_70.setBackground(Color.DARK_GRAY);
		panel_70.setBounds(0, 341, 138, 32);
		panel_61.add(panel_70);

		JLabel lblAkSystems = new JLabel("AK Systems");
		lblAkSystems.setForeground(Color.WHITE);
		lblAkSystems.setFont(new Font("Calibri", Font.PLAIN, 13));

		akSystemsSpinner = new JSpinner();
		GroupLayout gl_panel_70 = new GroupLayout(panel_70);
		gl_panel_70.setHorizontalGroup(gl_panel_70.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_70.createSequentialGroup().addContainerGap().addComponent(lblAkSystems)
						.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
						.addComponent(akSystemsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_70.setVerticalGroup(gl_panel_70.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_70.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_70.createParallelGroup(Alignment.BASELINE).addComponent(lblAkSystems)
								.addComponent(akSystemsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_70.setLayout(gl_panel_70);

		JPanel panel_71 = new JPanel();
		panel_71.setBackground(Color.DARK_GRAY);
		panel_71.setBounds(0, 384, 138, 32);
		panel_61.add(panel_71);

		JLabel lblAssualtOp = new JLabel("Assualt Ops");
		lblAssualtOp.setForeground(Color.WHITE);
		lblAssualtOp.setFont(new Font("Calibri", Font.PLAIN, 13));

		assualtOperationsSpinner = new JSpinner();
		GroupLayout gl_panel_71 = new GroupLayout(panel_71);
		gl_panel_71.setHorizontalGroup(gl_panel_71.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_71.createSequentialGroup().addContainerGap().addComponent(lblAssualtOp)
						.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(
								assualtOperationsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_71.setVerticalGroup(gl_panel_71.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_71.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_71.createParallelGroup(Alignment.BASELINE).addComponent(lblAssualtOp)
								.addComponent(assualtOperationsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_71.setLayout(gl_panel_71);

		JPanel panel_72 = new JPanel();
		panel_72.setBackground(Color.DARK_GRAY);
		panel_72.setBounds(0, 427, 138, 32);
		panel_61.add(panel_72);

		JLabel lblAuthority = new JLabel("Authority");
		lblAuthority.setForeground(Color.WHITE);
		lblAuthority.setFont(new Font("Calibri", Font.PLAIN, 13));

		authoritySpinner = new JSpinner();
		GroupLayout gl_panel_72 = new GroupLayout(panel_72);
		gl_panel_72.setHorizontalGroup(gl_panel_72.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_72.createSequentialGroup().addContainerGap().addComponent(lblAuthority)
						.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
						.addComponent(authoritySpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_72.setVerticalGroup(gl_panel_72.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_72.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_72.createParallelGroup(Alignment.BASELINE).addComponent(lblAuthority)
								.addComponent(authoritySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_72.setLayout(gl_panel_72);

		JPanel panel_73 = new JPanel();
		panel_73.setBackground(Color.DARK_GRAY);
		panel_73.setBounds(0, 470, 138, 32);
		panel_61.add(panel_73);

		JLabel lblRawPower = new JLabel("Raw Power");
		lblRawPower.setForeground(Color.WHITE);
		lblRawPower.setFont(new Font("Calibri", Font.PLAIN, 13));

		rawPowerSpinner = new JSpinner();
		GroupLayout gl_panel_73 = new GroupLayout(panel_73);
		gl_panel_73.setHorizontalGroup(gl_panel_73.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_73.createSequentialGroup().addContainerGap().addComponent(lblRawPower)
						.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
						.addComponent(rawPowerSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_73.setVerticalGroup(gl_panel_73.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_73.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_73.createParallelGroup(Alignment.BASELINE).addComponent(lblRawPower)
								.addComponent(rawPowerSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_73.setLayout(gl_panel_73);

		JPanel panel_74 = new JPanel();
		panel_74.setBackground(Color.DARK_GRAY);
		panel_74.setBounds(0, 513, 138, 32);
		panel_61.add(panel_74);

		JLabel lblArSystems = new JLabel("AR Systems");
		lblArSystems.setForeground(Color.WHITE);
		lblArSystems.setFont(new Font("Calibri", Font.PLAIN, 13));

		arSystemsSpinner = new JSpinner();
		GroupLayout gl_panel_74 = new GroupLayout(panel_74);
		gl_panel_74.setHorizontalGroup(gl_panel_74.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_74.createSequentialGroup().addContainerGap().addComponent(lblArSystems)
						.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
						.addComponent(arSystemsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_74.setVerticalGroup(gl_panel_74.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_74.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_74.createParallelGroup(Alignment.BASELINE).addComponent(lblArSystems)
								.addComponent(arSystemsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_74.setLayout(gl_panel_74);

		JPanel panel_75 = new JPanel();
		panel_75.setBackground(Color.DARK_GRAY);
		panel_75.setBounds(0, 556, 138, 32);
		panel_61.add(panel_75);

		JLabel lblLongRangeOptics = new JLabel("Long Range");
		lblLongRangeOptics.setForeground(Color.WHITE);
		lblLongRangeOptics.setFont(new Font("Calibri", Font.PLAIN, 13));

		longRangeOpticsSpinner = new JSpinner();
		GroupLayout gl_panel_75 = new GroupLayout(panel_75);
		gl_panel_75.setHorizontalGroup(gl_panel_75.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_75.createSequentialGroup().addContainerGap().addComponent(lblLongRangeOptics)
						.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addComponent(
								longRangeOpticsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_75.setVerticalGroup(gl_panel_75.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_75.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_75.createParallelGroup(Alignment.BASELINE).addComponent(lblLongRangeOptics)
								.addComponent(longRangeOpticsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_75.setLayout(gl_panel_75);

		JPanel panel_76 = new JPanel();
		panel_76.setBackground(Color.DARK_GRAY);
		panel_76.setBounds(0, 599, 138, 32);
		panel_61.add(panel_76);

		JLabel lblNegotiations = new JLabel("Negotiations");
		lblNegotiations.setForeground(Color.WHITE);
		lblNegotiations.setFont(new Font("Calibri", Font.PLAIN, 13));

		negotiationsSpinner = new JSpinner();
		GroupLayout gl_panel_76 = new GroupLayout(panel_76);
		gl_panel_76.setHorizontalGroup(gl_panel_76.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_76.createSequentialGroup().addContainerGap().addComponent(lblNegotiations)
						.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE).addComponent(
								negotiationsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_76.setVerticalGroup(gl_panel_76.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_76.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_76.createParallelGroup(Alignment.BASELINE).addComponent(lblNegotiations)
								.addComponent(negotiationsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_76.setLayout(gl_panel_76);

		JPanel panel_77 = new JPanel();
		panel_77.setBackground(Color.DARK_GRAY);
		panel_77.setBounds(0, 642, 138, 32);
		panel_61.add(panel_77);

		JLabel lblSuTactics = new JLabel("SU Tactics");
		lblSuTactics.setForeground(Color.WHITE);
		lblSuTactics.setFont(new Font("Calibri", Font.PLAIN, 13));

		smallUnitTacticsSpinner = new JSpinner();
		GroupLayout gl_panel_77 = new GroupLayout(panel_77);
		gl_panel_77.setHorizontalGroup(gl_panel_77.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_77.createSequentialGroup().addContainerGap().addComponent(lblSuTactics)
						.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE).addComponent(
								smallUnitTacticsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_77.setVerticalGroup(gl_panel_77.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_77.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_77.createParallelGroup(Alignment.BASELINE).addComponent(lblSuTactics)
								.addComponent(smallUnitTacticsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_77.setLayout(gl_panel_77);

		JPanel panel_66_1 = new JPanel();
		panel_66_1.setBackground(Color.DARK_GRAY);
		panel_66_1.setBounds(0, 134, 138, 32);
		panel_61.add(panel_66_1);

		JLabel lblFighterRank = new JLabel("Fighter Rank:");
		lblFighterRank.setForeground(Color.WHITE);
		lblFighterRank.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerFighterRanks = new JSpinner();
		GroupLayout gl_panel_66_1 = new GroupLayout(panel_66_1);
		gl_panel_66_1.setHorizontalGroup(gl_panel_66_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_66_1.createSequentialGroup().addContainerGap().addComponent(lblFighterRank)
						.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(
								spinnerFighterRanks, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_66_1.setVerticalGroup(gl_panel_66_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_66_1.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_66_1.createParallelGroup(Alignment.BASELINE).addComponent(lblFighterRank)
								.addComponent(spinnerFighterRanks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_66_1.setLayout(gl_panel_66_1);
		panelIndividualEdit.setLayout(gl_panelIndividualEdit);
		GroupLayout groupLayout = new GroupLayout(f.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 916, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		JPanel panelAdditionalSkills = new JPanel();
		panelAdditionalSkills.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Additional Details", null, panelAdditionalSkills, null);
		panelAdditionalSkills.setLayout(null);

		labelCTP = new JLabel("CTP(Phases):");
		labelCTP.setForeground(Color.WHITE);
		labelCTP.setFont(new Font("Calibri", Font.PLAIN, 15));
		labelCTP.setDoubleBuffered(true);
		labelCTP.setBounds(466, 188, 112, 31);
		panelStats.add(labelCTP);

		labelRR = new JLabel("RR: ");
		labelRR.setForeground(Color.WHITE);
		labelRR.setFont(new Font("Calibri", Font.PLAIN, 15));
		labelRR.setDoubleBuffered(true);
		labelRR.setBounds(466, 217, 112, 31);
		panelStats.add(labelRR);

		labelTimePassed = new JLabel("Time Passed:");
		labelTimePassed.setForeground(Color.WHITE);
		labelTimePassed.setFont(new Font("Calibri", Font.PLAIN, 15));
		labelTimePassed.setDoubleBuffered(true);
		labelTimePassed.setBounds(466, 242, 112, 31);
		panelStats.add(labelTimePassed);

		JPanel panel_84 = new JPanel();
		panel_84.setBackground(Color.DARK_GRAY);
		panel_84.setBounds(190, 37, 150, 45);
		panelAdditionalSkills.add(panel_84);

		JLabel lblShields = new JLabel("Max Shields");
		lblShields.setForeground(Color.WHITE);
		lblShields.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerMaxShields = new JSpinner();
		GroupLayout gl_panel_84 = new GroupLayout(panel_84);
		gl_panel_84.setHorizontalGroup(gl_panel_84.createParallelGroup(Alignment.LEADING)
				.addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_84.createSequentialGroup().addContainerGap().addComponent(lblShields)
						.addPreferredGap(ComponentPlacement.UNRELATED, 23, Short.MAX_VALUE)
						.addComponent(spinnerMaxShields, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_84.setVerticalGroup(gl_panel_84.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_84.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_84.createParallelGroup(Alignment.BASELINE).addComponent(lblShields)
								.addComponent(spinnerMaxShields, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_84.setLayout(gl_panel_84);

		JLabel label_5 = new JLabel("CurrentHP");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("Calibri", Font.PLAIN, 13));
		label_5.setBounds(200, 274, 53, 16);
		panelAdditionalSkills.add(label_5);

		spinnerCurrentHp = new JSpinner();
		spinnerCurrentHp.setBounds(285, 271, 45, 20);
		panelAdditionalSkills.add(spinnerCurrentHp);

		JPanel panel_82 = new JPanel();
		panel_82.setBackground(Color.DARK_GRAY);
		panel_82.setBounds(13, 402, 177, 45);
		panelAdditionalSkills.add(panel_82);

		JLabel label_11 = new JLabel("Time Mortally Wnd.");
		label_11.setForeground(Color.WHITE);
		label_11.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerTimeMortallyWounded = new JSpinner();
		GroupLayout gl_panel_82 = new GroupLayout(panel_82);
		gl_panel_82.setHorizontalGroup(gl_panel_82.createParallelGroup(Alignment.LEADING)
				.addGap(0, 150, Short.MAX_VALUE).addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_82.createSequentialGroup().addContainerGap().addComponent(label_11)
						.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(spinnerTimeMortallyWounded, GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_82.setVerticalGroup(gl_panel_82.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_82.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_82.createParallelGroup(Alignment.BASELINE).addComponent(label_11)
								.addComponent(spinnerTimeMortallyWounded, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_82.setLayout(gl_panel_82);

		JPanel panel_81 = new JPanel();
		panel_81.setBackground(Color.DARK_GRAY);
		panel_81.setBounds(13, 354, 150, 38);
		panelAdditionalSkills.add(panel_81);

		JLabel lblTimeUncon = new JLabel("Time Uncon.");
		lblTimeUncon.setForeground(Color.WHITE);
		lblTimeUncon.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerTimeUnconscious = new JSpinner();
		GroupLayout gl_panel_81 = new GroupLayout(panel_81);
		gl_panel_81.setHorizontalGroup(gl_panel_81.createParallelGroup(Alignment.LEADING)
				.addGap(0, 150, Short.MAX_VALUE).addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_81.createSequentialGroup().addContainerGap().addComponent(lblTimeUncon)
						.addPreferredGap(ComponentPlacement.UNRELATED, 20, Short.MAX_VALUE)
						.addComponent(spinnerTimeUnconscious, GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_81.setVerticalGroup(gl_panel_81.createParallelGroup(Alignment.LEADING).addGap(0, 38, Short.MAX_VALUE)
				.addGap(0, 38, Short.MAX_VALUE)
				.addGroup(gl_panel_81.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_81.createParallelGroup(Alignment.BASELINE).addComponent(lblTimeUncon)
								.addComponent(spinnerTimeUnconscious, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_81.setLayout(gl_panel_81);

		JPanel panel_80 = new JPanel();
		panel_80.setBackground(Color.DARK_GRAY);
		panel_80.setBounds(13, 310, 150, 38);
		panelAdditionalSkills.add(panel_80);

		JLabel label_9 = new JLabel("Stabalized");
		label_9.setForeground(Color.WHITE);
		label_9.setFont(new Font("Calibri", Font.PLAIN, 13));

		checkBoxStabalized = new JCheckBox("");
		GroupLayout gl_panel_80 = new GroupLayout(panel_80);
		gl_panel_80.setHorizontalGroup(gl_panel_80.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_80.createSequentialGroup().addContainerGap().addComponent(label_9)
						.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
						.addComponent(checkBoxStabalized, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_80.setVerticalGroup(gl_panel_80.createParallelGroup(Alignment.LEADING).addGap(0, 38, Short.MAX_VALUE)
				.addGroup(gl_panel_80.createSequentialGroup().addGap(11)
						.addGroup(gl_panel_80.createParallelGroup(Alignment.TRAILING).addComponent(label_9)
								.addComponent(checkBoxStabalized))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_80.setLayout(gl_panel_80);

		JPanel panel_79 = new JPanel();
		panel_79.setBackground(Color.DARK_GRAY);
		panel_79.setBounds(13, 254, 150, 45);
		panelAdditionalSkills.add(panel_79);

		JLabel label_8 = new JLabel("Mortal Wound");
		label_8.setForeground(Color.WHITE);
		label_8.setFont(new Font("Calibri", Font.PLAIN, 13));

		checkBoxMortalWound = new JCheckBox("");
		GroupLayout gl_panel_79 = new GroupLayout(panel_79);
		gl_panel_79.setHorizontalGroup(gl_panel_79.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_79.createSequentialGroup().addContainerGap().addComponent(label_8)
						.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
						.addComponent(checkBoxMortalWound, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_79.setVerticalGroup(gl_panel_79.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_79
						.createSequentialGroup().addGap(11).addGroup(gl_panel_79.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_8).addComponent(checkBoxMortalWound))
						.addContainerGap(13, Short.MAX_VALUE)));
		panel_79.setLayout(gl_panel_79);

		JPanel panel_60 = new JPanel();
		panel_60.setBackground(Color.DARK_GRAY);
		panel_60.setBounds(13, 203, 150, 45);
		panelAdditionalSkills.add(panel_60);

		JLabel lblDisabledLegs = new JLabel("Disabled Legs");
		lblDisabledLegs.setForeground(Color.WHITE);
		lblDisabledLegs.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerDisabledLegs = new JSpinner();
		GroupLayout gl_panel_60 = new GroupLayout(panel_60);
		gl_panel_60.setHorizontalGroup(gl_panel_60.createParallelGroup(Alignment.LEADING)
				.addGap(0, 150, Short.MAX_VALUE).addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_60.createSequentialGroup().addContainerGap().addComponent(lblDisabledLegs)
						.addPreferredGap(ComponentPlacement.UNRELATED, 12, Short.MAX_VALUE)
						.addComponent(spinnerDisabledLegs, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_60.setVerticalGroup(gl_panel_60.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_60.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_60.createParallelGroup(Alignment.BASELINE).addComponent(lblDisabledLegs)
								.addComponent(spinnerDisabledLegs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_60.setLayout(gl_panel_60);

		JPanel panel_59 = new JPanel();
		panel_59.setBackground(Color.DARK_GRAY);
		panel_59.setBounds(13, 147, 150, 45);
		panelAdditionalSkills.add(panel_59);

		JLabel lblDisabledArms = new JLabel("Disabled Arms");
		lblDisabledArms.setForeground(Color.WHITE);
		lblDisabledArms.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerDisabledArms = new JSpinner();
		GroupLayout gl_panel_59 = new GroupLayout(panel_59);
		gl_panel_59.setHorizontalGroup(gl_panel_59.createParallelGroup(Alignment.LEADING)
				.addGap(0, 150, Short.MAX_VALUE).addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_59.createSequentialGroup().addContainerGap().addComponent(lblDisabledArms)
						.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(spinnerDisabledArms, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_59.setVerticalGroup(gl_panel_59.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_59.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_59.createParallelGroup(Alignment.BASELINE).addComponent(lblDisabledArms)
								.addComponent(spinnerDisabledArms, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_59.setLayout(gl_panel_59);

		JPanel panel_58 = new JPanel();
		panel_58.setBackground(Color.DARK_GRAY);
		panel_58.setBounds(13, 92, 150, 45);
		panelAdditionalSkills.add(panel_58);

		JLabel lblLegs = new JLabel("Legs");
		lblLegs.setForeground(Color.WHITE);
		lblLegs.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerLegs = new JSpinner();
		GroupLayout gl_panel_58 = new GroupLayout(panel_58);
		gl_panel_58
				.setHorizontalGroup(gl_panel_58.createParallelGroup(Alignment.LEADING).addGap(0, 150, Short.MAX_VALUE)
						.addGroup(gl_panel_58.createSequentialGroup().addContainerGap().addComponent(lblLegs)
								.addPreferredGap(ComponentPlacement.UNRELATED, 61, Short.MAX_VALUE)
								.addComponent(spinnerLegs, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_58.setVerticalGroup(gl_panel_58.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_58.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_58.createParallelGroup(Alignment.BASELINE).addComponent(lblLegs)
								.addComponent(spinnerLegs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_58.setLayout(gl_panel_58);

		JPanel panel_57 = new JPanel();
		panel_57.setBackground(Color.DARK_GRAY);
		panel_57.setBounds(13, 37, 150, 45);
		panelAdditionalSkills.add(panel_57);

		JLabel lblArms = new JLabel("Arms");
		lblArms.setForeground(Color.WHITE);
		lblArms.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerArms = new JSpinner();
		GroupLayout gl_panel_57 = new GroupLayout(panel_57);
		gl_panel_57
				.setHorizontalGroup(gl_panel_57.createParallelGroup(Alignment.LEADING).addGap(0, 150, Short.MAX_VALUE)
						.addGroup(gl_panel_57.createSequentialGroup().addContainerGap().addComponent(lblArms)
								.addPreferredGap(ComponentPlacement.UNRELATED, 59, Short.MAX_VALUE)
								.addComponent(spinnerArms, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_57.setVerticalGroup(gl_panel_57.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_57.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_57.createParallelGroup(Alignment.BASELINE).addComponent(lblArms)
								.addComponent(spinnerArms, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_57.setLayout(gl_panel_57);

		JPanel panel_56 = new JPanel();
		panel_56.setBackground(Color.DARK_GRAY);
		panel_56.setBounds(190, 363, 150, 45);
		panelAdditionalSkills.add(panel_56);

		JLabel label_7 = new JLabel("Awake");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("Calibri", Font.PLAIN, 13));

		checkBoxAwake = new JCheckBox("");
		GroupLayout gl_panel_56 = new GroupLayout(panel_56);
		gl_panel_56
				.setHorizontalGroup(gl_panel_56.createParallelGroup(Alignment.TRAILING).addGap(0, 150, Short.MAX_VALUE)
						.addGroup(gl_panel_56.createSequentialGroup().addContainerGap().addComponent(label_7)
								.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
								.addComponent(checkBoxAwake, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_56.setVerticalGroup(gl_panel_56.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_56
						.createSequentialGroup().addGap(11).addGroup(gl_panel_56.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_7).addComponent(checkBoxAwake))
						.addContainerGap(13, Short.MAX_VALUE)));
		panel_56.setLayout(gl_panel_56);

		JPanel panel_55 = new JPanel();
		panel_55.setBackground(Color.DARK_GRAY);
		panel_55.setBounds(190, 312, 150, 45);
		panelAdditionalSkills.add(panel_55);

		JLabel label_6 = new JLabel("Alive");
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("Calibri", Font.PLAIN, 13));

		checkBoxAlive = new JCheckBox("");
		GroupLayout gl_panel_55 = new GroupLayout(panel_55);
		gl_panel_55
				.setHorizontalGroup(gl_panel_55.createParallelGroup(Alignment.TRAILING).addGap(0, 150, Short.MAX_VALUE)
						.addGroup(gl_panel_55.createSequentialGroup().addContainerGap().addComponent(label_6)
								.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
								.addComponent(checkBoxAlive, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_55.setVerticalGroup(gl_panel_55.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_55
						.createSequentialGroup().addGap(11).addGroup(gl_panel_55.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_6).addComponent(checkBoxAlive))
						.addContainerGap(13, Short.MAX_VALUE)));
		panel_55.setLayout(gl_panel_55);

		JPanel panel_52 = new JPanel();
		panel_52.setBackground(Color.DARK_GRAY);
		panel_52.setBounds(190, 210, 150, 45);
		panelAdditionalSkills.add(panel_52);

		JLabel label_4 = new JLabel("MaxHP");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerMaxHP = new JSpinner();
		GroupLayout gl_panel_52 = new GroupLayout(panel_52);
		gl_panel_52
				.setHorizontalGroup(gl_panel_52.createParallelGroup(Alignment.LEADING).addGap(0, 150, Short.MAX_VALUE)
						.addGroup(gl_panel_52.createSequentialGroup().addContainerGap().addComponent(label_4)
								.addPreferredGap(ComponentPlacement.UNRELATED, 48, Short.MAX_VALUE)
								.addComponent(spinnerMaxHP, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_52.setVerticalGroup(gl_panel_52.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_52.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_52.createParallelGroup(Alignment.BASELINE).addComponent(label_4)
								.addComponent(spinnerMaxHP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_52.setLayout(gl_panel_52);

		JPanel panel_86 = new JPanel();
		panel_86.setBackground(Color.DARK_GRAY);
		panel_86.setBounds(190, 143, 150, 45);
		panelAdditionalSkills.add(panel_86);

		JLabel lblShieldChance = new JLabel("Shield %C");
		lblShieldChance.setForeground(Color.WHITE);
		lblShieldChance.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerShieldChance = new JSpinner();
		GroupLayout gl_panel_86 = new GroupLayout(panel_86);
		gl_panel_86.setHorizontalGroup(gl_panel_86.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_86.createSequentialGroup().addContainerGap()
						.addComponent(lblShieldChance, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE).addGap(18)
						.addComponent(spinnerShieldChance, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_86.setVerticalGroup(gl_panel_86.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_86.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_86.createParallelGroup(Alignment.BASELINE).addComponent(lblShieldChance)
								.addComponent(spinnerShieldChance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_86.setLayout(gl_panel_86);

		JPanel panel_85 = new JPanel();
		panel_85.setBackground(Color.DARK_GRAY);
		panel_85.setBounds(190, 88, 150, 45);
		panelAdditionalSkills.add(panel_85);

		JLabel lblCurrentShields = new JLabel("Current Shields");
		lblCurrentShields.setForeground(Color.WHITE);
		lblCurrentShields.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerCurrentShields = new JSpinner();
		GroupLayout gl_panel_85 = new GroupLayout(panel_85);
		gl_panel_85.setHorizontalGroup(gl_panel_85.createParallelGroup(Alignment.LEADING)
				.addGap(0, 150, Short.MAX_VALUE)
				.addGroup(gl_panel_85.createSequentialGroup().addContainerGap().addComponent(lblCurrentShields)
						.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(spinnerCurrentShields, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_85.setVerticalGroup(gl_panel_85.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_85.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_85.createParallelGroup(Alignment.BASELINE).addComponent(lblCurrentShields)
								.addComponent(spinnerCurrentShields, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_85.setLayout(gl_panel_85);

		JLabel lblRecivingFirstAid = new JLabel("Reciving First Aid");
		lblRecivingFirstAid.setForeground(Color.WHITE);
		lblRecivingFirstAid.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblRecivingFirstAid.setBounds(357, 42, 176, 16);
		panelAdditionalSkills.add(lblRecivingFirstAid);

		checkBoxFirstAid = new JCheckBox("");
		checkBoxFirstAid.setBounds(564, 37, 32, 21);
		panelAdditionalSkills.add(checkBoxFirstAid);

		spinnerRRMod = new JSpinner();
		spinnerRRMod.setBounds(551, 68, 45, 20);
		panelAdditionalSkills.add(spinnerRRMod);

		JLabel lblRrMod = new JLabel("RR Mod:");
		lblRrMod.setForeground(Color.WHITE);
		lblRrMod.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblRrMod.setBounds(357, 69, 136, 17);
		panelAdditionalSkills.add(lblRrMod);

		JLabel lblAidMod = new JLabel("Aid Mod:");
		lblAidMod.setForeground(Color.WHITE);
		lblAidMod.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblAidMod.setBounds(357, 95, 136, 17);
		panelAdditionalSkills.add(lblAidMod);

		spinnerAidMod = new JSpinner();
		spinnerAidMod.setBounds(551, 93, 45, 20);
		panelAdditionalSkills.add(spinnerAidMod);

		spinnerPhysiciansSkill = new JSpinner();
		spinnerPhysiciansSkill.setBounds(551, 123, 45, 20);
		panelAdditionalSkills.add(spinnerPhysiciansSkill);

		JLabel label_12 = new JLabel("Physicians Skill");
		label_12.setForeground(Color.WHITE);
		label_12.setFont(new Font("Calibri", Font.PLAIN, 13));
		label_12.setBounds(356, 126, 177, 16);
		panelAdditionalSkills.add(label_12);

		JButton btnCalculate = new JButton("Calculate Aid");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				trooper.recivingFirstAid = checkBoxFirstAid.isSelected();
				trooper.recoveryRollMod = (int) spinnerRRMod.getValue();
				trooper.physicianSkill = (int) spinnerPhysiciansSkill.getValue();
				trooper.aidMod = (int) spinnerAidMod.getValue();
				trooper.calculateInjury(gameWindow, gameWindow.conflictLog);
				gameWindow.conflictLog.addNewLine("Injuries Re-Calculated");
			}
		});
		btnCalculate.setBounds(402, 153, 147, 21);
		panelAdditionalSkills.add(btnCalculate);

		spinnerSpottingDifficulty = new JSpinner();
		spinnerSpottingDifficulty.setBounds(551, 237, 45, 20);
		panelAdditionalSkills.add(spinnerSpottingDifficulty);

		JLabel lblSpottingDifficulty = new JLabel("Spotting Difficulty");
		lblSpottingDifficulty.setForeground(Color.WHITE);
		lblSpottingDifficulty.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblSpottingDifficulty.setBounds(350, 239, 187, 16);
		panelAdditionalSkills.add(lblSpottingDifficulty);

		JLabel lblUnspottable = new JLabel("Unspottable");
		lblUnspottable.setForeground(Color.WHITE);
		lblUnspottable.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblUnspottable.setBounds(350, 273, 183, 16);
		panelAdditionalSkills.add(lblUnspottable);

		checkBoxUnspottable = new JCheckBox("");
		checkBoxUnspottable.setBounds(548, 268, 48, 21);
		panelAdditionalSkills.add(checkBoxUnspottable);

		JLabel lblIonDamage = new JLabel("Ion Damage");
		lblIonDamage.setForeground(Color.WHITE);
		lblIonDamage.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblIonDamage.setBounds(664, 156, 150, 17);
		panelAdditionalSkills.add(lblIonDamage);

		spinnerIonDamage = new JSpinner();
		spinnerIonDamage.setBounds(664, 184, 45, 20);
		panelAdditionalSkills.add(spinnerIonDamage);

		JLabel lblPcSize = new JLabel("PC Size");
		lblPcSize.setForeground(Color.WHITE);
		lblPcSize.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblPcSize.setBounds(350, 337, 187, 16);
		panelAdditionalSkills.add(lblPcSize);

		SpinnerNumberModel model = new SpinnerNumberModel(0.0, -1000.0, 1000.0, 0.1);
		spinnerPCSize = new JSpinner(model);
		spinnerPCSize.setBounds(551, 337, 45, 20);
		panelAdditionalSkills.add(spinnerPCSize);
		f.getContentPane().setLayout(groupLayout);

		JLabel lblPen = new JLabel("PEN");
		lblPen.setForeground(Color.WHITE);
		lblPen.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblPen.setDoubleBuffered(true);
		lblPen.setBounds(581, 302, 30, 31);
		panelStats.add(lblPen);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(581, 76, 270, 221);
		panelStats.add(scrollPane_5);

		listInjuries = new JList();
		scrollPane_5.setViewportView(listInjuries);
		listInjuries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				// listInjuries.remove(listInjuries.getSelectedIndex());
				// System.out.println("SelectedIndex: "+listInjuries.getSelectedIndex());
				openTrooper.removeInjury(listInjuries.getSelectedIndex(), window.gameWindow.conflictLog,
						window.gameWindow);
				DefaultListModel listInjuries2 = new DefaultListModel();

				for (Injuries injury : trooper.injuries) {

					listInjuries2.addElement(injury.toString());

				}

				listInjuries.setModel(listInjuries2);
			}
		});
		listInjuries.setForeground(Color.WHITE);
		listInjuries.setFont(new Font("Calibri", Font.PLAIN, 13));
		listInjuries.setBackground(Color.DARK_GRAY);

		JLabel lblDc = new JLabel("DC");
		lblDc.setForeground(Color.WHITE);
		lblDc.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblDc.setDoubleBuffered(true);
		lblDc.setBounds(681, 302, 30, 31);
		panelStats.add(lblDc);

		JLabel lblOf = new JLabel("O/F");
		lblOf.setForeground(Color.WHITE);
		lblOf.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblOf.setDoubleBuffered(true);
		lblOf.setBounds(681, 328, 30, 31);
		panelStats.add(lblOf);

		textFieldPen = new JTextField();
		textFieldPen.setColumns(10);
		textFieldPen.setBounds(611, 307, 62, 20);
		panelStats.add(textFieldPen);

		textFieldDC = new JTextField();
		textFieldDC.setColumns(10);
		textFieldDC.setBounds(716, 307, 62, 20);
		panelStats.add(textFieldDC);

		comboBoxOF = new JComboBox();
		comboBoxOF.setModel(new DefaultComboBoxModel(new String[] { "Open", "Fire" }));
		comboBoxOF.setSelectedIndex(0);
		comboBoxOF.setBounds(716, 333, 62, 20);
		panelStats.add(comboBoxOF);

		JButton btnNewButton = new JButton("Add Injury");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if ((int) spinnerPD.getValue() > 0) {

					trooper.physicalDamage += (int) spinnerPD.getValue();
					trooper.calculateInjury(gameWindow, gameWindow.conflictLog);
					refreshTrooper(trooper);
					gameWindow.conflictLog.addQueuedText();
					return;
				}

				ResolveHits resolveHits = new ResolveHits(trooper);

				Injuries newInjury = resolveHits.getPCHitsManual(Integer.parseInt(textFieldPen.getText()),
						Integer.parseInt(textFieldDC.getText()), comboBoxOF.getSelectedIndex());

				if (!textFieldHitLocationLower.getText().equals("") && !textFieldHitLocationUpper.getText().equals("")
						&& !textFieldSecondHitLocationLower.getText().equals("")
						&& !textFieldSecondHitLocationUpper.getText().equals("")) {

					newInjury = resolveHits.getPCHitsManual(Integer.parseInt(textFieldPen.getText()),
							Integer.parseInt(textFieldDC.getText()), comboBoxOF.getSelectedIndex(),
							Integer.parseInt(textFieldHitLocationLower.getText()),
							Integer.parseInt(textFieldHitLocationUpper.getText()),
							Integer.parseInt(textFieldSecondHitLocationLower.getText()),
							Integer.parseInt(textFieldSecondHitLocationUpper.getText()), gameWindow.conflictLog);

				} else if (!textFieldHitLocationLower.getText().equals("")
						&& !textFieldHitLocationUpper.getText().equals("")) {
					newInjury = resolveHits.getPCHitsManual(Integer.parseInt(textFieldPen.getText()),
							Integer.parseInt(textFieldDC.getText()), comboBoxOF.getSelectedIndex(),
							Integer.parseInt(textFieldHitLocationLower.getText()),
							Integer.parseInt(textFieldHitLocationUpper.getText()), -1, -1, gameWindow.conflictLog);
				}

				if (newInjury == null) {
					window.gameWindow.conflictLog.addNewLine("EPEN < 0.5");
				} else {
					trooper.injured(gameWindow.conflictLog, newInjury, gameWindow.game, gameWindow);

					ArrayList<Injuries> injuries = trooper.injuries;

					DefaultListModel listInjuries2 = new DefaultListModel();

					for (Injuries injury : trooper.injuries) {

						listInjuries2.addElement(injury.toString());

					}

					listInjuries.setModel(listInjuries2);
				}

				trooper.calculateInjury(gameWindow, gameWindow.conflictLog);
				refreshTrooper(trooper);
				gameWindow.conflictLog.addQueuedText();
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		panelIndividual.setLayout(null);
		btnNewButton.setBounds(581, 356, 197, 23);
		panelStats.add(btnNewButton);

		JLabel lblEncum = new JLabel("Encum:");
		lblEncum.setForeground(Color.WHITE);
		lblEncum.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblEncum.setDoubleBuffered(true);
		lblEncum.setBounds(620, 429, 53, 31);
		panelStats.add(lblEncum);

		lblIncipactationTime = new JLabel("Incipactation Time: ");
		lblIncipactationTime.setForeground(Color.WHITE);
		lblIncipactationTime.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblIncipactationTime.setBounds(309, 217, 153, 31);
		panelStats.add(lblIncipactationTime);
		panelIndividual.add(tabbedPane2);
		panelIndividual.add(lblP1);
		panelIndividual.add(lblP2);
		panelIndividual.add(button_1);
		panelIndividual.add(btnHunkerDown);
		panelIndividual.add(btnPass);

		buttonExport = new JButton("Export");
		buttonExport.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				openTrooper.exportStats((int) spinnerEncumberance.getValue());

			}
		});
		buttonExport.setBounds(281, 13, 86, 25);
		panelIndividual.add(buttonExport);

		JLabel lblX_1 = new JLabel("X: ");
		lblX_1.setForeground(Color.WHITE);
		lblX_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblX_1.setBounds(45, 328, 30, 31);
		panelStats.add(lblX_1);

		spinnerSpotHexX = new JSpinner();
		spinnerSpotHexX.setBounds(69, 333, 40, 20);
		panelStats.add(spinnerSpotHexX);

		spinnerSpotHexY = new JSpinner();
		spinnerSpotHexY.setBounds(143, 333, 40, 20);
		panelStats.add(spinnerSpotHexY);

		JLabel lblY_1 = new JLabel("Y: ");
		lblY_1.setForeground(Color.WHITE);
		lblY_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblY_1.setBounds(119, 328, 30, 31);
		panelStats.add(lblY_1);

		JButton btnSpotHex = new JButton("Spot Hex");
		btnSpotHex.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				spotTestHex(unit, trooper, (int) spinnerSpotHexX.getValue(), (int) spinnerSpotHexY.getValue());

			}
		});
		btnSpotHex.setBounds(205, 333, 89, 23);
		panelStats.add(btnSpotHex);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Random Loc." }));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(581, 333, 92, 20);
		panelStats.add(comboBox);

		lblAmmo = new JLabel("Ammo:");
		lblAmmo.setBounds(466, 271, 105, 31);
		panelStats.add(lblAmmo);
		lblAmmo.setForeground(Color.WHITE);
		lblAmmo.setFont(new Font("Calibri", Font.PLAIN, 15));

		spinnerKO = new JSpinner();
		spinnerKO.setBounds(731, 49, 47, 20);
		panelStats.add(spinnerKO);

		lblFatiguePoints = new JLabel("Fatigue Points:");
		lblFatiguePoints.setForeground(Color.WHITE);
		lblFatiguePoints.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblFatiguePoints.setBounds(309, 242, 153, 31);
		panelStats.add(lblFatiguePoints);

		comboBoxPassActivity = new JComboBox();
		comboBoxPassActivity.setModel(new DefaultComboBoxModel(new String[] { "Normal", "Light", "Hard" }));
		comboBoxPassActivity.setBounds(751, 13, 105, 24);
		panelIndividual.add(comboBoxPassActivity);
		lblFatiguePoints.setText("Fatigue Points: ");
		lblFatiguePoints
				.setText("Fatigue Points: " + Math.round(trooper.fatigueSystem.fatiguePoints.get() * 100.0) / 100.0);

		lblAV = new JLabel("Analetic Value:");
		lblAV.setForeground(Color.WHITE);
		lblAV.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblAV.setBounds(309, 271, 153, 31);
		panelStats.add(lblAV);

		lblAV.setText("Analetic Value: " + trooper.fatigueSystem.analeticValue);

		JLabel lblKd = new JLabel("KO:");
		lblKd.setForeground(Color.WHITE);
		lblKd.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblKd.setDoubleBuffered(true);
		lblKd.setBounds(687, 46, 37, 31);
		panelStats.add(lblKd);

		JLabel lblHitLocationZone = new JLabel("Hit Location Zone Limits");
		lblHitLocationZone.setForeground(Color.WHITE);
		lblHitLocationZone.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblHitLocationZone.setDoubleBuffered(true);
		lblHitLocationZone.setBounds(581, 384, 204, 20);
		panelStats.add(lblHitLocationZone);

		textFieldHitLocationLower = new JTextField();
		textFieldHitLocationLower.setColumns(10);
		textFieldHitLocationLower.setBounds(581, 408, 47, 20);
		panelStats.add(textFieldHitLocationLower);

		textFieldHitLocationUpper = new JTextField();
		textFieldHitLocationUpper.setColumns(10);
		textFieldHitLocationUpper.setBounds(638, 408, 47, 20);
		panelStats.add(textFieldHitLocationUpper);

		textFieldSecondHitLocationLower = new JTextField();
		textFieldSecondHitLocationLower.setColumns(10);
		textFieldSecondHitLocationLower.setBounds(693, 408, 47, 20);
		panelStats.add(textFieldSecondHitLocationLower);

		textFieldSecondHitLocationUpper = new JTextField();
		textFieldSecondHitLocationUpper.setColumns(10);
		textFieldSecondHitLocationUpper.setBounds(750, 408, 47, 20);
		panelStats.add(textFieldSecondHitLocationUpper);

		JLabel lblMagnification = new JLabel("Magnification:");
		lblMagnification.setForeground(Color.WHITE);
		lblMagnification.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblMagnification.setDoubleBuffered(true);
		lblMagnification.setBounds(160, 429, 89, 31);
		panelStats.add(lblMagnification);

		spinnerMagnification = new JSpinner();
		spinnerMagnification.setBounds(279, 434, 47, 20);
		panelStats.add(spinnerMagnification);

		spinnerPD = new JSpinner();
		spinnerPD.setBounds(817, 307, 47, 20);
		panelStats.add(spinnerPD);

		JLabel lblPd = new JLabel("PD:");
		lblPd.setForeground(Color.WHITE);
		lblPd.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblPd.setDoubleBuffered(true);
		lblPd.setBounds(788, 302, 37, 31);
		panelStats.add(lblPd);

		f.setVisible(true);

		JPanel panel_53 = new JPanel();
		panel_53.setLayout(null);
		panel_53.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Armor", null, panel_53, null);

		JLabel lblArmorPage = new JLabel("Armor Page");
		lblArmorPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage.setForeground(Color.WHITE);
		lblArmorPage.setFont(new Font("Calibri", Font.BOLD, 16));
		lblArmorPage.setBounds(10, 31, 268, 16);
		panel_53.add(lblArmorPage);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 110, 268, 634);
		panel_53.add(scrollPane_4);

		armorList = new JList();
		armorList.setBackground(Color.DARK_GRAY);
		armorList.setForeground(Color.WHITE);
		armorList.setFont(new Font("Calibri", Font.PLAIN, 13));
		scrollPane_4.setViewportView(armorList);

		JLabel lblShieldPage = new JLabel("Shield Page");
		lblShieldPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblShieldPage.setForeground(Color.WHITE);
		lblShieldPage.setFont(new Font("Calibri", Font.BOLD, 16));
		lblShieldPage.setBounds(288, 31, 268, 16);
		panel_53.add(lblShieldPage);

		JScrollPane scrollPane_7 = new JScrollPane();
		scrollPane_7.setBounds(288, 110, 268, 634);
		panel_53.add(scrollPane_7);

		shieldList = new JList();
		shieldList.setFont(new Font("Calibri", Font.PLAIN, 13));
		shieldList.setForeground(Color.WHITE);
		shieldList.setBackground(Color.DARK_GRAY);
		scrollPane_7.setViewportView(shieldList);

		comboBoxArmor = new JComboBox();
		comboBoxArmor.setSelectedIndex(-1);
		comboBoxArmor.setBounds(10, 79, 143, 20);
		panel_53.add(comboBoxArmor);

		JLabel label_17 = new JLabel("Armor Type");
		label_17.setForeground(Color.WHITE);
		label_17.setFont(new Font("Calibri", Font.PLAIN, 15));
		label_17.setBounds(10, 58, 143, 20);
		panel_53.add(label_17);

		comboBoxPersonalShield = new JComboBox();
		comboBoxPersonalShield.setSelectedIndex(-1);
		comboBoxPersonalShield.setBounds(288, 79, 177, 20);
		panel_53.add(comboBoxPersonalShield);

		JLabel label_24 = new JLabel("Current Shield Strength");
		label_24.setForeground(Color.WHITE);
		label_24.setFont(new Font("Calibri", Font.PLAIN, 13));
		label_24.setBounds(475, 58, 150, 17);
		panel_53.add(label_24);

		spinnerCurrentShieldStrength = new JSpinner();
		spinnerCurrentShieldStrength.setBounds(475, 79, 45, 20);
		panel_53.add(spinnerCurrentShieldStrength);

		JLabel lblShieldType = new JLabel("Shield Type");
		lblShieldType.setForeground(Color.WHITE);
		lblShieldType.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblShieldType.setBounds(288, 58, 143, 20);
		panel_53.add(lblShieldType);

		lblWep = new JLabel("Weapon:");
		lblWep.setBounds(326, 352, 146, 31);
		panelStats.add(lblWep);
		lblWep.setForeground(Color.WHITE);
		lblWep.setFont(new Font("Calibri", Font.PLAIN, 15));

		JButton btnCharacterBuilder = new JButton("Character Builder");
		btnCharacterBuilder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new CharacterBuilderWindow(openTrooper);

			}
		});
		btnCharacterBuilder.setBounds(118, 12, 153, 25);
		panelIndividual.add(btnCharacterBuilder);

		JPanel panel_53_1 = new JPanel();
		panel_53_1.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Inventory", null, panel_53_1, null);
		panel_53_1.setLayout(null);

		lblArmorPage_1 = new JLabel("Inventory");
		lblArmorPage_1.setBounds(10, 31, 433, 16);
		lblArmorPage_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage_1.setForeground(Color.WHITE);
		lblArmorPage_1.setFont(new Font("Calibri", Font.BOLD, 16));
		panel_53_1.add(lblArmorPage_1);

		JScrollPane scrollPane_8 = new JScrollPane();
		scrollPane_8.setBounds(10, 57, 433, 688);
		panel_53_1.add(scrollPane_8);

		listInventory = new JList();
		listInventory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (listInventory.getSelectedIndex() < 0)
					return;

				try {
					openTrooper.inventory.removeItem(listInventory.getSelectedIndex());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				refreshInventory();

			}
		});
		listInventory.setBackground(Color.DARK_GRAY);
		listInventory.setForeground(Color.WHITE);
		listInventory.setFont(new Font("Calibri", Font.PLAIN, 13));
		scrollPane_8.setViewportView(listInventory);

		JScrollPane scrollPane_8_1 = new JScrollPane();
		scrollPane_8_1.setBounds(453, 57, 433, 688);
		panel_53_1.add(scrollPane_8_1);

		listItems = new JList();
		listItems.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (listItems.getSelectedIndex() < 0)
					return;

				try {
					Item item = Item.allItems.get(listItems.getSelectedIndex());

					if (item.isRound()) {
						openTrooper.inventory.addItems(item.weaponType, item.ammoType,
								(int) spinnerItemCount.getValue());
					} else if (item.isWeapon()) {
						openTrooper.inventory.addItems(item.weaponType, (int) spinnerItemCount.getValue());
					} else {
						openTrooper.inventory.addItems(item.ammoType, (int) spinnerItemCount.getValue());
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				refreshInventory();

			}
		});
		listItems.setForeground(Color.WHITE);
		listItems.setFont(new Font("Calibri", Font.PLAIN, 13));
		listItems.setBackground(Color.DARK_GRAY);
		scrollPane_8_1.setViewportView(listItems);

		JLabel lblArmorPage_1_1 = new JLabel("Add Items");
		lblArmorPage_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmorPage_1_1.setForeground(Color.WHITE);
		lblArmorPage_1_1.setFont(new Font("Calibri", Font.BOLD, 16));
		lblArmorPage_1_1.setBounds(453, 30, 433, 16);
		panel_53_1.add(lblArmorPage_1_1);

		JLabel lblNewLabel = new JLabel("Count");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(453, 30, 67, 14);
		panel_53_1.add(lblNewLabel);

		spinnerItemCount = new JSpinner();
		spinnerItemCount
				.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		spinnerItemCount.setBounds(503, 27, 60, 20);
		panel_53_1.add(spinnerItemCount);

		lblEncumberance = new JLabel("New label");
		lblEncumberance.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEncumberance.setForeground(Color.WHITE);
		lblEncumberance.setBounds(10, 28, 176, 16);
		panel_53_1.add(lblEncumberance);

		// Calls methods
		// Sets all fields on both pages
		trooperBuilding = trooper.getBuilding();
		setDetails(trooper);
		setEdit(trooper);
		setEditSkills(trooper);
		setWeapon(trooper);
		setAction();
		ArrayList<Injuries> injuries = trooper.injuries;
		/*
		 * System.out.println("Trooper, active injuries: "+injuries.size());
		 * for(Injuries injury : trooper.injuries) {
		 * System.out.println(injury.toString()); }
		 */

		// Sets targetable Units
		refreshTargetUnits(window);

		// System.out.println("Spotted Open Trooper Size: "+spottedTroopers.size());

		refreshSpotted(trooper);
		refreshTargets();
		setComboBoxes();

		DefaultListModel listInjuries2 = new DefaultListModel();

		for (Injuries injury : trooper.injuries) {

			listInjuries2.addElement(injury.toString());

		}

		listInjuries.setModel(listInjuries2);

		refreshInventory();

	}

	// Sets possible shots based off of current Selected Aim Time
	public void PCShots() {
		if (comboBoxTargets.getSelectedIndex() < 1 || targetTroopers.size() < 1)
			return;

		// System.out.println("PC Shots");

		Trooper shooterTrooper = openTrooper;
		int oldSl = shooterTrooper.sl;
		Trooper targetTrooper = findTarget();
		Unit targetUnit = findTrooperUnit(targetTrooper);
		Unit shooterUnit = findTrooperUnit(shooterTrooper);

		int maxAim = comboBoxAimTime.getSelectedIndex() - 1;
		String wepName;
		if (comboBoxLauncher.getSelectedIndex() > 0) {
			wepName = comboBoxLauncher.getSelectedItem().toString();
			shooterTrooper.sl = PCUtility.getSL("Launcher", shooterTrooper);
		} else {
			wepName = shooterTrooper.wep;
		}
		// System.out.println("Wep Name: " + wepName);

		setAction();

		if (targetedFire == null) {
			TargetedFire tf = new TargetedFire(shooterTrooper, targetTrooper, shooterUnit, targetUnit, gameWindow,
					maxAim, TFCA + (int) spinnerCABonus.getValue(),
					(int) spinnerEALBonus.getValue() + (int) spinnerConsecutiveEALBonus.getValue(),
					(int) spinnerPercentBonus.getValue(), (int) spinner2YardHexRange.getValue(), wepName);

			if (chckbxFullAuto.isSelected()) {
				tf.setFullAuto();
				tf.setFullAutoTargetNumber();
			} else {
				tf.setTargetNumber();
			}

			tf.spentCA = spentCA;
			tempTF = tf;

			if (comboBoxAimTime.getSelectedIndex() > tempTF.spentAimTime) {
				Weapons wep = new Weapons().findWeapon(wepName);
				int oldAim = tempTF.aim(shooterTrooper, tempTF.spentAimTime - 1, wep);
				oldAim += tempTF.weaponConditionsMod(wep, tempTF.spentAimTime - 1);

				int newAim = tempTF.aim(shooterTrooper, comboBoxAimTime.getSelectedIndex() - 1, wep);
				newAim += tempTF.weaponConditionsMod(wep, comboBoxAimTime.getSelectedIndex() - 1);

				tempTF.ALMSum -= oldAim;
				tempTF.ALMSum += newAim;
				tempTF.EAL -= oldAim;
				tempTF.EAL += newAim;

				tempTF.setTargetNumber();
			}

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

			targetedFire.spentCA = TFCA;

			if (chckbxFullAuto.isSelected()) {
				targetedFire.setFullAuto();
			}

			if (targetedFire.shotsTaken > 0 && !targetedFire.consecutiveShots && !chckbxFullAuto.isSelected()) {
				targetedFire.EAL += 2;
				targetedFire.ALMSum += 2;
				targetedFire.consecutiveShots = true;
			}

			if (chckbxFullAuto.isSelected())
				targetedFire.setFullAutoTargetNumber();
			else
				targetedFire.setTargetNumber();

			if (comboBoxAimTime.getSelectedIndex() > targetedFire.spentAimTime) {
				Weapons wep = new Weapons().findWeapon(wepName);
				int oldAim = targetedFire.aim(shooterTrooper, targetedFire.spentAimTime - 1, wep);
				oldAim += targetedFire.weaponConditionsMod(wep, targetedFire.spentAimTime - 1);

				int newAim = targetedFire.aim(shooterTrooper, comboBoxAimTime.getSelectedIndex() - 1, wep);
				newAim += targetedFire.weaponConditionsMod(wep, comboBoxAimTime.getSelectedIndex() - 1);

				targetedFire.ALMSum -= oldAim;
				targetedFire.ALMSum += newAim;
				targetedFire.EAL -= oldAim;
				targetedFire.EAL += newAim;

				targetedFire.setTargetNumber();
			}

			/*
			 * lblPossibleShots.setText("Possible Shots: "+(targetedFire.possibleShots-
			 * targetedFire.shotsTaken));
			 * lblAimTime.setText("Aim Time: "+targetedFire.spentAimTime);
			 * 
			 * 
			 * lblTfSpentCa.setText("TF Spent CA: "+targetedFire.spentCA);
			 * lblTN.setText("Target Number: "+targetedFire.TN);
			 */
		}

		// TargetedFire(Trooper shooterTrooper, Trooper targetTrooper, Unit shooterUnit
		// , Unit targetUnit, GameWindow game, int maxAim)

		shooterTrooper.sl = oldSl;

	}

	public void PCFireGuiUpdates() {
		if (comboBoxTargets.getSelectedIndex() < 1)
			return;

		if (targetedFire == null && tempTF != null) {
			// System.out.println("TF Null");
			lblPossibleShots.setText("Possible Shots: " + (tempTF.possibleShots - tempTF.shotsTaken));
			lblAimTime.setText("Aim Time: " + tempTF.spentAimTime);

			String rslt = "Target Number: " + tempTF.TN;

			if (!tempTF.fullAutoResults.equals("")) {
				rslt += ", Full Auto: " + tempTF.fullAutoResults;
			}

			lblTN.setText(rslt);

			lblTfSpentCa.setText("TF Spent CA: " + tempTF.spentCA);
		} else if (targetedFire != null) {
			// System.out.println("TF Not Null");
			// System.out.println("Possible Shots: "+targetedFire.possibleShots+", Shots
			// Taken: "+targetedFire.shotsTaken);
			lblPossibleShots
					.setText("Possible Shots: " + (targetedFire.possibleShots - targetedFire.shotsTaken - TFCA));
			lblAimTime.setText("Aim Time: " + targetedFire.spentAimTime);

			String rslt = "Target Number: " + targetedFire.TN;

			if (!targetedFire.fullAutoResults.equals("")) {
				rslt += ", Full Auto: " + tempTF.fullAutoResults;
			}

			lblTN.setText(rslt);

			lblTfSpentCa.setText("TF Spent CA: " + targetedFire.spentCA);
		}

		lblAmmo.setText("Ammo: " + openTrooper.ammo);
		lblCombatActions.setText("TF CA: " + TFCA);
		gameWindow.conflictLog.addQueuedText();
		gameWindow.refreshInitiativeOrder();
		// refreshTargets();
	}

	public void PCFire() {

		boolean shots;

		if (chckbxFullAuto.isSelected()) {
			shots = openTrooper.inventory.fireShots(new Weapons().findWeapon(openTrooper.wep).fullAutoROF,
					new Weapons().findWeapon(openTrooper.wep));
		} else {
			int roll = new Random().nextInt(3) + 1;
			shots = openTrooper.inventory.fireShots(roll, new Weapons().findWeapon(openTrooper.wep));
		}

		// Checks for out of ammo
		if (!shots) {
			textPaneTargetedFire.setText("OUT OF AMMO");
			return;
		}

		if (chckbxFullAuto.isSelected() && new Weapons().findWeapon(openTrooper.wep).fullAutoROF == 0) {
			gameWindow.conflictLog.addNewLineToQueue("This weapon is not full auto capable.");
		}

		if (comboBoxTargets.getSelectedIndex() < 1)
			return;

		Trooper shooterTrooper = openTrooper;
		int oldSl = shooterTrooper.sl;
		Trooper targetTrooper = findTarget();
		Unit targetUnit = findTrooperUnit(targetTrooper);
		Unit shooterUnit = findTrooperUnit(shooterTrooper);

		int maxAim = comboBoxAimTime.getSelectedIndex() - 1;
		String wepName;
		if (comboBoxLauncher.getSelectedIndex() > 0) {
			String name = (String) comboBoxLauncher.getSelectedItem() + ": "
					+ comboBoxAmmoTypeLauncher.getSelectedItem().toString() + " round";

			if (!openTrooper.inventory.containsItem(name) && !chkbxOverRideInventory.isSelected()) {
				GameWindow.gameWindow.conflictLog
						.addNewLine("Could not make attack, trooper does not have item. Name: " + name);
				return;
			}
			wepName = comboBoxLauncher.getSelectedItem().toString();
			shooterTrooper.sl = PCUtility.getSL("Launcher", shooterTrooper);
		} else {
			wepName = shooterTrooper.wep;
		}
		Weapons weapon = new Weapons().findWeapon(wepName);

		// System.out.println("Wep Name: " + wepName);

		setAction();
		TargetedFire tf = new TargetedFire(shooterTrooper, targetTrooper, shooterUnit, targetUnit, gameWindow, maxAim,
				TFCA + (int) spinnerCABonus.getValue(),
				(int) spinnerEALBonus.getValue() + (int) spinnerConsecutiveEALBonus.getValue(),
				(int) spinnerPercentBonus.getValue(), (int) spinner2YardHexRange.getValue(), wepName);

		// System.out.println("Leaving TF");

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
				// System.out.println("Leaving Shot");
				if (chckbxFreeAction.isSelected()) {
					targetedFire.spentCA -= 1;
				}
			}

			// System.out.println("TARGETED FIRE: ");
			// System.out.println("targetedFire.shotsTaken: "+targetedFire.shotsTaken);
			// System.out.println("targetedFire.timeToReaction:
			// "+targetedFire.timeToReaction);
			if (targetedFire.shotsTaken >= targetedFire.timeToReaction && targetedFire.shotsTaken != 0
					&& this.reaction == null && targetTrooper.alive && targetTrooper.conscious
					&& targetTrooper.canAct(gameWindow.game)) {
				// React
				// System.out.println("REACTION");
				// ReactionToFireWindow reaction = new ReactionToFireWindow(shooterTrooper,
				// targetTrooper, windowOpenTrooper, gameWindow);
				// this.reaction = reaction;

			}
		}

		if (weapon.type.equals("Launcher")) {
			try {
				// System.out.println("Pass Launcher");
				InjuryLog.InjuryLog.addAlreadyInjured(targetedFire.targetUnit.X, targetedFire.targetUnit.Y);
				PCAmmo pcAmmo = weapon.pcAmmoTypes.get(comboBoxAmmoTypeLauncher.getSelectedIndex());
				Explosion explosion = new Explosion(pcAmmo);
				if (targetedFire.PCHits > 0) {
					// System.out.println("Pass Launcher 1, PC Ammo: " + pcAmmo.name + ", pen:" +
					// pcAmmo.impactDc
					// + ", dc: " + pcAmmo.impactDc);
					// Impact
					explosion.explosiveImpact(targetedFire.targetTrooper, pcAmmo, weapon);
					// System.out.println("Pass Launcher 2");
					// Linked explosion
					explosion.explodeTrooper(targetedFire.targetTrooper, 0);
					// System.out.println("Pass Launcher 3");
					explosion.excludeTroopers.add(targetedFire.targetTrooper);
				}

				// -12 EAL for 2 yard hex size +32 EAL for 20 yard hex size
				targetedFire.EAL += 20;

				int eal = targetedFire.BA > targetedFire.EAL ? targetedFire.EAL : targetedFire.BA;
				/*
				 * System.out.println( "Ternary Test, BA: " + targetedFire.BA + ", EAL: " +
				 * targetedFire.EAL + ", used Value: " + eal);
				 * System.out.println("TF Roll: +targetedFire.tfRoll");
				 * System.out.println("Odds: " + PCUtility.getOddsOfHitting(true, eal));
				 */
				// Hit Hex
				if (targetedFire.tfRoll <= PCUtility.getOddsOfHitting(true, eal + 20)) {
					// System.out.println("Pass Explosion 1");
					explosion.explodeHex(targetedFire.targetUnit.X, targetedFire.targetUnit.Y, "None");
					// System.out.println("Pass Explosion 2");
				}

				String name = (String) comboBoxLauncher.getSelectedItem() + ": "
						+ comboBoxAmmoTypeLauncher.getSelectedItem().toString() + " round";
				// System.out.println("Name: "+name);
				if (!chkbxOverRideInventory.isSelected()) {
					try {
						openTrooper.inventory.removeItem(name);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				reaction = null;
				possibleShots = false;
				targetedFire = null;
				PCShots();
			} catch (Exception e) {
				e.printStackTrace();
			}

			shooterTrooper.sl = oldSl;
			InjuryLog.InjuryLog.printResultsToLog();
			String name = (String) comboBoxLauncher.getSelectedItem() + ": "
					+ comboBoxAmmoTypeLauncher.getSelectedItem().toString() + " round.";
			System.out.println("Name: " + name);
			if (openTrooper.inventory.containsItem(name)) {
				try {
					openTrooper.inventory.removeItem(name);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return;
		}

		if (targetedFire.PCHits > 0) {
			ResolveHits resolveHits = new ResolveHits(targetTrooper, targetedFire.PCHits,
					new Weapons().findWeapon(shooterTrooper.wep), gameWindow.conflictLog,
					targetTrooper.returnTrooperUnit(gameWindow), shooterUnit, gameWindow);

			if (targetedFire.calledShot) {
				resolveHits.calledShot = true;
				resolveHits.calledShotBounds = targetedFire.calledShotBounds;

			}

			if (targetTrooper.returnTrooperUnit(gameWindow).suppression + targetedFire.PCHits < 100) {
				targetTrooper.returnTrooperUnit(gameWindow).suppression += targetedFire.PCHits;
			} else {
				targetTrooper.returnTrooperUnit(gameWindow).suppression = 100;
			}
			if (targetTrooper.returnTrooperUnit(gameWindow).organization - targetedFire.PCHits > 0) {
				targetTrooper.returnTrooperUnit(gameWindow).organization -= targetedFire.PCHits;
			} else {
				targetTrooper.returnTrooperUnit(gameWindow).organization = 0;
			}

			resolveHits.performCalculations(gameWindow.game, gameWindow.conflictLog);
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
			PCShots();
		}

		if (!targetTrooper.alive) {

			if (chckbxMultipleTargets.isSelected()) {

				targetedFire = null;
				possibleShots = true;
				reaction = null;
				PCShots();
			} else {
				// Performed after swing worker is done
				/*
				 * actionSpent(openUnit, index); openUnit.openNext(true); f.dispose();
				 */
			}

		}

		shooterTrooper.sl = oldSl;

		// setDetails(openTrooper);

	}

	public void setAction() {

		openTrooper.setPCStats();
		TFCA = openTrooper.combatActions;
	}

	public int calculateCT(int LSF) {

		int CT = 0;

		int column = 0;
		try {
			String path = System.getProperty("user.dir");
			FileInputStream excelFile = new FileInputStream(new File(path + "\\Leadership.xlsx"));
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

	/*
	 * public int calculateCA(double ms, int isf) {
	 * 
	 * int CA = 0; int column = 0; try { String path =
	 * System.getProperty("user.dir"); // System.out.println("PATH: "+path);
	 * FileInputStream excelFile = new FileInputStream(new File(path +
	 * "\\CA.xlsx")); Workbook workbook = new XSSFWorkbook(excelFile); Sheet
	 * worksheet = workbook.getSheetAt(0);
	 * 
	 * Row row = worksheet.getRow(0);
	 * 
	 * if (isf <= 7) {
	 * 
	 * column = 1;
	 * 
	 * } else {
	 * 
	 * for (int i = 1; i < 19; i++) { if (isf < row.getCell(i +
	 * 1).getNumericCellValue()) { column = i; break; } } }
	 * 
	 * for (int x = 1; x < 22; x++) {
	 * 
	 * if (ms == worksheet.getRow(x).getCell(0).getNumericCellValue()) { CA = (int)
	 * worksheet.getRow(x).getCell(column).getNumericCellValue(); break; } }
	 * 
	 * excelFile.close(); workbook.close(); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * return CA;
	 * 
	 * }
	 */

	public double maximumSpeed(int encum, Trooper trooper) {
		double baseSpeed = baseSpeed(encum, trooper);

		int column = 0;
		try {
			String path = System.getProperty("user.dir");
			FileInputStream excelFile = new FileInputStream(new File(path + "\\MaximumSpeed.xlsx"));
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
			String path = System.getProperty("user.dir");
			FileInputStream excelFile = new FileInputStream(new File(path + "\\BaseSpeed.xlsx"));
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

	// Sets combo boxes, selecting the top unit and the maximum shots
	public void setComboBoxes() {

		// Sets first target active

		// Targeted Fire
		/*
		 * if(comboBoxTargets.getItemCount() > 1) { comboBoxTargets.setSelectedIndex(1);
		 * }
		 */
		// Suppressive Fire
		/*
		 * if (comboBoxTargetUnits.getItemCount() > 1) {
		 * comboBoxTargetUnits.setSelectedIndex(1); }
		 */

		// Sets max shot active

		// Targeted Fire
		/*
		 * if(comboBoxAimTime.getItemCount() > 1) {
		 * comboBoxAimTime.setSelectedIndex(comboBoxAimTime.getItemCount() - 1); }
		 */

		if (openTrooper.stance == "Standing") {
			comboBoxStance.setSelectedIndex(0);
		} else if (openTrooper.stance == "Crouched") {
			comboBoxStance.setSelectedIndex(1);
		} else if (openTrooper.stance == "Prone") {
			comboBoxStance.setSelectedIndex(2);
		}
		if (openTrooper.manualStance)
			chckbxManualStance.setSelected(true);
		int index = 0;
		int count = 0;
		for (ArmorType type : ArmorType.values()) {
			comboBoxArmor.addItem(new Armor(type).armorName);
			if (type == openTrooper.armor.type) {
				index = count;
			}
			count++;
		}
		comboBoxArmor.setSelectedIndex(index);
		index = 0;
		count = 1;
		for (ShieldType type : ShieldType.values()) {
			comboBoxPersonalShield.addItem(new PersonalShield(type).shieldName);
			if (openTrooper.personalShield != null && type == openTrooper.personalShield.shieldType) {
				index = count;
			}
			count++;
		}
		comboBoxPersonalShield.setSelectedIndex(index - 1);

		comboBoxBuilding.removeAllItems();
		comboBoxBuilding.addItem("None");
		Hex hex = GameWindow.gameWindow.findHex(trooperUnit.X, trooperUnit.Y);
		if (trooperBuilding == null && hex != null) {
			for (Building building : hex.buildings) {

				comboBoxBuilding.addItem(building.name);

			}
			comboBoxBuilding.setSelectedIndex(0);
		} else if (hex != null) {
			comboBoxBuilding.removeAllItems();
			comboBoxBuilding.addItem("None");
			comboBoxBuilding.addItem("ALREADY INSIDE");
			comboBoxBuilding.setSelectedIndex(1);
		}

	}

	// Gets weapon name from trooper
	// Sets that weapon equal to the selected row in the drop down menu
	public void setWeapon(Trooper trooper) {
		String weapon = trooper.wep;
		String meleeWeapon = trooper.meleeWep;
		boolean wepFound = false;
		boolean supWepFound = false;
		boolean meleeWepFound = false;

		// comboBoxWeapon
		for (int i = 0; i < comboBoxWeapon.getItemCount(); i++) {
			comboBoxWeapon.setSelectedIndex(i);

			if (comboBoxWeapon.getSelectedItem().toString().equals(weapon)) {
				wepFound = true;
				break;
			}
		}

		// comboBoxMeleeWeapon
		for (int i = 0; i < comboBoxMeleeWeapon.getItemCount(); i++) {
			comboBoxMeleeWeapon.setSelectedIndex(i);

			if (comboBoxMeleeWeapon.getSelectedItem().toString().equals(meleeWeapon)) {
				meleeWepFound = true;
				break;
			}
		}

		if (!wepFound) {
			comboBoxWeapon.setSelectedIndex(0);
		}

		if (!meleeWepFound) {
			comboBoxMeleeWeapon.setSelectedIndex(0);
		}
	}

	// Saves Changes of all fields, sets trooper equal to the troop in its index at
	// the parent unit
	// Refreshes unit troopers panel
	public void saveChanges() {

		Trooper individual = openTrooper;

		// Sets basic fields
		individual.name = textFieldName.getText();
		individual.rank = textFieldRank.getText();
		individual.designation = textFieldRole.getText();
		individual.vet = textFieldVet.getText();
		individual.wep = textFieldWeapon.getText();
		individual.ammo = (int) spinnerAmmo.getValue();

		individual.kills = (int) spinnerKills.getValue();

		individual.armorLegacy = (int) spinnerArmor.getValue();
		individual.legArmor = (int) spinnerArmorLeg.getValue();
		individual.armArmor = (int) spinnerArmorArm.getValue();
		individual.headArmor = (int) spinnerArmorHead.getValue();

		// Sets skills equal to skill bonus
		// Individual.getSkillBonus(0);

		// Sets individual stats
		individual.str = (int) spinnerStr.getValue();
		individual.wit = (int) spinnerWit.getValue();
		individual.soc = (int) spinnerSoc.getValue();
		individual.wil = (int) spinnerWil.getValue();
		individual.per = (int) spinnerPer.getValue();
		individual.hlt = (int) spinnerHlt.getValue();
		individual.agi = (int) spinnerAgi.getValue();

		int attr[] = new int[7];
		attr[0] = individual.str;
		attr[1] = individual.wit;
		attr[2] = individual.soc;
		attr[3] = individual.wil;
		attr[4] = individual.per;
		attr[5] = individual.hlt;
		attr[6] = individual.agi;

		// Sets skills equal to spinners

		/*
		 * individual.ballance = (int) spinnerBallance.getValue(); individual.climb =
		 * (int) spinnerClimb.getValue(); individual.composure = (int)
		 * spinnerComposure.getValue(); individual.dodge = (int)
		 * spinnerDodge.getValue(); individual.endurance = (int)
		 * spinnerEndurance.getValue(); individual.expression = (int)
		 * spinnerExpression.getValue(); individual.grapple = (int)
		 * spinnerGrapple.getValue(); individual.hold = (int) spinnerHold.getValue();
		 * individual.jump = (int) spinnerJump.getValue(); individual.lift = (int)
		 * spinnerLift.getValue(); individual.resistPain = (int)
		 * spinnerResistPain.getValue(); individual.search = (int)
		 * spinnerSearch.getValue(); individual.spotListen = (int)
		 * spinnerSpot.getValue(); individual.stealth = (int) spinnerStealth.getValue();
		 * individual.camouflage = (int) spinnerCamo.getValue(); individual.calm = (int)
		 * spinnerCalm.getValue(); individual.diplomacy = (int)
		 * spinnerDiplomacy.getValue(); individual.barter = (int)
		 * spinnerBarter.getValue(); individual.command = (int)
		 * spinnerCommand.getValue(); individual.tactics = (int)
		 * spinnerTactics.getValue(); individual.detMotives = (int)
		 * spinnerDetMotives.getValue(); individual.intimidate = (int)
		 * spinnerIntimidate.getValue(); individual.persuade = (int)
		 * spinnerPersuade.getValue(); individual.digiSystems = (int)
		 * spinnerComputers.getValue(); individual.pistol = (int)
		 * spinnerPistol.getValue(); individual.heavy = (int) spinnerHeavy.getValue();
		 * individual.subgun = (int) spinnerSubgun.getValue(); individual.launcher =
		 * (int) spinnerLauncher.getValue(); individual.rifle = (int)
		 * spinnerRifle.getValue(); individual.explosives = (int)
		 * spinnerExplosives.getValue(); individual.advancedMedicine = (int)
		 * advancedMedicineSpinner.getValue(); individual.firstAid = (int)
		 * spinnerFirstAid.getValue(); individual.navigation = (int)
		 * spinnerNavigation.getValue(); individual.swim = (int) spinnerSwim.getValue();
		 * individual.Throw = (int) spinnerThrow.getValue();
		 * 
		 * individual.cleanOperations = (int) cleanOperationsSpinner.getValue();
		 * individual.covertMovement = (int) covertMovementSpinner.getValue();
		 * individual.fighter = (int) fighterSpinner.getValue();
		 * individual.skills.getSkill("Fighter").rank = (int)
		 * spinnerFighterRanks.getValue(); individual.recoilControl = (int)
		 * recoilControlSpinner.getValue(); individual.reloadDrills = (int)
		 * reloadDrillsSpinner.getValue(); individual.silentOperations = (int)
		 * silentOperationsSpinner.getValue();
		 * 
		 * individual.akSystems = (int) akSystemsSpinner.getValue();
		 * individual.assualtOperations = (int) assualtOperationsSpinner.getValue();
		 * individual.authority = (int) authoritySpinner.getValue(); individual.rawPower
		 * = (int) rawPowerSpinner.getValue();
		 * 
		 * individual.arSystems = (int) arSystemsSpinner.getValue();
		 * individual.longRangeOptics = (int) longRangeOpticsSpinner.getValue();
		 * individual.negotiations = (int) negotiationsSpinner.getValue();
		 * individual.smallUnitTactics = (int) smallUnitTacticsSpinner.getValue();
		 */

		individual.encumberance = (int) spinnerEncumberance.getValue();
		individual.carryingCapacity = (int) spinnerCapacity.getValue();

		// Creates PC stats
		individual.setPCStats();

		// Sets health window
		individual.currentHP = (int) spinnerCurrentHp.getValue();
		individual.hp = (int) spinnerMaxHP.getValue();
		if (checkBoxAlive.isSelected()) {
			individual.alive = true;
		} else {
			individual.alive = false;
		}
		if (checkBoxAwake.isSelected()) {
			individual.conscious = true;
		} else {
			individual.conscious = false;
		}

		if (chckbxReacted.isSelected()) {
			individual.reacted = true;
		} else {
			individual.reacted = true;
		}

		individual.legs = (int) spinnerLegs.getValue();
		individual.arms = (int) spinnerArms.getValue();
		individual.disabledLegs = (int) spinnerDisabledLegs.getValue();
		individual.disabledArms = (int) spinnerDisabledArms.getValue();

		individual.timeUnconscious = (int) spinnerTimeUnconscious.getValue();
		individual.timeMortallyWounded = (int) spinnerTimeMortallyWounded.getValue();
		individual.physicianSkill = (int) spinnerPhysiciansSkill.getValue();

		individual.aidMod = (int) spinnerAidMod.getValue();
		individual.recoveryRollMod = (int) spinnerRRMod.getValue();

		if (checkBoxMortalWound.isSelected()) {
			individual.mortallyWounded = true;
		} else {
			individual.mortallyWounded = false;
		}

		if (checkBoxStabalized.isSelected()) {
			individual.stabalized = true;
		} else {
			individual.stabalized = false;
		}

		individual.shields = (int) spinnerMaxShields.getValue();
		individual.currentShields = (int) spinnerCurrentShields.getValue();
		individual.shieldChance = (int) spinnerShieldChance.getValue();

		individual.spottingDifficulty = (int) spinnerSpottingDifficulty.getValue();
		if (checkBoxUnspottable.isSelected()) {
			individual.unspottable = true;
		} else {
			individual.unspottable = false;
		}

		if (checkBoxFirstAid.isSelected()) {
			individual.recivingFirstAid = true;
		} else {
			individual.recivingFirstAid = false;
		}

		individual.ionDamage = (int) spinnerIonDamage.getValue();

		if (individual.personalShield != null)
			individual.personalShield.currentShieldStrength = (int) spinnerCurrentShieldStrength.getValue();

		// Sets notes
		individual.notes = textPaneNotes.getText();
		individual.eqiupment = textPaneEquipment.getText();

		individual.setPCStats();

		// Create and set individual stats
		IndividualStats individualStats = new IndividualStats(individual.combatActions, individual.sal,
				individual.skills.getSkill("Pistol").value, individual.skills.getSkill("Rifle").value,
				individual.skills.getSkill("Launcher").value, individual.skills.getSkill("Heavy").value,
				individual.skills.getSkill("Subgun").value, true);

		individual.P1 = individualStats.P1;
		individual.P2 = individualStats.P2;

		individual.notes = textPaneNotes.getText();

		int count = 0;
		for (ArmorType type : ArmorType.values()) {
			if (comboBoxArmor.getSelectedIndex() < 0)
				break;

			if (count == comboBoxArmor.getSelectedIndex()) {
				individual.armor = new Armor(type);
				// System.out.println("New Armor Type: " + type);
				break;
			}

			count++;

		}

		count = 0;
		for (ShieldType type : ShieldType.values()) {
			if (comboBoxPersonalShield.getSelectedIndex() < 1) {
				individual.personalShield = null;
				break;
			}

			if (count == comboBoxPersonalShield.getSelectedIndex()) {

				if (individual.personalShield == null || individual.personalShield.shieldType != type)
					individual.personalShield = new PersonalShield(type);

				break;
			}

			count++;

		}

		individual.magnification = (int) spinnerMagnification.getValue();
		individual.PCSize = (double) spinnerPCSize.getValue();

		individual.setPCStats();

		individual.KO = (int) spinnerKO.getValue();

		openUnit.setTrooper(individual, index);

		openUnit.refreshIndividuals();

		f.dispose();

	}

	// Sets all of the fields and lists on the first details page
	public void setDetails(Trooper individual) {
		lblName.setText("Name: " + individual.name + ", Number: " + individual.number);
		labelRank.setText("Rank: " + individual.rank);
		lblDesignation.setText("Role: " + individual.designation);
		lblVet.setText("Vet: " + individual.vet);
		labelConscious.setText("Conscious: " + individual.conscious);
		lblIncipactationTime.setText("KO Time: " + individual.incapacitationTime);
		labelAlive.setText("Alive: " + individual.alive);
		lblP1.setText("P1: " + individual.spentPhase1 + "/" + individual.P1);
		lblP2.setText("P2: " + individual.spentPhase2 + "/" + individual.P2);
		lblWep.setText("Weapon: " + individual.wep);
		lblAmmo.setText("Ammo: " + individual.ammo);
		lblMaxHp.setText("Max HP: " + individual.hp);
		lblCurrentHp.setText("Current  HP: " + individual.currentHP);
		lblMaxShields_1.setText("Max Shields: " + individual.shields);
		lblCurrentShields_1.setText("Current Shields: " + individual.currentShields);
		labelRR.setText("RR: " + individual.recoveryRoll);
		labelCTP.setText("CTP(Phases): " + individual.criticalTime);
		labelTimePassed.setText("TP(Phases): " + individual.timePassed);

		/*
		 * int cffwSkills[] = new int[10]; cffwSkills[0] = individual.rifleRWS;
		 * cffwSkills[1] = individual.pistolRWS; cffwSkills[2] = individual.subgunRWS;
		 * cffwSkills[3] = individual.heavyRWS; cffwSkills[4] = individual.launcherRWS;
		 * cffwSkills[5] = individual.spotListen; cffwSkills[6] = individual.camouflage;
		 * cffwSkills[7] = individual.stealth; cffwSkills[8] = individual.command;
		 * cffwSkills[9] = individual.tactics;
		 */

		/*
		 * DefaultListModel cffwSkillsList = new DefaultListModel(); for (int i = 0; i <
		 * listCFFWSkills.getModel().getSize(); i++) { skills[i] =
		 * listCFFWSkills.getModel().getElementAt(i).toString() + " " + cffwSkills[i];
		 * 
		 * }
		 * 
		 * for (int i = 0; i < skills.length; i++) {
		 * cffwSkillsList.addElement(skills[i]); }
		 * 
		 * refreshSpotted(individual);
		 * 
		 * 
		 * // lblHD lblHD.setText("HD: "+individual.HD);
		 */

		// Injury list
		/*
		 * if(individual.injuries != null && individual.injuries.size() > 0) { String
		 * injuries = "";
		 * 
		 * for(int i = 0; i < individual.injuries.size(); i++) { injuries +=
		 * individual.injuries.get(i).toString() + "\n"; }
		 * textPaneInjuries.setText(injuries); }
		 */

		if (individual.recivingFirstAid)
			checkBoxFirstAid.setSelected(true);
		else
			checkBoxFirstAid.setSelected(false);

		if (individual.armor != null)
			SwingUtility.setList(armorList, individual.armor.getLocations());

		if (individual.personalShield != null)
			SwingUtility.setList(shieldList, individual.personalShield.getLocations());

	}

	public void refreshSpotted(Trooper individual) {

		// Clears target troopers
		targetTroopers.clear();

		ArrayList<Trooper> spottedIndividuals = new ArrayList<Trooper>();

		// Spotted list update
		DefaultListModel spottedList = new DefaultListModel();

		ArrayList<Spot> spotted = individual.spotted;

		for (int i = 0; i < spotted.size(); i++) {
			Spot spotAction = spotted.get(i);

			for (int j = 0; j < spotAction.spottedIndividuals.size(); j++) {

				// Checks if target individual is alive or hunkered down
				if (spotAction.spottedIndividuals.get(j).alive && spotAction.spottedIndividuals.get(j).HD == false) {

					// Adds individual to spotted list
					if (!spottedIndividuals.contains(spotAction.spottedIndividuals.get(j))) {

						spottedIndividuals.add(spotAction.spottedIndividuals.get(j));

						findTarget();

						if (spotAction.spottedUnit == null) {
							spottedList.addElement(findTrooperUnit(spotAction.spottedIndividuals.get(j)).callsign + "::"
									+ " " + spotAction.spottedIndividuals.get(j).number + " "
									+ spotAction.spottedIndividuals.get(j).name);
						} else {
							spottedList.addElement(spotAction.spottedUnit.callsign + "::" + " "
									+ spotAction.spottedIndividuals.get(j).number + " "
									+ spotAction.spottedIndividuals.get(j).name);
						}

					}

					// Adds to targetable individuals
					if (!targetTroopers.contains(spotAction.spottedIndividuals.get(j))) {
						targetTroopers.add(spotAction.spottedIndividuals.get(j));
					}

				}

				refreshTargets();
			}
		}

		listSpotted.setModel(spottedList);

		// Adds spotted individuals to JComboBoxes
		spotted = individual.spotted;

		// Clears comboBox
		comboBoxRemoveSpotted.removeAllItems();
		comboBoxRemoveSpotted.addItem("None");
		comboBoxRemoveSpotted.setSelectedIndex(0);

		for (int i = 0; i < spotted.size(); i++) {
			Spot spotAction = spotted.get(i);

			for (int j = 0; j < spotAction.spottedIndividuals.size(); j++) {
				// Checks if target individual is alive or hunkered down
				if (spotAction.spottedIndividuals.get(j).alive && spotAction.spottedIndividuals.get(j).HD == false) {
					Trooper trooper = spotAction.spottedIndividuals.get(j);
					comboBoxRemoveSpotted
							.addItem(findTrooperUnit(trooper) + ":: " + trooper.number + ": " + trooper.name);
					spottedTroopers.add(trooper);
				}
			}

		}

		// Adds units
		// Clears comboBox
		comboBoxAddUnit.setSelectedIndex(0);
		comboBoxAddUnit.removeAllItems();
		comboBoxAddUnit.addItem("None");

		// Units
		for (int i = 0; i < gameWindow.initiativeOrder.size(); i++) {
			comboBoxAddUnit.addItem(
					gameWindow.initiativeOrder.get(i).side + ":: " + gameWindow.initiativeOrder.get(i).callsign);
		}

		// Adds unit to spotting module

		comboBoxSpottingUnits.removeAllItems();
		comboBoxSpottingUnits.addItem("None");
		for (Unit spottedUnit : trooperUnit.lineOfSight)
			comboBoxSpottingUnits.addItem(spottedUnit.callsign);

	}

	// Sets all of the fields and lists on the edit page
	public void setEdit(Trooper individual) {
		textFieldName.setText(individual.name);
		textFieldRank.setText(individual.rank);

		// Sets role
		textFieldRole.setText(individual.designation);

		textFieldVet.setText(individual.vet);
		textFieldWeapon.setText(individual.wep);
		spinnerAmmo.setValue(individual.ammo);

		// Attritutes
		spinnerStr.setValue(individual.str);
		spinnerWit.setValue(individual.wit);
		spinnerSoc.setValue(individual.soc);
		spinnerWil.setValue(individual.wil);
		spinnerPer.setValue(individual.per);
		spinnerHlt.setValue(individual.hlt);
		spinnerAgi.setValue(individual.agi);
		if (individual.alive) {
			checkBoxAlive.setSelected(true);
		} else {
			checkBoxAlive.setSelected(false);
		}
		if (individual.conscious) {
			checkBoxAwake.setSelected(true);
		} else {
			checkBoxAwake.setSelected(false);
		}
		if (individual.reacted) {
			chckbxReacted.setSelected(true);
		} else {
			chckbxReacted.setSelected(false);
		}
		if (individual.nightVisionInUse) {
			chckbxNvgs.setSelected(true);
		} else {
			chckbxNvgs.setSelected(false);
		}
		if (individual.weaponLightOn) {
			chckbxWeaponLights.setSelected(true);
		} else {
			chckbxWeaponLights.setSelected(false);
		}

		spinnerKills.setValue(individual.kills);

		spinnerArmor.setValue(individual.armorLegacy);
		spinnerArmorArm.setValue(individual.armArmor);
		spinnerArmorLeg.setValue(individual.legArmor);
		spinnerArmorHead.setValue(individual.headArmor);
		if (individual.mortallyWounded) {
			checkBoxMortalWound.setSelected(true);
		} else {
			checkBoxMortalWound.setSelected(false);
		}
		if (individual.stabalized) {
			checkBoxStabalized.setSelected(true);
		} else {
			checkBoxStabalized.setSelected(false);
		}

		spinnerEncumberance.setValue(individual.encumberance);
		spinnerCapacity.setValue(individual.carryingCapacity);

		spinnerSpottingDifficulty.setValue(individual.spottingDifficulty);

		if (individual.unspottable) {
			checkBoxUnspottable.setSelected(true);
		} else {
			checkBoxUnspottable.setSelected(false);
		}

		if (individual.recivingFirstAid) {
			checkBoxFirstAid.setSelected(true);
		} else {
			checkBoxFirstAid.setSelected(false);
		}

		spinnerRRMod.setValue(individual.recoveryRollMod);
		spinnerAidMod.setValue(individual.aidMod);
		spinnerPhysiciansSkill.setValue(individual.physicianSkill);
		spinnerKO.setValue(individual.KO);
		if (individual.personalShield != null)
			spinnerCurrentShieldStrength.setValue(individual.personalShield.currentShieldStrength);
		spinnerIonDamage.setValue(individual.ionDamage);
		spinnerPCSize.setValue(individual.PCSize);
		spinnerMagnification.setValue(individual.magnification);
	}

	// Sets the skills spinners with individual's skills
	public void setEditSkills(Trooper individual) {
		spinnerBallance.setValue(individual.getSkill("Ballance"));
		spinnerClimb.setValue(individual.getSkill("Climb"));
		spinnerComposure.setValue(individual.getSkill("Composure"));
		spinnerDodge.setValue(individual.getSkill("Dodge"));
		spinnerEndurance.setValue(individual.getSkill("Endurance"));
		spinnerExpression.setValue(individual.getSkill("Expression"));
		spinnerGrapple.setValue(individual.getSkill("Grapple"));
		spinnerHold.setValue(individual.getSkill("Hold"));
		spinnerJump.setValue(individual.getSkill("Jump/Leap"));
		spinnerLift.setValue(individual.getSkill("Lift/Pull"));
		spinnerResistPain.setValue(individual.getSkill("Resist Pain"));
		spinnerSearch.setValue(individual.getSkill("Search"));
		spinnerSpot.setValue(individual.getSkill("Spot/Listen"));
		spinnerStealth.setValue(individual.getSkill("Stealth"));
		spinnerCamo.setValue(individual.getSkill("Camouflage"));
		spinnerCalm.setValue(individual.getSkill("Calm Other"));
		spinnerDiplomacy.setValue(individual.getSkill("Diplomacy"));
		spinnerBarter.setValue(individual.getSkill("Barter"));
		spinnerCommand.setValue(individual.getSkill("Command"));
		spinnerTactics.setValue(individual.getSkill("Tactics"));
		spinnerDetMotives.setValue(individual.getSkill("Determine Motives"));
		spinnerIntimidate.setValue(individual.getSkill("Intimidate"));
		spinnerPersuade.setValue(individual.getSkill("Persuade"));
		spinnerComputers.setValue(individual.getSkill("Digi. Systems"));
		spinnerPistol.setValue(individual.getSkill("Pistol"));
		spinnerHeavy.setValue(individual.getSkill("Heavy"));
		spinnerSubgun.setValue(individual.getSkill("Subgun"));
		spinnerLauncher.setValue(individual.getSkill("Launcher"));
		spinnerRifle.setValue(individual.getSkill("Rifle"));
		spinnerExplosives.setValue(individual.getSkill("Explosives"));
		spinnerFirstAid.setValue(individual.getSkill("First Aid"));
		advancedMedicineSpinner.setValue(individual.getSkill("Advanced Medicine"));
		spinnerNavigation.setValue(individual.getSkill("Navigation"));
		spinnerSwim.setValue(individual.getSkill("Swim"));
		spinnerThrow.setValue(individual.getSkill("Throw"));

		cleanOperationsSpinner.setValue(individual.getSkill("Clean Operations"));
		covertMovementSpinner.setValue(individual.getSkill("Covert Movement"));
		fighterSpinner.setValue(individual.getSkill("Fighter"));
		spinnerFighterRanks.setValue(individual.skills.getSkill("Fighter").rank);
		recoilControlSpinner.setValue(individual.getSkill("Recoil Control"));
		reloadDrillsSpinner.setValue(individual.getSkill("Reload Drills"));
		silentOperationsSpinner.setValue(individual.getSkill("Silent Operations"));

		akSystemsSpinner.setValue(individual.getSkill("AK Systems"));
		assualtOperationsSpinner.setValue(individual.getSkill("Assault Operations"));
		authoritySpinner.setValue(individual.getSkill("Authority"));
		rawPowerSpinner.setValue(individual.getSkill("Raw Power"));

		arSystemsSpinner.setValue(individual.getSkill("AR Systems"));
		longRangeOpticsSpinner.setValue(individual.getSkill("Long Range Optics"));
		negotiationsSpinner.setValue(individual.getSkill("Negotiations"));
		smallUnitTacticsSpinner.setValue(individual.getSkill("Small Unit Tactics"));

		textPaneNotes.setText(individual.notes);
		textPaneEquipment.setText(individual.eqiupment);

	}

	// Act
	// Increaes Spent AP by 1
	public void actionSpent(OpenUnit window, int index) {
		Trooper trooper = window.openUnit.troopers.get(index);

		/*
		 * if(!firedWeapons) { trooper.firedTracers = false; }
		 */

		float time = 60;

		if (gameWindow.game.getPhase() == 1) {

			if (trooper.P1 > 0)
				time = 60 / trooper.P1;
		} else {
			if (trooper.P2 > 0)
				time = 60 / trooper.P2;
		}

		if (window.gameWindow.game.getPhase() == 1) {
			trooper.spentPhase1 += 1;
			// trooper.fatigueSystem.AddStrenuousActivityTime(time);
		} else {
			trooper.spentPhase2 += 1;
			// trooper.fatigueSystem.AddStrenuousActivityTime(time);
		}

		window.openUnit.refreshIndividuals();
		refreshTrooper(trooper);
	}

	// Spot test
	public void spotTest(String targetCallsign, OpenUnit window, Trooper trooper, Unit unit, int index) {
		if (!chckbxFreeSpotTest.isSelected()) {
			actionSpent(window, index);
		}

		// Find spotter unit
		Unit spotterUnit = unit;

		// Find target unit
		findTargetUnit(window, targetCallsign);

		Spot spotAction = new Spot(gameWindow, spotterUnit, targetUnit, trooper,
				comboBoxScanArea.getSelectedItem().toString(), gameWindow.visibility, gameWindow.initiativeOrder,
				gameWindow);

		// Print results
		window.gameWindow.conflictLog.addNewLine(
				"Spotted: " + "\nSuccess: " + spotAction.success + "\n" + spotAction.spottedIndividuals.toString());

		// Set results in trooper
		trooper.spotted.add(spotAction);
		// Refresh trooper
		refreshTrooper(trooper);
		window.openUnit.troopers.set(index, trooper);

	}

	// Spot(Unit spotterUnit, int xCord, int yCord, Trooper spotter, String
	// scanArea, String visibility, ArrayList<Unit> initiativeOrder, GameWindow
	// game)

	// spotTest(String targetCallsign, OpenUnit window, Trooper trooper, Unit unit,
	// int index)

	public void spotTestHex(Unit unit, Trooper spotter, int x, int y) {
		// Find spotter unit
		Unit spotterUnit = unit;

		Spot spotAction = new Spot(spotterUnit, x, y, spotter, "180", gameWindow.visibility, gameWindow.initiativeOrder,
				gameWindow);

		// Print results
		gameWindow.conflictLog.addNewLine(
				"Spotted: " + "\nSuccess: " + spotAction.success + "\n" + spotAction.spottedIndividuals.toString());

		// Set results in trooper
		spotter.spotted.add(spotAction);

		// Refresh trooper
		refreshTrooper(spotter);

	}

	public void refreshTrooper(Trooper trooper) {

		setDetails(trooper);
		setEdit(trooper);
		setEditSkills(trooper);
		refreshInventory();
	}

	public void refreshInventory() {

		ArrayList<String> items = openTrooper.inventory.getItems();

		SwingUtility.setList(listInventory, items);

		items = new ArrayList<>();

		for (Item item : Item.allItems) {
			items.add(item.getItemName());
		}

		SwingUtility.setList(listItems, items);
		setEdit(openTrooper);

		lblEncumberance.setText("Encumerance: " + openTrooper.encumberance);
	}

	// Populates the target dropdown menus based off of the list of spotted
	// individuals
	public void refreshTargets() {
		if (targetTroopers == null || targetTroopers.size() < 1) {
			return;
		}

		String[] targets = new String[targetTroopers.size() + 1];
		targets[0] = "None";

		int targetIndex = comboBoxTargets.getSelectedIndex();

		DefaultComboBoxModel grenadeTargetsModel = new DefaultComboBoxModel();
		DefaultComboBoxModel launcherTargetsModel = new DefaultComboBoxModel();
		DefaultComboBoxModel meleeTargetsModel = new DefaultComboBoxModel();
		launcherTargetsModel.addElement("None");
		grenadeTargetsModel.addElement("None");
		meleeTargetsModel.addElement("None");
		meleeTargets.clear();
		grenadeTargets.clear();
		for (int i = 0; i < targetTroopers.size(); i++) {
			Trooper target = targetTroopers.get(i);
			/*
			 * if(target.alive == false || target.conscious == false || target.HD) continue;
			 */

			Unit targetUnit = null;
			// gets troopers unit
			for (int j = 0; j < gameWindow.initiativeOrder.size(); j++) {

				for (int x = 0; x < gameWindow.initiativeOrder.get(j).getSize(); x++) {
					// System.out.println("pass1");
					if (gameWindow.initiativeOrder.get(j).getTroopers().get(x) == null) {

					} else if (gameWindow.initiativeOrder.get(j).getTroopers().get(x).compareTo(target)) {
						targetUnit = gameWindow.initiativeOrder.get(j);
						// System.out.println("pass2");
					}
				}
			}

			if (gameWindow == null) {
				// System.out.println("Fail 002");
			}
			if (targetUnit == null) {
				// System.out.println("Fail 001");
				return;
			}

			launcherTargetsModel.addElement(targetUnit.callsign + ":: " + target.number + ":: " + target.name);
			grenadeTargetsModel.addElement(targetUnit.callsign + ":: " + target.number + ":: " + target.name);
			grenadeTargets.add(target);
			if (targetUnit.X == trooperUnit.X && targetUnit.Y == trooperUnit.Y) {
				meleeTargets.add(target);
				meleeTargetsModel.addElement(targetUnit.callsign + ":: " + target.number + ":: " + target.name);

			}

			if (target.inCover) {
				targets[i + 1] = targetUnit.callsign + ":: In Cover: " + target.number + ":: " + target.name;
			} else if (!target.conscious) {
				targets[i + 1] = targetUnit.callsign + ":: UNCONSCIOUS: " + target.number + ":: " + target.name;
			} else if (!target.alive) {
				targets[i + 1] = targetUnit.callsign + ":: DEAD: " + target.number + ":: " + target.name;
			} else {
				targets[i + 1] = targetUnit.callsign + ":: " + target.number + ":: " + target.name;
			}

			if (openTrooper.storedAimTime.containsKey(target)) {
				targets[i + 1] = "Targeted: " + targets[i + 1];
			}
		}
		// System.out.println("\n\nTargetTroopers: " + targetTroopers.toString());

		comboBoxGrenadeTargets.setModel(grenadeTargetsModel);
		DefaultComboBoxModel model = new DefaultComboBoxModel(targets);
		comboBoxTargets.setModel(model);
		comboBoxMeleeTargets.setModel(meleeTargetsModel);

		if (targetIndex >= comboBoxTargets.getItemCount())
			targetIndex = 0;

		comboBoxTargets.setSelectedIndex(targetIndex);
	}

	public void refreshTargetUnits(OpenUnit openUnit) {
		/*
		 * ArrayList<Unit> units = new ArrayList<Unit>(); for (int i = 0; i <
		 * openUnit.gameWindow.initiativeOrder.size(); i++) { Unit tempUnit =
		 * openUnit.gameWindow.initiativeOrder.get(i); if
		 * (!tempUnit.side.equals(openUnit.unit.side)) { units.add(tempUnit); } }
		 * 
		 * if (units == null || units.size() < 1) { return; }
		 */

		comboBoxTargetUnits.removeAllItems();

		comboBoxTargetUnits.addItem("None");

		for (Unit targetUnit : trooperUnit.lineOfSight)
			comboBoxTargetUnits.addItem(targetUnit.callsign);

		if (comboBoxTargetUnits.getItemCount() > 0)
			comboBoxTargetUnits.setSelectedIndex(0);

		/*
		 * String[] targets = new String[units.size() + 1]; targets[0] = "None";
		 * 
		 * for (int i = 0; i < units.size(); i++) { Unit targetUnit = units.get(i);
		 * 
		 * if(!trooperUnit.lineOfSight.contains(targetUnit)) continue;
		 * 
		 * targets[i + 1] = targetUnit.callsign; }
		 * System.out.println("\n\nTargetUnits: " + targets.toString());
		 * 
		 * DefaultComboBoxModel model = new DefaultComboBoxModel(targets);
		 * comboBoxTargetUnits.setModel(model);
		 */
	}

	public Trooper findTarget() {
		if (targetTroopers == null || targetTroopers.size() < 1 || comboBoxTargets.getSelectedIndex() == 0) {
			/*
			 * System.out.println("Fail 2"); System.out.println("Target Troopers Fail 2: " +
			 * targetTroopers.toString()); System.out.println("comboBoxint: " +
			 * comboBoxTargets.getSelectedIndex());
			 */
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
		ArrayList<Unit> units = gameWindow.initiativeOrder;

		for (Unit unit : units) {

			for (Trooper trooper1 : unit.getTroopers()) {

				if (trooper1.compareTo(trooper))
					return unit;

			}

		}

		return null;

	}

	public int getRWSSuppressive(Trooper trooper) {
		// System.out.println("Get rws");
		int rws = 0;
		if (trooper == null) {
			// System.out.println("Trooper is null");
			return rws;
		}

		Weapons weapon = new Weapons();
		weapon.createWeapons();
		ArrayList<Weapons> weapons = weapon.getWeapons();
		// System.out.println("Weapons to string: " + weapons.toString());
		for (int i = 0; i < weapons.size(); i++) {
			if (comboBoxWeapon.getSelectedItem().toString().equals(weapons.get(i).name)) {
				// System.out.println("Weapon match");

				String weaponType = weapons.get(i).type;

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

			}
		}

		// Apply GURPS missing arm penalty
		if (trooper.disabledArms > 0) {
			rws -= 20;
		}

		return rws;
	}

	// Gets weapon
	public Weapons getWeapon() {
		Weapons weapon = new Weapons();
		weapon.createWeapons();
		ArrayList<Weapons> weapons = weapon.getWeapons();
		for (int i = 0; i < weapons.size(); i++) {
			if (comboBoxWeapon.getSelectedItem().toString().equals(weapons.get(i).name)) {
				return weapon = weapons.get(i);
			}
		}
		return weapon;
	}

	// Gets weapon
	public Weapons getMeleeWeapon() {
		Weapons weapon = new Weapons();
		weapon.createWeapons();
		ArrayList<Weapons> weapons = weapon.getWeapons();
		for (int i = 0; i < weapons.size(); i++) {
			if (comboBoxMeleeWeapon.getSelectedItem().toString().equals(weapons.get(i).name)) {
				return weapon = weapons.get(i);
			}
		}
		return weapon;
	}

	// Melee attack action
	// Rolls Melee attack
	// Applies damage
	public void meleeAttack(OpenUnit window, Trooper attacker) {
		if (comboBoxMeleeTargets.getSelectedIndex() < 0) {
			return;
		}

		Random rand = new Random();

		Trooper target = meleeTargets.get(comboBoxMeleeTargets.getSelectedIndex());

		int attackerRoll = rand.nextInt(100) + 1;
		int targetRoll = rand.nextInt(100) + 1;

		// Apply GURPS missing arm penalty
		if (attacker.disabledArms > 0) {
			attackerRoll += 20;
		}

		int attackerMargin = attacker.getSkill("Fighter") - attackerRoll;
		int targetMargin = target.getSkill("Fighter") - targetRoll;

		int trooperHits = 0;

		if (attackerMargin > -1 && attackerMargin > targetMargin) {
			trooperHits++;
		}

		String result = "";

		if (trooperHits > 0) {
			result = "HIT";
		} else {
			result = "MISS";
		}

		window.gameWindow.conflictLog
				.addNewLine("Melee Attack: " + result + ", AM: " + attackerMargin + ", TM: " + targetMargin);

		Weapons weapon = getMeleeWeapon();

		// System.out.println("WEAPON: "+weapon.toString());

		Unit tempUnit = window.gameWindow.initiativeOrder.get(0);

		for (int i = 0; i < window.gameWindow.initiativeOrder.size(); i++) {
			// System.out.println("Looking for unit");

			Unit targetUnit = null;
			// gets troopers unit
			for (int j = 0; j < gameWindow.initiativeOrder.size(); j++) {

				for (int x = 0; x < gameWindow.initiativeOrder.get(j).getSize(); x++) {
					// System.out.println("pass1");
					if (gameWindow.initiativeOrder.get(j).getTroopers().get(x) == null) {

					} else if (gameWindow.initiativeOrder.get(j).getTroopers().get(x).compareTo(target)) {
						targetUnit = gameWindow.initiativeOrder.get(j);
						// System.out.println("pass2");

					}

				}

			}

			// System.out.println("targetUnit: "+targetUnit.callsign);
			if (window.gameWindow.initiativeOrder.get(i).compareTo(targetUnit)) {
				// System.out.println("Pass melee unit");
				tempUnit = window.gameWindow.initiativeOrder.get(i);

			}
		}

		if (trooperHits > 0) {
			ResolveHits resolveHits = new ResolveHits(target, trooperHits, weapon, window.gameWindow.conflictLog,
					tempUnit, trooperUnit, gameWindow);
			resolveHits.performCalculations(window.gameWindow.game, openTrooper.str / 3);
			target = resolveHits.returnTarget();
		}

		window.openUnit.troopers.set(index, attacker);
		refreshTrooper(attacker);
		window.openUnit.refreshIndividuals();

		// Adds action point, if it is not a free action
		if (!chckbxFreeAction.isSelected()) {
			actionSpent(window, index);
		}

	}

	public void guiUpdates() {

		ArrayList<Shoot> shots = new ArrayList<>();
		shots.add(shoot);
		if (shoot == null) {
			System.out.println("Shoot is null 2");
			return;
		}
		ShootUtility.shootGuiUpdate(lblPossibleShots, lblAimTime, lblTN, lblTfSpentCa, lblAmmo, lblCombatActions,
				chckbxFullAuto, shots);
	}

	public void setCalledShotBounds() {
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
		if (shoot == null)
			return;

		shoot.setBonuses((int) spinnerPercentBonus.getValue(), (int) spinnerEALBonus.getValue(),
				(int) spinnerConsecutiveEALBonus.getValue());
		guiUpdates();
	}

}
