package Conflict;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import Artillery.Artillery;
import Artillery.Artillery.BatteryType;
import Artillery.Shell;
import Artillery.FireMission;
import Artillery.FireMission.FireMissionStatus;
import Trooper.Trooper;
import Unit.Unit;
import Vehicle.Vehicle;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.SpinnerNumberModel;

public class ArtilleryWindow {

	/**
	 * 
	 */

	public Unit unit; 
	public GameWindow gameWindow; 
	public ConflictLog conflictLog; 
	
	private transient JFrame frame;
	private transient JScrollPane fireMissionScrollPane;
	private transient JComboBox comboBoxBattery;
	private transient JComboBox comboBoxShell;
	private transient JList fireMissionList;
	private transient JList batteryList;
	private transient JTextField batteryNameField;
	private transient JTextField fireMissionNameField;
	private transient JSpinner spinnerCrewSkill;
	private transient JSpinner spinnerTargetY;
	private transient JSpinner spinnerTargetX;
	private transient JSpinner spinnerShots;
	private transient JComboBox comboBoxSpotter;
	private transient JCheckBox chckbxCLvl;
	private transient JCheckBox chckbxLOS;
	private transient JSpinner spinnerCrewCount;
	private transient JCheckBox chckbxLosI;
	private JCheckBox chckbxPLvl;
	private JSpinner spinnerRadius;
	private JSpinner spinnerOrders;
	private JCheckBox chckbxFireBaseFo;
	private JCheckBox chckbxAssignedBat;
	private JCheckBox chckbxVehicleSpot;
	
	/**
	 * Create the application.
	 */
	public ArtilleryWindow(Unit unit, GameWindow gameWindow) {
		this.unit = unit; 
		this.gameWindow = gameWindow;
		this.conflictLog = gameWindow.conflictLog;
		initialize();
	}
	
