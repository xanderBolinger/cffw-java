package Individuals;

import Trooper.IndividualStats;
import Trooper.Skills;
import Trooper.Trooper;
import Company.EditCompany;
import CreateGame.JsonSaveRunner;
import CreateGame.TrooperJson;
import Injuries.Injuries;
import Unit.EditUnit;
import Unit.Unit;
import UtilityClasses.TrooperUtility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

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
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import CharacterBuilder.CharacterBuilderWindow;

import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class EditIndividual implements Serializable {

	private JLabel lblName;
	private JLabel labelRank;
	private JLabel lblDesignation;
	private JLabel lblVet;
	private JLabel lblConscious;
	private JLabel lblPD;
	private JLabel lblP1;
	private JLabel lblP2;
	private JLabel lblWep;
	private JLabel lblAmmo;
	private JList listCFFWSkills;
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
	private JTextField textFieldUnit;
	private JButton btnAddToUnit;
	private JTextPane textPaneNotes;
	private JTextField textFieldRole;
	private JLabel lblAlive_1;
	private JLabel lblCurrentHp;
	private JLabel lblMaxHp;
	private JSpinner advancedMedicineSpinner;
	private JSpinner cleanOperationsSpinner;
	private JSpinner covertMovementSpinner;
	private JSpinner fighterSpinner;
	private JSpinner recoilControlSpinner;
	private JSpinner triggerDisciplineSpinner;
	private JSpinner reloadDrillsSpinner;
	private JSpinner silentOperationsSpinner;
	private JSpinner akSystemsSpinner;
	private JSpinner assualtOperationsSpinner;
	private JSpinner authoritySpinner;
	private JSpinner rawPowerSpinner;
	private JSpinner arSystemsSpinner;
	private JSpinner longRangeOpticsSpinner;
	private JSpinner negotiationsSpinner;
	private JSpinner smallUnitTacticsSpinner;
	private JSpinner spinnerKills;
	private JSpinner spinnerCurrentShields;
	private JSpinner spinnerShieldChance;
	private JLabel lblMaxShields_1;
	private JLabel lblCurrentShields_1;
	private JTextPane textPaneEquipment;
	private JSpinner spinnerMaxHP;
	private JSpinner spinnerCurrentHp;
	private JCheckBox checkBoxAlive;
	private JCheckBox checkBoxAwake;
	private JCheckBox checkBoxMortalWound;
	private JCheckBox checkBoxStabalized;
	private JSpinner spinnerTimeUnconscious;
	private JSpinner spinnerTimeMortallyWounded;
	private JSpinner spinnerPhysiciansSkill;
	private JSpinner spinnerArms;
	private JSpinner spinnerLegs;
	private JSpinner spinnerDisabledArms;
	private JSpinner spinnerDisabledLegs;
	private JSpinner spinnerMaxShields;
	private JList listInjuries;
	private JSpinner spinnerRRMod;
	private JCheckBox chckbxFirstAid;
	private JLabel lblTP;
	private JLabel lblRR;
	private JLabel lblCTP;
	private JSpinner spinnerKO;
	private JSpinner spinnerFighterRanks;
	public Trooper trooper;

	/**
	 * Launch the application.
	 */
	public EditIndividual(Trooper trooper, EditCompany editCompany, EditUnit editUnit, int index) {
		this.trooper = trooper;
		final JFrame f = new JFrame("Edit Individual");
		f.setSize(957, 778);
		f.getContentPane().setLayout(null);
		f.setVisible(true);
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - f.getWidth()) / 2;
		int y = (screenSize.height - f.getHeight()) / 2;

		// Set the new frame location
		f.setLocation(x, y);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(f.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE));

		JPanel panelIndividual = new JPanel();
		panelIndividual.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Details", null, panelIndividual, null);

		JTabbedPane tabbedPane2 = new JTabbedPane(JTabbedPane.TOP);

		lblP1 = new JLabel("P1:");
		lblP1.setForeground(Color.WHITE);
		lblP1.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblP2 = new JLabel("P2:");
		lblP2.setForeground(Color.WHITE);
		lblP2.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblWep = new JLabel("Weapon:");
		lblWep.setForeground(Color.WHITE);
		lblWep.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblAmmo = new JLabel("Ammo:");
		lblAmmo.setForeground(Color.WHITE);
		lblAmmo.setFont(new Font("Calibri", Font.PLAIN, 15));

		JButton btnNewButton = new JButton("Open Character Builder");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				openCharacterBuilder();

			}
		});

		JButton btnNewButton_1 = new JButton("Load JSON");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					loadTrooper();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		JButton btnNewButton_1_1 = new JButton("Save JSON");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String json = JsonSaveRunner.saveTrooper(new TrooperJson(trooper));

				try {
					JsonSaveRunner.saveFile(trooper.name, json);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panelIndividual = new GroupLayout(panelIndividual);
		gl_panelIndividual.setHorizontalGroup(gl_panelIndividual.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
				.addGroup(gl_panelIndividual.createSequentialGroup().addContainerGap()
						.addComponent(lblP1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblP2, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblWep, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblAmmo, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnNewButton_1_1, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panelIndividual.setVerticalGroup(gl_panelIndividual.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelIndividual.createSequentialGroup().addContainerGap().addGroup(gl_panelIndividual
						.createParallelGroup(Alignment.LEADING)
						.addComponent(lblP1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelIndividual.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblP2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblWep, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelIndividual.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAmmo, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton).addComponent(btnNewButton_1)
								.addComponent(btnNewButton_1_1)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tabbedPane2, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE).addGap(0)));

		JPanel panelStats = new JPanel();
		panelStats.setBackground(Color.DARK_GRAY);
		tabbedPane2.addTab("Stats", null, panelStats, null);

		JLabel lblBasic = new JLabel("Basic");
		lblBasic.setBounds(51, 37, 51, 31);
		lblBasic.setFont(new Font("Calibri", Font.PLAIN, 25));
		lblBasic.setForeground(Color.WHITE);

		lblName = new JLabel("Name:");
		lblName.setBounds(51, 74, 226, 31);
		lblName.setForeground(Color.WHITE);
		lblName.setFont(new Font("Calibri", Font.PLAIN, 15));

		labelRank = new JLabel("Rank:");
		labelRank.setBounds(51, 111, 226, 31);
		labelRank.setForeground(Color.WHITE);
		labelRank.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblDesignation = new JLabel("Role:");
		lblDesignation.setBounds(51, 148, 226, 31);
		lblDesignation.setForeground(Color.WHITE);
		lblDesignation.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblVet = new JLabel("Vet:");
		lblVet.setBounds(51, 189, 226, 31);
		lblVet.setForeground(Color.WHITE);
		lblVet.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblInjuries = new JLabel("Equipment");
		lblInjuries.setBounds(480, 408, 447, 31);
		lblInjuries.setForeground(Color.WHITE);
		lblInjuries.setFont(new Font("Calibri", Font.PLAIN, 25));

		JScrollPane scrollEquipment = new JScrollPane();
		scrollEquipment.setBounds(480, 445, 447, 185);

		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(51, 226, 226, 31);
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setFont(new Font("Calibri", Font.PLAIN, 25));

		JLabel lblSkills = new JLabel("Skills");
		lblSkills.setBounds(252, 37, 53, 31);
		lblSkills.setForeground(Color.WHITE);
		lblSkills.setFont(new Font("Calibri", Font.PLAIN, 25));

		JScrollPane scrollPaneCFFWSkills = new JScrollPane();
		scrollPaneCFFWSkills.setBounds(252, 74, 163, 556);
		scrollPaneCFFWSkills.setBorder(null);

		lblConscious = new JLabel("Conscious:");
		lblConscious.setBounds(51, 263, 226, 31);
		lblConscious.setForeground(Color.WHITE);
		lblConscious.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblPD = new JLabel("PD:");
		lblPD.setBounds(51, 330, 256, 31);
		lblPD.setDoubleBuffered(true);
		lblPD.setForeground(Color.WHITE);
		lblPD.setFont(new Font("Calibri", Font.PLAIN, 15));

		JLabel lblHpStatus = new JLabel("HP Status");
		lblHpStatus.setBounds(51, 371, 226, 31);
		lblHpStatus.setForeground(Color.WHITE);
		lblHpStatus.setFont(new Font("Calibri", Font.PLAIN, 25));

		lblAlive_1 = new JLabel("Alive:");
		lblAlive_1.setBounds(51, 296, 226, 31);
		lblAlive_1.setForeground(Color.WHITE);
		lblAlive_1.setFont(new Font("Calibri", Font.PLAIN, 15));

		lblMaxHp = new JLabel("Max HP:");
		lblMaxHp.setBounds(51, 445, 232, 31);
		lblMaxHp.setForeground(Color.WHITE);
		lblMaxHp.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblMaxHp.setDoubleBuffered(true);

		lblCurrentHp = new JLabel("Current HP:");
		lblCurrentHp.setBounds(51, 482, 232, 31);
		lblCurrentHp.setForeground(Color.WHITE);
		lblCurrentHp.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblCurrentHp.setDoubleBuffered(true);

		lblMaxShields_1 = new JLabel("Max Shields: 0");
		lblMaxShields_1.setBounds(51, 519, 232, 31);
		lblMaxShields_1.setForeground(Color.WHITE);
		lblMaxShields_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblMaxShields_1.setDoubleBuffered(true);

		lblCurrentShields_1 = new JLabel("Current Shields: 0");
		lblCurrentShields_1.setBounds(51, 556, 232, 31);
		lblCurrentShields_1.setForeground(Color.WHITE);
		lblCurrentShields_1.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblCurrentShields_1.setDoubleBuffered(true);

		textPaneEquipment = new JTextPane();
		textPaneEquipment.setBackground(Color.DARK_GRAY);
		textPaneEquipment.setForeground(Color.WHITE);
		scrollEquipment.setViewportView(textPaneEquipment);

		listCFFWSkills = new JList();
		listCFFWSkills.setModel(new AbstractListModel() {
			String[] values = new String[] { "Rifle RWS: ", "Pistol RWS:", "Subgun RWS:", "Heavy RWS:", "Launcher RWS:",
					"Spot:", "Camoflauge:", "Stealth:", "Command:", "Tactics:" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listCFFWSkills.setBackground(Color.DARK_GRAY);
		listCFFWSkills.setForeground(Color.WHITE);
		listCFFWSkills.setFont(new Font("Calibri", Font.PLAIN, 13));
		scrollPaneCFFWSkills.setViewportView(listCFFWSkills);
		panelStats.setLayout(null);
		panelStats.add(lblConscious);
		panelStats.add(lblBasic);
		panelStats.add(lblStatus);
		panelStats.add(lblName);
		panelStats.add(lblVet);
		panelStats.add(lblDesignation);
		panelStats.add(labelRank);
		panelStats.add(lblHpStatus);
		panelStats.add(lblCurrentShields_1);
		panelStats.add(lblMaxShields_1);
		panelStats.add(lblCurrentHp);
		panelStats.add(lblPD);
		panelStats.add(lblAlive_1);
		panelStats.add(lblMaxHp);
		panelStats.add(lblSkills);
		panelStats.add(scrollPaneCFFWSkills);
		panelStats.add(lblInjuries);
		panelStats.add(scrollEquipment);

		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(595, 47, 270, 228);
		panelStats.add(scrollPane_5);

		listInjuries = new JList();
		listInjuries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// listInjuries.remove(listInjuries.getSelectedIndex());
				trooper.removeInjury(listInjuries.getSelectedIndex(), null, null);
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
		scrollPane_5.setViewportView(listInjuries);

		JLabel lblInjuries_1 = new JLabel("Injuries");
		lblInjuries_1.setForeground(Color.WHITE);
		lblInjuries_1.setFont(new Font("Calibri", Font.PLAIN, 25));
		lblInjuries_1.setBounds(595, 10, 197, 31);
		panelStats.add(lblInjuries_1);

		lblTP = new JLabel("TP(Phases): 0");
		lblTP.setForeground(Color.WHITE);
		lblTP.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblTP.setDoubleBuffered(true);
		lblTP.setBounds(473, 101, 112, 31);
		panelStats.add(lblTP);

		lblRR = new JLabel("RR: 0");
		lblRR.setForeground(Color.WHITE);
		lblRR.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblRR.setDoubleBuffered(true);
		lblRR.setBounds(473, 76, 112, 31);
		panelStats.add(lblRR);

		lblCTP = new JLabel("CTP(Phases): 0");
		lblCTP.setForeground(Color.WHITE);
		lblCTP.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblCTP.setDoubleBuffered(true);
		lblCTP.setBounds(473, 47, 112, 31);
		panelStats.add(lblCTP);

		JButton btnCalculate = new JButton("Calculate Aid");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				trooper.recivingFirstAid = chckbxFirstAid.isSelected();
				// System.out.println("Trooper reciving first aid: "+trooper.recivingFirstAid);
				trooper.recoveryRollMod = (int) spinnerRRMod.getValue();
				trooper.calculateInjury(null, null);
				refreshInjuries(trooper);
				editUnit.refreshIndividuals();

			}
		});
		btnCalculate.setBounds(438, 192, 147, 21);
		panelStats.add(btnCalculate);

		spinnerRRMod = new JSpinner();
		spinnerRRMod.setBounds(540, 159, 49, 20);
		panelStats.add(spinnerRRMod);

		JLabel lblRrMod = new JLabel("RR Mod:");
		lblRrMod.setForeground(Color.WHITE);
		lblRrMod.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblRrMod.setBounds(485, 160, 54, 17);
		panelStats.add(lblRrMod);

		JLabel lblRecivingFirstAid = new JLabel("Reciving First Aid");
		lblRecivingFirstAid.setForeground(Color.WHITE);
		lblRecivingFirstAid.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblRecivingFirstAid.setBounds(449, 131, 90, 16);
		panelStats.add(lblRecivingFirstAid);

		chckbxFirstAid = new JCheckBox("");
		chckbxFirstAid.setBounds(557, 126, 32, 21);
		panelStats.add(chckbxFirstAid);

		JButton btnRecoveryRoll = new JButton("Recovery Roll");
		btnRecoveryRoll.setBounds(438, 226, 147, 21);
		panelStats.add(btnRecoveryRoll);

		JLabel lblKo = new JLabel("KO:");
		lblKo.setForeground(Color.WHITE);
		lblKo.setFont(new Font("Calibri", Font.PLAIN, 13));
		lblKo.setBounds(51, 413, 32, 22);
		panelStats.add(lblKo);

		spinnerKO = new JSpinner();
		spinnerKO.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinnerKO.setBounds(93, 415, 68, 20);
		panelStats.add(spinnerKO);
		panelIndividual.setLayout(gl_panelIndividual);

		JPanel panelIndividualEdit = new JPanel();
		panelIndividualEdit.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Full Details + Edit", null, panelIndividualEdit, null);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panelIndividualEdit = new GroupLayout(panelIndividualEdit);
		gl_panelIndividualEdit.setHorizontalGroup(gl_panelIndividualEdit.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 936, Short.MAX_VALUE));
		gl_panelIndividualEdit.setVerticalGroup(gl_panelIndividualEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndividualEdit.createSequentialGroup().addGap(12).addComponent(scrollPane,
						GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)));

		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(panel);

		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.setBounds(10, 11, 135, 21);
		btnSaveChanges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Trooper trooper = saveChanges();
				
				if (editUnit != null) {
					editUnit.setTrooper(trooper, index);
					editUnit.refreshIndividuals();
				} else {
					editCompany.setIndividual(trooper, index);
					editCompany.refreshIndividuals();
				}	
				
				f.dispose();
				
			}
		});

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 63, 185, 45);
		panel_1.setBackground(Color.DARK_GRAY);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(201, 63, 113, 45);
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
		panel_3.setBounds(201, 114, 113, 45);
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
		panel_4.setBounds(201, 169, 113, 45);
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
		panel_5.setBounds(201, 224, 113, 45);
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
		panel_6.setBounds(201, 279, 113, 45);
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
		panel_7.setBounds(201, 334, 113, 45);
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
		panel_8.setBounds(201, 389, 113, 45);
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
		panel_9.setBounds(10, 114, 185, 45);
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
		panel_10.setBounds(10, 169, 185, 45);
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
		panel_11.setBounds(10, 224, 185, 45);
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
		panel_12.setBounds(10, 279, 185, 45);
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
		panel_13.setBounds(10, 334, 185, 45);
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
		scrollPane_1.setBounds(324, 63, 160, 571);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(517, 63, 160, 571);

		JPanel panel_50 = new JPanel();
		panel_50.setBounds(10, 389, 185, 45);
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

		JButton btnDeleteIndividual = new JButton("Delete Individual");
		btnDeleteIndividual.setBounds(313, 11, 130, 21);
		btnDeleteIndividual.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (editUnit != null) {
					deleteIndividualUnit(f, index, editUnit);
				} else {
					deleteIndividualCompany(f, index, editCompany);
				}
			}
		});

		btnAddToUnit = new JButton("Add to Unit");
		btnAddToUnit.setBounds(453, 11, 120, 21);
		btnAddToUnit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (textFieldUnit.equals("Type Unit Callsign") || textFieldUnit.equals("Invalid Unit Callsign")) {
					return;
				}
				if (editUnit != null) {
					addUnit(f, editUnit, trooper, textFieldUnit.getText(), index);
				} else {
					addRoster(f, editCompany, trooper, textFieldUnit.getText(), index);
				}

			}
		});

		textFieldUnit = new JTextField();
		textFieldUnit.setBounds(583, 10, 178, 23);
		textFieldUnit.setText("Type unit callsign");
		textFieldUnit.setColumns(10);

		textPaneNotes = new JTextPane();
		textPaneNotes.setBounds(10, 502, 304, 131);
		textPaneNotes.setForeground(Color.WHITE);
		textPaneNotes.setFont(new Font("Calibri", Font.PLAIN, 13));
		textPaneNotes.setCaretColor(Color.BLACK);
		textPaneNotes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textPaneNotes.setBackground(Color.DARK_GRAY);

		JPanel panel_51 = new JPanel();
		panel_51.setBounds(10, 444, 185, 45);
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

		JButton btnAddToRoster = new JButton("Add to Roster");
		btnAddToRoster.setBounds(774, 11, 120, 21);
		btnAddToRoster.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (editUnit != null) {
					editUnit.window.addIndividual(trooper);
					editUnit.window.refreshIndividuals();
					editUnit.deleteIndividual(index);
					editUnit.refreshIndividuals();
					f.dispose();
				}

			}
		});

		JPanel panel_78 = new JPanel();
		panel_78.setBounds(201, 444, 113, 45);
		panel_78.setBackground(Color.DARK_GRAY);

		JLabel lblKills = new JLabel("Kills");
		lblKills.setForeground(Color.WHITE);
		lblKills.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerKills = new JSpinner();
		GroupLayout gl_panel_78 = new GroupLayout(panel_78);
		gl_panel_78.setHorizontalGroup(gl_panel_78.createParallelGroup(Alignment.LEADING)
				.addGap(0, 153, Short.MAX_VALUE).addGap(0, 153, Short.MAX_VALUE).addGap(0, 153, Short.MAX_VALUE)
				.addGroup(gl_panel_78.createSequentialGroup().addContainerGap().addComponent(lblKills)
						.addPreferredGap(ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
						.addComponent(spinnerKills, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_78.setVerticalGroup(gl_panel_78.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_78.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_78.createParallelGroup(Alignment.BASELINE).addComponent(lblKills)
								.addComponent(spinnerKills, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_78.setLayout(gl_panel_78);

		JPanel panel_85 = new JPanel();
		panel_85.setBounds(765, 820, 198, 45);
		panel_85.setBackground(Color.DARK_GRAY);

		JLabel lblCurrentShields = new JLabel("Current Shields");
		lblCurrentShields.setForeground(Color.WHITE);
		lblCurrentShields.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerCurrentShields = new JSpinner();
		GroupLayout gl_panel_85 = new GroupLayout(panel_85);
		gl_panel_85.setHorizontalGroup(gl_panel_85.createParallelGroup(Alignment.LEADING)
				.addGap(0, 198, Short.MAX_VALUE)
				.addGroup(gl_panel_85.createSequentialGroup().addContainerGap().addComponent(lblCurrentShields)
						.addPreferredGap(ComponentPlacement.UNRELATED, 72, Short.MAX_VALUE)
						.addComponent(spinnerCurrentShields, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_85.setVerticalGroup(gl_panel_85.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_85.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_85.createParallelGroup(Alignment.BASELINE).addComponent(lblCurrentShields)
								.addComponent(spinnerCurrentShields, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_85.setLayout(gl_panel_85);

		JPanel panel_86 = new JPanel();
		panel_86.setBounds(765, 871, 198, 45);
		panel_86.setBackground(Color.DARK_GRAY);

		JLabel lblShieldChance = new JLabel("Shield Chance");
		lblShieldChance.setForeground(Color.WHITE);
		lblShieldChance.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerShieldChance = new JSpinner();
		GroupLayout gl_panel_86 = new GroupLayout(panel_86);
		gl_panel_86.setHorizontalGroup(gl_panel_86.createParallelGroup(Alignment.LEADING)
				.addGap(0, 198, Short.MAX_VALUE)
				.addGroup(gl_panel_86.createSequentialGroup().addContainerGap().addComponent(lblShieldChance)
						.addPreferredGap(ComponentPlacement.UNRELATED, 72, Short.MAX_VALUE)
						.addComponent(spinnerShieldChance, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_86.setVerticalGroup(gl_panel_86.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_86.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_86.createParallelGroup(Alignment.BASELINE).addComponent(lblShieldChance)
								.addComponent(spinnerShieldChance, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(14, Short.MAX_VALUE)));
		panel_86.setLayout(gl_panel_86);

		JPanel panel_31 = new JPanel();
		panel_31.setBounds(681, 144, 138, 32);
		panel_31.setBackground(Color.DARK_GRAY);

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
		panel_32.setBounds(681, 182, 138, 32);
		panel_32.setBackground(Color.DARK_GRAY);

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

		JPanel panel_30 = new JPanel();
		panel_30.setBounds(681, 106, 138, 32);
		panel_30.setBackground(Color.DARK_GRAY);

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

		JPanel panel_29 = new JPanel();
		panel_29.setBounds(681, 63, 138, 32);
		panel_29.setBackground(Color.DARK_GRAY);

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

		spinnerSwim = new JSpinner();
		spinnerSwim.setBounds(774, 231, 45, 20);

		JLabel lblSwim = new JLabel("Swim");
		lblSwim.setBounds(697, 235, 49, 16);
		lblSwim.setForeground(Color.WHITE);
		lblSwim.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerThrow = new JSpinner();
		spinnerThrow.setBounds(774, 272, 45, 20);

		JLabel lblThrow = new JLabel("Throw");
		lblThrow.setBounds(696, 276, 65, 16);
		lblThrow.setForeground(Color.WHITE);
		lblThrow.setFont(new Font("Calibri", Font.PLAIN, 13));

		advancedMedicineSpinner = new JSpinner();
		advancedMedicineSpinner.setBounds(774, 302, 45, 20);

		JLabel lblAdvancedMed = new JLabel("Adv. Med.");
		lblAdvancedMed.setBounds(697, 306, 80, 16);
		lblAdvancedMed.setForeground(Color.WHITE);
		lblAdvancedMed.setFont(new Font("Calibri", Font.PLAIN, 13));

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
		panel_47.setBounds(10, 525, 138, 32);
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
		panel.add(panel_31);
		panel.add(panel_32);
		panel.add(panel_30);
		panel.add(panel_29);
		panel.add(spinnerSwim);
		panel.add(lblSwim);
		panel.add(spinnerThrow);
		panel.add(lblThrow);
		panel.add(advancedMedicineSpinner);
		panel.add(lblAdvancedMed);
		panel.add(btnSaveChanges);
		panel.add(btnDeleteIndividual);
		panel.add(btnAddToUnit);
		panel.add(textFieldUnit);
		panel.add(panel_1);
		panel.add(panel_9);
		panel.add(panel_10);
		panel.add(panel_11);
		panel.add(panel_12);
		panel.add(panel_13);
		panel.add(panel_50);
		panel.add(panel_3);
		panel.add(panel_2);
		panel.add(panel_4);
		panel.add(panel_5);
		panel.add(panel_6);
		panel.add(panel_7);
		panel.add(panel_8);
		panel.add(textPaneNotes);
		panel.add(panel_51);
		panel.add(panel_78);
		panel.add(scrollPane_1);
		panel.add(scrollPane_2);
		panel.add(panel_85);
		panel.add(panel_86);
		panel.add(btnAddToRoster);

		JButton btnCalcStats = new JButton("Roll");
		btnCalcStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				trooper.str = (int) spinnerStr.getValue();
				trooper.wit = (int) spinnerWit.getValue();
				trooper.soc = (int) spinnerSoc.getValue();
				trooper.wil = (int) spinnerWil.getValue();
				trooper.per = (int) spinnerPer.getValue();
				trooper.hlt = (int) spinnerHlt.getValue();
				trooper.agi = (int) spinnerAgi.getValue();

				TrooperUtility.recalcTrooper(trooper);
				setDetails(trooper);
				setEdit(trooper);
				setEditSkills(trooper);

			}
		});
		btnCalcStats.setBounds(155, 11, 71, 21);
		panel.add(btnCalcStats);

		JButton btnCalc = new JButton("Calc");
		btnCalc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TrooperUtility.setTrooperStatsSafe(trooper);
				setDetails(trooper);
				setEdit(trooper);
				setEditSkills(trooper);

			}
		});
		btnCalc.setBounds(236, 11, 67, 21);
		panel.add(btnCalc);
		panelIndividualEdit.setLayout(gl_panelIndividualEdit);

		JPanel panel_61 = new JPanel();
		panel_61.setLayout(null);
		panel_61.setBackground(Color.DARK_GRAY);
		tabbedPane.addTab("Additional Skills", null, panel_61, null);

		JPanel panel_62 = new JPanel();
		panel_62.setLayout(null);
		panel_62.setBackground(Color.DARK_GRAY);
		panel_62.setBounds(34, 11, 137, 838);
		panel_61.add(panel_62);

		JPanel panel_63 = new JPanel();
		panel_63.setBackground(Color.DARK_GRAY);
		panel_63.setBounds(0, 305, 138, 32);
		panel_62.add(panel_63);

		JLabel label_5 = new JLabel("Silent Op.");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("Calibri", Font.PLAIN, 13));

		silentOperationsSpinner = new JSpinner();
		GroupLayout gl_panel_63 = new GroupLayout(panel_63);
		gl_panel_63.setHorizontalGroup(gl_panel_63.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_63.createSequentialGroup().addContainerGap().addComponent(label_5)
						.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE).addComponent(
								silentOperationsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_63.setVerticalGroup(gl_panel_63.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_63.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_63.createParallelGroup(Alignment.BASELINE).addComponent(label_5)
								.addComponent(silentOperationsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_63.setLayout(gl_panel_63);

		JPanel panel_64 = new JPanel();
		panel_64.setBackground(Color.DARK_GRAY);
		panel_64.setBounds(0, 262, 138, 32);
		panel_62.add(panel_64);

		JLabel label_6 = new JLabel("Reload Drills");
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("Calibri", Font.PLAIN, 13));

		reloadDrillsSpinner = new JSpinner();
		GroupLayout gl_panel_64 = new GroupLayout(panel_64);
		gl_panel_64.setHorizontalGroup(gl_panel_64.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_64.createSequentialGroup().addContainerGap().addComponent(label_6)
						.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE).addComponent(
								reloadDrillsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_64.setVerticalGroup(gl_panel_64.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_64.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_64.createParallelGroup(Alignment.BASELINE).addComponent(label_6)
								.addComponent(reloadDrillsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_64.setLayout(gl_panel_64);

		JPanel panel_65 = new JPanel();
		panel_65.setBackground(Color.DARK_GRAY);
		panel_65.setBounds(0, 224, 138, 32);
		panel_62.add(panel_65);

		JLabel label_7 = new JLabel("Trigger Disc.");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("Calibri", Font.PLAIN, 13));

		triggerDisciplineSpinner = new JSpinner();
		GroupLayout gl_panel_65 = new GroupLayout(panel_65);
		gl_panel_65.setHorizontalGroup(gl_panel_65.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_65.createSequentialGroup().addContainerGap().addComponent(label_7)
						.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE).addComponent(
								triggerDisciplineSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_65.setVerticalGroup(gl_panel_65.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_65.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_65.createParallelGroup(Alignment.BASELINE).addComponent(label_7)
								.addComponent(triggerDisciplineSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_65.setLayout(gl_panel_65);

		JPanel panel_66 = new JPanel();
		panel_66.setBackground(Color.DARK_GRAY);
		panel_66.setBounds(0, 181, 138, 32);
		panel_62.add(panel_66);

		JLabel label_8 = new JLabel("Recoil Cont.");
		label_8.setForeground(Color.WHITE);
		label_8.setFont(new Font("Calibri", Font.PLAIN, 13));

		recoilControlSpinner = new JSpinner();
		GroupLayout gl_panel_66 = new GroupLayout(panel_66);
		gl_panel_66.setHorizontalGroup(gl_panel_66.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_66.createSequentialGroup().addContainerGap().addComponent(label_8)
						.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE).addComponent(
								recoilControlSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_66.setVerticalGroup(gl_panel_66.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_66.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_66.createParallelGroup(Alignment.BASELINE).addComponent(label_8)
								.addComponent(recoilControlSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_66.setLayout(gl_panel_66);

		JPanel panel_67 = new JPanel();
		panel_67.setBackground(Color.DARK_GRAY);
		panel_67.setBounds(0, 92, 138, 32);
		panel_62.add(panel_67);

		JLabel label_9 = new JLabel("Fighter");
		label_9.setForeground(Color.WHITE);
		label_9.setFont(new Font("Calibri", Font.PLAIN, 13));

		fighterSpinner = new JSpinner();
		GroupLayout gl_panel_67 = new GroupLayout(panel_67);
		gl_panel_67.setHorizontalGroup(gl_panel_67.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_67.createSequentialGroup().addContainerGap().addComponent(label_9)
						.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
						.addComponent(fighterSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_67.setVerticalGroup(gl_panel_67.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_67.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_67.createParallelGroup(Alignment.BASELINE).addComponent(label_9)
								.addComponent(fighterSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_67.setLayout(gl_panel_67);

		JPanel panel_68 = new JPanel();
		panel_68.setBackground(Color.DARK_GRAY);
		panel_68.setBounds(0, 49, 138, 32);
		panel_62.add(panel_68);

		JLabel label_10 = new JLabel("Covert Mov.");
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("Calibri", Font.PLAIN, 13));

		covertMovementSpinner = new JSpinner();
		GroupLayout gl_panel_68 = new GroupLayout(panel_68);
		gl_panel_68.setHorizontalGroup(gl_panel_68.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_68.createSequentialGroup().addContainerGap().addComponent(label_10)
						.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE).addComponent(
								covertMovementSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_68.setVerticalGroup(gl_panel_68.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_68.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_68.createParallelGroup(Alignment.BASELINE).addComponent(label_10)
								.addComponent(covertMovementSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_68.setLayout(gl_panel_68);

		JPanel panel_69 = new JPanel();
		panel_69.setBackground(Color.DARK_GRAY);
		panel_69.setBounds(0, 11, 138, 32);
		panel_62.add(panel_69);

		JLabel label_11 = new JLabel("Clean Op.");
		label_11.setForeground(Color.WHITE);
		label_11.setFont(new Font("Calibri", Font.PLAIN, 13));

		cleanOperationsSpinner = new JSpinner();
		GroupLayout gl_panel_69 = new GroupLayout(panel_69);
		gl_panel_69.setHorizontalGroup(gl_panel_69.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_69.createSequentialGroup().addContainerGap().addComponent(label_11)
						.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE).addComponent(
								cleanOperationsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_69.setVerticalGroup(gl_panel_69.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_69.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_69.createParallelGroup(Alignment.BASELINE).addComponent(label_11)
								.addComponent(cleanOperationsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_69.setLayout(gl_panel_69);

		JPanel panel_70 = new JPanel();
		panel_70.setBackground(Color.DARK_GRAY);
		panel_70.setBounds(0, 348, 138, 32);
		panel_62.add(panel_70);

		JLabel label_12 = new JLabel("AK Systems");
		label_12.setForeground(Color.WHITE);
		label_12.setFont(new Font("Calibri", Font.PLAIN, 13));

		akSystemsSpinner = new JSpinner();
		GroupLayout gl_panel_70 = new GroupLayout(panel_70);
		gl_panel_70.setHorizontalGroup(gl_panel_70.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_70.createSequentialGroup().addContainerGap().addComponent(label_12)
						.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
						.addComponent(akSystemsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_70.setVerticalGroup(gl_panel_70.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_70.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_70.createParallelGroup(Alignment.BASELINE).addComponent(label_12)
								.addComponent(akSystemsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_70.setLayout(gl_panel_70);

		JPanel panel_71 = new JPanel();
		panel_71.setBackground(Color.DARK_GRAY);
		panel_71.setBounds(0, 391, 138, 32);
		panel_62.add(panel_71);

		JLabel label_13 = new JLabel("Assualt Ops");
		label_13.setForeground(Color.WHITE);
		label_13.setFont(new Font("Calibri", Font.PLAIN, 13));

		assualtOperationsSpinner = new JSpinner();
		GroupLayout gl_panel_71 = new GroupLayout(panel_71);
		gl_panel_71.setHorizontalGroup(gl_panel_71.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_71.createSequentialGroup().addContainerGap().addComponent(label_13)
						.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE).addComponent(
								assualtOperationsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_71.setVerticalGroup(gl_panel_71.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_71.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_71.createParallelGroup(Alignment.BASELINE).addComponent(label_13)
								.addComponent(assualtOperationsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_71.setLayout(gl_panel_71);

		JPanel panel_72 = new JPanel();
		panel_72.setBackground(Color.DARK_GRAY);
		panel_72.setBounds(0, 434, 138, 32);
		panel_62.add(panel_72);

		JLabel label_14 = new JLabel("Authority");
		label_14.setForeground(Color.WHITE);
		label_14.setFont(new Font("Calibri", Font.PLAIN, 13));

		authoritySpinner = new JSpinner();
		GroupLayout gl_panel_72 = new GroupLayout(panel_72);
		gl_panel_72.setHorizontalGroup(gl_panel_72.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_72.createSequentialGroup().addContainerGap().addComponent(label_14)
						.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
						.addComponent(authoritySpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_72.setVerticalGroup(gl_panel_72.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_72.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_72.createParallelGroup(Alignment.BASELINE).addComponent(label_14)
								.addComponent(authoritySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_72.setLayout(gl_panel_72);

		JPanel panel_73 = new JPanel();
		panel_73.setBackground(Color.DARK_GRAY);
		panel_73.setBounds(0, 477, 138, 32);
		panel_62.add(panel_73);

		JLabel label_15 = new JLabel("Raw Power");
		label_15.setForeground(Color.WHITE);
		label_15.setFont(new Font("Calibri", Font.PLAIN, 13));

		rawPowerSpinner = new JSpinner();
		GroupLayout gl_panel_73 = new GroupLayout(panel_73);
		gl_panel_73.setHorizontalGroup(gl_panel_73.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_73.createSequentialGroup().addContainerGap().addComponent(label_15)
						.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
						.addComponent(rawPowerSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_73.setVerticalGroup(gl_panel_73.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_73.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_73.createParallelGroup(Alignment.BASELINE).addComponent(label_15)
								.addComponent(rawPowerSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_73.setLayout(gl_panel_73);

		JPanel panel_74 = new JPanel();
		panel_74.setBackground(Color.DARK_GRAY);
		panel_74.setBounds(0, 520, 138, 32);
		panel_62.add(panel_74);

		JLabel label_16 = new JLabel("AR Systems");
		label_16.setForeground(Color.WHITE);
		label_16.setFont(new Font("Calibri", Font.PLAIN, 13));

		arSystemsSpinner = new JSpinner();
		GroupLayout gl_panel_74 = new GroupLayout(panel_74);
		gl_panel_74.setHorizontalGroup(gl_panel_74.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_74.createSequentialGroup().addContainerGap().addComponent(label_16)
						.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
						.addComponent(arSystemsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_74.setVerticalGroup(gl_panel_74.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_74.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_74.createParallelGroup(Alignment.BASELINE).addComponent(label_16)
								.addComponent(arSystemsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_74.setLayout(gl_panel_74);

		JPanel panel_75 = new JPanel();
		panel_75.setBackground(Color.DARK_GRAY);
		panel_75.setBounds(0, 563, 138, 32);
		panel_62.add(panel_75);

		JLabel label_17 = new JLabel("Long Range");
		label_17.setForeground(Color.WHITE);
		label_17.setFont(new Font("Calibri", Font.PLAIN, 13));

		longRangeOpticsSpinner = new JSpinner();
		GroupLayout gl_panel_75 = new GroupLayout(panel_75);
		gl_panel_75.setHorizontalGroup(gl_panel_75.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_75.createSequentialGroup().addContainerGap().addComponent(label_17)
						.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE).addComponent(
								longRangeOpticsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_75.setVerticalGroup(gl_panel_75.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_75.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_75.createParallelGroup(Alignment.BASELINE).addComponent(label_17)
								.addComponent(longRangeOpticsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_75.setLayout(gl_panel_75);

		JPanel panel_76 = new JPanel();
		panel_76.setBackground(Color.DARK_GRAY);
		panel_76.setBounds(0, 606, 138, 32);
		panel_62.add(panel_76);

		JLabel label_18 = new JLabel("Negotiations");
		label_18.setForeground(Color.WHITE);
		label_18.setFont(new Font("Calibri", Font.PLAIN, 13));

		negotiationsSpinner = new JSpinner();
		GroupLayout gl_panel_76 = new GroupLayout(panel_76);
		gl_panel_76.setHorizontalGroup(gl_panel_76.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_76.createSequentialGroup().addContainerGap().addComponent(label_18)
						.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE).addComponent(
								negotiationsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_76.setVerticalGroup(gl_panel_76.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_76.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_76.createParallelGroup(Alignment.BASELINE).addComponent(label_18)
								.addComponent(negotiationsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_76.setLayout(gl_panel_76);

		JPanel panel_77 = new JPanel();
		panel_77.setBackground(Color.DARK_GRAY);
		panel_77.setBounds(0, 649, 138, 32);
		panel_62.add(panel_77);

		JLabel label_19 = new JLabel("SU Tactics");
		label_19.setForeground(Color.WHITE);
		label_19.setFont(new Font("Calibri", Font.PLAIN, 13));

		smallUnitTacticsSpinner = new JSpinner();
		GroupLayout gl_panel_77 = new GroupLayout(panel_77);
		gl_panel_77.setHorizontalGroup(gl_panel_77.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_77.createSequentialGroup().addContainerGap().addComponent(label_19)
						.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE).addComponent(
								smallUnitTacticsSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_77.setVerticalGroup(gl_panel_77.createParallelGroup(Alignment.LEADING).addGap(0, 32, Short.MAX_VALUE)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_77.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_77.createParallelGroup(Alignment.BASELINE).addComponent(label_19)
								.addComponent(smallUnitTacticsSpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_77.setLayout(gl_panel_77);

		JPanel panel_66_1 = new JPanel();
		panel_66_1.setBackground(Color.DARK_GRAY);
		panel_66_1.setBounds(0, 134, 138, 32);
		panel_62.add(panel_66_1);

		JLabel label_8_1 = new JLabel("Fighter Ranks");
		label_8_1.setForeground(Color.WHITE);
		label_8_1.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerFighterRanks = new JSpinner();
		GroupLayout gl_panel_66_1 = new GroupLayout(panel_66_1);
		gl_panel_66_1.setHorizontalGroup(gl_panel_66_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 138, Short.MAX_VALUE).addGap(0, 138, Short.MAX_VALUE)
				.addGroup(gl_panel_66_1.createSequentialGroup().addContainerGap().addComponent(label_8_1)
						.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addComponent(
								spinnerFighterRanks, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)));
		gl_panel_66_1.setVerticalGroup(gl_panel_66_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 32, Short.MAX_VALUE).addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_panel_66_1.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_66_1.createParallelGroup(Alignment.BASELINE).addComponent(label_8_1)
								.addComponent(spinnerFighterRanks, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_66_1.setLayout(gl_panel_66_1);

		JPanel panel_84 = new JPanel();
		panel_84.setBackground(Color.DARK_GRAY);
		panel_84.setBounds(391, 62, 198, 45);
		panel_61.add(panel_84);

		JLabel lblMaxShields = new JLabel("Max Shields");
		lblMaxShields.setForeground(Color.WHITE);
		lblMaxShields.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerMaxShields = new JSpinner();
		GroupLayout gl_panel_84 = new GroupLayout(panel_84);
		gl_panel_84.setHorizontalGroup(gl_panel_84.createParallelGroup(Alignment.LEADING)
				.addGap(0, 198, Short.MAX_VALUE)
				.addGroup(gl_panel_84.createSequentialGroup().addContainerGap().addComponent(lblMaxShields)
						.addPreferredGap(ComponentPlacement.UNRELATED, 71, Short.MAX_VALUE)
						.addComponent(spinnerMaxShields, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_84.setVerticalGroup(gl_panel_84.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_84.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_84.createParallelGroup(Alignment.BASELINE).addComponent(lblMaxShields)
								.addComponent(spinnerMaxShields, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_84.setLayout(gl_panel_84);

		JPanel panel_59 = new JPanel();
		panel_59.setBackground(Color.DARK_GRAY);
		panel_59.setBounds(391, 11, 200, 45);
		panel_61.add(panel_59);

		JLabel label_4 = new JLabel("Disabled Legs");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerDisabledLegs = new JSpinner();
		GroupLayout gl_panel_59 = new GroupLayout(panel_59);
		gl_panel_59.setHorizontalGroup(gl_panel_59.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_59.createSequentialGroup().addContainerGap().addComponent(label_4)
						.addPreferredGap(ComponentPlacement.UNRELATED, 62, Short.MAX_VALUE)
						.addComponent(spinnerDisabledLegs, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_59.setVerticalGroup(gl_panel_59.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_59.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_59.createParallelGroup(Alignment.BASELINE).addComponent(label_4)
								.addComponent(spinnerDisabledLegs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_59.setLayout(gl_panel_59);

		JPanel panel_58 = new JPanel();
		panel_58.setBackground(Color.DARK_GRAY);
		panel_58.setBounds(181, 602, 200, 45);
		panel_61.add(panel_58);

		JLabel label_3 = new JLabel("Disabled Arms");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerDisabledArms = new JSpinner();
		GroupLayout gl_panel_58 = new GroupLayout(panel_58);
		gl_panel_58.setHorizontalGroup(gl_panel_58.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_58.createSequentialGroup().addContainerGap().addComponent(label_3)
						.addPreferredGap(ComponentPlacement.UNRELATED, 60, Short.MAX_VALUE)
						.addComponent(spinnerDisabledArms, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_58.setVerticalGroup(gl_panel_58.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_58.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_58.createParallelGroup(Alignment.BASELINE).addComponent(label_3)
								.addComponent(spinnerDisabledArms, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_58.setLayout(gl_panel_58);

		JPanel panel_57 = new JPanel();
		panel_57.setBackground(Color.DARK_GRAY);
		panel_57.setBounds(181, 546, 200, 45);
		panel_61.add(panel_57);

		JLabel label_2 = new JLabel("Legs");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerLegs = new JSpinner();
		GroupLayout gl_panel_57 = new GroupLayout(panel_57);
		gl_panel_57.setHorizontalGroup(gl_panel_57.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_57.createSequentialGroup().addContainerGap().addComponent(label_2)
						.addPreferredGap(ComponentPlacement.UNRELATED, 111, Short.MAX_VALUE)
						.addComponent(spinnerLegs, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_57.setVerticalGroup(gl_panel_57.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_57.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_57.createParallelGroup(Alignment.BASELINE).addComponent(label_2)
								.addComponent(spinnerLegs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_57.setLayout(gl_panel_57);

		JPanel panel_56 = new JPanel();
		panel_56.setBackground(Color.DARK_GRAY);
		panel_56.setBounds(181, 491, 200, 45);
		panel_61.add(panel_56);

		JLabel label_1 = new JLabel("Arms");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerArms = new JSpinner();
		GroupLayout gl_panel_56 = new GroupLayout(panel_56);
		gl_panel_56.setHorizontalGroup(gl_panel_56.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_56.createSequentialGroup().addContainerGap().addComponent(label_1)
						.addPreferredGap(ComponentPlacement.UNRELATED, 109, Short.MAX_VALUE)
						.addComponent(spinnerArms, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_56.setVerticalGroup(gl_panel_56.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_56.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_56.createParallelGroup(Alignment.BASELINE).addComponent(label_1)
								.addComponent(spinnerArms, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_56.setLayout(gl_panel_56);

		JPanel panel_83 = new JPanel();
		panel_83.setBackground(Color.DARK_GRAY);
		panel_83.setBounds(181, 436, 200, 45);
		panel_61.add(panel_83);

		JLabel lblPhysiciansSkill = new JLabel("Physicians Skill");
		lblPhysiciansSkill.setForeground(Color.WHITE);
		lblPhysiciansSkill.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerPhysiciansSkill = new JSpinner();
		GroupLayout gl_panel_83 = new GroupLayout(panel_83);
		gl_panel_83.setHorizontalGroup(gl_panel_83.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_83.createSequentialGroup().addContainerGap().addComponent(lblPhysiciansSkill)
						.addPreferredGap(ComponentPlacement.UNRELATED, 57, Short.MAX_VALUE)
						.addComponent(spinnerPhysiciansSkill, GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_83.setVerticalGroup(gl_panel_83.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_83.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_83.createParallelGroup(Alignment.BASELINE).addComponent(lblPhysiciansSkill)
								.addComponent(spinnerPhysiciansSkill, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_83.setLayout(gl_panel_83);

		JPanel panel_82 = new JPanel();
		panel_82.setBackground(Color.DARK_GRAY);
		panel_82.setBounds(181, 385, 200, 45);
		panel_61.add(panel_82);

		JLabel lblTimeMortallyWnd = new JLabel("Time Mortally Wnd.");
		lblTimeMortallyWnd.setForeground(Color.WHITE);
		lblTimeMortallyWnd.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerTimeMortallyWounded = new JSpinner();
		GroupLayout gl_panel_82 = new GroupLayout(panel_82);
		gl_panel_82.setHorizontalGroup(gl_panel_82.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_82.createSequentialGroup().addContainerGap().addComponent(lblTimeMortallyWnd)
						.addPreferredGap(ComponentPlacement.UNRELATED, 33, Short.MAX_VALUE)
						.addComponent(spinnerTimeMortallyWounded, GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_82.setVerticalGroup(gl_panel_82.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_82.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_82.createParallelGroup(Alignment.BASELINE).addComponent(lblTimeMortallyWnd)
								.addComponent(spinnerTimeMortallyWounded, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_82.setLayout(gl_panel_82);

		JPanel panel_81 = new JPanel();
		panel_81.setBackground(Color.DARK_GRAY);
		panel_81.setBounds(181, 330, 200, 45);
		panel_61.add(panel_81);

		JLabel lblTimeUnconscious = new JLabel("Time Unconscious");
		lblTimeUnconscious.setForeground(Color.WHITE);
		lblTimeUnconscious.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerTimeUnconscious = new JSpinner();
		GroupLayout gl_panel_81 = new GroupLayout(panel_81);
		gl_panel_81.setHorizontalGroup(gl_panel_81.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE).addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_81.createSequentialGroup().addContainerGap().addComponent(lblTimeUnconscious)
						.addPreferredGap(ComponentPlacement.UNRELATED, 41, Short.MAX_VALUE)
						.addComponent(spinnerTimeUnconscious, GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_81.setVerticalGroup(gl_panel_81.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_81.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_81.createParallelGroup(Alignment.BASELINE).addComponent(lblTimeUnconscious)
								.addComponent(spinnerTimeUnconscious, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_81.setLayout(gl_panel_81);

		JPanel panel_80 = new JPanel();
		panel_80.setBackground(Color.DARK_GRAY);
		panel_80.setBounds(181, 274, 200, 45);
		panel_61.add(panel_80);

		JLabel lblStabalized = new JLabel("Stabalized");
		lblStabalized.setForeground(Color.WHITE);
		lblStabalized.setFont(new Font("Calibri", Font.PLAIN, 13));

		checkBoxStabalized = new JCheckBox("");
		GroupLayout gl_panel_80 = new GroupLayout(panel_80);
		gl_panel_80.setHorizontalGroup(gl_panel_80.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_80.createSequentialGroup().addContainerGap().addComponent(lblStabalized)
						.addPreferredGap(ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
						.addComponent(checkBoxStabalized, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_80.setVerticalGroup(gl_panel_80.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_80
						.createSequentialGroup().addGap(11).addGroup(gl_panel_80.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblStabalized).addComponent(checkBoxStabalized))
						.addContainerGap(13, Short.MAX_VALUE)));
		panel_80.setLayout(gl_panel_80);

		JPanel panel_79 = new JPanel();
		panel_79.setBackground(Color.DARK_GRAY);
		panel_79.setBounds(181, 219, 200, 45);
		panel_61.add(panel_79);

		JLabel lblMortallyWounded = new JLabel("Mortal Wound");
		lblMortallyWounded.setForeground(Color.WHITE);
		lblMortallyWounded.setFont(new Font("Calibri", Font.PLAIN, 13));

		checkBoxMortalWound = new JCheckBox("");
		GroupLayout gl_panel_79 = new GroupLayout(panel_79);
		gl_panel_79.setHorizontalGroup(gl_panel_79.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_79.createSequentialGroup().addContainerGap().addComponent(lblMortallyWounded)
						.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
						.addComponent(checkBoxMortalWound, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_79.setVerticalGroup(gl_panel_79.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_79
						.createSequentialGroup().addGap(11).addGroup(gl_panel_79.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblMortallyWounded).addComponent(checkBoxMortalWound))
						.addContainerGap(13, Short.MAX_VALUE)));
		panel_79.setLayout(gl_panel_79);

		JPanel panel_55 = new JPanel();
		panel_55.setBackground(Color.DARK_GRAY);
		panel_55.setBounds(181, 164, 200, 45);
		panel_61.add(panel_55);

		JLabel lblAwake = new JLabel("Awake");
		lblAwake.setForeground(Color.WHITE);
		lblAwake.setFont(new Font("Calibri", Font.PLAIN, 13));

		checkBoxAwake = new JCheckBox("");
		GroupLayout gl_panel_55 = new GroupLayout(panel_55);
		gl_panel_55
				.setHorizontalGroup(gl_panel_55.createParallelGroup(Alignment.TRAILING).addGap(0, 200, Short.MAX_VALUE)
						.addGroup(gl_panel_55.createSequentialGroup().addContainerGap().addComponent(lblAwake)
								.addPreferredGap(ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
								.addComponent(checkBoxAwake, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_55.setVerticalGroup(gl_panel_55.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_55
						.createSequentialGroup().addGap(11).addGroup(gl_panel_55.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAwake).addComponent(checkBoxAwake))
						.addContainerGap(13, Short.MAX_VALUE)));
		panel_55.setLayout(gl_panel_55);

		JPanel panel_54 = new JPanel();
		panel_54.setBackground(Color.DARK_GRAY);
		panel_54.setBounds(181, 113, 200, 45);
		panel_61.add(panel_54);

		JLabel lblAlive_2 = new JLabel("Alive");
		lblAlive_2.setForeground(Color.WHITE);
		lblAlive_2.setFont(new Font("Calibri", Font.PLAIN, 13));

		checkBoxAlive = new JCheckBox("");
		GroupLayout gl_panel_54 = new GroupLayout(panel_54);
		gl_panel_54
				.setHorizontalGroup(gl_panel_54.createParallelGroup(Alignment.TRAILING).addGap(0, 200, Short.MAX_VALUE)
						.addGroup(gl_panel_54.createSequentialGroup().addContainerGap().addComponent(lblAlive_2)
								.addPreferredGap(ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
								.addComponent(checkBoxAlive, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_54.setVerticalGroup(gl_panel_54.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_54
						.createSequentialGroup().addGap(11).addGroup(gl_panel_54.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblAlive_2).addComponent(checkBoxAlive))
						.addContainerGap(13, Short.MAX_VALUE)));
		panel_54.setLayout(gl_panel_54);

		JPanel panel_53 = new JPanel();
		panel_53.setBackground(Color.DARK_GRAY);
		panel_53.setBounds(181, 62, 200, 45);
		panel_61.add(panel_53);

		JLabel lblCurrenthp = new JLabel("CurrentHP");
		lblCurrenthp.setForeground(Color.WHITE);
		lblCurrenthp.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerCurrentHp = new JSpinner();
		GroupLayout gl_panel_53 = new GroupLayout(panel_53);
		gl_panel_53.setHorizontalGroup(gl_panel_53.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE)
				.addGroup(gl_panel_53.createSequentialGroup().addContainerGap().addComponent(lblCurrenthp)
						.addPreferredGap(ComponentPlacement.UNRELATED, 82, Short.MAX_VALUE)
						.addComponent(spinnerCurrentHp, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel_53.setVerticalGroup(gl_panel_53.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_53.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_53.createParallelGroup(Alignment.BASELINE).addComponent(lblCurrenthp)
								.addComponent(spinnerCurrentHp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_53.setLayout(gl_panel_53);

		JPanel panel_52 = new JPanel();
		panel_52.setBackground(Color.DARK_GRAY);
		panel_52.setBounds(181, 11, 200, 45);
		panel_61.add(panel_52);

		JLabel lblMaxhp = new JLabel("MaxHP");
		lblMaxhp.setForeground(Color.WHITE);
		lblMaxhp.setFont(new Font("Calibri", Font.PLAIN, 13));

		spinnerMaxHP = new JSpinner();
		GroupLayout gl_panel_52 = new GroupLayout(panel_52);
		gl_panel_52
				.setHorizontalGroup(gl_panel_52.createParallelGroup(Alignment.LEADING).addGap(0, 200, Short.MAX_VALUE)
						.addGroup(gl_panel_52.createSequentialGroup().addContainerGap().addComponent(lblMaxhp)
								.addPreferredGap(ComponentPlacement.UNRELATED, 98, Short.MAX_VALUE)
								.addComponent(spinnerMaxHP, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panel_52.setVerticalGroup(gl_panel_52.createParallelGroup(Alignment.LEADING).addGap(0, 45, Short.MAX_VALUE)
				.addGroup(gl_panel_52.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_52.createParallelGroup(Alignment.BASELINE).addComponent(lblMaxhp)
								.addComponent(spinnerMaxHP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(15, Short.MAX_VALUE)));
		panel_52.setLayout(gl_panel_52);
		f.getContentPane().setLayout(groupLayout);
		f.setVisible(true);

		// Calls methods
		// Sets all fields on both pages
		setDetails(trooper);
		setEdit(trooper);
		setEditSkills(trooper);
		refreshInjuries(trooper);
	}

	public void loadTrooper() throws IOException {
		this.trooper = JsonSaveRunner.loadTrooper(JsonSaveRunner.loadFile());
		// Calls methods
		// Sets all fields on both pages
		setDetails(trooper);
		setEdit(trooper);
		setEditSkills(trooper);
		refreshInjuries(trooper);
	}

	// Sets all of the fields and lists on the first details page
	public void setDetails(Trooper individual) {
		lblName.setText("Name: " + individual.name);
		labelRank.setText("Rank: " + individual.rank);
		lblDesignation.setText("Role: " + individual.designation);
		lblVet.setText("Vet: " + individual.vet);
		lblPD.setText("PD: " + individual.physicalDamage);
		lblP1.setText("P1: " + individual.spentPhase1 + "/" + individual.P1);
		lblP2.setText("P2: " + individual.spentPhase2 + "/" + individual.P2);
		lblWep.setText("Weapon: " + individual.wep);
		lblAmmo.setText("Ammo: " + individual.ammo);
		lblMaxHp.setText("Max HP: " + individual.hp);
		lblCurrentHp.setText("Current HP: " + individual.currentHP);
		lblAlive_1.setText("Alive: " + individual.alive);
		lblConscious.setText("Conscious: " + individual.conscious);
		lblMaxShields_1.setText("Max Shields: " + individual.shields);
		lblCurrentShields_1.setText("Current Shields: " + individual.currentShields);

		int cffwSkills[] = new int[10];
		cffwSkills[0] = individual.getSkill("Rifle");
		cffwSkills[1] = individual.getSkill("Pistol");
		cffwSkills[2] = individual.getSkill("Subgun");
		cffwSkills[3] = individual.getSkill("Heavy");
		cffwSkills[4] = individual.getSkill("Launcher");
		cffwSkills[5] = individual.getSkill("Spot/Listen");
		cffwSkills[6] = individual.getSkill("Camouflage");
		cffwSkills[7] = individual.getSkill("Stealth");
		cffwSkills[8] = individual.getSkill("Command");
		cffwSkills[9] = individual.getSkill("Tactics");

		DefaultListModel cffwSkillsList = new DefaultListModel();

		String[] skills = new String[listCFFWSkills.getModel().getSize()];
		for (int i = 0; i < listCFFWSkills.getModel().getSize(); i++) {
			skills[i] = listCFFWSkills.getModel().getElementAt(i).toString() + " " + cffwSkills[i];

		}

		for (int i = 0; i < skills.length; i++) {
			cffwSkillsList.addElement(skills[i]);
		}

		listCFFWSkills.setModel(cffwSkillsList);
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
		spinnerCurrentShields.setValue(individual.currentShields);
		spinnerShieldChance.setValue(individual.shieldChance);

		spinnerKills.setValue(individual.kills);
		spinnerKO.setValue(individual.KO);
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

	}

	public void deleteIndividualUnit(JFrame f, int index, EditUnit window) {
		window.deleteIndividual(index);
		window.refreshIndividuals();
		f.dispose();
	}

	public void deleteIndividualCompany(JFrame f, int index, EditCompany window) {
		window.deleteIndividual(index);
		window.refreshIndividuals();
		f.dispose();
	}

	public Trooper saveChanges() {

		Trooper individual = this.trooper;
		
		// Sets basic fields
		individual.name = textFieldName.getText();
		individual.rank = textFieldRank.getText();
		individual.designation = textFieldRole.getText();
		individual.vet = textFieldVet.getText();
		individual.wep = textFieldWeapon.getText();
		individual.ammo = (int) spinnerAmmo.getValue();

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
		 * //individual.longRangeOptics = (int) longRangeOpticsSpinner.getValue();
		 * individual.negotiations = (int) negotiationsSpinner.getValue();
		 * individual.smallUnitTactics = (int) smallUnitTacticsSpinner.getValue();
		 */

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

		individual.shields = (int) spinnerMaxShields.getValue();
		individual.currentShields = (int) spinnerCurrentShields.getValue();
		individual.shieldChance = (int) spinnerShieldChance.getValue();

		individual.legs = (int) spinnerLegs.getValue();
		individual.arms = (int) spinnerArms.getValue();
		individual.disabledLegs = (int) spinnerDisabledLegs.getValue();
		individual.disabledArms = (int) spinnerDisabledArms.getValue();
		individual.kills = (int) spinnerKills.getValue();

		individual.timeUnconscious = (int) spinnerTimeUnconscious.getValue();
		individual.timeMortallyWounded = (int) spinnerTimeMortallyWounded.getValue();
		individual.physicianSkill = (int) spinnerPhysiciansSkill.getValue();

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

		// Sets notes and equipment
		individual.notes = textPaneNotes.getText();
		individual.eqiupment = textPaneEquipment.getText();

		// Creates PC stats
		individual.setPCStats();

		// Create and set individual stats
		IndividualStats individualStats = new IndividualStats(individual.combatActions, individual.sal,
				individual.skills.getSkill("Pistol").value, individual.skills.getSkill("Rifle").value,
				individual.skills.getSkill("Launcher").value, individual.skills.getSkill("Heavy").value,
				individual.skills.getSkill("Subgun").value, true);
		individual.P1 = individualStats.P1;
		individual.P2 = individualStats.P2;

		individual.notes = textPaneNotes.getText();

		individual.KO = (int) spinnerKO.getValue();

		return individual;

	}

	// Sets troopers stats to equal the changed values
	// Recalculates skills based off of attribute changes
	// Sets skills equal to their skill bonus
	public void saveChangesUnit(JFrame f, Trooper individual, int index, EditUnit window) {

		// Sets basic fields
		individual.name = textFieldName.getText();
		individual.rank = textFieldRank.getText();
		individual.designation = textFieldRole.getText();
		individual.vet = textFieldVet.getText();
		individual.wep = textFieldWeapon.getText();
		individual.ammo = (int) spinnerAmmo.getValue();

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
		 * //individual.longRangeOptics = (int) longRangeOpticsSpinner.getValue();
		 * individual.negotiations = (int) negotiationsSpinner.getValue();
		 * individual.smallUnitTactics = (int) smallUnitTacticsSpinner.getValue();
		 */

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

		individual.shields = (int) spinnerMaxShields.getValue();
		individual.currentShields = (int) spinnerCurrentShields.getValue();
		individual.shieldChance = (int) spinnerShieldChance.getValue();

		individual.legs = (int) spinnerLegs.getValue();
		individual.arms = (int) spinnerArms.getValue();
		individual.disabledLegs = (int) spinnerDisabledLegs.getValue();
		individual.disabledArms = (int) spinnerDisabledArms.getValue();
		individual.kills = (int) spinnerKills.getValue();

		individual.timeUnconscious = (int) spinnerTimeUnconscious.getValue();
		individual.timeMortallyWounded = (int) spinnerTimeMortallyWounded.getValue();
		individual.physicianSkill = (int) spinnerPhysiciansSkill.getValue();

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

		// Sets notes and equipment
		individual.notes = textPaneNotes.getText();
		individual.eqiupment = textPaneEquipment.getText();

		// Creates PC stats
		individual.setPCStats();

		// Create and set individual stats
		IndividualStats individualStats = new IndividualStats(individual.combatActions, individual.sal,
				individual.skills.getSkill("Pistol").value, individual.skills.getSkill("Rifle").value,
				individual.skills.getSkill("Launcher").value, individual.skills.getSkill("Heavy").value,
				individual.skills.getSkill("Subgun").value, true);
		individual.P1 = individualStats.P1;
		individual.P2 = individualStats.P2;

		individual.notes = textPaneNotes.getText();

		individual.KO = (int) spinnerKO.getValue();

		window.setTrooper(individual, index);
		window.refreshIndividuals();
		f.dispose();

	}

	// Sets troopers stats to equal the changed values
	// Recalculates skills based off of attribute changes
	// Sets skills equal to their skill bonus
	public void saveChangesCompany(JFrame f, Trooper individual, int index, EditCompany window) {

		// Sets basic fields
		individual.name = textFieldName.getText();
		individual.rank = textFieldRank.getText();
		individual.designation = textFieldRole.getText();
		individual.vet = textFieldVet.getText();
		individual.wep = textFieldWeapon.getText();
		individual.ammo = (int) spinnerAmmo.getValue();
		individual.kills = (int) spinnerKills.getValue();

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
		 * //individual.longRangeOptics = (int) longRangeOpticsSpinner.getValue();
		 * individual.negotiations = (int) negotiationsSpinner.getValue();
		 * individual.smallUnitTactics = (int) smallUnitTacticsSpinner.getValue();
		 */

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

		individual.shields = (int) spinnerMaxShields.getValue();
		individual.currentShields = (int) spinnerCurrentShields.getValue();
		individual.shieldChance = (int) spinnerShieldChance.getValue();

		individual.legs = (int) spinnerLegs.getValue();
		individual.arms = (int) spinnerArms.getValue();
		individual.disabledLegs = (int) spinnerDisabledLegs.getValue();
		individual.disabledArms = (int) spinnerDisabledArms.getValue();
		individual.kills = (int) spinnerKills.getValue();

		individual.timeUnconscious = (int) spinnerTimeUnconscious.getValue();
		individual.timeMortallyWounded = (int) spinnerTimeMortallyWounded.getValue();
		individual.physicianSkill = (int) spinnerPhysiciansSkill.getValue();

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

		// Sets notes and equipment
		individual.notes = textPaneNotes.getText();
		individual.eqiupment = textPaneEquipment.getText();

		individual.setPCStats();

		// Create and set individual stats
		// Create and set individual stats
		IndividualStats individualStats = new IndividualStats(individual.combatActions, individual.sal,
				individual.skills.getSkill("Pistol").value, individual.skills.getSkill("Rifle").value,
				individual.skills.getSkill("Launcher").value, individual.skills.getSkill("Heavy").value,
				individual.skills.getSkill("Subgun").value, true);

		individual.P1 = individualStats.P1;
		individual.P2 = individualStats.P2;

		individual.KO = (int) spinnerKO.getValue();

		window.setIndividual(individual, index);
		window.refreshIndividuals();
		f.dispose();

	}

	// Gets units from edit company window
	// Loops through units
	// Checks for unit name
	// Adds individual to unit
	public void addUnit(JFrame f, EditUnit window, Trooper individual, String unitName, int index) {
		boolean found = false;

		ArrayList<Unit> units = window.window.getUnits();
		if (units == null) {
			return;
		}
		for (int i = 0; i < units.size(); i++) {
			if (units.get(i).callsign.equals(unitName)) {
				found = true;
				window.window.unitAddIndividual(individual, i);
				window.deleteIndividual(index);
				window.refreshIndividuals();
				f.dispose();
			}
		}
		if (found == false) {
			textFieldUnit.setText("Invalid Unit Name");

		}

	}

	public void refreshInjuries(Trooper trooper) {

		chckbxFirstAid.setSelected(trooper.recivingFirstAid);

		spinnerRRMod.setValue(trooper.recoveryRollMod);
		lblRR.setText("RR: " + trooper.recoveryRoll);

		lblCTP.setText("CT(Phases): " + trooper.criticalTime);
		lblTP.setText("TP(Phases): " + trooper.timePassed);

		DefaultListModel listInjuries2 = new DefaultListModel();

		for (Injuries injury : trooper.injuries) {

			listInjuries2.addElement(injury.toString());

		}

		listInjuries.setModel(listInjuries2);

	}

	// Gets units from edit company window
	// Loops through units
	// Checks for unit name
	// Adds individual to unit
	public void addRoster(JFrame f, EditCompany window, Trooper individual, String unitName, int index) {

		boolean found = false;

		ArrayList<Unit> units = window.getUnits();
		if (units == null) {
			return;
		}
		for (int i = 0; i < units.size(); i++) {
			if (units.get(i).callsign.equals(unitName)) {
				found = true;
				window.unitAddIndividual(individual, i);
				window.deleteIndividual(index);
				window.refreshIndividuals();
				f.dispose();
			}
		}
		if (found == false) {
			textFieldUnit.setText("Invalid Unit Name");

		}

	}

	public void openCharacterBuilder() {
		new CharacterBuilderWindow(this.trooper);
	}

}