	public ArtilleryWindow(Vehicle vehicle, GameWindow gameWindow) {
		
		var unit = new Unit();
		unit.fireMissions = vehicle.fireMissions;
		unit.individuals = vehicle.getTroopers();
		
		this.unit = unit; 
		this.gameWindow = gameWindow;
		this.conflictLog = gameWindow.conflictLog;
		initialize();
		chckbxVehicleSpot.setSelected(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 757, 869);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fireMissionScrollPane = new JScrollPane();
		fireMissionScrollPane.setBounds(470, 46, 261, 773);
		frame.getContentPane().add(fireMissionScrollPane);
		
		// Get the screen size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		// Calculate the frame location
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		//y -= screenSize.height / 5;

		// Set the new frame location
		frame.setLocation(x, y);
		
		fireMissionList = new JList();
		fireMissionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(fireMissionList.getSelectedIndex() < 0) {
					return; 
				}
				FireMission fireMission = unit.fireMissions.get(fireMissionList.getSelectedIndex());
				fireMission.window = GameWindow.gameWindow;
				
				fireMissionNameField.setText(fireMission.fireMissionDisplayName);
				
				if(fireMission.LOSToImpact) {
					chckbxLosI.setSelected(true);
				} else {
					chckbxLosI.setSelected(false);
				}
				
				if(fireMission.LOSToTarget) {
					chckbxLOS.setSelected(true);
				} else {
					chckbxLOS.setSelected(false);
				}
				
				if(fireMission.companyLevelSupport) {
					chckbxCLvl.setSelected(true);
				} else {
					chckbxCLvl.setSelected(false);
				}
				
				if(fireMission.platoonLevelSupport) {
					chckbxPLvl.setSelected(true);
				} else {
					chckbxPLvl.setSelected(false);
				}
				
				if(fireMission.firebaseFo) {
					chckbxFireBaseFo.setSelected(true);
				} else {
					chckbxFireBaseFo.setSelected(false);
				}
				if(fireMission.assignedBattery) {
					chckbxAssignedBat.setSelected(true);
				} else {
					chckbxAssignedBat.setSelected(false);
				}
				if(fireMission.vehicleFo) {
					chckbxVehicleSpot.setSelected(true);
				} else {
					chckbxVehicleSpot.setSelected(false);
				}
				
				int count = 0; 
				for(Trooper trooper : unit.getTroopers()) {
					if(!trooper.alive ||!trooper.conscious) {
						continue;
					}
					
					if(trooper == fireMission.spotter) {
						comboBoxSpotter.setSelectedIndex(count);
						break; 
					}
					
					count++; 
				}
				
				spinnerTargetX.setValue(fireMission.targetX);
				spinnerTargetY.setValue(fireMission.targetY);
				spinnerRadius.setValue(fireMission.fireMissionRadius);
				
				refreshBatteryList();
				
			}
		});
		fireMissionScrollPane.setViewportView(fireMissionList);
		
		JLabel lblNewLabel = new JLabel("Fire Missions");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(470, 10, 252, 26);
		frame.getContentPane().add(lblNewLabel);
		
		comboBoxBattery = new JComboBox();
		comboBoxBattery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				comboBoxShell.removeAllItems();
				
				if(comboBoxBattery.getSelectedIndex() < 0)
					return;
				
				BatteryType batType = null;
				
				for(int i = 0; i < BatteryType.values().length; i++) {
					if(i == comboBoxBattery.getSelectedIndex()) {
						batType = BatteryType.values()[i];
						break; 
					}
				}
				
				//System.out.println("Bat Type: "+ batType.toString());
				Artillery bat = new Artillery(batType, 0);
				
				
				for(Shell shell : bat.shells) {
					
					comboBoxShell.addItem(shell.shellName);
					
				}
				
				comboBoxShell.setSelectedIndex(0);
				spinnerCrewCount.setValue(bat.crew);
			}
		});
		comboBoxBattery.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxBattery.setBounds(10, 609, 160, 26);
		frame.getContentPane().add(comboBoxBattery);
		
		JLabel lblBattery = new JLabel("Battery");
		lblBattery.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBattery.setBounds(10, 580, 195, 26);
		frame.getContentPane().add(lblBattery);
		
		JLabel lblShell = new JLabel("Shell");
		lblShell.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblShell.setBounds(10, 632, 195, 26);
		frame.getContentPane().add(lblShell);
		
		comboBoxShell = new JComboBox();
		comboBoxShell.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxShell.setBounds(10, 659, 160, 26);
		frame.getContentPane().add(comboBoxShell);
		
		JScrollPane fireMissionScrollPane_1 = new JScrollPane();
		fireMissionScrollPane_1.setBounds(208, 46, 252, 773);
		frame.getContentPane().add(fireMissionScrollPane_1);
		
		batteryList = new JList();
		batteryList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				if(batteryList.getSelectedIndex() < 0 || fireMissionList.getSelectedIndex() < 0) {
					return; 
				}
				
				Artillery battery = unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries.get(batteryList.getSelectedIndex());
				
				batteryNameField.setText(battery.batteryDisplayName);
				spinnerCrewSkill.setValue(battery.crewQuality);
				spinnerCrewCount.setValue(battery.currentCrew);
				
				int index = 0; 
				
				
				
				for(int i = 0; i < BatteryType.values().length; i++) {
					if(battery.batteryType == BatteryType.values()[i]) {
						index = i; 
						break; 
					}
				}
				
				comboBoxBattery.setSelectedIndex(index);
				
			}
		});
		fireMissionScrollPane_1.setViewportView(batteryList);
		
		JLabel lblBatteries = new JLabel("Batteries");
		lblBatteries.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBatteries.setBounds(208, 10, 129, 26);
		frame.getContentPane().add(lblBatteries);
		
		batteryNameField = new JTextField();
		batteryNameField.setBounds(10, 555, 160, 26);
		frame.getContentPane().add(batteryNameField);
		batteryNameField.setColumns(10);
		
		JLabel lblBatteryName = new JLabel("Battery Name");
		lblBatteryName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBatteryName.setBounds(10, 528, 195, 26);
		frame.getContentPane().add(lblBatteryName);
		
		fireMissionNameField = new JTextField();
		fireMissionNameField.setColumns(10);
		fireMissionNameField.setBounds(10, 46, 160, 26);
		frame.getContentPane().add(fireMissionNameField);
		
		JLabel lblFireMissionName = new JLabel("Fire Mission Name:");
		lblFireMissionName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFireMissionName.setBounds(10, 10, 195, 26);
		frame.getContentPane().add(lblFireMissionName);
		
		JButton btnNewButton = new JButton("Add Battery");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fireMissionList.getSelectedIndex() < 0) {
					conflictLog.addNewLine("Selected Fire Mission less than 0");
					return; 
				}
				
				BatteryType type = BatteryType.M107; 
				for(BatteryType batType : BatteryType.values()) {
					if(batType.toString().equals(comboBoxBattery.getSelectedItem().toString())) {
						type = batType; 
						break; 
					}
				}
				
				Artillery battery = new Artillery(type, 
						(int) spinnerCrewSkill.getValue());
				
				battery.batteryDisplayName = batteryNameField.getText();
				battery.currentCrew = (int) spinnerCrewCount.getValue();
				unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries
					.add(battery);
				
				unit.fireMissions.get(fireMissionList.getSelectedIndex()).setCrewQuality();
				
				DefaultListModel listBatteries = new DefaultListModel();
				
				for(Artillery bat : unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries) {
					listBatteries.addElement(bat.batteryDisplayName);
				}
				
				batteryList.setModel(listBatteries);
				
				refreshBatteryList();
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}
		});
		btnNewButton.setBounds(10, 758, 160, 26);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnAddFireMission = new JButton("Update FM");
		btnAddFireMission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fireMissionList.getSelectedIndex() < 0) {
					conflictLog.addNewLine("Select Fire Mission");
					return;
				}
				
				FireMission fireMission = unit.fireMissions.get(fireMissionList.getSelectedIndex());
				
				fireMission.fireMissionDisplayName = fireMissionNameField.getText();
				fireMission.targetX = (int) spinnerTargetX.getValue();
				fireMission.targetY = (int) spinnerTargetY.getValue();
				fireMission.fireMissionRadius = (int) spinnerRadius.getValue();
				
				fireMission.companyLevelSupport = chckbxCLvl.isSelected();
				fireMission.platoonLevelSupport = chckbxPLvl.isSelected();
				fireMission.LOSToImpact = chckbxLosI.isSelected();
				fireMission.LOSToTarget = chckbxLOS.isSelected();
				fireMission.firebaseFo = chckbxFireBaseFo.isSelected();
				fireMission.assignedBattery = chckbxAssignedBat.isSelected();
				fireMission.vehicleFo = chckbxVehicleSpot.isSelected();
				
				int count = 0; 
				for(Trooper trooper : unit.getTroopers()) {
					if(!trooper.alive ||!trooper.conscious) {
						continue;
					}
					
					if(trooper == fireMission.spotter) {
						comboBoxSpotter.setSelectedIndex(count);
						break; 
					}
					
					count++; 
				}
				
				int index = fireMissionList.getSelectedIndex();
				
				refreshFireMissionList(); 
				
				fireMissionList.setSelectedIndex(index);
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}
		});
		btnAddFireMission.setBounds(10, 458, 160, 26);
		frame.getContentPane().add(btnAddFireMission);
		
		JButton btnNewButton_1 = new JButton("Remove");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(batteryList.getSelectedIndex() < 0 || fireMissionList.getSelectedIndex() < 0) {
					conflictLog.addNewLine("Select battery and fire mission.");
					return; 
				}
				
				unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries.remove(batteryList.getSelectedIndex());
				refreshBatteryList();
				
			}
		});
		btnNewButton_1.setBounds(375, 15, 85, 21);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Remove");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fireMissionList.getSelectedIndex() < 0) {
					conflictLog.addNewLine("Select fire mission.");
					return; 
				}
				
				unit.fireMissions.remove(fireMissionList.getSelectedIndex());
				
				refreshLists();
				
			}
		});
		btnNewButton_1_1.setBounds(620, 15, 111, 21);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JLabel lblCrewSkillLevel = new JLabel("Crew SL");
		lblCrewSkillLevel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCrewSkillLevel.setBounds(10, 690, 55, 26);
		frame.getContentPane().add(lblCrewSkillLevel);
		
		spinnerCrewSkill = new JSpinner();
		spinnerCrewSkill.setBounds(67, 696, 42, 20);
		frame.getContentPane().add(spinnerCrewSkill);
		
		JButton btnUpdate = new JButton("Update Bat.");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(batteryList.getSelectedIndex() < 0 || fireMissionList.getSelectedIndex() < 0) {
					conflictLog.addNewLine("Select battery and fire mission.");
					return; 
				}
				
				Artillery battery = unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries.get(batteryList.getSelectedIndex());
				
				battery.batteryDisplayName = batteryNameField.getText();
				battery.crewQuality = (int) spinnerCrewSkill.getValue();
				battery.currentCrew = (int) spinnerCrewCount.getValue();
				
				int count = 0; 
				for(BatteryType type: BatteryType.values()) {
					
					if(count == comboBoxBattery.getSelectedIndex()) {
						
						if(type != battery.batteryType) {
							Artillery newBattery = new Artillery(type, 0);
							newBattery.batteryDisplayName = batteryNameField.getText();
							newBattery.crewQuality = (int) spinnerCrewSkill.getValue();
							newBattery.currentCrew = (int) spinnerCrewCount.getValue();
							unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries.set(batteryList.getSelectedIndex(), newBattery);
						}
						
						break; 
					}
					
					count++;
				}
				
				refreshBatteryList();
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}
		});
		btnUpdate.setBounds(10, 793, 160, 26);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnUpdate_1 = new JButton("Add Fire Mission");
		btnUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//public FireMission(Trooper spotter, ArrayList<Artillery> batteries, boolean LOSToTarget, int targetX, int targetY, boolean companyLevelSupport)
				FireMission fireMission = new FireMission(unit.individuals.get(comboBoxSpotter.getSelectedIndex()), 
						new ArrayList<Artillery>(), chckbxLOS.isSelected(), (int) spinnerTargetX.getValue(), 
						(int) spinnerTargetY.getValue(), chckbxCLvl.isSelected(), gameWindow);
				fireMission.platoonLevelSupport = chckbxPLvl.isSelected();
				fireMission.fireMissionDisplayName = fireMissionNameField.getText();
				fireMission.LOSToImpact = chckbxLosI.isSelected();
				fireMission.firebaseFo = chckbxFireBaseFo.isSelected();
				fireMission.assignedBattery = chckbxAssignedBat.isSelected();
				fireMission.vehicleFo = chckbxVehicleSpot.isSelected();
				
				unit.fireMissions.add(fireMission);
				
				refreshLists();
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}
		});
		btnUpdate_1.setBounds(10, 422, 160, 26);
		frame.getContentPane().add(btnUpdate_1);
		
		JLabel lblX = new JLabel("X:");
		lblX.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblX.setBounds(10, 209, 28, 26);
		frame.getContentPane().add(lblX);
		
		spinnerTargetX = new JSpinner();
		spinnerTargetX.setBounds(31, 215, 42, 20);
		frame.getContentPane().add(spinnerTargetX);
		
		spinnerTargetY = new JSpinner();
		spinnerTargetY.setBounds(104, 215, 42, 20);
		frame.getContentPane().add(spinnerTargetY);
		
		JLabel lblY = new JLabel("Y:");
		lblY.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblY.setBounds(83, 209, 28, 26);
		frame.getContentPane().add(lblY);
		
		JButton btnUpdate_1_1 = new JButton("Fire For Effect");
		btnUpdate_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fireMissionList.getSelectedIndex() < 0 || unit.fireMissions.size() < 1) {
					conflictLog.addNewLine("Select a fire mission.");
					return; 
				} else if(unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries.size() < 1) {
					conflictLog.addNewLine("No batteries added to fire mission.");
					return; 
				} else if(unit.fireMissions.get(fireMissionList.getSelectedIndex()).fireMissionStatus != 
						FireMissionStatus.WAITING) {
					conflictLog.addNewLine("Fire mission battery's occupied.");
					return; 
				} else if(!unit.fireMissions.get(fireMissionList.getSelectedIndex()).plotted()) {
					
					conflictLog.addNewLine("Began Plotting");
					unit.fireMissions.get(fireMissionList.getSelectedIndex()).setPlotTime();
					conflictLog.addNewLine("Actions to Plotted: "+unit.fireMissions.get(fireMissionList.getSelectedIndex()).actionsToPlotted);
					return;
				}
				
				var shots = (int) spinnerShots.getValue();
				var shotIndex = comboBoxShell.getSelectedIndex();
				FireMission fireMission = unit.fireMissions.get(fireMissionList.getSelectedIndex());
				fireMission.fireForEffect(shots, shotIndex);
				fireMission.addOrders((int)spinnerOrders.getValue(), shots, shotIndex);
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}
		});
		btnUpdate_1_1.setBounds(10, 389, 160, 26);
		frame.getContentPane().add(btnUpdate_1_1);
		
		JButton btnUpdate_1_1_1 = new JButton("Adjustment");
		btnUpdate_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fireMissionList.getSelectedIndex() < 0 || unit.fireMissions.size() < 1) {
					conflictLog.addNewLine("Select a fire mission.");
					return; 
				} else if(unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries.size() < 1) {
					conflictLog.addNewLine("No batteries added to fire mission.");
					return; 
				} else if(unit.fireMissions.get(fireMissionList.getSelectedIndex()).fireMissionStatus != 
						FireMissionStatus.WAITING) {
					conflictLog.addNewLine("Fire mission battery's occupied.");
					return; 
				} else if(!unit.fireMissions.get(fireMissionList.getSelectedIndex()).plotted()) {
					
					conflictLog.addNewLine("Began Plotting");
					unit.fireMissions.get(fireMissionList.getSelectedIndex()).setPlotTime();
					conflictLog.addNewLine("Actions to Plotted: "+unit.fireMissions.get(fireMissionList.getSelectedIndex()).actionsToPlotted);
					return;
				}
				
				FireMission fireMission = unit.fireMissions.get(fireMissionList.getSelectedIndex());
				fireMission.adjustmentShot( (int) spinnerTargetX.getValue(), (int) spinnerTargetY.getValue(), (int) spinnerShots.getValue(), comboBoxShell.getSelectedIndex());
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}
		});
		btnUpdate_1_1_1.setBounds(10, 353, 160, 26);
		frame.getContentPane().add(btnUpdate_1_1_1);
		
		JLabel lblShots = new JLabel("Shots");
		lblShots.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblShots.setBounds(10, 246, 42, 26);
		frame.getContentPane().add(lblShots);
		
		spinnerShots = new JSpinner();
		spinnerShots.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerShots.setBounds(57, 251, 42, 20);
		frame.getContentPane().add(spinnerShots);
		
		JButton btnUpdate_1_1_1_1 = new JButton("Plot/Spot Round");
		btnUpdate_1_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(fireMissionList.getSelectedIndex() < 0 || unit.fireMissions.size() < 1) {
					conflictLog.addNewLine("Select a fire mission.");
					return; 
				} else if(unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries.size() < 1) {
					conflictLog.addNewLine("No batteries added to fire mission.");
					return; 
				} else if(unit.fireMissions.get(fireMissionList.getSelectedIndex()).fireMissionStatus != 
						FireMissionStatus.WAITING) {
					conflictLog.addNewLine("Fire mission battery's occupied.");
					return; 
				} else if(!unit.fireMissions.get(fireMissionList.getSelectedIndex()).plotted()) {
					
					conflictLog.addNewLine("Began Plotting");
					unit.fireMissions.get(fireMissionList.getSelectedIndex()).setPlotTime();
					conflictLog.addNewLine("Actions to Plotted: "+unit.fireMissions.get(fireMissionList.getSelectedIndex()).actionsToPlotted);
					return;
				}
				
				
				FireMission fireMission = unit.fireMissions.get(fireMissionList.getSelectedIndex());
				fireMission.rangeShot((int) spinnerShots.getValue(), comboBoxShell.getSelectedIndex());
				
				/*if(fireMission.fire) {
					
				}*/
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}
		});
		btnUpdate_1_1_1_1.setBounds(10, 317, 160, 26);
		frame.getContentPane().add(btnUpdate_1_1_1_1);
		
		JLabel lblSpotter = new JLabel("Spotter:");
		lblSpotter.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSpotter.setBounds(10, 75, 79, 26);
		frame.getContentPane().add(lblSpotter);
		
		comboBoxSpotter = new JComboBox();
		comboBoxSpotter.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxSpotter.setBounds(10, 101, 160, 20);
		frame.getContentPane().add(comboBoxSpotter);
		
		chckbxLOS = new JCheckBox("LOS to Target");
		chckbxLOS.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckbxLOS.setBounds(10, 121, 99, 21);
		frame.getContentPane().add(chckbxLOS);
		
		chckbxCLvl = new JCheckBox("Company Lvl");
		chckbxCLvl.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckbxCLvl.setBounds(114, 145, 87, 21);
		frame.getContentPane().add(chckbxCLvl);
		
		spinnerCrewCount = new JSpinner();
		spinnerCrewCount.setBounds(67, 727, 42, 20);
		frame.getContentPane().add(spinnerCrewCount);
		
		JLabel lblCrew = new JLabel("Crew");
		lblCrew.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCrew.setBounds(10, 719, 42, 26);
		frame.getContentPane().add(lblCrew);
		
		chckbxLosI = new JCheckBox("LOS to Impact");
		chckbxLosI.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckbxLosI.setBounds(10, 145, 99, 21);
		frame.getContentPane().add(chckbxLosI);
		
		JButton btnSetPlotted = new JButton("Set Plotted");
		btnSetPlotted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fireMissionList.getSelectedIndex() < 0)
					return;
				
				FireMission fm = unit.fireMissions.get(fireMissionList.getSelectedIndex());
				
				fm.plotShot();
				fm.timeSpentPlotting = 0; 
				fm.actionsToPlotted = 0; 
				
				refreshLists();
				GameWindow.gameWindow.conflictLog.addQueuedText();
				
			}
			
		});
		btnSetPlotted.setBounds(10, 280, 160, 26);
		frame.getContentPane().add(btnSetPlotted);
		
		JButton btnClearTask = new JButton("Clear Task");
		btnClearTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fireMissionList.getSelectedIndex() < 0 || fireMissionList.getSelectedIndex() >= unit.fireMissions.size())
					return;
				
				FireMission fm = unit.fireMissions.get(fireMissionList.getSelectedIndex());
				fm.fireMissionStatus = FireMissionStatus.WAITING;
				fm.actionsToFire = 0; 
				fm.actionsToPlotted = 0;
				fm.timeSpentPrepingFire = 0; 
				fm.timeSpentPlotting = 0;
				
				conflictLog.addNewLine("Fire Mission: "+fm.fireMissionDisplayName+" task cleared.");
				GameWindow.gameWindow.conflictLog.addQueuedText();
			}
		});
		btnClearTask.setBounds(10, 491, 160, 26);
		frame.getContentPane().add(btnClearTask);
		
		chckbxPLvl = new JCheckBox("Platoon Lvl");
		chckbxPLvl.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckbxPLvl.setBounds(114, 121, 87, 21);
		frame.getContentPane().add(chckbxPLvl);
		
		spinnerRadius = new JSpinner();
		spinnerRadius.setBounds(156, 215, 42, 20);
		frame.getContentPane().add(spinnerRadius);
		
		JLabel lblRadius = new JLabel("Radius");
		lblRadius.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRadius.setBounds(150, 200, 55, 15);
		frame.getContentPane().add(lblRadius);
		
		JLabel lblOrders = new JLabel("Orders");
		lblOrders.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOrders.setBounds(110, 246, 42, 26);
		frame.getContentPane().add(lblOrders);
		
		spinnerOrders = new JSpinner();
		spinnerOrders.setBounds(156, 251, 42, 20);
		frame.getContentPane().add(spinnerOrders);
		
		chckbxFireBaseFo = new JCheckBox("Fire Base Fo");
		chckbxFireBaseFo.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckbxFireBaseFo.setBounds(10, 169, 99, 21);
		frame.getContentPane().add(chckbxFireBaseFo);
		
		chckbxAssignedBat = new JCheckBox("Assigned Bat");
		chckbxAssignedBat.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckbxAssignedBat.setBounds(114, 169, 99, 21);
		frame.getContentPane().add(chckbxAssignedBat);
		
		chckbxVehicleSpot = new JCheckBox("Vehicle Spot");
		chckbxVehicleSpot.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckbxVehicleSpot.setBounds(10, 188, 99, 21);
		frame.getContentPane().add(chckbxVehicleSpot);
		frame.setVisible(true);
		
		setFields();
		
	}
	
	public void setFields() {
		
		for(BatteryType batteryType : BatteryType.values()) {
			comboBoxBattery.addItem(batteryType.toString());
		}

		
		comboBoxBattery.setSelectedIndex(0);
		
		comboBoxSpotter.removeAll();
		
		int highestSkill = 0; 
		int highestIndex = 0; 
		
		for(Trooper trooper : unit.individuals) {
			if(!trooper.conscious || !trooper.alive)
				continue; 
			
			Trooper spotter = trooper; 
			int spotterSkill = ((spotter.getSkill("Navigation") / 5) + ((spotter.per * 3) / 5)) / 2;
			
			if(spotterSkill > highestSkill) {
				highestSkill = spotterSkill; 
				highestIndex = trooper.number - 1; 
			}
			
			comboBoxSpotter.addItem(trooper.number+" "+trooper.name);
		}
		
		comboBoxSpotter.setSelectedIndex(highestIndex);
		
		DefaultListModel fireMissionList = new DefaultListModel();
		
		for(FireMission fireMission : unit.fireMissions) {
			fireMissionList.addElement(fireMission.fireMissionDisplayName);
		}
		
		this.fireMissionList.setModel(fireMissionList);
		
		refreshLists();
		
	}
	
	public void refreshLists() {
		
		refreshFireMissionList();
		refreshBatteryList();
		
	}
	
	public void refreshFireMissionList() {
		
		fireMissionList.removeAll();
		
		DefaultListModel listFireMission = new DefaultListModel();
		
		for(FireMission fireMission : unit.fireMissions) {
			listFireMission.addElement(fireMission.toString());
		}
		
		fireMissionList.setModel(listFireMission);
		
	}
	
	public void refreshBatteryList() {
		if(fireMissionList.getSelectedIndex() < 0) {
			batteryList.removeAll();
			DefaultListModel listBatteries = new DefaultListModel();
			batteryList.setModel(listBatteries);
			return; 
		}
		
		batteryList.removeAll();
		
		DefaultListModel listBatteries = new DefaultListModel();
		
		for(Artillery battery : unit.fireMissions.get(fireMissionList.getSelectedIndex()).batteries) {
			listBatteries.addElement(battery.toString());
		}
		
		batteryList.setModel(listBatteries);
		
	}
}
